package com.ruian.core.service;

import com.ruian.core.entity.Product;
import com.ruian.core.model.PageResult;
import com.ruian.core.model.Pageable;
import com.ruian.core.model.ProductQuery;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: zhudawei
 * @date: 2021/3/6
 * @description:
 */
public interface ProductService {
    PageResult<Product> find(ProductQuery query, Pageable page);
    boolean save(Product product);
    boolean addFromExcel(MultipartFile file);
}
