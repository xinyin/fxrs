package com.yzj.jep.app.workflow.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

public interface GetBusProcService {

    Map<String,Object> selectByProcInstId(String procInstId);

    List<Map<String,String>> selectPathBytransNum(String transNum,String url,String tenantId);

     String selectItemByDictCode(String dictCode,String itemValue);

     List<JSONObject> selectRelByPid(Integer pid,String tenantId);

}
