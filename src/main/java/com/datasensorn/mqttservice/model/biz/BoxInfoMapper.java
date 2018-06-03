package com.datasensorn.mqttservice.model.biz;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Delete;
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

    /**
     * 删除盒子
     * @param boxInfoId
     * @return
     */
    @Delete("delete from t_boxinfo where id = #{0}")
    public int delBoxInfo(Integer boxInfoId);

}
