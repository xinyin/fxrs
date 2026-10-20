/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2021 pactera.com. All rights reserved.
 *
 * Generated at 2021年07月19日 10:12:36
 *
 */

package com.yzj.jep.app.ins.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.sch.UnSortOutTask;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.fil.model.RelatedTranBaseInfoVo;
import com.yzj.jep.app.fil.model.RelatedTranMapperVo;
import com.yzj.jep.app.ins.constants.TranTypeConstant;
import com.yzj.jep.app.ins.enums.RelatedPartyCategoryEnum;
import com.yzj.jep.app.ins.mapper.UnSortOutMapper;
import com.yzj.jep.app.ins.model.*;
import com.yzj.jep.app.ins.service.*;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.BatchNumberUtil;
import com.yzj.jep.app.util.EmailSend;
import com.yzj.jep.app.util.JudgeTranType;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import com.yzj.jep.sys.mail.IEmailSendService;
import com.yzj.jep.sys.model.AppConfig;
import com.yzj.jep.sys.service.AppConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;


/**
 * @author duanliqiang
 * @Description 未归类数据进行归类保存
 */
@Service
@Transactional(readOnly = false)
public class UnSortOutServiceImpl extends MyBatisCRUDService<UnSortOut, Integer> implements UnSortOutService {

    private static final Logger LOG = LoggerFactory.getLogger(UnSortOutServiceImpl.class);

    @Resource
    private UnSortOutMapper unSortOutMapper;


    @Resource
    private TraFundsService traFundsService;

    @Resource
    private TraBusinessService traBusinessService;

    @Resource
    private TraInterestService traInterestService;

    @Resource
    private TraServiceService traServiceService;

    @Resource
    private RelPsnService relPsnService;

    @Resource
    private RelOrgService relOrgService;

    @Resource
    private IEmailSendService iEmailSendService;

    @Resource
    private EmailSend emailSend;

    AppConfigService appConfigService = SpringContextHolder.getBean(AppConfigService.class);

    private static final Logger logger = LoggerFactory.getLogger(UnSortOutTask.class);

    @Override
    public Mapper<UnSortOut, Integer> getMapper() {
        return unSortOutMapper;
    }

    @Override
    public void addTrad(UnSortOut unSortOut, String contractNo) {
        UserProfile profile = UserProfileUtils.getUserProfile();
        String tranType = unSortOut.getTranType();
        // 组装查询是否重大关联交易数据
        RelatedTranBaseInfoVo tranBaseInfoVo = new RelatedTranBaseInfoVo();
        BeanUtils.copyProperties(unSortOut, tranBaseInfoVo);
        tranBaseInfoVo.setRelTrandeAmount(unSortOut.getRelTradeAmount());
        // 如果是服务类或者保险业务及其他类需要默认是否统一交易协议为否0
        if (TranTypeConstant.TRAN_TYPE_2.equals(tranType) || TranTypeConstant.TRAN_TYPE_4.equals(tranType)) {
            tranBaseInfoVo.setIsAgreement("0");
        }
        RelatedTranMapperVo tranMapperVo = JudgeTranType.assemblyData(tranBaseInfoVo, unSortOut.getRelId());


        if (TranTypeConstant.TRAN_TYPE_1.equals(tranType)) {
            LOG.info("============================【资金运用类】==================================");
            traFundsType(unSortOut, tranMapperVo, profile);

        } else if (TranTypeConstant.TRAN_TYPE_2.equals(tranType)) {
            LOG.info("============================【服务类】==================================");
            traServiceType(unSortOut, tranMapperVo, profile);

        } else if (TranTypeConstant.TRAN_TYPE_3.equals(unSortOut.getTranType())) {
            LOG.info("============================【利益转移类】==================================");
            traInterestType(unSortOut, tranMapperVo, profile);


        } else if (TranTypeConstant.TRAN_TYPE_4.equals(unSortOut.getTranType())) {
            LOG.info("============================【保险业务及其他类】==================================");
            traBusinessType(unSortOut, tranMapperVo, profile);
        }
    }


