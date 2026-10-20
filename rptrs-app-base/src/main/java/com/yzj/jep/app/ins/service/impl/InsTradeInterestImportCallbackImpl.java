package com.yzj.jep.app.ins.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.yzj.jep.app.bus.vo.PsnInfoVo;
import com.yzj.jep.app.enums.DataSource;
import com.yzj.jep.app.ins.common.ImportUtil;
import com.yzj.jep.app.ins.constants.DicItemType;
import com.yzj.jep.app.ins.enums.YesOrNoEnum;
import com.yzj.jep.app.ins.model.TraFunds;
import com.yzj.jep.app.ins.model.TraFundsVo;
import com.yzj.jep.app.ins.model.TraInterest;
import com.yzj.jep.app.ins.model.TraInterestVo;
import com.yzj.jep.app.ins.service.BaseService;
import com.yzj.jep.app.ins.service.TraInterestService;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.sys.util.XssCheck;
import com.yzj.jep.app.util.BatchNumberUtil;
import com.yzj.jep.app.util.Dict;
import com.yzj.jep.app.util.InsUtil;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.utils.XlsMessage;
import com.yzj.jep.core.utils.xlsio.XlsImportCallback;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import com.yzj.jep.web.code.WebCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.yzj.jep.app.util.DicItemUtils.getItemValue;
import static com.yzj.jep.commons.StringUtils.trim;

/**
 * 内部交易管理导入实现
 *
 * @author lq
 * @time 2020年3月13日 下午3:47:25
 * @company 银之杰技术有限公司
 */
@Service(value = InsTradeInterestImportCallbackImpl.BEANID)
public class InsTradeInterestImportCallbackImpl implements XlsImportCallback {
    private static final Logger LOGGER = LoggerFactory.getLogger(InsTradeInterestImportCallbackImpl.class);

    @Resource
    DictItemService dictItemService;

    @Resource
    TraInterestService traInterestService;

    @Resource
    BaseService baseService;

    public static final String BEANID = "InsTradeInterestImportCallbackImpl";

    /**
     * 配置导入的模板（XML）文件
     */
    private static final String XML_TEMP_FILE = "InsTradeInterestImportMapper.xml";
    private static final String IMPORT_DATAS = "InsTraInterest";

    /**
     * 获取导入XML配置模板
     *
     * @return 模板文件名
     */
    @Override
    public String getImportXmlTemplate() {
        return XLSTEMPLATE_PATH + XML_TEMP_FILE;
    }

    /**
     * 设置Excel数据的上下文
     *
     * @param beans 模板中定义的变量
     */
    @Override
    public void setXlsReaderBeans(Map beans) {
        //一般而言，有几个sheet页，就有几个元素323
        beans.put(IMPORT_DATAS, new ArrayList());
    }

    /**
     * 导入文件前检查文件有效性
     *
     * @param parameters
     * @param mapReaded
     * @return String 提示信息，检查通过返回null
     */
    @Override
    public String checkBeforeImport(Map parameters, Map mapReaded) throws ServiceException {
        List<TraInterestVo> importDataList = (List<TraInterestVo>) mapReaded.get(IMPORT_DATAS);
        if (CollUtil.isEmpty(importDataList) || importDataList.size() <= 1) {
            return "导入数据为空。";
        }

        Map<String, List<DictItem>> dictMap = getDicValue();

        List<String> errorList = new ArrayList<>();

        for (int index = 0; index < importDataList.size() - 1; index++) {
            TraInterestVo lineData = importDataList.get(index);
            List<String> singleErrorList = new ArrayList<>();
            // 1、去除前后空格
            trimConvert(lineData);
            // 2、非空校验
            nonnullCheck(lineData, singleErrorList);
            // 3、日期转换
            dateConvert(lineData, singleErrorList);
            // 4、数值转换
            numberConvert(lineData, singleErrorList);
            // 5、字典转码
            dictConvert(lineData, dictMap, singleErrorList);
            // 保存单行错误信息
            if (CollUtil.isNotEmpty(singleErrorList)) {
                String singleError = StrUtil.format("检测到第{}条数据问题含有：{}", index + 1, String.join("，", singleErrorList));
                errorList.add(singleError);
            }
        }

        // 如果返回null，表示检查通过
        return CollUtil.isEmpty(errorList) ? null : String.join("；<br/>", errorList);
    }

