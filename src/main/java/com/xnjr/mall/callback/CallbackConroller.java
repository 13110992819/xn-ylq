package com.xnjr.mall.callback;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xnjr.mall.ao.IHzbAO;
import com.xnjr.mall.ao.IJewelRecordAO;
import com.xnjr.mall.common.JsonUtil;
import com.xnjr.mall.dto.req.XN802181Req;
import com.xnjr.mall.dto.res.XN802181Res;
import com.xnjr.mall.enums.EBizType;
import com.xnjr.mall.http.BizConnecter;

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
        PrintWriter out = response.getWriter();
        InputStream inStream = request.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        String result = new String(outSteam.toByteArray(), "utf-8");
        logger.info("**** APP支付回调结果 ****：" + result);
        XN802181Req req = new XN802181Req();
        req.setResult(result);
        XN802181Res res = BizConnecter.getBizData("802181",
            JsonUtil.Object2Json(req), XN802181Res.class);
        String jourCode = res.getJourCode();
        String bizType = res.getBizType();
        // 支付成功，商户处理后同步返回给微信参数
        if (!res.getIsSuccess()) {
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
            // ------------------------------
            // 处理业务完毕
            // ------------------------------
            String noticeStr = setXML("SUCCESS", "");
            out.print(new ByteArrayInputStream(noticeStr.getBytes(Charset
                .forName("UTF-8"))));
        }
    }

    public String setXML(String return_code, String return_msg) {
        return "<xml><return_code><![CDATA[" + return_code
                + "]]></return_code><return_msg><![CDATA[" + return_msg
                + "]]></return_msg></xml>";
    }
}
