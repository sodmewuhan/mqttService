package com.datasensorn.mqttservice.model.Request;

/**
 * 图请求的参数
 */
public class ChartRequest {

    private String boxId;

    private String deviceId;

    private int days;

    public String getBoxId() {
        return boxId;
    }

    public void setBoxId(String boxId) {
        this.boxId = boxId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }
}
