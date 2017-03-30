package com.cdkj.zhpay.ao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdkj.zhpay.ao.IHzbAO;
import com.cdkj.zhpay.bo.IAccountBO;
import com.cdkj.zhpay.bo.IHzbBO;
import com.cdkj.zhpay.bo.IHzbMgiftBO;
import com.cdkj.zhpay.bo.IHzbTemplateBO;
import com.cdkj.zhpay.bo.IHzbYyBO;
import com.cdkj.zhpay.bo.ISYSConfigBO;
import com.cdkj.zhpay.bo.IUserBO;
import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.common.DateUtil;
import com.cdkj.zhpay.common.SysConstants;
import com.cdkj.zhpay.common.UserUtil;
import com.cdkj.zhpay.core.OrderNoGenerater;
import com.cdkj.zhpay.domain.Hzb;
import com.cdkj.zhpay.domain.HzbTemplate;
import com.cdkj.zhpay.domain.User;
import com.cdkj.zhpay.dto.res.BooleanRes;
import com.cdkj.zhpay.dto.res.XN002500Res;
import com.cdkj.zhpay.enums.EBizType;
import com.cdkj.zhpay.enums.EBoolean;
import com.cdkj.zhpay.enums.ECurrency;
import com.cdkj.zhpay.enums.EGeneratePrefix;
import com.cdkj.zhpay.enums.EHzbStatus;
import com.cdkj.zhpay.enums.EHzbTemplateStatus;
import com.cdkj.zhpay.enums.EPayType;
import com.cdkj.zhpay.enums.ESysUser;
import com.cdkj.zhpay.enums.ESystemCode;
import com.cdkj.zhpay.exception.BizException;

@Service
public class HzbAOImpl implements IHzbAO {

    @Autowired
    private IHzbTemplateBO hzbTemplateBO;

    @Autowired
    private IHzbBO hzbBO;

    @Autowired
    private ISYSConfigBO sysConfigBO;

    @Autowired
    private IHzbYyBO hzbYyBO;

    @Autowired
    private IHzbMgiftBO hzbMgiftBO;

    @Autowired
    private IUserBO userBO;

    @Autowired
    private IAccountBO accountBO;

    @Override
    @Transactional
    public Object buyHzbOfZH(String userId, String hzbTemplateCode,
            String payType) {
        Object result = null;
        hzbBO.checkBuy(userId);
        HzbTemplate hzbTemplate = hzbTemplateBO.getHzbTemplate(hzbTemplateCode);
        if (!EHzbTemplateStatus.ON.getCode().equals(hzbTemplate.getStatus())) {
            throw new BizException("xn0000", "该汇赚宝模板未上线，不可购买");
        }
        // 判断用户是否实名认证
        User user = userBO.getRemoteUser(userId);
        if (!EBoolean.YES.getCode().equals(user.getIdentityFlag())) {
            throw new BizException("xn0000", "用户未实名认证，请先实名认证");
        }
        // 购买摇钱树
        if (EPayType.YEFR.getCode().equals(payType)) {
            result = buyHzbFRPay(user, hzbTemplate);
        } else if (EPayType.WEIXIN.getCode().equals(payType)) {
            result = buyHzbWXPay(userId, hzbTemplate);
        } else if (EPayType.ALIPAY.getCode().equals(payType)) {
            result = buyHzbZFBPay(userId, hzbTemplate);
        }
        return result;
    }

    @Override
    @Transactional
    public Object buyHzbOfCG(String userId, String hzbTemplateCode,
            String payType) {
        Object result = null;
        hzbBO.checkBuy(userId);
        HzbTemplate hzbTemplate = hzbTemplateBO.getHzbTemplate(hzbTemplateCode);
        if (!EHzbTemplateStatus.ON.getCode().equals(hzbTemplate.getStatus())) {
            throw new BizException("xn0000", "该摇钱树模板未上线，不可购买");
        }
        // 单一币种支付
        User user = userBO.getRemoteUser(userId);
        if (EPayType.YEFR.getCode().equals(payType)) {
            result = buyHzbYEPayCG(user, hzbTemplate);
        } else if (EPayType.WEIXIN.getCode().equals(payType)) {
            result = buyHzbWXPay(userId, hzbTemplate);
        } else if (EPayType.ALIPAY.getCode().equals(payType)) {
            result = buyHzbZFBPay(userId, hzbTemplate);
        }
        return result;
    }

