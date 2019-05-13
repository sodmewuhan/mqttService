package com.datasensorn.mqttservice.model.biz;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    private Integer id;

    private String phone;

    private String password;

    private String username;

    private String phonenum;

    private String usertype;

    private String companyname;

    private String province;

    private String city;

    private String cityId;

    /**
     * jwt的token信息
     */
    private String token;
}