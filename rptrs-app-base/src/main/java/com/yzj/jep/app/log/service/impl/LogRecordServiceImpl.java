/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年07月16日 14:41:49
 *
 */

package com.yzj.jep.app.log.service.impl;

import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yzj.jep.app.log.mapper.LogRecordMapper;
import com.yzj.jep.app.log.service.LogRecordService;
import com.yzj.jep.app.log.model.LogRecord;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Service
@Transactional
public class LogRecordServiceImpl extends MyBatisCRUDService<LogRecord, Integer> implements LogRecordService {

    Logger logger = LoggerFactory.getLogger(LogRecordServiceImpl.class);

    @Autowired
    private LogRecordMapper logRecordMapper;

    @Autowired
    private LogRecordService logRecordService;

    @Override
    public Mapper<LogRecord, Integer> getMapper() {
        return logRecordMapper;
    }

    private static final String LOG_CONTENT = "[类名]:%s,[方法]:%s,[参数]:%s,[IP]:%s";

    private String username;

    public String initUsername(String username) {
        if (!StringUtils.isEmpty(username)) {
            this.username = username;
        }
        return this.username;
    }

    @Override
    public void put(JoinPoint joinPoint, String methodName, String module, String description) {
        String userId;
        String ip;
        logger.info("日志切面开始，获取相应的参数数据......");
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            LogRecord log = new LogRecord();
            //username = ((JwtClient) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
            // 获取当前登录用户
            UserProfile profile = UserProfileUtils.getUserProfile();
            if (profile == null) {
                throw new ServiceException("user session may be expired.");
            } else {
                username = initUsername(profile.getUsername());
                userId = profile.getUserid();
                ip = profile.getIp();
            }
            log.setCreateById(userId);
            log.setCreateByName(username);
            log.setModule(module);
            log.setLogDescription(description);
            log.setLoginIp(ip);
            log.setLogContent(operateContent(joinPoint, methodName, ip, request));
            log.setCreateTime(new Date());
            log.setModifiedTime(new Date());
            logRecordService.insertSelective(log);
            logger.info("操作日志记录数据保存成功！");
        } catch (Exception e) {
            logger.info("操作日志记录失败！");
            e.printStackTrace();
        }
    }

    /**
     * 获取详细内容
     *
     * @param joinPoint
     * @param methodName
     * @param ip
     * @param request
     * @return
     * @throws ClassNotFoundException
     * @throws NotFoundException
     */
    public String operateContent(JoinPoint joinPoint, String methodName, String ip, HttpServletRequest request) throws ClassNotFoundException, NotFoundException {
        String className = joinPoint.getTarget().getClass().getName();
        Object[] params = joinPoint.getArgs();
        String classType = joinPoint.getTarget().getClass().getName();
        Class<?> clazz = Class.forName(classType);
        String clazzName = clazz.getName();
        Map<String, Object> nameAndArgs = getFieldsName(this.getClass(), clazzName, methodName, params);
        StringBuffer bf = new StringBuffer();
        if (!CollectionUtils.isEmpty(nameAndArgs)) {
            Iterator it = nameAndArgs.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                String key = (String) entry.getKey();
                String value = entry.getValue().toString();
                bf.append(key).append("=");
                bf.append(value).append("&");
            }
        }
        if (StringUtils.isEmpty(bf.toString())) {
            bf.append(request.getQueryString());
        }
        return String.format(LOG_CONTENT, className, methodName, bf.toString(), ip);
    }


    private Map<String, Object> getFieldsName(Class cls, String clazzName, String methodName, Object[] args) throws NotFoundException {
        Map<String, Object> map = new HashMap<String, Object>();

        ClassPool pool = ClassPool.getDefault();
        ClassClassPath classPath = new ClassClassPath(cls);
        pool.insertClassPath(classPath);

        CtClass cc = pool.get(clazzName);
        CtMethod cm = cc.getDeclaredMethod(methodName);
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        if (attr == null) {
            // exception
            return map;
        }
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
        for (int i = 0; i < cm.getParameterTypes().length; i++) {
            map.put(attr.variableName(i + pos), args[i]);//paramNames即参数名
        }
        return map;
    }

}