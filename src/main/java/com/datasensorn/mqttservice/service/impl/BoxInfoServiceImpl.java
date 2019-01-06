package com.datasensorn.mqttservice.service.impl;

import com.alibaba.fastjson.JSON;
import com.datasensorn.mqttservice.controller.model.BoxAndWaterStatusDTO;
import com.datasensorn.mqttservice.controller.model.BoxInfoDTO;
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
import com.google.common.collect.Maps;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

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
    public int addBoxInfo(BoxInfoDTO boxInfo) throws ServiceException {

        Assert.notNull(boxInfo,"传递对象不能为空");
        Assert.notNull(boxInfo.getBoxnumber(),"盒子编号不能为空");
        Assert.notNull(boxInfo.getUsername(),"用户编号不能为空");

        LOGGER.info("the addBoxInfo parameter is " + JSON.toJSONString(boxInfo,true));
        UserInfo userInfo = userService.findUserByUserName(boxInfo.getUsername());
        if (userInfo == null) {
            throw  new ServiceException("系统查询不到该用户");
        }
        BoxInfo boxInfoModel = new BoxInfo();
        try {
            BeanUtils.copyProperties(boxInfoModel,boxInfo);
        }catch (Exception e) {
            throw new ServiceException("数据转换发生失败",e);
        }
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
    public List<BoxAndWaterStatusDTO> getBoxStatusByUser(UserInfoDTO userInfoDTO) throws Exception{
        Assert.notNull(userInfoDTO,"传递参数对象不能为空");
        Assert.notNull(userInfoDTO.getUsername(),"传递参数用户名称不能为空");
        UserInfo userInfo = userService.findUserByUserName(userInfoDTO.getUsername());
        if (userInfo == null) {
            throw  new ServiceException("系统查询不到该用户");
        }
        List<BoxInfo> boxInfos = boxInfoMapper.findBoxInfosByUserId(userInfo.getId());
        if (CollectionUtils.isNotEmpty(boxInfos)) {
            // 得到所有设备控制单元的当前状态
            List<String> boxNumbers = Lists.newArrayList();
            for(BoxInfo boxInfo : boxInfos) {
                boxNumbers.add(boxInfo.getBoxnumber());
            }
            List<BoxStatus> boxStatuses = boxStatusMapper.getBoxStatusByBox(boxNumbers);
            Map<String,List<BoxStatusDTO>> boxStatusMap = Maps.newHashMap();
            for(BoxStatus boxStatus : boxStatuses) {
                BoxStatusDTO boxStatusDTO = new BoxStatusDTO();
                BeanUtils.copyProperties(boxStatusDTO,boxStatus);

                String boxNumber = boxStatus.getBoxnumber();
                List<BoxStatusDTO> boxStatusDTOS = boxStatusMap.get(boxNumber);
                if (CollectionUtils.isEmpty(boxStatusDTOS)) {
                    List<BoxStatusDTO> list = Lists.newArrayList();
                    list.add(boxStatusDTO);
                    boxStatusMap.put(boxNumber,list);
                } else {
                    boxStatusDTOS.add(boxStatusDTO);
                }
            }
            // 得到设备的上报最后数据

            // 组装结果
            List<BoxAndWaterStatusDTO> boxAndWaterStatusDTOS = Lists.newArrayList();
            for (BoxInfo boxInfo : boxInfos) {
                BoxAndWaterStatusDTO boxAndWaterStatusDTO = new BoxAndWaterStatusDTO();
                // 设置盒子信息
                BoxInfoDTO boxInfoDTO = new BoxInfoDTO();
                BeanUtils.copyProperties(boxInfoDTO,boxInfo);
                boxAndWaterStatusDTO.setBoxInfoDTO(boxInfoDTO);
                // 设置盒子设备状态信息
                List<BoxStatusDTO> boxStatusDTOS = boxStatusMap.get(boxInfo.getBoxnumber());
                boxAndWaterStatusDTO.setBoxStatusDTOS(boxStatusDTOS);
                boxAndWaterStatusDTOS.add(boxAndWaterStatusDTO);
            }
            return boxAndWaterStatusDTOS;
        }
        return null;
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
    public List<BoxStatusDTO> getBoxStatus(String boxNumber) throws Exception{
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
    public void setBoxStatus(BoxStatusDTO boxStatusDTO) throws Exception{
        BoxStatus boxStatus = new BoxStatus();
        BeanUtils.copyProperties(boxStatusDTO,boxStatus);
        boxStatusMapper.updateByBoxNumber(boxStatus);
    }
}