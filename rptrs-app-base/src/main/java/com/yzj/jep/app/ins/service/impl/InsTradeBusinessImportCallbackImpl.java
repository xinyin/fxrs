package com.yzj.jep.app.ins.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.model.RootOrgInfo;
import com.yzj.jep.app.bus.service.RootOrgInfoService;
import com.yzj.jep.app.bus.vo.PsnInfoVo;
import com.yzj.jep.app.ins.constants.DicItemType;
import com.yzj.jep.app.ins.enums.YesOrNoEnum;
import com.yzj.jep.app.ins.model.TraBusiness;
import com.yzj.jep.app.ins.model.TraBusinessVo;
import com.yzj.jep.app.ins.model.TraCounterparty;
import com.yzj.jep.app.ins.model.TraRelInfo;
import com.yzj.jep.app.ins.service.BaseService;
import com.yzj.jep.app.ins.service.TraBusinessService;
import com.yzj.jep.app.ins.service.TraCounterpartyService;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.sys.util.XssCheck;
import com.yzj.jep.app.util.BatchNumberUtil;
import com.yzj.jep.app.util.DicItemUtils;
import com.yzj.jep.app.util.Dict;
import com.yzj.jep.app.util.InsUtil;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.core.utils.XlsMessage;
import com.yzj.jep.core.utils.xlsio.XlsImportCallback;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.OrderType;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import com.yzj.jep.web.util.RequestUtils;
import org.apache.commons.collections.CollectionUtils;
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
@Service(value = InsTradeBusinessImportCallbackImpl.BEANID)
public class InsTradeBusinessImportCallbackImpl implements XlsImportCallback {
    private static final Logger LOGGER = LoggerFactory.getLogger(InsTradeBusinessImportCallbackImpl.class);

    @Autowired
    DictItemService dictItemService;

    @Autowired
    BaseService baseService;

    @Autowired
    RootOrgInfoService rootOrgInfoService;

    public static final String BEANID = "InsTradeBusinessImportCallbackImpl";

    /**
     * 配置导入的模板（XML）文件
     */
    private static final String XML_TEMP_FILE = "InsTradeBusinessImportMapper.xml";
    private static final String IMPORT_DATAS = "insTradeBusiness";

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
        int index = 0;
        List<TraBusinessVo> list = (List<TraBusinessVo>) beans.get(IMPORT_DATAS);
        Integer falNum = 0;
        StringBuilder resMsg = new StringBuilder();
        //状态为审批中的数量
        int count1 = 0;
        //新增的数量
        int count2 = 0;
        //更新的数量
        int count3 = 0;
        //总数
        int sum = list.size();
        if(list.size() != 0){
            sum = list.size() - 1;
        }
        // 是否统一交易协议标识 TRUE- 否，FALSE -是
        boolean isAgre = true;
        /**
         * 一次性查询全部字典值
         */
        Map<String, List<DictItem>> dicMap = getDicValue();

