package com.cdkj.zhpay.ao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdkj.zhpay.ao.IStockAO;
import com.cdkj.zhpay.bo.IAccountBO;
import com.cdkj.zhpay.bo.ISYSConfigBO;
import com.cdkj.zhpay.bo.IStockBO;
import com.cdkj.zhpay.bo.IStockBackBO;
import com.cdkj.zhpay.bo.IStockHoldBO;
import com.cdkj.zhpay.bo.IUserBO;
import com.cdkj.zhpay.bo.base.Paginable;
import com.cdkj.zhpay.common.DateUtil;
import com.cdkj.zhpay.common.SysConstants;
import com.cdkj.zhpay.domain.Stock;
import com.cdkj.zhpay.domain.StockBack;
import com.cdkj.zhpay.domain.StockHold;
import com.cdkj.zhpay.domain.UserExt;
import com.cdkj.zhpay.dto.res.XN802503Res;
import com.cdkj.zhpay.dto.res.XN805060Res;
import com.cdkj.zhpay.dto.res.XN805901Res;
import com.cdkj.zhpay.enums.ECurrency;
import com.cdkj.zhpay.enums.EDiviFlag;
import com.cdkj.zhpay.enums.EPayType;
import com.cdkj.zhpay.enums.EStockHoldStatus;
import com.cdkj.zhpay.enums.EStockStatus;
import com.cdkj.zhpay.enums.EStockType;
import com.cdkj.zhpay.exception.BizException;

@Service
public class StockAOImpl implements IStockAO {
    static Logger logger = Logger.getLogger(StockAOImpl.class);

    @Autowired
    private IStockBO stockBO;

    @Autowired
    private IStockHoldBO stockHoldBO;

    @Autowired
    private IStockBackBO stockBackBO;

    @Autowired
    private IAccountBO accountBO;

    @Autowired
    private ISYSConfigBO sysConfigBO;

    @Autowired
    private IUserBO userBO;

    @Override
    @Transactional
    public int editStock(Stock data) {
        Stock stock = stockBO.getStock(data.getCode());
        if (!EStockStatus.IN_USE.getCode().equals(stock.getStatus())) {
            throw new BizException("xn0000", "福利月卡信息状态不允许修改操作");
        }
        // 原先记录假删除
        stockBO.refreshStockStatus(data.getCode(),
            EStockStatus.FAKE_DELETED.getCode());
        // 新增福利月卡记录
        Stock newStock = new Stock();
        newStock.setName(data.getName());
        newStock.setType(stock.getType());
        newStock.setPic(data.getPic());
        newStock.setDescription(data.getDescription());
        newStock.setCapital(data.getCapital());
        newStock.setPrice(data.getPrice());
        newStock.setCurrency(ECurrency.CNY.getCode());
        newStock.setBackInterval(data.getBackInterval());
        // String backCount = sysConfigBO.getConfigValue(null, null, null,
        // SysConstants.ST_BACKNUM);
        // if (StringUtils.isBlank(backCount)) {
        // backCount = SysConstants.ST_BACKNUM_DEF;
        // }
        // newStock.setBackCount(Integer.valueOf(backCount));
        // newStock.setWelfare1(data.getWelfare1());
        // newStock.setWelfare2(data.getWelfare2());
        // newStock.setStatus(EStockStatus.IN_USE.getCode());
        // newStock.setSystemCode(stock.getSystemCode());
        return stockBO.saveStock(newStock);
    }

    @Override
    public int dropStock(String code) {
        if (!stockBO.isStockExist(code)) {
            throw new BizException("xn0000", "记录编号不存在");
        }
        return stockBO.removeStock(code);
    }

    @Override
    public Paginable<Stock> queryStockPage(int start, int limit, Stock condition) {
        return stockBO.getPaginable(start, limit, condition);
    }

    @Override
    public List<Stock> queryStockList(Stock condition) {
        return stockBO.queryStockList(condition);
    }

    @Override
    public Stock getStock(String code) {
        return stockBO.getStock(code);
    }

