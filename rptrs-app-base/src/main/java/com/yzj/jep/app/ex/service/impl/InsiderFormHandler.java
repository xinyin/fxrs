package com.yzj.jep.app.ex.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.client.ProcessFormHandler;
import com.yzj.jep.workflow.client.annotation.FormHandler;
import org.springframework.web.servlet.ModelAndView;

@FormHandler("YZJ_REL_INSIDER")
public class InsiderFormHandler implements ProcessFormHandler {
    @Override
    public ModelAndView processForm(String nodeKey, String data, String opt, ModelAndView model) throws WorkflowException {
        JSONObject json = null;
        try {
            json = JSONObject.parseObject(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        model.addObject("sex", json.getString("sex"));
//        model.addObject("orgName", json.getString("orgName"));
//        model.addObject("pid", json.getString("pid"));
////        model.addObject("race", json.getString("race"));
//        model.addObject("domesticOrForeign", json.getString("domesticOrForeign"));
//        model.addObject("identifyType", json.getString("identifyType"));
//        model.addObject("uniscCode", json.getString("uniscCode"));
//        model.addObject("mgerOrg", json.getString("mgerOrg"));
//        model.addObject("toRelCategory", json.getString("toRelCategory"));
//        model.addObject("shareholdingRatio", json.getString("shareholdingRatio"));
//        model.addObject("isMainGd", json.getString("isMainGd"));
//        String cognizaReason = json.getString("cognizaReason");
//        if (StringUtils.isEmpty(cognizaReason)) {
//            model.addObject("cognizaReason", "暂无意见");
//        } else {
//            model.addObject("cognizaReason", json.getString("cognizaReason").trim());
//        }

        RelOrg relOrg = JSON.parseObject(data, RelOrg.class);
        model.addObject("relOrg", relOrg);

        String cognizaReason = json.getString("cognizaReason");
        if (StringUtils.isEmpty(cognizaReason)) {
            model.addObject("cognizaReason", "暂无意见");
        } else {
            model.addObject("cognizaReason", json.getString("cognizaReason").trim());
        }
        //不展示内容
        model.addObject("opt", "edit");
        model.addObject("op", 1);
        model.setViewName("ex/insiderForm");

        return model;
    }
}
