/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年05月03日 09:44:42
 *
 */

package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.model.LimitSensory;
import com.yzj.jep.app.bus.service.LimitSensoryService;
import com.yzj.jep.orm.entity.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.bus.mapper.LimitTransMapper;
import com.yzj.jep.app.bus.service.LimitTransService;
import com.yzj.jep.app.bus.model.LimitTrans;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class LimitTransServiceImpl extends MyBatisCRUDService<LimitTrans, Integer> implements LimitTransService {

    Logger logger = LoggerFactory.getLogger(LimitTransServiceImpl.class);

    @Autowired
    private LimitTransMapper limitTransMapper;
    @Autowired
    private LimitSensoryService limitSensoryService;

    @Override
    public Mapper<LimitTrans, Integer> getMapper() {
        return limitTransMapper;
    }

    @Override
    public String warnUse(Integer pid, Integer rate) {
        List<Filter> filters = new Filter.Builder()
                .eq("busLeve", pid)
                .eq("delflag", "1").build();

        boolean exists1 = limitSensoryService.exists(filters);
        String warn = "0";
        Integer warnThreshold1l = 0;
        Integer warnThreshold2l = 0;
        Integer warnThreshold3l = 0;
        if (exists1) {
            LimitSensory limitSensory = limitSensoryService.get(pid);
            if (limitSensory != null) {
                warnThreshold1l = limitSensory.getWarnThreshold1l();
                warnThreshold2l = limitSensory.getWarnThreshold2l();
                warnThreshold3l = limitSensory.getWarnThreshold3l();
            }
        }
        if (warnThreshold3l > rate) {
            warn = "3";
        } else if (rate > warnThreshold2l) {
            warn = "2";
        } else if (rate > warnThreshold1l) {
            warn = "1";
        }
        return warn;
    }


}