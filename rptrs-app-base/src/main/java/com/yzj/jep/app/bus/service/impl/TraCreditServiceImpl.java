/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月22日 16:09:52
 *
 */

package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.bus.mapper.TraCreditMapper;
import com.yzj.jep.app.bus.service.TraCreditService;
import com.yzj.jep.app.bus.model.TraCredit;

import java.math.BigDecimal;


@Service
@Transactional(readOnly = false)
public class TraCreditServiceImpl extends MyBatisCRUDService<TraCredit, Integer> implements TraCreditService {

    Logger logger = LoggerFactory.getLogger(TraCreditServiceImpl.class);

    @Autowired
    private TraCreditMapper traCreditMapper;

    @Override
    public Mapper<TraCredit, Integer> getMapper() {
        return traCreditMapper;
    }

    @Override
    public TraCredit selectByID(Integer pid) {
        return traCreditMapper.selectByID(pid);
    }

    @Override
    public void updateStatus(Integer pid) {
        traCreditMapper.updateStatus(pid);
    }

    @Override
    public Integer queryMaxID() {

        return traCreditMapper.queryMaxID();
    }

    @Override
    public void del(Integer pid) {
        traCreditMapper.del(pid);
    }

    @Override
    public BigDecimal getCreditBalance(TraCredit traCredit) {
        return traCreditMapper.getCreditBalance(traCredit);
    }

    @Override
    public void approvalReturn(Integer pid) {
        TraCredit traCredit = new TraCredit();
        traCredit.setStatus("5");
        traCredit.setPid(pid);
        traCreditMapper.updateSelective(traCredit);
    }

    @Override
    public BigDecimal getBankDeposit(TraCredit traCredit) {
        return traCreditMapper.getBankDeposit(traCredit);
    }

    @Override
    public BigDecimal getNationalDebt(TraCredit traCredit) {
        return traCreditMapper.getNationalDebt(traCredit);
    }

    @Override
    public BigDecimal getSecuritDeposit(TraCredit traCredit) {
        return traCreditMapper.getSecuritDeposit(traCredit);
    }
}