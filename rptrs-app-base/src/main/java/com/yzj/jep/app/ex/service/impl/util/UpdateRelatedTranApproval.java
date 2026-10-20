package com.yzj.jep.app.ex.service.impl.util;

import cn.hutool.core.util.ObjectUtil;
import com.yzj.jep.app.ins.model.TraBusiness;
import com.yzj.jep.app.ins.model.TraFunds;
import com.yzj.jep.app.ins.model.TraInterest;
import com.yzj.jep.app.ins.model.TraService;
import com.yzj.jep.app.ins.service.TraBusinessService;
import com.yzj.jep.app.ins.service.TraFundsService;
import com.yzj.jep.app.ins.service.TraInterestService;
import com.yzj.jep.app.ins.service.TraServiceService;
import com.yzj.jep.app.util.Dict;
import com.yzj.jep.core.holder.SpringContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author by Liqiang
 * @Date 2022/11/2
 * @Description 审批通过时更新关联交易数据状态
 */
public class UpdateRelatedTranApproval {

    private static final Logger logger = LoggerFactory.getLogger(UpdateRelatedTranApproval.class);
    private static final TraFundsService TRA_FUNDS_SERVICE = SpringContextHolder.getBean(TraFundsService.class);
    private static final TraServiceService TRA_SERVICE_SERVICE = SpringContextHolder.getBean(TraServiceService.class);
    private static final TraInterestService TRA_INTEREST_SERVICE = SpringContextHolder.getBean(TraInterestService.class);
    private static final TraBusinessService TRA_BUSINESS_SERVICE = SpringContextHolder.getBean(TraBusinessService.class);

