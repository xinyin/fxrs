package com.yzj.jep.app.pub.service;

import com.yzj.jep.app.pub.model.PubFileTemplate;
import com.yzj.jep.orm.service.CRUDService;

public interface PubFileTemplateService extends CRUDService<PubFileTemplate, Integer>{
    public PubFileTemplate selectById(int pid);


}
