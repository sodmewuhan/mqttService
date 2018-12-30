package com.datasensorn.mqttservice.controller;

import com.alibaba.fastjson.JSON;
import com.datasensorn.mqttservice.Utils.ResultGenerator;
import com.datasensorn.mqttservice.controller.model.LoginParmObject;
import com.datasensorn.mqttservice.controller.model.UserInfoDTO;
import com.datasensorn.mqttservice.model.Result;
import com.datasensorn.mqttservice.model.biz.UserInfo;
import com.datasensorn.mqttservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理控制类
 */
@RestController
@RequestMapping(value="/api/user")
public class UserRestController {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserRestController.class);

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody LoginParmObject parmObject) {

        boolean retn = userService.logon(parmObject.getUsername(),parmObject.getPassword());
        ResultGenerator resultGenerator = new ResultGenerator();
        return resultGenerator.genSuccessResult(retn);

    }

    /**
     * 根据用户登录的电话号码，得到用户信息
     * @param userInfoDTO
     * @return
     */
    @RequestMapping(value = "/findUser", method = RequestMethod.POST)
    public Result findUser(@RequestBody UserInfoDTO userInfoDTO) {
        Assert.notNull(userInfoDTO,"用户查询参数为空");
        Assert.hasText(userInfoDTO.getUsername(),"用户查询参数为空");
        try {
            UserInfo user = userService.findUserByUserName(userInfoDTO.getUsername());
            if (user != null) {
                ResultGenerator resultGenerator = new ResultGenerator();
                return resultGenerator.genSuccessResult(user);
            } else {
                ResultGenerator resultGenerator = new ResultGenerator();
                return resultGenerator.genFailResult("没有找到该用户");
            }
        }catch (Exception e) {
            LOGGER.error("call the function findUserByPhone error ",e);
            ResultGenerator resultGenerator = new ResultGenerator();
            return resultGenerator.genFailResult("服务错误，错误信息为" + e.getMessage());
        }

    }

    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    public Result updateUserInfo(@RequestBody UserInfoDTO userInfoDTO) {
        LOGGER.info("update the user info the parameter is " + JSON.toJSONString(userInfoDTO,true));
        ResultGenerator resultGenerator = new ResultGenerator();
        try {
            boolean retn = userService.updateUserInfo(userInfoDTO);
            if (retn) {
                return resultGenerator.genSuccessResult();
            } else {
                return resultGenerator.genFailResult("数据更新失败");
            }
        } catch (Exception e) {
            LOGGER.error("用户数据更新失败",e);
            return resultGenerator.genFailResult(e.getMessage());
        }
    }
}
