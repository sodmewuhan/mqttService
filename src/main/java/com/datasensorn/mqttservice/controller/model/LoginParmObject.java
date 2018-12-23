package com.datasensorn.mqttservice.controller.model;

import lombok.Data;

/**
 * 登录参数
 */
@Data
public class LoginParmObject {

    private String phone;

    private String password;

    private String username;
}
