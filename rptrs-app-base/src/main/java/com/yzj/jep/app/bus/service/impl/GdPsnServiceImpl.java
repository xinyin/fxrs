/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年04月01日 11:59:13
 *
 */

package com.yzj.jep.app.bus.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.bus.mapper.GdPsnMapper;
import com.yzj.jep.app.bus.service.GdPsnService;
import com.yzj.jep.app.bus.model.GdPsn;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GdPsnServiceImpl extends MyBatisCRUDService<GdPsn, Integer> implements GdPsnService {

    Logger logger = LoggerFactory.getLogger(GdPsnServiceImpl.class);

    @Autowired
    private GdPsnMapper gdPsnMapper;

    @Override
    public Mapper<GdPsn, Integer> getMapper() {
        return gdPsnMapper;
    }

    @Override
    public Float getUseShareAmount(String code) {
        GdPsn gdPsn = new GdPsn();
        gdPsn.setShareholdersCode(code);
        gdPsn.setDelflag("1");
        List<GdPsn> select = gdPsnMapper.select(gdPsn);
        if (select.size() > 0) {
            GdPsn psn = select.get(0);
            Float holeShareAmount = Float.parseFloat(psn.getHoleShareAmount());
            Float frozenShareAmount = Float.parseFloat(psn.getFrozenShareAmount());
            Float pledgedShareAmount = Float.parseFloat(psn.getPledgedShareAmount());
            return holeShareAmount - frozenShareAmount - pledgedShareAmount;
        }
        return null;
    }
}