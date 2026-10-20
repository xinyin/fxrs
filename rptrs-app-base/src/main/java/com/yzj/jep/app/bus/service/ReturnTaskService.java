package com.yzj.jep.app.bus.service;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public interface ReturnTaskService {
    Map<String, Object> getData(String taskId, String formData, String procInstId, String memo);
}
