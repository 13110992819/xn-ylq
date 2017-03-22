package com.cdkj.zhpay.dao;

import java.util.List;

import com.cdkj.zhpay.dao.base.IBaseDAO;
import com.cdkj.zhpay.domain.HzbMgift;

//dao层 
public interface IHzbMgiftDAO extends IBaseDAO<HzbMgift> {
    String NAMESPACE = IHzbMgiftDAO.class.getName().concat(".");

    int doSendHzbMgift(HzbMgift hzbMgift);

    int doReceiveHzbMgift(HzbMgift hzbMgift);

    int doDailyInvalid(List<String> codeList);
}
