package com.yzj.jep.app.pub.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.ins.model.*;
import com.yzj.jep.app.ins.service.TraInterestService;
import com.yzj.jep.app.ins.service.TraInvestmentService;
import com.yzj.jep.app.ins.service.TraServiceService;
import com.yzj.jep.app.pub.model.Publog;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.api.code.ClientCodes;
import com.yzj.jep.workflow.client.ProcessFormHandler;
import com.yzj.jep.workflow.client.annotation.FormHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import com.yzj.jep.app.pub.service.PublogService;

//流程编码


@FormHandler("PUB_YEAR")
public class PubYearFormHandler implements ProcessFormHandler {
    Logger logger = LoggerFactory.getLogger(PubYearFormHandler.class);
    PublogService publogService;


    @Override
    public ModelAndView processForm(String nodekey, String data, String opt, ModelAndView model)
            throws WorkflowException {

        JSONObject json = null;
        try {
            json = JSONObject.parseObject(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Publog publog = JSON.parseObject(data, Publog.class);
        model.addObject("publog", publog);

        String cognizaReason = json.getString("cognizaReason");
        if (StringUtils.isEmpty(cognizaReason)) {
            model.addObject("cognizaReason", "暂无意见");
        } else {
            model.addObject("cognizaReason", json.getString("cognizaReason").trim());
        }
        model.addObject("opt", "edit");
        //不展示内容
        model.addObject("op", 1);
        model.setViewName("pub/pubYearForm");

        return model;
    }
}
