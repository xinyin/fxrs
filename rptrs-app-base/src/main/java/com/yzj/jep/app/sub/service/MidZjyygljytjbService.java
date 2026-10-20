package com.yzj.jep.app.sub.service;

import com.yzj.jep.app.sub.model.MidZjyygljytjb;
import com.yzj.jep.orm.service.CRUDService;


public interface MidZjyygljytjbService extends CRUDService<MidZjyygljytjb, Integer> {

    void createEastMidZjyygljytjb(String batchNo);
}
