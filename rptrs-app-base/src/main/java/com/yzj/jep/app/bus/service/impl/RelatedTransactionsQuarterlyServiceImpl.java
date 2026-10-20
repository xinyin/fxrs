/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月06日 14:36:42
 *
 */

package com.yzj.jep.app.bus.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.bus.mapper.RelatedTransactionsQuarterlyMapper;
import com.yzj.jep.app.bus.service.RelatedTransactionsQuarterlyService;
import com.yzj.jep.app.bus.model.RelatedTransactionsQuarterly;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;

@Service
@Transactional(readOnly = true)
public class RelatedTransactionsQuarterlyServiceImpl extends MyBatisCRUDService<RelatedTransactionsQuarterly, Integer> implements RelatedTransactionsQuarterlyService {

    Logger logger = LoggerFactory.getLogger(RelatedTransactionsQuarterlyServiceImpl.class);

    @Autowired
    private RelatedTransactionsQuarterlyMapper relatedTransactionsQuarterlyMapper;

    @Override
    public Mapper<RelatedTransactionsQuarterly, Integer> getMapper() {
        return relatedTransactionsQuarterlyMapper;
    }

}