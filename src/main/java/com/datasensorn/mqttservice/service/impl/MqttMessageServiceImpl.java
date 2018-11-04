package com.datasensorn.mqttservice.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.datasensorn.mqttservice.Utils.Constant;
import com.datasensorn.mqttservice.dto.BoxStatusDTO;
import com.datasensorn.mqttservice.influxdb.InfluxDBUtil;
import com.datasensorn.mqttservice.service.BoxInfoService;
import com.datasensorn.mqttservice.service.MqttMessageService;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class MqttMessageServiceImpl implements MqttMessageService {

    private final static Logger LOGGER = LoggerFactory.getLogger(MqttMessageServiceImpl.class);

    @Autowired
    private BoxInfoService boxInfoService;

    @Autowired
    private InfluxDBUtil influxDBUtil;

    private final static String VALUE = "value";

    private final static String STATE = "state";

    private final static String DEVICE = "deviceId";

    @Override
    public void handleMsg(Message<?> message) {
        if (message==null || message.getPayload()==null) {
            throw new MessagingException("the message is empty");
        } else {
            try {
                String topic = String.valueOf(message.getHeaders().get("mqtt_receivedTopic"));
                if (topic.contains(Constant.MQTT_PREFIX)) {
                    //如果是上报数据
                    String[] topics = topic.split(Constant.MQTT_PREFIX);
                    String msg = String.valueOf(message.getPayload());
                    LOGGER.info("***************MqttMessageServiceImpl the receive message is " + msg);
                    if (isValidJSON(msg)) {
                        JSONObject jsonObject = JSON.parseObject(msg);
                        if (jsonObject.getIntValue(DEVICE) < 10) {
                            // TODO 保存上传的水质数据
                            savePoolStatusToInfluxdb(topics[1], jsonObject);
                        } else {
                            // TODO 保存上传的设备数据
                            saveDeviceStatusToInfluxdb(topics[1], jsonObject);
                            saveDeviceStatusToDB(topics[1],jsonObject);
                        }
                    }
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage(),e);
            }
        }
    }

    private void savePoolStatusToInfluxdb(String topic, JSONObject jsonObject) {
        InfluxDB influxDB = influxDBUtil.getInfluxDB();
        // measurement 数据库中的表  point 数据库中的记录
        final Point p = Point.measurement(Constant.INFLUXDB_NAME_FISH)
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .addField(Constant.INFLUXDB_COL_BOXID, topic)
                .addField(Constant.INFLUXDB_COL_DEVICEID,jsonObject.getIntValue(DEVICE))
                .addField(Constant.INFLUXDB_COL_VALUE,jsonObject.getString(VALUE))
                .build();
        influxDB.write(p);
        LOGGER.info("the function savePoolStatusToInfluxdb save the data to inflxdb success." );
    }
    private void saveDeviceStatusToInfluxdb(String topic,JSONObject jsonObject) {
        InfluxDB influxDB = influxDBUtil.getInfluxDB();
        // measurement 数据库中的表  point 数据库中的记录
        final Point p = Point.measurement(Constant.INFLUXDB_NAME_DEVICE)
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .addField(Constant.INFLUXDB_COL_BOXID, topic)
                .addField(Constant.INFLUXDB_COL_DEVICEID,jsonObject.getIntValue(DEVICE) - 10)
                .addField(Constant.INFLUXDB_COL_VALUE,jsonObject.getString(VALUE))
                .build();
        influxDB.write(p);
        LOGGER.info("the function saveDeviceStatusToInfluxdb save the data to inflxdb success." );
    }

    private void saveDeviceStatusToDB(String topic,JSONObject jsonObject ) {
        // 更新到数据库中
        Integer deviceId = Integer.valueOf(jsonObject.getIntValue(DEVICE)) - 10;
        BoxStatusDTO boxStatusDTO = new BoxStatusDTO();
        boxStatusDTO.setBoxnumber(topic);
        boxStatusDTO.setDeviceid(deviceId.toString());
        boxStatusDTO.setStatus(jsonObject.getString(VALUE));
        boxInfoService.setBoxStatus(boxStatusDTO);
        LOGGER.info("the function saveDeviceStatusToDB save the data to DB success." );
    }
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
