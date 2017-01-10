/**
 * @Title LuckyNumberGenerator.java 
 * @Package com.xnjr.mall.core 
 * @Description 
 * @author haiqingzheng  
 * @date 2017年1月9日 上午9:59:41 
 * @version V1.0   
 */
package com.xnjr.mall.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.xnjr.mall.exception.BizException;

/** 
 * @author: haiqingzheng 
 * @since: 2017年1月9日 上午9:59:41 
 * @history:
 */
public class LuckyNumberGenerator {

    /** 
     * @param args 
     * @create: 2017年1月9日 上午9:59:41 haiqingzheng
     * @history: 
     */
    public static void main(String[] args) {
        List<String> exists = new ArrayList<String>();
        // exists.add("10000003");
        // exists.add("10000004");
        // exists.add("10000005");
        List<String> randomNumber = generateLuckyNumbers(10000000L, 10L,
            exists, 10L);
        System.out.println(randomNumber);
        getLuckyNumber(10000000L, 6000L, 1L);

    }

    /**
     * 生成一个随机抽奖号码
     * @param initialValue 初始值
     * @param headcount 总人数
     * @param existValues 已存在号码，不能包含重复值，否则会出错
     * @return 
     * @create: 2017年1月9日 上午10:04:59 haiqingzheng
     * @history:
     */
    public static String generateLuckyNumber(Long initialValue, Long headcount,
            List<String> existValues) {
        if (initialValue == null || headcount == null || existValues == null) {
            throw new BizException("xn000000", "入参有误");
        }
        if (initialValue <= 0) {
            throw new BizException("xn000000", "初始值必须是大于0的整数");
        }
        if (headcount <= 0) {
            throw new BizException("xn000000", "总人数必须是大于0的整数");
        }
        if (existValues.size() >= headcount) {
            throw new BizException("xn000000", "抽奖号码已分配完毕");
        }
        String luckNumber = null;
        while (true) {
            // 生成 1 到 headcount 的随机数
            Long randomNumber = 1 + (long) (Math.random() * headcount);
            luckNumber = Long.valueOf(initialValue + randomNumber).toString();
            if (existValues == null || CollectionUtils.isEmpty(existValues)) {
                break;
            }
            if (!existValues.contains(luckNumber)) {
                break;
            }
        }
        return luckNumber;
    }

    /**
     * 一次生成多个随机抽奖号码
     * @param initialValue 初始值
     * @param headcount 总人数
     * @param existValues 已存在号码，不能包含重复值，否则会出错
     * @param count 生成几个随机号码
     * @return 
     * @create: 2017年1月9日 上午10:40:56 haiqingzheng
     * @history:
     */
    public static List<String> generateLuckyNumbers(Long initialValue,
            Long headcount, List<String> existValues, Long count) {
        List<String> luckNumbers = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            String luckNumber = generateLuckyNumber(initialValue, headcount,
                existValues);
            luckNumbers.add(luckNumber);
            existValues.add(luckNumber);
        }
        Collections.sort(luckNumbers);
        return luckNumbers;
    }

    /**
     * 抽中一个幸运号码
     * @param initialValue 初始值
     * @param headcount 总人数
     * @param outRandom 外部随机数
     * @return 
     * @create: 2017年1月9日 上午10:53:10 haiqingzheng
     * @history:
     */
    private static String getLuckyNumber(Long initialValue, Long headcount,
            Long outRandom) {
        String luckyNumber = null;
        // 生成一个内部5位随机数
        int random = (int) (Math.random() * 90000 + 10000);
        // 内部随机数和外部随机数求和，取余，结果范围是 0 到 headcount-1
        Long remainder = (random + outRandom) % headcount;
        luckyNumber = Long.valueOf(initialValue + remainder + 1).toString();
        return luckyNumber;
    }
}
