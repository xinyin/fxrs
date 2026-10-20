/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年09月07日 15:06:10
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

import com.yzj.jep.app.sub.mapper.InsBusinessViewMapper;
import com.yzj.jep.app.sub.service.InsBusinessViewService;
import com.yzj.jep.app.sub.model.InsBusinessView;

@Service
@Transactional
public class InsBusinessViewServiceImpl extends MyBatisCRUDService<InsBusinessView, Integer> implements InsBusinessViewService {
	
	Logger	logger	= LoggerFactory.getLogger(InsBusinessViewServiceImpl.class);
	
	@Autowired
	private InsBusinessViewMapper			insBusinessViewMapper;
	
	@Override
	public Mapper<InsBusinessView, Integer> getMapper() {
		return insBusinessViewMapper;
	}
	
}