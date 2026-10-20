package com.yzj.jep.app.ex.service.impl;


import java.math.BigDecimal;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.sys.service.DictItemService;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.client.ProcessDataHandler;
import com.yzj.jep.workflow.client.annotation.DataHandler;


@DataHandler("YJZR_LeaveApplyProcess001")
public class LeaveApplyProcessDataHandler implements ProcessDataHandler {


    @Override
    public void handleData(String data) throws WorkflowException {


    }

}