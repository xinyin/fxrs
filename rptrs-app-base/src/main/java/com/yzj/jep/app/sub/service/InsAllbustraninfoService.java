/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年12月19日 15:10:30
 *
 */

package com.yzj.jep.app.sub.service;


import com.yzj.jep.app.sub.model.InsAllbustraninfo;
import com.yzj.jep.orm.service.CRUDService;
import com.yzj.jep.app.sub.model.InsAllbustraninfo;

import java.awt.*;
import java.util.List;
import java.util.Map;

public interface InsAllbustraninfoService extends CRUDService<InsAllbustraninfo, Integer>{

    void extractCredit(Map map);

    List<Map<String, Object>> selectAllTrans(String year, String subQuarter,String tid);


}
