package com.datasensorn.mqttservice.controller;

import com.datasensorn.mqttservice.Utils.ResultGenerator;
import com.datasensorn.mqttservice.controller.model.BoxInfoDDTO;
import com.datasensorn.mqttservice.controller.model.UserInfoDTO;
import com.datasensorn.mqttservice.dto.BoxStatusDTO;
import com.datasensorn.mqttservice.exception.ServiceException;
import com.datasensorn.mqttservice.model.Result;
import com.datasensorn.mqttservice.model.biz.BoxInfo;
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
@RequestMapping(value="/api/box")
public class BoxMangeRestControll {

    private final static Logger LOGGER = LoggerFactory.getLogger(BoxMangeRestControll.class);

    @Autowired
    private BoxInfoService boxInfoService;

    /**
     * 增加盒子信息，用户id不能为空
     * @return
     */
    @RequestMapping(value = "/addBoxInfo", method = RequestMethod.POST)
    public Result addBoxInfo(@RequestBody BoxInfoDDTO boxInfo) {
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
     * 根据用户，得到该用户名下的所有设备
     * @param userInfoDTO
     * @return
     */
    @RequestMapping(value = "/getBoxInfoByUser", method = RequestMethod.POST)
    public Result getBoxInfoByUser(@RequestBody UserInfoDTO userInfoDTO) {
        LOGGER.info("======findBoxInfosByUser========");
        try {
            List<BoxInfo> list = boxInfoService.getBoxInfoByUser(userInfoDTO);
            ResultGenerator resultGenerator = new ResultGenerator();
            return resultGenerator.genSuccessResult(list);
        } catch (Exception e) {
            ResultGenerator resultGenerator = new ResultGenerator();
            return resultGenerator.genFailResult(e.getMessage());
        }

    }
    /**
     * 删除盒子信息
     * @return
     */
    @Deprecated
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
    @Deprecated
    @RequestMapping(value = "/getBoxStatus/{boxId}", method = RequestMethod.GET)
    public Result getBoxStatus(@PathVariable("boxId") String boxId) {
        LOGGER.info("begin called the function getBoxStatus");
        List<BoxStatusDTO> boxStatusDTO = boxInfoService.getBoxStatus(boxId);
        ResultGenerator resultGenerator = new ResultGenerator();
        return resultGenerator.genSuccessResult(boxStatusDTO);
    }

    /**
     *
     * @param boxStatusDTO
     * @return
     */
    @Deprecated
    @RequestMapping(value = "/setBoxStatus", method = RequestMethod.POST)
    @ResponseBody
    public Result setBoxStatus(@RequestBody BoxStatusDTO boxStatusDTO) {
        ResultGenerator resultGenerator = new ResultGenerator();
        boxInfoService.setBoxStatus(boxStatusDTO);
        return resultGenerator.genSuccessResult();
    }
}
