/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月02日 15:15:15
 *
 */

package com.yzj.jep.app.sub.service.impl;

import com.yzj.jep.app.bus.model.TraAsset;
import com.yzj.jep.app.bus.model.TraOther;
import com.yzj.jep.app.bus.model.TraService;
import com.yzj.jep.app.bus.service.TraAssetService;
import com.yzj.jep.app.bus.service.TraOtherService;
import com.yzj.jep.app.bus.service.TraServiceService;
import com.yzj.jep.app.sub.mapper.NonloanMapper;
import com.yzj.jep.app.sub.model.Nonloan;
import com.yzj.jep.app.sub.service.NonloanService;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
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

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service(value = "NonloanServiceImpl")
@Transactional
public class NonloanServiceImpl extends MyBatisCRUDService<Nonloan, Integer> implements NonloanService {

    Logger logger = LoggerFactory.getLogger(NonloanServiceImpl.class);

    @Autowired
    private NonloanMapper nonloanMapper;

    @Autowired
    private TraOtherService traOtherService;

    @Autowired
    private TraAssetService traAssetService;

    @Autowired
    private TraServiceService traServiceService;

    @Override
    public Mapper<Nonloan, Integer> getMapper() {
        return nonloanMapper;
    }

    @Override
    public void extractNoLoan(Map map, String nowDate) {
        String tag = (String) map.get("tag");
        List<TraOther> traTraOthers = new ArrayList<>();
        Page<TraOther> page = new Page<>(1, 100);
        List<TraAsset> traAssetstras = new ArrayList<>();
        Page<TraAsset> traAssetPage = new Page<>(1, 100);
        List<TraService> traServices = new ArrayList<>();
        Page<TraService> traServicePage = new Page<>(1, 100);
        PageHelper.start(page);
        PageHelper.start(traAssetPage);
        PageHelper.start(traServicePage);
        if ("all".equals(tag)) {
            traTraOthers = traOtherService.selectAll();
            traAssetstras = traAssetService.selectAll();
            traServices = traServiceService.selectAll();
        } else {
            List<Filter> filters = new Filter.Builder().like("modifiedTime", nowDate).build();
            traTraOthers = traOtherService.select(filters);
            traAssetstras = traAssetService.select(filters);
            traServices = traServiceService.select(filters);
        }
        PageHelper.end(page, traTraOthers);
        PageHelper.end(traAssetPage, traAssetstras);
        PageHelper.end(traServicePage, traServices);
        //交易其他类总页数
        int pageSize = page.getPages();
        List<TraOther> exTraTraOthers = page.getResult();
        //开始抽取
        startExtract(exTraTraOthers);
        for (int i = 2; i <= pageSize; i++) {
            //从第二页开始循环
            page = new Page<>(i, 100);
            PageHelper.start(page);
            if ("all".equals(tag)) {
                traTraOthers = traOtherService.selectAll();
            } else {
                List<Filter> filters = new Filter.Builder().like("modifiedTime", nowDate).build();
                traTraOthers = traOtherService.select(filters);
            }
            PageHelper.end(page, traTraOthers);
            exTraTraOthers = page.getResult();
            //开始抽取
            startExtract(exTraTraOthers);
        }

        //交易资产转移类总页数
        int apageSize = traAssetPage.getPages();
        List<TraAsset> exTraAssets = traAssetPage.getResult();
        //开始抽取
        startExtractAssets(exTraAssets);
        for (int i = 2; i <= apageSize; i++) {
            //从第二页开始循环
            traAssetPage = new Page<>(i, 100);
            PageHelper.start(traAssetPage);
            if ("all".equals(tag)) {
                traAssetstras = traAssetService.selectAll();
            } else {
                List<Filter> filters = new Filter.Builder().like("modifiedTime", nowDate).build();
                traAssetstras = traAssetService.select(filters);
            }
            PageHelper.end(traAssetPage, traAssetstras);
            exTraAssets = traAssetPage.getResult();
            //开始抽取
            startExtractAssets(exTraAssets);
        }

        //交易提供服务类总页数
        int spageSize = traServicePage.getPages();
        List<TraService> exTraServices = traServicePage.getResult();
        //开始抽取
        startExtractService(exTraServices);
        for (int i = 2; i <= spageSize; i++) {
            //从第二页开始循环
            traServicePage = new Page<>(i, 100);
            PageHelper.start(traServicePage);
            if ("all".equals(tag)) {
                traTraOthers = traOtherService.selectAll();
            } else {
                List<Filter> filters = new Filter.Builder().like("modifiedTime", nowDate).build();
                traTraOthers = traOtherService.select(filters);
            }
            PageHelper.end(traServicePage, traTraOthers);
            exTraServices = traServicePage.getResult();
            //开始抽取
            startExtractService(exTraServices);
        }
    }

