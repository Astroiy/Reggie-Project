package com.itheima.reggie.service;

import com.itheima.reggie.common.ResultInfo;
import com.itheima.reggie.domain.Employee;

public interface EmployeeService {
    ResultInfo login(String username,String password);

    ResultInfo find(String name);

    void save(Employee employee);

    Employee findById(Long id);

    void update(Employee employee);
}
