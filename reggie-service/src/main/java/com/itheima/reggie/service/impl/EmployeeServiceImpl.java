package com.itheima.reggie.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.druid.util.StringUtils;
import com.itheima.reggie.common.ResultInfo;
import com.itheima.reggie.domain.Employee;
import com.itheima.reggie.mapper.EmployeeMapper;
import com.itheima.reggie.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.transform.Result;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.alibaba.druid.sql.ast.SQLPartitionValue.Operator.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public ResultInfo login(String username, String password) {
        Employee employee=employeeMapper.findByUsername(username);
        ResultInfo resultInfo = new ResultInfo();
        if(employee==null){
            return ResultInfo.error("用户名不存在");
        }
        String passwordWithMd5= SecureUtil.md5(password);
        if(!StringUtils.equals(passwordWithMd5,employee.getPassword())){
            return ResultInfo.error("密码错误");
        }
        if(Objects.equals(employee.getStatus(), Employee.STATUS_DISABLE)){
            return ResultInfo.error("当前用户处于禁用状态，不允许登录");
        }
        return ResultInfo.success(employee);
    }

    @Override
    public ResultInfo find(String name) {
        ResultInfo resultInfo = new ResultInfo();
        List<Employee> empList= employeeMapper.find(name);
        return ResultInfo.success(empList);
    }

    @Override
    public void save(Employee employee) {
        employee.setId(IdUtil.getSnowflake(1,1).nextId());
        employee.setPassword(SecureUtil.md5("123"));

        employee.setStatus(Employee.STATUS_ENABLE);

//        employee.setCreateTime(new Date());
//        employee.setUpdateTime(new Date());

        //todo 待优化
//        employee.setCreateUser(1L);
//        employee.setUpdateUser(1L);

        employeeMapper.save(employee);
    }

    @Override
    public Employee findById(Long id) {
        Employee employee=employeeMapper.findById(id);
        return employee;
    }

    @Override
    public void update(Employee employee) {
//        employee.setUpdateTime(new Date());
//        employee.setUpdateUser(2L);
        employeeMapper.update(employee);
    }
}
