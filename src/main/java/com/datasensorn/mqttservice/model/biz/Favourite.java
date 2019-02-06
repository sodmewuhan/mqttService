package com.datasensorn.mqttservice.model.biz;

import java.util.Date;

public class Favourite {
    private Integer id;

    private String username;

    private Integer questionId;

    private Date createtime;

    private Boolean isfavourite;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Boolean getIsfavourite() {
        return isfavourite;
    }

    public void setIsfavourite(Boolean isfavourite) {
        this.isfavourite = isfavourite;
    }
}