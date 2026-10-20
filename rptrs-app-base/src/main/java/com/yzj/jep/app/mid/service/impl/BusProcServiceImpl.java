/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2021 pactera.com. All rights reserved.
 *
 * Generated at 2021年07月17日 13:42:50
 *
 */

package com.yzj.jep.app.mid.service.impl;

import com.yzj.jep.app.mid.mapper.BusProcMapper;
import com.yzj.jep.app.mid.model.BusProc;
import com.yzj.jep.app.mid.service.BusProcService;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional(readOnly = true)
public class BusProcServiceImpl extends MyBatisCRUDService<BusProc, String> implements BusProcService {
	
	Logger	logger	= LoggerFactory.getLogger(BusProcServiceImpl.class);
	
	@Autowired
	private BusProcMapper busProcMapper;
	
	@Override
	public Mapper<BusProc, String> getMapper() {
		return busProcMapper;
	}
	
}