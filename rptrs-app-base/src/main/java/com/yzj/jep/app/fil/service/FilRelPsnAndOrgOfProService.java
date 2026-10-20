/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月23日 13:59:27
 *
 */

package com.yzj.jep.app.fil.service;

import com.yzj.jep.app.bus.model.RelPsnAndOrgOfPro;
import com.yzj.jep.app.fil.model.FilRelProVo;
import com.yzj.jep.app.fil.model.FilRelPsnAndOrgOfPro;
import com.yzj.jep.orm.service.CRUDService;


import java.util.List;
import java.util.Map;

public interface FilRelPsnAndOrgOfProService extends CRUDService<FilRelPsnAndOrgOfPro, Integer> {

    //获取机构归属上级金融产品
    public List<FilRelProVo> selectRelProUpOfOrgInformation(String pid);

    //获取机构关联下级金融产品
    public List<FilRelProVo> selectRelProDownOfOrgInformation(String pid);

}
