package com.yzj.jep.app.sys.util;

import com.yzj.jep.app.sys.model.UserProfile;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.subject.Subject;

public class UserProfileUtils {

    /**
     * 获得当前登录用户
     *
     * @return 当前登录用户
     */
    public static UserProfile getUserProfile() {
        Subject currentUser = null;
        try {
            currentUser = SecurityUtils.getSubject();
        } catch (UnavailableSecurityManagerException usme) {
            return null;
        }

        if (null != currentUser) {
            UserProfile profile = (UserProfile) currentUser.getSession().getAttribute(UserProfile.USER_SESSION_KEY);
            return profile;
        }
        return null;
    }
}
