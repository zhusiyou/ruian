package com.ruian.core.service;

import com.ruian.core.entity.Category;

import java.util.List;

/**
 * @author: zhudawei
 * @date: 2021/3/5
 * @description:
 */
public interface CategoryService {
    List<Category> findAll();
    List<Category> findByLevel(int level);
    Category findByName(String name);
    boolean save(Category category);
}
