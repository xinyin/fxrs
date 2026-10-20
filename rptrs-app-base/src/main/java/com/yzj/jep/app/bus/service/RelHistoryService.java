/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月23日 13:59:31
 *
 */

package com.yzj.jep.app.bus.service;


import com.yzj.jep.app.bus.model.Rel;
import com.yzj.jep.app.bus.model.RelHistory;
import com.yzj.jep.app.bus.model.RelPsnHistory;
import com.yzj.jep.orm.service.CRUDService;

import java.util.List;

public interface RelHistoryService extends CRUDService<RelHistory, Integer> {

    /**
     * 保存关系修改
     * @param rel
     * @param type
     */
    void saveHistory(Rel rel, String type);

    /**
     *获取所有的版本号
     * @return
     */
    List<String> recordNumber();

    /**
     * 获取符合条件的历史数据
     * @return
     * @param recordNumber 历史版本
     * @param recordType 变更类型
     */
    List<RelHistory> queryRecordNumber(String recordType,String recordNumber);

    /**
     * 获取符合条件的历史数据
     * @return
     */
    List<RelHistory> queryRecordNumberData(String createTime);
}
