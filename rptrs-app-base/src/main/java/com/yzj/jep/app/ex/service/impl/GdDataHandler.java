package com.yzj.jep.app.ex.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.yzj.jep.app.bus.mapper.DelRelMapper;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.client.ProcessDataHandler;
import com.yzj.jep.workflow.client.annotation.DataHandler;
import io.swagger.models.auth.In;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//业务逻辑

@DataHandler("gd")
public class GdDataHandler implements ProcessDataHandler {
    RelService relService;
    RelPsnService relPsnService;
    RelOrgService relOrgService;
    GdTraZrService gdTraZrService;
    GdTraDjService gdTraDjService;
    GdTraZyService gdTraZyService;
    DelRelMapper delRelMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handleData(String data) throws WorkflowException {

        relService = SpringContextHolder.getBean(RelService.class);
        delRelMapper = SpringContextHolder.getBean(DelRelMapper.class);
        relPsnService = SpringContextHolder.getBean(RelPsnService.class);
        relOrgService = SpringContextHolder.getBean(RelOrgService.class);
        gdTraZrService = SpringContextHolder.getBean(GdTraZrService.class);
        gdTraDjService = SpringContextHolder.getBean(GdTraDjService.class);
        gdTraZyService = SpringContextHolder.getBean(GdTraZyService.class);
        JSONObject json = JSONObject.parseObject(data);
        String type = (String) json.get("type");

        //流程type by Wrf
        if ("open".equals(type)) {
            String gdType = (String) json.get("gdType");
            if ("PSN".equals(gdType)) {
                psnOpenHandler(data);
            } else {
                orgOpenHandler(data);
            }
        }
        if ("xh".equals(type)) {
            String relType = (String) json.get("relType");
            if ("PSN".equals(relType)) {
                RelPsn relPsn = new RelPsn();
                RelPsn psn = JSON.parseObject(data, RelPsn.class);
                relPsn.setIsShareholder("0");
                relPsn.setPid(psn.getPid());
                String[] strs = {"holeShareAmount", "openBank", "tradeAccount", "shareholdingProportion", "frozenShareAmount", "pledgedShareAmount", "shareholdersCode", "isShareholder", "inTime", "inPrice", "status", "statusComment"};
                relPsn.setDjStatus("6");
                relPsn.setStatusComment("审批通过");
                relPsnService.update(relPsn, strs);
            }
            if ("ORG".equals(relType)) {
                RelOrg relOrg = new RelOrg();
                RelOrg org = JSON.parseObject(data, RelOrg.class);
                relOrg.setPid(org.getPid());
                relOrg.setIsShareholder("0");
                String[] strs = {"holeShareAmount", "openBank", "tradeAccount", "shareholdingProportion", "frozenShare", "pledgedShare", "shareholdersCode", "isShareholder", "status", "statusComment"};
                relOrg.setDjStatus("6");
                relOrg.setStatusComment("审批通过");
                relOrgService.update(relOrg, strs);
            }
        }
        if ("dj".equals(type)) {
            String relType = (String) json.get("relType");
            if ("PSN".equals(relType)) {
                RelPsn psn = JSON.parseObject(data, RelPsn.class);
                psn.setDjStatus("1");
                psn.setStatus("6");
                psn.setStatusComment("审批通过");
                relPsnService.updateSelective(psn);
            }
            if ("ORG".equals(relType)) {
                RelOrg org = JSON.parseObject(data, RelOrg.class);
                org.setDjStatus("1");
                org.setStatus("6");
                org.setStatusComment("审批通过");
                relOrgService.updateSelective(org);
            }
        }
        if ("jd".equals(type)) {
            String relType = (String) json.get("relType");
            if ("PSN".equals(relType)) {
                RelPsn psn = JSON.parseObject(data, RelPsn.class);
                psn.setDjStatus("0");
                psn.setStatus("6");
                psn.setStatusComment("审批通过");
                relPsnService.updateSelective(psn);
            }
            if ("ORG".equals(relType)) {
                RelOrg org = JSON.parseObject(data, RelOrg.class);
                org.setDjStatus("0");
                org.setStatus("6");
                org.setStatusComment("审批通过");
                relOrgService.updateSelective(org);
            }
        }
        if ("black".equals(type)) {
            String relType = (String) json.get("relType");
            if ("PSN".equals(relType)) {
                RelPsn psn = JSON.parseObject(data, RelPsn.class);
                if ("0".equals(psn.getIsBlack())) {
                    psn.setIsBlack("1");
                }
                if ("1".equals(psn.getIsBlack())) {
                    psn.setIsBlack("0");
                }

                psn.setStatus("6");
                psn.setStatusComment("审批通过");
                relPsnService.updateSelective(psn);
            }
            if ("ORG".equals(relType)) {
                RelOrg org = JSON.parseObject(data, RelOrg.class);
                if ("0".equals(org.getIsBlack())) {
                    org.setIsBlack("1");
                }
                if ("1".equals(org.getIsBlack())) {
                    org.setIsBlack("0");
                }
                org.setStatus("6");
                org.setStatusComment("审批通过");
                relOrgService.updateSelective(org);
            }
        }
        if ("zrTra".equals(type) || "jcTra".equals(type) || "giftTra".equals(type)) {
            stockChange(data);
        }
        if ("djTra".equals(type) || "jdTra".equals(type)) {
            stockFreeze(data);
        }
        if ("zyTra".equals(type) || "jyTra".equals(type)) {
            equityPledge(data);
        }
        if ("pfTra".equals(type)) {
            JSONArray pidArray = json.getJSONArray("pid");
            Integer pid = pidArray.getInteger(0);
            GdTraPfService gdTraPfService = SpringContextHolder.getBean(GdTraPfService.class);
            GdTraPf gdTraPf = new GdTraPf();
            gdTraPf.setPid(pid);
            gdTraPf.setStatus("6");
            gdTraPfService.updateSelective(gdTraPf);
        }
        if ("export".equals(type)) {
            JSONArray pidArray = json.getJSONArray("pid");
            Integer pid = pidArray.getInteger(0);
            GdExportFileService gdExportFileService = SpringContextHolder.getBean(GdExportFileService.class);
            GdExportFile gdExportFile = new GdExportFile();
            gdExportFile.setPid(pid);
            gdExportFile.setStatus("6");
            gdExportFileService.updateSelective(gdExportFile);
        }
    }

