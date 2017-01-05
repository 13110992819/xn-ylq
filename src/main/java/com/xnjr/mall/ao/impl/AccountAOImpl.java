/**
 * @Title AccountAOImpl.java 
 * @Package com.xnjr.mall.ao.impl 
 * @Description 
 * @author xieyj  
 * @date 2017年1月4日 下午10:02:38 
 * @version V1.0   
 */
package com.xnjr.mall.ao.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xnjr.mall.ao.IAccountAO;
import com.xnjr.mall.bo.IAccountBO;
import com.xnjr.mall.bo.ISYSConfigBO;
import com.xnjr.mall.common.SysConstants;
import com.xnjr.mall.enums.EBizType;
import com.xnjr.mall.enums.ECategoryType;
import com.xnjr.mall.exception.BizException;

/** 
 * @author: xieyj 
 * @since: 2017年1月4日 下午10:02:38 
 * @history:
 */
@Service
public class AccountAOImpl implements IAccountAO {

    @Autowired
    IAccountBO accountBO;

    @Autowired
    ISYSConfigBO sysConfigBO;

    /**
     * @see com.xnjr.mall.ao.IAccountAO#exchangeAmount(java.lang.String, java.lang.String, java.lang.String, java.lang.Long)
     */
    @Override
    public void approveExchangeAmount(String systemCode, String code,
            String bizType, String approveResult, String approver,
            String approveNote) {
        String rate = null;
        String type = ECategoryType.QBHL.getCode();
        String ckey = null;
        if (EBizType.AJ_HB2FR.getCode().equals(bizType)) {
            ckey = SysConstants.HB2FR;
        } else if (EBizType.AJ_HBYJ2FR.getCode().equals(bizType)) {
            ckey = SysConstants.HBYJ2FR;
        } else if (EBizType.AJ_HBYJ2GXJL.getCode().equals(bizType)) {
            ckey = SysConstants.HBYJ2GXJL;
        }
        rate = sysConfigBO.getConfigValue(systemCode, type, null, ckey);
        if (StringUtils.isBlank(rate)) {
            throw new BizException("xn000000", "兑换比例不存在，请检查钱包汇率规则参数");
        }
        accountBO.doExchangeAmount(systemCode, code, rate, approveResult,
            approver, approveNote);
    }
}
