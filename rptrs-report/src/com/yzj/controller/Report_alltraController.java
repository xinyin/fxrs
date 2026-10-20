package com.yzj.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yzj.beans.Report_alltra;
import com.yzj.service.Report_alltraService;

public class Report_alltraController {
	public static List<Report_alltra> selectAll(String startTime,String endTime,String tenantId){
        ApplicationContext application = new ClassPathXmlApplicationContext("applicationContext.xml");
        Report_alltraService as = (Report_alltraService)application.getBean("report_alltraService");
        List<Report_alltra> li = new ArrayList<Report_alltra>();
        li = as.selectAll(startTime, endTime,tenantId);

        return li;
    }


}
