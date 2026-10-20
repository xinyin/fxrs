package com.yzj.jep.app.ex.service.impl;


import java.util.List;

import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.api.code.ClientCodes;
import com.yzj.jep.workflow.client.ProcessFormHandler;
import com.yzj.jep.workflow.client.annotation.FormHandler;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;


@FormHandler("YJZR_LeaveApplyProcess001")
public class LeaveApplyProcessFormHandler implements ProcessFormHandler {


    @Override
    public ModelAndView processForm(String nodekey, String data, String opt, ModelAndView model)
            throws WorkflowException {


        JSONObject json = JSONObject.parseObject(data);
        model.addObject("uninId", (json.getString("startDate") + json.getString("endDate") + json.getString("mobile") + json.getString("hours")).replace(" ", ""));
        model.addObject("flag", "1");
        if (ClientCodes.OPT_EDIT.equals(opt)) {
            model.addObject("mobile", json.getString("mobile"));
            model.addObject("startDate", json.getString("startDate"));
            model.addObject("endDate", json.getString("endDate"));
            model.addObject("hours", json.getString("hours"));
            model.addObject("remark", json.getString("remark"));
            model.addObject("toHR", json.getString("toHR"));
            model.addObject("toDUM", json.getString("toDUM"));
            model.addObject("toBUM", json.getString("toBUM"));
            model.setViewName("ex/attendanceProcces");
        }
        if (ClientCodes.OPT_VIEW.equals(opt)) {
            model.addObject("mobile", json.getString("mobile"));
            model.addObject("startDate", json.getString("startDate"));
            model.addObject("endDate", json.getString("endDate"));
            model.addObject("hours", json.getString("hours"));
            model.addObject("remark", json.getString("remark"));
            model.setViewName("ex/attendanceProcces");
        }

        return model;
    }
}
