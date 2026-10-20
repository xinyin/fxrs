/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月23日 14:26:15
 *
 */

package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.mapper.RelMapper;
import com.yzj.jep.app.bus.mapper.RelOrgSuspectMapper;
import com.yzj.jep.app.bus.mapper.RelSuspectMapper;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.app.bus.vo.PersonOrgVo;
import com.yzj.jep.app.bus.vo.PersonRelationVo;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.mybatis.helper.EntityHelper;
import com.yzj.jep.web.code.WebCode;
import org.apache.ibatis.javassist.bytecode.stackmap.BasicBlock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.bus.mapper.RelPsnSuspectMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;

import java.util.*;

@Service
@Transactional
public class RelPsnSuspectServiceImpl extends MyBatisCRUDService<RelPsnSuspect, Integer> implements RelPsnSuspectService {

    Logger logger = LoggerFactory.getLogger(RelPsnSuspectServiceImpl.class);

    @Autowired
    private RelPsnSuspectMapper relPsnSuspectMapper;

    @Autowired
    private RelOrgSuspectMapper relOrgSuspectMapper;

    @Autowired
    private RelSuspectMapper relSuspectMapper;

    @Autowired
    private RelMapper relMapper;

    @Autowired
    private RelDictService relDictService;

    @Autowired
    private RelSuspectService relSuspectService;

    @Autowired
    private RelPsnService relPsnService;

    @Autowired
    private RelService relService;


    @Override
    public Mapper<RelPsnSuspect, Integer> getMapper() {
        return relPsnSuspectMapper;
    }

    /*
     * 根据自然人的主键查询附属信息
     * */
    @Override
    public List<PersonRelationVo> queryPsnRelation(Integer pid) {
        List<PersonRelationVo> personRelationVoList = new ArrayList<>();
        List<Map<String, Object>> map = relPsnSuspectMapper.queryPsnRelation(pid);
        if (map != null && map.size() > 0) {
            for (Map<String, Object> stringStringMap : map) {
                PersonRelationVo personRelationVo = new PersonRelationVo();
                if (stringStringMap.containsKey("psnpid") &&
                        stringStringMap.get("psnpid") != null &&
                        stringStringMap.get("psnpid") != "") {
                    personRelationVo.setPidForPsnTo(new Integer(String.valueOf(stringStringMap.get("psnpid"))));
                }
                if (stringStringMap.containsKey("PSNPID") &&
                        stringStringMap.get("PSNPID") != null &&
                        stringStringMap.get("PSNPID") != "") {
                    personRelationVo.setPidForPsnTo(new Integer(String.valueOf(stringStringMap.get("PSNPID"))));
                }
                if (stringStringMap.containsKey("identify_type") &&
                        stringStringMap.get("identify_type") != null &&
                        stringStringMap.get("identify_type") != "") {
                    personRelationVo.setIdentifyType(stringStringMap.get("identify_type").toString());
                }
                if (stringStringMap.containsKey("IDENTIFY_TYPE") &&
                        stringStringMap.get("IDENTIFY_TYPE") != null &&
                        stringStringMap.get("IDENTIFY_TYPE") != "") {
                    personRelationVo.setIdentifyType(stringStringMap.get("IDENTIFY_TYPE").toString());
                }
                if (stringStringMap.containsKey("identify_number") &&
                        stringStringMap.get("identify_number") != null &&
                        stringStringMap.get("identify_number") != "") {
                    personRelationVo.setIdentifyNumber(stringStringMap.get("identify_number").toString());
                }
                if (stringStringMap.containsKey("IDENTIFY_NUMBER") &&
                        stringStringMap.get("IDENTIFY_NUMBER") != null &&
                        stringStringMap.get("IDENTIFY_NUMBER") != "") {
                    personRelationVo.setIdentifyNumber(stringStringMap.get("IDENTIFY_NUMBER").toString());
                }
                if (stringStringMap.containsKey("name_") &&
                        stringStringMap.get("name_") != null &&
                        stringStringMap.get("name_") != "") {
                    personRelationVo.setName(stringStringMap.get("name_").toString());
                }
                if (stringStringMap.containsKey("NAME_") &&
                        stringStringMap.get("NAME_") != null &&
                        stringStringMap.get("NAME_") != "") {
                    personRelationVo.setName(stringStringMap.get("NAME_").toString());
                }
                if (stringStringMap.containsKey("sex") &&
                        stringStringMap.get("sex") != null &&
                        stringStringMap.get("sex") != "") {
                    personRelationVo.setSex(stringStringMap.get("sex").toString());
                }
                if (stringStringMap.containsKey("SEX") &&
                        stringStringMap.get("SEX") != null &&
                        stringStringMap.get("SEX") != "") {
                    personRelationVo.setSex(stringStringMap.get("SEX").toString());
                }
                if (stringStringMap.containsKey("mger_org") &&
                        stringStringMap.get("mger_org") != null &&
                        stringStringMap.get("mger_org") != "") {
                    personRelationVo.setMgerOrg(stringStringMap.get("mger_org").toString());
                }
                if (stringStringMap.containsKey("MGER_ORG") &&
                        stringStringMap.get("MGER_ORG") != null &&
                        stringStringMap.get("MGER_ORG") != "") {
                    personRelationVo.setMgerOrg(stringStringMap.get("MGER_ORG").toString());
                }
                if (stringStringMap.containsKey("race") &&
                        stringStringMap.get("race") != null &&
                        stringStringMap.get("race") != "") {
                    personRelationVo.setRace(stringStringMap.get("race").toString());
                }
                if (stringStringMap.containsKey("RACE") &&
                        stringStringMap.get("RACE") != null &&
                        stringStringMap.get("RACE") != "") {
                    personRelationVo.setRace(stringStringMap.get("RACE").toString());
                }
                if (stringStringMap.containsKey("domestic_or_foreign") &&
                        stringStringMap.get("domestic_or_foreign") != null &&
                        stringStringMap.get("domestic_or_foreign") != "") {
                    personRelationVo.setDomesticOrForeign(stringStringMap.get("domestic_or_foreign").toString());
                }
                if (stringStringMap.containsKey("DOMESTIC_OR_FOREIGN") &&
                        stringStringMap.get("DOMESTIC_OR_FOREIGN") != null &&
                        stringStringMap.get("DOMESTIC_OR_FOREIGN") != "") {
                    personRelationVo.setDomesticOrForeign(stringStringMap.get("DOMESTIC_OR_FOREIGN").toString());
                }
                if (stringStringMap.containsKey("marital_status") &&
                        stringStringMap.get("marital_status") != null &&
                        stringStringMap.get("marital_status") != "") {
                    personRelationVo.setMaritalStatus(stringStringMap.get("marital_status").toString());
                }
                if (stringStringMap.containsKey("MARITAL_STATUS") &&
                        stringStringMap.get("MARITAL_STATUS") != null &&
                        stringStringMap.get("MARITAL_STATUS") != "") {
                    personRelationVo.setMaritalStatus(stringStringMap.get("MARITAL_STATUS").toString());
                }
                if (stringStringMap.containsKey("birthdate") &&
                        stringStringMap.get("birthdate") != null &&
                        stringStringMap.get("birthdate") != "") {
                    personRelationVo.setBirthdate(stringStringMap.get("birthdate").toString());
                }
                if (stringStringMap.containsKey("BIRTHDATE") &&
                        stringStringMap.get("BIRTHDATE") != null &&
                        stringStringMap.get("BIRTHDATE") != "") {
                    personRelationVo.setBirthdate(stringStringMap.get("BIRTHDATE").toString());
                }
                if (stringStringMap.containsKey("mobile") &&
                        stringStringMap.get("mobile") != null &&
                        stringStringMap.get("mobile") != "") {
                    personRelationVo.setMobile(stringStringMap.get("mobile").toString());
                }
                if (stringStringMap.containsKey("MOBILE") &&
                        stringStringMap.get("MOBILE") != null &&
                        stringStringMap.get("MOBILE") != "") {
                    personRelationVo.setMobile(stringStringMap.get("MOBILE").toString());
                }
                if (stringStringMap.containsKey("email") &&
                        stringStringMap.get("email") != null &&
                        stringStringMap.get("email") != "") {
                    personRelationVo.setEmail(stringStringMap.get("email").toString());
                }
                if (stringStringMap.containsKey("EMAIL") &&
                        stringStringMap.get("EMAIL") != null &&
                        stringStringMap.get("EMAIL") != "") {
                    personRelationVo.setEmail(stringStringMap.get("EMAIL").toString());
                }
                if (stringStringMap.containsKey("rel_category") &&
                        stringStringMap.get("rel_category") != null &&
                        stringStringMap.get("rel_category") != "") {
                    personRelationVo.setRelCategory(stringStringMap.get("rel_category").toString());
                }
                if (stringStringMap.containsKey("REL_CATEGORY") &&
                        stringStringMap.get("REL_CATEGORY") != null &&
                        stringStringMap.get("REL_CATEGORY") != "") {
                    personRelationVo.setRelCategory(stringStringMap.get("REL_CATEGORY").toString());
                }
                if (stringStringMap.containsKey("relation") &&
                        stringStringMap.get("relation") != null &&
                        stringStringMap.get("relation") != "") {
                    personRelationVo.setRelation(stringStringMap.get("relation").toString());
                }
                if (stringStringMap.containsKey("RELATION") &&
                        stringStringMap.get("RELATION") != null &&
                        stringStringMap.get("RELATION") != "") {
                    personRelationVo.setRelation(stringStringMap.get("RELATION").toString());
                }
                if (stringStringMap.containsKey("relpid") &&
                        stringStringMap.get("relpid") != null &&
                        stringStringMap.get("relpid") != "") {
                    personRelationVo.setPidForRel(new Integer(String.valueOf(stringStringMap.get("relpid"))));
                }
                if (stringStringMap.containsKey("RELPID") &&
                        stringStringMap.get("RELPID") != null &&
                        stringStringMap.get("RELPID") != "") {
                    personRelationVo.setPidForRel(new Integer(String.valueOf(stringStringMap.get("RELPID"))));
                }
                if (stringStringMap.containsKey("relCategorySub") &&
                        stringStringMap.get("relCategorySub") != null &&
                        stringStringMap.get("relCategorySub") != "") {
                    personRelationVo.setRelCategorySub(stringStringMap.get("relCategorySub").toString());
                }
                if (stringStringMap.containsKey("RELCATEGORYSUB") &&
                        stringStringMap.get("RELCATEGORYSUB") != null &&
                        stringStringMap.get("RELCATEGORYSUB") != "") {
                    personRelationVo.setRelCategorySub(stringStringMap.get("RELCATEGORYSUB").toString());
                }
                if (stringStringMap.containsKey("relStatus") &&
                        stringStringMap.get("relStatus") != null &&
                        stringStringMap.get("relStatus") != "") {
                    personRelationVo.setRelStatus(stringStringMap.get("relStatus").toString());
                }
                if (stringStringMap.containsKey("RELSTATUS") &&
                        stringStringMap.get("RELSTATUS") != null &&
                        stringStringMap.get("RELSTATUS") != "") {
                    personRelationVo.setRelStatus(stringStringMap.get("RELSTATUS").toString());
                }
                if (stringStringMap.containsKey("residentialAddress") &&
                        stringStringMap.get("residentialAddress") != null &&
                        stringStringMap.get("residentialAddress") != "") {
                    personRelationVo.setResidentialAddress(stringStringMap.get("residentialAddress").toString());
                }
                if (stringStringMap.containsKey("RESIDENTIALADDRESS") &&
                        stringStringMap.get("RESIDENTIALADDRESS") != null &&
                        stringStringMap.get("RESIDENTIALADDRESS") != "") {
                    personRelationVo.setResidentialAddress(stringStringMap.get("RESIDENTIALADDRESS").toString());
                }
                if (stringStringMap.containsKey("voteProportion") &&
                        stringStringMap.get("voteProportion") != null &&
                        stringStringMap.get("voteProportion") != "") {
                    personRelationVo.setVoteProportion(stringStringMap.get("voteProportion").toString());
                }
                if (stringStringMap.containsKey("VOTEPROPORTION") &&
                        stringStringMap.get("VOTEPROPORTION") != null &&
                        stringStringMap.get("VOTEPROPORTION") != "") {
                    personRelationVo.setVoteProportion(stringStringMap.get("VOTEPROPORTION").toString());
                }
                personRelationVo.setPidForPsnFrom(pid);
                personRelationVoList.add(personRelationVo);
            }
        }
        return personRelationVoList;
    }

