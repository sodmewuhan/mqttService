package com.datasensorn.mqttservice.service;

import com.datasensorn.mqttservice.controller.model.InstructionObject;
import com.datasensorn.mqttservice.dto.BoxStatusDTO;
import com.datasensorn.mqttservice.dto.WaterInfo;
import com.datasensorn.mqttservice.exception.ServiceException;
import com.datasensorn.mqttservice.model.biz.BoxInfo;
import com.datasensorn.mqttservice.model.biz.BoxStatus;

import java.util.List;

/**
 * 盒子服务
 */
public interface BoxInfoService {

    /**
     * 添加盒子信息
     * @param userId 用户编号
     * @param boxInfo 盒子信息
     * @return
     */
    public int addBoxInfo(BoxInfo boxInfo) throws ServiceException ;

    /**
     * 删除盒子信息
     * @param boxInfoId
     * @return
     * @throws ServiceException
     */
    public int delBoxInfo(Integer boxInfoId) throws ServiceException;

    /**
     * 向设备发送消息
     * @throws Exception
     */
    public void publishMessage(InstructionObject instructionObject) throws Exception;

    /**
     * 更新盒子状态
     * @param boxStatus
     * @throws ServiceException
     */
//    public void updateBoxStatus(BoxStatus boxStatus) throws ServiceException;

    /**
     * 根据盒子的ID编号
     * @param boxId
     * @return
     */
    public List<BoxStatusDTO> getBoxStatus(String boxId);

    /**
     * 根据盒子的ID编号
     * @param boxId
     * @return
     */
    public void setBoxStatus(BoxStatusDTO boxStatus);
}
