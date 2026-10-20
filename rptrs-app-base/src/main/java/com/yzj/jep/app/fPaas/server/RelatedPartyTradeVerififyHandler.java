package com.yzj.jep.app.fPaas.server;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.fPaaS1.core.IFPaaS1Handler;
import com.fPaaS1.core.Message;
import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.ins.service.*;
import com.yzj.jep.app.holder.SpringHolder;
import com.yzj.jep.app.ins.model.*;
import com.yzj.jep.app.ins.web.controller.Commons;
import com.yzj.jep.app.util.BatchNumberUtil;
import com.yzj.jep.app.util.Dict;
import com.yzj.jep.app.util.InsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;

import com.yzj.jep.orm.entity.Filter;
import org.springframework.beans.BeanUtils;

/**
 * 阳光保险添加：关联交易合规验证接口
 */
public class RelatedPartyTradeVerififyHandler implements IFPaaS1Handler<Message> {
    private static final Logger logger = LoggerFactory.getLogger(RelatedPartyTradeVerififyHandler.class);
    private TraInvestmentService traInvestmentService;
    private TraFundsService traFundsService;
    private TraBusinessService traBusinessService;
    private TraInterestService traInterestService;
    private TraServiceService traServiceService;
    private TraOtherService traOtherService;
    private TraCounterpartyService traCounterpartyService;
    @Override
    public Message handle(Message message) throws Exception {
        traInvestmentService = SpringHolder.getBean(TraInvestmentService.class);
        traFundsService = SpringHolder.getBean(TraFundsService.class);
        traBusinessService = SpringHolder.getBean(TraBusinessService.class);
        traInterestService = SpringHolder.getBean(TraInterestService.class);
        traServiceService = SpringHolder.getBean(TraServiceService.class);
        traOtherService = SpringHolder.getBean(TraOtherService.class);
        traCounterpartyService = SpringHolder.getBean(TraCounterpartyService.class);

        Map tips = new HashMap<String, Object>();
        Map map = (Map) message.getTarget().getBodys();
        tips.put("CHNL_ID",map.get("chnlId"));
        tips.put("TRANS_CODE",map.get("tranCode"));
        try {
            JSONObject dataJson = new JSONObject(map);
            //传入参数
            logger.info("---------进入关联交易合规校验接口----传入参数为-------------：" + dataJson.toString());
            //交易类型,01投资入股
            String tranType = dataJson.getString("tranType");
            //交易主体
            String tenantId =  dataJson.getString("tenantId");
            //合同编号
            String contractNo =  dataJson.getString("contractNo");
            //交易对手列表
            String traRelList = dataJson.getString("relList");

            /**解析json*/
            List<TraRelInfo> traRelInfoList = JSONObject.parseArray(traRelList, TraRelInfo.class);
            if (CollectionUtil.isEmpty(traRelInfoList)) {
                tips.put("resultCode","9999");
                tips.put("msg","交易失败，没有收到交易对手信息");
                message.getTarget().setBody(tips);
                return message;
            }
            //关联交易金额，由交易对手金额合计算出
            BigDecimal relTradeAmount = new BigDecimal("0");
            for(TraRelInfo tra:traRelInfoList){
                //查询关联方，返回完整交易对手信息
                tips = InsUtil.findRelPsnOrOrgBy(tenantId,tra.getRelType(), tra.getRelNumber(), tra.getCertType(), tra.getRelName());

                if(Dict.REL_TYPE_PSN.equals(tra.getRelType())){
                    if(CollectionUtil.isEmpty(tips)||tips.get("relPsn")==null){
                        tips.put("resultCode","9999");
                        tips.put("msg","交易失败，没有查到该关联方信息");
                        message.getTarget().setBody(tips);
                        return message;
                    }
                    RelPsn psn =(RelPsn) tips.get("relPsn");
                    if(psn.getPid()!=null){
                        tra.setRelId(psn.getPid());
                    }
                }else if(Dict.REL_TYPE_ORG.equals(tra.getRelType())){
                    if(CollectionUtil.isEmpty(tips)||tips.get("relOrg")==null){
                        tips.put("resultCode","9999");
                        tips.put("msg","交易失败，没有查到该关联方信息");
                        message.getTarget().setBody(tips);
                        return message;
                    }
                    RelOrg org =(RelOrg) tips.get("relOrg");
                    if(org.getPid()!=null){
                        tra.setRelId(org.getPid());
                    }
                }

                tra.setTenantId(tenantId);
                relTradeAmount = relTradeAmount.add(tra.getTradeAmount());
            }
            //校验是否重大
            Map resultMap = InsUtil.GreatCheckNew2(tranType, relTradeAmount,traRelInfoList);
            String majorTrade = Dict.SF_0;
            String majorType = Dict.MAJOR_TYPE_F;
            if(CollectionUtil.isNotEmpty(resultMap)){
                majorTrade = String.valueOf(resultMap.get("majorTrade"));
                majorType = String.valueOf(resultMap.get("majorType"));
            }
            //保存入库
            Map paraMap = new HashMap();
            paraMap.put("tenantId",tenantId);
            paraMap.put("contractNo",contractNo);
            paraMap.put("relTradeAmount",relTradeAmount);
            paraMap.put("tranType",tranType);
            paraMap.put("majorTrade",majorTrade);
            paraMap.put("majorType",majorType);

            //调用保存落库方法
            tips = saveDispatch(paraMap,traRelInfoList);
            if("9999".equals(tips.get("resultCode"))){
                message.getTarget().setBody(tips);
                return message;
            }
            tips.put("majorTrade",majorTrade);
            tips.put("majorType",majorType);
            tips.put("contractNo",contractNo);
            tips.put("tranType",tranType);
            tips.put("tenantId",tenantId);
            tips.put("CHNL_ID",map.get("chnlId"));
            tips.put("TRANS_CODE",map.get("tranCode"));
            tips.put("resultCode","0000");
            tips.put("msg","成功");
            tips.put("relList",traRelInfoList);

            message.getTarget().setBody(tips);
        }catch (Exception e){
            logger.error("关联交易合规验证接口调用失败，错误信息：",e);
        }
        return message;
    }


