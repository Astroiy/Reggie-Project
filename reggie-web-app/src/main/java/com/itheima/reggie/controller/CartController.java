package com.itheima.reggie.controller;

import com.itheima.reggie.common.ResultInfo;
import com.itheima.reggie.domain.Cart;
import com.itheima.reggie.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/cart/add")
    public ResultInfo add(@RequestBody Cart cart){
        Cart cartFromDb = cartService.add(cart);
        return ResultInfo.success(cartFromDb);
    }

    @GetMapping("/cart/list")
    public ResultInfo findList(){
        List<Cart> cartFromDb = cartService.findList();
        return ResultInfo.success(cartFromDb);

    }

    @PostMapping("/cart/sub")
    public ResultInfo sub(@RequestBody Map<String,String> map){
        String dishId = map.get("dishId");
        String setmealId = map.get("setmealId");
        Long aLong=null;
        Long aLong1=null;
        if(dishId!=null) {
            aLong = Long.valueOf(dishId);
        }
        if(setmealId!=null) {
            aLong1 = Long.valueOf(setmealId);
        }
        Cart cart = cartService.sub(aLong,aLong1);
        return ResultInfo.success(cart);
    }

    @DeleteMapping("/cart/clean")
    public ResultInfo clean(){
        cartService.clean();
        return ResultInfo.success(null);
    }
}
