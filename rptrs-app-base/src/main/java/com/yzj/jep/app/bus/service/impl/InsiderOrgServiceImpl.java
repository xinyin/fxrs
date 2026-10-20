/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月23日 13:59:30
 *
 */

package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.mapper.InsiderOrgMapper;
import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.model.RelOrgVo;
import com.yzj.jep.app.bus.model.RelPsnVo;
import com.yzj.jep.app.bus.service.InsiderOrgService;
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
public class InsiderOrgServiceImpl extends MyBatisCRUDService<RelOrg, Integer> implements InsiderOrgService {

    Logger logger = LoggerFactory.getLogger(InsiderOrgServiceImpl.class);

    @Autowired
    private InsiderOrgMapper insiderOrgMapper;

    @Override
    public Mapper<RelOrg, Integer> getMapper() {

        return insiderOrgMapper;
    }

    @Override
    public List<RelOrg> getTZOrgPsn(String orgid) {

        return insiderOrgMapper.getTZOrgPsn(orgid);
    }

    @Override
    public List<RelOrg> selectList(RelOrg relOrg) {

        return insiderOrgMapper.selectList(relOrg);
    }

    @Override
    public List<RelOrg> selectListNew(RelOrg relOrg) {

        return insiderOrgMapper.selectListNew(relOrg);
    }

    @Override
    public List<RelOrg> queryOrgList() {
        return
                insiderOrgMapper.queryOrgList();
    }

    @Override
    public List<RelOrg> getOrgPsn(String orgid) {

        return insiderOrgMapper.getOrgPsn(orgid);
    }

    @Override
    public RelOrg getGdInfo(String code) {
        RelOrg relOrg = new RelOrg();
        relOrg.setShareholdersCode(code);
        relOrg.setIsShareholder("1");
        relOrg.setDelflag("1");
        List<RelOrg> relOrgs = insiderOrgMapper.select(relOrg);
        if (relOrgs.size() > 0) {
            return relOrgs.get(0);
        }
        return null;
    }
}