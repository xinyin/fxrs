package com.yzj.jep.app.pub.service.impl;

import com.yzj.jep.app.pub.mapper.ArchManageMapper;
import com.yzj.jep.app.pub.model.ArchManageVo;
import com.yzj.jep.app.pub.service.ArchManageService;
import com.yzj.jep.orm.entity.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wll
 */
@Service
public class ArchManageServiceImpl implements ArchManageService {

    @Autowired
    private ArchManageMapper archManageMapper;

    @Override
    public List<ArchManageVo> selectArchList() {
        List<ArchManageVo> list = archManageMapper.selectArchList();
        return list;
    }

    @Override
    public List<ArchManageVo> selectArchList(List<Filter> filters) {
//        List<ArchManageVo> list = archManageMapper.selectArchList(Filter filters);
        return null;
    }
}
