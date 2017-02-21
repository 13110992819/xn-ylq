package com.cdkj.zhpay.ao;

import java.util.List;

import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.domain.JewelTemplate;

public interface IJewelTemplateAO {
    static final String DEFAULT_ORDER_COLUMN = "code";

    public String addJewelTemplate(JewelTemplate data);

    public int dropJewelTemplate(String code);

    public int editJewelTemplate(JewelTemplate data);

    public Paginable<JewelTemplate> queryJewelTemplatePage(int start,
            int limit, JewelTemplate condition);

    public List<JewelTemplate> queryJewelTemplateList(JewelTemplate condition);

    public JewelTemplate getJewelTemplate(String code);

    /**
     * 模板上下架
     * @param code
     * @param updater
     * @param remark
     * @return 
     * @create: 2017年2月20日 下午2:47:34 haiqingzheng
     * @history:
     */
    public int putOnOff(String code, String updater, String remark);

}
