/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年12月19日 15:10:36
 *
 */

package com.yzj.jep.app.sub.service.impl;

import cn.hutool.core.date.DateUtil;
import com.yzj.jep.app.bus.model.FundsInvestment;
import com.yzj.jep.app.bus.model.RelFunds;
import com.yzj.jep.app.bus.service.RelFundsService;
import com.yzj.jep.app.ins.enums.RelatedPartyCategoryEnum;
import com.yzj.jep.app.ins.model.TraFunds;
import com.yzj.jep.app.ins.service.TraFundsService;
import com.yzj.jep.app.sub.mapper.InsRelfundsMapper;
import com.yzj.jep.app.sub.model.InsAllfunds;
import com.yzj.jep.app.sub.model.InsRelfunds;
import com.yzj.jep.app.sub.service.InsRelfundsService;
import com.yzj.jep.app.util.DateUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class InsRelfundsServiceImpl extends MyBatisCRUDService<InsRelfunds, Integer> implements InsRelfundsService {

	Logger logger = LoggerFactory.getLogger(InsRelfundsServiceImpl.class);

	@Autowired
	private InsRelfundsMapper insRelfundsMapper;

	@Autowired
	private RelFundsService relFundsService;

	@Override
	public Mapper<InsRelfunds, Integer> getMapper() {
		return insRelfundsMapper;
	}

	@Override
	public void extractCredit(Map map) {
		Date nowTime=new Date();
		//当前时间所在年
		int years= DateUtil.year(nowTime);
		//当前时间所在季度
		int quarter = DateUtil.quarter(nowTime);
		if(1 == quarter){
			years = years - 1;
			quarter = 4;
		}else {
			quarter = quarter - 1;
		}
		// 【当前季度的上个季度】的上季度开始日期
		String startTime = DateUtils.preQuarterBeginDateStr(nowTime);
		// 【当前季度的上个季度】的上季度结束日期
		String endTime = DateUtils.preQuarterEndDateStr(nowTime);
		List<Filter> filters = new Filter.Builder().eq("status","1").build();
		filters.add(new Filter("investmentBalanceTime",Operator.GE, startTime));
		filters.add(new Filter("investmentBalanceTime",Operator.LE, endTime));
		List<Order> orders = new Order.Builder().desc("investmentBalanceTime").build();
		List<RelFunds> relFunds = relFundsService.select(filters,orders);
		if(!relFunds.isEmpty()){
			startExtract(relFunds.get(0),String.valueOf(years), String.valueOf(quarter));
		}
	}

	private void startExtract(RelFunds relFunds, String year,String quarter) {
		try {
			String relIdentifyNumber = relFunds.getRelIdentifyNumber();
			InsRelfunds insRelfunds = new InsRelfunds();

			//关联方姓名
			insRelfunds.setRelName(relFunds.getRelName());
			//关联方证件号
			insRelfunds.setRelNumber(relFunds.getRelIdentifyNumber());
			insRelfunds.setRelType(RelatedPartyCategoryEnum.getCodeStr(relFunds.getRelType()));
			//资金运用类投资余额
			insRelfunds.setCorpInvestFund(relFunds.getFundsInvestmentBalance());
			//投资余额时间
			insRelfunds.setInsFundsDate(relFunds.getInvestmentBalanceTime());
			insRelfunds.setStatus("3");
			insRelfunds.setSubYear(year);
			insRelfunds.setSubQuarter(quarter);

			//该年度季度下有没有统计记录
			List<Filter> filters = new Filter.Builder().eq("subYear", year).build();
			filters.add(new Filter("subQuarter", Operator.EQ, quarter));
			filters.add(new Filter("relNumber", Operator.EQ, relIdentifyNumber));

			if (exists(filters)) {
				InsRelfunds insRefundSelect = get(filters);
				insRelfunds.setPid(insRefundSelect.getPid());
				updateSelective(insRelfunds);
			} else {
				insertSelective(insRelfunds);
			}
		}catch (ServiceException e) {
			logger.error("关联交易明细表数据更新异常：", e);
		}
	}
}