    /**
     * 资金运用类数据保存或者新增
     *
     * @param unSortOut    未归类数据
     * @param tranMapperVo 重大信息
     * @param profile      用户信息
     */
    public void traFundsType(UnSortOut unSortOut, RelatedTranMapperVo tranMapperVo, UserProfile profile) {
        // 关联交易金额
        BigDecimal relTrandeAmount = unSortOut.getRelTradeAmount();
        // 实际交易金额
        String  pracTradeAmount = unSortOut.getPracTradeAmount();
        //合同编号
        String contractNo = unSortOut.getContractNo();
        // 关联方正将号码
        String relNumber = unSortOut.getRelNumber();
        //合同签署日期
        Date contractDate = unSortOut.getTranDate();
        String sourceSysCode = unSortOut.getUniqueCode();
        String sourceSysType = unSortOut.getSourceSystem();
        String sourceModlueType = unSortOut.getSourceModule();
        TraFunds traFundsNew = new TraFunds();
        String tradeNunber = BatchNumberUtil.makeBatchNumber();

        List<Filter> filter = new Filter.Builder()
                .eq("contractNo", contractNo)
                .eq("dataVersion", "E2.0")
//                .eq("relNumber", relNumber)
//                .ne("status", "9")
                .eq("delflag", "1").build();

        List<TraFunds> listTraFunds = traFundsService.select(filter);
        if (listTraFunds != null && !listTraFunds.isEmpty()) {
            TraFunds traFundsNo = traFundsService.traContractNo(contractNo, null);

            if (traFundsNo != null) {
                //id
                Integer pid = traFundsNo.getPid();
                traFundsNew.setModifiedTime(new Date());
                traFundsNew.setPaymentId(unSortOut.getPaymentId());
                traFundsNew.setEffectOrPay(unSortOut.getEffectOrPay());
                traFundsNew.setRelType(unSortOut.getRelType());
                traFundsNew.setPid(pid);
                if(ObjectUtil.isEmpty(traFundsNew.getBusStartTime())){
                    traFundsNew.setBusStartTime(contractDate);
                }
                if(ObjectUtil.isEmpty(traFundsNew.getTranDate())){
                    traFundsNew.setTranDate(contractDate);
                }
                traFundsNew.setTradTime(unSortOut.getTradTime());
                traFundsNew.setPracTradeAmount(pracTradeAmount);
                traFundsNew.setStatus("6");
                traFundsService.updateSelective(traFundsNew);
            } else {
                TraFunds getTraFunds = listTraFunds.get(0);
                BeanUtils.copyProperties(getTraFunds, traFundsNew);

                traFundsNew.setPid(null);
                traFundsNew.setAmendId(profile.getStaffCode());
                traFundsNew.setAmendName(profile.getStaffName());
                traFundsNew.setDeptName(profile.getDeptName());
                traFundsNew.setPaymentId(unSortOut.getPaymentId());
                traFundsNew.setEffectOrPay(unSortOut.getEffectOrPay());
                traFundsNew.setRelType(unSortOut.getRelType());
                traFundsNew.setRelName(unSortOut.getRelName());
                traFundsNew.setRelNumber(unSortOut.getRelNumber());
                //交易日期
                traFundsNew.setTranDate(contractDate);
                traFundsNew.setBusStartTime(contractDate);
                traFundsNew.setTradTime(unSortOut.getTradTime());
                // 实际交易金额
                traFundsNew.setPracTradeAmount(pracTradeAmount);
                //从新生成交易编号
                traFundsNew.setTransNum(tradeNunber);
                traFundsNew.setCreateTime(new Date());
//                traFundsNew.setSourceModule(sourceModlueType);
                traFundsNew.setSourceSystem(sourceSysType);
                traFundsNew.setUniqueCode(sourceSysCode);
                traFundsNew.setStatus("1");
                //新增一条数据
                traFundsService.insertSelective(traFundsNew);
            }
        } else {
            BeanUtils.copyProperties(unSortOut, traFundsNew);
            // 把查询是否重大关联交易结果赋值到相应实体类
            BeanUtils.copyProperties(tranMapperVo, traFundsNew);
            traFundsNew.setAmendId(profile.getStaffCode());
            traFundsNew.setAmendName(profile.getStaffName());
            traFundsNew.setDeptName(profile.getDeptName());
            traFundsNew.setPaymentId(unSortOut.getPaymentId());
            traFundsNew.setEffectOrPay(unSortOut.getEffectOrPay());
            traFundsNew.setRelType(unSortOut.getRelType());
            traFundsNew.setPid(null);
            traFundsNew.setStatus("1");
            traFundsNew.setCreateTime(new Date());
            traFundsNew.setTransNum(tradeNunber);
            traFundsNew.setRelTradeAmount(relTrandeAmount);
            traFundsNew.setPracTradeAmount(pracTradeAmount);
//            traFundsNew.setSourceModule(sourceModlueType);
            traFundsNew.setSourceSystem(sourceSysType);
            traFundsNew.setUniqueCode(sourceSysCode);
            traFundsNew.setDelflag("1");
            traFundsNew.setIsHoldingSubsidiary("0");
            traFundsService.insertSelective(traFundsNew);
        }
    }

