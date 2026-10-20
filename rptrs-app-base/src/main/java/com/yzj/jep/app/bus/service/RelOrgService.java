/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月23日 13:59:30
 *
 */

package com.yzj.jep.app.bus.service;


import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.orm.service.CRUDService;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface RelOrgService extends CRUDService<RelOrg, Integer> {

    List<RelOrgVo> selectRelProOfOrgInformation(String pid);

    List<RelPsnVo> selectRelPsnOfOrgInformation(String pid);

    List<RelOrgVo> selectRelOrgOfOrgInformation(String pid);

    List<RelPsnVo> getOrgPsn(String orgid);

    List<RelPsnVo> getDownOrgPsn(String orgid);

    List<RelOrgVo> getOrgOrg(String orgid);

    //查找机构下的所属金融产品
    List<RelProVo> getOrgUpPro(String orgid);

    //查找机构下的下级金融产品
    List<RelProVo> getOrgDownPro(String orgid);

    List<RelOrgVo> getDownOrgOrg(String orgid);

    //金融产品关联机构信息
    List<RelOrgVo> selectRelOrgOfProInformation(String orgid);

    //自然人所属关联方
    List<RelOrgVo> getTZOrgPsn(String orgid);

    //自然人下级关联方
    List<RelOrgVo> getDownTZOrgPsn(String orgid);

    List<RelOrg> queryOrgList();

    List<RelOrg> selectList(RelOrg relOrg);

    void insertExcelToDb(InputStream in, MultipartFile file);

    RelOrg getGdInfo(String code);

    /**
     * 根据机构的主键查询关系机构信息
     *
     * @param pid
     * @return
     */
    List<RelOrgVo> queryRelOrgInformation(Integer pid);

    List<RelOrg> selectListQuota(RelOrg relOrg);

    void returnPsnTask(JSONObject json, String memo);

    String getAllPercent();

    List<Map<String, Object>> getSingle();
//    String getSingle();

    void insertNeo4jOTO(RelOrg fromOrg, RelOrg relOrg, Rel rel);

    void insertNeo4jPROTO(RelPro fromPro, RelOrg relOrg, Rel rel);

    void insertNeo4jPsnTOrg(RelPsn fromPsn, RelOrg relOrg, Rel rel);

    //金融产品所属关联方
    List<RelOrgVo> getProOrgPsn(String valueOf);

    //金融产品下一级关联方
    List<RelOrgVo> getDownProOrgPsn(String valueOf);

    List<RelOrgVo> selecUpOrg(String valueOf);

    List<RelOrgVo> selecDownOrg(String valueOf);

    void updateIsIterateByPid(Integer pid, Integer isIterate);

    String getMemGroup(String relId);

    BigDecimal getFundsAgreementAmount(String contractSigningDate, String memGroup);

    List<RelOrg> selectByProEffective();

    List<RelOrg> selectById(String identify_type,String unisc_code);


    String getMaxBh();

    List<RelOrgVo> selectOrgByRelToPid(String pid);

    List<RelOrgVo> selectOrgByProToPid(String rootId);

    List<RelOrgVo> selectOrgByRelFromPid(String rootId);

    List<RelOrgVo> selectOrgByProFromPid(String rootId);

    void isAddRecordNumber(Map<String, Object> tips);

    RelOrg selectByIdentifyNumber(String uniscCode);

}
