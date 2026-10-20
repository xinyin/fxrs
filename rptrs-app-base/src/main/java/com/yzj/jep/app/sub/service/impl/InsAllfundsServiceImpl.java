/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年12月19日 15:10:34
 *
 */

package com.yzj.jep.app.sub.service.impl;

import cn.hutool.core.date.DateUtil;
import com.yzj.jep.app.bus.model.FundsInvestment;
import com.yzj.jep.app.bus.service.FundsInvestmentService;
import com.yzj.jep.app.sub.mapper.InsAllfundsMapper;
import com.yzj.jep.app.sub.model.InsAllfunds;
import com.yzj.jep.app.sub.service.InsAllfundsService;
import com.yzj.jep.app.util.DateUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import com.yzj.jep.sys.service.AppConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class InsAllfundsServiceImpl extends MyBatisCRUDService<InsAllfunds, Integer> implements InsAllfundsService {
	
	Logger	logger	= LoggerFactory.getLogger(InsAllfundsServiceImpl.class);
	
	@Autowired
	private InsAllfundsMapper insAllfundsMapper;

	@Autowired
	private FundsInvestmentService fundsInvestmentService;
	
	@Override
	public Mapper<InsAllfunds, Integer> getMapper() {
		return insAllfundsMapper;
	}


	private static AppConfigService appConfigService = SpringContextHolder.getBean(AppConfigService.class);

	@Override
	public void extractCredit(Map map) {
		Date nowDate=new Date();
		int years= DateUtil.year(nowDate);
		int quarter = DateUtil.quarter(nowDate);
		List<Filter> filters = new Filter.Builder().eq("status","1").build();
		filters.add(new Filter("delflag",Operator.EQ, "1"));
		List<Order> orders = new Order.Builder().desc("investmentBalanceTime").build();
		if(1 == quarter){
			years = years - 1;
			quarter = 4;
		}else {
			quarter = quarter - 1;
		}

		// 【当前季度的上个季度】的上季度开始日期
		String startTime = DateUtils.preQuarterBeginDateStr(nowDate);
		// 【当前季度的上个季度】的上季度结束日期
		String endTime = DateUtils.preQuarterEndDateStr(nowDate);
		filters.add(new Filter("investmentBalanceTime",Operator.GE, startTime));
		filters.add(new Filter("investmentBalanceTime",Operator.LE, endTime));

		List<FundsInvestment> fundsInvestments = fundsInvestmentService.select(filters,orders);
		if(!fundsInvestments.isEmpty()){
			startExtract(fundsInvestments.get(0),String.valueOf(years), String.valueOf(quarter));
		}

	}
	private void startExtract(FundsInvestment fundsInvestment , String subYear, String subQuarter){
		try {
			InsAllfunds insAllfunds = new InsAllfunds();

			//流动性资产投资余额-投资关联方
			insAllfunds.setMobilityBalance(fundsInvestment.getMobilityBalance());
			//不动产类资产投资余额-投向关联方
			insAllfunds.setImvbsInvestBalance(fundsInvestment.getRealEstateBalance());
			//境外投资余额-投向关联方
			insAllfunds.setAbroadFund(fundsInvestment.getForeignBalance());
			//固定收益类资产投余额
			insAllfunds.setFixedProfitBalance(fundsInvestment.getFixedIncomeBalance());
			// 权益类资产投资余额
			insAllfunds.setInvestAmount(fundsInvestment.getBalanceInvestment());
			//保险资产管理产品-投资关联方
			insAllfunds.setInsManageProFund(fundsInvestment.getInsuranceManagementBalance());
			//商业银行理财产品-投资关联方
			insAllfunds.setComBankFinFund(fundsInvestment.getBankFinancialBalance());
			//银行业金融机构信资产支持证券-投资关联方
			insAllfunds.setBankCreditFund(fundsInvestment.getBankNegotiableSecuritiesBalance());
			//信托公司集合资金信托计划-投资关联方
			insAllfunds.setTrustFund(fundsInvestment.getTrustSchemeBalance());
			//证券公司专项资产管理计划-投资关联方
			insAllfunds.setBondSpecialFund(fundsInvestment.getNegotiableSecuritiesSpecialPlanBalance());
			//保险资管公司发行资产支持计划-投资关联方
			insAllfunds.setInsCompanyPlanFund(fundsInvestment.getInsuranceFundsSupportPlanBalance());
			//其他-投资关联方
			insAllfunds.setOtherTotalAmount(fundsInvestment.getOtherInvestmentBalance());
			//全部投资余额
			insAllfunds.setAllInvestmentBalance(fundsInvestment.getAllInvestmentBalance());

			//投资余额时间
			insAllfunds.setInsFundsDate(fundsInvestment.getInvestmentBalanceTime());
			insAllfunds.setSubQuarter(subQuarter);
			insAllfunds.setSubYear(subYear);
			insAllfunds.setStatus("3");
			//该年度季度下有没有统计记录
			List<Filter> filters = new Filter.Builder().eq("subYear",subYear).build();
			filters.add(new Filter("subQuarter", Operator.EQ,subQuarter));
			if(exists(filters)){
				InsAllfunds insAllFundsSelect = get(filters);
				insAllfunds.setPid(insAllFundsSelect.getPid());
				updateSelective(insAllfunds);
			}else {

				insertSelective(insAllfunds);
			}
		}catch (
				ServiceException e){
			logger.error("关联交易明细表数据更新异常：",e);
		}

	}
}