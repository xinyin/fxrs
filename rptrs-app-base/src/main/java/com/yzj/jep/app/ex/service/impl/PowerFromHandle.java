package com.yzj.jep.app.ex.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.bus.model.AuthApply;
import com.yzj.jep.app.bus.model.RelPro;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.client.ProcessFormHandler;
import com.yzj.jep.workflow.client.annotation.DataHandler;
import com.yzj.jep.workflow.client.annotation.FormHandler;
import org.springframework.web.servlet.ModelAndView;

@FormHandler("ZZ_AuthApproval")
public class PowerFromHandle implements ProcessFormHandler {
    @Override
    public ModelAndView processForm(String nodeKey, String data, String opt, ModelAndView model) throws WorkflowException {
        JSONObject json = null;
        try {
            json = JSONObject.parseObject(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        AuthApply authApply = JSON.parseObject(data, AuthApply.class);
        model.addObject("authApply", authApply);

        String cognizaReason = json.getString("cognizaReason");
        if (StringUtils.isEmpty(cognizaReason)) {
            model.addObject("cognizaReason", "暂无意见");
        } else {
            model.addObject("cognizaReason", json.getString("cognizaReason").trim());
        }
        model.addObject("opt", "edit");
        //不展示内容
        model.addObject("op", 1);
        model.setViewName("ex/qxForm");

        return model;
    }
}
