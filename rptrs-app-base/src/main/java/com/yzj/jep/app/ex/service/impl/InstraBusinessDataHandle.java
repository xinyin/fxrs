package com.yzj.jep.app.ex.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.bus.mapper.DelRelMapper;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.bus.service.RelService;
import com.yzj.jep.app.fil.model.FilBusiness;
import com.yzj.jep.app.fil.service.FilBusinessService;
import com.yzj.jep.app.ins.model.TraBusiness;
import com.yzj.jep.app.ins.service.TraBusinessService;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.client.ProcessDataHandler;
import com.yzj.jep.workflow.client.annotation.DataHandler;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DataHandler("YZJ_INS_TRA_BUSINESS")
public class InstraBusinessDataHandle implements ProcessDataHandler {
    TraBusinessService traBusinessService;
    FilBusinessService filBusinessService;

    //
    @Override
    public void handleData(String data) throws WorkflowException {
        traBusinessService = SpringContextHolder.getBean(TraBusinessService.class);
        filBusinessService = SpringContextHolder.getBean(FilBusinessService.class);
        JSONObject json = JSONObject.parseObject(data);
        Map<String, Object> tips = new HashMap<String, Object>();
        int pid = (int) json.get("pid");
        TraBusiness traBusiness = traBusinessService.get(pid);
        if (traBusiness != null) {
            //3 状态由审批中改为 已归档 6
            traBusiness.setStatus("6");
            traBusiness.setStatusComment("");
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
            filBusinessService.insert(filBusiness);
        }
    }
}
