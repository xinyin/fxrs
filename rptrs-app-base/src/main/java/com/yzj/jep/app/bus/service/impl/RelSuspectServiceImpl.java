/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月23日 14:26:18
 *
 */

package com.yzj.jep.app.bus.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.bus.model.RelOrgSuspect;
import com.yzj.jep.app.bus.model.RelPsnSuspect;
import com.yzj.jep.app.bus.service.RelOrgSuspectService;
import com.yzj.jep.app.bus.service.RelPsnSuspectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.bus.mapper.RelSuspectMapper;
import com.yzj.jep.app.bus.service.RelSuspectService;
import com.yzj.jep.app.bus.model.RelSuspect;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RelSuspectServiceImpl extends MyBatisCRUDService<RelSuspect, Integer> implements RelSuspectService {

    Logger logger = LoggerFactory.getLogger(RelSuspectServiceImpl.class);

    @Autowired
    private RelSuspectMapper relSuspectMapper;

    @Autowired
    private RelPsnSuspectService relPsnSuspectService;
    @Autowired
    private RelOrgSuspectService relOrgSuspectService;

    @Override
    public Mapper<RelSuspect, Integer> getMapper() {
        return relSuspectMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public void removeRel(String fromId, String toId, String fromType, String toType) {
        RelSuspect relSuspect = new RelSuspect();
        relSuspect.setToPid(toId);
        relSuspect.setToType(toType);
        relSuspect.setFromPid(fromId);
        relSuspect.setFromType(fromType);
        Integer pid = relSuspectMapper.select(relSuspect).get(0).getPid();
        relSuspect.setPid(pid);
        if ("1".equals(relSuspectMapper.select(relSuspect).get(0).getDelflag())) {
            relSuspect.setDelflag("0");
            relSuspectMapper.updateSelective(relSuspect);
        }
        RelSuspect relCond = new RelSuspect();
        relCond.setToType(toType);
        relCond.setToPid(toId);
        relCond.setDelflag("1");
        List<RelSuspect> rels = relSuspectMapper.select(relCond);
        if (rels.size() > 0) {
            RelSuspect relCon2 = new RelSuspect();
            relCon2.setToType(toType);
            relCon2.setToPid(toId);
            relCon2.setDelflag("1");
            List<RelSuspect> relList = relSuspectMapper.select(relCon2);
            for (RelSuspect rel1 : relList) {
                String fromPid = rel1.getFromPid();
                String fromType1 = rel1.getFromType();
                RelSuspect rel2 = new RelSuspect();
                rel2.setToPid(fromPid);
                rel2.setToType(fromType1);
                rel2.setDelflag("1");
                List<RelSuspect> relList1 = relSuspectMapper.select(rel2);
                if (relList1.size() > 0) {
                    continue;
                } else {
                    relCon2.setPid(rel1.getPid());
                    relCon2.setFromPid(fromPid);
                    relCon2.setFromType(fromType1);
                    relCon2.setDelflag("0");
                    relSuspectMapper.updateSelective(relCon2);
                }

            }
        }
        RelSuspect relCon = new RelSuspect();
        relCon.setToPid(toId);
        relCon.setToType(toType);
        relCon.setDelflag("1");
        List<RelSuspect> relList = relSuspectMapper.select(relCon);
        if (relList.size() > 0) {
            return;
        }
        RelSuspect relCond1 = new RelSuspect();
        relCond1.setFromType(toType);
        relCond1.setFromPid(toId);
        List<RelSuspect> relList1 = relSuspectMapper.select(relCond1);
        for (RelSuspect rel1 : relList1) {
            removeRel(rel1.getFromPid(), rel1.getToPid(), rel1.getFromType(), rel1.getToType());
        }
    }

    @Override
    public void returnSuspectTask(JSONObject json, String memo) {
        Integer pid = (Integer) json.get("pid");
        //流程type by Wrf
        String proessType = (String) json.get("processType");
        //认定结果  0是非关联方 1是关联方
        String isSuspect = (String) json.get("isSuspect");
        if ("CheckByPsn".equals(proessType)) {
            RelPsnSuspect relPsnSuspect = relPsnSuspectService.get(pid);
            //设置为审批退回
            relPsnSuspect.setStatus("3");
            relPsnSuspect.setStatusComment(memo);
            relPsnSuspectService.updateSelective(relPsnSuspect);
        } else if ("CheckByOrg".equals(proessType)) {
            RelOrgSuspect relOrgSuspect = relOrgSuspectService.get(pid);
            //设置为审批退回
            relOrgSuspect.setApprovalStatus("3");
            relOrgSuspect.setStatusComment(memo);
            relOrgSuspectService.updateSelective(relOrgSuspect);
        }
    }

}