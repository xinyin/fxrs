package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.mapper.ActRuMapper;
import com.yzj.jep.workflow.impl.service.ActRuservice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActRuServiceImpl implements ActRuservice {
    private static Logger logger = LoggerFactory.getLogger(ActRuServiceImpl.class);

    @Autowired
    private ActRuMapper actRuMapper;

    @Override
    public int deletTask(String taskId) {
        int type = actRuMapper.deleteRuIdentitylink(taskId);
        int type2 = actRuMapper.deleteRuTask(taskId);
        return type2;
    }

   /* @Override
    public int deletTaskForProcInstId(String procInstId) {
        int k = 1;
        int link =relPsnMapper.deleteRuIdentitylinkForProcInstId(procInstId);
        int task = relPsnMapper.deleteRuTaskForProcInstId(procInstId);
        if(link == 0 || task == 0){
            k = 0;
        }
        return k;
    }*/
}
