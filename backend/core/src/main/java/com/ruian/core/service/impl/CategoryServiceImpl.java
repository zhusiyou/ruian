package com.ruian.core.service.impl;

import cn.hutool.core.util.StrUtil;
import com.ruian.core.entity.Category;
import com.ruian.core.mapper.CategoryMapper;
import com.ruian.core.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: zhudawei
 * @date: 2021/3/5
 * @description:
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper mapper;

    @Override
    public List<Category> findAll() {
        return mapper.findAll();
    }

    @Override
    public List<Category> findByLevel(int level) {
        return mapper.findAll().stream().filter(item->item.getLevel()==level).collect(Collectors.toList());
    }

    @Override
    public Category findByName(String name) {
        return mapper.findByName(name);
    }

    @Override
    public boolean save(Category category) {
        return StrUtil.isEmpty(category.getId())?mapper.add(category)>0:mapper.update(category)>0;
    }
}
