package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.ResultInfo;
import com.itheima.reggie.domain.Setmeal;
import com.itheima.reggie.service.SetMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SetMealController {

    @Autowired
    private SetMealService setMealService;

    @GetMapping("/setmeal/page")
    public ResultInfo findByPage(@RequestParam(value="page",defaultValue = "1") Integer page, @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize, String name){
        Page<Setmeal> pageShowed= setMealService.findByPage(page,pageSize,name);
        return ResultInfo.success(pageShowed);
    }

    @PostMapping("/setmeal")
    public ResultInfo save(@RequestBody Setmeal setmeal){
        setMealService.save(setmeal);
        return ResultInfo.success(null);
    }

    @DeleteMapping("/setmeal")
    public ResultInfo delete(@RequestParam List<Long> ids){
        setMealService.delete(ids);
        return ResultInfo.success(null);
    }

    @GetMapping("/setmeal/{id}")
    public ResultInfo findById(@PathVariable Long id){
        Setmeal setmeal = setMealService.findById(id);
        return ResultInfo.success(setmeal);
    }

    @PutMapping("/setmeal")
    public ResultInfo update(@RequestBody Setmeal setmeal){
        setMealService.update(setmeal);
        return ResultInfo.success(null);
    }

    @PostMapping("/setmeal/status/{status}")
    public ResultInfo changeStatus(@PathVariable("status")Integer status,@RequestParam("ids")List<Long> ids){
        setMealService.changeStatus(status,ids);
        return ResultInfo.success(null);
    }
}
