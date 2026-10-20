package com.yzj.jep.app.bus.service.impl;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.yzj.jep.app.bus.batch.BatchInsertHistoryData;
import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.model.RelOrgHistory;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.service.RelOrgHistoryService;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.vo.RelOrgInfoVo;
import com.yzj.jep.app.enums.DictionaryEnum;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.sys.util.XssCheck;
import com.yzj.jep.app.util.IntegerUtils;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.utils.XlsMessage;
import com.yzj.jep.core.utils.xlsio.XlsImportCallback;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 自然人模板导出实现
 *
 * @author lq
 * @time 2020年3月13日 下午3:47:25
 * @company 银之杰技术有限公司
 */
@Service(value = RelOrgImportCallbackImpl.BEANID)
public class RelOrgImportCallbackImpl implements XlsImportCallback {
    @Resource
    private RelOrgService relOrgService;

    @Resource
    DictItemService dictItemService;

    @Resource
    private RelOrgHistoryService relOrgHistoryService;

    public static final String BEANID = "RelOrgImportCallbackImpl";


    /**
     * 配置导入的模板（XML）文件
     */
    private static final String XML_TEMP_FILE = "RelOrgImportMapper.xml";

    private static final String IMPORT_DATAS = "RelOrgInfo";


    @Override
    public String getImportXmlTemplate() {
        return XLSTEMPLATE_PATH + XML_TEMP_FILE;
    }

    @Override
    public void setXlsReaderBeans(Map beans) {
        //一般而言，有几个sheet页，就有几个元素
        beans.put(IMPORT_DATAS, new ArrayList<>());
    }

    @Override
    public XlsMessage executeSaveCallback(Map parameters, Map beans) throws ServiceException {
        XlsMessage res;
        String dictCode = "dictCode";
        String uniscCode = "uniscCode";
        String delflag = "delflag";
        List<RelOrgInfoVo> list = (List<RelOrgInfoVo>) beans.get(IMPORT_DATAS);
        int index;
        //总数
        int sum = list.size();
        //for计数
        int num = 0;
        //社会信用统一代码为空的数量
        int count1 = 0;
        //新增的数量
        int count2 = 0;
        //更新的数量
        int count3 = 0;
        StringBuilder importErrorMsg = new StringBuilder();
        StringBuilder result = new StringBuilder();
        importErrorMsg.append("失败原因：");
        try {
            if (CollUtil.isNotEmpty(list)) {
                //根据部门存储数据权限
                UserProfile profile = UserProfileUtils.getUserProfile();
                //获取字典信息
                Map<String, Map<String, String>> dictCodeMap = obtainDictInfo(dictCode);
                List<RelOrgHistory> relOrgHistoryAddList = new ArrayList<>();
                List<RelOrgHistory> relOrgHistoryUpdataList = new ArrayList<>();
                for (RelOrgInfoVo entity : list) {
                    try {
                        boolean b = XssCheck.reflect(entity);
                        if (!b) {
                            throw new Exception("数据格式不规范, 请检查！");
                        }
                        if (entity.getOrgName() == null) {
                            result.append("第").append(num + 1).append("条数据【法人或非法人组织名称】为空！");
                            continue;
                        }
                        StringBuilder errorMsgDetail = new StringBuilder();
                        StringBuilder errorMsg = new StringBuilder();
                        errorMsg.append("第【").append(num + 1).append("】条数据，关联方名称为：【").append(entity.getOrgName()).append("】的数据中");
                        if (entity.getUniscCode() == null) {
                            errorMsgDetail.append("【证件号码】为空；");
                        }
                        if (entity.getIdentifyType() == null) {
                            errorMsgDetail.append("【证件类型】为空；");
                        }
                        if (entity.getDomesticOrForeign() == null) {
                            errorMsgDetail.append("【境内外标识】为空；");
                        }

                        if(ObjectUtil.isNotEmpty(errorMsgDetail)){
                            result.append(errorMsg).append(errorMsgDetail);
                            continue;
                        }

                        if (entity.getIdentifyType() != null && entity.getUniscCode() != null && entity.getDomesticOrForeign() != null) {
                            String identifyType = entity.getIdentifyType();
                            String uniscCode1 = entity.getUniscCode();
                            String orgName = entity.getOrgName();
                            String domesticOrForeign = entity.getDomesticOrForeign();
                            entity.setIdentifyType(identifyType.trim());
                            entity.setUniscCode(uniscCode1.trim());
                            entity.setOrgName(orgName.trim());
                            entity.setDomesticOrForeign(domesticOrForeign.trim());
                        }
                        //检查基本信息是否存在
                        boolean checkOrgExist = false;
                        //查询机构信息
                        List<RelOrg> relOrgList = relOrgService.select(builderFilterTwo(uniscCode,
                                entity.getUniscCode(), delflag, "1"));
                        if (CollUtil.isNotEmpty(relOrgList)) {
                            checkOrgExist = true;
                        }
                        String recordNumberNew;
                        // 新增时需要查询当前是否存在相应版本号的数据
                        List<Filter> isExistNum = new Filter.Builder().nnl("recordNumber").eq("delflag", "1").build();
                        List<RelOrg> relPsns = relOrgService.select(isExistNum);
                        if(CollUtil.isNotEmpty(relPsns)){
                            recordNumberNew = relPsns.get(0).getRecordNumber();
                        }else {
                            recordNumberNew = IntegerUtils.recordNumber(false, null);
                        }
                        //构建机构的基本信息
                        RelOrg relOrg = builderOrgBasicInfo(entity, dictCodeMap, profile);

                        //如果不是新的基础信息，则无需进行更新或插入
                        if (checkOrgExist) {
                            relOrg.setPid(relOrgList.get(0).getPid());
                            relOrg.setRecordNumber(recordNumberNew);
                            //如果状态不是已归档则用数据库中的状态
                            if (!"6".equals(relOrg.getApprovalStatus())) {
                                relOrg.setApprovalStatus(relOrgList.get(0).getApprovalStatus());
                            }
                            index = relOrgService.updateSelective(relOrg);
                            if (index > 0) {
                                BatchInsertHistoryData.createOrgData(relOrgHistoryUpdataList, relOrgList.get(0), "2", relOrg.getPid());
                            }
                            count3++;
                        } else {
                            relOrg.setEffectRelation("0");
                            String maxBh = relOrgService.getMaxBh();
                            relOrg.setGlfbh(maxBh);
                            relOrg.setRecordNumber(recordNumberNew);
                            index = relOrgService.insertSelective(relOrg);
                            if (index > 0) {
                                List<Filter> filters = new Filter.Builder().eq("uniscCode", relOrg.getUniscCode()).build();
                                RelOrg relOrg2 = relOrgService.get(filters);
                                BatchInsertHistoryData.createOrgData(relOrgHistoryAddList, relOrg, "1", relOrg2.getPid());
                            }
                            count2++;
                        }
                        num ++;
                    } catch (Exception e) {
                        importErrorMsg.append(e.getMessage());
                        count1++;
                    }
                }
                // 导入成功的数据进行备份表备份
                BatchInsertHistoryData.insertOrgHistoryTable(relOrgHistoryAddList);
                BatchInsertHistoryData.insertOrgHistoryTable(relOrgHistoryUpdataList);
                result.append("数据总数量为：【").append(sum).append("】条，成功【")
                        .append(num).append("】条。");
                // 有错误数据
                if(count1 > 0){
                    result.append("失败【").append(count1).append("】条，").append(importErrorMsg);
                }
                // 有新增数据
                if(count2 > 0){
                    result.append("新增【").append(count2).append("】条");
                }
                // 有更新数据
                if(count3 > 0){
                    result.append("更新【").append(count3).append("】条");
                }
                res = new XlsMessage();
                res.setMessage(result.toString());
                return res;
            } else {
                res = new XlsMessage();
                res.setMessage("导入失败：");
                res.setException("导入数据为空或数据格式不正确，请检查数据！");
                return res;
            }
        } catch (Exception e) {
            res = new XlsMessage();
            res.setMessage("导入失败：");
            res.setException("失败原因：导入过程出现异常，请重试！");
            return res;
        }
    }

