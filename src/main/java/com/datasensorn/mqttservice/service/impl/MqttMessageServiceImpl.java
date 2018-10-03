package com.datasensorn.mqttservice.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.datasensorn.mqttservice.Utils.Constant;
import com.datasensorn.mqttservice.influxdb.InfluxDBUtil;
import com.datasensorn.mqttservice.model.biz.BoxStatus;
import com.datasensorn.mqttservice.service.BoxInfoService;
import com.datasensorn.mqttservice.service.MqttMessageService;
import org.apache.commons.lang3.StringUtils;
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

    private final static String DEVICE = "device";

    @Override
    public void handleMsg(Message<?> message) {
        if (message==null || message.getPayload()==null) {
            throw new MessagingException("the message is empty");
        } else {
            try {
                String topic = String.valueOf(message.getHeaders().get("mqtt_topic"));
                if (topic.contains(Constant.MQTT_PREFIX)) {
                    //如果是上报数据
                    String[] topics = topic.split(Constant.MQTT_PREFIX);
                    String msg = String.valueOf(message.getPayload());
                    if (isValidJSON(msg)) {
                        JSONObject jsonObject = JSON.parseObject(msg);
                        //设备上传水质情况
                        if (StringUtils.contains(msg,VALUE)) {
                            //保存至influxdb数据库中
                            InfluxDB influxDB = influxDBUtil.getInfluxDB();
                            // measurement 数据库中的表  point 数据库中的记录
                            final Point p = Point.measurement(Constant.INFLUXDB_NAME)
                                    .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                                    .addField(Constant.INFLUXDB_COL_BOXID,topics[1])
                                    .addField(Constant.INFLUXDB_COL_DEVICEID,jsonObject.getIntValue(DEVICE))
                                    .addField(Constant.INFLUXDB_COL_VALUE,jsonObject.getString(VALUE))
                                    .build();
                            influxDB.write(p);
                        } else if (StringUtils.contains(msg,STATE)) {
                            // 上传当前的设备状态，保存到MySQL数据库 。
                            String boxId = topics[1];
                            String deviceId = jsonObject.get("deviceId")==null ? null :jsonObject.get("deviceId").toString();
                            String state = jsonObject.get("state")==null ? null :jsonObject.get("state").toString();
//                            BoxStatus boxStatus = new BoxStatus(boxId,deviceId ,state);
//                            boxInfoService.updateBoxStatus(boxStatus);
                        }
                    }
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage(),e);
            }
        }
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
