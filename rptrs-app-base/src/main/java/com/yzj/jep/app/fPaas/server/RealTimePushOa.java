package com.yzj.jep.app.fPaas.server;

import cn.hutool.core.collection.CollUtil;
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
import com.yzj.jep.app.ins.enums.RelatedPartyCategoryEnum;
import com.yzj.jep.app.ins.enums.YesOrNoEnum;
import com.yzj.jep.app.ins.model.*;
import com.yzj.jep.app.ins.service.TraBusinessService;
import com.yzj.jep.app.ins.service.TraFundsService;
import com.yzj.jep.app.ins.service.TraInterestService;
import com.yzj.jep.app.ins.service.TraServiceService;
import com.yzj.jep.app.record.model.InterfaceRecord;
import com.yzj.jep.app.record.service.TraInterfaceRecordService;
import com.yzj.jep.app.util.*;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static com.yzj.jep.app.util.CheckEntityFiledEmpty.isObjectFieldEmpty;
import static java.net.URLDecoder.decode;

/**
 * 关联交易数据获取接口（实时）
 *
 * @author duanliqiang
 */
@Transactional
public class RealTimePushOa implements IFPaaS1Handler<Message> {
    private static final Logger logger = LoggerFactory.getLogger(RealTimePushOa.class);
    com.yzj.jep.app.ins.service.TraServiceService traServiceService;
    TraFundsService traFundsService;
    TraInterestService traInterestService;
    TraBusinessService traBusinessService;
    RelPsnService relPsnService;
    RelOrgService relOrgService;
    EmailSend emailSend;
    TraInterfaceRecordService traInterfaceRecordService;

    /**
     * 逻辑判断，1是0否
     */
    public static final String SF_0 = "0";
    public static final String SF_1 = "1";

    /**
     * 关联方类别 1-法人、2-自然人
     */
    public static final String REL_TYPE1 = "1";

    @Override
    public Message handle(Message message) throws Exception {
        //提供服务类
        traServiceService = SpringContextHolder.getBean(TraServiceService.class);
        //资金运用类
        traFundsService = SpringContextHolder.getBean(TraFundsService.class);
        //利益转移
        traInterestService = SpringContextHolder.getBean(TraInterestService.class);
        //保险业务类
        traBusinessService = SpringContextHolder.getBean(TraBusinessService.class);
        relOrgService = SpringContextHolder.getBean(RelOrgService.class);
        relPsnService = SpringContextHolder.getBean(RelPsnService.class);
        emailSend = SpringContextHolder.getBean(EmailSend.class);
        traInterfaceRecordService = SpringContextHolder.getBean(TraInterfaceRecordService.class);
        Map<String, Object> tips = new HashMap<>(15);
        Map<String, Object> map = (Map<String, Object>) message.getTarget().getBodys();

        if (null == map) {
            returnData(tips, message, "接收的数据为空", "", "1001");
            return message;
        }

        try {
            JSONObject dataJson = new JSONObject(map);
            //传入参数
            String strInfo = JSON.toJSONString(map);
            logger.info(String.format("关联交易数据获取统数据为【%s】", strInfo));
            String str = dataJson.getString("relTransaction");
            RelTransactions relTransactions = JSON.parseObject(str, RelTransactions.class);
            /* 返回信息 */
            String chnlId = map.get("chnlId").toString();
            String relType = REL_TYPE1.equals(relTransactions.getRelType()) ? "ORG" : "PSN";
            relTransactions.setRelType(relType);
            String relName = decode(relTransactions.getRelName(), "utf-8");
            String relNumber = decode(relTransactions.getRelNumber(), "utf-8");
            logger.info(String.format("relName =【%s】", relName));
            logger.info(String.format("relNumber =【%s】", relNumber));
            relTransactions.setRelName(relName);
            relTransactions.setRelNumber(relNumber);
            // 数据验证
            boolean checkResultFlag = judgeTranData(relTransactions, tips, message, chnlId);
            if (checkResultFlag) {
                return message;
            }


            BigDecimal relTrandeAmount = relTransactions.getRelTrandeAmount();
            BigDecimal bigDecimalNew = new BigDecimal(10000);
            BigDecimal relTrandeAmountNew = relTrandeAmount.divide(bigDecimalNew, 4, RoundingMode.HALF_UP);
            relTransactions.setRelTrandeAmount(relTrandeAmountNew);

            /* 数据保存 */
            Map<String, String> resultMap = traInvesType(relTransactions);
            tips.put("isBlock", resultMap.get("isBlock"));
            tips.put("sourceSysCode", resultMap.get("sourceSysCode"));
            returnData(tips, message, "成功", chnlId, "0000");
            return message;

        } catch (NullPointerException e) {
            logger.error(String.format("NullPointerException message is【%s】", e.getMessage()));
            logger.error("交易异常：", e);
            tips.put("resultCode", "1001");
            tips.put("msg", e.getMessage());
        } catch (JSONException e) {
            logger.error(String.format("JSONException message is【%s】", e.getMessage()));
            logger.error("交易异常：", e);
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
                    /* 是否为统一交易协议、统一交易协议类型、定价政策、来源模块、收付费标记字段非必填 */
                    s -> !"isTradeAgreement".equalsIgnoreCase(s) && !"tradeAgreementType".equalsIgnoreCase(s)
                            && !"pricingPolicy".equalsIgnoreCase(s) && !"sourceSysModule".equalsIgnoreCase(s)
                            && !"paymentMark".equalsIgnoreCase(s)
                            /* 排除其他接口字段 */
                            && !"tranDate".equalsIgnoreCase(s)
                            && !"contractDate".equalsIgnoreCase(s)
                            && !"effectOrPay".equalsIgnoreCase(s)
                            && !"tranAmountTranRecordNumber".equalsIgnoreCase(s)
                            && !"currencyType".equalsIgnoreCase(s)
                            && !"originalCurrency".equalsIgnoreCase(s)
                            && !"policyNo".equalsIgnoreCase(s)
                            && !"groupNature".equalsIgnoreCase(s)
                            && !"busStartTime".equalsIgnoreCase(s)
                            && !"busEndTime".equalsIgnoreCase(s)
            ).forEach(str -> sb.append(str).append(","));
        }
        if (CharSequenceUtil.isNotBlank(sb)) {
            String msg = sb.append("字段为空！").toString();
            returnData(tips, message, msg, chnlId, "1001");
            return true;
        }

