package com.cdkj.zhpay.bo;

import java.util.Date;
import java.util.List;

import com.cdkj.zhpay.bo.base.IPaginableBO;
import com.cdkj.zhpay.domain.HzbYy;

public interface IHzbYyBO extends IPaginableBO<HzbYy> {

    public boolean isHzbYyExist(String code);

    public void checkHzbYyCondition(String systemCode, String userId,
            String deviceNo);

    public void checkHzbYyCondition(String systemCode, Long hzbHoldId,
            String userId, String deviceNo);

    public String saveHzbYy(HzbYy data);

    public int removeHzbYy(String code);

    public int refreshHzbYy(HzbYy data);

    public List<HzbYy> queryHzbYyList(HzbYy condition);

    public Long getTotalHzbYyCount(Date startDate, Date endDate, Long hzbHoldId);

    public HzbYy getHzbYy(String code);

    // 判断该用户是否摇到红包
    public String isHaveHB(String userId);

}
