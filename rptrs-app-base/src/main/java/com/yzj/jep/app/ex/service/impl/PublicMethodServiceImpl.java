package com.yzj.jep.app.ex.service.impl;

import com.yzj.jep.app.bus.model.Rel;
import com.yzj.jep.app.bus.model.RelOrgSuspect;
import com.yzj.jep.app.bus.model.RelPro;
import com.yzj.jep.app.bus.model.RelPsnSuspect;
import com.yzj.jep.app.bus.service.RelOrgSuspectService;
import com.yzj.jep.app.bus.service.RelProService;
import com.yzj.jep.app.bus.service.RelPsnSuspectService;
import com.yzj.jep.app.bus.service.RelService;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.workflow.service.PublicMethodService;
import com.yzj.jep.app.ins.model.*;
import com.yzj.jep.app.ins.service.*;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import com.yzj.jep.web.entity.NameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yzj.jep.app.util.ConfidentialInfoUtil.idEncrypt;

@Service
public class PublicMethodServiceImpl implements PublicMethodService {

    @Autowired
    RelPsnSuspectService relPsnSuspectService;

    @Autowired
    RelOrgSuspectService relOrgSuspectService;

    @Autowired
    TraInvestmentService traInvestmentService;

    @Autowired
    TraFundsService traFundsService;

    @Autowired
    TraBusinessService traBusinessService;
    @Autowired
    private RelService relService;

    @Autowired
    TraInterestService traInterestService;

    @Autowired
    TraServiceService traServiceService;
    @Autowired
    DictItemService dictItemService;

    @Autowired
    TraOtherService traOtherService;

    @Autowired
    private RelProService relProService;

    private static final Map<String, String> subjectName = new HashMap<>();

    static {

        /*
         * 根据不同的主体来添加不同的后缀
         * */
        subjectName.put("JT", "JT_");
        subjectName.put("SX", "SX_");
        subjectName.put("CX", "CX_");
        subjectName.put("XB", "XB_");
        subjectName.put("ZG", "ZG_");
    }

