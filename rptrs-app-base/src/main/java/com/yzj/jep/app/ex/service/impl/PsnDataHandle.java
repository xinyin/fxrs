package com.yzj.jep.app.ex.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.bus.mapper.DelRelMapper;
import com.yzj.jep.app.bus.mapper.RelMapper;
import com.yzj.jep.app.bus.mapper.RelOrgMapper;
import com.yzj.jep.app.bus.mapper.RelPsnMapper;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.app.fil.mapper.FilRelPsnMapper;
import com.yzj.jep.app.fil.model.FilRel;
import com.yzj.jep.app.fil.model.FilRelPsn;
import com.yzj.jep.app.fil.service.FilRelOrgService;
import com.yzj.jep.app.fil.service.FilRelPsnService;
import com.yzj.jep.app.fil.service.FilRelService;
import com.yzj.jep.app.mid.model.BusProc;
import com.yzj.jep.app.mid.service.BusProcService;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.client.ProcessDataHandler;
import com.yzj.jep.workflow.client.annotation.DataHandler;
import org.springframework.beans.BeanUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 关联关系审批流程
 */
//@DataHandler("zjcc_Psn001")
@DataHandler("zyRel003")//关联关系审批
public class PsnDataHandle implements ProcessDataHandler {
    RelPsnService relPsnService;
    DelRelService delRelService;
    RelOrgService relOrgService;
    RelService relService;
    RelPsnAndOrgOfProService relPsnAndOrgOfProService;
    private DelRelMapper delRelMapper;
    FilRelOrgService filRelOrgService;
    FilRelPsnService filRelPsnService;
    FilRelService filRelService;
    RelHistoryService relHistoryService;
    BusProcService busProcService;
    RelProService relProService;

    private RelMapper relMapper;
    private RelOrgMapper relOrgMapper;
    private RelPsnMapper relPsnMapper;
    FilRelPsnMapper filRelPsnMapper;

