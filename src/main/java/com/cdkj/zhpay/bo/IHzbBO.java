package com.cdkj.zhpay.bo;

import java.util.List;

import com.cdkj.zhpay.bo.base.IPaginableBO;
import com.cdkj.zhpay.domain.Hzb;
import com.cdkj.zhpay.domain.HzbTemplate;

public interface IHzbBO extends IPaginableBO<Hzb> {

    public boolean isHzbExistByUser(String userId);

    public void checkBuy(String userId);

    public int saveHzb(String userId, HzbTemplate hzbTemplate, Long frPayAmount);

    public int buyHzb(String userId, HzbTemplate hzbTemplate, String payGroup);

    public int refreshPayStatus(String code, String status, String payCode,
            Long payAmount);

    public int refreshPutStatus(String code, String status);

    public void resetPeriodRockNum();

    public int refreshRockNum(String code, Integer periodRockNum,
            Integer totalRockNum);

    public List<Hzb> queryHzbList(String payGroup);

    public List<Hzb> queryHzbList(Hzb condition);

    public List<Hzb> queryHzbList(String userId, String companyCode,
            String systemCode);

    public List<Hzb> queryDistanceHzbList(Hzb condition);

    public Hzb getHzb(String code);
}