    /**
     * 保存分发
     * @param traRelList
     */
    private Map saveDispatch(Map map,List<TraRelInfo> traRelList) throws Exception{
        Map resultMap = new HashMap();
        //校验是否已经存在该笔交易
        List<Filter> filterList =new ArrayList<>();
        String tenantId = String.valueOf(map.get("tenantId"));
        filterList.add(new Filter("contractNo",String.valueOf(map.get("contractNo"))));
        filterList.add(new Filter("tenantId",tenantId));
        filterList.add(new Filter("delflag","1"));
        String tranType = String.valueOf(map.get("tranType"));
        switch (tranType){
            case Dict.TRAN_TYPE_01 :resultMap = saveTraInvestment(map,filterList,traRelList);break;
            case Dict.TRAN_TYPE_02 :resultMap = saveTraFunds(map,filterList,traRelList);break;
            case Dict.TRAN_TYPE_03 :resultMap = saveTraBusiness(map,filterList,traRelList);break;
            case Dict.TRAN_TYPE_04 :resultMap = saveTraInterest(map,filterList,traRelList);break;
            case Dict.TRAN_TYPE_05 :resultMap = saveTraService(map,filterList,traRelList);break;
            case Dict.TRAN_TYPE_06 :resultMap = saveTraOther(map,filterList,traRelList);break;
        }
        return resultMap;
    }

