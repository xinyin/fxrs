package com.yzj.jep.app.workflow.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yzj.jep.sys.service.NotifyService;
import com.yzj.jep.workflow.api.external.service.SysNotifyService;

public class SysNotifyServiceImpl implements SysNotifyService {

	Logger logger = LoggerFactory.getLogger(SysNotifyServiceImpl.class);
	@Autowired
	private NotifyService notifyService;
	
	
	@Override
	public int createRemind(String receiver, String subject, String content) {
		return notifyService.createRemind(receiver, subject, content);
//		return 1;
	}
	

	@Override
	public int createTodo(String subject, String content, String receiver, String target, String targetType,
			String action) {
		return notifyService.createTodo(subject,content,receiver,target,targetType,action);
	}
}
