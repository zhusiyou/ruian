package com.ruian.core.mapper;

import com.ruian.core.entity.Product;
import com.ruian.core.model.Pageable;
import com.ruian.core.model.ProductQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: zhudawei
 * @date: 2021/3/6
 * @description:
 */
public interface ProductMapper {
    List<Product> find(@Param("query") ProductQuery query, @Param("page")Pageable page);
    Integer findCount(@Param("query") ProductQuery query);
    int add(Product product);
    int update(Product product);
}
