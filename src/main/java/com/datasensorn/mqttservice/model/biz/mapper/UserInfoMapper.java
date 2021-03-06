package com.datasensorn.mqttservice.model.biz.mapper;

import com.datasensorn.mqttservice.model.biz.UserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);

    /**
     * 用户登录校验
     * @param user
     * @return
     */
    int checkUserLogin(UserInfo user);

    /**
     * 根据用户电话，得到用户信息
     * @param phone
     * @return
     */
    UserInfo findUserByUserName(String phone);
}