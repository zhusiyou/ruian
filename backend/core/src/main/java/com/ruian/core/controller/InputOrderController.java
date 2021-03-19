package com.ruian.core.controller;

import com.ruian.core.entity.InputOrder;
import com.ruian.core.entity.Product;
import com.ruian.core.model.InputOrderQuery;
import com.ruian.core.model.PageResult;
import com.ruian.core.model.Pageable;
import com.ruian.core.model.ProductQuery;
import com.ruian.core.service.InputOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: zhudawei
 * @date: 2021/3/15
 * @description:
 */
@RestController
@RequestMapping("/inputOrder")
public class InputOrderController {

    @Autowired
    private InputOrderService inputOrderService;

    @PostMapping("/save")
    public boolean save(@RequestBody InputOrder order){
        try {
            return inputOrderService.save(order);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @GetMapping("/page")
    public PageResult<InputOrder> find(@RequestParam(name = "code", required = false) String inputCode,
                                    @RequestParam(name="id", required = false)String id,
                                    @RequestParam(name = "pageIndex", defaultValue = "0") int pageIndex,
                                    @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        InputOrderQuery query = new InputOrderQuery();
        query.setInputCode(inputCode);
        query.setId(id);

        Pageable page = new Pageable();
        page.setPageIndex(pageIndex);
        page.setPageSize(pageSize);


        return inputOrderService.find(query, page);
    }
}
