package com.ruian.core.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: zhudawei
 * @date: 2021/3/5
 * @description: 入库单
 */
@Data
public class InputOrder {
    private String id;
    private String inputCode;
    private String storeManager;
    private String insertUserId;
    private String inputDate;
    private String supplierId;
    private String companyName;
    private String createTime;
    private Double amount;
    private String remark;
    private Integer version;

    /**
     * 入库商品
     */
    private List<InputDetail> details = new ArrayList<>();
}
