/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月06日 14:36:34
 *
 */

package com.yzj.jep.app.bus.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.bus.mapper.IncidenceRelationMapper;
import com.yzj.jep.app.bus.service.IncidenceRelationService;
import com.yzj.jep.app.bus.model.IncidenceRelation;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;

@Service
@Transactional(readOnly = true)
public class IncidenceRelationServiceImpl extends MyBatisCRUDService<IncidenceRelation, Integer> implements IncidenceRelationService {

    Logger logger = LoggerFactory.getLogger(IncidenceRelationServiceImpl.class);

    @Autowired
    private IncidenceRelationMapper incidenceRelationMapper;

    @Override
    public Mapper<IncidenceRelation, Integer> getMapper() {
        return incidenceRelationMapper;
    }

}