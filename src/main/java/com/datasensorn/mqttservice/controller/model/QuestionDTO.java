package com.datasensorn.mqttservice.controller.model;

import lombok.Data;

import java.util.Date;

@Data
public class QuestionDTO {

    private Integer id;

    private String qtitle;

    private String qcontent;

    private String username;

    private Date createtime;

    private Date updatetime;

    // 请求时的当前用户
    private String curUsername;
}
