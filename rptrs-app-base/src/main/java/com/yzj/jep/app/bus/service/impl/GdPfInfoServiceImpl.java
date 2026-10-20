/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年04月19日 14:20:05
 *
 */

package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.model.GdPfInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.bus.mapper.GdPfInfoMapper;
import com.yzj.jep.app.bus.service.GdPfInfoService;
import com.yzj.jep.app.bus.model.GdPfInfo;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;

import java.util.List;

@Service
@Transactional()
public class GdPfInfoServiceImpl extends MyBatisCRUDService<GdPfInfo, Integer> implements GdPfInfoService {

    Logger logger = LoggerFactory.getLogger(GdPfInfoServiceImpl.class);

    @Autowired
    private GdPfInfoMapper gdPfInfoMapper;

    @Override
    public Mapper<GdPfInfo, Integer> getMapper() {
        return gdPfInfoMapper;
    }

    @Override
    public void createNewPfInfo(GdPfInfo gdPfInfo) {
        gdPfInfoMapper.createNewPfInfo(gdPfInfo);
    }

    @Override
    public void changePfInfo(List<GdPfInfo> gdPfInfos) {
        gdPfInfos.forEach(gdPfInfo -> gdPfInfoMapper.changePfInfo(gdPfInfo));
    }

    @Override
    public List<GdPfInfoVo> queryGdPfInfo(GdPfInfoVo gdPfInfoVo) {
        return gdPfInfoMapper.queryGdPfInfo(gdPfInfoVo);
    }
}