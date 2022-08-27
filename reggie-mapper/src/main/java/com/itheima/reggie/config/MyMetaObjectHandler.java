package com.itheima.reggie.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.itheima.reggie.common.EmployeeHolder;
import com.itheima.reggie.common.UserHolder;
import com.itheima.reggie.domain.Employee;
import com.itheima.reggie.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {

   /* @Autowired
    private HttpSession session;*/
    @Override
    public void insertFill(MetaObject metaObject) {
        metaObject.setValue("createTime",new Date());
        metaObject.setValue("updateTime",new Date());
//        metaObject.setValue("createUser",1L);
//        metaObject.setValue("updateUser",1L);
       /* Employee emp = (Employee) session.getAttribute("SESSION_EMPLOYEE");*/
        Employee emp = EmployeeHolder.get();
        if(emp!=null){
            metaObject.setValue("createUser", EmployeeHolder.get().getId());
            metaObject.setValue("updateUser",EmployeeHolder.get().getId());
        }

        User user = UserHolder.get();
        if(user!=null){
            metaObject.setValue("createUser",user.getId());
            metaObject.setValue("updateUser",user.getId());
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        metaObject.setValue("updateTime",new Date());
        /*metaObject.setValue("updateUser",1L);
        Employee emp = (Employee) session.getAttribute("SESSION_EMPLOYEE");*/
        Employee emp = EmployeeHolder.get();
        if(emp!=null){
            metaObject.setValue("updateUser",EmployeeHolder.get().getId());
        }
        User user = UserHolder.get();
        if(user!=null){
            metaObject.setValue("updateUser",user.getId());
        }
    }
}
