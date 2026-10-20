package com.yzj.jep.app.sys.service;

import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.core.service.BaseService;
import com.yzj.jep.sys.dto.StaffDto;
import com.yzj.jep.sys.model.Staff;
import com.yzj.jep.sys.model.Tenant;
import com.yzj.jep.sys.model.User;

public interface UserProfileService extends BaseService {

    /**
     * 根据用户信息，查询用户详细信息，包括角色等信息。
     *
     * @param user 登录用户
     * @return
     */
    UserProfile getUserProfileByUser(User user, String proxyStaff);

    /**
     * 修改用户密码
     *
     * @param user 登录用户
     * @return
     */
    boolean changePWD(UserProfile userProfile, String newPassword);

    /**
     * 根据新建用户创建一个登录用户
     *
     * @param staff
     * @return
     */
    int saveUser(Staff staff);

    /**
     * 保存用户，及一个事物中多个操作
     *
     * @param staff
     * @return
     */
    int saveStaff(StaffDto staff);

    /**
     * 保存租户管理员
     *
     * @param staff
     * @param tenant
     */
    void insertStaffAdm(StaffDto staff, Tenant tenant);

    /**
     * 编辑当前租户
     *
     * @param staff
     * @param tenant
     */
    void updateStaffAdm(StaffDto staff, Tenant tenant);
}
