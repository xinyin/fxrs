package com.yzj.jep.app.fPaas.server;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.fPaaS1.core.IFPaaS1Handler;
import com.fPaaS1.core.Message;
import com.yzj.jep.app.bus.service.TraEnclosureService;
import com.yzj.jep.app.holder.SpringHolder;
import com.yzj.jep.app.ins.model.*;
import com.yzj.jep.app.ins.service.*;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.rules.drools5.service.ChkLogService;
import com.yzj.jep.rules.drools5.service.PackagesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SelEtransactionHandle implements IFPaaS1Handler<Message> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SelEtransactionHandle.class);
    private ChkLogService chkLogService;
    private PackagesService packagesService;


    private TraFundsService traFundsService;

    private TraServiceService traServiceService;

    private TraInterestService traInterestService;

    private TraBusinessService traBusinessService;

    private TraInvestmentService traInvestmentService;

    private TraOtherService traOtherService;
    /**
     * 关联交易审批状态查询
     */
    @Override
    public Message handle(Message message) {


        Map maps = (Map) message.getTarget().getBodys();
        JSONObject data = new JSONObject(maps);
        LOGGER.info("参数:[{}]", data);
        Map map = new HashMap<String,Object>();
        try{
            String contractNo = (String) data.get("contractNo");
            String applyStatus = "1";
            //运用资金审批状态
            TraFunds traFunds = new TraFunds();
            traFunds.setContractNo(contractNo);
            traFundsService = SpringContextHolder.getBean(TraFundsService.class);
            List<TraFunds> traFundsList = traFundsService.select(traFunds);
            for (TraFunds a:traFundsList) {
                if (!"10".equals(a.getStatus())&&("0001".equals(a.getTraSubName())||"0001-ZZHK".equals(a.getTraSubName()))){
                    applyStatus = "0";
                }
            }
            //服务审批状态
            TraService traService = new TraService();
            traService.setContractNo(contractNo);
            traServiceService = SpringContextHolder.getBean(TraServiceService.class);
            List<TraService> traServiceList = traServiceService.select(traService);
            for (TraService a:traServiceList) {
                if (!"10".equals(a.getStatus())&&("0001".equals(a.getTraSubName())||"0001-ZZHK".equals(a.getTraSubName()))){
                    applyStatus = "0";
                }
            }
            //利益审批状态
            TraInterest traInterest = new TraInterest();
            traInterest.setContractNo(contractNo);
            traInterestService = SpringContextHolder.getBean(TraInterestService.class);
            List<TraInterest> traInterestList = traInterestService.select(traInterest);
            for (TraInterest a:traInterestList) {
                if (!"10".equals(a.getStatus())&&("0001".equals(a.getTraSubName())||"0001-ZZHK".equals(a.getTraSubName()))){
                    applyStatus = "0";
                }
            }
            //保险业务审批状态
            TraBusiness traBusiness = new TraBusiness();
            traBusiness.setContractNo(contractNo);
            traBusinessService = SpringContextHolder.getBean(TraBusinessService.class);
            List<TraBusiness> traBusinessList = traBusinessService.select(traBusiness);
            for (TraBusiness a:traBusinessList) {
                if (!"10".equals(a.getStatus())&&("0001".equals(a.getTraSubName())||"0001-ZZHK".equals(a.getTraSubName()))){
                    applyStatus = "0";
                }
            }
            //投资入股审批状态
            TraInvestment traInvestment = new TraInvestment();
            traInvestment.setContractNo(contractNo);
            traInvestmentService = SpringContextHolder.getBean(TraInvestmentService.class);
            List<TraInvestment> traInvestmentList = traInvestmentService.select(traInvestment);
            for (TraInvestment a:traInvestmentList) {
                if (!"10".equals(a.getStatus())&&("0001".equals(a.getTraSubName())||"0001-ZZHK".equals(a.getTraSubName()))){
                    applyStatus = "0";
                }
            }
            //其他类
            TraOther traOther = new TraOther();
            traOther.setContractNo(contractNo);
            traOtherService = SpringContextHolder.getBean(TraOtherService.class);
            List<TraOther> traOtherList = traOtherService.select(traOther);
            for (TraOther a:traOtherList) {
                if (!"10".equals(a.getStatus())&&("0001".equals(a.getTraSubName())||"0001-ZZHK".equals(a.getTraSubName()))){
                    applyStatus = "0";
                }
            }
            if (traInvestmentList.size() == 0 && traFundsList.size() == 0 && traBusinessList.size() == 0 && traInterestList.size() == 0 && traServiceList.size() ==0 && traOtherList.size() == 0 ){
                map.put("resultCode", "1002");
                map.put("msg", "该合同号下没有对应交易");
            }else {
                map.put("CHNL_ID",maps.get("chnlId"));
                map.put("TRANS_CODE","selEtransaction");
                map.put("contractNo",contractNo);
                map.put("applyStatus",applyStatus);
                map.put("resultCode", "0000");
                map.put("msg", "成功");
            }

        }catch (NullPointerException e){
            map.put("resultCode","1001");
            map.put("msg","失败");
        }catch (JSONException e){
            map.put("resultCode","1001");
            map.put("msg","失败");
        }catch (Exception e){
            map.put("resultCode","9999");
            map.put("msg","失败");
        }finally {
            message.getTarget().setBody(map);
            return message;
        }

    }

}
