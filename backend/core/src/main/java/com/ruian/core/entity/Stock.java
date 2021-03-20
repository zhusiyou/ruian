package com.ruian.core.entity;

import lombok.Data;

/**
 * @author: zhudawei
 * @date: 2021/3/5
 * @description: 库存
 */
@Data
public class Stock {
    private String id;
    private String productId;
    private String storeNo;
    private Double count;
    private Double inputPrice;
    private Double amount;

    private String productName;
    private String unit;
}
