/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月23日 13:59:27
 *
 */

package com.yzj.jep.app.fil.mapper;

import com.yzj.jep.app.fil.model.FilRelProVo;
import com.yzj.jep.app.fil.model.FilRelPsnAndOrgOfPro;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;


import java.util.List;


@MyBatisMapper
public interface FilRelPsnAndOrgOfProMapper extends Mapper<FilRelPsnAndOrgOfPro, Integer> {
    //获取机构归属上级金融产品
    public List<FilRelProVo> selectRelProUpOfOrgInformation(String pid);

    //获取机构关联下级金融产品
    public List<FilRelProVo> selectRelProDownOfOrgInformation(String pid);

}