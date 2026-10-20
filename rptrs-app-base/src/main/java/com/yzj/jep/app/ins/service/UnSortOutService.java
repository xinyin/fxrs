/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2021 pactera.com. All rights reserved.
 *
 * Generated at 2021年07月19日 10:12:36
 *
 */

package com.yzj.jep.app.ins.service;


import com.yzj.jep.app.ins.model.UnSortOut;
import com.yzj.jep.orm.service.CRUDService;

public interface UnSortOutService extends CRUDService<UnSortOut, Integer> {

    public void addTrad(UnSortOut unSortOut,String contractNo);

    public Integer getRelId(String relType, String relNumber);

    Boolean scheduledTasks();

}
