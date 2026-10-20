/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月01日 17:05:11
 *
 */

package com.yzj.jep.app.sub.service;

import com.yzj.jep.app.sub.model.TemplateDetails;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.service.CRUDService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface TemplateDetailsService extends CRUDService<TemplateDetails, Integer> {
    boolean createSubFile(Integer templateId, List<Filter> filters, String subYear, String subQuarter, String fileName, String subType) throws Exception;

    boolean createSubmitFile(Integer templateId, List<Filter> filters, String fileName, String subType) throws Exception;

    boolean createSingleSubFile(Integer templateId, List<Filter> filters, String fileName, String subType) throws Exception;

    boolean createQuarterSubFile(Integer templateId, List<Filter> filters, String fileName, String subYear, String subQuarter, String subType, HttpServletRequest request) throws Exception;

    Map<String, Object> getcheckItem(Integer templateId);

    boolean addEastView(List<Filter> filters, String fileName,String subType);
    void creatExcel(String bean, String fileName, Map<String, Object> params) throws Exception;


}
