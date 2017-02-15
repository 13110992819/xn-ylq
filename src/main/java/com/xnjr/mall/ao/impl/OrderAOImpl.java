/**
 * @Title OrderAOImpl.java 
 * @Package com.xnjr.mall.ao.impl 
 * @Description 
 * @author xieyj  
 * @date 2016年5月25日 上午9:37:32 
 * @version V1.0   
 */
package com.xnjr.mall.ao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xnjr.mall.ao.IOrderAO;
import com.xnjr.mall.bo.IAccountBO;
import com.xnjr.mall.bo.ICartBO;
import com.xnjr.mall.bo.IJewelInteractBO;
import com.xnjr.mall.bo.IOrderBO;
import com.xnjr.mall.bo.IProductBO;
import com.xnjr.mall.bo.IProductOrderBO;
import com.xnjr.mall.bo.ISYSConfigBO;
import com.xnjr.mall.bo.ISmsOutBO;
import com.xnjr.mall.bo.IUserBO;
import com.xnjr.mall.bo.base.Paginable;
import com.xnjr.mall.common.DateUtil;
import com.xnjr.mall.common.SysConstants;
import com.xnjr.mall.core.CalculationUtil;
import com.xnjr.mall.core.OrderNoGenerater;
import com.xnjr.mall.core.StringValidater;
import com.xnjr.mall.domain.Cart;
import com.xnjr.mall.domain.Order;
import com.xnjr.mall.domain.Product;
import com.xnjr.mall.domain.ProductOrder;
import com.xnjr.mall.dto.res.XN802180Res;
import com.xnjr.mall.dto.res.XN808051Res;
import com.xnjr.mall.enums.EBizType;
import com.xnjr.mall.enums.EBoolean;
import com.xnjr.mall.enums.ECurrency;
import com.xnjr.mall.enums.EGeneratePrefix;
import com.xnjr.mall.enums.EOrderStatus;
import com.xnjr.mall.enums.EPayType;
import com.xnjr.mall.enums.ESysUser;
import com.xnjr.mall.exception.BizException;

/** 
 * @author: xieyj 
 * @since: 2016年5月25日 上午9:37:32 
 * @history:
 */
@Service
public class OrderAOImpl implements IOrderAO {
    protected static final Logger logger = LoggerFactory
        .getLogger(OrderAOImpl.class);

    @Autowired
    private IOrderBO orderBO;

    @Autowired
    private IProductOrderBO productOrderBO;

    @Autowired
    private ICartBO cartBO;

    @Autowired
    private IUserBO userBO;

    @Autowired
    private IAccountBO accountBO;

    @Autowired
    private IProductBO productBO;

    @Autowired
    private ISYSConfigBO sysConfigBO;

    @Autowired
    private ISmsOutBO smsOutBO;

    @Autowired
    private IJewelInteractBO jewelInteractBO;

    /**
     * @see com.xnjr.mall.ao.IOrderAO#commitOrder(java.lang.String, java.lang.Integer, java.lang.Long, com.xnjr.mall.domain.Order)
     */
    @Override
    @Transactional
    public String commitOrder(String productCode, Integer quantity, Order data) {
        // 计算订单总价
        Product product = productBO.getProduct(productCode);
        if (product.getQuantity() != null) {
            if ((product.getQuantity() - quantity) < 0) {
                throw new BizException("xn0000", "该商品库存量不足，无法购买");
            }
            // 减去库存量
            productBO.refreshProductQuantity(productCode, quantity);
        }
        if (null != product.getPrice1()) {
            Long amount1 = quantity * product.getPrice1();
            data.setAmount1(amount1);
            // 计算订单运费
            Long yunfei = totalYunfei(data.getSystemCode(),
                product.getCompanyCode(), amount1);
            data.setYunfei(yunfei);
        }
        if (null != product.getPrice2()) {
            Long amount2 = quantity * product.getPrice2();
            data.setAmount2(amount2);
        }
        if (null != product.getPrice3()) {
            Long amount3 = quantity * product.getPrice3();
            data.setAmount3(amount3);
        }
        // 设置订单所属公司
        data.setCompanyCode(product.getCompanyCode());
        data.setSystemCode(product.getSystemCode());
        // 订单号生成
        String code = OrderNoGenerater.generateM(EGeneratePrefix.ORDER
            .getCode());
        data.setCode(code);
        orderBO.saveOrder(data);
        // 订单产品快照关联
        productOrderBO.saveProductOrder(code, productCode, quantity,
            product.getPrice1(), product.getPrice2(), product.getPrice3(),
            product.getSystemCode());
        return code;
    }

