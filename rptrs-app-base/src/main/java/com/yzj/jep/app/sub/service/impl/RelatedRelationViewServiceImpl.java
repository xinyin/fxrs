/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年09月15日 14:40:20
 *
 */

package com.yzj.jep.app.sub.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.sub.mapper.RelatedRelationViewMapper;
import com.yzj.jep.app.sub.service.RelatedRelationViewService;
import com.yzj.jep.app.sub.model.RelatedRelationView;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;

@Service
@Transactional(readOnly = true)
public class RelatedRelationViewServiceImpl extends MyBatisCRUDService<RelatedRelationView, Integer> implements RelatedRelationViewService {
	
	Logger	logger	= LoggerFactory.getLogger(RelatedRelationViewServiceImpl.class);
	
	@Autowired
	private RelatedRelationViewMapper			relatedRelationViewMapper;
	
	@Override
	public Mapper<RelatedRelationView, Integer> getMapper() {
		return relatedRelationViewMapper;
	}
	
}