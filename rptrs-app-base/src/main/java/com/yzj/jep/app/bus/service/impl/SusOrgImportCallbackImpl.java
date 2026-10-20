package com.yzj.jep.app.bus.service.impl;


import com.yzj.jep.app.bus.model.RelOrgSuspect;
import com.yzj.jep.app.bus.model.RelOrgSuspectVo;
import com.yzj.jep.app.bus.service.RelOrgSuspectService;
import com.yzj.jep.app.enums.DictionaryEnum;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.utils.XlsMessage;
import com.yzj.jep.core.utils.xlsio.XlsImportCallback;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 自然人模板导出实现
 *
 * @author lq
 * @time 2020年3月13日 下午3:47:25
 * @company 银之杰技术有限公司
 */
@Service(value = SusOrgImportCallbackImpl.BEANID)
public class SusOrgImportCallbackImpl implements XlsImportCallback {
    @Autowired
    private RelOrgSuspectService relOrgSuspectService;

    @Autowired
    DictItemService dictItemService;

    public static final String BEANID = "SusOrgImportCallbackImpl";


    /**
     * 配置导入的模板（XML）文件
     */
    private static final String XML_TEMP_FILE = "SusOrgImportMapper.xml";

    private static final String IMPORT_DATAS = "SusOrgPsns";

    private static final String YES = "是";

    private static final String NO = "否";

    private static final String VALID = "有效";

    private static final String INVALID = "无效";

    @Override
    public String getImportXmlTemplate() {
        return XLSTEMPLATE_PATH + XML_TEMP_FILE;
    }

    @Override
    public void setXlsReaderBeans(Map beans) {
        //一般而言，有几个sheet页，就有几个元素
        beans.put(IMPORT_DATAS, new ArrayList());
    }

    @Override
    public XlsMessage executeSaveCallback(Map parameters, Map beans) throws ServiceException {
        XlsMessage res = null;
        Date date = new Date();
        int num = 0;
        String dictCode = "dictCode";
        String uniscCode = "uniscCode";
        String identifyType = "identifyType";
        //非0001的机构码都为委托机构
        String mainOrg = "0001";
        Integer index = 0;
        Integer numUp = 0;
        Integer numIn = 0;
        Integer rens = 0;
        String msg = null;
        List<Integer> listres = new ArrayList<>();

        @SuppressWarnings("unchecked")
        List<RelOrgSuspectVo> list = (List<RelOrgSuspectVo>) beans.get(IMPORT_DATAS);
        if (list != null && list.size() > 0) {
            //根据部门存储数据权限
            UserProfile profile = UserProfileUtils.getUserProfile();
            String unitCode = profile.getUnitCode();
            //获取字典信息
            Map<String, Map<String, String>> dictCodeMap = obtainDictInfo(dictCode);
            int size = list.size();
            for (int i = 0; i < list.size(); i++) {
                //机构名称为空
                if (StringUtils.isEmpty(list.get(i).getOrgName())) {
                    rens = i + 1;
                    listres.add(rens);
                    continue;
                }
                //机构证件类型为空
                if (StringUtils.isEmpty(list.get(i).getIdentifyType())) {
                    rens = i + 1;
                    listres.add(rens);
                    continue;
                }
                //机构证件号码为空
                if (StringUtils.isEmpty(list.get(i).getUniscCode())) {
                    rens = i + 1;
                    listres.add(rens);
                    continue;
                }
                //机构境内外标识为空
                if (StringUtils.isEmpty(list.get(i).getDomesticOrForeign())) {
                    rens = i + 1;
                    listres.add(rens);
                    continue;
                }
                //是否为本公司主体控股公司为空
                if (StringUtils.isEmpty(list.get(i).getIsSubsidiary())) {
                    rens = i + 1;
                    listres.add(rens);
                    continue;
                }
                if (list.get(i).getUniscCode() == null) {
                    rens = i + 1;
                    listres.add(rens);
                    continue;
                } else {
                    String unCode = list.get(i).getUniscCode();
                    String idType = list.get(i).getIdentifyType();
                    if (idType.equals("统一社会信用代码") || idType.equals("工商注册号")) {
                        if (!(8 <= unCode.length() && unCode.length() <= 18)) {
                            rens = i + 1;
                            listres.add(rens);
                            continue;
                        }
                    } else {
                        if (!(5 <= unCode.length() && unCode.length() <= 18)) {
                            rens = i + 1;
                            listres.add(rens);
                            continue;
                        }
                    }
                }
                //检查基本信息是否存在
                boolean checkOrgExist = false;
                //查询机构信息
                List<RelOrgSuspect> relOrgList = relOrgSuspectService.select(builderFilterTwo(uniscCode, list.get(i).getUniscCode(), identifyType,
                        dictCodeMap.get("idTypeMap").get(list.get(i).getIdentifyType())));
                if (relOrgList.size() != 0) {
                    checkOrgExist = true;
                }
                num++;
                //构建机构的基本信息
                RelOrgSuspect RelOrgSuspect = builderOrgBasicInfo(list.get(i), dictCodeMap, mainOrg, profile);
                //如果不是新的基础信息，则无需进行更新或插入
                if (checkOrgExist) {
                    RelOrgSuspect.setPid(relOrgList.get(0).getPid());
                    //如果状态不是已归档则用数据库中的状态
                    if (!"6".equals(RelOrgSuspect.getApprovalStatus())) {
                        RelOrgSuspect.setApprovalStatus(relOrgList.get(0).getApprovalStatus());
                    }
                    relOrgSuspectService.updateSelective(RelOrgSuspect);
                    numUp++;
                } else {
//                    RelOrgSuspect.setEffectRelation("0");
                    relOrgSuspectService.insertSelective(RelOrgSuspect);
                    numIn++;
                }
            }
            String replace = listres.toString().replace("[", "").replace("]", "");
            if (numUp == 0 && (size - num) == 0) {
                msg = "成功导入" + numIn + "条信息";
            } else if (numUp == 0) {
                msg = "成功导入" + numIn + "条信息，第" + replace + "条信息有误。";
            } else if ((size - num) == 0) {
                msg = "成功导入" + numIn + "条信息，" + "更新" + numUp + "条信息";
            } else {
                msg = "更新" + numUp + "条信息" + "，第" + replace + "条信息有误。";
            }
            res = new XlsMessage();
            res.setMessage(msg);
        } else {
            res = new XlsMessage();
            res.setMessage("导入失败：");
            res.setException("导入失败，请检查导入数据。");
            return res;
        }

        return res;
    }

