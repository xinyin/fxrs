package com.yzj.jep.app.fPaas.server;

import com.alibaba.fastjson.JSONObject;
import com.fPaaS1.core.IFPaaS1Handler;
import com.fPaaS1.core.Message;
import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.holder.SpringHolder;
import com.yzj.jep.app.util.Dict;
import com.yzj.jep.orm.entity.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 阳光保险添加：关联方信息推送接口
 */
public class RelatedPartyInfoPushHandler implements IFPaaS1Handler<Message> {
    private static final Logger logger = LoggerFactory.getLogger(RelatedPartyInfoPushHandler.class);

    private RelPsnService relPsnService;
    private RelOrgService relOrgService;

    @Override
    public Message handle(Message message) throws Exception {
        relPsnService = SpringHolder.getBean(RelPsnService.class);
        relOrgService = SpringHolder.getBean(RelOrgService.class);

        Map tips = new HashMap<String, Object>();
        Map map = (Map) message.getTarget().getBodys();

        try {
            JSONObject dataJson = new JSONObject(map);
            //传入参数
            logger.info("---------关联方信息推送接口----传入参数为-------------：" + dataJson.toString());

            String pushType =  dataJson.getString("pushType");
            String tenantId =  dataJson.getString("tenantId");
            /*Map<String, Object> param = new HashMap<>();
            param.put("tenantId",tenantId);*/
            List<Filter> filters = new ArrayList<>();
            List<Filter> filterss = new ArrayList<>();
            //查询关联方表获取关联方信息
            if(Dict.PUSH_TYPE_SINGLE.equals(pushType)){
                filters.add(new Filter("tenantId", tenantId));
                filterss.add(new Filter("tenantId", tenantId));
            }

            filters.add(new Filter("delflag", "1"));
            filters.add(new Filter("status", "6"));
            filters.add(new Filter("effectRelation", "1"));

            filterss.add(new Filter("delflag", "1"));
            filterss.add(new Filter("approvalStatus", "6"));
            filterss.add(new Filter("effectRelation", "1"));
            List<RelPsn> relPsns = relPsnService.select(filters);
            List<RelOrg> relOrgs = relOrgService.select(filterss);
            List<Map> relList = new ArrayList<>();
            //自然人
            for(RelPsn psn:relPsns){
                Map map1 = new HashMap();
                map1.put("relName",psn.getName());
                map1.put("relType",Dict.REL_TYPE_PSN);
                map1.put("certType",psn.getIdentifyType());
                map1.put("relNumber",psn.getIdentifyNumber());
                relList.add(map1);
            }
            //法人
            for(RelOrg org:relOrgs){
                Map map1 = new HashMap();
                map1.put("relName",org.getOrgName());
                map1.put("relType",Dict.REL_TYPE_ORG);
                map1.put("certType",org.getIdentifyType());
                map1.put("relNumber",org.getUniscCode());
                relList.add(map1);
            }
            tips.put("relList",relList);
            tips.put("tenantId",tenantId);
            tips.put("pushType",pushType);
            tips.put("CHNL_ID",map.get("chnlId"));
            tips.put("TRANS_CODE",map.get("TRANS_CODE"));
            tips.put("resultCode","0000");
            tips.put("msg","成功");
        }catch (Exception e){
            logger.error("关联方信息推送接口调用失败，错误信息：",e);
            tips.put("CHNL_ID",map.get("chnlId"));
            tips.put("TRANS_CODE",map.get("TRANS_CODE"));
            tips.put("resultCode", "9999");
            tips.put("msg", "失败，未知异常");
        }
        message.getTarget().setBody(tips);
        return message;
    }
}