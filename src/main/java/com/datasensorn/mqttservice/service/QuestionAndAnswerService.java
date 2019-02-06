package com.datasensorn.mqttservice.service;

import com.datasensorn.mqttservice.controller.model.*;
import com.datasensorn.mqttservice.model.Result;

import java.util.List;

/**
 * 问答服务接口
 */
public interface QuestionAndAnswerService {

    public void createQuestion(QuestionDTO questionDTO) throws Exception;

    public List<FeedDTO> getFeeds() throws Exception;

    /**
     * 根据ID得到问题列表
     * @param questionDTO
     * @return
     */
    public QuestionDetailDTO getQuestionById(QuestionDTO questionDTO) throws Exception;

    /**
     * 创建一个回答
     * @param answerDTO
     */
    void createAnswer(AnswerDTO answerDTO)  throws Exception;

    /**
     * 得到关注的信息
     * @return
     * @throws Exception
     */
    FavouriteDTO getFaourite(FavouriteDTO favouriteDTO) throws  Exception;
}
