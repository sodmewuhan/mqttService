package com.datasensorn.mqttservice.dto;

import com.datasensorn.mqttservice.Utils.Constant;
import org.apache.commons.lang3.StringUtils;
import org.omg.CORBA.PUBLIC_MEMBER;

/**
 * 盒子当前状态
 */
public class BoxStatusDTO {

    private Integer id;

    private String boxnumber;

    private String deviceid;

    private String status;
    /** 状态描述 */
    private String statusDes;
    /** button 显示 */
    private String actionShow;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBoxnumber() {
        return boxnumber;
    }

    public void setBoxnumber(String boxnumber) {
        this.boxnumber = boxnumber;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDes() {
        if (Constant.ZERO.equals(status)) {
            return "关闭";
        } else if (Constant.ONE.equals(status)){
            return "打开";
        }
        return StringUtils.EMPTY;
    }

    public void setStatusDes(String statusDes) {
        if (Constant.ZERO.equals(status)) {
            statusDes = "关闭";
        } else if (Constant.ONE.equals(status)){
            statusDes ="打开";
        }
    }

    public String getActionShow() {
        if (Constant.ZERO.equals(status)) {
            return "打开";
        } else if (Constant.ONE.equals(status)){
            return "关闭";
        }
        return StringUtils.EMPTY;
    }

    public void setActionShow(String actionShow) {
        if (Constant.ZERO.equals(status)) {
            actionShow = "打开";
        } else if (Constant.ONE.equals(status)){
            actionShow ="关闭";
        }
    }
}
