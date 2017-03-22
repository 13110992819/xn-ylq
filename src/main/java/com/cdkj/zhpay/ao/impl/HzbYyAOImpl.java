package com.cdkj.zhpay.ao.impl;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdkj.zhpay.ao.IHzbYyAO;
import com.cdkj.zhpay.bo.IAccountBO;
import com.cdkj.zhpay.bo.IHzbBO;
import com.cdkj.zhpay.bo.IHzbYyBO;
import com.cdkj.zhpay.bo.ISYSConfigBO;
import com.cdkj.zhpay.bo.IUserBO;
import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.common.SysConstants;
import com.cdkj.zhpay.common.UserUtil;
import com.cdkj.zhpay.domain.Hzb;
import com.cdkj.zhpay.domain.HzbYy;
import com.cdkj.zhpay.domain.User;
import com.cdkj.zhpay.domain.UserExt;
import com.cdkj.zhpay.dto.res.XN805060Res;
import com.cdkj.zhpay.dto.res.XN805901Res;
import com.cdkj.zhpay.dto.res.XN808460Res;
import com.cdkj.zhpay.enums.EBizType;
import com.cdkj.zhpay.enums.ECurrency;
import com.cdkj.zhpay.enums.EPrizeCurrency;
import com.cdkj.zhpay.enums.ESystemCode;
import com.cdkj.zhpay.exception.BizException;

@Service
public class HzbYyAOImpl implements IHzbYyAO {
    @Autowired
    private IHzbBO hzbBO;

    @Autowired
    private IHzbYyBO hzbYyBO;

    @Autowired
    private ISYSConfigBO sysConfigBO;

    @Autowired
    private IAccountBO accountBO;

    @Autowired
    private IUserBO userBO;

    /**
     * A、能不能摇：树是否失效；摇一摇全局规则校验
     * B、业务逻辑：树不同，摇一摇整个逻辑是不同的
     * 菜狗的摇钱树
     * 1、摇到什么并记录摇到结果
     * 2、刷新对应摇钱树生命值
     * 3、兑现奖励
     * 正汇的摇钱树
     * 1、摇到什么并记录摇到结果
     * 2、刷新对应摇钱树生命值
     * 3、兑现奖励：正汇系统摇到红包时，将促发分销规则;其他币时，单纯给摇的人
     * 
     */
    @Override
    @Transactional
    public XN808460Res doHzbYy(String userId, String hzbCode, String deviceNo) {
        // A、能不能摇：树是否失效；人/设备是否超过次数
        User yyUser = userBO.getRemoteUser(userId);
        Hzb hzb = hzbBO.checkActivated(hzbCode);
        hzbYyBO.checkYyGlobalRule(hzb, yyUser, deviceNo);
        // B、树不同，摇一摇整个逻辑是不同的
        if (ESystemCode.ZHPAY.getCode().equalsIgnoreCase(hzb.getSystemCode())) {
            return doYyByZH(yyUser, hzb, deviceNo);
        } else if (ESystemCode.Caigo.getCode().equalsIgnoreCase(
            hzb.getSystemCode())) {
            return doYyByCG(yyUser, hzb, deviceNo);
        } else {
            throw new BizException("xn0000", "所选摇钱树不属于本系统");
        }

    }

    private XN808460Res doYyByCG(User yyUser, Hzb hzb, String deviceNo) {
        // 1、摇到什么并记录摇到结果
        XN808460Res prize = hzbYyBO.calculatePrizeByCG();
        hzbYyBO.saveHzbYy(prize, yyUser, hzb, deviceNo);
        // 2、刷新对应摇钱树生命值
        hzbBO.refreshYy(hzb, prize);
        // 3、平台兑现奖励
        // 兑现摇的人

        // 兑现树主人

        return prize;
    }

    private XN808460Res doYyByZH(User yyUser, Hzb hzb, String deviceNo) {
        // 1、摇到什么并记录摇到结果
        XN808460Res prize = hzbYyBO.calculatePrizeByZH(yyUser);
        hzbYyBO.saveHzbYy(prize, yyUser, hzb, deviceNo);
        // 2、刷新对应摇钱树生命值
        hzbBO.refreshYy(hzb, prize);
        // 3、特殊处理：正汇系统摇到红包时，将促发分销规则
        String currency = prize.getYyCurrency();
        if (EPrizeCurrency.ZH_GWB.getCode().equals(currency)
                || EPrizeCurrency.ZH_QBB.getCode().equals(currency)) {
            // 兑现摇的人
        } else if (EPrizeCurrency.ZH_HBB.getCode().equals(currency)) {
            // 促发分销规则
        }
        return prize;
    }

