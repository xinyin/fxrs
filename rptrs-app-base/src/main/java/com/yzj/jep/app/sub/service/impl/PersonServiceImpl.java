/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月02日 09:48:47
 *
 */

package com.yzj.jep.app.sub.service.impl;

import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.sub.mapper.PersonMapper;
import com.yzj.jep.app.sub.model.Person;
import com.yzj.jep.app.sub.service.PersonService;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service(value = "PersonServiceImpl")
@Transactional
public class PersonServiceImpl extends MyBatisCRUDService<Person, Integer> implements PersonService {

    Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private RelPsnService relPsnService;

    @Override
    public Mapper<Person, Integer> getMapper() {
        return personMapper;
    }

    @Override
    public void extractPerson(Map map, String nowDate) {

        String tag = (String) map.get("tag");
        List<RelPsn> relPsns = new ArrayList<>();
        List<Filter> filters = new Filter.Builder().like("modifiedTime", nowDate).eq("status","6").eq("delflag", "1").eq("effectRelation","1").build();
        //分批查询导入
        Page<RelPsn> page = new Page<>(1, 100);
        PageHelper.start(page);
        if ("all".equals(tag)) {
            relPsns = relPsnService.selectAll();
        } else {
            relPsns = relPsnService.select(filters);
        }
        PageHelper.end(page,relPsns);
        int pageSize=page.getPages();
        List<RelPsn>  psns= page.getResult();
        startExtract(psns);
        for(int i=2;i<=pageSize;i++){
            //从第二页开始循环
            page=new Page<>(i,100);
            PageHelper.start(page);
            if ("all".equals(tag)){
                relPsns=relPsnService.selectAll();
            }else {
                relPsns=relPsnService.select(filters);
            }
            PageHelper.end(page,relPsns);
            psns = page.getResult();
            //开始抽取
            startExtract(psns);
        }
    }

    private void startExtract(List<RelPsn> relPsns) {
        for (RelPsn relPsn : relPsns) {
            Person person = new Person();
            BeanUtils.copyProperties(relPsn, person);
            person.setName(relPsn.getName());//关联方名称
            person.setIdentityNo(relPsn.getIdentifyNumber());//关联方证件号码
            person.setIdentityType(relPsn.getIdentifyType());//关联方证件类型
            person.setDomesticOrForeign(relPsn.getDomesticOrForeign());//关联方境内外识别
            person.setStatus("3");//状态
            person.setStatusComment("数据已抽取");
            person.setIsExtract("0");
//            person.setEffectRelation(relPsn.getEffectRelation());
            boolean exists = personMapper.existsByPrimaryKey(relPsn.getPid());
            try {
                if (exists) {
                    personMapper.updateSelective(person);
                } else {
                    personMapper.insertSelective(person);
                }
            } catch (ServiceException e) {
                logger.error("自然人" + person.getName() + "数据更新异常：", e);
            }
        }
    }
}