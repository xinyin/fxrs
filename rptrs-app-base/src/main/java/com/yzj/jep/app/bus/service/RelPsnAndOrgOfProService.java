/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月23日 13:59:27
 *
 */

package com.yzj.jep.app.bus.service;


import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.orm.service.CRUDService;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface RelPsnAndOrgOfProService extends CRUDService<RelPsnAndOrgOfPro, Integer> {
    List<RelPsnVo> selectRelPsnOfProInformation(String pid);

    //金融产品关联机构信息
    List<RelOrgVo> selectRelOrgOfProInformation(String orgid);

    void removeRel(String fromId, String toId, String fromType, String toType);

    //获取自然人归属上级金融产品
    public List<RelProVo> selectRelProUpOfPsnInformation(String pid);

    //获取自然人关联的下级金融产品
    public List<RelProVo> selectRelProDownOfPsnInformation(String pid);

    //获取机构归属上级金融产品
    public List<RelProVo> selectRelProUpOfOrgInformation(String pid);

    //获取机构关联下级金融产品
    public List<RelProVo> selectRelProDownOfOrgInformation(String pid);

    //获取金融产品归属上级关联自然人
    public List<RelPsnVo> selectRelPsnUpOfProInformation(String pid);

    //获取金融产品下级关联自然人
    public List<RelPsnVo> selectRelPsnDownOfProInformation(String pid);

    //获取金融产品归属上级关联机构
    public List<RelOrgVo> selectRelOrgUpOfProInformation(String pid);

    //获取金融产品下级关联机构
    public List<RelOrgVo> selectRelOrgDownOfProInformation(String pid);

    //获取  自然人关联方信息  pid应该是自身
    List<RelPsnAndOrgOfPro> getUpInfoOnOrg(@Param(value = "pid") Integer pid);

    //获取  机构关联方信息  pid应该是自身
    List<RelPsnAndOrgOfPro> getUpInfoOnPsn(@Param(value = "pid") Integer pid);

    List<PsnAndOrgVo> selectPsnOrOrg(@Param(value = "pid") String pid);

    /**
     * 获取近亲属id
     *
     * @param pid
     * @return
     */
    List<Integer> getAllCloseRelatives(Integer pid);

    /**
     * 获取控股方信息
     *
     * @param map
     * @return
     */
    List<Map<String, Object>> getControllingShareholder(Map<String, Object> map);

    void insertNeo4jPsnToPro(RelPsn fromPsn, RelPro relPro, RelPsnAndOrgOfPro relPsnAndOrgOfPro);

    void insertNeo4jOrgToPro(RelOrg fromOrg, RelPro relPro, RelPsnAndOrgOfPro relPsnAndOrgOfPro);

    void updateNeo4j(Map<String, Object> map, String nowDate);

    List<Map<String, Object>> getToAlignCodeByFromPidAndAlign(Integer fromPid, String fromAlignCode, String type);

    void updateAlignCodeByPid(Integer pid, String fromAlignCode, String toAlignCode);
}
