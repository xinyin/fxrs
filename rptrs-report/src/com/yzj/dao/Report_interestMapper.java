package com.yzj.dao;

import java.util.List;
import com.yzj.beans.Report_interest;

public interface Report_interestMapper {
	public List<Report_interest> selectByAll(String startTime,String endTime,String tenantId);


}
