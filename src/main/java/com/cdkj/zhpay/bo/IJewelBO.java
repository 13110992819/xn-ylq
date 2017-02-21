package com.cdkj.zhpay.bo;

import java.util.List;

import com.cdkj.zhpay.bo.base.IPaginableBO;
import com.cdkj.zhpay.domain.Jewel;
import com.cdkj.zhpay.domain.JewelTemplate;
import com.cdkj.zhpay.enums.EJewelStatus;

/**
 * 
 * @author: shan 
 * @since: 2016年12月19日 下午3:42:11 
 * @history:
 */
public interface IJewelBO extends IPaginableBO<Jewel> {
    /**
     * 判断是否存在
     * @param code
     * @return 
     * @create: 2016年12月19日 下午3:49:31 shan
     * @history:
     */
    public boolean isJewelExist(String code);

    /**
     * 保存新夺宝商品
     * @param data
     * @return 
     * @create: 2016年12月19日 下午3:49:36 shan
     * @history:
     */
    public String saveJewel(JewelTemplate jewelTemplate);

    /**
     * 查询夺宝商品详情
     * @param code
     * @return 
     * @create: 2016年12月19日 下午3:49:48 shan
     * @history:
     */
    public Jewel getJewel(String code);

    /**
     * 查询夺宝商品详情
     * @param code
     * @return 
     * @create: 2016年12月19日 下午3:49:48 shan
     * @history:
     */
    public boolean isExist(String templateCode, EJewelStatus status);

    /**
     * 查询所有商品
     * @param data
     * @return 
     * @create: 2016年12月19日 下午3:49:52 shan
     * @history:
     */
    public List<Jewel> queryJewelList(Jewel data);

    /**
     * 更新状态
     * @param code
     * @param status
     * @return 
     * @create: 2017年1月12日 下午5:20:58 xieyj
     * @history:
     */
    public int refreshStatus(String code, String status);

    public int refreshInvestInfo(String code, Integer investNum);

    public int refreshWinInfo(String code, String winNumber, String winUserId);
}
