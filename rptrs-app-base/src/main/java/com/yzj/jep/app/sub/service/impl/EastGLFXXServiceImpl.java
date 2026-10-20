/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年12月13日 15:41:26
 *
 */

package com.yzj.jep.app.sub.service.impl;

import com.yzj.jep.app.bus.model.Rel;
import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.bus.service.RelService;
import com.yzj.jep.app.sub.mapper.EastGLFXXMapper;
import com.yzj.jep.app.sub.model.EastGLFXX;
import com.yzj.jep.app.sub.model.Organ;
import com.yzj.jep.app.sub.model.Person;
import com.yzj.jep.app.sub.service.EastGLFXXService;
import com.yzj.jep.app.sub.service.OrganService;
import com.yzj.jep.app.sub.service.PersonService;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import com.yzj.jep.sys.service.StaffService;
import com.yzj.jep.sys.service.UnitService;
import com.yzj.jep.web.entity.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class EastGLFXXServiceImpl extends MyBatisCRUDService<EastGLFXX, Integer> implements EastGLFXXService {

    Logger logger = LoggerFactory.getLogger(EastGLFXXServiceImpl.class);

    @Autowired
    EastGLFXXMapper eastGLFXXMapper;
    @Autowired
    PersonService personService;
    @Autowired
    OrganService organService;
    @Autowired
    RelService relService;
    @Autowired
    RelPsnService relPsnService;
    @Autowired
    RelOrgService relOrgService;
    @Autowired
    UnitService unitService;
    @Autowired
    DictItemService dictItemService;

    private StaffService staffService;


    @Override
    public Mapper<EastGLFXX, Integer> getMapper() {
        return eastGLFXXMapper;
    }

    @Override
    public void eastExtract() {
        /**
         * 每个月抽一次，抽数规则为创建时间为上个月，未报送的数据
         */
        List<Filter> filter = new Filter.Builder().eq("isExtract", "0").eq("delflag", 1).build();
//		List<Filter> filter = new Filter.Builder().eq("isExtract", "0").eq("delflag", 1).build();
        List<Person> psns = personService.select(filter);
        List<Organ> orgs = organService.select(filter);
        EastGLFXX glfxx = new EastGLFXX();

        if (psns != null && psns.size() > 0) {
            for (Person rp : psns) {
                glfxx.setGlfbh(rp.getGlfbh());//关联方编号
                glfxx.setGlfmc(rp.getName());//关联方名称
                glfxx.setGlflb("自然人");
                glfxx.setGlfsbm(rp.getIdentityNo());//关联方证件号码
                glfxx.setDelflag("1");
                //有效标识
                List<Filter> dictCode3 = new Filter.Builder().eq("dictCode", "BUS-EffectRelation").build();
                List<DictItem> dictItem3 = dictItemService.select(dictCode3);
                Map map3 = new HashMap();
                for (DictItem dic : dictItem3) {
                    map3.put(dic.getItemValue(), dic.getItemLabel());
                }
                glfxx.setYxbz(map3.get(rp.getEffectRelation()).toString());//关联方有效标识
                glfxx.setEastStatus(1);
                List<Filter> filters = new Filter.Builder().eq("fromMemo", glfxx.getGlfmc()).eq("fromIdentifyNumber", glfxx.getGlfsbm()).build();
                List<Rel> rels = relService.select(filters);
                if (rels.size() == 1) {
                    glfxx.setGxlx(selectByValue(rels.get(0).getRelationType(), "BUS-RelationType"));
                    glfxx.setQtglgxsm(rels.get(0).getRelStatement()); // 其他关联关系说明
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    glfxx.setGxlx("其他关联关系");
                    for (Rel rel : rels) {
                        stringBuilder.append(rel.getRelStatement() + ";");
                    }
                    glfxx.setQtglgxsm(stringBuilder.toString());
                }
//				List<EastGLFXX> list = getMapper().select(glfxx);
                glfxx.setCreateTime(new Date());
                //保存并更新
                getMapper().insert(glfxx);
                rp.setIsExtract("1");
                personService.updateSelective(rp);
            }
        }

        if (orgs != null && orgs.size() > 0) {
            for (Organ ro : orgs) {
                glfxx.setGlfbh(ro.getGlfbh());
                glfxx.setGlfmc(ro.getName());
                glfxx.setGlflb("法人或其他组织");
                glfxx.setGlfsbm(ro.getIdentityNo());
                glfxx.setDelflag("1");
                //有效标识
                List<Filter> dictCode3 = new Filter.Builder().eq("dictCode", "BUS-EffectRelation").build();
                List<DictItem> dictItem3 = dictItemService.select(dictCode3);
                Map map3 = new HashMap();
                for (DictItem dic : dictItem3) {
                    map3.put(dic.getItemValue(), dic.getItemLabel());
                }
                glfxx.setYxbz(map3.get(ro.getEffectRelation()).toString());
                glfxx.setEastStatus(1);
                List<Filter> filters = new Filter.Builder().eq("fromMemo", glfxx.getGlfmc()).eq("fromIdentifyNumber", glfxx.getGlfsbm()).build();
                List<Rel> rels = relService.select(filters);
                if (rels.size() == 1) {
                    glfxx.setGxlx(selectByValue(rels.get(0).getRelationType(), "BUS-RelationType"));
                    glfxx.setQtglgxsm(rels.get(0).getRelStatement()); // 其他关联关系说明
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    glfxx.setGxlx("其他关联关系");
                    for (Rel rel : rels) {
                        stringBuilder.append(rel.getRelStatement() + ";");
                    }
                    glfxx.setQtglgxsm(stringBuilder.toString());
                }
                glfxx.setCreateTime(new Date());
                getMapper().insert(glfxx);
                //保存并更新
                List<EastGLFXX> list = getMapper().select(glfxx);
                ro.setIsExtract("1");
                organService.updateSelective(ro);
            }
        }
    }

    public String selectByValue(String value, String simpleName) {
        List<NameValuePair> items = new ArrayList<>();
        try {
            List<Filter> filters = new Filter.Builder().eq("dictCode", simpleName).build();
            List<DictItem> list = dictItemService.select(filters);
            //过滤掉不要的字典项
            if (!org.springframework.util.CollectionUtils.isEmpty(list)) {
                for (DictItem dictItem : list) {
                    if (dictItem.getItemValue().equals(value)) {
                        return dictItem.getItemLabel();
                    }
                }
            }
        } catch (Exception e) {
        }
        return null;
    }


}