    /** 
     * @see com.xnjr.mall.ao.IOrderAO#commitOrder(com.xnjr.mall.domain.Order)
     */
    @Override
    @Transactional
    public XN808051Res commitOrder(List<String> cartCodeList, Order data) {
        XN808051Res res = new XN808051Res();
        Long cnyAmount = 0L;
        Long gwAmount = 0L;
        Long qbAmount = 0L;
        List<String> result = new ArrayList<String>();
        // 按公司编号进行拆单, 遍历获取公司编号列表
        Map<String, String> companyMap = new HashMap<String, String>();
        for (String cartCode : cartCodeList) {
            Cart cart = cartBO.getCart(cartCode);
            Product product = productBO.getProduct(cart.getProductCode());
            String companyCode = product.getCompanyCode();
            companyMap.put(companyCode, companyCode);
        }
        // 遍历产品编号
        for (String company : companyMap.keySet()) {
            List<String> cartsCompany = new ArrayList<String>();
            for (String cartCode : cartCodeList) {
                Cart cart = cartBO.getCart(cartCode);
                Product product = productBO.getProduct(cart.getProductCode());
                String companyCode = product.getCompanyCode();
                if (company.equals(companyCode)) {
                    cartsCompany.add(cartCode);
                }
                cnyAmount += cart.getQuantity() * cart.getPrice1();
                gwAmount += cart.getQuantity() * cart.getPrice2();
                qbAmount += cart.getQuantity() * cart.getPrice3();
            }
            String orderCode = commitOneOrder(cartsCompany, data);
            result.add(orderCode);
        }
        res.setCodeList(result);
        res.setCnyAmount(cnyAmount);
        res.setGwAmount(gwAmount);
        res.setQbAmount(qbAmount);
        return res;
    }

    private String commitOneOrder(List<String> cartCodeList, Order data) {
        String code = OrderNoGenerater.generateME(EGeneratePrefix.ORDER
            .getCode());
        data.setCode(code);
        // 落地订单产品关联信息 计算订单总金额
        Long amount1 = 0L;
        Long amount2 = 0L;
        Long amount3 = 0L;
        String companyCode = null;
        String systemCode = null;
        for (String cartCode : cartCodeList) {
            Cart cart = cartBO.getCart(cartCode);
            Product product = productBO.getProduct(cart.getProductCode());
            if (StringUtils.isBlank(systemCode)) {
                // 设置系统编号
                systemCode = product.getSystemCode();
            }
            if (product.getQuantity() != null) {
                if ((product.getQuantity() - cart.getQuantity()) < 0) {
                    throw new BizException("xn0000", "商品[" + product.getName()
                            + "]库存量不足，无法购买");
                }
                // 减去库存量
                productBO.refreshProductQuantity(product.getCode(),
                    cart.getQuantity());
            }
            if (null != product.getPrice1()) {
                amount1 = amount1 + (cart.getQuantity() * product.getPrice1());
            }
            if (null != product.getPrice2()) {
                amount2 = amount2 + (cart.getQuantity() * product.getPrice2());
            }
            if (null != product.getPrice3()) {
                amount3 = amount3 + (cart.getQuantity() * product.getPrice3());
            }
            companyCode = product.getCompanyCode();
            productOrderBO.saveProductOrder(code, cart.getProductCode(),
                cart.getQuantity(), product.getPrice1(), product.getPrice2(),
                product.getPrice3(), product.getSystemCode());
        }
        data.setAmount1(amount1);
        data.setAmount2(amount2);
        data.setAmount3(amount3);
        data.setCompanyCode(companyCode);
        data.setSystemCode(systemCode);
        // 计算订单运费
        Long yunfei = totalYunfei(systemCode, companyCode, amount2);
        data.setYunfei(yunfei);
        // 保存订单
        orderBO.saveOrder(data);
        // 删除购物车选中记录
        for (String cartCode : cartCodeList) {
            cartBO.removeCart(cartCode);
        }
        return code;
    }

    private Long totalYunfei(String systemCode, String companyCode, Long amount) {
        Long yunfei = 0L;
        Long byje = StringValidater.toLong(sysConfigBO.getConfigValue(
            systemCode, null, companyCode, SysConstants.SP_BYJE)) * 1000;
        if (amount < byje) {
            yunfei = StringValidater.toLong(sysConfigBO.getConfigValue(
                systemCode, null, companyCode, SysConstants.SP_YUNFEI)) * 1000;
        }
        return yunfei;
    }

