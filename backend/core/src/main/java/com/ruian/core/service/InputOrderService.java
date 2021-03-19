package com.ruian.core.service;

import com.ruian.core.entity.InputOrder;
import com.ruian.core.model.InputOrderQuery;
import com.ruian.core.model.PageResult;
import com.ruian.core.model.Pageable;

/**
 * @author: zhudawei
 * @date: 2021/3/10
 * @description:
 */
public interface InputOrderService {
    PageResult<InputOrder> page(Pageable page);
    boolean save(InputOrder entity) throws Exception;
    PageResult<InputOrder> find(InputOrderQuery query, Pageable page);
}
