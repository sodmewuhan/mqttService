package com.datasensorn.mqttservice.controller.model;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class QuestionDetailDTO {

    // 问题ID
    private Integer questionId;

    // 问题标题
    private String qtitle;
    // 问题内容
    private String qcontent;
    // 问题提出人
    private String qusername;
    // 问题提出时间
    private Date qcreatetime;

    // 回答列表
    private List<AnswerDTO> answerDTOList = Lists.newArrayList();

    // 得到关注信息
    private FavouriteDTO favouriteDTO;

    // 问题回答的数量
    private  Integer answerCount;

    // 是否是我的问题
    private boolean isMyQuestion;
}
