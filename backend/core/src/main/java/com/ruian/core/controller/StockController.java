package com.ruian.core.controller;

import com.ruian.core.entity.Stock;
import com.ruian.core.model.PageResult;
import com.ruian.core.model.Pageable;
import com.ruian.core.model.StockQuery;
import com.ruian.core.service.StockService;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author: zhudawei
 * @date: 2021/3/20
 * @description:
 */
@RestController
@RequestMapping("/stock")
public class StockController {
    @Autowired
    private StockService service;

    @GetMapping("/export")
    public void export(HttpServletResponse response){
        try {
            Workbook wb = service.getExportExcel();

            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            OutputStream os = response.getOutputStream();
            response.setHeader("Content-disposition", "attachment;filename=BGM.xls");
            wb.write(os);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/upload")
    public boolean upload(@RequestParam("file") MultipartFile file){
        return service.initFromExcel(file);
    }

    @GetMapping("/page")
    public PageResult<Stock> find(@RequestParam(name = "productName", required = false) String productName,
                                  @RequestParam(name = "pageIndex", defaultValue = "1") int pageIndex,
                                  @RequestParam(name = "pageSize", defaultValue = "2") int pageSize){
        StockQuery query = new StockQuery();
        query.setProductName(productName);

        Pageable pageable = new Pageable();
        pageable.setPageIndex(pageIndex);
        pageable.setPageSize(pageSize);

        return service.find(query, pageable);
    }
}