    @Override
    public void toPayOrderList(List<String> codeList, String channel) {
        for (String orderCode : codeList) {
            this.toPayOrder(orderCode, channel);
        }
    }

    @Override
    @Transactional
    public Object toPayOrder(String code, String payType) {
        Order order = orderBO.getOrder(code);
        if (!EOrderStatus.TO_PAY.getCode().equals(order.getStatus())) {
            throw new BizException("xn000000", "订单不处于待支付状态");
        }
        Long payAmount1 = order.getAmount1() + order.getYunfei(); // 人民币
        if (EPayType.INTEGRAL.getCode().equals(payType)) {
            // 虚拟币兑换
        } else if (EPayType.WEIXIN.getCode().equals(payType)) {
        } else if (EPayType.ALIPAY.getCode().equals(payType)) {
        }
        orderBO.refreshOrderPayAmount(code, payAmount1, 0L, 0L);
        return null;
    }

    @Override
    @Transactional
    public Object toPayMixOrder(String code, String payType, String ip) {
        Order order = orderBO.getOrder(code);
        if (!EOrderStatus.TO_PAY.getCode().equals(order.getStatus())) {
            throw new BizException("xn000000", "订单不处于待支付状态");
        }
        Long cnyAmount = order.getAmount1();// 去除运费 order.getYunfei(); // 人民币
        Long gwAmount = order.getAmount2(); // 购物币
        Long qbAmount = order.getAmount3(); // 钱包币
        String systemCode = order.getSystemCode();
        String fromUserId = order.getApplyUser();
        // 人民币+购物币+钱包币
        // 余额支付(余额支付)
        if (EPayType.YEZP.getCode().equals(payType)) {
            // 检验购物币和钱包币和余额是否充足
            accountBO.checkGwQbAndBalance(systemCode, fromUserId, gwAmount,
                qbAmount, cnyAmount);
            // 更新订单支付金额
            orderBO.refreshOrderPayAmount(code, cnyAmount, gwAmount, qbAmount);
            // 扣除金额
            accountBO.doGwQbAndBalancePay(systemCode, fromUserId,
                ESysUser.SYS_USER.getCode(), gwAmount, qbAmount, cnyAmount,
                EBizType.AJ_GW);
        } else if (EPayType.WEIXIN.getCode().equals(payType)) {
            // 检验购物币和钱包币是否充足
            accountBO.checkGWBQBBAmount(systemCode, fromUserId, gwAmount,
                qbAmount);
            String bizNote = "订单号：" + order.getCode() + "——购买尖货";
            String body = "正汇钱包—尖货";
            XN802180Res res = accountBO.doWeiXinPay(systemCode, fromUserId,
                EBizType.AJ_GW, bizNote, body, cnyAmount, ip);
            orderBO.refreshOrderPayCode(code, res.getJourCode());
            return res;
        } else if (EPayType.ALIPAY.getCode().equals(payType)) {
        }
        return null;
    }

