package com.ruian.core.service.impl;

import cn.hutool.core.util.StrUtil;
import com.ruian.core.entity.Category;
import com.ruian.core.entity.Product;
import com.ruian.core.mapper.ProductMapper;
import com.ruian.core.model.PageResult;
import com.ruian.core.model.Pageable;
import com.ruian.core.model.ProductQuery;
import com.ruian.core.service.CategoryService;
import com.ruian.core.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: zhudawei
 * @date: 2021/3/6
 * @description:
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper mapper;
    @Autowired
    private CategoryService categoryService;

    @Override
    public PageResult<Product> find(ProductQuery query, Pageable page) {

        PageResult<Product> rst = new PageResult<>();
        rst.setData(mapper.find(query, page));
        rst.setTotal(mapper.findCount(query));
        rst.setPageIndex(page.getPageIndex());
        rst.setPageSize(page.getPageSize());

        return rst;
    }

    @Override
    public boolean save(Product product) {
        if (StrUtil.isEmpty(product.getId())) {
            return mapper.add(product) > 0;
        } else {
            return mapper.update(product) > 0;
        }
    }

    @Override
    public boolean addFromExcel(MultipartFile file) {
        if (file.isEmpty()) {
            return false;
        }
        try {
            Workbook wb = WorkbookFactory.create(file.getInputStream());
            Sheet sheet = wb.getSheetAt(0);
            int rowCount = sheet.getLastRowNum();
            DataFormatter formatter = new DataFormatter();
            for (int i = 1; i <= rowCount; i++) {
                Row row = sheet.getRow(i);

                Cell cell = row.getCell(0);
                Category ctg = categoryService.findByName(formatter.formatCellValue(cell));
                String productName = formatter.formatCellValue(row.getCell(1));
                String unit = formatter.formatCellValue(row.getCell(2));
                Double price = row.getCell(3).getNumericCellValue();
                String remark = formatter.formatCellValue(row.getCell(4));

                Product pro = new Product();
                //ctg==null时会Exception - rollback
                pro.setCategoryId(ctg.getId());
                pro.setProductName(productName);
                pro.setUnit(unit);
                pro.setDefaultPrice(price);
                pro.setRemark(remark);

                mapper.add(pro);
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