    /**
     * 投资入股类
     * @param map
     * @param filterList
     * @param traRelList
     * @throws SQLException
     */
    private Map saveTraInvestment(Map map,List<Filter> filterList,List<TraRelInfo> traRelList) throws SQLException {
        Map resultMap = new HashMap();
        TraInvestment traInvestment = new TraInvestment();
        traInvestment.setTranType(Dict.TRAN_TYPE_01);
        TraInvestment existTraInvestment = traInvestmentService.get(filterList);
        if(existTraInvestment!=null){
            String status = existTraInvestment.getStatus();
            if(Dict.APPLY_STATUS_2.equals(status)||Dict.APPLY_STATUS_4.equals(status)||Dict.APPLY_STATUS_6.equals(status)||Dict.APPLY_STATUS_7.equals(status)){
                logger.info("该交易信息已存在，且状态不为可编辑状态！");
                resultMap.put("resultCode","9999");
                resultMap.put("msg","失败，该交易信息已存在，且状态不为可编辑状态！");
                return resultMap;
            }

            /**
             * 根据添加的交易对手列表，去第一个赋值给交易信息里面的交易对手信息
             */
            TraRelInfo r = traRelList.get(0);
            traInvestment.setRelId(r.getRelId());
            traInvestment.setRelType(r.getRelType());
            traInvestment.setRelName(r.getRelName());
            traInvestment.setRelNumber(r.getRelNumber());
            traInvestment.setCertType(r.getCertType());
            traInvestment.setTradeAmount(r.getTradeAmount());
            BigDecimal relTradeAmount = new BigDecimal("0");
            if(map.get("relTradeAmount")!=null){
                relTradeAmount = new BigDecimal(String.valueOf(map.get("relTradeAmount")));
            }
            traInvestment.setRelTradeAmount(relTradeAmount);
            traInvestmentService.updateSelective(traInvestment);
            String transNum = existTraInvestment.getTransNum();
            //更新关联交易-交易对手信息表
            map.put("transNum",transNum);
            updateTraCounterparty(traRelList,map);
        }else{
            //生成交易编号
            traInvestment.setTransNum(BatchNumberUtil.makeBatchNumber());
            //默认国家定价现金交易为否
            traInvestment.setIsDealPrice(Dict.SF_0);
            traInvestment.setIsCashSubscript(Dict.SF_0);
            //traInvestment.setRelInfoListStr("[]");
            traInvestment.setIsHoldingSubsidiary(Dict.SF_0);
            //添加交易主体名称
            String tenantId = String.valueOf(map.get("tenantId"));
            traInvestment.setTraSubName(Commons.getTenantNameByTenantId(tenantId));
            TraRelInfo r = traRelList.get(0);
            traInvestment.setRelId(r.getRelId());
            traInvestment.setRelType(r.getRelType());
            traInvestment.setStatus(Dict.APPLY_STATUS_1);
            traInvestment.setRelName(r.getRelName());
            traInvestment.setRelNumber(r.getRelNumber());
            traInvestment.setCertType(r.getCertType());
            traInvestment.setTradeAmount(r.getTradeAmount());
            BigDecimal relTradeAmount = new BigDecimal("0");
            if(map.get("relTradeAmount")!=null){
                relTradeAmount = new BigDecimal(String.valueOf(map.get("relTradeAmount")));
            }
            traInvestment.setRelTradeAmount(relTradeAmount);
            traInvestment.setTenantId(tenantId);
            String contractNo =String.valueOf(map.get("contractNo"));
            traInvestment.setContractNo(contractNo);
            String majorTrade = String.valueOf(map.get("majorTrade"));
            String majorType = String.valueOf(map.get("majorType"));
            traInvestment.setMajorTrade(majorTrade);
            traInvestment.setMajorType(majorType);
            traInvestment.setDelflag("1");
            traInvestment.setCreateTime(new Date());
            traInvestment.setDataSources("1");//数据来源，手工录入
            traInvestmentService.insertSelective(traInvestment);
            //保存交易对手到新表
            Map map1 = new HashMap();
            map1.put("transNum",traInvestment.getTransNum());
            map1.put("relTradeAmount",relTradeAmount);
            map1.put("status",traInvestment.getStatus());
            map1.put("traSubName",traInvestment.getTraSubName());
            saveTraCounterparty(traRelList,map1);
        }
        return resultMap;
    }

