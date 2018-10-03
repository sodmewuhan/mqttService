package com.datasensorn.mqttservice.controller.model;

/**
 * 设备下发消息
 */
public class DeviceMessage {
    private String deviceId;

    private Integer action ;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }
}
