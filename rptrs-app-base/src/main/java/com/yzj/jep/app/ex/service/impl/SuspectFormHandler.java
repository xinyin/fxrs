package com.yzj.jep.app.ex.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.bus.model.RelOrgSuspect;
import com.yzj.jep.app.bus.model.RelPsnSuspect;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.api.code.ClientCodes;
import com.yzj.jep.workflow.client.ProcessFormHandler;
import com.yzj.jep.workflow.client.annotation.FormHandler;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

//流程编码


@FormHandler("YZJ_SUSPECT")
public class SuspectFormHandler implements ProcessFormHandler {


    @Override
    public ModelAndView processForm(String nodekey, String data, String opt, ModelAndView model)
            throws WorkflowException {


        JSONObject json = null;
        try {
            json = JSONObject.parseObject(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (ClientCodes.OPT_VIEW.equals(opt)) {
            if ("CheckByPsn".equals(json.getString("processType"))) {
//                model.addObject("sex", json.getString("sex"));
//                model.addObject("name", json.getString("name"));
//                model.addObject("pid", json.getString("pid"));
//                model.addObject("cognizaReason", json.getString("cognizaReason").trim());
//
//                model.addObject("race", json.getString("race"));
//                model.addObject("domesticOrForeign", json.getString("domesticOrForeign"));
//                model.addObject("identifyType", json.getString("identifyType"));
//                model.addObject("identifyNumber", json.getString("identifyNumber"));
//                model.addObject("maritalStatus", json.getString("maritalStatus"));
//                model.addObject("birthdate", json.getString("birthdate"));
//                model.addObject("mobile", json.getString("mobile"));
//
//                model.addObject("email", json.getString("email"));
//                model.addObject("mgerOrg", json.getString("mgerOrg"));
//                model.addObject("cognizaReason", json.getString("cognizaReason"));
//                model.addObject("isSuspect", json.getString("isSuspect"));
                RelPsnSuspect relPsnSuspect = JSON.parseObject(data, RelPsnSuspect.class);
                model.addObject("relPsnSuspect", relPsnSuspect);

                String cognizaReason = json.getString("cognizaReason");
                if (StringUtils.isEmpty(cognizaReason)) {
                    model.addObject("cognizaReason", "暂无意见");
                } else {
                    model.addObject("cognizaReason", json.getString("cognizaReason").trim());
                }
                model.setViewName("ex/suspectRelPsnEdit");
            }
            if ("CheckByOrg".equals(json.getString("processType"))) {
//                model.addObject("pid", json.getString("pid"));
//                model.addObject("orgName", json.getString("orgName"));
//                model.addObject("orgType", json.getString("orgType"));
//                model.addObject("cognizaReason", json.getString("cognizaReason").trim());
//
//                model.addObject("domesticOrForeign", json.getString("domesticOrForeign"));
//                model.addObject("identifyType", json.getString("identifyType"));
//                model.addObject("uniscCode", json.getString("uniscCode"));
//                model.addObject("regCapital", json.getString("regCapital"));
//                model.addObject("regCapitalCurrency", json.getString("regCapitalCurrency"));
//                model.addObject("legalPerson", json.getString("legalPerson"));
//
//                model.addObject("isShareholder", json.getString("isShareholder"));
//                model.addObject("stateOwned", json.getString("stateOwned"));
//                model.addObject("shareholdersCode", json.getString("shareholdersCode"));
//                model.addObject("openBank", json.getString("openBank"));
//
//                model.addObject("tradeAccount", json.getString("tradeAccount"));
//                model.addObject("holeShareAmount", json.getString("holeShareAmount"));
//                model.addObject("shareholdingProportion", json.getString("shareholdingProportion"));
//                model.addObject("regAddress", json.getString("regAddress"));
                RelOrgSuspect relOrgSuspect = JSON.parseObject(data, RelOrgSuspect.class);
                model.addObject("relOrgSuspect", relOrgSuspect);

                String cognizaReason = json.getString("cognizaReason");
                if (StringUtils.isEmpty(cognizaReason)) {
                    model.addObject("cognizaReason", "暂无意见");
                } else {
                    model.addObject("cognizaReason", json.getString("cognizaReason").trim());
                }
                model.setViewName("ex/suspectRelOrgEdit");
            }
        }

        return model;
    }
}
