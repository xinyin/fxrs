package com.yzj.jep.app.fPaas.server;


import com.fPaaS1.core.IFPaaS1Handler;
import com.fPaaS1.core.Message;
import com.yzj.jep.app.contract.service.FileContractService;
import com.yzj.jep.app.holder.SpringHolder;
import com.yzj.jep.app.ins.model.*;
import com.yzj.jep.app.ins.service.*;
import com.yzj.jep.orm.entity.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 合同签订信息推送接口
 */
public class InformationPushHandler implements IFPaaS1Handler<Message> {
    private static final Logger logger = LoggerFactory.getLogger(com.fpaas.handl.GatewayServerHandler.class);

    private TraInvestmentService traInvestmentService;
    private TraFundsService traFundsService;
    private TraInterestService traInterestService;
    private TraBusinessService traBusinessService;
    private TraServiceService traServiceService;
    private TraOtherService traOtherService ;
    private FileContractService fileContractService;

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Message handle(Message message) throws Exception {
        traInvestmentService = SpringHolder.getBean(TraInvestmentService.class);
        traFundsService = SpringHolder.getBean(TraFundsService.class);
        traBusinessService = SpringHolder.getBean(TraBusinessService.class);
        traInterestService = SpringHolder.getBean(TraInterestService.class);
        traServiceService = SpringHolder.getBean(TraServiceService.class);
        traOtherService = SpringHolder.getBean(TraOtherService.class);
        fileContractService = SpringHolder.getBean(FileContractService.class);


        Map tips = new HashMap<String,Object>();

        Map map = (Map) message.getTarget().getBodys();

        try {
            //合同编号
            String contractNo = (String) map.get("contractNo");
            String signTIme = (String) map.get("signTime");


            List<Filter> filters = new Filter.Builder().eq("contractNo", contractNo).build();
            List<TraInvestment> trainvestments = traInvestmentService.select(filters);
            List<TraFunds> traFunds = traFundsService.select(filters);
            List<TraBusiness> traBusinesses = traBusinessService.select(filters);
            List<TraInterest> traInterests = traInterestService.select(filters);
            List<TraService> traServices = traServiceService.select(filters);
            List<TraOther> traOthers = traOtherService.select(filters);
            for (TraInvestment tra : trainvestments) {
                tra.setTranDateString(signTIme);
                tra.setModifiedTime(new Date());
                if ("0001".equals(tra.getTraSubName()) || "0001-ZZHK".equals(tra.getTraSubName())){
                    tra.setStatus("6");
                    tra.setStatusComment("审批通过");
                }
                traInvestmentService.update(tra);
            }
            for (TraFunds tra : traFunds) {
                tra.setTranDateString(signTIme);
                tra.setModifiedTime(new Date());
                if ("0001".equals(tra.getTraSubName()) || "0001-ZZHK".equals(tra.getTraSubName())){
                    tra.setStatus("6");
                    tra.setStatusComment("审批通过");
                }
                traFundsService.update(tra);
            }
            for (TraBusiness tra : traBusinesses) {
                tra.setTranDateString(signTIme);
                tra.setModifiedTime(new Date());
                if ("0001".equals(tra.getTraSubName()) || "0001-ZZHK".equals(tra.getTraSubName())){
                    tra.setStatus("6");
                    tra.setStatusComment("审批通过");
                }
                traBusinessService.update(tra);
            }
            for (TraInterest tra : traInterests) {
                tra.setTranDateString(signTIme);
                tra.setModifiedTime(new Date());
                if ("0001".equals(tra.getTraSubName()) || "0001-ZZHK".equals(tra.getTraSubName())){
                    tra.setStatus("6");
                    tra.setStatusComment("审批通过");
                }
                traInterestService.update(tra);
            }
            for (TraService tra : traServices) {
                tra.setTranDateString(signTIme);
                tra.setModifiedTime(new Date());
                if ("0001".equals(tra.getTraSubName()) || "0001-ZZHK".equals(tra.getTraSubName())){
                    tra.setStatus("6");
                    tra.setStatusComment("审批通过");
                }
                traServiceService.update(tra);
            }
            for (TraOther tra : traOthers) {
                tra.setTranDateString(signTIme);
                tra.setModifiedTime(new Date());
                if ("0001".equals(tra.getTraSubName()) || "0001-ZZHK".equals(tra.getTraSubName())){
                    tra.setStatus("6");
                    tra.setStatusComment("审批通过");
                }
                traOtherService.update(tra);
            }

            tips.put("CHNL_ID",map.get("chnlId"));
            tips.put("TRANS_CODE","InformationPush");
            if (trainvestments.size() == 0 && traFunds.size() == 0 && traBusinesses.size() == 0 && traInterests.size() == 0 && traServices.size() ==0){
                tips.put("resultCode", "1002");
            }else {
                tips.put("resultCode","0000");
            }
            tips.put("msg", "成功");
            //获取合同文件
            fileContractService.getContractFiles2Relate(contractNo);
        }catch (NullPointerException e){
            tips.put("resultCode","1001");
            tips.put("msg","失败");
        }finally {
            message.getTarget().setBody(tips);
            return message;
        }

    }
}
