package com.yzj.dao;

import java.math.BigDecimal;
import java.util.List;
import com.yzj.beans.Report_alltra;

public interface Report_alltraMapper {
	public List<Report_alltra> selectAll(String startTime,String endTime,String tenantId);
	public BigDecimal selectAll1(String number,String startTime,String endTime,String tenantId);
	public BigDecimal selectAll2(String number,String startTime,String endTime,String tenantId);
	public BigDecimal selectAll3(String number,String startTime,String endTime,String tenantId);
	public BigDecimal selectAll4(String number,String startTime,String endTime,String tenantId);
	public BigDecimal selectAll5(String number,String startTime,String endTime,String tenantId);


}
