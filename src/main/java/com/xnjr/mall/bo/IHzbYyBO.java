package com.xnjr.mall.bo;

import java.util.List;

import com.xnjr.mall.bo.base.IPaginableBO;
import com.xnjr.mall.domain.HzbYy;

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

    public HzbYy getHzbYy(String code);

    // 判断该用户是否第三次摇没有红包
    public boolean isThirdYyNoHB(String userId);

}
