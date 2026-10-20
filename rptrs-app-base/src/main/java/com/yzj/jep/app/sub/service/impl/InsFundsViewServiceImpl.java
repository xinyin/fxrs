/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年09月07日 15:06:12
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

import com.yzj.jep.app.sub.mapper.InsFundsViewMapper;
import com.yzj.jep.app.sub.service.InsFundsViewService;
import com.yzj.jep.app.sub.model.InsFundsView;

@Service
@Transactional
public class InsFundsViewServiceImpl extends MyBatisCRUDService<InsFundsView, Integer> implements InsFundsViewService {
	
	Logger	logger	= LoggerFactory.getLogger(InsFundsViewServiceImpl.class);
	
	@Autowired
	private InsFundsViewMapper			insFundsViewMapper;
	
	@Override
	public Mapper<InsFundsView, Integer> getMapper() {
		return insFundsViewMapper;
	}
	
}