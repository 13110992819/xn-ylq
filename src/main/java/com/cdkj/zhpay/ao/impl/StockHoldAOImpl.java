package com.cdkj.zhpay.ao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdkj.zhpay.ao.IStockHoldAO;
import com.cdkj.zhpay.bo.IStockBO;
import com.cdkj.zhpay.bo.IStockHoldBO;
import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.domain.Stock;
import com.cdkj.zhpay.domain.StockHold;
import com.cdkj.zhpay.exception.BizException;

@Service
public class StockHoldAOImpl implements IStockHoldAO {

    @Autowired
    private IStockHoldBO stockHoldBO;

    @Autowired
    private IStockBO stockBO;

    @Override
    public int addStockHold(StockHold data) {
        return stockHoldBO.saveStockHold(data);
    }

    @Override
    public int dropStockHold(Long id) {
        if (!stockHoldBO.isStockHoldExist(id)) {
            throw new BizException("xn0000", "记录编号不存在");
        }
        return stockHoldBO.removeStockHold(id);
    }

    @Override
    public Paginable<StockHold> queryStockHoldPage(int start, int limit,
            StockHold condition) {
        return stockHoldBO.getPaginable(start, limit, condition);
    }

    @Override
    public List<StockHold> queryStockHoldList(StockHold condition) {
        return stockHoldBO.queryStockHoldList(condition);
    }

    @Override
    public StockHold getStockHold(Long id) {
        StockHold stockHold = stockHoldBO.getStockHold(id);
        Stock stock = stockBO.getStock(stockHold.getStockCode());
        stockHold.setStock(stock);
        return stockHold;
    }
}
