package com.datasensorn.mqttservice.service;

import com.datasensorn.mqttservice.controller.model.UserInfoDTO;
import com.datasensorn.mqttservice.model.Result;
import com.datasensorn.mqttservice.model.biz.UserInfo;

/**
 * 用户服务
 */
public interface UserService {

    /**
     * 用户登录
     * @param accountName
     * @param password    对password解密
     */
    public UserInfo logon(String accountName , String password);

    /**
     * 根据用户名称，得到用户信息
     * @return
     */
    public UserInfo findUserByUserName(String userName);

    /**
     *  更新用户信息
     * @param userInfoDTO 用户信息
     * @return
     */
    public boolean updateUserInfo(UserInfoDTO userInfoDTO) throws Exception;

    /**
     * 注册用户
     * @param userInfoDTO
     * @throws Exception
     */
    public void registerUser(UserInfoDTO userInfoDTO) throws Exception;
}
