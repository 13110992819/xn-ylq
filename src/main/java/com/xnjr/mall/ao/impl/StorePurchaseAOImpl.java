package com.xnjr.mall.ao.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xnjr.mall.ao.IStorePurchaseAO;
import com.xnjr.mall.bo.IAccountBO;
import com.xnjr.mall.bo.ISYSConfigBO;
import com.xnjr.mall.bo.IStoreBO;
import com.xnjr.mall.bo.IStorePurchaseBO;
import com.xnjr.mall.bo.IStoreTicketBO;
import com.xnjr.mall.bo.IUserBO;
import com.xnjr.mall.bo.IUserTicketBO;
import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.common.SysConstants;
import com.xnjr.mall.domain.Store;
import com.xnjr.mall.domain.StorePurchase;
import com.xnjr.mall.domain.UserTicket;
import com.xnjr.mall.dto.res.XN802503Res;
import com.xnjr.mall.dto.res.XN805060Res;
import com.xnjr.mall.enums.EBizType;
import com.xnjr.mall.enums.ECategoryType;
import com.xnjr.mall.enums.ECurrency;
import com.xnjr.mall.enums.EPayType;
import com.xnjr.mall.enums.EStoreStatus;
import com.xnjr.mall.enums.EStoreTicketType;
import com.xnjr.mall.enums.ESysAccount;
import com.xnjr.mall.enums.EUserTicketStatus;
import com.xnjr.mall.exception.BizException;

@Service
public class StorePurchaseAOImpl implements IStorePurchaseAO {

    @Autowired
    private IStorePurchaseBO storePurchaseBO;

    @Autowired
    private IStoreBO storeBO;

    @Autowired
    private IStoreTicketBO storeTicketBO;

    @Autowired
    private IUserTicketBO userTicketBO;

    @Autowired
    private IAccountBO accountBO;

    @Autowired
    private ISYSConfigBO sysConfigBO;

    @Autowired
    private IUserBO userBO;

