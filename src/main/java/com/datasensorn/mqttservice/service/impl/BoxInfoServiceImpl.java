package com.datasensorn.mqttservice.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.datasensorn.mqttservice.controller.model.DeviceMessage;
import com.datasensorn.mqttservice.controller.model.InstructionObject;
import com.datasensorn.mqttservice.dto.BoxStatusDTO;
import com.datasensorn.mqttservice.exception.ServiceException;

import com.datasensorn.mqttservice.model.biz.BoxInfo;
import com.datasensorn.mqttservice.model.biz.BoxInfoMapper;
import com.datasensorn.mqttservice.model.biz.BoxStatus;
import com.datasensorn.mqttservice.model.biz.mapper.BoxStatusMapper;

import com.datasensorn.mqttservice.mqtt.MqttGateway;
import com.datasensorn.mqttservice.service.BoxInfoService;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoxInfoServiceImpl implements BoxInfoService {

    private final static Logger LOGGER = LoggerFactory.getLogger(BoxInfoServiceImpl.class);

    @Autowired
    private BoxInfoMapper boxInfoMapper;

    @Autowired
    private BoxStatusMapper boxStatusMapper;

    @Autowired
    private MqttGateway mqttGateway;

    @Override
    public int addBoxInfo(BoxInfo boxInfo) throws ServiceException {

        if (boxInfo.getUserId() == null) {
            throw new ServiceException("添加盒子信息，用户ID不能为空");
        }
        if (StringUtils.isEmpty(boxInfo.getBoxNumber()) ||
                StringUtils.isEmpty(boxInfo.getBoxSimNumber()) ||
                StringUtils.isEmpty(boxInfo.getRegisterPhone())) {
            throw new ServiceException("添加盒子信息不完整");
        }
        return boxInfoMapper.addBoxInfo(boxInfo);
    }

    @Override
    public int delBoxInfo(Integer boxInfoId) throws ServiceException {

        if (boxInfoId == null) {
            throw new ServiceException("删除盒子信息ID为空");
        }

        return boxInfoMapper.delBoxInfo(boxInfoId);
    }

    @Override
    public void publishMessage(InstructionObject instructionObject) throws Exception {
        // 发送消息 json串
        DeviceMessage deviceMessage = new DeviceMessage();
        deviceMessage.setDeviceId(instructionObject.getDeviceId());
        deviceMessage.setAction(Integer.valueOf(instructionObject.getAction()));
        mqttGateway.sendToMqtt(JSON.toJSONString(deviceMessage),instructionObject.getTopic());
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