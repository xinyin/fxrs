/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月19日 16:17:00
 *
 */

package com.yzj.jep.app.bus.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.bus.mapper.SystemfileMapper;
import com.yzj.jep.app.bus.service.SystemfileService;
import com.yzj.jep.app.bus.model.Systemfile;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;

@Service
@Transactional(readOnly = true)
public class SystemfileServiceImpl extends MyBatisCRUDService<Systemfile, Integer> implements SystemfileService {

    Logger logger = LoggerFactory.getLogger(SystemfileServiceImpl.class);

    @Autowired
    private SystemfileMapper systemfileMapper;

    @Override
    public Mapper<Systemfile, Integer> getMapper() {
        return systemfileMapper;
    }

}