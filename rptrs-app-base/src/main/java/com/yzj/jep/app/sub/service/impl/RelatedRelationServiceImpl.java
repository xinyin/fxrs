/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月02日 09:48:52
 *
 */

package com.yzj.jep.app.sub.service.impl;

import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.app.sub.mapper.RelatedRelationMapper;
import com.yzj.jep.app.sub.model.RelatedRelation;
import com.yzj.jep.app.sub.service.RelatedRelationService;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
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

@Service(value = "RelatedRelationServiceImpl")
@Transactional
public class RelatedRelationServiceImpl extends MyBatisCRUDService<RelatedRelation, Integer> implements RelatedRelationService {

    Logger logger = LoggerFactory.getLogger(RelatedRelationServiceImpl.class);

    @Autowired
    private RelatedRelationMapper relatedRelationMapper;

    @Autowired
    private RelatedRelationService relatedRelationService;

    @Autowired
    private RelService relService;

    @Autowired
    private RelPsnService relPsnService;

    @Autowired
    private RelOrgService relOrgService;

    @Autowired
    private RelProService relProService;

    @Autowired
    RelPsnAndOrgOfProService relPsnAndOrgOfProService;

    @Override
    public Mapper<RelatedRelation, Integer> getMapper() {
        return relatedRelationMapper;
    }

    @Override
    public void extractRelatedRelation(Map map, String nowDate) {
        String tag = (String) map.get("tag");
        List<Rel> rels = new ArrayList<>();
        List<RelPsnAndOrgOfPro> proRel = new ArrayList<>();
        if ("all".equals(tag)) {
            rels = relService.selectAll();
        } else {
            List<Filter> filters = new Filter.Builder().like("modifiedTime", nowDate).
                    eq("delflag", "1").eq("approvalStatus", "6").build();
            rels = relService.select(filters);
        }
        startExtractPsnAndOrg(rels);
    }

