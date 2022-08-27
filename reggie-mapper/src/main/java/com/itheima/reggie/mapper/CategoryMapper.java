package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.domain.Category;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMapper extends BaseMapper<Category> {
    List<Category> findAll();

    void save(Category category);

    void update(Category category);

    Integer countDishByCategoryId(Long id);

    Integer countSetmealByCategoryId(Long id);

    void delete(Long id);
}
