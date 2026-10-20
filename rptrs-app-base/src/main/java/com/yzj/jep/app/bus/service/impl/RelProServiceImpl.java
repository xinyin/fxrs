/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年05月25日 14:22:10
 *
 */

package com.yzj.jep.app.bus.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.bus.mapper.RelPsnMapper;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.vo.Neo4jRelVo;
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

import com.yzj.jep.app.bus.mapper.RelProMapper;
import com.yzj.jep.app.bus.service.RelProService;

import java.util.List;

@Service
public class RelProServiceImpl extends MyBatisCRUDService<RelPro, Integer> implements RelProService {

    Logger logger = LoggerFactory.getLogger(RelProServiceImpl.class);

    @Autowired
    private RelProMapper relProMapper;
    @Autowired
    private RelProService relProService;

    @Autowired
    private DictItemService dictItemService;

    @Override
    public Mapper<RelPro, Integer> getMapper() {
        return relProMapper;
    }

    @Override
    public List<RelProVo> selectRelProOrg(String pid) {

        return relProMapper.selectRelProOrg(pid);
    }

    @Override
    public List<RelProVo> selectDownRelProOrg(String pid) {

        return relProMapper.selectDownRelProOrg(pid);


    }

    @Override
    public void updateIsIterateByPid(Integer pid, Integer isIterate) {
        relProMapper.updateIsIterateByPid(pid, isIterate);
    }

    @Override
    public void insertNeo4jOrgToPsn(RelOrg relFromOrg, RelPro relToPro, RelPsnAndOrgOfPro rel) {

    }

    @Override
    public void returnProTask(JSONObject json, String memo) {
        //处理逻辑是  关系 ：新增改为删除数据  归档-删除 改为归档  关联方：状态由审批中改为 审批退回
        //查询自然人的关系
        int pid = (int) json.get("pid");
        //3 状态由审批中改为 退回审批 5
        RelPro relPro = relProService.get(pid);
        relPro.setApprovalStatus("5");
        relPro.setStatusComment(memo);
        relProService.update(relPro);
    }

    /**
     * 自然人-金融产品之间的关系
     * @param fromPsn
     * @param toPro
     * @param rel
     */
    @Override
    public void insertNeo4jPsnToPro(RelPsn fromPsn, RelPro toPro, Rel rel) {

        //插入图数据库
        Neo4jRelVo neo4j = new Neo4jRelVo();
        neo4j.setFromPid(Integer.valueOf(rel.getFromPid()));
        neo4j.setFromName(fromPsn.getName());
        neo4j.setFromIdentifyType(fromPsn.getIdentifyType());
        neo4j.setFromIdentifyNumber(fromPsn.getIdentifyNumber());
        neo4j.setToTenantId(fromPsn.getTenantId());//租户
        //psn-人，org-机构，pro-产品
        neo4j.setFromType("psn");
        neo4j.setToPid(Integer.valueOf(rel.getToPid()));
        neo4j.setToName(toPro.getProName());
        neo4j.setToIdentifyNumber(toPro.getProProvider());
        neo4j.setToIdentifyType(toPro.getProType());
        neo4j.setToType("pro");
        neo4j.setToTenantId(toPro.getTenantId());//租户
        List<Filter> rfilters = new Filter.Builder().eq("dictCode", "BUS-RelZZD").eq("itemValue", rel.getRelPsnOrg()).build();
        DictItem rdictItem = dictItemService.get(rfilters);
        neo4j.setRelWithprevious(rdictItem.getItemLabel());
        //是否关联方(0-否，1-是)
        //neo4j.setFromIsRelParty(StringUtils.isEmpty(fromPsn.getAligCode()) ? 0 : 1);
        //是否内部人
        //neo4j.setFromIsInsider(0);
        //是否持股
       //neo4j.setFromIsShareholder(StringUtils.isEmpty(fromPsn.getIsShareholder()) || "0".equals(fromPsn.getIsShareholder()) ? 0 : 1);
        //是否关联方
        //neo4j.setToIsRelParty(0);
        //是否内部人
        //neo4j.setToIsInsider(0);
        //是否持股
        //neo4j.setToIsShareholder(0);
        net.sf.json.JSONObject param = net.sf.json.JSONObject.fromObject(neo4j);
        net.sf.json.JSONObject result = null;
        //如果是删除状态则调用删除关系
        //boolean isDel = "0".equals(rel.getDelflag()) || ("00".equals(rel.getRelStatus()) && (rel.getIsValid() == null || "0".equals(rel.getIsValid())));
        //没有定位码
        /*boolean isGL = rel.getFromAligCode() == null && rel.getToAligCode() == null;
        if (isDel) {
            result = Neo4jUtil.removeNeo4jRel(param);
        } else {
            if (!isGL) {*/
        result = Neo4jUtil.addNeo4jRel(param);
//            }
//        }
        if (result == null) {
            logger.info(fromPsn.getName() + "图数据库保存失败" + toPro.getProName());
//            throw new Exception("图数据库保存失败");
        } else {
            String code = (String) result.get("code");
            if ("000".equals(code)) {
                logger.info(fromPsn.getName() + "图数据库保存成功" + toPro.getProName());
            }
        }

    }

