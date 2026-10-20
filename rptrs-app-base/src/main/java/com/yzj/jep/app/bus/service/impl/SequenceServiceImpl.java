package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.mapper.SequenceMapper;
import com.yzj.jep.app.bus.model.Sequence;
import com.yzj.jep.app.bus.service.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SequenceServiceImpl implements SequenceService {

    @Autowired
    private SequenceMapper sequenceMapper;

    @Override
    public List<Sequence> getOrgShareOrg(Integer pid) {
        return sequenceMapper.getOrgShareOrg(pid);
    }

    @Override
    public List<Sequence> getOrgSharePsn(Integer pid) {
        return sequenceMapper.getOrgSharePsn(pid);
    }

    @Override
    public List<Sequence> getOrgInvest(Integer pid) {
        return sequenceMapper.getOrgInvest(pid);
    }

    @Override
    public List<Sequence> getOrgSubsidiary(Integer pid) {
        return sequenceMapper.getOrgSubsidiary(pid);
    }

    @Override
    public List<Sequence> getPsnInvest(Integer pid) {
        return sequenceMapper.getPsnInvest(pid);
    }

    @Override
    public List<Sequence> getPsnRelation(Integer pid) {
        return sequenceMapper.getPsnRelation(pid);
    }

    @Override
    public List<Sequence> getPsnUnit(Integer pid) {
        return sequenceMapper.getPsnUnit(pid);
    }
}
