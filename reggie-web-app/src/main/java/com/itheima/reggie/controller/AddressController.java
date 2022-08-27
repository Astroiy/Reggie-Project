package com.itheima.reggie.controller;

import com.itheima.reggie.common.ResultInfo;
import com.itheima.reggie.domain.Address;
import com.itheima.reggie.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/address/list")
    public ResultInfo findList(){
        List<Address> addresses=addressService.findList();
        return ResultInfo.success(addresses);
    }

    @PostMapping("/address")
    public ResultInfo save(@RequestBody Address address){
        addressService.save(address);
        return ResultInfo.success(null);
    }

    @PutMapping("/address/default")
    public ResultInfo setDefault(@RequestBody Map<String,String> map){
        String id = map.get("id");
        Long value = Long.valueOf(id);
        addressService.setDefault(value);
        return ResultInfo.success(null);
    }

    @GetMapping("/address/default")
    public ResultInfo getDefault(){
        Address address=addressService.getDefault();
        if(address==null){
            return ResultInfo.error("当前用户未设置默认地址");
        }else{
            return ResultInfo.success(address);
        }
    }

    @GetMapping("/address/{id}")
    public ResultInfo findById(@PathVariable Long id){
        Address address=addressService.findById(id);
        return ResultInfo.success(address);
    }

    @PutMapping("/address")
    public ResultInfo update(@RequestBody Address address){
        addressService.update(address);
        return ResultInfo.success(null);
    }

    @DeleteMapping("address")
    public ResultInfo deleteById(@RequestParam List<Long> ids){
        addressService.deleteById(ids);
        return ResultInfo.success(null);
    }

}
