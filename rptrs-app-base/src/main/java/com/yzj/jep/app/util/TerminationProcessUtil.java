package com.yzj.jep.app.util;

import cn.hutool.core.collection.CollUtil;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.sys.model.Staff;
import com.yzj.jep.sys.service.StaffService;
import com.yzj.jep.workflow.api.engine.WorkflowEngine;
import com.yzj.jep.workflow.impl.model.StaffImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @author by Liqiang
 * @Date 2022/10/21
 * @Description 终止流程
 */
public class TerminationProcessUtil {

    private static final WorkflowEngine WORKFLOW_ENGINE = SpringContextHolder.getBean(WorkflowEngine.class);
    private static final StaffService staffService = SpringContextHolder.getBean(StaffService.class);

    Logger logger = LoggerFactory.getLogger(TerminationProcessUtil.class);


    /**
     * 流程终止
     *
     * @param procInstId 流程ID
     * @param memo       终止信息
     */
    public static void terminate(String procInstId, String memo) {

        UserProfile profile = UserProfileUtils.getUserProfile();
        // 如果没有从登录获取到员工信息怎查询管理员信息进行流程终止操作
        Staff staff = new Staff();
        StaffImpl staffImpl = new StaffImpl();
        if (profile == null) {
            List<Filter> filters = new Filter.Builder()
                    .eq("staffCode", "FXAdmin").build();
            List<Staff> staffList = staffService.select(filters);
            if(CollUtil.isNotEmpty(staffList)){
                staff = staffList.get(0);
            }else {
                List<Filter> filterJep = new Filter.Builder()
                        .eq("staffCode", "jepAdmin").build();
                List<Staff> staffListJep = staffService.select(filterJep);
                if(CollUtil.isNotEmpty(staffListJep)){
                    staff = staffListJep.get(0);
                }
            }

            BeanUtils.copyProperties(staff, staffImpl);
        } else {
            BeanUtils.copyProperties(profile, staffImpl);
        }
        WORKFLOW_ENGINE.getRuntimeService().terminate(procInstId, staffImpl, memo);
    }
}
