package com.yzj.jep.app.web.controller;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.UnknownHostException;


import cn.hutool.core.util.StrUtil;
import com.novell.ldap.*;
import com.yzj.jep.app.sys.util.DesUtil;
import com.yzj.jep.app.sys.util.RsaUtil;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.web.security.realm.ADUtil;
import com.yzj.jep.app.web.security.realm.EasyTypeToken;
import jcifs.UniAddress;
import jcifs.smb.*;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.service.UserProfileService;
import com.yzj.jep.app.sys.util.IPUtils;
import com.yzj.jep.sys.code.LoginMsg;
import com.yzj.jep.sys.code.StaffType;
import com.yzj.jep.sys.dto.LoginUserDto;
import com.yzj.jep.sys.model.Staff;
import com.yzj.jep.sys.model.User;
import com.yzj.jep.sys.service.AppConfigService;
import com.yzj.jep.sys.service.StaffService;
import com.yzj.jep.sys.service.UserService;

/**
 * 系统登录
 *
 * @author yinke.hou
 * @time 2017年7月21日 上午10:15:42
 * @company 银之杰技术有限公司
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    Logger logger = LoggerFactory.getLogger(LoginController.class);

    //日期格式化
    private static final String DATE_FORMAT = "yyyy年MM月dd日 E";

    @Autowired
    AppConfigService appConfigService;

    @Autowired
    private UserService userService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private LdapController ldapController;


    private String my_host_LDAP;
    private int my_port_LDAP;
    private String loginDN_LDAP;
    private String password_LDAP;
    private String searchBase_LDAP;
    private  static  String msg = "用户名或密码错误";

    /**
     * 是否允许本地登录，取自参数
     */
    private static final String LOCAL_LOGIN_ENABLE = "LOCAL_LOGIN_ENABLE";

    /**
     * 登录页面
     *
     * @param model
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "")
    public String login(Model model, HttpServletRequest request) throws Exception {

        return "login";
    }

    /**
     * 查询当前登录人
     *
     * @param proxyStaffCode
     * @return
     */
    @RequestMapping(value = "/queryStaff")
    @ResponseBody
    public List<LoginUserDto> queryStaff(String proxyStaffCode) {

        return staffService.querySecondedStaff(proxyStaffCode);
    }

    /**
     * 第三方登录验证
     *
     * @param
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/signinForThree")
    @ResponseBody
    public Map<String, Object> signinForThree(HttpServletRequest request, LoginUserDto loginUserDto) throws Exception {
        Map<String, Object> map = new HashMap<>();

        Map<String, Object> authBoo = new ADUtil().AdAuth(loginUserDto.getUserName(), loginUserDto.getPassWord(), 0);
        if (!loginUserDto.getUserName().contains("Admin")) {
            if (authBoo.get("success").equals(false)) {
                return authBoo;
            }
        }


        map.put("success", true);

        Subject currentUser = SecurityUtils.getSubject();
        EasyTypeToken token = new EasyTypeToken(loginUserDto.getUserName());
        try {
            currentUser.login(token);
            Staff staff = staffService.get(loginUserDto.getUserName());
            if (!StaffType.FRONT.getValue().equals(staff.getStaffType())) {
                //验证当前用户是不是在选择租户下面
                if (((StringUtils.isBlank(loginUserDto.getTenantId()) || "null".equals(loginUserDto.getTenantId())) && StringUtils.isBlank(staff.getTenantId())) ||
                        loginUserDto.getTenantId().equals(staff.getTenantId())) {
                    User user = userService.get(loginUserDto.getUserName());
                    UserProfile userProfile = userProfileService.getUserProfileByUser(user, loginUserDto.getSecondedStaff());//在此过程中完成用户授权工作
                    //用户登录的IP
                    userProfile.setIp(IPUtils.getIpAddr(request));
                    currentUser.getSession().setAttribute(UserProfile.USER_SESSION_KEY, userProfile);
                    currentUser.getSession().setAttribute("currentDate", new SimpleDateFormat(DATE_FORMAT).format(new Date()));
                } else {
                    currentUser.logout();
                    map.put("success", false);
                    map.put("msg", msg);
                }
            } else {
                currentUser.logout();
                map.put("success", false);
                map.put("msg", msg);
            }

            //TODO 暂时未记录登录日子

        } catch (UnknownAccountException uae) {//账号不存在
            logger.debug("username wasn't in the system.", uae);
            map.put("success", false);
            map.put("msg", msg);
        } catch (IncorrectCredentialsException ice) {//密码错误
            logger.debug("password didn't match.", ice);
            map.put("success", false);
            map.put("msg", msg);
        } catch (LockedAccountException lae) {//账号已锁定
            logger.debug("account for that username is locked - can't login.", lae);
            map.put("success", false);
            map.put("msg", msg);
        } catch (ExcessiveAttemptsException eae) {//不允许访问
            logger.debug("Error authenticating.", eae);
            map.put("success", false);
            map.put("msg", msg);
        } catch (ExpiredCredentialsException ece) {//信用超期
            logger.debug("Error authenticating.", ece);
            map.put("success", false);
            map.put("msg", msg);
        } catch (DisabledAccountException dae) {//账号已关闭
            logger.debug("username disabled.", dae);
            map.put("success", false);
            map.put("msg", msg);
        } catch (AuthenticationException ae) {//认证异常
            logger.warn("Error authenticating.", ae);
            map.put("success", false);
            map.put("msg", msg);
        } catch (Exception e) {
            logger.error("[登录异常],原因：", e);
            map.put("success", false);
            map.put("msg", "登录异常");
        }
        return map;
    }


    /**
     * 登录验证
     *
     * @param
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/verification")
    @ResponseBody
    public Map<String, Object> signin(HttpServletRequest request, LoginUserDto loginUserDto) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        try {
//            String passWprd = DesUtil.decryption(loginUserDto.getPassWord());
            String passWprd = RsaUtil.decode(loginUserDto.getPassWord());
            Subject currentUser = SecurityUtils.getSubject();
            //UsernamePasswordToken token = new UsernamePasswordToken(loginUserDto.getUserName(), loginUserDto.getPassWord());
            Staff staff = null;
            String loginName = ldapController.login(loginUserDto.getUserName(), passWprd);
            // String loginName = loginUserDto.getUserName();
            if (loginName != null) {
                if (loginName.equals(loginUserDto.getUserName())) {
                    EasyTypeToken token = new EasyTypeToken(loginUserDto.getUserName());
                    currentUser.login(token);
                    staff = staffService.get(loginUserDto.getUserName());
                }

            /*    if ("jepAdmin".equals(loginUserDto.getUserName())) {
                    //超级管理员租户可以指定当前租户id，登录系统。以便绕过租户校验
                    loginUserDto.setTenantId(staff.getTenantId());
                }*/
            } else if (loginName ==null) {
                Boolean localEnable = checkLocalEnable(loginUserDto.getUserName());
                if (!localEnable) {
                    map.put("success", false);
                    map.put("msg", "账号或密码错误！");
                    return map;
                }
                EasyTypeToken token = new EasyTypeToken(loginUserDto.getUserName(), passWprd);
                currentUser.login(token);
                staff = staffService.get(loginUserDto.getUserName());
            }
            if (!StaffType.FRONT.getValue().equals(staff.getStaffType())) {
                //验证当前用户是不是在选择租户下面
                if (((StringUtils.isBlank(loginUserDto.getTenantId()) || "null".equals(loginUserDto.getTenantId())) && StringUtils.isBlank(staff.getTenantId())) ||
                        loginUserDto.getTenantId().equals(staff.getTenantId())) {
                    User user = userService.get(loginUserDto.getUserName());
                    UserProfile userProfile = userProfileService.getUserProfileByUser(user, loginUserDto.getSecondedStaff());//在此过程中完成用户授权工作

                    //用户登录的IP
                    userProfile.setIp(IPUtils.getIpAddr(request));
                    currentUser.getSession().setAttribute(UserProfile.USER_SESSION_KEY, userProfile);
                    currentUser.getSession().setAttribute("currentDate", new SimpleDateFormat(DATE_FORMAT).format(new Date()));
                } else {
                    currentUser.logout();
                    map.put("success", false);
                    map.put("msg", msg);
                }
            } else {
                currentUser.logout();
                map.put("success", false);
                map.put("msg", msg);
            }

            //TODO 暂时未记录登录日子

        } catch (UnknownAccountException uae) {//账号不存在
            logger.debug("username wasn't in the system.", uae);
            map.put("success", false);
            map.put("msg", msg);
        } catch (IncorrectCredentialsException ice) {//密码错误
            logger.debug("password didn't match.", ice);
            map.put("success", false);
            map.put("msg", msg);
        } catch (LockedAccountException lae) {//账号已锁定
            logger.debug("account for that username is locked - can't login.", lae);
            map.put("success", false);
            map.put("msg", msg);
        } catch (ExcessiveAttemptsException eae) {//不允许访问
            logger.debug("Error authenticating.", eae);
            map.put("success", false);
            map.put("msg", msg);
        } catch (ExpiredCredentialsException ece) {//信用超期
            logger.debug("Error authenticating.", ece);
            map.put("success", false);
            map.put("msg", msg);
        } catch (DisabledAccountException dae) {//账号已关闭
            logger.debug("username disabled.", dae);
            map.put("success", false);
            map.put("msg", msg);
        } catch (AuthenticationException ae) {//认证异常
            logger.warn("Error authenticating.", ae);
            map.put("success", false);
            map.put("msg", msg);
        } catch (Exception e) {
            logger.error("[登录异常],原因：", e);
            map.put("success", false);
            map.put("msg", msg);
        }
        UserProfile profile = UserProfileUtils.getUserProfile();
//        if (profile != null) {
//            String unitCode = profile.getUnitCode();
//            if (unitCode.contains("0001")) {
//                map.put("success", false);
//                map.put("msg", LoginMsg.NOTEXISTS.getValue());
//            }
//        }
        return map;
    }

    /**
     * 是否允许本地登录
     * @return
     * @param userName
     */
    private Boolean checkLocalEnable(String userName) {
        //管理员放行
        if (StrUtil.isNotBlank(userName) && (userName.contains("Admin") || userName.contains("admin"))) {
            return true;
        }
        //参数配置
        String configValue = appConfigService.getConfigValue(LOCAL_LOGIN_ENABLE);
        if ("Y".equals(configValue)) {
            return true;
        }
        return false;
    }

    /**
     * 退出登录
     *
     * @param request
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser != null) {
            currentUser.logout();
        }

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return "redirect:/login";
    }
}
