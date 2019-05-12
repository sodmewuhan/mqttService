package com.datasensorn.mqttservice.service.impl;

import com.datasensorn.mqttservice.controller.model.UserInfoDTO;
import com.datasensorn.mqttservice.model.biz.UserInfo;
import com.datasensorn.mqttservice.model.biz.WeatherArea;
import com.datasensorn.mqttservice.model.biz.mapper.UserInfoMapper;
import com.datasensorn.mqttservice.model.biz.mapper.WeatherAreaMapper;
import com.datasensorn.mqttservice.service.UserService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    WeatherAreaMapper weatherAreaMapper; // 地区mapper

    @Autowired
    StringRedisTemplate redisTemplate;  // redis 工具

    @Override
    public UserInfo logon(String userName, String password) {
        Assert.notNull(userName, "parameter userName is empty");
        Assert.notNull(password, "password phone is empty");

        LOGGER.info("begin check user login the parameter is the userName :" + userName +
                " and password :" + password);
        UserInfo user = new UserInfo();
        user.setUsername(userName);
        user.setPassword(password);
        return userInfoMapper.checkUserLogin(user);
    }

    @Override
    public UserInfo findUserByUserName(String userName) {
        Assert.notNull(userName, "parameter userName is empty");
        UserInfo  userInfo = userInfoMapper.findUserByUserName(userName);
        if (userInfo != null && !StringUtils.isEmpty(userInfo.getCity())) {
            WeatherArea weatherArea = weatherAreaMapper.getAreaIdByName(userInfo.getCity());
            if (weatherArea!=null) {
                userInfo.setCityId(weatherArea.getAreaid());
                return userInfo;
            }
            return userInfo;
        } else {
            return userInfo;
        }
    }

    @Override
    public boolean updateUserInfo(UserInfoDTO userInfoDTO) throws Exception{
        Assert.notNull(userInfoDTO,"参数对象不能为空");
        Assert.notNull(userInfoDTO.getId(),"更新对象编号不能为空");

        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userInfo,userInfoDTO);
        userInfoMapper.updateByPrimaryKeySelective(userInfo);
        LOGGER.info("用户数据更新成功");
        return true;
    }

    @Override
    public void registerUser(UserInfoDTO userInfoDTO) throws Exception {
        Assert.notNull(userInfoDTO,"参数对象不能为空");
        Assert.hasText(userInfoDTO.getPhone(),"用户电话不能为空");
        Assert.hasText(userInfoDTO.getPassword(),"密码不能为空");

        // 检查用户名是否存在
        UserInfo  userInfo = userInfoMapper.findUserByPhone(userInfoDTO.getPhone());
        if (userInfo == null) {
            userInfo = new UserInfo();
            BeanUtils.copyProperties(userInfo,userInfoDTO);
            userInfoMapper.insert(userInfo);
        } else {
            throw new Exception("该用户名已经存在");
        }
    }

    private void reNewToken() {

    }
}
