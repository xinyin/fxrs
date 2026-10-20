package com.yzj.dao;

import java.util.List;

import com.yzj.beans.Report_business;

public interface Report_businessMapper {
	public List<Report_business> selectByAll(String startTime,String endTime,String tenantId);



}