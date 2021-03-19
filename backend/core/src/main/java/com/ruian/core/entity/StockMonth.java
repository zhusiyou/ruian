package com.ruian.core.entity;

import lombok.Data;

/**
 * @author: zhudawei
 * @date: 2021/3/5
 * @description: 库存月度报表
 */
@Data
public class StockMonth {
    private String id;
    private Integer year;
    private Integer month;
    private String productId;
    private String productName;
    private Double balanceCount;
    private Double balanceAmount;
    private Double balancePrice;
    private Double inputCount;
    private Double inputAmount;
    private Double inputPrice;
    private Double beforeCount;
    private Double beforeAmount;
    private Double beforePrice;
    private Double costCount;
    private Double costAmount;
    private Double costPrice;
    private Double afterCount;
    private Double afterAmount;
    private Double afterPrice;

}