    /**
     * 服务类数据保存或者新增
     *
     * @param unSortOut    未归类数据
     * @param tranMapperVo 重大信息
     * @param profile      用户信息
     */
    public void traServiceType(UnSortOut unSortOut, RelatedTranMapperVo tranMapperVo, UserProfile profile) {
        // 关联交易金额
        BigDecimal relTrandeAmount = unSortOut.getRelTradeAmount();
        // 实际交易金额
        String  pracTradeAmount = unSortOut.getPracTradeAmount();
        //合同编号
        String contractNo = unSortOut.getContractNo();
        // 关联方正将号码
        String relNumber = unSortOut.getRelNumber();
        //合同签署日期
        Date contractDate = unSortOut.getTranDate();
        String sourceSysCode = unSortOut.getUniqueCode();
        String sourceSysType = unSortOut.getSourceSystem();
        String sourceModlueType = unSortOut.getSourceModule();

        TraService traServiceNew = new TraService();

        String tradeNunber = BatchNumberUtil.makeBatchNumber();

        List<Filter> filter = new Filter.Builder()
                .eq("contractNo", contractNo)
                .eq("dataVersion", "E2.0")
//                .eq("relNumber", relNumber)
//                .ne("status", "9")
                .eq("delflag", "1").build();

        List<TraService> traServiceList = traServiceService.select(filter);
        if (traServiceList != null && !traServiceList.isEmpty()) {
            TraService traServiceNo = traServiceService.traServiceNo(contractNo, null);

            if (traServiceNo != null) {
                //id
                Integer pid = traServiceNo.getPid();
                traServiceNew.setPaymentId(unSortOut.getPaymentId());
                traServiceNew.setEffectOrPay(unSortOut.getEffectOrPay());
                traServiceNew.setRelType(unSortOut.getRelType());
                traServiceNew.setModifiedTime(new Date());
                traServiceNew.setPid(pid);
                if(ObjectUtil.isEmpty(traServiceNew.getBusStartTime())){
                    traServiceNew.setBusStartTime(contractDate);
                }
                if(ObjectUtil.isEmpty(traServiceNew.getContractDate())){
                    traServiceNew.setContractDate(contractDate);
                }
                traServiceNew.setTranDate(unSortOut.getTradTime());
                traServiceNew.setPracTradeAmount(pracTradeAmount);
                traServiceNew.setStatus("6");
                traServiceService.updateSelective(traServiceNew);
            } else {
                TraService getTraService = traServiceList.get(0);
                BeanUtils.copyProperties(getTraService, traServiceNew);
                traServiceNew.setPid(null);
                traServiceNew.setAmendId(profile.getStaffCode());
                traServiceNew.setAmendName(profile.getStaffName());
                traServiceNew.setDeptName(profile.getDeptName());
                traServiceNew.setPaymentId(unSortOut.getPaymentId());
                traServiceNew.setEffectOrPay(unSortOut.getEffectOrPay());
                traServiceNew.setRelType(unSortOut.getRelType());
                traServiceNew.setRelName(unSortOut.getRelName());
                traServiceNew.setRelNumber(unSortOut.getRelNumber());
                //交易日期
                traServiceNew.setTranDate(unSortOut.getTradTime());
                traServiceNew.setBusStartTime(contractDate);
                // 实际交易金额
                traServiceNew.setPracTradeAmount(pracTradeAmount);
                //从新生成交易编号
                traServiceNew.setTransNum(tradeNunber);
                traServiceNew.setCreateTime(new Date());
//                traServiceNew.setSourceModule(sourceModlueType);
                traServiceNew.setSourceSystem(sourceSysType);
                traServiceNew.setUniqueCode(sourceSysCode);
                traServiceNew.setContractDate(contractDate);
                traServiceNew.setStatus("1");
                //新增一条数据
                traServiceService.insertSelective(traServiceNew);

            }
        } else {
            BeanUtils.copyProperties(unSortOut, traServiceNew);
            // 把查询是否重大关联交易结果赋值到相应实体类
            BeanUtils.copyProperties(tranMapperVo, traServiceNew);
            traServiceNew.setPid(null);
            traServiceNew.setTranDate(unSortOut.getTradTime());
            traServiceNew.setPaymentId(unSortOut.getPaymentId());
            traServiceNew.setEffectOrPay(unSortOut.getEffectOrPay());
            traServiceNew.setDeptName(profile.getDeptName());
            traServiceNew.setRelType(unSortOut.getRelType());
            traServiceNew.setAmendId(profile.getStaffCode());
            traServiceNew.setAmendName(profile.getStaffName());
            traServiceNew.setStatus("1");
            traServiceNew.setCreateTime(new Date());
            traServiceNew.setTransNum(tradeNunber);
            traServiceNew.setRelTradeAmount(relTrandeAmount);
            traServiceNew.setPracTradeAmount(pracTradeAmount);
//            traServiceNew.setSourceModule(sourceModlueType);
            traServiceNew.setSourceSystem(sourceSysType);
            traServiceNew.setUniqueCode(sourceSysCode);
            traServiceNew.setDelflag("1");
            traServiceService.insertSelective(traServiceNew);
        }
    }


