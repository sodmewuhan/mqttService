package com.datasensorn.mqttservice.model.biz;

import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.Date;

/**
 * 图片的点值
 */
public class ChartPoint {

    //时间
    private Date time;
    //盒子编号
    private String boxid;
    //探头编号
    private String deviceId;
    //值
    private Float value;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public String getBoxid() {
        return boxid;
    }

    public void setBoxid(String boxid) {
        this.boxid = boxid;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
