package com.datasensorn.mqttservice.model.biz;

import java.util.ArrayList;
import java.util.List;

/**
 * 对
 */
public class AxisDatas {

    private String title;//显示的数据名称

    private List<String> xAxis = new ArrayList<>(); //横坐标

    private List<Float> yAxis = new ArrayList<>(); //纵坐标

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getxAxis() {
        return xAxis;
    }

    public void setxAxis(List<String> xAxis) {
        this.xAxis = xAxis;
    }

    public List<Float> getyAxis() {
        return yAxis;
    }

    public void setyAxis(List<Float> yAxis) {
        this.yAxis = yAxis;
    }
}
