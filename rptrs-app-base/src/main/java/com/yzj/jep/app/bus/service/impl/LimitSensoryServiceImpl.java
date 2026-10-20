/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年05月03日 11:00:21
 *
 */

package com.yzj.jep.app.bus.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.bus.mapper.LimitSensoryMapper;
import com.yzj.jep.app.bus.service.LimitSensoryService;
import com.yzj.jep.app.bus.model.LimitSensory;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;

@Service
@Transactional(readOnly = true)
public class LimitSensoryServiceImpl extends MyBatisCRUDService<LimitSensory, Integer> implements LimitSensoryService {

    Logger logger = LoggerFactory.getLogger(LimitSensoryServiceImpl.class);

    @Autowired
    private LimitSensoryMapper limitSensoryMapper;

    @Override
    public Mapper<LimitSensory, Integer> getMapper() {
        return limitSensoryMapper;
    }

}