/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年09月07日 15:06:15
 *
 */

package com.yzj.jep.app.sub.service.impl;

import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.sub.mapper.InsInvestmentViewMapper;
import com.yzj.jep.app.sub.service.InsInvestmentViewService;
import com.yzj.jep.app.sub.model.InsInvestmentView;

@Service
@Transactional
public class InsInvestmentViewServiceImpl extends MyBatisCRUDService<InsInvestmentView, Integer> implements InsInvestmentViewService {
	
	Logger	logger	= LoggerFactory.getLogger(InsInvestmentViewServiceImpl.class);
	
	@Autowired
	private InsInvestmentViewMapper			insInvestmentViewMapper;
	
	@Override
	public Mapper<InsInvestmentView, Integer> getMapper() {
		return insInvestmentViewMapper;
	}
	
}