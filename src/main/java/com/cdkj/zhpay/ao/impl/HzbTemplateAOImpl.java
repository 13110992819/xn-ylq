package com.cdkj.zhpay.ao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdkj.zhpay.ao.IHzbTemplateAO;
import com.cdkj.zhpay.bo.IAccountBO;
import com.cdkj.zhpay.bo.IHzbTemplateBO;
import com.cdkj.zhpay.bo.IHzbBO;
import com.cdkj.zhpay.bo.IHzbMgiftBO;
import com.cdkj.zhpay.bo.ISYSConfigBO;
import com.cdkj.zhpay.bo.IUserBO;
import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.common.SysConstants;
import com.cdkj.zhpay.common.UserUtil;
import com.cdkj.zhpay.core.OrderNoGenerater;
import com.cdkj.zhpay.domain.HzbTemplate;
import com.cdkj.zhpay.domain.Hzb;
import com.cdkj.zhpay.domain.UserExt;
import com.cdkj.zhpay.dto.res.PayBalanceRes;
import com.cdkj.zhpay.dto.res.XN802180Res;
import com.cdkj.zhpay.dto.res.XN805060Res;
import com.cdkj.zhpay.dto.res.XN805901Res;
import com.cdkj.zhpay.enums.EBizType;
import com.cdkj.zhpay.enums.EBoolean;
import com.cdkj.zhpay.enums.ECurrency;
import com.cdkj.zhpay.enums.EDiviFlag;
import com.cdkj.zhpay.enums.EGeneratePrefix;
import com.cdkj.zhpay.enums.EHzbHoldStatus;
import com.cdkj.zhpay.enums.EPayType;
import com.cdkj.zhpay.enums.ESysUser;
import com.cdkj.zhpay.exception.BizException;

@Service
public class HzbTemplateAOImpl implements IHzbTemplateAO {
    @Autowired
    private IHzbTemplateBO hzbTemplateBO;

    @Autowired
    private IHzbBO hzbBO;

    @Autowired
    private IAccountBO accountBO;

    @Autowired
    private ISYSConfigBO sysConfigBO;

    @Autowired
    private IUserBO userBO;

    @Autowired
    private IHzbMgiftBO hzbMgiftBO;

    @Override
    public void editHzb(HzbTemplate data) {
        if (!hzbTemplateBO.isHzbExist(data.getCode())) {
            throw new BizException("xn0000", "汇赚宝记录不存在");
        }
        hzbTemplateBO.refreshHzb(data);
    }

    @Override
    @Transactional
    public Object buyHzb(String userId, String hzbCode, String payType,
            String ip) {
        Object result = null;
        // 判断用户是否实名认证
        XN805901Res userRes = userBO.getRemoteUser(userId, userId);
        if (!EBoolean.YES.getCode().equals(userRes.getIdentityFlag())) {
            throw new BizException("xn0000", "用户未实名认证，请先实名认证");
        }
        // 查询是否已经购买摇钱树
        Hzb condition = new Hzb();
        condition.setUserId(userId);
        condition.setStatus(EDiviFlag.EFFECT.getCode());
        if (hzbBO.getTotalCount(condition) > 0) {
            throw new BizException("xn0000", "您已经购买过汇赚宝");
        }
        // 落地汇赚宝购买记录
        HzbTemplate hzbTemplate = hzbTemplateBO.getHzb(hzbCode);
        if (EPayType.YEFR.getCode().equals(payType)) {
            result = doFRPay(userRes, hzbTemplate);
        } else if (EPayType.WEIXIN.getCode().equals(payType)) {
            result = doWeixinPay(userId, hzbTemplate, ip);
        } else if (EPayType.ALIPAY.getCode().equals(payType)) {
            return null;
        }
        return result;
    }

    /**
     * 分润支付
     * @param userRes
     * @param hzbTemplate
     * @return 
     * @create: 2017年2月25日 下午1:20:29 xieyj
     * @history:
     */
    @Transactional
    private Object doFRPay(XN805901Res userRes, HzbTemplate hzbTemplate) {
        // 余额支付
        PayBalanceRes payRes = accountBO.doFRPay(hzbTemplate.getSystemCode(), userRes,
            ESysUser.SYS_USER.getCode(), hzbTemplate.getPrice(), EBizType.AJ_GMHZB);
        Object result = hzbBO.saveHzbHold(userRes.getUserId(), hzbTemplate,
            payRes.getFrAmount());
        // 分销规则
        distributeAmount(hzbTemplate.getSystemCode(), userRes.getUserId(),
            hzbTemplate.getPrice());
        // 产生红包
        hzbMgiftBO.sendHzbMgift(userRes.getUserId());
        return result;
    }

