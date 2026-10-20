/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月30日 15:51:59
 *
 */

package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.apache.commons.collections.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.bus.mapper.TraEnclosureMapper;
import com.yzj.jep.app.bus.service.TraEnclosureService;
import com.yzj.jep.app.bus.model.TraEnclosure;

import java.util.List;


@Service
@Transactional(readOnly = false)
public class TraEnclosureServiceImpl extends MyBatisCRUDService<TraEnclosure, Integer> implements TraEnclosureService {

    Logger logger = LoggerFactory.getLogger(TraEnclosureServiceImpl.class);

    @Autowired
    private TraEnclosureMapper traEnclosureMapper;

    @Override
    public Mapper<TraEnclosure, Integer> getMapper() {
        return traEnclosureMapper;
    }

    @Override
    public void updateTransNum(List<Integer> docTableSelectItem, String transNum) {
        if (docTableSelectItem != null) {
            for (Integer pid : docTableSelectItem) {
                TraEnclosure traEnclosure = new TraEnclosure();
                traEnclosure.setPid(pid);
                traEnclosure.setTransNum(transNum);
                updateSelective(traEnclosure);
            }
        }
    }
}