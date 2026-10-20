package com.yzj.jep.app.ex.service.impl;


import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.bus.service.TraAssetService;
import com.yzj.jep.app.bus.service.TraCreditService;
import com.yzj.jep.app.ins.model.*;
import com.yzj.jep.app.ins.service.*;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.client.ProcessFormHandler;
import com.yzj.jep.workflow.client.annotation.FormHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static com.yzj.jep.app.util.ConfidentialInfoUtil.idEncrypt;
import static com.yzj.jep.app.util.ConfidentialInfoUtil.otherIdEncrypt;

/**
 * 关联交易处理页面 重大统一关联交易
 */
@FormHandler("FXBDX_ZDTY")
public class FXBDX_ZdtyFormHandle implements ProcessFormHandler {
    Logger logger = LoggerFactory.getLogger(FXBDX_ZdtyFormHandle.class);
    TraCreditService traCreditService;
    TraAssetService traAssetService;
    RelPsnService relPsnService;
    TraInvestmentService traInvestmentService;
    TraFundsService traFundsService;
    TraBusinessService traBusinessService;
    TraInterestService traInterestService;
    TraServiceService traServiceService;
    TraOtherService traOtherService;
    TraCounterpartyService traCounterpartyService;

    @Override
    public ModelAndView processForm(String nodekey, String data, String opt, ModelAndView model)
            throws WorkflowException {

        traCreditService = SpringContextHolder.getBean(TraCreditService.class);
        traAssetService = SpringContextHolder.getBean(TraAssetService.class);
        relPsnService = SpringContextHolder.getBean(RelPsnService.class);
        //投资入股类
        traInvestmentService = SpringContextHolder.getBean(TraInvestmentService.class);
        //资金运用
        traFundsService = SpringContextHolder.getBean(TraFundsService.class);
        //保险业务
        traBusinessService = SpringContextHolder.getBean(TraBusinessService.class);
        //利益转移
        traInterestService = SpringContextHolder.getBean(TraInterestService.class);
        //提供服务
        traServiceService = SpringContextHolder.getBean(TraServiceService.class);
        //其他类
        traOtherService = SpringContextHolder.getBean(TraOtherService.class);
        //交易对手服务
        traCounterpartyService =SpringContextHolder.getBean(TraCounterpartyService.class);

        JSONObject json = JSONObject.parseObject(data);
        Integer pid = json.getInteger("pid");
        String type = json.getString("TransactionTtype");
//        Integer pid = Integer.parseInt(jsonString);
//        logger.debug("处理主键[{}]", pid);
        //审批页面标识
        model.addObject("optNoe","handler");
        if("investment".equals(type)){
            TraInvestment investment = traInvestmentService.get(pid);
            //获取交易对手列表信息
            List<Filter> filterList = new ArrayList<>();
            filterList.add(new Filter("transNum",investment.getTransNum()));
            List<TraCounterparty> counterpartyList =traCounterpartyService.select(filterList);
            if (null!= investment&&counterpartyList!=null&&counterpartyList.size()>0){
                investment.setRelInfoListStr(JSONObject.toJSONString(counterpartyList));
            }
            if("PSN".equals(investment.getRelType())){
                //查询关联方证件类型
                if(org.apache.commons.lang3.StringUtils.isNotEmpty(investment.getRelNumber())){
                    RelPsn relPsn = relPsnService.selectByIdentifyNumber(investment.getRelNumber());
                    if(ObjectUtil.isNotEmpty(relPsn)){
                        if("1".equals(relPsn.getIdentifyType())){
                            investment.setRelNumber(idEncrypt(investment.getRelNumber()));
                        }else{
                            investment.setRelNumber(otherIdEncrypt(investment.getRelNumber()));
                        }
                    }
                }
            }
            model.addObject("traInvestment",investment );
            model.addObject("opt","view");
            model.setViewName("ins/traInvestmentEdit");
        }else if("funds".equals(type)){
            TraFunds funds = traFundsService.get(pid);
            //获取交易对手列表信息
            List<Filter> filterList = new ArrayList<>();
            filterList.add(new Filter("transNum",funds.getTransNum()));
            List<TraCounterparty> counterpartyList =traCounterpartyService.select(filterList);
            if (null!= funds&&counterpartyList!=null&&counterpartyList.size()>0){
                funds.setRelInfoListStr(JSONObject.toJSONString(counterpartyList));
            }
            if("PSN".equals(funds.getRelType())){
                //查询关联方证件类型
                if(org.apache.commons.lang3.StringUtils.isNotEmpty(funds.getRelNumber())){
                    RelPsn relPsn = relPsnService.selectByIdentifyNumber(funds.getRelNumber());
                    if(ObjectUtil.isNotEmpty(relPsn)){
                        if("1".equals(relPsn.getIdentifyType())){
                            funds.setRelNumber(idEncrypt(funds.getRelNumber()));
                        }else{
                            funds.setRelNumber(otherIdEncrypt(funds.getRelNumber()));
                        }
                    }
                }
            }
            model.addObject("traFunds",funds);
            model.addObject("opt","view");
            model.addObject("sendOpt","view");
            model.setViewName("ins/traFundsEdit");
        }else if("business".equals(type)){
            TraBusiness business = traBusinessService.get(pid);
            //获取交易对手列表信息
            List<Filter> filterList = new ArrayList<>();
            filterList.add(new Filter("transNum",business.getTransNum()));
            List<TraCounterparty> counterpartyList =traCounterpartyService.select(filterList);
            if (null!= business&&counterpartyList!=null&&counterpartyList.size()>0){
                business.setRelInfoListStr(JSONObject.toJSONString(counterpartyList));
            }

            if("PSN".equals(business.getRelType())){
                //查询关联方证件类型
                if(org.apache.commons.lang3.StringUtils.isNotEmpty(business.getRelNumber())){
                    RelPsn relPsn = relPsnService.selectByIdentifyNumber(business.getRelNumber());
                    if(ObjectUtil.isNotEmpty(relPsn)){
                        if("1".equals(relPsn.getIdentifyType())){
                            business.setRelNumber(idEncrypt(business.getRelNumber()));
                        }else{
                            business.setRelNumber(otherIdEncrypt(business.getRelNumber()));
                        }
                    }
                }
            }
            model.addObject("traBusiness",business);
            model.addObject("opt","view");
            model.addObject("optNoe","handler");
            model.setViewName("ins/traBusinessEdit");
        }else if("interest".equals(type)){
            TraInterest interest = traInterestService.get(pid);
            //获取交易对手列表信息
            List<Filter> filterList = new ArrayList<>();
            filterList.add(new Filter("transNum",interest.getTransNum()));
            List<TraCounterparty> counterpartyList =traCounterpartyService.select(filterList);
            if (null!= interest&&counterpartyList!=null&&counterpartyList.size()>0){
                interest.setRelInfoListStr(JSONObject.toJSONString(counterpartyList));
            }

            if("PSN".equals(interest.getRelType())){
                //查询关联方证件类型
                if(org.apache.commons.lang3.StringUtils.isNotEmpty(interest.getRelNumber())){
                    RelPsn relPsn = relPsnService.selectByIdentifyNumber(interest.getRelNumber());
                    if(ObjectUtil.isNotEmpty(relPsn)){
                        if("1".equals(relPsn.getIdentifyType())){
                            interest.setRelNumber(idEncrypt(interest.getRelNumber()));
                        }else{
                            interest.setRelNumber(otherIdEncrypt(interest.getRelNumber()));
                        }
                    }
                }
            }
            model.addObject("traInterest",interest);
            model.addObject("opt","view");
            model.addObject("optNoe","handler");
            model.setViewName("ins/traInterestEdit");
        }else if("service".equals(type)){
            TraService service = traServiceService.get(pid);
            //获取交易对手列表信息
            List<Filter> filterList = new ArrayList<>();
            filterList.add(new Filter("transNum",service.getTransNum()));
            List<TraCounterparty> counterpartyList =traCounterpartyService.select(filterList);
            if (null!= service&&counterpartyList!=null&&counterpartyList.size()>0){
                service.setRelInfoListStr(JSONObject.toJSONString(counterpartyList));
            }

            if("PSN".equals(service.getRelType())){
                //查询关联方证件类型
                if(org.apache.commons.lang3.StringUtils.isNotEmpty(service.getRelNumber())){
                    RelPsn relPsn = relPsnService.selectByIdentifyNumber(service.getRelNumber());
                    if(ObjectUtil.isNotEmpty(relPsn)){
                        if("1".equals(relPsn.getIdentifyType())){
                            service.setRelNumber(idEncrypt(service.getRelNumber()));
                        }else{
                            service.setRelNumber(otherIdEncrypt(service.getRelNumber()));
                        }
                    }
                }
            }
            model.addObject("traService",service);
            model.addObject("opt","view");
            model.addObject("optNoe","handler");
            model.setViewName("ins/traServiceEdit");
        }else if("other".equals(type)){
            TraOther other = traOtherService.get(pid);
            //获取交易对手列表信息
            List<Filter> filterList = new ArrayList<>();
            filterList.add(new Filter("transNum",other.getTransNum()));
            List<TraCounterparty> counterpartyList =traCounterpartyService.select(filterList);
            if (null!= other&&counterpartyList!=null&&counterpartyList.size()>0){
                other.setRelInfoListStr(JSONObject.toJSONString(counterpartyList));
            }

            if("PSN".equals(other.getRelType())){
                //查询关联方证件类型
                if(org.apache.commons.lang3.StringUtils.isNotEmpty(other.getRelNumber())){
                    RelPsn relPsn = relPsnService.selectByIdentifyNumber(other.getRelNumber());
                    if(ObjectUtil.isNotEmpty(relPsn)){
                        if("1".equals(relPsn.getIdentifyType())){
                            other.setRelNumber(idEncrypt(other.getRelNumber()));
                        }else{
                            other.setRelNumber(otherIdEncrypt(other.getRelNumber()));
                        }
                    }
                }
            }
            model.addObject("traOther",other);
            model.addObject("opt","view");
            model.setViewName("ins/traOtherEdit");
        }else{
            throw new WorkflowException("缺少类型参数type");
        }


        return model;
    }

    //身份证前六后四脱敏
    public static String idEncrypt(String id) {
        if (StringUtils.isEmpty(id) || (id.length() < 10)) {
            return id;
        }
        return id.replaceAll("(?<=\\w{6})\\w(?=\\w{4})", "*");
    }
    //自然人非身份证脱敏
    public static String otherIdEncrypt(String id) {
        if (StringUtils.isEmpty(id) || (id.length() < 8)) {
            return id;
        }
        return id.replaceAll("(?<=\\w{3})\\w(?=\\w{2})", "*");
    }
}