        // OA系统必填字段（是否为统一交易协议、定价政策）
        if (SourceSys.SOURCE_SYS_TYPE_03.getCategory().equals(relTransactions.getSourceSysName())) {
            if (TranTypeConstant.TRAN_TYPE_2.equals(relTransactions.getTranType()) || TranTypeConstant.TRAN_TYPE_4.equals(relTransactions.getTranType())) {
                if (CharSequenceUtil.isBlank(relTransactions.getIsTradeAgreement())) {
                    sb.append("isTradeAgreement,");
                }
                // 统一交易协议类型（字典参_tradeAgreementType,当是否为统一交易协议为是时必填）
                if (YesOrNoEnum.YES.getCode().equals(relTransactions.getIsTradeAgreement())) {
                    if (CharSequenceUtil.isBlank(relTransactions.getTradeAgreementType())) {
                        sb.append("tradeAgreementType,");
                    }
                }
            }
            if (CharSequenceUtil.isBlank(relTransactions.getPricingPolicy())) {
                sb.append("pricingPolicy,");
            }
        }

        if (CharSequenceUtil.isNotBlank(sb)) {
            String msg = sb.append("字段为空！").toString();
            returnData(tips, message, msg, chnlId, "1001");
            return true;
        }

        /* 交易类型不匹配 */
        if (!TranTypeConstant.TRAN_TYPE_1.equals(relTransactions.getTranType()) && !TranTypeConstant.TRAN_TYPE_2.equals(relTransactions.getTranType())
                && !TranTypeConstant.TRAN_TYPE_3.equals(relTransactions.getTranType()) && !TranTypeConstant.TRAN_TYPE_4.equals(relTransactions.getTranType())) {
            returnData(tips, message, "交易类型【" + relTransactions.getTranType() + "】不匹配", chnlId, "1001");
            return true;
        }