    /** 
     * 微信支付
     * @param userId
     * @param hzbTemplate
     * @param ip
     * @return 
     * @create: 2017年2月22日 下午4:43:17 xieyj
     * @history: 
     */
    @Transactional
    private XN802180Res doWeixinPay(String userId, HzbTemplate hzbTemplate, String ip) {
        // 生成支付组号
        String payGroup = OrderNoGenerater.generateM(EGeneratePrefix.PAY_GROUP
            .getCode());
        // 落地本地系统消费记录，状态为未支付
        hzbBO.saveHzbHold(userId, hzbTemplate, payGroup);
        XN802180Res res = accountBO.doWeiXinPay(hzbTemplate.getSystemCode(), userId,
            payGroup, EBizType.AJ_GMHZB, hzbTemplate.getPrice(), ip);
        return res;
    }

    @Override
    public void putOnOffHzb(String userId) {
        if (StringUtils.isNotBlank(userId)) {
            // 查询是否已经购买摇钱树
            Hzb condition = new Hzb();
            condition.setUserId(userId);
            List<Hzb> list = hzbBO.queryHzbHoldList(condition);
            if (CollectionUtils.isEmpty(list)) {
                throw new BizException("xn0000", "该用户未购买过汇赚宝");
            }
            Hzb hzb = list.get(0);
            if (EHzbHoldStatus.ACTIVATED.getCode().equals(hzb.getStatus())) {
                hzbBO.refreshStatus(hzb.getId(),
                    EHzbHoldStatus.OFFLINE.getCode());
            } else if (EHzbHoldStatus.OFFLINE.getCode().equals(
                hzb.getStatus())) {
                hzbBO.refreshStatus(hzb.getId(),
                    EHzbHoldStatus.ACTIVATED.getCode());
            }
        }
    }

    @Override
    public Hzb myHzb(String userId) {
        Hzb hzb = null;
        // 查询是否已经购买摇钱树
        Hzb condition = new Hzb();
        condition.setUserId(userId);
        condition.setStatus(EDiviFlag.EFFECT.getCode());
        List<Hzb> list = hzbBO.queryHzbHoldList(condition);
        if (CollectionUtils.isNotEmpty(list)) {
            hzb = list.get(0);
        }
        return hzb;
    }

    /**
     * 支付回调
     * @param payCode
     * @return 
     * @create: 2017年1月6日 下午9:25:22 xieyj
     * @history:
     */
    @Override
    @Transactional
    public void paySuccess(String payGroup, String payCode, Long transAmount) {
        Hzb condition = new Hzb();
        condition.setPayGroup(payGroup);
        List<Hzb> result = hzbBO.queryHzbHoldList(condition);
        if (CollectionUtils.isEmpty(result)) {
            throw new BizException("XN000000", "找不到对应的消费记录");
        }
        if (!transAmount.equals(hzbBO.getTotalAmount(payGroup))) {
            throw new BizException("XN000000", "金额校验错误，非正常调用");
        }
        for (Hzb hzb : result) {
            if (!EHzbHoldStatus.TO_PAY.getCode().equals(hzb.getStatus())) {
                throw new BizException("XN000000", "汇赚宝号：" + hzb.getId()
                        + "已支付，重复回调");
            }
        }
        for (Hzb hzb : result) {
            // 更新状态
            hzbBO.refreshPayStatus(hzb.getId(),
                EHzbHoldStatus.ACTIVATED.getCode(), payCode, transAmount);
            // 分配分成
            distributeAmount(hzb.getSystemCode(), hzb.getUserId(),
                hzb.getPrice());
            // 产生红包
            hzbMgiftBO.sendHzbMgift(hzb.getUserId());
        }
    }

