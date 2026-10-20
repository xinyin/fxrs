package com.yzj.jep.app.ex.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.bus.model.EntrustApply;
import com.yzj.jep.app.bus.service.EntrustApplyService;
import com.yzj.jep.app.fil.model.FilEntrustApply;
import com.yzj.jep.app.fil.service.FilEntrustApplyService;
import com.yzj.jep.app.mid.model.BusProc;
import com.yzj.jep.app.mid.service.BusProcService;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.client.ProcessDataHandler;
import com.yzj.jep.workflow.client.annotation.DataHandler;
import com.yzj.jep.workflow.wf.model.ProcAutoTransfer;
import com.yzj.jep.workflow.wf.service.ProcAutoTransferService;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@DataHandler("ZZ_taskDelegation")
public class EntrustDataHandler implements ProcessDataHandler {

    private EntrustApplyService entrustApplyService;

    private ProcAutoTransferService procAutoTransferService;

    private BusProcService busProcService;

    private FilEntrustApplyService filEntrustApplyService;

    @Override
    public void handleData(String data) throws WorkflowException {
        try {
            ProcAutoTransfer procAutoTransfer = new ProcAutoTransfer();
            entrustApplyService = SpringContextHolder.getBean(EntrustApplyService.class);
            procAutoTransferService = SpringContextHolder.getBean(ProcAutoTransferService.class);
            busProcService = SpringContextHolder.getBean(BusProcService.class);
            filEntrustApplyService = SpringContextHolder.getBean(FilEntrustApplyService.class);
            JSONObject json = JSONObject.parseObject(data);
            String procInstId = (String) json.get("procInstId");
            String filNum = json.getString("filNum");
            EntrustApply entrustApply = entrustApplyService.get(json.getInteger("pid"));
            //已完成，审批通过
            entrustApply.setStatus(6);
            entrustApplyService.update(entrustApply);
            LocalDate localDate =LocalDate.now();
            Date date = entrustApply.getStartTime();
            Instant instant = date.toInstant();
            ZoneId zoneId = ZoneId.systemDefault();
            LocalDate stDate = instant.atZone(zoneId).toLocalDate();
            if(stDate.isAfter(localDate)){
                procAutoTransfer.setStatus("0");
            }else{
                procAutoTransfer.setStatus("1");
            }
            procAutoTransfer.setStaffName(entrustApply.getStaffName());
            procAutoTransfer.setStaffCode(entrustApply.getStaffCode());
            procAutoTransfer.setStartTime(entrustApply.getStartTime());
            procAutoTransfer.setStaffDep(entrustApply.getStaffDep());
            procAutoTransfer.setEndTime(entrustApply.getEndTime());
            procAutoTransfer.setTransferStaffDep(entrustApply.getTransferStaffDep());
            procAutoTransfer.setApproveStatus("6");
            procAutoTransfer.setDelflag("1");
            procAutoTransfer.setCreateTime(new Date());
            //新增委托
            if("0".equals(entrustApply.getEntrustType())){
                procAutoTransfer.setTransferStaffName(entrustApply.getTransferStaffName());
                procAutoTransfer.setTransferStaffCode(entrustApply.getTransferStaffCode());
            }else {
                //变更委托
                //1:更具任务编号先取消原来的委托任务
                ProcAutoTransfer pro = procAutoTransferService.get(entrustApply.getTaskNumber());
                pro.setStatus("3");
                procAutoTransferService.update(pro);
                //2：添加新的委托任务
                procAutoTransfer.setTransferStaffName(entrustApply.getTransferStaffNameNew());
                procAutoTransfer.setTransferStaffCode(entrustApply.getTransferStaffCodeNew());
            }
            procAutoTransfer.setId(UUID.randomUUID().toString());
            procAutoTransferService.insertSelective(procAutoTransfer);
            /**保存备份数据*/
            FilEntrustApply filEntrustApply = new FilEntrustApply();
            BeanUtil.copyProperties(entrustApply,filEntrustApply);
            filEntrustApply.setFilNum(filNum);
            filEntrustApply.setFlagPid(String.valueOf(entrustApply.getPid()));
            filEntrustApplyService.insertSelective(filEntrustApply);
            filEntrustApply = filEntrustApplyService.get(filEntrustApply);
            /**保存备份中间表*/
            BusProc busProc = new BusProc();
            busProc.setProcInstId(procInstId);
            busProc.setFlagPid(String.valueOf(json.get("pid")));
            busProc.setFilPid(String.valueOf(filEntrustApply.getPid()));
            busProc.setBusType("entrust_apply");
            busProcService.insertSelective(busProc);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("委托审批异常");
        }

    }
}
