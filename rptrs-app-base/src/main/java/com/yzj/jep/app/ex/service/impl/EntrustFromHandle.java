package com.yzj.jep.app.ex.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.bus.model.AuthApply;
import com.yzj.jep.app.bus.model.EntrustApply;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.client.ProcessFormHandler;
import com.yzj.jep.workflow.client.annotation.FormHandler;
import org.springframework.web.servlet.ModelAndView;

@FormHandler("ZZ_taskDelegation")
public class EntrustFromHandle implements ProcessFormHandler {
    @Override
    public ModelAndView processForm(String nodeKey, String data, String opt, ModelAndView model) throws WorkflowException {
        JSONObject json = null;
        try {
            json = JSONObject.parseObject(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        EntrustApply entrustApply = JSON.parseObject(data, EntrustApply.class);
        if("0".equals(entrustApply.getEntrustType())){
            entrustApply.setEntrustType("新增委托");
        }else {
            entrustApply.setEntrustType("变更委托");
        }
        model.addObject("entrustApply", entrustApply);

        String cognizaReason = json.getString("cognizaReason");
        if (StringUtils.isEmpty(cognizaReason)) {
            model.addObject("cognizaReason", "暂无意见");
        } else {
            model.addObject("cognizaReason", json.getString("cognizaReason").trim());
        }
        model.addObject("opt", "edit");
        //不展示内容
        model.addObject("op", 1);
        model.setViewName("ex/entrustForm");

        return model;
    }
}
