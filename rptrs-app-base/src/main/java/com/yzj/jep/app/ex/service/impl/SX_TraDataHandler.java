package com.yzj.jep.app.ex.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.yzj.jep.app.bus.model.TraCredit;
import com.yzj.jep.app.bus.model.TraUsecredit;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.app.ins.model.*;
import com.yzj.jep.app.ins.service.TraServiceService;
import com.yzj.jep.app.ins.service.*;
import com.yzj.jep.app.util.Dict;
import com.yzj.jep.app.util.InsUtil;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.sys.service.StaffService;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.client.ProcessDataHandler;
import com.yzj.jep.workflow.client.annotation.DataHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

//业务逻辑

@DataHandler("SX_GLJYSPLC")
public class SX_TraDataHandler implements ProcessDataHandler {
    private static Logger logger = LoggerFactory.getLogger(SX_TraDataHandler.class);
    TraServiceService traServiceService;
    TraAssetService traAssetService;
    com.yzj.jep.app.ins.service.TraOtherService traOtherService;
    TraCreditService traCreditService;
    TraFundsService traFundsService;
    RelService relService;
    RelPsnService relPsnService;
    TraUsecreditService traUsecreditService;
    StaffService staffService;
    TraInterestService traInterestService;
    TraInvestmentService traInvestmentService;
    TraBusinessService traBusinessService;

