/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年07月10日 18:04:45
 *
 */

package com.yzj.jep.app.fil.mapper;


import com.yzj.jep.app.bus.model.RelPsnVo;
import com.yzj.jep.app.fil.model.FilRelPsn;
import com.yzj.jep.app.fil.model.FilRelPsnVo;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;

import java.util.List;

@MyBatisMapper
public interface FilRelPsnMapper extends Mapper<FilRelPsn, Integer> {
    List<FilRelPsnVo> selectRelPsnInformation(String pid);

    public List<FilRelPsnVo> selectDownRelPsnInformation(String pid);

    List<FilRelPsn> selectFilRelPsnByfalgPid(String falgPid);
    List<FilRelPsn> selectFilRelPsnByfalgPidDesc(FilRelPsn filRelPsn);

}