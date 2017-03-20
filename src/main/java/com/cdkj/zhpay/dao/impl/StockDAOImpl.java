package com.cdkj.zhpay.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cdkj.zhpay.dao.IStockDAO;
import com.cdkj.zhpay.dao.base.support.AMybatisTemplate;
import com.cdkj.zhpay.domain.Stock;

@Repository("stockDAOImpl")
public class StockDAOImpl extends AMybatisTemplate implements IStockDAO {

    @Override
    public int insert(Stock data) {
        return super.insert(NAMESPACE.concat("insert_stock"), data);
    }

    @Override
    public int delete(Stock data) {
        return super.delete(NAMESPACE.concat("delete_stock"), data);
    }

    @Override
    public Stock select(Stock condition) {
        return super.select(NAMESPACE.concat("select_stock"), condition,
            Stock.class);
    }

    @Override
    public Long selectTotalCount(Stock condition) {
        return super.selectTotalCount(NAMESPACE.concat("select_stock_count"),
            condition);
    }

    @Override
    public List<Stock> selectList(Stock condition) {
        return super.selectList(NAMESPACE.concat("select_stock"), condition,
            Stock.class);
    }

    @Override
    public List<Stock> selectList(Stock condition, int start, int count) {
        return super.selectList(NAMESPACE.concat("select_stock"), start, count,
            condition, Stock.class);
    }

    @Override
    public int updateStatus(Stock data) {
        return super.update(NAMESPACE.concat("update_status"), data);
    }

}
