/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年09月15日 14:40:18
 *
 */

package com.yzj.jep.app.sub.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.sub.mapper.OrganViewMapper;
import com.yzj.jep.app.sub.service.OrganViewService;
import com.yzj.jep.app.sub.model.OrganView;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;

@Service
@Transactional(readOnly = true)
public class OrganViewServiceImpl extends MyBatisCRUDService<OrganView, Integer> implements OrganViewService {
	
	Logger	logger	= LoggerFactory.getLogger(OrganViewServiceImpl.class);
	
	@Autowired
	private OrganViewMapper			organViewMapper;
	
	@Override
	public Mapper<OrganView, Integer> getMapper() {
		return organViewMapper;
	}
	
}