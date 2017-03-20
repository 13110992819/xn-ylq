package com.cdkj.zhpay.bo.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cdkj.zhpay.bo.IStockHoldBO;
import com.cdkj.zhpay.bo.base.PaginableBOImpl;
import com.cdkj.zhpay.dao.IStockHoldDAO;
import com.cdkj.zhpay.domain.StockHold;
import com.cdkj.zhpay.exception.BizException;

@Component
public class StockHoldBOImpl extends PaginableBOImpl<StockHold> implements
        IStockHoldBO {

    @Autowired
    private IStockHoldDAO stockHoldDAO;

    @Override
    public boolean isStockHoldExist(Long id) {
        StockHold condition = new StockHold();
        condition.setId(id);
        if (stockHoldDAO.selectTotalCount(condition) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public int saveStockHold(StockHold data) {
        int count = 0;
        if (data != null) {
            count = stockHoldDAO.insert(data);
        }
        return count;
    }

    @Override
    public int removeStockHold(Long id) {
        int count = 0;
        if (id != null) {
            StockHold data = new StockHold();
            data.setId(id);
            count = stockHoldDAO.delete(data);
        }
        return count;
    }

    @Override
    public List<StockHold> queryStockHoldList(StockHold condition) {
        return stockHoldDAO.selectList(condition);
    }

    @Override
    public StockHold getStockHold(Long id) {
        StockHold data = null;
        if (id != null) {
            StockHold condition = new StockHold();
            condition.setId(id);
            data = stockHoldDAO.select(condition);
            if (data == null) {
                throw new BizException("xn0000", "异常");
            }
        }
        return data;
    }

    @Override
    public int refreshStockHold(StockHold data) {
        int count = 0;
        if (data.getId() != null) {
            count = stockHoldDAO.update(data);
        }
        return count;
    }

    /** 
     * @see com.xnjr.mall.bo.IStockHoldBO#refreshStatus(java.lang.Long, java.lang.String)
     */
    @Override
    public int refreshStatus(Long id, String status) {
        int count = 0;
        if (id == null) {
            StockHold data = new StockHold();
            data.setId(id);
            data.setStatus(status);
            count = stockHoldDAO.update(data);
        }
        return count;
    }

    /** 
     * @see com.xnjr.mall.bo.IStockHoldBO#getStockHoldByUser(java.lang.String)
     */
    @Override
    public StockHold getStockHoldByUser(String userId) {
        StockHold stockHold = null;
        if (StringUtils.isNotBlank(userId)) {
            StockHold condition = new StockHold();
            condition.setUserId(userId);
            List<StockHold> shList = this.queryStockHoldList(condition);
            if (CollectionUtils.isNotEmpty(shList)) {
                stockHold = shList.get(0);
            }
        }
        return stockHold;
    }
}