    /**
     * 机构-金融产品之间的关系
     * @param fromOrg
     * @param toPro
     * @param rel
     */
    @Override
    public void insertNeo4jOrgToPro(RelOrg fromOrg, RelPro toPro, Rel rel) {
        //插入图数据库
        Neo4jRelVo neo4j = new Neo4jRelVo();
        neo4j.setFromPid(Integer.valueOf(rel.getFromPid()));
        neo4j.setFromName(fromOrg.getOrgName());
        neo4j.setFromIdentifyType(fromOrg.getIdentifyType());
        neo4j.setFromIdentifyNumber(fromOrg.getUniscCode());
        neo4j.setFromTenantId(fromOrg.getTenantId());//租户类型
        //psn-人，org-机构，pro-产品
        neo4j.setFromType("org");
        neo4j.setToPid(Integer.valueOf(rel.getToPid()));
        neo4j.setToName(toPro.getProName());
        neo4j.setToType("pro");
        neo4j.setFromTenantId(toPro.getTenantId());//租户类型
        neo4j.setToIdentifyType(toPro.getProType());
        neo4j.setToIdentifyNumber(toPro.getProProvider());
        List<Filter> rfilters = new Filter.Builder().eq("dictCode", "BUS-RelZZD").eq("itemValue", rel.getRelPsnOrg()).build();
        DictItem rdictItem = dictItemService.get(rfilters);
        neo4j.setRelWithprevious(rdictItem.getItemLabel());
        //是否关联方(0-否，1-是)
        neo4j.setFromIsRelParty(StringUtils.isEmpty(fromOrg.getAligCode()) ? 0 : 1);
        //是否内部人
        //neo4j.setFromIsInsider(StringUtils.isEmpty(fromOrg.getIsInsider()) || "0".equals(fromOrg.getIsInsider()) ? 0 : 1);
        //是否持股
        //neo4j.setFromIsShareholder(StringUtils.isEmpty(fromOrg.getIsShareholder()) || "0".equals(fromOrg.getIsShareholder()) ? 0 : 1);
        //是否关联方
        //neo4j.setToIsRelParty(0);
        //是否内部人
        //neo4j.setToIsInsider(0);
        //是否持股
       //neo4j.setToIsShareholder(0);
        net.sf.json.JSONObject param = net.sf.json.JSONObject.fromObject(neo4j);
        ;
        net.sf.json.JSONObject result = null;
        //如果是删除状态则调用删除关系
       // boolean isDel = "0".equals(rel.getDelflag()) || ("00".equals(rel.getRelStatus()) && (rel.getIsValid() == null || "0".equals(rel.getIsValid())));
        //没有定位码
        //boolean isGL = rel.getFromAligCode() == null && rel.getToAligCode() == null;
        /*if (isDel) {
            result = Neo4jUtil.removeNeo4jRel(param);
        } else {
            if (!isGL) {*/
        result = Neo4jUtil.addNeo4jRel(param);
//            }
//        }
        if (result == null) {
            logger.info(fromOrg.getOrgName() + "图数据库保存失败" + toPro.getProName());
//            throw new Exception("图数据库保存失败");
        } else {
            String code = (String) result.get("code");
            if ("000".equals(code)) {
                logger.info(fromOrg.getOrgName() + "图数据库保存成功" + toPro.getProName());
            }
        }
    }

    @Override
    public List<RelProVo> selectProByRelToPid(String pid) {
        return relProMapper.selectProRelByToPid(pid);
    }

    @Override
    public List<RelProVo> selectProByRelFromPid(String rootId) {
        return relProMapper.selectProByRelFromPid(rootId);
    }


}