/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月10日 11:18:54
 *
 */

package com.yzj.jep.app.bus.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.bus.mapper.RootOrgInfoMapper;
import com.yzj.jep.app.bus.service.RootOrgInfoService;
import com.yzj.jep.app.bus.model.RootOrgInfo;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;

@Service
@Transactional(readOnly = true)
public class RootOrgInfoServiceImpl extends MyBatisCRUDService<RootOrgInfo, Integer> implements RootOrgInfoService {

    Logger logger = LoggerFactory.getLogger(RootOrgInfoServiceImpl.class);

    @Autowired
    private RootOrgInfoMapper rootOrgInfoMapper;

    @Override
    public Mapper<RootOrgInfo, Integer> getMapper() {
        return rootOrgInfoMapper;
    }

    @Override
    public RootOrgInfo selectByid() {

        RootOrgInfo rootOrgInfo = rootOrgInfoMapper.selectById();
        return rootOrgInfo;
    }

    @Override
    public RootOrgInfo selectRootInfo(Integer year, Integer quarter) {
        return rootOrgInfoMapper.selectRootInfo(year, quarter);
    }
}