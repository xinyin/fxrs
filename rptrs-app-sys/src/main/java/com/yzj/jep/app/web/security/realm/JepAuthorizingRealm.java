package com.yzj.jep.app.web.security.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.yzj.jep.sys.service.AppConfigService;
import com.yzj.jep.sys.service.UserService;

public class JepAuthorizingRealm extends AuthorizingRealm {


    protected Boolean ssoEnable = false;

    @Autowired
    protected UserService userService;

    @Autowired
    protected AppConfigService appConfigService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
        return null;
    }

}
