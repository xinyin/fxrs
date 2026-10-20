/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月26日 18:23:04
 *
 */

package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.mapper.RelDictMapper;
import com.yzj.jep.app.bus.model.RelDict;
import com.yzj.jep.app.bus.service.RelDictService;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional(readOnly = true)
public class RelDictServiceImpl extends MyBatisCRUDService<RelDict, String> implements RelDictService {

    Logger logger = LoggerFactory.getLogger(RelDictServiceImpl.class);

    @Autowired
    private RelDictMapper relDictMapper;

    @Override
    public Mapper<RelDict, String> getMapper() {
        return relDictMapper;
    }

    @Override
    public RelDict selectDict(String relCategory, String relCategorySub) {
        List<Filter> filter = new Filter.Builder().eq("relCategoryCode", relCategory).eq("relCategorySubCode", relCategorySub).build();
        List<RelDict> relDictList = select(filter);
        return relDictList.get(0);
    }
}