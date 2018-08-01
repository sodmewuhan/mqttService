package com.datasensorn.mqttservice.model;

public class InfluxDBSettings {

    private String influxdbURL;

    private String username;

    private String password;

    private String database;

//    private String retentionpolicy;

    private String connecttimeout;

    private String readtimeout;

    private String timeout;

    public String getInfluxdbURL() {
        return influxdbURL;
    }

    public void setInfluxdbURL(String influxdbURL) {
        this.influxdbURL = influxdbURL;
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

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

//    public String getRetentionpolicy() {
//        return retentionpolicy;
//    }
//
//    public void setRetentionpolicy(String retentionpolicy) {
//        this.retentionpolicy = retentionpolicy;
//    }

    public String getConnecttimeout() {
        return connecttimeout;
    }

    public void setConnecttimeout(String connecttimeout) {
        this.connecttimeout = connecttimeout;
    }

    public String getReadtimeout() {
        return readtimeout;
    }

    public void setReadtimeout(String readtimeout) {
        this.readtimeout = readtimeout;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public InfluxDBSettings(String influxdbURL, String username, String password, String database,  String connecttimeout, String readtimeout, String timeout) {
        this.influxdbURL = influxdbURL;
        this.username = username;
        this.password = password;
        this.database = database;
//        this.retentionpolicy = retentionpolicy;
        this.connecttimeout = connecttimeout;
        this.readtimeout = readtimeout;
        this.timeout = timeout;
    }
}
