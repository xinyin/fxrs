/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2023 pactera.com. All rights reserved.
 *
 * Generated at 2023年04月04日 13:47:43
 *
 */

package com.yzj.jep.app.record.service.impl;

import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.StrUtil;
import com.yzj.jep.app.record.mapper.TraInterfaceRecordMapper;
import com.yzj.jep.app.record.model.InterfaceRecord;
import com.yzj.jep.app.record.service.TraInterfaceRecordService;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Service
@Transactional
public class TraInterfaceRecordServiceImpl extends MyBatisCRUDService<InterfaceRecord, Integer> implements TraInterfaceRecordService {

	Logger	logger	= LoggerFactory.getLogger(TraInterfaceRecordServiceImpl.class);

	@Autowired
	private TraInterfaceRecordMapper traInterfaceRecordMapper;
	@Autowired
	ThreadPoolTaskExecutor threadPoolTaskExecutor;

	@Override
	public Mapper<InterfaceRecord, Integer> getMapper() {
		return traInterfaceRecordMapper;
	}

	@Override
	public void setInterfaceRecord(InterfaceRecord interfaceRecord) {
		try {
			threadPoolTaskExecutor.execute(new Runnable() {
				@Override
				public void run() {
					String localhostStr = NetUtil.getLocalhostStr();
					interfaceRecord.setHostIp(localhostStr);
					interfaceRecord.setModifiedTime(new Date());
					interfaceRecord.setCreateTime(new Date());
					int insert = traInterfaceRecordMapper.insert(interfaceRecord);
				}
			});

		}catch (Exception e){
			logger.error("接口记录插入失败:{}",e);
		}
	}
}
