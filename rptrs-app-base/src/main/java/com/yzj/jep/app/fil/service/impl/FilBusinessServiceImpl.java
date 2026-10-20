/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年07月21日 14:55:54
 *
 */

package com.yzj.jep.app.fil.service.impl;

import com.yzj.jep.app.fil.mapper.FilBusinessMapper;
import com.yzj.jep.app.fil.model.FilBusiness;
import com.yzj.jep.app.fil.service.FilBusinessService;
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
public class FilBusinessServiceImpl extends MyBatisCRUDService<FilBusiness, Integer> implements FilBusinessService {

    Logger logger = LoggerFactory.getLogger(FilBusinessServiceImpl.class);

    @Autowired
    private FilBusinessMapper filBusinessMapper;

    @Override
    public Mapper<FilBusiness, Integer> getMapper() {
        return filBusinessMapper;
    }

    @Override
    public List<FilBusiness> selectFilBusinessByfalgPid(String flagPid) {
        return filBusinessMapper.selectFilBusinessByfalgPid(flagPid);
    }

}