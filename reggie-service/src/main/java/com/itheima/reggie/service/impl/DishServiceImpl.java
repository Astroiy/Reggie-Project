package com.itheima.reggie.service.impl;


import cn.hutool.core.collection.CollectionUtil;
import com.aliyuncs.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.CustomException;
import com.itheima.reggie.domain.Category;
import com.itheima.reggie.domain.Dish;
import com.itheima.reggie.domain.DishFlavor;
import com.itheima.reggie.mapper.CategoryMapper;
import com.itheima.reggie.mapper.DishFlavorMapper;
import com.itheima.reggie.mapper.DishMapper;
import com.itheima.reggie.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Override
    public Page<Dish> findByPage(Integer pageNum, Integer pageSize, String name) {
        Page<Dish> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(name),Dish::getName,name);
        page=dishMapper.selectPage(page,wrapper);

        List<Dish> dishList = page.getRecords();
        if(CollectionUtil.isNotEmpty(dishList)){
            for(Dish dish: dishList){
                Category category = categoryMapper.selectById(dish.getCategoryId());
                dish.setCategoryName(category.getName());
                LambdaQueryWrapper<DishFlavor> wrapper1 = new LambdaQueryWrapper<>();
                wrapper1.eq(DishFlavor::getDishId,dish.getId());
                List<DishFlavor> dishFlavors = dishFlavorMapper.selectList(wrapper1);
                dish.setFlavors(dishFlavors);
            }
        }
        System.out.println(page);

        return page;
    }

    @Override
    public void save(Dish dish) {
        dishMapper.insert(dish);

        if(CollectionUtil.isNotEmpty(dish.getFlavors())){
            for (DishFlavor flavor : dish.getFlavors()) {
                flavor.setDishId(dish.getId());
                dishFlavorMapper.insert(flavor);
            }
        }
    }

    @Override
    public Dish findById(Long id) {
        Dish dish = dishMapper.selectById(id);

        Category category = categoryMapper.selectById(dish.getCategoryId());
        dish.setCategoryName(category.getName());

        LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DishFlavor::getDishId,dish.getId());
        List<DishFlavor> dishFlavors = dishFlavorMapper.selectList(wrapper);
        dish.setFlavors(dishFlavors);
        return dish;

    }

    @Override
    public void update(Dish dish) {
        dishMapper.updateById(dish);

        LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DishFlavor::getDishId,dish.getId());
        dishFlavorMapper.delete(wrapper);

        if(CollectionUtil.isNotEmpty(dish.getFlavors())){
            for (DishFlavor flavor : dish.getFlavors()) {
                flavor.setDishId(dish.getId());
                dishFlavorMapper.insert(flavor);
            }
        }
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Dish::getStatus,1);
        wrapper.in(Dish::getId,ids);
        Long count = dishMapper.selectCount(wrapper);
        if(count>0){
            throw new CustomException("当前菜品有处于在售状态的，不嗯能够删除");
        }
        if(CollectionUtil.isNotEmpty(ids)){
            for (Long id : ids) {
                LambdaQueryWrapper<DishFlavor> wrapper1 = new LambdaQueryWrapper<>();
                wrapper1.eq(DishFlavor::getDishId,id);
                dishFlavorMapper.delete(wrapper1);
                dishMapper.deleteById(id);
            }
        }
    }

    @Override
    public void changeStatus(Integer status, List<Long> ids) {
            if(CollectionUtil.isNotEmpty(ids)){
                for (Long id : ids) {
                    Dish dish = dishMapper.selectById(id);
                    dish.setStatus(status);
                    dishMapper.updateById(dish);
                }
            }
    }

    @Override
    public List<Dish> findList(Long categoryId, String name) {
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(categoryId!=null,Dish::getCategoryId,categoryId).eq(Dish::getStatus,1).like(StringUtils.isNotEmpty(name),Dish::getName,name);
        List<Dish> dishes = dishMapper.selectList(wrapper);
        if(CollectionUtil.isNotEmpty(dishes)){
            for (Dish dish : dishes) {
                dish.setCategoryName(categoryMapper.selectById(dish.getCategoryId()).getName());
                dish.setFlavors(dishFlavorMapper.selectList(new LambdaQueryWrapper<DishFlavor>().eq(DishFlavor::getDishId,dish.getId())));
            }
        }
        return dishes;
    }
}