    /**
     * 传入类型和主键返回以下数据：
     * processType 流程类型
     * businessType 业务类型（自然人，投资入股等）
     * glfName 关联方名称
     * transactionAmount 关联交易金额（疑似关联方不存在交易金额）
     *
     * @param pid
     * @param type
     * @return
     */
    @Override
    public Map<String, String> data(Integer pid, String type) {
        Map<String, String> tips = new HashMap<>();
        if ("PSN".equals(type)) {
            RelPsnSuspect relPsnSuspect = relPsnSuspectService.get(pid);
            if (null != relPsnSuspect) {
                tips.put("relType", "PSN");
                tips.put("businessType", "-");
                tips.put("relName", relPsnSuspect.getName());
                tips.put("relNumber", relPsnSuspect.getIdentifyNumber());
                tips.put("transType", "-");
                tips.put("transactionAmount", "-");
            }
        } else if ("PRO".equals(type)) {
            RelPro relPro = relProService.get(pid);
            if (null != relPro) {
                tips.put("relType", "PRO");
                tips.put("businessType", "-");
                tips.put("relName", relPro.getProName());
                tips.put("relNumber", relPro.getCareCreditCode());
                tips.put("transType", "-");
                tips.put("transactionAmount", "-");
            }
        } else if ("REL".equals(type)) {
            Rel rel = relService.get(pid);
            if (null != rel) {
                tips.put("relType", rel.getFromType());
                tips.put("businessType", "-");
                tips.put("relName", rel.getFromMemo());
                tips.put("relNumber", rel.getFromIdentifyNumber());
                tips.put("transType", "-");
                tips.put("transactionAmount", "-");
            }
        } else if ("investment".equals(type)) {
            TraInvestment investment = traInvestmentService.get(pid);
            if (null != investment) {
                String businessType = selectByValue(investment.getInvestmentType(), "INS-InvestmentType");
                tips.put("relType", investment.getRelType());
                tips.put("businessType", businessType);
                tips.put("relName", investment.getRelName());
                tips.put("relNumber", investment.getRelNumber());
                tips.put("transType", investment.getTranType());
                tips.put("transactionAmount", String.valueOf(investment.getContractAmount()));
            }
        } else if ("funds".equals(type)) {
            TraFunds funds = traFundsService.get(pid);
            if (null != funds) {
                String businessType = selectByValue(funds.getBusType(), "INS-FundsType");
                tips.put("relType", funds.getRelType());
                tips.put("businessType", businessType);
                tips.put("relNumber", idEncrypt(funds.getRelNumber()));
                tips.put("relName", funds.getRelName());
                tips.put("transType", funds.getTranType());
                tips.put("transactionAmount", String.valueOf(funds.getRelTradeAmount()));
            }
        } else if ("business".equals(type)) {
            TraBusiness business = traBusinessService.get(pid);
            if (null != business) {
                String businessType = selectByValue(business.getBusType(), "INT-InsBusinessType");
                tips.put("relType", business.getRelType());
                tips.put("businessType", businessType);
                tips.put("relName", business.getRelName());
                tips.put("relNumber", idEncrypt(business.getRelNumber()));
                tips.put("transType", business.getTranType());
                tips.put("transactionAmount", String.valueOf(business.getRelTradeAmount()));
            }
        } else if ("interest".equals(type)) {
            TraInterest interest = traInterestService.get(pid);
            if (null != interest) {
                String businessType = selectByValue(interest.getBusType(), "INT-BusinessTypes");
                tips.put("relType", interest.getRelType());
                tips.put("businessType", businessType);
                tips.put("relName", interest.getRelName());
                tips.put("relNumber", idEncrypt(interest.getRelNumber()));
                tips.put("transType", interest.getTranType());
                tips.put("transactionAmount", String.valueOf(interest.getRelTradeAmount()));
            }
        } else if ("service".equals(type)) {
            TraService service = traServiceService.get(pid);
            if (null != service) {
                String businessType = selectByValue(service.getBusType(), "INS-ServiceType");
                tips.put("relType", service.getRelType());
                tips.put("businessType", businessType);
                tips.put("relName", service.getRelName());
                tips.put("relNumber", idEncrypt(service.getRelNumber()));
                tips.put("transType", service.getTranType());
                tips.put("transactionAmount", String.valueOf(service.getRelTradeAmount()));
            }
        } else if ("other".equals(type)) {
            TraOther other = traOtherService.get(pid);
            if (null != other) {
                String businessType = selectByValue(other.getInvestmentType(), "INS-otherType");
                tips.put("relType", other.getRelType());
                tips.put("businessType", businessType);
                tips.put("relName", other.getRelName());
                tips.put("relNumber", other.getRelNumber());
                tips.put("transType", other.getTranType());
                tips.put("transactionAmount", String.valueOf(other.getContractAmount()));
            }
        }
        return tips;
    }

