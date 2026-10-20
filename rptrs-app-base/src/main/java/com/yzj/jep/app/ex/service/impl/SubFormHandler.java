package com.yzj.jep.app.ex.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.fil.service.FilFundsService;
import com.yzj.jep.app.ins.service.TraInvestmentService;
import com.yzj.jep.app.sub.model.*;
import com.yzj.jep.app.sub.service.SublogService;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.sys.model.Tenant;
import com.yzj.jep.sys.service.TenantService;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.api.code.ClientCodes;
import com.yzj.jep.workflow.client.ProcessFormHandler;
import com.yzj.jep.workflow.client.annotation.FormHandler;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;


//流程编码


@FormHandler("YZJ_SUB001")
public class SubFormHandler implements ProcessFormHandler {


    TenantService tenantService;
    SublogService sublogService;

    @Override
    public ModelAndView processForm(String nodekey, String data, String opt, ModelAndView model)
            throws WorkflowException {
        tenantService = SpringContextHolder.getBean(TenantService.class);
        sublogService = SpringContextHolder.getBean(SublogService.class);

        JSONObject jsonObject = JSONObject.parseObject(data);
        if (ClientCodes.OPT_VIEW.equals(opt)) {
            if ("01".equals(jsonObject.getString("subType"))) {
                UserProfile profile = UserProfileUtils.getUserProfile();
                String tenantId = profile.getTenantId();
                if (tenantId != null || tenantId != "") {
                    Tenant tenant = new Tenant();
                    tenant.setCode(tenantId);
                    Tenant tenant1 = tenantService.get(tenant);
                    model.addObject("subOrg", tenant1.getName());
                }
                Sublog subLog = sublogService.get(jsonObject.getInteger("pid"));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String strDate = sdf.format(subLog.getMaxPercentInNetAssets());
                model.addObject("date", strDate);
                model.addObject("operator", subLog.getOperatorName());
                model.addObject("pid", subLog.getPid());
//                InsInvestment insInvestment = JSON.parseObject(data, InsInvestment.class);
//                model.addObject("insInvestment", insInvestment);
//                model.addObject("opt", "view");
                model.setViewName("ex/insInvestmentSubmit");
            } else if ("02".equals(jsonObject.getString("subType"))) {
                UserProfile profile = UserProfileUtils.getUserProfile();
                String tenantId = profile.getTenantId();
                if (tenantId != null || tenantId != "") {
                    Tenant tenant = new Tenant();
                    tenant.setCode(tenantId);
                    Tenant tenant1 = tenantService.get(tenant);
                    model.addObject("subOrg", tenant1.getName());
                }
                Sublog subLog = sublogService.get(jsonObject.getInteger("pid"));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String strDate = sdf.format(subLog.getMaxPercentInNetAssets());
                model.addObject("date", strDate);
                model.addObject("operator", subLog.getOperatorName());
                model.addObject("pid", subLog.getPid());
//                InsFunds insFunds = JSON.parseObject(data, InsFunds.class);
//                model.addObject("insFunds", insFunds);
                model.addObject("opt", "view");
                model.setViewName("ex/insFundsViewSubmit");
            } else if ("03".equals(jsonObject.getString("subType"))) {
                UserProfile profile = UserProfileUtils.getUserProfile();
                String tenantId = profile.getTenantId();
                if (tenantId != null || tenantId != "") {
                    Tenant tenant = new Tenant();
                    tenant.setCode(tenantId);
                    Tenant tenant1 = tenantService.get(tenant);
                    model.addObject("subOrg", tenant1.getName());
                }
                Sublog subLog = sublogService.get(jsonObject.getInteger("pid"));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String strDate = sdf.format(subLog.getMaxPercentInNetAssets());
                model.addObject("date", strDate);
                model.addObject("operator", subLog.getOperatorName());
                model.addObject("pid", subLog.getPid());
//                InsBusiness insBusiness = JSON.parseObject(data,InsBusiness.class);
//                model.addObject("insBusiness", insBusiness);
//                model.addObject("opt", "view");
                model.setViewName("ex/insBusinessViewSubmit");
            } else if ("04".equals(jsonObject.getString("subType"))) {
                UserProfile profile = UserProfileUtils.getUserProfile();
                String tenantId = profile.getTenantId();
                if (tenantId != null || tenantId != "") {
                    Tenant tenant = new Tenant();
                    tenant.setCode(tenantId);
                    Tenant tenant1 = tenantService.get(tenant);
                    model.addObject("subOrg", tenant1.getName());
                }
                Sublog subLog = sublogService.get(jsonObject.getInteger("pid"));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String strDate = sdf.format(subLog.getMaxPercentInNetAssets());
                model.addObject("date", strDate);
                model.addObject("operator", subLog.getOperatorName());
                model.addObject("pid", subLog.getPid());
//                InsInterest insInterest = JSON.parseObject(data, InsInterest.class);
//                model.addObject("insInterest", insInterest);
//                model.addObject("opt", "view");
                model.setViewName("ex/insInterestViewSubmit");
            } else if ("05".equals(jsonObject.getString("subType"))) {
                UserProfile profile = UserProfileUtils.getUserProfile();
                String tenantId = profile.getTenantId();
                if (tenantId != null || tenantId != "") {
                    Tenant tenant = new Tenant();
                    tenant.setCode(tenantId);
                    Tenant tenant1 = tenantService.get(tenant);
                    model.addObject("subOrg", tenant1.getName());
                }
                Sublog subLog = sublogService.get(jsonObject.getInteger("pid"));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String strDate = sdf.format(subLog.getMaxPercentInNetAssets());
                model.addObject("date", strDate);
                model.addObject("operator", subLog.getOperatorName());
                model.addObject("pid", subLog.getPid());
//                InsService insService = JSON.parseObject(data, InsService.class);
//                model.addObject("insService", insService);
//                model.addObject("opt", "view");
                model.setViewName("ex/insServiceViewSubmit");
            } else if ("0".equals(jsonObject.getString("subType"))) {
                UserProfile profile = UserProfileUtils.getUserProfile();
                String tenantId = profile.getTenantId();
                if (tenantId != null || tenantId != "") {
                    Tenant tenant = new Tenant();
                    tenant.setCode(tenantId);
                    Tenant tenant1 = tenantService.get(tenant);
                    model.addObject("subOrg", tenant1.getName());
                }
                Sublog subLog = sublogService.get(jsonObject.getInteger("pid"));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");    //格式化规则
                String strDate = sdf.format(subLog.getMaxPercentInNetAssets()); //格式化成yyyy-MM-dd格式的时间字符串
                model.addObject("date", strDate);
                model.addObject("operator", subLog.getOperatorName());
                model.addObject("pid", subLog.getPid());
                model.setViewName("ex/organViewSubmit");
            } else if ("1".equals(jsonObject.getString("subType"))) {
                UserProfile profile = UserProfileUtils.getUserProfile();
                String tenantId = profile.getTenantId();
                if (tenantId != null || tenantId != "") {
                    Tenant tenant = new Tenant();
                    tenant.setCode(tenantId);
                    Tenant tenant1 = tenantService.get(tenant);
                    model.addObject("subOrg", tenant1.getName());
                }
                Sublog subLog = sublogService.get(jsonObject.getInteger("pid"));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");    //格式化规则
                String strDate = sdf.format(subLog.getMaxPercentInNetAssets()); //格式化成yyyy-MM-dd格式的时间字符串
                model.addObject("date", strDate);
                model.addObject("operator", subLog.getOperatorName());
                model.addObject("pid", subLog.getPid());
                model.setViewName("ex/personViewSubmit");
            } else if ("2".equals(jsonObject.getString("subType"))) {
                UserProfile profile = UserProfileUtils.getUserProfile();
                String tenantId = profile.getTenantId();
                if (tenantId != null || tenantId != "") {
                    Tenant tenant = new Tenant();
                    tenant.setCode(tenantId);
                    Tenant tenant1 = tenantService.get(tenant);
                    model.addObject("subOrg", tenant1.getName());
                }
                Sublog subLog = sublogService.get(jsonObject.getInteger("pid"));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");    //格式化规则
                String strDate = sdf.format(subLog.getMaxPercentInNetAssets()); //格式化成yyyy-MM-dd格式的时间字符串
                model.addObject("date", strDate);
                model.addObject("operator", subLog.getOperatorName());
                model.addObject("pid", subLog.getPid());
                model.setViewName("ex/relatedRelationViewSubmit");
            } else if ("3".equals(jsonObject.getString("subType"))) {
                UserProfile profile = UserProfileUtils.getUserProfile();
                String tenantId = profile.getTenantId();
                if (tenantId != null || tenantId != "") {
                    Tenant tenant = new Tenant();
                    tenant.setCode(tenantId);
                    Tenant tenant1 = tenantService.get(tenant);
                    model.addObject("subOrg", tenant1.getName());
                }
                Sublog subLog = sublogService.get(jsonObject.getInteger("pid"));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");    //格式化规则
                String strDate = sdf.format(subLog.getMaxPercentInNetAssets()); //格式化成yyyy-MM-dd格式的时间字符串
                model.addObject("date", strDate);
                model.addObject("operator", subLog.getOperatorName());
                model.addObject("pid", subLog.getPid());
                model.setViewName("ex/sublogViewSubmit");
            }

        }

        return model;
    }

}
