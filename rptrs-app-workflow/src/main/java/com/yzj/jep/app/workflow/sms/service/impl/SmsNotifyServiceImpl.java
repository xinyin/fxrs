package com.yzj.jep.app.workflow.sms.service.impl;

import com.yzj.jep.app.workflow.sms.service.SmsNotifyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @title: SmsNotifyServiceImpl
 * @Author Whx
 * @Date: 2021/8/24 16:33
 * @Version 1.0
 *  此处只提醒，无需保存短信通知记录
 */
@Service
public class SmsNotifyServiceImpl implements SmsNotifyService {

    private Logger logger = LoggerFactory.getLogger(SmsNotifyServiceImpl.class);

    /**
     * 创建提醒
     * @param receiver 一个或多个用户
     * @param subject
     * @param content
     * @return
     */
    @Override
    public int createRemind(String receiver, String subject, String content) {
        logger.info("***********************此处处理发送短信的业务流程***********************");
        /**发起流程者接受的信息*/
        logger.info("消息接受人：【{}】",receiver);
        logger.info("消息主题：【{}】",subject);
        logger.info("消息内容：【{}】",content);
        return 0;
    }

    /**
     * 创建待办
     * @param subject 主题
     * @param content 内容
     * @param receiver 接收人
     * @param target 目标
     * @param targetType 目标类型
     * @param action 动作
     * @return
     */
    @Override
    public int createTodo(String subject, String content, String receiver, String target, String targetType, String action) {
        logger.info("***********************此处处理发送短信的业务流程***********************");
        logger.info("消息接受人：【{}】",receiver);
        logger.info("消息主题：【{}】",subject);
        logger.info("消息内容：【{}】",content);
        logger.info("消息目标：【{}】",target);
        logger.info("目标类型：【{}】",targetType);
        logger.info("动作：【{}】",action);
        return 0;
    }
}