    @Override
    public List<PersonRelationVo> queryPsnRelationDown(Integer pid) {
        List<PersonRelationVo> personRelationVoList = new ArrayList<>();
        List<Map<String, Object>> map = relPsnSuspectMapper.queryPsnRelation(pid);
        if (map != null && map.size() > 0) {
            for (Map<String, Object> stringStringMap : map) {
                PersonRelationVo personRelationVo = new PersonRelationVo();
                if (stringStringMap.containsKey("psnpid") &&
                        stringStringMap.get("psnpid") != null &&
                        stringStringMap.get("psnpid") != "") {
                    personRelationVo.setPidForPsnTo(new Integer(String.valueOf(stringStringMap.get("psnpid"))));
                }
                if (stringStringMap.containsKey("psnpid".toUpperCase()) &&
                        stringStringMap.get("psnpid".toUpperCase()) != null &&
                        stringStringMap.get("psnpid".toUpperCase()) != "") {
                    personRelationVo.setPidForPsnTo(new Integer(String.valueOf(stringStringMap.get("psnpid".toUpperCase()))));
                }
                if (stringStringMap.containsKey("identify_type") &&
                        stringStringMap.get("identify_type") != null &&
                        stringStringMap.get("identify_type") != "") {
                    personRelationVo.setIdentifyType(stringStringMap.get("identify_type").toString());
                }
                if (stringStringMap.containsKey("identify_type".toUpperCase()) &&
                        stringStringMap.get("identify_type".toUpperCase()) != null &&
                        stringStringMap.get("identify_type".toUpperCase()) != "") {
                    personRelationVo.setIdentifyType(stringStringMap.get("identify_type".toUpperCase()).toString());
                }
                if (stringStringMap.containsKey("identify_number") &&
                        stringStringMap.get("identify_number") != null &&
                        stringStringMap.get("identify_number") != "") {
                    personRelationVo.setIdentifyNumber(stringStringMap.get("identify_number").toString());
                }
                if (stringStringMap.containsKey("identify_number".toUpperCase()) &&
                        stringStringMap.get("identify_number".toUpperCase()) != null &&
                        stringStringMap.get("identify_number".toUpperCase()) != "") {
                    personRelationVo.setIdentifyNumber(stringStringMap.get("identify_number".toUpperCase()).toString());
                }
                if (stringStringMap.containsKey("name_") &&
                        stringStringMap.get("name_") != null &&
                        stringStringMap.get("name_") != "") {
                    personRelationVo.setName(stringStringMap.get("name_").toString());
                }
                if (stringStringMap.containsKey("name_".toUpperCase()) &&
                        stringStringMap.get("name_".toUpperCase()) != null &&
                        stringStringMap.get("name_".toUpperCase()) != "") {
                    personRelationVo.setName(stringStringMap.get("name_".toUpperCase()).toString());
                }
                if (stringStringMap.containsKey("sex") &&
                        stringStringMap.get("sex") != null &&
                        stringStringMap.get("sex") != "") {
                    personRelationVo.setSex(stringStringMap.get("sex").toString());
                }
                if (stringStringMap.containsKey("sex".toUpperCase()) &&
                        stringStringMap.get("sex".toUpperCase()) != null &&
                        stringStringMap.get("sex".toUpperCase()) != "") {
                    personRelationVo.setSex(stringStringMap.get("sex".toUpperCase()).toString());
                }
                if (stringStringMap.containsKey("mger_org") &&
                        stringStringMap.get("mger_org") != null &&
                        stringStringMap.get("mger_org") != "") {
                    personRelationVo.setMgerOrg(stringStringMap.get("mger_org").toString());
                }
                if (stringStringMap.containsKey("mger_org".toUpperCase()) &&
                        stringStringMap.get("mger_org".toUpperCase()) != null &&
                        stringStringMap.get("mger_org".toUpperCase()) != "") {
                    personRelationVo.setMgerOrg(stringStringMap.get("mger_org".toUpperCase()).toString());
                }
                if (stringStringMap.containsKey("race") &&
                        stringStringMap.get("race") != null &&
                        stringStringMap.get("race") != "") {
                    personRelationVo.setRace(stringStringMap.get("race").toString());
                }
                if (stringStringMap.containsKey("race".toUpperCase()) &&
                        stringStringMap.get("race".toUpperCase()) != null &&
                        stringStringMap.get("race".toUpperCase()) != "") {
                    personRelationVo.setRace(stringStringMap.get("race".toUpperCase()).toString());
                }
                if (stringStringMap.containsKey("domestic_or_foreign") &&
                        stringStringMap.get("domestic_or_foreign") != null &&
                        stringStringMap.get("domestic_or_foreign") != "") {
                    personRelationVo.setDomesticOrForeign(stringStringMap.get("domestic_or_foreign").toString());
                }
                if (stringStringMap.containsKey("domestic_or_foreign".toUpperCase()) &&
                        stringStringMap.get("domestic_or_foreign".toUpperCase()) != null &&
                        stringStringMap.get("domestic_or_foreign".toUpperCase()) != "") {
                    personRelationVo.setDomesticOrForeign(stringStringMap.get("domestic_or_foreign".toUpperCase()).toString());
                }
                if (stringStringMap.containsKey("marital_status") &&
                        stringStringMap.get("marital_status") != null &&
                        stringStringMap.get("marital_status") != "") {
                    personRelationVo.setMaritalStatus(stringStringMap.get("marital_status").toString());
                }
                if (stringStringMap.containsKey("marital_status".toUpperCase()) &&
                        stringStringMap.get("marital_status".toUpperCase()) != null &&
                        stringStringMap.get("marital_status".toUpperCase()) != "") {
                    personRelationVo.setMaritalStatus(stringStringMap.get("marital_status".toUpperCase()).toString());
                }
                if (stringStringMap.containsKey("birthdate") &&
                        stringStringMap.get("birthdate") != null &&
                        stringStringMap.get("birthdate") != "") {
                    personRelationVo.setBirthdate(stringStringMap.get("birthdate").toString());
                }
                if (stringStringMap.containsKey("birthdate".toUpperCase()) &&
                        stringStringMap.get("birthdate".toUpperCase()) != null &&
                        stringStringMap.get("birthdate".toUpperCase()) != "") {
                    personRelationVo.setBirthdate(stringStringMap.get("birthdate".toUpperCase()).toString());
                }
                if (stringStringMap.containsKey("mobile") &&
                        stringStringMap.get("mobile") != null &&
                        stringStringMap.get("mobile") != "") {
                    personRelationVo.setMobile(stringStringMap.get("mobile").toString());
                }
                if (stringStringMap.containsKey("mobile".toUpperCase()) &&
                        stringStringMap.get("mobile".toUpperCase()) != null &&
                        stringStringMap.get("mobile".toUpperCase()) != "") {
                    personRelationVo.setMobile(stringStringMap.get("mobile".toUpperCase()).toString());
                }
                if (stringStringMap.containsKey("email") &&
                        stringStringMap.get("email") != null &&
                        stringStringMap.get("email") != "") {
                    personRelationVo.setEmail(stringStringMap.get("email").toString());
                }
                if (stringStringMap.containsKey("email".toUpperCase()) &&
                        stringStringMap.get("email".toUpperCase()) != null &&
                        stringStringMap.get("email".toUpperCase()) != "") {
                    personRelationVo.setEmail(stringStringMap.get("email".toUpperCase()).toString());
                }
                if (stringStringMap.containsKey("rel_category") &&
                        stringStringMap.get("rel_category") != null &&
                        stringStringMap.get("rel_category") != "") {
                    personRelationVo.setRelCategory(stringStringMap.get("rel_category").toString());
                }
                if (stringStringMap.containsKey("rel_category".toUpperCase()) &&
                        stringStringMap.get("rel_category".toUpperCase()) != null &&
                        stringStringMap.get("rel_category".toUpperCase()) != "") {
                    personRelationVo.setRelCategory(stringStringMap.get("rel_category".toUpperCase()).toString());
                }
                if (stringStringMap.containsKey("relation") &&
                        stringStringMap.get("relation") != null &&
                        stringStringMap.get("relation") != "") {
                    personRelationVo.setRelation(stringStringMap.get("relation").toString());
                }
                if (stringStringMap.containsKey("relation".toUpperCase()) &&
                        stringStringMap.get("relation".toUpperCase()) != null &&
                        stringStringMap.get("relation".toUpperCase()) != "") {
                    personRelationVo.setRelation(stringStringMap.get("relation".toUpperCase()).toString());
                }
                if (stringStringMap.containsKey("relpid") &&
                        stringStringMap.get("relpid") != null &&
                        stringStringMap.get("relpid") != "") {
                    personRelationVo.setPidForRel(new Integer(String.valueOf(stringStringMap.get("relpid"))));
                }
                if (stringStringMap.containsKey("relpid".toUpperCase()) &&
                        stringStringMap.get("relpid".toUpperCase()) != null &&
                        stringStringMap.get("relpid".toUpperCase()) != "") {
                    personRelationVo.setPidForRel(new Integer(String.valueOf(stringStringMap.get("relpid".toUpperCase()))));
                }
                if (stringStringMap.containsKey("relCategorySub") &&
                        stringStringMap.get("relCategorySub") != null &&
                        stringStringMap.get("relCategorySub") != "") {
                    personRelationVo.setRelCategorySub(stringStringMap.get("relCategorySub").toString());
                }
                if (stringStringMap.containsKey("relCategorySub".toUpperCase()) &&
                        stringStringMap.get("relCategorySub".toUpperCase()) != null &&
                        stringStringMap.get("relCategorySub".toUpperCase()) != "") {
                    personRelationVo.setRelCategorySub(stringStringMap.get("relCategorySub".toUpperCase()).toString());
                }
                if (stringStringMap.containsKey("relStatus") &&
                        stringStringMap.get("relStatus") != null &&
                        stringStringMap.get("relStatus") != "") {
                    personRelationVo.setRelStatus(stringStringMap.get("relStatus").toString());
                }
                if (stringStringMap.containsKey("relStatus".toUpperCase()) &&
                        stringStringMap.get("relStatus".toUpperCase()) != null &&
                        stringStringMap.get("relStatus".toUpperCase()) != "") {
                    personRelationVo.setRelStatus(stringStringMap.get("relStatus".toUpperCase()).toString());
                }
                personRelationVo.setPidForPsnFrom(pid);
                personRelationVoList.add(personRelationVo);
            }
        }
        return personRelationVoList;
    }

