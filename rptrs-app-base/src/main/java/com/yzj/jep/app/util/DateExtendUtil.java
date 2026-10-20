package com.yzj.jep.app.util;

import cn.hutool.core.date.*;
import cn.hutool.core.util.StrUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author Jaws
 * @date 2021-11-08 10:13
 **/
public class DateExtendUtil extends DateUtil {

    public static final String QUARTER_1 = "1";
    public static final String QUARTER_2 = "2";
    public static final String QUARTER_3 = "3";
    public static final String QUARTER_4 = "4";

    /**
     * 获取sysDate
     *
     * @return 日期
     */
    public static String getYesterdayStr() {
        return format(yesterday(), DatePattern.NORM_DATE_PATTERN);
    }

    public static String getTodayStr() {
        return format(DateUtil.date(), DatePattern.NORM_DATE_PATTERN);
    }

    /**
     * 按格式获取当前日期
     *
     * @param format
     * @return
     */
    public static String getTodyStrByFormat(String format) {
        return format(DateUtil.date(), format);
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

    /**
     * 获取指定日期的明天
     *
     * @param endTime 日期
     * @return 上季度开始日期字符串
     */
    public static DateTime tomorrow(Object endTime) {
        DateTime date = DateUtil.parse((String) endTime, "yyyy-MM-dd");
        return offsetDay(date, 1);
    }


    /**
     * 获得月份，从0开始计数
     *
     * @param date 日期
     * @return 月份，从1开始计数
     */
    public static int monthStartFromOne(Date date) {
        return DateTime.of(date).month() + 1;
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

    /**
     * 判断当前日期是否是季度结束日期
     *
     * @param date 日期
     * @return boolean
     */
    public static boolean isQuarterEndDate(Date date) {
        return isSameDay(endOfQuarter(date), date);
    }


    /**
     * 比较两个季度是否相同
     *
     * @param quarter    季度枚举
     * @param quarterStr 季度字符串
     * @return 是否为同一季度
     * @since 5.4.1
     */
    public static boolean isSameQuarter(final Quarter quarter, final String quarterStr) {
        if (StrUtil.isBlank(quarterStr)) {
            //throw new IllegalArgumentException("The quarterStr must not be null");
            return false;
        }
        return quarter.equals(Quarter.of(Integer.parseInt(quarterStr)));
    }

    /**
     * 是否为1季度
     *
     * @param quarterStr 季度字符串
     * @return 是否为1季度
     * @since 5.4.1
     */
    public static boolean isQuarter1(final String quarterStr) {
        return isSameQuarter(Quarter.Q1, quarterStr);
    }

    /**
     * 是否为2季度
     *
     * @param quarterStr 季度字符串
     * @return 是否为2季度
     * @since 5.4.1
     */
    public static boolean isQuarter2(final String quarterStr) {
        return isSameQuarter(Quarter.Q2, quarterStr);
    }

    /**
     * 是否为3季度
     *
     * @param quarterStr 季度字符串
     * @return 是否为3季度
     * @since 5.4.1
     */
    public static boolean isQuarter3(final String quarterStr) {
        return isSameQuarter(Quarter.Q3, quarterStr);
    }

    /**
     * 是否为4季度
     *
     * @param quarterStr 季度字符串
     * @return 是否为4季度
     * @since 5.4.1
     */
    public static boolean isQuarter4(final String quarterStr) {
        return isSameQuarter(Quarter.Q4, quarterStr);
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
     * 获取上季度开始日期
     *
     * @param date 日期
     * @return 上季度开始日期字符串
     */
    public static String preQuarterBeginDateStr(Date date) {
        return format(preQuarterBeginDate(date), DatePattern.NORM_DATE_PATTERN);
    }

    /**
     * 获取上季度中间日期
     * 如果是1季度，则取去年的4季度
     *
     * @param date 日期
     * @return DateTime
     */
    public static DateTime preQuarterMiddleDate(Date date) {
        return offsetMonth(preQuarterEndDate(date), -1);
    }

    /**
     * 获取上季度中间日期
     * 如果是1季度，则取去年的4季度
     *
     * @param date 日期
     * @return DateTime
     */
    public static String preQuarterMiddleDateStr(Date date) {
        return format(preQuarterMiddleDate(date), DatePattern.NORM_DATE_PATTERN);
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
     * 获取上季度结束日期
     *
     * @param date 日期
     * @return 上季度结束日期字符串
     */
    public static String preQuarterEndDateStr(Date date) {
        return format(preQuarterEndDate(date), DatePattern.NORM_DATE_PATTERN);
    }

    /**
     * 获取 上上季度 结束日期
     *
     * @param date 日期
     * @return 上季度结束日期字符串
     */
    public static DateTime beforePreQuarterBeginDate(Date date) {
        return preQuarterBeginDate(preQuarterBeginDate(date));
    }

    /**
     * 获取 上上季度 结束日期
     *
     * @param date 日期
     * @return 上季度结束日期字符串
     */
    public static DateTime beforePreQuarterEndDate(Date date) {
        return preQuarterEndDate(preQuarterBeginDate(date));
    }


    /**
     * 指定日期的上月
     *
     * @return
     */
    public static DateTime lastMonth(Date date) {
        return offsetMonth(date, -1);
    }


    /**
     * 指定日期的上月月初日期：xx月01日
     *
     * @return 上月月初日期
     */
    public static DateTime beginOfLastMonth(Date date) {
        return beginOfMonth(lastMonth(date));
    }

    /**
     * 指定日期的上月月初日期：xx月01日
     *
     * @return 上月月初日期字符串 yyyyMMdd
     */
    public static String beginOfLastMonthStr(Date date) {
        return format(beginOfMonth(lastMonth(date)), DatePattern.PURE_DATE_PATTERN);
    }

    /**
     * 指定日期的上月月初日期
     *
     * @return 上月月初日期字符串 yyyy-mm-dd
     */
    public static String beginOfLastMonthStrAnother(Date date) {
        return format(beginOfMonth(lastMonth(date)), DatePattern.NORM_DATE_PATTERN);
    }

    /**
     * 指定日期的上月月末日期
     *
     * @return 上月月末日期
     */
    public static Date endOfLastMonth(Date date) {
        LocalDate localDate = endOfMonth(lastMonth(date)).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 指定日期的上月月末日期 yyyyMMdd
     *
     * @return 上月月末日期字符串
     */
    public static String endOfLastMonthStr(Date date) {
        return format(endOfLastMonth(date), DatePattern.PURE_DATE_PATTERN);
    }

    /**
     * 指定日期的上月月末日期
     *
     * @return 上月月末日期字符串 yyyy-mm-dd
     */
    public static String endOfLastMonthStrAnother(Date date) {
        return format(endOfLastMonth(date), DatePattern.NORM_DATE_PATTERN);
    }

    /**
     * 获取昨天日期字符串 yyyyMMdd
     *
     * @return 日期字符串
     */
    public static String getYesterdayPureStr() {
        return format(yesterday(), DatePattern.PURE_DATE_PATTERN);
    }

    /**
     * 获取上月度开始日期
     *
     * @param date 日期
     * @return 上月度开始日期字符串
     */
    public static String preMonthBeginDateStr(Date date) {
        return format(preMonthBeginDate(date), DatePattern.NORM_DATE_PATTERN);
    }

    /**
     * 获取上月度结束日期
     *
     * @param date 日期
     * @return 上月度结束日期字符串
     */
    public static String preMonthEndDateStr(Date date) {
        return format(preMonthEndDate(date), DatePattern.NORM_DATE_PATTERN);
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


    public static String getStartDateTimeOfMonth(String yearParam, String monthParam) {
        int year = Integer.parseInt(yearParam); // 将年份字符串转换为整数
        int month = Integer.parseInt(monthParam); // 将月份字符串转换为整数
        LocalDateTime startDateTime = LocalDateTime.of(year, month, 1, 0, 0, 0); // 当月1日 00:00:00
        return formatDateTime(startDateTime);
    }

    // 获取月份的终止日期时间（格式：yyyy-MM-dd HH:mm:ss）
    public static String getEndDateTimeOfMonth(String yearParam, String monthParam) {
        int year = Integer.parseInt(yearParam); // 将年份字符串转换为整数
        int month = Integer.parseInt(monthParam); // 将月份字符串转换为整数
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDateTime endDateTime = LocalDateTime.of(year, month, yearMonth.atEndOfMonth().getDayOfMonth(), 23, 59, 59); // 当月最后一天 23:59:59
        return formatDateTime(endDateTime);
    }

    // 获取当前年度的开始时间（格式：yyyy-MM-dd HH:mm:ss）
    public static String getStartDateTimeOfYear() {
        int year = LocalDateTime.now().getYear(); // 获取当前年份
        LocalDateTime startDateTime = LocalDateTime.of(year, 1, 1, 0, 0, 0); // 当年1月1日 00:00:00
        return formatDateTime(startDateTime);
    }

    // 获取当前年度的结束时间（格式：yyyy-MM-dd HH:mm:ss）
    public static String getEndDateTimeOfYear() {
        int year = LocalDateTime.now().getYear(); // 获取当前年份
        LocalDateTime endDateTime = LocalDateTime.of(year, 12, 31, 23, 59, 59); // 当年12月31日 23:59:59
        return formatDateTime(endDateTime);
    }

    // 格式化日期时间为 yyyy-MM-dd HH:mm:ss
    private static String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }

}
