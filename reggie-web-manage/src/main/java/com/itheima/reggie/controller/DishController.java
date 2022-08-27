package com.itheima.reggie.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.ResultInfo;
import com.itheima.reggie.domain.Dish;
import com.itheima.reggie.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DishController {

    @Autowired
    private DishService dishService;

    @GetMapping("/dish/page")
    public ResultInfo findByPage(@RequestParam(value="page",defaultValue = "1")Integer pageNum,@RequestParam(defaultValue="10")Integer pageSize,String name){
        Page<Dish> page=dishService.findByPage(pageNum,pageSize,name);
        return ResultInfo.success(page);
    }

    @PostMapping("/dish")
    public ResultInfo save(@RequestBody Dish dish){
        dishService.save(dish);
        return ResultInfo.success(null);
    }

    @GetMapping("/dish/{id}")
    public ResultInfo findById(@PathVariable Long id){
        Dish dish=dishService.findById(id);
        return ResultInfo.success(dish);
    }

    @PutMapping("/dish")
    public ResultInfo update(@RequestBody Dish dish){
        dishService.update(dish);
        return ResultInfo.success(null);
    }

    @PostMapping("/dish/status/{status}")
    public ResultInfo changeStatus(@PathVariable("status") Integer status, @RequestParam("ids") List<Long> ids){
        dishService.changeStatus(status,ids);
        return ResultInfo.success(null);
    }

    @DeleteMapping("/dish")
    public ResultInfo delete(@PathVariable("ids") List<Long> ids){
        dishService.deleteByIds(ids);
        return ResultInfo.success(null);
    }

    @GetMapping("/dish/list")
    public ResultInfo findList(Long categoryId,String name){
        List<Dish> dishes = dishService.findList(categoryId,name);
        return ResultInfo.success(dishes);
    }


}
