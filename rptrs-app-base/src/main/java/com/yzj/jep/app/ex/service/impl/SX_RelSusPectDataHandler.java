package com.yzj.jep.app.ex.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.sys.service.StaffService;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.client.ProcessDataHandler;
import com.yzj.jep.workflow.client.annotation.DataHandler;

import java.math.BigDecimal;
import java.util.List;

//业务逻辑

@DataHandler("SX_YSGLFSPLC")
public class SX_RelSusPectDataHandler implements ProcessDataHandler {
    TraServiceService traServiceService;
    TraAssetService traAssetService;
    TraOtherService traOtherService;
    TraCreditService traCreditService;
    RelService relService;
    RelPsnService relPsnService;
    StaffService staffService;
    RelPsnSuspectService relPsnSuspectService;

    RelOrgSuspectService relOrgSuspectService;
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
        staffService = SpringContextHolder.getBean(StaffService.class);
        /*
         * 疑似关联方
         * */
        relPsnSuspectService = SpringContextHolder.getBean(RelPsnSuspectService.class);
        relOrgSuspectService = SpringContextHolder.getBean(RelOrgSuspectService.class);
        relOrgService = SpringContextHolder.getBean(RelOrgService.class);
        traBalanceDeductionService = SpringContextHolder.getBean(TraBalanceDeductionService.class);
        traUsecreditService = SpringContextHolder.getBean(TraUsecreditService.class);
        try {
            //解析JSON串
            JSONObject json = JSONObject.parseObject(data);
            String jsonString = json.getString("pid");
            Integer pid = Integer.parseInt(jsonString);
            String type = (String) json.get("type");
            //流程结束后疑似自然人状态改为已认定，并且更新到关联方自然人表中
            if ("PSN".equals(type)) {
                RelPsnSuspect psnSuspect = new RelPsnSuspect();
                psnSuspect.setPid(pid);
                psnSuspect.setStatus("6");
                psnSuspect.setIsAssoParty("1");
                relPsnSuspectService.updateSelective(psnSuspect);
                //疑似自然人基础数据
                RelPsn relPsn = JSON.parseObject(data, RelPsn.class);
                //查询是否有相同的关联方自然人。
                List<Filter> filter = new Filter.Builder()
                        .eq("name", relPsn.getName())
                        .eq("identifyType", relPsn.getIdentifyType())
                        .eq("identifyNumber", relPsn.getIdentifyNumber())
                        .build();
                RelPsn psn = relPsnService.get(filter);
                relPsn.setAmendName(staffService.get(json.getString("createByName")).getStaffName());
                relPsn.setStatus("6");
                //没有相同的自然人插入数据
                if (psn == null) {
                    relPsn.setPid(null);
                    relPsnService.insertSelective(relPsn);
                } else {
                    //有相同的自然人更新数据
                    relPsn.setPid(psn.getPid());
                    relPsnService.updateSelective(relPsn);
                }

            } else if ("ORG".equals(type)) {
                RelOrgSuspect orgSuspect = new RelOrgSuspect();
                orgSuspect.setPid(pid);
                orgSuspect.setApprovalStatus("6");
                orgSuspect.setIsAssoParty("1");
                relOrgSuspectService.updateSelective(orgSuspect);
                //疑似自然人基础数据
                RelOrg relOrg = JSON.parseObject(data, RelOrg.class);
                //查询是否有相同的关联方自然人。
                List<Filter> filter = new Filter.Builder()
                        .eq("orgName", relOrg.getOrgName())
                        .eq("identifyType", relOrg.getIdentifyType())
                        .eq("uniscCode", relOrg.getUniscCode())
                        .build();
                RelOrg org = relOrgService.get(filter);
                relOrg.setAmendName(staffService.get(json.getString("createByName")).getStaffName());
                relOrg.setApprovalStatus("6");
                //没有相同的自然人插入数据
                if (org == null) {
                    relOrg.setPid(null);
                    relOrgService.insertSelective(relOrg);
                } else {
                    //有相同的自然人更新数据
                    relOrg.setPid(org.getPid());
                    relOrgService.updateSelective(relOrg);
                }
            }

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
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (ServiceException e) {
            e.printStackTrace();
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