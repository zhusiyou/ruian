package com.ruian.core.service;

import com.ruian.core.entity.Stock;
import com.ruian.core.model.PageResult;
import com.ruian.core.model.Pageable;
import com.ruian.core.model.StockQuery;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: zhudawei
 * @date: 2021/3/20
 * @description:
 */
public interface StockService {
    PageResult<Stock> find(StockQuery query, Pageable page);
    Workbook getExportExcel();
    boolean initFromExcel(MultipartFile file);
}
