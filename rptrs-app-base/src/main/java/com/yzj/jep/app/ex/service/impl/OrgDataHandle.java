package com.yzj.jep.app.ex.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.bus.mapper.*;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.app.bus.vo.Neo4jRelVo;
import com.yzj.jep.app.fil.mapper.FilRelOrgMapper;
import com.yzj.jep.app.fil.model.FilRel;
import com.yzj.jep.app.fil.model.FilRelOrg;
import com.yzj.jep.app.fil.service.FilRelOrgService;
import com.yzj.jep.app.fil.service.FilRelPsnService;
import com.yzj.jep.app.fil.service.FilRelService;
import com.yzj.jep.app.mid.model.BusProc;
import com.yzj.jep.app.mid.service.BusProcService;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.Neo4jUtil;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.client.ProcessDataHandler;
import com.yzj.jep.workflow.client.annotation.DataHandler;
import org.springframework.beans.BeanUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DataHandler("zjcc_ORG001")
public class OrgDataHandle implements ProcessDataHandler {
    RelPsnService relPsnService;
    DelRelService delRelService;
    RelOrgService relOrgService;
    RelService relService;
    private DelRelMapper delRelMapper;
    private DelRelOrgMapper delRelOrgMapper;
    FilRelOrgService filRelOrgService;
    FilRelPsnService filRelPsnService;
    FilRelService filRelService;
    RelPsnAndOrgOfProService relPsnAndOrgOfProService;
    private RelMapper relMapper;
    private RelOrgMapper relOrgMapper;
    private RelPsnMapper relPsnMapper;
    private RelProMapper relProMapper;
    private DictItemService dictItemService;
    RelHistoryService relHistoryService;
    FilRelOrgMapper filRelOrgMapper;
    BusProcService busProcService;

