
/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月23日 13:59:31
 *
 */

package com.yzj.jep.app.bus.mapper;


import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@MyBatisMapper
public interface RelPsnMapper extends Mapper<RelPsn, Integer> {
    //归属信息
    List<RelPsnVo> selectRelPsnInformation(@Param(value = "pid") String pid);

    //产品关联自然人信息
    List<RelPsnVo> selectRelPsnOfProInformation(@Param(value = "pid") String pid);

    //下级信息
    List<RelPsnVo> selectDownRelPsnInformation(@Param(value = "pid") String pid);

    List<RelPsn> selectPsnInformation(RelPsn relPsn);

    List<Map<String, Object>> selectAffiliateMessageForPsn(RelPsn relPsn);

    List<Map<String, Object>> selectAffiliateMessageForSusPsn(RelPsnSuspect relPsnSuspect);

    List<RelPsnVo> selectRelProInformation(String pid);

    List<RelPsnVo> selectDownRelProInformation(String pid);

    void updateIsIterateByPid(@Param("pid") Integer pid, @Param("isIterate") Integer isIterate);

//    List<PsnAndOrgVo> selectBatQue(@Param("type")String type, @Param("pid")String pid);

    List<PsnAndOrgVo> selectBatQueMax(@RequestBody PsnAndOrgVo psnAndOrgVo);

    List<RelPsn> selectByProEffective();


    String getMaxBh();

    String getIdentifyType(@Param("relType") String relType, @Param("relNumber") String relNumber);

    List<RelPsnVo> selectPsnByRelToPid(@Param(value = "pid")String pid);

    List<RelPsnVo> selectPsnByProToPid(@Param(value = "pid")String rootId);

    List<RelPsnVo> selectPsnByRelFromPid(@Param(value = "pid")String rootId);

    List<RelPsnVo> selectPsnByProFromPid(@Param(value = "pid")String rootId);

    List<RelPsnVo> selectRelationDataByToPid(@Param(value = "pid")String pid);

    List<RelPsnVo> selectRelationDataByFromPid(@Param(value = "pid")String pid);

    List<String> isRecordNumber(String yyyyMM);

    List<String> recordNumber();
}