    /**
     * 利益转移类数据保存或者新增
     *
     * @param unSortOut    未归类数据
     * @param tranMapperVo 重大信息
     * @param profile      用户信息
     */
    public void traInterestType(UnSortOut unSortOut, RelatedTranMapperVo tranMapperVo, UserProfile profile) {
        // 关联交易金额
        BigDecimal relTrandeAmount = unSortOut.getRelTradeAmount();
        // 实际交易金额
        String  pracTradeAmount = unSortOut.getPracTradeAmount();
        //合同编号
        String contractNo = unSortOut.getContractNo();
        //合同签署日期
        Date contractDate = unSortOut.getTranDate();
        String sourceSysCode = unSortOut.getUniqueCode();
        String sourceSysType = unSortOut.getSourceSystem();
        String sourceModlueType = unSortOut.getSourceModule();

        TraInterest traInterestNew = new TraInterest();

        String tradeNunber = BatchNumberUtil.makeBatchNumber();

        List<Filter> filter = new Filter.Builder()
                .eq("contractNo", contractNo)
                .eq("dataVersion", "E2.0")
//                .eq("relNumber", unSortOut.getRelNumber())
//                .ne("status", "9")
                .eq("delflag", "1").build();

        List<TraInterest> traInterestList = traInterestService.select(filter);
        if (traInterestList != null && !traInterestList.isEmpty()) {
            TraInterest traInterestNo = traInterestService.traContractNo(contractNo, null);
            if (traInterestNo != null) {
                //id
                Integer pid = traInterestNo.getPid();
                traInterestNew.setModifiedTime(new Date());
                traInterestNew.setPid(pid);
                traInterestNew.setPaymentId(unSortOut.getPaymentId());
                traInterestNew.setEffectOrPay(unSortOut.getEffectOrPay());
                traInterestNew.setRelType(unSortOut.getRelType());
                if(ObjectUtil.isEmpty(traInterestNew.getBusStartTime())){
                    traInterestNew.setBusStartTime(contractDate);
                }
                if(ObjectUtil.isEmpty(traInterestNew.getContractDate())){
                    traInterestNew.setContractDate(contractDate);
                }
                traInterestNew.setPracTradeAmount(pracTradeAmount);
                traInterestNew.setStatus("6");
                traInterestNew.setTranDate(unSortOut.getTradTime());
                traInterestService.updateSelective(traInterestNew);
            } else {
                TraInterest traInterest = traInterestList.get(0);
                BeanUtils.copyProperties(traInterest, traInterestNew);
                traInterestNew.setPid(null);
                traInterestNew.setAmendId(profile.getStaffCode());
                traInterestNew.setAmendName(profile.getStaffName());
                traInterestNew.setDeptName(profile.getDeptName());
                traInterestNew.setPaymentId(unSortOut.getPaymentId());
                traInterestNew.setEffectOrPay(unSortOut.getEffectOrPay());
                traInterestNew.setRelType(unSortOut.getRelType());
                traInterestNew.setRelName(unSortOut.getRelName());
                traInterestNew.setRelNumber(unSortOut.getRelNumber());
                //交易日期
                traInterestNew.setTranDate(unSortOut.getTradTime());
                traInterestNew.setBusStartTime(contractDate);
                // 交易金额
                traInterestNew.setPracTradeAmount(pracTradeAmount);
                //从新生成交易编号
                traInterestNew.setTransNum(tradeNunber);
                traInterestNew.setCreateTime(new Date());
//                traInterestNew.setSourceModule(sourceModlueType);
                traInterestNew.setSourceSystem(sourceSysType);
                traInterestNew.setUniqueCode(sourceSysCode);
                traInterestNew.setContractDate(contractDate);
                traInterestNew.setStatus("1");

                //新增一条数据
                traInterestService.insertSelective(traInterestNew);
            }
        } else {
            BeanUtils.copyProperties(unSortOut, traInterestNew);
            // 把查询是否重大关联交易结果赋值到相应实体类
            BeanUtils.copyProperties(tranMapperVo, traInterestNew);
            traInterestNew.setPid(null);
            traInterestNew.setAmendId(profile.getStaffCode());
            traInterestNew.setAmendName(profile.getStaffName());
            traInterestNew.setDeptName(profile.getDeptName());
            traInterestNew.setPaymentId(unSortOut.getPaymentId());
            traInterestNew.setEffectOrPay(unSortOut.getEffectOrPay());
            traInterestNew.setRelType(unSortOut.getRelType());
            traInterestNew.setTranDate(unSortOut.getTradTime());
            traInterestNew.setStatus("1");
            traInterestNew.setCreateTime(new Date());
            traInterestNew.setTransNum(tradeNunber);
            traInterestNew.setRelTradeAmount(relTrandeAmount);
            traInterestNew.setPracTradeAmount(pracTradeAmount);
//            traInterestNew.setSourceModule(sourceModlueType);
            traInterestNew.setSourceSystem(sourceSysType);
            traInterestNew.setUniqueCode(sourceSysCode);
            traInterestNew.setDelflag("1");
            traInterestService.insertSelective(traInterestNew);
        }
    }


