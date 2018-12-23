package com.datasensorn.mqttservice.controller.model;

import lombok.Data;

/**
 * 用户信息的传递
 */
@Data
public class UserInfoDTO {

    private Integer id;

    private String phone;

    private String password;

    private String username;

    private String phonenum;

    private String usertype;

    private String companyname;

    private String province;

    private String city;
}
