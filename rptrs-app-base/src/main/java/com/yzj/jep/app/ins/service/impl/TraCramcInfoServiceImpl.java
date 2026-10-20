/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年12月12日 11:10:44
 *
 */

package com.yzj.jep.app.ins.service.impl;

import com.yzj.jep.app.ins.mapper.TraCramcInfoMapper;
import com.yzj.jep.app.ins.model.TraCramcInfo;
import com.yzj.jep.app.ins.service.TraCramcInfoService;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional(readOnly = true)
public class TraCramcInfoServiceImpl extends MyBatisCRUDService<TraCramcInfo, Integer> implements TraCramcInfoService {
	
	Logger	logger	= LoggerFactory.getLogger(TraCramcInfoServiceImpl.class);
	
	@Autowired
	private TraCramcInfoMapper traCramcInfoMapper;
	
	@Override
	public Mapper<TraCramcInfo, Integer> getMapper() {
		return traCramcInfoMapper;
	}
	
}