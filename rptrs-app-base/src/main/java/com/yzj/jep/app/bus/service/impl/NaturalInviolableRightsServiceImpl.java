/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月16日 17:10:19
 *
 */

package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.mapper.NaturalInviolableRightsMapper;
import com.yzj.jep.app.bus.model.NaturalInviolableRights;
import com.yzj.jep.app.bus.service.NaturalInviolableRightsService;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class NaturalInviolableRightsServiceImpl extends MyBatisCRUDService<NaturalInviolableRights, Integer> implements NaturalInviolableRightsService {

    Logger logger = LoggerFactory.getLogger(NaturalInviolableRightsServiceImpl.class);

    @Autowired
    private NaturalInviolableRightsMapper naturalInviolableRightsMapper;

    @Override
    public Mapper<NaturalInviolableRights, Integer> getMapper() {
        return naturalInviolableRightsMapper;
    }

}