package com.yzj.controller;

import java.util.ArrayList;
import java.util.List;

import com.yzj.service.Report_fundsService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yzj.beans.Report_funds;

public class Report_fundsController {
	public static List<Report_funds> selectByAll(String startTime,String endTime,String tenantId){
        ApplicationContext application = new ClassPathXmlApplicationContext("applicationContext.xml");
        Report_fundsService as = (Report_fundsService)application.getBean("report_fundsService");
        List<Report_funds> li = new ArrayList<Report_funds>();
        li = as.selectByAll(startTime, endTime,tenantId);


        return li;
    }


}