    /**
     * 构建机构的基本信息
     */
    public RelOrg builderOrgBasicInfo(RelOrgInfoVo entity, Map<String, Map<String, String>> dictCodeMap, UserProfile profile) {
        RelOrg relOrg = new RelOrg();
        relOrg.setOrgName(entity.getOrgName());
        relOrg.setIdentifyType(dictCodeMap.get("idTypeMap").get(entity.getIdentifyType()));
        relOrg.setUniscCode(entity.getUniscCode());
        relOrg.setDomesticOrForeign(dictCodeMap.get("foreignMap").get(entity.getDomesticOrForeign()));
        relOrg.setLegalPerson(entity.getLegalPerson());
        String regCapital = entity.getRegCapital() == null ? "0" : entity.getRegCapital();
        relOrg.setRegCapital(Float.parseFloat(regCapital));
        relOrg.setOrgNature(entity.getOrgNature());
        relOrg.setRegAddress(entity.getRegAddress());
        relOrg.setBusinessScope(entity.getBusinessScope());
        //导入时默认都没加入黑名单
        relOrg.setIsBlack("0");
        relOrg.setDelflag("1");
        relOrg.setMgerOrg(profile.getUnitCode());
        //数据源默认设置下手工录入
        relOrg.setDataSource("2");
        relOrg.setModifiedTime(new Date());
        relOrg.setPledgeRatio(entity.getPledgeRatio());
        relOrg.setApprovalStatus("6");

        relOrg.setAmendId(profile.getStaffCode());
        relOrg.setAmendName(profile.getStaffName());
        relOrg.setCreateById(profile.getStaffCode());
        relOrg.setCreateByName(profile.getStaffName());
        relOrg.setTransactionDep(profile.getDeptName());
        relOrg.setOperator(profile.getStaffName());
        relOrg.setRegCapitalCurrency(entity.getRegCapitalCurrency());
        return relOrg;
    }

