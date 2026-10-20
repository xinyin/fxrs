package com.yzj.service;

import java.util.ArrayList;
import java.util.List;

import com.yzj.dao.Report_interestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yzj.beans.Report_interest;

@Service
public class Report_interestService {
	
	@Autowired
	private Report_interestMapper report_interestMapper;
	public List<Report_interest> selectByAll(String startTime, String endTime,String tenantId) {
		if (startTime == null) {
			startTime = "1000-01-01";
		}
		if (endTime == null) {
			endTime = "3333-02-02";
		}
		List<Report_interest> li = new ArrayList<Report_interest>();

		li = report_interestMapper.selectByAll(startTime, endTime,tenantId);

		return li;
		
	}


}