    // 店铺消费业务逻辑：
    // 1、店铺信息校验
    // 2、产生消费订单，更新折扣券信息
    // 3、划转各个账户金额，分销
    @Override
    @Transactional
    public String storePurchase(String userId, String storeCode,
            String ticketCode, Long amount, String payType) {
        Store store = storeBO.getStore(storeCode);
        String systemCode = store.getSystemCode();
        if (!EStoreStatus.ONLINE_OPEN.getCode().equals(store.getStatus())) {
            throw new BizException("xn0000", "店铺不处于可消费状态");
        }
        Double fcRate = 0.0d;
        // 优惠金额
        Long yhAmount = amount;
        String remark = store.getName() + " 消费" + amount / 1000 + "元";
        if (StringUtils.isBlank(ticketCode)) {
            fcRate = store.getRate1();
        } else {
            fcRate = store.getRate2();
            // 扣除折扣券优惠
            UserTicket userTicket = userTicketBO.getUserTicket(ticketCode);
            if (!EUserTicketStatus.UNUSED.getCode().equals(
                userTicket.getStatus())) {
                throw new BizException("xn0000", "该折扣券不可用");
            }
            if (EStoreTicketType.MANJIAN.getCode().equals(
                userTicket.getTicketType())) {
                if (amount < userTicket.getTicketKey1()) {
                    throw new BizException("xn0000", "消费金额还未达到折扣券满减金额");
                }
                // 扣减消费金额
                yhAmount = amount - userTicket.getTicketKey2();
            }
            remark = remark + ",优惠后金额" + yhAmount / 1000 + "元，使用折扣券编号:["
                    + ticketCode + "]";
        }
        StorePurchase data = new StorePurchase();
        data.setUserId(userId);
        data.setStoreCode(storeCode);
        data.setAmount(amount);
        data.setSystemCode(systemCode);
        data.setRemark(remark);
        // 消费1000块(优先共享奖励，分润)
        // 1,用户C可以得到消费额35%的钱包币
        // 2,用户B和A可以得到对应分润为X1和X2
        // 3,业务员可以得到分润为X3
        // 4,所在省市县可以得到对应分润为X4，X5和X6
        // 5,最后到店铺手里的钱为1000-X1-X2-X3-X4-X5-X6
        // 6,另外，35%的钱包币由平台发放
        if (EPayType.NBHZ.getCode().equals(payType)) {
            // 1,用户C可以得到消费额35%的钱包币,平台扣除钱包币
            Double qbbRate = Double.valueOf(SysConstants.CUSERQBBRATE);
            Long transAmount = Double.valueOf(yhAmount * qbbRate).longValue();
            XN802503Res qbbAccount = accountBO.getAccountByUserId(
                store.getSystemCode(), ESysAccount.SYS_ACCOUNT.getCode(),
                ECurrency.QBB.getCode());
            // 获取用户账户
            XN802503Res userAccount = accountBO.getAccountByUserId(
                store.getSystemCode(), userId, ECurrency.QBB.getCode());
            accountBO.doTransferAmount(systemCode,
                qbbAccount.getAccountNumber(), userAccount.getAccountNumber(),
                transAmount, EBizType.AJ_DPXF.getCode(),
                EBizType.AJ_DPXF.getValue());
            // 用户A和用户B和用户C分润值，从哪里来?
            Double o2oAUserRate = Double.valueOf(sysConfigBO.getConfigValue(
                systemCode, ECategoryType.O2O.getCode(), null,
                SysConstants.O2O_AUSER));
            Double o2oBUserRate = Double.valueOf(sysConfigBO.getConfigValue(
                systemCode, ECategoryType.O2O.getCode(), null,
                SysConstants.O2O_BUSER));
            Double o2oCUserRate = Double.valueOf(sysConfigBO.getConfigValue(
                systemCode, ECategoryType.O2O.getCode(), null,
                SysConstants.O2O_CUSER));
            Long frAmountA = Double.valueOf(yhAmount * o2oAUserRate * fcRate)
                .longValue();
            Long frAmountB = Double.valueOf(yhAmount * o2oBUserRate * fcRate)
                .longValue();
            Long frAmountC = Double.valueOf(yhAmount * o2oCUserRate * fcRate)
                .longValue();
            // 辖区管理用户获取的分润
            // 省合伙人
            Long proAmount = 0L;
            Long cityAmount = 0L;
            Long areaAmount = 0L;
            Double proRate = Double.valueOf(sysConfigBO.getConfigValue(
                systemCode, ECategoryType.O2O.getCode(), null,
                SysConstants.O2O_PROVINCE));
            Double cityRate = Double.valueOf(sysConfigBO.getConfigValue(
                systemCode, ECategoryType.O2O.getCode(), null,
                SysConstants.O2O_CITY));
            Double areaRate = Double.valueOf(sysConfigBO.getConfigValue(
                systemCode, ECategoryType.O2O.getCode(), null,
                SysConstants.O2O_AREA));
            // 省合伙人
            XN805060Res provinceRes = userBO.getPartnerUserInfo(
                store.getProvince(), null, null);
            if (provinceRes != null) {
                proAmount = Double.valueOf(yhAmount * proRate * fcRate)
                    .longValue();
            }
            // 市合伙人
            XN805060Res cityRes = userBO.getPartnerUserInfo(
                store.getProvince(), store.getCity(), null);
            if (cityRes != null) {
                cityAmount = Double.valueOf(yhAmount * cityRate * fcRate)
                    .longValue();
            }
            // 县合伙人
            XN805060Res areaRes = userBO.getPartnerUserInfo(
                store.getProvince(), store.getCity(), store.getArea());
            if (areaRes != null) {
                areaAmount = Double.valueOf(yhAmount * areaRate * fcRate)
                    .longValue();
            }
        }
        return storePurchaseBO.saveStorePurchase(data);
    }

    @Override
    public int dropStorePurchase(String code) {
        if (!storePurchaseBO.isStorePurchaseExist(code)) {
            throw new BizException("xn0000", "记录编号不存在");
        }
        return storePurchaseBO.removeStorePurchase(code);
    }

    @Override
    public Paginable<StorePurchase> queryStorePurchasePage(int start,
            int limit, StorePurchase condition) {
        return storePurchaseBO.getPaginable(start, limit, condition);
    }

    @Override
    public List<StorePurchase> queryStorePurchaseList(StorePurchase condition) {
        return storePurchaseBO.queryStorePurchaseList(condition);
    }

    @Override
    public StorePurchase getStorePurchase(String code) {
        return storePurchaseBO.getStorePurchase(code);
    }

}