    /*
     * 根据自然人主键查询机构信息
     * */
    @Override
    public List<PersonOrgVo> queryPsnOrg(Integer pid) {
        List<PersonOrgVo> personOrgVoList = new ArrayList<>();
        List<Map<String, Object>> maps = relPsnSuspectMapper.queryOrgCompany(pid);
        if (maps != null && maps.size() > 0) {
            for (Map<String, Object> map : maps) {
                PersonOrgVo personOrgVo = new PersonOrgVo();
                if (map.containsKey("orgpid") &&
                        map.get("orgpid") != null &&
                        map.get("orgpid") != "") {
                    personOrgVo.setPidForOrgTo(new Integer(String.valueOf(map.get("orgpid"))));
                }
                if (map.containsKey("orgpid".toUpperCase()) &&
                        map.get("orgpid".toUpperCase()) != null &&
                        map.get("orgpid".toUpperCase()) != "") {
                    personOrgVo.setPidForOrgTo(new Integer(String.valueOf(map.get("orgpid".toUpperCase()))));
                }
                if (map.containsKey("identify_type") &&
                        map.get("identify_type") != null &&
                        map.get("identify_type") != "") {
                    personOrgVo.setIdentifyType(map.get("identify_type").toString());
                }
                if (map.containsKey("identify_type".toUpperCase()) &&
                        map.get("identify_type".toUpperCase()) != null &&
                        map.get("identify_type".toUpperCase()) != "") {
                    personOrgVo.setIdentifyType(map.get("identify_type".toUpperCase()).toString());
                }
                if (map.containsKey("unisc_code") &&
                        map.get("unisc_code") != null &&
                        map.get("unisc_code") != "") {
                    personOrgVo.setUniscCode(map.get("unisc_code").toString());
                }
                if (map.containsKey("unisc_code".toUpperCase()) &&
                        map.get("unisc_code".toUpperCase()) != null &&
                        map.get("unisc_code".toUpperCase()) != "") {
                    personOrgVo.setUniscCode(map.get("unisc_code".toUpperCase()).toString());
                }
                if (map.containsKey("org_name") &&
                        map.get("org_name") != null &&
                        map.get("org_name") != "") {
                    personOrgVo.setOrgName(map.get("org_name").toString());
                }
                if (map.containsKey("org_name".toUpperCase()) &&
                        map.get("org_name".toUpperCase()) != null &&
                        map.get("org_name".toUpperCase()) != "") {
                    personOrgVo.setOrgName(map.get("org_name".toUpperCase()).toString());
                }
                if (map.containsKey("reg_address") &&
                        map.get("reg_address") != null &&
                        map.get("reg_address") != "") {
                    personOrgVo.setRegAddress(map.get("reg_address").toString());
                }
                if (map.containsKey("reg_address".toUpperCase()) &&
                        map.get("reg_address".toUpperCase()) != null &&
                        map.get("reg_address".toUpperCase()) != "") {
                    personOrgVo.setRegAddress(map.get("reg_address".toUpperCase()).toString());
                }
                if (map.containsKey("domestic_or_foreign") &&
                        map.get("domestic_or_foreign") != null &&
                        map.get("domestic_or_foreign") != "") {
                    personOrgVo.setDomesticOrForeign(map.get("domestic_or_foreign").toString());
                }
                if (map.containsKey("domestic_or_foreign".toUpperCase()) &&
                        map.get("domestic_or_foreign".toUpperCase()) != null &&
                        map.get("domestic_or_foreign".toUpperCase()) != "") {
                    personOrgVo.setDomesticOrForeign(map.get("domestic_or_foreign".toUpperCase()).toString());
                }
                if (map.containsKey("mger_org") &&
                        map.get("mger_org") != null &&
                        map.get("mger_org") != "") {
                    personOrgVo.setMgerOrg(map.get("mger_org").toString());
                }
                if (map.containsKey("mger_org".toUpperCase()) &&
                        map.get("mger_org".toUpperCase()) != null &&
                        map.get("mger_org".toUpperCase()) != "") {
                    personOrgVo.setMgerOrg(map.get("mger_org".toUpperCase()).toString());
                }
                if (map.containsKey("org_type") &&
                        map.get("org_type") != null &&
                        map.get("org_type") != "") {
                    personOrgVo.setOrgType(map.get("org_type").toString());
                }
                if (map.containsKey("org_type".toUpperCase()) &&
                        map.get("org_type".toUpperCase()) != null &&
                        map.get("org_type".toUpperCase()) != "") {
                    personOrgVo.setOrgType(map.get("org_type".toUpperCase()).toString());
                }
                if (map.containsKey("business_scope") &&
                        map.get("business_scope") != null &&
                        map.get("business_scope") != "") {
                    personOrgVo.setBusinessScope(map.get("business_scope").toString());
                }
                if (map.containsKey("business_scope".toUpperCase()) &&
                        map.get("business_scope".toUpperCase()) != null &&
                        map.get("business_scope".toUpperCase()) != "") {
                    personOrgVo.setBusinessScope(map.get("business_scope".toUpperCase()).toString());
                }
                if (map.containsKey("reg_capital") &&
                        map.get("reg_capital") != null &&
                        map.get("reg_capital") != "") {
                    Float f0 = Float.parseFloat(map.get("reg_capital").toString());
                    personOrgVo.setRegCapital(f0);
                }
                if (map.containsKey("reg_capital".toUpperCase()) &&
                        map.get("reg_capital".toUpperCase()) != null &&
                        map.get("reg_capital".toUpperCase()) != "") {
                    Float f0 = Float.parseFloat(map.get("reg_capital".toUpperCase()).toString());
                    personOrgVo.setRegCapital(f0);
                }
                if (map.containsKey("reg_capital_currency") &&
                        map.get("reg_capital_currency") != null &&
                        map.get("reg_capital_currency") != "") {
                    personOrgVo.setRegCapitalCurrency(map.get("reg_capital_currency").toString());
                }
                if (map.containsKey("reg_capital_currency".toUpperCase()) &&
                        map.get("reg_capital_currency".toUpperCase()) != null &&
                        map.get("reg_capital_currency".toUpperCase()) != "") {
                    personOrgVo.setRegCapitalCurrency(map.get("reg_capital_currency".toUpperCase()).toString());
                }
                if (map.containsKey("legal_person") &&
                        map.get("legal_person") != null &&
                        map.get("legal_person") != "") {
                    personOrgVo.setLegalPerson(map.get("legal_person").toString());
                }
                if (map.containsKey("legal_person".toUpperCase()) &&
                        map.get("legal_person".toUpperCase()) != null &&
                        map.get("legal_person".toUpperCase()) != "") {
                    personOrgVo.setLegalPerson(map.get("legal_person".toUpperCase()).toString());
                }
                if (map.containsKey("status") &&
                        map.get("status") != null &&
                        map.get("status") != "") {
                    personOrgVo.setStatus(map.get("status").toString());
                }
                if (map.containsKey("status".toUpperCase()) &&
                        map.get("status".toUpperCase()) != null &&
                        map.get("status".toUpperCase()) != "") {
                    personOrgVo.setStatus(map.get("status".toUpperCase()).toString());
                }
                if (map.containsKey("rel_category") &&
                        map.get("rel_category") != null &&
                        map.get("rel_category") != "") {
                    personOrgVo.setRelCategory(map.get("rel_category").toString());
                }
                if (map.containsKey("rel_category".toUpperCase()) &&
                        map.get("rel_category".toUpperCase()) != null &&
                        map.get("rel_category".toUpperCase()) != "") {
                    personOrgVo.setRelCategory(map.get("rel_category".toUpperCase()).toString());
                }
                if (map.containsKey("duty_code") &&
                        map.get("duty_code") != null &&
                        map.get("duty_code") != "") {
                    personOrgVo.setDutyCode(map.get("duty_code").toString());
                }
                if (map.containsKey("duty_code".toUpperCase()) &&
                        map.get("duty_code".toUpperCase()) != null &&
                        map.get("duty_code".toUpperCase()) != "") {
                    personOrgVo.setDutyCode(map.get("duty_code".toUpperCase()).toString());
                }
                if (map.containsKey("shareholding_ratio") &&
                        map.get("shareholding_ratio") != null &&
                        map.get("shareholding_ratio") != "") {
                    Float fl = Float.parseFloat(map.get("shareholding_ratio").toString());
                    personOrgVo.setShareholdingRatio(fl);
                }
                if (map.containsKey("shareholding_ratio".toUpperCase()) &&
                        map.get("shareholding_ratio".toUpperCase()) != null &&
                        map.get("shareholding_ratio".toUpperCase()) != "") {
                    Float fl = Float.parseFloat(map.get("shareholding_ratio".toUpperCase()).toString());
                    personOrgVo.setShareholdingRatio(fl);
                }
                if (map.containsKey("relpid") &&
                        map.get("relpid") != null &&
                        map.get("relpid") != "") {
                    personOrgVo.setPidForRel(new Integer(String.valueOf(map.get("relpid"))));
                }
                if (map.containsKey("relpid".toUpperCase()) &&
                        map.get("relpid".toUpperCase()) != null &&
                        map.get("relpid".toUpperCase()) != "") {
                    personOrgVo.setPidForRel(new Integer(String.valueOf(map.get("relpid".toUpperCase()))));
                }
                if (map.containsKey("relCategorySub") &&
                        map.get("relCategorySub") != null &&
                        map.get("relCategorySub") != "") {
                    personOrgVo.setRelCategorySub(map.get("relCategorySub").toString());
                }
                if (map.containsKey("relCategorySub".toUpperCase()) &&
                        map.get("relCategorySub".toUpperCase()) != null &&
                        map.get("relCategorySub".toUpperCase()) != "") {
                    personOrgVo.setRelCategorySub(map.get("relCategorySub".toUpperCase()).toString());
                }
                personOrgVo.setPidForOrgFrom(pid);
                personOrgVoList.add(personOrgVo);
            }
        }
        return personOrgVoList;
    }

