package com.datasensorn.mqttservice.model.biz.mapper;

import com.datasensorn.mqttservice.model.biz.BoxStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoxStatusMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BoxStatus record);

    int insertSelective(BoxStatus record);

    BoxStatus selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BoxStatus record);

    int updateByPrimaryKey(BoxStatus record);

    List<BoxStatus> selectByBoxId(@Param("boxnumber")String boxnumber);

    void updateByBoxNumber(BoxStatus boxStatus);
}