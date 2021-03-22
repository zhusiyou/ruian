package com.ruian.core.service.impl;

import cn.hutool.core.util.NumberUtil;
import com.ruian.core.entity.InputDetail;
import com.ruian.core.entity.Stock;
import com.ruian.core.mapper.StockMapper;
import com.ruian.core.model.PageResult;
import com.ruian.core.model.Pageable;
import com.ruian.core.model.StockQuery;
import com.ruian.core.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author: zhudawei
 * @date: 2021/3/20
 * @description:
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class StockServiceImpl implements StockService {

    @Autowired
    private StockMapper mapper;

    @Override
    public PageResult<Stock> find(StockQuery query, Pageable page) {
        PageResult<Stock> rst = new PageResult<>();
        rst.setData(mapper.find(query, page));
        rst.setTotal(mapper.findCount(query));
        rst.setPageIndex(page.getPageIndex());
        rst.setPageSize(page.getPageSize());

        return rst;
    }

    @Override
    public Workbook getExportExcel() {
        List<Stock> list = mapper.getExportData();

        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("库存");

        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("商品Id");
        row.createCell(1).setCellValue("商品名称");
        row.createCell(2).setCellValue("单位");
        row.createCell(3).setCellValue("数量");
        row.createCell(4).setCellValue("单价");
        row.createCell(5).setCellValue("金额");
        row.createCell(6).setCellValue("库房");

        int rowNumber = 1;
        for (Stock item: list
             ) {
            row = sheet.createRow(rowNumber);
            row.createCell(0).setCellValue(item.getProductId());
            row.createCell(1).setCellValue(item.getProductName());
            row.createCell(2).setCellValue(item.getUnit());
            row.createCell(3).setCellValue(item.getCount());
            row.createCell(4).setCellValue(item.getInputPrice());
            row.createCell(5).setCellValue(item.getAmount());
            row.createCell(6).setCellValue(item.getStoreNo());
            rowNumber++;
        }

        return wb;
    }

    @Override
    public boolean initFromExcel(MultipartFile file) {
        if (file.isEmpty()) {
            return false;
        }
        try {
            Workbook wb = WorkbookFactory.create(file.getInputStream());
            Sheet sheet = wb.getSheetAt(0);
            int rowCount = sheet.getLastRowNum();
            DataFormatter formatter = new DataFormatter();

            mapper.empty();

            for (int i = 1; i <= rowCount; i++) {
                Row row = sheet.getRow(i);

                String productId = formatter.formatCellValue(row.getCell(0));
                Double count = row.getCell(3).getNumericCellValue();
                if(count==0){
                    continue;
                }
                Cell cell = row.getCell(4);
                Double price;
                if(cell.getCellType() == CellType.FORMULA){
                    FormulaEvaluator evaluator = cell.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
                    evaluator.evaluateFormulaCell(cell);
                    CellValue cellValue = evaluator.evaluate(cell);
                    price = cellValue.getNumberValue();
                }else {
                    price = cell.getNumericCellValue();
                }
                Double amount = Double.parseDouble(formatter.formatCellValue(row.getCell(5)));
                String storeNo = formatter.formatCellValue(row.getCell(6));

                Stock stock = new Stock();
                stock.setAmount(amount);
                stock.setCount(count);
                stock.setInputPrice(price);
                stock.setProductId(productId);
                stock.setStoreNo(storeNo);

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

    @Override
    public boolean update(InputDetail before, InputDetail after) throws Exception {
        String productId = before==null?after.getProductId():before.getProductId();
        boolean result = true;
        if(before!=null){
         Stock stock = mapper.findByProductId(productId);
         stock.setCount(stock.getCount() - before.getCount());
         stock.setAmount(stock.getAmount() - before.getAmount());
         if(stock.getCount()==0 || stock.getAmount()==0){
             stock.setInputPrice(0D);
         }else{
             stock.setInputPrice(NumberUtil.div(stock.getAmount(), stock.getCount(), 2));
         }
         result = result && mapper.update(stock)>0;
        }

        if(after!=null){
            if(!exists(productId)){
                Stock stock = new Stock();
                stock.setInputPrice(after.getPrice());
                stock.setCount(after.getCount());
                stock.setAmount(after.getAmount());
                stock.setStoreNo(after.getStoreNo());
                stock.setProductId(productId);

                result  = result && mapper.add(stock)>0;
            }else{
                Stock stock = mapper.findByProductId(productId);
                stock.setCount(stock.getCount() + after.getCount());
                stock.setAmount(stock.getAmount() + after.getAmount());
                if(stock.getCount()==0 || stock.getAmount()==0){
                    stock.setInputPrice(0D);
                }else{
                    stock.setInputPrice(NumberUtil.div(stock.getAmount(), stock.getCount(), 2));
                }
                result = result && mapper.update(stock)>0;
            }

        }
        if(!result){
            throw new Exception("计算库存时出错");
        }
        return result;
    }

    private boolean exists(String productId){
        return mapper.exists(productId)>0;
    }
}
