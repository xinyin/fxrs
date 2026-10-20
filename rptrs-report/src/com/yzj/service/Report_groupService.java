package com.yzj.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yzj.beans.Report_group;
import com.yzj.dao.Report_groupMapper;


@Service
public class Report_groupService {
	@Autowired
	private Report_groupMapper report_groupMapper;
	
	public List<Report_group> select1(String startTime, String endTime,String tenantId){
		if (startTime == null) {
			startTime = "2020";
		}
		if (endTime == null) {
			endTime = "2020";
		}
		startTime = startTime + "-01-01";
		endTime = endTime + "-03-31";
		List<Report_group> li = new ArrayList<>();

		li = report_groupMapper.select(startTime, endTime,tenantId);

		return li;
	}
	
	public List<Report_group> select2(String startTime, String endTime,String tenantId){
		if (startTime == null) {
			startTime = "2020";
		}
		if (endTime == null) {
			endTime = "2020";
		}
		startTime = startTime + "-04-01";
		endTime = endTime + "-06-30";
		List<Report_group> li = new ArrayList<>();

		li = report_groupMapper.select(startTime, endTime,tenantId);

		return li;
	}
	
	public List<Report_group> select3(String startTime, String endTime,String tenantId){
		if (startTime == null) {
			startTime = "2020";
		}
		if (endTime == null) {
			endTime = "2020";
		}
		startTime = startTime + "-07-01";
		endTime = endTime + "-09-30";
		List<Report_group> li = new ArrayList<>();

		li = report_groupMapper.select(startTime, endTime,tenantId);

		return li;
	}
	
	public List<Report_group> select4(String startTime, String endTime,String tenantId){
		if (startTime == null) {
			startTime = "2020";
		}
		if (endTime == null) {
			endTime = "2020";
		}
		startTime = startTime + "-10-01";
		endTime = endTime + "-12-31";
		List<Report_group> li = new ArrayList<>();

		li = report_groupMapper.select(startTime, endTime,tenantId);

		return li;
	}
}