    @Override
    @Transactional
    public Object buyStock(String code, String userId, String payType, String ip) {
        Object result = null;
        StockHold condition = new StockHold();
        condition.setUserId(userId);
        condition.setStatus(EDiviFlag.EFFECT.getCode());
        if (stockHoldBO.getTotalCount(condition) > 0) {
            throw new BizException("xn0000", "用户已经购买过福利月卡，不允许多次购买");
        }
        Stock stock = stockBO.getStock(code);
        if (!EStockStatus.IN_USE.getCode().equals(stock.getStatus())) {
            throw new BizException("xn0000", "该福利月卡不可用");
        }
        String systemCode = stock.getSystemCode();
        if (EPayType.YEFR.getCode().equals(payType)) {
            // 校验余额是否充足
            accountBO.checkBalanceAmount(systemCode, userId, stock.getPrice());
            StockHold stockHold = new StockHold();
            stockHold.setUserId(userId);
            stockHold.setStockCode(code);
            stockHold.setStatus(EStockHoldStatus.UNCLEARED.getCode());
            stockHold.setBackWelfare1(0L);
            stockHold.setBackWelfare2(0L);
            stockHold.setBackNum(0);
            stockHold.setNextBack(DateUtil.getRelativeDate(
                DateUtil.getTodayStart(), 24 * 60 * 60));
            stockHold.setSystemCode(systemCode);
            distributeAmount(stockHold);
            result = stockHoldBO.saveStockHold(stockHold);
            // accountBO.doBalancePay(systemCode, userId,
            // ESysUser.SYS_USER.getCode(), stock.getPrice(),
            // EBizType.AJ_GMFLYK);
        } else if (EPayType.WEIXIN.getCode().equals(payType)) {
            // 获取微信APP支付信息
            String bizNote = stock.getName() + "——福利月卡购买";
            String body = "正汇钱包—福利月卡";
            // XN802180Res res = accountBO.doWeiXinPay(systemCode, userId,
            // EBizType.AJ_GMFLYK, bizNote, body, stock.getPrice(), ip);
            // 落地本地系统消费记录，状态为未支付
            StockHold data = new StockHold();
            data.setUserId(userId);
            data.setStockCode(code);
            data.setStatus(EStockHoldStatus.TO_PAY.getCode());
            data.setBackWelfare1(0L);
            data.setBackWelfare2(0L);
            data.setBackNum(0);
            data.setNextBack(DateUtil.getRelativeDate(DateUtil.getTodayStart(),
                24 * 60 * 60));
            // 橙账本流水号
            // data.setPayCode(res.getJourCode());
            // data.setSystemCode(systemCode);
            // stockHoldBO.saveStockHold(data);
            // result = res;
        }
        return result;
    }

    @Override
    @Transactional
    public void returnStock(String userId) {
        StockHold condition = new StockHold();
        condition.setUserId(userId);
        condition.setStatus(EStockHoldStatus.UNCLEARED.getCode());
        List<StockHold> list = stockHoldBO.queryStockHoldList(condition);
        if (CollectionUtils.isEmpty(list)) {
            throw new BizException("xn0000", "用户没有未清算的福利月卡，不允许返还操作");
        }
        StockHold stockHold = list.get(0);
        if (DateUtil.daysBetweenDate(new Date(), stockHold.getNextBack()) > 0) {
            if (stockHold.getBackNum() == 0) {
                throw new BizException("xn0000", "购买后首次领取时间为次日开始");
            } else {
                throw new BizException("xn0000", "未到返还时间，不允许返还");
            }
        }
        // 更新返还金额
        Stock stock = stockBO.getStock(stockHold.getStockCode());
        String status = EStockHoldStatus.UNCLEARED.getCode();
        int backNum = stockHold.getBackNum() + 1;
        Long backWelfare1 = stockHold.getBackWelfare1() + stock.getWelfare1();
        Long backWelfare2 = stockHold.getBackWelfare2() + stock.getWelfare2();
        Date nextBack = DateUtil.getRelativeDate(DateUtil.getTodayStart(),
            stock.getBackInterval() * 24 * 60 * 60);
        if (backNum == stock.getBackCount()) {
            status = EStockHoldStatus.CLEARED.getCode();
            nextBack = null;
        }
        stockHold.setStatus(status);
        stockHold.setBackNum(backNum);
        stockHold.setBackWelfare1(backWelfare1);
        stockHold.setBackWelfare2(backWelfare2);
        stockHold.setNextBack(nextBack);
        stockHoldBO.refreshStockHold(stockHold);
        // 落地返还记录
        StockBack stockBack = new StockBack();
        stockBack.setUserId(userId);
        stockBack.setStockCode(stockHold.getStockCode());
        stockBack.setBackDatetime(new Date());
        stockBack.setSystemCode(stockHold.getSystemCode());
        stockBackBO.saveStockBack(stockBack);
        // 发放贡献奖励和购物币
        transStockAmount(stockHold.getSystemCode(), userId,
            stock.getWelfare1(), stock.getWelfare2(), "福利月卡返还，已返第" + backNum
                    + "期");
    }

