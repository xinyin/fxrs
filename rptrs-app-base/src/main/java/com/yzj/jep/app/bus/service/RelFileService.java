/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年04月08日 18:30:22
 *
 */

package com.yzj.jep.app.bus.service;


import com.yzj.jep.app.bus.model.RelFile;
import com.yzj.jep.orm.service.CRUDService;

import java.util.List;

public interface RelFileService extends CRUDService<RelFile, Integer> {
    void updateRelSerial(List<Integer> docTableSelectItem, String relSerial, String merge);
}
