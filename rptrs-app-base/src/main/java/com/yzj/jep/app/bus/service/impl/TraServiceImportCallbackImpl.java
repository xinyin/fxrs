package com.yzj.jep.app.bus.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.yzj.jep.app.bus.vo.PsnInfoVo;
import com.yzj.jep.app.ins.constants.DicItemType;
import com.yzj.jep.app.ins.enums.YesOrNoEnum;
import com.yzj.jep.app.ins.model.TraService;
import com.yzj.jep.app.ins.model.TraServiceVo;
import com.yzj.jep.app.ins.service.BaseService;
import com.yzj.jep.app.ins.service.TraCounterpartyService;
import com.yzj.jep.app.ins.service.TraServiceService;
import com.yzj.jep.app.ins.service.impl.InsTradeBusinessImportCallbackImpl;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.sys.util.XssCheck;
import com.yzj.jep.app.util.BatchNumberUtil;
import com.yzj.jep.app.util.DicItemUtils;
import com.yzj.jep.app.util.InsUtil;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.core.utils.XlsMessage;
import com.yzj.jep.core.utils.xlsio.XlsImportCallback;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.yzj.jep.commons.StringUtils.trim;


/**
 * 内部交易管理导入实现
 *
 * @author lq
 * @time 2020年3月13日 下午3:47:25
 * @company 银之杰技术有限公司
 */
@Service(value = TraServiceImportCallbackImpl.BEANID)
public class TraServiceImportCallbackImpl implements XlsImportCallback {
    private static final Logger LOGGER = LoggerFactory.getLogger(TraServiceImportCallbackImpl.class);

    public static final String BEANID = "TraServiceImportCallbackImpl";

    //配置导入的模板（XML）文件
    private static final String XML_TEMP_FILE = "TraServiceImportMapper.xml";
    private static final String IMPORT_DATAS = "traServices";

    @Autowired
    TraServiceService traServiceService;
    @Autowired
    BaseService baseService;

    @Autowired
    DictItemService dictItemService;

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
        TraCounterpartyService traCounterpartyService = SpringContextHolder.getBean(TraCounterpartyService.class);
        XlsMessage res = null;
        int index = 0;
        String falNum = "";
        String resMsg = "";
        @SuppressWarnings("unchecked")
        List<TraServiceVo> list = (List<TraServiceVo>) beans.get(IMPORT_DATAS);
        //状态为审批中的数量
        int count1 = 0;
        //新增的数量
        int count2 = 0;
        //更新的数量
        int count3 = 0;
        //总数
        int sum = list.size() - 1;
        // 是否统一交易协议标识 TRUE- 否，FALSE -是
        boolean isAgre = true;
        /**
         * 一次性查询全部字典值
         */
        Map<String, List<DictItem>> dicMap = getDicValue();

