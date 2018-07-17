package com.datasensorn.mqttservice.controller.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.Serializable;

/**
 * 指令对象
 */
public class InstructionObject implements Serializable {

    @JSONField(serialize = false)
    private String topic;// 设备关联电话

    private String deviceId;// 设备的探头ID

    private Boolean onOff;  //设备开关

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Boolean getOnOff() {
        return onOff;
    }

    public void setOnOff(Boolean onOff) {
        this.onOff = onOff;
    }
}