        /* 获取关联方信息 */
        if (ObjectUtil.isEmpty(getRelId(relTransactions))) {
            returnData(tips, message, "关联方信息不存在", chnlId, "1001");
            return true;
        }
        return false;
    }


    /**
     * 返回非参数
     */
    private static void returnData(Map<String, Object> tips, Message message, String msg, String chnlId, String resultCode) {
        tips.put("msg", msg);
        tips.put("TRANS_CODE", "getRelTransaction");
        tips.put("resultCode", resultCode);
        tips.put("_TRAN_CODE", "getRelTransaction");
        if (CharSequenceUtil.isNotBlank(chnlId)) {
            tips.put("CHNL_ID", chnlId);
        }
        message.getTarget().setHeaders(tips);
        message.getTarget().setBody(tips);
    }

    /**
     * 根据合同编号去系统表中查询是否存审议类型为逐笔审议的数据，存在时之后的数据均为免于审议，否则按照之前进行判断
     * @return              true - 存在，  false - 不存在
     */
    public boolean judgeExistContractNo(RelTransactions relatedTranDataVo) {
        List<Filter> filters = new ArrayList<>();
        filters.add(new Filter("contractNo", Operator.EQ, relatedTranDataVo.getContractNo()));
        filters.add(new Filter("delflag", Operator.EQ, "1"));
        filters.add(new Filter("considerType", Operator.EQ, "3"));
        switch (relatedTranDataVo.getTranType()) {
            case TranTypeConstant.TRAN_TYPE_1:
                List<TraFunds> funds = traFundsService.select(filters);
                return CollUtil.isNotEmpty(funds);
            case TranTypeConstant.TRAN_TYPE_2:
                List<TraService> serviceList = traServiceService.select(filters);
                return CollUtil.isNotEmpty(serviceList);
            case TranTypeConstant.TRAN_TYPE_3:
                List<TraInterest> interestList = traInterestService.select(filters);
                return CollUtil.isNotEmpty(interestList);
            default:
                List<TraBusiness> businessList = traBusinessService.select(filters);
                return CollUtil.isNotEmpty(businessList);
        }
    }


    /**
     * 数据准备
     *
     * @param relTransactions 接口数据
     * @return 返回解结果信息
     */
    public Map<String, String> traInvesType(RelTransactions relTransactions) {
        //返回值
        Map<String, String> resultMap = new HashMap<>(2);
        // 组装查询是否重大关联交易数据
        RelatedTranBaseInfoVo tranBaseInfoVo = new RelatedTranBaseInfoVo();
        BeanUtils.copyProperties(relTransactions, tranBaseInfoVo);
        // 如果统一交易协议类型为4时审议类型为免于审议
        if (TranTypeConstant.TRAN_TYPE_2.equals(relTransactions.getTranType()) || TranTypeConstant.TRAN_TYPE_4.equals(relTransactions.getTranType())) {
            tranBaseInfoVo.setIsAgreement(relTransactions.getIsTradeAgreement());
            if ("1".equals(relTransactions.getIsTradeAgreement())) {
                if ("4".equals(relTransactions.getTradeAgreementType())) {
                    tranBaseInfoVo.setIsAgreement(null);
                    tranBaseInfoVo.setIsconsiderSituation("1");
                }
            }
        }
        // 判断当前合同编号是否在关联交易系统存存在逐笔审议的交易数据
//        boolean flag = judgeExistContractNo(relTransactions);
//        if(flag){
//            tranBaseInfoVo.setIsAgreement(null);
//            tranBaseInfoVo.setIsconsiderSituation("1");
//        }

        RelatedTranMapperVo tranMapperVo = JudgeTranType.assemblyData(tranBaseInfoVo, getRelId(relTransactions));

        // 审议类型(1:免予审议，2:一般审议，3逐笔审议)
        String considerType = tranMapperVo.getConsiderType();
        String cumulativeSignificant = tranMapperVo.getMajorTradeCritical();
        String majorNumber = tranMapperVo.getMajorNumber();

        String tradeNumber = BatchNumberUtil.makeBatchNumber();
        this.insertSelective(relTransactions, tranMapperVo, tradeNumber);
        //是累计重大
        if (SF_1.equals(cumulativeSignificant)) {
            List<Email> list = emailSend.selectPerple();
            emailSend.mojorEmailEighty(list, majorNumber, relTransactions.getRelName(), "4");
        }
        String isBlock;
        String sourceSysCode = relTransactions.getSourceSysCode();
        if (YesOrNoEnum.YES.getCode().equals(considerType)) {
            isBlock = "0";
        } else {
            isBlock = "1";
        }

        resultMap.put("isBlock", isBlock);
        resultMap.put("sourceSysCode", sourceSysCode);
        return resultMap;
    }

    /**
     * 数据保存
     *
     * @param relTransactions 接口数胡
     * @param tranMapperVo    判断重大相关结果信息
     * @param tradeNumber     交易编号
     */
    public void insertSelective(RelTransactions relTransactions, RelatedTranMapperVo tranMapperVo, String tradeNumber) {
        String tranType = relTransactions.getTranType();
        String relType = relTransactions.getRelType();
        /**存在统一交易 且协议类型为4时 由于调用判断重大的问题，把统一交易置为空 ，此处重新为是否为统一交易设值*/
        tranMapperVo.setIsAgreement(relTransactions.getIsTradeAgreement());
        String sourceModlueType = this.sourceModlueType(relTransactions.getSourceSysModule());
        List<Filter> filters = new ArrayList<>();
        filters.add(new Filter("contractNo", Operator.EQ, relTransactions.getContractNo()));
        filters.add(new Filter("delflag", Operator.EQ, "1"));
        switch (tranType) {
            case TranTypeConstant.TRAN_TYPE_1:
                // 资金御用类
                TraFunds traFundsNew = new TraFunds();
                BeanUtils.copyProperties(relTransactions, traFundsNew);
                // 把查询是否重大关联交易结果赋值到相应实体类
                BeanUtils.copyProperties(tranMapperVo, traFundsNew);
                traFundsNew.setContractAmount(relTransactions.getRelTrandeAmount());
                traFundsNew.setRelTradeAmount(relTransactions.getRelTrandeAmount());
                traFundsNew.setPaymentId(relTransactions.getPaymentMark());
                traFundsNew.setUniqueCode(relTransactions.getSourceSysCode());
                traFundsNew.setSourceSystem(sourceSysType(relTransactions.getSourceSysName()));
                traFundsNew.setSourceModule(sourceModlueType);
                //从新生成交易编号
                traFundsNew.setTransNum(tradeNumber);
                traFundsNew.setAmendName("System");
                traFundsNew.setCreateTime(new Date());
                traFundsNew.setDelflag("1");
                traFundsNew.setRelType(relType);
                traFundsNew.setStatus("1");
                traFundsNew.setIsAccumulated(YesOrNoEnum.NO.getCode());
                traFundsNew.setDataSources(DataSource.getCode(relTransactions.getSourceSysName()));
                traFundsNew.setDataVersion("E2.0");
                traFundsService.insertSelective(traFundsNew);

                return;
            case TranTypeConstant.TRAN_TYPE_2:
                // 服务类
                TraService traServiceNew = new TraService();
                BeanUtils.copyProperties(relTransactions, traServiceNew);
                // 把查询是否重大关联交易结果赋值到相应实体类
                BeanUtils.copyProperties(tranMapperVo, traServiceNew);
                traServiceNew.setContractAmount(relTransactions.getRelTrandeAmount());
                traServiceNew.setRelTradeAmount(relTransactions.getRelTrandeAmount());
                traServiceNew.setUniqueCode(relTransactions.getSourceSysCode());
                traServiceNew.setSourceSystem(sourceSysType(relTransactions.getSourceSysName()));
                traServiceNew.setPaymentId(relTransactions.getPaymentMark());
                traServiceNew.setSourceModule(sourceModlueType);
                //从新生成交易编号
                traServiceNew.setTransNum(tradeNumber);
                traServiceNew.setAmendName("System");
                traServiceNew.setCreateTime(new Date());
                traServiceNew.setDelflag("1");
                traServiceNew.setRelType(relType);
                traServiceNew.setStatus("1");
                traServiceNew.setIsAccumulated(YesOrNoEnum.NO.getCode());
                traServiceNew.setDataSources(DataSource.getCode(relTransactions.getSourceSysName()));
                traServiceNew.setDataVersion("E2.0");
                traServiceService.insertSelective(traServiceNew);
                return;
            case TranTypeConstant.TRAN_TYPE_3:
                // 利益转移类
                TraInterest traInterestNew = new TraInterest();
                BeanUtils.copyProperties(relTransactions, traInterestNew);
                // 把查询是否重大关联交易结果赋值到相应实体类
                BeanUtils.copyProperties(tranMapperVo, traInterestNew);
                traInterestNew.setContractAmount(relTransactions.getRelTrandeAmount());
                traInterestNew.setRelTradeAmount(relTransactions.getRelTrandeAmount());
                traInterestNew.setUniqueCode(relTransactions.getSourceSysCode());
                traInterestNew.setSourceSystem(sourceSysType(relTransactions.getSourceSysName()));
                traInterestNew.setPaymentId(relTransactions.getPaymentMark());
                traInterestNew.setSourceModule(sourceModlueType);
                //从新生成交易编号
                traInterestNew.setTransNum(tradeNumber);
                traInterestNew.setAmendName("System");
                traInterestNew.setCreateTime(new Date());
                traInterestNew.setDelflag("1");
                traInterestNew.setRelType(relType);
                traInterestNew.setStatus("1");
                traInterestNew.setIsAccumulated(YesOrNoEnum.NO.getCode());
                traInterestNew.setDataSources(DataSource.getCode(relTransactions.getSourceSysName()));
                traInterestNew.setDataVersion("E2.0");
                traInterestService.insertSelective(traInterestNew);
                return;
            default:
                // 保险业务类
                TraBusiness traBusinessNew = new TraBusiness();
                BeanUtils.copyProperties(relTransactions, traBusinessNew);
                // 把查询是否重大关联交易结果赋值到相应实体类
                BeanUtils.copyProperties(tranMapperVo, traBusinessNew);
                traBusinessNew.setContractAmount(relTransactions.getRelTrandeAmount());
                traBusinessNew.setRelTradeAmount(relTransactions.getRelTrandeAmount());
                traBusinessNew.setUniqueCode(relTransactions.getSourceSysCode());
                traBusinessNew.setSourceSystem(sourceSysType(relTransactions.getSourceSysName()));
                traBusinessNew.setPaymentId(relTransactions.getPaymentMark());
                traBusinessNew.setSourceModule(sourceModlueType);
                //从新生成交易编号
                traBusinessNew.setTransNum(tradeNumber);
                traBusinessNew.setAmendName("System");
                traBusinessNew.setCreateTime(new Date());
                traBusinessNew.setDelflag("1");
                traBusinessNew.setRelType(relType);
                traBusinessNew.setStatus("1");
                traBusinessNew.setIsAccumulated(YesOrNoEnum.NO.getCode());
                traBusinessNew.setDataSources(DataSource.getCode(relTransactions.getSourceSysName()));
                traBusinessNew.setDataVersion("E2.0");
                traBusinessService.insertSelective(traBusinessNew);
        }
    }

    /**
     * 查询关联方ID
     *
     * @param relTransactions 接口数据
     * @return 关联方ID
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
     * 系统发过来源转换
     *
     * @param sourceSysName 系统标识
     * @return 系统中文名称
     */
    private String sourceSysType(String sourceSysName) {
        String sourceSysType = null;
        if (SourceSys.SOURCE_SYS_TYPE_01.getCategory().equals(sourceSysName)) {
            sourceSysType = SourceSys.SOURCE_SYS_TYPE_01.getCategoryName();
        } else if (SourceSys.SOURCE_SYS_TYPE_02.getCategory().equals(sourceSysName)) {
            sourceSysType = SourceSys.SOURCE_SYS_TYPE_02.getCategoryName();
        } else if (SourceSys.SOURCE_SYS_TYPE_03.getCategory().equals(sourceSysName)) {
            sourceSysType = SourceSys.SOURCE_SYS_TYPE_03.getCategoryName();
        } else if (SourceSys.SOURCE_SYS_TYPE_04.getCategory().equals(sourceSysName)) {
            sourceSysType = SourceSys.SOURCE_SYS_TYPE_04.getCategoryName();
        } else if (SourceSys.SOURCE_SYS_TYPE_05.getCategory().equals(sourceSysName)) {
            sourceSysType = SourceSys.SOURCE_SYS_TYPE_05.getCategoryName();
        }
        return sourceSysType;
    }

    /**
     * 来源模块转换
     * @param sourceModlue
     * @return
     */
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
            interfaceRecord.setBusName("关联交易数据获取接口（实时）");
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
}
