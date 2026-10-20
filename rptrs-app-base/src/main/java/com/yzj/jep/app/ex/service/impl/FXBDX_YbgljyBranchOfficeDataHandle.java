package com.yzj.jep.app.ex.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.yzj.jep.app.bus.model.TraCredit;
import com.yzj.jep.app.bus.model.TraUsecredit;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.app.ex.service.impl.util.UpdateRelatedTranApproval;
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
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 关联交易处理页面 分公司一般关联交易
 */
@Transactional
@DataHandler("FXBDX_YBGLJY_Company")
public class FXBDX_YbgljyBranchOfficeDataHandle implements ProcessDataHandler {
    private static Logger logger = LoggerFactory.getLogger(FXBDX_YbgljyBranchOfficeDataHandle.class);
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
    ApprovalPushService approvalPushService;

    @Override
    public void handleData(String data) throws WorkflowException {

        approvalPushService = SpringContextHolder.getBean(ApprovalPushService.class);
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
            String type = (String) json.get("TransactionTtype");
            String jsonString = json.getString("pid");
            Integer pid = Integer.parseInt(jsonString);
            UpdateRelatedTranApproval.processData(type, pid);
            String uniqueCode = json.getString("uniqueCode");
            String sourceSystem = json.getString("sourceSystem");
            if(Dict.SOURCE_SYSTEM_OA.equals(sourceSystem)){
                approvalPushService.pushResult(uniqueCode,"0");
            }
        } catch (NumberFormatException e) {
            logger.error("关联交易流程审批结束业务处理异常，异常信息：", e);
            e.printStackTrace();
        } catch (ServiceException e) {
            logger.error("关联交易流程审批结束业务处理异常，异常信息：", e);
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

    /**
     * 业务数据处理
     *
     * @param type
     * @param pid
     * @param json
     */
    private void processData(String type, Integer pid, JSONObject json) {
        try {
            if (Dict.TRAN_TYPE_NAME_INVESTMENT.equals(type)) {
                /*
                 * 投资入股，判断是否逐笔审批  审议类型(1:可免予审议，2:一般审议，3逐笔审议)，是状态改为待确认，否状态改为已归档
                 * */
                TraInvestment traInvestment = traInvestmentService.get(pid);
                String transNum = traInvestment.getTransNum();
                if (Dict.CONSIDER_TYPE_ZBSY.equals(traInvestment.getConsiderType())) {
                    traInvestment.setStatus(Dict.APPLY_STATUS_2);
                    int i = traInvestmentService.updateSelective(traInvestment);
                    if (i > 0) {
                        logger.debug("投资入股流程执行结束状态已更改为---待确认");
                        //更新状态，累计清零
                        InsUtil.updateTraCounterpartyStatus(transNum,Dict.APPLY_STATUS_2);
                        if(Dict.MAJOR_TYPE_LJ.equals(traInvestment.getMajorType())){
                            InsUtil.grandTradeZero(transNum, type);
                        }
                    } else {
                        logger.debug("投资入股流程执行结束状态更改为待确认失败");
                    }

                } else {
                    traInvestment.setStatus(Dict.APPLY_STATUS_6);
                    int i = traInvestmentService.updateSelective(traInvestment);
                    if (i > 0) {
                        logger.debug("投资入股流程执行结束状态已更改为---已归档");
                        InsUtil.updateTraCounterpartyStatus(transNum,Dict.APPLY_STATUS_6);
                    } else {
                        logger.debug("投资入股流程执行结束状态更改为已归档失败");
                    }
                }

            } else if (Dict.TRAN_TYPE_NAME_FUNDS.equals(type)) {
                /*
                    资金运用， 审议类型(1:可免予审议，2:一般审议，3逐笔审议)，是状态改为待确认，否状态改为已归档
                 */
                TraFunds traFundsJson = traFundsService.get(pid);
                String transNum = traFundsJson.getTransNum();
                traFundsJson.setStatus("2");
                int i = traFundsService.updateSelective(traFundsJson);
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
               /* if (Dict.CONSIDER_TYPE_ZBSY.equals(traFundsJson.getConsiderType())) {
                    traFundsJson.setStatus(Dict.APPLY_STATUS_2);
                    int i = traFundsService.updateSelective(traFundsJson);
                    if (i > 0) {
                        logger.debug("资金运用流程执行结束状态已更改为---待确认");
                        //通过交易编号查询该关联交易交易对手
                        String transNum = traFundsJson.getTransNum();
                        InsUtil.grandTradeZero(transNum, type);
                    } else {
                        logger.debug("资金运用流程执行结束状态更改为待确认失败");
                    }

                } else {
                    traFundsJson.setStatus(Dict.APPLY_STATUS_6);
                    int i = traFundsService.updateSelective(traFundsJson);
                    if (i > 0) {
                        logger.debug("资金运用流程执行结束状态已更改为---已归档");
                    } else {
                        logger.debug("资金运用流程执行结束状态更改为已归档失败");
                    }
                }
*/
            } else if (Dict.TRAN_TYPE_NAME_BUSINESS.equals(type)) {
                /*
                 * 保险业务，判断是否逐笔审批  审议类型(1:可免予审议，2:一般审议，3逐笔审议)，是状态改为待确认，否状态改为已归档
                 * */
                TraBusiness traBusinessJson = traBusinessService.get(pid);
                String transNum = traBusinessJson.getTransNum();
                if (Dict.CONSIDER_TYPE_ZBSY.equals(traBusinessJson.getConsiderType())) {
                    traBusinessJson.setStatus(Dict.APPLY_STATUS_2);
                    int i = traBusinessService.updateSelective(traBusinessJson);
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
                    traBusinessJson.setStatus(Dict.APPLY_STATUS_6);
                    int i = traBusinessService.updateSelective(traBusinessJson);
                    if (i > 0) {
                        logger.debug("保险业务程执行结束状态已更改为---已归档");
                        InsUtil.updateTraCounterpartyStatus(transNum,Dict.APPLY_STATUS_6);
                    } else {
                        logger.debug("保险业务程执行结束状态更改为已归档失败");
                    }
                }

            } else if (Dict.TRAN_TYPE_NAME_INTEREST.equals(type)) {
                /*
                 * 利益转移，判断是否逐笔审批  审议类型(1:可免予审议，2:一般审议，3逐笔审议)，是状态改为待确认，否状态改为已归档
                 * */
                TraInterest traInterestJson = traInterestService.get(pid);
                String transNum = traInterestJson.getTransNum();
                if (Dict.CONSIDER_TYPE_ZBSY.equals(traInterestJson.getConsiderType())) {
                    traInterestJson.setStatus(Dict.APPLY_STATUS_2);
                    int i = traInterestService.updateSelective(traInterestJson);
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
                    traInterestJson.setStatus(Dict.APPLY_STATUS_6);
                    int i = traInterestService.updateSelective(traInterestJson);
                    if (i > 0) {
                        logger.debug("利益转移程执行结束状态已更改为---已归档");
                        InsUtil.updateTraCounterpartyStatus(transNum,Dict.APPLY_STATUS_6);
                    } else {
                        logger.debug("利益转移程执行结束状态更改为已归档失败");
                    }
                }

            } else if (Dict.TRAN_TYPE_NAME_SERVICE.equals(type)) {
                /*
                 * 提供服务，判断是否逐笔审批  审议类型(1:可免予审议，2:一般审议，3逐笔审议)，是状态改为待确认，否状态改为已归档
                 * */
                TraService traServiceJson = traServiceService.get(pid);
                String transNum = traServiceJson.getTransNum();
                if (Dict.CONSIDER_TYPE_ZBSY.equals(traServiceJson.getConsiderType())) {
                    traServiceJson.setStatus(Dict.APPLY_STATUS_2);
                    int i = traServiceService.updateSelective(traServiceJson);
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
                    traServiceJson.setStatus(Dict.APPLY_STATUS_6);
                    int i = traServiceService.updateSelective(traServiceJson);
                    if (i > 0) {
                        logger.debug("提供服务程执行结束状态已更改为---已归档");
                        InsUtil.updateTraCounterpartyStatus(transNum,Dict.APPLY_STATUS_6);
                    } else {
                        logger.debug("提供服务程执行结束状态更改为已归档失败");
                    }
                }


            } else if (Dict.TRAN_TYPE_NAME_OTHER.equals(type)) {
                /*
                 * 其他类，判断是否逐笔审批  审议类型(1:可免予审议，2:一般审议，3逐笔审议)，是状态改为待确认，否状态改为已归档
                 * */
                TraOther traOtherJson = traOtherService.get(pid);
                String transNum = traOtherJson.getTransNum();
                if (Dict.CONSIDER_TYPE_ZBSY.equals(traOtherJson.getConsiderType())) {
                    traOtherJson.setStatus(Dict.APPLY_STATUS_2);
                    int i = traOtherService.updateSelective(traOtherJson);
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
                    traOtherJson.setStatus(Dict.APPLY_STATUS_6);
                    int i = traOtherService.updateSelective(traOtherJson);
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
        } catch (Exception e) {
            logger.error("流程审批完成后业务处理逻辑出错，错误信息：", e);
        }
    }
}
