/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月23日 13:59:27
 *
 */

package com.yzj.jep.app.bus.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.yzj.jep.app.bus.mapper.DelRelMapper;
import com.yzj.jep.app.bus.mapper.RelMapper;
import com.yzj.jep.app.bus.mapper.RelOrgMapper;
import com.yzj.jep.app.bus.mapper.RelPsnMapper;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.app.bus.vo.Neo4jRelVo;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.IntegerUtils;
import com.yzj.jep.app.util.Neo4jUtil;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
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

import java.util.*;


@Service
@Transactional
public class RelServiceImpl extends MyBatisCRUDService<Rel, Integer> implements RelService {

    Logger logger = LoggerFactory.getLogger(RelServiceImpl.class);

    @Autowired
    private RelMapper relMapper;

    @Autowired
    private RelPsnMapper relPsnMapper;

    @Autowired
    private RelPsnService relPsnService;

    @Autowired
    private RelOrgService relOrgService;

    @Autowired
    private RelProService relProService;

    @Autowired
    private RelOrgMapper relOrgMapper;

    @Autowired
    private DelRelService delRelService;
    @Autowired
    private DelRelMapper delRelMapper;

    @Autowired
    private RelService relService;

    @Autowired
    private DictItemService dictItemService;

    private List<Integer> relPidList = new ArrayList<>();


