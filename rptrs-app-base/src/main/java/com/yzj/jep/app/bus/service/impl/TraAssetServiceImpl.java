/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月22日 16:09:49
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

import com.yzj.jep.app.bus.mapper.TraAssetMapper;
import com.yzj.jep.app.bus.service.TraAssetService;
import com.yzj.jep.app.bus.model.TraAsset;

import java.math.BigDecimal;

@Service
@Transactional(readOnly = false)
public class TraAssetServiceImpl extends MyBatisCRUDService<TraAsset, Integer> implements TraAssetService {

    Logger logger = LoggerFactory.getLogger(TraAssetServiceImpl.class);

    @Autowired
    private TraAssetMapper traAssetMapper;

    @Override
    public Mapper<TraAsset, Integer> getMapper() {
        return traAssetMapper;
    }

    @Override
    public TraAsset selectByID(Integer pid) {
        return traAssetMapper.selectByID(pid);
    }

    @Override
    public void updateStatus(Integer pid) {
        traAssetMapper.updateStatus(pid);
    }

    @Override
    public Integer queryMaxID() {

        return traAssetMapper.queryMaxID();
    }

    @Override
    public void del(Integer pid) {
        traAssetMapper.del(pid);
    }

    @Override
    public BigDecimal getBalance(TraAsset traAsset) {
        return traAssetMapper.getBalance(traAsset);
    }

    @Override
    public void approvalReturn(Integer pid) {
        TraAsset traAsset = new TraAsset();
        traAsset.setStatus("5");
        traAsset.setPid(pid);
        traAssetMapper.updateSelective(traAsset);
    }
}