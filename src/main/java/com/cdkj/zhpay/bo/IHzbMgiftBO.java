package com.cdkj.zhpay.bo;

import java.util.Date;
import java.util.List;

import com.cdkj.zhpay.bo.base.IPaginableBO;
import com.cdkj.zhpay.domain.HzbMgift;
import com.cdkj.zhpay.enums.EHzbMgiftStatus;

/**
 * @author: xieyj 
 * @since: 2017年2月20日 下午1:50:02 
 * @history:
 */
public interface IHzbMgiftBO extends IPaginableBO<HzbMgift> {

    public boolean isHzbMgiftExist(String code);

    /**
     * 发送红包
     * @param userId
     * @return 
     * @create: 2017年2月23日 下午1:27:20 xieyj
     * @history:
     */
    public void sendHzbMgift(String userId);

    public int refreshHzbMgiftStatus(String code, EHzbMgiftStatus status);

    public int refreshHzbMgiftReciever(String code, String userId);

    public List<HzbMgift> queryHzbMgiftList(HzbMgift condition);

    /**
     * 统计汇赚宝发一发次数
     * @param startDate
     * @param endDate
     * @param userId
     * @return 
     * @create: 2017年2月24日 上午10:40:44 xieyj
     * @history:
     */
    public List<HzbMgift> queryReceiveHzbMgift(Date startDate, Date endDate,
            String userId);

    public Long getReceiveHzbMgiftCount(Date startDate, Date endDate,
            String userId);

    public HzbMgift getHzbMgift(String code);

}
