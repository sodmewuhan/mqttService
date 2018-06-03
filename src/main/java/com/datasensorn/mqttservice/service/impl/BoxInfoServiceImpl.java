package com.datasensorn.mqttservice.service.impl;

import com.datasensorn.mqttservice.exception.ServiceException;
import com.datasensorn.mqttservice.model.biz.BoxInfo;
import com.datasensorn.mqttservice.model.biz.BoxInfoMapper;
import com.datasensorn.mqttservice.service.BoxInfoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoxInfoServiceImpl implements BoxInfoService {

    private final static Logger LOGGER = LoggerFactory.getLogger(BoxInfoServiceImpl.class);

    @Autowired
    private BoxInfoMapper boxInfoMapper;

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
}