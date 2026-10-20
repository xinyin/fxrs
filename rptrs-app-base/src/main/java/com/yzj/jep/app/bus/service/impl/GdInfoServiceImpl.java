package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.mapper.GdInfoMapper;
import com.yzj.jep.app.bus.model.GdInfoVo;
import com.yzj.jep.app.bus.service.GdInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GdInfoServiceImpl implements GdInfoService {
    @Autowired
    private GdInfoMapper gdInfoMapper;

    @Override
    public List<GdInfoVo> getAllGd(GdInfoVo gdInfoVo) {
        return gdInfoMapper.getAllGd(gdInfoVo);
    }
}
