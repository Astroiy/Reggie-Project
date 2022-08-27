package com.itheima.reggie.controller;


import com.itheima.reggie.common.ResultInfo;
import com.itheima.reggie.domain.Category;
import com.itheima.reggie.domain.Employee;
import com.itheima.reggie.service.CategoryService;
import com.itheima.reggie.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/employee/login")
    public ResultInfo login(HttpSession session,@RequestBody Map<String,String> map){
        String username=map.get("username");
        String password=map.get("password");
        ResultInfo resultInfo=employeeService.login(username,password);

        if(resultInfo.getCode().equals(1)){
            session.setAttribute("SESSION_EMPLOYEE",(Employee)resultInfo.getData());
        }
        return resultInfo;
    }

    @PostMapping("/employee/logout")
    public ResultInfo logout(HttpSession session){
        session.invalidate();
        return ResultInfo.success(null);
    }

    @GetMapping("/employee/find")
    public ResultInfo find(String name){
        ResultInfo resultInfo=employeeService.find(name);
        return resultInfo;
    }

    @PostMapping("/employee")
    public ResultInfo save(@RequestBody Employee employee){
        employeeService.save(employee);
        return ResultInfo.success(null);
    }

    @GetMapping("/employee/{id}")
    public ResultInfo findById(@PathVariable("id") Long id){
        Employee employee=employeeService.findById(id);
        return ResultInfo.success(employee);
    }

    //保存
    //PUT通过请求体提交的参数，后台要使用@RequestBody接收
    @PutMapping("/employee")
    public ResultInfo update(@RequestBody Employee employee){
        employeeService.update(employee);
        return ResultInfo.success(null);
    }
}
