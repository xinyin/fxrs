/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年12月19日 15:10:35
 *
 */

package com.yzj.jep.app.sub.service.impl;

import com.yzj.jep.app.sub.mapper.InsAllfundsViewMapper;
import com.yzj.jep.app.sub.model.InsAllfundsView;
import com.yzj.jep.app.sub.service.InsAllfundsViewService;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional(readOnly = true)
public class InsAllfundsViewServiceImpl extends MyBatisCRUDService<InsAllfundsView, Integer> implements InsAllfundsViewService {
	
	Logger	logger	= LoggerFactory.getLogger(InsAllfundsViewServiceImpl.class);
	
	@Autowired
	private InsAllfundsViewMapper insAllfundsViewMapper;
	
	@Override
	public Mapper<InsAllfundsView, Integer> getMapper() {
		return insAllfundsViewMapper;
	}
	
}