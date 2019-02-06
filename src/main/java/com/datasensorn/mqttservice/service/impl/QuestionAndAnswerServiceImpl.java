package com.datasensorn.mqttservice.service.impl;

import com.alibaba.fastjson.JSON;
import com.datasensorn.mqttservice.controller.model.*;
import com.datasensorn.mqttservice.model.biz.Answer;
import com.datasensorn.mqttservice.model.biz.Favourite;
import com.datasensorn.mqttservice.model.biz.Question;
import com.datasensorn.mqttservice.model.biz.mapper.AnswerMapper;
import com.datasensorn.mqttservice.model.biz.mapper.FavouriteMapper;
import com.datasensorn.mqttservice.model.biz.mapper.QuestionMapper;
import com.datasensorn.mqttservice.service.QuestionAndAnswerService;
import com.google.common.collect.Lists;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

@Service
public class QuestionAndAnswerServiceImpl implements QuestionAndAnswerService {

    private final static Logger LOGGER = LoggerFactory.getLogger(QuestionAndAnswerServiceImpl.class);

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private AnswerMapper answerMapper;

    @Autowired
    private FavouriteMapper favouriteMapper;

    @Override
    public void createQuestion(QuestionDTO questionDTO) throws Exception{
        Assert.notNull(questionDTO,"参数对象不能为空");
        Assert.notNull(questionDTO.getQtitle(),"标题不能为空");
        Assert.notNull(questionDTO.getQcontent(),"内容不能为空");
        Assert.notNull(questionDTO.getUsername(),"提问人不能为空");

        LOGGER.info("the paramter is " + JSON.toJSONString(questionDTO,true));
        Question question = new Question();
        BeanUtils.copyProperties(question,questionDTO);
        question.setCreatetime(new Date());
        question.setUpdatetime(new Date());

        questionMapper.insert(question);
    }

    @Override
    public List<FeedDTO> getFeeds() throws Exception {
        LOGGER.info("call the function getFeeds");

        List<FeedDTO> dtoList = Lists.newArrayList();
        List<Question> questions = questionMapper.getFeeds();
        if (CollectionUtils.isNotEmpty(questions)) {
            questions.stream().forEach(
                    v -> {
                        try {
                            FeedDTO dto = new FeedDTO();
                            BeanUtils.copyProperties(dto,v);
                            dto.setQuestionId(v.getId());
                            dto.setAskUsername(v.getUsername());
                            dtoList.add(dto);
                        } catch (Exception e) {
                            LOGGER.error("copyProperties error",e);
                        }
                    }
            );
        }
        return dtoList;
    }

    @Override
    public QuestionDetailDTO getQuestionById(QuestionDTO questionDTO) throws Exception {
        Assert.notNull(questionDTO,"参数对象不能为空");
        Assert.notNull(questionDTO.getId(),"问题编号不能为空");
        Assert.notNull(questionDTO.getCurUsername(),"当前用户不能为空");

        QuestionDetailDTO ret = new QuestionDetailDTO();

        Question question = questionMapper.selectByPrimaryKey(questionDTO.getId());
        if (question != null) {
            // 设置问题信息
            BeanUtils.copyProperties(ret,question);
            ret.setQuestionId(question.getId());
            ret.setQcreatetime(question.getCreatetime());
            ret.setQusername(question.getUsername());
            // 得到回答列表
            List<Answer> list = answerMapper.selectAnswerByQuestionId(questionDTO.getId());
            if (CollectionUtils.isNotEmpty(list)) {
                list.stream().forEach(
                        v-> {
                            AnswerDTO answerDTO = new AnswerDTO();
                            try {
                                BeanUtils.copyProperties(answerDTO,v);
                            } catch (Exception e) {
                                LOGGER.error("copyProperties error is " + e.getMessage(),e);
                            }
                            answerDTO.setAnswerId(v.getId());
                            ret.getAnswerDTOList().add(answerDTO);
                        }
                );
            }
            // 得到关注信息
            FavouriteDTO favouriteDTO = new FavouriteDTO();
            favouriteDTO.setQuestionId(questionDTO.getId());
            favouriteDTO.setUsername(questionDTO.getCurUsername());
            FavouriteDTO retFavouriteDTO = getFaourite(favouriteDTO);
            ret.setFavouriteDTO(retFavouriteDTO);

        }
        return ret;
    }

    @Override
    public void createAnswer(AnswerDTO answerDTO) throws Exception {
        // 参数检查
        Assert.notNull(answerDTO.getQuestionId(),"问题编号不能为空");
        Assert.notNull(answerDTO.getAnswerUsername(),"回答用户名不能为空");
        Assert.notNull(answerDTO.getComent(),"回答内容不能为空");

        Answer answer = new Answer();
        BeanUtils.copyProperties(answer,answerDTO);
        answer.setUsername(answerDTO.getAnswerUsername());
        answer.setCreatetime(new Date());
        answerDTO.setLastupdatetime(new Date());
        answerMapper.insert(answer);
    }

    @Override
    public FavouriteDTO getFaourite(FavouriteDTO favouriteDTO) throws Exception {
        // 参数检查
        Assert.notNull(favouriteDTO.getUsername(),"参数用户名不能为空");
        Assert.notNull(favouriteDTO.getQuestionId(),"参数问题ID不能为空");

        FavouriteDTO ret = new FavouriteDTO();
        BeanUtils.copyProperties(ret,favouriteDTO);
        // 得到当前用户是否关注
        Favourite favourite = new Favourite();
        BeanUtils.copyProperties(favourite,favouriteDTO);
        Favourite getFavourite = favouriteMapper.isFavouriteByUsername(favourite);
        if (getFavourite==null || !getFavourite.getIsfavourite()) {
            ret.setFavourite(false);
        } else {
            ret.setFavourite(true);
        }
        // 得到当前问题的关注人数
        int favouriteCount = favouriteMapper.getFavouriteCount(favourite);
        ret.setFavouriteCount(favouriteCount);
        return ret;
    }
}
