package com.yzj.jep.app.sub.service;

import com.yzj.jep.app.sub.model.MidBxywhqtlgljyxyxxb;
import com.yzj.jep.orm.service.CRUDService;
/**
* @author Administrator
* @description 针对表【mid_bxywhqtlgljyxyxxb_2】的数据库操作Service
* @createDate 2025-01-09 17:14:46
*/
public interface MidBxywhqtlgljyxyxxbService extends CRUDService<MidBxywhqtlgljyxyxxb, Integer> {

    void createEastMidBxywhqtlgljyxyxxb(String batchNo);
}
