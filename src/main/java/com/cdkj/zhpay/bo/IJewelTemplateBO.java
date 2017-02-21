package com.cdkj.zhpay.bo;

import java.util.List;

import com.cdkj.zhpay.bo.base.IPaginableBO;
import com.cdkj.zhpay.domain.JewelTemplate;

public interface IJewelTemplateBO extends IPaginableBO<JewelTemplate> {

    public boolean isJewelTemplateExist(String code);

    public String saveJewelTemplate(JewelTemplate data);

    public int removeJewelTemplate(String code);

    public int refreshJewelTemplate(JewelTemplate data);

    public List<JewelTemplate> queryJewelTemplateList(JewelTemplate condition);

    public JewelTemplate getJewelTemplate(String code);

    public int refreshStatus(String code, String status, String updater,
            String remark);

    public int refreshPeriods(String code, Integer periods);

}
