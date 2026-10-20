package com.yzj.jep.app.workflow.sms.service;

/**
 * @title: SmsNotifyService
 * @Author Whx
 * @Date: 2021/8/24 16:33
 * @Version 1.0
 */
public interface SmsNotifyService {

    /**
     * 创建提醒
     *
     * @param receiver 一个或多个用户
     * @param subject
     * @param content
     * @return
     */
    int createRemind(String receiver, String subject, String content);

    /**
     * 创建待办
     *
     * @param subject    主题
     * @param content    内容
     * @param receiver   接收人
     * @param target     目标
     * @param targetType 目标类型
     * @param action     动作
     * @return
     */
    int createTodo(String subject, String content, String receiver, String target, String targetType, String action);
}
