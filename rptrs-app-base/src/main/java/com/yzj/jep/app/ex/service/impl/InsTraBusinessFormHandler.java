package com.yzj.jep.app.ex.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.fil.model.FilBusiness;
import com.yzj.jep.app.fil.model.FilInvestment;
import com.yzj.jep.app.fil.service.FilBusinessService;
import com.yzj.jep.app.ins.model.TraBusiness;
import com.yzj.jep.app.ins.model.TraInvestment;
import com.yzj.jep.app.ins.service.TraBusinessService;
import com.yzj.jep.app.ins.service.TraInvestmentService;
import com.yzj.jep.app.util.JsonUtil;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.api.code.ClientCodes;
import com.yzj.jep.workflow.client.ProcessFormHandler;
import com.yzj.jep.workflow.client.annotation.FormHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

//流程编码


@FormHandler("YZJ_INS_TRA_BUSINESS")
public class InsTraBusinessFormHandler implements ProcessFormHandler {
    Logger logger = LoggerFactory.getLogger(InsTraBusinessFormHandler.class);
    TraBusinessService traBusinessService;
    FilBusinessService filBusinessService;

    @Override
    public ModelAndView processForm(String nodekey, String data, String opt, ModelAndView model)
            throws WorkflowException {
        filBusinessService = SpringContextHolder.getBean(FilBusinessService.class);
        JSONObject json = JSONObject.parseObject(data);
       /*JSONObject json = null;
        try {
            json = JSONObject.parseObject(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        TraBusiness traBusiness = JSON.parseObject(data, TraBusiness.class);
        model.addObject("traBusiness", traBusiness);

        String cognizaReason = json.getString("cognizaReason");
        if (StringUtils.isEmpty(cognizaReason)) {
            model.addObject("cognizaReason", "暂无意见");
        } else {
            model.addObject("cognizaReason", json.getString("cognizaReason").trim());
        }
        model.addObject("opt","edit");
        //不展示内容
        model.addObject("op",1);
        model.setViewName("ex/traBusinessForm");*/
        TraBusiness traBusiness = JSON.parseObject(data, TraBusiness.class);
        //获取当前pid查询备案表里面是否有备案编号里包含pid
        List<Filter> filters = new Filter.Builder().eq("flagPid", json.getString("pid")).build();
        List<FilBusiness> list = filBusinessService.select(filters);
        if (list.size() > 1) {
            FilBusiness filBusiness = list.get(0);
            TraBusiness traBusinessL = new TraBusiness();
            BeanUtils.copyProperties(filBusiness, traBusinessL);
            //实体类转json
            JSONObject traBusiness1 = (JSONObject) JSON.toJSON(traBusiness);
            JSONObject traBusiness2 = (JSONObject) JSON.toJSON(traBusinessL);
            Map result = JsonUtil.compareJson(traBusiness1, traBusiness2, null);
            model.addObject("traBusiness", traBusiness);
            model.addObject("filBusiness2", result);
            model.addObject("opt", "view");
            model.setViewName("ex/traBusinessForm");
        } else {
            JSONObject traBusiness1 = (JSONObject) JSON.toJSON(traBusiness);
            JSONObject traBusiness2 = (JSONObject) JSON.toJSON(traBusiness);
            Map result = JsonUtil.compareJson(traBusiness1, traBusiness2, null);
            model.addObject("traBusiness", traBusiness);
            model.addObject("filBusiness2", result);
            model.addObject("opt", "view");
            model.setViewName("ex/traBusinessForm");
        }
        return model;
    }
}
