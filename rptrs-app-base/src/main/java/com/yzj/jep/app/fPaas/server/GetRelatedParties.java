package com.yzj.jep.app.fPaas.server;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.fPaaS1.core.IFPaaS1Handler;
import com.fPaaS1.core.Message;
import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.record.model.InterfaceRecord;
import com.yzj.jep.app.record.service.TraInterfaceRecordService;
import com.yzj.jep.app.sub.model.Bus_rel_list;
import com.yzj.jep.app.util.Dict;
import com.yzj.jep.core.holder.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetRelatedParties implements IFPaaS1Handler<Message> {
    private static final Logger logger = LoggerFactory.getLogger(GetRelatedParties.class);

    RelOrgService relOrgService;

    RelPsnService relPsnService;
    TraInterfaceRecordService traInterfaceRecordService;


    @Override
    public Message handle(Message message) throws Exception {

        relOrgService = SpringContextHolder.getBean(RelOrgService.class);

        relPsnService = SpringContextHolder.getBean(RelPsnService.class);
        traInterfaceRecordService = SpringContextHolder.getBean(TraInterfaceRecordService.class);

        Map tips = new HashMap<String, Object>();
        Map map = (Map) message.getTarget().getBodys();
        JSONObject dataJson = new JSONObject(map);
        //传入参数
        logger.info("=============================>" + dataJson.toString());
        String version = dataJson.getString("version");
        try {
            Bus_rel_list busRels =null;
            List<Bus_rel_list> relList = new ArrayList<>();
            List<RelOrg> relOrgs = relOrgService.selectByProEffective();
            for (RelOrg relorg : relOrgs) {
                if(!"9131000005459537XP".equals(relorg.getUniscCode())){
                    busRels = new Bus_rel_list();
                    busRels.setRelName(relorg.getOrgName());
                    busRels.setRelType("1");
                    busRels.setRelUscCode(relorg.getUniscCode());
                    busRels.setRelUscType(relorg.getIdentifyType());
                    relList.add(busRels);
                }
            }

            List<RelPsn> relPsnList = relPsnService.selectByProEffective();
            for (RelPsn relPsn : relPsnList) {
                busRels = new Bus_rel_list();
                busRels.setRelName(relPsn.getName());
                busRels.setRelType("2");
                busRels.setRelUscCode(relPsn.getIdentifyNumber());
                busRels.setRelUscType(relPsn.getIdentifyType());
                relList.add(busRels);
            }
            relList.forEach(busRelList -> {
                busRelList.setRelUscCode(changeSpecialChar(busRelList.getRelUscCode()));
                busRelList.setRelName(changeSpecialChar(busRelList.getRelName()));
            });
            tips.put("CHNL_ID", map.get("chnlId"));
            tips.put("TRANS_CODE", "getRelList");
            tips.put("resultCode", "0000");
            tips.put("msg", "成功");
            tips.put("relList", relList);
        } catch (NullPointerException e) {
            e.printStackTrace();
            tips.put("resultCode", "1001");
            tips.put("msg", "失败");
        } catch (
                JSONException e) {
            tips.put("resultCode", "1001");
            tips.put("msg", "失败");
        } catch (Exception e) {
            tips.put("resultCode", "9999");
            tips.put("msg", "失败");
        } finally {
            interFaceRecord(tips,map);
            message.getTarget().setBody(tips);
        }
        return message;
    }


    public String changeSpecialChar(String str){

        try {
            str = URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            logger.error(String.format("字符串【%s】转码失败！", str));
            throw new RuntimeException(e);
        }

        return str;
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
            interfaceRecord.setBusName("获取关联方名单表接口");
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
