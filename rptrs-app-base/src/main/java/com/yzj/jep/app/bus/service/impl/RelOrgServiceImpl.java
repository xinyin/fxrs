/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月23日 13:59:30
 *
 */

package com.yzj.jep.app.bus.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.bus.mapper.RelOrgMapper;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.RelService;
import com.yzj.jep.app.bus.vo.Neo4jRelVo;
import com.yzj.jep.app.sys.web.controller.XlsieController;
import com.yzj.jep.app.util.IntegerUtils;
import com.yzj.jep.app.util.Neo4jUtil;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


@Service(value = "RelOrgServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class RelOrgServiceImpl extends MyBatisCRUDService<RelOrg, Integer> implements RelOrgService {

    Logger logger = LoggerFactory.getLogger(RelOrgServiceImpl.class);

    @Autowired
    private RelOrgMapper relOrgMapper;

    @Autowired
    private RelService relService;

    @Autowired
    private RelOrgService relOrgService;

    @Autowired
    private DictItemService dictItemService;

    @Override
    public Mapper<RelOrg, Integer> getMapper() {
        return relOrgMapper;
    }

    @Override
    public List<RelOrgVo> getTZOrgPsn(String orgid) {
        return relOrgMapper.getTZOrgPsn(orgid);
    }

    @Override
    public List<RelOrgVo> selectRelOrgOfProInformation(String orgid) {
        return relOrgMapper.getDownTZOrgPsn(orgid);
    }

    @Override
    public List<RelOrgVo> getDownTZOrgPsn(String orgid) {
        return relOrgMapper.getDownTZOrgPsn(orgid);
    }

    @Override
    public List<RelOrg> selectList(RelOrg relOrg) {
        return relOrgMapper.selectList(relOrg);
    }

    @Override
    public void insertExcelToDb(InputStream in, MultipartFile file) {
        try {
            List<List<Object>> lists = XlsieController.parseExcel(in, file.getOriginalFilename());
            List<RelOrg> listOrg = new ArrayList<>();
            //遍历lists
            for (int i = 0; i < lists.size(); i++) {
                List<Object> list = lists.get(i);   ///该集合是Excel中一行的单元格数据集合
                RelOrg ro = new RelOrg();
                ro.setPid(Integer.parseInt(String.valueOf(list.get(0))));
                ro.setRegCode(String.valueOf(list.get(1)));
                ro.setOrgName(String.valueOf(list.get(2)));
                ro.setOrgType(String.valueOf(list.get(3)));
                listOrg.add(ro);
            }
            relOrgMapper.insertExcelToDb(listOrg);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<RelOrg> queryOrgList() {
        return relOrgMapper.queryOrgList();
    }

    @Override
    public List<RelOrgVo> selectRelProOfOrgInformation(String pid) {
        return relOrgMapper.selectRelProOfOrgInformation(pid);
    }

    @Override
    public List<RelPsnVo> selectRelPsnOfOrgInformation(String pid) {
        return relOrgMapper.selectRelPsnOfOrgInformation(pid);
    }

    @Override
    public List<RelOrgVo> selectRelOrgOfOrgInformation(String pid) {
        return relOrgMapper.selectRelOrgOfOrgInformation(pid);
    }

    @Override
    public List<RelPsnVo> getOrgPsn(String orgid) {
        return relOrgMapper.getOrgPsn(orgid);
    }

    @Override
    public List<RelPsnVo> getDownOrgPsn(String orgid) {
        return relOrgMapper.getDownOrgPsn(orgid);
    }

    @Override
    public List<RelOrgVo> getOrgOrg(String orgid) {
        return relOrgMapper.getOrgOrg(orgid);
    }

    @Override
    public List<RelProVo> getOrgUpPro(String orgid) {
        return relOrgMapper.getOrgUpPro(orgid);
    }

    @Override
    public List<RelProVo> getOrgDownPro(String orgid) {
        return relOrgMapper.getOrgDownPro(orgid);
    }

    @Override
    public List<RelOrgVo> getDownOrgOrg(String orgid) {
        return relOrgMapper.getDownOrgOrg(orgid);
    }

    @Override
    public RelOrg getGdInfo(String code) {
        RelOrg relOrg = new RelOrg();
        relOrg.setShareholdersCode(code);
        relOrg.setIsShareholder("1");
        relOrg.setDelflag("1");
        List<RelOrg> relOrgs = relOrgMapper.select(relOrg);
        if (relOrgs.size() > 0) {
            return relOrgs.get(0);
        }
        return null;
    }

    @Override
    public List<RelOrgVo> queryRelOrgInformation(Integer pid) {
        ArrayList<RelOrgVo> relOrgVoList = new ArrayList<>();
        List<Map<String, Object>> map = relOrgMapper.selectRelOrgInformation(pid);
        if (map != null && map.size() > 0) {
            for (Map<String, Object> stringObjectMap : map) {
                RelOrgVo relOrgVo = new RelOrgVo();
                if (stringObjectMap.containsKey("orgpid")) {
                    relOrgVo.setOrgId(new Integer(String.valueOf(stringObjectMap.get("orgpid"))));
                }
                if (stringObjectMap.containsKey("identify_type")) {
                    relOrgVo.setIdentifyType(String.valueOf(stringObjectMap.get("identify_type")));
                }
                if (stringObjectMap.containsKey("unisc_code")) {
                    relOrgVo.setUniscCode((String.valueOf(stringObjectMap.get("unisc_code"))));
                }
                if (stringObjectMap.containsKey("org_name")) {
                    relOrgVo.setOrgName(String.valueOf(stringObjectMap.get("org_name")));
                }
                if (stringObjectMap.containsKey("reg_address")) {
                    relOrgVo.setRegAddress(String.valueOf(stringObjectMap.get("reg_address")));
                }
                if (stringObjectMap.containsKey("domestic_or_foreign")) {
                    relOrgVo.setDomesticOrForeign(String.valueOf(stringObjectMap.get("domestic_or_foreign")));
                }
                if (stringObjectMap.containsKey("mger_org")) {
                    relOrgVo.setMgerOrg(String.valueOf(stringObjectMap.get("mger_org")));
                }
                if (stringObjectMap.containsKey("legal_person")) {
                    relOrgVo.setLegalPerson(String.valueOf(stringObjectMap.get("legal_person")));
                }
                if (stringObjectMap.containsKey("legal_identify_type")) {
                    relOrgVo.setLegalIdentifyType(String.valueOf(stringObjectMap.get("legal_identify_type")));
                }
                if (stringObjectMap.containsKey("legal_identify_number")) {
                    relOrgVo.setLegalIdentifyNumber(String.valueOf(stringObjectMap.get("legal_identify_number")));
                }
                if (stringObjectMap.containsKey("relpid")) {
                    relOrgVo.setRelId(new Integer(String.valueOf(stringObjectMap.get("relpid"))));
                }
                if (stringObjectMap.containsKey("rel_category")) {
                    relOrgVo.setRelCategory(String.valueOf(stringObjectMap.get("rel_category")));
                }
                if (stringObjectMap.containsKey("rel_category_sub")) {
                    relOrgVo.setRelCategorySub(String.valueOf(stringObjectMap.get("rel_category_sub")));
                }
                relOrgVoList.add(relOrgVo);
            }
        }
        return relOrgVoList;
    }

    @Override
    public List<RelOrg> selectListQuota(RelOrg relOrg) {
        return relOrgMapper.selectListQuota(relOrg);
    }

    @Override
    public void returnPsnTask(JSONObject json, String memo) {
        //处理逻辑是  关系 ：新增改为删除数据  归档-删除 改为归档  关联方：状态由审批中改为 审批退回
        //查询自然人的关系
        int pid = (int) json.get("pid");
        Rel relUpdata = relService.get(pid);
        //审批状态1、待补录；3、待提交；4、审批中；5、审批退回；6、已归档
        relUpdata.setApprovalStatus("5");
        relUpdata.setRelStatement(memo);
        relService.updateSelective(relUpdata);
    }

    @Override
    public String getAllPercent() {
        return relOrgMapper.getAllPercent();
    }

    @Override
    public List<Map<String, Object>> getSingle() {
        return relOrgMapper.getSingle();
    }

    /**
     * 机构-机构之间的关系
     * @param fromOrg
     * @param relOrg
     * @param rel
     */
    @Override
    public void insertNeo4jOTO(RelOrg fromOrg, RelOrg relOrg, Rel rel) {
        //插入图数据库
        Neo4jRelVo neo4j = new Neo4jRelVo();
        neo4j.setFromPid(Integer.valueOf(rel.getFromPid()));
        neo4j.setFromName(fromOrg.getOrgName());
        neo4j.setFromType("org");
        neo4j.setFromIdentifyType(fromOrg.getIdentifyType());
        neo4j.setFromIdentifyNumber(fromOrg.getUniscCode());
        neo4j.setFromTenantId(fromOrg.getTenantId());//租户
        neo4j.setToPid(Integer.valueOf(rel.getToPid()));
        neo4j.setToName(relOrg.getOrgName());
        neo4j.setToType("org");
        neo4j.setToIdentifyType(relOrg.getIdentifyType());
        neo4j.setToIdentifyNumber(relOrg.getUniscCode());
        neo4j.setToTenantId(relOrg.getTenantId());//租户
        List<Filter> rfilters = new Filter.Builder().eq("dictCode", "BUS-RelZZD").eq("itemValue", rel.getRelPsnOrg()).build();
        DictItem rdictItem = dictItemService.get(rfilters);
        neo4j.setRelWithprevious(rdictItem.getItemLabel());

        //是否关联方
        //neo4j.setFromIsRelParty(1);
        //是否内部人
        //neo4j.setFromIsInsider(0);
        //是否持股
        //neo4j.setFromIsShareholder(StringUtils.isEmpty(fromOrg.getIsShareholder()) || "0".equals(fromOrg.getIsShareholder()) ? 0 : 1);
        //是否关联方
        //neo4j.setToIsRelParty(1);
        //是否内部人
        //neo4j.setToIsInsider(StringUtils.isEmpty(relOrg.getIsInsider()) ? 0 : Integer.parseInt(relOrg.getIsInsider()));
        //是否持股
        //neo4j.setToIsShareholder(StringUtils.isEmpty(relOrg.getIsShareholder()) || "0".equals(relOrg.getIsShareholder()) ? 0 : 1);
        net.sf.json.JSONObject param = net.sf.json.JSONObject.fromObject(neo4j);
        //neo4j.setShareholdingRatio(new BigDecimal(rel.getShareholdingRatio()));
        net.sf.json.JSONObject result = null;
        //如果是删除状态则调用删除关系
/*        boolean isDel = "0".equals(rel.getDelflag()) || ("00".equals(rel.getRelStatus()) && (rel.getIsValid() == null || "0".equals(rel.getIsValid())));
        //没有定位码
        boolean isGL = rel.getFromAligCode() == null && rel.getToAligCode() == null;
        if (isDel) {
            result = Neo4jUtil.removeNeo4jRel(param);
        } else {
            if (!isGL) {*/
        result = Neo4jUtil.addNeo4jRel(param);
       /*     }
        }*/
        if (result == null) {
            logger.info(fromOrg.getOrgName() + "图数据库保存失败" + relOrg.getOrgName());
//			throw new Exception("图数据库保存失败");
        } else {
            String code = (String) result.get("code");
            if ("000".equals(code)) {
                logger.info(fromOrg.getOrgName() + "图数据库保存成功" + relOrg.getOrgName());
            }
        }
    }

    /**
     * 产品-机构 之间的关系
     * @param fromPro
     * @param relOrg
     * @param rel
     */
    @Override
    public void insertNeo4jPROTO(RelPro fromPro, RelOrg relOrg, Rel rel) {
        //插入图数据库
        Neo4jRelVo neo4j = new Neo4jRelVo();
        neo4j.setFromPid(Integer.valueOf(rel.getFromPid()));
        neo4j.setFromName(fromPro.getProName());
        neo4j.setFromType("pro");
        neo4j.setFromIdentifyType(fromPro.getProType());
        neo4j.setFromIdentifyNumber(fromPro.getProProvider());
        neo4j.setFromTenantId(fromPro.getTenantId());//租户
        neo4j.setToPid(Integer.valueOf(rel.getToPid()));
        neo4j.setToName(relOrg.getOrgName());
        neo4j.setToType("org");
        neo4j.setToIdentifyType(relOrg.getIdentifyType());
        neo4j.setToIdentifyNumber(relOrg.getUniscCode());
        neo4j.setToTenantId(relOrg.getTenantId());//租户
        List<Filter> rfilters = new Filter.Builder().eq("dictCode", "BUS-RelZZD").eq("itemValue", rel.getRelPsnOrg()).build();
        DictItem rdictItem = dictItemService.get(rfilters);
        neo4j.setRelWithprevious(rdictItem.getItemLabel());

        //是否关联方
        //neo4j.setFromIsRelParty(1);
        //是否内部人
        //neo4j.setFromIsInsider(0);
        //是否持股
        //neo4j.setFromIsShareholder(1);
        //是否关联方
        //neo4j.setToIsRelParty(1);
        //是否内部人
        //neo4j.setToIsInsider(0);
        //是否持股
        //neo4j.setToIsShareholder(StringUtils.isEmpty(relOrg.getIsShareholder()) || "0".equals(relOrg.getIsShareholder()) ? 0 : 1);
        net.sf.json.JSONObject param = net.sf.json.JSONObject.fromObject(neo4j);
        net.sf.json.JSONObject result = null;
        //如果是删除状态则调用删除关系
        //boolean isDel = "0".equals(rel.getDelflag()) || ("00".equals(rel.getRelStatus()) && (rel.getIsValid() == null || "0".equals(rel.getIsValid())));
        result = Neo4jUtil.addNeo4jRel(param);
        if (result == null) {
            logger.info(fromPro.getProName() + "图数据库保存失败" + relOrg.getOrgName());
//			throw new Exception("图数据库保存失败");
        } else {
            String code = (String) result.get("code");
            if ("000".equals(code)) {
                logger.info(fromPro.getProName() + "图数据库保存成功" + relOrg.getOrgName());
            }
        }
    }

    /**
     * 自然人-机构之间的关系
     * @param fromPsn
     * @param relOrg
     * @param rel
     */
    @Override
    public void insertNeo4jPsnTOrg(RelPsn fromPsn, RelOrg relOrg, Rel rel) {
        //插入图数据库
        Neo4jRelVo neo4j = new Neo4jRelVo();
        neo4j.setFromPid(Integer.valueOf(rel.getFromPid()));
        neo4j.setFromName(fromPsn.getName());
        neo4j.setFromType("psn");
        neo4j.setFromIdentifyType(fromPsn.getIdentifyType());
        neo4j.setFromIdentifyNumber(fromPsn.getIdentifyNumber());
        neo4j.setFromTenantId(fromPsn.getTenantId());//租户
        neo4j.setToPid(Integer.valueOf(rel.getToPid()));
        neo4j.setToName(relOrg.getOrgName());
        neo4j.setToType("org");
        neo4j.setToIdentifyType(relOrg.getIdentifyType());
        neo4j.setToIdentifyNumber(relOrg.getUniscCode());
        neo4j.setToTenantId(relOrg.getTenantId());//租户
        List<Filter> rfilters = new Filter.Builder().eq("dictCode", "BUS-RelZZD").eq("itemValue", rel.getRelPsnOrg()).build();
        DictItem rdictItem = dictItemService.get(rfilters);
        neo4j.setRelWithprevious(rdictItem.getItemLabel());
        //是否关联方
       // neo4j.setFromIsRelParty(1);
        //是否内部人
        //neo4j.setFromIsInsider(0);
        //是否持股
       // neo4j.setFromIsShareholder(fromPsn.getIsShareholder() == null || "0".equals(fromPsn.getIsShareholder()) ? 0 : 1);
        //是否关联方
        //neo4j.setToIsRelParty(1);
        //是否内部人
        //neo4j.setToIsInsider(0);
        //是否持股
        //neo4j.setToIsShareholder(relOrg.getIsShareholder() == null || "0".equals(relOrg.getIsShareholder()) ? 0 : 1);
        net.sf.json.JSONObject param = net.sf.json.JSONObject.fromObject(neo4j);
        net.sf.json.JSONObject result = null;
        //如果是删除状态则调用删除关系
        //boolean isDel = "0".equals(rel.getDelflag()) || ("00".equals(rel.getRelStatus()) && (rel.getIsValid() == null || "0".equals(rel.getIsValid())));
        //没有定位码
        // boolean isGL = rel.getFromAligCode() == null && rel.getToAligCode() == null;
//        if (isDel) {
//            result = Neo4jUtil.removeNeo4jRel(param);
//        } else {
//            if (!isGL) {
        result = Neo4jUtil.addNeo4jRel(param);
//            }
//        }
        if (result == null) {
            logger.info(fromPsn.getName() + "图数据库保存失败" + relOrg.getOrgName());
        } else {
            String code = (String) result.get("code");
            if ("000".equals(code)) {
                logger.info(fromPsn.getName() + "图数据库保存成功" + relOrg.getOrgName());
            }
        }
    }

    //金融产品所属关系
    @Override
    public List<RelOrgVo> getProOrgPsn(String valueOf) {

        return relOrgMapper.getProOrgPsn(valueOf);
    }

    //金融产品下一级关系
    @Override
    public List<RelOrgVo> getDownProOrgPsn(String valueOf) {


        return relOrgMapper.getDownProOrgPsn(valueOf);
    }

    @Override
    public List<RelOrgVo> selecUpOrg(String valueOf) {

        return relOrgMapper.selecUpOrg(valueOf);
    }

    @Override
    public List<RelOrgVo> selecDownOrg(String valueOf) {
        relOrgMapper.selecDownOrg(valueOf);
        return null;
    }

    /*@Override
    public String getSingle(){
        return relOrgMapper.getSingle();
    }*/
    @Override
    public void updateIsIterateByPid(Integer pid, Integer isIterate) {
        relOrgMapper.updateIsIterateByPid(pid, isIterate);
    }

    @Override
    public String getMemGroup(String relId) {
        return relOrgMapper.getMemGroup(relId);
    }

    @Override
    public BigDecimal getFundsAgreementAmount(String contractSigningDate, String memGroup) {
        return relOrgMapper.getFundsAgreementAmount(contractSigningDate, memGroup);
    }

    @Override
    public List<RelOrg> selectByProEffective() {
        List<RelOrg> relOrgs = relOrgMapper.selectByEffective();
        return relOrgs;
    }

    @Override
    public List<RelOrg> selectById(String identify_type, String unisc_code) {
        return relOrgMapper.selectById(identify_type,unisc_code);
    }

    @Override
    public String getMaxBh() {
        String maxBh = relOrgMapper.getMaxBh();
        if(StringUtils.isEmpty(maxBh)){
            return "CC00001";
        }
        Integer in = Integer.valueOf(maxBh.substring(2));
        AtomicInteger val = new AtomicInteger(in);
        return "CC" + String.format("%05d", val.incrementAndGet());
    }


    @Override
    public List<RelOrgVo> selectOrgByRelToPid(String pid) {
        return relOrgMapper.selectOrgByRelToPid(pid);
    }

    @Override
    public List<RelOrgVo> selectOrgByProToPid(String rootId) {
        return relOrgMapper.selectOrgByProToPid(rootId);
    }

    @Override
    public List<RelOrgVo> selectOrgByRelFromPid(String rootId) {
        return relOrgMapper.selectOrgByRelFromPid(rootId);
    }

    @Override
    public List<RelOrgVo> selectOrgByProFromPid(String rootId) {
        return relOrgMapper.selectOrgByProFromPid(rootId);
    }

    @Override
    public void isAddRecordNumber(Map<String, Object> tips) {
        String yyyyMM = DateUtil.format(new Date(), "yyyyMM");
        List<String> isRecordNumber = relOrgMapper.isRecordNumber(yyyyMM);
        List<String> strings = relOrgMapper.recordNumber();

        String recordNumber =null;
        if(ObjectUtil.isNotEmpty(strings)){
            tips.put("version",false);
            if(ObjectUtil.isNotEmpty(isRecordNumber)){
                recordNumber = isRecordNumber.get(0);
                String smallRecordNumber = IntegerUtils.recordNumber(true, recordNumber);
                tips.put("smallRecordNumber",smallRecordNumber);
                tips.put("newVersion",true);
            }else {
                recordNumber = strings.get(0);
                String smallRecordNumber = IntegerUtils.recordNumber(true, recordNumber);
                String newRecordNumber = IntegerUtils.recordNumber(false, null);
                tips.put("smallRecordNumber",smallRecordNumber);
                tips.put("newRecordNumber", newRecordNumber);
                tips.put("newVersion",false);
            }
        }else {
            recordNumber = IntegerUtils.recordNumber(false, null);
            tips.put("version",true);
        }
        tips.put("recordNumber",recordNumber);
    }

    @Override
    public RelOrg selectByIdentifyNumber(String uniscCode) {
        RelOrg relOrg = new RelOrg();
        relOrg.setUniscCode(uniscCode);
        relOrg.setDelflag("1");
        List<RelOrg> relOrgs = relOrgService.select(relOrg);
        if (relOrgs.size() > 0) {
            return relOrgs.get(0);
        }
        return null;
    }

}
