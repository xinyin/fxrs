/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2021 pactera.com. All rights reserved.
 *
 * Generated at 2021年07月13日 13:55:31
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

import com.yzj.jep.app.bus.mapper.RelOrgBranchMapper;
import com.yzj.jep.app.bus.service.RelOrgBranchService;
import com.yzj.jep.app.bus.model.RelOrgBranch;

@Service
@Transactional(readOnly = true)
public class RelOrgBranchServiceImpl extends MyBatisCRUDService<RelOrgBranch, Integer> implements RelOrgBranchService {
	
	Logger	logger	= LoggerFactory.getLogger(RelOrgBranchServiceImpl.class);
	
	@Autowired
	private RelOrgBranchMapper			relOrgBranchMapper;
	
	@Override
	public Mapper<RelOrgBranch, Integer> getMapper() {
		return relOrgBranchMapper;
	}
	
}