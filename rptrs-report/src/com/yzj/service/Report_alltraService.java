package com.yzj.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.yzj.dao.Report_alltraMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yzj.beans.Report_alltra;

@Service
public class Report_alltraService {
	@Autowired
	private Report_alltraMapper report_alltraMapper;
	
	public List<Report_alltra> selectAll(String startTime, String endTime,String tenantId){
		
		if (startTime == null) {
			startTime = "1000-01-01";
		}
		if (endTime == null) {
			endTime = "3333-02-02";
		}
		
		List<Report_alltra> li = new ArrayList<Report_alltra>();
		li = report_alltraMapper.selectAll(startTime, endTime,tenantId);
		for(Report_alltra report_alltra : li){
			String name = report_alltra.getNAME();
			String number = report_alltra.getNumber();
			BigDecimal ii =report_alltraMapper.selectAll1(number, startTime, endTime, tenantId);
			report_alltra.setIi(ii);
			BigDecimal a =report_alltraMapper.selectAll2(number, startTime, endTime, tenantId);
			report_alltra.setA(a);
			BigDecimal b =report_alltraMapper.selectAll3(number, startTime, endTime, tenantId);
			report_alltra.setB(b);
		    BigDecimal c =report_alltraMapper.selectAll4(number, startTime, endTime, tenantId);
			report_alltra.setC(c);
			BigDecimal d =report_alltraMapper.selectAll5(number, startTime, endTime, tenantId);
			report_alltra.setD(d);
		}
		return li;
	}


}
