package com.yzj.controller;

import com.yzj.beans.Report_business;
import com.yzj.service.Report_businessService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class Report_businessController {
    public static List<Report_business> selectByAll(String startTime,String endTime,String tenantId){
        ApplicationContext application = new ClassPathXmlApplicationContext("applicationContext.xml");
        Report_businessService as = (Report_businessService)application.getBean("report_businessService");
        List<Report_business> li = new ArrayList<Report_business>();
        li = as.selectByAll(startTime, endTime,tenantId);


        return li;
    }


}