    /**
     * 正汇分润支付
     * @param userRes
     * @param hzbTemplate
     * @return 
     * @create: 2017年2月25日 下午1:20:29 xieyj
     * @history:
     */
    @Transactional
    private Object buyHzbFRPay(User user, HzbTemplate hzbTemplate) {
        // 汇赚宝购买成功
        Hzb hzb = hzbBO.saveHzb(user, hzbTemplate);// 人民币=分润
        // 产生红包
        hzbMgiftBO.generateHzbMgift(hzb, DateUtil.getTodayStart());
        // 单个币种资金划转
        accountBO.doTransferAmountRemote(user.getUserId(),
            ESysUser.SYS_USER_ZHPAY.getCode(), ECurrency.FRB,
            hzbTemplate.getPrice(), EBizType.AJ_GMHZB, "购买汇赚宝",
            UserUtil.getUserMobile(user.getMobile()) + "购买汇赚宝");
        // 分销规则
        distributeAmount(hzbTemplate.getSystemCode(), user.getUserId(),
            hzbTemplate.getPrice());
        return new BooleanRes(true);
    }

    @Transactional
    private Object buyHzbYEPayCG(User user, HzbTemplate hzbTemplate) {
        // 汇赚宝购买成功
        Hzb hzb = hzbBO.saveHzb(user, hzbTemplate);
        // 产生红包
        hzbMgiftBO.generateHzbMgift(hzb, DateUtil.getTodayStart());
        // 单个币种资金划转
        accountBO.doTransferAmountRemote(user.getUserId(),
            ESysUser.SYS_USER_CAIGO.getCode(), ECurrency.CNY,
            hzbTemplate.getPrice(), EBizType.AJ_GMHZB, "购买摇钱树",
            UserUtil.getUserMobile(user.getMobile()) + "购买摇钱树");
        return new BooleanRes(true);
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
    private XN002500Res buyHzbWXPay(String userId, HzbTemplate hzbTemplate) {
        // 生成支付组号
        String payGroup = OrderNoGenerater.generateM(EGeneratePrefix.PAY_GROUP
            .getCode());
        // 落地本地系统消费记录，状态为未支付
        hzbBO.buyHzb(userId, hzbTemplate, payGroup);
        String systemUserId = userBO.getSystemUser(hzbTemplate.getSystemCode());
        XN002500Res res = accountBO.doWeiXinPayRemote(userId, systemUserId,
            hzbTemplate.getPrice(), EBizType.AJ_GMHZB, "购买汇赚宝", "用户购买汇赚宝",
            payGroup);
        return res;
    }

    @Transactional
    private Object buyHzbZFBPay(String userId, HzbTemplate hzbTemplate) {
        throw new BizException("XN000000", "暂不支持支付宝支付");
    }

    /**
     * 支付回调
     * @see com.cdkj.zhpay.ao.IHzbAO#paySuccess(java.lang.String, java.lang.String, java.lang.Long)
     */
    @Override
    @Transactional
    public void paySuccess(String payGroup, String payCode, Long transAmount) {
        // 获取payGroup和未支付的汇赚宝
        List<Hzb> resultList = hzbBO.queryHzbList(payGroup);
        if (CollectionUtils.isEmpty(resultList)) {
            throw new BizException("XN000000", "找不到对应的消费记录");
        }
        Hzb hzb = resultList.get(0);
        if (!transAmount.equals(hzb.getPrice())) {
            throw new BizException("XN000000", "金额校验错误，非正常调用");
        }
        // 更新状态
        hzbBO.refreshPayStatus(hzb.getCode(), EHzbStatus.ACTIVATED.getCode(),
            payCode, transAmount);
        // 正汇才有分配分成
        if (ESystemCode.ZHPAY.getCode().equals(hzb.getSystemCode())) {
            distributeAmount(hzb.getSystemCode(), hzb.getUserId(),
                hzb.getPrice());
        }
        // 产生红包
        hzbMgiftBO.generateHzbMgift(hzb, DateUtil.getTodayStart());
    }

    // 汇赚宝分成:
    // 分成对象:购买用户_已购买汇赚宝的一级二级三级推荐人和所在辖区用户
    private void distributeAmount(String systemCode, String userId, Long price) {
        User ownerUser = userBO.getRemoteUser(userId);
        // 用户分成
        String cUserId = ownerUser.getUserReferee();
        if (StringUtils.isNotBlank(cUserId)) {
            User cUser = userBO.getRemoteUser(userId);
            boolean cHzbResult = hzbBO.isBuyHzb(cUserId);
            if (cHzbResult) {
                userFcAmount(cUser, ownerUser, SysConstants.HZB_CUSER, price);
            }
            // B用户分成
            String bUserId = cUser.getUserReferee();
            if (StringUtils.isNotBlank(bUserId)) {
                User bUser = userBO.getRemoteUser(bUserId);
                boolean bHzbResult = hzbBO.isBuyHzb(bUserId);
                if (bHzbResult) {
                    userFcAmount(bUser, ownerUser, SysConstants.HZB_BUSER,
                        price);
                }
                // A用户分成
                String aUserId = bUser.getUserReferee();
                if (StringUtils.isNotBlank(aUserId)) {
                    User aUser = userBO.getRemoteUser(aUserId);
                    boolean aHzbResult = hzbBO.isBuyHzb(aUserId);
                    if (aHzbResult) {
                        userFcAmount(aUser, ownerUser, SysConstants.HZB_AUSER,
                            price);
                    }
                }
            }
        }
        // 辖区分成
        if (StringUtils.isNotBlank(ownerUser.getProvince())) {
            // 省合伙人
            User provinceUser = userBO.getPartner(ownerUser.getProvince(),
                null, null);
            if (provinceUser != null) {
                areaFcAmount(provinceUser, ownerUser,
                    SysConstants.HZB_PROVINCE, price, "省");
            }
            if (StringUtils.isNotBlank(ownerUser.getCity())) {
                // 市合伙人
                User cityUser = userBO.getPartner(ownerUser.getProvince(),
                    ownerUser.getCity(), null);
                if (cityUser != null) {
                    areaFcAmount(cityUser, ownerUser, SysConstants.HZB_CITY,
                        price, "市");
                }
                if (StringUtils.isNotBlank(ownerUser.getArea())) {
                    // 县合伙人
                    User areaUser = userBO.getPartner(ownerUser.getProvince(),
                        ownerUser.getCity(), ownerUser.getArea());
                    if (areaUser != null) {
                        areaFcAmount(areaUser, ownerUser,
                            SysConstants.HZB_AREA, price, "县");
                    }
                }
            }
        }
    }

    private void userFcAmount(User fcUser, User ownerUser, String sysConstants,
            Long price) {
        Map<String, String> rateMap = sysConfigBO
            .getConfigsMap(ESystemCode.ZHPAY.getCode());
        Double hzbUserRate = Double.valueOf(rateMap.get(sysConstants));
        Long transAmount = Double.valueOf(hzbUserRate * price).longValue();
        if (transAmount != null && transAmount != 0) {
            String fromBizNote = UserUtil.getUserMobile(ownerUser.getMobile())
                    + EBizType.AJ_GMHZBFC.getValue() + ","
                    + UserUtil.getUserMobile(fcUser.getMobile()) + "分成";
            String toBizNote = UserUtil.getUserMobile(ownerUser.getMobile())
                    + EBizType.AJ_GMHZBFC.getValue();
            accountBO.doTransferAmountRemote(ESysUser.SYS_USER_ZHPAY.getCode(),
                fcUser.getUserId(), ECurrency.FRB, transAmount,
                EBizType.AJ_GMHZBFC, fromBizNote, toBizNote);
        }
    }

    private void areaFcAmount(User areaUser, User ownerUser,
            String sysConstants, Long price, String remark) {
        Map<String, String> rateMap = sysConfigBO
            .getConfigsMap(ESystemCode.ZHPAY.getCode());
        Double rate = Double.valueOf(rateMap.get(sysConstants));
        Long transAmount = Double.valueOf(price * rate).longValue();
        if (transAmount != null && transAmount != 0) {
            String fromBizNote = UserUtil.getUserMobile(ownerUser.getMobile())
                    + EBizType.AJ_GMHZBFC.getValue() + "," + remark + "合伙人"
                    + UserUtil.getUserMobile(areaUser.getMobile()) + "分成";
            String toBizNote = UserUtil.getUserMobile(ownerUser.getMobile())
                    + EBizType.AJ_GMHZBFC.getValue() + "," + remark + "合伙人分成";
            accountBO.doTransferAmountRemote(ESysUser.SYS_USER_ZHPAY.getCode(),
                areaUser.getUserId(), ECurrency.FRB, transAmount,
                EBizType.AJ_GMHZBFC, fromBizNote, toBizNote);
        }
    }

    @Override
    @Transactional
    public Object queryDistanceHzbList(String userLatitude,
            String userLongitude, String userId, String deviceNo,
            String companyCode, String systemCode) {
        User yyUser = userBO.getRemoteUser(userId);
        hzbYyBO.checkYyGlobalRule(systemCode, yyUser, deviceNo);
        List<Hzb> hzbList = hzbBO.queryDistanceHzbList(userLatitude,
            userLongitude, companyCode, systemCode);
        this.doGetUsers(hzbList);
        return hzbList;
    }

    @Override
    public Paginable<Hzb> queryHzbPage(int start, int limit, Hzb condition) {
        Paginable<Hzb> page = hzbBO.getPaginable(start, limit, condition);
        if (page != null && CollectionUtils.isNotEmpty(page.getList())) {
            doGetUsers(page.getList());
        }
        return page;
    }

    @Override
    public List<Hzb> queryHzbList(Hzb condition) {
        List<Hzb> results = hzbBO.queryHzbList(condition);
        doGetUsers(results);
        return results;
    }

    @Override
    public Hzb getHzb(String code) {
        Hzb result = hzbBO.getHzb(code);
        doGetUserDetail(result);
        return result;
    }

    @Override
    public List<Hzb> myHzb(String userId) {
        return hzbBO.queryHzbListByUser(userId);
    }

    private void doGetUsers(List<Hzb> hzbList) {
        for (Hzb hzb : hzbList) {
            doGetUserDetail(hzb);
        }
    }

    private void doGetUserDetail(Hzb hzb) {
        if (StringUtils.isNotBlank(hzb.getUserId())) {
            hzb.setUser(userBO.getRemoteUser(hzb.getUserId()));
        }
    }

    @Override
    public void putOnOff(String code) {
        Hzb hzb = hzbBO.getHzb(code);
        String status = null;
        if (EHzbStatus.OFFLINE.getCode().equals(hzb.getStatus())) {
            status = EHzbStatus.ACTIVATED.getCode();
        } else if (EHzbStatus.ACTIVATED.getCode().equals(hzb.getStatus())) {
            status = EHzbStatus.OFFLINE.getCode();
        } else {
            throw new BizException("xn0000", "当前状态无法上下架");
        }
        hzbBO.refreshPutStatus(code, status);
    }

    @Override
    public void doResetRockNumDaily() {
        hzbBO.resetPeriodRockNum();
    }
}
