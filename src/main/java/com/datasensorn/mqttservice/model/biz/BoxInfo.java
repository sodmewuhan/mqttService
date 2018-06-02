package com.datasensorn.mqttservice.model.biz;

/**
 * 盒子信息
 */
public class BoxInfo {

    private int id;

    private String boxNumber;//盒子编号

    private String registerPhone; //注册用电话号码

    private String boxSimNumber; //盒子用SIM卡信息

    private Integer userId; //用户ID编号

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBoxNumber() {
        return boxNumber;
    }

    public void setBoxNumber(String boxNumber) {
        this.boxNumber = boxNumber;
    }

    public String getRegisterPhone() {
        return registerPhone;
    }

    public void setRegisterPhone(String registerPhone) {
        this.registerPhone = registerPhone;
    }

    public String getBoxSimNumber() {
        return boxSimNumber;
    }

    public void setBoxSimNumber(String boxSimNumber) {
        this.boxSimNumber = boxSimNumber;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
