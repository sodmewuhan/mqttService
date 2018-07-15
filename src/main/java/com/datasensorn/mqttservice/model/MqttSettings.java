package com.datasensorn.mqttservice.model;

public class MqttSettings {

    private String brokerUrl; //mqtt的url
    private String publisherName; //发布
    private String subscriberName;
    private String topic;  //主题

    //用户名和密码
    private String username;
    private String password;
    //超时 和 心跳
    private Integer timeout;
    private Integer heartbeat;
//
//    // servierURI
//    private String serviceURI;

    public MqttSettings(String brokerUrl, String publisherName, String subscriberName,String topic,
                        String username,String password,Integer timeout,Integer heartbeat) {
        this.brokerUrl = brokerUrl;
        this.publisherName = publisherName;
        this.subscriberName = subscriberName;
        this.topic = topic;

        this.username = username;
        this.password = password;

        this.timeout = timeout;
        this.heartbeat = heartbeat;
    }

    public String getBrokerUrl() {
        return brokerUrl;
    }

    public void setBrokerUrl(String brokerUrl) {
        this.brokerUrl = brokerUrl;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getSubscriberName() {
        return subscriberName;
    }

    public void setSubscriberName(String subscriberName) {
        this.subscriberName = subscriberName;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Integer getHeartbeat() {
        return heartbeat;
    }

    public void setHeartbeat(Integer heartbeat) {
        this.heartbeat = heartbeat;
    }
}
