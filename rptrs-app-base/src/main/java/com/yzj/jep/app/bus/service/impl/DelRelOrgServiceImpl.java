/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年09月28日 10:02:44
 *
 */

package com.yzj.jep.app.bus.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.bus.mapper.DelRelOrgMapper;
import com.yzj.jep.app.bus.service.DelRelOrgService;
import com.yzj.jep.app.bus.model.DelRelOrg;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;

@Service
@Transactional(readOnly = true)
public class DelRelOrgServiceImpl extends MyBatisCRUDService<DelRelOrg, Integer> implements DelRelOrgService {

    Logger logger = LoggerFactory.getLogger(DelRelOrgServiceImpl.class);

    @Autowired
    private DelRelOrgMapper delRelOrgMapper;

    @Override
    public Mapper<DelRelOrg, Integer> getMapper() {
        return delRelOrgMapper;
    }

}