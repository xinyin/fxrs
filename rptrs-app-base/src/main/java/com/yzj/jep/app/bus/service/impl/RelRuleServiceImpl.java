/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年05月10日 14:29:15
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

import com.yzj.jep.app.bus.mapper.RelRuleMapper;
import com.yzj.jep.app.bus.service.RelRuleService;
import com.yzj.jep.app.bus.model.RelRule;

@Service
@Transactional(readOnly = true)
public class RelRuleServiceImpl extends MyBatisCRUDService<RelRule, Integer> implements RelRuleService {

    Logger logger = LoggerFactory.getLogger(RelRuleServiceImpl.class);

    @Autowired
    private RelRuleMapper relRuleMapper;

    @Override
    public Mapper<RelRule, Integer> getMapper() {
        return relRuleMapper;
    }

}