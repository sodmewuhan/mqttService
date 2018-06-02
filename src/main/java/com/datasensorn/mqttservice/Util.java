package com.datasensorn.mqttservice;

import com.alibaba.fastjson.JSON;
import com.datasensorn.mqttservice.model.biz.BoxInfo;
import com.datasensorn.mqttservice.model.biz.User;

@Deprecated
public class Util {

    public static void main(String[] args) {
        User user = new User();

//        user.setAccountName("sodmewuhan");
//        user.setAccountName("cailong");
//        user.setCity("wuhan");
//        user.setCompanyName("coship");
//        user.setPassword("uxxxx");
//        user.setPhoneNum("12344");

        BoxInfo boxInfo = new BoxInfo();
        boxInfo.setUserId(1);
        boxInfo.setBoxNumber("122");
        boxInfo.setBoxSimNumber("123");
        boxInfo.setRegisterPhone("122");
        String str = JSON.toJSONString(boxInfo);

        System.out.println(str);
    }
}
