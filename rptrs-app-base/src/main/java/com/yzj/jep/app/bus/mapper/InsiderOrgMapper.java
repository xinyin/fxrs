/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月23日 13:59:30
 *
 */

package com.yzj.jep.app.bus.mapper;


import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.model.RelOrgSuspect;
import com.yzj.jep.app.bus.model.RelOrgVo;
import com.yzj.jep.app.bus.model.RelPsnVo;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@MyBatisMapper
public interface InsiderOrgMapper extends Mapper<RelOrg, Integer> {
    List<RelOrg> getOrgPsn(String orgid);

    List<RelOrg> getTZOrgPsn(String orgid);

    List<RelOrg> queryOrgList();

    List<RelOrg> selectList(RelOrg relOrg);

    List<RelOrg> selectListNew(RelOrg relOrg);

    /*
     * 关联方查询
     * */
    List<Map<String, Object>> selectAffiliateMessageForOrg(RelOrg relOrg);

    List<Map<String, Object>> selectAffiliateMessageForSusOrg(RelOrgSuspect relOrgSuspect);


    List<RelOrg> selectByEffective();
}