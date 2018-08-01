package com.datasensorn.mqttservice.mqtt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.datasensorn.mqttservice.Utils.Constant;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;;

import java.util.concurrent.TimeUnit;

public class PushCallback implements MqttCallback {

    private final static Logger LOGGER = LoggerFactory.getLogger(PushCallback.class);

    private MiddlewareMqttClient service;

    public PushCallback() {
    }

    public PushCallback(MiddlewareMqttClient service) {
        this.service = service;
    }

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
     */
    //@Async("executorService")
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        //主题
        if (null == message.getPayload()) {
           //s throw new MessagingException("the topic is empty.");
        } else {
            LOGGER.info( "Recevied the list Topic: {} >>> Recevied Message: {}",
                    topic, new String(message.getPayload()));
            if (topic.contains("fish/")) {
                //如果是上报数据
                String[] topics = topic.split(Constant.MQTT_PREFIX);
                String msg = new String(message.getPayload());
                if (isValidJSON(msg)) {
                    JSONObject jsonObject = JSON.parseObject(msg);
                    //保存至数据库中
                    InfluxDB influxDB = service.getInfluxDBUtil().getInfluxDB();
//                    // measurement 数据库中的表  point 数据库中的记录
                    final Point p = Point.measurement(Constant.INFLUXDB_NAME)
                            .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                            .addField(Constant.INFLUXDB_COL_BOXID,topics[1])
                            .addField(Constant.INFLUXDB_COL_DEVICEID,jsonObject.getIntValue("device"))
                            .addField(Constant.INFLUXDB_COL_VALUE,jsonObject.getString("value"))
                            .build();
                    influxDB.write(p);
//                            .write(service.getInfluxDBSettings().getDatabase(),
//                            service.getInfluxDBSettings().getRetentionpolicy(),
//                            p);
                }

            }
        }

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

        //LOGGER.info("the message is delivery overed");
    }

    /**
     * 判断json是否合法
     * @param str
     * @return
     */
    private boolean isValidJSON(String str) {
        try {
            JSONObject.parseObject(str);
        } catch (JSONException ex) {
            try {
                JSONObject.parseArray(str);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;

    }


}
