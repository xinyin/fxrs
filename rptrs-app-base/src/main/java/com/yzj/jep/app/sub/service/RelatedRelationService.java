/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月02日 09:48:52
 *
 */

package com.yzj.jep.app.sub.service;

import com.yzj.jep.orm.service.CRUDService;
import com.yzj.jep.app.sub.model.RelatedRelation;

import java.util.Map;

public interface RelatedRelationService extends CRUDService<RelatedRelation, Integer>{

    void extractRelatedRelation(Map map, String nowDate);
}
