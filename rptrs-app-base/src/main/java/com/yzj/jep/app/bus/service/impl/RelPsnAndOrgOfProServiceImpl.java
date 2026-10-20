/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月23日 13:59:27
 *
 */

package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.mapper.DelRelMapper;
import com.yzj.jep.app.bus.mapper.RelOrgMapper;
import com.yzj.jep.app.bus.mapper.RelPsnAndOrgOfProMapper;
import com.yzj.jep.app.bus.mapper.RelPsnMapper;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.RelProService;
import com.yzj.jep.app.bus.service.RelPsnAndOrgOfProService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.bus.vo.Neo4jRelVo;
import com.yzj.jep.app.util.Neo4jUtil;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
@Transactional(readOnly = true)
public class RelPsnAndOrgOfProServiceImpl extends MyBatisCRUDService<RelPsnAndOrgOfPro, Integer> implements RelPsnAndOrgOfProService {

    Logger logger = LoggerFactory.getLogger(RelPsnAndOrgOfProServiceImpl.class);

    @Autowired
    private RelPsnAndOrgOfProMapper relPsnAndOrgOfProMapper;

    @Autowired
    private RelPsnMapper relPsnMapper;

    @Autowired
    private RelOrgMapper relOrgMapper;

    @Autowired
    private DelRelMapper delRelMapper;

    @Autowired
    private RelPsnService relPsnService;

    @Autowired
    private RelOrgService relOrgService;

    @Autowired
    private RelProService relProService;

    @Autowired
    private DictItemService dictItemService;

    @Override
    public Mapper<RelPsnAndOrgOfPro, Integer> getMapper() {
        return relPsnAndOrgOfProMapper;
    }

    //获取自然人归属上级金融产品
    @Override
    public List<RelProVo> selectRelProUpOfPsnInformation(String pid) {
        return relPsnAndOrgOfProMapper.selectRelProUpOfPsnInformation(pid);
    }

    //获取自然人关联的下级金融产品
    @Override
    public List<RelProVo> selectRelProDownOfPsnInformation(String pid) {
        return relPsnAndOrgOfProMapper.selectRelProDownOfPsnInformation(pid);
    }

    //获取机构归属上级金融产品
    @Override
    public List<RelProVo> selectRelProUpOfOrgInformation(String pid) {
        return relPsnAndOrgOfProMapper.selectRelProUpOfOrgInformation(pid);
    }

    //获取机构关联下级金融产品
    @Override
    public List<RelProVo> selectRelProDownOfOrgInformation(String pid) {
        return relPsnAndOrgOfProMapper.selectRelProDownOfOrgInformation(pid);
    }

    //获取金融产品归属上级关联自然人
    @Override
    public List<RelPsnVo> selectRelPsnUpOfProInformation(String pid) {
        return relPsnAndOrgOfProMapper.selectRelPsnUpOfProInformation(pid);
    }

    //获取金融产品下级关联自然人
    @Override
    public List<RelPsnVo> selectRelPsnDownOfProInformation(String pid) {
        return relPsnAndOrgOfProMapper.selectRelPsnDownOfProInformation(pid);
    }

    //获取金融产品归属上级关联机构
    @Override
    public List<RelOrgVo> selectRelOrgUpOfProInformation(String pid) {
        return relPsnAndOrgOfProMapper.selectRelOrgUpOfProInformation(pid);
    }

    //获取金融产品下级关联机构
    @Override
    public List<RelOrgVo> selectRelOrgDownOfProInformation(String pid) {
        return relPsnAndOrgOfProMapper.selectRelOrgDownOfProInformation(pid);
    }

    @Override
    public List<RelPsnVo> selectRelPsnOfProInformation(String pid) {
        return relPsnAndOrgOfProMapper.selectRelPsnOfProInformation(pid);
    }