    @Override
    public Map<String, String> getData(String procInstId, String procCode) {
        Map<String, String> tips = new HashMap<>();
        UserProfile profile = UserProfileUtils.getUserProfile();
        List<Filter> filters = new Filter.Builder().eq("procInstId", procInstId).build();
        String code = profile.getTenantId();
        if (procCode.equals(" ")) {
            RelOrgSuspect relOrgSuspect = relOrgSuspectService.get(filters);
            RelPsnSuspect psnSuspect = relPsnSuspectService.get(filters);
            if (relOrgSuspect != null) {
                tips.put("relType", "ORG");
                tips.put("businessType", "-");
                tips.put("relName", relOrgSuspect.getOrgName());
                tips.put("relNumber", relOrgSuspect.getUniscCode());
                tips.put("transType", "-");
                tips.put("transactionAmount", "-");
            } else if (psnSuspect != null) {
                tips.put("relType", "PSN");
                tips.put("businessType", "-");
                tips.put("relName", psnSuspect.getName());
                tips.put("relNumber", psnSuspect.getIdentifyNumber());
                tips.put("transType", "-");
                tips.put("transactionAmount", "-");
            }
        } else if (procCode.equals("FXBDX_GLGXBGBA")) {
            Rel rel = relService.get(filters);
            if (rel != null) {
                tips.put("relType", rel.getFromType());
                tips.put("businessType", "-");
                tips.put("relName", rel.getFromMemo());
                tips.put("relNumber", rel.getFromIdentifyNumber());
                tips.put("transType", "-");
                tips.put("transactionAmount", "-");
            }
        } else if (procCode.equals("FXBDX_YBGLJY") ||procCode.equals("FXBDX_ZDTY") ||procCode.equals("FXBDX_ZDTY_BranchOffice") ||procCode.equals("FXBDX_YBGLJY_Company")) {
            TraInvestment traInvestment = traInvestmentService.get(filters);
            TraFunds traFunds = traFundsService.get(filters);
            TraBusiness traBusiness = traBusinessService.get(filters);
            TraInterest traInterest = traInterestService.get(filters);
            TraService traService = traServiceService.get(filters);
            TraOther traOther = traOtherService.get(filters);
            if (traInvestment != null) {
                String businessType = selectByValue(traInvestment.getInvestmentType(), "INS-InvestmentType");
                tips.put("relType", traInvestment.getRelType());
                tips.put("businessType", businessType);
                tips.put("relName", traInvestment.getRelName());
                tips.put("relNumber", idEncrypt(traInvestment.getRelNumber()));
                tips.put("transType", traInvestment.getTranType());
                tips.put("transactionAmount", String.valueOf(traInvestment.getContractAmount()));
            } else if (traFunds != null) {
                String businessType = selectByValue(traFunds.getBusType(), "INS-FundsType");
                tips.put("relType", traFunds.getRelType());
                tips.put("businessType", businessType);
                tips.put("relNumber", idEncrypt(traFunds.getRelNumber()));
                tips.put("relName", traFunds.getRelName());
                tips.put("transType", traFunds.getTranType());
                tips.put("transactionAmount", String.valueOf(traFunds.getRelTradeAmount()));
            } else if (null != traBusiness) {
                String businessType = selectByValue(traBusiness.getBusType(), "INT-InsBusinessType");
                tips.put("relType", traBusiness.getRelType());
                tips.put("businessType", businessType);
                tips.put("relName", traBusiness.getRelName());
                tips.put("relNumber", idEncrypt(traBusiness.getRelNumber()));
                tips.put("transType", traBusiness.getTranType());
                tips.put("transactionAmount", String.valueOf(traBusiness.getRelTradeAmount()));
            } else if (null != traInterest) {
                String businessType = selectByValue(traInterest.getBusType(), "INT-BusinessTypes");
                tips.put("relType", traInterest.getRelType());
                tips.put("businessType", businessType);
                tips.put("relName", traInterest.getRelName());
                tips.put("relNumber", idEncrypt(traInterest.getRelNumber()));
                tips.put("transType", traInterest.getTranType());
                tips.put("transactionAmount", String.valueOf(traInterest.getRelTradeAmount()));
            } else if (null != traService) {
                String businessType = selectByValue(traService.getBusType(), "INS-ServiceType");
                tips.put("relType", traService.getRelType());
                tips.put("businessType", businessType);
                tips.put("relName", traService.getRelName());
                tips.put("relNumber", idEncrypt(traService.getRelNumber()));
                tips.put("transType", traService.getTranType());
                tips.put("transactionAmount", String.valueOf(traService.getRelTradeAmount()));
            } else if (null != traOther) {
                String businessType = selectByValue(traOther.getInvestmentType(), "INS-otherType");
                tips.put("relType", traOther.getRelType());
                tips.put("businessType", businessType);
                tips.put("relName", traOther.getRelName());
                tips.put("relNumber", idEncrypt(traOther.getRelNumber()));
                tips.put("transType", traOther.getTranType());
                tips.put("transactionAmount", String.valueOf(traOther.getContractAmount()));
            }
        }
        return tips;
    }


    private String selectByValue(String value, String simpleName) {
        List<NameValuePair> items = new ArrayList<>();
        try {
            List<Filter> filters = new Filter.Builder().eq("dictCode", simpleName).build();
            List<DictItem> list = dictItemService.select(filters);
            //过滤掉不要的字典项
            if (!CollectionUtils.isEmpty(list)) {
                for (DictItem dictItem : list) {
                    if (dictItem.getItemValue().equals(value)) {
                        return dictItem.getItemLabel();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
