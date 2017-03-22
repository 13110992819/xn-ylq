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
import com.cdkj.zhpay.common.PropertiesUtil;
import com.cdkj.zhpay.common.SysConstants;
import com.cdkj.zhpay.common.UserUtil;
import com.cdkj.zhpay.core.OrderNoGenerater;
import com.cdkj.zhpay.domain.Hzb;
import com.cdkj.zhpay.domain.HzbTemplate;
import com.cdkj.zhpay.domain.User;
import com.cdkj.zhpay.domain.UserExt;
import com.cdkj.zhpay.dto.res.BooleanRes;
import com.cdkj.zhpay.dto.res.XN802180Res;
import com.cdkj.zhpay.enums.EBizType;
import com.cdkj.zhpay.enums.EBoolean;
import com.cdkj.zhpay.enums.ECurrency;
import com.cdkj.zhpay.enums.EGeneratePrefix;
import com.cdkj.zhpay.enums.EHzbStatus;
import com.cdkj.zhpay.enums.EHzbTemplateStatus;
import com.cdkj.zhpay.enums.EPayType;
import com.cdkj.zhpay.enums.ESysUser;
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
        // 判断用户是否实名认证
        User user = userBO.getRemoteUser(userId);
        if (!EBoolean.YES.getCode().equals(user.getIdentityFlag())) {
            throw new BizException("xn0000", "用户未实名认证，请先实名认证");
        }
        hzbBO.checkBuy(userId);
        HzbTemplate hzbTemplate = hzbTemplateBO.getHzbTemplate(hzbTemplateCode);
        if (!EHzbTemplateStatus.ON.getCode().equals(hzbTemplate.getStatus())) {
            throw new BizException("xn0000", "该汇赚宝模板未上线，不可购买");
        }
        // 购买摇钱树
        if (ECurrency.CNY.getCode().equals(hzbTemplate.getCurrency())) {
            if (EPayType.YEFR.getCode().equals(payType)) {
                result = doZHFRPay(user, hzbTemplate);
            } else if (EPayType.WEIXIN.getCode().equals(payType)) {
                result = doWeixinPay(userId, hzbTemplate);
            } else if (EPayType.ALIPAY.getCode().equals(payType)) {
                return null;
            }
        } else {

        }
        return result;
    }

    @Override
    @Transactional
    public Object buyHzbOfCG(String userId, String hzbTemplateCode,
            String payType) {
        Object result = null;
        // 判断用户是否实名认证
        User user = userBO.getRemoteUser(userId);
        if (!EBoolean.YES.getCode().equals(user.getIdentityFlag())) {
            throw new BizException("xn0000", "用户未实名认证，请先实名认证");
        }
        hzbBO.checkBuy(userId);
        HzbTemplate hzbTemplate = hzbTemplateBO.getHzbTemplate(hzbTemplateCode);
        if (!EHzbTemplateStatus.ON.getCode().equals(hzbTemplate.getStatus())) {
            throw new BizException("xn0000", "该摇钱树模板未上线，不可购买");
        }
        if (ECurrency.CNY.getCode().equals(hzbTemplate.getCurrency())) {
            if (EPayType.YEFR.getCode().equals(payType)) {
                result = doCGOneCurrencyPay(user, hzbTemplate);
            } else if (EPayType.WEIXIN.getCode().equals(payType)) {
                result = doWeixinPay(userId, hzbTemplate);
            } else if (EPayType.ALIPAY.getCode().equals(payType)) {
                return null;
            }
        } else {
            result = doCGOneCurrencyPay(user, hzbTemplate);
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
    private Object doZHFRPay(User user, HzbTemplate hzbTemplate) {
        // 余额支付
        Long frPayAmount = accountBO.doFRPay(hzbTemplate.getSystemCode(), user,
            ESysUser.SYS_USER.getCode(), hzbTemplate.getPrice(),
            EBizType.AJ_GMHZB);
        // 汇赚宝购买成功
        Hzb hzb = hzbBO.saveHzb(user.getUserId(), hzbTemplate, frPayAmount);
        // 分销规则
        distributeAmount(hzbTemplate.getSystemCode(), user.getUserId(),
            hzbTemplate.getPrice());
        // 产生红包
        hzbMgiftBO.generateHzbMgift(hzb, DateUtil.getTodayStart());
        return new BooleanRes(true);
    }

    @Transactional
    private Object doCGOneCurrencyPay(User user, HzbTemplate hzbTemplate) {
        // 单个币种资金划转
        accountBO.doTransferAmountByUser(hzbTemplate.getSystemCode(),
            user.getUserId(), ESysUser.SYS_USER.getCode(),
            hzbTemplate.getCurrency(), hzbTemplate.getPrice(),
            EBizType.AJ_GMHZB.getCode(), "购买摇钱树", user.getMobile() + "购买摇钱树");
        hzbBO.saveHzb(user.getUserId(), hzbTemplate, hzbTemplate.getPrice());
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
    private XN802180Res doWeixinPay(String userId, HzbTemplate hzbTemplate) {
        // 生成支付组号
        String payGroup = OrderNoGenerater.generateM(EGeneratePrefix.PAY_GROUP
            .getCode());
        // 落地本地系统消费记录，状态为未支付
        hzbBO.buyHzb(userId, hzbTemplate, payGroup);
        XN802180Res res = accountBO.doWeiXinPay(hzbTemplate.getSystemCode(),
            userId, payGroup, EBizType.AJ_GMHZB, hzbTemplate.getPrice());
        return res;
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
        // 分配分成
        distributeAmount(hzb.getSystemCode(), hzb.getUserId(), hzb.getPrice());
        // 产生红包
        // hzbMgiftBO.sendHzbMgift(hzb.getUserId());
    }

    // 汇赚宝分成:
    // 1、数据准备
    // 2、计算分成:针对用户_已购买汇赚宝的一级二级推荐人和所在辖区用户
    private void distributeAmount(String systemCode, String userId, Long price) {
        User ownerUser = userBO.getRemoteUser(userId);
        // 用户分成
        String cUserId = ownerUser.getUserReferee();
        if (StringUtils.isNotBlank(cUserId)) {
            User cUser = userBO.getRemoteUser(userId);
            boolean cHzbResult = hzbBO.isHzbExistByUser(cUserId);
            if (cHzbResult) {
                this.userFcAmount(systemCode, cUser, ownerUser,
                    SysConstants.HZB_CUSER, price);
            }
            // B用户分成
            String bUserId = cUser.getUserReferee();
            if (StringUtils.isNotBlank(bUserId)) {
                User bUser = userBO.getRemoteUser(bUserId);
                boolean bHzbResult = hzbBO.isHzbExistByUser(bUserId);
                if (bHzbResult) {
                    this.userFcAmount(systemCode, bUser, ownerUser,
                        SysConstants.HZB_BUSER, price);
                }
                // A用户分成
                String aUserId = bUser.getUserReferee();
                if (StringUtils.isNotBlank(aUserId)) {
                    User aUser = userBO.getRemoteUser(aUserId);
                    boolean aHzbResult = hzbBO.isHzbExistByUser(aUserId);
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
                User provinceUser = userBO.getPartnerUserInfo(
                    userExt.getProvince(), null, null);
                if (provinceUser != null) {
                    areaFcAmount(systemCode, provinceUser, ownerUser,
                        SysConstants.HZB_PROVINCE, price, "省");
                }
                if (StringUtils.isNotBlank(userExt.getCity())) {
                    // 市合伙人
                    User cityUser = userBO.getPartnerUserInfo(
                        userExt.getProvince(), userExt.getCity(), null);
                    if (cityUser != null) {
                        areaFcAmount(systemCode, cityUser, ownerUser,
                            SysConstants.HZB_CITY, price, "市");
                    }
                    if (StringUtils.isNotBlank(userExt.getArea())) {
                        // 县合伙人
                        User areaUser = userBO.getPartnerUserInfo(
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

    private void userFcAmount(String systemCode, User fcUser, User ownerUser,
            String sysConstants, Long price) {
        Map<String, String> rateMap = sysConfigBO.getConfigsMap(systemCode);
        Double hzbUserRate = Double.valueOf(rateMap.get(sysConstants));
        Long transAmount = Double.valueOf(hzbUserRate * price).longValue();
        if (transAmount != null && transAmount != 0) {
            String fromBizNote = UserUtil.getUserMobile(ownerUser.getMobile())
                    + EBizType.AJ_GMHZBFC.getValue() + ","
                    + UserUtil.getUserMobile(fcUser.getMobile()) + "分成";
            String toBizNote = UserUtil.getUserMobile(ownerUser.getMobile())
                    + EBizType.AJ_GMHZBFC.getValue();
            accountBO.doTransferAmountByUser(systemCode, fcUser.getUserId(),
                ESysUser.SYS_USER.getCode(), ECurrency.FRB.getCode(),
                transAmount, EBizType.AJ_GMHZBFC.getCode(), fromBizNote,
                toBizNote);
        }
    }

    private void areaFcAmount(String systemCode, User areaUser, User ownerUser,
            String sysConstants, Long price, String remark) {
        Map<String, String> rateMap = sysConfigBO.getConfigsMap(systemCode);
        Double rate = Double.valueOf(rateMap.get(sysConstants));
        Long transAmount = Double.valueOf(price * rate).longValue();
        if (transAmount != null && transAmount != 0) {
            String fromBizNote = UserUtil.getUserMobile(ownerUser.getMobile())
                    + EBizType.AJ_GMHZBFC.getValue() + "," + remark + "合伙人"
                    + UserUtil.getUserMobile(areaUser.getMobile()) + "分成";
            String toBizNote = UserUtil.getUserMobile(ownerUser.getMobile())
                    + EBizType.AJ_GMHZBFC.getValue() + "," + remark + "合伙人分成";
            accountBO.doTransferAmountByUser(systemCode, areaUser.getUserId(),
                ESysUser.SYS_USER.getCode(), ECurrency.FRB.getCode(),
                transAmount, EBizType.AJ_GMHZBFC.getCode(), fromBizNote,
                toBizNote);
        }
    }

    @Override
    @Transactional
    public Object queryDistanceHzbList(String latitude, String longitude,
            String userId, String deviceNo, String companyCode,
            String systemCode) {
        hzbYyBO.checkHzbYyCondition(systemCode, userId, deviceNo);
        // 取距离
        Map<String, String> sysConfigMap = sysConfigBO
            .getConfigsMap(systemCode);
        Hzb condition = new Hzb();
        condition.setDistance(sysConfigMap.get(SysConstants.HZB_DISTANCE));
        // 设置最多被摇次数
        String periodRockNumCount = sysConfigMap
            .get(SysConstants.HZB_YY_DAY_MAX_COUNT);
        Integer periodRockNum = Integer.valueOf(periodRockNumCount);
        condition.setPeriodRockNum(periodRockNum);
        List<Hzb> list = hzbBO.queryDistanceHzbList(condition);
        // 截取数量
        String hzbMaxNumStr = sysConfigMap.get(SysConstants.HZB_MAX_NUM);
        Integer hzbMaxNum = Integer.valueOf(hzbMaxNumStr);
        if (CollectionUtils.isNotEmpty(list) && list.size() > hzbMaxNum) {
            list = list.subList(0, hzbMaxNum);
        }
        for (Hzb hzb : list) {
            hzb.setShareUrl(PropertiesUtil.Config.SHARE_URL);
        }
        return list;
    }

    @Override
    public Paginable<Hzb> queryHzbPage(int start, int limit, Hzb condition) {
        return hzbBO.getPaginable(start, limit, condition);
    }

    @Override
    public List<Hzb> queryHzbList(Hzb condition) {
        return hzbBO.queryHzbList(condition);
    }

    @Override
    public Hzb getHzb(String code) {
        return hzbBO.getHzb(code);
    }

    /** 
     * @see com.cdkj.zhpay.ao.IHzbAO#myHzb(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public List<Hzb> myHzb(String userId, String companyCode, String systemCode) {
        return hzbBO.queryHzbList(userId, companyCode, systemCode);
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
            throw new BizException("xn0000", "待支付状态无法上下架");
        }
        hzbBO.refreshPutStatus(code, status);
    }

    @Override
    public void doResetRockNumDaily() {
        hzbBO.resetPeriodRockNum();
    }
}
