package com.yzj.jep.app.bus.service.impl;

import com.google.common.collect.Maps;
import com.yzj.jep.app.bus.model.TraBalanceDeduction;
import com.yzj.jep.app.bus.service.RuleLibRestCallback;
import com.yzj.jep.app.bus.service.TraBalanceDeductionService;
import com.yzj.jep.app.util.TraUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service(value = "JudgeIsImportTradeServiceImpl")
public class JudgeIsImportTradeServiceImpl implements RuleLibRestCallback {
    private static final Logger LOGGER = LoggerFactory.getLogger(JudgeIsImportTradeServiceImpl.class);

    @Autowired
    private TraBalanceDeductionService traBalanceDeductionService;

    @Override
    public Map checkRule(Map parameters) {
        Map<String, String> result = Maps.newHashMap();
        try {
            String relatedName = (String) parameters.get("relatedName");
            String identifyType = (String) parameters.get("identifyType");
            String identNumber = (String) parameters.get("identNumber");
            String tranType = (String) parameters.get("tranType");
            String isCredit = (String) parameters.get("isCredit");
            BigDecimal tradeAmount = parameters.get("tradeAmount") == null ? BigDecimal.ZERO : new BigDecimal((String) parameters.get("tradeAmount"));
            BigDecimal securitDeposit = parameters.get("securitDeposit") == null ? BigDecimal.ZERO : new BigDecimal((String) parameters.get("securitDeposit"));
            BigDecimal nationalDebt = parameters.get("nationalDebt") == null ? BigDecimal.ZERO : new BigDecimal((String) parameters.get("nationalDebt"));
            BigDecimal bankDeposit = parameters.get("bankDeposit") == null ? BigDecimal.ZERO : new BigDecimal((String) parameters.get("bankDeposit"));
            Map<String, Object> relInfo = TraUtil.getRelInfo(null, identNumber, identifyType, relatedName);
            boolean isRel = (boolean) relInfo.get("success");
            if (isRel) {
                String relType = (String) relInfo.get("relType");
                Integer relId = (Integer) relInfo.get("relId");
//                if ("0".equals(tranType)) {
//                    result.put("code", "00");
//                    result.put("msg", "无担保贷款");
//                    return result;
//                }
                if ("1".equals(isCredit)) {
                    TraBalanceDeduction traBalanceDeduction = traBalanceDeductionService.get(1);
                    if ("1".equals(traBalanceDeduction.getSecuritDeposit())) {
                        tradeAmount = tradeAmount.subtract(securitDeposit);
                    }
                    if ("1".equals(traBalanceDeduction.getNationalDebt())) {
                        tradeAmount = tradeAmount.subtract(nationalDebt);
                    }
                    if ("1".equals(traBalanceDeduction.getBankDeposit())) {
                        tradeAmount = tradeAmount.subtract(bankDeposit);
                    }
                }
                Map<String, Object> stringObjectMap = TraUtil.JudgeAllCreditAmount(tradeAmount);
                if (!(boolean) stringObjectMap.get("success")) {
                    result.put("code", "00");
                    result.put("msg", (String) stringObjectMap.get("msg"));
                    return result;
                }
                Map<String, Object> stringObjectMap1 = Maps.newHashMap();
                if ("PSN".equals(relType)) {
                    stringObjectMap1 = TraUtil.JudgePsnCreditBalance(relId, tradeAmount);
                } else {
                    stringObjectMap1 = TraUtil.JudgeOrgCreditBalance(relId, tradeAmount);
                }
                if (!(boolean) stringObjectMap1.get("success")) {
                    result.put("code", "00");
                    result.put("msg", (String) stringObjectMap1.get("msg"));
                    return result;
                }
                boolean importantTrade = TraUtil.isImportantTrade(tradeAmount, relId, relType, null);
                if (importantTrade) {
                    result.put("code", "00");
                    result.put("msg", "重大关联交易");
                    return result;
                } else {
                    result.put("code", "00");
                    result.put("msg", "一般关联交易");
                    return result;
                }

            } else {
                Map<String, Object> stringObjectMap = TraUtil.JudgeSuspectedRel(identNumber, identifyType, relatedName);
                boolean isSuspect = (boolean) stringObjectMap.get("success");
                if (isSuspect) {
                    result.put("code", "00");
                    result.put("msg", (String) stringObjectMap.get("msg"));
                    return result;
                } else {
                    result.put("code", "00");
                    result.put("msg", "不是关联方");
                    return result;
                }
            }
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            result.put("code", "00");
            result.put("msg", "不是关联方");
        }
        return result;
    }
}
