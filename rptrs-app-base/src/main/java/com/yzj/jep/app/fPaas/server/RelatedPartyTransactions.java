package com.yzj.jep.app.fPaas.server;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.fPaaS1.core.IFPaaS1Handler;
import com.fPaaS1.core.Message;
import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.enums.DataSource;
import com.yzj.jep.app.enums.SourceModlue;
import com.yzj.jep.app.enums.SourceSys;
import com.yzj.jep.app.fil.model.RelTransactions;
import com.yzj.jep.app.fil.model.RelatedTranBaseInfoVo;
import com.yzj.jep.app.fil.model.RelatedTranMapperVo;
import com.yzj.jep.app.ins.constants.TranTypeConstant;
import com.yzj.jep.app.ins.enums.CurrencyTypeEnum;
import com.yzj.jep.app.ins.enums.RelatedPartyCategoryEnum;
import com.yzj.jep.app.ins.enums.YesOrNoEnum;
import com.yzj.jep.app.ins.model.*;
import com.yzj.jep.app.ins.service.*;
import com.yzj.jep.app.record.model.InterfaceRecord;
import com.yzj.jep.app.record.service.TraInterfaceRecordService;
import com.yzj.jep.app.util.*;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;

import static com.yzj.jep.app.util.CheckEntityFiledEmpty.isObjectFieldEmpty;
import static java.net.URLDecoder.decode;

/**
 * 关联交易数据接口 T+1
 *
 * @author duanliqiang
 */
public class RelatedPartyTransactions implements IFPaaS1Handler<Message> {
    private static final Logger logger = LoggerFactory.getLogger(RelatedPartyTransactions.class);
    com.yzj.jep.app.ins.service.TraServiceService traServiceService;
    TraFundsService traFundsService;
    RelPsnService relPsnService;
    RelOrgService relOrgService;
    TraInterestService traInterestService;
    TraBusinessService traBusinessService;
    UnSortOutService unSortOutService;
    EmailSend emailSend;
    TraInterfaceRecordService traInterfaceRecordService;
    /**
     * 关联方类别 1-法人、2-自然人
     */
    public static final String REL_TYPE1 = "1";

    @Override
//    @Transactional
    public Message handle(Message message) throws Exception {

        //提供服务类
        traServiceService = SpringContextHolder.getBean(TraServiceService.class);
        //资金运用类
        traFundsService = SpringContextHolder.getBean(TraFundsService.class);
        //利益转移
        traInterestService = SpringContextHolder.getBean(TraInterestService.class);
        //保险业务类
        traBusinessService = SpringContextHolder.getBean(TraBusinessService.class);
        unSortOutService = SpringContextHolder.getBean(UnSortOutService.class);
        relOrgService = SpringContextHolder.getBean(RelOrgService.class);
        relPsnService = SpringContextHolder.getBean(RelPsnService.class);
        emailSend = SpringContextHolder.getBean(EmailSend.class);
        traInterfaceRecordService = SpringContextHolder.getBean(TraInterfaceRecordService.class);
        Map<String, Object> tips = new HashMap<>(8);
        Map<String, Object> map = (Map<String, Object>) message.getTarget().getBodys();

        if (null == map) {
            returnData(tips, message, "接收的数据为空", "", "1001");
            return message;
        }

        List<FailTransList> list = new ArrayList<>();
        List<String> allFailList = new ArrayList<>();
        /**将所有的数据主键保存到错误列表中*/
        try {
            JSONObject dataJson = new JSONObject(map);
            //传入参数
            String strInfo = JSON.toJSONString(map);
            logger.info(String.format("关联交易数据获取数据为【%s】", strInfo));
            String str = dataJson.getString("relTransactions");

            List<RelTransactions> tradList = JSON.parseArray(str, RelTransactions.class);
            tradList.forEach(tra -> allFailList.add(tra.getSourceSysCode()));
            /* 返回信息 */
            String chnlId = map.get("chnlId").toString();
            for (RelTransactions relTransaction : tradList) {
                String relType = REL_TYPE1.equals(relTransaction.getRelType()) ? "ORG" : "PSN";
                relTransaction.setRelType(relType);
                String relName = decode(relTransaction.getRelName(), "utf-8");
                String relNumber = decode(relTransaction.getRelNumber(), "utf-8");
                logger.info(String.format("relName =【%s】", relName));
                logger.info(String.format("relNumber =【%s】", relNumber));
                relTransaction.setRelName(relName);
                relTransaction.setRelNumber(relNumber);
                relTransaction.setGroupNature("团险".equals(relTransaction.getGroupNature()) ? "1" : "2");
                relTransaction.setCurrencyType(CurrencyTypeEnum.getSysCode(relTransaction.getCurrencyType()));
                // 数据验证
                boolean checkResultFlag = judgeTranData(relTransaction, tips, message, chnlId);
                if (checkResultFlag) {
                    list = buildFailTransList(allFailList);
                    tips.put("failTransList", list);
                    return message;
                }
                //交易类型
                String tranType = relTransaction.getTranType();
                // 金额转换
                relTransaction.setRelTrandeAmount(relTransaction.getRelTrandeAmount().divide(new BigDecimal("10000"), 4, RoundingMode.HALF_UP));
                if (relTransaction.getOriginalCurrency() != null) {
                    relTransaction.setOriginalCurrency(relTransaction.getOriginalCurrency().divide(new BigDecimal("10000"), 4, RoundingMode.HALF_UP));
                }

                // 组装查询是否重大关联交易数据
                RelatedTranBaseInfoVo tranBaseInfoVo = new RelatedTranBaseInfoVo();
                BeanUtils.copyProperties(relTransaction, tranBaseInfoVo);
                // 如果是服务类或者保险业务及其他类需要默认是否统一交易协议为否0
                if (TranTypeConstant.TRAN_TYPE_2.equals(tranType) || TranTypeConstant.TRAN_TYPE_4.equals(tranType)) {
                    tranBaseInfoVo.setIsAgreement("0");
                }
                RelatedTranMapperVo tranMapperVo = JudgeTranType.assemblyData(tranBaseInfoVo, getRelId(relTransaction));
                try {
                    if (StringUtils.isEmpty(tranType)){
                        String contractNo = relTransaction.getContractNo();
                        if (StringUtils.isEmpty(contractNo)){
                            logger.info("======================未归类===============================>" + dataJson);
                            unSortOutType(relTransaction, relType);
                            allFailList.remove(relTransaction.getSourceSysCode());
                            continue;
                        }
                        List<TraFunds> contractNo1 = traFundsService.isContractNo(contractNo.trim());
                        if (contractNo1.size()>0){
                            String tranType1 = contractNo1.get(0).getTranType();
                            /* 数据保存 */
                            switch (tranType1) {
                                case TranTypeConstant.TRAN_TYPE_1:
                                    logger.info("======================资金运用类===========================>" + dataJson);
                                    traFundsType(relTransaction, tranMapperVo, relType);
                                    break;
                                case TranTypeConstant.TRAN_TYPE_2:
                                    logger.info("======================服务类==============================>" + dataJson);
                                    traServiceType(relTransaction, tranMapperVo, relType);
                                    break;
                                case TranTypeConstant.TRAN_TYPE_3:
                                    logger.info("======================利益转移类============================>" + dataJson);
                                    traInterestType(relTransaction, tranMapperVo, relType);
                                    break;
                                case TranTypeConstant.TRAN_TYPE_4:
                                    logger.info("======================保险业务和其他类=======================>" + dataJson);
                                    traBusinessType(relTransaction, tranMapperVo, relType);
                                    break;
                                default:
                                    logger.info("======================未归类===============================>" + dataJson);
                                    unSortOutType(relTransaction, relType);
                                    break;
                            }
                            //成功之后将数据移除
                            allFailList.remove(relTransaction.getSourceSysCode());
                        }else {
                            logger.info("======================未归类===============================>" + dataJson);
                            unSortOutType(relTransaction, relType);
                            allFailList.remove(relTransaction.getSourceSysCode());
                        }
                    }else {
                        /* 数据保存 */
                        switch (tranType) {
                            case TranTypeConstant.TRAN_TYPE_1:
                                logger.info("======================资金运用类===========================>" + dataJson);
                                traFundsType(relTransaction, tranMapperVo, relType);
                                break;
                            case TranTypeConstant.TRAN_TYPE_2:
                                logger.info("======================服务类==============================>" + dataJson);
                                traServiceType(relTransaction, tranMapperVo, relType);
                                break;
                            case TranTypeConstant.TRAN_TYPE_3:
                                logger.info("======================利益转移类============================>" + dataJson);
                                traInterestType(relTransaction, tranMapperVo, relType);
                                break;
                            case TranTypeConstant.TRAN_TYPE_4:
                                logger.info("======================保险业务和其他类=======================>" + dataJson);
                                traBusinessType(relTransaction, tranMapperVo, relType);
                                break;
                            default:
                                logger.info("======================未归类===============================>" + dataJson);
                                unSortOutType(relTransaction, relType);
                                break;
                        }
                        //成功之后将数据移除
                        allFailList.remove(relTransaction.getSourceSysCode());
                    }

                } catch (Exception e) {
                    logger.error("接口数据保存异常：" , e);
                    /*failTransList = new FailTransList();
                    failTransList.setSourceSysCode(relTransaction.getSourceSysCode());
                    list.add(failTransList);*/
                }
            }
            list = buildFailTransList(allFailList);
            tips.put("failTransList", list);
            returnData(tips, message, "成功", chnlId, "0000");

        } catch (NullPointerException e) {
            logger.error(String.format("NullPointerException message is【%s】", e.getMessage()));
            logger.error("交易异常：", e);
            list = buildFailTransList(allFailList);
            tips.put("resultCode", "1001");
            tips.put("failTransList", list);
            tips.put("msg", e.getMessage());
        } catch (JSONException e) {
            logger.error(String.format("JSONException message is【%s】", e.getMessage()));
            logger.error("交易异常：", e);
            list = buildFailTransList(allFailList);
            tips.put("resultCode", "1001");
            tips.put("failTransList", list);
            tips.put("resultCode", "1001");
            tips.put("msg", "Json数据转换异常！");
        } catch (Exception e) {
            logger.error(String.format("Exception message is【%s】", e.getMessage()));
            logger.error("交易异常：", e);
            tips.put("resultCode", "9999");
            tips.put("msg", "接口异常！");
        } finally {
            interFaceRecord(tips,map);
            message.getTarget().setBody(tips);
        }
        return message;
    }

