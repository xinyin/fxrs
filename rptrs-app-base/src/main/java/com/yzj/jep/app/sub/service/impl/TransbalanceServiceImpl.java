/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月02日 15:18:56
 *
 */

package com.yzj.jep.app.sub.service.impl;

import com.yzj.jep.app.sub.mapper.*;
import com.yzj.jep.app.sub.model.Organ;
import com.yzj.jep.app.sub.model.Person;
import com.yzj.jep.app.sub.service.OrganService;
import com.yzj.jep.app.sub.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.sub.service.TransbalanceService;
import com.yzj.jep.app.sub.model.Transbalance;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service(value = "TransbalanceServiceImpl")
@Transactional
public class TransbalanceServiceImpl extends MyBatisCRUDService<Transbalance, Integer> implements TransbalanceService {
	
	Logger	logger	= LoggerFactory.getLogger(TransbalanceServiceImpl.class);
	
	@Autowired
	private TransbalanceMapper	transbalanceMapper;
	@Autowired
	PersonMapper personMapper;
	@Autowired
	private PersonService personService;
	@Autowired
	private OrganService organService;
	@Autowired
	OrganMapper organMapper;
	@Autowired
	CreditMapper creditMapper;
	@Autowired
	LoanMapper loanMapper;
	@Autowired
	NonloanMapper nonloanMapper;
	@Autowired
	InfoMapper infoMapper;
	
	@Override
	public Mapper<Transbalance, Integer> getMapper() {
		return transbalanceMapper;
	}

