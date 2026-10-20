package com.yzj.jep.app.bus.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.yzj.jep.app.bus.batch.BatchInsertHistoryData;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.app.enums.DictionaryEnum;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.IntegerUtils;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.utils.XlsMessage;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author root
 */
@Service
public class RelBatchImportPartServiceImpl implements RelBatchImportPartService {
    private  Logger logger = LoggerFactory.getLogger(RelBatchImportPartServiceImpl.class);
    @Resource
    private RelService relService;
    @Resource
    private RelImportService relImportService;
    @Resource
    DictItemService dictItemService;
    @Resource
    private RelPsnService relPsnService;
    @Resource
    private RelOrgService relOrgService;
    @Resource
    private RelProService relProService;
    private final  String dictCode = "dictCode";
    @Override
    @Transactional(propagation= Propagation.REQUIRES_NEW,rollbackFor = RuntimeException.class)
    public XlsMessage BatchService(StringBuilder builder, StringBuilder errorMsg, List<RelVo> list,int num,int num2,int num3,String recordNumberNew) {
        //把导入的Excel插入进关联关系备份表

        // 新增时需要查询当前是否存在相应版本号的数据
        List<Filter> isExistNum = new Filter.Builder().eq("delflag", "1").build();
        List<Rel> relList = relService.select(isExistNum);

        List<RelHistory> relHistories = new ArrayList<>();

        if (CollUtil.isNotEmpty(relList)) {
            relList.forEach(rel1 -> {
                relService.deleteByPrimaryKey(rel1.getPid());
                BatchInsertHistoryData.createRelData(relHistories, rel1, "4", rel1.getPid());
            });
            BatchInsertHistoryData.insertRelHistoryTable(relHistories);
            relHistories.clear();
        }

        try {
            for (RelVo entity : list) {
                if (entity.getFromIdentifyNumber() == null) {
                    continue;
                }
                RelImport relImport = new RelImport();
                BeanUtils.copyProperties(entity, relImport);
                relImportService.insertSelective(relImport);
            }
            logger.info("插入关联关系备份表成功");
        } catch (Exception e) {
            logger.error("插入备份表失败:", e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        //根据部门存储数据权限
        UserProfile profile = UserProfileUtils.getUserProfile();
        //获取字典信息
        Map<String, Map<String, String>> dictCodeMap = obtainDictInfo(dictCode);

        /**
         * 循环list 根据证件号码查询该条前一层关联方是否在关联关系备份表中为本层关联方
         */
        for (RelVo entity : list) {
            if (entity.getFromIdentifyNumber() == null) {
                continue;
            }
            //构建关联关系
            Rel rel = builderPsnBasicInfo(entity, dictCodeMap, profile);
            rel.setRecordNumber(recordNumberNew);
            num2++;

            //进行本层关联方是否存在判断
            boolean fromFlag = this.fromRel(rel);
            boolean toFlag = this.toRel(rel);


            if (fromFlag && toFlag) {
                int i = relImportService.selectCountByToIdentifyNumber(entity.getToIdentifyNumber());
                if (i > 0) {
                    //当然上一层关联方在备份表中存在未建立关联关系的关联方，直接插入
                    try {
                        int index = relService.insertSelective(rel);
//                            if(index>0){
//                                Rel newRel = new Rel();
//                                newRel.setFromIdentifyNumber(rel.getFromIdentifyNumber());
//                                newRel.setToIdentifyNumber(rel.getToIdentifyNumber());
//                                newRel.setDelflag(DELFLAG);
//                                Rel relOld = relService.get(newRel);
//                                BatchInsertHistoryData.createRelData(relHistories,relOld,"1",  relOld.getPid());
//                            }
                        num++;
                    } catch (Exception e) {
                        errorMsg.append("第【").append(num + 1).append("】条数据，关联方名称为：【").append(entity.getToMemo()).append("】的数据导入失败");
                        logger.error("系统异常：", e);
                        throw new RuntimeException(e.getMessage());
                    }
                } else {
                    //进行本层关联方是否存在判断，与前一层关联方是否存在关系判断
                    boolean toByIsVailFlag = this.updateDatabaseData(rel);
                    if (toByIsVailFlag) {
                        //前一层关联方存在有效关系，进行插入操作
                        try {
                            int index = relService.insertSelective(rel);
//                                if(index>0){
//                                    Rel newRel = new Rel();
//                                    newRel.setFromIdentifyNumber(rel.getFromIdentifyNumber());
//                                    newRel.setToIdentifyNumber(rel.getToIdentifyNumber());
//                                    newRel.setDelflag(DELFLAG);
//                                    Rel relOld = relService.get(newRel);
//                                    BatchInsertHistoryData.createRelData(relHistories,relOld,"1",  relOld.getPid());
//                                }
                            num++;
                        } catch (Exception e) {
                            errorMsg.append("第【").append(num + 1).append("】条数据，关联方名称为：【").append(entity.getToMemo()).append("】的数据导入失败");
                            logger.error(e.getMessage());
                            throw new RuntimeException(e.getMessage());
                        }
                    } else {
                        //前一层关联方不存在有效关系，返回一个不存在有效关系的错误信息
                        builder.append("第").append(num2).append("条数据，失败原因：前一层关联方不存在有效关系");
                        builder.append("</br>");
                    }
                }
            } else {
                //记录当前数据行信息，返回一个本层关联方不存在或不是归档状态的信息
                builder.append("第").append(num2).append("条数据，失败原因：本层关联方或前一层关联方不存在");
                builder.append("</br>");
            }
        }

        String msg = "";
        if (num3 > 0) {
            msg = "其中" + num3 + "条数据已存在。";
        }
        XlsMessage res = new XlsMessage();
        if (StringUtils.isNotEmpty(builder.toString())) {
            res.setMessage("成功导入" + num + "条信息。</br>" + "导入失败信息为" + builder + msg);
        } else {
            res.setMessage("成功导入" + num + "条信息,其中信息全部存在有效关系" + msg);
        }

        return res;
    }

    public boolean fromRel(Rel rel) {
        //查询本级是否已归档
        int  result =  relService.checkRel(rel.getFromType(),rel.getFromIdentifyNumber());
        return result > 0;
    }
    public boolean toRel(Rel rel) {
        //查询本级是否已归档
        int  result =  relService.checkRel(rel.getToType(),rel.getToIdentifyNumber());
        return result > 0;
    }

    /**
     * @param entity
     * @param dictCodeMap
     * @return
     * @desc 构建自然人基本信息
     */
    public Rel builderPsnBasicInfo(RelVo entity, Map<String, Map<String, String>> dictCodeMap, UserProfile profile) {
        Rel rel = new Rel();
        //from_pid
        Map fromMap = getBaseInfo(entity.getFromIdentifyNumber(), dictCodeMap.get("fromTypeMap").get(entity.getFromType()));
        if(fromMap.containsKey("pid")){
            rel.setFromPid(fromMap.get("pid").toString());
            rel.setFromMemo(fromMap.get("name").toString());
        }
        //to_pid
        Map toMap = getBaseInfo(entity.getToIdentifyNumber(), dictCodeMap.get("toTypeMap").get(entity.getToType()));
        if(toMap.containsKey("pid")){
            rel.setToPid(toMap.get("pid").toString());
            rel.setToMemo(toMap.get("name").toString());
        }
        rel.setFromMemo(entity.getFromMemo());
        rel.setToMemo(entity.getToMemo());
        rel.setFromIdentifyNumber(entity.getFromIdentifyNumber());
        rel.setFromType(dictCodeMap.get("fromTypeMap").get(entity.getFromType()));
        rel.setToIdentifyNumber(entity.getToIdentifyNumber());
        rel.setToType(dictCodeMap.get("toTypeMap").get(entity.getToType()));
        rel.setRelPsnOrg(dictCodeMap.get("relPsnOrgMap").get(entity.getRelPsnOrg()));
        rel.setEquityShare(dictCodeMap.get("equityShareMap").get(entity.getEquityShare()));
        if(StringUtils.isNotEmpty(entity.getShareholdingRatio())){
            rel.setShareholdingRatio(Float.parseFloat(entity.getShareholdingRatio()));
        }

        rel.setIsValid(dictCodeMap.get("isValidMap").get(entity.getIsValid()));
        rel.setActiveTime(DateUtils.parseDateShort(entity.getActiveTime()));
        if(StringUtils.isNotEmpty(entity.getInvalidTime())){
            rel.setInvalidTime(DateUtils.parseDateShort(entity.getInvalidTime()));
        }
        if(StringUtils.isNotEmpty(entity.getRelationType())){
            rel.setRelationType(dictCodeMap.get("relationTypeMap").get(entity.getRelationType()));
        }

        rel.setPledgeRatio(entity.getPledgeRatio());
        rel.setRelStatement(entity.getRelStatement());
        rel.setOperator(profile.getStaffName());
        rel.setTransactionDep(profile.getDeptName());
        // 待归档
        rel.setApprovalStatus("7");
        //校验必填项
        rel.setCreateById(profile.getStaffCode());
        rel.setCreateByName(profile.getStaffName());

        return rel;
    }

    private static final String ROOT_ID = "9131000005459537XP";
    /**
     * 查询上级是否存在有效关系
     *
     * @param rel
     */
    public boolean updateDatabaseData(Rel rel) {
        String name = rel.getToMemo();
        String code = rel.getToIdentifyNumber();
        //复星主体，使用证件号判定
        if (ROOT_ID.equals(code)) {
            return true;
        }
        boolean flag = false;
        List<RelPsnOrg> result =  relService.getRelPsnOrgByNameOrNumFront(name, code);
        if(result.size() >0){
            flag = true;
        }
        return  flag;
    }

    /**
     * @param dictCode
     * @return
     * @desc 获取字典信息
     */
    public Map<String, Map<String, String>> obtainDictInfo(String dictCode) {
        Map<String, Map<String, String>> dictCodeMap = new HashMap<>(16);
        //本层关联方类型
        List<DictItem> dictItems1 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_ORGORPSN.getCode(), "", ""));
        Map fromTypeMap = dictItems1.stream().collect(Collectors.toMap(DictItem::getItemLabel, DictItem::getItemValue));
        dictCodeMap.put("fromTypeMap", fromTypeMap);
        //前一层关联方类型
        List<DictItem> dictItems2 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_ORGORPSN.getCode(), "", ""));
        Map toTypeMap = dictItems2.stream().collect(Collectors.toMap(DictItem::getItemLabel, DictItem::getItemValue));
        dictCodeMap.put("toTypeMap", toTypeMap);
        //与前一层关联关系
        List<DictItem> dictItems3 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_RELZZD.getCode(), "", ""));
        Map relPsnOrgMap = dictItems3.stream().collect(Collectors.toMap(DictItem::getItemLabel, DictItem::getItemValue,(k1,k2)->k1));
        dictCodeMap.put("relPsnOrgMap", relPsnOrgMap);
        //是否有权益份额
        List<DictItem> dictItems4 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_RELEQUITYSHARE.getCode(), "", ""));
        Map equityShareMap = dictItems4.stream().collect(Collectors.toMap(DictItem::getItemLabel, DictItem::getItemValue));
        dictCodeMap.put("equityShareMap", equityShareMap);
        //关系是否有效
        List<DictItem> dictItems5 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_ISNO.getCode(), "", ""));
        Map isValidMap = dictItems5.stream().collect(Collectors.toMap(DictItem::getItemLabel, DictItem::getItemValue));
        dictCodeMap.put("isValidMap", isValidMap);
        //关系类型
        List<DictItem> dictItems6 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_RELATIONTYPE.getCode(), "", ""));
        Map relationTypeMap = dictItems6.stream().collect(Collectors.toMap(DictItem::getItemLabel, DictItem::getItemValue));
        dictCodeMap.put("relationTypeMap", relationTypeMap);
        return dictCodeMap;
    }

    /**
     * @param property1
     * @param property2
     * @param value1
     * @param value2
     * @return
     * @desc 构建查询的filter
     */
    public List<Filter> builderFilter(String property1, String value1, String property2, String value2) {
        if ("".equals(property2.trim())) {
            return new Filter.Builder().eq(property1, value1).build();
        }
        return new Filter.Builder().eq(property1, value1).eq(property2, value2).build();
    }

    public Map getBaseInfo(String num, String type){
        Map map = new HashMap(16);
        List<Filter> filter;
        if("PSN".equals(type)){
            filter = new Filter.Builder().eq("delflag", "1")
                    .eq("status", "6").eq("identifyNumber", num).build();
            List<RelPsn> select = relPsnService.select(filter);
            if(!select.isEmpty()){
                map.put("pid", select.get(0).getPid());
                map.put("name", select.get(0).getName());
            }
        }else if("ORG".equals(type)){
            filter = new Filter.Builder().eq("delflag", "1")
                    .eq("approvalStatus", "6").eq("uniscCode", num).build();
            List<RelOrg> select = relOrgService.select(filter);
            if(!select.isEmpty()){
                map.put("pid", select.get(0).getPid());
                map.put("name", select.get(0).getOrgName());
            }
        }else if("PRO".equals(type)){
            filter = new Filter.Builder().eq("delflag", "1")
                    .eq("approvalStatus", "6").eq("proProvider", num).build();
            List<RelPro> select = relProService.select(filter);
            if(!select.isEmpty()){
                map.put("pid", select.get(0).getPid());
                map.put("name", select.get(0).getProName());
            }
        }

        return map;
    }
}
