package com.datasensorn.mqttservice.model.biz;

import lombok.Data;

import java.util.Date;

@Data
public class DeviceSetAutoInfo {
    private Integer id;

    private Integer boxId;

    private Date begintime;

    private Integer runtime;

    private Double topvaule;

    private Double lowvalue;

//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public Integer getBoxId() {
//        return boxId;
//    }
//
//    public void setBoxId(Integer boxId) {
//        this.boxId = boxId;
//    }
//
//    public Date getBegintime() {
//        return begintime;
//    }
//
//    public void setBegintime(Date begintime) {
//        this.begintime = begintime;
//    }
//
//    public Integer getRuntime() {
//        return runtime;
//    }
//
//    public void setRuntime(Integer runtime) {
//        this.runtime = runtime;
//    }
//
//    public Double getTopvaule() {
//        return topvaule;
//    }
//
//    public void setTopvaule(Double topvaule) {
//        this.topvaule = topvaule;
//    }
//
//    public Double getLowvalue() {
//        return lowvalue;
//    }
//
//    public void setLowvalue(Double lowvalue) {
//        this.lowvalue = lowvalue;
//    }
}