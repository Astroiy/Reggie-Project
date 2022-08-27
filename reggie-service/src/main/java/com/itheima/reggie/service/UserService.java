package com.itheima.reggie.service;


import com.itheima.reggie.common.ResultInfo;

public interface UserService {

    void sendSms(String phone);

    ResultInfo login(String phone, String code);

    void remove(String token);
}
