package com.yzj.jep.app.ex.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.bus.model.Rel;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.client.ProcessFormHandler;
import com.yzj.jep.workflow.client.annotation.FormHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

//流程编码


@FormHandler("ZG_GLGXSPLC")
public class ZG_RelFormHandler implements ProcessFormHandler {
    Logger logger = LoggerFactory.getLogger(ZG_RelFormHandler.class);
    TraCreditService traCreditService;
    TraAssetService traAssetService;
    TraServiceService traServiceService;
    TraOtherService traOtherService;
    RelPsnService relPsnService;
    RelService relService;

    @Override
    public ModelAndView processForm(String nodekey, String data, String opt, ModelAndView model)
            throws WorkflowException {

        traCreditService = SpringContextHolder.getBean(TraCreditService.class);
        traAssetService = SpringContextHolder.getBean(TraAssetService.class);
        traServiceService = SpringContextHolder.getBean(TraServiceService.class);
        traOtherService = SpringContextHolder.getBean(TraOtherService.class);
        relPsnService = SpringContextHolder.getBean(RelPsnService.class);
        //关联关系
        relService = SpringContextHolder.getBean(RelService.class);

        JSONObject json = JSONObject.parseObject(data);
        Integer pid = json.getInteger("pid");
        String type = json.getString("type");
//        Integer pid = Integer.parseInt(jsonString);
//        logger.debug("处理主键[{}]", pid);
        if("REL".equals(type)){
            Rel rel = relService.get(pid);
            model.addObject("rel", rel);
            model.addObject("opt","send");
            model.setViewName("bus/relationView");
        }else {
            throw new WorkflowException("缺少类型参数type");
        }


        return model;
    }
}
