package com.yzj.service;

import java.util.ArrayList;
import java.util.List;

import com.yzj.dao.Report_fundsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yzj.beans.Report_funds;

@Service
public class Report_fundsService {
	@Autowired
	private Report_fundsMapper report_fundsMapper;
	public List<Report_funds> selectByAll(String startTime, String endTime,String tenantId) {

		String tenId = "";
		String stTime = "";

		if (startTime == null) {
			startTime = "1000-01-01";
		}
		if (endTime == null) {
			endTime = "3333-02-02";
		}
		List<Report_funds> li = new ArrayList<>();

		li = report_fundsMapper.selectByAll(startTime, endTime,tenantId);

		return li;
	}


}
