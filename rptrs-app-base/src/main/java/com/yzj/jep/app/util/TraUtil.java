package com.yzj.jep.app.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.orm.mybatis.util.StringUtil;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import com.yzj.jep.sys.service.UnitService;
import com.yzj.jep.web.code.WebCode;
import net.sf.jsqlparser.statement.select.Limit;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.apache.zookeeper.Op;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TraUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(TraUtil.class);

    private static RelOrgService relOrgService = SpringContextHolder.getBean(RelOrgService.class);
    private static RelPsnService relPsnService = SpringContextHolder.getBean(RelPsnService.class);
    private static RelService relService = SpringContextHolder.getBean(RelService.class);
    private static TraCreditService traCreditService = SpringContextHolder.getBean(TraCreditService.class);
    private static TraUsecreditService traUsecreditService = SpringContextHolder.getBean(TraUsecreditService.class);
    private static TraAssetService traAssetService = SpringContextHolder.getBean(TraAssetService.class);
    private static TraServiceService traServiceService = SpringContextHolder.getBean(TraServiceService.class);
    private static TraOtherService traOtherService = SpringContextHolder.getBean(TraOtherService.class);
    private static UnitService unitService = SpringContextHolder.getBean(UnitService.class);
    private static RelPsnSuspectService relPsnSuspectService = SpringContextHolder.getBean(RelPsnSuspectService.class);
    private static RelOrgSuspectService relOrgSuspectService = SpringContextHolder.getBean(RelOrgSuspectService.class);
    private static TraBalanceDeductionService traBalanceDeductionService = SpringContextHolder.getBean(TraBalanceDeductionService.class);
    private static RootOrgInfoService rootOrgInfoService = SpringContextHolder.getBean(RootOrgInfoService.class);
    private static ProductService productService = SpringContextHolder.getBean(ProductService.class);
    private static LimitTransService limitTransService = SpringContextHolder.getBean(LimitTransService.class);
    private static DictItemService dictItemService = SpringContextHolder.getBean(DictItemService.class);

    /**
     * 获取交易余额合并计算
     *
     * @param pid
     * @param type
     * @return
     */
    public static BigDecimal getMergeTraBalance(Integer pid, String type) {
        LOGGER.info("合并计算交易余额...pid[{}],type[{}]", pid, type);

        BigDecimal balance = BigDecimal.ZERO;
        if ("PSN".equals(type)) {
            List<Integer> relativesPid = relService.getAllCloseRelatives(pid);
            if (relativesPid.size() > 0) {
                for (Integer relativePid : relativesPid) {
                    balance = balance.add(getTradeBalance(relativePid, "PSN"));
                }
            }
            balance = balance.add(getTradeBalance(pid, type));
        } else {
            RelOrg org = relOrgService.get(pid);
            String memGroup = org.getMemGroup();
            List<RelOrg> relOrgs = null;
            if (StringUtils.isNotBlank(memGroup)) {
                RelOrg relOrg = new RelOrg();
                relOrg.setDelflag("1");
                relOrg.setMemGroup(memGroup);
                relOrgs = relOrgService.select(relOrg);
                for (RelOrg relOrg1 : relOrgs) {
                    Integer pid1 = relOrg1.getPid();
                    BigDecimal balance1 = getTradeBalance(pid1, "ORG");
                    balance = balance.add(balance1);
                }
            } else {
                balance = balance.add(getTradeBalance(pid, type));
            }
            if ("1".equals(org.getIsMainGd())) {
                balance = getMainGdBalance(balance, pid, type, relOrgs);
            }

        }
        return balance;
    }

    /**
     * 获取主要控股股东交易余额
     */
    public static BigDecimal getMainGdBalance(BigDecimal balance, Integer pid, String type, List<RelOrg> relOrgs) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("pid", pid);
        map.put("type", type);
        List<Map<String, Object>> controllingShareholder = relService.getControllingShareholder(map);
        if (controllingShareholder.size() == 0) {
            return balance;
        }
        for (Map<String, Object> map1 : controllingShareholder) {
            Integer pid1 = Integer.valueOf((String) map1.get("pid"));
            if (relOrgs != null && relOrgs.stream().map(relOrg -> relOrg.getPid()).anyMatch(orgPid -> orgPid.equals(pid1))) {
                balance = getMainGdBalance(balance, pid1, type, relOrgs);
            } else {
                String type1 = (String) map1.get("type");
                BigDecimal balance1 = getTradeBalance(pid1, type1);
                balance = balance.add(balance1);
                balance = getMainGdBalance(balance, pid1, type1, relOrgs);
            }
        }
        return balance;
    }

    /**
     * 获取关联方交易余额
     *
     * @param pid
     * @param type
     * @return
     */
    public static BigDecimal getTradeBalance(Integer pid, String type) {
        BigDecimal balance = BigDecimal.ZERO;
        TraUsecredit traUsecredit = new TraUsecredit();
        traUsecredit.setRelId(pid);
        traUsecredit.setRelType(type);
        traUsecredit.setIsEffective("1");
        BigDecimal tradeBalance = traUsecreditService.getTradeBalance(traUsecredit);
        TraAsset traAsset = new TraAsset();
        traAsset.setRelId(pid);
        traAsset.setRelType(type);
        traAsset.setIsEffective("1");
        BigDecimal balance1 = traAssetService.getBalance(traAsset);
        TraOther traOther = new TraOther();
        traOther.setRelId(pid);
        traOther.setRelType(type);
        traOther.setIsEffective("1");
        BigDecimal balance2 = traOtherService.getBalance(traOther);
        TraService traService = new TraService();
        traService.setRelId(pid);
        traService.setRelType(type);
        traService.setIsEffective("1");
        BigDecimal balance3 = traServiceService.getBalance(traService);
        tradeBalance = tradeBalance == null ? BigDecimal.ZERO : tradeBalance;
        balance1 = balance1 == null ? BigDecimal.ZERO : balance1;
        balance2 = balance2 == null ? BigDecimal.ZERO : balance2;
        balance3 = balance3 == null ? BigDecimal.ZERO : balance3;
        BigDecimal relBalance = balance.add(tradeBalance).add(balance1).add(balance2).add(balance3);
        return relBalance;
    }

    /**
     * 关联自然人授信余额合并计算
     */
    public static Map<String, Object> JudgePsnCreditBalance(Integer relId, BigDecimal tradeAmount) {
        Map<String, Object> tips = Maps.newHashMap();
        BigDecimal psnCreditBalance = tradeAmount;
        BigDecimal netCapital = getNetCapital();
        List<Integer> relativesPid = relService.getAllCloseRelatives(relId);
        for (Integer relativePid : relativesPid) {
            BigDecimal tradeBalance = getCurrRelCreditBalance(relativePid, "PSN");
            psnCreditBalance = psnCreditBalance.add(tradeBalance);
        }
        BigDecimal currRelCreditBalance = getCurrRelCreditBalance(relId, "PSN");
        psnCreditBalance = psnCreditBalance.add(currRelCreditBalance);
        tips.put("psnCreditBalance", psnCreditBalance);
        BigDecimal divide1 = netCapital.multiply(BigDecimal.valueOf(10L)).divide(BigDecimal.valueOf(100L));
        if (psnCreditBalance.compareTo(divide1) >= 0) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "该关联方的授信余额已达上限，禁止交易");
            return tips;
        }
        tips.put(WebCode.SUCCESS, true);
        return tips;
    }

    /**
     * 关联法人授信余额合并计算
     */
    public static Map<String, Object> JudgeOrgCreditBalance(Integer relId, BigDecimal tradeAmount) {
        Map<String, Object> tips = Maps.newHashMap();
        BigDecimal orgCreditBalance = tradeAmount;
        BigDecimal netCapital = getNetCapital();
        RelOrg org = relOrgService.get(relId);
        String memGroup = org.getMemGroup();
        if (StringUtils.isNotBlank(memGroup)) {
            RelOrg relOrg = new RelOrg();
            relOrg.setDelflag("1");
            relOrg.setMemGroup(memGroup);
            List<RelOrg> orgs = relOrgService.select(relOrg);
            for (RelOrg relOrg1 : orgs) {
                Integer orgPid = relOrg1.getPid();
                BigDecimal tradeBalance = getCurrRelCreditBalance(orgPid, "ORG");
                orgCreditBalance = orgCreditBalance.add(tradeBalance);
            }
        } else {
            BigDecimal currRelCreditBalance = getCurrRelCreditBalance(relId, "ORG");
            orgCreditBalance = orgCreditBalance.add(currRelCreditBalance);
        }
        tips.put("orgCreditBalance", orgCreditBalance);
        BigDecimal divide1 = netCapital.multiply(BigDecimal.valueOf(15L)).divide(BigDecimal.valueOf(100L));
        if (orgCreditBalance.compareTo(divide1) >= 0) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "该关联方的授信余额已达上限，禁止交易");
            return tips;
        }
        tips.put(WebCode.SUCCESS, true);
        return tips;
    }

    /**
     * 判断所有关联方授信余额
     */
    public static Map<String, Object> JudgeAllCreditAmount(BigDecimal tradeAmount) {
        Map<String, Object> tips = Maps.newHashMap();
        BigDecimal totalCreditBalance = tradeAmount;
        BigDecimal netCapital = getNetCapital();
        List<Filter> filters = Lists.newArrayList();
        filters.add(Filter.nnl("aligCode"));
        filters.add(Filter.eq("delflag", 1));
        TraCredit traCredit = new TraCredit();
        List<RelPsn> relPsns = relPsnService.select(filters);
        for (RelPsn relPsn : relPsns) {
            Integer pid = relPsn.getPid();
            BigDecimal tradeBalance = getCurrRelCreditBalance(pid, "PSN");
            totalCreditBalance = totalCreditBalance.add(tradeBalance);
        }
        List<RelOrg> relOrgs = relOrgService.select(filters);
        for (RelOrg relOrg : relOrgs) {
            Integer pid = relOrg.getPid();
            BigDecimal tradeBalance = getCurrRelCreditBalance(pid, "ORG");
            totalCreditBalance = totalCreditBalance.add(tradeBalance);
        }
        tips.put("totalCreditBalance", totalCreditBalance);
        BigDecimal divide = netCapital.multiply(BigDecimal.valueOf(50L)).divide(BigDecimal.valueOf(100L));
        if (totalCreditBalance.compareTo(divide) >= 0) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "所有关联方的授信余额已达上限，禁止交易");
            return tips;
        }
        tips.put(WebCode.SUCCESS, true);
        return tips;
    }

    /**
     * 根据条件查询关联方信息
     */
    public static Map<String, Object> getRelInfo(String unitCode, String identNumber, String identifyType, String relatedName) {
        LOGGER.info("根据条件查询关联方信息...unitCode[{}],identNumber[{}],identifyType[{}],relatedName[{}]", unitCode, identNumber, identifyType, relatedName);
        Map<String, Object> tips = Maps.newHashMap();
        List<Filter> relPsnFilters = Lists.newArrayList();
        List<Filter> relOrgFilters = Lists.newArrayList();
        if (StringUtils.isNotEmpty(unitCode)) {
            List<String> codes = unitService.selectUnitAllOrDeptAllCodes(unitCode);
            relPsnFilters.add(Filter.in("mgerOrg", codes));
            relOrgFilters.add(Filter.in("mgerOrg", codes));
        }
        if (StringUtils.isNotEmpty(relatedName)) {
            relPsnFilters.add(Filter.eq("name", relatedName));
            relOrgFilters.add(Filter.eq("orgName", relatedName));
        }
        if (StringUtils.isNotEmpty(identifyType)) {
            relPsnFilters.add(Filter.eq("identifyType", identifyType));
            relOrgFilters.add(Filter.eq("identifyType", identifyType));
        }
        if (StringUtils.isNotEmpty(identNumber)) {
            relPsnFilters.add(Filter.eq("identifyNumber", identNumber));
            relOrgFilters.add(Filter.eq("uniscCode", identNumber));
        }
        relPsnFilters.add(Filter.nnl("aligCode"));
        relOrgFilters.add(Filter.nnl("aligCode"));
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
            RootOrgInfo orgInfo = rootOrgInfoService.get(1);
            String totalShareAmount = orgInfo.getTotalShareAmount();
            LOGGER.info("totalShareAmount:[{}]", totalShareAmount);
            if (relPsns.size() == 1) {
                RelPsn psn = relPsns.get(0);
                if (StringUtils.isNotEmpty(psn.getAligCode())) {
                    LOGGER.info("自然人关联方");
                    String holeShareAmount = psn.getHoleShareAmount();
                    if (holeShareAmount != null) {
                        Float shareholdingProportion = Float.parseFloat(holeShareAmount) / Float.parseFloat(totalShareAmount);
                        tips.put("shareholdingProportion", shareholdingProportion);
                    }
                    tips.put("voteProportion", psn.getVoteProportion());
                    tips.put("relType", "PSN");
                    tips.put("relId", psn.getPid());
                    tips.put("name", psn.getName());
                    tips.put("identifyType", psn.getIdentifyType());
                    tips.put("identifyNumber", psn.getIdentifyNumber());
                    tips.put(WebCode.SUCCESS, true);
                    tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
                    return tips;
                } else {
                    tips.put(WebCode.SUCCESS, false);
                    tips.put(WebCode.MSG, "无此关联方");
                    return tips;
                }
            }
            if (relOrgs.size() == 1) {
                RelOrg org = relOrgs.get(0);
                if (StringUtils.isNotEmpty(org.getAligCode())) {
                    LOGGER.info("机构关联方");
                    String holeShareAmount = org.getHoleShareAmount();
                    if (holeShareAmount != null) {
                        Float shareholdingProportion = Float.parseFloat(holeShareAmount) / Float.parseFloat(totalShareAmount);
                        tips.put("shareholdingProportion", shareholdingProportion);
                    }
                    tips.put("voteProportion", org.getVoteProportion());
                    tips.put("relType", "ORG");
                    tips.put("relId", org.getPid());
                    tips.put("name", org.getOrgName());
                    tips.put("identifyType", org.getIdentifyType());
                    tips.put("identifyNumber", org.getUniscCode());
                    tips.put("isInsider", org.getIsInsider());
                    tips.put("inInsiderQuota", org.getPresetLimit());
                    tips.put(WebCode.SUCCESS, true);
                    tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
                    return tips;
                } else {
                    tips.put(WebCode.SUCCESS, false);
                    tips.put(WebCode.MSG, "无此关联方");
                    return tips;
                }
            }
        }
        return tips;
    }

    /**
     * 判断疑似关联方
     */
    public static Map<String, Object> JudgeSuspectedRel(String identNumber, String identifyType, String relatedName) {
        Map<String, Object> tips = Maps.newHashMap();
        RelPsnSuspect relPsnSuspect = new RelPsnSuspect();
        relPsnSuspect.setName(relatedName);
        relPsnSuspect.setIdentifyNumber(identNumber);
        relPsnSuspect.setIdentifyType(identifyType);
        relPsnSuspect.setDelflag("1");
        List<RelPsnSuspect> relPsnSuspectList = relPsnSuspectService.select(relPsnSuspect);
        if (relPsnSuspectList.isEmpty()) {
            RelOrgSuspect relOrgSuspect = new RelOrgSuspect();
            relOrgSuspect.setIdentifyType(identifyType);
            relOrgSuspect.setOrgName(relatedName);
            relOrgSuspect.setUniscCode(identNumber);
            relOrgSuspect.setDelflag("1");
            List<RelOrgSuspect> relOrgSuspectList = relOrgSuspectService.select(relOrgSuspect);
            if (relOrgSuspectList.isEmpty()) {
                tips.put(WebCode.SUCCESS, false);
                return tips;
            }
        }
        tips.put(WebCode.SUCCESS, true);
        tips.put(WebCode.MSG, "疑似关联方");
        return tips;
    }

    /**
     * 判断是否为重大关联交易
     */
    public static boolean isImportantTrade(BigDecimal tradeAmount, Integer relId, String relType, BigDecimal diff) {
        LOGGER.info("判断是否为重大关联交易...tradeAmount[{}],relId[{}],relType[{}]", tradeAmount, relId, relType);
        BigDecimal netCapital = getNetCapital();
        boolean a = tradeAmount.compareTo(netCapital.divide(BigDecimal.valueOf(100L))) < 0;
        LOGGER.info("单笔是否超限[{}]", a);
        if (!a) {
            return true;
        }
        BigDecimal balance = getMergeTraBalance(relId, relType);
        boolean b;
        BigDecimal multiply = netCapital.divide(BigDecimal.valueOf(100L)).multiply(BigDecimal.valueOf(5L));
        if (diff != null) {
            b = (balance.add(diff)).compareTo(multiply) < 0;
        } else {
            b = (balance.add(tradeAmount)).compareTo(multiply) < 0;
        }
        LOGGER.info("合并计算交易余额是否超限[{}],余额[{}]", b, balance);
        if (a && b) {
            return false;
        }
        return true;
    }

    /**
     * 获取资本净额
     */
    public static BigDecimal getNetCapital() {
        RootOrgInfo orgInfo = rootOrgInfoService.get(1);

        return orgInfo.getNetCaptial();
    }

    /**
     * 授信余额获取扣除项金额
     */
    public static BigDecimal getDeductionAmount(Integer pid, String type) {
        BigDecimal deductionAmount = BigDecimal.ZERO;
        TraCredit traCredit = new TraCredit();
        traCredit.setRelId(pid);
        traCredit.setRelType(type);
        TraBalanceDeduction traBalanceDeduction = traBalanceDeductionService.get(1);
        if ("1".equals(traBalanceDeduction.getBankDeposit())) {
            BigDecimal bankDeposit = traCreditService.getBankDeposit(traCredit);
            bankDeposit = bankDeposit == null ? BigDecimal.ZERO : bankDeposit;
            deductionAmount = deductionAmount.add(bankDeposit);
        }
        if ("1".equals(traBalanceDeduction.getNationalDebt())) {
            BigDecimal nationalDebt = traCreditService.getNationalDebt(traCredit);
            nationalDebt = nationalDebt == null ? BigDecimal.ZERO : nationalDebt;
            deductionAmount = deductionAmount.add(nationalDebt);
        }
        if ("1".equals(traBalanceDeduction.getSecuritDeposit())) {
            BigDecimal securitDeposit = traCreditService.getSecuritDeposit(traCredit);
            securitDeposit = securitDeposit == null ? BigDecimal.ZERO : securitDeposit;
            deductionAmount = deductionAmount.add(securitDeposit);
        }
        return deductionAmount;
    }

    /**
     * 获取当前关联方授信余额
     */
    public static BigDecimal getCurrRelCreditBalance(Integer pid, String type) {
        BigDecimal creditBalance = BigDecimal.ZERO;
        TraCredit traCredit = new TraCredit();
        traCredit.setRelId(pid);
        traCredit.setRelType(type);
        BigDecimal tradeBalance = traCreditService.getCreditBalance(traCredit);
        BigDecimal deductionAmount = getDeductionAmount(pid, type);
        tradeBalance = tradeBalance == null ? BigDecimal.ZERO : tradeBalance;
        tradeBalance = tradeBalance.subtract(deductionAmount);
        creditBalance = creditBalance.add(tradeBalance);
        return creditBalance;
    }

    /**
     * 获取产品下交易余额
     */
    public static BigDecimal getProTraBalance(String productName) {
        BigDecimal balance = BigDecimal.ZERO;
        Product product = new Product();
        product.setProName(productName);
        Product product1 = productService.get(product);
        Integer proId = product1.getPid();
        TraUsecredit traUsecredit = new TraUsecredit();
        traUsecredit.setProductCode(proId.toString());
        traUsecredit.setIsEffective("1");
        BigDecimal tradeBalance = traUsecreditService.getTradeBalance(traUsecredit);
        TraAsset traAsset = new TraAsset();
        traAsset.setProductCode(proId.toString());
        traAsset.setIsEffective("1");
        BigDecimal balance1 = traAssetService.getBalance(traAsset);
        TraOther traOther = new TraOther();
        traOther.setProductCode(proId.toString());
        traOther.setIsEffective("1");
        BigDecimal balance2 = traOtherService.getBalance(traOther);

        TraService traService = new TraService();
        traService.setProductCode(proId.toString());
        traService.setIsEffective("1");
        BigDecimal balance3 = traServiceService.getBalance(traService);
        tradeBalance = tradeBalance == null ? BigDecimal.ZERO : tradeBalance;
        balance1 = balance1 == null ? BigDecimal.ZERO : balance1;
        balance2 = balance2 == null ? BigDecimal.ZERO : balance2;
        balance3 = balance3 == null ? BigDecimal.ZERO : balance3;
        BigDecimal relBalance = balance.add(tradeBalance).add(balance1).add(balance2).add(balance3);
        return relBalance;
    }

    /**
     * 获取产品预计额度
     */
    public static BigDecimal getProductQuota(String name) {
        LimitTrans limitTrans = new LimitTrans();
        limitTrans.setProName(name);
        LimitTrans trans = limitTransService.get(limitTrans);
        BigDecimal preLimit = trans == null ? BigDecimal.ZERO : trans.getPreLimit() == null ? BigDecimal.ZERO : new BigDecimal(trans.getPreLimit());
        return preLimit;
    }

    /**
     * 内部人交易金额
     */
    public static BigDecimal getInsiderAmount(Integer relId) {
        TraOther traOther = new TraOther();
        traOther.setRelId(relId);
        traOther.setIsInsider("1");
        BigDecimal balance = traOtherService.getBalance(traOther);
        TraCredit traCredit = new TraCredit();
        traCredit.setRelId(relId);
        traCredit.setIsInsider("1");
        BigDecimal balance1 = traCreditService.getCreditBalance(traCredit);
        balance = balance == null ? BigDecimal.ZERO : balance;
        balance1 = balance1 == null ? BigDecimal.ZERO : balance1;
        return balance.add(balance1);
    }

    //根据字典编码 查询字典
    public static List<DictItem> getDictItems(String simpleName) {
        try {
            List<Filter> filters = new Filter.Builder().eq("dictCode", simpleName).build();
            return dictItemService.select(filters);
        } catch (ServiceException se) {
            se.printStackTrace();
        }
        return new ArrayList<>();
    }

    //根据关联方证件类型字典项值，查询证件类型字典项名称
    public static String getIdentifyType(List<DictItem> dictItems, String identifyType) {
        try {

            for (DictItem dictItem : dictItems) {
                if (dictItem.getItemValue().equals(identifyType)) {
                    return dictItem.getItemLabel();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}