    /**
     * 保存资金运用
     * @param map
     * @param filterList
     * @param traRelList
     */
    private Map saveTraFunds(Map map,List<Filter> filterList,List<TraRelInfo> traRelList) throws SQLException {
        Map resultMap = new HashMap();

        TraFunds traFunds =  new TraFunds();
        traFunds.setTranType(Dict.TRAN_TYPE_02);
        //校验是否已经存在该笔交易
        TraFunds existTraFunds = traFundsService.get(filterList);
        if(existTraFunds!=null){
            String status = existTraFunds.getStatus();
            if(Dict.APPLY_STATUS_2.equals(status)||Dict.APPLY_STATUS_4.equals(status)||Dict.APPLY_STATUS_6.equals(status)||Dict.APPLY_STATUS_7.equals(status)){
                logger.info("该交易信息已存在，且状态不为可编辑状态！");
                resultMap.put("resultCode","9999");
                resultMap.put("msg","失败，该交易信息已存在，且状态不为可编辑状态！");
                return resultMap;
            }
            traFunds.setModifiedTime(new Date());
            /**
             * 根据添加的交易对手列表，去第一个赋值给交易信息里面的交易对手信息
             */
            TraRelInfo r = traRelList.get(0);
            traFunds.setRelId(r.getRelId());
            traFunds.setRelType(r.getRelType());
            traFunds.setRelName(r.getRelName());
            traFunds.setRelNumber(r.getRelNumber());
            traFunds.setCertType(r.getCertType());
            traFunds.setTradeAmount(r.getTradeAmount());
            BigDecimal relTradeAmount = new BigDecimal("0");
            if(map.get("relTradeAmount")!=null){
                relTradeAmount = new BigDecimal(String.valueOf(map.get("relTradeAmount")));
            }
            traFunds.setRelTradeAmount(relTradeAmount);
            traFundsService.updateSelective(traFunds);

            //更新关联交易-交易对手信息表
            String transNum = existTraFunds.getTransNum();
            map.put("transNum",transNum);
            updateTraCounterparty(traRelList,map);
        }else{
            //生成交易编号
            traFunds.setTransNum(BatchNumberUtil.makeBatchNumber());
            //默认国家定价现金交易为否
            traFunds.setIsDealPrice(Dict.SF_0);
            traFunds.setTradeAmount(new BigDecimal(0));
            //traFunds.setRelInfoListStr("[]");
            traFunds.setIsHoldingSubsidiary(Dict.SF_0);
            //添加交易主体名称
            String tenantId = String.valueOf(map.get("tenantId"));
            traFunds.setTraSubName(Commons.getTenantNameByTenantId(tenantId));

            TraRelInfo r = traRelList.get(0);
            traFunds.setRelId(r.getRelId());
            traFunds.setRelType(r.getRelType());
            traFunds.setRelName(r.getRelName());
            traFunds.setRelNumber(r.getRelNumber());
            traFunds.setCertType(r.getCertType());
            traFunds.setTradeAmount(r.getTradeAmount());
            BigDecimal relTradeAmount = new BigDecimal("0");
            if(map.get("relTradeAmount")!=null){
                relTradeAmount = new BigDecimal(String.valueOf(map.get("relTradeAmount")));
            }
            traFunds.setRelTradeAmount(relTradeAmount);
            String majorTrade = String.valueOf(map.get("majorTrade"));
            String majorType = String.valueOf(map.get("majorType"));
            traFunds.setMajorTrade(majorTrade);
            traFunds.setMajorType(majorType);
            String contractNo =String.valueOf(map.get("contractNo"));
            traFunds.setContractNo(contractNo);
            traFunds.setDelflag("1");
            traFunds.setCreateTime(new Date());
            traFunds.setDataSources("1");//数据来源，手工录入
            traFundsService.insertSelective(traFunds);
            //保存交易对手到新表
            Map map1 = new HashMap();
            map1.put("transNum",traFunds.getTransNum());
            map1.put("relTradeAmount",relTradeAmount);
            map1.put("status",traFunds.getStatus());
            map1.put("traSubName",traFunds.getTraSubName());
            saveTraCounterparty(traRelList,map1);
        }
        return resultMap;
    }

