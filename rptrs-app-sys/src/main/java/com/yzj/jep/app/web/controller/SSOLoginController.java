package com.yzj.jep.app.web.controller;

import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.service.UserProfileService;
import com.yzj.jep.app.sys.util.IPUtils;
import com.yzj.jep.sys.code.LoginMsg;
import com.yzj.jep.sys.code.StaffType;
import com.yzj.jep.sys.model.Staff;
import com.yzj.jep.sys.model.User;
import com.yzj.jep.sys.service.StaffService;
import com.yzj.jep.sys.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/sso/data")
public class SSOLoginController {

    Logger logger = LoggerFactory.getLogger(SSOLoginController.class);

    /*日期格式化*/
    private static final String DATE_FORMAT = "yyyy年MM月dd日 E";

    private static final String LOGIN_URL = "http://172.20.100.14:8801/jep-app/login";

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private UserProfileService userProfileService;

    /**
     * @param token
     * @param userId
     * @param appId
     * @return
     */
    @RequestMapping("/login")
    public String login(HttpServletRequest request, String token, String userId, String appId) {
        User user = userService.get(userId);
        Staff staff = staffService.get(userId);
        return "login";
    }

    @RequestMapping("/TEST")
    public String signin(HttpServletRequest request, String token, String userId) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);

        Subject currentUser = SecurityUtils.getSubject();
//        UsernamePasswordToken token1 = new UsernamePasswordToken(userId, "");
        try {
//            currentUser.login(token1);

            Staff staff = staffService.get(userId);
            if (!StaffType.FRONT.getValue().equals(staff.getStaffType())) {
                //验证当前用户是不是在选择租户下面
                User user = userService.get(userId);
                UserProfile userProfile = userProfileService.getUserProfileByUser(user, null);//在此过程中完成用户授权工作
                //用户登录的IP
                userProfile.setIp(IPUtils.getIpAddr(request));
                currentUser.getSession().setAttribute(UserProfile.USER_SESSION_KEY, userProfile);
                currentUser.getSession().setAttribute("currentDate", new SimpleDateFormat(DATE_FORMAT).format(new Date()));
            } else {
                currentUser.logout();
                map.put("success", false);
                map.put("msg", LoginMsg.NOTEXISTS.getValue());
                return "redirect:/login";
            }

            //TODO 暂时未记录登录日子

        } catch (UnknownAccountException uae) {//账号不存在
            logger.debug("username wasn't in the system.", uae);
            map.put("success", false);
            map.put("msg", LoginMsg.NOTEXISTS.getValue());
        } catch (IncorrectCredentialsException ice) {//密码错误
            logger.debug("password didn't match.", ice);
            map.put("success", false);
            map.put("msg", LoginMsg.PWDERROR.getValue());
        } catch (LockedAccountException lae) {//账号已锁定
            logger.debug("account for that username is locked - can't login.", lae);
            map.put("success", false);
            map.put("msg", LoginMsg.ACCOUNTFREEZE.getValue());
        } catch (ExcessiveAttemptsException eae) {//不允许访问
            logger.debug("Error authenticating.", eae);
            map.put("success", false);
            map.put("msg", LoginMsg.NOAUTHORITY.getValue());
        } catch (ExpiredCredentialsException ece) {//信用超期
            logger.debug("Error authenticating.", ece);
            map.put("success", false);
            map.put("msg", LoginMsg.NOAUTHORITY.getValue());
        } catch (DisabledAccountException dae) {//账号已关闭
            logger.debug("username disabled.", dae);
            map.put("success", false);
            map.put("msg", LoginMsg.ACCOUNTCLOSE.getValue());
        } catch (AuthenticationException ae) {//认证异常
            logger.warn("Error authenticating.", ae);
            map.put("success", false);
            map.put("msg", LoginMsg.LOGINEXCEPTION.getValue());
        } catch (Exception e) {
            logger.error("[登录异常],原因：", e);
            map.put("success", false);
            map.put("msg", "登录异常");
        }

        return "redirect:/index";
    }
}
