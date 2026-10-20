package com.yzj.jep.app.workflow.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.sys.model.AppConfig;
import com.yzj.jep.sys.model.Attachment;
import com.yzj.jep.sys.model.AttachmentType;
import com.yzj.jep.sys.service.AppConfigService;
import com.yzj.jep.sys.service.AttachmentService;
import com.yzj.jep.sys.service.AttachmentTypeService;
import com.yzj.jep.workflow.api.external.service.SysConfigService;

public class SysConfigServiceImpl implements SysConfigService {

	Logger logger = LoggerFactory.getLogger(SysConfigServiceImpl.class);
	
	@Autowired
	private AppConfigService appConfigService;
	
	@Autowired
	private AttachmentService attachmentService;
	
	@Autowired
	private AttachmentTypeService attachmentTypeService;
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getConfigValue(String configCode) throws ServiceException {
		
		return appConfigService.getConfigValue(configCode);
	}

	@Override
	public AppConfig getAppConfig(String code) {
		return appConfigService.get(code);
	}


	@Override
	public List<Attachment> select(String entityId, String uploadSession, String entityType) {
		return attachmentService.select(entityId, uploadSession, entityType);
	}


	@Override
	public AttachmentType getAttachmentType(String attachTypeCode) {
		return attachmentTypeService.get(attachTypeCode);
	}
	
}
