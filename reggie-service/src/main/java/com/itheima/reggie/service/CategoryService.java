package com.itheima.reggie.service;

import com.itheima.reggie.common.ResultInfo;
import com.itheima.reggie.domain.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();

    void save(Category category);

    void update(Category category);

    ResultInfo delete(Long id);

    List<Category> findByType(Integer type);
}
