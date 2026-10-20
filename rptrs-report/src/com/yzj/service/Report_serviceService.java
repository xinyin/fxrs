package com.yzj.service;

import java.util.ArrayList;
import java.util.List;

import com.yzj.dao.Report_serviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yzj.beans.Report_service;

@Service
public class Report_serviceService {
	@Autowired
	private Report_serviceMapper report_serviceMapper;
	public List<Report_service> selectByAll(String startTime, String endTime,String tenantId) {
		if (startTime == null) {
			startTime = "1000-01-01";
		}
		if (endTime == null) {
			endTime = "3333-02-02";
		}
		List<Report_service> li = new ArrayList<Report_service>();

		li = report_serviceMapper.selectByAll(startTime, endTime,tenantId);

		return li;
		
	}


}