    /*
     * 自然人信息删除
     * */
    @Override
    public Map<String, Object> delPsn(Integer pid) {
        Map<String, Object> result = new HashMap<>();
        if (pid != null) {
//            List<Filter> filterForFromPsn = new Filter.Builder().eq("pid", pid).eq("delflag",1).build();
//            List<RelPsnSuspect> relPsnSuspectList = relPsnSuspectMapper.selectByFilter(EntityHelper.f2f(entityClass, filterForFromPsn));
//            if (relPsnSuspectList!=null && relPsnSuspectList.size()>0){
//                RelPsnSuspect relPsnSuspectUpdate = relPsnSuspectList.get(0);
//                relPsnSuspectUpdate.setDelflag("0");
////                relPsnSuspectUpdate.setModifiedTime(new Date());
////                relPsnSuspectUpdate.setModifiedTimeString(new Date().toString());
//                Integer update = relPsnSuspectMapper.updateSelective(relPsnSuspectUpdate);
//                if (update==1){
            List<PersonRelationVo> personRelationVos = this.queryPsnRelation(pid);
            if (personRelationVos != null && personRelationVos.size() > 0) {
//                        List<Integer> listForToPsn = new ArrayList<>();
                List<Integer> listForRel = new ArrayList<>();
                for (PersonRelationVo personRelationVo : personRelationVos) {
//                            listForToPsn.add(personRelationVo.getPidForPsnTo());
                    listForRel.add(personRelationVo.getPidForRel());
                }
//                        if (listForToPsn!=null && listForToPsn.size()>0){
//                            for (Integer toPsn : listForToPsn) {
//                                List<Filter> filterForToPsn = new Filter.Builder().eq("pid",toPsn).eq("delflag",1).build();
//                               List<RelPsnSuspect> relPsnSuspects =  relPsnSuspectMapper.selectByFilter(EntityHelper.f2f(entityClass, filterForToPsn));
//                                RelPsnSuspect toPsnUpdate = relPsnSuspects.get(0);
//                                toPsnUpdate.setDelflag("0");
//                                toPsnUpdate.setModifiedTime(new Date());
//                                toPsnUpdate.setModifiedTimeString(new Date().toString());
//                                relPsnSuspectMapper.update(toPsnUpdate);
//                            }
//                        }
                if (listForRel != null && listForRel.size() > 0) {
                    for (Integer rel : listForRel) {
                        List<Filter> filtersForRel = new Filter.Builder().eq("pid", rel).eq("delflag", 1).build();
                        List<RelSuspect> relSuspects = relSuspectMapper.selectByFilter(EntityHelper.f2f(entityClass, filtersForRel));
                        RelSuspect relUpdate = relSuspects.get(0);
                        relSuspectService.removeRel(relUpdate.getFromPid(), relUpdate.getToPid(), relUpdate.getFromType(), relUpdate.getToType());
//                                relUpdate.setDelflag("0");
////                                relUpdate.setModifiedTime(new Date());
////                                relUpdate.setModifiedTimeString(new Date().toString());
//                                relSuspectMapper.update(relUpdate);
                    }
                }
            }
            List<PersonOrgVo> personOrgVos = this.queryPsnOrg(pid);
            if (personOrgVos != null && personOrgVos.size() > 0) {
//                        List<Integer> orgForTos = new ArrayList<>();
                List<Integer> rels = new ArrayList<>();
                for (PersonOrgVo personOrgVo : personOrgVos) {
//                            orgForTos.add(personOrgVo.getPidForOrgTo());
                    rels.add(personOrgVo.getPidForRel());
                }
//                        if (orgForTos!=null && orgForTos.size()>0){
//                            for (Integer orgForTo : orgForTos) {
//                                List<Filter> filterForToOrg = new Filter.Builder().eq("pid",orgForTo).eq("delflag",1).build();
//                               List<RelOrgSuspect> relOrgSuspects = relOrgSuspectMapper.selectByFilter(EntityHelper.f2f(entityClass, filterForToOrg));
//                               if (relOrgSuspects!=null && relOrgSuspects.size()>0){
//                                   RelOrgSuspect toOrgUpdate = relOrgSuspects.get(0);
//                                   toOrgUpdate.setDelflag("0");
//                                   toOrgUpdate.setModifiedTime(new Date());
//                                   toOrgUpdate.setModifiedTimeString(new Date().toString());
//                                   relOrgSuspectMapper.update(toOrgUpdate);
//                               }
//                            }
//                        }
                if (rels != null && rels.size() > 0) {
                    for (Integer rel : rels) {
                        List<Filter> filtersForRel = new Filter.Builder().eq("pid", rel).eq("delflag", 1).build();
                        List<RelSuspect> relSuspects = relSuspectMapper.selectByFilter(EntityHelper.f2f(entityClass, filtersForRel));
                        RelSuspect relUpdate = relSuspects.get(0);
                        relSuspectService.removeRel(relUpdate.getFromPid(), relUpdate.getToPid(), relUpdate.getFromType(), relUpdate.getToType());
//                                relUpdate.setDelflag("0");
////                                relUpdate.setModifiedTime(new Date());
////                                relUpdate.setModifiedTimeString(new Date().toString());
//                                relSuspectMapper.updateSelective(relUpdate);
                    }
                }
            }
            result.put(WebCode.SUCCESS, true);
            result.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
//                }else {
//                    result.put(WebCode.SUCCESS, false);
//                    result.put(WebCode.MSG, WebCode.FAILURE_DELETE);
//                }
//                }else {
//                    result.put(WebCode.SUCCESS, false);
//                    result.put(WebCode.MSG, WebCode.FAILURE_DELETE);
//                }
        } else {
            result.put(WebCode.SUCCESS, false);
            result.put(WebCode.MSG, WebCode.FAILURE_DELETE);
        }
        return result;
    }

