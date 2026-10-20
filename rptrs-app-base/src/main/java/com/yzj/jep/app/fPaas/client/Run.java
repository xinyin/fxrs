package com.yzj.jep.app.fPaas.client;

import com.fPaaS1.core.IFPaaS1Handler;
import com.fPaaS1.core.Message;
import com.fPaaS1.dubbo.startup.StartupUtil;
import com.jzr.fpaas.util.MessageUtil;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hongwen.li
 */
public class Run {

    public static void main(String[] args) throws Exception {
        StartupUtil su = StartupUtil.getInstance("dbank-gateway-client-fra", args);
        su.setConfigLocations(new String[]{"classpath*:META-INF/spring/**/*.xml",
                "classpath*:META-INF/beans/*.xml",
                "classpath*:META-INF/comm/*.xml",
                "classpath*:META-INF/flow/*.xml",
                "classpath*:META-INF/dubbo/*.xml"});
        su.loadxml();
        ClassPathXmlApplicationContext ctx = su.getCtx();
        IFPaaS1Handler handler = (IFPaaS1Handler) ctx.getBean("SA_GAT_GatewayClientHandler");
        Map<String, Object> body = new HashMap<String, Object>();
        body.put("transId", "11111");
        body.put("descCode", "helloword");
        Message m = MessageUtil.getMessage("SA_GAT_GatewayClientHandler", body);
        handler.handle(m);
        su.start();
    }

}
