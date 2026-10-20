/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年07月21日 14:55:55
 *
 */

package com.yzj.jep.app.fil.service.impl;

import com.yzj.jep.app.fil.mapper.FilFundsMapper;
import com.yzj.jep.app.fil.model.FilFunds;
import com.yzj.jep.app.fil.service.FilFundsService;
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
public class FilFundsServiceImpl extends MyBatisCRUDService<FilFunds, Integer> implements FilFundsService {

    Logger logger = LoggerFactory.getLogger(FilFundsServiceImpl.class);

    @Autowired
    private FilFundsMapper filFundsMapper;

    @Override
    public Mapper<FilFunds, Integer> getMapper() {
        return filFundsMapper;
    }

    @Override
    public List<FilFunds> selectfilFundsByfalgPid(String flagPid) {

        return filFundsMapper.selectfilFundsByfalgPid(flagPid);
    }

}