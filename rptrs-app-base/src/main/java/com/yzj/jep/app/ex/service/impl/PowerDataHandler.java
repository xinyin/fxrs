package com.yzj.jep.app.ex.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.bus.model.AuthApply;
import com.yzj.jep.app.bus.service.AuthApplyService;
import com.yzj.jep.app.fil.model.FilAuthApply;
import com.yzj.jep.app.fil.service.FilAuthApplyService;
import com.yzj.jep.app.mid.model.BusProc;
import com.yzj.jep.app.mid.service.BusProcService;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.client.ProcessDataHandler;
import com.yzj.jep.workflow.client.annotation.DataHandler;

@DataHandler("ZZ_AuthApproval")
public class PowerDataHandler implements ProcessDataHandler {

    private AuthApplyService authApplyService;
    private BusProcService busProcService;
    private FilAuthApplyService filAuthApplyService;

    @Override
    public void handleData(String data) throws WorkflowException {
        authApplyService = SpringContextHolder.getBean(AuthApplyService.class);
        busProcService = SpringContextHolder.getBean(BusProcService.class);
        filAuthApplyService = SpringContextHolder.getBean(FilAuthApplyService.class);
        JSONObject json = JSONObject.parseObject(data);
        String procInstId = (String) json.get("procInstId");
        String filNum = json.getString("filNum");
        AuthApply authApply = authApplyService.get(json.getInteger("pid"));
        //已完成，审批通过
        authApply.setStatus("6");
        authApplyService.updateSelective(authApply);
        /**处理备份*/
        FilAuthApply filAuthApply = new FilAuthApply();
        BeanUtil.copyProperties(authApply, filAuthApply);
        filAuthApply.setFilNum(filNum);
        filAuthApplyService.insertSelective(filAuthApply);
        filAuthApply = filAuthApplyService.get(filAuthApply);
        /**保存备份中间表*/
        BusProc busProc = new BusProc();
        busProc.setProcInstId(procInstId);
        busProc.setFlagPid(String.valueOf(json.get("pid")));
        busProc.setFilPid(String.valueOf(filAuthApply.getPid()));
        busProc.setBusType("auth_apply");
        busProcService.insertSelective(busProc);
    }
}
