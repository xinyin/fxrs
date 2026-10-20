package com.yzj.jep.app.fPaas.server;

import com.alibaba.fastjson.JSONObject;
import com.fPaaS1.core.IFPaaS1Handler;
import com.fPaaS1.core.Message;
import com.yzj.jep.app.bus.service.RuleLibRestCallback;
import com.yzj.jep.app.holder.SpringHolder;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.rules.drools5.model.ChkLog;
import com.yzj.jep.rules.drools5.service.ChkLogService;
import com.yzj.jep.rules.drools5.service.PackagesService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author hongwen.li
 */
public class RuleServerHandler implements IFPaaS1Handler<Message> {
    private static final Logger logger = LoggerFactory.getLogger(com.fpaas.handl.GatewayServerHandler.class);

    private ChkLogService chkLogService;
    private PackagesService packagesService;

    @Override
    public Message handle(Message message) throws Exception {
        chkLogService = SpringHolder.getBean(ChkLogService.class);
        packagesService = SpringHolder.getBean(PackagesService.class);
        Map map = (Map) message.getTarget().getBodys();
        JSONObject json = new JSONObject(map);

        //传入参数
        logger.info("-----------------------------" + json.toString());
        ChkLog chkLog = new ChkLog();
        //规则 id
        String pkgId = (String) map.get("pkgId");
        Map resMap = packagesService.execute(pkgId, json.toJSONString());
        Map<String, String> result = new HashMap();
        List<Map> resultList = (List) resMap.get("resultList");
        String retCode = "";
        String retMsg = "";
        if (CollectionUtils.isNotEmpty(resultList)) {
            for (Map<String, String> ruleRetMap : resultList) {
                //调用规则实现类
                String ruleClass = (String) ruleRetMap.get("retClass");
                if (ruleClass != null) {
                    RuleLibRestCallback rCallback = (RuleLibRestCallback) SpringContextHolder.getBean(ruleClass);
                    result = rCallback.checkRule(map);
                    retCode = result.get("retCode");
                    retMsg = result.get("retMsg");
                    break;
                }//否则取规则引擎返回result
                else {
                    retCode = ruleRetMap.get("retCode");
                    retMsg = ruleRetMap.get("retMsg");
                    break;
                }
            }
            result.put("code", retCode);
            result.put("msg", retMsg);
            //通知预警

        }
        message.getTarget().setBody(result);
        return message;
    }

    /**
     * 信息入库
     *
     * @param chkLog
     */
    public void insert(ChkLogService chkLogService, ChkLog chkLog) {
        try {
            chkLogService.insert(chkLog);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }

    /**
     * 参数值设置
     *
     * @param chkLog
     * @param pkgId
     * @param busCode
     */
    public void param(ChkLogService chkLogService, ChkLog chkLog, String pkgId, String busCode, String serNum, String channelCode, String traCode) {
        chkLog.setSerNum(serNum);
        chkLog.setChannelCode(channelCode);
        chkLog.setBusCode(busCode);
        chkLog.setTraCode(traCode);
        chkLog.setTraType("授信类");
        chkLog.setRuleId(pkgId);
        chkLog.setCreateTime(new Date());
        insert(chkLogService, chkLog);
    }
}