    @Override
    public List<RelOrgVo> selectRelOrgOfProInformation(String orgid) {
        return relPsnAndOrgOfProMapper.selectRelOrgOfProInformation(orgid);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeRel(String fromId, String toId, String fromType, String toType) {
        RelPsnAndOrgOfPro rel = new RelPsnAndOrgOfPro();
        rel.setToPid(toId);
        rel.setToType(toType);
        rel.setFromPid(fromId);
        rel.setFromType(fromType);
        Integer pid = relPsnAndOrgOfProMapper.select(rel).get(0).getPid();
        rel.setPid(pid);
        if ("1".equals(relPsnAndOrgOfProMapper.select(rel).get(0).getDelflag())) {
            rel.setDelflag("0");
            relPsnAndOrgOfProMapper.updateSelective(rel);
            logger.debug("解除关联关系，frompid[{}],fromtype[{}],topid[{}],totype[{}]", rel.getFromPid(), rel.getFromType(), rel.getToPid(), rel.getToType());
            createNewDelRel(pid, "to");
        }
        RelPsnAndOrgOfPro relCond = new RelPsnAndOrgOfPro();
        relCond.setToType(toType);
        relCond.setToPid(toId);
        relCond.setDelflag("1");
        List<RelPsnAndOrgOfPro> rels = relPsnAndOrgOfProMapper.select(relCond);
        if (rels.size() > 0) {
            RelPsnAndOrgOfPro relCon2 = new RelPsnAndOrgOfPro();
            relCon2.setToType(toType);
            relCon2.setToPid(toId);
            relCon2.setDelflag("1");
            List<RelPsnAndOrgOfPro> relList = relPsnAndOrgOfProMapper.select(relCon2);
            for (RelPsnAndOrgOfPro rel1 : relList) {
                String fromPid = rel1.getFromPid();
                String fromType1 = rel1.getFromType();
                RelPsnAndOrgOfPro rel2 = new RelPsnAndOrgOfPro();
                rel2.setToPid(fromPid);
                rel2.setToType(fromType1);
                rel2.setDelflag("1");
                List<RelPsnAndOrgOfPro> relList1 = relPsnAndOrgOfProMapper.select(rel2);
                if (relList1.size() > 0) {
                    continue;
                } else {
                    relCon2.setPid(rel1.getPid());
                    relCon2.setFromPid(fromPid);
                    relCon2.setFromType(fromType1);
                    relCon2.setDelflag("0");
                    relPsnAndOrgOfProMapper.updateSelective(relCon2);
                    logger.debug("解除关联关系，frompid[{}],fromtype[{}],topid[{}],totype[{}]", rel.getFromPid(), rel.getFromType(), rel.getToPid(), rel.getToType());
                    createNewDelRel(rel1.getPid(), "from");
                }
            }
        }
        RelPsnAndOrgOfPro relCon = new RelPsnAndOrgOfPro();
        relCon.setToPid(toId);
        relCon.setToType(toType);
        relCon.setDelflag("1");
        List<RelPsnAndOrgOfPro> relList = relPsnAndOrgOfProMapper.select(relCon);
        if (relList.size() > 0) {
            return;
        }
        RelPsnAndOrgOfPro relCond1 = new RelPsnAndOrgOfPro();
        relCond1.setFromType(toType);
        relCond1.setFromPid(toId);
        List<RelPsnAndOrgOfPro> relList1 = relPsnAndOrgOfProMapper.select(relCond1);
        for (RelPsnAndOrgOfPro rel1 : relList1) {
            removeRel(rel1.getFromPid(), rel1.getToPid(), rel1.getFromType(), rel1.getToType());
        }
    }

    @Override
    public List<RelPsnAndOrgOfPro> getUpInfoOnOrg(Integer pid) {
        return null;
    }

    @Override
    public List<RelPsnAndOrgOfPro> getUpInfoOnPsn(Integer pid) {
        return null;
    }

    @Override
    public List<PsnAndOrgVo> selectPsnOrOrg(String pid) {
        List<PsnAndOrgVo> psnAndOrgVos = relPsnAndOrgOfProMapper.queryPsnOrOrg(pid);
        return psnAndOrgVos;
    }

    private void createNewDelRel(Integer relPid, String type) {
        logger.debug("添加解除关系记录...relPid[{}],type[{}]", relPid, type);
        RelPsnAndOrgOfPro rel = relPsnAndOrgOfProMapper.selectByPrimaryKey(relPid);
        String toType = null;
        String toId = null;
        if ("to".equals(type)) {
            toType = rel.getToType();
            toId = rel.getToPid();
        } else {
            toType = rel.getFromType();
            toId = rel.getFromPid();
        }

        if ("PSN".equals(toType)) {
            RelPsn relPsn = relPsnMapper.selectByPrimaryKey(Integer.valueOf(toId));
            DelRel delRel = new DelRel();
            delRel.setName(relPsn.getName());
            delRel.setDomesticOrForeign(relPsn.getDomesticOrForeign());
            delRel.setIdentifyType(relPsn.getIdentifyType());
            delRel.setIdentifyNumber(relPsn.getIdentifyNumber());
            delRel.setMgerOrg(relPsn.getMgerOrg());
            delRel.setRegulator(rel.getRegulator());
            delRel.setRelPid(relPsn.getPid());
            delRel.setType("PSN");
            delRelMapper.insert(delRel);
            RelPsn relPsnCon = new RelPsn();
            relPsnCon.setPid(Integer.valueOf(toId));
            relPsnCon.setDelflag("0");
            relPsnMapper.updateSelective(relPsnCon);
        } else {
            RelOrg relOrg = relOrgMapper.selectByPrimaryKey(Integer.valueOf(toId));
            DelRel delRel = new DelRel();
            delRel.setName(relOrg.getOrgName());
            delRel.setDomesticOrForeign(relOrg.getDomesticOrForeign());
            delRel.setIdentifyType(relOrg.getIdentifyType());
            delRel.setIdentifyNumber(relOrg.getUniscCode());
            delRel.setMgerOrg(relOrg.getMgerOrg());
            delRel.setRegulator(rel.getRegulator());
            delRel.setType("ORG");
            delRel.setRelPid(relOrg.getPid());
            delRelMapper.insert(delRel);
            RelOrg relOrgCon = new RelOrg();
            relOrgCon.setPid(Integer.valueOf(toId));
            relOrgCon.setDelflag("0");
            relOrgMapper.updateSelective(relOrgCon);
        }
    }

    @Override
    public List<Integer> getAllCloseRelatives(Integer pid) {
        return relPsnAndOrgOfProMapper.getAllCloseRelatives(pid);
    }

    @Override
    public List<Map<String, Object>> getControllingShareholder(Map<String, Object> map) {
        return relPsnAndOrgOfProMapper.getControllingShareholder(map);
    }

    @Override
    public void insertNeo4jPsnToPro(RelPsn fromPsn, RelPro relPro, RelPsnAndOrgOfPro rel) {
        //插入图数据库
        Neo4jRelVo neo4j = new Neo4jRelVo();
        neo4j.setFromPid(Integer.valueOf(rel.getFromPid()));
        neo4j.setFromName(fromPsn.getName());
        neo4j.setFromIdentifyType(fromPsn.getIdentifyType());
        neo4j.setFromIdentifyNumber(fromPsn.getIdentifyNumber());
        //p-人，o-机构，pro-产品
        neo4j.setFromType("p");
        neo4j.setToPid(Integer.valueOf(rel.getToPid()));
        neo4j.setToName(relPro.getProName());
        neo4j.setToType("pro");
        List<Filter> filters = new Filter.Builder().eq("dictCode", "BUS-RelCategory").eq("itemValue", rel.getRelCategory()).build();
        DictItem dictItem = dictItemService.get(filters);
        neo4j.setRelCategory(dictItem.getItemLabel());
        List<Filter> rfilters = new Filter.Builder().eq("dictCode", "BUS-Rel").eq("itemValue", rel.getRelCategorySub()).build();
        DictItem rdictItem = dictItemService.get(rfilters);
        neo4j.setRelCategorySub(rdictItem.getItemLabel());
        //是否关联方(0-否，1-是)
        neo4j.setFromIsRelParty(StringUtils.isEmpty(fromPsn.getAligCode()) ? 0 : 1);
        //是否内部人
        neo4j.setFromIsInsider(0);
        //是否持股
        neo4j.setFromIsShareholder(StringUtils.isEmpty(fromPsn.getIsShareholder()) || "0".equals(fromPsn.getIsShareholder()) ? 0 : 1);
        //是否关联方
        neo4j.setToIsRelParty(0);
        //是否内部人
        neo4j.setToIsInsider(0);
        //是否持股
        neo4j.setToIsShareholder(0);
        net.sf.json.JSONObject param = net.sf.json.JSONObject.fromObject(neo4j);
        ;
        net.sf.json.JSONObject result = null;
        //如果是删除状态则调用删除关系
        boolean isDel = "0".equals(rel.getDelflag()) || ("00".equals(rel.getRelStatus()) && (rel.getIsValid() == null || "0".equals(rel.getIsValid())));
        //没有定位码
        boolean isGL = rel.getFromAligCode() == null && rel.getToAligCode() == null;
        if (isDel) {
            result = Neo4jUtil.removeNeo4jRel(param);
        } else {
            if (!isGL) {
                result = Neo4jUtil.addNeo4jRel(param);
            }
        }
        if (result == null) {
            logger.info(fromPsn.getName() + "图数据库保存失败" + relPro.getProName());
//            throw new Exception("图数据库保存失败");
        } else {
            String code = (String) result.get("code");
            if ("000".equals(code)) {
                logger.info(fromPsn.getName() + "图数据库保存成功" + relPro.getProName());
            }
        }
    }

    @Override
    public void insertNeo4jOrgToPro(RelOrg fromOrg, RelPro relPro, RelPsnAndOrgOfPro rel) {
        //插入图数据库
        Neo4jRelVo neo4j = new Neo4jRelVo();
        neo4j.setFromPid(Integer.valueOf(rel.getFromPid()));
        neo4j.setFromName(fromOrg.getOrgName());
        neo4j.setFromIdentifyType(fromOrg.getIdentifyType());
        neo4j.setFromIdentifyNumber(fromOrg.getUniscCode());
        //p-人，o-机构，pro-产品
        neo4j.setFromType("o");
        neo4j.setToPid(Integer.valueOf(rel.getToPid()));
        neo4j.setToName(relPro.getProName());
        neo4j.setToIdentifyNumber(relPro.getProProvider());
        neo4j.setToIdentifyType(relPro.getProType());
        neo4j.setToType("o");
        List<Filter> filters = new Filter.Builder().eq("dictCode", "BUS-RelCategory").eq("itemValue", rel.getRelCategory()).build();
        DictItem dictItem = dictItemService.get(filters);
        neo4j.setRelCategory(dictItem.getItemLabel());
        List<Filter> rfilters = new Filter.Builder().eq("dictCode", "BUS-Rel").eq("itemValue", rel.getRelCategorySub()).build();
        DictItem rdictItem = dictItemService.get(rfilters);
        neo4j.setRelCategorySub(rdictItem.getItemLabel());
        //是否关联方(0-否，1-是)
        neo4j.setFromIsRelParty(StringUtils.isEmpty(fromOrg.getAligCode()) ? 0 : 1);
        //是否内部人
        neo4j.setFromIsInsider(StringUtils.isEmpty(fromOrg.getIsInsider()) || "0".equals(fromOrg.getIsInsider()) ? 0 : 1);
        //是否持股
        neo4j.setFromIsShareholder(StringUtils.isEmpty(fromOrg.getIsShareholder()) || "0".equals(fromOrg.getIsShareholder()) ? 0 : 1);
        //是否关联方
        neo4j.setToIsRelParty(0);
        //是否内部人
        neo4j.setToIsInsider(0);
        //是否持股
        neo4j.setToIsShareholder(0);
        net.sf.json.JSONObject param = net.sf.json.JSONObject.fromObject(neo4j);
        ;
        net.sf.json.JSONObject result = null;
        //如果是删除状态则调用删除关系
        boolean isDel = "0".equals(rel.getDelflag()) || ("00".equals(rel.getRelStatus()) && (rel.getIsValid() == null || "0".equals(rel.getIsValid())));
        //没有定位码
        boolean isGL = rel.getFromAligCode() == null && rel.getToAligCode() == null;
        if (isDel) {
            result = Neo4jUtil.removeNeo4jRel(param);
        } else {
            if (!isGL) {
                result = Neo4jUtil.addNeo4jRel(param);
            }
        }
        if (result == null) {
            logger.info(fromOrg.getOrgName() + "图数据库保存失败" + relPro.getProName());
//            throw new Exception("图数据库保存失败");
        } else {
            String code = (String) result.get("code");
            if ("000".equals(code)) {
                logger.info(fromOrg.getOrgName() + "图数据库保存成功" + relPro.getProName());
            }
        }
    }

    @Override
    public void updateNeo4j(Map<String, Object> map, String nowDate) {
        String tag = (String) map.get("tag");
        List<RelPsnAndOrgOfPro> rels = new ArrayList<>();
        //分批查询导入
        Page<RelPsnAndOrgOfPro> page = new Page<>(1, 100);
        PageHelper.start(page);
        if ("all".equals(tag)) {
            rels = this.selectAll();
        } else {
            List<Filter> filters = new Filter.Builder().like("modifiedTime", nowDate).build();
            rels = this.select(filters);
        }
        PageHelper.end(page, rels);
        //总页数
        int pageSize = page.getPages();
        List<RelPsnAndOrgOfPro> exRels = page.getResult();
        //开始抽取
        startUpNeo4j(exRels);
        for (int i = 2; i <= pageSize; i++) {
            //从第二页开始循环
            page = new Page<>(i, 100);
            PageHelper.start(page);
            if ("all".equals(tag)) {
                rels = this.selectAll();
            } else {
                List<Filter> filters = new Filter.Builder().like("modifiedTime", nowDate).build();
                rels = this.select(filters);
            }
            PageHelper.end(page, rels);
            exRels = page.getResult();
            //开始抽取
            startUpNeo4j(exRels);
        }
    }

    @Override
    public List<Map<String, Object>> getToAlignCodeByFromPidAndAlign(Integer fromPid, String fromAlignCode, String type) {
        return relPsnAndOrgOfProMapper.getToAlignCodeByFromPidAndAlign(fromPid, fromAlignCode, type);
    }

    @Override
    public void updateAlignCodeByPid(Integer pid, String fromAlignCode, String toAlignCode) {
        relPsnAndOrgOfProMapper.updateAlignCodeByPid(pid, fromAlignCode, toAlignCode);
    }

    private void startUpNeo4j(List<RelPsnAndOrgOfPro> rels) {
        //遍历所有关系更新图数据库
        /*for (RelPsnAndOrgOfPro rel : rels) {
            String fromType = rel.getFromType();
            String toType = rel.getToType();
            RelPsn fromPsn = new RelPsn();
            RelPsn toPsn = new RelPsn();
            RelOrg fromOrg = new RelOrg();
            RelOrg toOrg = new RelOrg();
            RelPro fromPro = new RelPro();
            RelPro toPro = new RelPro();
            //人->产品
            if ("PSN".equals(fromType) && "PRO".equals(toType)) {
                fromPsn = relPsnService.get(Integer.valueOf(rel.getFromPid()));
                toPro = relProService.get(Integer.valueOf(rel.getToPid()));
                //更新neo4j
                insertNeo4jPsnToPro(fromPsn, toPro, rel);
            }//产品->机构
            else if ("PRO".equals(fromType) && "ORG".equals(toType)) {
                fromPro = relProService.get(Integer.valueOf(rel.getFromPid()));
                toOrg = relOrgService.get(Integer.valueOf(rel.getToPid()));
                //更新neo4j
                relOrgService.insertNeo4jPROTO(fromPro, toOrg, rel);
            }//机构->产品
            else if ("ORG".equals(fromType) && "PRO".equals(toType)) {
                fromOrg = relOrgService.get(Integer.valueOf(rel.getFromPid()));
                toPsn = relPsnService.get(Integer.valueOf(rel.getToPid()));
                //更新neo4j
                insertNeo4jOrgToPro(fromOrg, toPro, rel);
            }//产品->人
            else if ("PRO".equals(fromType) && "PSN".equals(toType)) {
                fromPro = relProService.get(Integer.valueOf(rel.getFromPid()));
                toPsn = relPsnService.get(Integer.valueOf(rel.getToPid()));
                //更新neo4j
                relPsnService.insertNeo4jProToPsn(fromPro, toPsn, rel);
            }
        }*/
    }



}