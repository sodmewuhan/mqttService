package com.datasensorn.mqttservice.service.impl;

import com.alibaba.fastjson.JSON;
import com.datasensorn.mqttservice.controller.model.BoxInfoDDTO;
import com.datasensorn.mqttservice.controller.model.InstructionObject;
import com.datasensorn.mqttservice.controller.model.UserInfoDTO;
import com.datasensorn.mqttservice.dto.BoxStatusDTO;
import com.datasensorn.mqttservice.exception.ServiceException;

import com.datasensorn.mqttservice.model.biz.BoxInfo;
import com.datasensorn.mqttservice.model.biz.BoxStatus;
import com.datasensorn.mqttservice.model.biz.UserInfo;
import com.datasensorn.mqttservice.model.biz.mapper.BoxInfoMapper;
import com.datasensorn.mqttservice.model.biz.mapper.BoxStatusMapper;

import com.datasensorn.mqttservice.mqtt.MqttGateway;
import com.datasensorn.mqttservice.service.BoxInfoService;
import com.datasensorn.mqttservice.service.UserService;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class BoxInfoServiceImpl implements BoxInfoService {

    private final static Logger LOGGER = LoggerFactory.getLogger(BoxInfoServiceImpl.class);

    @Autowired
    private BoxInfoMapper boxInfoMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private BoxStatusMapper boxStatusMapper;

    @Autowired
    private MqttGateway mqttGateway;

    @Override
    public int addBoxInfo(BoxInfoDDTO boxInfo) throws ServiceException {

        Assert.notNull(boxInfo,"传递对象不能为空");
        Assert.notNull(boxInfo.getBoxnumber(),"盒子编号不能为空");
        Assert.notNull(boxInfo.getUsername(),"用户编号不能为空");

        LOGGER.info("the addBoxInfo parameter is " + JSON.toJSONString(boxInfo,true));
        UserInfo userInfo = userService.findUserByUserName(boxInfo.getUsername());
        if (userInfo == null) {
            throw  new ServiceException("系统查询不到该用户");
        }
        BoxInfo boxInfoModel = new BoxInfo();
        BeanUtils.copyProperties(boxInfo,boxInfoModel);
        BoxInfo findBox = boxInfoMapper.findBoxInfoByBoxNumber(boxInfoModel);
        if (findBox != null) {
            // 更新盒子信息
            findBox.setUserid(userInfo.getId());
            findBox.setBoxname(boxInfo.getBoxname());
            boxInfoMapper.updateByPrimaryKeySelective(findBox);
        } else {
            throw  new ServiceException("没有找到该盒子信息");
        }
        return 0;
    }

    @Override
    public List<BoxInfo> getBoxInfoByUser(UserInfoDTO userInfoDTO) throws Exception {
        Assert.notNull(userInfoDTO,"传递参数对象不能为空");
        Assert.notNull(userInfoDTO.getUsername(),"传递参数用户名称不能为空");
        UserInfo userInfo = userService.findUserByUserName(userInfoDTO.getUsername());
        if (userInfo == null) {
            throw  new ServiceException("系统查询不到该用户");
        }
        return boxInfoMapper.findBoxInfosByUserId(userInfo.getId());
    }

    @Override
    public int delBoxInfo(Integer boxInfoId) throws ServiceException {

        if (boxInfoId == null) {
            throw new ServiceException("删除盒子信息ID为空");
        }

//        return boxInfoMapper.delBoxInfo(boxInfoId);
        return 0;
    }

    @Override
    public void publishMessage(InstructionObject instructionObject) throws Exception {
        // 发送消息 json串
        try {
            String message = StringUtils.EMPTY;
            message = "a" + instructionObject.getDeviceId() + instructionObject.getAction() + ";";
//            DeviceMessage deviceMessage = new DeviceMessage();
//            deviceMessage.setDeviceId(Integer.valueOf(instructionObject.getDeviceId()));
//            deviceMessage.setAction(Integer.valueOf(instructionObject.getAction()));
            //mqttGateway.sendToMqtt(JSON.toJSONString(deviceMessage),instructionObject.getTopic());
            mqttGateway.sendToMqtt(message,instructionObject.getTopic());
            LOGGER.info(JSON.toJSONString(message));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e);
        }
    }

    @Override
    public List<BoxStatusDTO> getBoxStatus(String boxNumber) {
        List<BoxStatusDTO> lists = Lists.newArrayList();
        List<BoxStatus> boxStatuses = boxStatusMapper.selectByBoxId(boxNumber);
        for(BoxStatus boxStatus :boxStatuses) {
            BoxStatusDTO boxStatusDTO = new BoxStatusDTO();
            BeanUtils.copyProperties(boxStatus,boxStatusDTO);
            lists.add(boxStatusDTO);
        }
        return lists;
    }

    @Override
    public void setBoxStatus(BoxStatusDTO boxStatusDTO) {
        BoxStatus boxStatus = new BoxStatus();
        BeanUtils.copyProperties(boxStatusDTO,boxStatus);
        boxStatusMapper.updateByBoxNumber(boxStatus);
    }
}