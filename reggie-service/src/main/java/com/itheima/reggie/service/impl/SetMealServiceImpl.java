package com.itheima.reggie.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.aliyuncs.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.CustomException;
import com.itheima.reggie.domain.*;
import com.itheima.reggie.mapper.*;
import com.itheima.reggie.service.SetMealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SetMealServiceImpl implements SetMealService {
    @Autowired
    private SetMealMapper setMealMapper;

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private SetMealDishMapper setMealDishMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Override
    public Page<Setmeal> findByPage(Integer page, Integer pageSize, String name) {
        Page<Setmeal> setmealPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotEmpty(name)) {
            wrapper.like(Setmeal::getName,name);
        }
        Page<Setmeal> selectPage = setMealMapper.selectPage(setmealPage, wrapper);
        if(CollectionUtil.isNotEmpty(selectPage.getRecords()))
            for (Setmeal record : selectPage.getRecords()) {
                LambdaQueryWrapper<SetmealDish> wrapper1 = new LambdaQueryWrapper<>();
                wrapper1.eq(SetmealDish::getSetmealId,record.getId());
                List<SetmealDish> dishes = setMealDishMapper.selectList(wrapper1);
                record.setSetmealDishes(dishes);

                Category category = categoryMapper.selectById(record.getCategoryId());
                record.setCategoryName(category.getName());
            }
        return setmealPage;
    }

    @Override
    public void save(Setmeal setmeal) {
        setMealMapper.insert(setmeal);

        List<SetmealDish> setmealDishes = setmeal.getSetmealDishes();
        if(CollectionUtil.isNotEmpty(setmealDishes)){
            int i=0;
            for (SetmealDish setmealDish : setmealDishes) {
                setmealDish.setSetmealId(setmeal.getId());
                setmealDish.setSort(i++);
                setMealDishMapper.insert(setmealDish);
            }
        }
    }

    @Override
    public void delete(List<Long> ids) {
        List<Setmeal> setmeals = setMealMapper.selectBatchIds(ids);
        if(setmeals.stream().anyMatch(e->e.getStatus().equals(1))){
            throw new CustomException("套餐处于售卖状态，禁止删除");
        }
        if(CollectionUtil.isNotEmpty(ids)){
            LambdaQueryWrapper<SetmealDish> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(SetmealDish::getSetmealId,ids);
            setMealDishMapper.delete(wrapper);
            setMealMapper.deleteBatchIds(ids);
        }
    }

    @Override
    public Setmeal findById(Long id) {
        Setmeal setmeal = setMealMapper.selectById(id);
        setmeal.setSetmealDishes(setMealDishMapper.selectList(new LambdaQueryWrapper<SetmealDish>().eq(SetmealDish::getSetmealId,id)));
        setmeal.setCategoryName(categoryMapper.selectById(setmeal.getCategoryId()).getName());
        return setmeal;
    }

    @Override
    public void update(Setmeal setmeal) {
        setMealMapper.updateById(setmeal);
        setMealDishMapper.delete(new LambdaQueryWrapper<SetmealDish>().eq(SetmealDish::getSetmealId,setmeal.getId()));
        if(CollectionUtil.isNotEmpty(setmeal.getSetmealDishes())){
            int i=0;
            for (SetmealDish setmealDish : setmeal.getSetmealDishes()) {
                setmealDish.setSetmealId(setmeal.getId());
                setmealDish.setSort(i++);
                setMealDishMapper.insert(setmealDish);
            }
        }
    }

    @Override
    public void changeStatus(Integer status, List<Long> ids) {
        if(CollectionUtil.isNotEmpty(ids)){
            long count = ids.stream().peek(i -> {
                Setmeal setmeal = setMealMapper.selectById(i);
                setmeal.setStatus(status);
                setMealMapper.updateById(setmeal);
            }).count();
            log.info("改变了"+count+"个套餐的状态");
        }
    }

    @Override
    public List<Setmeal> findList(Long categoryId, Integer status) {
        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(categoryId!=null,Setmeal::getCategoryId,categoryId).eq(Setmeal::getStatus,1);
        List<Setmeal> setmeals = setMealMapper.selectList(wrapper);
        if(CollectionUtil.isNotEmpty(setmeals)){
            setmeals.forEach(e -> {
                e.setCategoryName(categoryMapper.selectById(e.getCategoryId()).getName());
                e.setSetmealDishes(setMealDishMapper.selectList(new LambdaQueryWrapper<SetmealDish>().eq(SetmealDish::getSetmealId, e.getId())));
            });
        }
        return setmeals;
    }

    @Override
    public List<Dish> findDishList(Long id) {
        LambdaQueryWrapper<SetmealDish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SetmealDish::getSetmealId,id);
        List<SetmealDish> setmealDishes = setMealDishMapper.selectList(wrapper);
        ArrayList<Dish> dishes = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(setmealDishes)){
            setmealDishes.forEach(e->{
                Dish dish = dishMapper.selectById(e.getDishId());
                dish.setFlavors(dishFlavorMapper.selectList(new LambdaQueryWrapper<DishFlavor>().eq(DishFlavor::getDishId,dish.getId())));
                dish.setCategoryName(categoryMapper.selectById(dish.getCategoryId()).getName());
                dish.setCopies(e.getCopies());
                dishes.add(dish);
            });
        }
        return dishes;
    }
}