    private void startExtractService(List<TraService> exTraServices) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (TraService traService : exTraServices) {
            Nonloan nonloan = new Nonloan();
            BeanUtils.copyProperties(traService, nonloan);
            nonloan.setRelatedPartyName(traService.getRelatedName());
            nonloan.setRelatedPartyCertNo(traService.getIdentNumber());
            nonloan.setRelatedPartyType(traService.getRelType());
            nonloan.setIsDeclared(traService.getImportantTrade());
            try {
                nonloan.setSignDate(traService.getContractSignDate() == null ? null : sdf.parse(traService.getContractSignDate()));
                nonloan.setTransStartDate(traService.getBusinessStartTime() == null ? null : sdf.parse(traService.getBusinessStartTime()));
                nonloan.setTransEndDate(traService.getBusinessEndTime() == null ? null : sdf.parse(traService.getBusinessEndTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            nonloan.setTransType("03");
            nonloan.setBuzType(traService.getBusinessType());
            nonloan.setTransAmount(traService.getTranAmount() == null ? null : new BigDecimal(traService.getTranAmount()));
            nonloan.setTransPrice(traService.getTranPrice() == null ? null : new BigDecimal(traService.getTranPrice()));
            nonloan.setTransSubject(traService.getTranTarget());
            nonloan.setPricingMark(traService.getPricingStrategy());
            nonloan.setStatus("0");
            nonloan.setStatusComment("数据已抽取");
            List<Filter> filters = new Filter.Builder().eq("pid", nonloan.getPid()).build();
            try {
                Nonloan dbNoLoan = this.get(filters);
                if (dbNoLoan != null) {
                    this.update(nonloan);
                } else {
                    this.insert(nonloan);
                }
            } catch (ServiceException e) {
                logger.error("用信明细[" + nonloan.getContractNo() + "]数据更新异常：", e);
            }
        }
    }

    private void startExtractAssets(List<TraAsset> exTraAssets) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (TraAsset traAsset : exTraAssets) {
            Nonloan nonloan = new Nonloan();
            BeanUtils.copyProperties(traAsset, nonloan);
            nonloan.setRelatedPartyName(traAsset.getRelatedName());
            nonloan.setRelatedPartyCertNo(traAsset.getIdentNumber());
            nonloan.setRelatedPartyType(traAsset.getRelType());
            nonloan.setIsDeclared(traAsset.getImportantTrade());
            try {
                nonloan.setSignDate(traAsset.getContractSignDate() == null ? null : sdf.parse(traAsset.getContractSignDate()));
                nonloan.setTransStartDate(traAsset.getBusinessStartTime() == null ? null : sdf.parse(traAsset.getBusinessStartTime()));
                nonloan.setTransEndDate(traAsset.getBusinessEndTime() == null ? null : sdf.parse(traAsset.getBusinessEndTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            nonloan.setTransType("02");
            nonloan.setBuzType(traAsset.getBusinessType());
            nonloan.setTransAmount(traAsset.getTranAmount() == null ? null : new BigDecimal(traAsset.getTranAmount()));
            nonloan.setTransPrice(traAsset.getTranPrice() == null ? null : new BigDecimal(traAsset.getTranPrice()));
            nonloan.setTransSubject(traAsset.getTranTarget());
            nonloan.setPricingMark(traAsset.getPricingStrategy());
            nonloan.setStatus("0");
            nonloan.setStatusComment("数据已抽取");
            List<Filter> filters = new Filter.Builder().eq("pid", nonloan.getPid()).build();
            try {
                Nonloan dbNoLoan = this.get(filters);
                if (dbNoLoan != null) {
                    this.update(nonloan);
                } else {
                    this.insert(nonloan);
                }
            } catch (ServiceException e) {
                logger.error("用信明细[" + nonloan.getContractNo() + "]数据更新异常：", e);
            }
        }
    }

    private void startExtract(List<TraOther> traOthers) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (TraOther traOther : traOthers) {
            Nonloan nonloan = new Nonloan();
            BeanUtils.copyProperties(traOther, nonloan);
            nonloan.setRelatedPartyName(traOther.getRelatedName());
            nonloan.setRelatedPartyCertNo(traOther.getIdentNumber());
            nonloan.setRelatedPartyType(traOther.getRelType());
            nonloan.setIsDeclared(traOther.getImportantTrade());
            try {
                nonloan.setSignDate(traOther.getContractSignDate() == null ? null : sdf.parse(traOther.getContractSignDate()));
                nonloan.setTransStartDate(traOther.getBusinessStartTime() == null ? null : sdf.parse(traOther.getBusinessStartTime()));
                nonloan.setTransEndDate(traOther.getBusinessEndTime() == null ? null : sdf.parse(traOther.getBusinessEndTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            nonloan.setTransType("04");
            nonloan.setBuzType(traOther.getBusinessType());
            nonloan.setTransAmount(traOther.getTranAmount() == null ? null : new BigDecimal(traOther.getTranAmount()));
            nonloan.setTransPrice(traOther.getTranPrice() == null ? null : new BigDecimal(traOther.getTranPrice()));
            nonloan.setTransSubject(traOther.getTranTarget());
            nonloan.setPricingMark(traOther.getPricingStrategy());
            nonloan.setStatus("0");
            nonloan.setStatusComment("数据已抽取");
            List<Filter> filters = new Filter.Builder().eq("pid", nonloan.getPid()).build();
            try {
                Nonloan dbNoLoan = this.get(filters);
                if (dbNoLoan != null) {
                    this.update(nonloan);
                } else {
                    this.insert(nonloan);
                }
            } catch (ServiceException e) {
                logger.error("用信明细[" + nonloan.getContractNo() + "]数据更新异常：", e);
            }
        }
    }
}