    @Override
    public XlsMessage executeSaveCallback(Map parameters, Map beans) throws ServiceException {
        // 响应信息对象
        XlsMessage res = new XlsMessage();

        List<TraInterestVo> importDataList = (List<TraInterestVo>) beans.get(IMPORT_DATAS);
        if (CollUtil.isEmpty(importDataList)) {
            res.setMessage("导入失败，未识别到导入数据！");
            return res;
        }

        List<String> errorList = new ArrayList<>();
        // ************************************************************************************************

        for (int index = 0; index < importDataList.size() - 1; index++) {
            TraInterestVo interest = importDataList.get(index);
            try {
                singleProcess(interest);
            } catch (Exception e) {
                String singleError = StrUtil.format("第{}条数据导入失败！", index + 1);
                LOGGER.error(singleError, e);
                errorList.add(singleError);
            }
        }

        // ************************************************************************************************
        String resultInfo = "总共【{}】条数据，成功导入【{}】条，失败【{}】条，失败记录如下：<br/>{}";
        // 总数
        int allCount = importDataList.size() - 1;
        int failCount = errorList.size();
        String failMsg = String.join("；<br/>", errorList);
        res.setMessage(CharSequenceUtil.format(resultInfo, allCount, allCount - failCount, failCount, failMsg));
        return res;
    }

    @Override
    public Map setReturnBeanFromXls(Map beans, InputStream inputXLS) throws ServiceException {
        //对导入的数据做特殊处理，如不处理则直接返回beans
        @SuppressWarnings("unchecked")
        List<PsnInfoVo> list = (List<PsnInfoVo>) beans.get(IMPORT_DATAS);

        return beans;
    }

    // ************************************************************************************************

    /**
     * 单行数据处理
     *
     * @param interest
     */
    private void singleProcess(TraInterestVo interest) {
        boolean xssCheckResult = XssCheck.reflect(interest);
        Assert.isTrue(xssCheckResult, "数据格式不规范, 请检查！");

        // 校验关联方是否存在
        Map<String, Object> relInfo = baseService.getRelInfo(interest.getRelNumber(), null);
        // true：存在/false：不存在；
        boolean isExist = (Boolean) relInfo.get(WebCode.SUCCESS);
        Assert.isTrue(isExist, "此关联方不存在！");

        // 获取当前用户信息
        UserProfile userProfile = UserProfileUtils.getUserProfile();

        TraInterest traInterest = new TraInterest();
        BeanUtils.copyProperties(interest, traInterest);

        // ******************************************************************************************
        //封装重大判断参数
        Map<String, Object> ruleMap = new HashMap<>(5);
        ruleMap.put("relType", traInterest.getRelType());
        ruleMap.put("relNumber", traInterest.getRelNumber());
        ruleMap.put("isAgreement", null);
        ruleMap.put("relTranAmount", traInterest.getRelTradeAmount());

        //判断重大
        Map<String, String> ruleResultMap = InsUtil.judgeMajor(ruleMap);
        traInterest.setMajorType(ruleResultMap.get("majorType"));
        traInterest.setMajorTrade(ruleResultMap.get("majorTrade"));
        traInterest.setSingleRelatedTranType(ruleResultMap.get("singleRelatedTrantype"));
        traInterest.setConsiderType(ruleResultMap.get("considerType"));
        traInterest.setEachReport(ruleResultMap.get("eachReport"));
        traInterest.setEachDisclosure(ruleResultMap.get("eachDisclosure"));
        traInterest.setIsCollectRelated(ruleResultMap.get("isCollectRelated"));

        // ********************************************************************************************
        setStatus(traInterest);
        // ********************************************************************************************

        Integer relId = Integer.parseInt(String.valueOf(relInfo.get("relId")));

        traInterest.setRelId(relId);

        traInterest.setDataSources(DataSource.SOURCE_SYS_TYPE_1.getSysCode());
        traInterest.setDelflag("1");
        traInterest.setTranType(Dict.TRAN_TYPE_04);
        traInterest.setIsAccumulated(YesOrNoEnum.NO.getCode());

        traInterest.setTradeOrg(userProfile.getUnitCode());
        traInterest.setCreateById(userProfile.getStaffCode());
        traInterest.setCreateByName(userProfile.getStaffName());
        traInterest.setAmendId(userProfile.getStaffCode());
        traInterest.setAmendName(userProfile.getStaffName());
        traInterest.setDeptName(userProfile.getDeptName());
        traInterest.setTransNum(BatchNumberUtil.makeBatchNumber());

        // 保存
        traInterestService.insertSelective(traInterest);
    }

