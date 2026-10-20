/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年05月06日 10:02:38
 *
 */

package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.mapper.WarnDeployMapper;
import com.yzj.jep.app.bus.model.WarnDeploy;
import com.yzj.jep.app.bus.service.WarnDeployService;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional()
public class WarnDeployServiceImpl extends MyBatisCRUDService<WarnDeploy, Integer> implements WarnDeployService {

    Logger logger = LoggerFactory.getLogger(WarnDeployServiceImpl.class);

    @Autowired
    private WarnDeployMapper warnDeployMapper;

    @Override
    public Mapper<WarnDeploy, Integer> getMapper() {
        return warnDeployMapper;
    }


}