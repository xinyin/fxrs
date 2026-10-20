package com.yzj.jep.app.bus.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.yzj.jep.app.bus.model.RelPsnHistory;
import com.yzj.jep.app.bus.service.RelPsnHistoryService;
import com.yzj.jep.app.bus.vo.PsnInfoVo;
import com.yzj.jep.app.enums.DictionaryEnum;
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
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.yzj.jep.commons.StringUtils.isEmpty;

/**
 * @author llc
 */
@Service(value = PsnHistoryExportCallbackImpl.BEANID)
public class PsnHistoryExportCallbackImpl implements XlsExportCallback {

    public static final String BEANID = "PsnHistoryExportCallbackImpl";

    private static final String EXPORT_TEMPLATE_NAME = "RelPsnHistory.xls";

    private static final String PREFIX = "自然人信息记录_";

    private static final String SUFFIX = ".xls";

    private static final String DICTCODE = "dictCode";

    private static final String YES = "1";

    private static final String NO = "0";

    private static final String PLACEHOLDER = "%";


    RelPsnHistoryService relPsnHistoryService;

    @Resource
    DictItemService dictItemService;
    @Resource
    private UnitService unitService;

    @Override
    public String getExportTemplate() {
        return XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_NAME;
    }

    @Override
    public String getExportFilename(Map parameters) throws ServiceException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        return PREFIX + sf.format(new Date()) + SUFFIX;
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
        relPsnHistoryService = SpringContextHolder.getBean(RelPsnHistoryService.class);
        List<PsnInfoVo> psnInfoVos = new ArrayList<>();
        Map resultMap = new HashMap(16);

        String data = (String) parameters.get("id");
        String[] datas = data.split(",");
        String createTime = datas[1];
        String recordNumber = datas[2];
        String recordType = datas[3];
        List<RelPsnHistory> relPsns = new ArrayList<>();
        // 时点查询列表数据
        if(StrUtil.isNotBlank(createTime)){
            relPsns = relPsnHistoryService.queryRecordNumberData(createTime);
        }else {
            relPsns = relPsnHistoryService.queryRecordNumber(recordType, recordNumber);
        }
        //字典码
        //获取字典码
        Map<String, Map<String, String>> dictCodeMap = obtainDictInfo(DICTCODE);
        if (relPsns.isEmpty()) {
            resultMap.put("relPsnHistroy", psnInfoVos);
            return resultMap;
        }
        //构建自然人的基本信息
        for (RelPsnHistory relPsn : relPsns) {
            PsnInfoVo psnInfoVo = new PsnInfoVo();
            //自然人的基本信息
            builderPsnBasicInfo(psnInfoVo, relPsn, dictCodeMap);
            psnInfoVos.add(psnInfoVo);
        }
        resultMap.put("relPsnHistroy", psnInfoVos);
        return resultMap;
    }

    /**
     * @param psnInfoVo
     * @param relPsn
     * @param dictCodeMap
     * @desc 构建自然人的基本信息
     */
    public void builderPsnBasicInfo(PsnInfoVo psnInfoVo, RelPsnHistory relPsn, Map<String, Map<String, String>> dictCodeMap) {
        psnInfoVo.setName(relPsn.getName());
        psnInfoVo.setIdentifyType(dictCodeMap.get("idTypeMap").get(relPsn.getIdentifyType()));
        psnInfoVo.setIdentifyNumber(ConfidentialInfoUtil.hideAllIdCardNum(relPsn.getIdentifyNumber()));
        psnInfoVo.setDomesticOrForeign(dictCodeMap.get("foreignMap").get(relPsn.getDomesticOrForeign()));
        psnInfoVo.setMgerOrg(generateMgerOrg().get(relPsn.getMgerOrg()));
        psnInfoVo.setStatus(relPsn.getStatus());
        psnInfoVo.setWorkUnits(relPsn.getWorkUnits());
        psnInfoVo.setOperator(relPsn.getOperator());
        psnInfoVo.setTransactionDep(relPsn.getTransactionDep());
        psnInfoVo.setRecordType(dictCodeMap.get("operationtypeMap").get(relPsn.getRecordType()));

        psnInfoVo.setSex(dictCodeMap.get("sexMap").get(relPsn.getSex()));
        psnInfoVo.setPoliticsStatus(dictCodeMap.get("politicallyMap").get(relPsn.getPoliticsStatus()));
        psnInfoVo.setBirthdate(relPsn.getBirthdate());
        psnInfoVo.setMobile(relPsn.getMobile());
        psnInfoVo.setMaritalStatus(dictCodeMap.get("maritalMap").get(relPsn.getMaritalStatus()));
        psnInfoVo.setEmail(relPsn.getEmail());
        psnInfoVo.setUnitsAddress(relPsn.getUnitAddress());
        psnInfoVo.setPsnResidentialAddress(relPsn.getResidentialAddress());
        psnInfoVo.setRecordNumber(relPsn.getRecordNumber());
        psnInfoVo.setCreateTimeString(relPsn.getCreateTimeString());
    }

    /**
     * @param parameters
     * @return
     * @desc 构建查询的filter
     */
    public List<Filter> builderFilter(Map parameters) {
        //参数
        String data = (String) parameters.get("id");
        List<Filter> filters;
        //获取要导出自然人的id
        String[] ids = data.split(",");
        String type = ids[0];
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
            String pids = ids[9];
            filters = new Filter.Builder().eq("delflag", "1").build();
            if (!PLACEHOLDER.equals(name)) {
                filters.add(new Filter("name", Operator.LIKE, name));
            }

            if (!PLACEHOLDER.equals(uniscCode)) {
                filters.add(new Filter("identifyNumber", Operator.LIKE, uniscCode));
            }

            if (!PLACEHOLDER.equals(identifyType)) {
                filters.add(new Filter("identifyType",Operator.LIKE, identifyType));
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
        //关联方证件类型
        List<DictItem> dictItems1 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_IDENTIFYTYPE.getCode(), "", ""));
        Map idTypeMap = dictItems1.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("idTypeMap", idTypeMap);
        //境内外标识
        List<DictItem> dictItems2 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_DOMESTICORFOREIGN.getCode(), "", ""));
        Map foreignMap = dictItems2.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("foreignMap", foreignMap);
        //性别
        List<DictItem> dictItems3 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_SEX.getCode(), "", ""));
        Map sexMap = dictItems3.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("sexMap", sexMap);
        //政治面貌
        List<DictItem> dictItems5 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_POLITICALLY.getCode(), "", ""));
        Map politicallyMap = dictItems5.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("politicallyMap", politicallyMap);
        //婚姻状况
        List<DictItem> dictItems6 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_MARITALSTATUS.getCode(), "", ""));
        Map maritalMap = dictItems6.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("maritalMap", maritalMap);

        List<DictItem> dictItems7 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.INS_OPERATIONTYPE.getCode(), "", ""));
        Map operationtypeMap = dictItems7.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("operationtypeMap", operationtypeMap);

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

    public Map<String, String> generateMgerOrg(){
        List<Filter> filters = new Filter.Builder().eq("kind", "UNIT").build();
        List<Unit> units = unitService.select(filters);
        return units.stream().collect(Collectors.toMap(Unit::getUnitCode, Unit::getUnitName));
    }

}
