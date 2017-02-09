package com.xnjr.mall.ao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xnjr.mall.ao.IJewelInteractAO;
import com.xnjr.mall.bo.IJewelBO;
import com.xnjr.mall.bo.IJewelInteractBO;
import com.xnjr.mall.bo.IJewelRecordBO;
import com.xnjr.mall.bo.IProductBO;
import com.xnjr.mall.bo.IProductOrderBO;
import com.xnjr.mall.domain.Jewel;
import com.xnjr.mall.domain.JewelInteract;
import com.xnjr.mall.domain.JewelRecord;
import com.xnjr.mall.domain.Product;
import com.xnjr.mall.domain.ProductOrder;
import com.xnjr.mall.dto.res.XN808325Res;
import com.xnjr.mall.enums.EGeneratePrefix;
import com.xnjr.mall.enums.EInteractType;
import com.xnjr.mall.enums.EevaluateType;
import com.xnjr.mall.exception.BizException;

/**
 * 
 * @author: shan 
 * @since: 2016年12月19日 下午8:25:41 
 * @history:
 */
@Service
public class JewelInteractAOImpl implements IJewelInteractAO {
    @Autowired
    IJewelInteractBO jewelInteractBO;

    @Autowired
    IProductBO productBO;

    @Autowired
    IProductOrderBO productOrderBO;

    @Autowired
    IJewelBO jewelBO;

    @Autowired
    IJewelRecordBO jewelRecordBO;

    @Override
    public String addJewelInteract(String interacter, String jewelCode,
            String orderCode, String evaluateType) {
        String systemCode = "";
        String type = "";
        if (jewelCode.startsWith(EGeneratePrefix.PRODUCT.getCode())) {
            Product product = productBO.getProduct(jewelCode);
            systemCode = product.getSystemCode();
            type = EInteractType.PRODUCT.getCode();
        } else if (jewelCode.startsWith(EGeneratePrefix.IEWEL.getCode())) {
            Jewel jewel = jewelBO.getJewel(jewelCode);
            systemCode = jewel.getSystemCode();
            type = EInteractType.JEWEL.getCode();
        } else {
            throw new BizException("xn0000", "商品编号有误");
        }
        JewelInteract condition = new JewelInteract();
        condition.setInteracter(interacter);
        condition.setJewelCode(jewelCode);
        condition.setOrderCode(orderCode);
        condition.setType(type);
        condition.setSystemCode(systemCode);
        if (jewelInteractBO.getTotalCount(condition) > 0) {
            throw new BizException("xn0000", "您已评价,无需再次评价");
        }
        JewelInteract data = new JewelInteract();
        data.setInteracter(interacter);
        data.setJewelCode(jewelCode);
        data.setOrderCode(orderCode);
        data.setType(type);
        data.setEvaluateType(evaluateType);
        data.setSystemCode(systemCode);
        return jewelInteractBO.saveJewelInteract(data);
    }

    @Override
    public Object queryJewelInteractPage(int start, int limit,
            JewelInteract condition) {
        XN808325Res res = new XN808325Res();
        Object page = jewelInteractBO.getPaginable(start, limit, condition);
        res.setPage(page);
        String jewelCode = condition.getJewelCode();
        // 购买人次
        Long bugNums = 0L;
        if (jewelCode.startsWith(EGeneratePrefix.PRODUCT.getCode())) {
            ProductOrder poCondition = new ProductOrder();
            poCondition.setProductCode(jewelCode);
            bugNums = productOrderBO.getTotalCount(poCondition);
        } else if (jewelCode.startsWith(EGeneratePrefix.IEWEL.getCode())) {
            JewelRecord jrCondition = new JewelRecord();
            jrCondition.setJewelCode(jewelCode);
            bugNums = jewelRecordBO.getTotalCount(jrCondition);
        } else {
            throw new BizException("xn0000", "商品编号有误");
        }
        res.setBuyNums(bugNums);
        // 好评率
        JewelInteract goodCondition = new JewelInteract();
        goodCondition.setJewelCode(jewelCode);
        goodCondition.setEvaluateType(EevaluateType.GOOD.getCode());
        long goodNums = jewelInteractBO.getTotalCount(goodCondition);
        JewelInteract allCondition = new JewelInteract();
        allCondition.setJewelCode(jewelCode);
        long allNums = jewelInteractBO.getTotalCount(allCondition);
        if (allNums == 0) {
            res.setGoodRate("0.0");
        } else {
            res.setGoodRate((1.0 * goodNums) / allNums + "");
        }
        return res;
    }

    @Override
    public List<JewelInteract> queryJewelInteractList(JewelInteract condition) {
        return jewelInteractBO.queryJewelInteractList(condition);
    }

    @Override
    public JewelInteract getJewelInteract(Long id) {
        if (!jewelInteractBO.isJewelInteractExist(id)) {
            throw new BizException("xn0000", "产品编号不存在");
        }
        return jewelInteractBO.getJewelInteract(id);
    }

}
