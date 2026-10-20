package com.yzj.controller;

import com.yzj.beans.Report_annual;
import com.yzj.beans.Report_annualSum;
import com.yzj.service.Report_annualService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class Report_annualController {

    private static final Logger log = LoggerFactory.getLogger(Report_annualController.class);
    /**
     * @desc 保险业务类
     * @param year
     * @return
     */
    public static Report_annual selectBusiness(String year, String code) {
        log.info("查询保险业务类开始");
        ApplicationContext application = new ClassPathXmlApplicationContext("applicationContext.xml");
        Report_annualService annualService = (Report_annualService)application.getBean("Report_annualService");
        Report_annual list = annualService.selectBusiness(year, null, code);
        log.info("查询保险业务类结束");
        return list;
    }

    /**
     * @desc 资金运用类
     * @param year
     * @return
     */
    public static Report_annual selectFunds(String year, String code) {
        ApplicationContext application = new ClassPathXmlApplicationContext("applicationContext.xml");
        Report_annualService annualService = (Report_annualService)application.getBean("Report_annualService");
        Report_annual list = annualService.selectFunds(year, null, code);
        return list;
    }

    /**
     * @desc 利益转移类
     * @param year
     * @return
     */
    public static Report_annual selectInterest(String year, String code) {
        ApplicationContext application = new ClassPathXmlApplicationContext("applicationContext.xml");
        Report_annualService annualService = (Report_annualService)application.getBean("Report_annualService");
        Report_annual list = annualService.selectInterest(year, null, code);
        return list;
    }

    /**
     * @desc 提供服务类
     * @param year
     * @return
     */
    public static Report_annual selectService(String year, String code) {
        ApplicationContext application = new ClassPathXmlApplicationContext("applicationContext.xml");
        Report_annualService annualService = (Report_annualService)application.getBean("Report_annualService");
        Report_annual list = annualService.selectService(year, null, code);
        return list;
    }

    /**
     * @desc 投资入股类
     * @param year
     * @return
     */
    public static Report_annual selectInvestment(String year, String code) {
        ApplicationContext application = new ClassPathXmlApplicationContext("applicationContext.xml");
        Report_annualService annualService = (Report_annualService)application.getBean("Report_annualService");
        Report_annual list = annualService.selectInvestment(year, null, code);
        return list;
    }

    /**
     * @desc 其他类
     * @param year
     * @return
     */
    public static Report_annual selectOther(String year, String code) {
        ApplicationContext application = new ClassPathXmlApplicationContext("applicationContext.xml");
        Report_annualService annualService = (Report_annualService)application.getBean("Report_annualService");
        Report_annual list = annualService.selectOther(year, null, code);
        return list;
    }

    /**
     * @desc 总计
     * @param year
     * @return
     */
    public static Report_annualSum selectAllTable(String year, String code) {
        ApplicationContext application = new ClassPathXmlApplicationContext("applicationContext.xml");
        Report_annualService annualService = (Report_annualService)application.getBean("Report_annualService");
        Report_annualSum annualSum = annualService.selectTableCount(year, null, code);
        return annualSum;
    }
}
