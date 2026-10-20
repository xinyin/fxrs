package com.yzj.jep.app.pub.service;

import com.yzj.jep.app.pub.model.ArchManageVo;
import com.yzj.jep.orm.entity.Filter;

import java.util.List;

/**
 * ClassName:ArchManageService
 * Package:com.yzj.jep.app.pub.service
 * Description:
 *
 * @date:2021/8/2 16:27
 * @author:wanng
 */
public interface ArchManageService {

    List<ArchManageVo> selectArchList();

    List<ArchManageVo> selectArchList(List<Filter> filters);
}