    @Override
    public Mapper<Rel, Integer> getMapper() {
        return relMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeRel(String fromId, String toId, String fromType, String toType) {
        Rel rel = new Rel();
        rel.setToPid(toId);
        rel.setToType(toType);
        rel.setFromPid(fromId);
        rel.setFromType(fromType);
        Integer pid = relMapper.select(rel).get(0).getPid();
        rel.setPid(pid);
        if ("1".equals(relMapper.select(rel).get(0).getDelflag())) {
            rel.setDelflag("0");
            relMapper.updateSelective(rel);
            logger.debug("解除关联关系，frompid[{}],fromtype[{}],topid[{}],totype[{}]", rel.getFromPid(), rel.getFromType(), rel.getToPid(), rel.getToType());
            createNewDelRel(pid, "to");
        }
        Rel relCond = new Rel();
        relCond.setToType(toType);
        relCond.setToPid(toId);
        relCond.setDelflag("1");
        List<Rel> rels = relMapper.select(relCond);
        if (rels.size() > 0) {
            Rel relCon2 = new Rel();
            relCon2.setToType(toType);
            relCon2.setToPid(toId);
            relCon2.setDelflag("1");
            List<Rel> relList = relMapper.select(relCon2);
            for (Rel rel1 : relList) {
                String fromPid = rel1.getFromPid();
                String fromType1 = rel1.getFromType();
                Rel rel2 = new Rel();
                rel2.setToPid(fromPid);
                rel2.setToType(fromType1);
                rel2.setDelflag("1");
                List<Rel> relList1 = relMapper.select(rel2);
                if (relList1.size() > 0) {
                    continue;
                } else {
                    relCon2.setPid(rel1.getPid());
                    relCon2.setFromPid(fromPid);
                    relCon2.setFromType(fromType1);
                    relCon2.setDelflag("0");
                    relMapper.updateSelective(relCon2);
                    logger.debug("解除关联关系，frompid[{}],fromtype[{}],topid[{}],totype[{}]", rel.getFromPid(), rel.getFromType(), rel.getToPid(), rel.getToType());
                    createNewDelRel(rel1.getPid(), "from");
                }
            }
        }
        Rel relCon = new Rel();
        relCon.setToPid(toId);
        relCon.setToType(toType);
        relCon.setDelflag("1");
        List<Rel> relList = relMapper.select(relCon);
        if (relList.size() > 0) {
            return;
        }
        Rel relCond1 = new Rel();
        relCond1.setFromType(toType);
        relCond1.setFromPid(toId);
        List<Rel> relList1 = relMapper.select(relCond1);
        for (Rel rel1 : relList1) {
            removeRel(rel1.getFromPid(), rel1.getToPid(), rel1.getFromType(), rel1.getToType());
        }
    }

    @Override
    public List<Rel> getUpInfoOnOrg(Integer pid) {
        return null;
    }

    @Override
    public List<Rel> getUpInfoOnPsn(Integer pid) {
        return null;
    }

    @Override
    public List<PsnAndOrgVo> selectPsnOrOrg(String pid) {
        List<PsnAndOrgVo> psnAndOrgVos = relMapper.queryPsnOrOrg(pid);
        return psnAndOrgVos;
    }


    private void createNewDelRel(Integer relPid, String type) {
        logger.debug("添加解除关系记录...relPid[{}],type[{}]", relPid, type);
        Rel rel = relMapper.selectByPrimaryKey(relPid);
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
        return relMapper.getAllCloseRelatives(pid);
    }

    @Override
    public List<Map<String, Object>> getControllingShareholder(Map<String, Object> map) {
        return relMapper.getControllingShareholder(map);
    }

    /**
     * 进入解除关联库
     */
    @Override
    public void updatePsnOrgValid(Rel rel) {
        String fromType = rel.getFromType();
        UserProfile profile = UserProfileUtils.getUserProfile();
        String toType = rel.getToType();
        if ("PSN".equals(toType)) {
            List<Filter> filtera = new Filter.Builder().eq("toPid", rel.getToPid()).eq("toType", "PSN")
                    .eq("isValid", "1").eq("delflag", "1").build();
            List<Rel> relsa = relService.select(filtera);
            List<Filter> filterb = new Filter.Builder().eq("fromPid", rel.getToPid()).eq("fromType", "PSN")
                    .eq("isValid", "1").eq("delflag", "1").build();
            List<Rel> relsb = relService.select(filterb);
            //无关联关系的时候进解除库，并删除源数据
            if ((relsa == null || relsa.size() < 1) && (relsb == null || relsb.size() < 1)) {
                DelRel delRel = new DelRel();
                RelPsn relPsn = relPsnService.get(Integer.parseInt(rel.getToPid()));
                delRel.setRelPid(relPsn.getPid());
                delRel.setName(relPsn.getName());
                delRel.setIdentifyNumber(relPsn.getIdentifyNumber());
                delRel.setIdentifyType(relPsn.getIdentifyType());
                delRel.setTenantId(profile.getTenantId());
                delRel.setType("PSN");
                delRel.setDomesticOrForeign(relPsn.getDomesticOrForeign());
                delRel.setRegulator(relPsn.getRegulatorOth1());
                delRel.setDelflag("1");
                delRelService.insertSelective(delRel);
                //删除
                relPsn.setDelflag("0");
                relPsn.setEffectRelation("0");
                relPsnService.updateSelective(relPsn);
            }
        }
        if ("ORG".equals(toType)) {
            List<Filter> filtera = new Filter.Builder().eq("toPid", rel.getToPid()).eq("isValid", "1")
                    .eq("toType", "ORG").eq("delflag", "1").build();
            List<Rel> relsa = relService.select(filtera);
            List<Filter> filterb = new Filter.Builder().eq("fromPid", rel.getToPid()).eq("isValid", "1")
                    .eq("fromType", "ORG").eq("delflag", "1").build();
            List<Rel> relsb = relService.select(filterb);
            //无关联关系的时候进解除库，并删除源数据
            if ((relsa == null || relsa.size() < 1) && (relsb == null || relsb.size() < 1)) {
                DelRel delRel = new DelRel();
                RelOrg relOrg = relOrgService.get(Integer.parseInt(rel.getToPid()));
                delRel.setRelPid(relOrg.getPid());
                delRel.setName(relOrg.getOrgName());
                delRel.setIdentifyNumber(relOrg.getUniscCode());
                delRel.setIdentifyType(relOrg.getLegalIdentifyType());
                delRel.setTenantId(profile.getTenantId());
                delRel.setType("ORG");
                delRel.setDomesticOrForeign(relOrg.getDomesticOrForeign());
                delRel.setRegulator(relOrg.getRegulatorOth1());
                delRel.setDelflag("1");
                delRelService.insertSelective(delRel);
                //删除
                relOrg.setDelflag("0");
                relOrg.setEffectRelation("0");
                relOrgService.updateSelective(relOrg);
            }
        }
        if ("PSN".equals(fromType)) {
            List<Filter> filtera = new Filter.Builder().eq("toPid", rel.getFromPid()).eq("isValid", "1")
                    .eq("toType", "PSN").eq("delflag", "1").build();
            List<Rel> relsa = relService.select(filtera);
            List<Filter> filterb = new Filter.Builder().eq("fromPid", rel.getFromPid()).eq("isValid", "1")
                    .eq("fromType", "PSN").eq("delflag", "1").build();
            List<Rel> relsb = relService.select(filterb);
            //无关联关系的时候进解除库，并删除源数据
            if ((relsa == null || relsa.size() < 1) && (relsb == null || relsb.size() < 1)) {
                DelRel delRel = new DelRel();
                RelPsn relPsn = relPsnService.get(Integer.parseInt(rel.getFromPid()));
                delRel.setRelPid(relPsn.getPid());
                delRel.setName(relPsn.getName());
                delRel.setIdentifyNumber(relPsn.getIdentifyNumber());
                delRel.setIdentifyType(relPsn.getIdentifyType());
                delRel.setTenantId(profile.getTenantId());
                delRel.setType("PSN");
                delRel.setDomesticOrForeign(relPsn.getDomesticOrForeign());
                delRel.setRegulator(relPsn.getRegulatorOth1());
                delRel.setDelflag("1");
                delRelService.insertSelective(delRel);
                //删除
                relPsn.setDelflag("0");
                relPsn.setEffectRelation("0");
                relPsnService.updateSelective(relPsn);
            }
        }
        if ("ORG".equals(fromType)) {
            List<Filter> filtera = new Filter.Builder().eq("toPid", rel.getFromPid()).eq("isValid", "1")
                    .eq("toType", "ORG").eq("delflag", "1").build();
            List<Rel> relsa = relService.select(filtera);
            List<Filter> filterb = new Filter.Builder().eq("fromPid", rel.getFromPid()).eq("isValid", "1")
                    .eq("fromType", "ORG").eq("delflag", "1").build();
            List<Rel> relsb = relService.select(filterb);
            //无关联关系的时候进解除库，并删除源数据
            if ((relsa == null || relsa.size() < 1) && (relsb == null || relsb.size() < 1)) {
                DelRel delRel = new DelRel();
                RelOrg relOrg = relOrgService.get(Integer.parseInt(rel.getFromPid()));
                delRel.setRelPid(relOrg.getPid());
                delRel.setName(relOrg.getOrgName());
                delRel.setIdentifyNumber(relOrg.getUniscCode());
                delRel.setIdentifyType(relOrg.getIdentifyType());
                delRel.setTenantId(profile.getTenantId());
                delRel.setType("ORG");
                delRel.setDomesticOrForeign(relOrg.getDomesticOrForeign());
                delRel.setRegulator(relOrg.getRegulatorOth1());
                delRel.setDelflag("1");
                delRelService.insertSelective(delRel);
                //删除
                relOrg.setDelflag("0");
                relOrg.setEffectRelation("0");
                relOrgService.updateSelective(relOrg);
            }
        }
    }

    @Override
    public List<String> selectByFormPid(String fromPid) {

        List list = new ArrayList();
        List<Rel> rels = relMapper.selectByFormPid(fromPid);
        for (Rel rel : rels) {
            String toPid = rel.getToPid();
            list.add(toPid);
        }
        return list;
    }

    @Override
    public List<String> selectByToPid(String toPid) {
        List list = new ArrayList();
        List<Rel> rels = relMapper.selectByToPid(toPid);
        for (Rel rel : rels) {
            String fromPid = rel.getFromPid();
            list.add(fromPid);
        }
        return list;
    }

    @Override
    public void updateNeo4j(Map<String, Object> map, String nowDate) {
        String tag = (String) map.get("tag");
        List<Rel> rels = new ArrayList<>();
        //分批查询导入
        Page<Rel> page = new Page<>(1, 100);
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
        List<Rel> exRels = page.getResult();
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
        public void updateNeo4j1(Map<String, Object> map, String nowDate) {
            String tag = (String) map.get("tag");
            List<Rel> rels = new ArrayList<>();
            if ("all".equals(tag)) {
                rels = this.selectAll();
            } else {
                List<Filter> filters = new Filter.Builder().like("modifiedTime", nowDate).build();
                rels = this.select(filters);
            }
            startUpNeo4j(rels);

        }
    @Override
    public List<Map<String, Object>> getToAlignCodeByFromPidAndAlign(Integer fromPid, String fromAlignCode, String type) {
        return relMapper.getToAlignCodeByFromPidAndAlign(fromPid, fromAlignCode, type);
    }

    @Override
    public void updateAlignCodeByPid(Integer pid, String fromAlignCode, String toAlignCode) {
        relMapper.updateAlignCodeByPid(pid, fromAlignCode, toAlignCode);
    }

    private void startUpNeo4j(List<Rel> rels) {
        //遍历所有关系更新图数据库
        for (Rel rel : rels) {
            String fromType = rel.getFromType();
            String toType = rel.getToType();
            RelPsn fromPsn = new RelPsn();
            RelPsn toPsn = new RelPsn();
            RelOrg fromOrg = new RelOrg();
            RelOrg toOrg = new RelOrg();
            RelPro fromPro = new RelPro();
            RelPro toPro = new RelPro();
            //人->人
            if ("PSN".equals(fromType) && "PSN".equals(toType)) {
                fromPsn = relPsnService.get(Integer.valueOf(rel.getFromPid()));
                toPsn = relPsnService.get(Integer.valueOf(rel.getToPid()));
                //更新neo4j
                relPsnService.insertNeo4jPsnToPsn(fromPsn, toPsn, rel);
            }//人->机构
            else if ("PSN".equals(fromType) && "ORG".equals(toType)) {
                fromPsn = relPsnService.get(Integer.valueOf(rel.getFromPid()));
                toOrg = relOrgService.get(Integer.valueOf(rel.getToPid()));
                //更新neo4j
                relOrgService.insertNeo4jPsnTOrg(fromPsn, toOrg, rel);
            }//人->产品
            else if ("PSN".equals(fromType) && "PRO".equals(toType)) {
                fromPsn = relPsnService.get(Integer.valueOf(rel.getFromPid()));
                toPro = relProService.get(Integer.valueOf(rel.getToPid()));
                //更新neo4j
                relProService.insertNeo4jPsnToPro(fromPsn, toPro, rel);
            }//机构->人
            else if ("ORG".equals(fromType) && "PSN".equals(toType)) {
                fromOrg = relOrgService.get(Integer.valueOf(rel.getFromPid()));
                toPsn = relPsnService.get(Integer.valueOf(rel.getToPid()));
                //更新neo4j
                relPsnService.insertNeo4jOrgToPsn(fromOrg, toPsn, rel);
            }//机构->机构
            else if ("ORG".equals(fromType) && "ORG".equals(toType)) {
                fromOrg = relOrgService.get(Integer.valueOf(rel.getFromPid()));
                toOrg = relOrgService.get(Integer.valueOf(rel.getToPid()));
                //更新neo4j
                relOrgService.insertNeo4jOTO(fromOrg, toOrg, rel);
            }//机构->产品
            else if ("ORG".equals(fromType) && "PRO".equals(toType)) {
                fromOrg = relOrgService.get(Integer.valueOf(rel.getFromPid()));
                toPro = relProService.get(Integer.valueOf(rel.getToPid()));
                //更新neo4j
                relProService.insertNeo4jOrgToPro(fromOrg, toPro, rel);
            } else if ("PRO".equals(fromType) && "PSN".equals(toType)) {
                //产品->人
                fromPro = relProService.get(Integer.valueOf(rel.getFromPid()));
                toPsn = relPsnService.get(Integer.valueOf(rel.getToPid()));
                //更新neo4j
                relPsnService.insertNeo4jProToPsn(fromPro, toPsn, rel);
            } else if ("PRO".equals(fromType) && "ORG".equals(toType)) {
                //产品->机构
                fromPro = relProService.get(Integer.valueOf(rel.getFromPid()));
                toOrg = relOrgService.get(Integer.valueOf(rel.getToPid()));
                //更新neo4j
                relOrgService.insertNeo4jPROTO(fromPro, toOrg, rel);
            }
        }
    }

    /**
     * 查询自然人关联方对应得所有关联关系
     *
     * @param toPid
     * @return
     */
    @Override
    public List<Rel> queryRelForPsn(String toPid) {
        Rel param = new Rel();
        param.setToType("PSN");
        param.setToPid(toPid);
        return relMapper.selectByParam(param);
    }

    /**
     * 查询所有关机构关联方得所有关联关系
     *
     * @param toPid
     * @return
     */
    @Override
    public List<Rel> queryRelForOrg(String toPid) {
        Rel param = new Rel();
        param.setToType("ORG");
        param.setToPid(toPid);
        return relMapper.selectByParam(param);
    }

    @Override
    public int checkRel(String type, String code) {
        return relMapper.checkRel(type,code);
    }

    /**
     * 是否进入解除关联关系库 true是  false否
     *
     * @param pid
     * @param type
     * @return
     */
    @Override
    public List<Integer> removeDelRel(Integer pid, String type) {
        String typePid = type + "-" + String.valueOf(pid);
        Map<String, String> relMap = new HashMap<>();
        relMap.put(typePid, typePid);
        removeRel(relMap);
        List<Filter> relFrom = new Filter.Builder().eq("toPid", pid)
                .eq("toType", type).build();
        List<Rel> relFromList = relService.select(relFrom);
        for (Rel r : relFromList) {
            relPidList.add(r.getPid());
        }
        return relPidList;
    }

    @Override
    public List<RelPsnOrg> getRelPsnOrg() {
        return relMapper.getRelPsnOrg();
    }

    @Override
    public void save(Rel rel) {
        relMapper.save(rel);
    }

    @Override
    public List<Rel>  selectSavePsnOrg(String fromIdentifyNumber, String toIdentifyNumber) {
        return relMapper.selectSavePsnOrg(fromIdentifyNumber, toIdentifyNumber);
    }


    @Transactional(rollbackFor = Exception.class)
    public void removeRel(Map<String, String> relMap) {

        if (relMap.size() == 0) {
            return;
        }
        Integer pid = null;
        String type = null;
        //遍历relMap 取值第一个 ，并拆分key值 ，获取pid 和 type
        for (Map.Entry<String, String> relmaplist : relMap.entrySet()) {
            String key = relmaplist.getKey();
            type = key.split("-")[0];
            pid = Integer.valueOf(key.split("-")[1]);
            break;
        }

//        List<String>  rels = relService.selectByToPid(String.valueOf(pid));
        //查询上级关系自然人、机构
        List<Filter> relFrom = new Filter.Builder().eq("toPid", pid)
                .eq("toType", type).eq("delflag", "1")
                .eq("isValid", 1).build();
        List<Rel> relFromList = relService.select(relFrom);
        if (relFromList.size() == 0) {
            if ("PSN".equals(type)) {
                RelPsn relPsn = relPsnMapper.selectByPrimaryKey(Integer.valueOf(pid));
                DelRel delRel = new DelRel();
                delRel.setName(relPsn.getName());
                delRel.setDomesticOrForeign(relPsn.getDomesticOrForeign());
                delRel.setIdentifyType(relPsn.getIdentifyType());
                delRel.setIdentifyNumber(relPsn.getIdentifyNumber());
                delRel.setMgerOrg(relPsn.getMgerOrg());
                delRel.setRegulator(relPsn.getRegulatorOth1());
//                delRel.setRegulator(rel.getRegulator());
                delRel.setRelPid(relPsn.getPid());
                delRel.setType("PSN");
                delRel.setDelflag("1");
                delRel.setTenantId(relPsn.getTenantId());
                delRelMapper.insertSelective(delRel);
                RelPsn relPsnCon = new RelPsn();
                relPsnCon.setPid(Integer.valueOf(pid));
                relPsnCon.setDelflag("0");
                relPsnMapper.updateSelective(relPsnCon);
                //查询下级
                List<Filter> relTo = new Filter.Builder().eq("fromPid", pid)
                        .eq("fromType", type).eq("delflag", 1)
                        .eq("isValid", 1).build();
                List<Rel> relToList = relService.select(relTo);
                for (Rel rel : relToList) {
                    rel.setIsValid("0");
                    relService.update(rel);
                    relPidList.add(rel.getPid());
                }
            } else {
                RelOrg relOrg = relOrgMapper.selectByPrimaryKey(Integer.valueOf(pid));
                DelRel delRel = new DelRel();
                delRel.setName(relOrg.getOrgName());
                delRel.setDomesticOrForeign(relOrg.getDomesticOrForeign());
                delRel.setIdentifyType(relOrg.getIdentifyType());
                delRel.setIdentifyNumber(relOrg.getUniscCode());
                delRel.setMgerOrg(relOrg.getMgerOrg());
                delRel.setDelflag("1");
                delRel.setRegulator(relOrg.getRegulatorOth1());
                delRel.setType("ORG");
                delRel.setRelPid(relOrg.getPid());
                delRel.setTenantId(relOrg.getTenantId());
                delRelMapper.insert(delRel);
                RelOrg relOrgCon = new RelOrg();
                relOrgCon.setPid(Integer.valueOf(pid));
                relOrgCon.setDelflag("0");
                relOrgMapper.updateSelective(relOrgCon);
                //查询下级
                List<Filter> relTo = new Filter.Builder().eq("fromPid", pid)
                        .eq("fromType", type).eq("delflag", 1)
                        .eq("isValid", 1).build();
                List<Rel> relToList = relService.select(relTo);
                for (Rel rel : relToList) {
                    rel.setIsValid("0");
                    relService.update(rel);
                    relPidList.add(rel.getPid());
                }
            }
            relMap.remove(type + "-" + pid);
            List<Filter> relTo = new Filter.Builder().eq("fromPid", pid)
                    .eq("fromType", type).eq("delflag", 1)
                    .eq("isValid", 1).build();
            List<Rel> relToList = relService.select(relTo);
            for (Rel rel : relToList) {
                String typePid = rel.getToType() + "-" + rel.getToPid();
                relMap.put(typePid, typePid);
            }
            removeRel(relMap);
        }
    }

    @Override
    public List<RelPsnOrg> getRelPsnOrgByNameOrNum(String name, String code) {
        return relMapper.getRelPsnOrgByNameOrNum(name, code);
    }

    /**
     * （新 ）  解除关联关系库
     * @param fromType
     * @param fromId
     */
    @Override
    @Transactional
    public void delRelation(String fromType, String fromId) {
        /**
         * 查询本层关联方关系，如果存在多条关联方关系，不进入解除关联关系库，反之进入
         */
        List<Filter> relFrom = new Filter.Builder().eq("fromPid", fromId)
                .eq("fromType", fromType).eq("delflag", "1")
                .eq("isValid", "1").build();
        List<Rel> relList = relService.select(relFrom);
        if (relList.size() == 0) {
            if ("PSN".equals(fromType)) {
                RelPsn relPsn = relPsnMapper.selectByPrimaryKey(Integer.valueOf(fromId));
                //查询该关联方是否已经在解除关联关系库
                DelRel del = delRelMapper.selectByRelId(relPsn.getPid());
                //如果不存在该关联方
                if(del == null){
                    DelRel delRel = new DelRel();
                    delRel.setName(relPsn.getName());
                    delRel.setDomesticOrForeign(relPsn.getDomesticOrForeign());
                    delRel.setIdentifyType(relPsn.getIdentifyType());
                    delRel.setIdentifyNumber(relPsn.getIdentifyNumber());
                    delRel.setRegulator(relPsn.getRegulatorOth1());
                    delRel.setRelPid(relPsn.getPid());
                    delRel.setType("PSN");
                    delRel.setDelflag("1");
                    delRel.setTenantId(relPsn.getTenantId());
                    delRelMapper.insertSelective(delRel);
                    RelPsn relPsnCon = new RelPsn();
                    relPsnCon.setPid(Integer.valueOf(fromId));
                    relPsnCon.setDelflag("0");
                    relPsnMapper.updateSelective(relPsnCon);
                }

            } else {
                RelOrg relOrg = relOrgMapper.selectByPrimaryKey(Integer.valueOf(fromId));
                //查询该关联方是否已经在解除关联关系库
                DelRel del = delRelMapper.selectByRelId(relOrg.getPid());
                //如果不存在该关联方
                if(del == null){
                    DelRel delRel = new DelRel();
                    delRel.setName(relOrg.getOrgName());
                    delRel.setDomesticOrForeign(relOrg.getDomesticOrForeign());
                    delRel.setIdentifyType(relOrg.getIdentifyType());
                    delRel.setIdentifyNumber(relOrg.getUniscCode());
                    delRel.setDelflag("1");
                    delRel.setType("ORG");
                    delRel.setRelPid(relOrg.getPid());
                    delRel.setTenantId(relOrg.getTenantId());
                    delRelMapper.insert(delRel);
                    RelOrg relOrgCon = new RelOrg();
                    relOrgCon.setPid(Integer.valueOf(fromId));
                    relOrgCon.setDelflag("0");
                    relOrgMapper.updateSelective(relOrgCon);
                }
            }
            //查询下级
            List<Filter> relTo = new Filter.Builder().eq("toPid", fromId)
                    .eq("toType", fromType).eq("delflag", "1")
                    .eq("isValid", "1").build();
            List<Rel> relToList = relService.select(relTo);
            for (Rel rel : relToList) {
                rel.setIsValid("0");
                relService.update(rel);
                //this.delRelation(rel.getFromType(), rel.getToPid());
                this.delRelation(rel.getFromType(), rel.getFromPid());
                //deleteNeo4j(rel);//删除图谱
            }
        }

    }

    @Override
    public List<Rel> queryRelForOrgAndPsn(String toPid) {
        Rel param = new Rel();
        param.setToPid(toPid);
        return relMapper.selectByParam(param);
    }

    @Override
    public List<RelPsnOrg> getRelPsnOrgByNameOrNumFront(String name, String code) {
        return relMapper.getRelPsnOrgByNameOrNumFront(name, code);
    }

    @Override
    public List<RelPsnOrg> getRelPsnOrgFront() {
        return relMapper.getRelPsnOrgFront();
    }

    @Override
    public void insertNeo4j(List<Rel> rel) {
        startUpNeo4j(rel);
    }

    @Override
    public int deleteNeo4j(Rel rel) {
        Neo4jRelVo neo4j = new Neo4jRelVo();
        String fromType = rel.getFromType();
        String toType = rel.getToType();
        net.sf.json.JSONObject result = null;
        if ("ORG".equals(fromType) && "PSN".equals(toType)) {
            //psn-人，org-机构，pro-产品
            net.sf.json.JSONObject param = new net.sf.json.JSONObject();
            param.put("fromPid", rel.getFromPid());
            param.put("toPid", rel.getToPid());
            param.put("fromType", "org");
            param.put("toType", "psn");
            List<Filter> rfilters = new Filter.Builder().eq("dictCode", "BUS-RelZZD").eq("itemValue", rel.getRelPsnOrg()).build();
            DictItem rdictItem = dictItemService.get(rfilters);
            param.put("relPsnOrg", rdictItem.getItemLabel());
            result = Neo4jUtil.removeNeo4jRel(param);
        } else if ("ORG".equals(fromType) && "ORG".equals(toType)) {
            neo4j.setFromPid(Integer.valueOf(rel.getFromPid()));
            neo4j.setFromType("org");
            neo4j.setToPid(Integer.valueOf(rel.getToPid()));
            neo4j.setToType("org");
            List<Filter> rfilters = new Filter.Builder().eq("dictCode", "BUS-RelZZD").eq("itemValue", rel.getRelPsnOrg()).build();
            DictItem rdictItem = dictItemService.get(rfilters);
            neo4j.setRelWithprevious(rdictItem.getItemLabel());
            net.sf.json.JSONObject param = net.sf.json.JSONObject.fromObject(neo4j);
            result = Neo4jUtil.removeNeo4jRel(param);
        } else if ("PSN".equals(fromType) && "PSN".equals(toType)) {
            neo4j.setFromPid(Integer.valueOf(rel.getFromPid()));
            neo4j.setFromType("psn");
            neo4j.setToPid(Integer.valueOf(rel.getToPid()));
            neo4j.setToType("psn");
            List<Filter> rfilters = new Filter.Builder().eq("dictCode", "BUS-RelZZD").eq("itemValue", rel.getRelPsnOrg()).build();
            DictItem rdictItem = dictItemService.get(rfilters);
            neo4j.setRelWithprevious(rdictItem.getItemLabel());
            net.sf.json.JSONObject param = net.sf.json.JSONObject.fromObject(neo4j);
            result = Neo4jUtil.removeNeo4jRel(param);
        } else if ("PSN".equals(fromType) && "ORG".equals(toType)) {
            neo4j.setFromPid(Integer.valueOf(rel.getFromPid()));
            neo4j.setFromType("psn");
            neo4j.setToPid(Integer.valueOf(rel.getToPid()));
            neo4j.setToType("org");
            List<Filter> rfilters = new Filter.Builder().eq("dictCode", "BUS-RelZZD").eq("itemValue", rel.getRelPsnOrg()).build();
            DictItem rdictItem = dictItemService.get(rfilters);
            neo4j.setRelWithprevious(rdictItem.getItemLabel());
            net.sf.json.JSONObject param = net.sf.json.JSONObject.fromObject(neo4j);
            result = Neo4jUtil.removeNeo4jRel(param);
        } else if ("PSN".equals(fromType) && "PRO".equals(toType)) {
            neo4j.setFromPid(Integer.valueOf(rel.getFromPid()));
            neo4j.setFromType("psn");
            neo4j.setToPid(Integer.valueOf(rel.getToPid()));
            neo4j.setToType("pro");
            List<Filter> rfilters = new Filter.Builder().eq("dictCode", "BUS-RelZZD").eq("itemValue", rel.getRelPsnOrg()).build();
            DictItem rdictItem = dictItemService.get(rfilters);
            neo4j.setRelWithprevious(rdictItem.getItemLabel());
            net.sf.json.JSONObject param = net.sf.json.JSONObject.fromObject(neo4j);
            result = Neo4jUtil.removeNeo4jRel(param);
        } else if ("ORG".equals(fromType) && "PRO".equals(toType)) {
            neo4j.setFromPid(Integer.valueOf(rel.getFromPid()));
            neo4j.setFromType("org");
            neo4j.setToPid(Integer.valueOf(rel.getToPid()));
            neo4j.setToType("pro");
            List<Filter> rfilters = new Filter.Builder().eq("dictCode", "BUS-RelZZD").eq("itemValue", rel.getRelPsnOrg()).build();
            DictItem rdictItem = dictItemService.get(rfilters);
            neo4j.setRelWithprevious(rdictItem.getItemLabel());
            net.sf.json.JSONObject param = net.sf.json.JSONObject.fromObject(neo4j);
            result = Neo4jUtil.removeNeo4jRel(param);
        } else if ("PRO".equals(fromType) && "ORG".equals(toType)) {
            neo4j.setFromPid(Integer.valueOf(rel.getFromPid()));
            neo4j.setFromType("pro");
            neo4j.setToPid(Integer.valueOf(rel.getToPid()));
            neo4j.setToType("org");
            List<Filter> rfilters = new Filter.Builder().eq("dictCode", "BUS-RelZZD").eq("itemValue", rel.getRelPsnOrg()).build();
            DictItem rdictItem = dictItemService.get(rfilters);
            neo4j.setRelWithprevious(rdictItem.getItemLabel());
            net.sf.json.JSONObject param = net.sf.json.JSONObject.fromObject(neo4j);
            result = Neo4jUtil.removeNeo4jRel(param);
        } else if ("PRO".equals(fromType) && "PSN".equals(toType)) {
            neo4j.setFromPid(Integer.valueOf(rel.getFromPid()));
            neo4j.setFromType("pro");
            neo4j.setToPid(Integer.valueOf(rel.getToPid()));
            neo4j.setToType("psn");
            List<Filter> rfilters = new Filter.Builder().eq("dictCode", "BUS-RelZZD").eq("itemValue", rel.getRelPsnOrg()).build();
            DictItem rdictItem = dictItemService.get(rfilters);
            neo4j.setRelWithprevious(rdictItem.getItemLabel());
            net.sf.json.JSONObject param = net.sf.json.JSONObject.fromObject(neo4j);
            result = Neo4jUtil.removeNeo4jRel(param);
        }

        if (result != null && "000".equals(result.get("code"))) {
            return 1;
        }
        return 0;
    }

    @Override
    public List<com.yzj.jep.app.design.model.RelInfoVo> queryRelationsByParams(com.yzj.jep.app.design.model.RelInfoVo param) {
        return relMapper.queryRelationsByParams(param);
    }

    @Override
    public List<Rel> queryByParam(Rel rel) {
        return relMapper.selectByParam(rel);
    }

    @Override
    public String getRecordNumber() {
        List<Filter> filters = new Filter.Builder().nnl("recordNumber").build();
        List<Order> orders = new Order.Builder().desc("createTime").build();
        List<Rel> psnDayList = this.select(filters, orders);
        boolean flagExist = CollectionUtils.isNotEmpty(psnDayList);
        String recordNumberNew;
        if(flagExist){
            recordNumberNew = psnDayList.get(0).getRecordNumber();
        }else {
            recordNumberNew = IntegerUtils.recordNumber(flagExist, null);
        }
        return recordNumberNew;
    }

    @Override
    public void isAddRecordNumber(Map<String, Object> tips) {
        String yyyyMM = DateUtil.format(new Date(), "yyyyMM");
        List<String> isRecordNumber = relMapper.isRecordNumber(yyyyMM);
        List<String> strings = relMapper.recordNumber();

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
}
