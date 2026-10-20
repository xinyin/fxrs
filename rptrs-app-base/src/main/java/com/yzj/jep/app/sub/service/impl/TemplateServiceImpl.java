/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月01日 17:05:10
 *
 */

package com.yzj.jep.app.sub.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.sub.mapper.TemplateMapper;
import com.yzj.jep.app.sub.service.TemplateService;
import com.yzj.jep.app.sub.model.Template;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;

@Service
@Transactional(readOnly = true)
public class TemplateServiceImpl extends MyBatisCRUDService<Template, Integer> implements TemplateService {
	
	Logger	logger	= LoggerFactory.getLogger(TemplateServiceImpl.class);
	
	@Autowired
	private TemplateMapper			templateMapper;
	
	@Override
	public Mapper<Template, Integer> getMapper() {
		return templateMapper;
	}
	
}