package com.cdkj.zhpay.ao;

import java.util.List;

import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.domain.Hzb;
import com.cdkj.zhpay.dto.res.XN808802Res;

public interface IHzbAO {
    static final String DEFAULT_ORDER_COLUMN = "id";

    public Object buyHzbOfZH(String userId, String hzbTemplateCode,
            String payType);

    public Object buyHzbOfCG(String userId, String hzbTemplateCode,
            String payType);

    public void paySuccess(String payGroup, String payCode, Long transAmount);

    public List<Hzb> myHzb(String userId, String systemCode, String companyCode);

    public void putOnOff(String code);

    public Hzb getHzb(String code);

    public Paginable<Hzb> queryHzbHoldPage(int start, int limit, Hzb condition);

    public Paginable<Hzb> queryDistanceHzbHoldPage(int start, int limit,
            Hzb condition);

    public List<Hzb> queryHzbHoldList(Hzb condition);

    /** 
     * @param latitude
     * @param longitude
     * @param userId
     * @param deviceNo
     * @param string2 
     * @param string 
     * @return 
     * @create: 2017年3月21日 下午2:14:07 xieyj
     * @history: 
     */
    public Object queryHzbList(String latitude, String longitude,
            String userId, String deviceNo, String companyCode,
            String systemCode);

    public XN808802Res doGetHzbTotalData(String systemCode, String userId);

    public void doResetRockNumDaily();

}
