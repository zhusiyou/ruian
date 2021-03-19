package com.ruian.core.entity;

import lombok.Data;

/**
 * @author: zhudawei
 * @date: 2021/3/19
 * @description:
 */
@Data
public class InputDetailChangeRecord {
    private String id;
    private String inputOrderId;
    private String inputDetailId;
    private String productId;
    private Double price;
    private Double count;
    /**
     * 仓库编号
     */
    private String storeNo;
    private String status;
    private String userId;
    private String create_time;
    private Integer inputOrderVersion;
}
