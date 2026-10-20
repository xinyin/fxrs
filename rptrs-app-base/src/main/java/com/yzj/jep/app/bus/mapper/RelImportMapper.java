package com.yzj.jep.app.bus.mapper;

import com.yzj.jep.app.bus.model.RelImport;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

@MyBatisMapper
public interface RelImportMapper extends Mapper<RelImport, Integer> {
        void clearTable();
        int selectCountByToIdentifyNumber(@Param("identifyNumber") String identifyNumber);
}
