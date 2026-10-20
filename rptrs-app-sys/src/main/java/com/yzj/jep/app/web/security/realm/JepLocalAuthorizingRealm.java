package com.yzj.jep.app.web.security.realm;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yzj.jep.sys.code.SecurityConstant;
import com.yzj.jep.sys.model.AppConfig;
import com.yzj.jep.sys.model.User;

public class JepLocalAuthorizingRealm extends JepAuthorizingRealm {
    //@Value("#{appcfg['sso.enable']}")
    Logger logger = LoggerFactory.getLogger(JepLocalAuthorizingRealm.class);

    private static final String ALLOW_LOGIN_LOCAL_NONE = "LOCAL_NONE";
    private static final String ALLOW_LOGIN_LOCAL_ADMIN = "LOCAL_ADMIN";

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken at) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) at;
        String userId = token.getUsername();
        if (StringUtils.isNotEmpty(userId)) {

            if (ssoEnable) {//单点登录的情况下才判断是否本地登录
                String allowLocalLogin = ALLOW_LOGIN_LOCAL_NONE;
                AppConfig appConfig = appConfigService.get("ALLOW_LOCAL_LOGIN");
                if (appConfig != null) {
                    allowLocalLogin = appConfig.getConfigValue();
                }

                if (ALLOW_LOGIN_LOCAL_NONE.equals(allowLocalLogin)) {//不允许本地登录
                    logger.error("单点登录状态下，不允许所有人本地登录。当前登录用户：" + userId);
                    return null;

                }
                if (ALLOW_LOGIN_LOCAL_ADMIN.equals(allowLocalLogin)) {//只允许管理员本地登录
                    if (!SecurityConstant.ADMIN_USER_NAME.equals(userId)) {
                        logger.error("单点登录状态下，只允许管理员登录。当前登录用户：" + userId);
                        return null;
                    }
                }
            }

            User user = userService.get(userId);
            if (user != null) {
                // 判断帐号是否锁定  
                if (new Integer(1).equals(user.getAccountNonLocked())) {
                    // 抛出 帐号锁定异常  
                    throw new LockedAccountException();
                }

                ByteSource salt = new SimpleByteSource(userId);
                return new SimpleAuthenticationInfo(token.getUsername(), user.getPassword(), salt, getName());
            } else {
                // 抛出 帐号找不到异常  
                throw new UnknownAccountException();
            }

        }
        return null;
    }

}
