package com.datasensorn.mqttservice.controller.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 指令对象
 */
@Getter
@Setter
@EqualsAndHashCode
public class InstructionObject implements Serializable {

    @JSONField(serialize = false)
    private String topic;// 设备关联电话

    private String deviceId;// 设备的探头ID

    private String action;  //设备开关

}
