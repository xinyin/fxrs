package com.yzj.jep.app.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yzj.jep.app.sys.model.SingleVerification;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.service.UserProfileService;
import com.yzj.jep.app.sys.util.IPUtils;
import com.yzj.jep.app.web.security.realm.EasyTypeToken;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.sys.code.LoginMsg;
import com.yzj.jep.sys.code.StaffType;
import com.yzj.jep.sys.dto.LoginUserDto;
import com.yzj.jep.sys.model.Staff;
import com.yzj.jep.sys.model.User;
import com.yzj.jep.sys.service.AppConfigService;
import com.yzj.jep.sys.service.StaffService;
import com.yzj.jep.sys.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统登录
 * @author yinke.hou
 * @time 2020年12月11日 上午10:15:42
 * @company 银之杰技术有限公司
 */
@Controller
public class SingleLoginController {
	
	Logger logger = LoggerFactory.getLogger(SingleLoginController.class);
	
	/*日期格式化*/
	private static final String DATE_FORMAT	= "yyyy年MM月dd日 E";
	
	@Autowired
	AppConfigService		appConfigService;
	
	@Autowired
	private UserService	    userService;
	
	@Autowired
	private StaffService	staffService;
	
	@Autowired
	private UserProfileService userProfileService;

	
	/**
	 * 单点登录
	 * @param token 单点登录服务器token
	 * @param userId 用户id
	 * @return
	 */
	@RequestMapping(value = "/RptrsSso",method = RequestMethod.GET)
	public String verification(HttpServletRequest request,
							   @RequestParam("token") String token,
							   @RequestParam("userId") String userId) {
		HttpClient client = HttpClients.createDefault();
		String url;
		SingleVerification sv;
		String resultStr;
		String[] sourceStrArray;
		try{
			logger.info("单点登陆请求信息token："+token+"#userId:"+userId);
			InetAddress address = InetAddress.getLocalHost();
			String hostAddress = address.getHostAddress();
			//String hostAddress = "172.20.95.21";
			String channel_Single_Login = appConfigService.getConfigValue("channel_Single_Login");
			//url = "http://172.20.100.68:8080/TICKETVALIDATOR?token="+token+"&ip="+hostAddress;
			url = channel_Single_Login+"?token="+token+"&ip="+hostAddress;
			logger.info("验证接口访问路径："+url);
			HttpGet httpGet = new HttpGet(url);
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000).setConnectionRequestTimeout(10000).setSocketTimeout(10000).build();
			httpGet.setConfig(requestConfig);
			httpGet.setHeader("Accept", "application/json");
			HttpResponse response = client.execute(httpGet);
			HttpEntity entity = response.getEntity();
			byte[] body = EntityUtils.toByteArray(entity);
			StatusLine sL = response.getStatusLine();
			int statusCode = sL.getStatusCode();
			if (statusCode == 200) {
				resultStr = new String(body, "utf-8");
				sourceStrArray = resultStr.split(";");
				logger.info("单点登陆验证接口返回信息msg = ："+sourceStrArray[0]);
				entity.consumeContent();
			} else {
				logger.info("单点登陆验证接口异常statusCode = "+statusCode);
				return "login";
			}

			if("001".equals(sourceStrArray[0])){
				Subject currentUser = SecurityUtils.getSubject();
				EasyTypeToken MMtoken = new EasyTypeToken(userId);
				currentUser.login(MMtoken);

				Staff staff = staffService.get(userId);
				if(!StaffType.FRONT.getValue().equals(staff.getStaffType())){

					User user = userService.get(userId);
					UserProfile userProfile = userProfileService.getUserProfileByUser(user, "");//在此过程中完成用户授权工作
					//用户登录的IP
					userProfile.setIp(IPUtils.getIpAddr(request));
					currentUser.getSession().setAttribute(UserProfile.USER_SESSION_KEY, userProfile);
					currentUser.getSession().setAttribute("currentDate", new SimpleDateFormat(DATE_FORMAT).format(new Date()));
					logger.info("单点登陆成功");
					return "index";
				} else {
					logger.info("单点登陆失败StaffType："+staff.getStaffType());
					currentUser.logout();
					return "login";
				}

			}else{
				return "login";
			}
		}catch (UnknownAccountException uae) {//账号不存在
			logger.debug("username wasn't in the system.", uae);
			return "login";
		} catch (IncorrectCredentialsException ice) {//密码错误
			logger.debug("password didn't match.", ice);
			return "login";
		} catch (LockedAccountException lae) {//账号已锁定
			logger.debug("account for that username is locked - can't login.", lae);
			return "login";
		} catch (ExcessiveAttemptsException eae) {//不允许访问
			logger.debug("Error authenticating.", eae);
			return "login";
		} catch (ExpiredCredentialsException ece) {//信用超期
			logger.debug("Error authenticating.", ece);
			return "login";
		} catch (DisabledAccountException dae) {//账号已关闭
			logger.debug("username disabled.", dae);
			return "login";
		} catch (AuthenticationException ae) {//认证异常
			logger.warn("Error authenticating.", ae);
			return "login";
		} catch (Exception e) {
			logger.error("[登录异常],原因：", e);
			return "login";
		}

	}

}
