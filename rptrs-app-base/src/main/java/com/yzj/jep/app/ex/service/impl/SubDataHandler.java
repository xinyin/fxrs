package com.yzj.jep.app.ex.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.sub.model.Sublog;
import com.yzj.jep.app.sub.service.SublogService;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.client.ProcessDataHandler;
import com.yzj.jep.workflow.client.annotation.DataHandler;

import java.util.Date;


//业务逻辑

@DataHandler("YZJ_SUB001")
public class SubDataHandler implements ProcessDataHandler {

    SublogService sublogService;

    @Override
    public void handleData(String data) throws WorkflowException {
        sublogService = SpringContextHolder.getBean(SublogService.class);

        JSONObject json = JSONObject.parseObject(data);
        String jsonString = json.getString("pid");
        Integer pid = Integer.parseInt(jsonString);
//        String ment = json.getString("statusComment");

        Sublog sublog = sublogService.get(pid);
        sublog.setStatus("03");
        sublog.setStatusComment("审批通过");
        sublog.setModifiedTime(new Date());

        sublogService.update(sublog);
    }
}