package com.yzj.jep.app.fPaas.server;

import com.alibaba.fastjson.JSONObject;
import com.fPaaS1.core.IFPaaS1Handler;
import com.fPaaS1.core.Message;
import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.model.RelPartyVo;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.holder.SpringHolder;
import com.yzj.jep.app.util.Dict;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 阳光保险添加：关联方识别接口
 */
class RelatedPartyIdentificationHandler implements IFPaaS1Handler<Message> {
    private static final Logger logger = LoggerFactory.getLogger(com.yzj.jep.app.fPaas.server.RelatedPartyIdentificationHandler.class);
    private RelPsnService relPsnService;
    private RelOrgService relOrgService;

    @Override
    public Message handle(Message message) throws Exception {
        relOrgService = SpringHolder.getBean(RelOrgService.class);
        relPsnService = SpringHolder.getBean(RelPsnService.class);
        Map tips = new HashMap<String, Object>();
        Map map = (Map) message.getTarget().getBodys();

        try {
            JSONObject dataJson = new JSONObject(map);
            //传入参数
            logger.info("---------进入关联方识别接口----传入参数为-------------：" + dataJson.toString());

            String tenantId =  dataJson.getString("tenantId");
            String contractNo = dataJson.getString("contractNo");
            String relList = dataJson.getString("relList");
            List<RelPartyVo> dataRelPartyList = JSONObject.parseArray(relList,RelPartyVo.class);
            for (RelPartyVo r : dataRelPartyList) {
                if ("PSN".equals(r.getRelType())) {
                    RelPsn psn = new RelPsn();
                    psn.setName(r.getRelName());
                    psn.setTenantId(tenantId);
                    psn.setIdentifyType(r.getCertType());
                    psn.setIdentifyNumber(r.getRelNumber());
                    List<RelPsn> relPsnList = relPsnService.select(psn);
                    if (relPsnList != null && relPsnList.size() > 0) {
                        r.setIsRelParty(Dict.SF_1);
                    } else {
                        r.setIsRelParty(Dict.SF_0);
                    }
                }
                if ("ORG".equals(r.getRelType())) {
                    RelOrg org = new RelOrg();
                    org.setTenantId(tenantId);
                    org.setIdentifyType(r.getCertType());
                    org.setOrgName(r.getRelName());
                    org.setUniscCode(r.getRelNumber());
                    List<RelOrg> relOrgList = relOrgService.select(org);
                    if (relOrgList != null && relOrgList.size() > 0) {
                        r.setIsRelParty(Dict.SF_1);
                    }else {
                        r.setIsRelParty(Dict.SF_0);
                    }
                }
            }
            tips.put("contractNo",contractNo);
            tips.put("tenantId",tenantId);
            tips.put("CHNL_ID",map.get("chnlId"));
            tips.put("TRANS_CODE",map.get("tranCode"));
            tips.put("resultCode","0000");
            tips.put("msg","成功");
            tips.put("relList",dataRelPartyList);
            message.getTarget().setBody(tips);
        }catch (Exception e){
            logger.error("关联方识别验证接口调用失败，错误信息：",e);
        }
        return message;
    }
}