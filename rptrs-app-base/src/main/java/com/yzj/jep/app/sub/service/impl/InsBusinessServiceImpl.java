/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年08月31日 10:16:54
 *
 */

package com.yzj.jep.app.sub.service.impl;

import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.ins.model.TraBusiness;
import com.yzj.jep.app.ins.service.TraBusinessService;
import com.yzj.jep.app.sub.mapper.InsBusinessMapper;
import com.yzj.jep.app.sub.model.InsBalance;
import com.yzj.jep.app.sub.model.InsBusiness;
import com.yzj.jep.app.sub.service.InsBalanceService;
import com.yzj.jep.app.sub.service.InsBusinessService;
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

@Service(value = "insBusinessServiceImpl")
@Transactional
public class InsBusinessServiceImpl extends MyBatisCRUDService<InsBusiness, Integer> implements InsBusinessService {

	Logger	logger	= LoggerFactory.getLogger(InsBusinessServiceImpl.class);

	@Autowired
	private InsBusinessMapper			insBusinessMapper;

	@Autowired
	private TraBusinessService traBusinessService;
	@Autowired
	private RelPsnService relPsnService;
	@Autowired
	private RelOrgService relOrgService;
	@Autowired
	private DictItemService dictItemService;
	@Autowired
	private InsBalanceService insBalanceService;

	@Override
	public Mapper<InsBusiness, Integer> getMapper() {
		return insBusinessMapper;
	}

	@Override
	public void extractInsBusiness(Map map, String nowDate) {
		String tag= (String) map.get("tag");
		List<TraBusiness> traBusinesss=new ArrayList<>();
		Page<TraBusiness> page=new Page<>(1,100);
		String id= "6,7";
		String[] status = id.split(",");
		List<Filter> filters = new Filter.Builder().eq("dataVersion","E2.0").in("status",status).eq("delflag", "1").build();
		List<Filter> filter = new Filter.Builder().eq("dataVersion","E2.0").in("status", status).like("modifiedTime", nowDate).eq("delflag", "1").eq("considerType", "3").build();
		//重大逐笔审批
		filters.add(new Filter("considerType","3"));
		startPre(filters, tag, nowDate);
		List<TraBusiness> select = traBusinessService.select(filter);
		startExtractHz(select);

	}
	public void startPre(List<Filter> filters, String tag, String nowDate) {
		List<TraBusiness> traBusinesss=new ArrayList<>();
		Page<TraBusiness> page = new Page<>(1, 100);
		PageHelper.start(page);
		if ("all".equals(tag)) {
			traBusinesss = traBusinessService.select(filters);
		} else {
			filters.add(new Filter("modifiedTime", Operator.LIKE, nowDate));
			traBusinesss = traBusinessService.select(filters);
		}
		PageHelper.end(page, traBusinesss);
		//总页数
		int pageSize = page.getPages();
		List<TraBusiness> exBusinesss = page.getResult();
		//开始抽取
		startExtract(exBusinesss);
		for (int i = 2; i <= pageSize; i++) {
			//从第二页开始循环
			page = new Page<>(i, 100);
			PageHelper.start(page);
			if ("all".equals(tag)) {
				traBusinesss = traBusinessService.select(filters);
			} else {
				filters.add(new Filter("modifiedTime", Operator.LIKE, nowDate));
				traBusinesss = traBusinessService.select(filters);
			}
			PageHelper.end(page, traBusinesss);
			exBusinesss = page.getResult();
			//开始抽取
			startExtract(exBusinesss);
		}
	}

	private void startExtract(List<TraBusiness> traBusinesss){
		for(TraBusiness traBusiness:traBusinesss){
			InsBusiness insBusiness= new InsBusiness();
			BeanUtils.copyProperties(traBusiness,insBusiness);
			insBusiness.setStatus("3");
			insBusiness.setStatusComment("数据已抽取");
			// 合同编号
			insBusiness.setContractNo(StringUtils.isNotEmpty(traBusiness.getUniqueCode()) ? traBusiness.getUniqueCode() : traBusiness.getTransNum());
			// 关联交易金额
			insBusiness.setTradeAmount(traBusiness.getRelTradeAmount());
			//关联交易日期
			insBusiness.setTranDate(traBusiness.getContractDate());
			insBusiness.setIsExtract("0");
			insBusiness.setIsDiscovery("0");
			List<Filter> filters = new Filter.Builder().eq("pid",insBusiness.getPid()).build();
			try {
				InsBusiness dbInvestment=this.get(filters);
				if(dbInvestment == null){
					this.insert(insBusiness);
				}
			}catch (ServiceException e){
				logger.error("保险业务类"+insBusiness.getRelName()+"数据更新异常：",e);
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
	/* 抽取到关联交易汇总表*/

	private void startExtractHz(List<TraBusiness> traBusinesss) {
		for (TraBusiness traBusiness : traBusinesss) {

			try {


				//交易明细表
				InsBalance ib = new InsBalance();
				ib.setPid(traBusiness.getPid());
				ib.setRelId(traBusiness.getRelId());
				ib.setRelType(traBusiness.getRelType());
				ib.setRelName(traBusiness.getRelName());
				ib.setRelNumber(traBusiness.getRelNumber());
				List<Filter> filter = null;
				if (traBusiness.getRelType().equals("PSN")) {
					filter = new Filter.Builder().eq("identifyNumber", traBusiness.getRelNumber()).build();
					RelPsn psn = relPsnService.get(filter);
					ib.setRelCode(psn.getRelateNumber());
				} else if (traBusiness.getRelType().equals("ORG")) {
					filter = new Filter.Builder().eq("uniscCode", traBusiness.getRelNumber()).build();
					RelOrg relOrg = relOrgService.get(filter);
					ib.setRelCode(relOrg.getRelateNumber());
				}


				ib.setTradeOrg(traBusiness.getTradeOrg());
				ib.setTranType(traBusiness.getTranType());
				ib.setBusType(selectByValue(traBusiness.getBusType(), "INT-InsBusinessType"));
				ib.setContractSigningDate(traBusiness.getContractDate());
				ib.setTradeAmount(traBusiness.getRelTradeAmount());
				ib.setTenantId(traBusiness.getTenantId());
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
				logger.error("保险业务类" + traBusiness.getRelName() + "数据更新异常：", e);
			}
		}
	}
}
