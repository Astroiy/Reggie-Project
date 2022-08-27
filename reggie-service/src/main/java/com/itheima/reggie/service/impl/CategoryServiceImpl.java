package com.itheima.reggie.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.reggie.common.CustomException;
import com.itheima.reggie.common.ResultInfo;
import com.itheima.reggie.domain.Category;
import com.itheima.reggie.mapper.CategoryMapper;
import com.itheima.reggie.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.xml.transform.Result;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public List<Category> findAll() {
        List<Category> categoryList = categoryMapper.findAll();
        categoryList.sort(Comparator.comparing(Category::getSort));
        return categoryList;
    }

    @Override
    public void save(Category category) {
        category.setId(IdUtil.getSnowflake(1,1).nextId());
//        category.setCreateTime(new Date());
//        category.setUpdateTime(new Date());
//        category.setCreateUser(1L);
//        category.setUpdateUser(1L);

        categoryMapper.save(category);
    }

    @Override
    public void update(Category category) {
//        category.setUpdateTime(new Date());
//        category.setUpdateUser(2L);
        categoryMapper.update(category);
    }

    @Override
    public ResultInfo delete(Long id) {
        Integer dishes=categoryMapper.countDishByCategoryId(id);
        Integer setmeals=categoryMapper.countSetmealByCategoryId(id);
        if(dishes>0){
            throw new CustomException("此分类下存在菜品，不允许删除");
        }
        if(setmeals>0){
            throw new CustomException("当前分类下有套餐，不能删除");
        }

        categoryMapper.delete(id);
        return ResultInfo.success(null);
    }

    @Override
    public List<Category> findByType(Integer type) {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getType,type);

        return categoryMapper.selectList(wrapper);
    }
}
