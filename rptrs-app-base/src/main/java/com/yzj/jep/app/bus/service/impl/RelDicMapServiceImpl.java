/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年04月06日 18:46:52
 *
 */

package com.yzj.jep.app.bus.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.bus.mapper.RelDicMapMapper;
import com.yzj.jep.app.bus.service.RelDicMapService;
import com.yzj.jep.app.bus.model.RelDicMap;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;

@Service
public class RelDicMapServiceImpl extends MyBatisCRUDService<RelDicMap, Integer> implements RelDicMapService {

    Logger logger = LoggerFactory.getLogger(RelDicMapServiceImpl.class);

    @Autowired
    private RelDicMapMapper relDicMapMapper;

    @Override
    public Mapper<RelDicMap, Integer> getMapper() {
        return relDicMapMapper;
    }

}