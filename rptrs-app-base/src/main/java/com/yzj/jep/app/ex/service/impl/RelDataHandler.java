package com.yzj.jep.app.ex.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.client.ProcessDataHandler;
import com.yzj.jep.workflow.client.annotation.DataHandler;

//业务逻辑

@DataHandler("YZJ_REL")
public class RelDataHandler implements ProcessDataHandler {
    RelPsnService relPsnService;
    RelOrgService relOrgService;

    @Override
    public void handleData(String data) throws WorkflowException {

        relPsnService = SpringContextHolder.getBean(RelPsnService.class);
        relOrgService = SpringContextHolder.getBean(RelOrgService.class);
        JSONObject json = JSONObject.parseObject(data);
        int pid = (int) json.get("pid");
        String type = (String) json.get("type");
        if ("PSN".equals(type)) {
            RelPsn relPsn = relPsnService.get(pid);
            relPsn.setStatus("6");
            relPsn.setBlackReason((String) json.get("blackReason"));
            if ("0".equals(relPsn.getIsBlack())) {
                relPsn.setIsBlack("1");
            } else {
                relPsn.setIsBlack("0");
            }
            relPsnService.update(relPsn);
        }
        if ("ORG".equals(type)) {
            RelOrg relOrg = relOrgService.get(pid);
            relOrg.setApprovalStatus("6");
            if ("0".equals(relOrg.getIsBlack())) {
                relOrg.setIsBlack("1");
            } else {
                relOrg.setIsBlack("0");
            }
            relOrg.setBlackReason((String) json.get("blackReason"));
            relOrgService.update(relOrg);
        }
    }

}