/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月06日 14:36:43
 *
 */

package com.yzj.jep.app.bus.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.bus.mapper.SingleRelatedPartyMapper;
import com.yzj.jep.app.bus.service.SingleRelatedPartyService;
import com.yzj.jep.app.bus.model.SingleRelatedParty;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;

@Service
@Transactional(readOnly = true)
public class SingleRelatedPartyServiceImpl extends MyBatisCRUDService<SingleRelatedParty, Integer> implements SingleRelatedPartyService {

    Logger logger = LoggerFactory.getLogger(SingleRelatedPartyServiceImpl.class);

    @Autowired
    private SingleRelatedPartyMapper singleRelatedPartyMapper;

    @Override
    public Mapper<SingleRelatedParty, Integer> getMapper() {
        return singleRelatedPartyMapper;
    }

}