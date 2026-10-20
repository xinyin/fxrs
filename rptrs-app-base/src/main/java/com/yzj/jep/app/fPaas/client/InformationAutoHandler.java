package com.yzj.jep.app.fPaas.client;

import com.fPaaS1.core.bean.Fault;
import com.fPaaS1.po.gnr.GnrSafInfoPo;
import com.jzr.fpaas.handl.fpaashandler.AbstractClientFPaaS1Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/***
 *
 * 合同签订信息自动获取接口
 */

public class InformationAutoHandler extends AbstractClientFPaaS1Handler {
    static final Logger log = LoggerFactory.getLogger(ContractGatewayClientHandler.class);

    @Override
    protected void setInitParam(Map<String, Object> init) {
        init.put("_TRAN_CODE", "InformationAuto");
    }

    @Override
    protected void doSuccessHandle(Map<String, Object> arg0, Map<String, Object> arg1, Fault arg2) {
        System.out.println("进入dosuccess");
        arg1.put("msgCode",arg0.get("msgCode"));
        arg1.put("msg",arg0.get("msg"));
        arg1.put("data",arg0.get("data"));
    }

    @Override
    protected void doErrorHandle(Map<String, Object> arg0, Fault arg1) {
        arg0.put("coreStat", GnrSafInfoPo.CoreStat.TIME_OUT);
        arg0.put("errCode", arg1.getOutCode());
        arg0.put("errMsg", arg1.getOutMsg());

    }

    @Override
    protected void doFailureHandle(Map<String, Object> arg0, Map<String, Object> arg1, Fault arg2) {
        arg1.put("coreStat", GnrSafInfoPo.CoreStat.FAULT);
        arg1.put("errCode", arg2.getOutCode());
        arg1.put("errMsg", arg2.getOutMsg());
    }

    @Override
    public boolean isFailureThrow() {
        return true;
    }
}
