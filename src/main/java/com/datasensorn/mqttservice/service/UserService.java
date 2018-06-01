package com.datasensorn.mqttservice.service;

import com.datasensorn.mqttservice.model.biz.User;

/**
 * 用户服务
 */
public interface UserService {

    /**
     * 注册用户
     * @param user
     */
    public void registerUser(User user);

    /**
     * 用户登录
     * @param accountName
     * @param password    对password解密
     */
    public boolean logon(String accountName ,String password);
}
