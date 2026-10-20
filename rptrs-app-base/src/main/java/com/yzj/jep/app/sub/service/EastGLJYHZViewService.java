package com.yzj.jep.app.sub.service;

import com.yzj.jep.app.sub.model.EastGljyhzbView;
import com.yzj.jep.orm.service.CRUDService;

import java.util.List;

public interface EastGLJYHZViewService extends CRUDService<EastGljyhzbView,Integer> {


    List<EastGljyhzbView> queryOne(String id);

}
