package com.yzj.jep.app.bus.service.impl;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.yzj.jep.app.bus.batch.BatchInsertHistoryData;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.app.enums.DictionaryEnum;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.IntegerUtils;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.utils.XlsMessage;
import com.yzj.jep.core.utils.xlsio.XlsImportCallback;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.model.Role;
import com.yzj.jep.sys.service.DictItemService;
import com.yzj.jep.sys.service.DutyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 关联关系维护导入实现
 *
 * @author lq
 * @time 2020年3月13日 下午3:47:25
 * @company 银之杰技术有限公司
 */
@Service(value = RelationBatchImportCallbackImpl.BEANID)
@Transactional(rollbackFor = Exception.class)
public class RelationBatchImportCallbackImpl implements XlsImportCallback {
    Logger logger = LoggerFactory.getLogger(RelationBatchImportCallbackImpl.class);
    @Resource
    DictItemService dictItemService;
    @Resource
    private RelBatchImportPartService relBatchImportPart;

    @Resource
    private RelService relService;

    @Resource
    private RelPsnService relPsnService;
    @Resource
    private RelOrgService relOrgService;
    @Resource
    private RelProService relProService;
    @Resource
    private DutyService dutyService;

    @Resource
    private RelImportService relImportService;

    public static final String BEANID = "RelationBatchImportCallbackImpl";

    /**
     * 配置导入的模板（XML）文件
     */
    private static final String XML_TEMP_FILE = "RelationBatchImportMapper.xml";

    private static final String IMPORT_DATAS = "rels";

    private static final String DELFLAG="1";

    private final  String dictCode = "dictCode";

    @Override
    public String getImportXmlTemplate() {
        return XLSTEMPLATE_PATH + XML_TEMP_FILE;
    }

