package com.cdkj.zhpay.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cdkj.zhpay.dao.IStockHoldDAO;
import com.cdkj.zhpay.dao.base.support.AMybatisTemplate;
import com.cdkj.zhpay.domain.StockHold;

@Repository("stockHoldDAOImpl")
public class StockHoldDAOImpl extends AMybatisTemplate implements IStockHoldDAO {

    @Override
    public int insert(StockHold data) {
        return super.insert(NAMESPACE.concat("insert_stockHold"), data);
    }

    @Override
    public int delete(StockHold data) {
        return super.delete(NAMESPACE.concat("delete_stockHold"), data);
    }

    @Override
    public StockHold select(StockHold condition) {
        return super.select(NAMESPACE.concat("select_stockHold"), condition,
            StockHold.class);
    }

    @Override
    public Long selectTotalCount(StockHold condition) {
        return super.selectTotalCount(
            NAMESPACE.concat("select_stockHold_count"), condition);
    }

    @Override
    public List<StockHold> selectList(StockHold condition) {
        return super.selectList(NAMESPACE.concat("select_stockHold"),
            condition, StockHold.class);
    }

    @Override
    public List<StockHold> selectList(StockHold condition, int start, int count) {
        return super.selectList(NAMESPACE.concat("select_stockHold"), start,
            count, condition, StockHold.class);
    }

    @Override
    public int update(StockHold data) {
        return super.update(NAMESPACE.concat("update_stockHold"), data);
    }

    /** 
     * @see com.xnjr.mall.dao.IStockHoldDAO#updatePutStatus(com.xnjr.mall.domain.StockHold)
     */
    @Override
    public int updateStatus(StockHold data) {
        return super.update(NAMESPACE.concat("update_stockHold_status"), data);
    }

}
