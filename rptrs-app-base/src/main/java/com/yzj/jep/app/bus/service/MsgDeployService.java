/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2021 pactera.com. All rights reserved.
 *
 * Generated at 2021年06月28日 16:09:20
 *
 */

package com.yzj.jep.app.bus.service;

import com.yzj.jep.orm.service.CRUDService;
import com.yzj.jep.app.bus.model.MsgDeploy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface MsgDeployService extends CRUDService<MsgDeploy, Integer>{
    public Map<String,Object> fileUpload(HttpServletRequest request, HttpServletResponse response);
    public List<MsgDeploy> selectReleaseMsg();
    public List<MsgDeploy>  selectReleaseNews();
}
