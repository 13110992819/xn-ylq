package com.cdkj.zhpay.callback;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cdkj.zhpay.ao.IHzbAO;
import com.cdkj.zhpay.ao.IJewelRecordAO;
import com.cdkj.zhpay.enums.EBizType;

/** 
 * @author: haiqingzheng 
 * @since: 2016年12月26日 下午1:44:16 
 * @history:
 */
@Controller
public class CallbackConroller {

    private static Logger logger = Logger.getLogger(CallbackConroller.class);

    @Autowired
    private IJewelRecordAO jewelRecordAO;

    @Autowired
    IHzbAO hzbAO;

    @RequestMapping("/zhpay/app/callback")
    public synchronized void doCallbackZhpay(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        boolean isSuccess = Boolean.valueOf(request.getParameter("isSuccess"));
        String jourCode = request.getParameter("jourCode");
        String payGroup = request.getParameter("payGroup");
        String bizType = request.getParameter("bizType");
        // 支付成功，商户处理后同步返回给微信参数
        if (!isSuccess) {
            logger.info("支付失败");
        } else {
            logger.info("===============付款成功==============");
            // ------------------------------
            // 处理业务开始
            // ------------------------------
            try {
                logger.info("流水编号为：" + jourCode);
                if (EBizType.AJ_DUOBAO.getCode().equals(bizType)) {
                    System.out.println("**** 进入一元夺宝，微信APP支付服务器回调 start****");
                    jewelRecordAO.paySuccess(jourCode);
                    System.out.println("**** 进入一元夺宝，微信APP支付服务器回调 end****");
                } else if (EBizType.AJ_GMHZB.getCode().equals(bizType)) {
                    System.out.println("**** 进入购买汇赚宝，微信APP支付服务器回调 start****");
                    hzbAO.paySuccess(jourCode);
                    System.out.println("**** 进入购买汇赚宝，微信APP支付服务器回调 end****");
                }
            } catch (Exception e) {
                logger.info("支付回调异常");
            }
        }
    }
}
