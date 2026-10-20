package com.yzj.jep.app.bus.service.impl;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.model.RelOrgHistory;
import com.yzj.jep.app.bus.model.RelPsnHistory;
import com.yzj.jep.app.bus.service.RelOrgHistoryService;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.vo.OrgInfoVo;
import com.yzj.jep.app.enums.DictionaryEnum;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.ConfidentialInfoUtil;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.model.Unit;
import com.yzj.jep.sys.service.DictItemService;
import com.yzj.jep.sys.service.UnitService;
import com.yzj.jep.web.entity.NameValuePair;
import com.yzj.jep.web.util.NVUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.yzj.jep.commons.StringUtils.isEmpty;

/**
 * @author
 */
@Service(value = OrgHistoryExportCallbackImpl.BEANID)
public class OrgHistoryExportCallbackImpl implements XlsExportCallback {

    public static final String BEANID = "OrgHistoryExportCallbackImpl";

    private static final String EXPORT_TEMPLATE_NAME = "RelOrgHistory.xls";

    private static final String PREFIX = "机构信息_";

    private static final String SUFFIX = ".xls";

    private static final String DICTCODE = "dictCode";

    private static final String YES = "1";

    private static final String NO = "0";

    private static final String PLACEHOLDER = "%";

    RelOrgHistoryService relOrgHistoryService;

    @Autowired
    private UnitService unitService;

    @Autowired
    DictItemService dictItemService;


    @Override
    public String getExportTemplate() {
        return XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_NAME;
    }