	@Override
	public void getRelOrgList() throws Exception {
		//资本净额
		BigDecimal captial = infoMapper.getCaptial();
		System.out.println("getCaptial===" + captial);
		//transbalanceMapper.deleteAll();
		//获取当前时间
		Date now = new Date();
		System.out.println(now);
		//本季度初时间
		String startNow = null;
		//本季度末时间
		String endNow = null;
		//上季度初时间
		String startTime = null;
		//上季度末时间
		String endTime = null;
		DateFormat dateformat = new SimpleDateFormat("yyyy-MM");
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		//当前年度
		String year = dateformat.format(now).split("-")[0];
		//当前月份
		String month = dateformat.format(now).split("-")[1];
		//上一年度
		int i = Integer.parseInt(year) - 1;
		String before = i + "";
		int timeToNum = Integer.valueOf(month);
		System.out.println(year + "   year");
		System.out.println(before + "   before");
		System.out.println(timeToNum + "   timeToNum");
		//判断上季度时间标准
		if (timeToNum >= 1 && timeToNum <= 3) {
			startNow = year + "-01-01";
			endNow = year + "-03-31";
			startTime = before + "-10-01";
			endTime = before + "-12-31";
		} else if (timeToNum >= 4 && timeToNum <= 6) {
			startNow = year + "-04-01";
			endNow = year + "-06-30";
			startTime = year + "-01-01";
			endTime = year + "-03-31";
		} else if (timeToNum >= 7 && timeToNum <= 9) {
			startNow = year + "-07-01";
			endNow = year + "-09-30";
			startTime = year + "-04-01";
			endTime = year + "-06-30";
		} else {
			startNow = year + "-10-01";
			endNow = year + "-12-31";
			startTime = year + "-07-01";
			endTime = year + "-09-30";
		}
		System.out.println("startNow==" + startNow);
		System.out.println("endNow==" + endNow);
		System.out.println("startTime==" + startTime);
		System.out.println("endTime==" + endTime);
		Date startN = format.parse(startNow);
		String staN = format.format(startN);
		Date endN = format.parse(endNow);
		String enN = format.format(endN);
		Date start = format.parse(startTime);
		Date end = format.parse(endTime);
		System.out.println("start===" + start + "===end" + end);
		System.out.println("startN===" + startN + "===endN" + endN);
		List<Person> allPerson = personService.selectAll();
		List<Organ> allOrg = organService.selectAll();
		//关联自然人
		for (Person p : allPerson) {
			Transbalance transbalance = new Transbalance();
			//关联方名称/自然人姓名
			String name = p.getName();
			transbalance.setRelatedPartyName(p.getName());
			//关联方代码
			String identityNo = p.getIdentityNo();
			transbalance.setRelatedPartyCertNo(p.getIdentityNo());
			//关联方类型
			String type = creditMapper.getType(identityNo);
			transbalance.setRelatedPartyType(creditMapper.getType(identityNo));
			//各项贷款
			BigDecimal loan = loanMapper.getLoan(identityNo, start, end);
			if (loan == null) {
				loan = new BigDecimal(0);
			}
			transbalance.setLoansBalance(loan);
			//债券投资
			BigDecimal bondInvestment = loanMapper.getBondInvestment(identityNo, start, end);
			if (bondInvestment == null) {
				bondInvestment = new BigDecimal(0);
			}
			transbalance.setSecuritiesBalance(bondInvestment);
			//特定目的载体投资
			BigDecimal investment = loanMapper.getInvestment(identityNo, start, end);
			if (investment == null) {
				investment = new BigDecimal(0);
			}
			transbalance.setSpvbalance(investment);
			//其他表内授信
			BigDecimal otherinnercredit = loanMapper.getOtherinnercredit(identityNo, start, end);
			if (otherinnercredit == null) {
				otherinnercredit = new BigDecimal(0);
			}
			transbalance.setOtherOnBalance(otherinnercredit);
			//不可撤销的承诺及或有负债
			BigDecimal irrevocablecommitments = loanMapper.getIrrevocablecommitments(identityNo, start, end);
			if (irrevocablecommitments == null) {
				irrevocablecommitments = new BigDecimal(0);
			}
			transbalance.setLiabilitiesBalance(irrevocablecommitments);
			//本行非保本理财产品进行的授信
			BigDecimal nonbreakevenfinancial = loanMapper.getNonbreakevenfinancial(identityNo, start, end);
			if (nonbreakevenfinancial == null) {
				nonbreakevenfinancial = new BigDecimal(0);
			}
			transbalance.setNbebalance(nonbreakevenfinancial);
			//其他表外授信
			BigDecimal otheroutcredit = loanMapper.getOtheroutcredit(identityNo, start, end);
			if (otheroutcredit == null) {
				otheroutcredit = new BigDecimal(0);
			}
			transbalance.setOtherOffBalance(otheroutcredit);
			//保证金、银行存单、国债
			BigDecimal depanddet = creditMapper.getDepanddet(identityNo, start, end);
			if (depanddet == null) {
				depanddet = new BigDecimal(0);
			}
			transbalance.setDeposits(depanddet);
			//资产转移
			BigDecimal transAmcount = nonloanMapper.getTransAmcount(identityNo, start, end);
			if (transAmcount == null) {
				transAmcount = new BigDecimal(0);
			}
			transbalance.setAssetTransferBalance(transAmcount);
			//提供服务
			BigDecimal serviceAmcount = nonloanMapper.getServiceAmcount(identityNo, start, end);
			if (serviceAmcount == null) {
				serviceAmcount = new BigDecimal(0);
			}
			transbalance.setServiceBalance(serviceAmcount);
			//关联其他
			BigDecimal otherAmcount = nonloanMapper.getOtherAmcount(identityNo, start, end);
			if (otherAmcount == null) {
				otherAmcount = new BigDecimal(0);
			}
			transbalance.setOtherBalance(otherAmcount);
			//修改时间
			transbalance.setModifiedTime(new Date());
			System.out.println("来了！！");
			//授信净额
			//BigDecimal creditBalance = loan.add(bondInvestment.add(investment.add(otherinnercredit.add(irrevocablecommitments.add(nonbreakevenfinancial))))).subtract(depanddet);
			BigDecimal creditBalance = loan.add(bondInvestment).add(investment).add(otherinnercredit).add(irrevocablecommitments).add(nonbreakevenfinancial).subtract(depanddet);
			transbalance.setCreditBalance(creditBalance);
			//表内外授信合计占资本净额比例
			BigDecimal percentInNetAssets = creditBalance.divide(captial);
			transbalance.setPercentInNetAssets(percentInNetAssets);
			//报告期内最高风险额计算
			List<BigDecimal> dayOfDepanddet = creditMapper.getDayOfDepanddet(identityNo, start, end);
			List<BigDecimal> maxCreditBalance = loanMapper.getMaxCreditBalance(identityNo, start, end);
			List<BigDecimal> objects = new ArrayList<BigDecimal>();
			System.out.println("dayOfDepanddet============+++++++" + dayOfDepanddet);
			System.out.println("maxCreditBalance============+++++++" + maxCreditBalance);
			System.out.println("objects============+++++++" + objects);
			if (dayOfDepanddet.size() != 0 && maxCreditBalance.size() != 0) {
				for (int j = 0; j < dayOfDepanddet.size(); j++) {
					if (dayOfDepanddet.get(j) == null) {
						dayOfDepanddet.set(j, new BigDecimal(0));
					}
					if (maxCreditBalance.get(j) == null) {
						maxCreditBalance.set(j, new BigDecimal(0));
					}
					BigDecimal dep = dayOfDepanddet.get(j);
					BigDecimal bla = maxCreditBalance.get(j);
					objects.add(bla.subtract(dep));
				}
				BigDecimal max = Collections.max(objects);
				BigDecimal creditRiskLimit = max;
				transbalance.setCreditRiskLimit(creditRiskLimit);
				//报告期内最高风险额授信净额占资本净额比例
				BigDecimal maxPercentInNetAssets = creditRiskLimit.divide(captial);
				transbalance.setMaxPercentInNetAssets(maxPercentInNetAssets);
			} else {
				transbalance.setCreditRiskLimit(new BigDecimal(0));
				//报告期内最高风险额授信净额占资本净额比例
				transbalance.setMaxPercentInNetAssets(new BigDecimal(0));
			}
			//前置流程状态3(待报送)
			transbalance.setStatus("3");
			//前置流程状态说明
			transbalance.setStatusComment("已统计");

			//判断上季度该关联方数据是否已经存在
			Person ifExit = transbalanceMapper.getIfExit(identityNo, startN, endN);
			if (ifExit == null) {
				//批量插入
				transbalanceMapper.insertAll(transbalance);
				System.out.println(loanMapper.getLoan(identityNo, start, end));
			}


		}

		//关联机构
		for (Organ p : allOrg) {
			Transbalance transbalance = new Transbalance();
			//关联方名称/自然人姓名
			String name = p.getName();
			transbalance.setRelatedPartyName(p.getName());
			//关联方代码
			String identityNo = p.getIdentityNo();
			transbalance.setRelatedPartyCertNo(p.getIdentityNo());
			//关联方类型
			String type = creditMapper.getType(identityNo);
			transbalance.setRelatedPartyType(creditMapper.getType(identityNo));
			//各项贷款
			BigDecimal loan = loanMapper.getLoan(identityNo, start, end);
			if (loan == null) {
				loan = new BigDecimal(0);
			}
			transbalance.setLoansBalance(loan);
			//债券投资
			BigDecimal bondInvestment = loanMapper.getBondInvestment(identityNo, start, end);
			if (bondInvestment == null) {
				bondInvestment = new BigDecimal(0);
			}
			transbalance.setSecuritiesBalance(bondInvestment);
			//特定目的载体投资
			BigDecimal investment = loanMapper.getInvestment(identityNo, start, end);
			if (investment == null) {
				investment = new BigDecimal(0);
			}
			transbalance.setSpvbalance(investment);
			//其他表内授信
			BigDecimal otherinnercredit = loanMapper.getOtherinnercredit(identityNo, start, end);
			if (otherinnercredit == null) {
				otherinnercredit = new BigDecimal(0);
			}
			transbalance.setOtherOnBalance(otherinnercredit);
			//不可撤销的承诺及或有负债
			BigDecimal irrevocablecommitments = loanMapper.getIrrevocablecommitments(identityNo, start, end);
			if (irrevocablecommitments == null) {
				irrevocablecommitments = new BigDecimal(0);
			}
			transbalance.setLiabilitiesBalance(irrevocablecommitments);
			//本行非保本理财产品进行的授信
			BigDecimal nonbreakevenfinancial = loanMapper.getNonbreakevenfinancial(identityNo, start, end);
			if (nonbreakevenfinancial == null) {
				nonbreakevenfinancial = new BigDecimal(0);
			}
			transbalance.setNbebalance(nonbreakevenfinancial);
			//其他表外授信
			BigDecimal otheroutcredit = loanMapper.getOtheroutcredit(identityNo, start, end);
			if (otheroutcredit == null) {
				otheroutcredit = new BigDecimal(0);
			}
			transbalance.setOtherOffBalance(otheroutcredit);
			//保证金、银行存单、国债
			BigDecimal depanddet = creditMapper.getDepanddet(identityNo, start, end);
			if (depanddet == null) {
				depanddet = new BigDecimal(0);
			}
			transbalance.setDeposits(depanddet);
			//资产转移
			BigDecimal transAmcount = nonloanMapper.getTransAmcount(identityNo, start, end);
			if (transAmcount == null) {
				transAmcount = new BigDecimal(0);
			}
			transbalance.setAssetTransferBalance(transAmcount);
			//提供服务
			BigDecimal serviceAmcount = nonloanMapper.getServiceAmcount(identityNo, start, end);
			if (serviceAmcount == null) {
				serviceAmcount = new BigDecimal(0);
			}
			transbalance.setServiceBalance(serviceAmcount);
			//关联其他
			BigDecimal otherAmcount = nonloanMapper.getOtherAmcount(identityNo, start, end);
			if (otherAmcount == null) {
				otherAmcount = new BigDecimal(0);
			}
			transbalance.setOtherBalance(otherAmcount);
			//修改时间
			transbalance.setModifiedTime(new Date());
			System.out.println("来了！！");
			//授信净额
			//BigDecimal creditBalance = loan.add(bondInvestment.add(investment.add(otherinnercredit.add(irrevocablecommitments.add(nonbreakevenfinancial))))).subtract(depanddet);
			BigDecimal creditBalance = loan.add(bondInvestment).add(investment).add(otherinnercredit).add(irrevocablecommitments).add(nonbreakevenfinancial).subtract(depanddet);
			transbalance.setCreditBalance(creditBalance);
			//表内外授信合计占资本净额比例
			BigDecimal percentInNetAssets = creditBalance.divide(captial);
			transbalance.setPercentInNetAssets(percentInNetAssets);
			//报告期内最高风险额计算
			List<BigDecimal> dayOfDepanddet = creditMapper.getDayOfDepanddet(identityNo, start, end);
			List<BigDecimal> maxCreditBalance = loanMapper.getMaxCreditBalance(identityNo, start, end);
			List<BigDecimal> objects = new ArrayList<BigDecimal>();
			System.out.println("dayOfDepanddet============+++++++" + dayOfDepanddet);
			System.out.println("maxCreditBalance============+++++++" + maxCreditBalance);
			System.out.println("objects============+++++++" + objects);
			if (dayOfDepanddet.size() != 0 && maxCreditBalance.size() != 0) {
				for (int j = 0; j < dayOfDepanddet.size(); j++) {
					if (dayOfDepanddet.get(j) == null) {
						dayOfDepanddet.set(j, new BigDecimal(0));
					}
					if (maxCreditBalance.get(j) == null) {
						maxCreditBalance.set(j, new BigDecimal(0));
					}
					BigDecimal dep = dayOfDepanddet.get(j);
					BigDecimal bla = maxCreditBalance.get(j);
					objects.add(bla.subtract(dep));
				}
				BigDecimal max = Collections.max(objects);
				BigDecimal creditRiskLimit = max;
				transbalance.setCreditRiskLimit(creditRiskLimit);
				//报告期内最高风险额授信净额占资本净额比例
				BigDecimal maxPercentInNetAssets = creditRiskLimit.divide(captial);
				transbalance.setMaxPercentInNetAssets(maxPercentInNetAssets);
			} else {
				transbalance.setCreditRiskLimit(new BigDecimal(0));
				//报告期内最高风险额授信净额占资本净额比例
				transbalance.setMaxPercentInNetAssets(new BigDecimal(0));
			}
			//前置流程状态3(待报送)
			transbalance.setStatus("3");
			//前置流程状态说明
			transbalance.setStatusComment("已统计");
			//判断上季度该关联方数据是否已经存在
			Organ ifExit = transbalanceMapper.getOrgIfExit(identityNo, startN, endN);
			if (ifExit == null) {
				//批量插入
				transbalanceMapper.insertAll(transbalance);
				System.out.println(loanMapper.getLoan(identityNo, start, end));
			}


		}
	}
}