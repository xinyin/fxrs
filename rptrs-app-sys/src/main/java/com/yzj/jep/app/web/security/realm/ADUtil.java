package com.yzj.jep.app.web.security.realm;

import com.yzj.jep.sys.code.LoginMsg;
import jcifs.UniAddress;
import jcifs.smb.*;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class ADUtil {

    private static String domainName = "cramc.cn";
    private static String ADName = "ch-dc-01.chinare.local";
    /**
     * 域用户认证
     *
     * @param user
     * @param pw
     * @param level
     * @return
     */
    public Map<String, Object> AdAuth(String user, String pw, int level) {
        Map<String, Object> map = new HashMap<>();
        if (level >= 3) {
            return null;
        }
        String msg = "AD系统出现内部错误";
        UniAddress mydomaincontroller = null;
        try {
            mydomaincontroller = UniAddress.getByName(ADName);
            NtlmPasswordAuthentication mycreds = new NtlmPasswordAuthentication(domainName, user, pw);
            SmbSession.logon(mydomaincontroller, mycreds);
            map.put("success", true);

        } catch (SmbAuthException sae) {
            msg = "AD账号或密码不正确";
//            msg += "Error Detail : " + sae.toString();
            map.put("success", false);
            map.put("msg" , msg);
        } catch (UnknownHostException uhe) {
            msg = "登陆AD认证地址错误";
//            msg += "Error Detail : " + uhe.toString();
            System.out.println("AD Auth : " + uhe);
            map.put("success", false);
            map.put("msg" , msg);
        } catch (SmbException se) {
            msg = "AD系统出现内部错误";
//            msg += "Error Detail : " + se.toString();
            System.out.println("AD Auth : " + se);
            SmbTransport.CONNECTIONS.clear();
            String ret = AdAuth(user, pw, level + 1).toString();
            if (ret != null) {
                msg = ret;
            }
            map.put("success", false);
            map.put("msg" , msg);

        }
        return map;
    }


}
