/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月23日 13:59:31
 *
 */

package com.yzj.jep.app.bus.service;


import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.orm.service.CRUDService;

import java.util.List;
import java.util.Map;

public interface RelPsnService extends CRUDService<RelPsn, Integer> {
    List<RelPsnVo> selectRelPsnOfProInformation(String pid);

    List<RelPsnVo> selectRelPsnInformation(String pid);

    List<RelPsnVo> selectDownRelPsnInformation(String pid);

    List<RelPsn> selectPsnInformation(RelPsn relPsn);

    RelPsn getGdInfo(String code);

    void returnPsnTask(JSONObject json, String memo);

    void insertNeo4jProToPsn(RelPro fromPro, RelPsn relPsn, Rel rel);

    void insertNeo4jPsnToPsn(RelPsn fromPsn, RelPsn relPsn, Rel rel);

    void insertNeo4jOrgToPsn(RelOrg fromOrg, RelPsn relPsn, Rel rel);


    List<RelPsnVo> selectRelProInformation(String pid);

    List<RelPsnVo> selectDownRelProInformation(String pid);

    void updateIsIterateByPid(Integer pid, Integer isIterate);

    /*List<PsnAndOrgVo> selectBatQue(String type, String pid);*/

    List<PsnAndOrgVo> selectBatQueMax(PsnAndOrgVo psnAndOrgVo);

    List<RelPsn> selectByProEffective();

    String getMaxBh();

    String getIdentifyType(String relType, String relNumber);

    List<RelPsnVo> selectPsnByRelToPid(String pid);
    List<RelPsnVo> selectPsnByProToPid(String rootId);
    List<RelPsnVo> selectPsnByRelFromPid(String rootId);
    List<RelPsnVo> selectPsnByProFromPid(String rootId);
    List<RelPsnVo> selectRelationDataByToPid(String rootId);
    List<RelPsnVo> selectRelationDataByFromPid(String rootId);

    void isAddRecordNumber(Map<String, Object> tips);
    //根据证件号码查询关联方信息
    RelPsn selectByIdentifyNumber(String identifyNumber);
}
