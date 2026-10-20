package com.yzj.controller;

import com.yzj.beans.Report_annual;
import com.yzj.beans.Report_annualSum;
import com.yzj.service.Report_annualService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Report_quarterController {
    private static final Logger log = LoggerFactory.getLogger(Report_quarterController.class);

    /**
     * @desc 保险业务类
     * @param year
     * @return
     */
    public static List<Report_annual> selectBusiness(String year, String quarter, String code) {
        log.info("季度-保险业务类-开始！");
        System.out.println("**************************");
        List<Report_annual> list = new ArrayList<>();
        ApplicationContext application = new ClassPathXmlApplicationContext("applicationContext.xml");
        Report_annualService annualService = application.getBean(Report_annualService.class);
        log.info("***********：" + annualService.getClass().getName());
        Report_annual annual = annualService.selectBusiness(year, quarter, code);
        if (annual == null) {
            list.add(new Report_annual());
            return list;
        }
        list.add(annual);
        log.info("季度-保险业务类-结束！");
        return list;
    }

    /**
     * @desc 资金运用类
     * @param year
     * @return
     */
    public static List<Report_annual> selectFunds(String year, String quarter, String code) {
        log.info("季度-资金运用类-开始");
        List<Report_annual> list = new ArrayList<>();
        ApplicationContext application = new ClassPathXmlApplicationContext("applicationContext.xml");
        Report_annualService annualService = application.getBean(Report_annualService.class);
        log.info("***********：" + annualService.getClass().getName());
        Report_annual annual = annualService.selectFunds(year, quarter, code);
        if (annual == null) {
            list.add(new Report_annual());
            return list;
        }
        list.add(annual);
        log.info("季度-资金运用类-结束");
        return list;
    }

    /**
     * @desc 利益转移类
     * @param year
     * @return
     */
    public static List<Report_annual> selectInterest(String year, String quarter, String code) {
        log.info("季度-利益转移类-开始");
        List<Report_annual> list = new ArrayList<>();
        ApplicationContext application = new ClassPathXmlApplicationContext("applicationContext.xml");
        Report_annualService annualService = application.getBean(Report_annualService.class);
        log.info("***********：" + annualService.getClass().getName());
        Report_annual annual = annualService.selectInterest(year, quarter, code);
        if (annual == null) {
            list.add(new Report_annual());
            return list;
        }
        list.add(annual);
        log.info("季度-利益转移类-结束");
        return list;
    }

    /**
     * @desc 提供服务类
     * @param year
     * @return
     */
    public static List<Report_annual> selectService(String year, String quarter, String code) {
        log.info("季度-提供服务类-开始");
        List<Report_annual> list = new ArrayList<>();
        ApplicationContext application = new ClassPathXmlApplicationContext("applicationContext.xml");
        Report_annualService annualService = application.getBean(Report_annualService.class);
        log.info("***********：" + annualService.getClass().getName());
        Report_annual annual = annualService.selectService(year, quarter, code);
        if (annual == null) {
            list.add(new Report_annual());
            return list;
        }
        list.add(annual);
        log.info("季度-提供服务类-结束");
        return list;
    }

    /**
     * @desc 投资入股类
     * @param year
     * @return
     */
    public static List<Report_annual> selectInvestment(String year, String quarter, String code) {
        log.info("季度-投资入股类-开始");
        List<Report_annual> list = new ArrayList<>();
        ApplicationContext application = new ClassPathXmlApplicationContext("applicationContext.xml");
        Report_annualService annualService = application.getBean(Report_annualService.class);
        log.info("***********：" + annualService.getClass().getName());
        Report_annual annual = annualService.selectInvestment(year, quarter, code);
        if (annual == null) {
            list.add(new Report_annual());
            return list;
        }
        list.add(annual);
        log.info("季度-投资入股类-结束");
        return list;
    }

    /**
     * @desc 其他类
     * @param year
     * @return
     */
    public static List<Report_annual> selectOther(String year, String quarter, String code) {
        log.info("季度-其他类-开始");
        List<Report_annual> list = new ArrayList<>();
        ApplicationContext application = new ClassPathXmlApplicationContext("applicationContext.xml");
        Report_annualService annualService = application.getBean(Report_annualService.class);
        log.info("***********：" + annualService.getClass().getName());
        Report_annual annual = annualService.selectOther(year, quarter, code);
        if (annual == null) {
            list.add(new Report_annual());
            return list;
        }
        list.add(annual);
        log.info("季度-其他类-结束");
        return list;
    }

    /**
     * @desc 总计
     * @param year
     * @return
     */
    public static List<Report_annualSum> selectAllTable(String year, String quarter, String code) {
        log.info("季度-总计类-开始");
        List<Report_annualSum> list = new ArrayList<>();
        ApplicationContext application = new ClassPathXmlApplicationContext("applicationContext.xml");
        Report_annualService annualService = application.getBean(Report_annualService.class);
        log.info("***********：" + annualService.getClass().getName());
        Report_annualSum annualSum = annualService.selectTableCount(year, quarter, code);
        if (annualSum != null) {
            list.add(annualSum);
        }
        log.info("季度-总计类-结束");
        return list;
    }

    /**
     * @desc 获取季度或者年份
     * @param year
     * @param quater
     * @return
     */
    public static List<String> selectYearAndQuarter (String year, String quater) {
        List<String> list = new ArrayList<>();
        if (quater != null && !quater.trim().equals("") && !quater.equals("ALL")) {
            list.add(quater);
            return list;
        }

        if (year != null && !year.trim().equals("")) {
            list.add(year);
            return list;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
        Date date = new Date();
        year = sdf.format(date).substring(0,4);
        list.add(year);
        return list;
    }
}
