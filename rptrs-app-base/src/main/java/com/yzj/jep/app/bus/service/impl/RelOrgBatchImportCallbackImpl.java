package com.yzj.jep.app.bus.service.impl;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import com.yzj.jep.app.bus.batch.BatchInsertHistoryData;
import com.yzj.jep.app.bus.model.*;
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
import com.yzj.jep.sys.service.AppConfigService;
import com.yzj.jep.sys.service.DictItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Service(value = RelOrgBatchImportCallbackImpl.BEANID)
@Transactional
public class RelOrgBatchImportCallbackImpl implements XlsImportCallback {
    @Resource
    private RelOrgService relOrgService;

    @Resource
    DictItemService dictItemService;

    @Resource
    private RelOrgHistoryService relOrgHistoryService;

    public static final String BEANID = "RelOrgBatchImportCallbackImpl";


    /**
     * 配置导入的模板（XML）文件
     */
    private static final String XML_TEMP_FILE = "RelOrgImportMapper.xml";

    private static final String IMPORT_DATAS = "RelOrgInfo";

    private static Logger logger = LoggerFactory.getLogger(RelOrgBatchImportCallbackImpl.class);


    /**
     * 字典吗
     */
    private static final String dictCode = "dictCode";

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
        List<RelOrgInfoVo> list = (List<RelOrgInfoVo>) beans.get(IMPORT_DATAS);
        String type = (String) parameters.get("type");
        int index;
        //总数
        int sum = list.size();
        //for计数
        int num = 0;
        //社会信用统一代码为空的数量
        int count1 = 0;
        //新增的数量
        int count2 = 0;
        StringBuilder importErrorMsg = new StringBuilder();
        StringBuilder result = new StringBuilder();
        importErrorMsg.append("失败原因：");
        StringBuilder errorMsg = new StringBuilder();
        try {
            if (CollUtil.isNotEmpty(list)) {
                //根据部门存储数据权限
                UserProfile profile = UserProfileUtils.getUserProfile();
                //获取字典信息
                Map<String, Map<String, String>> dictCodeMap = obtainDictInfo(dictCode);

                // 每次导入数据时，版本号为null的数据更新成删除标识，并且加上版本号"20221231-V1"，否则直接新增数据
                // 每次导入时，先判断当天是否有新生成的版本，有的话需要更新版本号
                // 查询当前版本关联方数据
                List<Filter> isExistNum = new Filter.Builder().nnl("recordNumber").eq("delflag", "1").build();
                List<RelOrg> orgList = relOrgService.select(isExistNum);
                List<RelOrgHistory> relOrgHistories = new ArrayList<>();
                if (CollUtil.isNotEmpty(orgList)) {
                    orgList.forEach(org -> {
                        RelOrg relOrgUpdate = new RelOrg();
                        relOrgUpdate.setDelflag("0");
                        relOrgUpdate.setPid(org.getPid());
                        relOrgService.deleteByPrimaryKey(org.getPid());
                        BatchInsertHistoryData.createOrgData(relOrgHistories, org, "4", org.getPid());
                    });
                    BatchInsertHistoryData.insertOrgHistoryTable(relOrgHistories);
                    relOrgHistories.clear();
                }

                // 查询当前最新版本号
                List<Filter> filter = new Filter.Builder().nnl("recordNumber").build();
                List<Order> orders = new Order.Builder().desc("createTime").build();
                List<RelOrg> orgDayList = relOrgService.select(filter, orders);
                boolean flagExist = CollectionUtils.isNotEmpty(orgDayList);
                String recordNumberNew;
                if (flagExist) {
                    recordNumberNew = IntegerUtils.recordNumber(flagExist, orgDayList.get(0).getRecordNumber());
                } else {
                    recordNumberNew = IntegerUtils.recordNumber(flagExist, null);
                }
                if (CharSequenceUtil.isNotBlank(type)) {
                    recordNumberNew = type;
                }
                for (RelOrgInfoVo entity : list) {
                    try {
                        num++;
                        boolean b = XssCheck.reflect(entity);
                        if (!b) {
                            throw new Exception("第" + (num + 1) + "条数据格式不规范, 请检查！");
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

                        //构建机构的基本信息
                        RelOrg relOrg = builderOrgBasicInfo(entity, dictCodeMap, profile);
                        relOrg.setEffectRelation("0");
                        String maxBh = relOrgService.getMaxBh();
                        relOrg.setGlfbh(maxBh);
                        relOrg.setRelateNumber(maxBh);
                        relOrg.setRecordNumber(recordNumberNew);
                        index = relOrgService.insertSelective(relOrg);
//                        if (index > 0) {
//                            List<Filter> filters = new Filter.Builder().eq("uniscCode", relOrg.getUniscCode()).build();
//                            RelOrg relOrg2 = relOrgService.get(filters);
//                            BatchInsertHistoryData.createOrgData(relOrgHistories, relOrg, "1", relOrg2.getPid());
//                        }
                    } catch (Exception e) {
                        errorMsg.append("第【").append(num + 1).append("】条数据，关联方名称为：【").append(entity.getOrgName()).append("】的数据导入失败");
                        logger.error(e.getMessage());
                        throw new RuntimeException(e.getMessage());
                    }
                }
                // 导入成功的数据进行备份表备份
               /* BatchInsertHistoryData.insertOrgHistoryTable(relOrgHistories);
                relOrgHistories.clear();*/
                result.append("数据总数量为：【").append(sum).append("】条，成功【")
                        .append(num).append("】条。");
                // 有错误数据
                if (count1 > 0) {
                    result.append("失败【").append(count1).append("】条，").append(importErrorMsg);
                }
                // 有新增数据
                if (count2 > 0) {
                    result.append("新增【").append(count2).append("】条");
                }
                res = new XlsMessage();
                res.setMessage(result.toString());
            } else {
                res = new XlsMessage();
                res.setMessage("导入失败：");
                res.setException("导入数据为空或数据格式不正确，请检查数据！");
            }
        } finally {
            if (ObjectUtil.isNotEmpty(errorMsg)) {
                res = new XlsMessage();
                res.setMessage(errorMsg.toString());
                res.setException("失败原因：导入出现异常，请检查数据和格式是否正确！");
            }
        }
        return res;
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
        int num = 1;
        StringBuilder sb = new StringBuilder();
        Map<String, Map<String, String>> dictCodeMap = obtainDictInfo(dictCode);
        StringBuilder errorMsg = new StringBuilder();
        String msg = "";
        //确认获取的数据是否为空
        if (CollUtil.isNotEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                RelOrgInfoVo orgInfoVo = list.get(i);
                num ++;
                if (StringUtils.isEmpty(orgInfoVo.getOrgName())) {
                    sb.append("第").append(num).append("条数据【姓名】为空！");
                    continue;
                }
                msg = checkBaseInfo( dictCodeMap, orgInfoVo);
                if(StringUtils.isNotEmpty(msg)){
                    errorMsg.append("第【").append(num).append("】条数据，关联方名称为：【").append(orgInfoVo.getOrgName()).append("】的数据中");
                    errorMsg.append(msg);
                }
            }
            if(ObjectUtil.isNotEmpty(errorMsg)){
                return errorMsg.toString();
            }

        } else {
            result.append("导入文件模板不匹配或者数据为空;");
        }
        return result.toString();
    }

    private String checkBaseInfo(Map<String, Map<String, String>> dictCodeMap, RelOrgInfoVo orgInfoVo) {
        StringBuilder errorMsgDetail = new StringBuilder();

        if (orgInfoVo.getOrgName().trim().length() > 200) {
            errorMsgDetail.append("【姓名】长度超过200个字符；");
        }
        if (orgInfoVo.getIdentifyType() == null) {
            errorMsgDetail.append("【证件类型】为空；");
        }
        String identifyType = dictCodeMap.get("idTypeMap").get(orgInfoVo.getIdentifyType());
        if (orgInfoVo.getUniscCode() == null) {
            errorMsgDetail.append("【证件号码】为空；");
        } else {
                String trim = orgInfoVo.getUniscCode();
                if ("1".equals(identifyType) || "2".equals(identifyType)) {//统一社会信用代码或工商注册号
                    if (!(8 <= trim.length() && trim.length() <= 18)) {
                        errorMsgDetail.append("【证件号码格式】不正确；");
                    }
                    if (orgInfoVo.getUniscCode().trim().length() > 200) {
                        errorMsgDetail.append("【证件号码】长度超过100个字符；");
                    }
                }
//                if ("3".equals(identifyType)) {//境外有效代码
//                    if (!(trim.length() <= 30)) {
//                        errorMsgDetail.append("【证件号码格式】不正确；");
//                    }
//                }
        }

        if (orgInfoVo.getDomesticOrForeign() == null) {
            errorMsgDetail.append("【境内外标识】为空；");
        }

        return errorMsgDetail.toString();
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
