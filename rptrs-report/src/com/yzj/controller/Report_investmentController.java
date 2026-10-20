package com.yzj.controller;

import java.util.ArrayList;
import java.util.List;

import com.yzj.service.Report_investmentService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.yzj.beans.Report_investment;

public class Report_investmentController {
	public static List<Report_investment> selectByAll(String startTime,String endTime,String tenantId){
        ApplicationContext application = new ClassPathXmlApplicationContext("applicationContext.xml");
        Report_investmentService as = (Report_investmentService)application.getBean("report_investmentService");
        List<Report_investment> li = new ArrayList<Report_investment>();
        li = as.selectByAll(startTime, endTime,tenantId);


        return li;
    }


}
