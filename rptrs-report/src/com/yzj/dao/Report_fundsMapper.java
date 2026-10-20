package com.yzj.dao;

import java.util.List;
import com.yzj.beans.Report_funds;

public interface Report_fundsMapper {
	public List<Report_funds> selectByAll(String startTime,String endTime,String tenantId);


}
