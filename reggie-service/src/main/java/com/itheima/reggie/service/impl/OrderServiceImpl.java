package com.itheima.reggie.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.aliyuncs.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.UserHolder;
import com.itheima.reggie.domain.Address;
import com.itheima.reggie.domain.Cart;
import com.itheima.reggie.domain.Order;
import com.itheima.reggie.domain.OrderDetail;
import com.itheima.reggie.mapper.OrderDetailMapper;
import com.itheima.reggie.mapper.OrderMapper;
import com.itheima.reggie.service.AddressService;
import com.itheima.reggie.service.CartService;
import com.itheima.reggie.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private AddressService addressService;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Override
    public void submit(Order order) {
        //设置地址以及用户相关信息
        Address address = addressService.findById(order.getAddressId());
        order.setAddress(address.getDetail());
        order.setPhone(address.getPhone());
        order.setUserName(UserHolder.get().getName());
        order.setUserId(UserHolder.get().getId());
        order.setConsignee(address.getConsignee());
        order.setOrderTime(new Date());
        order.setCheckoutTime(new Date());

        //订单号与状态
        long id = IdWorker.getId();
        order.setId(id);
        order.setNumber(id+"");
        order.setStatus(1);

        //设置订单菜品或者套餐相关信息
        List<Cart> list = cartService.findList();
        Optional<BigDecimal> reduce = list.stream().map(e->{
            return e.getAmount().multiply(new BigDecimal(e.getNumber()));
        }).reduce(BigDecimal::add);


        reduce.ifPresent(order::setAmount);

        List<OrderDetail> collect = list.stream().map(e -> {
            OrderDetail detail = new OrderDetail();
            detail.setId(IdWorker.getId());
            detail.setName(e.getName());
            detail.setImage(e.getImage());
            detail.setOrderId(order.getId());
            detail.setDishId(e.getDishId());
            detail.setSetmealId(e.getSetmealId());
            detail.setDishFlavor(e.getDishFlavor());
            detail.setNumber(e.getNumber());
            detail.setAmount(e.getAmount().multiply(new BigDecimal(e.getNumber())));
            orderDetailMapper.insert(detail);
            return detail;
        }).collect(Collectors.toList());

        order.setOrderDetails(collect);

        orderMapper.insert(order);

        cartService.clean();
    }

    @Override
    public Page<Order> findByPage(Integer pageNum, Integer pageSize) {
        Page<Order> page = new Page<>(pageNum, pageSize);

        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Order::getUserId,UserHolder.get().getId());
        wrapper.orderByDesc(Order::getOrderTime);

        page= orderMapper.selectPage(page, wrapper);

        if(CollectionUtil.isNotEmpty(page.getRecords())){
            page.getRecords().forEach(e->{
                LambdaQueryWrapper<OrderDetail> wrapper1 = new LambdaQueryWrapper<>();
                wrapper1.eq(OrderDetail::getOrderId,e.getId());
                List<OrderDetail> orderDetails = orderDetailMapper.selectList(wrapper1);
                e.setOrderDetails(orderDetails);
            });
        }

        return page;
    }

    @Override
    public Page<Order> findByPageAndTime(Integer pageNum, Integer pageSize, String number, Date beginTime, Date endTime) {
        Page<Order> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.isNotEmpty(number),Order::getNumber,number).between(beginTime!=null,Order::getOrderTime,beginTime,endTime);

        return orderMapper.selectPage(page,wrapper);
    }
}
