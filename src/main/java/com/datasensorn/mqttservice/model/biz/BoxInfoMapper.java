package com.datasensorn.mqttservice.model.biz;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoxInfoMapper {

    /**
     * 增加盒子信息
     * @param boxInfo
     * @return
     */
    @Insert("insert into t_boxinfo(boxNumber,registerPhone,boxSimNumber,userId) " +
            "values(#{boxNumber},#{registerPhone},#{boxSimNumber},#{userId})")
    public int addBoxInfo(BoxInfo boxInfo);
}
