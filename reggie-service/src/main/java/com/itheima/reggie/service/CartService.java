package com.itheima.reggie.service;

import com.itheima.reggie.domain.Cart;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CartService {
    Cart add(Cart cart);

    List<Cart> findList();

    Cart sub(Long aLong,Long aLong1);

    void clean();
}
