package com.yzj.jep.app.bus.mapper;

import com.yzj.jep.app.bus.model.Sequence;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@MyBatisMapper
public interface SequenceMapper {
    /*
     * 获取机构  - 机构股东信息
     * */
    public List<Sequence> getOrgShareOrg(@Param(value = "pid") Integer pid);

    /*
     * 获取机构  - 自然人股东信息
     * */
    public List<Sequence> getOrgSharePsn(@Param(value = "pid") Integer pid);

    /*
     * 获取机构投资信息
     * */
    public List<Sequence> getOrgInvest(@Param(value = "pid") Integer pid);

    /*
     * 获取机构附属-员工信息
     * */
    public List<Sequence> getOrgSubsidiary(@Param(value = "pid") Integer pid);

    /*
     * 获取自然人投资信息
     * */
    public List<Sequence> getPsnInvest(@Param(value = "pid") Integer pid);

    /*
     * 获取自然人亲属信息
     * */
    public List<Sequence> getPsnRelation(@Param(value = "pid") Integer pid);

    /*
     * 获取自然人单位信息
     * */
    public List<Sequence> getPsnUnit(@Param(value = "pid") Integer pid);
}
