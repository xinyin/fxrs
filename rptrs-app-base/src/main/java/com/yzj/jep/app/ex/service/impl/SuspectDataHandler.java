package com.yzj.jep.app.ex.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.client.ProcessDataHandler;
import com.yzj.jep.workflow.client.annotation.DataHandler;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Map;

//业务逻辑

@DataHandler("YZJ_SUSPECT")
public class SuspectDataHandler implements ProcessDataHandler {

    private RelOrgSuspectService relOrgSuspectService;
    private RelPsnSuspectService relPsnSuspectService;

    private RelOrgService relOrgService;
    private RelPsnService relPsnService;

    private RelService relService;

    @Override
    public void handleData(String data) throws WorkflowException {

        relOrgSuspectService = SpringContextHolder.getBean(RelOrgSuspectService.class);
        relPsnSuspectService = SpringContextHolder.getBean(RelPsnSuspectService.class);

        relOrgService = SpringContextHolder.getBean(RelOrgService.class);
        relPsnService = SpringContextHolder.getBean(RelPsnService.class);
        relService = SpringContextHolder.getBean(RelService.class);
        JSONObject json = JSONObject.parseObject(data);
        Integer pid = (Integer) json.get("pid");
        //流程type by Wrf
        String proessType = (String) json.get("processType");
        //认定结果  0是非关联方 1是关联方
        String isSuspect = (String) json.get("isSuspect");
        if ("CheckByPsn".equals(proessType)) {
            RelPsnSuspect relPsnSuspect = relPsnSuspectService.get(pid);
            if ("1".equals(isSuspect)) {
                //先获取对应的关系列表  并将其状态进行改变
                List<Filter> filters = new Filter.Builder().eq("toPid", pid).eq("toType", "SUSPSN").eq("isShow", 0).build();
                List<Rel> rels = relService.select(filters);
                //认定成功
                RelPsn relPsn = new RelPsn();
                BeanUtils.copyProperties(relPsnSuspect, relPsn);
                //设置几处默认值
                //非黑名单
                relPsn.setIsBlack("0");
                //非删除状态
                relPsn.setDelflag("1");

                Map<String, Object> maps = relPsnSuspectService.replacePsn(pid, relPsn, rels);
                if ("true".equals(maps.get("result"))) {
                    System.out.println("疑似自然人审批完成");
                } else {
                    System.out.println("疑似自然人审批失败");
                }
            } else if ("0".equals(isSuspect)) {
                relPsnSuspectService.deleteByPrimaryKey(pid);
                System.out.println("疑似自然人审批完成:不是关联方");
            }
            //审批通过
            relPsnSuspect.setStatus("6");
            relPsnSuspectService.updateSelective(relPsnSuspect);
        } else if ("CheckByOrg".equals(proessType)) {
            RelOrgSuspect relOrgSuspect = relOrgSuspectService.get(pid);
            if ("1".equals(isSuspect)) {
                //先获取对应的关系列表  并将其状态进行改变
                List<Filter> filters = new Filter.Builder().eq("toPid", pid).eq("toType", "SUSORG").build();
//				.eq("isShow", 0)
                List<Rel> rels = relService.select(filters);
                //认定成功
                RelOrg relOrg = new RelOrg();
//				relOrgSuspect.setPid(null);
                relOrgSuspect.setApprovalStatus("1");
                BeanUtils.copyProperties(relOrgSuspect, relOrg);
                relOrg.setPid(null);
                //设置几处默认值
                //pid
//				Integer maxPid = relOrgSuspectService.getMaxId("bus_rel_psn");
//				relPsn.setPid(maxPid+1);
                //非黑名单
                relOrg.setIsBlack("0");
                //非删除状态
                relOrg.setDelflag("1");
                Map<String, Object> maps = relOrgSuspectService.replaceOrg(pid, relOrg, rels);
                if ("true".equals(maps.get("result"))) {
                    System.out.println("疑似机构审批完成");
                } else {
                    System.out.println("疑似机构审批失败");
                }
            } else if ("0".equals(isSuspect)) {
                relOrgSuspectService.deleteByPrimaryKey(pid);
                System.out.println("疑似机构审批完成:不是关联方");
            }

            relOrgSuspect.setApprovalStatus("6");
            relOrgSuspectService.updateSelective(relOrgSuspect);
        }
    }

}