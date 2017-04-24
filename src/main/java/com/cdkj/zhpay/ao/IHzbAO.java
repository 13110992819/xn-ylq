package com.cdkj.zhpay.ao;

import java.util.List;

import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.domain.Hzb;
import com.cdkj.zhpay.dto.res.XN615119Res;

public interface IHzbAO {
    static final String DEFAULT_ORDER_COLUMN = "code";

    public Object buyHzbOfZH(String userId, String hzbTemplateCode,
            String payType);

    // 赠送汇赚宝
    public void giveHzbOfOss(List<String> userIdList, String hzbTemplateCode);

    public Object buyHzbOfCG(String userId, String hzbTemplateCode,
            String payType);

    public void paySuccess(String payGroup, String payCode, Long transAmount);

    public List<Hzb> myHzb(String userId);

    public void putOnOff(String code);

    public Paginable<Hzb> queryHzbPage(int start, int limit, Hzb condition);

    public List<Hzb> queryHzbList(Hzb condition);

    public Hzb getHzb(String code);

    public Object queryDistanceHzbList(String latitude, String longitude,
            String userId, String deviceNo, String companyCode,
            String systemCode);

    public void doResetRockNumDaily();

    public XN615119Res doGetHzbTotalData(String userId);
}
