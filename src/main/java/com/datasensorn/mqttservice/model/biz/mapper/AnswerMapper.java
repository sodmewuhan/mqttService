package com.datasensorn.mqttservice.model.biz.mapper;

import com.datasensorn.mqttservice.model.biz.Answer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 问题回答
 */
@Mapper
public interface AnswerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Answer record);

    int insertSelective(Answer record);

    Answer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Answer record);

    int updateByPrimaryKey(Answer record);

    /**
     * 根据问题编号得到回答的信息
     * @param questionId
     * @return
     */
    List<Answer> selectAnswerByQuestionId(@Param("questionId") Integer questionId);

    /**
     * 得到该问题的所有的回答总数
     * @param questionId
     * @return
     */
    Integer getAnswerCount(@Param("questionId") Integer questionId);
}