    /**
     * 去空格
     *
     * @param lineData 导入数据
     */
    private void trimConvert(TraInterestVo lineData) {
        // 资金流水编号
        lineData.setFundsFlowNumber(trim(lineData.getFundsFlowNumber()));
        lineData.setTranName(trim(lineData.getTranName()));
        lineData.setRelName(trim(lineData.getRelName()));
        lineData.setRelType(trim(lineData.getRelType()));
        lineData.setRelNumber(trim(lineData.getRelNumber()));

        lineData.setBusType(trim(lineData.getBusType()));
        lineData.setContractDateString(trim(lineData.getContractDateString()));
        lineData.setContractNo(trim(lineData.getContractNo()));
        lineData.setOriginalCurrencyAmountString(trim(lineData.getOriginalCurrencyAmountString()));
        lineData.setCurrencyCode(trim(lineData.getCurrencyCode()));

        lineData.setRelTradeAmountString(trim(lineData.getRelTradeAmountString()));
        lineData.setBusStartTimeString(trim(lineData.getBusStartTimeString()));
        lineData.setBusEndTimeString(trim(lineData.getBusEndTimeString()));
        lineData.setTradingDirection(trim(lineData.getTradingDirection()));
        lineData.setGuaranteeConditions(trim(lineData.getGuaranteeConditions()));

        lineData.setTradePrice(trim(lineData.getTradePrice()));
        lineData.setPricingPolicy(trim(lineData.getPricingPolicy()));
        lineData.setTradeSubject(trim(lineData.getTradeSubject()));
        lineData.setRemark(trim(lineData.getRemark()));
    }

    /**
     * 非空校验
     *
     * @param lineData 导入数据
     */
    private void nonnullCheck(TraInterestVo lineData, List<String> errorList) {
        ImportUtil.isBlank(errorList, lineData.getFundsFlowNumber(), "资金流水编号不能为空；");
        ImportUtil.isBlank(errorList, lineData.getTranName(), "交易概述不能为空；");
        ImportUtil.isBlank(errorList, lineData.getRelName(), "关联方名称不能为空；");
        ImportUtil.isBlank(errorList, lineData.getRelType(), "关联方类型不能为空；");
        ImportUtil.isBlank(errorList, lineData.getRelNumber(), "关联方证件号码不能为空；");

        ImportUtil.isBlank(errorList, lineData.getBusType(), "业务类型不能为空；");
        ImportUtil.isBlank(errorList, lineData.getContractDateString(), "合同签订日期不能为空；");
        ImportUtil.isBlank(errorList, lineData.getContractNo(), "合同编号不能为空；");
        ImportUtil.isBlank(errorList, lineData.getOriginalCurrencyAmountString(), "原币交易金额不能为空；");
        ImportUtil.isBlank(errorList, lineData.getCurrencyCode(), "交易币种不能为空；");

        ImportUtil.isBlank(errorList, lineData.getRelTradeAmountString(), "本位币交易金额不能为空；");
        ImportUtil.isBlank(errorList, lineData.getBusStartTimeString(), "业务起始日期不能为空；");
        ImportUtil.isBlank(errorList, lineData.getBusEndTimeString(), "业务结束日期不能为空；");
        ImportUtil.isBlank(errorList, lineData.getTradingDirection(), "交易方向不能为空；");
        boolean isBusType03 = "担保".equals(lineData.getBusType());
        ImportUtil.isBlank(isBusType03, errorList, lineData.getGuaranteeConditions(), "担保条件不能为空；");

        ImportUtil.isBlank(errorList, lineData.getTradePrice(), "交易价格不能为空；");
        ImportUtil.isBlank(errorList, lineData.getPricingPolicy(), "定价政策不能为空；");
        ImportUtil.isBlank(errorList, lineData.getTradeSubject(), "交易标的不能为空；");
    }

    /**
     * 日期解析
     *
     * @param lineData
     * @param errorList
     */
    private void dateConvert(TraInterestVo lineData, List<String> errorList) {

        if (StringUtils.isNotBlank(lineData.getContractDateString())) {
            Date tranDate = DateUtils.parseDate(lineData.getContractDateString());
            ImportUtil.addErrorMsg(tranDate == null, errorList, "合同签订日期格式不正确；");
            lineData.setContractDate(tranDate);
        }

        if (StringUtils.isNotBlank(lineData.getBusStartTimeString())) {
            Date busStartTime = DateUtils.parseDate(lineData.getBusStartTimeString());
            ImportUtil.addErrorMsg(busStartTime == null, errorList, "业务开始时间格式不正确；");
            lineData.setBusStartTime(busStartTime);
        }

        if (StringUtils.isNotBlank(lineData.getBusEndTimeString())) {
            Date busEndTime = DateUtils.parseDate(lineData.getBusEndTimeString());
            ImportUtil.addErrorMsg(busEndTime == null, errorList, "业务结束时间格式不正确；");
            lineData.setBusEndTime(busEndTime);
        }

    }

