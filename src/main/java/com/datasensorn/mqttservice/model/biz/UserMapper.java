package com.datasensorn.mqttservice.model.biz;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    /**
     * 注册用户
     * @param user
     */
    @Insert("INSERT INTO t_user(accountName, password,userName,phoneNum,userType,companyName,province,city) " +
            "VALUES(#{accountName}, #{password},#{userName},#{phoneNum},#{userType},#{companyName},#{province},#{city})")
    public void registerUser(User user);

    /**
     * 用户登录
     * @param accountName
     * @param password
     * @return
     */
    @Select("select count(1) from t_user where accountName = #{accountName} and password=#{password}")
    public int logon(@Param("accountName") String accountName, @Param("password") String password);
}