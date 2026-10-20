package com.yzj.controller;

import java.util.ArrayList;
import java.util.List;

import com.yzj.service.Report_serviceService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.yzj.beans.Report_service;

public class Report_serviceController {
	public static List<Report_service> selectByAll(String startTime,String endTime,String tenantId){
        ApplicationContext application = new ClassPathXmlApplicationContext("applicationContext.xml");
        Report_serviceService as = (Report_serviceService)application.getBean("report_serviceService");
        List<Report_service> li = new ArrayList<Report_service>();
        li = as.selectByAll(startTime, endTime,tenantId);


        return li;
    }


}
