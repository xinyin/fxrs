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
import com.yzj.jep.app.sub.mapper.RdsMiddatalogMapper;
import com.yzj.jep.app.sub.model.RdsMiddatalog2;
import com.yzj.jep.app.sub.service.*;
import com.yzj.jep.app.util.DateUtils;
import com.yzj.jep.app.util.EastSubTypeEnum;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
@Transactional
public class RdsMiddatalogServiceImpl extends MyBatisCRUDService<RdsMiddatalog2, Integer> implements RdsMiddatalogService {

    Logger logger = LoggerFactory.getLogger(RdsMiddatalogServiceImpl.class);

    @Autowired
    RdsMiddatalogMapper rdsMiddatalogMapper;
    @Autowired
    private MidJrcpxxbService midJrcpxxbService;
    @Resource
    private MidGlfxxbService midGlfxxbService;
    @Resource
    private MidGlgxbService midGlgxbService;


    @Autowired
    private MidGljymxbService midGljymxbService;

    @Autowired
    private  MidLyzylgljyxyxxbService midLyzylgljyxyxxbService;

    @Autowired
    private  MidZjyylgljyxyxxbService midZjyylgljyxyxxbService;

    @Autowired
    private MidBxywhqtlgljyxyxxbService midBxywhqtlgljyxyxxbService;

    @Autowired
    private MidFwlgljyxyxxbService midFwlgljyxyxxbService;
//    @Autowired
//    PersonService personService;
//    @Autowired
//    OrganService organService;
//    @Autowired
//    RelService relService;
//    @Autowired
//    RelPsnService relPsnService;
//    @Autowired
//    RelOrgService relOrgService;
//    @Autowired
//    UnitService unitService;
//    @Autowired
//    DictItemService dictItemService;

    private static final String dataSource = "08";


    @Override
    public Mapper<RdsMiddatalog2, Integer> getMapper() {
        return rdsMiddatalogMapper;
    }

    @Override
    @Transactional
    public void createEastBatch(String[] eastTypes) {
        // 获取上月日期开始
        String dataStartDate = DateUtils.preMonthBeginDateStr(new Date());
        // 获取上月日期截止
        String dataEndDate = DateUtils.preMonthEndDateStr(new Date());

        // 生成批次号
        String batchNo = createBatchNo();

        for (String eastType : eastTypes) {
           if (EastSubTypeEnum.PRODUCT.name().equals(eastType)) {
               midJrcpxxbService.createEastMidJrcpxxb(batchNo);
           }else if (EastSubTypeEnum.RELATED.name().equals(eastType)) {
               midGlfxxbService.createEastMidGlfxxb(batchNo);
           } else if (EastSubTypeEnum.RELATION.name().equals(eastType)) {
               //关联关系
               midGlgxbService.createEastMidGlgxb(batchNo);
//               midJrcpxxbService.createEastMidJrcpxxb(batchNo);
           }else if (EastSubTypeEnum.SERVICE.name().equals(eastType)){
               midFwlgljyxyxxbService.createEastMidFwlgljyxyxxb(batchNo);
           }else if (EastSubTypeEnum.BUSINESS.name().equals(eastType)){
               midBxywhqtlgljyxyxxbService.createEastMidBxywhqtlgljyxyxxb(batchNo);
           }else if (EastSubTypeEnum.FUNDS.name().equals(eastType)){
               midZjyylgljyxyxxbService.createEastMidZjyylgljyxyxxb(batchNo);
           }else if (EastSubTypeEnum.INTEREST.name().equals(eastType)){
               midLyzylgljyxyxxbService.createEastMidLyzylgljyxyxxb(batchNo);
           }else if (EastSubTypeEnum.TRANSACTION.name().equals(eastType)){
              midGljymxbService.createEastMidGljymxb(batchNo);
           }

        }

        RdsMiddatalog2 dataLog = new RdsMiddatalog2();
        dataLog.setLogId(ObjectUtil.toString(System.currentTimeMillis()));
        dataLog.setBatchNo(batchNo);
        dataLog.setDataStartDate(dataStartDate);
        dataLog.setDataEndDate(dataEndDate);
        dataLog.setDataSource(dataSource);
        dataLog.setDealStatus("S");

        dataLog.setCreateTime(new Date());
        dataLog.setModifiedTime(new Date());
        insert(dataLog);
    }

    @Override
    public String createBatchNo() {
        // 如 09 20250103 123455 0001
        String batchNo = dataSource + com.yzj.jep.commons.DateUtils.format(new Date(), DatePattern.PURE_DATETIME_PATTERN) + "01";
        return batchNo;
    }
}
