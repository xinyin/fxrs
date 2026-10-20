package com.yzj.jep.app.web.plugin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.sys.service.MenuService;

/**
 * 请求拦截器， 验证每个请求的合法性
 *
 * @author yinke.hou
 * @time 2017年8月2日 下午3:59:07
 * @company 银之杰技术有限公司
 */
public class PermissionInterceptor extends HandlerInterceptorAdapter {
    Logger logger = LoggerFactory.getLogger(PermissionInterceptor.class);


    @Autowired
    private MenuService menuService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        //1.获取当前请求路径

        String appName = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + appName;
        String url = request.getServletPath();
        String _sid = request.getSession().getId();
        logger.info("请求地址为:" + basePath + url);
        String _mngUrl = url;

        //2.是否忽略的地址
        //3.验证是否有访问权限
        /*先获得当前用户*/
        UserProfile currentUser = UserProfileUtils.getUserProfile();

        //获取是否有访问权限
        boolean isAccess = true; //menuService.canAccessUrl(currentUser.getStaffCode(), _mngUrl);
        if (isAccess) {
            logger.debug("获取权限成功: " + url + "： 请求通过。");
            return super.preHandle(request, response, handler);
        } else {
            // 获取权限失败
            response.setCharacterEncoding("UTF-8");
            logger.info(_sid + "获取权限错误:" + HttpStatus.FORBIDDEN.value());
            response.sendError(HttpStatus.FORBIDDEN.value(), "您无权限访问该资源！");
            logger.info(_sid + "跳转到" + HttpStatus.FORBIDDEN.value() + "界面");

            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub
    }
}
