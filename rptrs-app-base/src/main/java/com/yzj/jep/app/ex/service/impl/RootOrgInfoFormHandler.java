package com.yzj.jep.app.ex.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.bus.model.RootOrgInfo;
import com.yzj.jep.app.bus.service.RootOrgInfoService;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.client.ProcessFormHandler;
import com.yzj.jep.workflow.client.annotation.FormHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * @title: RootOrgInfoFormHandler
 * @Author Whx
 * @Date: 2021/7/20 9:31
 * @Version 1.0
 */
@FormHandler("ZZ_Org001")
public class RootOrgInfoFormHandler implements ProcessFormHandler {

    private RootOrgInfoService rootOrgInfoService;

    @Override
    public ModelAndView processForm(String nodeKey, String data, String opt, ModelAndView model) throws WorkflowException {
        rootOrgInfoService = SpringContextHolder.getBean(RootOrgInfoService.class);
        JSONObject json = null;
        try {
            json = JSONObject.parseObject(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RootOrgInfo rootOrgInfo = JSON.parseObject(data, RootOrgInfo.class);
        model.addObject("rootOrgInfo", rootOrgInfo);
        model.addObject("opt", "view");
        model.setViewName("ex/rootOrgInfoForm");
        return model;
    }
}
