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
                                value="${relFunds.investmentBalanceTimeString}" disabled="true">
                    </bf:datebox>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="6">
                    <bc:label cls="required" text="关联方名称" col="4" formControl="true"/>
                    <bf:text id="relName" name="relName" col="8"
                             value="${relFunds.relName}" disabled="true">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="6">
                    <bc:label text="关联方类型" col="4" formControl="true"/>
                    <bf:select id="relType" name="relType" col="7" dataSource="custom"
                               url="sys/dictItem/getDictItems/INS-relType" disabled="true" value="${relFunds.relType}">
                        <bf:option text="-" value=""></bf:option>
                    </bf:select>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="6">
                    <bc:label cls="required" text="关联方证件号" col="4" formControl="true"/>
                    <bf:text id="relIdentifyNumber" name="relIdentifyNumber" col="8"
                             value="${relFunds.relIdentifyNumber}" disabled="true">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
            <bf:formGroup hr="dashed" hrp="bottom">
                <bc:col col="6">
                    <bc:label text="资金运用投资余额" col="4" formControl="true"/>
                    <bf:text id="fundsInvestmentBalance" name="fundsInvestmentBalance" col="8"
                             value="${relFunds.fundsInvestmentBalance}"
                             disabled="true">
                    </bf:text>
                </bc:col>
            </bf:formGroup>
<%--            <bf:formGroup hr="dashed" hrp="bottom">--%>
<%--                <bc:col col="6">--%>
<%--                    <bc:label cls="required" text="关联方总资产" col="4" formControl="true"/>--%>
<%--                    <bf:text id="relTotalAssets" name="relTotalAssets" col="8"--%>
<%--                             value="${relFunds.relTotalAssets}" disabled="true">--%>

<%--                    </bf:text>--%>
<%--                </bc:col>--%>
<%--            </bf:formGroup>--%>
            <bf:formGroup>
                <div class="col-sm-8 col-sm-offset-2">
                    <bc:col col="6">
                    </bc:col>
                    <bc:button type="primary" text="返回" icon="arrow-circle-left">
                        <be:event>
                            window.location = '${base}/bus/relFunds/list';
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
