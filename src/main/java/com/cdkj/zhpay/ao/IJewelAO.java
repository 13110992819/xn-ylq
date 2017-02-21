package com.cdkj.zhpay.ao;

import java.util.List;

import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.domain.Jewel;

/**
 * @author: haiqingzheng 
 * @since: 2017年2月20日 下午4:11:27 
 * @history:
 */
public interface IJewelAO {
    static final String DEFAULT_ORDER_COLUMN = "code";

    /**
     * 根据模板编号，如果没有正在募集中的项目，则发布下一期
     * @param templateCode 
     * @create: 2017年2月20日 下午4:27:36 haiqingzheng
     * @history:
     */
    public void publishNextPeriods(String templateCode);

    /**
     * 分页查询
     * @param start
     * @param limit
     * @param condition
     * @return 
     * @create: 2016年12月21日 下午3:59:41 asus
     * @history:
     */
    public Paginable<Jewel> queryJewelPage(int start, int limit, Jewel condition);

    /**
     * 列表查询
     * @param condition
     * @return 
     * @create: 2016年12月21日 下午4:45:57 asus
     * @history:
     */
    public List<Jewel> queryJewelList(Jewel condition);

    public Jewel getJewel(String code);
}
