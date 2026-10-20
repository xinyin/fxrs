package com.yzj.dao;

import java.util.List;
import com.yzj.beans.Report_investment;

public interface Report_investmentMapper {
	public List<Report_investment> selectByAll11(String startTime,String endTime,String tenantId);


}
