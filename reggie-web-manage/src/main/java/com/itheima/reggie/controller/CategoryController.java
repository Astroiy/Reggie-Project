package com.itheima.reggie.controller;

import com.itheima.reggie.common.ResultInfo;
import com.itheima.reggie.domain.Category;
import com.itheima.reggie.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category/findAll")
    public ResultInfo findAll(){
        List<Category> categoryList = categoryService.findAll();
        return ResultInfo.success(categoryList);
    }

    @PostMapping("/category")
    public ResultInfo save(@RequestBody Category category){
        categoryService.save(category);
        return ResultInfo.success(null);
    }

    @PutMapping("/category")
    public ResultInfo update(@RequestBody Category category){
        categoryService.update(category);
        return ResultInfo.success(null);
    }

    @DeleteMapping("/category")
    public ResultInfo delete(Long id){
        ResultInfo resultInfo=categoryService.delete(id);
        return resultInfo;
    }

    @GetMapping("/category/list")
    public ResultInfo findByType(Integer type){
        List<Category> categoryList = categoryService.findByType(type);
        return ResultInfo.success(categoryList);
    }
}
