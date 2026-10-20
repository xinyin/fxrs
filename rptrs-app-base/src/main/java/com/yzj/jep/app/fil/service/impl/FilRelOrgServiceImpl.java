/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年07月10日 18:04:43
 *
 */

package com.yzj.jep.app.fil.service.impl;


import com.yzj.jep.app.bus.model.RelOrgVo;
import com.yzj.jep.app.fil.mapper.FilRelOrgMapper;
import com.yzj.jep.app.fil.model.FilRelOrg;
import com.yzj.jep.app.fil.model.FilRelOrgVo;
import com.yzj.jep.app.fil.model.FilRelProVo;
import com.yzj.jep.app.fil.model.FilRelPsnVo;
import com.yzj.jep.app.fil.service.FilRelOrgService;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class FilRelOrgServiceImpl extends MyBatisCRUDService<FilRelOrg, Integer> implements FilRelOrgService {

    Logger logger = LoggerFactory.getLogger(FilRelOrgServiceImpl.class);

    @Autowired
    private FilRelOrgMapper filRelOrgMapper;

    @Override
    public Mapper<FilRelOrg, Integer> getMapper() {
        return filRelOrgMapper;
    }

    @Override
    public List<FilRelOrgVo> getTZOrgPsn(String orgid) {
        return filRelOrgMapper.getTZOrgPsn(orgid);
    }

    @Override
    public List<FilRelOrgVo> getDownTZOrgPsn(String orgid) {
        return filRelOrgMapper.getDownTZOrgPsn(orgid);
    }

    @Override
    public List<FilRelPsnVo> getOrgPsn(String orgid) {
        return filRelOrgMapper.getOrgPsn(orgid);
    }

    @Override
    public List<FilRelPsnVo> getDownOrgPsn(String orgid) {
        return filRelOrgMapper.getDownOrgPsn(orgid);
    }

    @Override
    public List<FilRelOrgVo> getOrgOrg(String orgid) {
        return filRelOrgMapper.getOrgOrg(orgid);
    }

    @Override
    public List<FilRelOrgVo> getDownOrgOrg(String orgid) {
        return filRelOrgMapper.getDownOrgOrg(orgid);
    }

    @Override
    public List<FilRelProVo> getOrgUpPro(String orgid) {
        return filRelOrgMapper.getOrgUpPro(orgid);
    }

    @Override
    public List<FilRelProVo> getOrgDownPro(String orgid) {
        return filRelOrgMapper.getOrgDownPro(orgid);
    }

    @Override
    public List<FilRelOrgVo> selecUpOrg(String valueOf) {
        return filRelOrgMapper.selecUpOrg(valueOf);
    }

    @Override
    public List<FilRelOrgVo> selecDownOrg(String valueOf) {
        return filRelOrgMapper.selecDownOrg(valueOf);
    }

    @Override
    public List<FilRelOrg> selectFilRelOrgByfalgPid(String flagPid) {
        return filRelOrgMapper.selectFilRelOrgByfalgPid(flagPid);
    }


}