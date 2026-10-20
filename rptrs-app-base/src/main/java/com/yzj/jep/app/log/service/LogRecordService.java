/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年07月16日 14:41:49
 *
 */

package com.yzj.jep.app.log.service;

import com.yzj.jep.app.log.model.LogRecord;
import com.yzj.jep.orm.service.CRUDService;
import org.aspectj.lang.JoinPoint;

public interface LogRecordService extends CRUDService<LogRecord, Integer> {
    void put(JoinPoint joinPoint, String methodName, String module, String description);
}
