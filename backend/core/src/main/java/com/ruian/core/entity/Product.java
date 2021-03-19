package com.ruian.core.entity;

import lombok.Data;

/**
 * @author: zhudawei
 * @date: 2021/3/5
 * @description: 商品
 */
@Data
public class Product {
    private String id;
    private String categoryId;
    private String productName;
    private String unit;
    private String barCode;
    private String productCode;
    private Double defaultPrice;
    private String remark;
    private String createTime;

    private String categoryName;
    private String firstCategoryId;
    private String firstCategoryName;
}
