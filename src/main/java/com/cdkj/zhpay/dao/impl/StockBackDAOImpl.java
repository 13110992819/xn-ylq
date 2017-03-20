package com.cdkj.zhpay.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cdkj.zhpay.dao.IStockBackDAO;
import com.cdkj.zhpay.dao.base.support.AMybatisTemplate;
import com.cdkj.zhpay.domain.StockBack;

@Repository("stockBackDAOImpl")
public class StockBackDAOImpl extends AMybatisTemplate implements IStockBackDAO {

    @Override
    public int insert(StockBack data) {
        return super.insert(NAMESPACE.concat("insert_stockBack"), data);
    }

    @Override
    public int delete(StockBack data) {
        return super.delete(NAMESPACE.concat("delete_stockBack"), data);
    }

    @Override
    public StockBack select(StockBack condition) {
        return super.select(NAMESPACE.concat("select_stockBack"), condition,
            StockBack.class);
    }

    @Override
    public Long selectTotalCount(StockBack condition) {
        return super.selectTotalCount(
            NAMESPACE.concat("select_stockBack_count"), condition);
    }

    @Override
    public List<StockBack> selectList(StockBack condition) {
        return super.selectList(NAMESPACE.concat("select_stockBack"),
            condition, StockBack.class);
    }

    @Override
    public List<StockBack> selectList(StockBack condition, int start, int count) {
        return super.selectList(NAMESPACE.concat("select_stockBack"), start,
            count, condition, StockBack.class);
    }

}
