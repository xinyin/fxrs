/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月23日 13:59:30
 *
 */

package com.yzj.jep.app.bus.mapper;

import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@MyBatisMapper
public interface RelOrgMapper extends Mapper<RelOrg, Integer> {

    List<RelOrgVo> selectRelProOfOrgInformation(String pid);

    List<RelPsnVo> selectRelPsnOfOrgInformation(String pid);

    List<RelOrgVo> selectRelOrgOfOrgInformation(String pid);

    List<RelPsnVo> getOrgPsn(@Param(value = "orgid") String orgid);

    List<RelPsnVo> getDownOrgPsn(@Param(value = "orgid") String orgid);

    List<RelOrgVo> getOrgOrg(@Param(value = "orgid") String orgid);

    //查找机构下的所属金融产品
    List<RelProVo> getOrgDownPro(@Param(value = "orgid") String orgid);

    //查找机构下的所属金融产品
    List<RelProVo> getOrgUpPro(@Param(value = "orgid") String orgid);

    List<RelOrgVo> getDownOrgOrg(@Param(value = "orgid") String orgid);

    //所属关联方
    List<RelOrgVo> selectRelOrgOfProInformation(@Param(value = "orgid") String orgid);

    //所属关联方
    List<RelOrgVo> getTZOrgPsn(@Param(value = "orgid") String orgid);

    //下级关联方
    List<RelOrgVo> getDownTZOrgPsn(String orgid);

    List<RelOrg> queryOrgList();

    List<RelOrg> selectList(RelOrg relOrg);

    //selectListQuota
    List<RelOrg> selectListQuota(RelOrg relOrg);

    boolean insertExcelToDb(List<RelOrg> list);

    /*
     * 关联方查询
     * */
    List<Map<String, Object>> selectAffiliateMessageForOrg(RelOrg relOrg);

    List<Map<String, Object>> selectAffiliateMessageForSusOrg(RelOrgSuspect relOrgSuspect);

    /**
     * 根据机构的主键查询关系机构信息
     *
     * @param pid
     * @return
     */
    List<Map<String, Object>> selectRelOrgInformation(Integer pid);


    String getAllPercent();

    List<Map<String, Object>> getSingle();

    //金融产品所属关系
    List<RelOrgVo> getProOrgPsn(String valueOf);

    //金融产品下一级关系
    List<RelOrgVo> getDownProOrgPsn(String valueOf);

    List<RelOrgVo> selecUpOrg(String valueOf);

    List<RelOrgVo> selecDownOrg(String valueOf);

    //    String  getSingle();
    /*查询定位码*/
    List<RelPsn> selecPsnidnumber(String psnIdentifyNumber);

    /*查询关联关系*/
    List<Rel> selectBusRel(String toPid);

    void updateIsIterateByPid(@Param("pid") Integer pid, @Param("isIterate") Integer isIterate);

    String getMemGroup(@Param(value = "relId") String relId);

    BigDecimal getFundsAgreementAmount(@Param(value = "contractSigningDate") String contractSigningDate, @Param(value = "memGroup") String memGroup);

    List<RelOrg> selectByEffective();

    List<RelOrg> selectById(@Param("identify_type") String identify_type, @Param("unisc_code")String unisc_code );

    String getMaxBh();

    List<RelOrgVo> selectOrgByRelToPid(@Param("pid")String pid);

    List<RelOrgVo> selectOrgByProToPid(@Param("pid")String rootId);

    List<RelOrgVo> selectOrgByRelFromPid(@Param("pid")String rootId);

    List<RelOrgVo> selectOrgByProFromPid(@Param("pid")String rootId);

    List<String> isRecordNumber(String yyyyMM);

    List<String> recordNumber();
}
