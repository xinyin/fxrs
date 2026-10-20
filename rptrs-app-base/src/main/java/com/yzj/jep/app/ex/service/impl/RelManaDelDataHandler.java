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

@DataHandler("YZJ_REL_MANA_DEL")
public class RelManaDelDataHandler implements ProcessDataHandler {
    private RelService relService;

    @Override
    public void handleData(String data) throws WorkflowException {
        relService = SpringContextHolder.getBean(RelService.class);
        JSONObject json = JSONObject.parseObject(data);
        //关联关系pid
//        Integer relPid = (Integer) json.get("relPid");
        //先找到对应的关系列表
//        String toPid=(String) json.get("toPid");
//        String toType=(String) json.get("toType");
        String fromPid = (String) json.get("fromPid");
        String fromType = (String) json.get("fromType");
        String relPid = (String) json.get("relPid");
        List<Filter> filterForPsnPid = new Filter.Builder().eq("delflag", 1).eq("fromPid", fromPid).eq("fromType", fromType)
                .eq("isShow", 1).eq("pid", relPid).build();
        try {
//            List<Rel> rels=relService.select(filterForPsnPid);
//            List<Rel> rels=relService.select(filterForPsnPid);
            Rel rel = relService.get(Integer.parseInt(relPid));
            //将关联关系显示为 不可展示
            rel.setIsShow("0");
            rel.setDelflag("0");
            relService.updateSelective(rel);
            System.out.println("删除成功");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
