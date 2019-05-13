package com.datasensorn.mqttservice.model;

import lombok.Data;

/**
 * 返回消息的统一对象
 */
@Data
public class Result {

    private int code;

    private String message;

    private Object data;
}
