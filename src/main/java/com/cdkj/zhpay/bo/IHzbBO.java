package com.cdkj.zhpay.bo;

import java.util.List;

import com.cdkj.zhpay.bo.base.IPaginableBO;
import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.domain.Hzb;
import com.cdkj.zhpay.domain.HzbTemplate;

public interface IHzbBO extends IPaginableBO<Hzb> {

    public boolean isHzbExistByUser(String userId);

    public void checkBuy(String userId);

    public int doFRPay(String userId, HzbTemplate hzbTemplate);

    public int buySuccess(String userId, HzbTemplate hzbTemplate,
            String payGroup);

    /**
     * 余额支付落地汇赚宝记录
     * @param userId
     * @param hzbTemplate
     * @param amount
     * @return 
     * @create: 2017年2月27日 下午4:12:54 haiqingzheng
     * @history:
     */
    public int saveHzb(String userId, HzbTemplate hzbTemplate, Long amount);

    public int removeHzb(Long id);

    public int refreshStatus(Long id, String status);

    public int refreshPayStatus(String code, String status, String payCode,
            Long payAmount);

    public int refreshRockNum(Long id, Integer periodRockNum,
            Integer totalRockNum);

    public void resetPeriodRockNum();

    public List<Hzb> queryHzbList(Hzb condition);

    public List<Hzb> queryDistanceHzbList(Hzb condition);

    public Paginable<Hzb> queryDistancePaginable(int start, int pageSize,
            Hzb condition);

    public Hzb getHzb(String code);

    public Hzb getHzbByUser(String userId);

    public Long getTotalAmount(String payGroup);

}
