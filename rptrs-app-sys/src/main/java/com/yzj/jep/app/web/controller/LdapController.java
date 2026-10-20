package com.yzj.jep.app.web.controller;

import com.novell.ldap.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

public class LdapController {

    Logger logger = LoggerFactory.getLogger(LdapController.class);


    private String my_host_LDAP;
    private int my_port_LDAP;
    private String searchBase_LDAP;

    public String login(String userId,String userPassword) {
        String MY_HOST = my_host_LDAP;//访问AD域的IP地址
        int MY_PORT = my_port_LDAP;//端口号，默认为389
        //String loginDN = loginDN_LDAP;
        String loginDN = userId+"@pramericafosunlife.com";
        //String password = password_LDAP;
        String password = userPassword;
        String searchBase = searchBase_LDAP;//域名入口
        int searchScope = LDAPConnection.SCOPE_SUB;//搜索范围
        String stringValue = null;
        LDAPConnection ld = new LDAPConnection();
        try {

            ld.connect(MY_HOST, MY_PORT);
            ld.bind(LDAPConnection.LDAP_V3, loginDN, password.getBytes("UTF8"));

            //过滤器 查询范围
            //String filter = "objectClass=User";
            String filter = "sAMAccountName=" + userId;

            LDAPSearchResults searchResults = ld.search(searchBase,
                    searchScope,
                    filter,
                    null,
                    false);

            while (searchResults.hasMore()) {
                LDAPEntry nextEntry = searchResults.next();
                System.out.println("We found " + nextEntry.getDN());
                LDAPAttribute sAMAccountName = nextEntry.getAttribute("sAMAccountName");
                 stringValue = sAMAccountName.getStringValue();

                LDAPAttributeSet attributeSet = nextEntry.getAttributeSet();

                Iterator ite = attributeSet.iterator();
                while (ite.hasNext()) {
                    System.out.println(ite.next());
                }
            }
        } catch (LDAPException | UnsupportedEncodingException e) {
            logger.info("连接异常: " , e);
            e.printStackTrace();
        } finally {
            ld.clone();
        }
        return stringValue;
    }

    public String getMy_host_LDAP() {
        return my_host_LDAP;
    }

    public void setMy_host_LDAP(String my_host_LDAP) {
        this.my_host_LDAP = my_host_LDAP;
    }

    public int getMy_port_LDAP() {
        return my_port_LDAP;
    }

    public void setMy_port_LDAP(int my_port_LDAP) {
        this.my_port_LDAP = my_port_LDAP;
    }


    public String getSearchBase_LDAP() {
        return searchBase_LDAP;
    }

    public void setSearchBase_LDAP(String searchBase_LDAP) {
        this.searchBase_LDAP = searchBase_LDAP;
    }
}
