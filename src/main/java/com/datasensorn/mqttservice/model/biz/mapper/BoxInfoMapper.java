package com.datasensorn.mqttservice.model.biz.mapper;

import com.datasensorn.mqttservice.model.biz.BoxInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoxInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BoxInfo record);

    int insertSelective(BoxInfo record);

    BoxInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BoxInfo record);

    int updateByPrimaryKey(BoxInfo record);

    /**
     * 根据盒子的编号，得到盒子信息
     * @param record
     * @return
     */
    BoxInfo findBoxInfoByBoxNumber(@Param("entity") BoxInfo record);

    /**
     * 得到用户名下的所有设备信息
     * @param userId 用户编号
     * @return
     */
    List<BoxInfo> findBoxInfosByUserId(@Param("userid") Integer userId);


}