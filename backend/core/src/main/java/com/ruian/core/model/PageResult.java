package com.ruian.core.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: zhudawei
 * @date: 2021/3/10
 * @description:
 */
@Data
public class PageResult<T> {
    private int pageIndex;
    private int pageSize;
    private int total;
    private List<T> data = new ArrayList<>();
}
