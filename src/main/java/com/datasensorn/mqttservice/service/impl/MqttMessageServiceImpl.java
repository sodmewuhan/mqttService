package com.datasensorn.mqttservice.service.impl;

import com.datasensorn.mqttservice.Utils.Constant;
import com.datasensorn.mqttservice.dto.BoxStatusDTO;
import com.datasensorn.mqttservice.influxdb.InfluxDBUtil;
import com.datasensorn.mqttservice.model.biz.DeviceSetAutoInfo;
import com.datasensorn.mqttservice.model.biz.UpWaterQualityDTO;
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
import org.springframework.util.Assert;

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
                    String[] topics = topic.split(Constant.SPLIT);
                    if (topics == null || topics.length < 3) {
                        LOGGER.error("上传的信息中，没有物联网卡号或者 上传类型的信息");
                        return;
                    }
                    String msg = String.valueOf(message.getPayload());
                    LOGGER.info("***************MqttMessageServiceImpl the receive message is " + msg);
                    // 得到设备的物联网卡号
                    String boxId = topics[1];
                    String topicEx = topics[2]; // 上传的是控制设备还是水质信息

                    if (StringUtils.isEmpty(boxId) || StringUtils.isEmpty(topicEx)) {
                        LOGGER.error("上传的信息中，没有物联网卡号或者 上传类型的信息");
                        return;
                    }

                    deliverMessage(boxId,topicEx,msg);

//                    if (isValidJSON(msg)) {
//                        JSONObject jsonObject = JSON.parseObject(msg);
//                        if (jsonObject.getIntValue(DEVICE) < 10) {
//                            // TODO 保存上传的水质数据
//                            savePoolStatusToInfluxdb(topics[1], jsonObject);
//                        } else {
//                            // TODO 保存上传的设备数据
//                            saveDeviceStatusToInfluxdb(topics[1], jsonObject);
//                            saveDeviceStatusToDB(topics[1],jsonObject);
//                        }
//                    } else {
//                        // 其他类型的上传
//                        String prefix = msg.substring(0,1);
//                        if ("t".equals(prefix)) {
//                            // 保存定时开关
//                        } else if ("o".equals(prefix)) {
//                            // 保存上下限
//                            saveDeviceTopLow(topics[1],msg);
//                        }
//                    }
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage(),e);
            }
        }
    }

    /**
     * 处理上传的消息
     * 上传水质   主题：物联网卡号/VX  payload 数值
     *      V1 : 溶解氧
     *      V2 ： 温度
     *      V3 ： PH值
     *
     *      例如：主题：UP/15919829955/V1  内容：66
     *
     * 上传设备开启：   物联网卡号/DX 0    0 关闭  1 打开
     *      例如：159119829955/D1 0  关闭物联网卡号159119829955设备1
     *      主题：UP/159119829955/D1 0 关闭物联网卡
     * @param topicEx
     */
    private void deliverMessage(String boxId, String topicEx,String message) {
        Assert.notNull(boxId,"设备编号不能为空");
        Assert.notNull(topicEx,"控制主体不能为空");
        Assert.notNull(message,"消息体不能为空");

        if (topicEx.length() != 2) {
            LOGGER.error("上传的控制信息不正确");
            return;
        }

        String control= topicEx.substring(0,1);
        if ("V".equalsIgnoreCase(control)) {
            // 上传的是水质情况
            String id = topicEx.substring(1,2);
            if ("1".equalsIgnoreCase(id)) {
                // 溶解氧
                UpWaterQualityDTO upWaterQualityDTO = new UpWaterQualityDTO();
                upWaterQualityDTO.setBoxid(boxId);
                upWaterQualityDTO.setDeviceId(id);
                upWaterQualityDTO.setValue(message);

                savePoolStatusToInfluxdb(upWaterQualityDTO);
            }
            // TODO 扩展其他的数值
        }

        if ("D".equalsIgnoreCase(control)) {
            // 上传的是设备控制信息
            String id = topicEx.substring(1,2);
            UpWaterQualityDTO upWaterQualityDTO = new UpWaterQualityDTO();
            upWaterQualityDTO.setBoxid(boxId);
            upWaterQualityDTO.setDeviceId(id);
            upWaterQualityDTO.setValue(message);
            saveDeviceStatusToInfluxdb(upWaterQualityDTO);
            // 保存数据库
            saveDeviceStatusToDB(upWaterQualityDTO);
        }
    }

