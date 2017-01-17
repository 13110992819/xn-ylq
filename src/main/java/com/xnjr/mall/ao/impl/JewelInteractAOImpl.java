package com.xnjr.mall.ao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xnjr.mall.ao.IJewelInteractAO;
import com.xnjr.mall.bo.IJewelBO;
import com.xnjr.mall.bo.IJewelInteractBO;
import com.xnjr.mall.bo.IProductBO;
import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.domain.Jewel;
import com.xnjr.mall.domain.JewelInteract;
import com.xnjr.mall.domain.Product;
import com.xnjr.mall.enums.EGeneratePrefix;
import com.xnjr.mall.enums.EInteractType;
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
    IJewelBO jewelBO;

    @Override
    public String addJewelInteract(String interacter, String jewelCode) {
        if (interacter == null && jewelCode == null) {
            throw new BizException("xn0000", "添加不能为空");
        }
        String systemCode = "";
        if (jewelCode.startsWith(EGeneratePrefix.PRODUCT.getCode())) {
            Product product = productBO.getProduct(jewelCode);
            systemCode = product.getSystemCode();
        } else if (jewelCode.startsWith(EGeneratePrefix.IEWEL.getCode())) {
            Jewel jewel = jewelBO.getJewel(jewelCode);
            systemCode = jewel.getSystemCode();
        } else {
            throw new BizException("xn0000", "商品编号有误");
        }
        // JewelInteract condition = new JewelInteract();
        // condition.setInteracter(interacter);
        // condition.setJewelCode(jewelCode);
        // condition.setType(EInteractType.HAOPING.getCode());
        // condition.setSystemCode(systemCode);
        // if (jewelInteractBO.getTotalCount(condition) > 0) {
        // throw new BizException("xn0000", "该用户已经评价过该产品");
        // }

        JewelInteract data = new JewelInteract();
        data.setInteracter(interacter);
        data.setJewelCode(jewelCode);
        data.setSystemCode(systemCode);
        data.setType(EInteractType.HAOPING.getCode());
        return jewelInteractBO.saveJewelInteract(data);
    }

    @Override
    public void editJewelInteract(JewelInteract data) {
        if (!jewelInteractBO.isJewelInteractExist(data.getId())) {
            throw new BizException("xn0000", "产品编号不存在");
        }
        jewelInteractBO.refreshJewelInteract(data);
    }

    @Override
    public void dropJewelInteract(Long id) {
        if (!jewelInteractBO.isJewelInteractExist(id)) {
            throw new BizException("xn0000", "产品编号不存在");
        }
        jewelInteractBO.removeJewelInteract(id);
    }

    @Override
    public Paginable<JewelInteract> queryJewelInteractPage(int start,
            int limit, JewelInteract condition) {
        return jewelInteractBO.getPaginable(start, limit, condition);
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
