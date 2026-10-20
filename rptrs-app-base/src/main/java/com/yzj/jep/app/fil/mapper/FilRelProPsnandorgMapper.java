/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年07月17日 15:30:13
 *
 */

package com.yzj.jep.app.fil.mapper;


import com.yzj.jep.app.fil.model.FilRelProPsnandorg;
import com.yzj.jep.app.fil.model.FilRelProVo;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;

import java.util.List;

@MyBatisMapper
public interface FilRelProPsnandorgMapper extends Mapper<FilRelProPsnandorg, Integer> {
    //获取自然人归属上级金融产品
    public List<FilRelProVo> selectRelProUpOfPsnInformation(String pid);

    //获取自然人关联的下级金融产品
    public List<FilRelProVo> selectRelProDownOfPsnInformation(String pid);
}