    /**
     * 保险业务及其他类数据保存或者新增
     *
     * @param unSortOut    未归类数据
     * @param tranMapperVo 重大信息
     * @param profile      用户信息
     */
    public void traBusinessType(UnSortOut unSortOut, RelatedTranMapperVo tranMapperVo, UserProfile profile) {
        // 关联交易金额
        BigDecimal relTrandeAmount = unSortOut.getRelTradeAmount();
        // 实际交易金额
        String  pracTradeAmount = unSortOut.getPracTradeAmount();
        //合同编号
        String contractNo = unSortOut.getContractNo();
        //合同签署日期
        Date contractDate = unSortOut.getTranDate();
        String sourceSysCode = unSortOut.getUniqueCode();
        String sourceSysType = unSortOut.getSourceSystem();
        String sourceModlueType = unSortOut.getSourceModule();

        TraBusiness traBusinessNew = new TraBusiness();

        List<Filter> filter = new Filter.Builder()
                .eq("contractNo", contractNo)
                .eq("dataVersion", "E2.0")
//                .eq("relNumber", unSortOut.getRelNumber())
//                .ne("status", "9")
                .eq("delflag", "1").build();

        String tradeNunber = BatchNumberUtil.makeBatchNumber();
        List<TraBusiness> traBusinessList = traBusinessService.select(filter);
        if (traBusinessList != null && !traBusinessList.isEmpty()) {
            TraBusiness traBusinessNo = traBusinessService.traBusinessNo(contractNo, null);
            if (traBusinessNo != null) {
                //id
                Integer pid = traBusinessNo.getPid();
                traBusinessNew.setModifiedTime(new Date());
                traBusinessNew.setPid(pid);
                traBusinessNew.setTranDate(unSortOut.getTradTime());
                traBusinessNew.setPracTradeAmount(pracTradeAmount);
                traBusinessNew.setPaymentId(unSortOut.getPaymentId());
                traBusinessNew.setEffectOrPay(unSortOut.getEffectOrPay());
                traBusinessNew.setRelType(unSortOut.getRelType());
                traBusinessNew.setStatus("6");
                if(ObjectUtil.isEmpty(traBusinessNew.getBusStartTime())){
                    traBusinessNew.setBusStartTime(contractDate);
                }
                if(ObjectUtil.isEmpty(traBusinessNew.getContractDate())){
                    traBusinessNew.setContractDate(contractDate);
                }
                traBusinessService.updateSelective(traBusinessNew);

            } else {
                TraBusiness getTraBusiness = traBusinessList.get(0);
                BeanUtils.copyProperties(getTraBusiness, traBusinessNew);

                traBusinessNew.setPid(null);
                traBusinessNew.setAmendId(profile.getStaffCode());
                traBusinessNew.setAmendName(profile.getStaffName());
                traBusinessNew.setDeptName(profile.getDeptName());
                traBusinessNew.setPaymentId(unSortOut.getPaymentId());
                traBusinessNew.setEffectOrPay(unSortOut.getEffectOrPay());
                traBusinessNew.setRelType(unSortOut.getRelType());
                traBusinessNew.setRelName(unSortOut.getRelName());
                traBusinessNew.setRelNumber(unSortOut.getRelNumber());
                //交易日期
                traBusinessNew.setTranDate(unSortOut.getTradTime());
                // 实际交易金额
                traBusinessNew.setPracTradeAmount(pracTradeAmount);
                traBusinessNew.setRelTradeAmount(relTrandeAmount);
                //从新生成交易编号
                traBusinessNew.setTransNum(tradeNunber);
                traBusinessNew.setCreateTime(new Date());
//                traBusinessNew.setSourceModule(sourceModlueType);
                traBusinessNew.setSourceSystem(sourceSysType);
                traBusinessNew.setUniqueCode(sourceSysCode);
                traBusinessNew.setContractDate(contractDate);
                traBusinessNew.setStatus("1");
                //新增一条数据
                traBusinessService.insertSelective(traBusinessNew);
            }
        } else {
            BeanUtils.copyProperties(unSortOut, traBusinessNew);
            // 把查询是否重大关联交易结果赋值到相应实体类
            BeanUtils.copyProperties(tranMapperVo, traBusinessNew);
            traBusinessNew.setPid(null);
            traBusinessNew.setTranDate(unSortOut.getTradTime());
            traBusinessNew.setAmendId(profile.getStaffCode());
            traBusinessNew.setAmendName(profile.getStaffName());
            traBusinessNew.setDeptName(profile.getDeptName());
            traBusinessNew.setPaymentId(unSortOut.getPaymentId());
            traBusinessNew.setEffectOrPay(unSortOut.getEffectOrPay());
            traBusinessNew.setRelType(unSortOut.getRelType());
            traBusinessNew.setStatus("1");
            traBusinessNew.setCreateTime(new Date());
            traBusinessNew.setTransNum(tradeNunber);
            traBusinessNew.setRelTradeAmount(relTrandeAmount);
            traBusinessNew.setPracTradeAmount(pracTradeAmount);
//            traBusinessNew.setSourceModule(sourceModlueType);
            traBusinessNew.setSourceSystem(sourceSysType);
            traBusinessNew.setUniqueCode(sourceSysCode);
            traBusinessNew.setDelflag("1");
            traBusinessService.insertSelective(traBusinessNew);
        }
    }


