/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年12月19日 15:10:37
 *
 */

package com.yzj.jep.app.sub.service.impl;

import com.yzj.jep.app.sub.mapper.InsRelfundsViewMapper;
import com.yzj.jep.app.sub.model.InsRelfundsView;
import com.yzj.jep.app.sub.service.InsRelfundsViewService;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
public class InsRelfundsViewServiceImpl extends MyBatisCRUDService<InsRelfundsView, Integer> implements InsRelfundsViewService {
	
	Logger	logger	= LoggerFactory.getLogger(InsRelfundsViewServiceImpl.class);
	
	@Autowired
	private InsRelfundsViewMapper insRelfundsViewMapper;
	
	@Override
	public Mapper<InsRelfundsView, Integer> getMapper() {
		return insRelfundsViewMapper;
	}
	
}