    /*
     * 自然人基本信息新增
     * */
    @Override
    public Map<String, Object> insertPsnMsg(RelPsnSuspect relPsnSuspect) {
        Map<String, Object> result = new HashMap<>();
        if (relPsnSuspect != null) {
            RelPsnSuspect relPsnSuspectNew = new RelPsnSuspect();
            BeanUtils.copyProperties(relPsnSuspect, relPsnSuspectNew);
            relPsnSuspectNew.setDelflag("1");
//			relPsnSuspectNew.setCreateTime(new Date());
//			relPsnSuspectNew.setCreateTimeString(new Date().toString());
//			relPsnSuspectNew.setModifiedTime(new Date());
//			relPsnSuspectNew.setModifiedTimeString(new Date().toString());
            Integer insert = relPsnSuspectMapper.insertForMapper(relPsnSuspectNew);
            if (insert == 1) {
                result.put(WebCode.SUCCESS, true);
                result.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
                result.put("formPid", relPsnSuspectNew.getPid());
            } else {
                result.put(WebCode.SUCCESS, false);
                result.put(WebCode.MSG, WebCode.FAILURE_SAVE);
            }
        } else {
            result.put(WebCode.SUCCESS, false);
            result.put(WebCode.MSG, WebCode.FAILURE_SAVE);
        }
        return result;
    }

