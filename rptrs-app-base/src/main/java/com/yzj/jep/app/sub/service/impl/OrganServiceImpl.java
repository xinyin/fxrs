/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月02日 09:48:44
 *
 */

package com.yzj.jep.app.sub.service.impl;

import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.sub.mapper.OrganMapper;
import com.yzj.jep.app.sub.model.Organ;
import com.yzj.jep.app.sub.service.OrganService;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service(value = "OrganServiceImpl")
@Transactional
public class OrganServiceImpl extends MyBatisCRUDService<Organ, Integer> implements OrganService {

    Logger logger = LoggerFactory.getLogger(OrganServiceImpl.class);

    @Autowired
    private OrganMapper organMapper;

    @Autowired
    private RelOrgService relOrgService;

    @Override
    public Mapper<Organ, Integer> getMapper() {
        return organMapper;
    }

    @Override
    public void extracOrgan(Map map, String nowDate) {
        String tag = (String) map.get("tag");
        List<RelOrg> relOrgs = new ArrayList<>();
        List<Filter> filters = new Filter.Builder().like("modifiedTime", nowDate).eq("approvalStatus","6").eq("delflag", "1")
                                .eq("effectRelation","1").ne("uniscCode","9131000005459537XP").build();
        Page<RelOrg> page = new Page<>(1, 100);
        PageHelper.start(page);
        if ("all".equals(tag)) {
            relOrgs = relOrgService.selectAll();
        } else {
            relOrgs = relOrgService.select(filters);
        }
        //开始抽取
        //startExtract(relOrgs);

        PageHelper.end(page,relOrgs);
        int pageSize = page.getPageSize();
        List<RelOrg> orgs = page.getResult();
        //开始抽取
        startExtract(orgs);
        for (int i = 2; i <= pageSize; i++) {
            //从第二页开始循环
            page=new Page<>(i,100);
            PageHelper.start(page);
            if ("all".equals(tag)){
                relOrgs=relOrgService.selectAll();
            }else {
                relOrgs=relOrgService.select(filters);
            }
            PageHelper.end(page,relOrgs);
            orgs = page.getResult();
            //开始抽取
            startExtract(orgs);
        }
    }

    private void startExtract(List<RelOrg> relOrgs) {
        for (RelOrg relOrg : relOrgs) {
            Organ organ = new Organ();
            BeanUtils.copyProperties(relOrg, organ);
            organ.setIdentityNo(relOrg.getUniscCode());
            organ.setName(relOrg.getOrgName());
            organ.setStatus("3");
            organ.setStatusComment("数据已抽取");
            organ.setIsExtract("0");
            boolean exists = organMapper.existsByPrimaryKey(relOrg.getPid());
            try {
                if (exists) {
                    organMapper.updateSelective(organ);
                } else {
                    organMapper.insertSelective(organ);
                }
            } catch (ServiceException e) {
                logger.error("法人或其他组织" + organ.getName() + "数据更新异常：", e);
            }
        }
    }
}