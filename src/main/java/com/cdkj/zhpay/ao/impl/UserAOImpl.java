/**
 * @Title UserAOImpl.java 
 * @Package com.xnjr.mall.ao.impl 
 * @Description 
 * @author xieyj  
 * @date 2017年1月15日 下午5:28:15 
 * @version V1.0   
 */
package com.cdkj.zhpay.ao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdkj.zhpay.ao.IUserAO;
import com.cdkj.zhpay.bo.IHzbBO;
import com.cdkj.zhpay.bo.IUserBO;
import com.cdkj.zhpay.domain.Hzb;
import com.cdkj.zhpay.domain.User;
import com.cdkj.zhpay.dto.res.XN808800Res;
import com.cdkj.zhpay.enums.EDiviFlag;
import com.cdkj.zhpay.enums.EUserKind;
import com.cdkj.zhpay.exception.BizException;

/** 
 * @author: xieyj 
 * @since: 2017年1月15日 下午5:28:15 
 * @history:
 */
@Service
public class UserAOImpl implements IUserAO {

    @Autowired
    private IHzbBO hzbBO;

    @Autowired
    private IUserBO userBO;

    @Override
    public XN808800Res getParterStatistics(String userId) {
        User userRes = userBO.getRemoteUser(userId);
        if (!EUserKind.Partner.getCode().equals(userRes.getKind())) {
            throw new BizException("xn0000", "该用户类型不是辖区合伙人，无法查询统计信息");
        }

        List<User> bUsers = null;
        List<String> bUserList = new ArrayList<String>();
        List<User> cUsers = null;
        List<String> cUserList = new ArrayList<String>();
        cUsers = userBO.queryRemoteUserList(userRes.getProvince(),
            userRes.getCity(), userRes.getArea(), EUserKind.F1);
        bUsers = userBO.queryRemoteUserList(userRes.getProvince(),
            userRes.getCity(), userRes.getArea(), EUserKind.F2);
        for (User cUser : cUsers) {
            cUserList.add(cUser.getUserId());
        }
        for (User bUser : bUsers) {
            bUserList.add(bUser.getUserId());
        }

        Hzb hzbHoldCondition = new Hzb();
        hzbHoldCondition.setUserList(cUserList);
        hzbHoldCondition.setStatus(EDiviFlag.EFFECT.getCode());
        long hzbNum = hzbBO.getTotalCount(hzbHoldCondition);
        return new XN808800Res(hzbNum);
    }
}
