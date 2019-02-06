package com.datasensorn.mqttservice.controller;

import com.datasensorn.mqttservice.Utils.ResultGenerator;
import com.datasensorn.mqttservice.controller.model.AnswerDTO;
import com.datasensorn.mqttservice.controller.model.FeedDTO;
import com.datasensorn.mqttservice.controller.model.QuestionDTO;
import com.datasensorn.mqttservice.controller.model.QuestionDetailDTO;
import com.datasensorn.mqttservice.model.Result;
import com.datasensorn.mqttservice.service.QuestionAndAnswerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 问答接口
 */
@RestController
@RequestMapping(value="/api/community")
public class QuestionAndAnswerController {

    private final static Logger LOGGER = LoggerFactory.getLogger(QuestionAndAnswerController.class);

    @Autowired
    private QuestionAndAnswerService questionAndAnswerService;

    /**
     * 创建问题接口
     * @param questionDTO
     * @return
     */
    @PostMapping("/createQuestion")
    public Result  createQuestion(@RequestBody QuestionDTO questionDTO) {
        ResultGenerator resultGenerator = new ResultGenerator();
        try {
            questionAndAnswerService.createQuestion(questionDTO);
            return resultGenerator.genSuccessResult();
        } catch (Exception e) {
            LOGGER.error("the interface createQuestion occure ,the error is " + e.getMessage());
            return resultGenerator.genFailResult(e.getMessage());
        }
    }

    @PostMapping("/getFeeds")
    public Result getFeeds() {
        ResultGenerator resultGenerator = new ResultGenerator();
        try {
            List<FeedDTO> lists = questionAndAnswerService.getFeeds();
            return resultGenerator.genSuccessResult(lists);
        } catch (Exception e) {
            LOGGER.error("the interface getFeeds occure ,the error is " + e.getMessage());
            return resultGenerator.genFailResult(e.getMessage());
        }
    }

    @PostMapping("/getQuestionById")
    public Result getQuestionById(@RequestBody QuestionDTO questionDTO) {
        ResultGenerator resultGenerator = new ResultGenerator();
        try {
            QuestionDetailDTO dto = questionAndAnswerService.getQuestionById(questionDTO);
            return resultGenerator.genSuccessResult(dto);
        } catch (Exception e) {
            LOGGER.error("the interface getQuestionById occure ,the error is " + e.getMessage());
            return resultGenerator.genFailResult(e.getMessage());
        }
    }

    /**
     * 创建一个回答
     * @param answerDTO
     * @return
     */
    @PostMapping("/createAnswer")
    public Result createAnswer(@RequestBody AnswerDTO answerDTO) {
        ResultGenerator resultGenerator = new ResultGenerator();
        try {
            questionAndAnswerService.createAnswer(answerDTO);
            return resultGenerator.genSuccessResult();
        } catch (Exception e) {
            LOGGER.error("the interface createAnswer occure ,the error is " + e.getMessage());
            return resultGenerator.genFailResult(e.getMessage());
        }
    }
}
