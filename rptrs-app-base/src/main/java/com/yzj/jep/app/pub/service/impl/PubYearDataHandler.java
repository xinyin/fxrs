package com.yzj.jep.app.pub.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.ins.model.TraFunds;
import com.yzj.jep.app.ins.model.TraInterest;
import com.yzj.jep.app.ins.model.TraInvestment;
import com.yzj.jep.app.ins.model.TraService;
import com.yzj.jep.app.ins.service.TraFundsService;
import com.yzj.jep.app.ins.service.TraInterestService;
import com.yzj.jep.app.ins.service.TraInvestmentService;
import com.yzj.jep.app.ins.service.TraServiceService;
import com.yzj.jep.app.pub.model.Publog;
import com.yzj.jep.app.pub.service.PublogService;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.client.ProcessDataHandler;
import com.yzj.jep.workflow.client.annotation.DataHandler;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//业务逻辑

@DataHandler("PUB_YEAR")
public class PubYearDataHandler implements ProcessDataHandler {
    TraInvestmentService traInvestmentService;
    TraFundsService traFundsService;
    TraServiceService traServiceService;
    PublogService publogService;

    @Override
    public void handleData(String data) throws WorkflowException {
        publogService = SpringContextHolder.getBean(PublogService.class);
        JSONObject json = JSONObject.parseObject(data);
        Map<String, Object> tips = new HashMap<String, Object>();
        int pid = (int) json.get("pid");
        Publog publog = publogService.get(pid);
        if (publog != null) {
            //3 状态由审批中改为 已归档 6
            publog.setStatus("03");
            publog.setStatusComment("");
        }
        publogService.updateSelective(publog);
    }
}