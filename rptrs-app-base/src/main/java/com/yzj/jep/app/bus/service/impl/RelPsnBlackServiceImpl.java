/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月24日 16:43:06
 *
 */

package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.mapper.RelPsnBlackMapper;
import com.yzj.jep.app.bus.model.RelPsnBlack;
import com.yzj.jep.app.bus.service.RelPsnBlackService;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class RelPsnBlackServiceImpl extends MyBatisCRUDService<RelPsnBlack, Integer> implements RelPsnBlackService {

    Logger logger = LoggerFactory.getLogger(RelPsnBlackServiceImpl.class);

    @Autowired
    private RelPsnBlackMapper relPsnBlackMapper;

    @Override
    public Mapper<RelPsnBlack, Integer> getMapper() {
        return relPsnBlackMapper;
    }

}