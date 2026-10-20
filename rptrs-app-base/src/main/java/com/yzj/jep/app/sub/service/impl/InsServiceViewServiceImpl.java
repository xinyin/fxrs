/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年09月07日 15:06:16
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

import com.yzj.jep.app.sub.mapper.InsServiceViewMapper;
import com.yzj.jep.app.sub.service.InsServiceViewService;
import com.yzj.jep.app.sub.model.InsServiceView;

@Service
@Transactional
public class InsServiceViewServiceImpl extends MyBatisCRUDService<InsServiceView, Integer> implements InsServiceViewService {
	
	Logger	logger	= LoggerFactory.getLogger(InsServiceViewServiceImpl.class);
	
	@Autowired
	private InsServiceViewMapper			insServiceViewMapper;
	
	@Override
	public Mapper<InsServiceView, Integer> getMapper() {
		return insServiceViewMapper;
	}
	
}