/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年07月21日 14:55:58
 *
 */

package com.yzj.jep.app.fil.service.impl;

import com.yzj.jep.app.fil.mapper.FilInvestmentMapper;
import com.yzj.jep.app.fil.model.FilInvestment;
import com.yzj.jep.app.fil.service.FilInvestmentService;
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
public class FilInvestmentServiceImpl extends MyBatisCRUDService<FilInvestment, Integer> implements FilInvestmentService {

    Logger logger = LoggerFactory.getLogger(FilInvestmentServiceImpl.class);

    @Autowired
    private FilInvestmentMapper filInvestmentMapper;

    @Override
    public Mapper<FilInvestment, Integer> getMapper() {
        return filInvestmentMapper;
    }

    @Override
    public List<FilInvestment> selectFilInvestmentByfalgPid(String flagPid) {
        return filInvestmentMapper.selectFilInvestmentByfalgPid(flagPid);
    }

}