/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月23日 13:59:27
 *
 */

package com.yzj.jep.app.bus.mapper;


import com.yzj.jep.app.bus.model.PsnAndOrgVo;
import com.yzj.jep.app.bus.model.Rel;
import com.yzj.jep.app.bus.model.RelPsnOrg;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@MyBatisMapper
public interface RelMapper extends Mapper<Rel, Integer> {


    List<Map<String, Object>> queryOrgForOrgFrom(@Param(value = "pid") Integer pid);

    List<Map<String, Object>> queryPsnForOrgFrom(@Param(value = "pid") Integer pid);

    List<Map<String, Object>> queryOrgForPsnFrom(@Param(value = "pid") Integer pid);

    List<Map<String, Object>> queryPsnForPsnFrom(@Param(value = "pid") Integer pid);

    Integer insertForMapper(Rel rel);


    List<Map<String, Object>> querySusOrgForOrgFrom(@Param(value = "pid") Integer pid);

    List<Map<String, Object>> querySusPsnForOrgFrom(@Param(value = "pid") Integer pid);

    List<Map<String, Object>> querySusOrgForPsnFrom(@Param(value = "pid") Integer pid);

    List<Map<String, Object>> querySusPsnForPsnFrom(@Param(value = "pid") Integer pid);

    //获取  自然人关联方信息  pid应该是自身
    List<Rel> getUpInfoOnOrg(@Param(value = "pid") Integer pid);

    //获取  机构关联方信息  pid应该是自身
    List<Rel> getUpInfoOnPsn(@Param(value = "pid") Integer pid);

    //获取 所关联的自然人或机构的信息
    List<PsnAndOrgVo> queryPsnOrOrg(@Param(value = "pid") String pid);

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

    List<Rel> selectByFormPid(String fromPid);

    List<Rel> selectByToPid(String toPid);
    List<Map<String,Object>> getToAlignCodeByFromPidAndAlign(@Param("fromPid") Integer fromPid,@Param("fromAlignCode") String fromAlignCode,@Param("type") String type);
    void updateAlignCodeByPid(Integer pid,String fromAlignCode,String toAlignCode);

    /**
     * 根据参数参数查询
     * @param rel
     * @return
     */
    List<Rel> selectByParam(Rel rel);

    List<RelPsnOrg> getRelPsnOrg();

    void save(Rel rel);

    List<Rel> selectSavePsnOrg(@Param("fromIdentifyNumber")String fromIdentifyNumber, @Param("toIdentifyNumber")String toIdentifyNumber);

    List<RelPsnOrg> getRelPsnOrgByNameOrNum(@Param("name")String name, @Param("code")String code);

    List<RelPsnOrg> getRelPsnOrgByNameOrNumFront(@Param("name")String name, @Param("code")String code);

    List<RelPsnOrg> getRelPsnOrgFront();

    int checkRel(@Param("type")String type, @Param("code")String code);

    List<com.yzj.jep.app.design.model.RelInfoVo> queryRelationsByParams(@Param("param") com.yzj.jep.app.design.model.RelInfoVo param);

    List<String> isRecordNumber(@Param("param") String yyyyMM);

    List<String> recordNumber();
}
