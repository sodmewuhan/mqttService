package com.datasensorn.mqttservice.mqtt;

import com.datasensorn.mqttservice.influxdb.InfluxDBUtil;
import com.datasensorn.mqttservice.model.InfluxDBSettings;
import com.datasensorn.mqttservice.model.MqttSettings;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class MiddlewareMqttClient {

    private final static Logger LOGGER = LoggerFactory.getLogger(MiddlewareMqttClient.class);

    private static MqttClient client;

    private final static String CLIENTID = "mqttServicePublish";

    @Autowired
    private MqttSettings mqttSettings;

    @Autowired
   private InfluxDBSettings influxDBSettings;

    @Autowired
    private InfluxDBUtil influxDBUtil;
//
//    @Autowired
//    PushCallback pushCallback;

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
            client = new MqttClient( mqttSettings.getBrokerUrl(), CLIENTID, new MemoryPersistence());
            client.setCallback(new PushCallback(MiddlewareMqttClient.this));
        }
        MqttConnectOptions options = getOptions();
        //判断拦截状态，这里注意一下，如果没有这个判断，是非常坑的
        if (!client.isConnected()) {
            client.connect(options);
            LOGGER.info("==============mqtt 连接成功，订阅主题" + mqttSettings.getTopic());
            client.subscribe(mqttSettings.getTopic());
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {

                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {

                    LOGGER.info("recvice message is " + message.getPayload());
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {

                }
            });
        }else {//这里的逻辑是如果连接成功就重新连接
            client.disconnect();
            client.connect(getOptions());
            client.subscribe(mqttSettings.getTopic());
            //client.setCallback(pushCallback);
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

    public static MqttClient getClient() {
        return client;
    }

    public InfluxDBSettings getInfluxDBSettings() {
        return influxDBSettings;
    }

    public InfluxDBUtil getInfluxDBUtil() {
        return influxDBUtil;
    }


}
