package com.datasensorn.mqttservice.model.biz;

import java.util.Date;

/**
 * 图片的点值
 */
public class ChartPoint {

    //时间
    private Date time;

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


}
