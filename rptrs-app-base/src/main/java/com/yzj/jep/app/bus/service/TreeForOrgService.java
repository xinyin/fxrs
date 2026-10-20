package com.yzj.jep.app.bus.service;

import com.yzj.jep.app.bus.mapper.RelMapper;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.vo.AffiliateVo;
import com.yzj.jep.app.bus.vo.SaveTreeMsgVo;
import com.yzj.jep.orm.service.CRUDService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public interface TreeForOrgService extends CRUDService<Rel, Integer> {

    //查询源主体为机构的机构信息
    List<Map<String, Object>> queryOrgForOrgFrom(Integer pid);

    //查询源主体为机构的自然人信息
    List<Map<String, Object>> queryPsnForOrgFrom(Integer pid);

    //查询源主体为自然人的机构信息
    List<Map<String, Object>> queryOrgForPsnFrom(Integer pid);

    //查询源主体为自然人的自然人信息
    List<Map<String, Object>> queryPsnForPsnFrom(Integer pid);


    //查询源主体为疑似机构的机构信息
    List<Map<String, Object>> querySusOrgForOrgFrom(Integer pid);

    //查询源主体为疑似机构的自然人信息
    List<Map<String, Object>> querySusPsnForOrgFrom(Integer pid);

    //查询源主体为疑似自然人的机构信息
    List<Map<String, Object>> querySusOrgForPsnFrom(Integer pid);

    //查询源主体为疑似自然人的自然人信息
    List<Map<String, Object>> querySusPsnForPsnFrom(Integer pid);


    List<AffiliateVo> selectAffiliateMessageForOrg(RelOrg relOrg);

    List<AffiliateVo> selectAffiliateMessageForPsn(RelPsn relPsn);


    //查询疑似机构
    List<AffiliateVo> selectAffiliateMessageForSusOrg(RelOrgSuspect relOrgSuspect);

    //查询疑似关联方
    List<AffiliateVo> selectAffiliateMessageForSusPsn(RelPsnSuspect relPsnSuspect);


    Map<String, Object> saveRelMsg(SaveTreeMsgVo saveTreeMsgVo);

    Map<String, Object> saveRelSusMsg(SaveTreeMsgVo saveTreeMsgVo);

}
