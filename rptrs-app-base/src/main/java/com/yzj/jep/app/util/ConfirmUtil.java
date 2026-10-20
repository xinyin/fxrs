package com.yzj.jep.app.util;

import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.model.RelPro;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.sys.util.UserProfileUtils;

/**
 * 判断委托机构设置关联状态
 */
public class ConfirmUtil {


    /**
     * 判断管户机构是否为委托机构，若为委托机构，则设为待确认
     *
     * @param relPsn
     */
    public void relPsn(RelPsn relPsn){
        String code = UserProfileUtils.getUserProfile().getUnitCode();
        if (code.contains("ZZ_Delegation")){
            relPsn.setStatus("7"); //待确认
        }else {
            relPsn.setStatus("3");
        }
    }

    /**
     * 判断管户机构是否为委托机构，若为委托机构，则设为待确认
     *
     * @param relOrg
     */
    public void relOrg(RelOrg relOrg){
//ZZ_Delegation
        String code = UserProfileUtils.getUserProfile().getUnitCode();
        if (code.contains("ZZ_Delegation")){
            relOrg.setApprovalStatus("7");
        }else {
            relOrg.setApprovalStatus("3");
        }
    }
    /**
     * 判断管户机构是否为委托机构，若为委托机构，则设为待确认
     *
     * @param relPro
     */
    public void relPro(RelPro relPro){
//ZZ_Delegation
        String code = UserProfileUtils.getUserProfile().getUnitCode();
        if (code.contains("ZZ_Delegation")){
            relPro.setApprovalStatus("6");
        }else {
            relPro.setApprovalStatus("3");
        }
    }

}
