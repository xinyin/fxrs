package com.yzj.jep.app.util;

import cn.hutool.core.date.DateUtil;

import java.util.Date;

/**
 * @author by Liqiang
 * @Date 2023/1/13
 * @Description 生成版本号
 */
public class IntegerUtils {

    /**
     * 计算版本号
     * @param flag
     * @param recordNumber
     * @return
     */
    public static String recordNumber(boolean flag, String recordNumber) {
       if(flag){
           String version = recordNumber.split("-")[1];
           String recordVersion = recordNumber.split("-")[0];
           int count = Integer.parseInt(version);
           int addition = addition(count, 1);
           String versionNew = String.valueOf(addition);
           if(addition<10){
               versionNew = "0".concat(versionNew);
           }
           return recordVersion.concat("-").concat(versionNew);
       }
       return "V".concat(DateUtil.format(new Date(), "yyyyMM")).concat("-").concat("01");
    }

    /**
     * 使用位运算实现int数据的加法
     * (a & b) << 1 => 相加 进位结果
     *  a ^ b => 相加 不进位结果
     *  将上述两个结果相加直到无进位
     * @param a
     * @param b
     * @return
     */
    public static int addition(int a, int b){
        int carry;
        while(b != 0){
            carry = (a & b) << 1;
            a = a ^ b;
            b = carry;
        }
        return a;
    }


    public static void main(String[] args) {
        System.out.printf("===========" + recordNumber(true, "V202209-01"));
    }
}
