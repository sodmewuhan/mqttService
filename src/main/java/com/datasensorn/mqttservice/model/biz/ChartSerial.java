package com.datasensorn.mqttservice.model.biz;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 图表序列
 */
public class ChartSerial {

    private List<ChartPoint> points = new ArrayList<>();

    //设备类型
    private String deviceType;



    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public List<ChartPoint> getPoints() {
        return points;
    }

    public void setPoints(List<ChartPoint> points) {
        this.points = points;
    }
}
