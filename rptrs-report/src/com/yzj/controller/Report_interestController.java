package com.yzj.controller;

import java.util.ArrayList;
import java.util.List;

import com.yzj.service.Report_interestService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yzj.beans.Report_interest;

public class Report_interestController {
	public static List<Report_interest> selectByAll(String startTime,String endTime,String tenantId){
        ApplicationContext application = new ClassPathXmlApplicationContext("applicationContext.xml");
        Report_interestService as = (Report_interestService)application.getBean("report_interestService");
        List<Report_interest> li = new ArrayList<Report_interest>();
        li = as.selectByAll(startTime, endTime,tenantId);


        return li;
    }


}
