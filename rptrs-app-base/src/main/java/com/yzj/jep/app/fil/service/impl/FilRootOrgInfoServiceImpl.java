/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2021 pactera.com. All rights reserved.
 *
 * Generated at 2021年08月03日 17:04:30
 *
 */

package com.yzj.jep.app.fil.service.impl;

import com.yzj.jep.app.fil.mapper.FilRootOrgInfoMapper;
import com.yzj.jep.app.fil.model.FilRootOrgInfo;
import com.yzj.jep.app.fil.service.FilRootOrgInfoService;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class FilRootOrgInfoServiceImpl extends MyBatisCRUDService<FilRootOrgInfo, Integer> implements FilRootOrgInfoService {
	
	Logger	logger	= LoggerFactory.getLogger(FilRootOrgInfoServiceImpl.class);
	
	@Autowired
	private FilRootOrgInfoMapper filRootOrgInfoMapper;
	
	@Override
	public Mapper<FilRootOrgInfo, Integer> getMapper() {
		return filRootOrgInfoMapper;
	}
	
}