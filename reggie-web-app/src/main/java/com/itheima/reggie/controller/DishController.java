package com.itheima.reggie.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.itheima.reggie.common.ResultInfo;
import com.itheima.reggie.domain.Dish;
import com.itheima.reggie.service.CategoryService;
import com.itheima.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/dish/list")
    @Cacheable(value = "dish",key = "'dish_'+#categoryId")
    public ResultInfo findList(Long categoryId,Integer status){
        /*ValueOperations ops = redisTemplate.opsForValue();
        String key = "dish_" + categoryId;
        List<Dish> dishes = (List<Dish>) ops.get(key);
        if(CollectionUtil.isNotEmpty(dishes)){
            log.info("从Redis中获取的数据");
            return ResultInfo.success(dishes);
        }else {
            log.info("从MYSQL中获取的数据");
            dishes = dishService.findList(categoryId, null);
            ops.set(key,dishes);
            return ResultInfo.success(dishes);
        }*/
        List<Dish> dishes = dishService.findList(categoryId, null);
        return ResultInfo.success(dishes);


    }
}