    // 汇赚宝分成:
    // 1、数据准备
    // 2、计算分成:针对用户_已购买汇赚宝的一级二级推荐人和所在辖区用户
    private void distributeAmount(String systemCode, String userId, Long price) {
        XN805901Res ownerUser = userBO.getRemoteUser(userId, userId);
        // 用户分成
        String cUserId = ownerUser.getUserReferee();
        if (StringUtils.isNotBlank(cUserId)) {
            XN805901Res cUser = userBO.getRemoteUser(cUserId, cUserId);
            boolean cHzbResult = hzbBO.isHzbHoldExistByUser(cUserId);
            if (cHzbResult) {
                this.userFcAmount(systemCode, cUser, ownerUser,
                    SysConstants.HZB_CUSER, price);
            }
            // B用户分成
            String bUserId = cUser.getUserReferee();
            if (StringUtils.isNotBlank(bUserId)) {
                XN805901Res bUser = userBO.getRemoteUser(bUserId, bUserId);
                boolean bHzbResult = hzbBO.isHzbHoldExistByUser(bUserId);
                if (bHzbResult) {
                    this.userFcAmount(systemCode, bUser, ownerUser,
                        SysConstants.HZB_BUSER, price);
                }
                // A用户分成
                String aUserId = bUser.getUserReferee();
                if (StringUtils.isNotBlank(aUserId)) {
                    XN805901Res aUser = userBO.getRemoteUser(aUserId, aUserId);
                    boolean aHzbResult = hzbBO
                        .isHzbHoldExistByUser(aUserId);
                    if (aHzbResult) {
                        this.userFcAmount(systemCode, aUser, ownerUser,
                            SysConstants.HZB_AUSER, price);
                    }
                }
            }
        }
        // 辖区分成
        UserExt userExt = ownerUser.getUserExt();
        if (userExt != null) {
            if (StringUtils.isNotBlank(userExt.getProvince())) {
                // 省合伙人
                XN805060Res provinceUser = userBO.getPartnerUserInfo(
                    userExt.getProvince(), null, null);
                if (provinceUser != null) {
                    areaFcAmount(systemCode, provinceUser, ownerUser,
                        SysConstants.HZB_PROVINCE, price, "省");
                }
                if (StringUtils.isNotBlank(userExt.getCity())) {
                    // 市合伙人
                    XN805060Res cityUser = userBO.getPartnerUserInfo(
                        userExt.getProvince(), userExt.getCity(), null);
                    if (cityUser != null) {
                        areaFcAmount(systemCode, cityUser, ownerUser,
                            SysConstants.HZB_CITY, price, "市");
                    }
                    if (StringUtils.isNotBlank(userExt.getArea())) {
                        // 县合伙人
                        XN805060Res areaUser = userBO.getPartnerUserInfo(
                            userExt.getProvince(), userExt.getCity(),
                            userExt.getArea());
                        if (areaUser != null) {
                            areaFcAmount(systemCode, areaUser, ownerUser,
                                SysConstants.HZB_AREA, price, "县");
                        }
                    }
                }
            }
        }
    }

    private void userFcAmount(String systemCode, XN805901Res fcUser,
            XN805901Res ownerUser, String sysConstants, Long price) {
        Map<String, String> rateMap = sysConfigBO.getConfigsMap(systemCode,
            null);
        Double hzbUserRate = Double.valueOf(rateMap.get(sysConstants));
        Long transAmount = Double.valueOf(hzbUserRate * price).longValue();
        if (transAmount != null && transAmount != 0) {
            String fromBizNote = UserUtil.getUserMobile(ownerUser.getMobile())
                    + EBizType.AJ_GMHZBFC.getValue() + ","
                    + UserUtil.getUserMobile(fcUser.getMobile()) + "分成";
            String toBizNote = UserUtil.getUserMobile(ownerUser.getMobile())
                    + EBizType.AJ_GMHZBFC.getValue();
            accountBO.doTransferFcBySystem(systemCode, fcUser.getUserId(),
                ECurrency.FRB.getCode(), transAmount,
                EBizType.AJ_GMHZBFC.getCode(), fromBizNote, toBizNote);
        }
    }

    private void areaFcAmount(String systemCode, XN805060Res areaUser,
            XN805901Res ownerUser, String sysConstants, Long price,
            String remark) {
        Map<String, String> rateMap = sysConfigBO.getConfigsMap(systemCode,
            null);
        Double rate = Double.valueOf(rateMap.get(sysConstants));
        Long transAmount = Double.valueOf(price * rate).longValue();
        if (transAmount != null && transAmount != 0) {
            String fromBizNote = UserUtil.getUserMobile(ownerUser.getMobile())
                    + EBizType.AJ_GMHZBFC.getValue() + "," + remark + "合伙人"
                    + UserUtil.getUserMobile(areaUser.getMobile()) + "分成";
            String toBizNote = UserUtil.getUserMobile(ownerUser.getMobile())
                    + EBizType.AJ_GMHZBFC.getValue() + "," + remark + "合伙人分成";
            accountBO.doTransferFcBySystem(systemCode, areaUser.getUserId(),
                ECurrency.FRB.getCode(), transAmount,
                EBizType.AJ_GMHZBFC.getCode(), fromBizNote, toBizNote);
        }
    }

    @Override
    public Paginable<HzbTemplate> queryHzbPage(int start, int limit, HzbTemplate condition) {
        return hzbTemplateBO.getPaginable(start, limit, condition);
    }

    @Override
    public List<HzbTemplate> queryHzbList(HzbTemplate condition) {
        return hzbTemplateBO.queryHzbList(condition);
    }

    @Override
    public HzbTemplate getHzb(String code) {
        return hzbTemplateBO.getHzb(code);
    }
}
