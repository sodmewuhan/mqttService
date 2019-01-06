package com.datasensorn.mqttservice.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.datasensorn.mqttservice.Utils.Constant;
import com.datasensorn.mqttservice.dto.BoxStatusDTO;
import com.datasensorn.mqttservice.influxdb.InfluxDBUtil;
import com.datasensorn.mqttservice.model.biz.DeviceSetAutoInfo;
import com.datasensorn.mqttservice.model.biz.mapper.DeviceSetAutoInfoMapper;
import com.datasensorn.mqttservice.service.BoxInfoService;
import com.datasensorn.mqttservice.service.MqttMessageService;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MqttMessageServiceImpl implements MqttMessageService {

    private final static Logger LOGGER = LoggerFactory.getLogger(MqttMessageServiceImpl.class);

    @Autowired
    private BoxInfoService boxInfoService;

    @Autowired
    private InfluxDBUtil influxDBUtil;

    @Autowired
    private DeviceSetAutoInfoMapper deviceSetAutoInfoMapper;

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
                    } else {
                        // 其他类型的上传
                        String prefix = msg.substring(0,1);
                        if ("t".equals(prefix)) {
                            // 保存定时开关
                        } else if ("o".equals(prefix)) {
                            // 保存上下限
                            saveDeviceTopLow(topics[1],msg);
                        }
                    }
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage(),e);
            }
        }
    }

    private void savePoolStatusToInfluxdb(String topic, JSONObject jsonObject) {
        Map<String, Object> fields = Maps.newHashMap();
        Map<String, String> tags = Maps.newHashMap();

        tags.put("TAG_CODE", topic);
        fields.put(Constant.INFLUXDB_COL_BOXID,topic);
        fields.put(Constant.INFLUXDB_COL_DEVICEID,jsonObject.getString(DEVICE));
        fields.put(Constant.INFLUXDB_COL_VALUE,jsonObject.getString(VALUE));
        influxDBUtil.insert(Constant.INFLUXDB_NAME_FISH, tags, fields);

        LOGGER.info("the function savePoolStatusToInfluxdb save the data to inflxdb success." );
    }
    private void saveDeviceStatusToInfluxdb(String topic,JSONObject jsonObject) {

        Map<String, Object> fields = Maps.newHashMap();
        Map<String, String> tags = Maps.newHashMap();

        tags.put("TAG_CODE", topic);
        fields.put(Constant.INFLUXDB_COL_BOXID,topic);
        fields.put(Constant.INFLUXDB_COL_DEVICEID,jsonObject.getIntValue(DEVICE) - 10);
        fields.put(Constant.INFLUXDB_COL_VALUE,jsonObject.getString(VALUE));
        influxDBUtil.insert(Constant.INFLUXDB_NAME_DEVICE, tags, fields);

        LOGGER.info("the function savePoolStatusToInfluxdb save the data to inflxdb success." );
    }

    private void saveDeviceStatusToDB(String topic,JSONObject jsonObject ) {
        // 更新到数据库中
        Integer deviceId = Integer.valueOf(jsonObject.getIntValue(DEVICE)) - 10;
        BoxStatusDTO boxStatusDTO = new BoxStatusDTO();
        boxStatusDTO.setBoxnumber(topic);
        boxStatusDTO.setDeviceid(deviceId.toString());
        boxStatusDTO.setStatus(jsonObject.getString(VALUE));
        try {
            boxInfoService.setBoxStatus(boxStatusDTO);
        } catch (Exception e) {
            LOGGER.error("the save boxInfo occure error,the error is " + e.getMessage(),e);
        }

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

    /** 保存设备的最高和最低值 */
    private void saveDeviceTopLow(String topic,String msg) {
        String topstr = msg.substring(1,4);
        String lowstr = msg.substring(4,7);

        Double top = Double.parseDouble(topstr);
        Double low = Double.parseDouble(lowstr);
        if (StringUtils.isNotEmpty(topic)) {
            DeviceSetAutoInfo deviceSetAutoInfo = deviceSetAutoInfoMapper.selectByBoxId(topic);
            if (deviceSetAutoInfo != null) {
                DeviceSetAutoInfo updateObject = new DeviceSetAutoInfo();
                updateObject.setId(deviceSetAutoInfo.getId());
                updateObject.setTopvaule(top);
                updateObject.setLowvalue(low);
                deviceSetAutoInfoMapper.updateByPrimaryKeySelective(updateObject);
            } else {
                DeviceSetAutoInfo insertObject = new DeviceSetAutoInfo();
                insertObject.setBoxId(topic);
                insertObject.setLowvalue(low);
                insertObject.setTopvaule(top);
                deviceSetAutoInfoMapper.insert(insertObject);
            }
        }

    }
}
