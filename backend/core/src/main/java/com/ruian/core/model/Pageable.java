package com.ruian.core.model;

import lombok.Data;

/**
 * @author: zhudawei
 * @date: 2021/3/6
 * @description:
 */
@Data
public class Pageable {
    private int pageIndex;

    public int getStartIndex() {
        if(pageIndex<1){
            pageIndex = 1;
        }
        if(pageSize<0){
            pageSize = 10;
        }
        return (pageIndex-1)*pageSize;
    }

    private int startIndex;
    private int pageSize;
}
