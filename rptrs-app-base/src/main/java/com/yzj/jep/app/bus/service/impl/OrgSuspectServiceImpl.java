package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.mapper.OrgSuspectMapper;
import com.yzj.jep.app.bus.model.OrgSuspect;
import com.yzj.jep.app.bus.service.OrgSuspectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrgSuspectServiceImpl implements OrgSuspectService {
    @Autowired
    private OrgSuspectMapper orgSuspectMapper;

    @Override
    public List<OrgSuspect> queryOrgSubInfo(Integer pid) {
        return orgSuspectMapper.queryOrgSubInfo(pid);
    }

    @Override
    public List<OrgSuspect> getOrgShareOrg(Integer pid) {
        return orgSuspectMapper.getOrgShareOrg(pid);
    }

    @Override
    public List<OrgSuspect> getOrgInvest(Integer pid) {
        return orgSuspectMapper.getOrgInvest(pid);
    }
}
