package com.datasensorn.mqttservice.handle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.datasensorn.mqttservice.Utils.Constant;
import org.influxdb.dto.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.influxdb.InfluxDBTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class InputHandler implements MessageHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(InputHandler.class);


    //上传数据保存至influxdb数据库
    @Autowired
    private InfluxDBTemplate<Point> influxDBTemplate;

    /**
     * 上报消息的处理，用线程来处理
     * @param message
     * @throws MessagingException
     */
    @Async("executorService")
    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        //主题
        if (null ==  message.getHeaders().get("mqtt_topic") || null == message.getPayload()) {
            throw new MessagingException("the topic is empty.");
        } else {
            String topic = message.getHeaders().get("mqtt_topic").toString();
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
}
