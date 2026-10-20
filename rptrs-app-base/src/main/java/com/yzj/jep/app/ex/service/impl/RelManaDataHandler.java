package com.yzj.jep.app.ex.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.bus.model.Rel;
import com.yzj.jep.app.bus.service.RelService;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.client.ProcessDataHandler;
import com.yzj.jep.workflow.client.annotation.DataHandler;

import java.util.List;

@DataHandler("YZJ_REL_MANA")
public class RelManaDataHandler implements ProcessDataHandler {

    private RelService relService;

    @Override
    public void handleData(String data) throws WorkflowException {
        relService = SpringContextHolder.getBean(RelService.class);
        JSONObject json = JSONObject.parseObject(data);
        //关联关系pid
//        Integer relPid = (Integer) json.get("relPid");
        //先找到对应的关系列表
        Integer toPid = (Integer) json.get("toPid");
        String toType = (String) json.get("toType");
        Integer fromPid = (Integer) json.get("fromPid");
        String fromType = (String) json.get("fromType");
        List<Filter> filterForPsnPid = new Filter.Builder().eq("toPid", toPid).eq("delflag", 1).eq("toType", toType).eq("fromPid", fromPid).eq("fromType", fromType).eq("isShow", 0).build();
        try {
            List<Rel> rels = relService.select(filterForPsnPid);
            if (rels.size() > 0) {
                Rel rel = rels.get(rels.size() - 1);
                //将关联关系显示为 可展示
                rel.setIsShow("1");
                relService.updateSelective(rel);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
