/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年05月25日 14:22:10
 *
 */

package com.yzj.jep.app.bus.mapper;

import com.yzj.jep.app.bus.model.RelPro;
import com.yzj.jep.app.bus.model.RelProVo;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.awt.*;
import java.util.List;

@MyBatisMapper
public interface RelProMapper extends Mapper<RelPro, Integer> {
    List<RelProVo> selectRelProOrg(String pid);

    List<RelProVo> selectDownRelProOrg(String pid);

    void updateIsIterateByPid(@Param("pid") Integer pid, @Param("isIterate") Integer isIterate);

    List<RelProVo> selectProRelByToPid(@Param("pid")String pid);

    List<RelProVo> selectProByRelFromPid(@Param("pid")String rootId);

    List<RelPro> selectRelPro();
}
