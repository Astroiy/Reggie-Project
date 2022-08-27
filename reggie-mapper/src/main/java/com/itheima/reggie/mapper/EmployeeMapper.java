package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.domain.Employee;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeMapper extends BaseMapper<Employee> {
    Employee findByUsername(String username);

    List<Employee> find(String name);

    void save(Employee employee);

    Employee findById(Long id);

    void update(Employee employee);
}
