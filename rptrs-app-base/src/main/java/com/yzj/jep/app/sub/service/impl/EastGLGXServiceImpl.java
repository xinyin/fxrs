/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年12月13日 15:41:26
 *
 */

package com.yzj.jep.app.sub.service.impl;

import com.yzj.jep.app.enums.Submitted;
import com.yzj.jep.app.sub.mapper.EastGLGXMapper;
import com.yzj.jep.app.sub.model.EastGLGX;
import com.yzj.jep.app.sub.model.RelatedRelation;
import com.yzj.jep.app.sub.service.EastGLGXService;
import com.yzj.jep.app.sub.service.RelatedRelationService;
import com.yzj.jep.app.util.BatchNumberUtil;
import com.yzj.jep.app.util.GuuidUtil;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import com.yzj.jep.sys.service.DictItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class EastGLGXServiceImpl extends MyBatisCRUDService<EastGLGX, Integer> implements EastGLGXService {

    Logger logger = LoggerFactory.getLogger(EastGLGXServiceImpl.class);

    @Autowired
    private EastGLGXMapper eastGLGXMapper;

    @Autowired
    private RelatedRelationService relatedRelationService;

    @Autowired
    DictItemService dictItemService;

    @Override
    public Mapper<EastGLGX, Integer> getMapper() {
        return eastGLGXMapper;
    }

    @Override
    public void eastExtract() {
        List<Filter> filter = new Filter.Builder().eq("isExtract", "0")
                                         .eq("delflag", 1).build();
        List<RelatedRelation> pros = relatedRelationService.select(filter);
        EastGLGX glgx = new EastGLGX();
        if(pros!=null && pros.size()>0){
            for(RelatedRelation rr : pros){
                String pch = BatchNumberUtil.makeBatchNumber();
                glgx.setBussedate(DateUtils.formatDateShort(rr.getCreateTime()));
                glgx.setGlfmcGlfzjjdmc(rr.getEntityName());
                glgx.setGlfdmGlfzjjddm(rr.getEntityUniscUode());
                glgx.setGlfGlfzjjdlx(rr.getEntityType());
                glgx.setQycglfmcGlfzjjdmc(rr.getSuperEntityName());
                glgx.setQycglfdmGlfzjjddm(rr.getSuperEntityUniscCode());
                glgx.setQycglfGlfzjjdlx(rr.getSuperEntityType());
                glgx.setYqycdglgx(rr.getRelationType());
                glgx.setCybl(rr.getHoldingPercent());
                glgx.setGlgxsm(rr.getRelationInfo());
                glgx.setGxyxbz(rr.getValidStatus());
                glgx.setSxrq(DateUtils.formatDateShort(rr.getActiveTime()));
                glgx.setSxrq1(DateUtils.formatDateShort(rr.getInvalidTime()));
                glgx.setEastStatus(1);
                glgx.setSubOrg(Submitted.ORGNUMBER.getName());
                glgx.setSubNum(Submitted.ORGNUMBER.getId());
                glgx.setKhbh(Submitted.NOTAPPLICABLE.getName());
                glgx.setKhlb(Submitted.NOTAPPLICABLE.getName());
                glgx.setKhsbm(Submitted.NOTAPPLICABLE.getName());
                glgx.setKhmc(Submitted.NOTAPPLICABLE.getName());
                glgx.setGlfbh(GuuidUtil.getUUID());
                glgx.setLxh(pch);
                //保存并更新
                glgx.setCreateTime(new Date());
                getMapper().insert(glgx);
                rr.setIsExtract("1");
                relatedRelationService.updateSelective(rr);
            }
        }
    }
}