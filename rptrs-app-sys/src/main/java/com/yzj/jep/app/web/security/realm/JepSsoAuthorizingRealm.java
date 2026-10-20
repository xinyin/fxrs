package com.yzj.jep.app.web.security.realm;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.sys.code.SecurityConstant;
import com.yzj.jep.sys.model.User;
import com.yzj.jep.sys.service.UserService;

public class JepSsoAuthorizingRealm extends JepAuthorizingRealm {

    Logger logger = LoggerFactory.getLogger(JepSsoAuthorizingRealm.class);

    @Autowired
    private UserService userService;

    public JepSsoAuthorizingRealm() {
        setName("ssoRealm");
        setAuthenticationTokenClass(UsernamePasswordToken.class);
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken at) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) at;
        //只有用这个密码的才是单点登录过来的，才会根据用户名放行，否则应该到本地认证realm中处理
        if (SecurityConstant.SSO_MOCK_PASSWORD.equals(String.valueOf(token.getPassword()))) {
            String username = token.getUsername();
            User user = userService.get(username);
            //本系统中存在该用户,则继续
            if (StringUtils.isNotBlank(username) && user != null) {
                try {
                    return new SimpleAuthenticationInfo(token.getUsername(), token.getPassword(), getName());
                } catch (ServiceException e) {
                    logger.error(e.getMessage());
                } catch (Exception e) {
                    logger.error("[单点登录异常],原因：" + e.getMessage());
                }
            }
        }

        return null;
    }

}