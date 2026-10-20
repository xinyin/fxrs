/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年09月07日 15:06:13
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

import com.yzj.jep.app.sub.mapper.InsInterestViewMapper;
import com.yzj.jep.app.sub.service.InsInterestViewService;
import com.yzj.jep.app.sub.model.InsInterestView;

@Service
@Transactional
public class InsInterestViewServiceImpl extends MyBatisCRUDService<InsInterestView, Integer> implements InsInterestViewService {
	
	Logger	logger	= LoggerFactory.getLogger(InsInterestViewServiceImpl.class);
	
	@Autowired
	private InsInterestViewMapper			insInterestViewMapper;
	
	@Override
	public Mapper<InsInterestView, Integer> getMapper() {
		return insInterestViewMapper;
	}
	
}