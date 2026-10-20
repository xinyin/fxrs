/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年05月18日 17:42:12
 *
 */

package com.yzj.jep.app.bus.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.bus.mapper.TraBalanceDeductionMapper;
import com.yzj.jep.app.bus.service.TraBalanceDeductionService;
import com.yzj.jep.app.bus.model.TraBalanceDeduction;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;

@Service
@Transactional(readOnly = true)
public class TraBalanceDeductionServiceImpl extends MyBatisCRUDService<TraBalanceDeduction, Integer> implements TraBalanceDeductionService {

    Logger logger = LoggerFactory.getLogger(TraBalanceDeductionServiceImpl.class);

    @Autowired
    private TraBalanceDeductionMapper traBalanceDeductionMapper;

    @Override
    public Mapper<TraBalanceDeduction, Integer> getMapper() {
        return traBalanceDeductionMapper;
    }

}