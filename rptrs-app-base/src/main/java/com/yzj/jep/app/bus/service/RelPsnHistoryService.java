/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月23日 13:59:31
 *
 */

package com.yzj.jep.app.bus.service;


import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.orm.service.CRUDService;

import java.util.List;

public interface RelPsnHistoryService extends CRUDService<RelPsnHistory, Integer> {

    /**
     * 根据时点日期查询出所有的数据
     *
     * @param createTime
     * @return
     */
    List<RelPsnHistory> queryRecordNumberData(String createTime);

    /**
     *获取所有的版本号
     * @return
     */
    List<String> recordNumber();

    List<RelPsnHistory> queryRecordNumber( String recordType, String recordNumber);

}
