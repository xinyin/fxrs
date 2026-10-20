package com.yzj.service;

import java.util.ArrayList;
import java.util.List;

import com.yzj.dao.Report_businessMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yzj.beans.Report_business;

@Service
public class Report_businessService {

	@Autowired
	private Report_businessMapper report_businessMapper;
	
	public List<Report_business> selectByAll(String startTime, String endTime,String tenantId) {
		System.out.println("进入到service");
		System.out.println("接口："+report_businessMapper);
		String tenId = "";
		String stTime = "";

		if (startTime == null) {
			startTime = "1000-01-01";
		}
		if (endTime == null) {
			endTime = "3333-02-02";
		}
		List<Report_business> li = new ArrayList<>();

		li = report_businessMapper.selectByAll(startTime, endTime,tenantId);

		return li;
	}
}
