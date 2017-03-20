package com.cdkj.zhpay.ao;

import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.domain.JewelTemplate;

public interface IJewelTemplateAO {
    static final String DEFAULT_ORDER_COLUMN = "code";

    public String addJewelTemplate(JewelTemplate data);

    public int dropJewelTemplate(String code);

    public int editJewelTemplate(JewelTemplate data);

    public Paginable<JewelTemplate> queryJewelTemplatePage(int start,
            int limit, JewelTemplate condition);

    public JewelTemplate getJewelTemplate(String code);

    public String putOnOff(String code, String updater, String remark);

}
