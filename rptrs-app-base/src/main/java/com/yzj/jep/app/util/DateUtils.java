package com.yzj.jep.app.util;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;

import java.util.Date;

import static cn.hutool.core.date.DateUtil.*;
import static com.yzj.jep.commons.DateUtils.format;
import static org.apache.commons.lang.time.DateUtils.isSameDay;

/**
 * @author by Liqiang
 * @Date 2022/12/27
 * @Description 日期工具类
 */
public class DateUtils {

    /**
     * 获取上季度开始日期
     *
     * @param date 日期
     * @return 上季度开始日期字符串
     */
    public static String preQuarterBeginDateStr(Date date) {
        return format(preQuarterBeginDate(date), DatePattern.NORM_DATE_PATTERN);
    }

    /**
     * 获取上季度结束日期
     *
     * @param date 日期
     * @return 上季度结束日期字符串
     */
    public static String preQuarterEndDateStr(Date date) {
        return format(preQuarterEndDate(date), DatePattern.NORM_DATE_PATTERN);
    }

    /**
     * 获取上季度开始日期
     * 如果是1季度，则取去年的四季度
     *
     * @param date 日期
     * @return 上季度开始日期字符串
     */
    public static DateTime preQuarterBeginDate(Date date) {
        return beginOfQuarter(preQuarterEndDate(date));
    }

    /**
     * 获取上季度结束日期
     *
     * @param date 日期
     * @return 上季度结束日期字符串
     */
    public static DateTime preQuarterEndDate(Date date) {
        // 如果为date季度第1天，则用上一天去取季度开始日期，否则取当前date的季度第1天的上一天
        return isQuarterBeginDate(date) ? yesterday(date) : yesterday(beginOfQuarter(date));
    }

    /**
     * 判断当前日期是否是季度起始日期
     *
     * @param date 日期
     * @return boolean
     */
    public static boolean isQuarterBeginDate(Date date) {
        return isSameDay(beginOfQuarter(date), date);
    }

    public static DateTime preMonthBeginDate(Date date) {
        return beginOfMonth(preMonthEndDate(date));
    }

    public static DateTime preMonthEndDate(Date date) {
        // 如果为date月度第1天，则用上一天去取月度开始日期，否则取当前date的月度第1天的上一天
        return isMonthBeginDate(date) ? yesterday(date) : yesterday(beginOfMonth(date));
    }

    public static boolean isMonthBeginDate(Date date) {
        return isSameDay(beginOfMonth(date), date);
    }

    /**
     * 获取上月度开始日期
     *
     * @param date 日期
     * @return 上月度开始日期字符串
     */
    public static String preMonthBeginDateStr(Date date) {
        return format(preMonthBeginDate(date), DatePattern.PURE_DATE_PATTERN);
    }

    /**
     * 获取上月度结束日期
     *
     * @param date 日期
     * @return 上月度结束日期字符串
     */
    public static String preMonthEndDateStr(Date date) {
        return format(preMonthEndDate(date), DatePattern.PURE_DATE_PATTERN);
    }

    /**
     * 获取指定日期的昨天
     *
     * @param date 日期
     * @return 上季度开始日期字符串
     */
    public static DateTime yesterday(Date date) {
        return offsetDay(date, -1);
    }
}
