package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.mapper.DownSuspectMapper;
import com.yzj.jep.app.bus.mapper.RelPsnSuspectMapper;
import com.yzj.jep.app.bus.model.suspect.DownSuspect;
import com.yzj.jep.app.bus.service.DownSuspectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DownSuspectServiceImpl implements DownSuspectService {
    @Autowired
    private DownSuspectMapper downSuspectMapper;

    @Override
    public List<DownSuspect> getDownPsnInfo(Integer pid) {
        return downSuspectMapper.getDownPsnInfo(pid);
    }
}
