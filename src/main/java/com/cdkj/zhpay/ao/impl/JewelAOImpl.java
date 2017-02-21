package com.cdkj.zhpay.ao.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdkj.zhpay.ao.IJewelAO;
import com.cdkj.zhpay.bo.IJewelBO;
import com.cdkj.zhpay.bo.IJewelTemplateBO;
import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.domain.Jewel;
import com.cdkj.zhpay.domain.JewelTemplate;
import com.cdkj.zhpay.enums.EJewelStatus;

/**
 * @author: xieyj 
 * @since: 2017年1月11日 下午6:16:26 
 * @history:
 */
@Service
public class JewelAOImpl implements IJewelAO {
    protected static final Logger logger = LoggerFactory
        .getLogger(JewelAOImpl.class);

    @Autowired
    IJewelBO jewelBO;

    @Autowired
    IJewelTemplateBO jewelTemplateBO;

    @Override
    public Paginable<Jewel> queryJewelPage(int start, int limit, Jewel condition) {
        return jewelBO.getPaginable(start, limit, condition);
    }

    @Override
    public List<Jewel> queryJewelList(Jewel condition) {
        return jewelBO.queryJewelList(condition);
    }

    @Override
    public Jewel getJewel(String code) {
        return jewelBO.getJewel(code);
    }

    @Override
    @Transactional
    public void publishNextPeriods(String templateCode) {
        JewelTemplate jewelTemplate = jewelTemplateBO
            .getJewelTemplate(templateCode);
        Jewel condition = new Jewel();
        condition.setTemplateCode(templateCode);
        condition.setStatus(EJewelStatus.RUNNING.getCode());
        // 如果没有正在募集中的项目
        if (jewelBO.getTotalCount(condition) <= 0) {
            // 发布下一期项目
            Jewel jewel = new Jewel();
            jewel.setTemplateCode(templateCode);
            if (jewelTemplate.getCurrentPeriods() == null) {
                jewel.setPeriods(1000000001);
            } else {
                jewel.setPeriods(jewelTemplate.getCurrentPeriods() + 1);
            }
            jewel.setCurrency(jewelTemplate.getCurrency());
            jewel.setAmount(jewelTemplate.getAmount());
            jewel.setTotalNum(jewelTemplate.getTotalNum());
            jewel.setPrice(jewelTemplate.getPrice());
            jewel.setMaxInvestNum(jewelTemplate.getMaxInvestNum());
            jewel.setAdvText(jewelTemplate.getAdvText());
            jewel.setAdvPic(jewelTemplate.getAdvPic());
            jewel.setCreateDatetime(new Date());
            jewel.setSystemCode(jewelTemplate.getSystemCode());
            jewelBO.saveJewel(jewel);
            // 更新模板当前发布期号
            jewelTemplateBO.refreshPeriods(templateCode, jewel.getPeriods());
        }
    }
}
