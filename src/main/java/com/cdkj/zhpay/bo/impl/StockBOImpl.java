package com.cdkj.zhpay.bo.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cdkj.zhpay.bo.IStockBO;
import com.cdkj.zhpay.bo.base.PaginableBOImpl;
import com.cdkj.zhpay.core.OrderNoGenerater;
import com.cdkj.zhpay.dao.IStockDAO;
import com.cdkj.zhpay.domain.Stock;
import com.cdkj.zhpay.enums.EGeneratePrefix;
import com.cdkj.zhpay.exception.BizException;

@Component
public class StockBOImpl extends PaginableBOImpl<Stock> implements IStockBO {

    @Autowired
    private IStockDAO stockDAO;

    @Override
    public boolean isStockExist(String code) {
        Stock condition = new Stock();
        condition.setCode(code);
        if (stockDAO.selectTotalCount(condition) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public int saveStock(Stock data) {
        int count = 0;
        if (data != null) {
            data.setCode(OrderNoGenerater.generateM(EGeneratePrefix.STOCK
                .getCode()));
            count = stockDAO.insert(data);
        }
        return count;
    }

    @Override
    public int removeStock(String code) {
        int count = 0;
        if (StringUtils.isNotBlank(code)) {
            Stock data = new Stock();
            data.setCode(code);
            count = stockDAO.delete(data);
        }
        return count;
    }

    @Override
    public int refreshStockStatus(String code, String status) {
        int count = 0;
        if (StringUtils.isNotBlank(code)) {
            Stock data = new Stock();
            data.setCode(code);
            data.setStatus(status);
            count = stockDAO.updateStatus(data);
        }
        return count;
    }

    @Override
    public List<Stock> queryStockList(Stock condition) {
        return stockDAO.selectList(condition);
    }

    @Override
    public Stock getStock(String code) {
        Stock data = null;
        if (StringUtils.isNotBlank(code)) {
            Stock condition = new Stock();
            condition.setCode(code);
            data = stockDAO.select(condition);
            if (data == null) {
                throw new BizException("xn0000", "股份编号不存在");
            }
        }
        return data;
    }
}
