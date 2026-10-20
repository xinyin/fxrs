package com.yzj.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.yzj.beans.Report_group;
import com.yzj.beans.Report_groupSum;
import com.yzj.service.Report_groupService;
import com.yzj.service.Report_groupSumService;

public class Report_groupController {
	public static List<Report_group> select1(String startTime,String endTime,String tenantId){
        ApplicationContext application = new ClassPathXmlApplicationContext("applicationContext.xml");
        Report_groupService as = (Report_groupService)application.getBean("report_groupService");
        List<Report_group> li = new ArrayList<Report_group>();
        li = as.select1(startTime, endTime,tenantId);


        return li;
    }
	
	public static List<Report_group> select2(String startTime,String endTime,String tenantId){
        ApplicationContext application = new ClassPathXmlApplicationContext("applicationContext.xml");
        Report_groupService as = (Report_groupService)application.getBean("report_groupService");
        List<Report_group> li = new ArrayList<Report_group>();
        li = as.select2(startTime, endTime,tenantId);


        return li;
    }
	
	public static List<Report_group> select3(String startTime,String endTime,String tenantId){
        ApplicationContext application = new ClassPathXmlApplicationContext("applicationContext.xml");
        Report_groupService as = (Report_groupService)application.getBean("report_groupService");
        List<Report_group> li = new ArrayList<Report_group>();
        li = as.select3(startTime, endTime,tenantId);


        return li;
    }
	
	public static List<Report_group> select4(String startTime,String endTime,String tenantId){
        ApplicationContext application = new ClassPathXmlApplicationContext("applicationContext.xml");
        Report_groupService as = (Report_groupService)application.getBean("report_groupService");
        List<Report_group> li = new ArrayList<Report_group>();
        li = as.select4(startTime, endTime,tenantId);


        return li;
    }
	
	public static List<Report_groupSum> select01(String startTime,String endTime,String tenantId){
        ApplicationContext application = new ClassPathXmlApplicationContext("applicationContext.xml");
        Report_groupSumService as = (Report_groupSumService)application.getBean("report_groupSumService");
        List<Report_groupSum> li = new ArrayList<Report_groupSum>();
        li = as.select1(startTime, endTime,tenantId);


        return li;
    }
	
	public static List<Report_groupSum> select02(String startTime,String endTime,String tenantId){
        ApplicationContext application = new ClassPathXmlApplicationContext("applicationContext.xml");
        Report_groupSumService as = (Report_groupSumService)application.getBean("report_groupSumService");
        List<Report_groupSum> li = new ArrayList<Report_groupSum>();
        li = as.select2(startTime, endTime,tenantId);


        return li;
    }
	
	public static List<Report_groupSum> select03(String startTime,String endTime,String tenantId){
        ApplicationContext application = new ClassPathXmlApplicationContext("applicationContext.xml");
        Report_groupSumService as = (Report_groupSumService)application.getBean("report_groupSumService");
        List<Report_groupSum> li = new ArrayList<Report_groupSum>();
        li = as.select3(startTime, endTime,tenantId);


        return li;
    }
	
	public static List<Report_groupSum> select04(String startTime,String endTime,String tenantId){
        ApplicationContext application = new ClassPathXmlApplicationContext("applicationContext.xml");
        Report_groupSumService as = (Report_groupSumService)application.getBean("report_groupSumService");
        List<Report_groupSum> li = new ArrayList<Report_groupSum>();
        li = as.select4(startTime, endTime,tenantId);


        return li;
    }
	
	public static List<Report_groupSum> select(String startTime,String endTime,String tenantId){
        ApplicationContext application = new ClassPathXmlApplicationContext("applicationContext.xml");
        Report_groupSumService as = (Report_groupSumService)application.getBean("report_groupSumService");
        List<Report_groupSum> li = new ArrayList<Report_groupSum>();
        li = as.select(startTime, endTime,tenantId);


        return li;
    }
}