    /**
     * 获取关联方ID
     * @param relType       关联方类型
     * @param relNumber     关联方证件号码
     * @return              返回rekID
     */
    public Integer getRelId(String relType, String relNumber) {
        Integer relId = 0;
        if (RelatedPartyCategoryEnum.LEGALPERSON.getSysCode().equals(relType)) {
            List<Filter> filter = new Filter.Builder()
                    .eq("identifyNumber", relNumber)
                    .eq("delflag", "1").build();
            List<RelPsn> psns = relPsnService.select(filter);
            if (CollectionUtils.isNotEmpty(psns)) {
                relId = psns.get(0).getPid();
            }
        } else if (RelatedPartyCategoryEnum.NATURALPERSON.getSysCode().equals(relType)) {
            List<Filter> filter = new Filter.Builder()
                    .eq("uniscCode", relNumber)
                    .eq("delflag", "1").build();
            List<RelOrg> orgs = relOrgService.select(filter);
            if (CollectionUtils.isNotEmpty(orgs)) {
                relId = orgs.get(0).getPid();
            }
        }
        return relId;
    }

    @Override
    public Boolean scheduledTasks() {
        List<UnSortOut> unSortOuts = unSortOutMapper.selectAll();
        List<Email> emailList = new ArrayList<>();
        String copyEmail = getEmailFromParam();
        for (UnSortOut u: unSortOuts) {
            //List<EmailToSendVo> emailToSendList = new ArrayList<>();
            EmailToSendVo emailToSendVo = new EmailToSendVo();
            String sourceModule = u.getSourceModule();
            if (StrUtil.isNotBlank(sourceModule) && sourceModule.contains("|")) {
                String name = sourceModule.substring(0, sourceModule.indexOf("|"));
                String email = sourceModule.substring(sourceModule.indexOf("|")+1);

                emailToSendVo.setName(name);
                emailToSendVo.setEmail(email);
               // emailToSendList.add(emailToSendVo);
            }
            String uniqueCode = u.getUniqueCode();
            Date date = new Date();
            String createDate = new DateTime(date).toString("YYYY年MM月dd日");
            String tranFileUploadPath = appConfigService.getConfigValue("tran_file_upload_path");
            String msg = "{}，你好：\n" +
                    "        {}  你发起的关联交易唯一编码为 {} 的交易，未能及时归类，请及时登录系统对此次交易进行归类。\n" +
                    "        系统登录地址：{} " + "到关联交易管理-未归类交易数据管理中进行交易的确认。#";
           // for (EmailToSendVo vo : emailToSendList) {
                String name = emailToSendVo.getName();
                String email = emailToSendVo.getEmail();
                String content = CharSequenceUtil.format(msg, name, createDate, uniqueCode, tranFileUploadPath);
                Email email2 = emailSend.buildEmailInfoCopyEmail(email, EmailSend.EmailTemplate.REMINDER, content,copyEmail);
                emailList.add(email2);
        //    }
            if (emailList.size() >= 30){
                emailSend.mailInformation(emailList);
                emailList.clear();
            }
        }
        if (CollectionUtils.isNotEmpty(emailList)) {
            emailSend.mailInformation(emailList);
        }
        return null;
    }

