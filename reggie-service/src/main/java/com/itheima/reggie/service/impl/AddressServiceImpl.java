package com.itheima.reggie.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.reggie.common.ResultInfo;
import com.itheima.reggie.common.UserHolder;
import com.itheima.reggie.domain.Address;
import com.itheima.reggie.mapper.AddressMapper;
import com.itheima.reggie.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public List<Address> findList() {
        LambdaQueryWrapper<Address> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Address::getUserId, UserHolder.get().getId());
        return addressMapper.selectList(wrapper);

    }

    @Override
    public void save(Address address) {
        address.setUserId(UserHolder.get().getId());
        LambdaQueryWrapper<Address> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Address::getUserId,UserHolder.get().getId());
        Long count = addressMapper.selectCount(wrapper);
        if(count.equals(0L)){
            address.setIsDefault(1);
        }else{
            address.setIsDeleted(0);
        }
        addressMapper.insert(address);
    }

    @Override
    public void setDefault(Long id) {
        LambdaQueryWrapper<Address> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Address::getUserId,UserHolder.get().getId());
        Address address = new Address();
        address.setIsDefault(0);
        addressMapper.update(address,wrapper);

        Address address1 = new Address();
        address1.setIsDefault(1);
        address1.setId(id);
        addressMapper.updateById(address1);
    }

    @Override
    public Address getDefault() {
        LambdaQueryWrapper<Address> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Address::getUserId,UserHolder.get().getId()).eq(Address::getIsDefault,1);
        return addressMapper.selectOne(wrapper);
    }

    @Override
    public Address findById(Long id) {
        return addressMapper.selectById(id);
    }

    @Override
    public void update(Address address) {
        addressMapper.updateById(address);
    }

    @Override
    public void deleteById(List<Long> ids) {
        if(CollectionUtil.isNotEmpty(ids)){
            int count = addressMapper.deleteBatchIds(ids);
            System.out.println("删除了"+count+"个地址");
        }
    }
}
