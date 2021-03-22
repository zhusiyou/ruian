package com.ruian.core.service;

/**
 * @author: zhudawei
 * @date: 2021/3/22
 * @description: 报表相关
 */
public interface ReportService {
    void generateStockMonth(Integer year, Integer month);

    /**
     * 校正库存
     */
    void resetStock();
}
