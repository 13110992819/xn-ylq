package com.xnjr.mall.ao;

import java.util.List;

import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.domain.Stock;
import com.xnjr.mall.domain.StockHold;

public interface IStockAO {
    static final String DEFAULT_ORDER_COLUMN = "code";

    public int dropStock(String code);

    public int editStock(Stock data);

    public Paginable<Stock> queryStockPage(int start, int limit, Stock condition);

    public List<Stock> queryStockList(Stock condition);

    public Stock getStock(String code);

    /**
     * 股份购买
     * @param code
     * @param userId
     * @param payType
     * @param ip 
     * @create: 2017年1月6日 下午6:24:20 xieyj
     * @history:
     */
    public Object buyStock(String code, String userId, String payType, String ip);

    /**
     * 股份返还
     * @param userId
     * @return 
     * @create: 2016年12月19日 下午7:49:25 haiqingzheng
     * @history:
     */
    public void returnStock(String userId);

    /**
     * 股份清算回购
     * @param userId
     * @return 
     * @create: 2016年12月19日 下午8:39:34 haiqingzheng
     * @history:
     */
    public void clearStock(String userId);

    /**
     * 我的股份查询
     * @param userId
     * @return 
     * @create: 2016年12月19日 下午8:49:33 haiqingzheng
     * @history:
     */
    public StockHold myStock(String userId);

    /**
     * 支付回调
     * @param payCode
     * @return 
     * @create: 2017年1月6日 下午9:25:22 xieyj
     * @history:
     */
    public void paySuccess(String payCode);
}