    public void equityPledge(String data) {
        GdTraZy gdTraZy = JSON.parseObject(data, GdTraZy.class);
        String operationType = gdTraZy.getOperationType();
        Integer operationAmount = Integer.parseInt(gdTraZy.getOperationAmount());
        String shareholdersType = gdTraZy.getShareholdersType();
        String shareholdersCode = gdTraZy.getShareholdersCode();
        String shareholdersName = gdTraZy.getShareholdersName();
        if ("PSN".equals(shareholdersType)) {
            RelPsn relPsn = new RelPsn();
            relPsn.setShareholdersCode(shareholdersCode);
            relPsn.setName(shareholdersName);
            RelPsn psn = relPsnService.get(relPsn);
            Integer pledgedShareAmount = psn.getPledgedShareAmount() == null ? 0 : Integer.parseInt(psn.getPledgedShareAmount());
            if ("1".equals(operationType)) {
                pledgedShareAmount = pledgedShareAmount + operationAmount;
                psn.setPledgedShareAmount(String.valueOf(pledgedShareAmount));
                relPsnService.updateSelective(psn);
            } else {
                pledgedShareAmount = pledgedShareAmount - operationAmount;
                psn.setPledgedShareAmount(String.valueOf(pledgedShareAmount));
                relPsnService.updateSelective(psn);
                String pledgedContract = gdTraZy.getPledgedContract();
                GdTraZy gdTraZy1 = new GdTraZy();
                gdTraZy1.setPledgedContract(pledgedContract);
                gdTraZy1.setOperationType("1");
                GdTraZy traZy = gdTraZyService.get(gdTraZy1);
                String unpledgedAmount = traZy.getUnpledgedAmount();
                Integer amount = unpledgedAmount == null ? 0 : Integer.parseInt(unpledgedAmount);
                amount = amount + operationAmount;
                traZy.setUnpledgedAmount(String.valueOf(amount));
                gdTraZyService.updateSelective(traZy);
            }
        } else {
            RelOrg relOrg = new RelOrg();
            relOrg.setShareholdersCode(shareholdersCode);
            relOrg.setOrgName(shareholdersName);
            RelOrg org = relOrgService.get(relOrg);
            Integer pledgedShareAmount = org.getPledgedShare() == null ? 0 : Integer.parseInt(org.getPledgedShare());
            if ("1".equals(operationType)) {
                pledgedShareAmount = pledgedShareAmount + operationAmount;
                org.setPledgedShare(String.valueOf(pledgedShareAmount));
                relOrgService.updateSelective(org);
            } else {
                pledgedShareAmount = pledgedShareAmount - operationAmount;
                org.setPledgedShare(String.valueOf(pledgedShareAmount));
                relOrgService.updateSelective(org);
                String pledgedContract = gdTraZy.getPledgedContract();
                GdTraZy gdTraZy1 = new GdTraZy();
                gdTraZy1.setPledgedContract(pledgedContract);
                gdTraZy1.setOperationType("1");
                GdTraZy traZy = gdTraZyService.get(gdTraZy1);
                String unpledgedAmount = traZy.getUnpledgedAmount();
                Integer amount = unpledgedAmount == null ? 0 : Integer.parseInt(unpledgedAmount);
                amount = amount + operationAmount;
                traZy.setUnpledgedAmount(String.valueOf(amount));
                gdTraZyService.updateSelective(traZy);
            }
        }

        GdTraZy traZy = new GdTraZy();
        traZy.setPid(gdTraZy.getPid());
        traZy.setStatus("6");
        gdTraZyService.updateSelective(traZy);

    }

