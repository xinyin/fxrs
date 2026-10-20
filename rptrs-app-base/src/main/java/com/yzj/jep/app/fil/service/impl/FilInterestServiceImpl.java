/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年07月21日 14:55:57
 *
 */

package com.yzj.jep.app.fil.service.impl;

import com.yzj.jep.app.fil.mapper.FilInterestMapper;
import com.yzj.jep.app.fil.model.FilInterest;
import com.yzj.jep.app.fil.service.FilInterestService;
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
public class FilInterestServiceImpl extends MyBatisCRUDService<FilInterest, Integer> implements FilInterestService {

    Logger logger = LoggerFactory.getLogger(FilInterestServiceImpl.class);

    @Autowired
    private FilInterestMapper filInterestMapper;

    @Override
    public Mapper<FilInterest, Integer> getMapper() {
        return filInterestMapper;
    }

    @Override
    public List<FilInterest> selectfilInterestByfalgPid(String flagPid) {
        return filInterestMapper.selectfilInterestByfalgPid(flagPid);
    }

}