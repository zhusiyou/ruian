package com.ruian.core.controller;

import com.ruian.core.entity.Category;
import com.ruian.core.entity.Product;
import com.ruian.core.model.PageResult;
import com.ruian.core.model.Pageable;
import com.ruian.core.model.ProductQuery;
import com.ruian.core.service.CategoryService;
import com.ruian.core.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author: zhudawei
 * @date: 2021/3/6
 * @description:
 */
@RestController
@RequestMapping("/product")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService service;


    @GetMapping("/page")
    public PageResult<Product> find(@RequestParam(name = "name", required = false) String productName,
                                    @RequestParam(name = "pageIndex", defaultValue = "1") int pageIndex,
                                    @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        ProductQuery query = new ProductQuery();
        query.setName(productName);

        Pageable page = new Pageable();
        page.setPageIndex(pageIndex);
        page.setPageSize(pageSize);


        return service.find(query, page);
    }

    @PostMapping("/save")
    public boolean save(@RequestBody Product product) {
        return service.save(product);
    }

    @PostMapping("/upload")
    public boolean upload(@RequestParam("file") MultipartFile file) {
        try {
            return service.addFromExcel(file);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) {
        Workbook wb = null;
        try {
            wb = WorkbookFactory.create(new File("D:/test.xlsx"));

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
}
