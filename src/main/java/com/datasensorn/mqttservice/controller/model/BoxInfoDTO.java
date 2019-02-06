package com.datasensorn.mqttservice.controller.model;

import lombok.Data;

/**
 * 传递的盒子信息
 */
@Data
public class BoxInfoDTO {

    private Integer id;

    private String boxnumber;

    private String registerphone;

    private String boxsimnumber;

    private Integer userid;

    private String boxname;

    private String boxtype;

    /**
     * 前端传送的用户名
     */
    private String username;
}
