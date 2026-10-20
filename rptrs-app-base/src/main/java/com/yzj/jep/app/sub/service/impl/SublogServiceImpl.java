/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月05日 10:57:27
 *
 */

package com.yzj.jep.app.sub.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.sub.mapper.SublogMapper;
import com.yzj.jep.app.sub.service.SublogService;
import com.yzj.jep.app.sub.model.Sublog;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;

@Service
@Transactional
public class SublogServiceImpl extends MyBatisCRUDService<Sublog, Integer> implements SublogService {

    Logger logger = LoggerFactory.getLogger(SublogServiceImpl.class);

    @Autowired
    private SublogMapper sublogMapper;

    @Override
    public Mapper<Sublog, Integer> getMapper() {
        return sublogMapper;
    }

}