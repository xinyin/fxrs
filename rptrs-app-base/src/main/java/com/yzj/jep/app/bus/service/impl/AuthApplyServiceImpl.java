/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年09月16日 20:03:29
 *
 */

package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.mapper.AuthApplyMapper;
import com.yzj.jep.app.bus.model.AuthApply;
import com.yzj.jep.app.bus.service.AuthApplyService;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AuthApplyServiceImpl extends MyBatisCRUDService<AuthApply, Integer> implements AuthApplyService {
	
	Logger	logger	= LoggerFactory.getLogger(AuthApplyServiceImpl.class);
	
	@Autowired
	private AuthApplyMapper authApplyMapper;
	
	@Override
	public Mapper<AuthApply, Integer> getMapper() {
		return authApplyMapper;
	}

}