    /** 
     * @see com.xnjr.mall.ao.IOrderAO#toPayMixOrderList(java.util.List, java.lang.String, java.lang.String)
     */
    @Override
    @Transactional
    public Object toPayMixOrderList(List<String> codeList, String payType,
            String ip, String applyUser) {
        Long cnyAmount = 0L;// 人民币 去除运费 order.getYunfei();
        Long gwAmount = 0L; // 购物币
        Long qbAmount = 0L; // 钱包币
        String systemCode = null;
        for (String code : codeList) {
            Order order = orderBO.getOrder(code);
            if (!EOrderStatus.TO_PAY.getCode().equals(order.getStatus())) {
                throw new BizException("xn000000", "订单不处于待支付状态");
            }
            if (!order.getApplyUser().equals(applyUser)) {
                throw new BizException("xn000000", "该订单不属于当前用户无法支付");
            }
            cnyAmount += order.getAmount1();// 人民币
            gwAmount += order.getAmount2(); // 购物币
            qbAmount += order.getAmount3(); // 钱包币
            if (EPayType.YEZP.getCode().equals(payType)) {
                orderBO.refreshOrderPayAmount(code, order.getAmount1(),
                    order.getAmount2(), order.getAmount3());
            }
            if (StringUtils.isBlank(systemCode)) {
                systemCode = order.getSystemCode();
            }
        }
        // 人民币+购物币+钱包币
        // 余额支付(余额支付)
        if (EPayType.YEZP.getCode().equals(payType)) {
            // 检验购物币和钱包币和余额是否充足
            accountBO.checkGwQbAndBalance(systemCode, applyUser, gwAmount,
                qbAmount, cnyAmount);
            // 扣除金额
            accountBO.doGwQbAndBalancePay(systemCode, applyUser,
                ESysUser.SYS_USER.getCode(), gwAmount, qbAmount, cnyAmount,
                EBizType.AJ_GW);
        } else if (EPayType.WEIXIN.getCode().equals(payType)) {
            // 检验购物币和钱包币是否充足
            accountBO.checkGWBQBBAmount(systemCode, applyUser, gwAmount,
                qbAmount);
            String bizNote = null;
            if (CollectionUtils.isNotEmpty(codeList) && codeList.size() <= 1) {
                bizNote = "订单号：" + codeList.get(0) + "——购买尖货";
            } else {
                bizNote = "多订单支付——购买尖货";
            }
            String body = "正汇钱包—尖货";
            XN802180Res res = accountBO.doWeiXinPay(systemCode, applyUser,
                EBizType.AJ_GW, bizNote, body, cnyAmount, ip);
            for (String code : codeList) {
                orderBO.refreshOrderPayCode(code, res.getJourCode());
            }
            return res;
        } else if (EPayType.ALIPAY.getCode().equals(payType)) {
        }
        return null;
    }

    /** 
     * @see com.xnjr.mall.ao.IOrderAO#cancelOrder(java.lang.String, java.lang.String)
     */
    @Override
    public int cancelOrder(String code, String userId, String remark) {
        Order data = orderBO.getOrder(code);
        if (!userId.equals(data.getApplyUser())) {
            throw new BizException("xn0000", "订单申请人和取消操作用户不符");
        }
        if (!EOrderStatus.TO_PAY.getCode().equals(data.getStatus())) {
            throw new BizException("xn0000", "订单状态不是待支付状态");
        }
        return orderBO.cancelOrder(code, remark);
    }

    /**
     * @see com.xnjr.mall.ao.IOrderAO#cancelOrder(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public int cancelOrderOss(String code, String updater, String remark) {
        Order data = orderBO.getOrder(code);
        if (!EOrderStatus.TO_PAY.getCode().equals(data.getStatus())
                && !EOrderStatus.PAY_YES.getCode().equals(data.getStatus())
                && !EOrderStatus.SEND.getCode().equals(data.getStatus())) {
            throw new BizException("xn0000", "该订单不是待支付，支付成功或已发货状态，无法操作");
        }
        String status = null;
        if (EOrderStatus.TO_PAY.getCode().equals(data.getStatus())) {
            status = EOrderStatus.YHYC.getCode();
        } else {
            if (EOrderStatus.PAY_YES.getCode().equals(data.getStatus())) {
                status = EOrderStatus.SHYC.getCode();
            } else if (EOrderStatus.SEND.getCode().equals(data.getStatus())) {
                status = EOrderStatus.KDYC.getCode();
            }
            Long cnyAmount = data.getPayAmount1(); // 人民币
            Long gwAmount = data.getPayAmount2(); // 购物币
            Long qbAmount = data.getPayAmount3(); // 钱包币
            accountBO.doOrderAmountBackBySysetm(data.getSystemCode(),
                data.getApplyUser(), gwAmount, qbAmount, cnyAmount,
                EBizType.AJ_GWTK, " 订单号：[" + code + "]");
            // 发送短信
            String userId = data.getApplyUser();
            smsOutBO.sentContent(userId, userId, "尊敬的用户，您的订单[" + data.getCode()
                    + "]已取消,退款原因:[" + remark + "],请及时查看退款。");
        }
        return orderBO.cancelOrder(code, updater, remark, status);
    }

    /** 
     * @see com.xnjr.mall.ao.IOrderAO#queryOrderPage(int, int, com.xnjr.mall.domain.Order)
     */
    @Override
    public Paginable<Order> queryOrderPage(int start, int limit, Order condition) {
        Paginable<Order> page = orderBO.getPaginable(start, limit, condition);
        if (page != null && CollectionUtils.isNotEmpty(page.getList())) {
            for (Order order : page.getList()) {
                ProductOrder imCondition = new ProductOrder();
                imCondition.setOrderCode(order.getCode());
                List<ProductOrder> productOrderList = productOrderBO
                    .queryProductOrderList(imCondition);
                order.setProductOrderList(productOrderList);
                for (ProductOrder productOrder : productOrderList) {
                    boolean result = jewelInteractBO.isComment(
                        order.getApplyUser(), productOrder.getOrderCode(),
                        productOrder.getProductCode());
                    productOrder.setIsComment(EBoolean.NO.getCode());
                    if (result) {
                        productOrder.setIsComment(EBoolean.YES.getCode());
                    }
                }
            }
        }
        return page;
    }

