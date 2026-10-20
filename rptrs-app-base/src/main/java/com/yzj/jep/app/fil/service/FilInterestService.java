/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年07月21日 14:55:57
 *
 */

package com.yzj.jep.app.fil.service;


import com.yzj.jep.app.fil.model.FilInterest;
import com.yzj.jep.orm.service.CRUDService;

import java.util.List;

public interface FilInterestService extends CRUDService<FilInterest, Integer> {

    List<FilInterest> selectfilInterestByfalgPid(String flagPid);
}
