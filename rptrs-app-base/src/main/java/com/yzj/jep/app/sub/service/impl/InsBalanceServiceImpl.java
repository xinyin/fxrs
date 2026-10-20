/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年12月14日 17:01:19
 *
 */

package com.yzj.jep.app.sub.service.impl;

import com.yzj.jep.app.sub.mapper.InsBalanceMapper;
import com.yzj.jep.app.sub.model.InsBalance;
import com.yzj.jep.app.sub.service.InsBalanceService;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "InsBalanceServiceImpl")
@Transactional
public class InsBalanceServiceImpl extends MyBatisCRUDService<InsBalance, Integer> implements InsBalanceService {

    Logger logger = LoggerFactory.getLogger(InsBalanceServiceImpl.class);
    @Autowired
    private InsBalanceMapper insBalanceMapper;

    @Override
    public Mapper<InsBalance, Integer> getMapper() {
        return insBalanceMapper;
    }

}