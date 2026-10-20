package com.yzj.jep.app.wf.service;

import com.yzj.jep.workflow.impl.model.StaffImpl;

import java.util.Map;

public interface WorkflowOperation {


    /**
     * 关联方交易类流程审批入口
     * @param pid
     * @param procDefCode
     * @param staff
     * @param oldData
     * @param newData
     * @param type
     * @param procInstId
     * @return
     */
    Map<String,Object> sendSubmit(Integer pid , String procDefCode, StaffImpl staff, String oldData, String newData, String type, String procInstId) throws Exception;
}
