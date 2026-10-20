/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月16日 13:38:15
 *
 */

package com.yzj.jep.app.bus.service;

import com.yzj.jep.app.bus.model.SubmissionFile;
import com.yzj.jep.orm.service.CRUDService;

import java.util.List;
import java.util.Map;

public interface SubmissionFileService extends CRUDService<SubmissionFile, Integer> {
    List<SubmissionFile> queryByCon(Map map);

    SubmissionFile queryByID(Integer ids);
}

