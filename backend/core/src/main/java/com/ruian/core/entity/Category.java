package com.ruian.core.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: zhudawei
 * @date: 2021/3/5
 * @description:
 */
@Data
public class Category implements Serializable {
    private String id;
    private String categoryName;
    private Integer level;
    private String parentId;
    private String parentName;
}
