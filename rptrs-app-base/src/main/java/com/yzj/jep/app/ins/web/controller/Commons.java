package com.yzj.jep.app.ins.web.controller;

import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public final class Commons {
    /**
     * @param request
     * @param filters
     */
    public static void common(HttpServletRequest request, List<Filter> filters) {
        String traSubName = request.getParameter("traSubName");//交易主体名称
        String unitfTradeAgrmentAbusiness = request.getParameter("unitfTradeAgrmentAbusiness");//统一交易协议业务情况
        String majorTrade = request.getParameter("majorTrade");//是否重大
        if (StringUtils.isNotBlank(traSubName) && StringUtils.isNotBlank(majorTrade) && StringUtils.isNotBlank(unitfTradeAgrmentAbusiness)) {
            filters.add(new Filter("majorTrade", "0"));
            if (CollectionUtils.isNotEmpty(traSubName.split(","))) {
                String[] tra = traSubName.split(",");
                filters.add(new Filter("traSubName", Operator.IN, tra));
            }
            if (StringUtils.isNotBlank(unitfTradeAgrmentAbusiness) && StringUtils.equals("0", unitfTradeAgrmentAbusiness)) {
                filters.add(new Filter("unitfTradeAgrmentAbusiness", unitfTradeAgrmentAbusiness));
            }
            //filters.add(new Filter("status", "6"));
        }
    }

    public static void common2(HttpServletRequest request, Model model) {
        String flag = request.getParameter("flswsw");
        if (StringUtils.isNotBlank(flag) && StringUtils.equals(flag, "1")) {
            model.addAttribute("flag", flag);
        }
    }

    /**
     * 通过当前登陆人信息获取交易主体名称
     *
     * @return
     */
    public static String getTenantName() {
        String tenateName = "";
        UserProfile userProfile = UserProfileUtils.getUserProfile();
        String tenantId = userProfile.getTenantId();
        if ("JT".equals(tenantId)) {
            tenateName = "阳光保险集团股份有限公司";
        }
        if ("CX".equals(tenantId)) {
            tenateName = "阳光财产保险股份有限公司";
        }
        if ("SX".equals(tenantId)) {
            tenateName = "阳光人寿保险股份有限公司";
        }
        if ("XB".equals(tenantId)) {
            tenateName = "阳光信用保证保险股份有限公司";
        }
        if ("ZG".equals(tenantId)) {
            tenateName = "阳光资产管理股份有限公司";
        }

        return tenateName;
    }

    /**
     * 通过主体编号获取交易主体名称
     * @param tenantId
     * @return
     */
    public static String getTenantNameByTenantId(String tenantId) {
        String tenateName = "";
        if ("JT".equals(tenantId)) {
            tenateName = "阳光保险集团股份有限公司";
        }
        if ("CX".equals(tenantId)) {
            tenateName = "阳光财产保险股份有限公司";
        }
        if ("SX".equals(tenantId)) {
            tenateName = "阳光人寿保险股份有限公司";
        }
        if ("XB".equals(tenantId)) {

            tenateName = "阳光信用保证保险股份有限公司";
        }
        if ("ZG".equals(tenantId)) {
            tenateName = "阳光资产管理股份有限公司";
        }

        return tenateName;
    }
    /**
     * 通过主体编号获取交易主体简称
     * @param tenantId
     * @return
     */
    public static String getTenantAbbreviationNameByTenantId(String tenantId) {
        String AbbreviationName = "";
        if ("JT".equals(tenantId)) {
            AbbreviationName = "阳光保险集团";
        }
        if ("CX".equals(tenantId)) {
            AbbreviationName = "阳光财险";
        }
        if ("SX".equals(tenantId)) {
            AbbreviationName = "阳光人寿";
        }
        if ("XB".equals(tenantId)) {
            AbbreviationName = "阳光信保";
        }
        if ("ZG".equals(tenantId)) {
            AbbreviationName = "阳光资产";
        }
        return AbbreviationName;
    }


}