    @Override
    public void handleData(String data) throws WorkflowException {
        relPsnService = SpringContextHolder.getBean(RelPsnService.class);
        relOrgService = SpringContextHolder.getBean(RelOrgService.class);
        delRelService = SpringContextHolder.getBean(DelRelService.class);
        relService = SpringContextHolder.getBean(RelService.class);
        delRelMapper = SpringContextHolder.getBean(DelRelMapper.class);
        delRelOrgMapper = SpringContextHolder.getBean(DelRelOrgMapper.class);
        relPsnAndOrgOfProService = SpringContextHolder.getBean(RelPsnAndOrgOfProService.class);
        relMapper = SpringContextHolder.getBean(RelMapper.class);
        relOrgMapper = SpringContextHolder.getBean(RelOrgMapper.class);
        relProMapper = SpringContextHolder.getBean(RelProMapper.class);
        relPsnMapper = SpringContextHolder.getBean(RelPsnMapper.class);
        filRelOrgService = SpringContextHolder.getBean(FilRelOrgService.class);
        filRelPsnService = SpringContextHolder.getBean(FilRelPsnService.class);
        filRelService = SpringContextHolder.getBean(FilRelService.class);
        dictItemService = SpringContextHolder.getBean(DictItemService.class);
        relHistoryService = SpringContextHolder.getBean(RelHistoryService.class);
        filRelOrgMapper = SpringContextHolder.getBean(FilRelOrgMapper.class);
        busProcService = SpringContextHolder.getBean(BusProcService.class);
        JSONObject json = JSONObject.parseObject(data);
        Map<String, Object> tips = new HashMap<String, Object>();
        //关联关系pid
        int pid = (int) json.get("pid");

        //获取审批 基础关联方pid
        int orgPid = Integer.valueOf(relService.get(pid).getFromPid());

        //查询上级关系自然人、机构、金融产品
        List<Filter> rel1 = new Filter.Builder().eq("toPid", orgPid).eq("toType", "ORG").eq("delflag", "1").eq("isValid", "1").build();
        List<Rel> relList1 = relService.select(rel1);
        //查询下级关系自然人、机构、金融产品
        List<Filter> downRel1 = new Filter.Builder().eq("fromPid", orgPid).eq("fromType", "ORG").eq("delflag", "1").eq("isValid", "1").build();
        List<Rel> downRelVoList1 = relService.select(downRel1);
        //判断是否存有关联关系、存在有效关联关系既可设置为 有效关联方
        if (relList1.size() > 0 || downRelVoList1.size() > 0) {
            RelOrg relOrg = relOrgService.get(orgPid);
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
            RelOrg relOrg = relOrgService.get(orgPid);
            //relOrg.setApprovalStatus("1");
            relOrg.setRegulatorOth1(null);
            //是否为关联方 1否，0是
            relOrg.setIsAssoParty("1");
            //是否存在有效关系 1 有、0无
            relOrg.setEffectRelation("0");
            relOrg.setStatusComment("审批通过");
            relOrgService.updateSelective(relOrg);
        }
        Rel relUpdata = new Rel();
        relUpdata.setPid(pid);
        //审批状态1、待补录；2、待提交；3、审批中；4、审批退回；5、已归档
        relUpdata.setApprovalStatus("5");
        //关系的关系状态
        relUpdata.setRelStatus("00");
        relService.updateSelective(relUpdata);
        //保存关联关系到历史库
        UserProfile profile = UserProfileUtils.getUserProfile();
        Rel rel = relService.get(relUpdata.getPid());
        RelHistory relHistory = new RelHistory();
        BeanUtils.copyProperties(rel, relHistory);
        relHistory.setOperator(profile.getStaffName());
        relHistory.setRecordType(1);
        relHistory.setTransactionDep(profile.getDeptName());
        relHistoryService.insert(relHistory);
        //add by dongjiangkun on 20210108 start
        //查询数据的最新备份
        FilRelOrg filRelOrg = getFilRelOrgBYPidAndTenantId(pid,filRelOrgMapper);
        //如果fileRelPsn不为空，比较关联方自然人名称、证件号码证、件类型、境内外标识
        if(null != filRelOrg){
            //判断关联方自然人名称、证件号码证、件类型、境内外标识是否发生改变
            boolean result = isChange(json,filRelOrg);
            if(result){
                //更新ischange状态为改变
                updateRelOrgIschangeByPid(pid,relOrgService);
            }
        }
        //备份数据
        String procInstId = json.getString("procInstId");
        String filNum = (String)json.get("filNum");
        saveFilRelOrgAndFilRel(procInstId, pid, filNum,relService, filRelOrgService, filRelService, busProcService);
        //新增或解除关系图谱
        /*List<Filter> relListFilter = new Filter.Builder().eq("toPid", orgPid).eq("toType", "ORG").eq("delflag", "1").build();
        List<Rel> relPidList = relService.select(relListFilter);
        if (relService.exists(relListFilter)) {
            for (Rel rel : relPidList) {
                RelOrg relToOrg = relOrgMapper.selectByPrimaryKey(Integer.valueOf(rel.getToPid()));
                //“1” 是  “0” 否
                if ("1".equals(rel.getIsValid())) {
                    if ("PSN".equals(rel.getFromType())) {
                        RelPsn relFromPsn = relPsnMapper.selectByPrimaryKey(Integer.valueOf(rel.getFromPid()));
                        relOrgService.insertNeo4jPsnTOrg(relFromPsn, relToOrg, rel);
                    } else if ("ORG".equals(rel.getFromType())) {
                        RelOrg relFromOrg = relOrgMapper.selectByPrimaryKey(Integer.valueOf(rel.getFromPid()));
                        relOrgService.insertNeo4jOTO(relFromOrg, relToOrg, rel);
                    }
                }
                if ("0".equals(rel.getIsValid())) {
                    Neo4jRelVo neo4jRelVo = new Neo4jRelVo();
                    neo4jRelVo.setToPid(Integer.parseInt(rel.getToPid()));
                    neo4jRelVo.setToType("o");
                    List<Filter> filters = new Filter.Builder().eq("dictCode", "BUS-RelCategory").eq("itemValue", rel.getRelCategory()).build();
                    DictItem dictItem = dictItemService.get(filters);
                    neo4jRelVo.setRelCategory(dictItem.getItemLabel());
                    List<Filter> rfilters = new Filter.Builder().eq("dictCode", "BUS-Rel").eq("itemValue", rel.getRelCategorySub()).build();
                    DictItem rdictItem = dictItemService.get(rfilters);
                    neo4jRelVo.setRelCategorySub(rdictItem.getItemLabel());
                    neo4jRelVo.setFromPid(Integer.parseInt(rel.getFromPid()));
                    neo4jRelVo.setFromType(rel.getFromType().toLowerCase().substring(0, 1));
                    Neo4jUtil.removeNeo4jRel(net.sf.json.JSONObject.fromObject(neo4jRelVo));
                }
            }
        }*/
    }
    /**
     * 查询备份数据
     * @param pid
     * @param
     * @param filRelOrgMapper
     * @return
     */
    public FilRelOrg getFilRelOrgBYPidAndTenantId (Integer pid, FilRelOrgMapper filRelOrgMapper){
        //查询基本信息
        FilRelOrg filRelOrg = new FilRelOrg();
        filRelOrg.setFlagPid(pid.toString());
        List<FilRelOrg> list = filRelOrgMapper.selectFilRelOrgByfalgPidDesc(filRelOrg);
        if(null != list && list.size()>0){
            filRelOrg = list.get(0);
        }else{
            filRelOrg = null;
        }
        return filRelOrg;
    }
    /**
     * 判断四要素是否发生改变
     * @param json
     * @param filRelOrg
     * @return
     */
    public boolean isChange(JSONObject json,FilRelOrg filRelOrg){
        //初始标识默认
        boolean result = false;
        //关联方自然人名称
        String orgName = (String)json.get("orgName");
        //证件类型
        String identifyType = (String)json.get("identifyType");
        //证件号码
        String uniscCode = (String)json.get("uniscCode");
        //境内外标识
        String domesticOrForeign = (String)json.get("domesticOrForeign");
        //字段校验
        if (!orgName.equals(filRelOrg.getOrgName())||!identifyType.equals(filRelOrg.getIdentifyType())||!uniscCode.equals(filRelOrg.getUniscCode())||!domesticOrForeign.equals(filRelOrg.getDomesticOrForeign())){
            result = true;
        }
        return result;
    }

