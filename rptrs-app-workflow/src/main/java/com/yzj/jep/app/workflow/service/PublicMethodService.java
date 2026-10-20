package com.yzj.jep.app.workflow.service;

import java.util.Map;

public interface PublicMethodService {
     Map<String,String> data(Integer pid, String type);
     Map<String,String> getData( String procInstId,String procCode);
}
