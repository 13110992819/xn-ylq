package com.cdkj.zhpay.bo;

import java.util.List;

import com.cdkj.zhpay.bo.base.IPaginableBO;
import com.cdkj.zhpay.domain.HzbTemplate;

public interface IHzbTemplateBO extends IPaginableBO<HzbTemplate> {

    public boolean isHzbExist(String code);

    public int removeHzb(String code);

    public int refreshHzb(HzbTemplate data);

    public List<HzbTemplate> queryHzbList(HzbTemplate condition);

    public HzbTemplate getHzb(String code);

}
