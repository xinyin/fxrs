package com.yzj.jep.app.ins.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.ins.mapper.TraCounterpartyMapper;
import com.yzj.jep.app.ins.model.TraCounterparty;
import com.yzj.jep.app.ins.service.*;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.sys.service.UnitService;
import com.yzj.jep.web.code.WebCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class BaseServiceImpl implements BaseService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseServiceImpl.class);

    @Autowired
    private RelOrgService relOrgService;

    @Autowired
    private RelPsnService relPsnService;

    @Autowired
    private UnitService unitService;

    @Autowired
    private TraFundsService traFundsService;

    @Autowired
    private TraServiceService traServiceService;

    @Autowired
    private TraInvestmentService traInvestmentService;

    @Autowired
    private TraInterestService traInterestService;

    @Autowired
    private TraBusinessService traBusinessService;

    @Autowired
    private TraOtherService traOtherService;

    @Autowired
    private MajorTraCheckService majorTraCheckService;

    @Autowired
    private TraCounterpartyMapper traCounterpartyMapper;

    @Override
    public Map<String, Object> getRelInfo(String relNumber, String relName) {
        LOGGER.info("根据条件查询关联方信息...unitCode[{}],relNumber[{}],identifyType[{}],relName[{}]", relNumber, relName);
        Map<String, Object> tips = Maps.newHashMap();
        List<Filter> relPsnFilters = Lists.newArrayList();
        List<Filter> relOrgFilters = Lists.newArrayList();
        if (StringUtils.isNotEmpty(relName)) {
            relPsnFilters.add(Filter.eq("name", relName));
            relOrgFilters.add(Filter.eq("orgName", relName));
        }
        if (StringUtils.isNotEmpty(relNumber)) {
            relPsnFilters.add(Filter.eq("identifyNumber", relNumber));
            relOrgFilters.add(Filter.eq("uniscCode", relNumber));
        }
     //   UserProfile userProfile = UserProfileUtils.getUserProfile();
     //   String unitCode = userProfile.getUnitCode();
     //   List<String> codes = unitService.selectUnitAllOrDeptAllCodes(unitCode);
//        relPsnFilters.add(Filter.in("mgerOrg", codes));
//        relOrgFilters.add(Filter.in("mgerOrg", codes));
//        relPsnFilters.add(Filter.nnl("aligCode"));
//        relOrgFilters.add(Filter.nnl("aligCode"));
        relPsnFilters.add(Filter.eq("delflag", "1"));
        relOrgFilters.add(Filter.eq("delflag", "1"));
        List<RelPsn> relPsns = relPsnService.select(relPsnFilters);
        List<RelOrg> relOrgs = relOrgService.select(relOrgFilters);
        LOGGER.info("查询结果数量:自然人关联方[{}],机构关联方[{}]", relPsns.size(), relOrgs.size());
        int count = relPsns.size() + relOrgs.size();
        if (count == 0) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "无此关联方");
            return tips;
        }
        if (count == 1) {
            if (relPsns.size() == 1) {
                RelPsn psn = relPsns.get(0);
                LOGGER.info("自然人关联方");
                tips.put("relType", "PSN");
                tips.put("relId", psn.getPid());
                tips.put("relName", psn.getName());
                tips.put("certType", psn.getIdentifyType());
                tips.put("relNumber", psn.getIdentifyNumber());
                tips.put("caliber", psn.getRegulatorOth1());
            }
            if (relOrgs.size() == 1) {
                RelOrg org = relOrgs.get(0);
                LOGGER.info("机构关联方");
                tips.put("relType", "ORG");
                tips.put("relId", org.getPid());
                tips.put("relName", org.getOrgName());
                tips.put("certType", org.getIdentifyType());
                tips.put("relNumber", org.getUniscCode());
                tips.put("caliber", org.getRegulatorOth1());
            }
            Map<String, Object> stringObjectMap = majorTraCheckService.sumAmount((Integer) tips.get("relId"), (String) tips.get("relType"));
            BigDecimal totalTradeAmount = (BigDecimal) stringObjectMap.get("totalTradeAmount");
            BigDecimal tradeZeroAmount = (BigDecimal) stringObjectMap.get("tradeZeroAmount");
            tips.put("totalTradeAmount", totalTradeAmount);
            tips.put("tradeAmount", tradeZeroAmount);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
            return tips;
        }
        return tips;
    }

    @Override
    public BigDecimal getCurrYearTotalTradeAmount(Integer RelId, String RelType) {
        Map map = Maps.newHashMap();
        map.put("relId", RelId);
        map.put("relType", RelType);
        map.put("year", DateUtils.getCurrentYear());
        BigDecimal totalTradeAmount = BigDecimal.ZERO;
        BigDecimal fundsCurrYearTotalTradeAmount = traFundsService.getCurrYearTotalTradeAmount(map);
        BigDecimal serviceCurrYearTotalTradeAmount = traServiceService.getCurrYearTotalTradeAmount(map);
        BigDecimal investmentCurrYearTotalTradeAmount = traInvestmentService.getCurrYearTotalTradeAmount(map);
        BigDecimal interestCurrYearTotalTradeAmount = traInterestService.getCurrYearTotalTradeAmount(map);
        BigDecimal businessCurrYearTotalTradeAmount = traBusinessService.getCurrYearTotalTradeAmount(map);
        BigDecimal traOtherCurrYearTotalTradeAmount = traOtherService.getCurrYearTotalTradeAmount(map);
        fundsCurrYearTotalTradeAmount = fundsCurrYearTotalTradeAmount == null ? BigDecimal.ZERO : fundsCurrYearTotalTradeAmount;
        serviceCurrYearTotalTradeAmount = serviceCurrYearTotalTradeAmount == null ? BigDecimal.ZERO : serviceCurrYearTotalTradeAmount;
        investmentCurrYearTotalTradeAmount = investmentCurrYearTotalTradeAmount == null ? BigDecimal.ZERO : investmentCurrYearTotalTradeAmount;
        interestCurrYearTotalTradeAmount = interestCurrYearTotalTradeAmount == null ? BigDecimal.ZERO : interestCurrYearTotalTradeAmount;
        businessCurrYearTotalTradeAmount = businessCurrYearTotalTradeAmount == null ? BigDecimal.ZERO : businessCurrYearTotalTradeAmount;
        traOtherCurrYearTotalTradeAmount = traOtherCurrYearTotalTradeAmount == null ? BigDecimal.ZERO : traOtherCurrYearTotalTradeAmount;
        totalTradeAmount = totalTradeAmount.add(fundsCurrYearTotalTradeAmount).add(serviceCurrYearTotalTradeAmount).add(investmentCurrYearTotalTradeAmount).add(interestCurrYearTotalTradeAmount).add(businessCurrYearTotalTradeAmount).add(traOtherCurrYearTotalTradeAmount);
        return totalTradeAmount;
    }

    @Override
    public Long getMajorTradeCount(Integer relId, String relType) {
        List<Filter> build = new Filter.Builder().eq("relId", relId).eq("relType", relType).eq("isEffective", "1").eq("delflag", "1").eq("majorTrade", "1").build();
        long count = traFundsService.count(build);
        long count1 = traBusinessService.count(build);
        long count2 = traInterestService.count(build);
        long count3 = traServiceService.count(build);
        long count4 = traInvestmentService.count(build);
        return count + count1 + count2 + count3 + count4;
    }

    @Override
    public BigDecimal getCurrYearTotalTradeAmount(Integer RelId, String RelType, String traSubName, String isAccumulated, String countType) {
        List<String> traSubNameList = new ArrayList<String>();
        //TODO 将交易主体改为常量
        if ("FX00001_1".equals(traSubName)) {
            traSubNameList.add("复星保德信人寿保险有限公司");
        } else {
            traSubNameList.add(traSubName);
        }
        Map map = Maps.newHashMap();
        map.put("relId", RelId);
        map.put("relType", RelType);
        map.put("isAccumulated",isAccumulated);
        map.put("countType",countType);
        map.put("year", DateUtils.getCurrentYear());
        BigDecimal totalTradeAmount = BigDecimal.ZERO;
        for (String str : traSubNameList) {
            map.put("traSubName", str);
            map.put("tranType", "01");
            BigDecimal investmentCurrYearTotalTradeAmount = traInvestmentService.getCurrYearTotalTradeAmount(map);
            map.put("tranType", "02");
            BigDecimal fundsCurrYearTotalTradeAmount = traFundsService.getCurrYearTotalTradeAmount(map);
            map.put("tranType", "03");
            BigDecimal businessCurrYearTotalTradeAmount = traBusinessService.getCurrYearTotalTradeAmount(map);
            map.put("tranType", "04");
            BigDecimal interestCurrYearTotalTradeAmount = traInterestService.getCurrYearTotalTradeAmount(map);
            map.put("tranType", "05");
            BigDecimal serviceCurrYearTotalTradeAmount = traServiceService.getCurrYearTotalTradeAmount(map);
            map.put("tranType", "06");
            BigDecimal traOtherCurrYearTotalTradeAmount = traOtherService.getCurrYearTotalTradeAmount(map);

            //TODO 添加其他类求和
            fundsCurrYearTotalTradeAmount = fundsCurrYearTotalTradeAmount == null ? BigDecimal.ZERO : fundsCurrYearTotalTradeAmount;
            serviceCurrYearTotalTradeAmount = serviceCurrYearTotalTradeAmount == null ? BigDecimal.ZERO : serviceCurrYearTotalTradeAmount;
            investmentCurrYearTotalTradeAmount = investmentCurrYearTotalTradeAmount == null ? BigDecimal.ZERO : investmentCurrYearTotalTradeAmount;
            interestCurrYearTotalTradeAmount = interestCurrYearTotalTradeAmount == null ? BigDecimal.ZERO : interestCurrYearTotalTradeAmount;
            businessCurrYearTotalTradeAmount = businessCurrYearTotalTradeAmount == null ? BigDecimal.ZERO : businessCurrYearTotalTradeAmount;
            traOtherCurrYearTotalTradeAmount = traOtherCurrYearTotalTradeAmount == null ? BigDecimal.ZERO : traOtherCurrYearTotalTradeAmount;
            totalTradeAmount = totalTradeAmount.add(fundsCurrYearTotalTradeAmount).add(serviceCurrYearTotalTradeAmount).add(investmentCurrYearTotalTradeAmount).add(interestCurrYearTotalTradeAmount).add(businessCurrYearTotalTradeAmount).add(traOtherCurrYearTotalTradeAmount);
        }
        return totalTradeAmount;
    }

    @Override
    public BigDecimal getCurrYearTotalTradeAmount(Integer RelId, String RelType, String traSubName, String tranType,String isAccumulated, String countType) {
        List<String> traSubNameList = new ArrayList<String>();
        //TODO 将交易主体改为常量
        if("0001".equals(traSubName) || "0001-ZZHK".equals(traSubName)){
            traSubNameList.add("0001");
            traSubNameList.add("0001-ZZHK");
        }else{
            traSubNameList.add(traSubName);
        }
        Map map = Maps.newHashMap();
        map.put("relId", RelId);
        map.put("relType", RelType);
        map.put("tranType", tranType);
        map.put("year", DateUtils.getCurrentYear());
        map.put("isAccumulated",isAccumulated);
        map.put("countType",countType);
        BigDecimal totalTradeAmount = BigDecimal.ZERO;
        for (String str:traSubNameList) {
            map.put("traSubName", str);
            map.put("tranType", "01");
            BigDecimal investmentCurrYearTotalTradeAmount = traInvestmentService.getCurrYearTotalTradeAmount(map);
            map.put("tranType", "02");
            BigDecimal fundsCurrYearTotalTradeAmount = traFundsService.getCurrYearTotalTradeAmount(map);
            map.put("tranType", "03");
            BigDecimal businessCurrYearTotalTradeAmount = traBusinessService.getCurrYearTotalTradeAmount(map);
            map.put("tranType", "04");
            BigDecimal interestCurrYearTotalTradeAmount = traInterestService.getCurrYearTotalTradeAmount(map);
            map.put("tranType", "05");
            BigDecimal serviceCurrYearTotalTradeAmount = traServiceService.getCurrYearTotalTradeAmount(map);
            map.put("tranType", "06");
            BigDecimal traOtherCurrYearTotalTradeAmount = traOtherService.getCurrYearTotalTradeAmount(map);
            //TODO 添加其他类求和
            fundsCurrYearTotalTradeAmount = fundsCurrYearTotalTradeAmount == null ? BigDecimal.ZERO : fundsCurrYearTotalTradeAmount;
            serviceCurrYearTotalTradeAmount = serviceCurrYearTotalTradeAmount == null ? BigDecimal.ZERO : serviceCurrYearTotalTradeAmount;
            investmentCurrYearTotalTradeAmount = investmentCurrYearTotalTradeAmount == null ? BigDecimal.ZERO : investmentCurrYearTotalTradeAmount;
            interestCurrYearTotalTradeAmount = interestCurrYearTotalTradeAmount == null ? BigDecimal.ZERO : interestCurrYearTotalTradeAmount;
            businessCurrYearTotalTradeAmount = businessCurrYearTotalTradeAmount == null ? BigDecimal.ZERO : businessCurrYearTotalTradeAmount;
            traOtherCurrYearTotalTradeAmount = traOtherCurrYearTotalTradeAmount == null ? BigDecimal.ZERO : traOtherCurrYearTotalTradeAmount;
            totalTradeAmount = totalTradeAmount.add(fundsCurrYearTotalTradeAmount).add(serviceCurrYearTotalTradeAmount).add(investmentCurrYearTotalTradeAmount).add(interestCurrYearTotalTradeAmount).add(businessCurrYearTotalTradeAmount).add(traOtherCurrYearTotalTradeAmount);
        }
        return totalTradeAmount;
    }

    @Override
    public BigDecimal getInRoadTotalTradeAmount(Integer RelId, String RelType, String traSubName) {
        List<String> traSubNameList = new ArrayList<String>();
        //TODO 将交易主体改为常量
        if ("0001".equals(traSubName) || "0001-ZZHK".equals(traSubName)) {
            traSubNameList.add("0001");//中再资产
            traSubNameList.add("0001-ZZHK");//中再资产香港
        } else {
            traSubNameList.add(traSubName);
        }
        Map map = Maps.newHashMap();
        map.put("relId", RelId);
        map.put("relType", RelType);
        map.put("year", DateUtils.getCurrentYear());
        BigDecimal totalTradeAmount = BigDecimal.ZERO;
        for (String str : traSubNameList) {
            map.put("traSubName", str);
            map.put("tranType", "01");
            BigDecimal investmentCurrYearTotalTradeAmount = traInvestmentService.getInRoadTotalTradeAmount(map);
            map.put("tranType", "02");
            BigDecimal fundsCurrYearTotalTradeAmount = traFundsService.getInRoadTotalTradeAmount(map);
            map.put("tranType", "03");
            BigDecimal businessCurrYearTotalTradeAmount = traBusinessService.getInRoadTotalTradeAmount(map);
            map.put("tranType", "04");
            BigDecimal interestCurrYearTotalTradeAmount = traInterestService.getInRoadTotalTradeAmount(map);
            map.put("tranType", "05");
            BigDecimal serviceCurrYearTotalTradeAmount = traServiceService.getInRoadTotalTradeAmount(map);
            map.put("tranType", "06");
            BigDecimal traOtherCurrYearTotalTradeAmount = traOtherService.getInRoadTotalTradeAmount(map);

            fundsCurrYearTotalTradeAmount = fundsCurrYearTotalTradeAmount == null ? BigDecimal.ZERO : fundsCurrYearTotalTradeAmount;
            serviceCurrYearTotalTradeAmount = serviceCurrYearTotalTradeAmount == null ? BigDecimal.ZERO : serviceCurrYearTotalTradeAmount;
            investmentCurrYearTotalTradeAmount = investmentCurrYearTotalTradeAmount == null ? BigDecimal.ZERO : investmentCurrYearTotalTradeAmount;
            interestCurrYearTotalTradeAmount = interestCurrYearTotalTradeAmount == null ? BigDecimal.ZERO : interestCurrYearTotalTradeAmount;
            businessCurrYearTotalTradeAmount = businessCurrYearTotalTradeAmount == null ? BigDecimal.ZERO : businessCurrYearTotalTradeAmount;
            traOtherCurrYearTotalTradeAmount = traOtherCurrYearTotalTradeAmount == null ? BigDecimal.ZERO : traOtherCurrYearTotalTradeAmount;
            totalTradeAmount = totalTradeAmount.add(fundsCurrYearTotalTradeAmount).add(serviceCurrYearTotalTradeAmount).add(investmentCurrYearTotalTradeAmount).add(interestCurrYearTotalTradeAmount).add(businessCurrYearTotalTradeAmount).add(traOtherCurrYearTotalTradeAmount);
        }
        return totalTradeAmount;
    }

    /**
     * 根据累计交易规则，对累计数据进行归零处理
     *
     * @param RelId
     * @param RelType
     * @param traSubName
     * @param tranType
     */
    @Override
    public void grandTradeZero(Integer RelId, String RelType, String traSubName, String tranType) {
        try {
            LOGGER.info("请求交易类型——tranType="+tranType);
            List<String> traSubNameList = new ArrayList<String>();
            if ("0001".equals(traSubName) || "0001-ZZHK".equals(traSubName)) {
                traSubNameList.add("0001");//中再资产
                traSubNameList.add("0001-ZZHK");//中再资产香港
            } else {
                traSubNameList.add(traSubName);
            }
            Map map = Maps.newHashMap();
            map.put("relId", RelId);
            map.put("relType", RelType);
            map.put("year", DateUtils.getCurrentYear());
            for (String str : traSubNameList) {
                map.put("traSubName", str);
//                switch (tranType) {
//                    case "01":
//                        map.put("tranType", "01");
//                        traInvestmentService.grandTradeZero(map);
//                        break;
//                    case "02":
//                        map.put("tranType", "02");
//                        traFundsService.grandTradeZero(map);
//                        break;
//                    case "funds":
//                        map.put("tranType", "02");
//                        traFundsService.grandTradeZero(map);
//                        break;
//                    case "03":
//                        map.put("tranType", "03");
//                        traBusinessService.grandTradeZero(map);
//                        break;
//                    case "04":
//                        map.put("tranType", "04");
//                        traInterestService.grandTradeZero(map);
//                        break;
//                    case "05":
//                        map.put("tranType", "05");
//                        traServiceService.grandTradeZero(map);
//                        break;
//                    case "06":
//                        map.put("tranType", "06");
//                        traOtherService.grandTradeZero(map);
//                        break;
//                    default:
//                        break;
//                }
                map.put("tranType", "01");
                traInvestmentService.grandTradeZero(map);
                map.put("tranType", "02");
                traFundsService.grandTradeZero(map);
                map.put("tranType", "03");
                traBusinessService.grandTradeZero(map);
                map.put("tranType", "04");
                traInterestService.grandTradeZero(map);
                map.put("tranType", "05");
                traServiceService.grandTradeZero(map);
                map.put("tranType", "06");
                traOtherService.grandTradeZero(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<TraCounterparty> sumAfterCleanAmount(Map<String, Object> param) {
        try {
            List<TraCounterparty> traCounterpartyList = traCounterpartyMapper.sumAfterCleanAmount(param);
            return traCounterpartyList;
        }catch(ServiceException se){
            se.printStackTrace();
        }
        return null;
    }
}
