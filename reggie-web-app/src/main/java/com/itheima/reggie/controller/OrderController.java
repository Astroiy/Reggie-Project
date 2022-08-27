package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.ResultInfo;
import com.itheima.reggie.domain.Order;
import com.itheima.reggie.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/order/submit")
    public ResultInfo submit(@RequestBody Order order){
        orderService.submit(order);
        return ResultInfo.success(null);
    }

    @GetMapping("/order/userPage")
    public ResultInfo findByPage(
            @RequestParam(value = "page",defaultValue = "1") Integer pageNum,@RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize){
        Page<Order> page=orderService.findByPage(pageNum,pageSize);
        return ResultInfo.success(page);
    }
}
