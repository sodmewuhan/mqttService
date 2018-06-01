package com.datasensorn.mqttservice.controller;

import com.datasensorn.mqttservice.Utils.ResultGenerator;
import com.datasensorn.mqttservice.exception.ServiceException;
import com.datasensorn.mqttservice.model.Result;
import com.datasensorn.mqttservice.model.biz.BoxInfo;
import com.datasensorn.mqttservice.service.BoxInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 盒子管理接口
 * 用户在APP上添加盒子，盒子与鱼塘绑定
 * 盒子与设备绑定
 * 鱼塘与探头绑定
 */
@RestController
@RequestMapping(value="/app/box")
public class BoxMangeRestControll {

    private final static Logger LOGGER = LoggerFactory.getLogger(BoxMangeRestControll.class);

    @Autowired
    private BoxInfoService boxInfoService;

    /**
     * 增加盒子信息，用户id不能为空
     * @return
     */
    @RequestMapping(value = "/addBoxInfo", method = RequestMethod.POST)
    public Result addBoxInfo(@RequestBody  BoxInfo boxInfo) {
        LOGGER.info("======add box information========");

        try {
            boxInfoService.addBoxInfo(boxInfo);
            ResultGenerator resultGenerator = new ResultGenerator();
            return resultGenerator.genSuccessResult();
        } catch (ServiceException e) {
            ResultGenerator resultGenerator = new ResultGenerator();
            return resultGenerator.genFailResult(e.getMessage());
        }

    }

    /**
     * 删除盒子信息
     * @param boxInfo
     * @return
     */
    public Result delBoxInfo(@RequestBody  BoxInfo boxInfo) {

        return null;
    }
}
