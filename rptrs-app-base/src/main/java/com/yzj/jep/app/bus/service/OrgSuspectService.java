package com.yzj.jep.app.bus.service;

import com.yzj.jep.app.bus.model.OrgSuspect;

import java.util.List;

public interface OrgSuspectService {
    /*
     * 获取机构附属信息
     * */
    List<OrgSuspect> queryOrgSubInfo(Integer pid);

    /*
     * 获取机构 股东信息
     * */
    List<OrgSuspect> getOrgShareOrg(Integer pid);

    /*
     * 获取机构 投资信息
     * */
    List<OrgSuspect> getOrgInvest(Integer pid);
}
