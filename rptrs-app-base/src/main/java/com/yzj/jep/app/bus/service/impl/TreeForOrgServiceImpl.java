package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.mapper.RelDicMapMapper;
import com.yzj.jep.app.bus.mapper.RelMapper;
import com.yzj.jep.app.bus.mapper.RelOrgMapper;
import com.yzj.jep.app.bus.mapper.RelPsnMapper;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.TreeForOrgService;
import com.yzj.jep.app.bus.vo.AffiliateVo;
import com.yzj.jep.app.bus.vo.SaveTreeMsgVo;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.mybatis.helper.EntityHelper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import com.yzj.jep.web.code.WebCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TreeForOrgServiceImpl extends MyBatisCRUDService<Rel, Integer> implements TreeForOrgService {

    Logger logger = LoggerFactory.getLogger(TreeForOrgServiceImpl.class);

    @Autowired
    private RelMapper relMapper;

    @Autowired
    private RelPsnMapper relPsnMapper;

    @Autowired
    private RelOrgMapper relOrgMapper;

    @Autowired
    private RelDicMapMapper relDicMapMapper;

    @Override
    public Mapper<Rel, Integer> getMapper() {
        return relMapper;
    }

    //查询源主体为机构的机构信息
    @Override
    public List<Map<String, Object>> queryOrgForOrgFrom(Integer pid) {
        List<Map<String, Object>> result = new ArrayList<>();
        if (pid != null) {
            List<Map<String, Object>> maps = relMapper.queryOrgForOrgFrom(pid);
            if (!maps.isEmpty()) {
                result = maps;
            }
        }
        return result;
    }

    //查询源主体为机构的自然人信息
    @Override
    public List<Map<String, Object>> queryPsnForOrgFrom(Integer pid) {
        List<Map<String, Object>> result = new ArrayList<>();
        if (pid != null) {
            List<Map<String, Object>> maps = relMapper.queryPsnForOrgFrom(pid);
            if (!maps.isEmpty()) {
                result = maps;
            }
        }
        return result;
    }

    //查询源主体为自然人的机构信息
    @Override
    public List<Map<String, Object>> queryOrgForPsnFrom(Integer pid) {
        List<Map<String, Object>> result = new ArrayList<>();
        if (pid != null) {
            List<Map<String, Object>> maps = relMapper.queryOrgForPsnFrom(pid);
            if (!maps.isEmpty()) {
                result = maps;
            }
        }
        return result;
    }

    //查询源主体为自然人的自然人信息
    @Override
    public List<Map<String, Object>> queryPsnForPsnFrom(Integer pid) {
        List<Map<String, Object>> result = new ArrayList<>();
        if (pid != null) {
            List<Map<String, Object>> maps = relMapper.queryPsnForPsnFrom(pid);
            if (!maps.isEmpty()) {
                result = maps;
            }
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> querySusOrgForOrgFrom(Integer pid) {
        List<Map<String, Object>> result = new ArrayList<>();
        if (pid != null) {
            List<Map<String, Object>> maps = relMapper.querySusOrgForOrgFrom(pid);
            if (!maps.isEmpty()) {
                result = maps;
            }
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> querySusPsnForOrgFrom(Integer pid) {
        List<Map<String, Object>> result = new ArrayList<>();
        if (pid != null) {
            List<Map<String, Object>> maps = relMapper.querySusPsnForOrgFrom(pid);
            if (!maps.isEmpty()) {
                result = maps;
            }
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> querySusOrgForPsnFrom(Integer pid) {
        List<Map<String, Object>> result = new ArrayList<>();
        if (pid != null) {
            List<Map<String, Object>> maps = relMapper.querySusOrgForPsnFrom(pid);
            if (!maps.isEmpty()) {
                result = maps;
            }
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> querySusPsnForPsnFrom(Integer pid) {
        List<Map<String, Object>> result = new ArrayList<>();
        if (pid != null) {
            List<Map<String, Object>> maps = relMapper.querySusPsnForPsnFrom(pid);
            if (!maps.isEmpty()) {
                result = maps;
            }
        }
        return result;
    }

    @Override
    public List<AffiliateVo> selectAffiliateMessageForOrg(RelOrg relOrg) {
        List<AffiliateVo> affiliateVos = new ArrayList<>();
        List<Map<String, Object>> mapList = relOrgMapper.selectAffiliateMessageForOrg(relOrg);
        if (mapList != null && mapList.size() > 0) {
            for (Map<String, Object> objectMap : mapList) {
                AffiliateVo affiliateVo = new AffiliateVo();
                if (objectMap.containsKey("pid")) {
                    affiliateVo.setPid(new Integer(String.valueOf(objectMap.get("pid"))));
                }
                if (objectMap.containsKey("org_name")) {
                    affiliateVo.setName(objectMap.get("org_name").toString());
                }
                if (objectMap.containsKey("identify_type")) {
                    affiliateVo.setIdentifyType(objectMap.get("identify_type").toString());
                }
                if (objectMap.containsKey("unisc_code")) {
                    affiliateVo.setIdentifyNumber(objectMap.get("unisc_code").toString());
                }
                if (objectMap.containsKey("shareholders_code")) {
                    affiliateVo.setShareholdersCode(objectMap.get("shareholders_code").toString());
                }
                affiliateVo.setType("ORG");
                affiliateVos.add(affiliateVo);
            }
        }
        return affiliateVos;
    }

    @Override
    public List<AffiliateVo> selectAffiliateMessageForSusOrg(RelOrgSuspect relOrgSuspect) {
        List<AffiliateVo> affiliateVos = new ArrayList<>();
        List<Map<String, Object>> mapList = relOrgMapper.selectAffiliateMessageForSusOrg(relOrgSuspect);
        if (mapList != null && mapList.size() > 0) {
            for (Map<String, Object> objectMap : mapList) {
                AffiliateVo affiliateVo = new AffiliateVo();
                if (objectMap.containsKey("pid")) {
                    affiliateVo.setPid(new Integer(String.valueOf(objectMap.get("pid"))));
                }
                if (objectMap.containsKey("org_name")) {
                    affiliateVo.setName(objectMap.get("org_name").toString());
                }
                if (objectMap.containsKey("identify_type")) {
                    affiliateVo.setIdentifyType(objectMap.get("identify_type").toString());
                }
                if (objectMap.containsKey("unisc_code")) {
                    affiliateVo.setIdentifyNumber(objectMap.get("unisc_code").toString());
                }
                if (objectMap.containsKey("shareholders_code")) {
                    affiliateVo.setShareholdersCode(objectMap.get("shareholders_code").toString());
                }
                affiliateVo.setType("ORG");
                affiliateVos.add(affiliateVo);
            }
        }
        return affiliateVos;
    }

    @Override
    public List<AffiliateVo> selectAffiliateMessageForSusPsn(RelPsnSuspect relPsnSuspect) {
        List<AffiliateVo> affiliateVos = new ArrayList<>();
        List<Map<String, Object>> mapList = relPsnMapper.selectAffiliateMessageForSusPsn(relPsnSuspect);
        if (mapList != null && mapList.size() > 0) {
            for (Map<String, Object> objectMap : mapList) {
                AffiliateVo affiliateVo = new AffiliateVo();
                if (objectMap.containsKey("pid")) {
                    affiliateVo.setPid(new Integer(String.valueOf(objectMap.get("pid"))));
                }
                if (objectMap.containsKey("name")) {
                    affiliateVo.setName(objectMap.get("name").toString());
                }
                if (objectMap.containsKey("identify_type")) {
                    affiliateVo.setIdentifyType(objectMap.get("identify_type").toString());
                }
                if (objectMap.containsKey("identify_number")) {
                    affiliateVo.setIdentifyNumber(objectMap.get("identify_number").toString());
                }
                if (objectMap.containsKey("shareholders_code")) {
                    affiliateVo.setShareholdersCode(objectMap.get("shareholders_code").toString());
                }
                affiliateVo.setType("PSN");
                affiliateVos.add(affiliateVo);
            }
        }
        return affiliateVos;
    }

    @Override
    public List<AffiliateVo> selectAffiliateMessageForPsn(RelPsn relPsn) {
        List<AffiliateVo> affiliateVos = new ArrayList<>();
        List<Map<String, Object>> mapList = relPsnMapper.selectAffiliateMessageForPsn(relPsn);
        if (mapList != null && mapList.size() > 0) {
            for (Map<String, Object> objectMap : mapList) {
                AffiliateVo affiliateVo = new AffiliateVo();
                if (objectMap.containsKey("pid")) {
                    affiliateVo.setPid(new Integer(String.valueOf(objectMap.get("pid"))));
                }
                if (objectMap.containsKey("name")) {
                    affiliateVo.setName(objectMap.get("name").toString());
                }
                if (objectMap.containsKey("identify_type")) {
                    affiliateVo.setIdentifyType(objectMap.get("identify_type").toString());
                }
                if (objectMap.containsKey("identify_number")) {
                    affiliateVo.setIdentifyNumber(objectMap.get("identify_number").toString());
                }
                if (objectMap.containsKey("shareholders_code")) {
                    affiliateVo.setShareholdersCode(objectMap.get("shareholders_code").toString());
                }
                affiliateVo.setType("PSN");
                affiliateVos.add(affiliateVo);
            }
        }
        return affiliateVos;
    }

    @Override
    public Map<String, Object> saveRelMsg(SaveTreeMsgVo saveTreeMsgVo) {
        Map<String, Object> tips = new HashMap<String, Object>();
        if (saveTreeMsgVo != null) {
            if (saveTreeMsgVo.getFromPid() != null && saveTreeMsgVo.getToPid() != null && (!StringUtils.isEmpty(saveTreeMsgVo.getFromType())) && (!StringUtils.isEmpty(saveTreeMsgVo.getToType())) && (!StringUtils.isEmpty(saveTreeMsgVo.getToRelCategory())) && (!StringUtils.isEmpty(saveTreeMsgVo.getFromRelCategory()))) {
                List<Filter> filterForRelDicMap = new Filter.Builder().eq("parentCode", saveTreeMsgVo.getFromRelCategory()).eq("subCode", saveTreeMsgVo.getToRelCategory()).build();
                List<RelDicMap> relDicMaps = relDicMapMapper.selectByFilter(EntityHelper.f2f(RelDicMap.class, filterForRelDicMap));
                if (relDicMaps != null && relDicMaps.size() > 0) {
                    RelDicMap relDicMap = relDicMaps.get(0);
                    String relType = relDicMap.getSubRelClass();
                    if ("3".equals(relType)) {
                        Rel relType1 = new Rel();
                        BeanUtils.copyProperties(saveTreeMsgVo, relType1);
                        relType1.setFromPid(saveTreeMsgVo.getFromPid().toString());
                        relType1.setToPid(saveTreeMsgVo.getToPid().toString());
                        relType1.setRelCategory(saveTreeMsgVo.getToRelCategory());
                        relType1.setRelType("3");
                        relType1.setDelflag("1");
                        //设置为不展示
                        relType1.setIsShow("0");
                        relMapper.insertForMapper(relType1);
                    }
                    if ("1".equals(relType)) {
                        Rel relType1 = new Rel();
                        BeanUtils.copyProperties(saveTreeMsgVo, relType1);
                        relType1.setFromPid(saveTreeMsgVo.getFromPid().toString());
                        relType1.setToPid(saveTreeMsgVo.getToPid().toString());
                        relType1.setRelCategory(saveTreeMsgVo.getToRelCategory());
                        relType1.setRelType("1");
                        relType1.setDelflag("1");
                        //设置为不展示
                        relType1.setIsShow("0");
                        relMapper.insertForMapper(relType1);
                    }
                    if ("2".equals(relType)) {
                        Rel relType2 = new Rel();
                        BeanUtils.copyProperties(saveTreeMsgVo, relType2);
                        relType2.setFromPid(saveTreeMsgVo.getFromPid().toString());
                        relType2.setToPid(saveTreeMsgVo.getToPid().toString());
                        relType2.setRelCategory(saveTreeMsgVo.getToRelCategory());
                        relType2.setRelType("2");
                        relType2.setDelflag("1");
                        //设置为不展示
                        relType2.setIsShow("0");
                        relMapper.insertSelective(relType2);
                    }
                    tips.put(WebCode.SUCCESS, true);
                    tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
                } else {
                    tips.put(WebCode.SUCCESS, false);
                    tips.put(WebCode.MSG, WebCode.FAILURE_SAVE);
                }
            } else {
                tips.put(WebCode.SUCCESS, false);
                tips.put(WebCode.MSG, WebCode.FAILURE_SAVE);
            }
        } else {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_SAVE);
        }
        return tips;
    }

    @Override
    public Map<String, Object> saveRelSusMsg(SaveTreeMsgVo saveTreeMsgVo) {
        Map<String, Object> tips = new HashMap<String, Object>();
        if (saveTreeMsgVo != null) {
            if (saveTreeMsgVo.getFromPid() != null && saveTreeMsgVo.getToPid() != null && (!StringUtils.isEmpty(saveTreeMsgVo.getFromType())) && (!StringUtils.isEmpty(saveTreeMsgVo.getToType())) && (!StringUtils.isEmpty(saveTreeMsgVo.getToRelCategory())) && (!StringUtils.isEmpty(saveTreeMsgVo.getFromRelCategory()))) {
                List<Filter> filterForRelDicMap = new Filter.Builder().eq("parentCode", saveTreeMsgVo.getFromRelCategory()).eq("subCode", saveTreeMsgVo.getToRelCategory()).build();
                List<RelDicMap> relDicMaps = relDicMapMapper.selectByFilter(EntityHelper.f2f(RelDicMap.class, filterForRelDicMap));
                if (relDicMaps != null && relDicMaps.size() > 0) {
                    RelDicMap relDicMap = relDicMaps.get(0);
                    String relType = relDicMap.getSubRelClass();
                    if ("3".equals(relType)) {
                        Rel relType1 = new Rel();
                        BeanUtils.copyProperties(saveTreeMsgVo, relType1);
                        relType1.setFromPid(saveTreeMsgVo.getFromPid().toString());
                        relType1.setToPid(saveTreeMsgVo.getToPid().toString());
                        relType1.setRelCategory(saveTreeMsgVo.getToRelCategory());
                        relType1.setRelType("3");
                        relType1.setDelflag("1");
                        //设置为 不展示   此处的不展示代表是疑似的信息
                        relType1.setIsShow("0");
                        Integer relPid = relMapper.insertForMapper(relType1);
                        saveTreeMsgVo.setRelPid(relPid);
                    }
                    if ("1".equals(relType)) {
                        Rel relType1 = new Rel();
                        BeanUtils.copyProperties(saveTreeMsgVo, relType1);
                        relType1.setFromPid(saveTreeMsgVo.getFromPid().toString());
                        relType1.setToPid(saveTreeMsgVo.getToPid().toString());
                        relType1.setRelCategory(saveTreeMsgVo.getToRelCategory());
                        relType1.setRelType("1");
                        relType1.setDelflag("1");
                        //设置为 不展示
                        relType1.setIsShow("0");
                        Integer relPid = relMapper.insertForMapper(relType1);
                        saveTreeMsgVo.setRelPid(relPid);
                    }
                    if ("2".equals(relType)) {
                        Rel relType2 = new Rel();
                        BeanUtils.copyProperties(saveTreeMsgVo, relType2);
                        relType2.setFromPid(saveTreeMsgVo.getFromPid().toString());
                        relType2.setToPid(saveTreeMsgVo.getToPid().toString());
                        relType2.setRelCategory(saveTreeMsgVo.getToRelCategory());
                        relType2.setRelType("2");
                        relType2.setDelflag("0");
                        relType2.setIsShow("0");
                        Integer relPid = relMapper.insertSelective(relType2);
                        saveTreeMsgVo.setRelPid(relPid);
                    }
                    tips.put("saveTreeMsgVo", saveTreeMsgVo);
                    tips.put(WebCode.SUCCESS, true);
                    tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
                } else {
                    tips.put(WebCode.SUCCESS, false);
                    tips.put(WebCode.MSG, WebCode.FAILURE_SAVE);
                }
            } else {
                tips.put(WebCode.SUCCESS, false);
                tips.put(WebCode.MSG, WebCode.FAILURE_SAVE);
            }
        } else {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_SAVE);
        }
        return tips;
    }
}
