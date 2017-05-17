package com.cdkj.zhpay.ao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
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
import com.cdkj.zhpay.common.AmountUtil;
import com.cdkj.zhpay.common.SysConstants;
import com.cdkj.zhpay.common.UserUtil;
import com.cdkj.zhpay.domain.Hzb;
import com.cdkj.zhpay.domain.HzbYy;
import com.cdkj.zhpay.domain.SYSConfig;
import com.cdkj.zhpay.domain.User;
import com.cdkj.zhpay.dto.res.XN615120Res;
import com.cdkj.zhpay.enums.EBizType;
import com.cdkj.zhpay.enums.ECurrency;
import com.cdkj.zhpay.enums.ESysUser;
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
     */
    @Override
    @Transactional
    public XN615120Res doHzbYy(String userId, String hzbCode, String deviceNo) {
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

    // 菜狗摇出币种 backAmount1=人民币 backAmount2=菜狗币 backAmount3=积分币
    private XN615120Res doYyByCG(User yyUser, Hzb hzb, String deviceNo) {
        // 1、摇到什么并记录摇到结果

        XN615120Res prize = hzbYyBO.calculatePrizeByCG(hzb);
        String yyCode = hzbYyBO.saveHzbYy(prize, yyUser, hzb, deviceNo,
            prize.getYyCurrency(), prize.getYyFcAmount());
        // 2、刷新对应摇钱树生命值
        hzbBO.refreshYyAmount(hzb, prize);
        // 3、平台兑现奖励
        // 兑现摇的人
        ECurrency currency = ECurrency.getECurrency(prize.getYyCurrency());
        accountBO.doTransferAmountRemote(ESysUser.SYS_USER_CAIGO.getCode(),
            yyUser.getUserId(), currency, prize.getYyAmount(),
            EBizType.AJ_YYJL, "摇一摇奖励发放", "摇一摇奖励获得", yyCode);

        SYSConfig sysConfig = sysConfigBO.getSYSConfig(SysConstants.YY_FC_RATE,
            yyUser.getSystemCode());
        Long fcAmount = AmountUtil.mul(prize.getYyAmount(),
            Double.valueOf(sysConfig.getCvalue()));
        // 兑现树主人
        accountBO.doTransferAmountRemote(ESysUser.SYS_USER_CAIGO.getCode(),
            hzb.getUserId(), currency, fcAmount, EBizType.AJ_YYFC, "摇一摇分成发放",
            "摇一摇分成获得", yyCode);
        return prize;
    }

    private XN615120Res doYyByZH(User yyUser, Hzb hzb, String deviceNo) {
        // 1、确定摇到什么
        XN615120Res prize = hzbYyBO.calculatePrizeByZH(hzb, yyUser);

        // 2、刷新对应摇钱树生命值
        hzbBO.refreshYyTimes(hzb, prize);
        // 3、特殊处理：正汇系统摇到红包时，将促发分销规则
        String currency = prize.getYyCurrency();
        Map<String, String> rateMap = sysConfigBO
            .getConfigsMap(ESystemCode.ZHPAY.getCode());
        Long ownerFcAmount = 0L;
        if (ECurrency.ZH_HBB.getCode().equals(currency)) {
            // C用户树主人分成
            String cAmount = rateMap.get(SysConstants.YY_CUSER);
            ownerFcAmount = Double.valueOf(
                Double.valueOf(cAmount) * SysConstants.AMOUNT_RADIX)
                .longValue();
        }
        // 4、记录摇到结果
        String yyCode = hzbYyBO.saveHzbYy(prize, yyUser, hzb, deviceNo,
            ECurrency.ZH_HBYJ.getCode(), ownerFcAmount);
        // 5、兑现摇的人
        ECurrency yyEurrency = ECurrency.getECurrency(currency);
        accountBO.doTransferAmountRemote(ESysUser.SYS_USER_ZHPAY.getCode(),
            yyUser.getUserId(), yyEurrency, prize.getYyAmount(),
            EBizType.AJ_YYJL, "摇一摇奖励发放", "摇一摇奖励获得", yyCode);
        // 分销
        fcAmount(hzb.getUserId(), rateMap, ownerFcAmount, yyCode);
        return prize;
    }

    // 汇赚宝分成:
    // 1、数据准备
    // 2、计算分成:针对用户：树主人已经树主人已购买汇赚宝的一级二级推荐人和所在辖区用户
    private void fcAmount(String hzbOwner, Map<String, String> rateMap,
            Long ownerFcAmount, String yyCode) {
        // 树主人分成区分
        accountBO.doTransferAmountRemote(ESysUser.SYS_USER_ZHPAY.getCode(),
            hzbOwner, ECurrency.ZH_HBYJ, ownerFcAmount, EBizType.AJ_YYFC,
            "摇一摇分成发放", "摇一摇分成获得", yyCode);
        // B用户分成
        User cHzbUser = userBO.getRemoteUser(hzbOwner);
        String bUserId = cHzbUser.getUserReferee();
        boolean bcheck = hzbBO.isBuyHzb(bUserId);
        if (StringUtils.isNotBlank(bUserId) && bcheck) {
            String bAmount = rateMap.get(SysConstants.YY_BUSER);
            userRefFcAmount(bUserId, bAmount, yyCode);
            // A用户分成
            User bUser = userBO.getRemoteUser(bUserId);
            String aUserId = bUser.getUserReferee();
            boolean acheck = hzbBO.isBuyHzb(aUserId);
            if (StringUtils.isNotBlank(aUserId) && acheck) {
                String aAmount = rateMap.get(SysConstants.YY_AUSER);
                userRefFcAmount(aUserId, aAmount, yyCode);
            }
        }

        // 辖区分成
        if (StringUtils.isNotBlank(cHzbUser.getProvince())) {
            // 省合伙人
            User provinceUser = userBO.getPartner(cHzbUser.getProvince(), null,
                null);
            if (provinceUser != null) {
                areaFcAmount(rateMap, cHzbUser, provinceUser,
                    SysConstants.YY_PROVINCE, "省", yyCode);
            }
            if (StringUtils.isNotBlank(cHzbUser.getCity())) {
                // 市合伙人
                User cityUser = userBO.getPartner(cHzbUser.getProvince(),
                    cHzbUser.getCity(), null);
                if (cityUser != null) {
                    areaFcAmount(rateMap, cHzbUser, cityUser,
                        SysConstants.YY_CITY, "市", yyCode);
                }
                if (StringUtils.isNotBlank(cHzbUser.getArea())) {
                    // 县合伙人
                    User areaRes = userBO.getPartner(cHzbUser.getProvince(),
                        cHzbUser.getCity(), cHzbUser.getArea());
                    if (areaRes != null) {
                        areaFcAmount(rateMap, cHzbUser, areaRes,
                            SysConstants.YY_AREA, "县", yyCode);
                    }
                }
            }

        }
    }

    private void userRefFcAmount(String refUserId, String configAmount,
            String yyCode) {
        Double fc = Double.valueOf(configAmount);
        Long fcAmount = Double.valueOf(fc * SysConstants.AMOUNT_RADIX)
            .longValue();
        accountBO.doTransferAmountRemote(ESysUser.SYS_USER_ZHPAY.getCode(),
            refUserId, ECurrency.ZH_HBYJ, fcAmount, EBizType.AJ_YYFC_REF,
            "摇一摇分成发放", "推荐人摇一摇分成获得", yyCode);
    }

    private void areaFcAmount(Map<String, String> rateMap, User hzbUser,
            User areaUser, String sysConstants, String remark, String yyCode) {
        // 分销规则
        Double fc = Double.valueOf(rateMap.get(sysConstants));
        Long fcAmount = Double.valueOf(fc * SysConstants.AMOUNT_RADIX)
            .longValue();
        String toBizNote = UserUtil.getUserMobile(hzbUser.getMobile()) + "的"
                + EBizType.AJ_YYFC.getValue();
        String fromBizNote = toBizNote + "," + remark + "合伙人分成";
        toBizNote = toBizNote + "," + remark + "合伙人分成";
        accountBO.doTransferAmountRemote(ESysUser.SYS_USER_ZHPAY.getCode(),
            areaUser.getUserId(), ECurrency.ZH_HBYJ, fcAmount,
            EBizType.AJ_YYFC, fromBizNote, toBizNote, yyCode);
    }

    @Override
    public Paginable<HzbYy> queryHzbYyPage(int start, int limit, HzbYy condition) {
        Paginable<HzbYy> page = hzbYyBO.getPaginable(start, limit, condition);
        if (page != null && CollectionUtils.isNotEmpty(page.getList())) {
            List<HzbYy> list = page.getList();
            for (HzbYy hzbYy : list) {
                User user = userBO.getRemoteUser(hzbYy.getUserId());
                hzbYy.setUser(user);
                Hzb hzb = hzbBO.getHzb(hzbYy.getHzbCode());
                hzbYy.setHzb(hzb);
            }
        }
        return page;
    }
}
