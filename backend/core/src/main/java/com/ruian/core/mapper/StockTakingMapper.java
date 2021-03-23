package com.ruian.core.mapper;

import com.ruian.core.entity.StockTaking;
import org.apache.ibatis.annotations.Param;

/**
 * @author: zhudawei
 * @date: 2021/3/22
 * @description:
 */
public interface StockTakingMapper {
    int add(StockTaking entity);
    int delete(@Param("year") Integer year, @Param("month") Integer month);
}