        if (CollectionUtils.isNotEmpty(list)) {
            //需要调用合适的事务方法保存导入的数据
            TraBusinessService traBusinessService = SpringContextHolder.getBean(TraBusinessService.class);
            UserProfile userProfile = UserProfileUtils.getUserProfile();
            String unitCode = userProfile.getUnitCode();
            List<Filter> filterList = new ArrayList<>();
            filterList.add(new Filter("delflag", "1"));
            int year = DateUtil.year(new Date());
            filterList.add(new Filter("years", String.valueOf(year - 1)));
            filterList.add(new Filter("quarter", "4"));
            filterList.add(new Filter("approvalStatus", "6"));
            List<Order> orders = new ArrayList<>();
            orders.add(new Order("modifiedTime", OrderType.DESC));
            List<RootOrgInfo> rootOrgInfos = rootOrgInfoService.select(filterList);
            String lastYearNetAsset = "0";
            if (CollectionUtil.isNotEmpty(rootOrgInfos)) {
                lastYearNetAsset = String.valueOf(rootOrgInfos.get(0).getNetCaptial());
                //上年度末总资产
            }

            for (int i = 0; i < list.size() - 1; i++) {
                TraBusinessVo traBusiness = list.get(i);
                try {
                    boolean b = XssCheck.reflect(traBusiness);
                    if(!b){
                        throw new Exception("数据格式不规范, 请检查！");
                    }
                    String relType = DicItemUtils.getItemValue(dicMap, DicItemType.RELTYPECODE,traBusiness.getRelType());
                    String busType = DicItemUtils.getItemValue(dicMap, DicItemType.BUSINESSBUSTYPE,traBusiness.getBusType());
                    String isconsiderSituation = DicItemUtils.getItemValue(dicMap,DicItemType.YESORNOCODE,traBusiness.getIsconsiderSituation());
                    String considerSituation = null;
                    if(StringUtils.isNotBlank(traBusiness.getConsiderSituation())){
                        considerSituation = DicItemUtils.getItemValue(dicMap,DicItemType.CONSIDERSITUATIONCODE,traBusiness.getConsiderSituation());
                    }
                    String isAgreement = DicItemUtils.getItemValue(dicMap,DicItemType.YESORNOCODE,traBusiness.getIsAgreement());

                    Map<String, Object> relInfo = baseService.getRelInfo(traBusiness.getRelNumber(), null);
                    if (!(boolean)relInfo.get("success")) {
                        falNum += i + 1;
                        resMsg.append("第").append(i + 1).append("条此关联方不存在,");
                        index++;
                        continue;
                    }
                    TraBusiness business = new TraBusiness();
                    BeanUtils.copyProperties(traBusiness, business);
                    if(StringUtils.isNotBlank(traBusiness.getTradeAgreementType())) {
                        String tradeAgreementType = DicItemUtils.getItemValue(dicMap, DicItemType.ISAGREEMENTIMPL, traBusiness.getTradeAgreementType());
                        business.setTradeAgreementType(tradeAgreementType);
                    }
                    business.setRelType(relType);
                    business.setBusType(busType);
                    business.setIsconsiderSituation(isconsiderSituation);
                    business.setConsiderSituation(considerSituation);
                    business.setIsAgreement(isAgreement);

                    if (StringUtils.isNotBlank(traBusiness.getBusStartTimeString())) {
                        business.setBusStartTime(DateUtils.parseDate(traBusiness.getBusStartTimeString().trim()));
                    }
                    if (StringUtils.isNotBlank(traBusiness.getBusEndTimeString())) {
                        business.setBusEndTime(DateUtils.parseDate(traBusiness.getBusEndTimeString().trim()));
                    }
                    if (StringUtils.isNotBlank(traBusiness.getContractDateString())) {
                        business.setContractDate(DateUtils.parseDate(traBusiness.getContractDateString().trim()));
                    }
                    if (StringUtils.isNotBlank(traBusiness.getRelTradeAmount())) {
                        business.setRelTradeAmount(new BigDecimal(traBusiness.getRelTradeAmount()));
                    }

                    Integer relId = Integer.valueOf(trimStr(Integer.parseInt(String.valueOf(relInfo.get("relId"))))) ;
                    business.setRelId(relId);
                    business.setRelType(relType);
                    business.setTradeOrg(unitCode);
                    business.setDataSources("1");
                    business.setDelflag("1");
                    business.setStatus("1");
                    business.setTranType("4");
                    business.setIsAccumulated(YesOrNoEnum.NO.getCode());
                    business.setModifiedTime(new Date());
                    //封装重大判断参数
                    Map<String, Object> ruleMap = new HashMap<>(5);
                    ruleMap.put("relType", relType);
                    ruleMap.put("relNumber", business.getRelNumber());
                    ruleMap.put("isConsiderSituation", isconsiderSituation);
                    ruleMap.put("isAgreement", isAgreement);
                    ruleMap.put("relTranAmount", business.getRelTradeAmount());

                    //判断重大
                    Map<String, String> ruleResultMap = InsUtil.judgeMajor(ruleMap);
                    if (null != ruleResultMap) {
                        business.setMajorType(ruleResultMap.get("majorType"));
                        business.setMajorTrade(ruleResultMap.get("majorTrade"));
                        business.setSingleRelatedTranType(ruleResultMap.get("singleRelatedTrantype"));
                        business.setConsiderType(ruleResultMap.get("considerType"));
                        business.setEachReport(ruleResultMap.get("eachReport"));
                        business.setEachDisclosure(ruleResultMap.get("eachDisclosure"));
                        business.setIsCollectRelated(ruleResultMap.get("isCollectRelated"));
                    }

                    /* 若是统一交易协议，直接新增不需要更新操作 */
//                    if("1".equals(isAgreement)){
//                        isAgre = false;
//                    }
//                    List<Filter> filters = new Filter.Builder().eq("contractNo", business.getContractNo()).eq("delflag","1").build();
//                    List<TraBusiness> tarBLists = traBusinessService.select(filters);

                    business.setTranName(trim(business.getTranName()));
                    business.setRelType(trim(business.getRelType()));
                    business.setRelNumber(trim(business.getRelNumber()));
                    business.setRelName(trim(business.getRelName()));
                    business.setContractDateString(trim(business.getContractDateString()));
                    business.setContractNo(trim(business.getContractNo()));
                    business.setBusStartTimeString(trim(business.getBusStartTimeString()));
                    business.setBusType(trim(business.getBusType()));
                    business.setIsAgreement(trim(business.getIsAgreement()));
                    business.setTradePrice(trim(business.getTradePrice()));
                    business.setPricingPolicy(trim(business.getPricingPolicy()));
                    business.setTradeSubject(trim(business.getTradeSubject()));
                    business.setIsconsiderSituation(trim(business.getIsconsiderSituation()));
                    business.setRemark(trim(business.getRemark()));
                    checkStrIsEmpty(business);
//                    if (!tarBLists.isEmpty() && isAgre) {
//                        if("4".equals(tarBLists.get(0).getStatus())){
//                            count1 ++;
//                            continue;
//                        }
//                        business.setPid(tarBLists.get(0).getPid());
//                        traBusinessService.updateSelective(business);
//                        count2 ++;
//                    } else {
                        business.setTradeOrg(userProfile.getUnitCode());
                        business.setCreateById(userProfile.getStaffCode());
                        business.setCreateByName(userProfile.getStaffName());
                        business.setTransNum(BatchNumberUtil.makeBatchNumber());
                        business.setAmendId(userProfile.getStaffCode());
                        business.setAmendName(userProfile.getStaffName());
                        business.setDeptName(userProfile.getDeptName());
                        traBusinessService.insertSelective(business);
                        count3 ++;
//                    }
                } catch (Exception e) {
                    index++;
                    LOGGER.error("导入出错:{}",e);
                    falNum += i + 1;
                    resMsg.append("第").append(i + 1).append("条数据导入失败,");
                }
            }
        }
        XlsMessage res = new XlsMessage();
        String result = "导入【"+sum+"】条，成功【"+(sum-falNum)+"】条。新增【"+count3+"】条; 失败【"+falNum+"】条数据,失败原因：" + resMsg;
        res.setMessage(result);
        return res;
    }

    /**
     * 处理字段空格(使用字符串)
     * @param obj
     * @return
     */
    private String trimStr(Object obj) {
        if(obj != null) {
            return obj.toString().trim();
        }
        return null;
    }


    @Override
    public String checkBeforeImport(Map parameters, Map mapReaded) throws ServiceException {
        //如果通过检查，就保持result为null，否则给出返回错误描述
        String result = null;
        @SuppressWarnings("unchecked")
        List<TraBusinessVo> list = (List<TraBusinessVo>) mapReaded.get(IMPORT_DATAS);
        if (list != null && list.size() - 1 > 0) {
            Map<String, List<DictItem>> dicMap = getDicValue();

//            for (int i = 0; i < list.size() - 1; i++) {
//                TraBusinessVo traBusiness = list.get(i);
//                //检查基本信息
//                String resBasic = checkBasicInfo(traBusiness, dicMap);
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

    public String checkBasicInfo(TraBusinessVo traBusinessVo, Map<String, List<DictItem>> dicMap) {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isBlank(traBusinessVo.getTranName())) {
            sb.append("交易概述不能为空；");
        }

        if(StringUtils.isBlank(traBusinessVo.getRelType())){
            sb.append("关联方类型不能为空；");
        }else {
            String relType = DicItemUtils.getItemValue(dicMap, DicItemType.RELTYPECODE,traBusinessVo.getRelType());
            if(StringUtils.isBlank(relType)){
                sb.append("关联方类型格式不正确；");
            }
        }

        if (StringUtils.isBlank(traBusinessVo.getRelNumber())) {
            sb.append("关联方证件号码为空；");
        }
        if (StringUtils.isBlank(traBusinessVo.getRelName())) {
            sb.append("关联方名称为空；");
        }
        if(StringUtils.isBlank(traBusinessVo.getContractDateString())){
            sb.append("合同签订日期不能为空；");
        }
        if (StringUtils.isBlank(traBusinessVo.getContractNo())) {
            sb.append("合同编号为空");
        }
        if (ObjectUtil.isEmpty(traBusinessVo.getRelTradeAmount())) {
            sb.append("关联交易金额不能为空；");
        }
        if (StringUtils.isBlank(traBusinessVo.getBusStartTimeString())) {
            sb.append("业务起始日期不能为空；");
        }

        if (StringUtils.isBlank(traBusinessVo.getBusType())) {
            sb.append("业务类型不能为空；");
        }else {
            String busType = DicItemUtils.getItemValue(dicMap, DicItemType.BUSINESSBUSTYPE,traBusinessVo.getBusType());
            if (StringUtils.isBlank(busType)) {
                sb.append("业务类型格式不正确；");
            }
        }



        if(StringUtils.isBlank(traBusinessVo.getIsAgreement())){
            sb.append("是否统一交易协议不能为空；");
        }else {
            String isAgreement = DicItemUtils.getItemValue(dicMap,DicItemType.YESORNOCODE,traBusinessVo.getIsAgreement());
            if(YesOrNoEnum.YES.getCode().equals(isAgreement) && StringUtils.isBlank(traBusinessVo.getIsAgreementImpl())){
                sb.append("统一交易协议执行情况不能为空；");
            }
        }

        if (StringUtils.isBlank(traBusinessVo.getTradePrice())) {
            sb.append("交易价格不能为空；");
        }
        if (StringUtils.isBlank(traBusinessVo.getPricingPolicy())) {
            sb.append("定价政策不能为空；");
        }
        if (StringUtils.isBlank(traBusinessVo.getTradeSubject())) {
            sb.append("交易标的不能为空；");
        }

        if(StringUtils.isBlank(traBusinessVo.getIsconsiderSituation())){
            sb.append("是否可免于审议不能为空；");
        }else {
            String isconsiderSituation = DicItemUtils.getItemValue(dicMap,DicItemType.YESORNOCODE,traBusinessVo.getIsconsiderSituation());

            if(StringUtils.isBlank(isconsiderSituation)){
                sb.append("是否可免于审议格式不正确；");
            }

            if(YesOrNoEnum.YES.getCode().equals(isconsiderSituation)){
                if(StringUtils.isBlank(traBusinessVo.getConsiderSituation())){
                    sb.append("免于审议情形不能为空；");
                }else {
                    String considerSituation = DicItemUtils.getItemValue(dicMap,DicItemType.CONSIDERSITUATIONCODE,traBusinessVo.getConsiderSituation());
                    if(StringUtils.isBlank(considerSituation)){
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
        if (CollUtil.isNotEmpty(dictItemList)) {
            dicItemMap = dictItemList.stream().collect(Collectors.groupingBy(DictItem::getDictCode));
        }

        return dicItemMap;
    }

    public void checkStrIsEmpty(TraBusiness traBusiness) {
        if ( StringUtils.isNotBlank(traBusiness.getTranName()) && StringUtils.isNotBlank(traBusiness.getRelName())
                && StringUtils.isNotBlank(traBusiness.getRelNumber()) && StringUtils.isNotBlank(traBusiness.getRelType())
                && StringUtils.isNotBlank(traBusiness.getIsAgreement()) && StringUtils.isNotBlank(traBusiness.getContractNo())
                && null != traBusiness.getRelTradeAmount() && StringUtils.isNotBlank(traBusiness.getIsconsiderSituation())
                && StringUtils.isNotBlank(traBusiness.getPricingPolicy()) && StringUtils.isNotBlank(traBusiness.getTradePrice())
                && StringUtils.isNotBlank(traBusiness.getTranType()) && StringUtils.isNotBlank(traBusiness.getBusType())
                && StringUtils.isNotBlank(traBusiness.getTradeSubject())) {

            traBusiness.setStatus("3");

            /* 免于审议时，状态为已归档 */
            if ("1".equals(traBusiness.getConsiderType())) {
                // 实际交易金额不为空 + 合同签署日期不为空 + 业务起始日期 = 已归档
                if (StringUtils.isNotBlank(traBusiness.getPracTradeAmount()) && ObjectUtil.isNotEmpty(traBusiness.getContractDate()) && ObjectUtil.isNotEmpty(traBusiness.getBusStartTime())) {
                    traBusiness.setStatus("6");
                }
                // 实际交易金额不为空 + 合同签署日期为空 = 待签订
                // 实际交易金额为空 + 合同签署日期为空 = 待签订
                if (ObjectUtil.isEmpty(traBusiness.getContractDate()) || ObjectUtil.isEmpty(traBusiness.getBusStartTime())) {
                    traBusiness.setStatus("8");
                }
                // 实际交易金额为空 + 合同签署日期不为空 = 履行中
                if(StringUtils.isBlank(traBusiness.getPracTradeAmount()) && ObjectUtil.isNotEmpty(traBusiness.getContractDate())){
                    traBusiness.setStatus("7");
                }
            }
        }
    }
}
