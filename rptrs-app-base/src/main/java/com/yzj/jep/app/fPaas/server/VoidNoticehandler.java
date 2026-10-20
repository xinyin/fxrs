package com.yzj.jep.app.fPaas.server;


import com.alibaba.fastjson.JSONException;
import com.fPaaS1.core.IFPaaS1Handler;
import com.fPaaS1.core.Message;
import com.yzj.jep.app.base.constant.IsStatusDict;
import com.yzj.jep.app.holder.SpringHolder;
import com.yzj.jep.app.ins.model.*;
import com.yzj.jep.app.ins.service.*;
import com.yzj.jep.orm.entity.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 合同作废通知接口
 */
public class VoidNoticehandler implements IFPaaS1Handler<Message> {
    private static final Logger logger = LoggerFactory.getLogger(com.fpaas.handl.GatewayServerHandler.class);

    private TraInterestService traInterestService;
    private TraFundsService traFundsService;
    private TraBusinessService traBusinessService;
    private TraInvestmentService traInvestmentService;
    private TraServiceService traServiceService;
    private TraOtherService traOtherService;

    @Override
    public Message handle(Message message) throws Exception {
        Map map = null;
        String contractNo = null;
        traInvestmentService = SpringHolder.getBean(TraInvestmentService.class);
        traFundsService = SpringHolder.getBean(TraFundsService.class);
        traBusinessService = SpringHolder.getBean(TraBusinessService.class);
        traInterestService = SpringHolder.getBean(TraInterestService.class);
        traServiceService = SpringHolder.getBean(TraServiceService.class);
        traOtherService = SpringHolder.getBean(TraOtherService.class);

        Map tips = new HashMap<String, Object>();

        try {
            map = (Map) message.getTarget().getBodys();
            contractNo = (String) map.get("contractNo");
            if(null == contractNo || "".equals(contractNo)){
                tips.put("resultCode", "1001");
                tips.put("msg", "请求参数[contractNo]缺失");
                tips.put("isRevoke", "0");
                logger.error("撤销失败，请求参数缺失：");
                return message;
            }
            List<Filter> filters = new Filter.Builder().eq("contractNo", contractNo).build();
            List<TraInvestment> trainvestments = traInvestmentService.select(filters);
            List<TraFunds> traFunds = traFundsService.select(filters);
            List<TraBusiness> traBusinesses = traBusinessService.select(filters);
            List<TraInterest> traInterests = traInterestService.select(filters);
            List<TraService> traServices = traServiceService.select(filters);
            List<TraOther> TraOthers = traOtherService.select(filters);

            for (TraInvestment tra : trainvestments) {
                tra.setDelflag(IsStatusDict.Y.getValue());
                traInvestmentService.update(tra);
            }
            for (TraFunds tra : traFunds) {
                tra.setDelflag(IsStatusDict.Y.getValue());
                traFundsService.update(tra);
            }
            for (TraBusiness tra : traBusinesses) {
                tra.setDelflag(IsStatusDict.Y.getValue());
                traBusinessService.update(tra);
            }
            for (TraInterest tra : traInterests) {
                tra.setDelflag(IsStatusDict.Y.getValue());
                traInterestService.update(tra);
            }
            for (TraService tra : traServices) {
                tra.setDelflag(IsStatusDict.Y.getValue());
                traServiceService.update(tra);
            }

            for (TraOther tra : TraOthers) {
                tra.setDelflag(IsStatusDict.Y.getValue());
                traOtherService.update(tra);
            }


            if (trainvestments.size() == 0 && traFunds.size() == 0 && traBusinesses.size() == 0 && traInterests.size() == 0 && traServices.size() ==0 && TraOthers.size() ==0){
                tips.put("resultCode", "1002");
                tips.put("isRevoke", "0");
                tips.put("msg", "没有合同号为["+contractNo+"]的关联交易");
            }else {
                tips.put("resultCode", "0000");
                tips.put("isRevoke", "1");
                tips.put("msg", "成功");
            }

        } catch (NullPointerException e) {
            tips.put("resultCode", "1001");
            tips.put("msg", "请求参数缺失");
            tips.put("isRevoke", "0");
            logger.error("撤销失败，请求参数缺失："+e);
        } catch (Exception e) {
            tips.put("resultCode", "9999");
            tips.put("msg", "失败，未知异常");
            tips.put("isRevoke", "0");
            logger.error("撤销失败，未知异常："+e);
        } finally {
            tips.put("CHNL_ID",map.get("chnlId"));
            tips.put("TRANS_CODE","voidNotice");
            tips.put("contractNo",contractNo);
            message.getTarget().setBody(tips);
            return message;
        }
    }
}
