package com.cdkj.zhpay.bo;

import java.util.List;

import com.cdkj.zhpay.bo.base.IPaginableBO;
import com.cdkj.zhpay.domain.Hzb;
import com.cdkj.zhpay.domain.HzbTemplate;
import com.cdkj.zhpay.dto.res.XN808460Res;

public interface IHzbBO extends IPaginableBO<Hzb> {

    public boolean isHzbExistByUser(String userId);

    public void checkBuy(String userId);

    public Hzb saveHzb(String userId, HzbTemplate hzbTemplate, Long frPayAmount);

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

    public List<Hzb> queryDistanceHzbList(String latitude, String longitude,
            String companyCode, String systemCode);

    public Hzb getHzb(String code);

    /**
     * 检查摇钱树是否是激活状态
     * @param hzbCode
     * @return 
     * @create: 2017年3月22日 下午7:06:13 myb858
     * @history:
     */
    public Hzb checkActivated(String hzbCode);

    /**
     * 根据摇到奖品刷新摇钱树
     * @param hzb
     * @param prize 
     * @create: 2017年3月22日 下午8:18:00 myb858
     * @history:
     */
    public void refreshYy(Hzb hzb, XN808460Res prize);
}