    /**
     * @param entity
     * @param dictCodeMap
     * @return
     * @desc 构建机构的基本信息
     */
    public RelOrgSuspect builderOrgBasicInfo(RelOrgSuspectVo entity, Map<String, Map<String, String>> dictCodeMap,
                                             String mgerOrg, UserProfile profile) {
        RelOrgSuspect RelOrgSuspect = new RelOrgSuspect();
        //机构名称
        RelOrgSuspect.setOrgName(entity.getOrgName());
        //证件号码类型
        RelOrgSuspect.setIdentifyType(dictCodeMap.get("idTypeMap").get(entity.getIdentifyType()));
        //证件号码
        //BigDecimal codeNum = new BigDecimal(entity.getUniscCode());
        RelOrgSuspect.setUniscCode(entity.getUniscCode());
        //境内外标识
        RelOrgSuspect.setDomesticOrForeign(dictCodeMap.get("foreignMap").get(entity.getDomesticOrForeign()));
        //机构类型
        RelOrgSuspect.setOrgType(dictCodeMap.get("companyTypeMap").get(entity.getOrgType()));
        //机构状态
        RelOrgSuspect.setStatus(dictCodeMap.get("statusMap").get(entity.getStatus()));
        //机构性质
        RelOrgSuspect.setOrgNature(entity.getOrgNature());
        //资本数量
        String regCapital = entity.getRegCapital() == null ? "0" : String.valueOf(entity.getRegCapital());
        RelOrgSuspect.setRegCapital(Float.parseFloat(regCapital));
        //注册资本人民币
        RelOrgSuspect.setRegCapitalCurrency(dictCodeMap.get("currencyMap").get(entity.getRegCapitalCurrency()));
        //注册地址
        RelOrgSuspect.setRegAddress(entity.getRegAddress());
        //业务范围
        RelOrgSuspect.setBusinessScope(entity.getBusinessScope());
        //法人
        RelOrgSuspect.setLegalPerson(entity.getLegalPerson());
        //法人证件类型
        RelOrgSuspect.setLegalIdentifyType(dictCodeMap.get("relIdTypeMap").get(entity.getLegalIdentifyType()));
        //证件号码
        //BigDecimal identifyNum = new BigDecimal(entity.getLegalIdentifyNumber());
        RelOrgSuspect.setLegalIdentifyNumber(entity.getLegalIdentifyNumber());
        //集团号码
        RelOrgSuspect.setMemGroup(entity.getMemGroup());
        //集团名称
        RelOrgSuspect.setGroupName(entity.getGroupName());
        //是否为股东
        //RelOrgSuspect.setIsShareholder(isNo(entity.getIsShareholder()));
        //主要股东
       // RelOrgSuspect.setIsMainGd(isNo(entity.getIsMainGd()));
        //资本数量
        //RelOrgSuspect.setCapitalNumber(entity.getCapitalNumber());
        //股东代码
        //RelOrgSuspect.setShareholdersCode(entity.getShareholdersCode());
        //开户行
        //RelOrgSuspect.setOpenBank(entity.getOpenBank());
        //账户
       // RelOrgSuspect.setTradeAccount(entity.getTradeAccount());
        //RelOrgSuspect.setInTime(getStringDateByCell(entity.getInTime()));
        //RelOrgSuspect.setInPrice(entity.getInPrice());
        //份额
        //RelOrgSuspect.setHoleShareAmount(entity.getHoleShareAmount());
        //股权比例
        //RelOrgSuspect.setShareholdingProportion(entity.getShareholdingProportion());

        //RelOrgSuspect.setFrozenShare(entity.getFrozenShare());
        //RelOrgSuspect.setPledgedShare(entity.getPledgedShare());
        ////是否具有非持股表决权
        //RelOrgSuspect.setIsNoShareVote(isNo(entity.getIsNoShareVote()));
        //投票比例
        //RelOrgSuspect.setVoteProportion(entity.getVoteProportion());
        //限制投票情况
       // RelOrgSuspect.setLimitVoteSituation(entity.getLimitVoteSituation());
        //是否控股子公司
        RelOrgSuspect.setIsSubsidiary(dictCodeMap.get("isSubsidiaryMap").get(entity.getIsSubsidiary()));
        //导入时默认都没加入黑名单
        RelOrgSuspect.setIsBlack("0");
        RelOrgSuspect.setDelflag("1");
        RelOrgSuspect.setIsAssoParty("0");
        RelOrgSuspect.setMgerOrg(profile.getUnitCode());
        //数据源默认设置下手工录入
        RelOrgSuspect.setDataSource("2");
        RelOrgSuspect.setModifiedTime(new Date());
        RelOrgSuspect.setApprovalStatus("3");
//        RelOrgSuspect.setAmendId(profile.getStaffCode());
//        RelOrgSuspect.setAmendName(profile.getStaffName());
        RelOrgSuspect.setCreateById(profile.getStaffCode());
        RelOrgSuspect.setCreateByName(profile.getStaffName());
//        RelOrgSuspect.setTransactionDep(profile.getDeptName());
//        RelOrgSuspect.setOperator(profile.getStaffName());
        return RelOrgSuspect;
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
        Map relIdTypeMap = dictItems0.stream().collect(Collectors.toMap(DictItem::getItemLabel, DictItem::getItemValue));
        dictCodeMap.put("relIdTypeMap", relIdTypeMap);
        //关联方证件类型
        List<DictItem> dictItems1 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_ORGIDENTIFYTYPE.getCode()));
        Map idTypeMap = dictItems1.stream().collect(Collectors.toMap(DictItem::getItemLabel, DictItem::getItemValue));
        dictCodeMap.put("idTypeMap", idTypeMap);
        //境内外标识
        List<DictItem> dictItems2 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_DOMESTICORFOREIGN.getCode()));
        Map foreignMap = dictItems2.stream().collect(Collectors.toMap(DictItem::getItemLabel, DictItem::getItemValue));
        dictCodeMap.put("foreignMap", foreignMap);
        //关联关系类型
        List<DictItem> dictItems3 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_RELCATEGORY.getCode()));
        Map relCategoryMap = dictItems3.stream().collect(Collectors.toMap(DictItem::getItemLabel, DictItem::getItemValue));
        dictCodeMap.put("relCategoryMap", relCategoryMap);
        //关系
        List<DictItem> dictItems4 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_REL.getCode()));
        Map relMap = dictItems4.stream().collect(Collectors.toMap(DictItem::getItemLabel, DictItem::getItemValue));
        dictCodeMap.put("relMap", relMap);
        //机构状态
        List<DictItem> dictItems5 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_STATUS.getCode()));
        Map statusMap = dictItems5.stream().collect(Collectors.toMap(DictItem::getItemLabel, DictItem::getItemValue));
        dictCodeMap.put("statusMap", statusMap);
        //币种代码
        List<DictItem> dictItems6 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_CURRENCY.getCode()));
        Map currencyMap = dictItems6.stream().collect(Collectors.toMap(DictItem::getItemLabel, DictItem::getItemValue));
        dictCodeMap.put("currencyMap", currencyMap);
        //企业类型
        List<DictItem> dictItems7 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_COMPANYTYPE.getCode()));
        Map companyTypeMap = dictItems7.stream().collect(Collectors.toMap(DictItem::getItemLabel, DictItem::getItemValue));
        dictCodeMap.put("companyTypeMap", companyTypeMap);
        //关联关系判定
        List<DictItem> dictItems10 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_PREDICATE.getCode()));
        Map predicateMap = dictItems10.stream().collect(Collectors.toMap(DictItem::getItemLabel, DictItem::getItemValue));
        dictCodeMap.put("predicateMap", predicateMap);
        //是否控股子公司
        List<DictItem> dictItems11 = dictItemService.select(builderFilter(dictCode, DictionaryEnum.BUS_ISSUBSIDIARY.getCode()));
        Map isSubsidiaryMap = dictItems11.stream().collect(Collectors.toMap(DictItem::getItemLabel, DictItem::getItemValue));
        dictCodeMap.put("isSubsidiaryMap", isSubsidiaryMap);
        return dictCodeMap;
    }

    /**
     * @param property1
     * @param value1
     * @return
     * @desc 构建查询的filter
     */
    public List<Filter> builderFilterTwo(String property1, String value1, String property2, String value2) {
        List<Filter> filters = new Filter.Builder().eq(property1, value1).eq(property2, value2).build();
        return filters;
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
    public String checkBeforeImport(Map parameters, Map mapReaded) throws ServiceException {
        //如果通过检查，就保持result为null，否则给出返回错误描述
        StringBuilder result = new StringBuilder();
        @SuppressWarnings("unchecked")
        //获取的数据在这里封装
        List<RelOrgSuspectVo> list = (List<RelOrgSuspectVo>) mapReaded.get(IMPORT_DATAS);
        //确认获取的数据是否为空
        if (list != null && list.size() > 0) {
            //TODO 执行导入前的检查，如xxx编码是否存在，xxx引用的编码不存在等
            for (RelOrgSuspectVo relOrgSuspectVo : list) {
                if (relOrgSuspectVo.getNo() == null) {
                    break;
                }
                //必填字段校验
                checkEmpty(relOrgSuspectVo, result);
            }

        } else {
            result.append("导入文件模板不匹配或者数据为空;");
        }
        return result.toString();
    }

    /**
     * @param orgInfoVo
     * @return
     * @desc 字段的非空校验
     */
    public String checkEmpty(RelOrgSuspectVo orgInfoVo, StringBuilder result) {
        if (StringUtils.isEmpty(orgInfoVo.getOrgName())) {
            result.append("机构名称为空").append(";");
        }

        if (StringUtils.isEmpty(orgInfoVo.getIdentifyType())) {
            result.append("机构证件类型为空").append(";");
        }

        if (StringUtils.isEmpty(orgInfoVo.getUniscCode())) {
            result.append("机构证件号码为空").append(";");
        }

        if (StringUtils.isEmpty(orgInfoVo.getDomesticOrForeign())) {
            result.append("机构境内外标识为空").append(";");
        }

        if (StringUtils.isEmpty(orgInfoVo.getIsSubsidiary())) {
            result.append("是否为本公司主体控股公司为空").append(";");
        }
        return result.toString();
    }

    @Override
    public Map setReturnBeanFromXls(Map beans, InputStream inputXls) throws ServiceException {
        //对导入的数据做特殊处理，如不处理则直接返回beans
        @SuppressWarnings("unchecked")

        List<RelOrgSuspectVo> list = (List<RelOrgSuspectVo>) beans.get(IMPORT_DATAS);

        if (CollectionUtils.isNotEmpty(list)) {
            for (RelOrgSuspectVo entity : list) {
                if (!StringUtils.isEmpty(entity.getLegalIdentifyNumber())) {
                    //TODO 演示对从excel读取后的数据在做导入处理前做业务处理
                    RelOrgSuspectVo RelOrgSuspectVo = new RelOrgSuspectVo();
                    RelOrgSuspectVo.setOrgName(entity.getOrgName());
                    RelOrgSuspectVo.setIdentifyType(entity.getIdentifyType());
                    System.out.println("演示，在这里对：" + entity.getOrgName() + "做进一步处理。");
                }
            }
        }

        return beans;
    }


    private String getStringDateByCell(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (date != null && !"".equals(date)) {
            Date javaDate = HSSFDateUtil.getJavaDate(Double.parseDouble(date));
            return sdf.format(javaDate);
        }
        return null;
    }

    /**
     * 将文字判定转换为数字字典
     *
     * @param str
     * @return
     */
    private String isNo(String str) {
        if (!StringUtils.isEmpty(str)) {
            if (str.contains(YES)) {
                return "1";
            }

            if (NO.equals(str)) {
                return "0";
            }

            if (VALID.equals(str)) {
                return "1";
            }

            if (INVALID.equals(str)) {
                return "0";
            }
        }
        return "";
    }
}
