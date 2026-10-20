package com.yzj.jep.app.bus.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.bus.vo.PsnInfoVo;
import com.yzj.jep.app.enums.DictionaryEnum;
import com.yzj.jep.app.util.ConfidentialInfoUtil;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.model.Unit;
import com.yzj.jep.sys.service.DictItemService;
import com.yzj.jep.sys.service.UnitService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.yzj.jep.app.util.ConfidentialInfoUtil.idEncrypt;

/**
 * @author llc
 */
@Service(value = PsnInfoExportCallbackImpl.BEANID)
public class PsnInfoExportCallbackImpl implements XlsExportCallback {

    public static final String BEANID = "PsnInfoExportCallbackImpl";

    private static final String EXPORT_TEMPLATE_NAME = "BusRelPsn1.xls";

    private static final String PREFIX = "自然人信息_";

    private static final String SUFFIX = ".xls";

    private static final String DICTCODE = "dictCode";

    private static final String YES = "1";

    private static final String NO = "0";

    private static final String PLACEHOLDER = "%";


    RelPsnService relPsnService;

    @Autowired
    DictItemService dictItemService;
    @Autowired
    private UnitService unitService;

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
        relPsnService = SpringContextHolder.getBean(RelPsnService.class);
        List<PsnInfoVo> psnInfoVos = new ArrayList<>();
        Map resultMap = new HashMap(16);
        //字典码
        String dictCode = DICTCODE;
        //获取字典码
        Map<String, Map<String, String>> dictCodeMap = obtainDictInfo(dictCode);
        //获取查询的filter
        List<Filter> filters = builderFilter(parameters);
        List<RelPsn> relPsns = relPsnService.select(filters);
        relPsns.stream().forEach( e -> {
                    if(e.getIdentifyType().equals("1")) {
                        e.setIdentifyNumber(idEncrypt(e.getIdentifyNumber()));
                    }
        });
        if (relPsns == null || relPsns.size() == 0) {
            resultMap.put("relPsn", psnInfoVos);
            return resultMap;
        }
        //构建自然人的基本信息
        for (RelPsn relPsn : relPsns) {
            //自然人的信息不完整则不导出自然人信息
            PsnInfoVo psnInfoVo = new PsnInfoVo();
            //自然人的基本信息
            builderPsnBasicInfo(psnInfoVo, relPsn, dictCodeMap);
            psnInfoVos.add(psnInfoVo);
        }
        resultMap.put("relPsn", psnInfoVos);
        return resultMap;
    }

    /**
     * @param psnInfoVo
     * @param relPsn
     * @param dictCodeMap
     * @desc 构建自然人的基本信息
     */
    public void builderPsnBasicInfo(PsnInfoVo psnInfoVo, RelPsn relPsn, Map<String, Map<String, String>> dictCodeMap) {
        psnInfoVo.setName(relPsn.getName());
        psnInfoVo.setIdentifyType(dictCodeMap.get("idTypeMap").get(relPsn.getIdentifyType()));
        psnInfoVo.setIdentifyNumber(relPsn.getIdentifyNumber());
        psnInfoVo.setDomesticOrForeign(dictCodeMap.get("foreignMap").get(relPsn.getDomesticOrForeign()));
        psnInfoVo.setSex(dictCodeMap.get("sexMap").get(relPsn.getSex()));
        psnInfoVo.setPoliticsStatus(dictCodeMap.get("politicallyMap").get(relPsn.getPoliticsStatus()));
        psnInfoVo.setBirthdate(relPsn.getBirthdate());
        psnInfoVo.setMobile(relPsn.getMobile());
        psnInfoVo.setMaritalStatus(dictCodeMap.get("maritalMap").get(relPsn.getMaritalStatus()));
        psnInfoVo.setEmail(relPsn.getEmail());
        psnInfoVo.setWorkUnits(relPsn.getWorkUnits());
        psnInfoVo.setUnitAddress(relPsn.getUnitAddress());
        psnInfoVo.setResidentialAddress(relPsn.getResidentialAddress());
//        psnInfoVo.setMgerOrg(generateMgerOrg().get(relPsn.getMgerOrg()));
//        psnInfoVo.setEducationBackground(dictCodeMap.get("educationMap").get(relPsn.getEducationBackground()));
        psnInfoVo.setIsShareholder(isNo(relPsn.getIsShareholder()));
//        psnInfoVo.setTradeAccount(relPsn.getTradeAccount());
//        psnInfoVo.setShareholdingProportion(relPsn.getShareholdingProportion());
//        psnInfoVo.setPledgeRatio(relPsn.getPledgeRatio());
        Date modifiedTime = relPsn.getModifiedTime();
        SimpleDateFormat sfDate = new SimpleDateFormat("yyyy-MM-dd");
        psnInfoVo.setModifiedTime(sfDate.format(modifiedTime));
        psnInfoVo.setRelateNumber(relPsn.getRelateNumber());//关联方编号
//        psnInfoVo.setModifiedTime(relPsn.getModifiedTimeString());

    }

    /**
     * @param parameters
     * @return
     * @desc 构建查询的filter
     */
    public List<Filter> builderFilter(Map parameters) {
        /**
         * var data = {
         *             "ids" : dataId,
         *             "name":name,
         *             "identifyNumber":identifyNumber,
         *             "transactionDep":transactionDep,
         *             "operator":operator,
         *             "createTime":createTime,
         *             "modifiedTime":modifiedTime,
         *             "status":status == '%'?'': status,
         *             "domesticOrForeign":domesticOrForeign,
         *             "effectRelation":effectRelation,
         *             "identifyType":identifyType
         *         }
         */
        //参数
        String data = (String) parameters.get("id");
        JSONObject jsonObject = JSONObject.parseObject(data);
        List<Filter> filters = new ArrayList<>();
        filters.add(new Filter("delflag",Operator.EQ,"1"));
        //获取要导出自然人的id
//        String ids = jsonObject.getString("ids");
//        if (StrUtil.isNotBlank(ids)) {
//            filters.add(new Filter("pid",Operator.IN,Arrays.asList(ids.split(","))));
//            return filters;
//        }
        String name = jsonObject.getString("name");
        String identifyNumber = jsonObject.getString("identifyNumber");
        String modifiedTime = jsonObject.getString("modifiedTime");
        String effectRelation = jsonObject.getString("effectRelation");
        String identifyType = jsonObject.getString("identifyType");

        if (StrUtil.isNotBlank(name)) {
            filters.add(new Filter("name", Operator.LIKE, name));
        }
        if(StrUtil.isNotBlank(identifyNumber)) {
            filters.add(new Filter("identifyNumber", Operator.EQ, identifyNumber));
        }
        if(StrUtil.isNotBlank(modifiedTime)) {
            filters.add(new Filter("modifiedTime", Operator.LIKE, modifiedTime));
        }
        if(StrUtil.isNotBlank(effectRelation)) {
            filters.add(new Filter("effectRelation", Operator.EQ, effectRelation));
        }
        if (StrUtil.isNotBlank(identifyType)) {
            filters.add(new Filter("identifyType", Operator.EQ, identifyType));
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
//        //性别
        List<DictItem> dictItems3 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_SEX.getCode(), "", ""));
        Map sexMap = dictItems3.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("sexMap", sexMap);
