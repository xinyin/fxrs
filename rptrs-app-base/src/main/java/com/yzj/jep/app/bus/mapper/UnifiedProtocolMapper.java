package com.yzj.jep.app.bus.mapper;

import com.yzj.jep.app.bus.vo.ProtocolVo;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @title: UnifiedProtocolMapper
 * @Author Whx
 * @Date: 2021/7/16 17:44
 * @Version 1.0
 */
@MyBatisMapper
public interface UnifiedProtocolMapper {

    List<ProtocolVo> queryByConditions(@Param("param") Map<String, Object> param);

    List<String> getAgreementNum();

    ProtocolVo getAgreementTotalDetail(@Param("agreementNumber")String agreementNumber);

    List<ProtocolVo> getIsAgreement(@Param("relNumber")String relNumber, @Param("relName")String relName);
}