    /*
     * 自然人基本信息修改
     * */
    @Override
    public Map<String, Object> updatePsnMsg(RelPsnSuspect relPsnSuspect) {
        Map<String, Object> result = new HashMap<>();
        if (relPsnSuspect != null && relPsnSuspect.getPid() != null) {
            List<Filter> filterForPsnMsg = new Filter.Builder().eq("pid", relPsnSuspect.getPid()).eq("delflag", 1).build();
            List<RelPsnSuspect> relPsnSuspects = relPsnSuspectMapper.selectByFilter(EntityHelper.f2f(entityClass, filterForPsnMsg));
            if (relPsnSuspects != null && relPsnSuspects.size() > 0) {
                RelPsnSuspect relPsnSuspectNew = relPsnSuspects.get(0);
                BeanUtils.copyProperties(relPsnSuspect, relPsnSuspectNew);
//				relPsnSuspectNew.setModifiedTime(new Date());
//				relPsnSuspectNew.setModifiedTimeString(new Date().toString());
                Integer update = relPsnSuspectMapper.updateSelective(relPsnSuspectNew);
                if (update == 1) {
                    result.put(WebCode.SUCCESS, true);
                    result.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
                    result.put("formPid", relPsnSuspect.getPid());
                } else {
                    result.put(WebCode.SUCCESS, false);
                    result.put(WebCode.MSG, WebCode.FAILURE_SAVE);
                    result.put("formPid", relPsnSuspect.getPid());
                }
            } else {
                result.put(WebCode.SUCCESS, false);
                result.put(WebCode.MSG, WebCode.FAILURE_SAVE);
                result.put("formPid", relPsnSuspect.getPid());
            }
        } else {
            result.put(WebCode.SUCCESS, false);
            result.put(WebCode.MSG, WebCode.FAILURE_SAVE);
            result.put("formPid", relPsnSuspect.getPid());
        }
        return result;
    }

    /*
     * 自然人附属信息新增
     * */
    @Override
    public Map<String, Object> insertPsgRelMsg(PersonRelationVo personRelationVo) {
        Map<String, Object> result = new HashMap<>();
        if (personRelationVo != null && personRelationVo.getPidForPsnFrom() != null) {
            RelPsnSuspect relPsnSuspectForRel = new RelPsnSuspect();
            BeanUtils.copyProperties(personRelationVo, relPsnSuspectForRel);
            relPsnSuspectForRel.setDelflag("1");
//			relPsnSuspectForRel.setCreateTime(new Date());
//			relPsnSuspectForRel.setCreateTimeString(new Date().toString());
//			relPsnSuspectForRel.setModifiedTime(new Date());
//			relPsnSuspectForRel.setModifiedTimeString(new Date().toString());
            Integer relPsnPid = relPsnSuspectMapper.insertForMapper(relPsnSuspectForRel);
            if (relPsnPid == 1) {
                if (personRelationVo.getPidForPsnFrom() != null) {
                    Rel relSuspect = new Rel();
                    BeanUtils.copyProperties(personRelationVo, relSuspect);
                    relSuspect.setFromPid(personRelationVo.getPidForPsnFrom().toString());
                    relSuspect.setToPid(relPsnSuspectForRel.getPid().toString());
                    relSuspect.setToType("SUSPSN");
                    relSuspect.setFromType("SUSPSN");
//					RelDict relDict = relDictService.selectDict(personRelationVo.getRelCategory(), personRelationVo.getRelation());
                    RelDict relDict = relDictService.selectDict("A", "03");
//					RelDict relDict = relDictService.selectDict(personRelationVo.getRelCategory(), personRelationVo.getRelCategorySub());
                    if (!StringUtils.isEmpty(relDict.getFromName()) && !StringUtils.isEmpty(relDict.getToName())) {
                        relSuspect.setFromName(relDict.getFromName());
                        relSuspect.setToName(relDict.getToName());
                    }
//					relSuspect.setCreateTime(new Date());
//					relSuspect.setCreateTimeString(new Date().toString());
//					relSuspect.setModifiedTime(new Date());
//					relSuspect.setModifiedTimeString(new Date().toString());
                    relSuspect.setDelflag("1");
                    Integer relPid = relMapper.insertForMapper(relSuspect);
                    if (relPid != null) {
                        result.put(WebCode.SUCCESS, true);
                        result.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
                    } else {
                        result.put(WebCode.SUCCESS, false);
                        result.put(WebCode.MSG, WebCode.FAILURE_SAVE);
                    }
                } else {
                    result.put(WebCode.SUCCESS, false);
                    result.put(WebCode.MSG, WebCode.FAILURE_SAVE);
                }
            } else {
                result.put(WebCode.SUCCESS, false);
                result.put(WebCode.MSG, WebCode.FAILURE_SAVE);
            }
        } else {
            result.put(WebCode.SUCCESS, false);
            result.put(WebCode.MSG, WebCode.FAILURE_SAVE);
        }
        return result;
    }