    /**
     * 保险业务类
     * @param map
     * @param filterList
     * @param traRelList
     */
    private Map saveTraBusiness(Map map,List<Filter> filterList,List<TraRelInfo> traRelList) throws SQLException {
        Map resultMap = new HashMap();
        TraBusiness traBusiness = new TraBusiness();
        traBusiness.setTranType(Dict.TRAN_TYPE_03);
        //校验是否已经存在该笔交易
        TraBusiness existTraBusiness = traBusinessService.get(filterList);
        if (existTraBusiness != null) {
            String status = existTraBusiness.getStatus();
            if (Dict.APPLY_STATUS_2.equals(status) || Dict.APPLY_STATUS_4.equals(status) || Dict.APPLY_STATUS_6.equals(status) || Dict.APPLY_STATUS_7.equals(status)) {
                logger.info("该交易信息已存在，且状态不为可编辑状态！");
                resultMap.put("resultCode", "9999");
                resultMap.put("msg", "失败，该交易信息已存在，且状态不为可编辑状态！");
                return resultMap;
            }
            /**
             * 根据添加的交易对手列表，去第一个赋值给交易信息里面的交易对手信息
             */
            TraRelInfo r = traRelList.get(0);
            traBusiness.setRelId(r.getRelId());
            traBusiness.setRelType(r.getRelType());
            traBusiness.setRelName(r.getRelName());
            traBusiness.setRelNumber(r.getRelNumber());
            traBusiness.setCertType(r.getCertType());
            traBusiness.setTradeAmount(r.getTradeAmount());
            BigDecimal relTradeAmount = new BigDecimal("0");
            if (map.get("relTradeAmount") != null) {
                relTradeAmount = new BigDecimal(String.valueOf(map.get("relTradeAmount")));
            }
            traBusiness.setRelTradeAmount(relTradeAmount);
            traBusinessService.updateSelective(traBusiness);
            //交易流水号
            String transNum = existTraBusiness.getTransNum();
            //更新关联交易-交易对手信息表
            map.put("transNum", transNum);
            updateTraCounterparty(traRelList, map);
        } else {
            //生成交易编号
            traBusiness.setTransNum(BatchNumberUtil.makeBatchNumber());
            //默认国家定价现金交易为否
            traBusiness.setIsDealPrice(Dict.SF_0);
            traBusiness.setIsCashSubscript(Dict.SF_0);
            traBusiness.setTradeAmount(new BigDecimal(0));
            traBusiness.setRelInfoListStr("[]");
            traBusiness.setIsHoldingSubsidiary(Dict.SF_0);
            //添加交易主体名称
            String tenantId = String.valueOf(map.get("tenantId"));
            traBusiness.setTraSubName(tenantId);

            TraRelInfo r = traRelList.get(0);
            traBusiness.setRelId(r.getRelId());
            traBusiness.setRelType(r.getRelType());
            traBusiness.setRelName(r.getRelName());
            traBusiness.setRelNumber(r.getRelNumber());
            traBusiness.setCertType(r.getCertType());
            traBusiness.setTradeAmount(r.getTradeAmount());
            BigDecimal relTradeAmount = new BigDecimal("0");
            if (map.get("relTradeAmount") != null) {
                relTradeAmount = new BigDecimal(String.valueOf(map.get("relTradeAmount")));
            }
            traBusiness.setRelTradeAmount(relTradeAmount);
            String majorTrade = String.valueOf(map.get("majorTrade"));
            String majorType = String.valueOf(map.get("majorType"));
            traBusiness.setMajorTrade(majorTrade);
            traBusiness.setMajorType(majorType);
            String contractNo = String.valueOf(map.get("contractNo"));
            traBusiness.setContractNo(contractNo);
            traBusiness.setDelflag("1");
            traBusiness.setCreateTime(new Date());
            traBusiness.setDataSources("1");//数据来源，手工录入
            traBusinessService.insertSelective(traBusiness);

            //保存交易对手到新表
            Map map1 = new HashMap();
            map1.put("transNum", traBusiness.getTransNum());
            map1.put("relTradeAmount", relTradeAmount);
            map1.put("status", traBusiness.getStatus());
            map1.put("traSubName", traBusiness.getTraSubName());
            saveTraCounterparty(traRelList, map1);
            return resultMap;
        }
        return null;
    }
    /**
     * 保存利益转移
     * @param map
     * @param filterList
     * @param traRelList
     */
    private Map saveTraInterest(Map map,List<Filter> filterList,List<TraRelInfo> traRelList) throws SQLException {
        Map resultMap = new HashMap();
        TraInterest traInterest = new TraInterest();
        traInterest.setTranType(Dict.TRAN_TYPE_04);
        //校验是否已经存在该笔交易
        TraInterest existTraInterest = traInterestService.get(filterList);
        if(existTraInterest!=null){
            String status = existTraInterest.getStatus();
            if(Dict.APPLY_STATUS_2.equals(status)||Dict.APPLY_STATUS_4.equals(status)||Dict.APPLY_STATUS_6.equals(status)||Dict.APPLY_STATUS_7.equals(status)){
                logger.info("该交易信息已存在，且状态不为可编辑状态！");
                resultMap.put("resultCode","9999");
                resultMap.put("msg","失败，该交易信息已存在，且状态不为可编辑状态！");
                return resultMap;
            }

            /**
             * 根据添加的交易对手列表，去第一个赋值给交易信息里面的交易对手信息
             */
            TraRelInfo r = traRelList.get(0);
            traInterest.setRelId(r.getRelId());
            traInterest.setRelType(r.getRelType());
            traInterest.setRelName(r.getRelName());
            traInterest.setRelNumber(r.getRelNumber());
            traInterest.setTranOpponent(r.getTranOpponent());
            traInterest.setCertType(r.getCertType());
            BigDecimal relTradeAmount = new BigDecimal("0");
            if(map.get("relTradeAmount")!=null){
                relTradeAmount = new BigDecimal(String.valueOf(map.get("relTradeAmount")));
            }
            traInterest.setRelTradeAmount(relTradeAmount);
            traInterest.setTradeAmount(r.getTradeAmount());
            traInterestService.updateSelective(traInterest);
            String transNum = traInterestService.get(traInterest.getPid()).getTransNum();
            //更新关联交易-交易对手信息表
            map.put("transNum",transNum);
            updateTraCounterparty(traRelList,map);
        }else{
            //生成交易编号
            traInterest.setTransNum(BatchNumberUtil.makeBatchNumber());
            //默认国家定价现金交易为否
            traInterest.setIsDealPrice(Dict.SF_0);
            traInterest.setTradeAmount(new BigDecimal(0));
            traInterest.setRelInfoListStr("[]");
            traInterest.setIsHoldingSubsidiary(Dict.SF_0);
            //添加交易主体名称
            String tenantId = String.valueOf(map.get("tenantId"));
            traInterest.setTraSubName(Commons.getTenantNameByTenantId(tenantId));

            TraRelInfo r = traRelList.get(0);
            traInterest.setRelId(r.getRelId());
            traInterest.setRelType(r.getRelType());
            traInterest.setRelName(r.getRelName());
            traInterest.setRelNumber(r.getRelNumber());
            traInterest.setTranOpponent(r.getTranOpponent());
            traInterest.setCertType(r.getCertType());
            BigDecimal relTradeAmount = new BigDecimal("0");
            if(map.get("relTradeAmount")!=null){
                relTradeAmount = new BigDecimal(String.valueOf(map.get("relTradeAmount")));
            }
            traInterest.setRelTradeAmount(relTradeAmount);
            traInterest.setTradeAmount(r.getTradeAmount());
            String majorTrade = String.valueOf(map.get("majorTrade"));
            String majorType = String.valueOf(map.get("majorType"));
            traInterest.setMajorTrade(majorTrade);
            traInterest.setMajorType(majorType);
            String contractNo =String.valueOf(map.get("contractNo"));
            traInterest.setContractNo(contractNo);
            traInterest.setDelflag("1");
            traInterest.setCreateTime(new Date());
            traInterest.setDataSources("1");//数据来源，手工录入
            traInterestService.insertSelective(traInterest);

            //保存交易对手到新表
            Map map1 = new HashMap();
            map1.put("transNum",traInterest.getTransNum());
            map1.put("relTradeAmount",relTradeAmount);
            map1.put("status",traInterest.getStatus());
            map1.put("traSubName",traInterest.getTraSubName());
            saveTraCounterparty(traRelList,map1);
        }
        return resultMap;
    }

