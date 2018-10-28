package com.datasensorn.mqttservice.controller.model;

/**
 * 设备下发消息
 */
public class DeviceMessage {
    private Integer deviceId;

    private Integer action ;

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }
}
