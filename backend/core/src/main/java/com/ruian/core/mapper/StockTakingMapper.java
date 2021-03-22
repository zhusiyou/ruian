package com.ruian.core.mapper;

import com.ruian.core.entity.StockTaking;

/**
 * @author: zhudawei
 * @date: 2021/3/22
 * @description:
 */
public interface StockTakingMapper {
    int add(StockTaking entity);
    int delete(Integer year, Integer month);
}
