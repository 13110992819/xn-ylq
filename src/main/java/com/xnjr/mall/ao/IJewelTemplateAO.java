package com.xnjr.mall.ao;

import java.util.List;

import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.domain.JewelTemplate;

public interface IJewelTemplateAO {
    static final String DEFAULT_ORDER_COLUMN = "code";

    public String addJewelTemplate(JewelTemplate data);

    public int dropJewelTemplate(String code);

    public int editJewelTemplate(JewelTemplate data);

    public Paginable<JewelTemplate> queryJewelTemplatePage(int start,
            int limit, JewelTemplate condition);

    public List<JewelTemplate> queryJewelTemplateList(JewelTemplate condition);

    public JewelTemplate getJewelTemplate(String code);

    public int putOnOff(String code);

}
