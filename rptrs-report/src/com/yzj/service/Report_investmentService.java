package com.yzj.service;

import java.util.ArrayList;
import java.util.List;

import com.yzj.dao.Report_investmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yzj.beans.Report_investment;

@Service
public class Report_investmentService {
	
	@Autowired
	private Report_investmentMapper report_investmentMapper;
	public List<Report_investment> selectByAll(String startTime, String endTime,String tenantId) {
		if (startTime == null) {
			startTime = "1000-01-01";
		}
		if (endTime == null) {
			endTime = "3333-02-02";
		}
		List<Report_investment> li = new ArrayList<Report_investment>();

		li = report_investmentMapper.selectByAll11(startTime, endTime,tenantId);

		return li;
		
	}


}
