package com.yzj.jep.app.sub.service;

import com.yzj.jep.app.sub.model.MidZjyylgljyxyxxb;
import com.yzj.jep.orm.service.CRUDService;


public interface MidZjyylgljyxyxxbService extends CRUDService<MidZjyylgljyxyxxb, Integer> {

    void createEastMidZjyylgljyxyxxb(String batchNo);
}
