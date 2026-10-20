/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2021 pactera.com. All rights reserved.
 *
 * Generated at 2021年06月29日 17:31:09
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

import com.yzj.jep.app.bus.mapper.KnowledgeBaseMapper;
import com.yzj.jep.app.bus.service.KnowledgeBaseService;
import com.yzj.jep.app.bus.model.KnowledgeBase;


@Service
@Transactional(readOnly = true)
public class KnowledgeBaseServiceImpl extends MyBatisCRUDService<KnowledgeBase, Integer> implements KnowledgeBaseService {

	Logger	logger	= LoggerFactory.getLogger(KnowledgeBaseServiceImpl.class);

	@Autowired
	private KnowledgeBaseMapper	knowledgeBaseMapper;

	@Override
	public Mapper<KnowledgeBase, Integer> getMapper() {
		return knowledgeBaseMapper;
	}

}