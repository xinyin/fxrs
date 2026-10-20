package com.yzj.dao;

import java.util.List;

import com.yzj.beans.Report_groupSum;

public interface Report_groupSumMapper {
	public List<Report_groupSum> select(String startTime,String endTime,String tenantId);
}
