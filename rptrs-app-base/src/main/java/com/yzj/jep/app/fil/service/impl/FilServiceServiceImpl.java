/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年07月21日 14:56:00
 *
 */

package com.yzj.jep.app.fil.service.impl;

import com.yzj.jep.app.fil.mapper.FilServiceMapper;
import com.yzj.jep.app.fil.model.FilService;
import com.yzj.jep.app.fil.service.FilServiceService;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class FilServiceServiceImpl extends MyBatisCRUDService<FilService, Integer> implements FilServiceService {

    Logger logger = LoggerFactory.getLogger(FilServiceServiceImpl.class);

    @Autowired
    private FilServiceMapper filServiceMapper;

    @Override
    public Mapper<FilService, Integer> getMapper() {
        return filServiceMapper;
    }

    @Override
    public List<FilService> selectfilServiceByfalgPid(String flagPid) {
        return filServiceMapper.selectfilServiceByfalgPid(flagPid);
    }

}