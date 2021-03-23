package com.ruian.core.controller;

import com.ruian.core.service.StockTakingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: zhudawei
 * @date: 2021/3/22
 * @description: 库存盘点
 */
@RestController
@RequestMapping("/taking")
public class StockTakingContoller {
    @Autowired
    private StockTakingService service;

    @PostMapping("/upload")
    public boolean upload(@RequestParam("file")MultipartFile file){
        return service.upload(file);
    }
}