    /**
     * 获取字典信息
     */
    public Map<String, Map<String, String>> obtainDictInfo(String dictCode) {
        Map<String, Map<String, String>> dictCodeMap = new HashMap<>(16);
        //自然人证件类型
        List<DictItem> dictItems0 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_IDENTIFYTYPE.getCode()));
        Map<String, String> relIdTypeMap = dictItems0.stream().collect(Collectors.toMap(DictItem::getItemLabel, DictItem::getItemValue));
        dictCodeMap.put("relIdTypeMap", relIdTypeMap);
        //关联方证件类型
        List<DictItem> dictItems1 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_ORGIDENTIFYTYPE.getCode()));
        Map<String, String> idTypeMap = dictItems1.stream().collect(Collectors.toMap(DictItem::getItemLabel, DictItem::getItemValue));
        dictCodeMap.put("idTypeMap", idTypeMap);
        //境内外标识
        List<DictItem> dictItems2 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_DOMESTICORFOREIGN.getCode()));
        Map<String, String> foreignMap = dictItems2.stream().collect(Collectors.toMap(DictItem::getItemLabel, DictItem::getItemValue));
        dictCodeMap.put("foreignMap", foreignMap);
        //关联关系类型
        List<DictItem> dictItems3 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_RELCATEGORY.getCode()));
        Map<String, String> relCategoryMap = dictItems3.stream().collect(Collectors.toMap(DictItem::getItemLabel, DictItem::getItemValue));
        dictCodeMap.put("relCategoryMap", relCategoryMap);
        //关系
        List<DictItem> dictItems4 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_REL.getCode()));
        Map<String, String> relMap = dictItems4.stream().collect(Collectors.toMap(DictItem::getItemLabel, DictItem::getItemValue));
        dictCodeMap.put("relMap", relMap);
        //机构状态
        List<DictItem> dictItems5 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_STATUS.getCode()));
        Map<String, String> statusMap = dictItems5.stream().collect(Collectors.toMap(DictItem::getItemLabel, DictItem::getItemValue));
        dictCodeMap.put("statusMap", statusMap);
        //币种代码
        List<DictItem> dictItems6 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_CURRENCY.getCode()));
        Map<String, String> currencyMap = dictItems6.stream().collect(Collectors.toMap(DictItem::getItemLabel, DictItem::getItemValue));
        dictCodeMap.put("currencyMap", currencyMap);
        //企业类型
        List<DictItem> dictItems7 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_COMPANYTYPE.getCode()));
        Map<String, String> companyTypeMap = dictItems7.stream().collect(Collectors.toMap(DictItem::getItemLabel, DictItem::getItemValue));
        dictCodeMap.put("companyTypeMap", companyTypeMap);
        //关联关系判定
        List<DictItem> dictItems10 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_PREDICATE.getCode()));
        Map<String, String> predicateMap = dictItems10.stream().collect(Collectors.toMap(DictItem::getItemLabel, DictItem::getItemValue));
        dictCodeMap.put("predicateMap", predicateMap);
        return dictCodeMap;
    }

    /**
     * 构建查询的filter
     */
    public List<Filter> builderFilterTwo(String property1, String value1, String property2, String value2) {
        return new Filter.Builder().eq(property1, value1).eq(property2, value2).build();
    }

    /**
     * 构建查询的filter
     */
    public List<Filter> builderFilter(String property1, String value1) {
        return new Filter.Builder().eq(property1, value1).build();
    }

    @Override
    public String checkBeforeImport(Map parameters, Map mapReaded) throws ServiceException {
        //如果通过检查，就保持result为null，否则给出返回错误描述
        StringBuilder result = new StringBuilder();
        //获取的数据在这里封装
        List<RelOrgInfoVo> list = (List<RelOrgInfoVo>) mapReaded.get(IMPORT_DATAS);
        //确认获取的数据是否为空
        if (CollUtil.isNotEmpty(list)) {
            //TODO 执行导入前的检查，如xxx编码是否存在，xxx引用的编码不存在等
            for (int i = 0; i < list.size(); i++) {
            }

        } else {
            result.append("导入文件模板不匹配或者数据为空;");
        }
        return result.toString();
    }

    @Override
    public Map setReturnBeanFromXls(Map beans, InputStream inputXLS) throws ServiceException {
        List<RelOrgInfoVo> list = (List<RelOrgInfoVo>) beans.get(IMPORT_DATAS);

        if (CollectionUtils.isNotEmpty(list)) {
            for (RelOrgInfoVo entity : list) {
                if (!StringUtils.isEmpty(entity.getOrgLegalIdentifyNumber())) {
                    //TODO 演示对从excel读取后的数据在做导入处理前做业务处理
                    RelOrgInfoVo relOrgInfoVo = new RelOrgInfoVo();
                    relOrgInfoVo.setOrgName(entity.getOrgName());
                    relOrgInfoVo.setIdentifyType(entity.getIdentifyType());
                    System.out.println("演示，在这里对：" + entity.getOrgName() + "做进一步处理。");
                }
            }
        }

        return beans;
    }
}