    @Override
    @Transactional
    public void clearStock(String userId) {
        StockHold condition = new StockHold();
        condition.setUserId(userId);
        condition.setStatus(EStockHoldStatus.UNCLEARED.getCode());
        List<StockHold> list = stockHoldBO.queryStockHoldList(condition);
        if (CollectionUtils.isEmpty(list)) {
            throw new BizException("xn0000", "用户没有未清算的福利月卡，不允许清算操作");
        }
        StockHold stockHold = list.get(0);
        // 更新返还金额
        Stock stock = stockBO.getStock(stockHold.getStockCode());
        String status = EStockHoldStatus.CLEARED.getCode();
        Long backWelfare1 = stock.getWelfare1()
                * (stock.getBackCount() - stockHold.getBackNum());
        Long backWelfare2 = stock.getWelfare2()
                * (stock.getBackCount() - stockHold.getBackNum());

        stockHold.setStatus(status);
        stockHold.setBackNum(stock.getBackCount());
        stockHold.setBackWelfare1(stockHold.getBackWelfare1() + backWelfare1);
        stockHold.setBackWelfare2(stockHold.getBackWelfare2() + backWelfare2);
        stockHold.setNextBack(null);
        stockHoldBO.refreshStockHold(stockHold);

        // 落地返还记录
        StockBack stockBack = new StockBack();
        stockBack.setUserId(userId);
        stockBack.setStockCode(stockHold.getStockCode());
        stockBack.setBackDatetime(new Date());
        stockBack.setSystemCode(stockHold.getSystemCode());
        stockBackBO.saveStockBack(stockBack);
        // 发放贡献奖励和购物币
        transStockAmount(stockHold.getSystemCode(), userId, backWelfare1,
            backWelfare2, "福利月卡清算");
    }

    private void transStockAmount(String systemCode, String userId,
            Long BackWelfare1, Long BackWelfare2, String bizNote) {
        // accountBO.doTransferAmountByUser(systemCode,
        // ESysUser.SYS_USER.getCode(), userId, ECurrency.GXJL.getCode(),
        // BackWelfare1, EBizType.AJ_FLYKHH.getCode(), bizNote);
        // accountBO.doTransferAmountByUser(systemCode,
        // ESysUser.SYS_USER.getCode(), userId, ECurrency.GWB.getCode(),
        // BackWelfare2, EBizType.AJ_FLYKHH.getCode(), bizNote);
    }

    @Override
    public StockHold myStock(String userId) {
        StockHold stockHold = null;
        StockHold condition = new StockHold();
        condition.setUserId(userId);
        condition.setStatus(EDiviFlag.EFFECT.getCode());
        List<StockHold> list = stockHoldBO.queryStockHoldList(condition);
        if (CollectionUtils.isNotEmpty(list)) {
            stockHold = list.get(0);
            Stock stock = stockBO.getStock(stockHold.getStockCode());
            stockHold.setStock(stock);
        }
        return stockHold;
    }

