package com.ruian.core.service;

import com.ruian.core.entity.Customer;

import java.util.List;

/**
 * @author: zhudawei
 * @date: 2021/3/10
 * @description:
 */
public interface CustomerService {
    boolean save(Customer customer);
    List<Customer> findAll();
}
