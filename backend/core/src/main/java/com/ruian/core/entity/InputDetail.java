package com.ruian.core.entity;

import lombok.Data;

/**
 * @author: zhudawei
 * @date: 2021/3/5
 * @description: 入库单商品明细
 */
@Data
public class InputDetail {
    private String id;
    private String inputId;
    private String productId;
    private Double price;
    private Double count;
    private String storeNo;
    private Double amount;

    private String productName;
    /**
     * 为了在修改时，从前端返回时标识记录所做的操作，用来处理库存数据
     * status: 空、added、updated、deleted
     */
    private String status;
}
