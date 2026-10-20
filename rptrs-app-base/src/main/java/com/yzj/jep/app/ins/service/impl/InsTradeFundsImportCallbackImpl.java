package com.yzj.jep.app.ins.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.yzj.jep.app.enums.DataSource;
import com.yzj.jep.app.ins.common.ImportUtil;
import com.yzj.jep.app.ins.constants.DicItemType;
import com.yzj.jep.app.ins.enums.YesOrNoEnum;
import com.yzj.jep.app.ins.model.TraFunds;
import com.yzj.jep.app.ins.model.TraFundsVo;
import com.yzj.jep.app.ins.model.TraInterestVo;
import com.yzj.jep.app.ins.service.BaseService;
import com.yzj.jep.app.ins.service.TraFundsService;
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
@Service(value = InsTradeFundsImportCallbackImpl.BEANID)
public class InsTradeFundsImportCallbackImpl implements XlsImportCallback {
    private static final Logger LOGGER = LoggerFactory.getLogger(InsTradeFundsImportCallbackImpl.class);

    @Resource
    DictItemService dictItemService;

    @Resource
    TraFundsService traFundsService;

    @Resource
    BaseService baseService;

    public static final String BEANID = "InsTradeFundsImportCallbackImpl";

    /**
     * 配置导入的模板（XML）文件
     */
    private static final String XML_TEMP_FILE = "InsTradeFundsImportMapper.xml";
    private static final String IMPORT_DATAS = "insTradeFunds";

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
        //一般而言，有几个sheet页，就有几个元素
        beans.put(IMPORT_DATAS, new ArrayList<>());
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
        List<TraFundsVo> importDataList = (List<TraFundsVo>) mapReaded.get(IMPORT_DATAS);
        if (CollUtil.isEmpty(importDataList) || importDataList.size() <= 1) {
            return "导入数据为空。";
        }

        Map<String, List<DictItem>> dictMap = getDicValue();

        List<String> errorList = new ArrayList<>();

