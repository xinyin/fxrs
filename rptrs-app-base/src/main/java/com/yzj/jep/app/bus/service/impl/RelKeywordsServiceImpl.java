/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年05月10日 14:29:13
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

import com.yzj.jep.app.bus.mapper.RelKeywordsMapper;
import com.yzj.jep.app.bus.service.RelKeywordsService;
import com.yzj.jep.app.bus.model.RelKeywords;

@Service
@Transactional(readOnly = true)
public class RelKeywordsServiceImpl extends MyBatisCRUDService<RelKeywords, Integer> implements RelKeywordsService {

    Logger logger = LoggerFactory.getLogger(RelKeywordsServiceImpl.class);

    @Autowired
    private RelKeywordsMapper relKeywordsMapper;

    @Override
    public Mapper<RelKeywords, Integer> getMapper() {
        return relKeywordsMapper;
    }

}