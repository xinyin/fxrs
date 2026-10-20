<%@ taglib prefix="bv" uri="http://www.yzj.com/ui/bootstrap/form/validator" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="form"/>
    <title>FundsInvestment</title>
</head>
<body class="gray-bg">
<div class="row">
    <bc:ibox id="fundsInvestmentBox" title="编辑信息" subTitle="" col="12" collapsible="true">
        <bf:form id="editForm" method="post" validation="true">
            <bf:hidden id="opt" name="opt" value="${opt}"/>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="6">
                    <bc:label cls="required" text="投资余额统计时间" col="4" formControl="true"/>
                    <bf:datebox id="investmentBalanceTime" name="investmentBalanceTime" col="8"
                                value="${fundsInvestment.investmentBalanceTimeString}" disabled="true">
                        <bv:date format="YYYY-MM-DD" message="只能输入合理的日期，类型为YYYY-MM-DD类型"/>
                        <bv:notEmpty/>
                    </bf:datebox>
                    <%--                    <bf:text id="investmentBalanceTime" name="investmentBalanceTime" col="8"--%>
                    <%--                             value="${fundsInvestment.investmentBalanceTimeString}">--%>
                    <%--                    </bf:text>--%>
                </bc:col>
                <bc:col col="6">
                    <bc:label cls="required" text="流动性资产投资余额(万元)" col="4" formControl="true"/>
                    <bf:text id="mobilityBalance" name="mobilityBalance" col="8"
                             value="${fundsInvestment.mobilityBalance}" disabled="true">

                        <bv:regexp regexp="(^[1-9]([0-9]+)?(\.[0-9]{1,6})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)"
                                   message="只能输入金钱格式，小数点最大为后六位"/>
                        <bv:stringLength max="22" message="最大长度为22位"/>
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="6">
                    <bc:label cls="required" text="不动产类资产投资余额(万元)" col="4" formControl="true"/>
                    <bf:text id="realEstateBalance" name="realEstateBalance" col="8"
                             value="${fundsInvestment.realEstateBalance}" disabled="true">

                        <bv:regexp regexp="(^[1-9]([0-9]+)?(\.[0-9]{1,6})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)"
                                   message="只能输入金钱格式，小数点最大为后六位"/>
                        <bv:stringLength max="22" message="最大长度为22位"/>
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="不动产类资产投资限额(万元)" col="4" formControl="true"/>
                    <bf:text id="realEstateLimit" name="realEstateLimit" col="8"
                             value="${fundsInvestment.realEstateLimit}" disabled="true">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="6">
                    <bc:label cls="required" text="境外投资余额(万元)" col="4" formControl="true"/>
                    <bf:text id="foreignBalance" name="foreignBalance" col="8"
                             value="${fundsInvestment.foreignBalance}" disabled="true">

                        <bv:regexp regexp="(^[1-9]([0-9]+)?(\.[0-9]{1,6})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)"
                                   message="只能输入金钱格式，小数点最大为后六位"/>
                        <bv:stringLength max="22" message="最大长度为22位"/>
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label text="境外投资限额(万元)" col="4" formControl="true"/>
                    <bf:text id="foreignLimit" name="foreignLimit" col="8"
                             value="${fundsInvestment.foreignLimit}" disabled="true">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="6">
                    <bc:label cls="required" text="固定收益类资产投资余额(万元)" col="4" formControl="true"/>
                    <bf:text id="fixedIncomeBalance" name="fixedIncomeBalance" col="8"
                             value="${fundsInvestment.fixedIncomeBalance}" disabled="true">

                        <bv:regexp regexp="(^[1-9]([0-9]+)?(\.[0-9]{1,6})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)"
                                   message="只能输入金钱格式，小数点最大为后六位"/>
                        <bv:stringLength max="22" message="最大长度为22位"/>
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label cls="required" text="权益类资产投资余额(万元)" col="4" formControl="true"/>
                    <bf:text id="balanceInvestment" name="balanceInvestment" col="8"
                             value="${fundsInvestment.balanceInvestment}" disabled="true">
                    </bf:text>
                </bc:col>
            </bf:formGroup>

            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="6">
                    <bc:label cls="required" text="保险资产管理产品投资余额(万元)" col="4" formControl="true"/>
                    <bf:text id="insuranceManagementBalance" name="insuranceManagementBalance" col="8"
                             value="${fundsInvestment.insuranceManagementBalance}" disabled="true">

                        <bv:regexp regexp="(^[1-9]([0-9]+)?(\.[0-9]{1,6})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)"
                                   message="只能输入金钱格式，小数点最大为后六位"/>
                        <bv:stringLength max="22" message="最大长度为22位"/>
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label cls="required" text="商业银行理财产品投资余额(万元)" col="4" formControl="true"/>
                    <bf:text id="bankFinancialBalance" name="bankFinancialBalance" col="8"
                             value="${fundsInvestment.bankFinancialBalance}" disabled="true">

                        <bv:regexp regexp="(^[1-9]([0-9]+)?(\.[0-9]{1,6})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)"
                                   message="只能输入金钱格式，小数点最大为后六位"/>
                        <bv:stringLength max="22" message="最大长度为22位"/>
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="6">
                    <bc:label cls="required" text="银行业金融机构信资产支持证券投资余额(万元)" col="4" formControl="true"/>
                    <bf:text id="bankNegotiableSecuritiesBalance" name="bankNegotiableSecuritiesBalance" col="8"
                             value="${fundsInvestment.bankNegotiableSecuritiesBalance}" disabled="true">

                        <bv:regexp regexp="(^[1-9]([0-9]+)?(\.[0-9]{1,6})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)"
                                   message="只能输入金钱格式，小数点最大为后六位"/>
                        <bv:stringLength max="22" message="最大长度为22位"/>
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label cls="required" text="信托公司集合资金信托计划投资余额(万元)" col="4" formControl="true"/>
                    <bf:text id="trustSchemeBalance" name="trustSchemeBalance" col="8" value="${fundsInvestment.trustSchemeBalance}" disabled="true">
                        <bv:regexp regexp="(^[1-9]([0-9]+)?(\.[0-9]{1,6})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)"
                                   message="只能输入金钱格式，小数点最大为后六位"/>
                        <bv:stringLength max="22" message="最大长度为22位"/>
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="6">
                    <bc:label cls="required" text="证券公司专项资产管理计划投资余额(万元)" col="4" formControl="true"/>
                    <bf:text id="negotiableSecuritiesSpecialPlanBalance" name="negotiableSecuritiesSpecialPlanBalance"
                             col="8" value="${fundsInvestment.negotiableSecuritiesSpecialPlanBalance}" disabled="true">

                        <bv:regexp regexp="(^[1-9]([0-9]+)?(\.[0-9]{1,6})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)"
                                   message="只能输入金钱格式，小数点最大为后六位"/>
                        <bv:stringLength max="22" message="最大长度为22位"/>
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label cls="required" text="保险资管公司发行资产支持计划投资余额(万元)" col="4" formControl="true"/>
                    <bf:text id="insuranceFundsSupportPlanBalance" name="insuranceFundsSupportPlanBalance" col="8"
                             value="${fundsInvestment.insuranceFundsSupportPlanBalance}" disabled="true">

                        <bv:regexp regexp="(^[1-9]([0-9]+)?(\.[0-9]{1,6})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)"
                                   message="只能输入金钱格式，小数点最大为后六位"/>
                        <bv:stringLength max="22" message="最大长度为22位"/>
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="6">
                    <bc:label cls="required" text="其他投资余额(万元)" col="4" formControl="true"/>
                    <bf:text id="otherInvestmentBalance" name="otherInvestmentBalance" col="8"
                             value="${fundsInvestment.otherInvestmentBalance}" disabled="true">

                        <bv:regexp regexp="(^[1-9]([0-9]+)?(\.[0-9]{1,6})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)"
                                   message="只能输入金钱格式，小数点最大为后六位"/>
                        <bv:stringLength max="22" message="最大长度为22位"/>
                    </bf:text>
                </bc:col>
                <bc:col col="6">
                    <bc:label cls="required" text="全部投资余额(万元)" col="4" formControl="true"/>
                    <bf:text id="allInvestmentBalance" name="allInvestmentBalance" col="8"
                             value="${fundsInvestment.otherInvestmentBalance}" disabled="true">

                        <bv:regexp regexp="(^[1-9]([0-9]+)?(\.[0-9]{1,6})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)"
                                   message="只能输入金钱格式，小数点最大为后六位"/>
                        <bv:stringLength max="22" message="最大长度为22位"/>
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup>
                <div class="col-sm-8 col-sm-offset-2">
                    <bc:col col="6">
                    </bc:col>
                    <bc:button type="primary" text="返回" icon="arrow-circle-left">
                        <be:event>
                            window.location = '${base}/bus/fundsInvestment/list';
                        </be:event>
                    </bc:button>
                </div>
            </bf:formGroup>
        </bf:form>
    </bc:ibox>
</div>
</div>
<br:js pack="form"/>
<be:processEvent/>
</body>
</html>