    @Override
    @Transactional
    public void paySuccess(String payCode) {
        StockHold condition = new StockHold();
        condition.setPayCode(payCode);
        List<StockHold> result = stockHoldBO.queryStockHoldList(condition);
        if (CollectionUtils.isEmpty(result)) {
            throw new BizException("XN000000", "找不到对应的消费记录");
        }
        stockHoldBO.refreshStatus(result.get(0).getId(),
            EStockHoldStatus.UNCLEARED.getCode());
        // 分配分成
        distributeAmount(result.get(0));
    }

    private void distributeAmount(StockHold stockHold) {
        String systemCode = stockHold.getSystemCode();
        // 分销规则
        Map<String, String> rateMap = sysConfigBO.getConfigsMap(systemCode,
            null);
        // 获取当前用户的推荐人，判断A用户是否有购买股份
        XN805901Res res = userBO.getRemoteUser(stockHold.getUserId(),
            stockHold.getUserId());
        UserExt userExt = res.getUserExt();
        XN805901Res refereeUser = userBO.getRemoteUser(res.getUserReferee(),
            res.getUserReferee());
        // 推荐人不存在无分销
        if (refereeUser == null) {
            logger.info("用户" + stockHold.getUserId() + "无推荐人,不分配福利月卡分成");
            return;
        }
        Long a1FrAmount = 0L;
        Long areaFrAmount = 0L;
        Long cityFrAmount = 0L;
        Long provinceFrAmount = 0L;
        // 获取当前购买福利月卡价格
        Stock stock = stockBO.getStock(stockHold.getStockCode());
        Long stockPrice = stock.getPrice();
        // 判断a是否购买
        StockHold aStockHold = stockHoldBO.getStockHoldByUser(refereeUser
            .getUserId());
        String bizNote = null;
        if (aStockHold == null) {
            Double areaRateNotA1 = Double.valueOf(rateMap
                .get(SysConstants.ST_AREA_NOTA1));
            Double cityRateNotA1 = Double.valueOf(rateMap
                .get(SysConstants.ST_CITY_NOTA1));
            Double provinceRateNotA1 = Double.valueOf(rateMap
                .get(SysConstants.ST_PROVINCE_NOTA1));
            Double a10Rate = Double.valueOf(rateMap.get(SysConstants.ST_A10));
            a1FrAmount = Double.valueOf(stockPrice * a10Rate).longValue();
            areaFrAmount = Double.valueOf(stockPrice * areaRateNotA1)
                .longValue();
            cityFrAmount = Double.valueOf(stockPrice * cityRateNotA1)
                .longValue();
            provinceFrAmount = Double.valueOf(stockPrice * provinceRateNotA1)
                .longValue();
            bizNote = "推荐人未买福利月卡";
        } else {
            Stock aStock = stockBO.getStock(aStockHold.getStockCode());
            if (EStockType.A.getCode().equals(aStock.getType())) {
                Double a12kRate = Double.valueOf(rateMap
                    .get(SysConstants.ST_A12000));
                a1FrAmount = Double.valueOf(stockPrice * a12kRate).longValue();
                bizNote = "推荐人买第一档福利月卡";
            }
            if (EStockType.B.getCode().equals(aStock.getType())) {
                Double a11wRate = Double.valueOf(rateMap
                    .get(SysConstants.ST_A110000));
                a1FrAmount = Double.valueOf(stockPrice * a11wRate).longValue();
                bizNote = "推荐人买第二档福利月卡";
            }
            if (EStockType.C.getCode().equals(aStock.getType())) {
                Double a13wRate = Double.valueOf(rateMap
                    .get(SysConstants.ST_A130000));
                a1FrAmount = Double.valueOf(stockPrice * a13wRate).longValue();
                bizNote = "推荐人买第三档福利月卡";
            }
            if (EStockType.D.getCode().equals(aStock.getType())) {
                Double a15wRate = Double.valueOf(rateMap
                    .get(SysConstants.ST_A150000));
                a1FrAmount = Double.valueOf(stockPrice * a15wRate).longValue();
                bizNote = "推荐人买第四档福利月卡";
            }
            Double areaRate = Double.valueOf(rateMap.get(SysConstants.ST_AREA));
            Double cityRate = Double.valueOf(rateMap.get(SysConstants.ST_CITY));
            Double provinceRate = Double.valueOf(rateMap
                .get(SysConstants.ST_PROVINCE));
            areaFrAmount = Double.valueOf(stockPrice * areaRate).longValue();
            cityFrAmount = Double.valueOf(stockPrice * cityRate).longValue();
            provinceFrAmount = Double.valueOf(stockPrice * provinceRate)
                .longValue();
        }
        distributeFrAmount(a1FrAmount, areaFrAmount, cityFrAmount,
            provinceFrAmount, bizNote, stock.getSystemCode(),
            refereeUser.getUserId(), userExt);
    }

