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

    /**
     * 微信支付回调(app和h5)
     * @param request
     * @param response
     * @throws IOException 
     * @create: 2017年3月31日 下午10:25:57 xieyj
     * @history:
     */
    @RequestMapping("/wechat/callback")
    public synchronized void doCallbackZhpay(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        boolean isSuccess = Boolean.valueOf(request.getParameter("isSuccess"));
        String payGroup = request.getParameter("payGroup");
        String payCode = request.getParameter("payCode");
        Long amount = Long.valueOf(request.getParameter("transAmount"));
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
                if (EBizType.AJ_DUOBAO.getCode().equals(bizType)) {
                    System.out.println("**** 进入一元夺宝，微信支付服务器回调 start****");
                    jewelRecordAO.paySuccess(payGroup, payCode, amount);
                    System.out.println("**** 进入一元夺宝，微信支付服务器回调 end****");
                } else if (EBizType.AJ_GMHZB.getCode().equals(bizType)) {
                    System.out.println("**** 进入购买汇赚宝，微信支付服务器回调 start****");
                    hzbAO.paySuccess(payGroup, payCode, amount);
                    System.out.println("**** 进入购买汇赚宝，微信支付服务器回调 end****");
                }
            } catch (Exception e) {
                logger.info("支付回调异常,原因：" + e.getMessage());
            }
        }
    }
}
