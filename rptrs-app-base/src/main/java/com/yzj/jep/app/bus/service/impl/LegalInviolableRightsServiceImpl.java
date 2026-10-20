/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月16日 16:30:53
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

import com.yzj.jep.app.bus.mapper.LegalInviolableRightsMapper;
import com.yzj.jep.app.bus.service.LegalInviolableRightsService;
import com.yzj.jep.app.bus.model.LegalInviolableRights;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class LegalInviolableRightsServiceImpl extends MyBatisCRUDService<LegalInviolableRights, Integer> implements LegalInviolableRightsService {

    Logger logger = LoggerFactory.getLogger(LegalInviolableRightsServiceImpl.class);

    @Autowired
    private LegalInviolableRightsMapper legalInviolableRightsMapper;

    @Override
    public Mapper<LegalInviolableRights, Integer> getMapper() {
        return legalInviolableRightsMapper;
    }

    @Override
    public List<LegalInviolableRights> searchByPid(Integer pid) {
        return legalInviolableRightsMapper.searchByPid(pid);
    }
}