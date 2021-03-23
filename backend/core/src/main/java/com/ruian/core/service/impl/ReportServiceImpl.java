package com.ruian.core.service.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.ruian.core.entity.StockMonth;
import com.ruian.core.mapper.StockMapper;
import com.ruian.core.mapper.StockMonthMapper;
import com.ruian.core.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: zhudawei
 * @date: 2021/3/22
 * @description:
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ReportServiceImpl implements ReportService {

    @Autowired
    private StockMonthMapper stockMonthMapper;

    @Autowired
    private StockMapper stockMapper;

    @Override
    public void generateStockMonth(Integer year, Integer month) {

        //TODO: 要设置结算标志

        DateTime dt = DateUtil.parse(StrUtil.format("{}-{}-01", year, month));
        DateTime last = DateUtil.offset(dt, DateField.MONTH, -1);
        Integer lastYear = DateUtil.year(last);
        Integer lastMonth = DateUtil.month(last) + 1;

        List<StockMonth> data = stockMonthMapper.findAll(year, month, lastYear, lastMonth);
        for(StockMonth item: data){
            //上月结存+本月购入
            item.setBeforeCount(item.getBalanceCount() + item.getInputCount());
            item.setBeforeAmount(item.getBalanceAmount() + item.getInputAmount());
            if(item.getBeforeCount()==0 || item.getBeforeAmount()==0){
                item.setBeforePrice(0D);
            }else{
                item.setBeforePrice(NumberUtil.div(item.getBeforeAmount(),
                        item.getBeforeCount(), 2));
            }

            //本月消耗
            item.setCostCount(item.getBeforeCount() - item.getAfterCount());
            item.setCostPrice(item.getBeforePrice());
            Double amount = NumberUtil.round(
                    NumberUtil.mul(item.getCostPrice() * item.getCostCount()),
                    2).doubleValue();
            item.setCostAmount(amount);

            //本月结存
            item.setAfterAmount(item.getBeforeAmount() - item.getCostAmount());
            if(item.getAfterCount()==0 || item.getAfterAmount() ==0){
                item.setAfterPrice(0D);
            }else{
                item.setAfterPrice(NumberUtil.div(item.getAfterAmount(),
                        item.getAfterCount(), 2));
            }

            item.setYear(year);
            item.setMonth(month);

            stockMonthMapper.add(item);
        }
    }

    @Override
    public String resetStock(Integer year, Integer month) {
        List<StockMonth> errData = stockMonthMapper.findErrorData(year, month);
        if(errData.size()>0){
            return String.join(",", errData.stream().limit(10).map(item->item.getProductName()).collect(Collectors.toList()));
        }

        stockMapper.empty();
        stockMapper.insertSelect(year, month);
        return "ok";
    }


}
