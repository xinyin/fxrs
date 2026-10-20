package com.yzj.jep.app.ex.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.bus.service.TraAssetService;
import com.yzj.jep.app.bus.service.TraCreditService;
import com.yzj.jep.app.ins.model.*;
import com.yzj.jep.app.ins.service.*;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.client.ProcessFormHandler;
import com.yzj.jep.workflow.client.annotation.FormHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

//流程编码


@FormHandler("ZG_GLJYSPLC")
public class ZG_TraFormHandler implements ProcessFormHandler {
    Logger logger = LoggerFactory.getLogger(ZG_TraFormHandler.class);
    TraCreditService traCreditService;
    TraAssetService traAssetService;
    RelPsnService relPsnService;
    TraInvestmentService traInvestmentService;
    TraFundsService traFundsService;
    TraBusinessService traBusinessService;
    TraInterestService traInterestService;
    TraServiceService traServiceService;
    TraOtherService traOtherService;


    @Override
    public ModelAndView processForm(String nodekey, String data, String opt, ModelAndView model)
            throws WorkflowException {

        traCreditService = SpringContextHolder.getBean(TraCreditService.class);
        traAssetService = SpringContextHolder.getBean(TraAssetService.class);
        relPsnService = SpringContextHolder.getBean(RelPsnService.class);
        //投资入股类
        traInvestmentService = SpringContextHolder.getBean(TraInvestmentService.class);
        //资金运用
        traFundsService = SpringContextHolder.getBean(TraFundsService.class);
        //保险业务
        traBusinessService = SpringContextHolder.getBean(TraBusinessService.class);
        //利益转移
        traInterestService = SpringContextHolder.getBean(TraInterestService.class);
        //提供服务
        traServiceService = SpringContextHolder.getBean(TraServiceService.class);
        //其他类
        traOtherService = SpringContextHolder.getBean(TraOtherService.class);
        JSONObject json = JSONObject.parseObject(data);
        Integer pid = json.getInteger("pid");
        String type = json.getString("type");
//        Integer pid = Integer.parseInt(jsonString);
//        logger.debug("处理主键[{}]", pid);
        //审批页面标识
        model.addObject("optNoe","handler");
        if("investment".equals(type)){
            TraInvestment investment = traInvestmentService.get(pid);
            model.addObject("traInvestment",investment );
            model.addObject("opt","view");
            model.setViewName("ins/traInvestmentEdit");
        }else if("funds".equals(type)){
            TraFunds funds = traFundsService.get(pid);
            model.addObject("traFunds",funds);
            model.addObject("opt","view");
            model.setViewName("ins/traFundsEdit");
        }else if("business".equals(type)){
            TraBusiness business = traBusinessService.get(pid);
            model.addObject("traBusiness",business);
            model.addObject("opt","view");
            model.setViewName("ins/traBusinessEdit");
        }else if("interest".equals(type)){
            TraInterest interest = traInterestService.get(pid);
            model.addObject("traInterest",interest);
            model.addObject("opt","view");
            model.setViewName("ins/traInterestEdit");
        }else if("service".equals(type)){
            TraService service = traServiceService.get(pid);
            model.addObject("traService",service);
            model.addObject("opt","view");
            model.setViewName("ins/traServiceEdit");
        }else if("other".equals(type)){
            TraOther other = traOtherService.get(pid);
            model.addObject("traOther",other);
            model.addObject("opt","view");
            model.setViewName("ins/traOtherEdit");
        }else{
            throw new WorkflowException("缺少类型参数type");
        }


        return model;
    }
}
