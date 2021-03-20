package com.ruian.core.mapper;

import com.ruian.core.entity.Stock;
import com.ruian.core.model.Pageable;
import com.ruian.core.model.StockQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: zhudawei
 * @date: 2021/3/20
 * @description:
 */
public interface StockMapper {
    List<Stock> getExportData();
    int add(Stock stock);
    int empty();
    List<Stock> find(@Param("query") StockQuery query, @Param("page") Pageable page);
    Integer findCount(@Param("query") StockQuery query);
}