    /*
     * 自然人附属信息更新
     * */
    @Override
    public Map<String, Object> updatePsgRelMsg(PersonRelationVo personRelationVo) {
        Map<String, Object> result = new HashMap<>();
        if (personRelationVo != null && personRelationVo.getPidForPsnTo() != null && personRelationVo.getPidForRel() != null) {
            List<Filter> filterForPsn = new Filter.Builder().eq("pid", personRelationVo.getPidForPsnTo()).eq("delflag", 1).build();
            List<RelPsnSuspect> relPsnSuspects = relPsnSuspectMapper.selectByFilter(EntityHelper.f2f(entityClass, filterForPsn));
            if (relPsnSuspects != null && relPsnSuspects.size() > 0) {
                RelPsnSuspect relPsnSuspect = relPsnSuspects.get(0);
                BeanUtils.copyProperties(personRelationVo, relPsnSuspect);
//			relPsnSuspect.setModifiedTimeString(new Date().toString());
//			relPsnSuspect.setModifiedTime(new Date());
                Integer update = relPsnSuspectMapper.update(relPsnSuspect);
                if (update == 1) {
                    List<Filter> filterForRel = new Filter.Builder().eq("pid", personRelationVo.getPidForRel()).eq("delflag", 1).build();
                    List<Rel> relSuspects = relMapper.selectByFilter(EntityHelper.f2f(entityClass, filterForRel));
                    if (relSuspects != null && relSuspects.size() > 0) {
                        Rel relSuspectUpdate = relSuspects.get(0);
                        BeanUtils.copyProperties(personRelationVo, relSuspectUpdate);
//					RelDict relDict = relDictService.selectDict(personRelationVo.getRelCategory(), personRelationVo.getRelation());
                        RelDict relDict = relDictService.selectDict(personRelationVo.getRelCategory(), personRelationVo.getRelCategorySub());
                        if (!StringUtils.isEmpty(relDict.getFromName()) && !StringUtils.isEmpty(relDict.getToName())) {
                            relSuspectUpdate.setFromName(relDict.getFromName());
                            relSuspectUpdate.setToName(relDict.getToName());
                        }
//					relSuspectUpdate.setModifiedTime(new Date());
//					relSuspectUpdate.setModifiedTimeString(new Date().toString());
                        Integer updateForRel = relMapper.updateSelective(relSuspectUpdate);
                        if (updateForRel == 1) {
                            result.put(WebCode.SUCCESS, true);
                            result.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
                        } else {
                            result.put(WebCode.SUCCESS, false);
                            result.put(WebCode.MSG, WebCode.FAILURE_SAVE);
                        }
                    } else {
                        result.put(WebCode.SUCCESS, false);
                        result.put(WebCode.MSG, WebCode.FAILURE_SAVE);
                    }
                } else {
                    result.put(WebCode.SUCCESS, false);
                    result.put(WebCode.MSG, WebCode.FAILURE_SAVE);
                }
            } else {
                result.put(WebCode.SUCCESS, false);
                result.put(WebCode.MSG, WebCode.FAILURE_SAVE);
            }
        } else {
            result.put(WebCode.SUCCESS, false);
            result.put(WebCode.MSG, WebCode.FAILURE_SAVE);
        }
        return result;
    }

    /*
     * 疑似自然人 ---关系自然人信息删除
     * */
    @Override
    public Map<String, Object> delPsgRelMsg(Integer pidForRel) {
        Map<String, Object> result = new HashMap<>();
        if (pidForRel != null) {
            List<Filter> filterForRel = new Filter.Builder().eq("pid", pidForRel).eq("delflag", 1).eq("fromType", "SUSPSN").eq("toType", "SUSPSN").build();
            List<Rel> relSuspects = relMapper.selectByFilter(EntityHelper.f2f(entityClass, filterForRel));
            if (relSuspects != null && relSuspects.size() > 0) {
                Rel relSuspect = relSuspects.get(0);
                String relStstus = relSuspect.getRelStatus();
                if (StringUtils.isNotEmpty(relStstus) && "00".equals(relStstus)) {
                    // 00代表归档  此时不可以删除
                    relSuspect.setRelStatus("03");
                    relService.updateSelective(relSuspect);
                    RelPsn relPsn = relPsnService.get(Integer.parseInt(relSuspect.getFromPid()));
                    relPsn.setStatus("3");
                    relPsnService.updateSelective(relPsn);
                    result.put(WebCode.SUCCESS, true);
                    result.put(WebCode.MSG, "请提交审批，然后删除");
                } else if (StringUtils.isNotEmpty(relStstus) && "02".equals(relStstus)) {
                    relService.removeRel(relSuspect.getFromPid(), relSuspect.getToPid(), relSuspect.getFromType(), relSuspect.getToType());
                    RelPsn relPsn = relPsnService.get(Integer.parseInt(relSuspect.getFromPid()));
                    relPsn.setStatus("3");
                    relPsnService.updateSelective(relPsn);
                    result.put(WebCode.SUCCESS, true);
                    result.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
                } else {
                    result.put(WebCode.SUCCESS, false);
                    result.put(WebCode.MSG, "状态码不正确，无法删除");
                }
            } else {
                result.put(WebCode.SUCCESS, false);
                result.put(WebCode.MSG, WebCode.FAILURE_DELETE);
            }
        } else {
            result.put(WebCode.SUCCESS, false);
            result.put(WebCode.MSG, WebCode.FAILURE_DELETE);
        }
        return result;
    }
//	@Override
//	public Map<String, Object> delPsgRelMsg(Integer pidForRel) {
//		Map<String,Object> result = new HashMap<>();
//		if (pidForRel!=null){
//			List<Filter> filterForRel = new Filter.Builder().eq("pid",pidForRel).eq("delflag",1).build();
//			List<RelSuspect> relSuspects = relSuspectMapper.selectByFilter(EntityHelper.f2f(entityClass, filterForRel));
//			if (relSuspects!=null && relSuspects.size()>0){
//				RelSuspect relSuspect = relSuspects.get(0);
////				if (relSuspect.)
//				relSuspectService.removeRel(relSuspect.getFromPid(),relSuspect.getToPid(),relSuspect.getFromType(),relSuspect.getToType());
//				result.put(WebCode.SUCCESS, true);
//				result.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
//			}else {
//				result.put(WebCode.SUCCESS, false);
//				result.put(WebCode.MSG, WebCode.FAILURE_DELETE);
//			}
//		}else {
//			result.put(WebCode.SUCCESS, false);
//			result.put(WebCode.MSG, WebCode.FAILURE_DELETE);
//		}
//		return result;
//	}