    @Override
    public void handleData(String data) throws WorkflowException {

        //提供服务类
        traServiceService = SpringContextHolder.getBean(TraServiceService.class);
        traAssetService = SpringContextHolder.getBean(TraAssetService.class);
        //其他类
        traOtherService = SpringContextHolder.getBean(com.yzj.jep.app.ins.service.TraOtherService.class);
        traCreditService = SpringContextHolder.getBean(TraCreditService.class);
        relService = SpringContextHolder.getBean(RelService.class);
        relPsnService = SpringContextHolder.getBean(RelPsnService.class);
        staffService = SpringContextHolder.getBean(StaffService.class);
        //投资入股类
        traInvestmentService = SpringContextHolder.getBean(TraInvestmentService.class);
        //资金运用类
        traFundsService = SpringContextHolder.getBean(TraFundsService.class);
        //利益转移
        traInterestService = SpringContextHolder.getBean(TraInterestService.class);
        //保险业务类
        traBusinessService = SpringContextHolder.getBean(TraBusinessService.class);
        try {
            //解析JSON串
            JSONObject json = JSONObject.parseObject(data);
            String type = (String) json.get("type");
            if ("investment".equals(type)) {
                /*
                 * 投资入股，判断是否逐笔审批  审议类型(1:可免予审议，2:可集中审议，3逐笔审议)，是状态改为待确认，否状态改为已归档
                 * */
                TraInvestment investmentJson = JSON.parseObject(data, TraInvestment.class);
                String transNum = investmentJson.getTransNum();
                TraInvestment investment = new TraInvestment();
                investment.setPid(investmentJson.getPid());
                if ("3".equals(investmentJson.getConsiderType())) {
                    investment.setStatus("2");
                    int i = traInvestmentService.updateSelective(investment);
                    if (i > 0) {
                        logger.debug("投资入股流程执行结束状态已更改为---待确认");
                        //更新状态，累计清零
                        InsUtil.updateTraCounterpartyStatus(transNum,Dict.APPLY_STATUS_2);
                        if(Dict.MAJOR_TYPE_LJ.equals(investmentJson.getMajorType())){
                            InsUtil.grandTradeZero(transNum, type);
                        }
                    } else {
                        logger.debug("投资入股流程执行结束状态更改为待确认失败");
                    }
                } else {
                    investment.setStatus("6");
                    int i = traInvestmentService.updateSelective(investment);
                    if (i > 0) {
                        logger.debug("投资入股流程执行结束状态已更改为---已归档");
                        InsUtil.updateTraCounterpartyStatus(transNum,Dict.APPLY_STATUS_6);
                    } else {
                        logger.debug("投资入股流程执行结束状态更改为已归档失败");
                    }
                }
            } else if ("funds".equals(type)) {
                /*
                    资金运用， 审议类型(1:可免予审议，2:可集中审议，3逐笔审议)，是状态改为待确认，否状态改为已归档
                 */
                TraFunds traFundsJson = JSON.parseObject(data, TraFunds.class);
                String transNum = traFundsJson.getTransNum();
                TraFunds funds = new TraFunds();
                funds.setPid(traFundsJson.getPid());
                //资金运用类不用判断是否逐笔审议，一律改为待确认
                funds.setStatus("2");
                int i = traFundsService.updateSelective(funds);
                if (i > 0) {
                    logger.debug("资金运用流程执行结束状态已更改为---待确认");
                    //更新状态，累计清零
                    InsUtil.updateTraCounterpartyStatus(transNum,Dict.APPLY_STATUS_2);
                    if(Dict.MAJOR_TYPE_LJ.equals(traFundsJson.getMajorType())){
                        InsUtil.grandTradeZero(transNum, type);
                    }
                } else {
                    logger.debug("资金运用流程执行结束状态更改为待确认失败");
                }
                /*if ("3".equals(traFundsJson.getConsiderType())) {
                    funds.setStatus("2");
                    int i = traFundsService.updateSelective(funds);
                    if (i > 0) {
                        logger.debug("资金运用流程执行结束状态已更改为---待确认");
                        //通过交易编号查询该关联交易交易对手
                        String transNum = traFundsJson.getTransNum();
                        InsUtil.grandTradeZero(transNum, type);
                    } else {
                        logger.debug("资金运用流程执行结束状态更改为待确认失败");
                    }
                } else {
                    funds.setStatus("6");
                    int i = traFundsService.updateSelective(funds);
                    if (i > 0) {
                        logger.debug("资金运用流程执行结束状态已更改为---已归档");
                    } else {
                        logger.debug("资金运用流程执行结束状态更改为已归档失败");
                    }
                }*/
            } else if ("business".equals(type)) {
                /*
                 * 保险业务，判断是否逐笔审批  审议类型(1:可免予审议，2:可集中审议，3逐笔审议)，是状态改为待确认，否状态改为已归档
                 * */
                TraBusiness traBusinessJson = JSON.parseObject(data, TraBusiness.class);
                String transNum = traBusinessJson.getTransNum();
                TraBusiness traBusiness = new TraBusiness();
                traBusiness.setPid(traBusinessJson.getPid());
                if ("3".equals(traBusinessJson.getConsiderType())) {
                    traBusiness.setStatus("2");
                    int i = traBusinessService.updateSelective(traBusiness);
                    if (i > 0) {
                        logger.debug("保险业务流程执行结束状态已更改为---待确认");
                        //更新状态，累计清零
                        InsUtil.updateTraCounterpartyStatus(transNum,Dict.APPLY_STATUS_2);
                        if(Dict.MAJOR_TYPE_LJ.equals(traBusinessJson.getMajorType())){
                            InsUtil.grandTradeZero(transNum, type);
                        }
                    } else {
                        logger.debug("保险业务流程执行结束状态更改为待确认失败");
                    }
                } else {
                    traBusiness.setStatus("6");
                    int i = traBusinessService.updateSelective(traBusiness);
                    if (i > 0) {
                        logger.debug("保险业务程执行结束状态已更改为---已归档");
                        InsUtil.updateTraCounterpartyStatus(transNum,Dict.APPLY_STATUS_6);
                    } else {
                        logger.debug("保险业务程执行结束状态更改为已归档失败");
                    }
                }
            } else if ("interest".equals(type)) {
                /*
                 * 利益转移，判断是否逐笔审批  审议类型(1:可免予审议，2:可集中审议，3逐笔审议)，是状态改为待确认，否状态改为已归档
                 * */
                TraInterest traInterestJson = JSON.parseObject(data, TraInterest.class);
                String transNum = traInterestJson.getTransNum();
                TraInterest traInterest = new TraInterest();
                traInterest.setPid(traInterestJson.getPid());
                if ("3".equals(traInterestJson.getConsiderType())) {
                    traInterest.setStatus("2");
                    int i = traInterestService.updateSelective(traInterest);
                    if (i > 0) {
                        logger.debug("利益转移流程执行结束状态已更改为---待确认");
                        //更新状态，累计清零
                        InsUtil.updateTraCounterpartyStatus(transNum,Dict.APPLY_STATUS_2);
                        if(Dict.MAJOR_TYPE_LJ.equals(traInterestJson.getMajorType())){
                            InsUtil.grandTradeZero(transNum, type);
                        }
                    } else {
                        logger.debug("利益转移流程执行结束状态更改为待确认失败");
                    }
                } else {
                    traInterest.setStatus("6");
                    int i = traInterestService.updateSelective(traInterest);
                    if (i > 0) {
                        logger.debug("利益转移程执行结束状态已更改为---已归档");
                        InsUtil.updateTraCounterpartyStatus(transNum,Dict.APPLY_STATUS_6);
                    } else {
                        logger.debug("利益转移程执行结束状态更改为已归档失败");
                    }
                }

            } else if ("service".equals(type)) {
                /*
                 * 提供服务，判断是否逐笔审批  审议类型(1:可免予审议，2:可集中审议，3逐笔审议)，是状态改为待确认，否状态改为已归档
                 * */
                com.yzj.jep.app.ins.model.TraService traServiceJson = JSON.parseObject(data, com.yzj.jep.app.ins.model.TraService.class);
                String transNum = traServiceJson.getTransNum();
                com.yzj.jep.app.ins.model.TraService traService = new com.yzj.jep.app.ins.model.TraService();
                traService.setPid(traServiceJson.getPid());
                if ("3".equals(traService.getConsiderType())) {
                    traService.setStatus("2");
                    int i = traServiceService.updateSelective(traService);
                    if (i > 0) {
                        logger.debug("提供服务流程执行结束状态已更改为---待确认");
                        //更新状态，累计清零
                        InsUtil.updateTraCounterpartyStatus(transNum,Dict.APPLY_STATUS_2);
                        if(Dict.MAJOR_TYPE_LJ.equals(traServiceJson.getMajorType())){
                            InsUtil.grandTradeZero(transNum, type);
                        }
                    } else {
                        logger.debug("提供服务流程执行结束状态更改为待确认失败");
                    }
                } else {
                    traService.setStatus("6");
                    int i = traServiceService.updateSelective(traService);
                    if (i > 0) {
                        logger.debug("提供服务程执行结束状态已更改为---已归档");
                        InsUtil.updateTraCounterpartyStatus(transNum,Dict.APPLY_STATUS_6);
                    } else {
                        logger.debug("提供服务程执行结束状态更改为已归档失败");
                    }
                }

            } else if ("other".equals(type)) {
                /*
                 * 其他类，判断是否逐笔审批  审议类型(1:可免予审议，2:可集中审议，3逐笔审议)，是状态改为待确认，否状态改为已归档
                 * */
                TraOther traOtherJson = JSON.parseObject(data, TraOther.class);
                String transNum = traOtherJson.getTransNum();
                TraOther traOther = new TraOther();
                traOther.setPid(traOtherJson.getPid());
                if ("3".equals(traOtherJson.getConsiderType())) {
                    traOther.setStatus("2");
                    int i = traOtherService.updateSelective(traOther);
                    if (i > 0) {
                        logger.debug("其他类流程执行结束状态已更改为---待确认");
                        //更新状态，累计清零
                        InsUtil.updateTraCounterpartyStatus(transNum,Dict.APPLY_STATUS_2);
                        if(Dict.MAJOR_TYPE_LJ.equals(traOtherJson.getMajorType())){
                            InsUtil.grandTradeZero(transNum, type);
                        }
                    } else {
                        logger.debug("其他类流程执行结束状态更改为待确认失败");
                    }
                } else {
                    traOther.setStatus("6");
                    int i = traOtherService.updateSelective(traOther);
                    if (i > 0) {
                        logger.debug("其他类程执行结束状态已更改为---已归档");
                        InsUtil.updateTraCounterpartyStatus(transNum,Dict.APPLY_STATUS_6);
                    } else {
                        logger.debug("其他类程执行结束状态更改为已归档失败");
                    }
                }

            } else {
                logger.debug("找不到对应类型-------dataHandle");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    private void creditProcess(String data, String memo) {
        TraCredit traCredit = JSON.parseObject(data, TraCredit.class);
        traCredit.setStatus("6");
        traCredit.setIsEffective("1");
        traCredit.setStatusComment(memo);
        traCreditService.updateSelective(traCredit);
    }

    private void useCreditProcess(String data, String memo) {
        TraUsecredit traUsecredit = JSON.parseObject(data, TraUsecredit.class);
        List<Filter> filters = Lists.newArrayList();
        String internalApplyNo = traUsecredit.getInternalApplyNo();
        filters.add(Filter.eq("internalApplyNo", internalApplyNo));
        filters.add(Filter.eq("delflag", "1"));
        TraCredit traCredit = traCreditService.get(filters);
        if ("1".equals(traUsecredit.getIsEffective())) {
            Long validityBalance = traCredit.getValidityBalance();
            BigDecimal balance = BigDecimal.valueOf(validityBalance);
            BigDecimal add = balance.add(traUsecredit.getThisTraDiff());
            traCredit.setValidityBalance(add.longValue());
        } else {
            Long validityBalance = traCredit.getValidityBalance();
            BigDecimal balance = validityBalance == null ? BigDecimal.ZERO : BigDecimal.valueOf(validityBalance);
            BigDecimal thisTraAmount = traUsecredit.getThisTraAmount();
            traCredit.setValidityBalance((balance.add(thisTraAmount)).longValue());
        }
        traCreditService.updateSelective(traCredit);
        traUsecredit.setIsEffective("1");
        traUsecredit.setStatus("6");
        traUsecredit.setStatusComment(memo);
        traUsecreditService.updateSelective(traUsecredit);
    }
}