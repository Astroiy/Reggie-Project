package com.itheima.reggie.controller;

import com.itheima.reggie.common.ResultInfo;
import com.itheima.reggie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/user/sendMsg")
    public ResultInfo sendSms(@RequestBody Map<String,String> map){
        String phone = map.get("phone");
        userService.sendSms(phone);
        return ResultInfo.success("短信发送成功");
    }

    @PostMapping("/user/login")
    public ResultInfo login(@RequestBody Map<String,String> map){
        String phone = map.get("phone");
        String code=map.get("code");

        ResultInfo resultInfo = userService.login(phone,code);

        return resultInfo;
    }

    @PostMapping("/user/logout")
    public ResultInfo logout(@RequestHeader("Authorization")String token){
        token=token.replace("Bearer","").trim();
        userService.remove(token);
        return ResultInfo.success(null);
    }
}
