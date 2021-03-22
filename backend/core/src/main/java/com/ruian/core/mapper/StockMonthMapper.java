package com.ruian.core.mapper;

import com.ruian.core.entity.StockMonth;

import java.util.List;

/**
 * @author: zhudawei
 * @date: 2021/3/22
 * @description: 月结算
 */
public interface StockMonthMapper {
    List<StockMonth> findAll(Integer year, Integer month, Integer lastYear, Integer lastMonth);
    int add(StockMonth entity);
}
