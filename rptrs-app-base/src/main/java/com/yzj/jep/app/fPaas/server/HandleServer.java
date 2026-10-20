package com.yzj.jep.app.fPaas.server;

import java.util.Map;

/**
 * 场景处理业务
 * @author wll
 */
public interface HandleServer {
    /**
     * 业务处理
     * @param data
     * @param res
     */
    void handle(Map<String, Object> data, Map<String, Object> res);
}