    /**
     * 提供货物服务类
     * @param map
     * @param filterList
     * @param traRelList
     */
    private Map saveTraService(Map map,List<Filter> filterList,List<TraRelInfo> traRelList) throws SQLException {
        Map resultMap = new HashMap();
        TraService traService = new TraService();
        traService.setTranType(Dict.TRAN_TYPE_05);
        //校验是否已经存在该笔交易
        TraService exsitTraService = traServiceService.get(filterList);
        if(exsitTraService!=null){
            String status = exsitTraService.getStatus();
            if(Dict.APPLY_STATUS_2.equals(status)||Dict.APPLY_STATUS_4.equals(status)||Dict.APPLY_STATUS_6.equals(status)||Dict.APPLY_STATUS_7.equals(status)){
                logger.info("该交易信息已存在，且状态不为可编辑状态！");
                resultMap.put("resultCode","9999");
                resultMap.put("msg","失败，该交易信息已存在，且状态不为可编辑状态！");
                return resultMap;
            }
            /**
             * 根据添加的交易对手列表，去第一个赋值给交易信息里面的交易对手信息
             */
            TraRelInfo r = traRelList.get(0);
            traService.setRelId(r.getRelId());
            traService.setRelType(r.getRelType());
            traService.setRelName(r.getRelName());
            traService.setRelNumber(r.getRelNumber());
            traService.setTranOpponent(r.getTranOpponent());
            traService.setCertType(r.getCertType());
            BigDecimal relTradeAmount = new BigDecimal("0");
            if(map.get("relTradeAmount")!=null){
                relTradeAmount = new BigDecimal(String.valueOf(map.get("relTradeAmount")));
            }
            traService.setRelTradeAmount(relTradeAmount);
            traService.setTradeAmount(r.getTradeAmount());
            traServiceService.updateSelective(traService);
            String transNum = traServiceService.get(traService.getPid()).getTransNum();
            //更新关联交易-交易对手信息表
            map.put("transNum",transNum);
            updateTraCounterparty(traRelList,map);
        }else{
            //生成交易编号
            traService.setTransNum(BatchNumberUtil.makeBatchNumber());
            //默认国家定价现金交易为否
            traService.setIsDealPrice(Dict.SF_0);
            traService.setTradeAmount(new BigDecimal(0));
            traService.setRelInfoListStr("[]");
            traService.setIsHoldingSubsidiary(Dict.SF_0);
            //添加交易主体名称
            String tenantId = String.valueOf(map.get("tenantId"));
            traService.setTraSubName(Commons.getTenantNameByTenantId(tenantId));

            TraRelInfo r = traRelList.get(0);
            traService.setRelId(r.getRelId());
            traService.setRelType(r.getRelType());
            traService.setRelName(r.getRelName());
            traService.setRelNumber(r.getRelNumber());
            traService.setCertType(r.getCertType());
            traService.setTradeAmount(r.getTradeAmount());
            BigDecimal relTradeAmount = new BigDecimal("0");
            if(map.get("relTradeAmount")!=null){
                relTradeAmount = new BigDecimal(String.valueOf(map.get("relTradeAmount")));
            }
            traService.setRelTradeAmount(relTradeAmount);
            traService.setTenantId(tenantId);
            String contractNo =String.valueOf(map.get("contractNo"));
            traService.setContractNo(contractNo);
            String majorTrade = String.valueOf(map.get("majorTrade"));
            String majorType = String.valueOf(map.get("majorType"));
            traService.setMajorTrade(majorTrade);
            traService.setMajorType(majorType);
            traService.setDelflag("1");
            traService.setCreateTime(new Date());
            traServiceService.insertSelective(traService);

            //保存交易对手到新表
            Map map1 = new HashMap();
            map1.put("transNum",traService.getTransNum());
            map1.put("relTradeAmount",relTradeAmount);
            map1.put("status",traService.getStatus());
            map1.put("traSubName",traService.getTraSubName());
            saveTraCounterparty(traRelList,map1);
        }
        return resultMap;
    }

