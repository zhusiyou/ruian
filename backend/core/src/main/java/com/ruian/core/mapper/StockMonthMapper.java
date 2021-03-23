package com.ruian.core.mapper;

import com.ruian.core.entity.StockMonth;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: zhudawei
 * @date: 2021/3/22
 * @description: 月结算
 */
public interface StockMonthMapper {
    List<StockMonth> findAll(@Param("year") Integer year,
                             @Param("month") Integer month,
                             @Param("lastYear") Integer lastYear,
                             @Param("lastMonth") Integer lastMonth);
    int add(StockMonth entity);

    /**
     * 结算数与当前库存数不致的商品
     * @param year
     * @param month
     * @return
     */
    List<StockMonth> findErrorData(@Param("year") Integer year,
                                   @Param("month") Integer month);

}
