package com.yzj.jep.app.sub.service;

import com.yzj.jep.app.sub.model.MidLyzylgljyxyxxb;
import com.yzj.jep.orm.service.CRUDService;


public interface MidLyzylgljyxyxxbService extends CRUDService<MidLyzylgljyxyxxb, Integer> {

    void createEastMidLyzylgljyxyxxb(String batchNo);
}
