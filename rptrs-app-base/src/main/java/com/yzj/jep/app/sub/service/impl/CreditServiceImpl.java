/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月02日 14:21:39
 *
 */

package com.yzj.jep.app.sub.service.impl;

import com.yzj.jep.app.bus.model.TraCredit;
import com.yzj.jep.app.bus.service.TraCreditService;
import com.yzj.jep.app.sub.mapper.CreditMapper;
import com.yzj.jep.app.sub.model.Credit;
import com.yzj.jep.app.sub.service.CreditService;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service(value = "CreditServiceImpl")
@Transactional
public class CreditServiceImpl extends MyBatisCRUDService<Credit, Integer> implements CreditService {
	
	Logger	logger	= LoggerFactory.getLogger(CreditServiceImpl.class);
	
	@Autowired
	private CreditMapper			creditMapper;

	@Autowired
	private TraCreditService traCreditService;
	
	@Override
	public Mapper<Credit, Integer> getMapper() {
		return creditMapper;
	}

	@Override
	public void extractCredit(Map map, String nowDate) {
		String tag= (String) map.get("tag");
		List<TraCredit> traCredits=new ArrayList<>();
		//分批查询导入
		Page<TraCredit> page=new Page<>(1,100);
		PageHelper.start(page);
		if ("all".equals(tag)){
			traCredits=traCreditService.selectAll();
		}else {
			List<Filter> filters = new Filter.Builder().like("modifiedTime",nowDate).build();
			traCredits=traCreditService.select(filters);
		}
		PageHelper.end(page,traCredits);
		//总页数
		int pageSize=page.getPages();
		List<TraCredit> exTraCredits=page.getResult();
		//开始抽取
		startExtract(exTraCredits);
		for(int i=2;i<=pageSize;i++){
			//从第二页开始循环
			page=new Page<>(i,100);
			PageHelper.start(page);
			if ("all".equals(tag)){
				traCredits=traCreditService.selectAll();
			}else {
				List<Filter> filters = new Filter.Builder().like("modifiedTime",nowDate).build();
				traCredits=traCreditService.select(filters);
			}
			PageHelper.end(page,traCredits);
			exTraCredits=page.getResult();
			//开始抽取
			startExtract(exTraCredits);
		}
	}

	private void startExtract(List<TraCredit> traCredits){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		for(TraCredit traCredit:traCredits){
			Credit credit=new Credit();
			BeanUtils.copyProperties(traCredit,credit);
			credit.setRelatedPartyName(traCredit.getRelatedName());
			credit.setRelatedPartyType(traCredit.getRelType());
			credit.setRelatedPartyCertNo(traCredit.getIdentNumber());
			credit.setIsDeclared(traCredit.getImportantTrade());
			credit.setCreditNo(traCredit.getInternalApplyNo());
			try {
				credit.setCreditConfirmDate(traCredit.getApplyOverTime()==null?null:sdf.parse(traCredit.getApplyOverTime()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			credit.setForeignCurrToRmb(traCredit.getForeignCurrency()==null?null:new BigDecimal(traCredit.getForeignCurrency()));
			credit.setCreditItems(traCredit.getCreditPlan());
			credit.setStatus("0");
			credit.setStatusComment("数据已抽取");
			List<Filter> filters = new Filter.Builder().eq("pid",credit.getPid()).build();
			try {
				Credit dbCredit=this.get(filters);
				if(dbCredit!=null){
					this.update(credit);
				}else {
					this.insert(credit);
				}
			}catch (ServiceException e){
				logger.error("授信明细表["+credit.getCreditNo()+"]数据更新异常：",e);
			}
		}
	}
}