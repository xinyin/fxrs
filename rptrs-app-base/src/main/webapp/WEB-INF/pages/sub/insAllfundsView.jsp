<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>保险业务类信息报表</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="资金运用类关联交易统计表" subTitle="" col="12" collapsible="true">
            <bf:formGroup hrp="bottom">
                <bf:formGroup col="4">
                    <bc:label text="生成报送文件日期：${date}" col="12" formControl="true"/>
                </bf:formGroup>
                <bf:formGroup col="4">
                    <bc:label text="报送机构：复星保德信人寿保险有限公司" col="12" formControl="true"/>
                </bf:formGroup>
            </bf:formGroup>
            <bc:table id="traBusinessTable" idField="pid" url="${base}/sub/insAllfunds"  multipleVisible="false" queryUrl="viewHandler/${pid}">
                <bc:column field="pid" title="自增长主键" width="100" visible="false"/>
                <bc:column field="currentAssetinvestmentBalance" title="流动性资产投资余额-投向关联方(万元)" width="100"/>
                <bc:column field="fixedProfitBalance" title="流动性资产投资余额-投向关联方(万元)" width="100"/>
<%--                <bc:column field="listedInvestBalance" title="上市权益类资产投资余额-投向关联方" width="100"/>--%>
<%--                <bc:column field="unlistedInvestBalance" title="未上市权益类资产投资余额-投向关联方" width="100"/>--%>
                <bc:column field="investAmount" title="权益类资产投资余额-投向关联方(万元)" width="100"/>
                <bc:column field="imvbsInvestBalance" title="不动产类资产投资余额-投资关联方(万元)" width="100"/>
                <bc:column field="abroadFund" title="境外投资余额-投资关联方(万元)" width="100"/>
                <bc:column field="insManageProFund" title="保险资产管理产品-投资关联方(万元)" width="100"/>
                <bc:column field="comBankFinFund" title="商业银行理财产品-投资关联方(万元)" width="100"/>
                <bc:column field="bankCreditFund" title="银行业金融机构信贷资产支持证券-投资关联方(万元)" width="100"/>
                <bc:column field="trustFund" title="信托公司集合资金信托计划-投资关联方(万元)" width="100"/>
                <bc:column field="bondSpecialFund" title="证券公司专项资产管理计划-投资关联方(万元)" width="100"/>
                <bc:column field="insCompanyPlanFund" title="保险资管公司发行资产支持计划-投资关联方(万元)" width="100"/>
                <bc:column field="otherTotalAmount" title="其他-投资关联方(万元)" width="100"/>
                <bc:column field="allTotalFund" title="全部投资余额(万元)" width="100"/>
            </bc:table>
            <bc:button  type="primary" text="返回" icon="arrow-circle-left">
                <be:event>
                    window.location = '${base}/sub/insAllfunds/list';
                </be:event>
            </bc:button>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
</body>
</html>