    /**
     * 数值转换
     *
     * @param lineData
     * @param errorList
     */
    private void numberConvert(TraInterestVo lineData, List<String> errorList) {

        // 本位币交易金额（万元）
        String relTradeAmountString = lineData.getRelTradeAmountString();
        if (StrUtil.isNotBlank(relTradeAmountString)) {
            BigDecimal relTradeAmount = ImportUtil.decimalConvert(errorList, relTradeAmountString, "本位币交易金额（万元）格式不正确；");
            lineData.setRelTradeAmount(relTradeAmount);
        }

        // 原币交易金额（万元）
        String originalCurrencyAmountString = lineData.getOriginalCurrencyAmountString();
        if (StrUtil.isNotBlank(originalCurrencyAmountString)) {
            BigDecimal originalCurrencyAmount = ImportUtil.decimalConvert(errorList, originalCurrencyAmountString, "原币交易金额（万元）格式不正确；");
            lineData.setOriginalCurrencyAmount(originalCurrencyAmount);
        }
    }

    /**
     * 字典项转换
     *
     * @param lineData 导入数据
     */
    private void dictConvert(TraInterestVo lineData, Map<String, List<DictItem>> dicMap, List<String> errorList) {
        // D：关联方类型
        String relType = getItemValue(dicMap, DicItemType.RELTYPECODE, lineData.getRelType());
        ImportUtil.isBlank(errorList, relType, "关联方类型格式不正确；");
        lineData.setRelType(relType);

        // F：业务类型
        String busType = getItemValue(dicMap, DicItemType.BUSTYPECODE, lineData.getBusType());
        ImportUtil.isBlank(errorList, busType, "业务类型格式不正确；");
        lineData.setBusType(busType);

        // J：交易币种
        String currencyCode = getItemValue(dicMap, DicItemType.CURRENCY_CODE, lineData.getCurrencyCode());
        ImportUtil.isBlank(errorList, currencyCode, "交易币种格式不正确；");
        lineData.setCurrencyCode(currencyCode);

        // N：交易方向
        String tradingDirection = getItemValue(dicMap, DicItemType.TRATARGET, lineData.getTradingDirection());
        ImportUtil.isBlank(errorList, tradingDirection, "交易方向格式不正确；");
        lineData.setTradingDirection(tradingDirection);

        // O：担保方式（业务类型为03时必填）
        /*if ("03".equals(busType)) {
            String guaranteeConditions = getItemValue(dicMap, DicItemType.YESORNOCODE, lineData.getGuaranteeConditions());
            ImportUtil.isBlank(errorList, guaranteeConditions, "担保方式格式不正确；");
            lineData.setGuaranteeConditions(guaranteeConditions);
        }*/
    }

    // ************************************************************************************************


    // 初始化字典项
    public Map<String, List<DictItem>> getDicValue() {
        Map<String, List<DictItem>> dicItemMap = new HashMap();
        List<DictItem> dictItemList = dictItemService.selectAll();
        if (CollUtil.isNotEmpty(dictItemList)) {
            dicItemMap = dictItemList.stream().collect(Collectors.groupingBy(DictItem::getDictCode));
        }
        return dicItemMap;
    }

    /**
     * 检验必填项
     *
     * @param traInterest 数据源
     */
    public void setStatus(TraInterest traInterest) {
        boolean hasBlank = !StrUtil.isAllNotBlank(traInterest.getBusType(),
                traInterest.getContractNo(),
                traInterest.getTradePrice(),
                traInterest.getPricingPolicy(),
                traInterest.getTranName(),
                traInterest.getTradeSubject(),
                traInterest.getMajorTrade(),
                traInterest.getIsconsiderSituation()
        );

        boolean hasNull = traInterest.getRelTradeAmount() != null;

        if (hasBlank || hasNull) {
            traInterest.setStatus("1");
            return;
        }

        /* 免于审议时，状态为已归档 */
        if ("1".equals(traInterest.getConsiderType())) {
            // 实际交易金额不为空 + 合同签署日期不为空 = 已归档
            if (ObjectUtil.isAllNotEmpty(traInterest.getPracTradeAmount(), traInterest.getContractDate())) {
                traInterest.setStatus("6");
            }

            // 实际交易金额为空 + 合同签署日期不为空 = 履行中
            if (StrUtil.isBlank(traInterest.getPracTradeAmount()) && ObjectUtil.isNotEmpty(traInterest.getContractDate())) {
                traInterest.setStatus("7");
            }

            // 实际交易金额不为空 + 合同签署日期为空 = 待签订
            // 实际交易金额为空 + 合同签署日期为空 = 待签订
            if (ObjectUtil.isEmpty(traInterest.getContractDate()) || ObjectUtil.isEmpty(traInterest.getBusStartTime())) {
                traInterest.setStatus("8");
            }

            /* 是统一交易协议时，统一交易审议类型不能为空 */
            if ("1".equals(traInterest.getIsAgreement()) && StrUtil.isBlank(traInterest.getIsAgreementImpl())) {
                traInterest.setStatus("1");
            }
        }

        traInterest.setStatus("3");
    }
}
