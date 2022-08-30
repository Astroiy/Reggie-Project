package com.itheima.reggie.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.ResultInfo;
import com.itheima.reggie.domain.Dish;
import com.itheima.reggie.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/dish/page")
    public ResultInfo findByPage(@RequestParam(value="page",defaultValue = "1")Integer pageNum,@RequestParam(defaultValue="10")Integer pageSize,String name){
        Page<Dish> page=dishService.findByPage(pageNum,pageSize,name);
        return ResultInfo.success(page);
    }

    //新增
    @PostMapping("/dish")
    @CacheEvict(value = "dish",allEntries = true)
    public ResultInfo save(@RequestBody Dish dish){
//        redisTemplate.delete(redisTemplate.keys("dish_"));
        dishService.save(dish);
        return ResultInfo.success(null);
    }

    @GetMapping("/dish/{id}")
    public ResultInfo findById(@PathVariable Long id){
        Dish dish=dishService.findById(id);
        return ResultInfo.success(dish);
    }

    //修改
    @PutMapping("/dish")
    @CacheEvict(value = "dish",allEntries = true)
    public ResultInfo update(@RequestBody Dish dish){
//        redisTemplate.delete(redisTemplate.keys("dish_"));
        dishService.update(dish);
        return ResultInfo.success(null);
    }

    //批量起售/停售
    @PostMapping("/dish/status/{status}")
    @CacheEvict(value = "dish",allEntries = true)
    public ResultInfo changeStatus(@PathVariable("status") Integer status, @RequestParam("ids") List<Long> ids){
//        redisTemplate.delete(redisTemplate.keys("dish_"));
        dishService.changeStatus(status,ids);
        return ResultInfo.success(null);
    }

    //批量删除
    @DeleteMapping("/dish")
    @CacheEvict(value = "dish",allEntries = true)
    public ResultInfo delete(@PathVariable("ids") List<Long> ids){
//        redisTemplate.delete(redisTemplate.keys("dish_"));
        dishService.deleteByIds(ids);
        return ResultInfo.success(null);
    }

    @GetMapping("/dish/list")
    public ResultInfo findList(Long categoryId,String name){
        List<Dish> dishes = dishService.findList(categoryId,name);
        return ResultInfo.success(dishes);
    }


}
