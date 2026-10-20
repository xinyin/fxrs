package com.yzj.jep.app.bus.service.impl;

import com.google.common.collect.Maps;
import com.yzj.jep.app.bus.model.LimitTrans;
import com.yzj.jep.app.bus.model.TraBalanceDeduction;
import com.yzj.jep.app.bus.service.LimitTransService;
import com.yzj.jep.app.bus.service.TraBalanceDeductionService;
import com.yzj.jep.app.util.TraUtil;
import com.yzj.jep.web.code.WebCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service(value = "RuleInitDataServiceImpl")
public class RuleInitDataServiceImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(RuleInitDataServiceImpl.class);

    @Autowired
    private TraBalanceDeductionService traBalanceDeductionService;

    @Autowired
    private LimitTransService limitTransService;

    public Map<String, Object> initData(Map map) {
        String relatedName = (String) map.get("relatedName");
        String identifyType = (String) map.get("identifyType");
        String identNumber = (String) map.get("identNumber");
        BigDecimal tradeAmount = new BigDecimal((String) map.get("tradeAmount"));
        String productName = (String) map.get("productName");
        String isCredit = (String) map.get("isCredit");
        BigDecimal securitDeposit = map.get("securitDeposit") == null ? BigDecimal.ZERO : new BigDecimal((String) map.get("securitDeposit"));
        BigDecimal nationalDebt = map.get("nationalDebt") == null ? BigDecimal.ZERO : new BigDecimal((String) map.get("nationalDebt"));
        BigDecimal bankDeposit = map.get("bankDeposit") == null ? BigDecimal.ZERO : new BigDecimal((String) map.get("bankDeposit"));
        //关联方id,类型
        Map<String, Object> relInfo = TraUtil.getRelInfo(null, identNumber, identifyType, relatedName);
        if (!(boolean) relInfo.get("success")) {
            return null;
        }
        Integer relId = (Integer) relInfo.get("relId");
        String type = (String) relInfo.get("relType");
        String isInsider = (String) relInfo.get("isInsider");
        BigDecimal inInsiderQuota = BigDecimal.ZERO;
        BigDecimal inInsiderAmount = BigDecimal.ZERO;
        if ("1".equals(isInsider)) {
            if (relInfo.get("inInsiderQuota") != null) {
                inInsiderQuota = new BigDecimal((String) relInfo.get("inInsiderQuota"));
                inInsiderAmount = TraUtil.getInsiderAmount(relId);
            }
        }
        //合并交易余额
        BigDecimal mergeTraBalance = TraUtil.getMergeTraBalance(relId, type);
        LOGGER.info("合并交易余额[{}]", mergeTraBalance);
        //产品预计额度
        BigDecimal productQuota = TraUtil.getProductQuota(productName);
        LOGGER.info("产品预计额度[{}]", productQuota);
        //产品下交易余额
        BigDecimal productTraBalance = BigDecimal.ZERO;
        if (productQuota.compareTo(BigDecimal.ZERO) > 0) {
            productTraBalance = TraUtil.getProTraBalance(productName);
        }

        //资本净额
        BigDecimal netCapital = TraUtil.getNetCapital();
        LOGGER.info("资本净额[{}]", netCapital);
        BigDecimal creditDeduction = BigDecimal.ZERO;
        BigDecimal psnCreditBalance = BigDecimal.ZERO;
        BigDecimal orgCreditBalance = BigDecimal.ZERO;
        BigDecimal totalCreditBalance = BigDecimal.ZERO;
        if ("1".equals(isCredit)) {
            //扣除项后的授信额度
            creditDeduction = getBalanceDeduction(tradeAmount, securitDeposit, nationalDebt, bankDeposit);
            if ("PSN".equals(type)) {
                //关联自然人授信余额
                Map<String, Object> stringObjectMap = TraUtil.JudgePsnCreditBalance(relId, creditDeduction);
                psnCreditBalance = (BigDecimal) stringObjectMap.get("psnCreditBalance");
                LOGGER.info("关联自然人授信余额[{}]", psnCreditBalance);
            }
            if ("ORG".equals(type)) {
                //关联法人授信余额
                Map<String, Object> stringObjectMap1 = TraUtil.JudgeOrgCreditBalance(relId, creditDeduction);
                orgCreditBalance = (BigDecimal) stringObjectMap1.get("orgCreditBalance");
                LOGGER.info("关联法人授信余额[{}]", orgCreditBalance);
            }
            //全部关联方授信余额
            Map<String, Object> stringObjectMap2 = TraUtil.JudgeAllCreditAmount(creditDeduction);
            totalCreditBalance = (BigDecimal) stringObjectMap2.get("totalCreditBalance");
            LOGGER.info("全部关联方授信余额[{}]", totalCreditBalance);
        }
        Map<String, Object> tips = Maps.newHashMap();

        BigDecimal onePersentNetCaptial = netCapital.multiply(new BigDecimal(1L).divide(new BigDecimal(100L)));
        BigDecimal fivePersentNetCaptial = netCapital.multiply(new BigDecimal(5L).divide(new BigDecimal(100L)));
        BigDecimal tenPersentNetCaptial = netCapital.multiply(new BigDecimal(10L).divide(new BigDecimal(100L)));
        BigDecimal fifteenPersentNetCaptial = netCapital.multiply(new BigDecimal(15L).divide(new BigDecimal(100L)));
        BigDecimal fiftyPersentNetCaptial = netCapital.multiply(new BigDecimal(15L).divide(new BigDecimal(100L)));
        //单笔交易金额
        tips.put("singleTraAmount", tradeAmount);
        LOGGER.info("单笔交易金额[{}]", tradeAmount);
        //交易余额
        tips.put("traBalnce", mergeTraBalance.add(tradeAmount));
        //资本净额10%
        tips.put("tenPersentNetCaptial", tenPersentNetCaptial);
        //资本净额15%
        tips.put("fifteenPersentNetCaptial", fifteenPersentNetCaptial);
        //资本净额50%
        tips.put("fiftyPersentNetCaptial", fiftyPersentNetCaptial);
        //资本净额5%
        tips.put("fivePersentNetCaptial", fivePersentNetCaptial);
        //资本金额1%
        tips.put("onePersentNetCaptial", onePersentNetCaptial);

        tips.put("psnCreditBalance", psnCreditBalance);
        tips.put("orgCreditBalance", orgCreditBalance);
        tips.put("totalCreditBalance", totalCreditBalance);
        tips.put("productQuota", productQuota);
        tips.put("productTraBalance", productTraBalance);

        //内部交易额度
        tips.put("inInsiderQuota", inInsiderQuota);
        LOGGER.info("内部交易额度", inInsiderQuota);
        //内部交易金额
        tips.put("inInsiderAmount", inInsiderAmount);
        LOGGER.info("内部交易额度", inInsiderAmount);
        return tips;
    }

    /**
     * 扣除项计算
     */
    public BigDecimal getBalanceDeduction(BigDecimal traAmount, BigDecimal securitDeposit, BigDecimal nationalDebt, BigDecimal bankDeposit) {
        TraBalanceDeduction traBalanceDeduction = traBalanceDeductionService.get(1);
        if ("1".equals(traBalanceDeduction.getSecuritDeposit())) {
            traAmount = traAmount.subtract(securitDeposit);
        }
        if ("1".equals(traBalanceDeduction.getNationalDebt())) {
            traAmount = traAmount.subtract(nationalDebt);
        }
        if ("1".equals(traBalanceDeduction.getBankDeposit())) {
            traAmount = traAmount.subtract(bankDeposit);
        }
        return traAmount;
    }

    /**
     * 查询产品是否存在
     */
    public Map<String, Object> checkPro(String productName) {
        Map<String, Object> tips = Maps.newHashMap();
        LimitTrans limitTrans = new LimitTrans();
        limitTrans.setProName(productName);
        LimitTrans trans = limitTransService.get(limitTrans);
        if (trans == null) {
            tips.put(WebCode.SUCCESS, false);
        } else {
            tips.put(WebCode.SUCCESS, true);
        }
        return tips;
    }
}

