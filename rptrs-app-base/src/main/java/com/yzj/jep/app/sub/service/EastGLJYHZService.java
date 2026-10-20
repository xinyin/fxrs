package com.yzj.jep.app.sub.service;

import com.yzj.jep.app.sub.model.EastGljyhzb;
import com.yzj.jep.orm.service.CRUDService;

import java.util.List;

public interface EastGLJYHZService extends CRUDService<EastGljyhzb,Integer> {

    public void eastExtract(String date);

    List<EastGljyhzb> queryAll();
}
