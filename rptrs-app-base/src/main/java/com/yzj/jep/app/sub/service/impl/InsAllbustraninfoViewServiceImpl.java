/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年12月19日 15:10:32
 *
 */

package com.yzj.jep.app.sub.service.impl;

import com.yzj.jep.app.sub.mapper.InsAllbustraninfoViewMapper;
import com.yzj.jep.app.sub.model.InsAllbustraninfoView;
import com.yzj.jep.app.sub.service.InsAllbustraninfoViewService;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
public class InsAllbustraninfoViewServiceImpl extends MyBatisCRUDService<InsAllbustraninfoView, Integer> implements InsAllbustraninfoViewService {
	
	Logger	logger	= LoggerFactory.getLogger(InsAllbustraninfoViewServiceImpl.class);
	
	@Autowired
	private InsAllbustraninfoViewMapper insAllbustraninfoViewMapper;
	
	@Override
	public Mapper<InsAllbustraninfoView, Integer> getMapper() {
		return insAllbustraninfoViewMapper;
	}
	
}