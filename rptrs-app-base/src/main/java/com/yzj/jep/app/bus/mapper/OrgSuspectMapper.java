package com.yzj.jep.app.bus.mapper;

import com.yzj.jep.app.bus.model.OrgSuspect;
import com.yzj.jep.app.bus.model.RelPsnSuspect;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisMapper
public interface OrgSuspectMapper {
    /*
     * 获取机构附属信息
     * */
    List<OrgSuspect> queryOrgSubInfo(@Param(value = "pid") Integer pid);

    /*
     * 获取机构 股东信息
     * */
    List<OrgSuspect> getOrgShareOrg(@Param(value = "pid") Integer pid);

    /*
     * 获取机构 投资信息
     * */
    List<OrgSuspect> getOrgInvest(@Param(value = "pid") Integer pid);
}
