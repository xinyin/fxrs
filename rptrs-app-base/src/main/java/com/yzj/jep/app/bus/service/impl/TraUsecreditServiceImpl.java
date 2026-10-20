/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年05月27日 16:35:32
 *
 */

package com.yzj.jep.app.bus.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.bus.mapper.TraUsecreditMapper;
import com.yzj.jep.app.bus.service.TraUsecreditService;
import com.yzj.jep.app.bus.model.TraUsecredit;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;

import java.math.BigDecimal;

@Service
@Transactional()
public class TraUsecreditServiceImpl extends MyBatisCRUDService<TraUsecredit, Integer> implements TraUsecreditService {

    Logger logger = LoggerFactory.getLogger(TraUsecreditServiceImpl.class);

    @Autowired
    private TraUsecreditMapper traUsecreditMapper;

    @Override
    public Mapper<TraUsecredit, Integer> getMapper() {
        return traUsecreditMapper;
    }

    @Override
    public void approvalReturn(Integer pid, String memo) {
        TraUsecredit traUsecredit = traUsecreditMapper.selectByPrimaryKey(pid);
//        if ("1".equals(traUsecredit.getIsEffective())) {
//            traUsecredit.setThisTraAmount(traUsecredit.getThisTraAmount().subtract(traUsecredit.getThisTraDiff()));
//            traUsecredit.setThisTraBalance(traUsecredit.getThisTraAmount());
//        }
        traUsecredit.setThisTraDiff(BigDecimal.ZERO);
        traUsecredit.setStatus("5");
        traUsecredit.setStatusComment(memo);
        traUsecreditMapper.updateSelective(traUsecredit);
    }

    @Override
    public BigDecimal getTradeBalance(TraUsecredit traUsecredit) {
        return traUsecreditMapper.getTradeBalance(traUsecredit);
    }
}