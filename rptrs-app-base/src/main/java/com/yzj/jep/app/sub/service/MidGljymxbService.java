package com.yzj.jep.app.sub.service;

import com.yzj.jep.app.sub.model.MidGljymxb;
import com.yzj.jep.app.sub.model.MidLyzylgljyxyxxb;
import com.yzj.jep.orm.service.CRUDService;


public interface MidGljymxbService extends CRUDService<MidGljymxb, Integer> {

    void createEastMidGljymxb(String batchNo);
}
