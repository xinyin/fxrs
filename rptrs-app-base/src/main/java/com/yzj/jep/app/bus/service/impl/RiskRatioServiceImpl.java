/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2021 pactera.com. All rights reserved.
 *
 * Generated at 2021年07月06日 16:15:35
 *
 */

package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.bus.mapper.RiskRatioMapper;
import com.yzj.jep.app.bus.service.RiskRatioService;
import com.yzj.jep.app.bus.model.RiskRatio;

@Service
@Transactional(readOnly = true)
public class RiskRatioServiceImpl extends MyBatisCRUDService<RiskRatio, Integer> implements RiskRatioService {
	
	Logger	logger	= LoggerFactory.getLogger(RiskRatioServiceImpl.class);
	
	@Autowired
	private RiskRatioMapper			riskRatioMapper;
	
	@Override
	public Mapper<RiskRatio, Integer> getMapper() {
		return riskRatioMapper;
	}
}