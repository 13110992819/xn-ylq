package com.cdkj.zhpay.bo;

import java.util.List;

import com.cdkj.zhpay.bo.base.IPaginableBO;
import com.cdkj.zhpay.domain.HzbTemplate;

public interface IHzbTemplateBO extends IPaginableBO<HzbTemplate> {
    public void saveHzbTemplate(HzbTemplate data);

    public int refreshHzbTemplate(HzbTemplate data);

    public List<HzbTemplate> queryHzbTemplateList(HzbTemplate condition);

    public HzbTemplate getHzbTemplate(String code);

    public int putOnTemplate(String code, String updater, String remark);

    public int putOffTemplate(String code, String updater, String remark);

}
