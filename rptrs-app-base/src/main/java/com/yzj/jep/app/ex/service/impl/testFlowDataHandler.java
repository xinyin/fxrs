package com.yzj.jep.app.ex.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.client.ProcessDataHandler;
import com.yzj.jep.workflow.client.annotation.DataHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//业务逻辑

@DataHandler("testFlow001")
public class testFlowDataHandler implements ProcessDataHandler {
    Logger logger = LoggerFactory.getLogger(testFlowDataHandler.class);

    @Override
    public void handleData(String data) throws WorkflowException {

        JSONObject json = JSONObject.parseObject(data);
        logger.info("testFlow001流程完成" + json.getIntValue("amount"));

    }

}