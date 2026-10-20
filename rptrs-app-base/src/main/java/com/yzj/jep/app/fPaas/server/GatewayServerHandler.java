package com.yzj.jep.app.fPaas.server;

import com.alibaba.fastjson.JSONObject;
import com.fPaaS1.core.IFPaaS1Handler;
import com.fPaaS1.core.Message;
import com.fPaaS1.core.utils.Constants.Channel;
import com.fPaaS1.presys.cp.server.DefaultServerFPaaS1Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


/**
 * @author hongwen.li
 */
public class GatewayServerHandler implements IFPaaS1Handler<Message> {
    private static final Logger LOG = LoggerFactory.getLogger(com.fpaas.handl.GatewayServerHandler.class);

    @Override
    public Message handle(Message message) throws Exception {
        LOG.info("进入handler");
        JSONObject object = new JSONObject();
        object.put("smRspcode", "1");
        object.put("smMessage", "1");
        object.put("smRspRiskLevel", "1");
        object.put("transTime", "1");
        object.put("outCode", "1");
        object.put("outMsg", "1");
        message.getTarget().setBody(object);
        return message;
    }
}