    private void distributeFrAmount(Long a1Amount, Long areaAmount,
            Long cityAmount, Long provinceAmount, String bizNote,
            String systemCode, String aUserId, UserExt userExt) {
        if (userExt != null) {
            XN805060Res areaRes = null;
            XN805060Res cityRes = null;
            XN805060Res provinceRes = null;
            if (StringUtils.isNotBlank(userExt.getProvince())) {
                // 省合伙人
                provinceRes = userBO.getPartnerUserInfo(userExt.getProvince(),
                    null, null);
                if (StringUtils.isNotBlank(userExt.getCity())) {
                    // 市合伙人
                    cityRes = userBO.getPartnerUserInfo(userExt.getProvince(),
                        userExt.getCity(), null);
                    if (StringUtils.isNotBlank(userExt.getArea())) {
                        // 县合伙人
                        areaRes = userBO.getPartnerUserInfo(
                            userExt.getProvince(), userExt.getCity(),
                            userExt.getArea());
                    }
                }
            }
            // a1分成
            if (a1Amount != null && a1Amount != 0L) {
                XN802503Res accountRes = accountBO.getAccountByUserId(
                    systemCode, aUserId, ECurrency.FRB.getCode());
                // accountBO.doTransferAmount(systemCode,
                // ESysAccount.FRB.getCode(), accountRes.getAccountNumber(),
                // a1Amount, EBizType.AJ_FLYKFC.getCode(), bizNote
                // + ",推荐人分成分润");
            }
            if (areaRes != null && areaAmount != null && areaAmount != 0L) {
                XN802503Res areaAccount = accountBO.getAccountByUserId(
                    systemCode, areaRes.getUserId(), ECurrency.FRB.getCode());
                // accountBO.doTransferAmount(systemCode,
                // ESysAccount.FRB.getCode(), areaAccount.getAccountNumber(),
                // areaAmount, EBizType.AJ_FLYKFC.getCode(), bizNote
                // + ",县分成分润");
            }
            if (cityRes != null && cityAmount != null && cityAmount != 0L) {
                XN802503Res cityAccount = accountBO.getAccountByUserId(
                    systemCode, cityRes.getUserId(), ECurrency.FRB.getCode());
                // accountBO.doTransferAmount(systemCode,
                // ESysAccount.FRB.getCode(), cityAccount.getAccountNumber(),
                // cityAmount, EBizType.AJ_FLYKFC.getCode(), bizNote
                // + ",市分成分润");
            }
            if (provinceRes != null && provinceAmount != null
                    && provinceAmount != 0L) {
                XN802503Res provinceAccount = accountBO.getAccountByUserId(
                    systemCode, areaRes.getUserId(), ECurrency.FRB.getCode());
                // accountBO.doTransferAmount(systemCode,
                // ESysAccount.FRB.getCode(),
                // provinceAccount.getAccountNumber(), provinceAmount,
                // EBizType.AJ_FLYKFC.getCode(), bizNote + ",省分成分润");
            }
        }
    }
}
