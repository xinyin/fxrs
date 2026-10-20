/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年07月20日 11:07:48
 *
 */

package com.yzj.jep.app.pub.service;

import com.github.pagehelper.PageInfo;
import com.yzj.jep.app.pub.model.ArchManageVo;
import com.yzj.jep.app.pub.model.PubQuarterVo;
import com.yzj.jep.app.pub.model.PubYearVo;
import com.yzj.jep.app.pub.model.Publog;
import com.yzj.jep.orm.service.CRUDService;

import java.math.BigDecimal;
import java.util.List;

public interface PublogService extends CRUDService<Publog, Integer> {

    List<PubYearVo> queryDate(String pubYear);

    BigDecimal selectTotal(String tranType, String pubYear);

    List<PubQuarterVo> selectQuarter(String pubYear, String pubQuarter);

    BigDecimal selectLastAmt(String relName, String tranType, String date);

    Integer majorTradeCount(String relName, String tranType, String pubYear);

    BigDecimal selectQuarterTotal(String tranType, String pubYear, String pubQuarter);

    List<PubQuarterVo> queryQuarterDate(String pubQuarterge, String pubQuarterle);

    void del(Integer pid);

    Publog selectById(int pid);

    List<Publog> selectList(Publog publog);

    ArchManageVo queryList(String quarter, String archDate);

    PageInfo<Publog> queryByConditions(Integer pageNumber, Integer pageSize, String archDate, String quarter, String month);
}
