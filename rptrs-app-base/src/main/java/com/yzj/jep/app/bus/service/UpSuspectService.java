package com.yzj.jep.app.bus.service;

import com.yzj.jep.app.bus.model.suspect.UpSuspect;

import java.util.List;

public interface UpSuspectService {
    /*
     * 获取自然人的上级信息
     * */
    List<UpSuspect> getUpPsnInfo(Integer pid);


    /*
     * 获取机构的上级信息
     * */
    List<UpSuspect> getUpOrgInfo(Integer pid);

    //获取关联方信息  pid应该是自身
    List<UpSuspect> getUpInfoOnOrg(Integer pid);

    //获取关联方信息  pid应该是自身
    List<UpSuspect> getUpInfoOnPsn(Integer pid);
}
