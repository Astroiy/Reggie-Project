package com.itheima.reggie.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.reggie.common.CustomException;
import com.itheima.reggie.common.JwtUtil;
import com.itheima.reggie.common.ResultInfo;
import com.itheima.reggie.common.SmsTemplate;
import com.itheima.reggie.domain.User;
import com.itheima.reggie.mapper.UserMapper;
import com.itheima.reggie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Repository
public class UserServiceImpl implements UserService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SmsTemplate smsTemplate;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void sendSms(String phone) {
        String code = RandomUtil.randomNumbers(6);
        System.out.println("生成的验证码是:"+code);

        redisTemplate.opsForValue().set("CODE_"+phone,code,5, TimeUnit.MINUTES);

        smsTemplate.sendSms(phone,code);
    }

    @Override
    public ResultInfo login(String phone, String code) {
        String codeFormRedis = (String) redisTemplate.opsForValue().get("CODE_" + phone);
        if(!StringUtils.equals(code,codeFormRedis)){
            return ResultInfo.error("验证码错误");
        }

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>();
        wrapper.eq(User::getPhone,phone);
        User user = userMapper.selectOne(wrapper);
        if(user!=null){
            if(user.getStatus()!=1){
                return ResultInfo.error("当前用户已被冻结");
            }
        }else{
            user = new User();
            user.setPhone(phone);
            user.setStatus(1);
            userMapper.insert(user);
        }

        HashMap map = new HashMap();
        map.put("id",user.getId());
        String token = JwtUtil.createToken(map);

        redisTemplate.opsForValue().set("TOKEN_"+token,user,1,TimeUnit.DAYS);

        return ResultInfo.success(token);

    }

    @Override
    public void remove(String token) {
        redisTemplate.delete("TOKEN_"+token);
    }
}
