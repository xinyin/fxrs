package com.yzj.jep.app.ex.service.impl;


import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.fil.service.FilFundsService;
import com.yzj.jep.app.fil.service.FilInterestService;
import com.yzj.jep.app.fil.service.FilInvestmentService;
import com.yzj.jep.app.fil.service.FilServiceService;
import com.yzj.jep.app.ins.model.*;
import com.yzj.jep.app.ins.service.TraFundsService;
import com.yzj.jep.app.ins.service.TraInterestService;
import com.yzj.jep.app.ins.service.TraInvestmentService;
import com.yzj.jep.app.ins.service.TraServiceService;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.sys.service.AppConfigService;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.client.ProcessFormHandler;
import com.yzj.jep.workflow.client.annotation.FormHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

//流程编码


/**
 * 总公司二级部门重大关联交易审批流程
 */
//@FormHandler("zjcc_tra001")
@FormHandler("zyzbejzdrel")
public class InsHDSecondTranFormHandler implements ProcessFormHandler {
    Logger logger = LoggerFactory.getLogger(InsHDSecondTranFormHandler.class);
    TraInvestmentService traInvestmentService;
    TraServiceService traServiceService;
    TraInterestService traInterestService;
    TraFundsService traFundsService;
    FilInvestmentService filInvestmentService;
    FilServiceService filServiceService;
    FilInterestService filInterestService;
    FilFundsService filFundsService;
    private AppConfigService appConfigService;


    @Override
    public ModelAndView processForm(String nodekey, String data, String opt, ModelAndView model)
            throws WorkflowException {
        appConfigService = SpringContextHolder.getBean(AppConfigService.class);
//      配置的可编辑节点nodekey
        String editNodekey = appConfigService.getConfigValue("zjcc_tra001");
        //判断当前节点是否是可编辑节点
        //boolean isEdit = nodekey.equals(editNodekey);
        boolean isEdit = false;

        traInvestmentService = SpringContextHolder.getBean(TraInvestmentService.class);
        traInterestService = SpringContextHolder.getBean(TraInterestService.class);
        traServiceService = SpringContextHolder.getBean(TraServiceService.class);
        traFundsService = SpringContextHolder.getBean(TraFundsService.class);
        filInvestmentService = SpringContextHolder.getBean(FilInvestmentService.class);
        filServiceService = SpringContextHolder.getBean(FilServiceService.class);
        filInterestService = SpringContextHolder.getBean(FilInterestService.class);
        filFundsService = SpringContextHolder.getBean(FilFundsService.class);

        JSONObject json = JSONObject.parseObject(data);
        String type = json.getString("tranType");
        if ("01".equals(type)) {
            TraInvestment traInvestment = JSONObject.parseObject(data, TraInvestment.class);
            List<TraRelInfo> relInfoList = traInvestment.getRelInfoList();
            if (CollectionUtil.isNotEmpty(relInfoList)) {
                String s = JSONObject.toJSONString(relInfoList);
                traInvestment.setRelInfoListStr(s);
            }
            model.addObject("traInvestment", traInvestment);
            model.addObject("opt", opt);
            model.setViewName("ex/traInvestmentApprove");
        } else if ("06".equals(type)) {
            TraOther traOther = JSON.parseObject(data, TraOther.class);
            List<TraRelInfo> relInfoList = traOther.getRelInfoList();
            if (CollectionUtil.isNotEmpty(relInfoList)) {
                String s = JSONObject.toJSONString(relInfoList);
                traOther.setRelInfoListStr(s);
            }
            model.addObject("opt", opt);
            model.addObject("traOther", traOther);
            model.setViewName("ex/traOtherApprove");
        } else if ("03".equals(type)) {
            TraBusiness traBusiness = JSON.parseObject(data, TraBusiness.class);
            List<TraRelInfo> relInfoList = traBusiness.getRelInfoList();
            if (CollectionUtil.isNotEmpty(relInfoList)) {
                String s = JSONObject.toJSONString(relInfoList);
                traBusiness.setRelInfoListStr(s);
            }
            model.addObject("opt", opt);
            model.addObject("traBusiness", traBusiness);
            model.setViewName("ex/traBusinessForm");
        } else if ("04".equals(type)) {
            TraInterest traInterest = JSON.parseObject(data, TraInterest.class);
            List<TraRelInfo> relInfoList = traInterest.getRelInfoList();
            if (CollectionUtil.isNotEmpty(relInfoList)) {
                String s = JSON.toJSONString(relInfoList);
                traInterest.setRelInfoListStr(s);
            }
            model.addObject("traInterest", traInterest);
            model.addObject("opt", opt);
            model.setViewName("ex/traInterestApprove");
        } else if ("05".equals(type)) {
            TraService traService = JSON.parseObject(data, TraService.class);
            List<TraRelInfo> relInfoList = traService.getRelInfoList();
            if (CollectionUtil.isNotEmpty(relInfoList)) {
                String s = JSON.toJSONString(relInfoList);
                traService.setRelInfoListStr(s);
            }
            model.addObject("traService", traService);
            model.addObject("opt", opt);
            model.setViewName("ex/traServiceApprove");
        } else if ("02".equals(type)) {
            TraFunds traFunds = JSON.parseObject(data, TraFunds.class);
            List<TraRelInfo> relInfoList = traFunds.getRelInfoList();
            if (CollectionUtil.isNotEmpty(relInfoList)) {
                String s = JSONObject.toJSONString(relInfoList);
                traFunds.setRelInfoListStr(s);
            }
            model.addObject("traFunds", traFunds);
            model.addObject("opt", opt);
            model.setViewName("ex/traFundsSubmit");
        }
        return model;
    }
}
