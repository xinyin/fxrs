package com.yzj.jep.app.fPaas.server;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.fPaaS1.core.IFPaaS1Handler;
import com.fPaaS1.core.Message;
import com.yzj.jep.app.ins.model.TraBusiness;
import com.yzj.jep.app.ins.model.TraFunds;
import com.yzj.jep.app.ins.model.TraInterest;
import com.yzj.jep.app.ins.model.TraService;
import com.yzj.jep.app.ins.service.TraBusinessService;
import com.yzj.jep.app.ins.service.TraFundsService;
import com.yzj.jep.app.ins.service.TraInterestService;
import com.yzj.jep.app.ins.service.TraServiceService;
import com.yzj.jep.app.record.model.InterfaceRecord;
import com.yzj.jep.app.record.service.TraInterfaceRecordService;
import com.yzj.jep.app.util.Dict;
import com.yzj.jep.app.util.TerminationProcessUtil;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author by Liqiang
 * @Date 2022/10/21
 * @Description oa系统, 交易废弃时，通过此接口同步废弃关联交易系统对应的交易
 */
public class DelRelTransaction implements IFPaaS1Handler<Message> {

    private static final TraInterestService TRA_INTEREST_SERVICE = SpringContextHolder.getBean(TraInterestService.class);
    private static final TraBusinessService TRA_BUSINESS_SERVICE = SpringContextHolder.getBean(TraBusinessService.class);
    private static final TraServiceService TRA_SERVICE_SERVICE = SpringContextHolder.getBean(TraServiceService.class);
    private static final TraFundsService TRA_FUNDS_SERVICE = SpringContextHolder.getBean(TraFundsService.class);
    private static final TraInterfaceRecordService traInterfaceRecordService = SpringContextHolder.getBean(TraInterfaceRecordService .class);

    private static final Logger logger = LoggerFactory.getLogger(DelRelTransaction.class);

    private static final String MENNO = "OA系统删除该关联交易数据";

    @Override
    public Message handle(Message message) throws Exception {
        Map<String, Object> tips = new HashMap<>(15);
        Map<String, Object> map = (Map<String, Object>) message.getTarget().getBodys();
        try {
            JSONObject dataJson = new JSONObject(map);
            //传入参数
            logger.info("---------进入关联交易废弃接口开始----传入参数为-------------：" + dataJson);
            if (CollUtil.isEmpty(dataJson)) {
                returnData(tips, message, "接收的数据为空!", "", "1001");
                return message;
            }
            String chnlId = dataJson.getString("chnlId");
            /* 校验必填项 */
            if (CharSequenceUtil.isBlank(dataJson.getString("contractNo"))) {
                returnData(tips, message, "contractNo字段值为空!", chnlId, "1001");
                return message;
            }
            String contractNo = dataJson.getString("contractNo").trim();

            /* 去数据库中根据合同编号进行数据查询，并进行发送邮件 */
            List<Filter> filters = new ArrayList<>();
            filters.add(new Filter("contractNo", Operator.EQ, contractNo));
            filters.add(new Filter("delflag", Operator.EQ, "1"));

            List<TraFunds> traFundsList = TRA_FUNDS_SERVICE.select(filters);
            if (CollUtil.isNotEmpty(traFundsList)) {
                traFundsList.forEach(traFunds -> {
                    terminate(traFunds.getStatus(), traFunds.getProcInstId());
                    TraFunds funds = new TraFunds();
                    funds.setStatusComment(MENNO);
                    funds.setPid(traFunds.getPid());
                    funds.setModifiedTime(new Date());
                    funds.setDelflag("0");
                    TRA_FUNDS_SERVICE.updateSelective(funds);

                });
                traFundsList.clear();
                returnData(tips, message, "成功", chnlId, "0000");
                return message;
            }

            List<TraService> traServiceList = TRA_SERVICE_SERVICE.select(filters);
            if (CollUtil.isNotEmpty(traServiceList)) {

                traServiceList.forEach(traService -> {
                    terminate(traService.getStatus(), traService.getProcInstId());
                    TraService service = new TraService();
                    service.setPid(traService.getPid());
                    service.setDelflag(Dict.SF_0);
                    service.setStatusComment(MENNO);
                    service.setModifiedTime(new Date());
                    TRA_SERVICE_SERVICE.updateSelective(service);
                });
                traServiceList.clear();
                returnData(tips, message, "成功", chnlId, "0000");
                return message;
            }

            List<TraInterest> traInterestList = TRA_INTEREST_SERVICE.select(filters);
            if (CollUtil.isNotEmpty(traInterestList)) {

                traInterestList.forEach(traInterest -> {
                    terminate(traInterest.getStatus(), traInterest.getProcInstId());

                    TraInterest interest = new TraInterest();
                    interest.setPid(traInterest.getPid());
                    interest.setDelflag(Dict.SF_0);
                    interest.setStatusComment(MENNO);
                    interest.setModifiedTime(new Date());
                    TRA_INTEREST_SERVICE.updateSelective(interest);
                });

                traInterestList.clear();
                returnData(tips, message, "成功", chnlId, "0000");
                return message;
            }

            List<TraBusiness> traBusinessList = TRA_BUSINESS_SERVICE.select(filters);
            if (CollUtil.isNotEmpty(traBusinessList)) {

                traBusinessList.forEach(traBusiness -> {
                    terminate(traBusiness.getStatus(), traBusiness.getProcInstId());

                    TraBusiness business = new TraBusiness();
                    business.setPid(traBusiness.getPid());
                    business.setDelflag(Dict.SF_0);
                    business.setStatusComment(MENNO);
                    business.setModifiedTime(new Date());
                    TRA_BUSINESS_SERVICE.updateSelective(business);
                });

                traBusinessList.clear();
                returnData(tips, message, "成功", chnlId, "0000");
                return message;
            }
            returnData(tips, message, "该合同编号【" + contractNo + "】关联交易在本系统中不存在！", chnlId, "1002");
            return message;
        } catch (Exception e) {
            logger.error("该合同编号【" + new JSONObject(map).getString("contractNo") + "的关联交易删除失败，失败信息：【" + e.getMessage() + "】");
            logger.error("交易异常：", e);
            returnData(tips, message, e.getMessage(), ObjectUtil.isEmpty(map.get("CHNL_ID")) ? "" : (String) map.get("CHNL_ID"), "9999");
            return message;
        }finally {
            interFaceRecord(tips,map);
        }
    }

