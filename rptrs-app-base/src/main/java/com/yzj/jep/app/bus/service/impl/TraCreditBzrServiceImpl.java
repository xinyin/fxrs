/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月28日 16:37:54
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

import com.yzj.jep.app.bus.mapper.TraCreditBzrMapper;
import com.yzj.jep.app.bus.service.TraCreditBzrService;
import com.yzj.jep.app.bus.model.TraCreditBzr;


@Service
@Transactional(readOnly = true)
public class TraCreditBzrServiceImpl extends MyBatisCRUDService<TraCreditBzr, Integer> implements TraCreditBzrService {

    Logger logger = LoggerFactory.getLogger(TraCreditBzrServiceImpl.class);

    @Autowired
    private TraCreditBzrMapper traCreditBzrMapper;

    @Override
    public Mapper<TraCreditBzr, Integer> getMapper() {
        return traCreditBzrMapper;
    }

    @Override
    public Integer queryMaxID() {

        return traCreditBzrMapper.queryMaxID();

    }
	/*@Override
	public void del(Integer creditId) {
		traCreditBzrMapper.del(creditId);
	}*/
}