package com.yzj.jep.app.ex.service.impl;


import com.alibaba.fastjson.JSONObject;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;


/**
 * 关联交易处理页面 分公司一般关联交易
 */
@FormHandler("FXBDX_YBGLJY_Company")
public class FXBDX_YbgljyBranchOfficeFormHandle implements ProcessFormHandler {
    Logger logger = LoggerFactory.getLogger(FXBDX_YbgljyBranchOfficeFormHandle.class);
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
            model.addObject("traOther",other);
            model.addObject("opt","view");
            model.setViewName("ins/traOtherEdit");
        }else{
            throw new WorkflowException("缺少类型参数type");
        }


        return model;
    }
}
