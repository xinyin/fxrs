/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年08月31日 10:17:01
 *
 */

package com.yzj.jep.app.sub.service.impl;

import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.ins.model.TraInvestment;
import com.yzj.jep.app.ins.service.TraInvestmentService;
import com.yzj.jep.app.sub.mapper.InsInvestmentMapper;
import com.yzj.jep.app.sub.model.InsBalance;
import com.yzj.jep.app.sub.model.InsInvestment;
import com.yzj.jep.app.sub.service.InsBalanceService;
import com.yzj.jep.app.sub.service.InsInvestmentService;
import com.yzj.jep.commons.CollectionUtils;
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
import java.util.List;
import java.util.Map;

@Service(value = "InsInvestmentServiceImpl")
@Transactional
public class InsInvestmentServiceImpl extends MyBatisCRUDService<InsInvestment, Integer> implements InsInvestmentService {
	
	Logger	logger	= LoggerFactory.getLogger(InsInvestmentServiceImpl.class);
	
	@Autowired
	private InsInvestmentMapper			insInvestmentMapper;

	@Autowired
	private TraInvestmentService traInvestmentService;
	@Autowired
	private DictItemService dictItemService;
	@Autowired
	private RelPsnService relPsnService;
	@Autowired
	private RelOrgService relOrgService;
	@Autowired
	private InsBalanceService insBalanceService;
	
	@Override
	public Mapper<InsInvestment, Integer> getMapper() {
		return insInvestmentMapper;
	}

	@Override
	public void extractInsInestment(Map map, String nowDate) {
		String tag= (String) map.get("tag");
		List<TraInvestment> traInvestments=new ArrayList<>();
		Page<TraInvestment> page=new Page<>(1,100);
		String id= "6,7";
		String[] status = id.split(",");
		List<Filter> filters = new Filter.Builder().in("status",status).build();
		List<Filter> filter = new Filter.Builder().in("status", status).like("modifiedTime", nowDate).ne("considerType", "3").build();
		filters.add(new Filter("considerType","3"));//重大逐笔审批
		PageHelper.start(page);
		if ("all".equals(tag)){
			traInvestments=traInvestmentService.select(filters);
		}else {
			 filters.add(new Filter("modifiedTime", Operator.LIKE,nowDate));
			traInvestments=traInvestmentService.select(filters);
		}
		PageHelper.end(page,traInvestments);
		//总页数
		int pageSize=page.getPages();
		List<TraInvestment> exTraInvestment=page.getResult();
		//开始抽取
		startExtract(exTraInvestment);
		for(int i=2;i<=pageSize;i++){
			//从第二页开始循环
			page=new Page<>(i,100);
			PageHelper.start(page);
			if ("all".equals(tag)){
				traInvestments=traInvestmentService.select(filters);
			}else {
				filters.add(new Filter("modifiedTime", Operator.LIKE,nowDate));
				traInvestments=traInvestmentService.select(filters);
			}
			PageHelper.end(page,traInvestments);
			exTraInvestment=page.getResult();
			//开始抽取
			startExtract(exTraInvestment);
		}
		List<TraInvestment> select = traInvestmentService.select(filter);
		startExtractHz(select);//向关联交易汇总表中抽取数据
	}

	private void startExtract(List<TraInvestment> TraInvestments){
		for(TraInvestment traInvestment:TraInvestments){
			InsInvestment insInvestment= new InsInvestment();
			BeanUtils.copyProperties(traInvestment,insInvestment);
			insInvestment.setTradeAmount(traInvestment.getRelTradeAmount());
			insInvestment.setTotolTradeAmount(traInvestment.getAccuPracTradeAmount());
			insInvestment.setStatus("3");
			insInvestment.setStatusComment("数据已抽取");
			insInvestment.setIsExtract("0");
			insInvestment.setIsDiscovery("0");
			List<Filter> filters = new Filter.Builder().eq("pid",insInvestment.getPid()).build();
			try {
				InsInvestment dbInvestment=this.get(filters);
				if(dbInvestment == null){
					this.insertSelective(insInvestment);
				}
			}catch (ServiceException e){
				logger.error("投资入股类"+insInvestment.getRelName()+"数据更新异常：",e);
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
	//关联交易汇总表
	private void startExtractHz(List<TraInvestment> TraInvestments) {
		for (TraInvestment traInvestment : TraInvestments) {
			try {

				List<Filter> filters = new Filter.Builder().eq("pid",traInvestment.getPid()).build();
				InsBalance ib = new InsBalance();
				ib.setPid(traInvestment.getPid());
				ib.setRelId(traInvestment.getRelId());
				ib.setRelType(traInvestment.getRelType());
				ib.setRelName(traInvestment.getRelName());
				ib.setRelNumber(traInvestment.getRelNumber());
				List<Filter> filter = null;
				if (traInvestment.getRelType().equals("PSN")) {
					filter = new Filter.Builder().eq("name", traInvestment.getRelName()).
							like("identifyNumber", traInvestment.getRelNumber()).build();
					RelPsn psn = relPsnService.get(filter);
					ib.setRelCode(psn.getRelateNumber());
				} else if (traInvestment.getRelType().equals("ORG")) {
					filter = new Filter.Builder().eq("orgName", traInvestment.getRelName()).
							like("uniscCode", traInvestment.getRelNumber()).build();
					RelOrg relOrg = relOrgService.get(filter);
					ib.setRelCode(relOrg.getRelateNumber());
				}
				ib.setTradeOrg(traInvestment.getTradeOrg());

				ib.setTranType(traInvestment.getTranType());
				ib.setBusType(selectByValue(traInvestment.getInvestmentType(), "INS-InvestmentType"));
				ib.setContractSigningDate(traInvestment.getTranDate());
				ib.setTradeAmount(traInvestment.getRelTradeAmount());
				ib.setTenantId(traInvestment.getTenantId());
				ib.setIsExtract("0");
				ib.setDelflag("1");
				InsBalance insBalance = insBalanceService.get(filters);
				if (insBalance == null) {//交易明细表
					insBalanceService.insert(ib);
				}else {
					insBalanceService.updateSelective(ib);
				}
			} catch (ServiceException e) {
				logger.error("投资入股类" + traInvestment.getRelName() + "数据更新异常：", e);
			}
		}
	}
}