    /**
     * 返回非参数
     */
    private static void returnData(Map<String, Object> tips, Message message, String msg, String chnlId, String resultCode) {
        tips.put("msg", msg);
        tips.put("TRANS_CODE", "delRelTransaction");
        tips.put("resultCode", resultCode);
        tips.put("_TRAN_CODE", "delRelTransaction");
        if (CharSequenceUtil.isNotBlank(chnlId)) {
            tips.put("CHNL_ID", chnlId);
        }
        message.getTarget().setHeaders(tips);
        message.getTarget().setBody(tips);
    }

    /**
     * 终止流程
     *
     * @param status
     * @param procInstId
     */
    public void terminate(String status, String procInstId) {
        /* 状态为审批中的需要终止流程后修改关联交易状态为已删除标识 */
        if (Dict.APPLY_STATUS_4.equals(status)) {
            if (!CharSequenceUtil.isBlank(procInstId)) {
                TerminationProcessUtil.terminate(procInstId, MENNO);
            }
        }
    }
    /**
     * 关联交易接口记录
     * @param tips
     * @param map
     */
    public void interFaceRecord(Map<String, Object> tips,Map<String, Object> map){
        try {
            InterfaceRecord interfaceRecord = new InterfaceRecord();
            String chnlId = map.get("chnlId").toString();
            String tranCode = map.get("tranCode").toString();
            interfaceRecord.setChnlId(chnlId);
            interfaceRecord.setTransCode(tranCode);
            interfaceRecord.setBusName("关联交易同步废弃接口");
            interfaceRecord.setType("1");
            interfaceRecord.setCode(String.valueOf(tips.get("resultCode")));
            if(Dict.INTERFACE_RETURN_CODE.equals(tips.get("resultCode"))){
                interfaceRecord.setResult("1");
            }else {
                interfaceRecord.setResult("2");
            }
            traInterfaceRecordService.setInterfaceRecord(interfaceRecord);
        }catch (Exception e){
            logger.error("关联交易删除记录错误：{}",e);
        }
    }
}
