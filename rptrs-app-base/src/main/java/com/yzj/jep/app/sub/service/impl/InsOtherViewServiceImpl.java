/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年12月18日 16:19:11
 *
 */

package com.yzj.jep.app.sub.service.impl;

import com.yzj.jep.app.sub.mapper.InsOtherViewMapper;
import com.yzj.jep.app.sub.model.InsOtherView;
import com.yzj.jep.app.sub.service.InsOtherViewService;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional(readOnly = true)
public class InsOtherViewServiceImpl extends MyBatisCRUDService<InsOtherView, Integer> implements InsOtherViewService {
	
	Logger	logger	= LoggerFactory.getLogger(InsOtherViewServiceImpl.class);
	
	@Autowired
	private InsOtherViewMapper insOtherViewMapper;
	
	@Override
	public Mapper<InsOtherView, Integer> getMapper() {
		return insOtherViewMapper;
	}
	
}