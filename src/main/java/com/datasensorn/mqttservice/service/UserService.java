package com.datasensorn.mqttservice.service;

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
    public boolean logon(String accountName ,String password);

    /**
     * 根据用户名称，得到用户信息
     * @return
     */
    public UserInfo findUserByUserName(String userName);
}
