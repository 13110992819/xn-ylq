/**
 * @Title AccountAOImpl.java 
 * @Package com.xnjr.mall.ao.impl 
 * @Description 
 * @author xieyj  
 * @date 2017年1月4日 下午10:02:38 
 * @version V1.0   
 */
package com.xnjr.mall.ao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xnjr.mall.ao.IAccountAO;
import com.xnjr.mall.bo.IAccountBO;

/** 
 * @author: xieyj 
 * @since: 2017年1月4日 下午10:02:38 
 * @history:
 */
@Service
public class AccountAOImpl implements IAccountAO {

    @Autowired
    IAccountBO accountBO;

    /**
     * @see com.xnjr.mall.ao.IAccountAO#exchangeAmount(java.lang.String, java.lang.String, java.lang.String, java.lang.Long)
     */
    @Override
    public void approveExchange(String systemCode, String code,
            String approveResult, String approver, String approveNote) {
        // accountBO.doExchangeAmount(systemCode, fromAccountNumber,
        // toAccountNumber, transAmount, rate, bizType, bizNote);
    }
}