    @Override
    public void setXlsReaderBeans(Map beans) {
        //一般而言，有几个sheet页，就有几个元素
        beans.put(IMPORT_DATAS, new ArrayList());
    }
    /**
     * 文件内容导入数据库逻辑判断
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public XlsMessage executeSaveCallback(Map parameters, Map beans) throws ServiceException {
        XlsMessage res;
        int num = 0;
        StringBuilder builder = new StringBuilder();
        StringBuilder errorMsg = new StringBuilder();

        //证件号码
        //字典码
        String dictCode = "dictCode";
        int num2 = 2;
        int num3=0;
        try {
            List<RelVo> list = (List<RelVo>) beans.get(IMPORT_DATAS);
            if (list != null && !list.isEmpty()) {
                String type = (String) parameters.get("type");
                // 每次导入数据时，版本号为null的数据更新成删除标识，并且加上版本号"20221231-V1"，否则直接新增数据
                // 每次导入时，先判断当天是否有新生成的版本，有的话需要更新版本号
                String recordNumberNew = "";

                recordNumberNew = IntegerUtils.recordNumber(false, "");
                if (StrUtil.isNotBlank(type)) {
                    recordNumberNew = type;
                }

                //对关联关系导入备份表做清表操作
                try {
                    logger.info("清除关联关系备份表开始");
                    relImportService.clearTable();
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("清除关联关系备份表失败");
                    throw new RuntimeException(e);
//                    res = new XlsMessage();
//                    res.setMessage("导入失败：");
//                    res.setException("导入失败，请检查导入数据。");
//                    return res;
                }
               res = relBatchImportPart.BatchService(builder,errorMsg,list,num,num2,num3,recordNumberNew);
                //根据部门存储数据权限
 //               UserProfile profile = UserProfileUtils.getUserProfile();
                //获取字典信息
  //              Map<String, Map<String, String>> dictCodeMap = obtainDictInfo(dictCode);

                //把导入的Excel插入进关联关系备份表
//                try {
//                    for (RelVo entity : list) {
//                        if (entity.getFromIdentifyNumber() == null) {
//                            continue;
//                        }
//                        RelImport relImport = new RelImport();
//                        BeanUtils.copyProperties(entity, relImport);
//                        relImportService.insertSelective(relImport);
//                    }
//                    logger.info("插入关联关系备份表成功");
//                } catch (Exception e) {
//                    logger.error("插入备份表失败");
//                    e.printStackTrace();
//                    throw new RuntimeException(e);
//                }
                /**
                 * 循环list 根据证件号码查询该条前一层关联方是否在关联关系备份表中为本层关联方
                 */
//                for (RelVo entity : list) {
//                    if (entity.getFromIdentifyNumber() == null) {
//                        continue;
//                    }
//                    //构建关联关系
//                    Rel rel = builderPsnBasicInfo(entity, dictCodeMap, profile);
//                    rel.setRecordNumber(recordNumberNew);
//                    num2++;
//
//                    //进行本层关联方是否存在判断
//                    boolean fromFlag = this.fromRel(rel);
//                    boolean toFlag = this.toRel(rel);
//
//
//                    if (fromFlag && toFlag) {
//                        int i = relImportService.selectCountByToIdentifyNumber(entity.getToIdentifyNumber());
//                        if (i > 0) {
//                            //当然上一层关联方在备份表中存在未建立关联关系的关联方，直接插入
//                            try {
//                                int index = relService.insertSelective(rel);
////                            if(index>0){
////                                Rel newRel = new Rel();
////                                newRel.setFromIdentifyNumber(rel.getFromIdentifyNumber());
////                                newRel.setToIdentifyNumber(rel.getToIdentifyNumber());
////                                newRel.setDelflag(DELFLAG);
////                                Rel relOld = relService.get(newRel);
////                                BatchInsertHistoryData.createRelData(relHistories,relOld,"1",  relOld.getPid());
////                            }
//                                num++;
//                                entity.getPid().toString();
//                            } catch (Exception e) {
//                                errorMsg.append("第【").append(num + 1).append("】条数据，关联方名称为：【").append(entity.getToMemo()).append("】的数据导入失败");
//                                logger.error(e.getMessage());
//                                throw new RuntimeException(e.getMessage());
//                            }
//                        } else {
//                            //进行本层关联方是否存在判断，与前一层关联方是否存在关系判断
//                            boolean toByIsVailFlag = this.updateDatabaseData(rel);
//                            if (toByIsVailFlag) {
//                                //前一层关联方存在有效关系，进行插入操作
//                                try {
//                                    int index = relService.insertSelective(rel);
////                                if(index>0){
////                                    Rel newRel = new Rel();
////                                    newRel.setFromIdentifyNumber(rel.getFromIdentifyNumber());
////                                    newRel.setToIdentifyNumber(rel.getToIdentifyNumber());
////                                    newRel.setDelflag(DELFLAG);
////                                    Rel relOld = relService.get(newRel);
////                                    BatchInsertHistoryData.createRelData(relHistories,relOld,"1",  relOld.getPid());
////                                }
//                                    num++;
//                                    entity.getPid().toString();
//                                } catch (Exception e) {
//                                    errorMsg.append("第【").append(num + 1).append("】条数据，关联方名称为：【").append(entity.getToMemo()).append("】的数据导入失败");
//                                    logger.error(e.getMessage());
//                                    throw new RuntimeException(e.getMessage());
//                                }
//                            } else {
//                                //前一层关联方不存在有效关系，返回一个不存在有效关系的错误信息
//                                builder.append("第").append(num2).append("条数据，失败原因：前一层关联方不存在有效关系");
//                                builder.append("</br>");
//                            }
//                        }
//                    } else {
//                        //记录当前数据行信息，返回一个本层关联方不存在或不是归档状态的信息
//                        builder.append("第").append(num2).append("条数据，失败原因：本层关联方或前一层关联方不存在");
//                        builder.append("</br>");
//                    }
//                }
//            BatchInsertHistoryData.insertRelHistoryTable(relHistories);
//            relHistories.clear();
//                String msg = "";
//                if (num3 > 0) {
//                    msg = "其中" + num3 + "条数据已存在。";
//                }
//                res = new XlsMessage();
//                if (StringUtils.isNotEmpty(builder.toString())) {
//                    res.setMessage("成功导入" + num + "条信息。</br>" + "导入失败信息为" + builder + msg);
//                } else {
//                    res.setMessage("成功导入" + num + "条信息,其中信息全部存在有效关系" + msg);
//                }
            } else {
                res = new XlsMessage();
                res.setMessage("导入失败：");
                res.setException("导入失败，请检查导入数据。");
                return res;
            }
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }finally {
            if (ObjectUtil.isNotEmpty(errorMsg)){
                res = new XlsMessage();
                res.setMessage(errorMsg.toString());
                res.setException("失败原因：导入出现异常，请检查数据和格式是否正确！");
            }
        }
        return res;
    }

    /**
     * 如果是总部合规部关联交易管理岗导入的，则直接归档
     */
    public void judeRel(UserProfile profile, Rel rel){
        List<Role> rolelist = dutyService.getStaffRoleList(profile.getStaffCode(), null);
        if(CollUtil.isNotEmpty(rolelist)){
            for(Role role : rolelist){
                if("1000012".equals(role.getRoleCode())){
                    rel.setApprovalStatus("6");
                }
            }
        }
    }

    /**
     * 查询上级是否存在有效关系
     *
     * @param rel
     */
    public boolean updateDatabaseData(Rel rel) {
        String name = rel.getToMemo();
        String code = rel.getToIdentifyNumber();
        boolean flag = false;
        List<RelPsnOrg> result =  relService.getRelPsnOrgByNameOrNumFront(name, code);
        if(result.size() >0){
            flag = true;
        }
        return  flag;
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
     * 获取文件内容，验证数据的有效性
     */
    @Override
    public String checkBeforeImport(Map parameters, Map mapReaded) throws ServiceException {
        //如果通过检查，就保持result为null，否则给出返回错误描述
        StringBuilder result = new StringBuilder();
        List<RelVo> list = (List<RelVo>) mapReaded.get(IMPORT_DATAS);
        //因为有一条空数据
        if (!list.isEmpty()) {
            //根据部门存储数据权限
            UserProfile profile = UserProfileUtils.getUserProfile();
            //获取字典信息
            Map<String, Map<String, String>> dictCodeMap = obtainDictInfo(dictCode);
            //TODO 执行导入前的检查，如xxx编码是否存在，xxx引用的编码不存在等
            for(int i=0;i<list.size();i++){
                if (StringUtils.isEmpty(list.get(i).getToType())) {
                    break;
                }
                //字符串校验
                checkEmpty(list.get(i), result,i);
                //日期校验
                checkDate(list.get(i).getActiveTime(),list.get(i).getInvalidTime(),result,i,list.get(i).getFromMemo());
                checkRelPsnAndOrg(list.get(i),result,dictCodeMap,profile,i);
            }

        } else {
            result.append("导入文件模板不匹配或者数据为空;");
        }
        return result.toString();
    }

    @Override
    public Map setReturnBeanFromXls(Map beans, InputStream inputXls) throws ServiceException {
        return beans;
    }

    /**
     * 检测关联方是否存在
     * @param relVo
     * @param result
     * @param dictCodeMap
     * @param profile
     * @param num2
     */
    public void checkRelPsnAndOrg(RelVo relVo,StringBuilder result,Map<String, Map<String, String>> dictCodeMap,UserProfile profile,int num2){
        Rel rel = builderPsnBasicInfo(relVo, dictCodeMap, profile);
        //进行本层关联方是否存在判断
        boolean fromFlag = this.fromRel(rel);
        boolean toFlag = this.toRel(rel);


        if (fromFlag && toFlag) {

        }else {
            result.append("第").append(num2+3).append("条数据，失败原因：本层关联方或前一层关联方不存在");
            result.append("</br>");
        }
    }

    /**
     * @param rel
     * @desc 字段的非空校验
     */
    public void checkEmpty(RelVo rel, StringBuilder result, int i) {
        if (StringUtils.isEmpty(rel.getFromMemo())) {
            result.append("校验结果：第").append(i + 1).append("条数据的").append("本层关联方名称为空;").append("\r\n");
        }else {
            if (rel.getFromMemo().trim().length() > 200) {
                result.append("【姓名】长度超过200个字符；").append("\r\n");
            }
        }

        if (StringUtils.isEmpty(rel.getFromIdentifyNumber())) {
            result.append("校验结果：第").append(i + 1).append("条本层关联方名称为：【").append(rel.getFromMemo()).append("】的数据的本层关联方代码为空;").append("\r\n");
        }

        if (StringUtils.isEmpty(rel.getFromType())) {
            result.append("校验结果：第").append(i + 1).append("条本层关联方名称为：【").append(rel.getFromMemo()).append("】的数据的本层关联方类型为空;").append("\r\n");
        }
        if (StringUtils.isEmpty(rel.getToMemo())) {
            result.append("校验结果：第").append(i + 1).append("条本层关联方名称为：【").append(rel.getFromMemo()).append("】的数据的前一层关联方名称为空;").append("\r\n");
        }else {
            if (rel.getToMemo().trim().length() > 200) {
                result.append("【姓名】长度超过200个字符；").append("\r\n");
            }
        }

        if (StringUtils.isEmpty(rel.getToIdentifyNumber())) {
            result.append("校验结果：第").append(i + 1).append("条本层关联方名称为：【").append(rel.getFromMemo()).append("】的数据的前一层关联方代码为空;").append("\r\n");
        }

        if (StringUtils.isEmpty(rel.getToType())) {
            result.append("校验结果：第").append(i + 1).append("条本层关联方名称为：【").append(rel.getFromMemo()).append("】的数据的前一层关联方类型为空;").append("\r\n");
        }

        if (StringUtils.isEmpty(rel.getRelPsnOrg())) {
            result.append("校验结果：第").append(i + 1).append("条本层关联方名称为：【").append(rel.getFromMemo()).append("】的数据的与前一层关联关系为空;").append("\r\n");
        }

        if (StringUtils.isEmpty(rel.getEquityShare())) {
            result.append("校验结果：第").append(i + 1).append("条本层关联方名称为：【").append(rel.getFromMemo()).append("】的数据的是否有权益份额为空;").append("\r\n");
        }

        if (StringUtils.isNotEmpty(rel.getEquityShare()) && !"0".equals(rel.getEquityShare())) {
            if (StringUtils.isEmpty(String.valueOf(rel.getShareholdingRatio()))) {
                result.append("校验结果：第").append(i + 1).append("条本层关联方名称为：【").append(rel.getFromMemo()).append("】的数据的持有比例（%）为空;").append("\r\n");
            }else {
                if(String.valueOf(rel.getShareholdingRatio()).trim().length()>6){
                    result.append("校验结果：第").append(i + 1).append("条本层关联方名称为：【").append(rel.getFromMemo()).append("】的数据的持有比例（%）过长;").append("\r\n");
                }
            }
            if (StringUtils.isEmpty(String.valueOf(rel.getPledgeRatio()))) {
                result.append("校验结果：第").append(i + 1).append("条本层关联方名称为：【").append(rel.getFromMemo()).append("】的数据的股权质押比例（%）为空;").append("\r\n");
            }
        }

        if (StringUtils.isEmpty(rel.getIsValid())) {
            result.append("校验结果：第").append(i + 1).append("条本层关联方名称为：【").append(rel.getFromMemo()).append("】的数据的关系是否有效为空;").append("\r\n");
        }

        if (StringUtils.isEmpty(rel.getRelStatement())) {
            result.append("校验结果：第").append(i + 1).append("条本层关联方名称为：【").append(rel.getFromMemo()).append("】的数据的关联关系说明为空;").append("\r\n");
        }else {
            if(rel.getRelStatement().trim().length() > 1000){
                result.append("校验结果：第").append(i + 1).append("条本层关联方名称为：【").append(rel.getFromMemo()).append("】的数据的关联关系说明字段过长;").append("\r\n");
            }
        }

        if (StringUtils.isEmpty(rel.getRelationType())) {
            result.append("校验结果：第").append(i + 1).append("条本层关联方名称为：【").append(rel.getFromMemo()).append("】的数据的关联类型为空;").append("\r\n");
        }

    }


    /**
     * @param
     * @desc 日期校验
     */
    public void checkDate(String activeTime, String invalidTime, StringBuilder result, int i, String fromMemo) {
        if (StringUtils.isEmpty(activeTime)) {
            result.append("校验结果：第").append(i + 1).append("条本层关联方名称为：【").append(fromMemo).append("】的数据的生效日期为空;").append("\r\n");
        }
        String str = "[0-9]*";
        if (!activeTime.matches(str)) {
            result.append("校验结果：第").append(i + 1).append("条本层关联方名称为：【").append(fromMemo).append("】的数据的生效日期不合法，含有字符,日期格式应改为为这种形式：20200101;").append("\r\n");
            return;
        }
        if (StringUtils.isNotEmpty(invalidTime)) {
            if (!invalidTime.matches(str)) {
                result.append("校验结果：第").append(i + 1).append("条本层关联方名称为：【").append(fromMemo).append("】的数据的失效日期不合法，含有字符,日期格式应改为为这种形式：20200101;").append("\r\n");
                return;
            }
        }

        if (activeTime.length() != 8) {
            result.append("校验结果：第").append(i + 1).append("条本层关联方名称为：【").append(fromMemo).append("】的数据的生效日期长度不合法，应为8位数字;").append("\r\n");
        } else {
            int activeYear = Integer.parseInt(activeTime.substring(0, 4));
            int activeMonth = Integer.parseInt(activeTime.substring(4, 6));
            int activeDay = Integer.parseInt(activeTime.substring(6, 8));

            if (activeYear < 1970 || activeYear > 2189 || activeMonth > 12 || activeMonth < 1 || activeDay > 31 || activeDay < 1) {
                if (!checkDay(activeYear, activeMonth, activeDay)) {
                    result.append("校验结果：第").append(i + 1).append("条本层关联方名称为：【").append(fromMemo).append("】的数据的生效日期年份、月份、天输入错误,不符合常规;").append("\r\n");
                }
            }
        }

        if (StringUtils.isNotEmpty(invalidTime)) {
            if (invalidTime.length() != 8) {
                result.append("校验结果：第").append(i + 1).append("条本层关联方名称为：【").append(fromMemo).append("】的数据的失效日期长度不合法，应为8位数字;").append("\r\n");
            } else {
                int invalidYear = Integer.parseInt(invalidTime.substring(0, 4));
                int invalidMonth = Integer.parseInt(invalidTime.substring(4, 6));
                int invalidDay = Integer.parseInt(invalidTime.substring(6, 8));
                if (invalidYear < 1970 || invalidYear > 2189 || invalidMonth > 12 || invalidMonth < 1) {
                    if (!checkDay(invalidYear, invalidMonth, invalidDay)) {
                        result.append("校验结果：第").append(i + 1).append("条本层关联方名称为：【").append(fromMemo).append("】的数据的失效日期年份、月份、天输入错误,不符合常规;").append("\r\n");
                    }
                }
            }
        }

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

    public boolean checkDay(Integer year, Integer month, Integer day){
        boolean flag=true;
        switch (month){
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                if (day >31 || day < 1){
                    flag=false;
                }
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                if (day > 30 || day < 1){
                    flag=false;
                }
                break;
            case 2:
                if (year % 4 == 0 && year % 100 != 0) {
                    if (day > 29 || day < 1){
                        flag=false;
                    }
                } else if (year % 400 == 0){
                    if (day > 29 || day < 1){
                        flag=false;
                    }
                } else {
                    if (day > 28 || day < 1){
                        flag=false;
                    }
                }
                break;
        }
        return flag ;
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
}
