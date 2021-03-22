package com.ruian.core.service;

import com.ruian.core.entity.InputDetail;
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

    /**
     * 处理修改入库单时引起的库存变更
     * @param before 修改前的商品入库信息，新增时为null
     * @param after 修改后的商品入库信息，删除时为null
     * @return
     */
    boolean update(InputDetail before, InputDetail after) throws Exception;
}