        for (int index = 0; index < importDataList.size() - 1; index++) {
            TraFundsVo lineData = importDataList.get(index);
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

        // 导入的数据
        List<TraFundsVo> importDataList = (List<TraFundsVo>) beans.get(IMPORT_DATAS);
        if (CollUtil.isEmpty(importDataList)) {
            res.setMessage("导入失败，未识别到导入数据！");
            return res;
        }

        List<String> errorList = new ArrayList<>();
        // ************************************************************************************************
        // 执行
        for (int index = 0; index < importDataList.size() - 1; index++) {
            TraFundsVo funds = importDataList.get(index);
            try {
                singleProcess(funds);
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
        // 对导入的数据做特殊处理，如不处理则直接返回beans
        //List<PsnInfoVo> list = (List<PsnInfoVo>) beans.get(IMPORT_DATAS);
        return beans;
    }

    // ************************************************************************************************

    /**
     * 单行数据处理
     *
     * @param funds
     */
    private void singleProcess(TraFundsVo funds) {
        boolean xssCheckResult = XssCheck.reflect(funds);
        Assert.isTrue(xssCheckResult, "数据格式不规范, 请检查！");

        // 校验关联方是否存在
        Map<String, Object> relInfo = baseService.getRelInfo(funds.getRelNumber(), null);
        // true：存在/false：不存在；
        boolean isExist = (Boolean) relInfo.get(WebCode.SUCCESS);
        Assert.isTrue(isExist, "此关联方不存在！");

        // 获取当前用户信息
        UserProfile userProfile = UserProfileUtils.getUserProfile();

        TraFunds traFunds = new TraFunds();
        BeanUtils.copyProperties(funds, traFunds);

        // ******************************************************************************************
        // 封装重大判断参数
        Map<String, Object> ruleMap = new HashMap<>(5);
        ruleMap.put("relType", traFunds.getRelType());
        ruleMap.put("relNumber", traFunds.getRelNumber());
        ruleMap.put("isAgreement", null);
        ruleMap.put("relTranAmount", traFunds.getRelTradeAmount());

        // 判断重大
        Map<String, String> ruleResultMap = InsUtil.judgeMajor(ruleMap);
        traFunds.setMajorType(ruleResultMap.get("majorType"));
        traFunds.setMajorTrade(ruleResultMap.get("majorTrade"));
        traFunds.setSingleRelatedTranType(ruleResultMap.get("singleRelatedTrantype"));
        traFunds.setConsiderType(ruleResultMap.get("considerType"));
        traFunds.setEachReport(ruleResultMap.get("eachReport"));
        traFunds.setEachDisclosure(ruleResultMap.get("eachDisclosure"));
        traFunds.setIsCollectRelated(ruleResultMap.get("isCollectRelated"));
        traFunds.setIsAccumulated(YesOrNoEnum.NO.getCode());
        traFunds.setModifiedTime(new Date());

        // ********************************************************************************************
        setStatus(traFunds);
        // ********************************************************************************************

        Integer relId = Integer.parseInt(String.valueOf(relInfo.get("relId")));

        traFunds.setRelId(relId);

        traFunds.setDataSources(DataSource.SOURCE_SYS_TYPE_1.getSysCode());
        traFunds.setDelflag("1");
        traFunds.setTranType(Dict.TRAN_TYPE_02);

        traFunds.setTradeOrg(userProfile.getUnitCode());
        traFunds.setCreateById(userProfile.getStaffCode());
        traFunds.setCreateByName(userProfile.getStaffName());
        traFunds.setAmendId(userProfile.getStaffCode());
        traFunds.setAmendName(userProfile.getStaffName());
        traFunds.setDeptName(userProfile.getDeptName());
        traFunds.setTransNum(BatchNumberUtil.makeBatchNumber());

        // 保存
        traFundsService.insertSelective(traFunds);
    }

    /**
     * 去空格
     *
     * @param lineData 导入数据
     */
    private void trimConvert(TraFundsVo lineData) {
        // 资金流水编号
        lineData.setFundsFlowNumber(trim(lineData.getFundsFlowNumber()));
        lineData.setTranName(trim(lineData.getTranName()));
        lineData.setRelType(trim(lineData.getRelType()));
        lineData.setRelName(trim(lineData.getRelName()));
        lineData.setRelNumber(trim(lineData.getRelNumber()));

        lineData.setTranDateString(trim(lineData.getTranDateString()));
        lineData.setContractNo(trim(lineData.getContractNo()));
        lineData.setOriginalCurrencyAmountString(trim(lineData.getOriginalCurrencyAmountString()));
        lineData.setCurrencyCode(trim(lineData.getCurrencyCode()));
        lineData.setRelTradeAmountString(trim(lineData.getRelTradeAmountString()));

        lineData.setBusStartTimeString(trim(lineData.getBusStartTimeString()));
        lineData.setBusEndTimeString(trim(lineData.getBusEndTimeString()));
        lineData.setBusType(trim(lineData.getBusType()));
        lineData.setInvestmentMethod(trim(lineData.getInvestmentMethod()));
        lineData.setFinancialAssstsName(trim(lineData.getFinancialAssstsName()));

        lineData.setFinancialAssstsNum(trim(lineData.getFinancialAssstsNum()));
        lineData.setFinancialProRatio(trim(lineData.getFinancialProRatio()));
        lineData.setIsOverseasinvestment(trim(lineData.getIsOverseasinvestment()));
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
    private void nonnullCheck(TraFundsVo lineData, List<String> errorList) {
        ImportUtil.isBlank(errorList, lineData.getFundsFlowNumber(), "资金流水编号不能为空；");
        ImportUtil.isBlank(errorList, lineData.getTranName(), "交易概述不能为空；");
        ImportUtil.isBlank(errorList, lineData.getRelType(), "关联方类型不能为空；");
        ImportUtil.isBlank(errorList, lineData.getRelName(), "关联方名称不能为空；");
        ImportUtil.isBlank(errorList, lineData.getRelNumber(), "关联方证件号码不能为空；");

        ImportUtil.isBlank(errorList, lineData.getTranDateString(), "合同签订日期不能为空；");
        ImportUtil.isBlank(errorList, lineData.getContractNo(), "合同编号不能为空；");
        ImportUtil.isBlank(errorList, lineData.getOriginalCurrencyAmountString(), "原币交易金额不能为空；");
        ImportUtil.isBlank(errorList, lineData.getCurrencyCode(), "交易币种不能为空；");
        ImportUtil.isBlank(errorList, lineData.getRelTradeAmountString(), "本位币交易金额不能为空；");

        ImportUtil.isBlank(errorList, lineData.getBusStartTimeString(), "业务起始日期不能为空；");
        ImportUtil.isBlank(errorList, lineData.getBusEndTimeString(), "业务结束日期不能为空；");
        ImportUtil.isBlank(errorList, lineData.getBusType(), "业务类型不能为空；");
        ImportUtil.isBlank(errorList, lineData.getInvestmentMethod(), "投资方式不能为空；");
        boolean isBusType04 = "投资金融产品".equals(lineData.getBusType());
        ImportUtil.isBlank(isBusType04, errorList, lineData.getFinancialAssstsName(), "金融产品名称不能为空；");

        ImportUtil.isBlank(isBusType04, errorList, lineData.getFinancialAssstsNum(), "产品批准/注册/备案号不能为空；");
        ImportUtil.isBlank(isBusType04, errorList, lineData.getFinancialProRatio(), "金融产品份额占比不能为空；");
        ImportUtil.isBlank(errorList, lineData.getIsOverseasinvestment(), "是否为境外投资不能为空；");
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
    private void dateConvert(TraFundsVo lineData, List<String> errorList) {
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

        if (StringUtils.isNotBlank(lineData.getTranDateString())) {
            Date tranDate = DateUtils.parseDate(lineData.getTranDateString());
            ImportUtil.addErrorMsg(tranDate == null, errorList, "交易日期格式不正确；");
            lineData.setTranDate(tranDate);
        }
    }

    /**
     * 数值转换
     *
     * @param lineData
     * @param errorList
     */
    private void numberConvert(TraFundsVo lineData, List<String> errorList) {

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
    private void dictConvert(TraFundsVo lineData, Map<String, List<DictItem>> dicMap, List<String> errorList) {
        // C：关联方类型
        String relType = getItemValue(dicMap, DicItemType.RELTYPECODE, lineData.getRelType());
        ImportUtil.isBlank(errorList, relType, "关联方类型格式不正确；");
        lineData.setRelType(relType);

        // I：交易币种
        String currencyCode = getItemValue(dicMap, DicItemType.CURRENCY_CODE, lineData.getCurrencyCode());
        ImportUtil.isBlank(errorList, currencyCode, "交易币种格式不正确；");
        lineData.setCurrencyCode(currencyCode);

        // M：业务类型
        String busType = getItemValue(dicMap, DicItemType.FUNDSBUSTYPE, lineData.getBusType());
        ImportUtil.isBlank(errorList, busType, "业务类型格式不正确；");
        lineData.setBusType(busType);

        // N：投资方式
        String investmentMethod = getItemValue(dicMap, DicItemType.INVESTMENTMETHOD, lineData.getInvestmentMethod());
        ImportUtil.isBlank(errorList, investmentMethod, "投资方式格式不正确；");
        lineData.setInvestmentMethod(investmentMethod);

        // R：是否境外投资
        String isOverseasinvestment = getItemValue(dicMap, DicItemType.YESORNOCODE, lineData.getIsOverseasinvestment());
        ImportUtil.isBlank(errorList, isOverseasinvestment, "是否为境外投资格式不正确；");
        lineData.setIsOverseasinvestment(isOverseasinvestment);
    }

    /**
     * 设置状态
     *
     * @param traFunds
     */
    private void setStatus(TraFunds traFunds) {
        boolean hasBlank = !StrUtil.isAllNotBlank(traFunds.getBusType(),
                traFunds.getContractNo(),
                traFunds.getTradePrice(),
                traFunds.getPricingPolicy(),
                traFunds.getTranName(),
                traFunds.getTradeSubject(),
                traFunds.getMajorTrade(),
                traFunds.getIsconsiderSituation(),
                traFunds.getIsOverseasinvestment(),
                traFunds.getInvestmentMethod()
        );

        boolean hasNull = traFunds.getRelTradeAmount() != null;
        if (hasBlank || hasNull || ("04".equals(traFunds.getBusType()) && StrUtil.isAllBlank(traFunds.getFinancialAssstsName(), traFunds.getFinancialAssstsNum(), traFunds.getFinancialProRatio()))) {
            traFunds.setStatus("1");
            return;
        }

        /* 免于审议时，状态为已归档 */
        if ("1".equals(traFunds.getConsiderType())) {
            // 实际交易金额不为空 + 合同签署日期不为空 + 业务起始日期 = 已归档
            if (ObjectUtil.isAllNotEmpty(traFunds.getPracTradeAmount(), traFunds.getTranDate(), traFunds.getBusStartTime())) {
                traFunds.setStatus("6");
                return;
            }

            // 实际交易金额为空 + 合同签署日期不为空 = 履行中
            if (StrUtil.isBlank(traFunds.getPracTradeAmount()) && ObjectUtil.isNotEmpty(traFunds.getTranDate())) {
                traFunds.setStatus("7");
                return;
            }

            // 实际交易金额不为空 + 合同签署日期为空 = 待签订
            // 实际交易金额为空 + 合同签署日期为空 = 待签订
            if (traFunds.getTranDate() == null || traFunds.getBusStartTime() == null) {
                traFunds.setStatus("8");
                return;
            }
        }

        // 状态为已归档
        traFunds.setStatus("3");
    }

    // 初始化字典项
    private Map<String, List<DictItem>> getDicValue() {
        Map<String, List<DictItem>> dicItemMap = new HashMap();
        List<DictItem> dictItemList = dictItemService.selectAll();
        if (CollUtil.isNotEmpty(dictItemList)) {
            dicItemMap = dictItemList.stream().collect(Collectors.groupingBy(DictItem::getDictCode));
        }
        return dicItemMap;
    }

}
