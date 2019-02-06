package com.datasensorn.mqttservice.controller.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Feed 信息流的对象
 */
@Data
public class FeedDTO {

    /**
     * 问题编号
     */
    private  Integer questionId;

    // 回答问题的用户名
    private List<String> answerUsername;

    // 提出问题的用户名
    private String askUsername;

    // 问题标题
    private String qtitle;

    // 问题内容
    private String qcontent;

    // 回答内容
    private String answerContent;

    // 创建时间
    private Date createtime;
}
