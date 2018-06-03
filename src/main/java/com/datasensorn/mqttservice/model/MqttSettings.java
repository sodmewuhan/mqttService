package com.datasensorn.mqttservice.model;

public class MqttSettings {

    private String brokerUrl; //mqtt的url
    private String publisherName; //发布
    private String subscriberName;
    private String topic;  //主题

    public MqttSettings(String brokerUrl, String publisherName, String subscriberName,String topic) {
        this.brokerUrl = brokerUrl;
        this.publisherName = publisherName;
        this.subscriberName = subscriberName;
        this.topic = topic;
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
}
