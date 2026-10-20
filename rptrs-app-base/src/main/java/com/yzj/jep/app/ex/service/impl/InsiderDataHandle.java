package com.yzj.jep.app.ex.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.bus.mapper.DelRelMapper;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.bus.service.RelService;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.client.ProcessDataHandler;
import com.yzj.jep.workflow.client.annotation.DataHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DataHandler("YZJ_REL_INSIDER")
public class InsiderDataHandle implements ProcessDataHandler {
    RelPsnService relPsnService;
    RelOrgService relOrgService;
    RelService relService;
    private DelRelMapper delRelMapper;

    @Override
    public void handleData(String data) throws WorkflowException {
        relPsnService = SpringContextHolder.getBean(RelPsnService.class);
        relOrgService = SpringContextHolder.getBean(RelOrgService.class);
        relService = SpringContextHolder.getBean(RelService.class);
        delRelMapper = SpringContextHolder.getBean(DelRelMapper.class);
        JSONObject json = JSONObject.parseObject(data);
        Map<String, Object> tips = new HashMap<String, Object>();
        int pid = (int) json.get("pid");

        //1 查询出所有的关系自然人  新增改为归档  归档-删除 改为删除
        //查询自然人的关系
        List<RelOrgVo> tzOrgPsn = relOrgService.getTZOrgPsn(String.valueOf(pid));
        List<RelPsnVo> relPsnVos = relOrgService.getOrgPsn(String.valueOf(pid));
        RelPsnVo relPsnVo;
        RelOrgVo relOrgVo;
        Rel rel;
        if (relPsnVos.size() > 0) {
            for (int i = 0; i < relPsnVos.size(); i++) {
                relPsnVo = relPsnVos.get(i);
                rel = relService.get(relPsnVo.getRelId());
                if ("01".equals(relPsnVo.getRelStatus())) {
                    rel.setRelStatus("00");
                } else if ("03".equals(relPsnVo.getRelStatus())) {
//                        relService.removeRel();
                    rel = relService.get(relPsnVo.getRelId());
                    rel.setDelflag("0");
                    DelRel delRel = new DelRel();
                    delRel.setName(relPsnVo.getName());
                    delRel.setDomesticOrForeign(relPsnVo.getDomesticOrForeign());
                    delRel.setIdentifyType(relPsnVo.getIdentifyType());
                    delRel.setIdentifyNumber(relPsnVo.getIdentifyNumber());
                    delRel.setMgerOrg(relPsnVo.getMgerOrg());
                    delRel.setRegulator(relPsnVo.getRegulator());
                    delRel.setRelPid(relPsnVo.getPid());
                    delRel.setType("PSN");
                    delRelMapper.insert(delRel);
                }
                relService.updateSelective(rel);
            }
        }
        //2 查询出所有的关系机构    新增改为归档  归档-删除 改为删除
        if (tzOrgPsn.size() > 0) {
            for (int i = 0; i < tzOrgPsn.size(); i++) {
                relOrgVo = tzOrgPsn.get(i);
                rel = relService.get(relOrgVo.getRelId());
                if ("01".equals(relOrgVo.getRelStatus())) {
                    rel.setRelStatus("00");
                } else if ("03".equals(relOrgVo.getRelStatus())) {
//                        relService.removeRel();
                    rel.setDelflag("0");
                    DelRel delRel = new DelRel();
                    delRel.setName(relOrgVo.getOrgName());
                    delRel.setDomesticOrForeign(relOrgVo.getDomesticOrForeign());
                    delRel.setIdentifyType(relOrgVo.getIdentifyType());
                    delRel.setIdentifyNumber(relOrgVo.getUniscCode());
                    delRel.setMgerOrg(relOrgVo.getMgerOrg());
                    delRel.setRegulator(relOrgVo.getRegulator());
                    delRel.setType("ORG");
                    delRel.setRelPid(relOrgVo.getPid());
                    delRelMapper.insert(delRel);
                }
                relService.updateSelective(rel);
            }
        }

        //3 状态由审批中改为 已归档 6
        RelOrg relOrg = relOrgService.get(pid);
        relOrg.setStatusComment("");
        List<RelOrgVo> tzOrgPsn1 = relOrgService.getTZOrgPsn(String.valueOf(pid));
        List<RelPsnVo> relPsnVos1 = relOrgService.getOrgPsn(String.valueOf(pid));
        if (tzOrgPsn1.size() <= 0 && relPsnVos1.size() <= 0) {
            relOrg.setApprovalStatus("1");
        } else {
            relOrg.setApprovalStatus("6");
        }
        relOrgService.updateSelective(relOrg);
    }
}
