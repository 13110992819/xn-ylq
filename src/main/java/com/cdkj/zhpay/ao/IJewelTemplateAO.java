package com.cdkj.zhpay.ao;

import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.domain.JewelTemplate;
import com.cdkj.zhpay.dto.req.XN615000Req;
import com.cdkj.zhpay.dto.req.XN615002Req;

public interface IJewelTemplateAO {
    static final String DEFAULT_ORDER_COLUMN = "code";

    public String addJewelTemplate(XN615000Req req);

    public void dropJewelTemplate(String code);

    public void editJewelTemplate(XN615002Req req);

    public String putOn(String code, String updater, String remark);

    public String putOff(String code, String updater, String remark);

    public Paginable<JewelTemplate> queryJewelTemplatePage(int start,
            int limit, JewelTemplate condition);

    public JewelTemplate getJewelTemplate(String code);

}
