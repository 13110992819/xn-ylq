package com.cdkj.zhpay.bo;

import java.util.List;

import com.cdkj.zhpay.bo.base.IPaginableBO;
import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.domain.HzbTemplate;
import com.cdkj.zhpay.domain.Hzb;

public interface IHzbBO extends IPaginableBO<Hzb> {

    public boolean isHzbHoldExist(Long id);

    public boolean isHzbHoldExistByUser(String userId);

    /**
     * 微信支付落地汇赚宝记录
     * @param userId
     * @param hzbTemplate
     * @param payGroup
     * @return 
     * @create: 2017年2月27日 下午4:10:24 haiqingzheng
     * @history:
     */
    public int saveHzbHold(String userId, HzbTemplate hzbTemplate, String payGroup);

    /**
     * 余额支付落地汇赚宝记录
     * @param userId
     * @param hzbTemplate
     * @param amount
     * @return 
     * @create: 2017年2月27日 下午4:12:54 haiqingzheng
     * @history:
     */
    public int saveHzbHold(String userId, HzbTemplate hzbTemplate, Long amount);

    public int removeHzbHold(Long id);

    public int refreshStatus(Long id, String status);

    public int refreshPayStatus(Long id, String status, String payCode,
            Long payAmount);

    public int refreshRockNum(Long id, Integer periodRockNum,
            Integer totalRockNum);

    public void resetPeriodRockNum();

    public List<Hzb> queryHzbHoldList(Hzb condition);

    public List<Hzb> queryDistanceHzbHoldList(Hzb condition);

    public Paginable<Hzb> queryDistancePaginable(int start, int pageSize,
            Hzb condition);

    public Hzb getHzbHold(Long id);

    public Hzb getHzbHold(String userId);

    public Long getTotalAmount(String payGroup);
}
