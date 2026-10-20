package com.yzj.jep.app.base.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.yzj.jep.app.base.constant.PrivilegeType;
import com.yzj.jep.app.base.constant.PrivilegeType;


/**
 * 数据权限相关工具方法
 *
 * @author mma
 * @version 3.0
 * @date 2011-12-16 下午1:42:08
 */
public final class PrivilegeUtils {

    private static final String NAMED_UNIT_CODE = ":unitCode";

    private static ThreadLocal<PrivilegeType> threadLocal = new ThreadLocal<PrivilegeType>();

    /**
     * 将权限保存到当前线程中
     *
     * @param type
     */
    public static final void setThreadPrivilegeType(PrivilegeType type) {
        threadLocal.set(type);
    }

    /**
     * 利用 ThreadLocal 返回当前线程中的权限
     *
     * @return
     */
    public static final PrivilegeType getThreadPrivilegeType() {
        return threadLocal.get();
    }

}
