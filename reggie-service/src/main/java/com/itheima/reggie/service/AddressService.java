package com.itheima.reggie.service;


import com.itheima.reggie.domain.Address;

import java.util.List;

public interface AddressService {
    List<Address> findList();

    void save(Address address);

    void setDefault(Long id);

    Address getDefault();

    Address findById(Long id);

    void update(Address address);

    void deleteById(List<Long> ids);
}
