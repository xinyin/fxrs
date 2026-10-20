/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月23日 13:59:30
 *
 */

package com.yzj.jep.app.bus.service;


import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.orm.service.CRUDService;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface RelOrgHistoryService extends CRUDService<RelOrgHistory, Integer> {


    List<RelOrgHistory> queryRecordNumberData(String createTime);

    List<RelOrgHistory> queryRecordNumber(String recordType, String recordNumber);

    List<String> recordNumber();

}
