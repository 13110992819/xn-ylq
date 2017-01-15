package com.xnjr.mall.ao;

import java.util.List;

import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.domain.JewelRecord;

/**
 * @author: shan 
 * @since: 2016年12月20日 下午12:08:57 
 * @history:
 */
public interface IJewelRecordAO {
    static final String DEFAULT_ORDER_COLUMN = "code";

    /**
     * 购买宝贝
     * @param userId
     * @param jewelCode
     * @param times
     * @param payType
     * @param ip
     * @return 
     * @create: 2017年1月11日 下午7:35:31 xieyj
     * @history:
     */
    public Object buyJewel(String userId, String jewelCode, Integer times,
            String payType, String ip);

    /**
     * 根据流水编号，找到对应消费记录，更新支付状态
     * @param payCode
     * @return 
     * @create: 2017年1月10日 下午7:48:09 xieyj
     * @history:
     */
    public void paySuccess(String payCode);

    /**
     * 填写收货地址
     * @param code
     * @param receiver
     * @param reMobile
     * @param reAddress 
     * @create: 2017年1月13日 下午2:24:57 xieyj
     * @history:
     */
    public void fulReAddress(String code, String receiver, String reMobile,
            String reAddress);

    /**
     * 发货 
     * @param code
     * @param updater
     * @param remark 
     * @create: 2017年1月13日 下午2:31:00 xieyj
     * @history:
     */
    public void sendJewel(String code, String updater, String remark);

    /**
     * 签收
     * @param code 
     * @create: 2017年1月13日 下午2:25:28 xieyj
     * @history:
     */
    public void signJewel(String code);

    /**
     * 标的购买记录分页
     * @param start
     * @param limit
     * @param condition
     * @return 
     * @create: 2016年12月20日 下午12:11:49 shan
     * @history:
     */
    public Paginable<JewelRecord> queryJewelRecordPage(int start, int limit,
            JewelRecord condition);

    public Paginable<JewelRecord> queryMyJewelRecordPage(int start, int limit,
            JewelRecord condition);

    /**
     * 查询所有标的购买记录
     * @param condition
     * @return 
     * @create: 2016年12月20日 下午12:11:53 shan
     * @history:
     */
    public List<JewelRecord> queryJewelRecordList(JewelRecord condition);

    /**
     * 查询标的购买详情
     * @param code
     * @return 
     * @create: 2016年12月20日 下午12:14:53 shan
     * @history:
     */
    public JewelRecord getJewelRecord(String code, String userId);

}