    /**
     * 更新ischange字段
     * @param pid
     * @param relOrgService
     */
    public void updateRelOrgIschangeByPid(Integer pid,RelOrgService relOrgService){
        RelOrg relOrg = new RelOrg();
        relOrg.setPid(pid);
        relOrg = relOrgService.get(relOrg);
        if(null != relOrg){
            relOrg.setIsChange("1");
            relOrgService.update(relOrg, new String[]{"isChange"});
        }
    }

    /**
     * 数据备份
     * @param pid
     * @param filNum
     * @param relService
     * @param filRelOrgService
     * @param filRelService
     */
    public void saveFilRelOrgAndFilRel(String procInstId, Integer pid, String filNum, RelService relService, FilRelOrgService filRelOrgService, FilRelService filRelService, BusProcService busProcService){
        //关联关系自然人机构表
        RelOrg relOrg = new RelOrg();
        relOrg.setPid(pid);
        relOrg = relOrgService.get(relOrg);
        if(null != relOrg){
            FilRelOrg filRelOrg = new FilRelOrg();
            BeanUtils.copyProperties(relOrg, filRelOrg);
            filRelOrg.setFilNum(filNum);
            filRelOrg.setFlagPid(pid.toString());
            filRelOrg.setPid(null);
            filRelOrgService.insertSelective(filRelOrg);

            //插入流程业务数据中间表
            filRelOrg = filRelOrgService.get(filRelOrg);
            int filPid = filRelOrg.getPid();
            String type = "relOrg";
            saveMidBusProc(procInstId,pid.toString(),String.valueOf(filPid),type,busProcService);

            //关系表备案
            //查出上级关系更新关系备案表
            List<Filter> upFilters = new Filter.Builder().eq("toType", "ORG").eq("toPid", pid).eq("delflag", "1").build();
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
            List<Filter> downFilters = new Filter.Builder().eq("fromType", "ORG").eq("fromPid", pid).eq("delflag", "1").build();
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
     * @param procInstId
     * @param pid
     * @param filPid
     * @param type
     */
    public void saveMidBusProc( String procInstId, String pid, String filPid, String type, BusProcService busProcService){
        BusProc busProc = new BusProc();
        busProc.setProcInstId(procInstId);
        busProc.setFlagPid(pid);
        busProc.setFilPid(filPid);
        busProc.setBusType(type);
        busProcService.insertSelective(busProc);
    }
}

