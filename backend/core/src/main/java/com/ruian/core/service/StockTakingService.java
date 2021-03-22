package com.ruian.core.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author: zhudawei
 * @date: 2021/3/22
 * @description: 库存盘点
 */
public interface StockTakingService {
    boolean upload(MultipartFile file);
}
