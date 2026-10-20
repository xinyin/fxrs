/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月23日 14:26:15
 *
 */

package com.yzj.jep.app.bus.service;

import com.yzj.jep.app.bus.model.Rel;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.vo.PersonOrgVo;
import com.yzj.jep.app.bus.vo.PersonRelationVo;
import com.yzj.jep.orm.service.CRUDService;
import com.yzj.jep.app.bus.model.RelPsnSuspect;

import java.util.List;
import java.util.Map;

public interface RelPsnSuspectService extends CRUDService<RelPsnSuspect, Integer> {

    /*
     * 根据主键获取自然人附属信息
     * */
    List<PersonRelationVo> queryPsnRelation(Integer pid);

    /*
     * 根据主键获取下级自然人附属信息
     * */
    List<PersonRelationVo> queryPsnRelationDown(Integer pid);


    /*
     * 根据主键获取自然人机构信息
     * */
    List<PersonOrgVo> queryPsnOrg(Integer pid);

    /*
     * 自然人信息删除
     * */
    Map<String, Object> delPsn(Integer pid);

    /*
     * 自然人基本信息新增
     * */
    Map<String, Object> insertPsnMsg(RelPsnSuspect relPsnSuspect);

    /*
     * 自然人基本信息修改
     * */
    Map<String, Object> updatePsnMsg(RelPsnSuspect relPsnSuspect);

    /*
     * 自然人附属信息新增
     * */
    Map<String, Object> insertPsgRelMsg(PersonRelationVo personRelationVo);

    /*
     * 自然人附属信息更新
     * */
    Map<String, Object> updatePsgRelMsg(PersonRelationVo personRelationVo);

    /*
     * 自然人附属信息删除
     * */
    Map<String, Object> delPsgRelMsg(Integer pidForRel);

    /*
     *自然人公司信息新增
     * */
    Map<String, Object> insertPsgOrg(PersonOrgVo personOrgVo);

    /*
     *自然人公司信息更新
     * */
    Map<String, Object> updatePsgOrg(PersonOrgVo personOrgVo);

    /*
     *自然人公司信息删除
     * */
    Map<String, Object> delPsgOrg(Integer pidForRel);

    /*
     * 将疑似关联方  转变为关联方
     * */
    Map<String, Object> replacePsn(Integer pid, RelPsn relPsn, List<Rel> rels);


}
