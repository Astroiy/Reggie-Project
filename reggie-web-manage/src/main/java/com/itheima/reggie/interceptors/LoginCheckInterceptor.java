package com.itheima.reggie.interceptors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.reggie.common.EmployeeHolder;
import com.itheima.reggie.common.ResultInfo;
import com.itheima.reggie.domain.Employee;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Employee emp = (Employee) session.getAttribute("SESSION_EMPLOYEE");
        if (emp != null) {
            EmployeeHolder.set(emp);
            return true;
        }
        //System.out.println("用户未登录");
        ResultInfo notlogin = ResultInfo.error("NOTLOGIN");
        String s = new ObjectMapper().writeValueAsString(notlogin);
        response.getWriter().write(s);
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        EmployeeHolder.remove();
    }
}
