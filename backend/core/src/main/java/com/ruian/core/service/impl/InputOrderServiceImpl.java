package com.ruian.core.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.ruian.core.entity.InputDetail;
import com.ruian.core.entity.InputDetailChangeRecord;
import com.ruian.core.entity.InputOrder;
import com.ruian.core.mapper.InputDetailChangeRecordMapper;
import com.ruian.core.mapper.InputDetailMapper;
import com.ruian.core.mapper.InputOrderMapper;
import com.ruian.core.model.InputOrderQuery;
import com.ruian.core.model.PageResult;
import com.ruian.core.model.Pageable;
import com.ruian.core.service.InputOrderService;
import com.ruian.core.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * @author: zhudawei
 * @date: 2021/3/10
 * @description:
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class InputOrderServiceImpl implements InputOrderService {

    @Autowired
    private InputOrderMapper orderMapper;

    @Autowired
    private InputDetailMapper detailMapper;

    @Autowired
    private InputDetailChangeRecordMapper recordMapper;

    @Autowired
    private StockService stockService;

    @Override
    public PageResult<InputOrder> page(Pageable page) {
        return null;
    }

    @Override
    public boolean save(InputOrder entity) throws Exception {
        if(StrUtil.isEmpty(entity.getId())){

            return add(entity);
        }
        return update(entity);
    }

    @Override
    public PageResult<InputOrder> find(InputOrderQuery query, Pageable page) {
        PageResult<InputOrder> rst = new PageResult<>();

        List<InputOrder> orders = orderMapper.find(query, page);
        orders.stream().forEach(item->{
            item.setDetails(detailMapper.findByInputId(item.getId()));
        });

        rst.setData(orders);
        rst.setTotal(orderMapper.findCount(query));
        rst.setPageIndex(page.getPageIndex());
        rst.setPageSize(page.getPageSize());

        return rst;
    }

    private boolean add(InputOrder entity) throws Exception {
        String id = UUID.randomUUID().toString();
        entity.setCreateTime(DateUtil.now());
        entity.setId(id);
//            entity.setInsertUserId();

        Integer rst = orderMapper.add(entity);
        if(rst != 1){
            throw new Exception("入库单保存时出现错误");
        }
        for (InputDetail item : entity.getDetails()) {
            item.setInputId(id);
            detailMapper.add(item);
            stockService.update(null, item);
        }
        return true;
    }

    private boolean update(InputOrder entity) throws Exception {

        String ADDED = "added";
        String UPDATED = "updated";
        String DELETED = "deleted";

        Integer rst = orderMapper.update(entity);
        if(rst != 1){
            throw new Exception("数据已过期");
        }
        for (InputDetail item : entity.getDetails()) {
            if (item.getStatus().equals(ADDED)) {
                item.setInputId(entity.getId());
                detailMapper.add(item);
                stockService.update(null, item);
            } else if (item.getStatus().equals(UPDATED) || item.getStatus().equals(DELETED)) {
                InputDetailChangeRecord record = new InputDetailChangeRecord();
                InputDetail detail = detailMapper.findById(item.getId());

                record.setCount(detail.getCount());
                record.setInputDetailId(detail.getId());
                record.setInputOrderId(entity.getId());
                record.setPrice(detail.getPrice());
                record.setStoreNo(detail.getStoreNo());

                record.setStatus(item.getStatus());
                record.setProductId(detail.getProductId());
//            record.setUserId();
                record.setInputOrderVersion(entity.getVersion());

                recordMapper.add(record);

                if (item.getStatus().equals(UPDATED)) {
                    detailMapper.update(item);
                    stockService.update(detail, item);
                } else {
                    detailMapper.delete(item.getId());
                    stockService.update(item, null);
                }
            }

            //TODO: 操作应付
        }
        return true;
    }
}
