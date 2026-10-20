/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月23日 13:59:27
 *
 */

package com.yzj.jep.app.bus.mapper;


import com.yzj.jep.app.bus.model.*;

import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@MyBatisMapper
public interface RelPsnAndOrgOfProMapper extends Mapper<RelPsnAndOrgOfPro, Integer> {
//    default List<RelPsnVo> selectRelProUpOfPsnInformation() {
//        return selectRelProUpOfPsnInformation();
//    }

    public List<RelProVo> selectRelProUpOfPsnInformation(String pid);

    public List<RelProVo> selectRelProDownOfPsnInformation(String pid);

    public List<RelProVo> selectRelProUpOfOrgInformation(String pid);

    public List<RelProVo> selectRelProDownOfOrgInformation(String pid);

    public List<RelPsnVo> selectRelPsnOfProInformation(String pid);

    public List<RelOrgVo> selectRelOrgOfProInformation(String orgid);

    public List<RelPsnVo> selectRelPsnUpOfProInformation(String pid);

    public List<RelPsnVo> selectRelPsnDownOfProInformation(String pid);

    public List<RelOrgVo> selectRelOrgUpOfProInformation(String pid);

    public List<RelOrgVo> selectRelOrgDownOfProInformation(String pid);

    List<Map<String, Object>> queryOrgForOrgFrom(@Param(value = "pid") Integer pid);

    List<Map<String, Object>> queryPsnForOrgFrom(@Param(value = "pid") Integer pid);

    List<Map<String, Object>> queryOrgForPsnFrom(@Param(value = "pid") Integer pid);

    List<Map<String, Object>> queryPsnForPsnFrom(@Param(value = "pid") Integer pid);

    Integer insertForMapper(RelPsnAndOrgOfPro rel);


    List<Map<String, Object>> querySusOrgForOrgFrom(@Param(value = "pid") Integer pid);

    List<Map<String, Object>> querySusPsnForOrgFrom(@Param(value = "pid") Integer pid);

    List<Map<String, Object>> querySusOrgForPsnFrom(@Param(value = "pid") Integer pid);

    List<Map<String, Object>> querySusPsnForPsnFrom(@Param(value = "pid") Integer pid);

    //获取  自然人关联方信息  pid应该是自身
    List<RelPsnAndOrgOfPro> getUpInfoOnOrg(@Param(value = "pid") Integer pid);

    //获取  机构关联方信息  pid应该是自身
    List<RelPsnAndOrgOfPro> getUpInfoOnPsn(@Param(value = "pid") Integer pid);

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

    List<Map<String, Object>> getToAlignCodeByFromPidAndAlign(@Param("fromPid") Integer fromPid, @Param("fromAlignCode") String fromAlignCode, @Param("type") String type);

    void updateAlignCodeByPid(@Param("pid") Integer pid, @Param("fromAlignCode") String fromAlignCode, @Param("toAlignCode") String toAlignCode);
}