    public void stockFreeze(String data) {
        GdTraDj gdTraDj = JSON.parseObject(data, GdTraDj.class);
        Integer operationAmount = Integer.parseInt(gdTraDj.getOperationAmount());
        String operationType = gdTraDj.getOperationType();
        String shareholdersType = gdTraDj.getShareholdersType();
        String shareholdersCode = gdTraDj.getShareholdersCode();
        String shareholdersName = gdTraDj.getShareholdersName();
        if ("PSN".equals(shareholdersType)) {
            RelPsn relPsn = new RelPsn();
            relPsn.setShareholdersCode(shareholdersCode);
            relPsn.setName(shareholdersName);
            RelPsn psn = relPsnService.get(relPsn);
            Integer frozenShareAmount = psn.getFrozenShareAmount() == null ? 0 : Integer.parseInt(psn.getFrozenShareAmount());
            if ("0".equals(operationType)) {
                frozenShareAmount = frozenShareAmount + operationAmount;
            } else {
                frozenShareAmount = frozenShareAmount - operationAmount;
            }
            psn.setFrozenShareAmount(String.valueOf(frozenShareAmount));
            relPsnService.updateSelective(psn);
            if ("1".equals(operationType)) {
                GdTraDj gdTraDj1 = new GdTraDj();
                gdTraDj1.setOperationType("0");
                gdTraDj1.setFrozenSerial(gdTraDj.getFrozenSerial());
                GdTraDj traDj = gdTraDjService.get(gdTraDj1);
                Integer unfrozenAmount = traDj.getUnfrozenAmount() == null ? 0 : Integer.parseInt(traDj.getUnfrozenAmount());
                unfrozenAmount = unfrozenAmount + operationAmount;
                traDj.setUnfrozenAmount(String.valueOf(unfrozenAmount));
                gdTraDjService.updateSelective(traDj);
            }
        } else {
            RelOrg relOrg = new RelOrg();
            relOrg.setShareholdersCode(shareholdersCode);
            relOrg.setOrgName(shareholdersName);
            RelOrg org = relOrgService.get(relOrg);
            Integer frozenShareAmount = org.getFrozenShare() == null ? 0 : Integer.parseInt(org.getFrozenShare());
            if ("0".equals(operationType)) {
                frozenShareAmount = frozenShareAmount + operationAmount;
            } else {
                frozenShareAmount = frozenShareAmount - operationAmount;
            }
            org.setFrozenShare(String.valueOf(frozenShareAmount));
            relOrgService.updateSelective(org);
            if ("1".equals(operationType)) {
                GdTraDj gdTraDj1 = new GdTraDj();
                gdTraDj1.setOperationType("0");
                gdTraDj1.setFrozenSerial(gdTraDj.getFrozenSerial());
                GdTraDj traDj = gdTraDjService.get(gdTraDj1);
                Integer unfrozenAmount = traDj.getUnfrozenAmount() == null ? 0 : Integer.parseInt(traDj.getUnfrozenAmount());
                unfrozenAmount = unfrozenAmount + operationAmount;
                traDj.setUnfrozenAmount(String.valueOf(unfrozenAmount));
                gdTraDjService.updateSelective(traDj);
            }
        }
        GdTraDj traDj = new GdTraDj();
        traDj.setPid(gdTraDj.getPid());
        traDj.setStatus("6");
        gdTraDjService.updateSelective(traDj);
    }

