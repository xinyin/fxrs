/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年05月10日 10:36:06
 *
 */

package com.yzj.jep.app.ins.service;

import com.yzj.jep.app.bus.model.ApprovalPush;
import com.yzj.jep.orm.service.CRUDService;

public interface ApprovalPushService extends CRUDService<ApprovalPush, Integer> {

    /**
     * 推送审批结果
     * @param contractNo
     */

    void pushResult(String contractNo, String status);

    void emailSends(String param);
}
