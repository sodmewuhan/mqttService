package com.datasensorn.mqttservice.mqtt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.datasensorn.mqttservice.Utils.Constant;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.influxdb.dto.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.influxdb.InfluxDBTemplate;
import org.springframework.messaging.MessagingException;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.TimeUnit;

public class PushCallback implements MqttCallback {

    private final static Logger LOGGER = LoggerFactory.getLogger(PushCallback.class);

    private MiddlewareMqttClient service;

    public PushCallback(MiddlewareMqttClient service) {
        this.service = service;
    }

    //上传数据保存至influxdb数据库
    @Autowired
    private InfluxDBTemplate<Point> influxDBTemplate;

    @Override
    public void connectionLost(Throwable cause) {
        while (true){
            try {//如果没有发生异常说明连接成功，如果发生异常，则死循环
                Thread.sleep(1000);
                service.init();
                break;
            }catch (Exception e){
                continue;
            }
        }
    }

    /**
     * 异步执行
     * @param topic
     * @param message
     * @throws Exception
     */
    @Async("executorService")
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {

        //主题
        if (null == message.getPayload()) {
            throw new MessagingException("the topic is empty.");
        } else {
            LOGGER.info( "Recevied the list Topic: {} >>> Recevied Message: {}",
                    topic, message.getPayload());
            if (topic.contains("fish/")) {
                //如果是上报数据
                String[] topics = topic.split(Constant.MQTT_PREFIX);
                JSONObject jsonObject = JSON.parseObject(message.getPayload().toString());
                //保存至数据库中
                influxDBTemplate.createDatabase();
                // measurement 数据库中的表  point 数据库中的记录
                final Point p = Point.measurement(Constant.INFLUXDB_NAME)
                        .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                        .addField(Constant.INFLUXDB_COL_BOXID,topics[1])
                        .addField(Constant.INFLUXDB_COL_DEVICEID,jsonObject.getIntValue("device"))
                        .addField(Constant.INFLUXDB_COL_VALUE,jsonObject.getString("value"))
                        .build();
                influxDBTemplate.write(p);
            }
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}
