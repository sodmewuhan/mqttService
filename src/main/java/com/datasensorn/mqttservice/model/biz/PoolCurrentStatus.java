package com.datasensorn.mqttservice.model.biz;

/**
 * 池塘的当前状态
 */
public class PoolCurrentStatus {

    private Float oxygen;  //溶氧量

    private Float ph; // PH值

    private Float temp; //温度

    private Float other;  //其他

    public Float getOxygen() {
        return oxygen;
    }

    public void setOxygen(Float oxygen) {
        this.oxygen = oxygen;
    }

    public Float getPh() {
        return ph;
    }

    public void setPh(Float ph) {
        this.ph = ph;
    }

    public Float getTemp() {
        return temp;
    }

    public void setTemp(Float temp) {
        this.temp = temp;
    }

    public Float getOther() {
        return other;
    }

    public void setOther(Float other) {
        this.other = other;
    }
}
