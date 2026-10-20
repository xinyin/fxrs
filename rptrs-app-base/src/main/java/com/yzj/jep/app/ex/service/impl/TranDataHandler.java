package com.yzj.jep.app.ex.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.client.ProcessDataHandler;
import com.yzj.jep.workflow.client.annotation.DataHandler;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.management.StringValueExp;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

//业务逻辑

@DataHandler("trancredit")
public class TranDataHandler implements ProcessDataHandler {
    TraServiceService traServiceService;
    TraAssetService traAssetService;
    TraOtherService traOtherService;
    TraCreditService traCreditService;
    RelService relService;
    RelPsnService relPsnService;
    RelOrgService relOrgService;
    TraBalanceDeductionService traBalanceDeductionService;
    TraUsecreditService traUsecreditService;

    @Override
    public void handleData(String data) throws WorkflowException {

        traServiceService = SpringContextHolder.getBean(TraServiceService.class);
        traAssetService = SpringContextHolder.getBean(TraAssetService.class);
        traOtherService = SpringContextHolder.getBean(TraOtherService.class);
        traCreditService = SpringContextHolder.getBean(TraCreditService.class);
        relService = SpringContextHolder.getBean(RelService.class);
        relPsnService = SpringContextHolder.getBean(RelPsnService.class);
        relOrgService = SpringContextHolder.getBean(RelOrgService.class);
        traBalanceDeductionService = SpringContextHolder.getBean(TraBalanceDeductionService.class);
        traUsecreditService = SpringContextHolder.getBean(TraUsecreditService.class);
        JSONObject json = JSONObject.parseObject(data);
        String jsonString = json.getString("pid");
        Integer pid = Integer.parseInt(jsonString);
        String type = (String) json.get("type");


        if ("OTHER".equals(type)) {
            TraOther traOther = new TraOther();
            traOther.setPid(pid);
            traOther.setStatus("6");
            traOther.setIsEffective("1");
            traOther.setStatusComment("审批通过");
            traOtherService.updateSelective(traOther);
        }
        if ("SERVICE".equals(type)) {
            TraService traService = new TraService();
            traService.setStatus("6");
            traService.setPid(pid);
            traService.setIsEffective("1");
            traService.setStatusComment("审批通过");
            traServiceService.updateSelective(traService);
        }
        if ("ASSET".equals(type)) {
            TraAsset traAsset = new TraAsset();
            traAsset.setPid(pid);
            traAsset.setStatus("6");
            traAsset.setIsEffective("1");
            traAsset.setStatusComment("审批通过");
            traAssetService.updateSelective(traAsset);
        }
        if ("CREDIT".equals(type)) {
            String memo = json.getString("memo");
            creditProcess(data, memo);
        }
        if ("USECREDIT".equals(type)) {
            String memo = json.getString("memo");
            useCreditProcess(data, memo);

        }
    }

    private void creditProcess(String data, String memo) {
        TraCredit traCredit = JSON.parseObject(data, TraCredit.class);
        traCredit.setStatus("6");
        traCredit.setIsEffective("1");
        traCredit.setStatusComment(memo);
        traCreditService.updateSelective(traCredit);
    }

    private void useCreditProcess(String data, String memo) {
        TraUsecredit traUsecredit = JSON.parseObject(data, TraUsecredit.class);
        List<Filter> filters = Lists.newArrayList();
        String internalApplyNo = traUsecredit.getInternalApplyNo();
        filters.add(Filter.eq("internalApplyNo", internalApplyNo));
        filters.add(Filter.eq("delflag", "1"));
        TraCredit traCredit = traCreditService.get(filters);
        if ("1".equals(traUsecredit.getIsEffective())) {
            Long validityBalance = traCredit.getValidityBalance();
            BigDecimal balance = BigDecimal.valueOf(validityBalance);
            BigDecimal add = balance.add(traUsecredit.getThisTraDiff());
            traCredit.setValidityBalance(add.longValue());
        } else {
            Long validityBalance = traCredit.getValidityBalance();
            BigDecimal balance = validityBalance == null ? BigDecimal.ZERO : BigDecimal.valueOf(validityBalance);
            BigDecimal thisTraAmount = traUsecredit.getThisTraAmount();
            traCredit.setValidityBalance((balance.add(thisTraAmount)).longValue());
        }
        traCreditService.updateSelective(traCredit);
        traUsecredit.setIsEffective("1");
        traUsecredit.setStatus("6");
        traUsecredit.setStatusComment(memo);
        traUsecreditService.updateSelective(traUsecredit);
    }
}