    public static void processData(String type, Integer pid) {
        try {
            if (Dict.TRAN_TYPE_NAME_FUNDS.equals(type)) {
                /*
                    资金运用
                 */
                TraFunds traFunds = TRA_FUNDS_SERVICE.get(pid);
                traFunds.setModifiedTime(new Date());
                if (StringUtils.isNotBlank(traFunds.getPracTradeAmount()) && ObjectUtil.isNotEmpty(traFunds.getTranDate()) && ObjectUtil.isNotEmpty(traFunds.getBusStartTime())) {
                    // 实际交易金额不为空 + 合同签署日期不为空 + 业务起始日期 = 已归档
                    traFunds.setStatus("6");
                }else if(StringUtils.isBlank(traFunds.getPracTradeAmount()) && ObjectUtil.isNotEmpty(traFunds.getTranDate()) && ObjectUtil.isNotEmpty(traFunds.getBusStartTime())){
                    // 实际交易金额为空 + 合同签署日期不为空 + 业务起始日期不为空 = 履行中
                    traFunds.setStatus("7");
                }else {
                    // 实际交易金额为空 + 合同签署日期为空 + 业务起始日期为空 = 待签订
                    // 实际交易金额不为空 + 合同签署日期为空 + 业务起始日期为空 = 待签订
                    traFunds.setStatus("8");
                }
                // 审议类型为逐笔时需要补录附件
                if("3".equals(traFunds.getConsiderType())){
                    boolean b = TRA_FUNDS_SERVICE.calibrationAccessories(traFunds.getTransNum());
                    if(!b){
                        traFunds.setStatus("2");
                    }
                }

                int i = TRA_FUNDS_SERVICE.updateSelective(traFunds);
                if (i > 0) {
                    logger.debug("资金运用流程执行结束状态已更改为：【" + traFunds.getStatus() + "】");
                } else {
                    logger.debug("资金运用流程执行结束状态更改失败");
                }
            } else if (Dict.TRAN_TYPE_NAME_BUSINESS.equals(type)) {
                /*
                 * 保险业务及其他类
                 * */
                TraBusiness traBusiness = TRA_BUSINESS_SERVICE.get(pid);
                traBusiness.setModifiedTime(new Date());
                if (StringUtils.isNotBlank(traBusiness.getPracTradeAmount()) && ObjectUtil.isNotEmpty(traBusiness.getContractDate()) && ObjectUtil.isNotEmpty(traBusiness.getBusStartTime())) {
                    // 实际交易金额不为空 + 合同签署日期不为空 + 业务起始日期 = 已归档
                    traBusiness.setStatus("6");
                }else if(StringUtils.isBlank(traBusiness.getPracTradeAmount()) && ObjectUtil.isNotEmpty(traBusiness.getContractDate()) && ObjectUtil.isNotEmpty(traBusiness.getBusStartTime())){
                    // 实际交易金额为空 + 合同签署日期不为空 + 业务起始日期不为空 = 履行中
                    traBusiness.setStatus("7");
                }else {
                    // 实际交易金额为空 + 合同签署日期为空 + 业务起始日期为空 = 待签订
                    // 实际交易金额不为空 + 合同签署日期为空 + 业务起始日期为空 = 待签订
                    traBusiness.setStatus("8");
                }
                // 审议类型为逐笔时需要补录附件
                if("3".equals(traBusiness.getConsiderType())){
                    boolean b = TRA_FUNDS_SERVICE.calibrationAccessories(traBusiness.getTransNum());
                    if(!b){
                        traBusiness.setStatus("2");
                    }
                }

                int i = TRA_BUSINESS_SERVICE.updateSelective(traBusiness);
                if (i > 0) {
                    logger.debug("保险业务及其他类关联交易流程执行结束状态已更改为：【" + traBusiness.getStatus() + "】");
                } else {
                    logger.debug("保险业务流程执行结束状态更改为待确认失败");
                }
            } else if (Dict.TRAN_TYPE_NAME_INTEREST.equals(type)) {
                /*
                 * 利益转移
                 * */
                TraInterest traInterest = TRA_INTEREST_SERVICE.get(pid);
                traInterest.setModifiedTime(new Date());
                if (StringUtils.isNotBlank(traInterest.getPracTradeAmount()) && ObjectUtil.isNotEmpty(traInterest.getContractDate()) && ObjectUtil.isNotEmpty(traInterest.getBusStartTime())) {
                    // 实际交易金额不为空 + 合同签署日期不为空 + 业务起始日期 = 已归档
                    traInterest.setStatus("6");
                }else if(StringUtils.isBlank(traInterest.getPracTradeAmount()) && ObjectUtil.isNotEmpty(traInterest.getContractDate()) && ObjectUtil.isNotEmpty(traInterest.getBusStartTime())){
                    // 实际交易金额为空 + 合同签署日期不为空 + 业务起始日期不为空 = 履行中
                    traInterest.setStatus("7");
                }else {
                    // 实际交易金额为空 + 合同签署日期为空 + 业务起始日期为空 = 待签订
                    // 实际交易金额不为空 + 合同签署日期为空 + 业务起始日期为空 = 待签订
                    traInterest.setStatus("8");
                }
                // 审议类型为逐笔时需要补录附件
                if("3".equals(traInterest.getConsiderType())){
                    boolean b = TRA_FUNDS_SERVICE.calibrationAccessories(traInterest.getTransNum());
                    if(!b){
                        traInterest.setStatus("2");
                    }
                }

                int i = TRA_INTEREST_SERVICE.updateSelective(traInterest);
                if (i > 0) {
                    logger.debug("利益转移类关联交易流程执行结束状态已更改为：【" + traInterest.getStatus() + "】");
                } else {
                    logger.debug("利益转移流程执行结束状态更改为待确认失败");
                }
            } else if (Dict.TRAN_TYPE_NAME_SERVICE.equals(type)) {
                /*
                 * 提供服务
                 * */
                TraService traService = TRA_SERVICE_SERVICE.get(pid);

                traService.setModifiedTime(new Date());
                if (StringUtils.isNotBlank(traService.getPracTradeAmount()) && ObjectUtil.isNotEmpty(traService.getContractDate()) && ObjectUtil.isNotEmpty(traService.getBusStartTime())) {
                    // 实际交易金额不为空 + 合同签署日期不为空 + 业务起始日期 = 已归档
                    traService.setStatus("6");
                }else if(StringUtils.isBlank(traService.getPracTradeAmount()) && ObjectUtil.isNotEmpty(traService.getContractDate()) && ObjectUtil.isNotEmpty(traService.getBusStartTime())){
                    // 实际交易金额为空 + 合同签署日期不为空 + 业务起始日期不为空 = 履行中
                    traService.setStatus("7");
                }else {
                    // 实际交易金额为空 + 合同签署日期为空 + 业务起始日期为空 = 待签订
                    // 实际交易金额不为空 + 合同签署日期为空 + 业务起始日期为空 = 待签订
                    traService.setStatus("8");
                }
                // 审议类型为逐笔时需要补录附件
                if("3".equals(traService.getConsiderType())){
                    boolean b = TRA_FUNDS_SERVICE.calibrationAccessories(traService.getTransNum());
                    if(!b){
                        traService.setStatus("2");
                    }
                }
                int i = TRA_SERVICE_SERVICE.updateSelective(traService);
                if (i > 0) {
                    logger.debug("服务类类关联交易流程执行结束状态已更改为：【" + traService.getStatus() + "】");
                } else {
                    logger.debug("提供服务流程执行结束状态更改为待确认失败");
                }
            } else {
                logger.debug("找不到对应类型-------dataHandle");
            }
        } catch (Exception e) {
            logger.error("流程审批完成后业务处理逻辑出错，错误信息：", e);
        }
    }
}
