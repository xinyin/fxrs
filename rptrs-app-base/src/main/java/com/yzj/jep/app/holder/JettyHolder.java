package com.yzj.jep.app.holder;

import com.fPaaS1.presys.cp.httpsvr.container.JettyServer;

/**
 * hongwen.li
 */
public class JettyHolder {

    public static void initServer() {
        try {
            JettyServer server = SpringHolder.getServerBean("jettyServer", JettyServer.class);
            server.runJettyServer();
        } catch (Exception e) {
            System.exit(-1);
        }
    }

}
