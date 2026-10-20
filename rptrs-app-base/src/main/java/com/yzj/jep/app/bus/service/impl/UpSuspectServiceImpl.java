package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.mapper.DownSuspectMapper;
import com.yzj.jep.app.bus.mapper.UpSuspectMapper;
import com.yzj.jep.app.bus.model.suspect.UpSuspect;
import com.yzj.jep.app.bus.service.UpSuspectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UpSuspectServiceImpl implements UpSuspectService {

    @Autowired
    private UpSuspectMapper upSuspectMapper;

    @Override
    public List<UpSuspect> getUpPsnInfo(Integer pid) {
        return upSuspectMapper.getUpPsnInfo(pid);
    }

    @Override
    public List<UpSuspect> getUpOrgInfo(Integer pid) {
        return upSuspectMapper.getUpOrgInfo(pid);
    }

    @Override
    public List<UpSuspect> getUpInfoOnOrg(Integer pid) {
        return upSuspectMapper.getUpInfoOnOrg(pid);
    }

    @Override
    public List<UpSuspect> getUpInfoOnPsn(Integer pid) {
        return upSuspectMapper.getUpInfoOnPsn(pid);
    }

}
