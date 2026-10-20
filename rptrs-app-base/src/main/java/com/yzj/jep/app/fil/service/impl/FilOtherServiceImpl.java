/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2021 pactera.com. All rights reserved.
 *
 * Generated at 2021年01月15日 17:08:23
 *
 */

package com.yzj.jep.app.fil.service.impl;

import com.yzj.jep.app.fil.mapper.FilOtherMapper;
import com.yzj.jep.app.fil.model.FilOther;
import com.yzj.jep.app.fil.service.FilOtherService;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class FilOtherServiceImpl extends MyBatisCRUDService<FilOther, Integer> implements FilOtherService {
	
	Logger	logger	= LoggerFactory.getLogger(FilOtherServiceImpl.class);
	
	@Autowired
	private FilOtherMapper filOtherMapper;
	
	@Override
	public Mapper<FilOther, Integer> getMapper() {
		return filOtherMapper;
	}
	
}