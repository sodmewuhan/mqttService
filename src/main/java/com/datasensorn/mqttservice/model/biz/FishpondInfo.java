package com.datasensorn.mqttservice.model.biz;

/**
 * 鱼塘信息
 */
public class FishpondInfo {

    private Integer id; //编号

    private String poundName; //鱼塘名字

    private String poundAddress; //鱼塘地址

    private Float poundArea; //鱼塘面积

    private Float poundDeepth; //鱼塘深度

    private String breedType ; //养殖水产的品种

    private String breedName; //养殖的产品名字

    private Integer counts; //养殖的总数量

    private Integer boxInfoId; //盒子编号

    private Integer userId; //用户编号

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPoundName() {
        return poundName;
    }

    public void setPoundName(String poundName) {
        this.poundName = poundName;
    }

    public Float getPoundArea() {
        return poundArea;
    }

    public void setPoundArea(Float poundArea) {
        this.poundArea = poundArea;
    }

    public Float getPoundDeepth() {
        return poundDeepth;
    }

    public void setPoundDeepth(Float poundDeepth) {
        this.poundDeepth = poundDeepth;
    }

    public String getBreedType() {
        return breedType;
    }

    public void setBreedType(String breedType) {
        this.breedType = breedType;
    }

    public String getBreedName() {
        return breedName;
    }

    public void setBreedName(String breedName) {
        this.breedName = breedName;
    }

    public Integer getCounts() {
        return counts;
    }

    public void setCounts(Integer counts) {
        this.counts = counts;
    }

    public String getPoundAddress() {
        return poundAddress;
    }

    public void setPoundAddress(String poundAddress) {
        this.poundAddress = poundAddress;
    }

    public Integer getBoxInfoId() {
        return boxInfoId;
    }

    public void setBoxInfoId(Integer boxInfoId) {
        this.boxInfoId = boxInfoId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
