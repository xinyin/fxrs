/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月02日 14:30:54
 *
 */

package com.yzj.jep.app.sub.service;

import com.yzj.jep.orm.service.CRUDService;
import com.yzj.jep.app.sub.model.Loan;

import java.util.Map;

public interface LoanService extends CRUDService<Loan, Integer>{

    void extractLoan(Map map, String nowDate);
}
