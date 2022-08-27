package com.itheima.reggie.controller;

import com.itheima.reggie.common.ResultInfo;
import com.itheima.reggie.domain.Dish;
import com.itheima.reggie.domain.Setmeal;
import com.itheima.reggie.service.SetMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SetmealController {
    @Autowired
    private SetMealService setMealService;

    @GetMapping("/setmeal/list")
    public ResultInfo findList(Long categoryId,Integer status){
        List<Setmeal> setmealList = setMealService.findList(categoryId,status);
        return ResultInfo.success(setmealList);
    }

    @GetMapping("/setmeal/dish/{id}")
    public ResultInfo findDishList(@PathVariable Long id){
        List<Dish> dishList = setMealService.findDishList(id);
        return ResultInfo.success(dishList);
    }
}
