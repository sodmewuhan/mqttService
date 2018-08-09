package com.datasensorn.mqttservice.model.biz;

import lombok.Getter;
import lombok.Setter;

/**
 * 池塘的当前状态
 */
@Getter
@Setter
public class PoolCurrentStatus {

    private Float oxygen;  //溶氧量

    private Float ph; // PH值

    private Float temp; //温度

    private Float other;  //其他
}