//        //民族
//        List<DictItem> dictItems4 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_RACE.getCode(), "", ""));
//        Map raceMap = dictItems4.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
//        dictCodeMap.put("raceMap", raceMap);
//        //政治面貌
        List<DictItem> dictItems5 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_POLITICALLY.getCode(), "", ""));
        Map politicallyMap = dictItems5.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("politicallyMap", politicallyMap);
//        //婚姻状况
        List<DictItem> dictItems6 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_MARITALSTATUS.getCode(), "", ""));
        Map maritalMap = dictItems6.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("maritalMap", maritalMap);
//        //学历
//        List<DictItem> dictItems7 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_EDUCATION.getCode(), "", ""));
//        Map educationMap = dictItems7.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
//        dictCodeMap.put("educationMap", educationMap);
//        //关联关系类型
//        List<DictItem> dictItems8 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_RELCATEGORY.getCode(), "", ""));
//        Map relCategoryMap = dictItems8.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
//        dictCodeMap.put("relCategoryMap", relCategoryMap);
//        //关系
//        List<DictItem> dictItems9 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_REL.getCode(), "", ""));
//        Map relMap = dictItems9.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
//        dictCodeMap.put("relMap", relMap);
//        //关联关系判定
//        List<DictItem> dictItems10 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_PREDICATE.getCode(), "", ""));
//        Map predicateMap = dictItems10.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
//        dictCodeMap.put("predicateMap", predicateMap);
//        //状态
//        List<DictItem> dictItems11 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_STATUS.getCode(), "", ""));
//        Map statusMap = dictItems11.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
//        dictCodeMap.put("statusMap", statusMap);
        //数据状态
        List<DictItem> dictItems11 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_APPLYSINGLESTATUS.getCode(), "", ""));
        Map statusMap = dictItems11.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("statusMap", statusMap);
        //是否
        List<DictItem> dictItems12 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_ISNO.getCode(), "", ""));
        Map isNoMap = dictItems12.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
        dictCodeMap.put("isNoMap", isNoMap);
//        //关联方证件类型
//        List<DictItem> dictItems12 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_ORGIDENTIFYTYPE.getCode(), "", ""));
//        Map orgIdTypeMap = dictItems12.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
//        dictCodeMap.put("orgIdTypeMap", orgIdTypeMap);
//        //该关系是否有效
//        List<DictItem> dictItems13 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_VALID.getCode(), "", ""));
//        Map vaildMap = dictItems13.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
//        dictCodeMap.put("vaildMap", vaildMap);
//        //是否有权益份额
//        List<DictItem> dictItems14 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.WHETHERTOSHARE.getCode(), "", ""));
//        Map shareMap = dictItems14.stream().collect(Collectors.toMap(DictItem::getItemValue, DictItem::getItemLabel));
//        dictCodeMap.put("shareMap", shareMap);

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
            List<Filter> filters = new Filter.Builder().eq(property1, value1).build();
            return filters;
        }
        List<Filter> filters = new Filter.Builder().eq(property1, value1).eq(property2, value2).build();
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

    public Map<String, String> generateMgerOrg(){
        List<Filter> filters = new Filter.Builder().eq("kind", "UNIT").build();
        List<Unit> units = unitService.select(filters);
        return units.stream().collect(Collectors.toMap(Unit::getUnitCode, Unit::getUnitName));
    }

}
