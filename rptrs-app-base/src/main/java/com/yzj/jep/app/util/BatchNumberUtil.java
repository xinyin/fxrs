package com.yzj.jep.app.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BatchNumberUtil {
    /*
     *此方法只是生成一个流水号
     * 如： 20200403105843380
     * */
    public static String makeBatchNumber() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Date date = new Date();
        String formatStr = formatter.format(date);
        return formatStr;
    }

    /*
     *此方法只是生成一个  前缀+流水号
     * 前缀  test_
     * 结果   test_20200403105843380
     * */
    public static String makeBatchNumber(String prefix) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Date date = new Date();
        String formatStr = formatter.format(date);
        return prefix + formatStr;
    }

    /*
     *此方法只是生成一个  前缀+流水号+后缀
     * 前缀  test_   后缀 .xls
     * 结果   test_20200403105843380.xls
     * */
    public static String makeBatchNumber(String prefix, String suffix) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Date date = new Date();
        String formatStr = formatter.format(date);
        return prefix + formatStr + suffix;
    }
}