    /*
     *自然人机构关系信息新增
     * */
    @Override
    public Map<String, Object> insertPsgOrg(PersonOrgVo personOrgVo) {
        Map<String, Object> result = new HashMap<>();
        if (personOrgVo != null && personOrgVo.getPidForOrgFrom() != null) {
            RelOrgSuspect relOrgSuspect = new RelOrgSuspect();
            BeanUtils.copyProperties(personOrgVo, relOrgSuspect);
            relOrgSuspect.setDelflag("1");
//			relOrgSuspect.setCreateTime(new Date());
//			relOrgSuspect.setCreateTimeString(new Date().toString());
//			relOrgSuspect.setModifiedTime(new Date());
//			relOrgSuspect.setModifiedTimeString(new Date().toString());
            Integer relOrg = relOrgSuspectMapper.insertForMapper(relOrgSuspect);
            if (relOrg == 1) {
                Rel relSuspect = new Rel();
                BeanUtils.copyProperties(personOrgVo, relSuspect);
                relSuspect.setToPid(relOrgSuspect.getPid().toString());
                relSuspect.setFromPid(personOrgVo.getPidForOrgFrom().toString());
                relSuspect.setToType("SUSORG");
                relSuspect.setFromType("SUSPSN");
                relSuspect.setDelflag("1");
//				RelDict relDict = relDictService.selectDict(personOrgVo.getRelCategory(), personOrgVo.getDutyCode());
                RelDict relDict = relDictService.selectDict(personOrgVo.getRelCategory(), personOrgVo.getRelCategorySub());
                if (!StringUtils.isEmpty(relDict.getFromName()) && !StringUtils.isEmpty(relDict.getToName())) {
                    relSuspect.setFromName(relDict.getFromName());
                    relSuspect.setToName(relDict.getToName());
                }
//				relSuspect.setCreateTime(new Date());
//				relSuspect.setCreateTimeString(new Date().toString());
//				relSuspect.setModifiedTime(new Date());
//				relSuspect.setModifiedTimeString(new Date().toString());
                Integer rel = relMapper.insertForMapper(relSuspect);
                if (rel == 1) {
                    result.put(WebCode.SUCCESS, true);
                    result.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
                } else {
                    result.put(WebCode.SUCCESS, false);
                    result.put(WebCode.MSG, WebCode.FAILURE_SAVE);
                }
            } else {
                result.put(WebCode.SUCCESS, false);
                result.put(WebCode.MSG, WebCode.FAILURE_SAVE);
            }
        } else {
            result.put(WebCode.SUCCESS, false);
            result.put(WebCode.MSG, WebCode.FAILURE_SAVE);
        }
        return result;
    }

    /*
     *自然人机构关系信息更新
     * */
    @Override
    public Map<String, Object> updatePsgOrg(PersonOrgVo personOrgVo) {
        Map<String, Object> result = new HashMap<>();
        if (personOrgVo != null && personOrgVo.getPidForRel() != null && personOrgVo.getPidForOrgTo() != null) {
            List<Filter> filterForRelOrg = new Filter.Builder().eq("pid", personOrgVo.getPidForOrgTo()).eq("delflag", 1).build();
            List<RelOrgSuspect> relOrgSuspects = relOrgSuspectMapper.selectByFilter(EntityHelper.f2f(entityClass, filterForRelOrg));
            if (relOrgSuspects != null && relOrgSuspects.size() > 0) {
                RelOrgSuspect relOrgSuspectForUpdate = relOrgSuspects.get(0);
                BeanUtils.copyProperties(personOrgVo, relOrgSuspectForUpdate);
//				relOrgSuspectForUpdate.setModifiedTime(new Date());
//				relOrgSuspectForUpdate.setModifiedTimeString(new Date().toString());
                Integer updateForOrg = relOrgSuspectMapper.updateSelective(relOrgSuspectForUpdate);
                if (updateForOrg == 1) {
                    List<Filter> filterForRel = new Filter.Builder().eq("pid", personOrgVo.getPidForRel()).eq("delflag", 1).build();
                    List<Rel> relSuspects = relMapper.selectByFilter(EntityHelper.f2f(entityClass, filterForRel));
                    if (relSuspects != null && relSuspects.size() > 0) {
                        Rel relSuspectUpdate = relSuspects.get(0);
                        BeanUtils.copyProperties(personOrgVo, relSuspectUpdate);
//						RelDict relDict = relDictService.selectDict(personOrgVo.getRelCategory(), personOrgVo.getDutyCode());
                        RelDict relDict = relDictService.selectDict(personOrgVo.getRelCategory(), personOrgVo.getRelCategorySub());
                        if (!StringUtils.isEmpty(relDict.getFromName()) && !StringUtils.isEmpty(relDict.getToName())) {
                            relSuspectUpdate.setFromName(relDict.getFromName());
                            relSuspectUpdate.setToName(relDict.getToName());
                        }
//						relSuspectUpdate.setModifiedTime(new Date());
//						relSuspectUpdate.setModifiedTimeString(new Date().toString());
                        Integer updateForRel = relMapper.updateSelective(relSuspectUpdate);
                        if (updateForRel == 1) {
                            result.put(WebCode.SUCCESS, true);
                            result.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
                        } else {
                            result.put(WebCode.SUCCESS, false);
                            result.put(WebCode.MSG, WebCode.FAILURE_SAVE);
                        }
                    } else {
                        result.put(WebCode.SUCCESS, false);
                        result.put(WebCode.MSG, WebCode.FAILURE_SAVE);
                    }
                } else {
                    result.put(WebCode.SUCCESS, false);
                    result.put(WebCode.MSG, WebCode.FAILURE_SAVE);
                }
            } else {
                result.put(WebCode.SUCCESS, false);
                result.put(WebCode.MSG, WebCode.FAILURE_SAVE);
            }
        } else {
            result.put(WebCode.SUCCESS, false);
            result.put(WebCode.MSG, WebCode.FAILURE_SAVE);
        }

        return result;
    }

    /*
     *自然人机构关系信息删除
     * */
    @Override
    public Map<String, Object> delPsgOrg(Integer pidForRel) {
        Map<String, Object> result = new HashMap<>();
        if (pidForRel != null) {
            List<Filter> filterForRel = new Filter.Builder().eq("pid", pidForRel).eq("delflag", 1).build();
            List<RelSuspect> relSuspects = relSuspectMapper.selectByFilter(EntityHelper.f2f(entityClass, filterForRel));
            if (relSuspects != null && relSuspects.size() > 0) {
                RelSuspect relSuspect = relSuspects.get(0);
//				relSuspect.setDelflag("0");
////				relSuspect.setModifiedTime(new Date());
////				relSuspect.setModifiedTimeString(new Date().toString());
//				Integer del = relSuspectMapper.updateSelective(relSuspect);
                relSuspectService.removeRel(relSuspect.getFromPid(), relSuspect.getToPid(), relSuspect.getFromType(), relSuspect.getToType());
//				if (del==1){
                result.put(WebCode.SUCCESS, true);
                result.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
//				}else {
//					result.put(WebCode.SUCCESS, false);
//					result.put(WebCode.MSG, WebCode.FAILURE_DELETE);
//				}
            } else {
                result.put(WebCode.SUCCESS, false);
                result.put(WebCode.MSG, WebCode.FAILURE_DELETE);
            }
        }
        return result;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public Map<String, Object> replacePsn(Integer pid, RelPsn relPsn, List<Rel> rels) {
        Map<String, Object> tips = new HashMap<>();
        try {
            relPsnService.insertSelective(relPsn);
            Integer toPid = relPsn.getPid();
            for (int i = 0; i < rels.size(); i++) {
                Rel rel = rels.get(i);
                rel.setDelflag("1");
                rel.setIsShow("1");
                rel.setToType("PSN");
                rel.setToPid(toPid.toString());
                relService.updateSelective(rel);
            }
            relPsnSuspectMapper.deleteByPrimaryKey(pid);
            tips.put("result", "true");
        } catch (Exception e) {
            e.printStackTrace();
            tips.put("result", "false");
        }
        return tips;
    }


}