    /** 
     * @see com.xnjr.mall.ao.IOrderAO#queryOrderList(com.xnjr.mall.domain.Order)
     */
    @Override
    public List<Order> queryOrderList(Order condition) {
        List<Order> list = orderBO.queryOrderList(condition);
        if (CollectionUtils.isNotEmpty(list)) {
            for (Order order : list) {
                ProductOrder imCondition = new ProductOrder();
                imCondition.setOrderCode(order.getCode());
                List<ProductOrder> productOrderList = productOrderBO
                    .queryProductOrderList(imCondition);
                order.setProductOrderList(productOrderList);
                for (ProductOrder productOrder : productOrderList) {
                    boolean result = jewelInteractBO.isComment(
                        order.getApplyUser(), productOrder.getOrderCode(),
                        productOrder.getProductCode());
                    productOrder.setIsComment(EBoolean.NO.getCode());
                    if (result) {
                        productOrder.setIsComment(EBoolean.YES.getCode());
                    }
                }
            }
        }
        return list;
    }

    /** 
     * @see com.xnjr.mall.ao.IOrderAO#getOrder(java.lang.String)
     */
    @Override
    public Order getOrder(String code) {
        Order order = orderBO.getOrder(code);
        ProductOrder imCondition = new ProductOrder();
        imCondition.setOrderCode(order.getCode());
        List<ProductOrder> productOrderList = productOrderBO
            .queryProductOrderList(imCondition);
        order.setProductOrderList(productOrderList);
        for (ProductOrder productOrder : productOrderList) {
            boolean result = jewelInteractBO.isComment(order.getApplyUser(),
                productOrder.getOrderCode(), productOrder.getProductCode());
            productOrder.setIsComment(EBoolean.NO.getCode());
            if (result) {
                productOrder.setIsComment(EBoolean.YES.getCode());
            }
        }
        return order;
    }

    @Override
    public void deliverOrder(String code, String logisticsCompany,
            String logisticsCode, String deliverer, String deliveryDatetime,
            String pdf, String updater, String remark) {
        Order order = orderBO.getOrder(code);
        if (!EOrderStatus.PAY_YES.getCode().equalsIgnoreCase(order.getStatus())) {
            throw new BizException("xn000000", "订单不是已支付状态，无法操作");
        }
        orderBO.deliverOrder(code, logisticsCompany, logisticsCode, deliverer,
            deliveryDatetime, pdf, updater, remark);
        // 发送短信
        String userId = order.getApplyUser();
        String notice = "";
        if (order.getProductOrderList().size() > 1) {
            notice = "尊敬的用户，您的订单[" + order.getCode() + "]中的商品["
                    + order.getProductOrderList().get(0).getProductName()
                    + "等]已发货，请注意查收。";
        } else {
            notice = "尊敬的用户，您的订单[" + order.getCode() + "]中的商品["
                    + order.getProductOrderList().get(0).getProductName()
                    + "]已发货，请注意查收。";
        }
        smsOutBO.sentContent(userId, userId, notice);
    }

    @Override
    public void deliverOrder(String code, String updater, String remark) {
        Order order = orderBO.getOrder(code);
        if (!EOrderStatus.PAY_YES.getCode().equalsIgnoreCase(order.getStatus())) {
            throw new BizException("xn000000", "该订单不是已支付状态，无法操作");
        }
        orderBO.approveOrder(code, updater, EOrderStatus.RECEIVE.getCode(),
            remark);
    }

