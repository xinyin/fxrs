package com.yzj.jep.app.bus.service;

import com.yzj.jep.app.bus.model.DelRelHistory;
import com.yzj.jep.orm.service.CRUDService;

/**
 * ClassName:DelRelHistoryService
 * Package:com.yzj.jep.app.bus.service
 * Description:
 *
 * @date:2021/6/23 11:38
 * @author:
 */
public interface DelRelHistoryService extends CRUDService<DelRelHistory, Integer> {

    boolean selectByRelId(Integer pid);
}
