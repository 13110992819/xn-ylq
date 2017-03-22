package com.cdkj.zhpay.bo;

import java.util.Date;

import com.cdkj.zhpay.bo.base.IPaginableBO;
import com.cdkj.zhpay.domain.Hzb;
import com.cdkj.zhpay.domain.HzbYy;
import com.cdkj.zhpay.domain.User;

public interface IHzbYyBO extends IPaginableBO<HzbYy> {

    public String saveHzbYy(HzbYy data);

    public Long getTotalHzbYyCount(Date startDate, Date endDate, Long hzbHoldId);

    public HzbYy getHzbYy(String code);

    // 判断该用户是否摇到红包
    public String isHaveHB(String userId);

    /**
     * 当然用户，当前设备在对应摇钱树上能否摇一摇
     * @param hzb
     * @param yyUser
     * @param deviceNo 
     * @create: 2017年3月22日 下午7:08:09 myb858
     * @history:
     */
    public void checkYy(Hzb hzb, User yyUser, String deviceNo);

}
