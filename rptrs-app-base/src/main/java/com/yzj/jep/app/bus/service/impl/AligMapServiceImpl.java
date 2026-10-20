/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年05月10日 10:36:06
 *
 */

package com.yzj.jep.app.bus.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.bus.mapper.AligMapMapper;
import com.yzj.jep.app.bus.service.AligMapService;
import com.yzj.jep.app.bus.model.AligMap;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;

@Service
@Transactional(readOnly = true)
public class AligMapServiceImpl extends MyBatisCRUDService<AligMap, Integer> implements AligMapService {

    Logger logger = LoggerFactory.getLogger(AligMapServiceImpl.class);

    @Autowired
    private AligMapMapper aligMapMapper;

    @Override
    public Mapper<AligMap, Integer> getMapper() {
        return aligMapMapper;
    }

}