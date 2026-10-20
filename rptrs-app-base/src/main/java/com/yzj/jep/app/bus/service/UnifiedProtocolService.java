package com.yzj.jep.app.bus.service;

import com.github.pagehelper.PageInfo;
import com.yzj.jep.app.bus.vo.ProtocolVo;

import java.util.List;

/**
 * @title: UnifiedProtocolService
 * @Author Whx
 * @Date: 2021/7/16 17:43
 * @Version 1.0
 */
public interface UnifiedProtocolService {
    PageInfo<ProtocolVo> queryByConditions(Integer pageNumber, Integer pageSize, String year, String quarter, String contractNo);

    /**
     * 查询统一交易协议执行情况
     * @param relNumber
     * @param relName
     * @return
     */
    List<ProtocolVo> getIsAgreementImpl(String relNumber, String relName);
}
