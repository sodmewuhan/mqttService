package com.datasensorn.mqttservice.service;

import com.datasensorn.mqttservice.exception.ServiceException;
import com.datasensorn.mqttservice.model.biz.BoxInfo;

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
}
