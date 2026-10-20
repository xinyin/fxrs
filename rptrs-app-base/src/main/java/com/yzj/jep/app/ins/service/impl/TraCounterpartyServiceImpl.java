/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月19日 15:14:52
 *
 */

package com.yzj.jep.app.ins.service.impl;

import com.yzj.jep.app.ins.mapper.TraCounterpartyMapper;
import com.yzj.jep.app.ins.model.TraCounterparty;
import com.yzj.jep.app.ins.service.TraCounterpartyService;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TraCounterpartyServiceImpl extends MyBatisCRUDService<TraCounterparty, Integer> implements TraCounterpartyService {

    Logger logger = LoggerFactory.getLogger(TraCounterpartyServiceImpl.class);
    @Autowired
    private TraCounterpartyMapper traCounterpartyMapper;

    @Override
    public Mapper<TraCounterparty, Integer> getMapper() {
        return traCounterpartyMapper;
    }

    @Override
    public void grandTradeZeroNew(Map map) {
        traCounterpartyMapper.grandTradeZeroNew(map);
    }

    @Override
    public void updataCounterpartyStatusByTransNum(TraCounterparty tra) {
        traCounterpartyMapper.updataCounterpartyStatusByTransNum(tra);
    }

    @Override
    public List<TraCounterparty> queryInRoadTransList(Map<String, Object> map) {
        try {
            return traCounterpartyMapper.queryInRoadTransList(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Boolean updataCounterpartyByTransNum(String transNum,String status, Date contractDate, String relTrandeAmount, BigDecimal bigDecimalOld) {
        return traCounterpartyMapper.updataCounterpartyByTransNum(transNum,status,contractDate,relTrandeAmount, bigDecimalOld);
    }

}