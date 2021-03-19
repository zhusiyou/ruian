package com.ruian.core.mapper;

import com.ruian.core.entity.InputOrder;
import com.ruian.core.model.InputOrderQuery;
import com.ruian.core.model.Pageable;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: zhudawei
 * @date: 2021/3/10
 * @description:
 */
public interface InputOrderMapper {
    int add(InputOrder entity);
    int update(InputOrder entity);

    List<InputOrder> find(@Param("query") InputOrderQuery query, @Param("page") Pageable page);
    Integer findCount(@Param("query") InputOrderQuery query);
}
