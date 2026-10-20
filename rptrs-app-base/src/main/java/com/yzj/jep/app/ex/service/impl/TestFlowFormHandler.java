package com.yzj.jep.app.ex.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.api.code.ClientCodes;
import com.yzj.jep.workflow.client.ProcessFormHandler;
import com.yzj.jep.workflow.client.annotation.FormHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

//流程编码


@FormHandler("testFlow001")
public class TestFlowFormHandler implements ProcessFormHandler {
    Logger logger = LoggerFactory.getLogger(TestFlowFormHandler.class);

    @Override
    public ModelAndView processForm(String nodekey, String data, String opt, ModelAndView model)
            throws WorkflowException {
//        data=data.substring(1,data.length()-1).replace("\\","");

        JSONObject json = JSONObject.parseObject(data);
        if (ClientCodes.OPT_VIEW.equals(opt)) {
            model.addObject("amount", json.getIntValue("amount"));
            model.setViewName("ex/testFlowApprove");
        }
        if (ClientCodes.OPT_EDIT.equals(opt)) {
            model.addObject("amount", json.getIntValue("amount"));
            model.setViewName("ex/testFlowApprove");
        }

        return model;
    }
}
