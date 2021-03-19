package com.ruian.core.entity;

import lombok.Data;

/**
 * @author: zhudawei
 * @date: 2021/3/5
 * @description: 客户/供应商
 */
@Data
public class Customer {
    private String id;
    private String companyName;
    private String address;
    private String linkman;
    private String telephone;
    private String remark;
}
