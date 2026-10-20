package com.yzj.jep.app.ex.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.fil.model.*;
import com.yzj.jep.app.fil.service.*;
import com.yzj.jep.app.ins.model.*;
import com.yzj.jep.app.ins.service.*;
import com.yzj.jep.app.mid.model.BusProc;
import com.yzj.jep.app.mid.service.BusProcService;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.client.ProcessDataHandler;
import com.yzj.jep.workflow.client.annotation.DataHandler;
import org.springframework.beans.BeanUtils;

import java.util.Date;

//业务逻辑

/**
 * 总部二级部门一般关联交易
 */
//@DataHandler("zjcc_YB_tra001")
@DataHandler("zyzbejybrel ")
public class InsHDSecondYbTranDataHandler implements ProcessDataHandler {
    TraInvestmentService traInvestmentService;
    TraFundsService traFundsService;
    TraServiceService traServiceService;
    TraInterestService traInterestService;
    FilInvestmentService filInvestmentService;
    FilInterestService filInterestService;
    FilServiceService filServiceService;
    FilFundsService filFundsService;
    TraOtherService traOtherService;
    TraBusinessService traBusinessService;
    BusProcService busProcService;
    FilBusinessService filBusinessService;
    FilOtherService filOtherService;

    @SuppressWarnings("AlibabaUndefineMagicConstant")
    @Override
    public void handleData(String data) throws WorkflowException {

        traInvestmentService = SpringContextHolder.getBean(TraInvestmentService.class);
        traFundsService = SpringContextHolder.getBean(TraFundsService.class);
        traInterestService = SpringContextHolder.getBean(TraInterestService.class);
        traServiceService = SpringContextHolder.getBean(TraServiceService.class);
        filInvestmentService = SpringContextHolder.getBean(FilInvestmentService.class);
        filInterestService = SpringContextHolder.getBean(FilInterestService.class);
        filServiceService = SpringContextHolder.getBean(FilServiceService.class);
        filFundsService = SpringContextHolder.getBean(FilFundsService.class);
        traOtherService = SpringContextHolder.getBean(TraOtherService.class);
        traBusinessService = SpringContextHolder.getBean(TraBusinessService.class);
        busProcService = SpringContextHolder.getBean(BusProcService.class);
        filBusinessService = SpringContextHolder.getBean(FilBusinessService.class);
        filOtherService = SpringContextHolder.getBean(FilOtherService.class);
        JSONObject json = JSONObject.parseObject(data);
        Object formData = json.get("data");
        String type = (String) json.get("tranType");
        if (formData==null){
            String jsonString = json.getString("pid");
            Integer pid = Integer.parseInt(jsonString);
            String memo = json.getString("memo");
            //单笔交易
            processData(type,pid,json);
        }else {
            //集中审议
            String pids = (String) json.get("pids");
            String[] pidStrArr = pids.split(",");
            for (String pidStr : pidStrArr) {
                processData(type,Integer.valueOf(pidStr),json);
            }
        }




    }

