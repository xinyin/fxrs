package com.yzj.dao;

import java.util.List;

import com.yzj.beans.Report_group;

public interface Report_groupMapper {
	public List<Report_group> select(String startTime,String endTime,String tenantId);
}
