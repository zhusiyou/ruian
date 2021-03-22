package com.ruian.core.service.impl;

import cn.hutool.core.util.StrUtil;
import com.ruian.core.entity.Product;
import com.ruian.core.entity.StockTaking;
import com.ruian.core.mapper.ProductMapper;
import com.ruian.core.mapper.StockTakingMapper;
import com.ruian.core.service.StockTakingService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: zhudawei
 * @date: 2021/3/22
 * @description:
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class StockTakingServiceImpl implements StockTakingService {

    @Autowired
    private StockTakingMapper mapper;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public boolean upload(MultipartFile file) {
        if (file.isEmpty()) {
            return false;
        }
        try {
            Workbook wb = WorkbookFactory.create(file.getInputStream());
            Sheet sheet = wb.getSheetAt(0);
            int rowCount = sheet.getLastRowNum();
            DataFormatter formatter = new DataFormatter();

            String[] yearAndMonth = sheet.getSheetName().split("-");
            Integer year = Integer.parseInt(yearAndMonth[0]);
            Integer month = Integer.parseInt(yearAndMonth[1]);

            mapper.delete(year, month);

            for (int i = 1; i <= rowCount; i++) {
                Row row = sheet.getRow(i);

                String productId = formatter.formatCellValue(row.getCell(0));
                Double count = row.getCell(3).getNumericCellValue();
                String productName = row.getCell(1).getStringCellValue();

                if(StrUtil.isEmpty(productId)){
                    Product product = productMapper.findByName(productName);
                    if(product==null){
                        throw new Exception("未找到对应商品: " + productName);
                    }
                    productId = product.getId();
                }

                StockTaking stock = new StockTaking();
                stock.setCount(count);
                stock.setProductId(productId);
                stock.setProductName(productName);
                stock.setYear(year);
                stock.setMonth(month);

                mapper.add(stock);
            }
            return true;
        } catch (Exception e) {
            log.error(e.toString(), e);
            //代码显示处理回滚 ！关键
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return false;
    }

}
