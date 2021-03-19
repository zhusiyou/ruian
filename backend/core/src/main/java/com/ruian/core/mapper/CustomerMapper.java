package com.ruian.core.mapper;

import com.ruian.core.entity.Customer;

import java.util.List;

/**
 * @author: zhudawei
 * @date: 2021/3/10
 * @description:
 */
public interface CustomerMapper {
    List<Customer> findAll();
    int add(Customer customer);
    int update(Customer customer);
}
