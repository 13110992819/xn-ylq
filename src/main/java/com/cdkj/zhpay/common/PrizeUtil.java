/**
 * @Title DoubleUtil.java 
 * @Package com.xnjr.mall.common 
 * @Description 
 * @author xieyj  
 * @date 2017年1月10日 上午11:29:00 
 * @version V1.0   
 */
package com.cdkj.zhpay.common;

import java.util.ArrayList;
import java.util.List;

/** 
 * @author: xieyj 
 * @since: 2017年1月10日 上午11:29:00 
 * @history:
 */
public class PrizeUtil {
    /**
     * 根据Math.random()产生一个double型的随机数，判断每个奖品出现的概率
     * @param prizes
     * @return random：奖品列表prizes中的序列（prizes中的第random个就是抽中的奖品）
     */
    public static int getPrizeIndex(List<Double> prizes) {
        int random = -1;
        try {
            // 计算总权重
            double sumWeight = 0;
            for (Double weight : prizes) {
                sumWeight += weight;
            }
            // 产生随机数
            double randomNumber;
            randomNumber = Math.random();
            // 根据随机数在所有奖品分布的区域并确定所抽奖品
            double d1 = 0;
            double d2 = 0;
            for (int i = 0; i < prizes.size(); i++) {
                d2 += Double.parseDouble(String.valueOf(prizes.get(i)))
                        / sumWeight;
                if (i == 0) {
                    d1 = 0;
                } else {
                    d1 += Double.parseDouble(String.valueOf(prizes.get(i - 1)))
                            / sumWeight;
                }
                if (randomNumber >= d1 && randomNumber <= d2) {
                    random = i;
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("生成随机数出错，出错原因：" + e.getMessage());
        }
        return random;
    }

    public static void main(String[] args) {
        List<Double> list = new ArrayList<Double>();
        Double hbb = new Double((1 / 3D));
        Double qbb = new Double(Math.random());
        Double gwb = new Double(Math.random());
        list.add(hbb);
        list.add(qbb);
        list.add(gwb);
        System.out.println(getPrizeIndex(list));
    }
}
