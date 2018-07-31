package com.datasensorn.mqttservice.model.Request;

import java.util.List;

/**
 * echarts 前端控件的图表数据
 */
public class ChartData {

    private String title;

    // 坐标说明
    private List<String> axis;

    // 点说明
    private List<Float> point;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAxis() {
        return axis;
    }

    public void setAxis(List<String> axis) {
        this.axis = axis;
    }

    public List<Float> getPoint() {
        return point;
    }

    public void setPoint(List<Float> point) {
        this.point = point;
    }
}
