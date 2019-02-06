package com.datasensorn.mqttservice.controller.model;

import lombok.Data;

import java.util.Date;

/**
 * 问题回答对象
 */
@Data
public class AnswerDTO {
    // 回答编号
    private Integer answerId;

    private String answerUsername;

    // 回答内容
    private String coment;
    // 回答时间
    private Date createtime;

    private Date lastupdatetime;
    // 问题编号
    private Integer questionId;

}