    @Override
    public String getExportFilename(Map parameters) throws ServiceException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        String fileName = PREFIX + sf.format(new Date()) + SUFFIX;
        return fileName;
    }

    @Override
    public Map<Integer, List<int[]>> getMergeColumns() {
        return null;
    }

    @Override
    public Map<Integer, Integer> getMergeStartRowNum() {
        return null;
    }

    /**
     * 编写业务代码
     */
    @Override
    public Map executeExportCallback(Map parameters) throws ServiceException {
        relOrgHistoryService = SpringContextHolder.getBean(RelOrgHistoryService.class);
        List<OrgInfoVo> orgInfoVos = new ArrayList<>();
        Map resultMap = new HashMap(16);
        //字典码
        String dictCode = DICTCODE;
        String data = (String) parameters.get("id");
        String[] datas = data.split(",");
        String createTime = datas[1];
        String recordNumber = datas[2];
        String recordType = datas[3];
        List<RelOrgHistory> relOrgs = new ArrayList<>();
        // 时点查询列表数据
        if(StrUtil.isNotBlank(createTime)){
            // 列表默认查询列表当天时间
            relOrgs = relOrgHistoryService.queryRecordNumberData(createTime);
        }else {
            relOrgs = relOrgHistoryService.queryRecordNumber(recordType, recordNumber);
        }
        //字典码
        //获取字典码
        Map<String, Map<String, String>> dictCodeMap = obtainDictInfo(DICTCODE);

        for (RelOrgHistory relOrg : relOrgs) {
            //机构信息不完整，则不导出基本信息
            if (relOrg.getIdentifyType() == null || relOrg.getOrgName() == null) {
                continue;
            }
            OrgInfoVo orgInfoVo = new OrgInfoVo();
            //机构的基本信息
            builderOrgBasicInfo(orgInfoVo, relOrg, dictCodeMap);
            orgInfoVos.add(orgInfoVo);
        }
        resultMap.put("relOrgHistory", orgInfoVos);
        return resultMap;
    }

    /**
     * @param orgInfoVo
     * @param relOrg
     * @param dictCodeMap
     * @desc 构建机构的基本信息
     */
    public void builderOrgBasicInfo(OrgInfoVo orgInfoVo, RelOrgHistory relOrg, Map<String, Map<String, String>> dictCodeMap) {
        orgInfoVo.setOrgName(relOrg.getOrgName());
        orgInfoVo.setIdentifyType(dictCodeMap.get("orgIdTypeMap").get(relOrg.getIdentifyType()));
        orgInfoVo.setUniscCode(ConfidentialInfoUtil.hideAllIdCardNum(relOrg.getUniscCode()));
        orgInfoVo.setDomesticOrForeign(dictCodeMap.get("foreignMap").get(relOrg.getDomesticOrForeign()));
        orgInfoVo.setMgerOrg(mgerOrg(relOrg.getMgerOrg()));
        orgInfoVo.setRegAddress(relOrg.getRegAddress());
        orgInfoVo.setLegalPerson(relOrg.getLegalPerson());
        orgInfoVo.setLegalIdentifytype(dictCodeMap.get("idTypeMap").get(relOrg.getLegalIdentifyType()));
        orgInfoVo.setLegalIdentifyNumber(ConfidentialInfoUtil.hideAllIdCardNum(relOrg.getLegalIdentifyNumber()));
        orgInfoVo.setStatus(dictCodeMap.get("statusMap").get(relOrg.getStatus()));
        orgInfoVo.setOrgType(dictCodeMap.get("companyTypeMap").get(relOrg.getOrgType()));
        orgInfoVo.setBusinessScope(relOrg.getBusinessScope());
        orgInfoVo.setRegCapital(null == relOrg.getRegCapital() ? "" : relOrg.getRegCapital() + "");
        orgInfoVo.setRegCapitalCurrency(dictCodeMap.get("currencyMap").get(relOrg.getRegCapitalCurrency()));
        orgInfoVo.setIsShareholder(isNo(relOrg.getIsShareholder()));
        orgInfoVo.setPledgeRatio(relOrg.getPledgeRatio());
        orgInfoVo.setShareholdingProportion(null == relOrg.getShareholdingProportion() ? "" : relOrg.getShareholdingProportion());
        //集团证件号
        orgInfoVo.setMemGroup(relOrg.getMemGroup() == null ? "" : relOrg.getMemGroup());
        orgInfoVo.setOperator(relOrg.getOperator());
        orgInfoVo.setTransactionDep(relOrg.getTransactionDep());
        orgInfoVo.setRecordType(dictCodeMap.get("operationtypeMap").get(relOrg.getRecordType()));
        orgInfoVo.setRecordNumber(relOrg.getRecordNumber());
        orgInfoVo.setCreateTimeString(relOrg.getCreateTimeString());


    }

    /**
     * @param parameters
     * @return
     * @desc 构建查询的filter
     */
    public List<Filter> builderFilter(Map parameters) {
        //参数
        String data = (String) parameters.get("id");
        List<Filter> filters = null;
        //获取要导出自然人的id
        String[] ids = data.split(",");
        String type = ids[0];
        String unitCode = UserProfileUtils.getUserProfile().getUnitCode();
        if(!"check".equals(type)){
            filters = new Filter.Builder().eq("delflag", "1").in("pid", ids).build();
        }else {
        String name = ids[1];
        String identifyType = ids[2];
        String uniscCode = ids[3];
        String recordType = ids[4];
        String domesticOrForeign = ids[5];
        String operator = ids[6];
        String transactionDep = ids[7];
        String startDate = ids[8];
        String endDate = ids[9];
         filters = new Filter.Builder().eq("delflag", "1").build();
        if (!PLACEHOLDER.equals(name)) {
            filters.add(new Filter("orgName", Operator.LIKE, name));
        }

        if (!PLACEHOLDER.equals(uniscCode)) {
            filters.add(new Filter("uniscCode", Operator.LIKE, uniscCode));
        }

        if (!PLACEHOLDER.equals(identifyType)) {
            filters.add(new Filter("identifyType", identifyType));
        }
        if (!"".equals(recordType.trim())) {
            filters.add(new Filter("recordType", recordType));
        }
        if (!"".equals(domesticOrForeign.trim())) {
            filters.add(new Filter("domesticOrForeign", domesticOrForeign));
        }
        if (!PLACEHOLDER.equals(operator)) {
            filters.add(new Filter("operator", operator));
        }
        if (!PLACEHOLDER.equals(transactionDep)) {
            filters.add(new Filter("transactionDep", transactionDep));
        }
        if (!PLACEHOLDER.equals(startDate) && !PLACEHOLDER.equals(endDate) ) {
            String[] list =  {startDate,endDate};
            filters.add(new Filter("modifiedTime",Operator.BETWEEN,list));
        }
        }
        return filters;
    }

    /**
     * @param dictCode
     * @return
     * @desc 获取字典信息
     */
    public Map<String, Map<String, String>> obtainDictInfo(String dictCode) {
        Map<String, Map<String, String>> dictCodeMap = new HashMap<>(16);
        //自然人证件类型
        List<DictItem> dictItems0 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_IDENTIFYTYPE.getCode()));
        Map relIdTypeMap = dictItems0.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("relIdTypeMap", relIdTypeMap);
        //关联方证件类型
        List<DictItem> dictItems1 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_ORGIDENTIFYTYPE.getCode()));
        Map idTypeMap = dictItems1.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("idTypeMap", idTypeMap);
        //境内外标识
        List<DictItem> dictItems2 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_DOMESTICORFOREIGN.getCode()));
        Map foreignMap = dictItems2.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("foreignMap", foreignMap);
        //关联关系类型
        List<DictItem> dictItems3 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_RELCATEGORY.getCode()));
        Map relCategoryMap = dictItems3.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("relCategoryMap", relCategoryMap);
        //关系
        List<DictItem> dictItems4 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_REL.getCode()));
        Map relMap = dictItems4.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("relMap", relMap);
        //机构状态
        List<DictItem> dictItems5 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_STATUS.getCode()));
        Map statusMap = dictItems5.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("statusMap", statusMap);
        //币种代码
        List<DictItem> dictItems6 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_CURRENCY.getCode()));
        Map currencyMap = dictItems6.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("currencyMap", currencyMap);
        //企业类型
        List<DictItem> dictItems7 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_COMPANYTYPE.getCode()));
        Map companyTypeMap = dictItems7.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("companyTypeMap", companyTypeMap);
        //关联关系判定
        List<DictItem> dictItems10 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_PREDICATE.getCode()));
        Map predicateMap = dictItems10.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("predicateMap", predicateMap);
        //关联方证件类型
        List<DictItem> dictItems11 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_ORGIDENTIFYTYPE.getCode()));
        Map orgIdTypeMap = dictItems11.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("orgIdTypeMap", orgIdTypeMap);
        //该关系是否有效
        List<DictItem> dictItems12 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_VALID.getCode()));
        Map vaildMap = dictItems12.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("vaildMap", vaildMap);
        //是否有权益份额
        List<DictItem> dictItems13 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.WHETHERTOSHARE.getCode()));
        Map shareMap = dictItems13.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("shareMap", shareMap);
        //产品分类
        List<DictItem> dictItems14 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_PRO.getCode()));
        Map proMap = dictItems14.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("proMap", proMap);

        List<DictItem> dictItems15 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.INS_OPERATIONTYPE.getCode()));
        Map operationtypeMap = dictItems15.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("operationtypeMap", operationtypeMap);
        return dictCodeMap;
    }

    /**
     * @param property1
     * @param value1
     * @return
     * @desc 构建查询的filter
     */
    public List<Filter> builderFilter(String property1, String value1) {
        List<Filter> filters = new Filter.Builder().eq(property1, value1).build();
        return filters;
    }

    @Override
    public Map executeExportCallback(HttpServletRequest request) throws ServiceException {
        return null;
    }

    @Override
    public void afterTransformXLS(Workbook workbook) {
    }

    private String isNo(String str) {
        if (null != str) {
            if (YES.equals(str)) {
                return str = "是";
            } else if (NO.equals(str)) {
                return str = "否";
            }
        }
        return str;
    }

    /**
     * 管户机构
     *
     * @param orgCode
     * @return
     */
    private String mgerOrg(String orgCode) {
        List<Filter> filters = new Filter.Builder().eq("kind", "UNIT").build();
        List<Unit> units = unitService.select(filters);
        List<NameValuePair> nameValuePairs = NVUtils.convert(units, units.size(), "unitCode", "unitName");

        for (NameValuePair nameVal : nameValuePairs) {
            if (null != orgCode) {

                if (orgCode.equals(nameVal.getValue())) {
                    orgCode = nameVal.getName();
                    return orgCode;
                }
            }
        }

        return orgCode;
    }
}
