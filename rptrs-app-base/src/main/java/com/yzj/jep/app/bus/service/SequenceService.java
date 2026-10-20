package com.yzj.jep.app.bus.service;

import com.yzj.jep.app.bus.model.Sequence;

import java.util.List;

public interface SequenceService {
    /*
     * 获取机构  - 机构股东信息
     * */
    public List<Sequence> getOrgShareOrg(Integer pid);

    /*
     * 获取机构  - 自然人股东信息
     * */
    public List<Sequence> getOrgSharePsn(Integer pid);

    /*
     * 获取机构投资信息
     * */
    public List<Sequence> getOrgInvest(Integer pid);

    /*
     * 获取机构附属-员工信息
     * */
    public List<Sequence> getOrgSubsidiary(Integer pid);

    /*
     * 获取自然人投资信息
     * */
    public List<Sequence> getPsnInvest(Integer pid);

    /*
     * 获取自然人亲属信息
     * */
    public List<Sequence> getPsnRelation(Integer pid);

    /*
     * 获取自然人单位信息
     * */
    public List<Sequence> getPsnUnit(Integer pid);
}
