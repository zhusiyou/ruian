package com.ruian.core.entity;

import lombok.Data;

/**
 * @author: zhudawei
 * @date: 2021/3/5
 * @description: 库存盘点
 */
@Data
public class StockTaking {
    private String id;
    private Integer year;
    private Integer month;
    private String productId;
    private String productName;
    private Double count;
}
