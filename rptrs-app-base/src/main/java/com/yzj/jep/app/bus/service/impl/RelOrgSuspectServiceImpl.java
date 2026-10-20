/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月23日 14:26:10
 *
 */

package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.model.Rel;
import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.model.RelPsnSuspect;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.RelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.bus.mapper.RelOrgSuspectMapper;
import com.yzj.jep.app.bus.service.RelOrgSuspectService;
import com.yzj.jep.app.bus.model.RelOrgSuspect;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class RelOrgSuspectServiceImpl extends MyBatisCRUDService<RelOrgSuspect, Integer> implements RelOrgSuspectService {

    Logger logger = LoggerFactory.getLogger(RelOrgSuspectServiceImpl.class);

    @Autowired
    private RelOrgSuspectMapper relOrgSuspectMapper;

    @Autowired
    private RelOrgService relOrgService;

    @Autowired
    private RelService relService;


    @Override
    public Mapper<RelOrgSuspect, Integer> getMapper() {
        return relOrgSuspectMapper;
    }

    @Override
    public List<RelPsnSuspect> queryOrgSubInfo(Integer pid) {
        return null;
    }

    @Override
    public Integer getMaxId(String tableName) {
        return relOrgSuspectMapper.getMaxId(tableName);
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public Map<String, Object> replaceOrg(Integer pid, RelOrg relOrg, List<Rel> rels) {
        Map<String, Object> tips = new HashMap<>();
        try {
            relOrgService.insertSelective(relOrg);
            Integer toPid = relOrgService.get(relOrg).getPid();
//			Integer toPid=relOrg.getPid();
            for (int i = 0; i < rels.size(); i++) {
                Rel rel = rels.get(i);
                rel.setDelflag("1");
//				rel.setIsShow("1");
                rel.setToType("ORG");
                rel.setToPid(toPid.toString());
                relService.updateSelective(rel);
            }
            relOrgSuspectMapper.deleteByPrimaryKey(pid);
            tips.put("result", "true");
        } catch (Exception e) {
            e.printStackTrace();
            tips.put("result", "false");
        }
        return tips;
    }
}