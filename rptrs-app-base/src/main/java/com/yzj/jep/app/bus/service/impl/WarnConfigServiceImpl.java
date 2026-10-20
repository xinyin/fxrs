/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年05月06日 10:02:38
 *
 */

package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.bus.mapper.WarnConfigMapper;
import com.yzj.jep.app.bus.service.WarnConfigService;
import com.yzj.jep.app.bus.model.WarnConfig;

@Service
@Transactional(readOnly = true)
public class WarnConfigServiceImpl extends MyBatisCRUDService<WarnConfig, Integer> implements WarnConfigService {

    Logger logger = LoggerFactory.getLogger(WarnConfigServiceImpl.class);

    @Autowired
    private WarnConfigMapper warnConfigMapper;

    @Override
    public Mapper<WarnConfig, Integer> getMapper() {
        return warnConfigMapper;
    }


}