    /**
     * 参数配置邮箱，规则 姓名|邮箱,姓名|邮箱
     */
    private static final String  EMAIL_TO_SEND = "EMAIL_TO_SEND";
    /**
     * 从参数表中获取需要发送邮件的
     * @return
     */
    private List<EmailToSendVo> getEmailFromParamTable() {
        /**
         * 李华治|Huazhi.Li@pflife.com.cn,陈佳倩|Jiaqian.Chen@pflife.com.cn
         */
        List<EmailToSendVo> list = new ArrayList<>();
        AppConfig config = appConfigService.get(EMAIL_TO_SEND);
        if (ObjectUtil.isNotNull(config)) {
            String configValue = config.getConfigValue();
            if (StrUtil.isNotBlank(configValue)) {
                String[] nameAndEmailArr = configValue.split(",");
                for (String nameAndEmail : nameAndEmailArr) {
                    if (nameAndEmail.contains("|")) {
                        String name = nameAndEmail.substring(0, nameAndEmail.indexOf("|"));
                        String email = nameAndEmail.substring(nameAndEmail.indexOf("|")+1);
                        EmailToSendVo emailToSendVo = new EmailToSendVo();
                        emailToSendVo.setName(name);
                        emailToSendVo.setEmail(email);
                        list.add(emailToSendVo);
                    }
                }
            }
        }
        return  list;
    }
    /**
     * 从参数表中获取需要发送邮件的
     * @return
     */
    private String getEmailFromParam() {
        /**
         * 李华治|Huazhi.Li@pflife.com.cn,陈佳倩|Jiaqian.Chen@pflife.com.cn
         * copyEmail="Huazhi.Li@pflife.com.cn#Jiaqian.Chen@pflife.com.cn#";
         */
        String copyEmail = null;
        AppConfig config = appConfigService.get(EMAIL_TO_SEND);
        if (ObjectUtil.isNotNull(config)) {
            String configValue = config.getConfigValue();
            if (StrUtil.isNotBlank(configValue)) {
                String[] nameAndEmailArr = configValue.split(",");
                for (String nameAndEmail : nameAndEmailArr) {
                    if (nameAndEmail.contains("|")) {
                        String name = nameAndEmail.substring(0, nameAndEmail.indexOf("|"));
                        String email = nameAndEmail.substring(nameAndEmail.indexOf("|")+1);
                       if(StrUtil.isEmpty(copyEmail)){
                           copyEmail = email.concat("#");
                       }else {
                           copyEmail = copyEmail.concat(email).concat("#");
                       }
                    }
                }
            }
        }
        return  copyEmail;
    }
}
