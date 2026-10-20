/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年12月19日 15:10:30
 *
 */

package com.yzj.jep.app.sub.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.yzj.jep.app.enums.DictionaryEnum;
import com.yzj.jep.app.ins.model.*;
import com.yzj.jep.app.ins.service.*;
import com.yzj.jep.app.sub.mapper.InsAllbustraninfoMapper;
import com.yzj.jep.app.sub.model.InsAllbustraninfo;
import com.yzj.jep.app.sub.service.InsAllbustraninfoService;
import com.yzj.jep.app.util.DateUtils;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class InsAllbustraninfoServiceImpl extends MyBatisCRUDService<InsAllbustraninfo, Integer> implements InsAllbustraninfoService {

    Logger logger = LoggerFactory.getLogger(InsAllbustraninfoServiceImpl.class);

    @Autowired
    private InsAllbustraninfoMapper insAllbustraninfoMapper;

    @Autowired
    private TraInterestService traInterestService;

    @Autowired
    private TraFundsService traFundsService;

    @Autowired
    private TraBusinessService traBusinessService;

    @Autowired
    private TraInvestmentService traInvestmentService;

    @Autowired
    private TraServiceService traServiceService;

    @Autowired
    private TraOtherService traOtherService;

    @Autowired
    private DictItemService dictItemService;

    @Override
    public Mapper<InsAllbustraninfo, Integer> getMapper() {
        return insAllbustraninfoMapper;
    }

    @Override
    public void extractCredit(Map map) {
        Date nowTime = new Date();
        int years = DateUtil.year(nowTime);
        int quarter = DateUtil.quarter(nowTime);
        if(1 == quarter){
            years = years - 1;
            quarter = 4;
        }else {
            quarter = quarter - 1;
        }

        // 【当前季度的上个季度】的上季度开始日期
        String startTime = DateUtils.preQuarterBeginDateStr(nowTime);
        // 【当前季度的上个季度】的上季度结束日期
        String endTime = DateUtils.preQuarterEndDateStr(nowTime);
        //开始抽取
        startExtract(startTime, endTime, String.valueOf(years), String.valueOf(quarter));
    }

    @Override
    public List<Map<String, Object>> selectAllTrans(String year, String subQuarter, String tid) {
        List<Map<String, Object>> maps=null;
        try {
          maps = insAllbustraninfoMapper.selectAllTrans(year, subQuarter, tid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return maps;
    }

    private void startExtract(String startTime, String endTime, String subYear, String subQuarter) {
        try {
            //抽取合同日期在本季度内的数据
            List<Filter> filters = new Filter.Builder().ge("tradTime", startTime).le("tradTime", endTime).eq("dataVersion","E2.0").eq("status","6").eq("delflag", "1").build();
            List<Filter> filterss = new Filter.Builder().ge("tranDate", startTime).le("tranDate", endTime).eq("dataVersion","E2.0").eq("status","6").eq("delflag", "1").build();

//            List<TraInvestment> trainvestments = traInvestmentService.select(filters);
            List<TraFunds> traFunds = traFundsService.select(filters);
            List<TraBusiness> traBusinesses = traBusinessService.select(filterss);
            List<TraInterest> traInterests = traInterestService.select(filterss);
            List<TraService> traServices = traServiceService.select(filterss);
//            List<TraOther> TraOthers = traOtherService.select(filterss);

//            for (TraInvestment tra : trainvestments) {//投资入股类
//                InsAllbustraninfo insAllbustraninfo = new InsAllbustraninfo();
//                BeanUtils.copyProperties(tra, insAllbustraninfo);
//                if (tra.getTranDate() == null) {
//                    insAllbustraninfo.setTranDate(tra.getBusStartTime());
//                }
//                //交易金额
//                insAllbustraninfo.setTradeAmount(tra.getRelTradeAmount());
//                insAllbustraninfo.setPid(null);
//                insAllbustraninfo.setSubQuarter(subQuarter);
//                insAllbustraninfo.setSubYear(subYear);
//                insAllbustraninfo.setStatus("3");
//                insAllbustraninfo.setTraStatus(tra.getStatus());
//                insAllbustraninfo.setPortPid(tra.getPid());
//                insAllbustraninfo.setIsDiscovery("0");
//                insertSelective(insAllbustraninfo);
//            }


             //资金运用类
            for (TraFunds tra : traFunds) {
                InsAllbustraninfo insAllbustraninfo = new InsAllbustraninfo();
                BeanUtils.copyProperties(tra, insAllbustraninfo);
                String contractNo = StringUtils.isNotEmpty(tra.getUniqueCode()) ? tra.getUniqueCode() : tra.getTransNum();
                if (tra.getTranDate() == null) {
                    insAllbustraninfo.setTranDate(tra.getBusStartTime());
                }

                if ("04".equals(tra.getBusType())) {
                    insAllbustraninfo.setFinancialAssstsName(tra.getFinancialAssstsName());
                    insAllbustraninfo.setFinancialProScale(tra.getFinancialAssstsNum());
                    insAllbustraninfo.setFinancialProRatio(tra.getFinancialProRatio());
                    insAllbustraninfo.setUnderInvolveRel(tra.getUnderInvolveRel());
                }
                insAllbustraninfo.setIsAgreement("0");
                insAllbustraninfo.setInvestmentType(tra.getBusType());
                //交易金额
                insAllbustraninfo.setTradeAmount(StringUtils.isNotEmpty(tra.getPracTradeAmount())?new BigDecimal(tra.getPracTradeAmount()):tra.getRelTradeAmount());
                insAllbustraninfo.setContractNo(contractNo);
                insAllbustraninfo.setPid(null);
                insAllbustraninfo.setSubQuarter(subQuarter);
                insAllbustraninfo.setSubYear(subYear);
                insAllbustraninfo.setStatus("3");
                insAllbustraninfo.setPortPid(tra.getPid());
                insAllbustraninfo.setIsDiscovery("0");
                List<Filter> filter = new Filter.Builder().eq("subYear", subYear).eq("subQuarter", subQuarter).build();
                filter.add(new Filter("contractNo",contractNo));
                filter.add(new Filter("tranType",tra.getTranType()));
                List<InsAllbustraninfo> insAllbustraninfoList = select(filter);
                if(!CollUtil.isEmpty(insAllbustraninfoList)){
                    insAllbustraninfo.setPid(insAllbustraninfoList.get(0).getPid());
                    updateSelective(insAllbustraninfo);
                }else {
                    insertSelective(insAllbustraninfo);
                }

            }
            //保险业务类
            for (TraBusiness tra : traBusinesses) {
                InsAllbustraninfo insAllbustraninfo = new InsAllbustraninfo();
                BeanUtils.copyProperties(tra, insAllbustraninfo);
                String contractNo = StringUtils.isNotEmpty(tra.getUniqueCode()) ? tra.getUniqueCode() : tra.getTransNum();
                insAllbustraninfo.setTranDate(tra.getContractDate());
                if (tra.getContractAmount() == null) {
                    insAllbustraninfo.setTranDate(tra.getBusStartTime());
                }
                insAllbustraninfo.setInvestmentType(tra.getBusType());
                insAllbustraninfo.setContractNo(contractNo);
                //交易金额
                insAllbustraninfo.setTradeAmount(StringUtils.isNotEmpty(tra.getPracTradeAmount())?new BigDecimal(tra.getPracTradeAmount()):tra.getRelTradeAmount());
                insAllbustraninfo.setPid(null);
                insAllbustraninfo.setSubQuarter(subQuarter);
                insAllbustraninfo.setSubYear(subYear);
                insAllbustraninfo.setIsDiscovery("0");
                insAllbustraninfo.setStatus("3");
                insAllbustraninfo.setTranDate(tra.getContractDate());
                List<Filter> filter = new Filter.Builder().eq("subYear", subYear).eq("subQuarter", subQuarter).build();
                filter.add(new Filter("contractNo",contractNo));
                filter.add(new Filter("tranType",tra.getTranType()));
                List<InsAllbustraninfo> insAllbustraninfoList = select(filter);
                if(!CollUtil.isEmpty(insAllbustraninfoList)){
                    insAllbustraninfo.setPid(insAllbustraninfoList.get(0).getPid());
                    updateSelective(insAllbustraninfo);
                }else {
                    insertSelective(insAllbustraninfo);
                }
            }
            for (TraInterest tra : traInterests) {
                //利益转移类
                InsAllbustraninfo insAllbustraninfo = new InsAllbustraninfo();
                BeanUtils.copyProperties(tra, insAllbustraninfo);
                String contractNo = StringUtils.isNotEmpty(tra.getUniqueCode()) ? tra.getUniqueCode() : tra.getTransNum();
                insAllbustraninfo.setTranDate(tra.getContractDate());
                insAllbustraninfo.setIsAgreement("0");
                insAllbustraninfo.setContractNo(contractNo);
                insAllbustraninfo.setInvestmentType(tra.getBusType());
                //实际交易金额
                insAllbustraninfo.setTradeAmount(StringUtils.isNotEmpty(tra.getPracTradeAmount())?new BigDecimal(tra.getPracTradeAmount()):tra.getRelTradeAmount());
                insAllbustraninfo.setPid(null);
                insAllbustraninfo.setSubQuarter(subQuarter);
                insAllbustraninfo.setSubYear(subYear);
                insAllbustraninfo.setStatus("3");
                insAllbustraninfo.setIsDiscovery("0");
                List<Filter> filter = new Filter.Builder().eq("subYear", subYear).eq("subQuarter", subQuarter).build();
                filter.add(new Filter("contractNo",contractNo));
                filter.add(new Filter("tranType",tra.getTranType()));
                List<InsAllbustraninfo> insAllbustraninfoList = select(filter);
                if(!CollUtil.isEmpty(insAllbustraninfoList)){
                    insAllbustraninfo.setPid(insAllbustraninfoList.get(0).getPid());
                    updateSelective(insAllbustraninfo);
                }else {
                    insertSelective(insAllbustraninfo);
                }
            }
            //提供货物类
            for (TraService tra : traServices) {
                InsAllbustraninfo insAllbustraninfo = new InsAllbustraninfo();
                String contractNo = StringUtils.isNotEmpty(tra.getUniqueCode()) ? tra.getUniqueCode() : tra.getTransNum();

                BeanUtils.copyProperties(tra, insAllbustraninfo);
                insAllbustraninfo.setTranDate(tra.getContractDate());
                insAllbustraninfo.setContractNo(contractNo);
                //实际交易金额
                insAllbustraninfo.setTradeAmount(StringUtils.isNotEmpty(tra.getPracTradeAmount())?new BigDecimal(tra.getPracTradeAmount()):tra.getRelTradeAmount());

                insAllbustraninfo.setPid(null);
                insAllbustraninfo.setSubQuarter(subQuarter);
                insAllbustraninfo.setSubYear(subYear);
                insAllbustraninfo.setIsDiscovery("0");
                insAllbustraninfo.setStatus("3");
                List<Filter> filter = new Filter.Builder().eq("subYear", subYear).eq("subQuarter", subQuarter).build();
                filter.add(new Filter("contractNo",contractNo));
                filter.add(new Filter("tranType",tra.getTranType()));
                List<InsAllbustraninfo> insAllbustraninfoList = select(filter);
                if(!CollUtil.isEmpty(insAllbustraninfoList)){
                    insAllbustraninfo.setPid(insAllbustraninfoList.get(0).getPid());
                    updateSelective(insAllbustraninfo);
                }else {
                    insertSelective(insAllbustraninfo);
                }
            }

//            for (TraOther tra : TraOthers) {//其他类
//                InsAllbustraninfo insAllbustraninfo = new InsAllbustraninfo();
//                BeanUtils.copyProperties(tra, insAllbustraninfo);
//                insAllbustraninfo.setTranDate(tra.getContractDate());
//                if (tra.getContractDate() == null) {
//                    insAllbustraninfo.setTranDate(tra.getBusStartTime());
//                }
//                insAllbustraninfo.setInvestmentType(tra.getBusType());
//                //实际交易金额
//                insAllbustraninfo.setTradeAmount(tra.getRelTradeAmount());
//                insAllbustraninfo.setPid(null);
//                insAllbustraninfo.setSubQuarter(subQuarter);
//                insAllbustraninfo.setSubYear(subYear);
//                insAllbustraninfo.setStatus("3");
//                insAllbustraninfo.setIsDiscovery("0");
//                insertSelective(insAllbustraninfo);
//            }
        } catch (ServiceException e) {
            logger.error("关联交易明细表数据更新异常：", e);
        }
    }

    /**
     * @param property1
     * @param value1
     * @return
     * @desc 构建查询的filter
     */
    public List<Filter> builderFilter(String property1, String value1) {
        List<Filter> filters = new Filter.Builder().eq(property1, value1).build();
        return filters;
    }

    /**
     * @param dictCode
     * @return
     * @desc 获取字典信息
     */
    public Map<String, Map<String, String>> obtainDictInfo(String dictCode) {
        Map<String, Map<String, String>> dictCodeMap = new HashMap<>(16);
        List<DictItem> dictItemsInvestment = dictItemService.select(builderFilter(dictCode, DictionaryEnum.INS_INVESTMENTTYPE.getCode()));
        Map investmentTypeMap = dictItemsInvestment.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("investmentType", investmentTypeMap);
        List<DictItem> dictItemsFunds = dictItemService.select(builderFilter(dictCode, DictionaryEnum.INS_FUNDSTYPE.getCode()));
        Map fundsTypeMap = dictItemsFunds.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("fundsType", fundsTypeMap);
        List<DictItem> dictItemsBusiness = dictItemService.select(builderFilter(dictCode, DictionaryEnum.INS_BUSINESSTYPE.getCode()));
        Map businessTypeMap = dictItemsBusiness.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("businessType", businessTypeMap);
        List<DictItem> dictItemsInterest = dictItemService.select(builderFilter(dictCode, DictionaryEnum.INS_INTERESTTYPE.getCode()));
        Map interestTypeMap = dictItemsInterest.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("interestType", interestTypeMap);
        List<DictItem> dictItemsService = dictItemService.select(builderFilter(dictCode, DictionaryEnum.INS_SERVICETYPE.getCode()));
        Map serviceTypeMap = dictItemsService.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("serviceType", serviceTypeMap);
        List<DictItem> dictItemsOther = dictItemService.select(builderFilter(dictCode, DictionaryEnum.INS_OTHERBUSTYPE.getCode()));
        Map otherTypeMap = dictItemsOther.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("otherType", otherTypeMap);
        return dictCodeMap;
    }
}
