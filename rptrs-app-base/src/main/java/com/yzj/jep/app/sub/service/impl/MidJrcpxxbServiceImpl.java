/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年12月13日 15:41:26
 *
 */

package com.yzj.jep.app.sub.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.ObjectUtil;
import com.yzj.jep.app.bus.mapper.RelProMapper;
import com.yzj.jep.app.bus.model.RelPro;
import com.yzj.jep.app.bus.service.RelProService;
import com.yzj.jep.app.enums.RdsDataSrc;
import com.yzj.jep.app.sub.mapper.MidJrcpxxbMapper;
import com.yzj.jep.app.sub.model.MidJrcpxxb;
import com.yzj.jep.app.sub.service.MidJrcpxxbService;
import com.yzj.jep.app.util.DateExtendUtil;
import com.yzj.jep.app.util.DateUtils;
import com.yzj.jep.app.util.DictUtil;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class MidJrcpxxbServiceImpl extends MyBatisCRUDService<MidJrcpxxb, Integer> implements MidJrcpxxbService {

    Logger logger = LoggerFactory.getLogger(MidJrcpxxbServiceImpl.class);

    @Autowired
    MidJrcpxxbMapper midJrcpxxbMapper;

    @Resource
    private RelProMapper relProMapper;

    @Override
    public Mapper<MidJrcpxxb, Integer> getMapper() {
        return midJrcpxxbMapper;
    }

    @Override
    public void createEastMidJrcpxxb(String batchNo) {
        List<RelPro> products = relProMapper.selectRelPro();

        //关联方类型	BUS-OrgOrPsn
        Map<String, String> proMap = DictUtil.convertToMap("BUS-pro");

        MidJrcpxxb jrcpxxb = null;
        for (RelPro relPro : products) {
            jrcpxxb = new MidJrcpxxb();
            jrcpxxb.setSerialno(batchNo + relPro.getPid());
            jrcpxxb.setBatchno(batchNo);
            jrcpxxb.setBussno(relPro.getProProvider());
            jrcpxxb.setMakedate(new Date());
            jrcpxxb.setBussdate(new Date());
            jrcpxxb.setDatasrc(RdsDataSrc.GLJY.getValue());
            jrcpxxb.setOthersign("");
            jrcpxxb.setRemark("");
            jrcpxxb.setBxjgmc(""); // TODO 待查问

            jrcpxxb.setJrcpbh(relPro.getProProvider()); //
            jrcpxxb.setJrcpqc(relPro.getProName());
            jrcpxxb.setCppzzcbah(relPro.getProProvider());
            jrcpxxb.setCpdm(relPro.getProCode());
            jrcpxxb.setCpfl(proMap.get(relPro.getProType()));
            jrcpxxb.setCpdjjg(relPro.getProRegOrg());

            jrcpxxb.setCpglrqc(relPro.getAdName());
            jrcpxxb.setGlrtyshxydm(relPro.getAdCreditCode());
            jrcpxxb.setCptgrqc(relPro.getCareName());
            jrcpxxb.setTgrtyshxydm(relPro.getCareCreditCode());
            jrcpxxb.setDczc(relPro.getAssetDesc());
            jrcpxxb.setTzz(relPro.getInvestors());
            jrcpxxb.setCjrq(DateExtendUtil.endOfLastMonth(new Date()));
            jrcpxxb.setJgxqdm(""); // TODO 待查问

            insert(jrcpxxb);
        }


    }

}
