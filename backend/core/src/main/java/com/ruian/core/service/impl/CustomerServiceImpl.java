package com.ruian.core.service.impl;

import cn.hutool.core.util.StrUtil;
import com.ruian.core.entity.Customer;
import com.ruian.core.mapper.CustomerMapper;
import com.ruian.core.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: zhudawei
 * @date: 2021/3/10
 * @description:
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper mapper;

    @Override
    public boolean save(Customer customer) {
        if(StrUtil.isEmpty(customer.getId())){
            return mapper.add(customer)>0;
        }
        return mapper.update(customer)>0;
    }

    @Override
    public List<Customer> findAll() {
        return mapper.findAll();
    }
}