    @Override
    public void handleData(String data) throws WorkflowException {
        relPsnService = SpringContextHolder.getBean(RelPsnService.class);
        delRelService = SpringContextHolder.getBean(DelRelService.class);
        relOrgService = SpringContextHolder.getBean(RelOrgService.class);
        relService = SpringContextHolder.getBean(RelService.class);
        relPsnAndOrgOfProService = SpringContextHolder.getBean(RelPsnAndOrgOfProService.class);
        delRelMapper = SpringContextHolder.getBean(DelRelMapper.class);
        relMapper = SpringContextHolder.getBean(RelMapper.class);
        relOrgMapper = SpringContextHolder.getBean(RelOrgMapper.class);
        relPsnMapper = SpringContextHolder.getBean(RelPsnMapper.class);
        filRelOrgService = SpringContextHolder.getBean(FilRelOrgService.class);
        filRelPsnService = SpringContextHolder.getBean(FilRelPsnService.class);
        filRelService = SpringContextHolder.getBean(FilRelService.class);
        relHistoryService = SpringContextHolder.getBean(RelHistoryService.class);
        filRelPsnMapper = SpringContextHolder.getBean(FilRelPsnMapper.class);
        busProcService = SpringContextHolder.getBean(BusProcService.class);
        relProService = SpringContextHolder.getBean(RelProService.class);
        JSONObject json = JSONObject.parseObject(data);
        Map<String, Object> tips = new HashMap<String, Object>();
        //关联关系pid
        String pids = (String) json.get("pids");
        String[] pidArr = pids.split(",");
        //备份数据
        String procInstId = json.getString("procInstId");
        String filNum = (String) json.get("filNum");
        for (String pidStr : pidArr) {

            /**将原来的逻辑放到循环中*/
            int pid = Integer.valueOf(pidStr);
            Rel rel = relService.get(pid);

            //修改数据是否有效
            updateRelation(rel);

            //修改关联方关系
            int psnPid = Integer.valueOf(rel.getFromPid());

            Rel relUpdata = new Rel();
            relUpdata.setPid(pid);
            //审批状态1、待补录；2、待提交；3、审批中；4、审批退回；5、已归档
            relUpdata.setApprovalStatus("5");
            //关系的关系状态
            relUpdata.setRelStatus("00");
            relService.updateSelective(relUpdata);
            //保存关联关系到历史库
            UserProfile profile = UserProfileUtils.getUserProfile();
//            Rel rel = relService.get(relUpdata.getPid());
            RelHistory relHistory = new RelHistory();
            BeanUtils.copyProperties(rel, relHistory);
            relHistory.setOperator(profile == null ? null : profile.getStaffName());
            relHistory.setRecordType(1);
            relHistory.setTransactionDep(profile == null ? null : profile.getDeptName());
            relHistory.setPid(null);
            relHistoryService.insertSelective(relHistory);

            //保存filRel
            FilRel filRel = new FilRel();
            BeanUtils.copyProperties(rel, filRel);
            filRel.setFilNum(filNum);
            filRel.setPid(null);
            filRelService.insertSelective(filRel);
            filRel = filRelService.get(filRel);
            //查询备份数据
           /* FilRelPsn filRelPsn = getFilRelPsnBYPidAndTenantId(pid,filRelPsnMapper);
            //如果fileRelPsn不为空，比较关联方自然人名称、证件号码证、件类型、境内外标识
            if(null != filRelPsn){
                //判断关联方自然人名称、证件号码证、件类型、境内外标识是否发生改变
                boolean result = isChange(json,filRelPsn);
                if(result){
                    //更新ischange状态为改变
                    updateRelPsnIschangeByPid(pid,relPsnService);
                }
            }*/
            //保存中间表数据
//            saveFilRelPsnAndFilRel(procInstId,pid,filNum,relService,filRelPsnService,filRelService,busProcService,pids);
            saveMidBusProc(procInstId, pidStr, String.valueOf(filRel.getPid()), "rel", busProcService, pids);
            //新增或解除关系图谱
        /*List<Filter> relForPid = new Filter.Builder().eq("toPid", psnPid).eq("toType", "PSN").eq("delflag", "1").build();
        List<Rel> relPidList = relService.select(relForPid);
        for (Rel rel : relPidList) {
            RelPsn relToPsn = relPsnMapper.selectByPrimaryKey(Integer.valueOf(rel.getToPid()));
            if ("1".equals(rel.getIsValid())) {
                if ("PSN".equals(rel.getFromType())) {
                    RelPsn relFromPsn = relPsnMapper.selectByPrimaryKey(Integer.valueOf(rel.getFromPid()));
                    relPsnService.insertNeo4jPsnToPsn(relFromPsn, relToPsn, rel);
                    relService.updateSelective(rel);
                } else if ("ORG".equals(rel.getFromType())) {
                    RelOrg relFromOrg = relOrgMapper.selectByPrimaryKey(Integer.valueOf(rel.getFromPid()));
                    relPsnService.insertNeo4jOrgToPsn(relFromOrg, relToPsn, rel);
                    relService.updateSelective(rel);
                }
            }
            if ("0".equals(rel.getIsValid())) {
                Neo4jRelVo neo4jRelVo = new Neo4jRelVo();
                neo4jRelVo.setToPid(Integer.parseInt(rel.getToPid()));
                neo4jRelVo.setToType("p");
                neo4jRelVo.setRelCategory(rel.getRelCategory());
                neo4jRelVo.setRelCategorySub(rel.getRelCategorySub());
                neo4jRelVo.setFromPid(Integer.parseInt(rel.getFromPid()));
                neo4jRelVo.setFromType(rel.getFromType().toLowerCase().substring(0, 1));
                Neo4jUtil.removeNeo4jRel(net.sf.json.JSONObject.fromObject(neo4jRelVo));
            }
        }*/
        }

    }

    /**
     * 修改关联关系的各个状态
     *
     * @param rel
     */
    private void updateRelation(Rel rel) {
        //获取审批 基础关联方pid
        String fromType = rel.getFromType();
        String fromPid = rel.getFromPid();
        updateDatabaseData(fromPid, fromType);
    }

