package com.itheima.reggie.common;

import com.itheima.reggie.domain.Employee;

public class EmployeeHolder {

    private static final ThreadLocal<Employee> threadLocal=new ThreadLocal<>();

    public static void set(Employee emp){
        threadLocal.set(emp);
    }

    public static Employee get(){
        return threadLocal.get();
    }

    public static void remove(){
        threadLocal.remove();
    }
}