    public void stockChange(String data) {
        GdTraZr gdTraZr = JSON.parseObject(data, GdTraZr.class);
        Integer operationAmount = Integer.parseInt(gdTraZr.getOperationAmount());
        String fromShareholdersCode = gdTraZr.getFromShareholdersCode();
        String fromShareholdersName = gdTraZr.getFromShareholdersName();
        String fromShareholdersType = gdTraZr.getFromShareholdersType();
        if ("PSN".equals(fromShareholdersType)) {
            RelPsn relPsn = new RelPsn();
            relPsn.setShareholdersCode(fromShareholdersCode);
            relPsn.setName(fromShareholdersName);
            RelPsn psn = relPsnService.get(relPsn);
            Integer holeShareAmount = Integer.parseInt(psn.getHoleShareAmount());
            holeShareAmount = holeShareAmount - operationAmount;
            psn.setHoleShareAmount(String.valueOf(holeShareAmount));
            psn.setShareholdingProportion(gdTraZr.getAfterFromProportion());
            relPsnService.updateSelective(psn);
        } else {
            RelOrg relOrg = new RelOrg();
            relOrg.setOrgName(fromShareholdersName);
            relOrg.setShareholdersCode(fromShareholdersCode);
            RelOrg org = relOrgService.get(relOrg);
            Integer holeShareAmount = Integer.parseInt(org.getHoleShareAmount());
            holeShareAmount = holeShareAmount - operationAmount;
            org.setHoleShareAmount(String.valueOf(holeShareAmount));
            org.setShareholdingProportion(gdTraZr.getAfterFromProportion());
            relOrgService.updateSelective(org);
        }
        String toShareholdersCode = gdTraZr.getToShareholdersCode();
        String toShareholdersName = gdTraZr.getToShareholdersName();
        String toShareholdersType = gdTraZr.getToShareholdersType();
        if ("PSN".equals(toShareholdersType)) {
            RelPsn relPsn = new RelPsn();
            relPsn.setShareholdersCode(toShareholdersCode);
            relPsn.setName(toShareholdersName);
            RelPsn psn = relPsnService.get(relPsn);
            Integer holeShareAmount = Integer.parseInt(psn.getHoleShareAmount());
            holeShareAmount = holeShareAmount + operationAmount;
            psn.setHoleShareAmount(String.valueOf(holeShareAmount));
            psn.setShareholdingProportion(gdTraZr.getAfterToProportion());
            relPsnService.updateSelective(psn);
        } else {
            RelOrg relOrg = new RelOrg();
            relOrg.setOrgName(toShareholdersName);
            relOrg.setShareholdersCode(toShareholdersCode);
            RelOrg org = relOrgService.get(relOrg);
            Integer holeShareAmount = Integer.parseInt(org.getHoleShareAmount());
            holeShareAmount = holeShareAmount + operationAmount;
            org.setHoleShareAmount(String.valueOf(holeShareAmount));
            org.setShareholdingProportion(gdTraZr.getAfterToProportion());
            relOrgService.updateSelective(org);
        }
        GdTraZr traZr = new GdTraZr();
        traZr.setPid(gdTraZr.getPid());
        traZr.setStatus("6");
        gdTraZrService.updateSelective(traZr);
    }

    private void orgOpenHandler(String data) {
        RelOrg relOrg = JSONObject.parseObject(data, RelOrg.class);
        Map<String, Object> tips = new HashMap<String, Object>();
        int pid = relOrg.getPid();

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
        RelOrg org = relOrgService.get(pid);
        org.setStatus("6");
        org.setStatusComment("审批通过");
        relOrgService.updateSelective(org);
    }

    private void psnOpenHandler(String data) {
        RelPsn relPsn = JSONObject.parseObject(data, RelPsn.class);
        Map<String, Object> tips = new HashMap<String, Object>();
        int pid = relPsn.getPid();

        //1 查询出所有的关系自然人  新增改为归档  归档-删除 改为删除
        //查询自然人的关系
        List<RelPsnVo> relPsnVoList = relPsnService.selectRelPsnInformation(pid + "");
        List<RelOrgVo> tzOrgPsn = relOrgService.getTZOrgPsn(String.valueOf(pid));
        RelPsnVo relPsnVo;
        RelOrgVo relOrgVo;
        Rel rel;
        if (relPsnVoList.size() > 0) {
            for (int i = 0; i < relPsnVoList.size(); i++) {
                relPsnVo = relPsnVoList.get(i);
                rel = relService.get(relPsnVo.getRelId());
                if ("01".equals(relPsnVo.getRelStatus())) {
                    rel.setRelStatus("00");
                } else if ("03".equals(relPsnVo.getRelStatus())) {
//                        relService.removeRel();
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
        RelPsn psn = relPsnService.get(pid);
        psn.setStatus("6");
        psn.setStatusComment("审批通过");
        relPsnService.updateSelective(psn);
    }

}