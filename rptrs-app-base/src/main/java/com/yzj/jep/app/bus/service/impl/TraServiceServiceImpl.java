/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月18日 15:01:38
 *
 */

package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.model.TraService;
import com.yzj.jep.app.bus.service.TraServiceService;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.bus.mapper.TraServiceMapper;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional(readOnly = false)
public class TraServiceServiceImpl extends MyBatisCRUDService<TraService, Integer> implements TraServiceService {

    Logger logger = LoggerFactory.getLogger(TraServiceServiceImpl.class);

    @Autowired
    private TraServiceMapper traServiceMapper;

    @Override
    public Mapper<TraService, Integer> getMapper() {
        return traServiceMapper;
    }

    @Override
    public Integer queryMaxID() {
        return traServiceMapper.queryMaxID();
    }

    @Override
    public void updateIsDel(Integer pid) {
        traServiceMapper.updateIsDel(pid);
    }

    @Override
    public TraService selectByID(Integer pid) {
        TraService result = traServiceMapper.selectByID(pid);
        return result;
    }

    @Override
    public void updateStatus(Integer pid) {
        traServiceMapper.updateStatus(pid);
    }

    @Override
    public List<String> selStaffSH() {
        return traServiceMapper.selStaffSH();
    }

    @Override
    public List<String> selStaffFH() {
        return traServiceMapper.selStaffFH();
    }

    @Override
    public BigDecimal getBalance(TraService traService) {
        return traServiceMapper.getBalance(traService);
    }

    @Override
    public void approvalReturn(Integer pid) {
        TraService traService = new TraService();
        traService.setStatus("5");
        traService.setPid(pid);
        traServiceMapper.updateSelective(traService);
    }
}