    /**
     * 保存其他类
     * @param map
     * @param filterList
     * @param traRelList
     */
    private Map saveTraOther(Map map,List<Filter> filterList,List<TraRelInfo> traRelList) throws SQLException {
        Map resultMap = new HashMap();

        TraOther traOther = new TraOther();
        traOther.setTranType(Dict.TRAN_TYPE_06);
        //校验是否已经存在该笔交易
        TraOther existTraOther = traOtherService.get(filterList);
        if(existTraOther!=null){
            String status = existTraOther.getStatus();
            if(Dict.APPLY_STATUS_2.equals(status)||Dict.APPLY_STATUS_4.equals(status)||Dict.APPLY_STATUS_6.equals(status)||Dict.APPLY_STATUS_7.equals(status)){
                logger.info("该交易信息已存在，且状态不为可编辑状态！");
                resultMap.put("resultCode","9999");
                resultMap.put("msg","失败，该交易信息已存在，且状态不为可编辑状态！");
                return resultMap;
            }

            TraRelInfo r = traRelList.get(0);
            traOther.setRelId(r.getRelId());
            traOther.setRelType(r.getRelType());
            traOther.setRelName(r.getRelName());
            traOther.setRelNumber(r.getRelNumber());
            traOther.setTranOpponent(r.getTranOpponent());
            traOther.setCertType(r.getCertType());
            traOther.setTradeAmount(r.getTradeAmount());
            traOtherService.updateSelective(traOther);
            String transNum = traOtherService.get(traOther.getPid()).getTransNum();

            //更新关联交易-交易对手信息表
            map.put("transNum",transNum);
            updateTraCounterparty(traRelList,map);
        }else{
            //生成交易编号
            traOther.setTransNum(BatchNumberUtil.makeBatchNumber());
            //默认国家定价现金交易为否
            traOther.setIsDealPrice(Dict.SF_0);
            traOther.setTradeAmount(new BigDecimal(0));
            traOther.setRelInfoListStr("[]");
            traOther.setIsHoldingSubsidiary(Dict.SF_0);
            //添加交易主体名称
            //添加交易主体名称
            String tenantId = String.valueOf(map.get("tenantId"));
            traOther.setTraSubName(Commons.getTenantNameByTenantId(tenantId));

            TraRelInfo r = traRelList.get(0);
            traOther.setRelId(r.getRelId());
            traOther.setRelType(r.getRelType());
            traOther.setRelName(r.getRelName());
            traOther.setRelNumber(r.getRelNumber());
            traOther.setTranOpponent(r.getTranOpponent());
            traOther.setCertType(r.getCertType());
            BigDecimal relTradeAmount = new BigDecimal("0");
            if(map.get("relTradeAmount")!=null){
                relTradeAmount = new BigDecimal(String.valueOf(map.get("relTradeAmount")));
            }
            traOther.setRelTradeAmount(relTradeAmount);
            traOther.setTradeAmount(r.getTradeAmount());
            String majorTrade = String.valueOf(map.get("majorTrade"));
            String majorType = String.valueOf(map.get("majorType"));
            traOther.setMajorTrade(majorTrade);
            traOther.setMajorType(majorType);
            String contractNo =String.valueOf(map.get("contractNo"));
            traOther.setContractNo(contractNo);
            traOther.setDelflag("1");
            traOther.setCreateTime(new Date());
            traOther.setDataSources("1");//数据来源，手工录入
            traOtherService.insertSelective(traOther);

            //保存交易对手到新表
            Map map1 = new HashMap();
            map1.put("transNum",traOther.getTransNum());
            map1.put("relTradeAmount",relTradeAmount);
            map1.put("status",traOther.getStatus());
            map1.put("traSubName",traOther.getTraSubName());
            saveTraCounterparty(traRelList,map1);
        }
        return resultMap;
    }

