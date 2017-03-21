package com.cdkj.zhpay.ao;

import java.util.List;

import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.domain.HzbTemplate;
import com.cdkj.zhpay.dto.req.XN615100Req;
import com.cdkj.zhpay.dto.req.XN615102Req;

public interface IHzbTemplateAO {
    static final String DEFAULT_ORDER_COLUMN = "code";

    public String addTemplate(XN615100Req req);

    public void editTemplate(XN615102Req req);

    public void putOnTemplate(String code, String updater, String remark);

    public void putOffTemplate(String code, String updater, String remark);

    public Paginable<HzbTemplate> queryHzbTemplatePage(int start, int limit,
            HzbTemplate condition);

    public List<HzbTemplate> queryHzbTemplateList(HzbTemplate condition);

    public HzbTemplate getHzbTemplate(String code);

}
