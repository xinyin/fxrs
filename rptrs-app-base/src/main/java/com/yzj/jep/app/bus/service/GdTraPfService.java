/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年04月02日 16:21:56
 *
 */

package com.yzj.jep.app.bus.service;

import com.yzj.jep.app.bus.model.GdTraPf;
import com.yzj.jep.orm.service.CRUDService;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface GdTraPfService extends CRUDService<GdTraPf, Integer> {
    /*Integer queryMaxID();*/
    /* void updateStatus(Integer pid);*/
    void del(Integer pid);

    void approvalReturn(Integer pid);

    Integer changeCreateTime(@Param(value = "createTime") Date createTime, @Param(value = "year") String year);

    List<GdTraPf> selectGdTraPfExprot(@Param("year") String year);
}