    private void startExtractPsnAndOrg(List<Rel> rels) {
        for (Rel rel : rels) {
            RelatedRelation relatedRelation = new RelatedRelation();
            //本层关联方名称
            relatedRelation.setEntityName(rel.getFromMemo());
            //本层关联方证件号码
            relatedRelation.setEntityUniscUode(rel.getFromIdentifyNumber());
            //前一层关联方名称
            relatedRelation.setSuperEntityName(rel.getToMemo());
            //前一层关联方号码
            relatedRelation.setSuperEntityUniscCode(rel.getToIdentifyNumber());
            //前一层关联方类型
            if("ORG".equals(rel.getToType())){
                relatedRelation.setSuperEntityType("1");
            }else if("PSN".equals(rel.getToType())){
                relatedRelation.setSuperEntityType("2");
            }else if("PRO".equals(rel.getToType())){
                relatedRelation.setSuperEntityType("3");
            }
            //本层关联方类型
            if("ORG".equals(rel.getFromType())){
                relatedRelation.setEntityType("1");
            }else if("PSN".equals(rel.getFromType())){
                relatedRelation.setEntityType("2");
            }else if("PRO".equals(rel.getFromType())){
                relatedRelation.setEntityType("3");
            }
            //关联关系
            relatedRelation.setRelationType(rel.getRelPsnOrg());
            if (rel.getShareholdingRatio()!=null){
                relatedRelation.setHoldingPercent(String.valueOf(rel.getShareholdingRatio())); //持有比例
            }
            //关系说明
            relatedRelation.setRelationInfo(rel.getRelStatement());
            //该关系是否有效
            relatedRelation.setValidStatus(rel.getIsValid());
            //生效时间
            relatedRelation.setActiveTimeString(rel.getActiveTimeString());
            //失效时间
            relatedRelation.setInvalidTimeString(rel.getInvalidTimeString());
            relatedRelation.setStatus("3");
            relatedRelation.setStatusComment("数据已抽取");
            relatedRelation.setEquityShare(rel.getEquityShare());
            relatedRelation.setPledgeRatio(rel.getPledgeRatio());
            relatedRelation.setIsExtract("0");
            List<Filter> filters = new Filter.Builder().eq("srPid", "p" + rel.getPid().toString()).build();
            boolean exists = relatedRelationService.exists(filters);

            try {
                if (exists) {
                    relatedRelationService.updateSelective(relatedRelation);
                } else {
                    relatedRelation.setSrPid("p" + rel.getPid());
                    relatedRelationService.insertSelective(relatedRelation);
                }
            } catch (ServiceException e) {
                logger.error("关系-机构自然人" + relatedRelation.getEntityName() + "和" + relatedRelation.getSuperEntityName() + "数据更新异常：", e);
            }
        }

    }


//    private void startExtractPro(List<RelPsnAndOrgOfPro> rels) {
//        String superPid = "";
//        String childPid = "";
//        String superType = "";
//        String childType = "";
//        String superName = "";
//        String childName = "";
//        String superIdentifyNumber = "";
//        String childIdentifyNumber = "";
//
//
//        for (RelPsnAndOrgOfPro rel : rels) {
//            RelatedRelation relatedRelation = new RelatedRelation();
//
//            superPid = rel.getToPid();
//            childPid = rel.getFromPid();
//            superType = rel.getToType();
//            childType = rel.getFromType();
//
//            //基本信息
//            if ("PRO".equals(superType)) {
//                if (!relProService.exists(Integer.parseInt(superPid))) {
//                    continue;
//                }
//                RelPro relPro = relProService.get(Integer.parseInt(superPid));
//                if (!relPro.getMgerOrg().contains("0001")) {
//                    continue;
//                }
//                superName = relPro.getProName();
//                superIdentifyNumber = relPro.getProProvider();
//            }
//            if ("ORG".equals(superType)) {
//                if (!relProService.exists(Integer.parseInt(superPid))) {
//                    continue;
//                }
//                RelOrg relOrg = relOrgService.get(Integer.parseInt(superPid));
//                superName = relOrg.getOrgName();
//                superIdentifyNumber = relOrg.getUniscCode();
//            }
//
//
//            //关系
//            if ("PRO".equals(childType)) {
//                if (!relProService.exists(Integer.parseInt(childPid))) {
//                    continue;
//                }
//                RelPro relPro = relProService.get(Integer.parseInt(childPid));
//                if (!relPro.getMgerOrg().contains("0001")) {
//                    continue;
//                }
//                childName = relPro.getProName();
//                childIdentifyNumber = relPro.getProProvider();
//            }
//            if ("ORG".equals(childType)) {
//                if (!relOrgService.exists(Integer.parseInt(childPid))) {
//                    continue;
//                }
//                RelOrg relOrg = relOrgService.get(Integer.parseInt(childPid));
//                childName = relOrg.getOrgName();
//                childIdentifyNumber = relOrg.getUniscCode();
//            }
//
//            relatedRelation.setEntityName(superName);
//            relatedRelation.setEntityUniscUode(superIdentifyNumber);
//            relatedRelation.setSuperEntityName(childName);
//            relatedRelation.setSuperEntityUniscCode(childIdentifyNumber);
//            relatedRelation.setEntityType("PRO".equals(superType) ? "3" : "1");
//            relatedRelation.setSuperEntityType("PRO".equals(childType) ? "3" : "1");
//
//            relatedRelation.setRelationType(relationMapping(rel.getRelCategorySub()));   //todo：关系，这里要做映射   已经实现
//            relatedRelation.setIsShareHolding(rel.getEquityShare());//权益份额
//            if (rel.getShareholdingRatio()!=null){
//                relatedRelation.setHoldingPercent(String.valueOf(rel.getShareholdingRatio())); //持有比例
//            }
//            relatedRelation.setRelationInfo(rel.getRelStatement());  //关系说明
//            relatedRelation.setValidStatus(rel.getIsValid()); //该关系是否有效
//            relatedRelation.setActiveTimeString(rel.getActiveTimeString());  //生效时间
//            relatedRelation.setInvalidTimeString(rel.getInvalidTimeString());   //失效时间
//            relatedRelation.setStatus("3");
//
//            List<Filter> filters = new Filter.Builder().eq("srPid", "p" + rel.getPid().toString()).build();
//            boolean exists = relatedRelationService.exists(filters);
//            try {
//                if (exists) {
//                    RelatedRelation relatedRelation1 = relatedRelationService.select(filters).get(0);
////                    BeanUtils.copyProperties(relatedRelation1, relatedRelation);
//                    relatedRelation1.setEntityName(superName);
//                    relatedRelation1.setEntityUniscUode(superIdentifyNumber);
//                    relatedRelation1.setSuperEntityName(childName);
//                    relatedRelation1.setSuperEntityUniscCode(childIdentifyNumber);
//                    relatedRelation1.setEntityType("PRO".equals(superType) ? "3" : "1");
//                    relatedRelation1.setSuperEntityType("PRO".equals(childType) ? "3" : "1");
//                    relatedRelation1.setRelationType(relationMapping(rel.getRelCategorySub()));   //todo：关系，这里要做映射   已经实现
//                    relatedRelation1.setIsShareHolding(rel.getEquityShare());//权益份额
//                    if (rel.getShareholdingRatio()!=null){
//                        relatedRelation1.setHoldingPercent(String.valueOf(rel.getShareholdingRatio())); //持有比例
//                    }
//                    relatedRelation1.setRelationInfo(rel.getRelStatement());  //关系说明
//                    relatedRelation1.setValidStatus(rel.getIsValid()); //该关系是否有效
//                    relatedRelation1.setActiveTimeString(rel.getActiveTimeString());  //生效时间
//                    relatedRelation1.setInvalidTimeString(rel.getInvalidTimeString());   //失效时间
//                    relatedRelation1.setStatus("3");
//                    relatedRelationService.updateSelective(relatedRelation1);
//                } else {
//                    relatedRelation.setSrPid("p" + rel.getPid());
//                    relatedRelationService.insertSelective(relatedRelation);
//                }
//            } catch (ServiceException e) {
//                logger.error("关系-机构金融产品" + relatedRelation.getEntityName() + "和" + relatedRelation.getSuperEntityName() + "数据更新异常：", e);
//            }
//        }
//    }
//
//
//
//    public String relationMapping(String subStr) {
//        if (subStr == null) {
//            return "";
//        }
//        Integer s = Integer.parseInt(subStr);
//        if (s == 1 || s == 2 || s == 3 || s == 4 || s == 5) {
//            return "113";
//        }
//        if (s == 6 || s == 7 || s == 8) {
//            return "109";
//        }
//        if (s == 9 || s == 10) {
//            return "112";
//        }
//        if (s == 11) {
//            return "111";
//        }
//        if (s == 12) {
//            return "115";
//        }
//        if (s == 13) {
//            return "106";
//        }
//        if (s == 14) {
//            return "104";
//        }
//        if (s == 15) {
//            return "107";
//        }
//        if (s == 16) {
//            return "105";
//        }
//
//        if (s == 17) {
//            return "106";
//        }
//        if (s == 18) {
//            return "104";
//        }
//        if (s == 19) {
//            return "107";
//        }
//        if (s == 20) {
//            return "105";
//        }
//
//        if (s == 21) {
//            return "106";
//        }
//        if (s == 22) {
//            return "104";
//        }
//        if (s == 23) {
//            return "107";
//        }
//        if (s == 24) {
//            return "105";
//        }
//        if (s == 25) {
//            return "102";
//        }
//        if (s == 26) {
//            return "100";
//        }
//        if (s == 27) {
//            return "103";
//        }
//        if (s == 28) {
//            return "101";
//        }
//        if (s == 34) {
//            return "108";
//        }
//        if (s == 35) {
//            return "108";
//        }
//        return "";
//    }
}