package com.yzj.jep.app.bus.service;

import com.yzj.jep.app.bus.model.DlyInformation;
import com.yzj.jep.app.bus.model.GdExportFile;
import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.model.RelPsnSuspect;
import com.yzj.jep.orm.service.CRUDService;


import java.util.List;

public interface DlyInformationService  extends CRUDService<DlyInformation, Integer> {
    List<DlyInformation> selectList(DlyInformation dlyInformation);
}
