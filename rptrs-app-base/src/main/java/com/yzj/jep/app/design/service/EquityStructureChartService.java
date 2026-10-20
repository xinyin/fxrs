package com.yzj.jep.app.design.service;

import com.yzj.jep.app.design.model.RootNode;


/**
 * @author duanliqiang
 */
public interface EquityStructureChartService {

    RootNode selectBypid(String relId, String relType);

    /**查询当前节点所有的上层与下层节点*/
    RootNode selectAllJson(String relId, String relType);

    /* RootNodeNew selectBypid(String pid, String type);*/

}
