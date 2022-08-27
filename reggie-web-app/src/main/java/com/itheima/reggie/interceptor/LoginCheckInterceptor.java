package com.itheima.reggie.interceptor;

import com.aliyuncs.utils.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.reggie.common.JwtUtil;
import com.itheima.reggie.common.ResultInfo;
import com.itheima.reggie.common.UserHolder;
import com.itheima.reggie.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        token = token.replace("Bearer", "").trim();
        log.info("token="+token);
        if(StringUtils.isEmpty(token)){
            ResultInfo resultInfo = ResultInfo.error("NOTLOGIN");
            String json = new ObjectMapper().writeValueAsString(resultInfo);
            response.getWriter().write(json);

            return false;
        }

        try{
            JwtUtil.parseToken(token);
        }catch (Exception e){
            ResultInfo resultInfo = ResultInfo.error(e.getMessage());
            String value = new ObjectMapper().writeValueAsString(resultInfo);
            response.getWriter().write(value);

            return false;
        }

        User user = (User) redisTemplate.opsForValue().get("TOKEN_" + token);
        if(user==null){
            ResultInfo resultInfo = ResultInfo.error("NOTLOGIN");
            String json = new ObjectMapper().writeValueAsString(resultInfo);
            response.getWriter().write(json);
            return false;
        }

        redisTemplate.opsForValue().set("TOKEN_"+token,user,1, TimeUnit.DAYS);
        UserHolder.set(user);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHolder.remove();
    }
}
