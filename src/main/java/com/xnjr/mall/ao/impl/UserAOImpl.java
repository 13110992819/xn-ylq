/**
 * @Title UserAOImpl.java 
 * @Package com.xnjr.mall.ao.impl 
 * @Description 
 * @author xieyj  
 * @date 2017年1月15日 下午5:28:15 
 * @version V1.0   
 */
package com.xnjr.mall.ao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xnjr.mall.ao.IUserAO;
import com.xnjr.mall.bo.IHzbHoldBO;
import com.xnjr.mall.bo.IUserBO;
import com.xnjr.mall.domain.HzbHold;
import com.xnjr.mall.domain.UserExt;
import com.xnjr.mall.dto.res.XN805060Res;
import com.xnjr.mall.dto.res.XN805901Res;
import com.xnjr.mall.dto.res.XN808800Res;
import com.xnjr.mall.enums.EDiviFlag;
import com.xnjr.mall.enums.EUserKind;
import com.xnjr.mall.exception.BizException;

/** 
 * @author: xieyj 
 * @since: 2017年1月15日 下午5:28:15 
 * @history:
 */
@Service
public class UserAOImpl implements IUserAO {

    @Autowired
    private IHzbHoldBO hzbHoldBO;

    @Autowired
    private IUserBO userBO;

    @Override
    public XN808800Res getParterStatistics(String userId) {
        XN805901Res userRes = userBO.getRemoteUser(userId, userId);
        if (!EUserKind.Partner.getCode().equals(userRes.getKind())) {
            throw new BizException("xn0000", "该用户类型不是辖区合伙人，无法查询统计信息");
        }
        UserExt userExt = userRes.getUserExt();
        List<XN805060Res> bUsers = null;
        List<String> bUserList = new ArrayList<String>();
        List<XN805060Res> cUsers = null;
        List<String> cUserList = new ArrayList<String>();
        if (userExt != null) {
            cUsers = userBO.getUserList(userExt.getProvince(),
                userExt.getCity(), userExt.getArea(), EUserKind.F1.getCode());
            bUsers = userBO.getUserList(userExt.getProvince(),
                userExt.getCity(), userExt.getArea(), EUserKind.F2.getCode());
            for (XN805060Res cUser : cUsers) {
                cUserList.add(cUser.getUserId());
            }
            for (XN805060Res bUser : bUsers) {
                bUserList.add(bUser.getUserId());
            }
        }
        HzbHold hzbHoldCondition = new HzbHold();
        hzbHoldCondition.setUserList(cUserList);
        hzbHoldCondition.setStatus(EDiviFlag.EFFECT.getCode());
        long hzbNum = hzbHoldBO.getTotalCount(hzbHoldCondition);
        return new XN808800Res(hzbNum);
    }
}
