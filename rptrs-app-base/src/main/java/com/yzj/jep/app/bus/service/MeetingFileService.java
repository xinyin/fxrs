/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月02日 16:13:55
 *
 */

package com.yzj.jep.app.bus.service;


import com.yzj.jep.orm.service.CRUDService;
import com.yzj.jep.app.bus.model.MeetingFile;

public interface MeetingFileService extends CRUDService<MeetingFile, Integer> {

    MeetingFile selectByPid(Integer filepid);
}