    /**
     * 循环插入交易对手信息
     * @param traRelList
     * @param map
     */
    private void saveTraCounterparty(List<TraRelInfo> traRelList,Map map){
        for(TraRelInfo tra:traRelList){
            TraCounterparty traCounterparty = new TraCounterparty();
            BeanUtils.copyProperties(tra, traCounterparty);
            traCounterparty.setTransNum(String.valueOf(map.get("transNum")));
            traCounterparty.setModifiedTime(new Date());
            traCounterparty.setTotalTradeAmount(tra.getTotolTradeAmount());
            traCounterparty.setRelTradeAmount(new BigDecimal(String.valueOf(map.get("relTradeAmount"))));
            traCounterparty.setTradeAmount(tra.getTradeAmount());
            traCounterparty.setTotalAssets(tra.getTotalAssets());
            traCounterparty.setCertType(tra.getCertType());
            traCounterparty.setRelNumber(tra.getRelNumber());
            traCounterparty.setStatus("1");
            traCounterparty.setTraSubName(String.valueOf(map.get("raSubName")));
            traCounterparty.setTotalNotCleanTradeAmount(tra.getTotalNotCleanTradeAmount());
            traCounterparty.setCreateTime(new Date());
            traCounterparty.setDelflag("1");
            traCounterparty.setTraSubName(map.get("traSubName").toString());
            traCounterpartyService.insertSelective(traCounterparty);
        }
    }

    /**
     * 循环更新交易对手信息
     * @param traRelList
     * @param map
     */
    private void updateTraCounterparty(List<TraRelInfo> traRelList,Map map){
        for(TraRelInfo tra:traRelList){
            TraCounterparty traCounterparty = new TraCounterparty();
            BeanUtils.copyProperties(tra, traCounterparty);
            traCounterparty.setTransNum(String.valueOf(map.get("transNum")));
            traCounterparty.setModifiedTime(new Date());
            traCounterparty.setTotalTradeAmount(tra.getTotolTradeAmount());
            traCounterparty.setRelTradeAmount(new BigDecimal(String.valueOf(map.get("relTradeAmount"))));
            traCounterparty.setTradeAmount(tra.getTradeAmount());
            traCounterparty.setTotalAssets(tra.getTotalAssets());
            traCounterparty.setCertType(tra.getCertType());
            traCounterparty.setRelNumber(tra.getRelNumber());
            traCounterparty.setStatus("1");
            traCounterparty.setTraSubName(String.valueOf(map.get("raSubName")));
            traCounterparty.setTotalNotCleanTradeAmount(tra.getTotalNotCleanTradeAmount());
            traCounterparty.setCreateTime(new Date());
            traCounterparty.setDelflag("1");
            traCounterparty.setMajorTrade(tra.getMajorTrade());
            traCounterparty.setMajorType(tra.getMajorType());
            traCounterpartyService.updateSelective(traCounterparty);
        }
    }
}