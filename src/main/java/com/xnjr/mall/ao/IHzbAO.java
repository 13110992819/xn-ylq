package com.xnjr.mall.ao;

import java.util.List;

import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.domain.Hzb;
import com.xnjr.mall.domain.HzbHold;

public interface IHzbAO {
    static final String DEFAULT_ORDER_COLUMN = "code";

    public Object buyHzb(String userId, String hzbCode, String payType,
            String fromIp);

    /**
     * 根据流水编号，找到对应购买记录，更新支付状态并分成
     * @param jourCode
     * @return 
     * @create: 2017年1月18日 下午5:16:27 xieyj
     * @history:
     */
    public void paySuccess(String jourCode);

    public void activateHzb(String userId);

    public void putOnOffHzb(String userId);

    public HzbHold myHzb(String userId);

    public void dropHzb(String code);

    public void editHzb(Hzb data);

    public Paginable<Hzb> queryHzbPage(int start, int limit, Hzb condition);

    public List<Hzb> queryHzbList(Hzb condition);

    public Hzb getHzb(String code);

}