    /**
     * 处理失败数据主键
     * @param list
     * @return
     */
    private List<FailTransList> buildFailTransList(List<String> list) {
        List<FailTransList> failList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(list)) {
            for (String code : list) {
                FailTransList failTransList = new FailTransList();
                failTransList.setSourceSysCode(code);
                failList.add(failTransList);
            }
        }
        return failList;
    }
    public String future(List<Email> list) {
        ExecutorService exec = Executors.newSingleThreadExecutor();
        String s = "0";
        Callable<String> call = () -> {
            emailSend.mojorEmail(list, "8");
            return "OK";
        };
        Future<String> future = exec.submit(call);
        try {
            future.get(1000 * 3, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            s = "1";
        } finally {
            exec.shutdown();
        }
        return s;
    }

    /**
     * 来源系统
     *
     * @param sourceSysName 系统标识
     * @return 系统中文名
     */
    private String sourceSysType(String sourceSysName) {
        String sourceSysType = null;
        try {
            if (SourceSys.SOURCE_SYS_TYPE_01.getCategory().equals(sourceSysName)) {
                sourceSysType = SourceSys.SOURCE_SYS_TYPE_01.getCategoryName();
                // TODO 20250321 关闭个团险邮件提醒
                /*List<Email> list = emailSend.selectPerpleYy();
                future(list);*/
            } else if (SourceSys.SOURCE_SYS_TYPE_02.getCategory().equals(sourceSysName)) {
                sourceSysType = SourceSys.SOURCE_SYS_TYPE_02.getCategoryName();
                // TODO 20250321 关闭个团险邮件提醒
                /*List<Email> list = emailSend.selectPerpleYy();
                future(list);*/
            } else if (SourceSys.SOURCE_SYS_TYPE_03.getCategory().equals(sourceSysName)) {
                sourceSysType = SourceSys.SOURCE_SYS_TYPE_03.getCategoryName();
            } else if (SourceSys.SOURCE_SYS_TYPE_04.getCategory().equals(sourceSysName)) {
                sourceSysType = SourceSys.SOURCE_SYS_TYPE_04.getCategoryName();
            } else if (SourceSys.SOURCE_SYS_TYPE_05.getCategory().equals(sourceSysName)) {
                sourceSysType = SourceSys.SOURCE_SYS_TYPE_05.getCategoryName();
            }
        } catch (Exception e) {
            logger.error("发送邮件失败：" + e);
        }
        return sourceSysType;
    }

    //来源模块
    private String sourceModlueType(String sourceModlue) {
        String sourceModlueType = null;
        if (SourceModlue.SOURCE_MODLUE_01.getCategory().equals(sourceModlue)) {
            sourceModlueType = SourceModlue.SOURCE_MODLUE_01.getCategoryName();
        } else if (SourceModlue.SOURCE_MODLUE_02.getCategory().equals(sourceModlue)) {
            sourceModlueType = SourceModlue.SOURCE_MODLUE_02.getCategoryName();
        } else if (SourceModlue.SOURCE_MODLUE_03.getCategory().equals(sourceModlue)) {
            sourceModlueType = SourceModlue.SOURCE_MODLUE_03.getCategoryName();
        } else if (SourceModlue.SOURCE_MODLUE_04.getCategory().equals(sourceModlue)) {
            sourceModlueType = SourceModlue.SOURCE_MODLUE_04.getCategoryName();
        } else if (SourceModlue.SOURCE_MODLUE_05.getCategory().equals(sourceModlue)) {
            sourceModlueType = SourceModlue.SOURCE_MODLUE_05.getCategoryName();
        } else if (SourceModlue.SOURCE_MODLUE_06.getCategory().equals(sourceModlue)) {
            sourceModlueType = SourceModlue.SOURCE_MODLUE_06.getCategoryName();
        }
        return sourceModlueType;
    }


    /**
     * 未归类数据保存
     *
     * @param relTransactions 接口数据
     * @param relType         关联方类型
     */
    public void unSortOutType(RelTransactions relTransactions, String relType) {
        //关联交易金额
        BigDecimal relTrandeAmount = relTransactions.getRelTrandeAmount();
        Date contractDate = relTransactions.getContractDate();
        UnSortOut unSortOutNew = new UnSortOut();
        BeanUtils.copyProperties(relTransactions, unSortOutNew);
        unSortOutNew.setAmendName("System");
        unSortOutNew.setCreateTime(new Date());
        unSortOutNew.setCollectionDate(new Date());
        unSortOutNew.setTranDate(contractDate);
        unSortOutNew.setRelTradeAmount(relTrandeAmount);
        unSortOutNew.setTradTime(relTransactions.getTranDate());
        unSortOutNew.setDataSources(DataSource.getCode(relTransactions.getSourceSysName()));
        String sourceSysType = this.sourceSysType(relTransactions.getSourceSysName());
//        String sourceModlueType = this.sourceModlueType(relTransactions.getSourceSysModule());

        unSortOutNew.setSourceSystem(sourceSysType);
        unSortOutNew.setSourceModule(relTransactions.getSourceSysModule());
        unSortOutNew.setUniqueCode(relTransactions.getSourceSysCode());
        unSortOutNew.setPaymentId(relTransactions.getPaymentMark());
        unSortOutNew.setPracTradeAmount(relTrandeAmount.toString());
        unSortOutNew.setRelType(relType);
        unSortOutNew.setRelId(getRelId(relTransactions));
        unSortOutNew.setModifiedTime(new Date());
        //新增一条数据
        unSortOutService.insertSelective(unSortOutNew);
    }

    /**
     * 保险业务及其他类数据保存或者新增
     *
     * @param relTransactions 接口数据
     * @param tranMapperVo    重大信息
     * @param relType         关联方类型
     */
    public void traBusinessType(RelTransactions relTransactions, RelatedTranMapperVo tranMapperVo, String relType) {
        //关联交易金额
        BigDecimal relTrandeAmount = relTransactions.getRelTrandeAmount();
        //合同编号
        String contractNo = relTransactions.getContractNo();
        //合同签署日期
        Date contractDate = relTransactions.getContractDate();
        String sourceSysCode = relTransactions.getSourceSysCode();
        String sourceSysType = this.sourceSysType(relTransactions.getSourceSysName());
        String sourceModlueType = this.sourceModlueType(relTransactions.getSourceSysModule());
        TraBusiness traBusinessNew = new TraBusiness();
        String tradeNunber = BatchNumberUtil.makeBatchNumber();

        List<Filter> filter = new Filter.Builder()
                .eq("contractNo", contractNo)
                .eq("dataVersion", "E2.0")
//                .eq("relNumber", relTransactions.getRelNumber())
//                .ne("status", "9")
                .eq("delflag", "1").build();

        List<TraBusiness> traBusinessList = traBusinessService.select(filter);
        if (traBusinessList != null && !traBusinessList.isEmpty()) {
            TraBusiness traBusinessNo = traBusinessService.traBusinessNo(contractNo, null);
            if (traBusinessNo != null) {
                //id
                Integer pid = traBusinessNo.getPid();
                traBusinessNew.setAmendName("System");
                traBusinessNew.setModifiedTime(new Date());
                traBusinessNew.setPid(pid);
                if (ObjectUtil.isEmpty(traBusinessNo.getBusStartTime())) {
                    traBusinessNew.setBusStartTime(contractDate);
                }
                if (ObjectUtil.isEmpty(traBusinessNo.getContractDate())) {
                    traBusinessNew.setContractDate(contractDate);
                }
                // 交易日期
                traBusinessNew.setTranDate(relTransactions.getTranDate());
                // 实际交易金额
                traBusinessNew.setPracTradeAmount(relTrandeAmount.toString());
                traBusinessNew.setStatus("6");
                traBusinessNew.setPaymentId(relTransactions.getPaymentMark());
                traBusinessNew.setEffectOrPay(relTransactions.getEffectOrPay());
                traBusinessNew.setFundsFlowNumber(relTransactions.getTranAmountTranRecordNumber());
                traBusinessNew.setCurrencyCode(relTransactions.getCurrencyType());
                traBusinessNew.setOriginalCurrencyAmount(relTransactions.getOriginalCurrency());
                traBusinessNew.setBusStartTime(DateUtil.parse(relTransactions.getBusStartTime()));
                traBusinessNew.setBusEndTime(DateUtil.parse(relTransactions.getBusEndTime()));
                traBusinessNew.setNumberOfPolicy(relTransactions.getPolicyNo());
                traBusinessNew.setGroupTheChar(relTransactions.getGroupNature());
                traBusinessNew.setModifiedTime(new Date());
                traBusinessService.updateSelective(traBusinessNew);

            } else {
                TraBusiness getTraBusiness = traBusinessList.get(0);
                BeanUtils.copyProperties(getTraBusiness, traBusinessNew);

                traBusinessNew.setPid(null);
                //交易日期
                traBusinessNew.setBusStartTime(contractDate);
                traBusinessNew.setContractDate(contractDate);
                traBusinessNew.setTranDate(relTransactions.getTranDate());

                // 实际交易金额
                traBusinessNew.setPracTradeAmount(relTrandeAmount.toString());
                traBusinessNew.setRelTradeAmount(relTrandeAmount);
                //从新生成交易编号
                traBusinessNew.setTransNum(tradeNunber);
                traBusinessNew.setAmendName("System");
                traBusinessNew.setCreateTime(new Date());
                traBusinessNew.setSourceModule(sourceModlueType);
                traBusinessNew.setSourceSystem(sourceSysType);
                traBusinessNew.setUniqueCode(sourceSysCode);
                traBusinessNew.setPaymentId(relTransactions.getPaymentMark());
                traBusinessNew.setEffectOrPay(relTransactions.getEffectOrPay());
                traBusinessNew.setFundsFlowNumber(relTransactions.getTranAmountTranRecordNumber());
                traBusinessNew.setCurrencyCode(relTransactions.getCurrencyType());
                traBusinessNew.setOriginalCurrencyAmount(relTransactions.getOriginalCurrency());
                traBusinessNew.setBusStartTime(DateUtil.parse(relTransactions.getBusStartTime()));
                traBusinessNew.setBusEndTime(DateUtil.parse(relTransactions.getBusEndTime()));
                traBusinessNew.setNumberOfPolicy(relTransactions.getPolicyNo());
                traBusinessNew.setGroupTheChar(relTransactions.getGroupNature());
                traBusinessNew.setModifiedTime(new Date());
                // 赋值默认值
                setDefaultValue(traBusinessNew, relTransactions, sourceModlueType, sourceSysType);
                //新增一条数据
                traBusinessService.insertSelective(traBusinessNew);
            }
        } else {
            BeanUtils.copyProperties(relTransactions, traBusinessNew);
            // 把查询是否重大关联交易结果赋值到相应实体类
            BeanUtils.copyProperties(tranMapperVo, traBusinessNew);
            traBusinessNew.setPid(null);
            traBusinessNew.setPaymentId(relTransactions.getPaymentMark());
            traBusinessNew.setStatus("1");
            traBusinessNew.setIsAgreement("0");
            traBusinessNew.setAmendName("System");
            traBusinessNew.setCreateTime(new Date());
            traBusinessNew.setTransNum(tradeNunber);
            traBusinessNew.setRelTradeAmount(relTrandeAmount);
            traBusinessNew.setPracTradeAmount(relTrandeAmount.toString());
            traBusinessNew.setSourceModule(sourceModlueType);
            traBusinessNew.setSourceSystem(sourceSysType);
            traBusinessNew.setUniqueCode(sourceSysCode);
            traBusinessNew.setDelflag("1");
            traBusinessNew.setRelType(relType);
            traBusinessNew.setDataSources(DataSource.getCode(relTransactions.getSourceSysName()));
            traBusinessNew.setFundsFlowNumber(relTransactions.getTranAmountTranRecordNumber());
            traBusinessNew.setCurrencyCode(relTransactions.getCurrencyType());
            traBusinessNew.setOriginalCurrencyAmount(relTransactions.getOriginalCurrency());
            traBusinessNew.setBusStartTime(DateUtil.parse(relTransactions.getBusStartTime()));
            traBusinessNew.setBusEndTime(DateUtil.parse(relTransactions.getBusEndTime()));
            traBusinessNew.setNumberOfPolicy(relTransactions.getPolicyNo());
            traBusinessNew.setGroupTheChar(relTransactions.getGroupNature());
            traBusinessNew.setModifiedTime(new Date());
            // 赋值默认值
            setDefaultValue(traBusinessNew, relTransactions, sourceModlueType, sourceSysType);
            traBusinessService.insertSelective(traBusinessNew);
        }
    }

    /**
     * 资金运用类数据保存或者新增
     *
     * @param relTransactions 接口数据
     * @param tranMapperVo    重大信息
     * @param relType         关联方类型
     */
    public void traFundsType(RelTransactions relTransactions, RelatedTranMapperVo tranMapperVo, String relType) {
        //关联交易金额
        BigDecimal relTrandeAmount = relTransactions.getRelTrandeAmount();
        //合同编号
        String contractNo = relTransactions.getContractNo();
        //合同签署日期
        Date contractDate = relTransactions.getContractDate();

        String sourceSysCode = relTransactions.getSourceSysCode();
        String sourceSysType = this.sourceSysType(relTransactions.getSourceSysName());
        String sourceModlueType = this.sourceModlueType(relTransactions.getSourceSysModule());
        TraFunds traFundsNew = new TraFunds();

        String tradeNunber = BatchNumberUtil.makeBatchNumber();

        List<Filter> filter = new Filter.Builder()
                .eq("contractNo", contractNo)
                .eq("dataVersion", "E2.0")
//                .eq("relNumber", relTransactions.getRelNumber())
//                .ne("status", "9")
                .eq("delflag", "1").build();

        List<TraFunds> listTraFunds = traFundsService.select(filter);
        if (listTraFunds != null && !listTraFunds.isEmpty()) {
            TraFunds traFundsNo = traFundsService.traContractNo(contractNo, null);
            if (traFundsNo != null) {
                //id
                Integer pid = traFundsNo.getPid();
                traFundsNew.setAmendName("System");
                traFundsNew.setModifiedTime(new Date());
                traFundsNew.setPid(pid);
                if (ObjectUtil.isEmpty(traFundsNew.getBusStartTime())) {
                    traFundsNew.setBusStartTime(contractDate);
                }
                if (ObjectUtil.isEmpty(traFundsNew.getTranDate())) {
                    traFundsNew.setTranDate(contractDate);
                }
                // 交易日期
                traFundsNew.setTradTime(relTransactions.getTranDate());
                // 实际交易金额
                traFundsNew.setPracTradeAmount(relTrandeAmount.toString());
                traFundsNew.setStatus("6");
                traFundsNew.setPaymentId(relTransactions.getPaymentMark());
                traFundsNew.setEffectOrPay(relTransactions.getEffectOrPay());
                traFundsNew.setRelType(relType);
                traFundsNew.setFundsFlowNumber(relTransactions.getTranAmountTranRecordNumber());
                traFundsNew.setCurrencyCode(relTransactions.getCurrencyType());
                traFundsNew.setOriginalCurrencyAmount(relTransactions.getOriginalCurrency());
                traFundsNew.setBusStartTime(DateUtil.parse(relTransactions.getBusStartTime()));
                traFundsNew.setBusEndTime(DateUtil.parse(relTransactions.getBusEndTime()));
                traFundsNew.setModifiedTime(new Date());
                traFundsService.updateSelective(traFundsNew);
            } else {
                TraFunds getTraFunds = listTraFunds.get(0);
                BeanUtils.copyProperties(getTraFunds, traFundsNew);

                traFundsNew.setPid(null);
                traFundsNew.setBusStartTime(contractDate);
                // 交易日期
                traFundsNew.setTradTime(relTransactions.getTranDate());
                // 交易日期
                traFundsNew.setTranDate(contractDate);
                // 实际交易金额
                traFundsNew.setPracTradeAmount(relTrandeAmount.toString());
                //从新生成交易编号
                traFundsNew.setTransNum(tradeNunber);
                traFundsNew.setAmendName("System");
                traFundsNew.setCreateTime(new Date());
                traFundsNew.setSourceModule(sourceModlueType);
                traFundsNew.setSourceSystem(sourceSysType);
                traFundsNew.setUniqueCode(sourceSysCode);
                traFundsNew.setPaymentId(relTransactions.getPaymentMark());
                traFundsNew.setEffectOrPay(relTransactions.getEffectOrPay());
                traFundsNew.setFundsFlowNumber(relTransactions.getTranAmountTranRecordNumber());
                traFundsNew.setCurrencyCode(relTransactions.getCurrencyType());
                traFundsNew.setOriginalCurrencyAmount(relTransactions.getOriginalCurrency());
                traFundsNew.setBusStartTime(DateUtil.parse(relTransactions.getBusStartTime()));
                traFundsNew.setBusEndTime(DateUtil.parse(relTransactions.getBusEndTime()));
                traFundsNew.setModifiedTime(new Date());
                //新增一条数据
                traFundsService.insertSelective(traFundsNew);
            }
        } else {
            BeanUtils.copyProperties(relTransactions, traFundsNew);
            // 把查询是否重大关联交易结果赋值到相应实体类
            BeanUtils.copyProperties(tranMapperVo, traFundsNew);
            traFundsNew.setPid(null);
            // 交易日期
            traFundsNew.setTranDate(contractDate);
            traFundsNew.setTradTime(relTransactions.getTranDate());
            traFundsNew.setPaymentId(relTransactions.getPaymentMark());
            traFundsNew.setStatus("1");
            traFundsNew.setAmendName("System");
            traFundsNew.setCreateTime(new Date());
            traFundsNew.setTransNum(tradeNunber);
            traFundsNew.setRelTradeAmount(relTrandeAmount);
            traFundsNew.setPracTradeAmount(relTrandeAmount.toString());
            traFundsNew.setSourceModule(sourceModlueType);
            traFundsNew.setSourceSystem(sourceSysType);
            traFundsNew.setUniqueCode(sourceSysCode);
            traFundsNew.setDelflag("1");
            traFundsNew.setIsHoldingSubsidiary("0");
            traFundsNew.setRelType(relType);
            traFundsNew.setDataSources(DataSource.getCode(relTransactions.getSourceSysName()));
            traFundsNew.setFundsFlowNumber(relTransactions.getTranAmountTranRecordNumber());
            traFundsNew.setCurrencyCode(relTransactions.getCurrencyType());
            traFundsNew.setOriginalCurrencyAmount(relTransactions.getOriginalCurrency());
            traFundsNew.setBusStartTime(DateUtil.parse(relTransactions.getBusStartTime()));
            traFundsNew.setBusEndTime(DateUtil.parse(relTransactions.getBusEndTime()));
            traFundsNew.setModifiedTime(new Date());
            traFundsService.insertSelective(traFundsNew);
        }
    }

    /**
     * 利益转移类数据保存或者新增
     *
     * @param relTransactions 接口数据
     * @param tranMapperVo    重大信息
     * @param relType         关联方类型
     */
    public void traInterestType(RelTransactions relTransactions, RelatedTranMapperVo tranMapperVo, String relType) {
        //关联交易金额
        BigDecimal relTrandeAmount = relTransactions.getRelTrandeAmount();
        //合同编号
        String contractNo = relTransactions.getContractNo();
        //合同签署日期
        Date contractDate = relTransactions.getContractDate();

        String sourceSysCode = relTransactions.getSourceSysCode();
        String sourceSysType = this.sourceSysType(relTransactions.getSourceSysName());
        String sourceModlueType = this.sourceModlueType(relTransactions.getSourceSysModule());
        TraInterest traInterestNew = new TraInterest();
        String tradeNunber = BatchNumberUtil.makeBatchNumber();

        List<Filter> filter = new Filter.Builder()
                .eq("contractNo", contractNo)
                .eq("dataVersion", "E2.0")
//                .eq("relNumber", relTransactions.getRelNumber())
//                .ne("status", "9")
                .eq("delflag", "1").build();

        List<TraInterest> traInterestList = traInterestService.select(filter);
        if (traInterestList != null && !traInterestList.isEmpty()) {
            TraInterest traInterestNo = traInterestService.traContractNo(contractNo, null);
            if (traInterestNo != null) {
                //id
                Integer pid = traInterestNo.getPid();
                traInterestNew.setAmendName("System");
                traInterestNew.setModifiedTime(new Date());
                traInterestNew.setPid(pid);
                if (ObjectUtil.isEmpty(traInterestNew.getBusStartTime())) {
                    traInterestNew.setBusStartTime(contractDate);
                }
                if (ObjectUtil.isEmpty(traInterestNew.getContractDate())) {
                    traInterestNew.setContractDate(contractDate);
                }
                // 交易日期
                traInterestNew.setTranDate(relTransactions.getTranDate());
                // 实际交易金额
                traInterestNew.setPracTradeAmount(relTrandeAmount.toString());
                traInterestNew.setStatus("6");
                traInterestNew.setPaymentId(relTransactions.getPaymentMark());
                traInterestNew.setEffectOrPay(relTransactions.getEffectOrPay());
                traInterestNew.setContractDate(contractDate);
                traInterestNew.setFundsFlowNumber(relTransactions.getTranAmountTranRecordNumber());
                traInterestNew.setCurrencyCode(relTransactions.getCurrencyType());
                traInterestNew.setOriginalCurrencyAmount(relTransactions.getOriginalCurrency());
                traInterestNew.setBusStartTime(DateUtil.parse(relTransactions.getBusStartTime()));
                traInterestNew.setBusEndTime(DateUtil.parse(relTransactions.getBusEndTime()));
                traInterestNew.setModifiedTime(new Date());
                traInterestService.updateSelective(traInterestNew);
            } else {
                TraInterest traInterest = traInterestList.get(0);
                BeanUtils.copyProperties(traInterest, traInterestNew);
                traInterestNew.setPid(null);
                // 合同签署日期和业务起始日期
                traInterestNew.setBusStartTime(contractDate);
                traInterestNew.setContractDate(contractDate);
                // 交易日期
                traInterestNew.setTranDate(relTransactions.getTranDate());
                // 实际交易金额
                traInterestNew.setPracTradeAmount(relTrandeAmount.toString());
                //从新生成交易编号
                traInterestNew.setTransNum(tradeNunber);
                traInterestNew.setAmendName("System");
                traInterestNew.setCreateTime(new Date());
                traInterestNew.setSourceModule(sourceModlueType);
                traInterestNew.setSourceSystem(sourceSysType);
                traInterestNew.setUniqueCode(sourceSysCode);
                traInterestNew.setPaymentId(relTransactions.getPaymentMark());
                traInterestNew.setEffectOrPay(relTransactions.getEffectOrPay());
                traInterestNew.setContractDate(contractDate);
                traInterestNew.setFundsFlowNumber(relTransactions.getTranAmountTranRecordNumber());
                traInterestNew.setCurrencyCode(relTransactions.getCurrencyType());
                traInterestNew.setOriginalCurrencyAmount(relTransactions.getOriginalCurrency());
                traInterestNew.setBusStartTime(DateUtil.parse(relTransactions.getBusStartTime()));
                traInterestNew.setBusEndTime(DateUtil.parse(relTransactions.getBusEndTime()));
                traInterestNew.setModifiedTime(new Date());
                //新增一条数据
                traInterestService.insertSelective(traInterestNew);
            }
        } else {
            BeanUtils.copyProperties(relTransactions, traInterestNew);
            // 把查询是否重大关联交易结果赋值到相应实体类
            BeanUtils.copyProperties(tranMapperVo, traInterestNew);
            traInterestNew.setPid(null);
            traInterestNew.setPaymentId(relTransactions.getPaymentMark());
            traInterestNew.setStatus("1");
            traInterestNew.setAmendName("System");
            traInterestNew.setCreateTime(new Date());
            traInterestNew.setTransNum(tradeNunber);
            traInterestNew.setRelTradeAmount(relTrandeAmount);
            traInterestNew.setPracTradeAmount(relTrandeAmount.toString());
            traInterestNew.setSourceModule(sourceModlueType);
            traInterestNew.setSourceSystem(sourceSysType);
            traInterestNew.setUniqueCode(sourceSysCode);
            traInterestNew.setDelflag("1");
            traInterestNew.setRelType(relType);
            traInterestNew.setDataSources(DataSource.getCode(relTransactions.getSourceSysName()));
            traInterestNew.setFundsFlowNumber(relTransactions.getTranAmountTranRecordNumber());
            traInterestNew.setCurrencyCode(relTransactions.getCurrencyType());
            traInterestNew.setOriginalCurrencyAmount(relTransactions.getOriginalCurrency());
            traInterestNew.setBusStartTime(DateUtil.parse(relTransactions.getBusStartTime()));
            traInterestNew.setBusEndTime(DateUtil.parse(relTransactions.getBusEndTime()));
            traInterestNew.setModifiedTime(new Date());
            traInterestService.insertSelective(traInterestNew);
        }
    }

    /**
     * 获取关联方ID
     *
     * @param relTransactions 接口数据
     * @return 返回关联方ID
     */
    public Integer getRelId(RelTransactions relTransactions) {
        Integer relId = null;
        if (RelatedPartyCategoryEnum.LEGALPERSON.getSysCode().equals(relTransactions.getRelType())) {
            List<Filter> filter = new Filter.Builder()
                    .eq("identifyNumber", relTransactions.getRelNumber())
                    .eq("delflag", "1").build();
            List<RelPsn> psns = relPsnService.select(filter);
            if (CollectionUtils.isNotEmpty(psns)) {
                relId = psns.get(0).getPid();
            }
        } else if (RelatedPartyCategoryEnum.NATURALPERSON.getSysCode().equals(relTransactions.getRelType())) {
            List<Filter> filter = new Filter.Builder()
                    .eq("uniscCode", relTransactions.getRelNumber())
                    .eq("delflag", "1").build();
            List<RelOrg> orgs = relOrgService.select(filter);
            if (CollectionUtils.isNotEmpty(orgs)) {
                relId = orgs.get(0).getPid();
            }
        }
        return relId;
    }

    /**
     * 服务类数据保存或者新增
     *
     * @param relTransactions 接口数据
     * @param tranMapperVo    重大信息
     * @param relType         关联方类型
     */
    public void traServiceType(RelTransactions relTransactions, RelatedTranMapperVo tranMapperVo, String relType) {
        //关联交易金额
        BigDecimal relTrandeAmount = relTransactions.getRelTrandeAmount();
        //合同编号
        String contractNo = relTransactions.getContractNo();
        //合同签署日期
        Date contractDate = relTransactions.getContractDate();
        String sourceSysCode = relTransactions.getSourceSysCode();
        String sourceSysType = this.sourceSysType(relTransactions.getSourceSysName());
        String sourceModlueType = this.sourceModlueType(relTransactions.getSourceSysModule());
        TraService traServiceNew = new TraService();
        String tradeNunber = BatchNumberUtil.makeBatchNumber();

        List<Filter> filter = new Filter.Builder()
                .eq("contractNo", contractNo)
                .eq("dataVersion", "E2.0")
//                .eq("relNumber", relTransactions.getRelNumber())
//                .ne("status", "9")
                .eq("delflag", "1").build();

        List<TraService> traServiceList = traServiceService.select(filter);
        if (traServiceList != null && !traServiceList.isEmpty()) {
            TraService traServiceNo = traServiceService.traServiceNo(contractNo, null);
            if (traServiceNo != null) {
                //id
                Integer pid = traServiceNo.getPid();
                traServiceNew.setAmendName("System");
                traServiceNew.setModifiedTime(new Date());
                traServiceNew.setPid(pid);
                if (ObjectUtil.isEmpty(traServiceNew.getBusStartTime())) {
                    traServiceNew.setBusStartTime(contractDate);
                }
                if (ObjectUtil.isEmpty(traServiceNew.getContractDate())) {
                    traServiceNew.setContractDate(contractDate);
                }
                // 交易日期
                traServiceNew.setTranDate(relTransactions.getTranDate());
                traServiceNew.setPracTradeAmount(relTrandeAmount.toString());
                traServiceNew.setPaymentId(relTransactions.getPaymentMark());
                traServiceNew.setEffectOrPay(relTransactions.getEffectOrPay());
                traServiceNew.setStatus("6");
                traServiceNew.setContractDate(contractDate);
                traServiceNew.setFundsFlowNumber(relTransactions.getTranAmountTranRecordNumber());
                traServiceNew.setCurrencyCode(relTransactions.getCurrencyType());
                traServiceNew.setOriginalCurrencyAmount(relTransactions.getOriginalCurrency());
                traServiceNew.setBusStartTime(DateUtil.parse(relTransactions.getBusStartTime()));
                traServiceNew.setBusEndTime(DateUtil.parse(relTransactions.getBusEndTime()));
                traServiceNew.setModifiedTime(new Date());
                traServiceService.updateSelective(traServiceNew);
            } else {
                TraService getTraService = traServiceList.get(0);
                BeanUtils.copyProperties(getTraService, traServiceNew);
                traServiceNew.setPid(null);
                //交易日期
                traServiceNew.setBusStartTime(contractDate);
                traServiceNew.setContractDate(contractDate);
                // 交易日期
                traServiceNew.setTranDate(relTransactions.getTranDate());
                // 实际交易金额
                traServiceNew.setPracTradeAmount(relTrandeAmount.toString());
                //从新生成交易编号
                traServiceNew.setTransNum(tradeNunber);
                traServiceNew.setAmendName("System");
                traServiceNew.setCreateTime(new Date());
                traServiceNew.setSourceModule(sourceModlueType);
                traServiceNew.setSourceSystem(sourceSysType);
                traServiceNew.setUniqueCode(sourceSysCode);
                traServiceNew.setPaymentId(relTransactions.getPaymentMark());
                traServiceNew.setEffectOrPay(relTransactions.getEffectOrPay());
                traServiceNew.setContractDate(contractDate);
                traServiceNew.setFundsFlowNumber(relTransactions.getTranAmountTranRecordNumber());
                traServiceNew.setCurrencyCode(relTransactions.getCurrencyType());
                traServiceNew.setOriginalCurrencyAmount(relTransactions.getOriginalCurrency());
                traServiceNew.setBusStartTime(DateUtil.parse(relTransactions.getBusStartTime()));
                traServiceNew.setBusEndTime(DateUtil.parse(relTransactions.getBusEndTime()));
                traServiceNew.setModifiedTime(new Date());
                //新增一条数据
                traServiceService.insertSelective(traServiceNew);

            }
        } else {
            BeanUtils.copyProperties(relTransactions, traServiceNew);
            // 把查询是否重大关联交易结果赋值到相应实体类
            BeanUtils.copyProperties(tranMapperVo, traServiceNew);
            traServiceNew.setPid(null);
            traServiceNew.setPaymentId(relTransactions.getPaymentMark());
            traServiceNew.setStatus("1");
            traServiceNew.setIsAgreement("0");
            traServiceNew.setAmendName("System");
            traServiceNew.setCreateTime(new Date());
            traServiceNew.setTransNum(tradeNunber);
            traServiceNew.setRelTradeAmount(relTrandeAmount);
            traServiceNew.setPracTradeAmount(relTrandeAmount.toString());
            traServiceNew.setSourceModule(sourceModlueType);
            traServiceNew.setSourceSystem(sourceSysType);
            traServiceNew.setUniqueCode(sourceSysCode);
            traServiceNew.setDelflag("1");
            traServiceNew.setRelType(relType);
            traServiceNew.setDataSources(DataSource.getCode(relTransactions.getSourceSysName()));
            traServiceNew.setFundsFlowNumber(relTransactions.getTranAmountTranRecordNumber());
            traServiceNew.setCurrencyCode(relTransactions.getCurrencyType());
            traServiceNew.setOriginalCurrencyAmount(relTransactions.getOriginalCurrency());
            traServiceNew.setBusStartTime(DateUtil.parse(relTransactions.getBusStartTime()));
            traServiceNew.setBusEndTime(DateUtil.parse(relTransactions.getBusEndTime()));
            traServiceNew.setModifiedTime(new Date());
            traServiceService.insertSelective(traServiceNew);
        }
    }

    /**
     * 返回非参数
     */
    private static void returnData(Map<String, Object> tips, Message message, String msg, String chnlId, String resultCode) {
        tips.put("msg", msg);
        tips.put("TRANS_CODE", "getRelTransactions");
        tips.put("resultCode", resultCode);
        tips.put("_TRAN_CODE", "getRelTransactions");
        if (CharSequenceUtil.isNotBlank(chnlId)) {
            tips.put("CHNL_ID", chnlId);
        }
        message.getTarget().setHeaders(tips);
        message.getTarget().setBody(tips);
    }

    /**
     * 判断交易数据是否符合录入标准
     *
     * @param relTransactions 接口数据
     * @param tips            返回的数据
     * @param message         message对象
     * @param chnlId          相应码
     * @return boolean                 返回Boolean值
     * @throws Exception 异常信息
     */
    private boolean judgeTranData(RelTransactions relTransactions, Map<String, Object> tips, Message message, String chnlId) throws Exception {
        // 校验必填数据是否为空
        StringBuilder sb = new StringBuilder();
        List<String> strings = isObjectFieldEmpty(relTransactions);
        TrimUtil.trimAllFields(relTransactions);
        if (CollUtil.isNotEmpty(strings)) {
            strings.stream().filter(
                    s ->    /* T+1接口条件必填活非必填字段 */
                            !"contractNo".equalsIgnoreCase(s)
                            && !"currencyType".equalsIgnoreCase(s)
                            && !"originalCurrency".equalsIgnoreCase(s)
                            && !"contractDate".equalsIgnoreCase(s)
                            && !"busStartTime".equalsIgnoreCase(s)
                            && !"busEndTime".equalsIgnoreCase(s)
                            && !"tranAmountTranRecordNumber".equalsIgnoreCase(s)
                            && !"tranType".equalsIgnoreCase(s)
                            && !"sourceSysModule".equalsIgnoreCase(s)
                            && !"groupNature".equalsIgnoreCase(s)
                            && !"policyNo".equalsIgnoreCase(s)
                            /* 排除其他接口字段 */
                            && !"isTradeAgreement".equalsIgnoreCase(s)
                            && !"tradeAgreementType".equalsIgnoreCase(s)
                            && !"pricingPolicy".equalsIgnoreCase(s)
            ).forEach(str -> sb.append(str).append(","));
        }
        if (CharSequenceUtil.isNotBlank(sb)) {
            String msg = sb.append("字段为空！").toString();
            returnData(tips, message, msg, chnlId, "1001");
            return true;
        }
        /* 个团核心必填字段校验 */
        if (SourceSys.SOURCE_SYS_TYPE_01.getCategory().equals(relTransactions.getSourceSysName())
                || (SourceSys.SOURCE_SYS_TYPE_02.getCategory().equals(relTransactions.getSourceSysName()))) {
            if (CharSequenceUtil.isBlank(relTransactions.getContractNo())) {
                sb.append("contractNo");
            }
            if (ObjectUtil.isEmpty(relTransactions.getContractDate())) {
                sb.append("contractDate");
            }
            if (ObjectUtil.isEmpty(relTransactions.getSourceSysModule())) {
                sb.append("sourceSysModule");
            }
            if (ObjectUtil.isEmpty(relTransactions.getGroupNature())) {
                sb.append("groupNature");
            }
            if (ObjectUtil.isEmpty(relTransactions.getPolicyNo())) {
                sb.append("policyNo");
            }
        }

        /* 来源模块为1-新保、5-续保时必填 */
        if (StringUtils.isNotEmpty(relTransactions.getSourceSysModule())) {
            if (SourceModlue.SOURCE_MODLUE_01.getCategory().equals(relTransactions.getSourceSysModule())
                    || SourceModlue.SOURCE_MODLUE_05.getCategory().equals(relTransactions.getSourceSysModule())) {
                if (ObjectUtil.isEmpty(relTransactions.getCurrencyType())) {
                    sb.append("currencyType").append(",");
                }
                if (ObjectUtil.isEmpty(relTransactions.getOriginalCurrency())) {
                    sb.append("originalCurrency").append(",");
                }
                if (ObjectUtil.isEmpty(relTransactions.getBusStartTime())) {
                    sb.append("busStartTime").append(",");
                }
                if (ObjectUtil.isEmpty(relTransactions.getBusEndTime())) {
                    sb.append("busEndTime").append(",");
                }
                if (ObjectUtil.isEmpty(relTransactions.getTranAmountTranRecordNumber())) {
                    sb.append("tranAmountTranRecordNumber");
                }
            }
        }

        if (CharSequenceUtil.isNotBlank(sb)) {
            String msg = sb.append("字段为空！").toString();
            returnData(tips, message, msg, chnlId, "1001");
            return true;
        }

        /* 交易类型不匹配 */
        if (!SourceSys.SOURCE_SYS_TYPE_04.getCategory().equals(relTransactions.getSourceSysName())) {
            if (!TranTypeConstant.TRAN_TYPE_1.equals(relTransactions.getTranType()) && !TranTypeConstant.TRAN_TYPE_2.equals(relTransactions.getTranType())
                    && !TranTypeConstant.TRAN_TYPE_3.equals(relTransactions.getTranType()) && !TranTypeConstant.TRAN_TYPE_4.equals(relTransactions.getTranType())) {
                returnData(tips, message, "交易类型【" + relTransactions.getTranType() + "】不匹配", chnlId, "1001");
                return true;
            }
        }

        /* 获取关联方信息 */
        if (ObjectUtil.isEmpty(getRelId(relTransactions))) {
            returnData(tips, message, "关联方信息不存在", chnlId, "1001");
            return true;
        }
        return false;
    }

    /**
     * 关联交易接口记录
     * @param tips
     * @param map
     */
    public void interFaceRecord(Map<String, Object> tips,Map<String, Object> map){
        try {
            InterfaceRecord interfaceRecord = new InterfaceRecord();
            String chnlId = map.get("chnlId").toString();
            String tranCode = map.get("tranCode").toString();
            interfaceRecord.setChnlId(chnlId);
            interfaceRecord.setTransCode(tranCode);
            interfaceRecord.setBusName("关联交易数据接口 T+1");
            interfaceRecord.setType("1");
            interfaceRecord.setCode(String.valueOf(tips.get("resultCode")));
            if(Dict.INTERFACE_RETURN_CODE.equals(tips.get("resultCode"))){
                interfaceRecord.setResult("1");
            }else {
                interfaceRecord.setResult("2");
            }
            traInterfaceRecordService.setInterfaceRecord(interfaceRecord);
        }catch (Exception e){
            logger.error("关联交易删除记录错误：{}",e);
        }
    }


    /**
     * 个团险赋默认值
     *
     * @param traBusinessNew    保险业务类
     * @param relTransactions   接口数据
     * @param sourceModlueType  来源模块
     * @param sourceSysType     来源系统
     */
    private void setDefaultValue(TraBusiness traBusinessNew, RelTransactions relTransactions,
                                 String sourceModlueType, String sourceSysType) {
        if (SourceSys.SOURCE_SYS_TYPE_01.getCategoryName().equals(sourceSysType) ||
                SourceSys.SOURCE_SYS_TYPE_02.getCategoryName().equals(sourceSysType)) {
            // 合同状态根据业务起始日期和业务结束日期赋值，交易日期在业务起始日期和业务结束日期区间内为有效，否则为无效，业务起始日期或业务结束日期有一个为空时，不计算。
            traBusinessNew.setContractStatus(contractStatus(relTransactions.getBusStartTime(), relTransactions.getBusEndTime()));
            // 协议标记默认1
            traBusinessNew.setProtocolTransactionSymbol("1");
            // “协议交易标志”为是时，“主协议合同编号”为必填，赋值合同编号
            traBusinessNew.setMasterAgreementNumber(relTransactions.getContractNo());
            // 交易标的=来源模块中文
            traBusinessNew.setTradeSubject(sourceModlueType);
            // 业务类型=全部赋值01 保险业务
            traBusinessNew.setBusType("01");
            // 交易价格=交易标的+交易金额
            traBusinessNew.setTradePrice(traBusinessNew.getTradeSubject() + traBusinessNew.getPracTradeAmount());
            // 交易概述=来源模块+交易金额
            traBusinessNew.setTranName(traBusinessNew.getTradeSubject() + traBusinessNew.getPracTradeAmount());
            // 定价政策：固定话术（符合市价）
            traBusinessNew.setPricingPolicy("符合市价");
            // 是否统一交易协议：默认否
            traBusinessNew.setIsAgreement("0");
            // 单笔关联交易类型 默认：统一交易协议
            traBusinessNew.setSingleRelatedTranType("2");
            // 状态默认已归档
            traBusinessNew.setStatus("6");
            // 是否可免于审议自然人和法人交易金额判断，如果自然人大于等于50万或法人大于等于500万，则为否，否则为是
            traBusinessNew.setIsconsiderSituation(setIsconsiderSituation(traBusinessNew.getRelType(),
                    traBusinessNew.getPracTradeAmount()));
            // 关联方证件号码
            String relNumber = relTransactions.getRelNumber();
                    /* 如果来源系统为个团险核心且关联方为：
                    星恒保险代理有限责任公司 91310115MA1H85J294
                    上海复衡保险经纪有限公司 911101176928898665
                    鼎睿再保险有限公司  1531647  赋值默认值 */
            if (StringUtils.isNotEmpty(relNumber) &&
                    ("91310115MA1H85J294".equals(relNumber) || "911101176928898665".equals(relNumber) ||
                            "1531647".equals(relNumber))) {
                // 是否统一交易协议 默认：是
                traBusinessNew.setIsAgreement("1");
                // 统一交易协议业务情况 默认：实际支出
                traBusinessNew.setIsAgreementImpl("实际支出");
                // 统一交易协议类型 默认：统一交易协议下发生的交易对应的字典值
                traBusinessNew.setTradeAgreementType("4");
            }
        }
    }

    /**
     * 判断合同状态
     *
     * @param StartTime 业务起始日期
     * @param endTime   业务终止日期
     * @return  生效/失效
     */
    public String contractStatus(String StartTime, String endTime) {
        if (StringUtils.isNotEmpty(StartTime) && StringUtils.isNotEmpty(endTime)) {
            // 合同结束日期
            String endTimeStr = DateUtil.format(DateUtil.parse(endTime), DatePattern.NORM_DATE_PATTERN);
            // 当前日期
            String format = LocalDate.now().format(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN));
            // 判断合同是否生效 1:生效  2：终止
            if (endTimeStr.compareTo(format) >= 0) {
                return "1";
            } else {
                return "2";
            }
        }
        return null;
    }

    /**
     * 判断是否可免于审议
     *
     * @param relType           关联方类型
     * @param pracTradeAmount   交易金额
     * @return  是否可免于审议
     */
    private String setIsconsiderSituation(String relType, String pracTradeAmount) {
        if (StringUtils.isNotEmpty(pracTradeAmount)) {
            BigDecimal bigDecimal = new BigDecimal(pracTradeAmount);
            if ("PSN".equals(relType)) {
                return bigDecimal.compareTo(new BigDecimal("50")) >= 0 ?
                        YesOrNoEnum.NO.getCode() : YesOrNoEnum.YES.getCode();
            }
            if ("ORG".equals(relType)) {
                return bigDecimal.compareTo(new BigDecimal("500")) >= 0 ?
                        YesOrNoEnum.NO.getCode() : YesOrNoEnum.YES.getCode();
            }
        }
        return YesOrNoEnum.NO.getCode();
    }

}