    /**
     * 修改数据库表结构
     *
     * @param pid
     * @param type
     */
    private void updateDatabaseData(String pid, String type) {
        Integer fromPid = Integer.valueOf(pid);
        if ("ORG".equals(type)) {
            //机构
            //查询上级关系自然人、机构、金融产品
            List<Filter> rel1 = new Filter.Builder().eq("toPid", fromPid).eq("toType", "ORG").eq("delflag", "1").eq("isValid", "1").build();
            List<Rel> relList1 = relService.select(rel1);
            //查询下级关系自然人、机构、金融产品
            List<Filter> downRel1 = new Filter.Builder().eq("fromPid", fromPid).eq("fromType", "ORG").eq("delflag", "1").eq("isValid", "1").build();
            List<Rel> downRelVoList1 = relService.select(downRel1);
            //判断是否存有关联关系、存在有效关联关系既可设置为 有效关联方
            if (relList1.size() > 0 || downRelVoList1.size() > 0) {
                RelOrg relOrg = relOrgService.get(fromPid);
                //relOrg.setApprovalStatus("1");
                relOrg.setRegulatorOth1(null);
                //是否为关联方 1否，0是
                relOrg.setIsAssoParty("0");
                //是否存在有效关系 1 有、0无
                relOrg.setEffectRelation("1");
                //审批状态说明
                relOrg.setStatusComment("审批通过");
                relOrgService.updateSelective(relOrg);
            } else {
                RelOrg relOrg = relOrgService.get(fromPid);
                //relOrg.setApprovalStatus("1");
                relOrg.setRegulatorOth1(null);
                //是否为关联方 1否，0是
                relOrg.setIsAssoParty("1");
                //是否存在有效关系 1 有、0无
                relOrg.setEffectRelation("0");
                relOrg.setStatusComment("审批通过");
                relOrgService.updateSelective(relOrg);
            }
        } else if ("PRO".equals(type)) {
            //产品
            List<Filter> upFilterList = new Filter.Builder().
                    eq("toPid", fromPid).
                    eq("toType", "PRO").
                    eq("delflag", "1").
                    eq("isValid", "1").build();
            List<Rel> upRelList = relService.select(upFilterList);
            //查询下级关系自然人、机构、金融产品
            List<Filter> downFilterList = new Filter.Builder().
                    eq("fromPid", fromPid).
                    eq("fromType", "PRO").
                    eq("delflag", "1").
                    eq("isValid", "1").build();
            List<Rel> downRelList = relService.select(downFilterList);
            if (upRelList.size() > 0 || downRelList.size() > 0) {
                RelPro relPro = relProService.get(fromPid);//修改为关联方pid
                //是否存在有效关系  1有、0无
                relPro.setEffectRelation("1");
                //relPsn.setStatus("6");
                relProService.updateSelective(relPro);
            } else {
                RelPro relPro = relProService.get(fromPid);//修改为关联方pid
                //relPsn.setStatus("1");
                //是否为关联方 1否，0是
                //是否存在有效关系 1 有、0无
                relPro.setEffectRelation("0");
                relProService.updateSelective(relPro);
            }
        } else if ("PSN".equals(type)) {
            //关联人
            List<Filter> upFilterList = new Filter.Builder().
                    eq("toPid", fromPid).
                    eq("toType", "PSN").
                    eq("delflag", "1").
                    eq("isValid", "1").build();
            List<Rel> upRelList = relService.select(upFilterList);
            //查询下级关系自然人、机构、金融产品
            List<Filter> downFilterList = new Filter.Builder().
                    eq("fromPid", fromPid).
                    eq("fromType", "PSN").
                    eq("delflag", "1").
                    eq("isValid", "1").build();
            List<Rel> downRelList = relService.select(downFilterList);
            if (upRelList.size() > 0 || downRelList.size() > 0) {
                //修改为关联方pid
                RelPsn relPsn = relPsnService.get(fromPid);
                //是否关联方 1否、0是
                relPsn.setIsAssoParty("0");
                //是否存在有效关系  1有、0无
                relPsn.setEffectRelation("1");
                //relPsn.setStatus("6");
                relPsn.setStatusComment("审批通过");
                relPsnService.updateSelective(relPsn);
                delRelService.deleteByPrimaryKey(relPsn.getPid());
            } else {
                //修改为关联方pid
                RelPsn relPsn = relPsnService.get(fromPid);
                //relPsn.setStatus("1");
                relPsn.setRegulatorOth1(null);
                //是否为关联方 1否，0是
                relPsn.setIsAssoParty("1");
                //是否存在有效关系 1 有、0无
                relPsn.setEffectRelation("0");
                relPsn.setStatusComment("审批通过");
                relPsnService.updateSelective(relPsn);
            }
        }
    }

    /**
     * 查询备份数据
     *
     * @param pid
     * @param
     * @param filRelPsnMapper
     * @return
     */
    public FilRelPsn getFilRelPsnBYPidAndTenantId(Integer pid, FilRelPsnMapper filRelPsnMapper) {
        //查询基本信息
        FilRelPsn filRelPsn = new FilRelPsn();
        filRelPsn.setFlagPid(pid.toString());
        List<FilRelPsn> list = filRelPsnMapper.selectFilRelPsnByfalgPidDesc(filRelPsn);
        if (null != list && list.size() > 0) {
            filRelPsn = list.get(0);
        } else {
            filRelPsn = null;
        }
        return filRelPsn;
    }

