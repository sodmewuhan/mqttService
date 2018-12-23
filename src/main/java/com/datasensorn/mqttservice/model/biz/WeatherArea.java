package com.datasensorn.mqttservice.model.biz;

public class WeatherArea {
    private String areaid;

    private String nameen;

    private String namecn;

    private String districten;

    private String districtcn;

    private String proven;

    private String provcn;

    private String nationen;

    private String nationcn;

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid == null ? null : areaid.trim();
    }

    public String getNameen() {
        return nameen;
    }

    public void setNameen(String nameen) {
        this.nameen = nameen == null ? null : nameen.trim();
    }

    public String getNamecn() {
        return namecn;
    }

    public void setNamecn(String namecn) {
        this.namecn = namecn == null ? null : namecn.trim();
    }

    public String getDistricten() {
        return districten;
    }

    public void setDistricten(String districten) {
        this.districten = districten == null ? null : districten.trim();
    }

    public String getDistrictcn() {
        return districtcn;
    }

    public void setDistrictcn(String districtcn) {
        this.districtcn = districtcn == null ? null : districtcn.trim();
    }

    public String getProven() {
        return proven;
    }

    public void setProven(String proven) {
        this.proven = proven == null ? null : proven.trim();
    }

    public String getProvcn() {
        return provcn;
    }

    public void setProvcn(String provcn) {
        this.provcn = provcn == null ? null : provcn.trim();
    }

    public String getNationen() {
        return nationen;
    }

    public void setNationen(String nationen) {
        this.nationen = nationen == null ? null : nationen.trim();
    }

    public String getNationcn() {
        return nationcn;
    }

    public void setNationcn(String nationcn) {
        this.nationcn = nationcn == null ? null : nationcn.trim();
    }
}