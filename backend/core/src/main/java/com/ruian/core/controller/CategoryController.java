package com.ruian.core.controller;

import com.ruian.core.entity.Category;
import com.ruian.core.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author: zhudawei
 * @date: 2021/3/5
 * @description:
 */
@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping("/all")
    public List<Category> findAll(){
        return service.findAll();
    }

    @GetMapping("/level/{current}")
    public List<Category> findByLevel(@PathVariable("current") int level){
        return service.findByLevel(level);
    }

    @PostMapping("/save")
    public boolean save(@RequestBody Category category){
        return service.save(category);
    }

}
