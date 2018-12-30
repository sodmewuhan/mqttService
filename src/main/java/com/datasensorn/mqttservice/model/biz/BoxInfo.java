package com.datasensorn.mqttservice.model.biz;

public class BoxInfo {
    private Integer id;

    private String boxnumber;

    private String registerphone;

    private String boxsimnumber;

    private Integer userid;

    private String boxname;

    private String boxtype;

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
        this.boxnumber = boxnumber == null ? null : boxnumber.trim();
    }

    public String getRegisterphone() {
        return registerphone;
    }

    public void setRegisterphone(String registerphone) {
        this.registerphone = registerphone == null ? null : registerphone.trim();
    }

    public String getBoxsimnumber() {
        return boxsimnumber;
    }

    public void setBoxsimnumber(String boxsimnumber) {
        this.boxsimnumber = boxsimnumber == null ? null : boxsimnumber.trim();
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getBoxname() {
        return boxname;
    }

    public void setBoxname(String boxname) {
        this.boxname = boxname == null ? null : boxname.trim();
    }

    public String getBoxtype() {
        return boxtype;
    }

    public void setBoxtype(String boxtype) {
        this.boxtype = boxtype == null ? null : boxtype.trim();
    }
}