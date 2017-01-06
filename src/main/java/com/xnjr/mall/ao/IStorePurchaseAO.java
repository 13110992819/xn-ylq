package com.xnjr.mall.ao;

import java.util.List;

import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.domain.Store;
import com.xnjr.mall.domain.StorePurchase;

public interface IStorePurchaseAO {
    static final String DEFAULT_ORDER_COLUMN = "code";

    /**
     * 消费买单
     * @param userId
     * @param storeCode
     * @param ticketCode
     * @param amount
     * @return 
     * @create: 2016年12月29日 上午11:03:25 xieyj
     * @history:
     */
    public Object storePurchase(String userId, String storeCode,
            String ticketCode, Long amount, String payType, String ip);

    /**
     * 购买成功
     * @param systemCode
     * @param store
     * @param yhAmount
     * @param userId
     * @param fcRate 
     * @create: 2017年1月5日 下午4:08:36 xieyj
     * @history:
     */
    public void purchaseSuccess(String systemCode, Store store, Long yhAmount,
            String userId, Long fcRate);

    public int dropStorePurchase(String code);

    public Paginable<StorePurchase> queryStorePurchasePage(int start,
            int limit, StorePurchase condition);

    public List<StorePurchase> queryStorePurchaseList(StorePurchase condition);

    public StorePurchase getStorePurchase(String code);

    /**
     * 根据流水编号，找到对应消费记录，更新支付状态
     * @param jourCode
     * @return 
     * @create: 2017年1月6日 下午1:02:30 haiqingzheng
     * @history:
     */
    public int paySuccess(String jourCode);

}
