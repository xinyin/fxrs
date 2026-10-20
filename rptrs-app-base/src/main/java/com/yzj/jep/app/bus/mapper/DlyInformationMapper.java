package com.yzj.jep.app.bus.mapper;

import com.yzj.jep.app.bus.model.DlyInformation;
import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.model.RelPsnSuspect;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;

import java.util.List;

@MyBatisMapper
public interface DlyInformationMapper  extends Mapper<DlyInformation, Integer> {
    List<DlyInformation> selectList(DlyInformation dlyInformation);
}
