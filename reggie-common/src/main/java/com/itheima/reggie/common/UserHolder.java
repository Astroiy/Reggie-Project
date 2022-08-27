package com.itheima.reggie.common;

import com.itheima.reggie.domain.User;

public class UserHolder {

    private static final ThreadLocal<User> threadLocal=new ThreadLocal<>();


    public static void set(User user){
        threadLocal.set(user);
    }
    public static User get(){
        return threadLocal.get();
    }
    public static void remove(){
        threadLocal.remove();
    }
}
