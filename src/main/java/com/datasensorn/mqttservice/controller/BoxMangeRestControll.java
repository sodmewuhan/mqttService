package com.datasensorn.mqttservice.controller;

import com.datasensorn.mqttservice.Utils.ResultGenerator;
import com.datasensorn.mqttservice.exception.ServiceException;
import com.datasensorn.mqttservice.model.Result;
import com.datasensorn.mqttservice.model.biz.BoxInfo;
import com.datasensorn.mqttservice.model.biz.BoxStatus;
import com.datasensorn.mqttservice.service.BoxInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * @return
     */
    @RequestMapping(value = "/delBoxInfo", method = RequestMethod.POST)
    public Result delBoxInfo(@RequestBody  Integer boxInfoId) {

        LOGGER.info("======delete box information========");

        try {
            boxInfoService.delBoxInfo(boxInfoId);
            ResultGenerator resultGenerator = new ResultGenerator();
            return resultGenerator.genSuccessResult();
        } catch (ServiceException e) {
            ResultGenerator resultGenerator = new ResultGenerator();
            return resultGenerator.genFailResult(e.getMessage());
        }
    }

    /**
     * 得到盒子下面的所有设备的当前状态（增氧机，投饵机等）
     */
    @RequestMapping(value = "/getBoxStatus", method = RequestMethod.POST)
    public Result getBoxStatus(@RequestParam(value = "boxId", required = true)String boxId) {

        List<BoxStatus> boxStatus = boxInfoService.getBoxStatus(boxId);
        ResultGenerator resultGenerator = new ResultGenerator();
        return resultGenerator.genSuccessResult(boxStatus);
    }

}