        if (!list.isEmpty()) {

            for (int i = 0; i < list.size() - 1; i++) {
                TraServiceVo vo = list.get(i);
                TraService tra = new TraService();
                BeanUtils.copyProperties(vo,tra);
                if(StrUtil.isNotBlank(vo.getRelTradeAmount())){
                    tra.setRelTradeAmount(new BigDecimal(vo.getRelTradeAmount()));
                }
                String relType = DicItemUtils.getItemValue(dicMap, DicItemType.RELTYPECODE, tra.getRelType());
                String busType = DicItemUtils.getItemValue(dicMap, DicItemType.SERVICEBUSTYPE, tra.getBusType());
                String tranTarget = DicItemUtils.getItemValue(dicMap, DicItemType.SERVICETRATARGET, tra.getTradingDirection());
                String isconsiderSituation = DicItemUtils.getItemValue(dicMap, DicItemType.YESORNOCODE, tra.getIsconsiderSituation());
                String considerSituation = null;
                if (StringUtils.isNotBlank(tra.getConsiderSituation())) {
                    considerSituation = DicItemUtils.getItemValue(dicMap, DicItemType.CONSIDERSITUATIONCODE, tra.getConsiderSituation());
                }
                UserProfile userProfile = UserProfileUtils.getUserProfile();
                String isAgreement = DicItemUtils.getItemValue(dicMap, DicItemType.YESORNOCODE, tra.getIsAgreement());
                try {
                    boolean b = XssCheck.reflect(tra);
                    if (!b) {
                        throw new Exception("数据格式不规范, 请检查！");
                    }
                    Map<String, Object> relInfo = baseService.getRelInfo(tra.getRelNumber(), null);
                    if (!(boolean)relInfo.get("success")) {
                        falNum += i + 1 + ",";
                        index++;
                        resMsg += "第" + (i + 1) + "条此关联方不存在,";
                        continue;
                    }
                    Integer relId = Integer.parseInt(String.valueOf(relInfo.get("relId")));
                    tra.setRelId(relId);


                    tra.setRelType(relType);
                    tra.setBusType(busType);
                    tra.setConsiderSituation(considerSituation);
                    if(StringUtils.isNotBlank(tra.getTradeAgreementType())) {
                        String tradeAgreementType = DicItemUtils.getItemValue(dicMap, DicItemType.ISAGREEMENTIMPL, tra.getTradeAgreementType());
                        tra.setTradeAgreementType(tradeAgreementType);
                    }
                    tra.setIsAgreement(isAgreement);
                    tra.setTradingDirection(tranTarget);

                    if (StringUtils.isNotBlank(tra.getBusStartTimeString())) {
                        tra.setBusStartTime(DateUtils.parseDate(tra.getBusStartTimeString().trim()));
                    }
                    if (StringUtils.isNotBlank(tra.getBusEndTimeString())) {
                        tra.setBusEndTime(DateUtils.parseDate(tra.getBusEndTimeString().trim()));
                    }
                    if (StringUtils.isNotBlank(tra.getContractDateString())) {
                        tra.setContractDate(DateUtils.parseDate(tra.getContractDateString().trim()));
                    }

                    UserProfile profile = UserProfileUtils.getUserProfile();
                    tra.setTranType("2");
                    tra.setDataSources("1");
                    tra.setDelflag("1");
                    tra.setStatus("1");

                    //封装重大判断参数
                    Map<String, Object> ruleMap = new HashMap<>(5);
                    ruleMap.put("relType", relType);
                    ruleMap.put("relNumber", tra.getRelNumber());
                    ruleMap.put("isConsiderSituation", isconsiderSituation);
                    ruleMap.put("isAgreement", isAgreement);
                    ruleMap.put("relTranAmount", tra.getRelTradeAmount());

                    //判断重大
                    Map<String, String> ruleResultMap = InsUtil.judgeMajor(ruleMap);
                    if (null != ruleResultMap) {
                        tra.setMajorType(ruleResultMap.get("majorType"));
                        tra.setMajorTrade(ruleResultMap.get("majorTrade"));
                        tra.setSingleRelatedTranType(ruleResultMap.get("singleRelatedTrantype"));
                        tra.setConsiderType(ruleResultMap.get("considerType"));
                        tra.setEachReport(ruleResultMap.get("eachReport"));
                        tra.setEachDisclosure(ruleResultMap.get("eachDisclosure"));
                        tra.setIsCollectRelated(ruleResultMap.get("isCollectRelated"));
                    }
                    tra.setIsAccumulated(YesOrNoEnum.NO.getCode());
                    tra.setModifiedTime(new Date());
//                    List<Filter> filters = new Filter.Builder().eq("contractNo", tra.getContractNo()).eq("delflag", "1").build();
//                    List<TraService> traSerList = traServiceService.select(filters);

                    tra.setTranName(tra.getTranName().trim());
                    tra.setRelType(tra.getRelType().trim());
                    tra.setRelNumber(tra.getRelNumber().trim());
                    tra.setRelName(tra.getRelName().trim());
                    tra.setContractDateString(tra.getContractDateString().trim());
                    tra.setContractNo(tra.getContractNo().trim());
                    tra.setBusStartTimeString(tra.getBusStartTimeString().trim());
                    tra.setBusType(tra.getBusType().trim());
                  //  tra.setIsHoldingSubsidiary(tra.getIsHoldingSubsidiary().trim()); 是否控股子公司
                    tra.setIsAgreement(tra.getIsAgreement().trim());
                    tra.setTradePrice(tra.getTradePrice().trim());
                    tra.setPricingPolicy(tra.getPricingPolicy().trim());
                    tra.setTradeSubject(tra.getTradeSubject().trim());
                    tra.setIsconsiderSituation(tra.getIsconsiderSituation().trim());
                    tra.setRemark(trim(tra.getRemark()));
                    checkStrIsEmpty(tra);

                    tra.setTradeOrg(profile.getUnitCode());
                    tra.setCreateById(profile.getStaffCode());
                    tra.setCreateByName(profile.getStaffName());
                    tra.setAmendId(userProfile.getStaffCode());
                    tra.setAmendName(userProfile.getStaffName());
                    tra.setDeptName(userProfile.getDeptName());
                    //设置交易号
                    tra.setTransNum(BatchNumberUtil.makeBatchNumber());
                    traServiceService.insertSelective(tra);
                    count3++;

                } catch (Exception e) {
                    LOGGER.error("导入出错:{}",e);
                    index++;
                    falNum += i + 1 + ",";
                    resMsg += "第" + (i + 1) + "条数据导入失败,";
                }

            }
            res = new XlsMessage();
            String resultInfo = "导入【{}】条，成功【{}】条,新增【{}】条;";
            if (!"0".equals(falNum)) {
                resultInfo += "失败【{" + index + "}】条数据,失败原因：" + resMsg;
            }
            res.setMessage(StrUtil.format(resultInfo, sum, (sum - index), count3));
        } else {
            //导入失败时，添加提示信息
            res = new XlsMessage();
            res.setMessage("导入失败：");
            res.setException("导入失败，请检查导入数据。");
        }
        return res;
    }

    @Override
    public String checkBeforeImport(Map parameters, Map mapReaded) throws ServiceException {
        //如果通过检查，就保持result为null，否则给出返回错误描述
        String result = null;
        @SuppressWarnings("unchecked")
        List<TraServiceVo> list = (List<TraServiceVo>) mapReaded.get(IMPORT_DATAS);

        if (list != null && list.size() - 1 > 0) {
            Map<String, List<DictItem>> dicMap = getDicValue();

//            for (int i = 0; i < list.size() - 1; i++) {
//                TraServiceVo vo = list.get(i);
//                TraService tra = new TraService();
//                BeanUtils.copyProperties(vo,tra);
//                if(StrUtil.isNotBlank(vo.getRelTradeAmount())){
//                    tra.setRelTradeAmount(new BigDecimal(vo.getRelTradeAmount()));
//                }
////                TraService tra = list.get(i - 1);
//                //检查基本信息
//                String resBasic = checkBasicInfo(tra, dicMap);
//                if (!"".equals(resBasic)) {
//                    result = "检测到第" + (i + 1) + "条数据问题含有：" + resBasic;
//                    return result;
//                }
//            }
        } else {
            result = "导入数据为空。";
        }
        return result;
    }

    @Override
    public Map setReturnBeanFromXls(Map beans, InputStream inputXLS) throws ServiceException {
        //对导入的数据做特殊处理，如不处理则直接返回beans
        @SuppressWarnings("unchecked")
        List<PsnInfoVo> list = (List<PsnInfoVo>) beans.get(IMPORT_DATAS);
        return beans;
    }

    public String checkBasicInfo(TraService traService, Map<String, List<DictItem>> dicMap) {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isBlank(traService.getTranName())) {
            sb.append("交易概述不能为空；");
        }

        if (StringUtils.isBlank(traService.getRelType())) {
            sb.append("关联方类型不能为空；");
        } else {
            String relType = DicItemUtils.getItemValue(dicMap, DicItemType.RELTYPECODE, traService.getRelType());
            if (StringUtils.isBlank(relType)) {
                sb.append("关联方类型格式不正确；");
            }
        }

        if (StringUtils.isBlank(traService.getRelNumber())) {
            sb.append("关联方证件号码为空；");
        }
        if (StringUtils.isBlank(traService.getRelName())) {
            sb.append("关联方名称为空；");
        }
        if (StringUtils.isBlank(traService.getContractDateString())) {
            sb.append("合同签订日期不能为空；");
        }
        if (StringUtils.isBlank(traService.getContractNo())) {
            sb.append("合同编号为空");
        }
        if (ObjectUtil.isEmpty(traService.getRelTradeAmount())) {
            sb.append("关联交易金额不能为空；");
        }
        if (StringUtils.isBlank(traService.getBusStartTimeString())) {
            sb.append("业务起始日期不能为空；");
        }

        if (StringUtils.isBlank(traService.getBusType())) {
            sb.append("业务类型不能为空；");
        } else {
            String busType = DicItemUtils.getItemValue(dicMap, DicItemType.SERVICEBUSTYPE, traService.getBusType());
            if (StringUtils.isBlank(busType)) {
                sb.append("业务类型格式不正确；");
            }
        }


