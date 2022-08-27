package com.itheima.reggie.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.domain.Dish;

import java.util.List;

public interface DishService {
    Page<Dish> findByPage(Integer pageNum, Integer pageSize, String name);

    void save(Dish dish);

    Dish findById(Long id);

    void update(Dish dish);

    void deleteByIds(List<Long> ids);

    void changeStatus(Integer status, List<Long> ids);

    List<Dish> findList(Long categoryId, String name);
}
