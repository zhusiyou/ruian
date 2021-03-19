package com.ruian.core.controller;

import com.ruian.core.entity.Customer;
import com.ruian.core.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: zhudawei
 * @date: 2021/3/10
 * @description:
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService service;

    @GetMapping("/all")
    public List<Customer> findAll(){
        return service.findAll();
    }

    @PostMapping("/save")
    public boolean save(@RequestBody Customer customer){
        return service.save(customer);
    }
}
