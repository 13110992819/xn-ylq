/**
 * @Title AccountAOImpl.java 
 * @Package com.xnjr.mall.ao.impl 
 * @Description 
 * @author xieyj  
 * @date 2017年1月4日 下午10:02:38 
 * @version V1.0   
 */
package com.cdkj.zhpay.ao.impl;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdkj.zhpay.ao.IAccountAO;
import com.cdkj.zhpay.bo.IAccountBO;
import com.cdkj.zhpay.bo.ISYSConfigBO;
import com.cdkj.zhpay.common.SysConstants;
import com.cdkj.zhpay.dto.res.XN802503Res;
import com.cdkj.zhpay.enums.EBizType;
import com.cdkj.zhpay.enums.EBoolean;
import com.cdkj.zhpay.enums.ECurrency;
import com.cdkj.zhpay.exception.BizException;

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
     * @see com.cdkj.zhpay.ao.IAccountAO#exchangeAmount(java.lang.String, java.lang.String, java.lang.String, java.lang.Long)
     */
    @Override
    public void approveExchangeAmount(String systemCode, String code,
            String bizType, String approveResult, String approver,
            String approveNote) {
        String rate = null;
        String ckey = null;
        if (EBizType.AJ_HB2FR.getCode().equals(bizType)) {
            ckey = SysConstants.HB2FR;
        } else if (EBizType.AJ_HBYJ2FR.getCode().equals(bizType)) {
            ckey = SysConstants.HBYJ2FR;
        } else if (EBizType.AJ_HBYJ2GXJL.getCode().equals(bizType)) {
            ckey = SysConstants.HBYJ2GXJL;
        }
        rate = sysConfigBO.getConfigValue(systemCode, null, null, ckey);
        if (StringUtils.isBlank(rate)) {
            throw new BizException("xn000000", "兑换比例不存在，请检查钱包汇率规则参数");
        }
        if (rate.equals(EBoolean.NO.getCode())) {
            throw new BizException("xn000000", "兑换比例为0，不能兑换");
        }
        accountBO.doExchangeAmount(systemCode, code, rate, approveResult,
            approver, approveNote);
    }

    /** 
     * @see com.cdkj.zhpay.ao.IAccountAO#getBalanceByUser(java.lang.String, java.lang.String)
     */
    @Override
    public Long getBalanceByUser(String systemCode, String userId) {
        Long balance = 0L;
        Map<String, String> rateMap = sysConfigBO.getConfigsMap(systemCode,
            null);
        // 查询用户贡献值账户
        XN802503Res gxjlAccount = accountBO.getAccountByUserId(systemCode,
            userId, ECurrency.GXJL.getCode());
        // 查询用户分润账户
        XN802503Res frAccount = accountBO.getAccountByUserId(systemCode,
            userId, ECurrency.FRB.getCode());
        Double gxjl2cny = Double.valueOf(rateMap.get(SysConstants.GXJL2CNY));
        Double fr2cny = Double.valueOf(rateMap.get(SysConstants.FR2CNY));
        balance = Double.valueOf(gxjlAccount.getAmount() / gxjl2cny)
            .longValue()
                + Double.valueOf(frAccount.getAmount() / fr2cny).longValue();
        return balance;
    }
}
