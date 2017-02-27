/**
 * @Title CMBUtil.java 
 * @Package com.cdkj.zhpay.core 
 * @Description 
 * @author leo(haiqing)  
 * @date 2017年2月26日 下午5:57:27 
 * @version V1.0   
 */
package com.cdkj.zhpay.core;

import java.util.Map;
import java.util.TreeMap;

/** 
 * @author: haiqingzheng 
 * @since: 2017年2月26日 下午5:57:27 
 * @history:
 */
public class CMBUtil {

    private static String appid = "aaa";

    private static String mch_id = "aaa";

    private static String body = "aaa";

    private static String out_trade_no = "aaa";

    private static String total_fee = "aaa";

    private static String spbill_create_ip = "aaa";

    private static String trade_type = "aaa";

    private static String notify_url = "aaa";

    private static String sign = "aaa";

    private static String partnerKey = "aaa";

    private static String openid = "aaa";

    private static String attach = "aaa";

    // 预支付订单号
    private static String prepay_id;

    /** 
     * @param args 
     * @create: 2017年2月26日 下午5:57:28 haiqingzheng
     * @history: 
     */
    public static void main(String[] args) {
        getPackage();

    }

    public static String getPackage() {
        TreeMap<String, String> treeMap = new TreeMap<String, String>();
        treeMap.put("appid", appid);
        treeMap.put("mch_id", mch_id);
        treeMap.put("body", body);
        treeMap.put("out_trade_no", out_trade_no);
        treeMap.put("total_fee", total_fee);
        treeMap.put("spbill_create_ip", spbill_create_ip);
        treeMap.put("trade_type", trade_type);
        treeMap.put("notify_url", notify_url);
        // if (StringUtils.isNotBlank(openid)) {
        treeMap.put("openid", openid);
        // }
        treeMap.put("attach", attach);
        StringBuilder sb = new StringBuilder();
        for (String key : treeMap.keySet()) {
            sb.append(key).append("=").append(treeMap.get(key)).append("&");
        }
        sb.append("key=" + partnerKey);
        // sign = MD5Util.MD5Encode(sb.toString(), "utf-8").toUpperCase();
        // treeMap.put("sign", sign);
        StringBuilder xml = new StringBuilder();
        xml.append("<xml>\n");
        for (Map.Entry<String, String> entry : treeMap.entrySet()) {
            if ("body".equals(entry.getKey()) || "sign".equals(entry.getKey())) {
                xml.append("<" + entry.getKey() + "><![CDATA[")
                    .append(entry.getValue())
                    .append("]]></" + entry.getKey() + ">\n");
            } else {
                xml.append("<" + entry.getKey() + ">").append(entry.getValue())
                    .append("</" + entry.getKey() + ">\n");
            }
        }
        xml.append("</xml>");
        System.out.println(xml.toString());
        return xml.toString();
    }

}
