package com.yzj.jep.app.sub.service;

import com.yzj.jep.app.sub.model.MidFwlgljyxyxxb;
import com.yzj.jep.orm.service.CRUDService;

/**
* @author Administrator
* @description 针对表【mid_fwlgljyxyxxb_2】的数据库操作Service
* @createDate 2025-01-09 17:14:41
*/
public interface MidFwlgljyxyxxbService extends CRUDService<MidFwlgljyxyxxb, Integer> {

    void createEastMidFwlgljyxyxxb(String batchNo);
}
