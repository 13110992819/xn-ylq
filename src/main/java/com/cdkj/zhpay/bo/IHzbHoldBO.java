package com.cdkj.zhpay.bo;

import java.util.List;

import com.cdkj.zhpay.bo.base.IPaginableBO;
import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.domain.Hzb;
import com.cdkj.zhpay.domain.HzbHold;

public interface IHzbHoldBO extends IPaginableBO<HzbHold> {

    public boolean isHzbHoldExist(Long id);

    public boolean isHzbHoldExistByUser(String userId);

    /**
     * 微信支付落地汇赚宝记录
     * @param userId
     * @param hzb
     * @param payGroup
     * @return 
     * @create: 2017年2月27日 下午4:10:24 haiqingzheng
     * @history:
     */
    public int saveHzbHold(String userId, Hzb hzb, String payGroup);

    /**
     * 余额支付落地汇赚宝记录
     * @param userId
     * @param hzb
     * @param amount
     * @return 
     * @create: 2017年2月27日 下午4:12:54 haiqingzheng
     * @history:
     */
    public int saveHzbHold(String userId, Hzb hzb, Long amount);

    public int removeHzbHold(Long id);

    public int refreshStatus(Long id, String status);

    public int refreshPayStatus(Long id, String status, String payCode);

    public int refreshRockNum(Long id, Integer periodRockNum,
            Integer totalRockNum);

    public void resetPeriodRockNum();

    public List<HzbHold> queryHzbHoldList(HzbHold condition);

    public List<HzbHold> queryDistanceHzbHoldList(HzbHold condition);

    public Paginable<HzbHold> queryDistancePaginable(int start, int pageSize,
            HzbHold condition);

    public HzbHold getHzbHold(Long id);

    public HzbHold getHzbHold(String userId);

    public Long getTotalAmount(String payGroup);
}
