package com.ruian.core.mapper;

import com.ruian.core.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author: zhudawei
 * @date: 2021/3/5
 * @description:
 */
@Mapper
public interface CategoryMapper {
    List<Category> findAll();
    int add(Category category);
    int update(Category category);
    Category findByName(String categoryName);
}
