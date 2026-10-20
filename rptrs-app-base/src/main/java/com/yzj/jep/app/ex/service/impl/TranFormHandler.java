package com.yzj.jep.app.ex.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.app.bus.web.controller.ProvideServicesController;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.api.code.ClientCodes;
import com.yzj.jep.workflow.client.ProcessFormHandler;
import com.yzj.jep.workflow.client.annotation.FormHandler;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

//流程编码


@FormHandler("trancredit")
public class TranFormHandler implements ProcessFormHandler {
    Logger logger = LoggerFactory.getLogger(TranFormHandler.class);
    TraCreditService traCreditService;
    TraAssetService traAssetService;
    TraServiceService traServiceService;
    TraOtherService traOtherService;
    RelPsnService relPsnService;

    @Override
    public ModelAndView processForm(String nodekey, String data, String opt, ModelAndView model)
            throws WorkflowException {

        traCreditService = SpringContextHolder.getBean(TraCreditService.class);
        traAssetService = SpringContextHolder.getBean(TraAssetService.class);
        traServiceService = SpringContextHolder.getBean(TraServiceService.class);
        traOtherService = SpringContextHolder.getBean(TraOtherService.class);
        relPsnService = SpringContextHolder.getBean(RelPsnService.class);
        JSONObject json = JSONObject.parseObject(data);
//        String jsonString = json.getString("pid");
//        Integer pid = Integer.parseInt(jsonString);
//        logger.debug("处理主键[{}]", pid);
        if (ClientCodes.OPT_VIEW.equals(opt)) {
            if ("SERVICE".equals(json.getString("type"))) {
                TraService traService = JSON.parseObject(data, TraService.class);
                model.addObject("provideServices", traService);
                model.setViewName("ex/provideServicesEdit");
            }
            if ("OTHER".equals(json.getString("type"))) {
                TraOther traOther = JSON.parseObject(data, TraOther.class);
                model.addObject("otherType", traOther);
                model.setViewName("ex/otherTypeEdit");
            }
            if ("ASSET".equals(json.getString("type"))) {
                TraAsset traAsset = JSON.parseObject(data, TraAsset.class);
                model.addObject("traAsset", traAsset);
                model.setViewName("ex/traAssetEdit");
            }
            if ("CREDIT".equals(json.getString("type"))) {
                TraCredit traCredit = JSON.parseObject(data, TraCredit.class);
                if (traCredit.getTranScope() != null) {
                    List list = JSON.parseObject(traCredit.getTranScope(), List.class);
                    String tranScope = StringUtils.join(list, ',');
                    traCredit.setTranScope(tranScope);
                }
                model.addObject("traCredit", traCredit);
                model.setViewName("ex/traCreditEdit");
            }
            if ("USECREDIT".equals(json.getString("type"))) {
                TraUsecredit traUsecredit = JSON.parseObject(data, TraUsecredit.class);
                String subRemark = json.getString("subRemark");
                model.addObject("subRemark", subRemark);
                model.addObject("traUsecredit", traUsecredit);
                model.setViewName("ex/traUsecreditSubmit");
            }
        }
        if (ClientCodes.OPT_EDIT.equals(opt)) {
            if ("SERVICE".equals(json.getString("type"))) {
                model.addObject("businessType", json.getString("businessType"));
                model.addObject("relatedName", json.getString("relatedName"));
                model.addObject("houseManageOrg", json.getString("houseManageOrg"));
                model.addObject("identNumber", json.getString("identNumber"));
                model.addObject("identlfyType", json.getString("identlfyType"));
                model.addObject("approvalTime", json.getString("approvalTime"));
                model.addObject("validityContract", json.getString("validityContract"));
                model.addObject("tranAmount", json.getString("tranAmount"));
                model.addObject("tranOmpTime", json.getString("tranOmpTime"));
                model.addObject("tranType", json.getString("tranType"));
                model.addObject("tranTarget", json.getString("tranTarget"));
                model.addObject("tranInfo", json.getString("tranInfo"));
                model.addObject("pricePolicy", json.getString("pricePolicy"));
                model.addObject("pid", json.getString("pid"));
                model.setViewName("ex/provideServicesEdit");
            }
            if ("OTHER".equals(json.getString("type"))) {
                model.addObject("businessType", json.getString("businessType"));
                model.addObject("relatedName", json.getString("relatedName"));
                model.addObject("houseManageOrg", json.getString("houseManageOrg"));
                model.addObject("identNumber", json.getString("identNumber"));
                model.addObject("identlfyType", json.getString("identlfyType"));
                model.addObject("approvalTime", json.getString("approvalTime"));
                model.addObject("validityContract", json.getString("validityContract"));
                model.addObject("tranAmount", json.getString("tranAmount"));
                model.addObject("tranOmpTime", json.getString("tranOmpTime"));
                model.addObject("tranType", json.getString("tranType"));
                model.addObject("tranTarget", json.getString("tranTarget"));
                model.addObject("tranInfo", json.getString("tranInfo"));
                model.addObject("pricePolicy", json.getString("pricePolicy"));
                model.addObject("pid", json.getString("pid"));
                model.setViewName("ex/otherEdit");
            }
            if ("ASSET".equals(json.getString("type"))) {
                model.addObject("businessType", json.getString("businessType"));
                model.addObject("relatedName", json.getString("relatedName"));
                model.addObject("manageOrg", json.getString("manageOrg"));
                model.addObject("identNumber", json.getString("identNumber"));
                model.addObject("identifyType", json.getString("identifyType"));
                model.addObject("approvalTime", json.getString("approvalTime"));
                model.addObject("validityContract", json.getString("validityContract"));
                model.addObject("tranAmount", json.getString("tranAmount"));
                model.addObject("tranOmpTime", json.getString("tranOmpTime"));
                model.addObject("tranType", json.getString("tranType"));
                model.addObject("tranTarget", json.getString("tranTarget"));
                model.addObject("tranInfo", json.getString("tranInfo"));
                model.addObject("appraisalAssets", json.getString("appraisalAssets"));
                model.addObject("pid", json.getString("pid"));
                model.setViewName("ex/assrtEdit");
            }
            if ("CREDIT".equals(json.getString("type"))) {
                model.addObject("businessType", json.getString("businessType"));
                model.addObject("relatedName", json.getString("relatedName"));
                model.addObject("manageOrg", json.getString("manageOrg"));
                model.addObject("identNumber", json.getString("identNumber"));
                model.addObject("identifyType", json.getString("identifyType"));
                model.addObject("approvalTime", json.getString("approvalTime"));
                model.addObject("validityContract", json.getString("validityContract"));
                model.addObject("creditAmount", json.getString("creditAmount"));
                model.addObject("creditStartDate", json.getString("creditStartDate"));
                model.addObject("creditEndDate", json.getString("creditEndDate"));
                model.addObject("guaraMode", json.getString("guaraMode"));
                model.addObject("collateral", json.getString("collateral"));
                model.addObject("guarantor", json.getString("guarantor"));
                model.addObject("five_level", json.getString("five_level"));
                model.addObject("securit_deposit", json.getString("securit_deposit"));

                model.addObject("margin_ratio", json.getString("margin_ratio"));
                model.addObject("business_type", json.getString("business_type"));
                model.addObject("regulator_ybj", json.getString("regulator_ybj"));
                model.addObject("regulator_zjh", json.getString("regulator_zjh"));
                model.addObject("regulator_czb", json.getString("regulator_czb"));
                model.addObject("related_tran_type", json.getString("related_tran_type"));
                model.addObject("tran_omp_time", json.getString("tran_omp_time"));
                model.addObject("tran_target", json.getString("tran_target"));
                model.addObject("pid", json.getString("pid"));
                model.setViewName("ex/creditEdit");
            }
            if ("CheckByPsn".equals(json.getString("processType"))) {
                model.addObject("sex", json.getString("sex"));
                model.addObject("name", json.getString("name"));
                model.addObject("pid", json.getString("pid"));
                model.setViewName("ex/suspectRelPsnEdit");
            }
        }

        return model;
    }
}
