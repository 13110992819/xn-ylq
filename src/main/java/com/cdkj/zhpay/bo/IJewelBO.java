package com.cdkj.zhpay.bo;

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
     * @param templateCode
     * @param status
     * @return 
     * @create: 2017年3月21日 下午9:39:08 xieyj
     * @history:
     */
    public boolean isExist(String templateCode, EJewelStatus status);

    public int refreshInvestInfo(Jewel jewel, Integer investNum);

    public int refreshWinInfo(String code, String winNumber, String winUserId);
}
