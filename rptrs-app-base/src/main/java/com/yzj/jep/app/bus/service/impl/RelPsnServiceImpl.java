/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月23日 13:59:31
 *
 */

package com.yzj.jep.app.bus.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.bus.mapper.RelOrgMapper;
import com.yzj.jep.app.bus.mapper.RelPsnMapper;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.bus.service.RelService;
import com.yzj.jep.app.bus.vo.Neo4jRelVo;
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
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


@Service
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class RelPsnServiceImpl extends MyBatisCRUDService<RelPsn, Integer> implements RelPsnService {

    Logger logger = LoggerFactory.getLogger(RelPsnServiceImpl.class);

    @Autowired
    private RelPsnMapper relPsnMapper;

    @Autowired
    private RelOrgMapper relOrgMapper;


    @Autowired
    private RelService relService;

    @Autowired
    private RelPsnService relPsnService;

    @Autowired
    private RelOrgService relOrgService;

    @Autowired
    private DictItemService dictItemService;

    @Override
    public Mapper<RelPsn, Integer> getMapper() {
        return relPsnMapper;
    }


    @Override
    public List<RelPsnVo> selectRelPsnInformation(String pid) {
        return relPsnMapper.selectRelPsnInformation(pid);
    }

    @Override
    public List<RelPsnVo> selectRelPsnOfProInformation(String pid) {
        return relPsnMapper.selectRelPsnOfProInformation(pid);
    }

    @Override
    public List<RelPsnVo> selectDownRelPsnInformation(String pid) {
        return relPsnMapper.selectDownRelPsnInformation(pid);
    }


    @Override
    public List<RelPsn> selectPsnInformation(RelPsn relPsn) {
        return relPsnMapper.selectPsnInformation(relPsn);
    }


    @Override
    public RelPsn getGdInfo(String code) {
        RelPsn relPsn = new RelPsn();
        relPsn.setShareholdersCode(code);
        relPsn.setDelflag("1");
        relPsn.setIsShareholder("1");
        List<RelPsn> relPsns = relPsnService.select(relPsn);
        if (relPsns.size() > 0) {
            return relPsns.get(0);
        }
        return null;
    }

    @Override
    public void returnPsnTask(JSONObject json, String memo) {
        //处理逻辑是  关系 ：新增改为删除数据  归档-删除 改为归档  关联方：状态由审批中改为 审批退回
        String pid = (String) json.get("pid");
        if (StrUtil.isNotBlank(pid)) {
            String[] pidArr = pid.split(",");
            for (String pidStr : pidArr) {
                Rel relUpdata = relService.get(Integer.valueOf(pidStr));
                //审批状态1、待补录；2、待提交；3、审批中；4、审批退回；5、已归档
                relUpdata.setApprovalStatus("4");
                relUpdata.setRelStatement(memo);
                relService.updateSelective(relUpdata);
            }
        }

    }

    /**
     * 产品-自然人 之间的关系
     * @param fromPro
     * @param relPsn
     * @param rel
     */
    @Override
    public void insertNeo4jProToPsn(RelPro fromPro, RelPsn relPsn, Rel rel) {
        //插入图数据库
        Neo4jRelVo neo4j = new Neo4jRelVo();
        neo4j.setFromPid(Integer.valueOf(rel.getFromPid()));
        neo4j.setFromName(fromPro.getProName());
        neo4j.setFromIdentifyType(fromPro.getProType());
        neo4j.setFromIdentifyNumber(fromPro.getProProvider());
        neo4j.setFromTenantId(fromPro.getTenantId());//租户类型
        //psn-人，org-机构，pro-产品
        neo4j.setFromType("pro");
        neo4j.setToPid(Integer.valueOf(rel.getToPid()));
        neo4j.setToName(relPsn.getName());
        neo4j.setToType("psn");
        neo4j.setToIdentifyType(relPsn.getIdentifyType());//证件类型
        neo4j.setToIdentifyNumber(relPsn.getIdentifyNumber());//证件号码
        neo4j.setToTenantId(relPsn.getTenantId());//租户
        List<Filter> rfilters = new Filter.Builder().eq("dictCode", "BUS-RelZZD").eq("itemValue", rel.getRelPsnOrg()).build();
        DictItem rdictItem = dictItemService.get(rfilters);
        neo4j.setRelWithprevious(rdictItem.getItemLabel());
        //是否关联方(0-否，1-是)
        //neo4j.setFromIsRelParty(1);
        //是否内部人
        //neo4j.setFromIsInsider(0);
        //是否持股
        //neo4j.setFromIsShareholder(0);
        //是否关联方
        //neo4j.setToIsRelParty(StringUtils.isEmpty(relPsn.getAligCode()) ? 1 : 0);
        //是否内部人
        //neo4j.setToIsInsider(0);
        //是否持股
        //neo4j.setToIsShareholder(StringUtils.isEmpty(relPsn.getIsShareholder()) || "0".equals(relPsn.getIsShareholder()) ? 0 : 1);
        net.sf.json.JSONObject param = net.sf.json.JSONObject.fromObject(neo4j);
        net.sf.json.JSONObject result = null;
        //如果是删除状态则调用删除关系
        /*boolean isDel = "0".equals(rel.getDelflag()) || ("00".equals(rel.getRelStatus()) && (rel.getIsValid() == null || "0".equals(rel.getIsValid())));
        //没有定位码
        boolean isGL = rel.getFromAligCode() == null && rel.getToAligCode() == null;
        if (isDel) {
            result = Neo4jUtil.removeNeo4jRel(param);
        } else {
            if (!isGL) {*/
        result = Neo4jUtil.addNeo4jRel(param);
         /*   }
        }*/
        if (result == null) {
            logger.info(fromPro.getProName() + "图数据库保存失败" + relPsn.getName());
//            throw new Exception("图数据库保存失败");
        } else {
            String code = (String) result.get("code");
            if ("000".equals(code)) {
                logger.info(fromPro.getProName() + "图数据库保存成功" + relPsn.getName());
            }
        }
    }

    /**
     * 自然人-自然人之间的关系
     * @param fromPsn
     * @param relPsn
     * @param rel
     */
    @Override
    public void insertNeo4jPsnToPsn(RelPsn fromPsn, RelPsn relPsn, Rel rel) {
        //插入图数据库
        net.sf.json.JSONObject param = new net.sf.json.JSONObject();
        param.put("fromPid", rel.getFromPid());
        param.put("toPid", rel.getToPid());
        param.put("fromName", fromPsn.getName());
        param.put("toName", relPsn.getName());
        param.put("fromType", "psn");
        param.put("toType", "psn");
        param.put("fromIdentifyType", fromPsn.getIdentifyType());
        param.put("toIdentifyType", relPsn.getIdentifyType());
        param.put("fromIdentifyNumber", fromPsn.getIdentifyNumber());
        param.put("toIdentifyNumber", relPsn.getIdentifyNumber());
        param.put("toTenantId",relPsn.getTenantId());//租户类型
        param.put("fromTenantId",fromPsn.getTenantId());//租户类型
        List<Filter> rfilters = new Filter.Builder().eq("dictCode", "BUS-RelZZD").eq("itemValue", rel.getRelPsnOrg()).build();
        DictItem rdictItem = dictItemService.get(rfilters);
        param.put("relWithprevious", rdictItem.getItemLabel());
        //是否关联方
        //param.put("fromIsRelParty", "1");
        //是否内部人
        //param.put("fromIsInsider", "0");
        //是否持股
        //param.put("fromIsShareholder", fromPsn.getIsShareholder() == null || "0".equals(fromPsn.getIsShareholder()) ? "0" : "1");
        //是否关联方
        //param.put("toIsRelParty", "1");
        //是否内部人
        //param.put("toIsInsider", "0");
        //是否持股
        //param.put("toIsShareholder", relPsn.getIsShareholder() == null || "0".equals(relPsn.getIsShareholder()) ? "0" : "1");
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
            logger.info(fromPsn.getName() + "图数据库保存失败" + relPsn.getName());
        } else {
            String code = (String) result.get("code");
            if ("000".equals(code)) {
                logger.info(fromPsn.getName() + "图数据库保存成功" + relPsn.getName());
            }
        }
    }

    /**
     * 机构-自然人之间的关系
     * @param fromOrg
     * @param relPsn
     * @param rel
     */
    @Override
    public void insertNeo4jOrgToPsn(RelOrg fromOrg, RelPsn relPsn, Rel rel) {
        //插入图数据库
        net.sf.json.JSONObject param = new net.sf.json.JSONObject();
        param.put("fromPid", rel.getFromPid());
        param.put("toPid", rel.getToPid());
        param.put("fromName", fromOrg.getOrgName());
        param.put("toName", relPsn.getName());
        param.put("fromType", "org");
        param.put("toType", "psn");
        param.put("fromIdentifyType", fromOrg.getIdentifyType());
        param.put("toIdentifyType", relPsn.getIdentifyType());
        param.put("fromIdentifyNumber", fromOrg.getUniscCode());
        param.put("toIdentifyNumber", relPsn.getIdentifyNumber());
        param.put("toTenantId",relPsn.getTenantId());//租户类型
        param.put("fromTenantId",fromOrg.getTenantId());//租户类型
        List<Filter> rfilters = new Filter.Builder().eq("dictCode", "BUS-RelZZD").eq("itemValue", rel.getRelPsnOrg()).build();
        DictItem rdictItem = dictItemService.get(rfilters);
        param.put("relWithprevious", rdictItem.getItemLabel());
        //是否关联方
        //param.put("fromIsRelParty", "1");
        //是否内部人
        //param.put("fromIsInsider", "0");
        //是否持股
        //param.put("fromIsShareholder", fromOrg.getIsShareholder() == null || "0".equals(fromOrg.getIsShareholder()) ? "0" : "1");
        //是否关联方
        //param.put("toIsRelParty", "1");
        //是否内部人
        //param.put("toIsInsider", "0");
        //是否持股
        //param.put("toIsShareholder", relPsn.getIsShareholder() == null || "0".equals(relPsn.getIsShareholder()) ? "0" : "1");
        net.sf.json.JSONObject result = null;
        //如果是删除状态则调用删除关系
        boolean isDel = "0".equals(rel.getDelflag()) || ("00".equals(rel.getRelStatus()) && (rel.getIsValid() == null || "0".equals(rel.getIsValid())));
        //没有定位码
//        boolean isGL = rel.getFromAligCode() == null && rel.getToAligCode() == null;
//        if (isDel) {
//            result = Neo4jUtil.removeNeo4jRel(param);
//        } else {
//            if (!isGL) {
        result = Neo4jUtil.addNeo4jRel(param);
//            }
//        }
        if (result == null) {
            logger.info(fromOrg.getOrgName() + "图数据库保存失败" + relPsn.getName());
        } else {
            String code = (String) result.get("code");
            if ("000".equals(code)) {
                logger.info(fromOrg.getOrgName() + "图数据库保存成功" + relPsn.getName());
            }
        }
    }

    @Override
    public List<RelPsnVo> selectRelProInformation(String pid) {

        return relPsnMapper.selectRelProInformation(pid);
    }

    @Override
    public List<RelPsnVo> selectDownRelProInformation(String pid) {


        return relPsnMapper.selectDownRelProInformation(pid);
    }

    @Override
    public void updateIsIterateByPid(Integer pid, Integer isIterate) {
        relPsnMapper.updateIsIterateByPid(pid, isIterate);
    }

   /* @Override
    public List<PsnAndOrgVo> selectBatQue(String type, String pid){
       return relPsnMapper.selectBatQue(type, pid);
    }*/

    @Override
    public List<PsnAndOrgVo> selectBatQueMax(PsnAndOrgVo psnAndOrgVo) {
        return relPsnMapper.selectBatQueMax(psnAndOrgVo);
    }

    @Override
    public List<RelPsn> selectByProEffective() {
        List<RelPsn> relPsnList = relPsnMapper.selectByProEffective();
        return relPsnList;
    }

    @Override
    public String getMaxBh() {
        String maxBh = relPsnMapper.getMaxBh();
        if(StringUtils.isEmpty(maxBh)){
            return "CP00001";
        }
        Integer in = Integer.valueOf(maxBh.substring(2));
        AtomicInteger val = new AtomicInteger(in);
        return "CP" + String.format("%05d", val.incrementAndGet());
    }

    @Override
    public String getIdentifyType(String relType, String relNumber) {
        return relPsnMapper.getIdentifyType(relType, relNumber);
    }

    //图谱
    @Override
    public List<RelPsnVo> selectPsnByRelToPid(String pid) {
        List<RelPsnVo> relPsnList = relPsnMapper.selectPsnByRelToPid(pid);
        return relPsnList;
    }

    @Override
    public List<RelPsnVo> selectPsnByProToPid(String rootId) {
        List<RelPsnVo> relPsnList = relPsnMapper.selectPsnByProToPid(rootId);
        return relPsnList;
    }

    @Override
    public List<RelPsnVo> selectPsnByRelFromPid(String rootId) {
        return relPsnMapper.selectPsnByRelFromPid(rootId);
    }

    @Override
    public List<RelPsnVo> selectPsnByProFromPid(String rootId) {
        return relPsnMapper.selectPsnByProFromPid(rootId);
    }

    @Override
    public List<RelPsnVo> selectRelationDataByToPid(String pid) {
        List<RelPsnVo> relPsnList = relPsnMapper.selectRelationDataByToPid(pid);
        return relPsnList;
    }

    @Override
    public List<RelPsnVo> selectRelationDataByFromPid(String pid) {
        List<RelPsnVo> relPsnList = relPsnMapper.selectRelationDataByFromPid(pid);
        return relPsnList;
    }

    @Override
    public void isAddRecordNumber(Map<String, Object> tips) {
        String yyyyMM = DateUtil.format(new Date(), "yyyyMM");
        //  当前月份是否已经存在版本
        List<String> isRecordNumber = relPsnMapper.isRecordNumber(yyyyMM);
        // 是否存在版本号
        List<String> strings = relPsnMapper.recordNumber();

        // 当前版本号
        String recordNumber =null;
        // 数据库不存在数据，没有版本号
        if(ObjectUtil.isNotEmpty(strings)){
            // 存在版本号
            tips.put("version",false);
            if(ObjectUtil.isNotEmpty(isRecordNumber)){
                // 当月存在版本号
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
            // 不存在版本号
            recordNumber = IntegerUtils.recordNumber(false, null);
            tips.put("version",true);
        }
        tips.put("recordNumber",recordNumber);
    }

    @Override
    public RelPsn selectByIdentifyNumber(String identifyNumber) {
        RelPsn relPsn = new RelPsn();
        relPsn.setIdentifyNumber(identifyNumber);
        relPsn.setDelflag("1");
        List<RelPsn> relPsns = relPsnService.select(relPsn);
        if (relPsns.size() > 0) {
            return relPsns.get(0);
        }
        return null;
    }

}
