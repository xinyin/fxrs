package com.yzj.dao;

import java.util.List;

import com.yzj.beans.Report_service;

public interface
Report_serviceMapper {
	public List<Report_service> selectByAll(String startTime,String endTime,String tenantId);

}
