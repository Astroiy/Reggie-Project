package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.reggie.common.UserHolder;
import com.itheima.reggie.domain.Cart;
import com.itheima.reggie.mapper.CartMapper;
import com.itheima.reggie.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    @Autowired
    private CartMapper cartMapper;

    @Override
    public Cart add(Cart cart) {
        LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Cart::getName,cart.getName()).eq(cart.getDishId()!=null,Cart::getDishId,cart.getDishId()).eq(cart.getSetmealId()!=null,Cart::getSetmealId,cart.getSetmealId()).eq(Cart::getUserId, UserHolder.get().getId());
        Cart cart1 = cartMapper.selectOne(wrapper);
        if(cart1==null){
            cart.setUserId(UserHolder.get().getId());
            cart.setNumber(1);
            cart.setCreateTime(new Date());
            cartMapper.insert(cart);
            return cart;
        }else{
            cart1.setNumber(cart1.getNumber()+1);
            cartMapper.updateById(cart1);
            return cart1;
        }
    }

    @Override
    public List<Cart> findList() {
        LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Cart::getUserId,UserHolder.get().getId());
        List<Cart> carts = cartMapper.selectList(wrapper);
        return carts;
    }

    @Override
    public Cart sub(Long aLong,Long aLong1) {
        LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Cart::getUserId,UserHolder.get().getId()).eq(aLong!=null,Cart::getDishId,aLong).eq(aLong1!=null,Cart::getSetmealId,aLong1);
        Cart cart = cartMapper.selectOne(wrapper);
        if(cart!=null &&cart.getNumber()>0){
            if(cart.getNumber().equals(1)){
                cartMapper.deleteById(cart);
                Cart cart1 = new Cart();
                cart1.setNumber(0);
                return cart1;
            }else{
                cart.setNumber(cart.getNumber()-1);
                cartMapper.updateById(cart);
                return cart;
            }
        }
        return new Cart();
    }

    @Override
    public void clean() {
        LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Cart::getUserId,UserHolder.get().getId());
        cartMapper.delete(wrapper);
    }


}