    /**
     * 根据pid更新ischange字段
     *
     * @param pid
     * @param
     * @param relPsnService
     */
    public void updateRelPsnIschangeByPid(Integer pid, RelPsnService relPsnService) {
        RelPsn relPsn = new RelPsn();
        relPsn.setPid(pid);
        relPsn = relPsnService.get(relPsn);
        if (null != relPsn) {
            relPsn.setIsChange("1");
            relPsnService.update(relPsn, new String[]{"isChange"});
        }
    }

    /**
     * 判断四要素是否发生改变
     *
     * @param json
     * @param filRelPsn
     * @return
     */
    public boolean isChange(JSONObject json, FilRelPsn filRelPsn) {
        boolean result = false;
        //关联方自然人名称
        String name = (String) json.get("name");
        //证件类型
        String identifyType = (String) json.get("identifyType");
        //证件号码
        String identifyNumber = (String) json.get("identifyNumber");
        //境内外标识
        String domesticOrForeign = (String) json.get("domesticOrForeign");
        if (!filRelPsn.getName().equals(name) || !filRelPsn.getIdentifyType().equals(identifyType) || !filRelPsn.getIdentifyNumber().equals(identifyNumber) || !filRelPsn.getDomesticOrForeign().equals(domesticOrForeign)) {
            result = true;
        }
        return result;
    }

    /**
     * 备份关联关系表和关联关系自然人表
     *
     * @param procInstId
     * @param pid
     * @param
     * @param filNum
     * @param relService
     * @param filRelPsnService
     * @param filRelService
     */
    public void saveFilRelPsnAndFilRel(String procInstId, Integer pid, String filNum, RelService relService, FilRelPsnService filRelPsnService, FilRelService filRelService, BusProcService busProcService, String pids) {
        //关联关系自然人表
        RelPsn relPsn = new RelPsn();
        relPsn.setPid(pid);
        relPsn = relPsnService.get(relPsn);
        if (null != relPsn) {
            FilRelPsn filRelPsn = new FilRelPsn();
            BeanUtils.copyProperties(relPsn, filRelPsn);
            filRelPsn.setFilNum(filNum);
            filRelPsn.setFlagPid(pid.toString());
            filRelPsn.setPid(null);
            filRelPsnService.insertSelective(filRelPsn);

            //插入流程业务数据中间表
            filRelPsn = filRelPsnService.get(filRelPsn);
            int filPid = filRelPsn.getPid();
            String type = "relPsn";
//            saveMidBusProc(procInstId,pid.toString(),String.valueOf(filPid),type,busProcService,pids);

            //关系表备案
            //查出上级关系更新关系备案表
            List<Filter> upFilters = new Filter.Builder().eq("toType", "PSN").eq("toPid", pid).eq("delflag", "1").build();
            List<Rel> upRelList = relService.select(upFilters);
            for (Rel rel : upRelList) {
                FilRel filRel = new FilRel();
                BeanUtils.copyProperties(rel, filRel);
                filRel.setFilNum(filNum);
                filRel.setPid(null);
                filRel.setFlagPid(rel.getPid().toString());
                filRel.setToPid(String.valueOf(filPid));
                filRelService.insertSelective(filRel);
            }
            //查出下级关系更新关系备案表
            List<Filter> downFilters = new Filter.Builder().eq("fromType", "PSN").eq("fromPid", pid).eq("delflag", "1").build();
            List<Rel> downRelList = relService.select(downFilters);
            for (Rel rel : downRelList) {
                FilRel filRel = new FilRel();
                BeanUtils.copyProperties(rel, filRel);
                filRel.setFilNum(filNum);
                filRel.setPid(null);
                filRel.setFlagPid(rel.getPid().toString());
                filRel.setFromPid(String.valueOf(filPid));
                filRelService.insertSelective(filRel);
            }
        }
    }

    /**
     * 保存中间表
     *
     * @param procInstId
     * @param pid
     * @param filPid
     * @param type
     */
    public void saveMidBusProc(String procInstId, String pid, String filPid, String type, BusProcService busProcService, String pids) {
        BusProc busProc = new BusProc();
        busProc.setProcInstId(procInstId);
        if (StrUtil.isNotBlank(pids)) {
            busProc.setFocusApprovePid(pids);
        }
        busProc.setFlagPid(pid);
        busProc.setFilPid(filPid);
        busProc.setBusType(type);
        busProcService.insertSelective(busProc);
    }
}

