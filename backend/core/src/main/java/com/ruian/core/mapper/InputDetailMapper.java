package com.ruian.core.mapper;

import com.ruian.core.entity.InputDetail;

import java.util.List;

/**
 * @author: zhudawei
 * @date: 2021/3/10
 * @description:
 */
public interface InputDetailMapper {
    int add(InputDetail entity);
    List<InputDetail> findByInputId(String inputId);
    InputDetail findById(String id);
    int delete(String id);
    int update(InputDetail enity);
}
