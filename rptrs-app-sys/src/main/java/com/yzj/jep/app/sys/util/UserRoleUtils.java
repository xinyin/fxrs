package com.yzj.jep.app.sys.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.sys.model.Duty;
import com.yzj.jep.sys.service.DutyService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by Liqiang
 * @Date 2022/11/03
 * @Description 查询用户角色
 */
public class UserRoleUtils {

    private static final DutyService dutyService = SpringContextHolder.getBean(DutyService.class);

    /** 总部合规部关联交易管理岗 */
    private static final String ROLE_CODE = "1000012";
    private static final String SEPARATOR = "@";

    /**
     * 判断该员工的角色是否为所有权限
     * 只针对关联交易关理模块
     */
    public static boolean judgeRole(String staffCode){
        List<String> roleList = getRole(staffCode);
        if(CollUtil.isNotEmpty(roleList)){
            return roleList.contains(ROLE_CODE);
        }
        return false;
    }

    /**
     * 获取该用户的所有角色
     */
    public static List<String> getRole(String staffCode) {
        List<String> roleList = new ArrayList<>();
        List<Filter> filters = new ArrayList<>();
        filters.add(new Filter("staffCode", Operator.EQ, staffCode));
        filters.add(new Filter("delflag", Operator.EQ, "1"));
        List<Duty> dutyList = dutyService.select(filters);
        dutyList.stream().filter(duty -> CharSequenceUtil.isNotBlank(duty.getPositionCode())).forEach(duty -> {
            String positionCode = duty.getPositionCode();
            String roleCode = positionCode.substring(0, positionCode.lastIndexOf(SEPARATOR));
            roleList.add(roleCode);
        });
        return roleList;
    }
}