    // 汇赚宝分成:
    // 1、数据准备
    // 2、计算分成:针对用户_已购买汇赚宝的一级二级推荐人和所在辖区用户
    private void distributeAmount(String systemCode, XN805901Res yyUser,
            String userId) {
        // C用户摇一摇分成
        XN805901Res cUser = userFcAmount(systemCode, yyUser, userId,
            SysConstants.YY_CUSER);
        // B用户摇一摇分成
        String bUserId = cUser.getUserReferee();
        boolean bHzbResult = hzbBO.isHzbHoldExistByUser(bUserId);
        if (StringUtils.isNotBlank(bUserId) && bHzbResult) {
            XN805901Res bUser = this.userFcAmount(systemCode, yyUser, bUserId,
                SysConstants.YY_BUSER);
            String aUserId = bUser.getUserReferee();
            boolean aHzbResult = hzbBO.isHzbHoldExistByUser(aUserId);
            if (StringUtils.isNotBlank(aUserId) && aHzbResult) {
                // A用户摇一摇分成
                userFcAmount(systemCode, yyUser, aUserId, SysConstants.YY_AUSER);
            }
        }
        // 辖区分成
        UserExt userExt = cUser.getUserExt();
        if (userExt != null) {
            if (StringUtils.isNotBlank(userExt.getProvince())) {
                // 省合伙人
                XN805060Res provinceUser = userBO.getPartnerUserInfo(
                    userExt.getProvince(), null, null);
                if (provinceUser != null) {
                    areaFcAmount(systemCode, yyUser, provinceUser,
                        SysConstants.YY_PROVINCE, "省");
                }
                if (StringUtils.isNotBlank(userExt.getCity())) {
                    // 市合伙人
                    XN805060Res cityUser = userBO.getPartnerUserInfo(
                        userExt.getProvince(), userExt.getCity(), null);
                    if (cityUser != null) {
                        areaFcAmount(systemCode, yyUser, cityUser,
                            SysConstants.YY_CITY, "市");
                    }
                    if (StringUtils.isNotBlank(userExt.getArea())) {
                        // 县合伙人
                        XN805060Res areaRes = userBO.getPartnerUserInfo(
                            userExt.getProvince(), userExt.getCity(),
                            userExt.getArea());
                        if (areaRes != null) {
                            areaFcAmount(systemCode, yyUser, areaRes,
                                SysConstants.YY_AREA, "县");
                        }
                    }
                }
            }
        }
    }

    private User userFcAmount(String systemCode, XN805901Res yyUser,
            String userId, String sysConstants) {
        // 分销规则
        Map<String, String> rateMap = sysConfigBO.getConfigsMap(systemCode);
        User refUser = userBO.getRemoteUser(userId);
        Double fc = Double.valueOf(rateMap.get(sysConstants));
        Long fcAmount = Double.valueOf(fc * SysConstants.AMOUNT_RADIX)
            .longValue();
        String toBizNote = UserUtil.getUserMobile(yyUser.getMobile())
                + EBizType.AJ_YYFC.getValue();
        String fromBizNote = toBizNote + ","
                + UserUtil.getUserMobile(refUser.getMobile()) + "分成";
        accountBO.doTransferFcBySystem(systemCode, userId,
            ECurrency.HBYJ.getCode(), fcAmount, EBizType.AJ_YYFC.getCode(),
            fromBizNote, toBizNote);
        return refUser;
    }

    private void areaFcAmount(String systemCode, XN805901Res yyUser,
            XN805060Res areaUser, String sysConstants, String remark) {
        // 分销规则
        Map<String, String> rateMap = sysConfigBO.getConfigsMap(systemCode);
        Double fc = Double.valueOf(rateMap.get(sysConstants));
        Long fcAmount = Double.valueOf(fc * SysConstants.AMOUNT_RADIX)
            .longValue();
        String toBizNote = UserUtil.getUserMobile(yyUser.getMobile())
                + EBizType.AJ_YYFC.getValue();
        String fromBizNote = toBizNote + "," + remark + "合伙人"
                + UserUtil.getUserMobile(areaUser.getMobile()) + "分成";
        toBizNote = toBizNote + "," + remark + "合伙人分成";
        accountBO.doTransferFcBySystem(systemCode, areaUser.getUserId(),
            ECurrency.HBYJ.getCode(), fcAmount, EBizType.AJ_YYFC.getCode(),
            fromBizNote, toBizNote);
    }

    @Override
    public Paginable<HzbYy> queryHzbYyPage(int start, int limit, HzbYy condition) {
        return hzbYyBO.getPaginable(start, limit, condition);
    }
}