//    @Deprecated
//    private void savePoolStatusToInfluxdb(String topic, JSONObject jsonObject) {
//        Map<String, Object> fields = Maps.newHashMap();
//        Map<String, String> tags = Maps.newHashMap();
//
//        tags.put("TAG_CODE", topic);
//        fields.put(Constant.INFLUXDB_COL_BOXID,topic);
//        fields.put(Constant.INFLUXDB_COL_DEVICEID,jsonObject.getString(DEVICE));
//        fields.put(Constant.INFLUXDB_COL_VALUE,jsonObject.getString(VALUE));
//        influxDBUtil.insert(Constant.INFLUXDB_NAME_FISH, tags, fields);
//
//        LOGGER.info("the function savePoolStatusToInfluxdb save the data to inflxdb success." );
//    }

    private void savePoolStatusToInfluxdb(UpWaterQualityDTO upWaterQualityDTO) {
        Map<String, Object> fields = Maps.newHashMap();
        Map<String, String> tags = Maps.newHashMap();

        tags.put("TAG_CODE", upWaterQualityDTO.getBoxid());
        fields.put(Constant.INFLUXDB_COL_BOXID,upWaterQualityDTO.getBoxid());
        fields.put(Constant.INFLUXDB_COL_DEVICEID,upWaterQualityDTO.getDeviceId());
        fields.put(Constant.INFLUXDB_COL_VALUE,upWaterQualityDTO.getValue());
        influxDBUtil.insert(Constant.INFLUXDB_NAME_FISH, tags, fields);

        LOGGER.info("the function savePoolStatusToInfluxdb save the data to inflxdb success." );
    }

//    private void saveDeviceStatusToInfluxdb(String topic,JSONObject jsonObject) {
//
//        Map<String, Object> fields = Maps.newHashMap();
//        Map<String, String> tags = Maps.newHashMap();
//
//        tags.put("TAG_CODE", topic);
//        fields.put(Constant.INFLUXDB_COL_BOXID,topic);
//        fields.put(Constant.INFLUXDB_COL_DEVICEID,jsonObject.getIntValue(DEVICE) - 10);
//        fields.put(Constant.INFLUXDB_COL_VALUE,jsonObject.getString(VALUE));
//        influxDBUtil.insert(Constant.INFLUXDB_NAME_DEVICE, tags, fields);
//
//        LOGGER.info("the function savePoolStatusToInfluxdb save the data to inflxdb success." );
//    }

    private void saveDeviceStatusToInfluxdb(UpWaterQualityDTO upWaterQualityDTO) {

        Map<String, Object> fields = Maps.newHashMap();
        Map<String, String> tags = Maps.newHashMap();

        tags.put("TAG_CODE", upWaterQualityDTO.getBoxid());
        fields.put(Constant.INFLUXDB_COL_BOXID,upWaterQualityDTO.getBoxid());
        fields.put(Constant.INFLUXDB_COL_DEVICEID,upWaterQualityDTO.getDeviceId());
        fields.put(Constant.INFLUXDB_COL_VALUE,upWaterQualityDTO.getValue());
        influxDBUtil.insert(Constant.INFLUXDB_NAME_DEVICE, tags, fields);

        LOGGER.info("the function savePoolStatusToInfluxdb save the data to inflxdb success." );
    }

    private void saveDeviceStatusToDB(UpWaterQualityDTO upWaterQualityDTO) {
        // 更新到数据库中
        BoxStatusDTO boxStatusDTO = new BoxStatusDTO();
        boxStatusDTO.setBoxnumber(upWaterQualityDTO.getBoxid());
        boxStatusDTO.setDeviceid(upWaterQualityDTO.getDeviceId());
        boxStatusDTO.setStatus(upWaterQualityDTO.getValue());
        try {
            boxInfoService.setBoxStatus(boxStatusDTO);
        } catch (Exception e) {
            LOGGER.error("the save boxInfo occure error,the error is " + e.getMessage(),e);
        }

        LOGGER.info("the function saveDeviceStatusToDB save the data to DB success." );
    }
//    private boolean isValidJSON(String str) {
//        try {
//            JSONObject.parseObject(str);
//        } catch (JSONException ex) {
//            try {
//                JSONObject.parseArray(str);
//            } catch (JSONException ex1) {
//                return false;
//            }
//        }
//        return true;
//    }

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
