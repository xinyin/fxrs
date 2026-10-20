package com.yzj.jep.app.fPaas.server;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.fPaaS1.core.IFPaaS1Handler;
import com.fPaaS1.core.Message;
import com.yzj.jep.app.holder.SpringHolder;
import com.yzj.jep.app.ins.mapper.RelTradeInterfaceMapper;
import com.yzj.jep.app.ins.model.TraCounterparty;
import com.yzj.jep.app.ins.model.TraRelInfo;
import com.yzj.jep.app.ins.service.TraCounterpartyService;
import com.yzj.jep.app.util.Dict;
import com.yzj.jep.orm.entity.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 阳光保险添加：关联交易审批结果查询
 */
public class RelatedPartyQueryApproveStatusHandler implements IFPaaS1Handler<Message> {
    private static final Logger logger = LoggerFactory.getLogger(RelatedPartyQueryApproveStatusHandler.class);

    private RelTradeInterfaceMapper relTradeInterfaceMapper;
    private TraCounterpartyService traCounterpartyService;
    @Override
    public Message handle(Message message) throws Exception {
        relTradeInterfaceMapper = SpringHolder.getBean(RelTradeInterfaceMapper.class);
        traCounterpartyService = SpringHolder.getBean(TraCounterpartyService.class);
        Map tips = new HashMap<String, Object>();
        Map map = (Map) message.getTarget().getBodys();

        try {
            JSONObject dataJson = new JSONObject(map);
            //传入参数
            logger.info("---------进入关联交易审批结果查询接口----传入参数为-------------：" + dataJson.toString());

            String tenantId =  dataJson.getString("tenantId");
            String contractNo =  dataJson.getString("contractNo");
            //交易类型
            String tranType = dataJson.getString("tranType");
            //交易对手列表
            String relList = dataJson.getString("relList");
            /**解析json*/
            List<TraRelInfo> traRelInfoList = JSONObject.parseArray(relList, TraRelInfo.class);
            Map<String, Object> param = new HashMap<>();
            /**合同编号*/
            param.put("contractNo", contractNo);
            param.put("tenantId",tenantId);
            String approveStatus="";//审批结果
            String isSame="";//交易是否一致，1是0否
            switch (tranType){
                case Dict.TRAN_TYPE_01 :param.put("tableName", "ins_tra_investment");
                case Dict.TRAN_TYPE_02 :param.put("tableName", "ins_tra_funds");
                case Dict.TRAN_TYPE_03 :param.put("tableName", "ins_tra_business");
                case Dict.TRAN_TYPE_04 :param.put("tableName", "ins_tra_interest");
                case Dict.TRAN_TYPE_05 :param.put("tableName", "ins_tra_service");
                case Dict.TRAN_TYPE_06 :param.put("tableName", "ins_tra_other");
            }
            Map map1 = queryTableMap(param);
            if(CollectionUtil.isEmpty(map1)){//查不到交易信息直接返回不一致
                isSame =Dict.SF_0;
                tips.put("relList",traRelInfoList);
                tips.put("isSame",isSame);
                tips.put("tranType",tranType);
                tips.put("tenantId",tenantId);
                tips.put("approveStatus",approveStatus);
                tips.put("contractNo",contractNo);
                tips.put("CHNL_ID",map.get("chnlId"));
                tips.put("TRANS_CODE",map.get("TRANS_CODE"));
                tips.put("resultCode","0000");
                tips.put("msg","成功");
                message.getTarget().setBody(tips);
                return message;
            }
            //如果已通过审批
            if(Dict.APPLY_STATUS_2.equals(approveStatus)||Dict.APPLY_STATUS_6.equals(approveStatus)){
                //查询交易对手
                String transNum = String.valueOf(map1.get("transNum"));
                //获取交易对手列表信息
                List<Filter> filterList = new ArrayList<>();
                filterList.add(new Filter("transNum",transNum));
                List<TraCounterparty> counterpartyList = traCounterpartyService.select(filterList);
                if (counterpartyList == null ||traRelInfoList==null|| counterpartyList.size()!= traRelInfoList.size()) {
                    isSame =Dict.SF_0;
                    tips.put("relList",traRelInfoList);
                    tips.put("isSame",isSame);
                    tips.put("tranType",tranType);
                    tips.put("tenantId",tenantId);
                    tips.put("approveStatus",approveStatus);
                    tips.put("contractNo",contractNo);
                    tips.put("CHNL_ID",map.get("chnlId"));
                    tips.put("TRANS_CODE",map.get("TRANS_CODE"));
                    tips.put("resultCode","0000");
                    tips.put("msg","成功");
                    message.getTarget().setBody(tips);
                    return message;
                }
                BigDecimal tradeAmount = new BigDecimal("0");
                boolean flag = false;
                for(TraRelInfo tri:traRelInfoList){
                    for(TraCounterparty tra:counterpartyList){
                        if(!(tri.getRelType().equals(tra.getRelType())&&tri.getRelName().equals(tra.getRelName())&&
                                tri.getRelNumber().equals(tra.getRelNumber())&&tri.getCertType().equals(tra.getCertType()))){
                            flag =true;
                        }
                        tradeAmount = tradeAmount.add(tri.getTradeAmount());
                    }
                }
                if(flag||!map1.get("relTradeAmount").equals(tradeAmount)){
                    isSame =Dict.SF_0;
                    tips.put("relList",traRelInfoList);
                    tips.put("isSame",isSame);
                    tips.put("tranType",tranType);
                    tips.put("tenantId",tenantId);
                    tips.put("approveStatus",approveStatus);
                    tips.put("contractNo",contractNo);
                    tips.put("CHNL_ID",map.get("chnlId"));
                    tips.put("TRANS_CODE",map.get("TRANS_CODE"));
                    tips.put("resultCode","0000");
                    tips.put("msg","成功");
                    message.getTarget().setBody(tips);
                    return message;
                }
            }
            tips.put("relList",traRelInfoList);
            tips.put("tranType",tranType);
            tips.put("tenantId",tenantId);
            tips.put("status",approveStatus);
            tips.put("contractNo",contractNo);
            tips.put("CHNL_ID",map.get("chnlId"));
            tips.put("TRANS_CODE",map.get("TRANS_CODE"));
            tips.put("resultCode","0000");
            tips.put("msg","成功");
        }catch (Exception e){
            logger.error("关联方审批结果查询调用失败，错误信息：",e);
            tips.put("CHNL_ID",map.get("chnlId"));
            tips.put("TRANS_CODE",map.get("TRANS_CODE"));
            tips.put("resultCode", "9999");
            tips.put("msg", "失败，未知异常");
        }
        message.getTarget().setBody(tips);
        return message;
    }

    /**
     * 通用类查询表状态
     * @param param
     * @return
     */
    private Map queryTableMap(Map<String, Object> param){
        return relTradeInterfaceMapper.queryTableMap(param);
    }
}