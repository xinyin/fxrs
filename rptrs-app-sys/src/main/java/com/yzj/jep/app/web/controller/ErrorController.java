package com.yzj.jep.app.web.controller;


import static org.springframework.web.util.WebUtils.ERROR_MESSAGE_ATTRIBUTE;
import static org.springframework.web.util.WebUtils.ERROR_STATUS_CODE_ATTRIBUTE;
import static org.springframework.web.util.WebUtils.FORWARD_CONTEXT_PATH_ATTRIBUTE;
import static org.springframework.web.util.WebUtils.FORWARD_SERVLET_PATH_ATTRIBUTE;

import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.commons.ErrorRequestUtil;
import com.yzj.jep.commons.StringConvert;
import com.yzj.jep.web.code.WebCode;

/**
 * http请求发生异常 返回页面
 *
 * @time 2017年3月16日 上午10:16:28
 * @company 银之杰技术有限公司
 */
@Controller
@RequestMapping("/error")
public class ErrorController{
    Logger logger = LoggerFactory.getLogger(ErrorController.class);

    private final String ERROR_MSG_404 = "资源未找到，请联系管理员";
    private final String ERROR_MSG_403 = "您没有访问该路径的权限";
    private final String ERROR_MSG_OTHER = "发生系统错误，请联系管理员";


    public static final String EMPTY = "";

    /**
     * 400 错误处理
     *
     * @param model
     * @param map
     * @param request
     * @param response
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"400"})
    public String show400(Model model, Map<String, Object> map,
                          HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

        //获取请求地址
        String _content = ErrorRequestUtil.getReqAttributes(request, FORWARD_CONTEXT_PATH_ATTRIBUTE);
        String _url = ErrorRequestUtil.getReqAttributes(request, FORWARD_SERVLET_PATH_ATTRIBUTE);

        //获取http请求错误状态
        String errCode = ErrorRequestUtil.getReqAttributes(request, ERROR_STATUS_CODE_ATTRIBUTE);

        logger.info("页面未找到({})， 应用程序上下文:{}， url地址:{}", errCode, _content, _url);


        //获取http 请求错误信息
        String errMessage = ErrorRequestUtil.getReqAttributes(request, ERROR_MESSAGE_ATTRIBUTE);
        errMessage = (null == errMessage || errMessage.length() == 0) ? "" : "(" + errMessage + ")";

        String msg = "请求地址[{}]时发生错误:{}: {}";
        msg = StrUtil.format(msg, ErrorRequestUtil.CleanXSS(_url), errCode, errMessage);


        //判断当前错误代码，设置错误信息。
        String errMsg = EMPTY;
        switch (StringConvert.ToInteger(errCode)) {
            case 403: {
                errMsg = ERROR_MSG_403;
                break;
            }
            case 404: {
                errMsg = ERROR_MSG_404;
                break;
            }
            default: {
                errMsg = ERROR_MSG_OTHER;
            }
        }

        //判断当前是ajax 还是普通请求，返回格式不同
        String accept = request.getHeader("accept");
        if ((accept != null && accept.indexOf("application/json") > -1)
                || (request.getHeader("X-Requested-With") != null
                && request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1)) {    //JSON返回方式
            response.setStatus(HttpStatus.OK.value());
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json; charset=utf-8");

            JSONObject jsonObject = new JSONObject();
            jsonObject.put(WebCode.SUCCESS, false);
            jsonObject.put(WebCode.MSG, errMsg);

            PrintWriter out = response.getWriter();
            out.print(jsonObject.toString());
            out.flush();
            out.close();
        } else {   // 返回一个400页面
            model.addAttribute(WebCode.MSG, errMsg);
            return "sys/error/400";
        }

        return null;
    }

    /**
     * 该资源发生系统错误
     * <p>
     * 胡帅博 2021/3/9 ，测试了一下，系统异常应该不会走这里，而是使用的rptrs-app-base/src/main/resources/spring/spring-mvc.xml中的配置
     *
     * @param model
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"500"})
    public String show500(Model model, Map<String, Object> map,
                          HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        //获取请求地址
        String _content = ErrorRequestUtil.getReqAttributes(request, FORWARD_CONTEXT_PATH_ATTRIBUTE);
        String _url = ErrorRequestUtil.getReqAttributes(request, FORWARD_SERVLET_PATH_ATTRIBUTE);
        logger.debug("请求url地址为:" + _url + ", content:" + _content);

        //获取http 请求错误状态
        String errCode = ErrorRequestUtil.getReqAttributes(request, ERROR_STATUS_CODE_ATTRIBUTE);

        //获取http 请求错误信息
        String errMessage = ErrorRequestUtil.getReqAttributes(request, ERROR_MESSAGE_ATTRIBUTE);
        errMessage = null == errMessage || "" == errMessage ? "" : "(" + errMessage + ")";
        String msg = "请求地址[{0}]时发生错误:{1}: {2}";
        msg = MessageFormat.format(msg, ErrorRequestUtil.CleanXSS(_url), errCode, errMessage);

        //判断当前错误代码，设置错误信息。
        String errMsg = EMPTY;
        switch (StringConvert.ToInteger(errCode)) {
            case 500: {
                errMsg = "系统错误，请联系管理员。";
                break;
            }
            default: {
                errMsg = "您要访问的页面开小差了。";
            }
        }

        //判断当前是ajax 还是普通请求，返回格式不同
        if (!(request.getHeader("accept").indexOf("application/json") > -1
                || (request.getHeader("X-Requested-With") != null
                && request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))) {

            model.addAttribute(WebCode.MSG, errMsg);
            return "sys/msg";
        } else {    //JSON返回方式
            response.setStatus(HttpStatus.OK.value());
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json; charset=utf-8");

            JSONObject jsonObject = new JSONObject();
            jsonObject.put(WebCode.SUCCESS, false);
            jsonObject.put(WebCode.MSG, errMsg);

            PrintWriter out = response.getWriter();
            out.print(jsonObject.toString());
            out.flush();
            out.close();
        }

        return null;
    }

}
