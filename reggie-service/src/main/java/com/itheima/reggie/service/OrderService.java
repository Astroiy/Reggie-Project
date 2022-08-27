package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.domain.Order;

import java.util.Date;

public interface OrderService {

    void submit(Order order);

    Page<Order> findByPage(Integer pageNum, Integer pageSize);

    Page<Order> findByPageAndTime(Integer pageNum, Integer pageSize, String number, Date beginTime, Date endTime);
}
