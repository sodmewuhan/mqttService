package com.datasensorn.mqttservice.service.impl;

import com.datasensorn.mqttservice.Utils.Constant;
import com.datasensorn.mqttservice.Utils.JwtHelper;
import com.datasensorn.mqttservice.controller.model.UserInfoDTO;
import com.datasensorn.mqttservice.model.biz.UserInfo;
import com.datasensorn.mqttservice.model.biz.WeatherArea;
import com.datasensorn.mqttservice.model.biz.mapper.UserInfoMapper;
import com.datasensorn.mqttservice.model.biz.mapper.WeatherAreaMapper;
import com.datasensorn.mqttservice.service.UserService;
import com.google.common.collect.Maps;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    WeatherAreaMapper weatherAreaMapper; // 地区mapper

    @Autowired
    StringRedisTemplate stringRedisTemplate;  // redis 工具

    private static final Integer EXTEND_DAY = 1;

    private static final Integer EXTEND_TIME = 30;

    @Override
    public UserInfo logon(String userName, String password) {
        Assert.notNull(userName, "parameter userName is empty");
        Assert.notNull(password, "password phone is empty");

        LOGGER.info("begin check user login the parameter is the userName :" + userName +
                " and password :" + password);
        UserInfo user = new UserInfo();
        user.setUsername(userName);
        user.setPassword(password);
        UserInfo userInfo = userInfoMapper.checkUserLogin(user);
        if (userInfo != null) {
            // 生成TOKEN
            Map<String,String> param = Maps.newHashMap();
            param.put(Constant.USER_NAME,userInfo.getUsername());
            param.put(Constant.PHONE,userInfo.getPhone());
            String token = JwtHelper.getToken(param);

            userInfo.setToken(token);
            reNewToken(token,userInfo);
        }
        return  userInfo;
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
            BeanUtils.copyProperties (userInfo,userInfoDTO);
            userInfoMapper.insert(userInfo);
        } else {
            throw new Exception("该用户名已经存在");
        }
    }

    @Override
    public UserInfo verifyUserByToken(String token) throws Exception {
        Map<String,String> map = JwtHelper.verfiyToken(token);
        if (map != null && !map.isEmpty()) {
            String username = map.get(Constant.USER_NAME);
            if (StringUtils.isNotEmpty(username)) {
                UserInfo userInfo = findUserByUserName(username);
                // 得到redis的token失效时间
                Long expire = stringRedisTemplate.getExpire(username);
                if (expire > 0) {
                    // token还未到失效时间
                    reNewToken(token, userInfo);
                    userInfo.setToken(token);
                    return userInfo;
                } else {
                    throw new Exception("用户token已经过期，请重新登录");
                }
            }
        }
        return null;
    }

    // 延长token的时间，保存到redis缓存中
    private void reNewToken(String token,UserInfo userInfo) {
        // 保存至redis
        stringRedisTemplate.opsForValue().set(userInfo.getUsername(),token,EXTEND_DAY,TimeUnit.DAYS);
//        // 延长过期时间
//        stringRedisTemplate.expire(userInfo.getUsername(),EXTEND_DAY, TimeUnit.DAYS);
    }

    @Override
    public void invalidate(String token) {
        Map<String,String> map = JwtHelper.verfiyToken(token);
        if (map != null && !map.isEmpty()) {
            String key = map.get(Constant.USER_NAME);
           stringRedisTemplate.delete(key);
        }
    }
}
