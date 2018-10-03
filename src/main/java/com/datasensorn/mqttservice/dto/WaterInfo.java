package com.datasensorn.mqttservice.dto;
/**
 * 当前的水质情况
 */
public class WaterInfo {

    /** 溶氧量 */
    public float oxygen;

    /** PH值 */
    public float phValue;

    /** 温度 */
    public float tempertor;

    public float getOxygen() {
        return oxygen;
    }

    public void setOxygen(float oxygen) {
        this.oxygen = oxygen;
    }

    public float getPhValue() {
        return phValue;
    }

    public void setPhValue(float phValue) {
        this.phValue = phValue;
    }

    public float getTempertor() {
        return tempertor;
    }

    public void setTempertor(float tempertor) {
        this.tempertor = tempertor;
    }
}