//        if (StringUtils.isBlank(traService.getIsHoldingSubsidiary())) {
//            sb.append("是否控股子公司发起的交易不能为空；");
//        } else {
//            String isHoldingSubsidiary = DicItemUtils.getItemValue(dicMap, DicItemType.YESORNOCODE, traService.getIsHoldingSubsidiary());
//
//            if (StringUtils.isBlank(isHoldingSubsidiary)) {
//                sb.append("是否控股子公司发起的交易格式不正确；");
//            }
//
//            if (YesOrNoEnum.YES.getCode().equals(isHoldingSubsidiary)) {
//                /**
//                 * 发起方是否为控股子公司为是时，发起方公司全称和发起方证件号码不能为空
//                 */
//                if (StringUtils.isBlank(traService.getHoldingSubsidiaryName())) {
//                    sb.append("发起方公司全称不能为空；");
//                }
//                if (StringUtils.isBlank(traService.getHoldingSubsidiaryNumber())) {
//                    sb.append("发起方证件号码不能为空；");
//                }
//            }
//        }

        if (StringUtils.isBlank(traService.getIsAgreement())) {
            sb.append("是否统一交易协议不能为空；");
        } else {
            String isAgreement = DicItemUtils.getItemValue(dicMap, DicItemType.YESORNOCODE, traService.getIsAgreement());
            if (YesOrNoEnum.YES.getCode().equals(isAgreement) && StringUtils.isBlank(traService.getIsAgreementImpl())) {
                sb.append("统一交易协议执行情况不能为空；");
            }
        }

        if (StringUtils.isBlank(traService.getTradePrice())) {
            sb.append("交易价格不能为空；");
        }
        if (StringUtils.isBlank(traService.getPricingPolicy())) {
            sb.append("定价政策不能为空；");
        }
        if (StringUtils.isBlank(traService.getTradeSubject())) {
            sb.append("交易标的不能为空；");
        }

        if (StringUtils.isBlank(traService.getIsconsiderSituation())) {
            sb.append("是否可免于审议不能为空；");
        } else {
            String isconsiderSituation = DicItemUtils.getItemValue(dicMap, DicItemType.YESORNOCODE, traService.getIsconsiderSituation());

            if (StringUtils.isBlank(isconsiderSituation)) {
                sb.append("是否可免于审议格式不正确；");
            }

            if (StringUtils.isBlank(traService.getTradingDirection())) {
                sb.append("交易方向不能为空；");
            }

            if (YesOrNoEnum.YES.getCode().equals(isconsiderSituation)) {
                if (StringUtils.isBlank(traService.getConsiderSituation())) {
                    sb.append("免于审议情形不能为空；");
                } else {
                    String considerSituation = DicItemUtils.getItemValue(dicMap, DicItemType.CONSIDERSITUATIONCODE, traService.getConsiderSituation());
                    if (StringUtils.isBlank(considerSituation)) {
                        sb.append("免于审议情形格式不正确；");
                    }
                }
            }
        }

        return sb.toString();
    }

    public Map<String, List<DictItem>> getDicValue() {

        Map<String, List<DictItem>> dicItemMap = new HashMap();
        List<DictItem> dictItemList = dictItemService.selectAll();
        if (CollectionUtil.isNotEmpty(dictItemList)) {
            dicItemMap = dictItemList.stream().collect(Collectors.groupingBy(DictItem::getDictCode));
        }

        return dicItemMap;
    }

    private String trimStr(Object obj) {
        if (obj != null) {
            return obj.toString().trim();
        }
        return null;
    }

    /**
     * 检验必填项
     *
     * @param traService 数据源
     */
    public void checkStrIsEmpty(TraService traService) {
        if (StringUtils.isNotBlank(traService.getBusType()) && null != traService.getContractNo()
                && null != traService.getRelTradeAmount() && StringUtils.isNotBlank(traService.getTradePrice()) && StringUtils.isNotBlank(traService.getPricingPolicy())
                && StringUtils.isNotBlank(traService.getTranName()) && StringUtils.isNotBlank(traService.getTradeSubject())
                && StringUtils.isNotBlank(traService.getIsconsiderSituation())) {
            traService.setStatus("3");

            /* 免于审议时，状态为已归档 */
            if ("1".equals(traService.getConsiderType())) {
                // 实际交易金额不为空 + 合同签署日期不为空 = 已归档
                if (StringUtils.isNotBlank(traService.getPracTradeAmount()) && ObjectUtil.isNotEmpty(traService.getContractDate())) {
                    traService.setStatus("6");
                }
                // 实际交易金额不为空 + 合同签署日期为空 = 待签订
                // 实际交易金额为空 + 合同签署日期为空 = 待签订
                if (ObjectUtil.isEmpty(traService.getContractDate()) || ObjectUtil.isEmpty(traService.getBusStartTime())) {
                    traService.setStatus("8");
                }
                // 实际交易金额为空 + 合同签署日期不为空 = 履行中
                if (StringUtils.isBlank(traService.getPracTradeAmount()) && ObjectUtil.isNotEmpty(traService.getContractDate())) {
                    traService.setStatus("7");
                }
                /* 是统一交易协议时，统一交易审议类型不能为空 */
                if ("1".equals(traService.getIsAgreement())) {
                    if (StringUtils.isBlank(traService.getIsAgreementImpl())) {
                        traService.setStatus("1");
                    }
                }
            }
        }
    }
}
