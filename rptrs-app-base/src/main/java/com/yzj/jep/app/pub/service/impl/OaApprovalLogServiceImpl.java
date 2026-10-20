/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年12月25日 15:19:35
 *
 */

package com.yzj.jep.app.pub.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.pub.mapper.OaApprovalLogMapper;
import com.yzj.jep.app.pub.service.OaApprovalLogService;
import com.yzj.jep.app.pub.model.OaApprovalLog;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;

@Service
@Transactional(readOnly = true)
public class OaApprovalLogServiceImpl extends MyBatisCRUDService<OaApprovalLog, Integer> implements OaApprovalLogService {
	
	Logger	logger	= LoggerFactory.getLogger(OaApprovalLogServiceImpl.class);
	
	@Autowired
	private OaApprovalLogMapper oaApprovalLogMapper;
	
	@Override
	public Mapper<OaApprovalLog, Integer> getMapper() {
		return oaApprovalLogMapper;
	}
	
}