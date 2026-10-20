package com.yzj.jep.app.ex.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.bus.model.RelOrgSuspect;
import com.yzj.jep.app.bus.model.RelPsnSuspect;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.client.ProcessFormHandler;
import com.yzj.jep.workflow.client.annotation.FormHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

//流程编码


@FormHandler("CX_YSGLFSPLC")
public class CX_RelSusPectFormHandler implements ProcessFormHandler {
    Logger logger = LoggerFactory.getLogger(CX_RelSusPectFormHandler.class);
    TraCreditService traCreditService;
    TraAssetService traAssetService;
    TraServiceService traServiceService;
    TraOtherService traOtherService;
    RelPsnService relPsnService;
    RelPsnSuspectService relPsnSuspectService;
    RelOrgSuspectService relOrgSuspectService;

    @Override
    public ModelAndView processForm(String nodekey, String data, String opt, ModelAndView model)
            throws WorkflowException {

        traCreditService = SpringContextHolder.getBean(TraCreditService.class);
        traAssetService = SpringContextHolder.getBean(TraAssetService.class);
        traServiceService = SpringContextHolder.getBean(TraServiceService.class);
        traOtherService = SpringContextHolder.getBean(TraOtherService.class);
        relPsnService = SpringContextHolder.getBean(RelPsnService.class);
        //疑似自然人
        relPsnSuspectService = SpringContextHolder.getBean(RelPsnSuspectService.class);
        //疑似法人
        relOrgSuspectService = SpringContextHolder.getBean(RelOrgSuspectService.class);
        JSONObject json = JSONObject.parseObject(data);
        Integer pid = json.getInteger("pid");
        String type = json.getString("type");
//        Integer pid = Integer.parseInt(jsonString);
//        logger.debug("处理主键[{}]", pid);
        if("PSN".equals(type)){
            RelPsnSuspect relPsnSuspect = relPsnSuspectService.get(pid);
            model.addObject("relPsnSuspect", relPsnSuspect);
            model.setViewName("ex/suspectRelPsnformHandleView");
        }else if("ORG".equals(type)){
            RelOrgSuspect relOrgSuspect = relOrgSuspectService.get(pid);
            model.addObject("relOrgSuspect", relOrgSuspect);
            model.setViewName("ex/relOrgSuspectformHandleView");
        }else{

        }


        return model;
    }
}
