/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月22日 11:31:54
 *
 */

package com.yzj.jep.app.ins.service;

import com.yzj.jep.app.ins.model.BusinessTypeVo;
import com.yzj.jep.orm.service.CRUDService;
import com.yzj.jep.app.ins.model.TraInsenclosure;

import java.util.List;

public interface TraInsenclosureService extends CRUDService<TraInsenclosure, Integer> {

    void updateTransNum(List<Integer> docTableSelectItem, String transNum);

    void setTypeValue(String typeCode, String type, BusinessTypeVo businessTypeVo);
}