    /**
     * 业务数据处理
     * @param type
     * @param pid
     * @param json
     */
    private void processData(String type, Integer pid, JSONObject json) {
        String procInstId = json.getString("procInstId");
        String pids = (String) json.get("pids");
        if ("01".equals(type)) {
            TraInvestment traInvestment = traInvestmentService.get(pid);

            traInvestment.setStatus("6");
            traInvestment.setStatusComment("审批通过");

            traInvestment.setIsEffective("1");
            traInvestment.setModifiedTime(new Date());
            //审批金额设置为当前金额
            //traInvestment.setApproveTradeAmount(traInvestment.getTradeAmount().toString());

            traInvestmentService.updateSelective(traInvestment);
            FilInvestment filInvestment = new FilInvestment();
            try {
                BeanUtils.copyProperties(traInvestment, filInvestment);
                filInvestment.setPid(null);
                filInvestment.setFlagPid(traInvestment.getPid().toString());
                filInvestment.setFilNum(json.getString("filNum"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            filInvestmentService.insertSelective(filInvestment);
            //插入流程业务数据中间表
            filInvestment = filInvestmentService.get(filInvestment);
            int filPid = filInvestment.getPid();
            saveMidBusProc(traInvestment.getTenantId(),procInstId,pid.toString(),String.valueOf(filPid),type,busProcService,pids);
        } else if ("04".equals(type)) {
            TraInterest traInterest = traInterestService.get(pid);

            traInterest.setStatus("6");
            traInterest.setStatusComment("审批通过");

            traInterest.setIsEffective("1");
            traInterest.setModifiedTime(new Date());
            //审批金额设置为当前金额
            //traInterest.setApproveTradeAmount(traInterest.getTradeAmount().toString());
            traInterestService.updateSelective(traInterest);
            FilInterest filInterest = new FilInterest();
            try {
                BeanUtils.copyProperties(traInterest, filInterest);
                filInterest.setPid(null);
                filInterest.setFlagPid(traInterest.getPid().toString());
                filInterest.setFilNum(json.getString("filNum"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            filInterestService.insertSelective(filInterest);
            //插入流程业务数据中间表
            filInterest = filInterestService.get(filInterest);
            int filPid = filInterest.getPid();
            saveMidBusProc(traInterest.getTenantId(),procInstId,pid.toString(),String.valueOf(filPid),type,busProcService,pids);
        }else if ("03".equals(type)) {
            TraBusiness traBusiness = traBusinessService.get(pid);

            traBusiness.setStatus("6");
            traBusiness.setStatusComment("审批通过");

            traBusiness.setIsEffective("1");
            traBusiness.setModifiedTime(new Date());
            //审批金额设置为当前金额
            //traBusiness.setApproveTradeAmount(traBusiness.getTradeAmount().toString());
            traBusinessService.updateSelective(traBusiness);
            FilBusiness filBusiness = new FilBusiness();
            try {
                BeanUtils.copyProperties(traBusiness, filBusiness);
                filBusiness.setPid(null);
                filBusiness.setFlagPid(traBusiness.getPid().toString());
                filBusiness.setFilNum(json.getString("filNum"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            filBusinessService.insertSelective(filBusiness);
            //插入流程业务数据中间表
            filBusiness = filBusinessService.get(filBusiness);
            int filPid = filBusiness.getPid();
            saveMidBusProc(traBusiness.getTenantId(),procInstId,pid.toString(),String.valueOf(filPid),type,busProcService,pids);
        }  else if ("05".equals(type)) {
            TraService traService = traServiceService.get(pid);

            traService.setStatus("6");
            traService.setStatusComment("审批通过");

            traService.setIsEffective("1");
            traService.setModifiedTime(new Date());
            //审批金额设置为当前金额
            // traService.setApproveTradeAmount(traService.getTradeAmount().toString());
            traServiceService.updateSelective(traService);
            FilService filService = new FilService();
            try {
                BeanUtils.copyProperties(traService, filService);
                filService.setPid(null);
                filService.setFlagPid(traService.getPid().toString());
                filService.setFilNum(json.getString("filNum"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            filServiceService.insertSelective(filService);
            //插入流程业务数据中间表
            filService = filServiceService.get(filService);
            int filPid = filService.getPid();
            saveMidBusProc(traService.getTenantId(),procInstId,pid.toString(),String.valueOf(filPid),type,busProcService,pids);
        }else if ("06".equals(type)) {
            TraOther traOther = traOtherService.get(pid);

            traOther.setStatus("6");
            traOther.setStatusComment("审批通过");

            traOther.setIsEffective("1");
            traOther.setModifiedTime(new Date());
            //审批金额设置为当前金额
            //traOther.setStatusComment("审批通过");
            traOtherService.updateSelective(traOther);
            FilOther filOther = new FilOther();
            try {
                BeanUtils.copyProperties(traOther, filOther);
                filOther.setPid(null);
                filOther.setFlagPid(traOther.getPid().toString());
                filOther.setFilNum(json.getString("filNum"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            filOtherService.insertSelective(filOther);
            //插入流程业务数据中间表
            filOther = filOtherService.get(filOther);
            int filPid = filOther.getPid();
            saveMidBusProc(traOther.getTenantId(),procInstId,pid.toString(),String.valueOf(filPid),type,busProcService,pids);
        } else if ("funds".equals(type)) {
            // BigDecimal tradeAmount = json.getBigDecimal("tradeAmount");
            TraFunds traFunds = traFundsService.get(pid);

            traFunds.setStatus("6");
            traFunds.setStatusComment("审批通过");

            // traFunds.setStatusComment(memo);
            // traFunds.setApproveTradeAmount(tradeAmount);
            traFunds.setIsEffective("1");
            traFundsService.updateSelective(traFunds);
            FilFunds filFunds = new FilFunds();
            try {
                BeanUtils.copyProperties(traFunds, filFunds);
                filFunds.setFilNum(json.getString("filNum"));
                filFunds.setPid(null);
                filFunds.setFlagPid(traFunds.getPid().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            filFundsService.insertSelective(filFunds);
            //插入流程业务数据中间表
            filFunds = filFundsService.get(filFunds);
            int filPid = filFunds.getPid();
            saveMidBusProc(traFunds.getTenantId(),procInstId,pid.toString(),String.valueOf(filPid),type,busProcService,pids);
        }
    }

    /**
     * 保存中间表
     * @param procInstId
     * @param pid
     * @param filPid
     * @param type
     */
    public void saveMidBusProc(String tenantId, String procInstId, String pid, String filPid, String type, BusProcService busProcService,String pids){
        BusProc busProc = new BusProc();
        busProc.setProcInstId(procInstId);
        busProc.setFlagPid(pid);
        busProc.setFilPid(filPid);
        busProc.setBusType(type);
        busProc.setTenantId(tenantId);
        busProc.setFocusApprovePid(pids);
        busProcService.insertSelective(busProc);
    }
}