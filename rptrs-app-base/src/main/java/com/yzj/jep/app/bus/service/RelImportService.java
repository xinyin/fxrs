package com.yzj.jep.app.bus.service;

import com.yzj.jep.app.bus.model.RelImport;
import com.yzj.jep.orm.service.CRUDService;

public interface RelImportService extends CRUDService<RelImport, Integer> {
    void clearTable();
     int selectCountByToIdentifyNumber(String toIdentifyNumber);

}
