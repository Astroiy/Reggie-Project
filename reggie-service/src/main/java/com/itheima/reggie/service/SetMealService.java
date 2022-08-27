package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.domain.Dish;
import com.itheima.reggie.domain.Setmeal;

import java.util.List;

public interface SetMealService {

    Page<Setmeal> findByPage(Integer page, Integer pageSize, String name);

    void save(Setmeal setmeal);

    void delete(List<Long> ids);

    Setmeal findById(Long id);

    void update(Setmeal setmeal);

    void changeStatus(Integer status, List<Long> ids);

    List<Setmeal> findList(Long categoryId, Integer status);

    List<Dish> findDishList(Long id);
}
