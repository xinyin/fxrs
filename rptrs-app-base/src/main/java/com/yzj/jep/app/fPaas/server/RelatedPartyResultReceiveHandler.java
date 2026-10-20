package com.yzj.jep.app.fPaas.server;

import com.alibaba.fastjson.JSONObject;
import com.fPaaS1.core.IFPaaS1Handler;
import com.fPaaS1.core.Message;
import com.yzj.jep.app.holder.SpringHolder;
import com.yzj.jep.app.ins.mapper.RelTradeInterfaceMapper;
import com.yzj.jep.app.util.Dict;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * OA用印申请审批信息回调处理接口
 */
public class RelatedPartyResultReceiveHandler implements IFPaaS1Handler<Message> {
    private static final Logger logger = LoggerFactory.getLogger(RelatedPartyResultReceiveHandler.class);

    private RelTradeInterfaceMapper relTradeInterfaceMapper;
    @Override
    public Message handle(Message message) throws Exception {
        relTradeInterfaceMapper = SpringHolder.getBean(RelTradeInterfaceMapper.class);
        Map tips = new HashMap<String, Object>();
        Map map = (Map) message.getTarget().getBodys();

        try {
            JSONObject dataJson = new JSONObject(map);
            //传入参数
            logger.info("---------OA用印申请审批信息回调处理接口----传入参数为-------------：" + dataJson.toString());

            String tenantId =  dataJson.getString("tenantId");
            String contractNo =  dataJson.getString("contractNo");
            String approveStatus = dataJson.getString("OAStatus");
            //交易类型
            String tranType = dataJson.getString("tranType");
            //判断驳回状态，如果是驳回更新数据库数据状态
            if("1".equals(approveStatus)){
                Map<String, Object> param = new HashMap<>();
                switch (tranType){
                    case Dict.TRAN_TYPE_01 : param.put("tableName", "ins_tra_investment");
                    case Dict.TRAN_TYPE_02 :param.put("tableName", "ins_tra_funds");
                    case Dict.TRAN_TYPE_03 :param.put("tableName", "ins_tra_business");
                    case Dict.TRAN_TYPE_04 :param.put("tableName", "ins_tra_interest");
                    case Dict.TRAN_TYPE_05 :param.put("tableName", "ins_tra_service");
                    case Dict.TRAN_TYPE_06 :param.put("tableName", "ins_tra_other");
                }
                /**审批状态*/
                param.put("tenantId", tenantId);
                //状态改为 7 失效
                param.put("status", Dict.APPLY_STATUS_7);
                param.put("contractNo",contractNo);
                relTradeInterfaceMapper.updateTableStatus(param);
            }
            tips.put("tranType",tranType);
            tips.put("tenantId",map.get("tenantId"));
            tips.put("OAStatus",map.get("approveStatus"));
            tips.put("contractNo",map.get("contractNo"));

            tips.put("CHNL_ID",map.get("chnlId"));
            tips.put("TRANS_CODE",map.get("TRANS_CODE"));
            tips.put("resultCode","0000");
            tips.put("msg","成功");
        }catch (Exception e){
            logger.error("OA用印申请审批信息回调处理接口调用失败，错误信息：",e);
            tips.put("CHNL_ID",map.get("chnlId"));
            tips.put("TRANS_CODE",map.get("TRANS_CODE"));
            tips.put("resultCode", "9999");
            tips.put("msg", "失败，未知异常");
        }
        message.getTarget().setBody(tips);
        return message;
    }
}