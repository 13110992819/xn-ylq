package com.cdkj.zhpay.bo;

import java.util.List;

import com.cdkj.zhpay.bo.base.IPaginableBO;
import com.cdkj.zhpay.domain.HzbMgift;
import com.cdkj.zhpay.enums.EHzbMgiftStatus;

/**
 * @author: xieyj 
 * @since: 2017年2月20日 下午1:50:02 
 * @history:
 */
public interface IHzbMgiftBO extends IPaginableBO<HzbMgift> {

    public boolean isHzbMgiftExist(String code);

    public String saveHzbMgift(HzbMgift data);

    public int refreshHzbMgiftStatus(String code, EHzbMgiftStatus status);

    public int refreshHzbMgiftReciever(String code, String userId);

    public List<HzbMgift> queryHzbMgiftList(HzbMgift condition);

    public HzbMgift getHzbMgift(String code);

}
