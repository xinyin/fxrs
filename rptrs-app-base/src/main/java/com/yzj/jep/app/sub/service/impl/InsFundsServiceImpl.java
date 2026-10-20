/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年08月31日 10:16:57
 *
 */

package com.yzj.jep.app.sub.service.impl;

import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.ins.model.TraFunds;
import com.yzj.jep.app.ins.service.TraFundsService;
import com.yzj.jep.app.sub.mapper.InsFundsMapper;
import com.yzj.jep.app.sub.model.InsBalance;
import com.yzj.jep.app.sub.model.InsFunds;
import com.yzj.jep.app.sub.service.InsBalanceService;
import com.yzj.jep.app.sub.service.InsFundsService;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import com.yzj.jep.web.entity.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(value = "insFundsServiceImpl")
@Transactional
public class InsFundsServiceImpl extends MyBatisCRUDService<InsFunds, Integer> implements InsFundsService {

	Logger logger = LoggerFactory.getLogger(InsFundsServiceImpl.class);

	@Autowired
	private InsFundsMapper insFundsMapper;
	@Autowired
	private RelPsnService relPsnService;
	@Autowired
	private RelOrgService relOrgService;
	@Autowired
	private DictItemService dictItemService;
	@Autowired
	private TraFundsService traFundsService;

	@Autowired
	private InsBalanceService insBalanceService;

	@Override
	public Mapper<InsFunds, Integer> getMapper() {
		return insFundsMapper;
	}

	@Override
	public void extractInsFunds(Map map, String nowDate) {
		String tag = (String) map.get("tag");
		List<TraFunds> traFundss = new ArrayList<>();
		Page<TraFunds> page = new Page<>(1, 100);
		String id= "6,7";
		String[] status = id.split(",");
		List<Filter> filters = new Filter.Builder().eq("dataVersion","E2.0").in("status",status).eq("delflag", "1").build();
		List<Filter> filter = new Filter.Builder().eq("dataVersion","E2.0").in("status", status).eq("delflag", "1").like("modifiedTime", nowDate).eq("considerType", "3").build();
		//重大逐笔审批
		filters.add(new Filter("considerType","3"));
		PageHelper.start(page);
		if ("all".equals(tag)) {
			traFundss = traFundsService.select(filters);
		} else {
			 filters.add( new Filter("modifiedTime",Operator.LIKE, nowDate));
			 traFundss = traFundsService.select(filters);
		}
		PageHelper.end(page, traFundss);
		//总页数
		int pageSize = page.getPages();
		List<TraFunds> exTraFundss = page.getResult();
		//开始抽取
		startExtract(exTraFundss);
		for (int i = 2; i <= pageSize; i++) {
			//从第二页开始循环
			page = new Page<>(i, 100);
			PageHelper.start(page);
			if ("all".equals(tag)) {
				traFundss = traFundsService.select(filters);
			} else {
				filters.add( new Filter("modifiedTime",Operator.LIKE, nowDate));
				traFundss = traFundsService.select(filters);
			}
			PageHelper.end(page, traFundss);
			exTraFundss = page.getResult();
			//开始抽取
			startExtract(exTraFundss);
		}
		List<TraFunds> select = traFundsService.select(filter);
		startExtractHz(select);
	}

	private void startExtract(List<TraFunds> traFundss) {
		for (TraFunds traFunds : traFundss) {
			InsFunds insFunds = new InsFunds();
			BeanUtils.copyProperties(traFunds, insFunds);
			// 合同编号
			insFunds.setContractNo(StringUtils.isNotEmpty(traFunds.getUniqueCode()) ? traFunds.getUniqueCode() : traFunds.getTransNum());
			// 关联交易金额
			insFunds.setTradeAmount(traFunds.getRelTradeAmount());
			insFunds.setStatus("3");
			insFunds.setStatusComment("数据已抽取");
			insFunds.setIsExtract("0");
			insFunds.setIsDiscovery("0");
			List<Filter> filters = new Filter.Builder().eq("pid", insFunds.getPid()).build();
			try {
				InsFunds dbInsFunds = this.get(filters);
				if(dbInsFunds == null){

					this.insertSelective(insFunds);
				}
			} catch (ServiceException e) {
				logger.error("资金运用类" + insFunds.getRelName() + "数据更新异常：", e);
			}
		}
	}

	private String selectByValue(String value, String simpleName) {
		List<NameValuePair> items = new ArrayList<>();
		try {
			List<Filter> filters = new Filter.Builder().eq("dictCode", simpleName).build();
			List<DictItem> list = dictItemService.select(filters);
			//过滤掉不要的字典项
			if (!CollectionUtils.isEmpty(list)) {
				for (DictItem dictItem : list) {
					if (dictItem.getItemValue().equals(value)) {
						return dictItem.getItemLabel();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/*  关联交易汇总表*/
	private void startExtractHz(List<TraFunds> traFundss) {
		for (TraFunds traFunds : traFundss) {
			try {

				//交易明细表
				InsBalance ib = new InsBalance();
				ib.setPid(traFunds.getPid());
				ib.setRelId(traFunds.getRelId());
				ib.setRelType(traFunds.getRelType());
				ib.setRelName(traFunds.getRelName());
				ib.setRelNumber(traFunds.getRelNumber());
				List<Filter> filter = null;
				if (traFunds.getRelType().equals("PSN")) {
					filter = new Filter.Builder().eq("identifyNumber", traFunds.getRelNumber()).build();
					RelPsn psn = relPsnService.get(filter);
					ib.setRelCode(psn.getRelateNumber());
				} else if (traFunds.getRelType().equals("ORG")) {
					filter = new Filter.Builder().eq("uniscCode", traFunds.getRelNumber()).build();
					RelOrg relOrg = relOrgService.get(filter);
					ib.setRelCode(relOrg.getRelateNumber());
				}
				ib.setTradeOrg(traFunds.getTradeOrg());
				ib.setTranType(traFunds.getTranType());
				ib.setBusType(selectByValue(traFunds.getInvestmentType(), "INS-FundsType"));
				ib.setContractSigningDate(traFunds.getTranDate());
				ib.setTradeAmount(traFunds.getRelTradeAmount());
				ib.setTenantId(traFunds.getTenantId());
				ib.setIsExtract("0");
				ib.setCreateTime(new Date());
				ib.setDelflag("1");
				InsBalance insBalance = insBalanceService.get(ib);
				if (insBalance == null) {
					insBalanceService.insert(ib);
				} else {
					insBalanceService.updateSelective(ib);
				}

			} catch (ServiceException e) {
				logger.error("资金运用类" + traFunds.getRelName() + "数据更新异常：", e);
			}
		}
	}
}

