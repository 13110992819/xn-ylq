package com.cdkj.zhpay.bo;

import java.util.List;

import com.cdkj.zhpay.bo.base.IPaginableBO;
import com.cdkj.zhpay.domain.JewelTemplate;
import com.cdkj.zhpay.enums.EJewelTemplateStatus;

public interface IJewelTemplateBO extends IPaginableBO<JewelTemplate> {

    public void saveJewelTemplate(JewelTemplate data);

    public int refreshJewelTemplate(JewelTemplate data);

    public List<JewelTemplate> queryJewelTemplateList(JewelTemplate condition);

    public JewelTemplate getJewelTemplate(String code);

    public String refreshStatus(String code, EJewelTemplateStatus status,
            String updater, String remark);

    public int refreshPeriods(String code, Integer periods);
}
