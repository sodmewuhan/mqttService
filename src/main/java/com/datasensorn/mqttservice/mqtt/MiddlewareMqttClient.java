package com.datasensorn.mqttservice.mqtt;

import com.datasensorn.mqttservice.influxdb.InfluxDBUtil;
import com.datasensorn.mqttservice.model.InfluxDBSettings;
import com.datasensorn.mqttservice.model.MqttSettings;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MiddlewareMqttClient {

    private final static Logger LOGGER = LoggerFactory.getLogger(MiddlewareMqttClient.class);

    private MqttClient client;

    @Autowired
    private MqttSettings mqttSettings;

    @Autowired
   private InfluxDBSettings influxDBSettings;

    @Autowired
    private InfluxDBUtil influxDBUtil;

    private static final Integer  QOS = 2;//投递消息，保证值投递一次

    private static final Boolean RETAINED = true; //MQ持久消息

    public MqttConnectOptions getOptions() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(false);
        options.setUserName(mqttSettings.getUsername());
        options.setPassword(mqttSettings.getPassword().toCharArray());
        options.setConnectionTimeout(mqttSettings.getTimeout());
        options.setKeepAliveInterval(mqttSettings.getHeartbeat());

        return options;
    }

    public void connect() throws MqttException {
        //防止重复创建MQTTClient实例
        if (client==null) {
            client = new MqttClient( mqttSettings.getBrokerUrl(),
                    mqttSettings.getPublisherName(), new MemoryPersistence());
            client.setCallback(new PushCallback(this));
        }
        MqttConnectOptions options = getOptions();
        client.connect(options);
        //判断拦截状态，这里注意一下，如果没有这个判断，是非常坑的
        if (!client.isConnected()) {
            client.subscribe(mqttSettings.getTopic());
            LOGGER.info("==============mqtt 连接成功，订阅主题" + mqttSettings.getTopic());
        }else {//这里的逻辑是如果连接成功就重新连接
            client.disconnect();
            client.connect(getOptions());
            client.subscribe(mqttSettings.getTopic());
            client.setCallback(new PushCallback(this));
            LOGGER.info("mqtt重新连接成功，订阅主题" + mqttSettings.getTopic());
        }
    }

    @PostConstruct
    public void init() {
        try {
            connect();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e.getCause());
        }
    }

//    public static MqttClient getClient() {
//        return client;
//    }

    public InfluxDBSettings getInfluxDBSettings() {
        return influxDBSettings;
    }

    public InfluxDBUtil getInfluxDBUtil() {
        return influxDBUtil;
    }


    /**
     * 向终端下发指令
     * @param msg
     */
    public void publishMessage(String topic ,String msg) throws Exception {

        MqttMessage message = new MqttMessage(msg.getBytes());
        message.setQos(QOS);
        message.setRetained(RETAINED);
        client.publish(topic,message);
    }
}