    @Override
    public void confirmOrder(String code, String updater, String remark) {
        Order order = orderBO.getOrder(code);
        if (!EOrderStatus.SEND.getCode().equalsIgnoreCase(order.getStatus())) {
            throw new BizException("xn000000", "订单不是已发货状态，无法操作");
        }
        orderBO.approveOrder(code, updater, EOrderStatus.RECEIVE.getCode(),
            remark);
        Long cnyAmount = order.getPayAmount1();
        String systemCode = order.getSystemCode();
        // 打款给商家分润账户,将人民币转出分润
        Map<String, String> rateMap = sysConfigBO.getConfigsMap(systemCode,
            null);
        Double fr2cny = Double.valueOf(rateMap.get(SysConstants.FR2CNY));
        Long frAmount = Double.valueOf(fr2cny * cnyAmount).longValue();
        String frAmountStr = CalculationUtil.divi(frAmount);
        accountBO.doTransferAmountByUser(order.getSystemCode(),
            ESysUser.SYS_USER.getCode(), order.getCompanyCode(),
            ECurrency.FRB.getCode(), frAmount, EBizType.AJ_QRSH.getCode(),
            "用户确认收货，系统需支付人民币:" + CalculationUtil.divi(cnyAmount) + "元，商户收到分润:"
                    + frAmountStr);
        smsOutBO
            .sentContent(order.getCompanyCode(), order.getCompanyCode(),
                "尊敬的商户，订单号[" + code + "]的用户已确认收货,本次收入分润：" + frAmountStr
                        + ",请注意查收!");
    }

    /** 
     * @see com.xnjr.mall.ao.IOrderAO#expedOrder(java.lang.String)
     */
    @Override
    public void expedOrder(String code) {
        orderBO.expedOrder(code);
    }

    /**
     * @see com.xnjr.mall.ao.IOrderAO#paySuccess(java.lang.String)
     */
    @Override
    @Transactional
    public  void paySuccess(String payCode) {
        Order condition = new Order();
        condition.setPayCode(payCode);
        List<Order> result = orderBO.queryOrderList(condition);
        if (CollectionUtils.isEmpty(result)) {
            throw new BizException("XN000000", "找不到对应的消费记录");
        }
        String systemCode = null;
        String applyUser = null;
        Long gwAmount = 0L; // 购物币
        Long qbAmount = 0L; // 钱包币
        for (Order order : result) {
            if (EOrderStatus.TO_PAY.getCode().equals(order.getStatus())) {
                // 更新支付金额
                orderBO.refreshOrderPayAmount(order.getCode(),
                    order.getAmount1(), order.getAmount2(), order.getAmount3());
            } else {
                logger.info("订单号：" + order.getCode() + "已支付，重复回调");
            }
            gwAmount += order.getAmount2(); // 购物币
            qbAmount += order.getAmount3(); // 钱包币
            if (StringUtils.isBlank(applyUser)) {
                applyUser = order.getApplyUser();
                systemCode = order.getSystemCode();
            }
        }
        // 扣除金额(购物币和钱包币)
        accountBO.doGWBQBBPay(systemCode, applyUser,
            ESysUser.SYS_USER.getCode(), gwAmount, qbAmount, EBizType.AJ_GW);
    }

    /** 
     * @see com.xnjr.mall.ao.IOrderAO#doChangeOrderStatusDaily()
     */
    @Override
    public void doChangeOrderStatusDaily() {
        doChangeNoPayOrder();
        doChangeNoReceiveOrder();
    }

    private void doChangeNoPayOrder() {
        logger.info("***************开始扫描未支付订单***************");
        Order condition = new Order();
        condition.setStatus(EOrderStatus.TO_PAY.getCode());
        // 前3天还未支付的订单
        condition.setApplyDatetimeEnd(DateUtil.getRelativeDate(new Date(),
            -(60 * 60 * 24 * 3 + 1)));
        List<Order> orderList = orderBO.queryOrderList(condition);
        if (CollectionUtils.isNotEmpty(orderList)) {
            for (Order order : orderList) {
                orderBO.refreshOrderStatus(order.getCode(),
                    EOrderStatus.YHYC.getCode());
            }
        }
        logger.info("***************结束扫描未支付订单***************");
    }

    private void doChangeNoReceiveOrder() {
        logger.info("***************开始扫描已发货未确认订单***************");
        Order condition = new Order();
        condition.setStatus(EOrderStatus.SEND.getCode());
        // 已发货后15天内未收货，自动确认收货
        condition.setUpdateDatetimeEnd(DateUtil.getRelativeDate(new Date(),
            -(60 * 60 * 24 * 15 + 1)));
        List<Order> orderList = orderBO.queryOrderList(condition);
        if (CollectionUtils.isNotEmpty(orderList)) {
            for (Order order : orderList) {
                orderBO.approveOrder(order.getCode(), "soft",
                    EOrderStatus.RECEIVE.getCode(), "系统自动确认收货");
            }
        }
        logger.info("***************结束扫描已发货未确认订单***************");
    }
}
