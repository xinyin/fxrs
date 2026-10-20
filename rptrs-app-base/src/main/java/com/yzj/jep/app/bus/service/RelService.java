/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月23日 13:59:27
 *
 */

package com.yzj.jep.app.bus.service;


import com.yzj.jep.app.bus.model.PsnAndOrgVo;
import com.yzj.jep.app.bus.model.Rel;
import com.yzj.jep.app.bus.model.RelPsnOrg;
import com.yzj.jep.app.design.model.RelInfoVo;
import com.yzj.jep.orm.service.CRUDService;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface RelService extends CRUDService<Rel, Integer> {
    void removeRel(String fromId, String toId, String fromType, String toType);

    //获取  自然人关联方信息  pid应该是自身
    List<Rel> getUpInfoOnOrg(@Param(value = "pid") Integer pid);

    //获取  机构关联方信息  pid应该是自身
    List<Rel> getUpInfoOnPsn(@Param(value = "pid") Integer pid);

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

    List<String> selectByFormPid(String fromPid);

    List<String>  selectByToPid(String toPid);

    void updateNeo4j(Map<String, Object> map, String nowDate);

    void updateNeo4j1(Map<String, Object> map, String nowDate);
    /**
     * 根据fromPid 和 fromAlignCode 查找 对应的 toPid 及 toAlignCode，relPid，前提是以org为from的
     * @param fromPid
     * @param fromAlignCode
     * @return
     */
    List<Map<String,Object>> getToAlignCodeByFromPidAndAlign(Integer fromPid, String fromAlignCode,String type);

    /**
     * 更新rel关联信息的fromAlignCode和toAlignCode
     * @param pid
     * @param fromAlignCode
     * @param toAlignCode
     */
    void updateAlignCodeByPid(Integer pid, String fromAlignCode, String toAlignCode);

    /**
     * 查询自然人关联方对应得所有关联关系
     * @param toPid
     * @return
     */
    List<Rel> queryRelForPsn(String toPid);

    /**
     * 查询所有关机构关联方得所有关联关系
     * @param toPid
     * @return
     */
    List<Rel> queryRelForOrg(String toPid);

    /**
     *解除关联关系入库
     * @param pid
     * @return
     */
    List<Integer> removeDelRel(Integer pid,String type);


    List<RelPsnOrg> getRelPsnOrg();

    void save(Rel rel);

    List<Rel>  selectSavePsnOrg(String fromIdentifyNumber, String toIdentifyNumber);

    List<RelPsnOrg> getRelPsnOrgByNameOrNum(String name, String code);

    /**
     * 解除关联关系（新）
     * @param fromType
     * @param fromId
     */
    void delRelation(String fromType,String fromId);
    /**
     * 解除关联关系
     * @param rel
     */
    void updatePsnOrgValid(Rel rel);
    /**
     * 查询所有关联关系
     * @param toPid
     * @return
     */
    List<Rel> queryRelForOrgAndPsn(String toPid);

    int  checkRel(String type, String code);

    List<RelPsnOrg> getRelPsnOrgByNameOrNumFront(String name, String code);

    List<RelPsnOrg> getRelPsnOrgFront();

    /**
     * 插入关系图谱信息
     * @param rel
     */
    void insertNeo4j(List<Rel> rel);
    /**
     * 删除关系图谱信息
     * @param rel
     */
    int deleteNeo4j(Rel rel);

    List<RelInfoVo> queryRelationsByParams(RelInfoVo param);

    List<Rel> queryByParam(Rel rel);

    /**
     * 获取所有的版本信息
     * @return
     */
    String getRecordNumber();

    /**
     * 比较yyyyMM返回是否新增版本信息
     * @param tips 返回到前端的信息组装
     */
    void isAddRecordNumber(Map<String, Object> tips);
}
