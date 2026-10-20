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
        <bc:ibox title="关联交易明细表" subTitle="" col="12" collapsible="true">
            <bf:formGroup col="12">
                <bc:label text="生成报送文件日期：${date}" col="6" formControl="true"/>
                <bc:label text="报送机构：复星保德信人寿保险有限公司" col="6" formControl="true"/>
            </bf:formGroup>
            <bc:table id="traBusinessTable" idField="pid" url="${base}/sub/insAllbustraninfo"  multipleVisible="false" queryUrl="viewHandler/${pid}">
                <bc:column field="pid" title="自增长主键" width="100" visible="false"/>
                <bc:column field="contractNo" title="合同编号" width="100"/>
                <bc:column field="tranDate" title="合同签署日期" width="100"/>
                <bc:column field="isAgreement" title="是否统一交易协议" width="100" formatter="fixedDict:IsStatusDict"/>
                <bc:column field="isAgreementImpl" title="统一交易协议执行情况" width="150"/>
                <bc:column field="tradeSubject" title="交易标的" width="100"/>
                <bc:column field="tranType" title="交易类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/INS-tranType"/>
                <bc:column field="busType" title="业务类型" width="100"/>
                <bc:column field="tradeAmount" title="交易金额（万元）" width="100"/>
                <bc:column field="tradingDirection" title="交易方向" width="100"/>
                <bc:column field="tradePrice" title="交易价格" width="100"/>
                <bc:column field="pricingPolicy" title="定价政策" width="100"/>
                <bc:column field="relName" title="关联方名称" width="100"/>
                <bc:column field="relNumber" title="关联方证件号码" width="100"/>
                <bc:column field="relType" title="关联方类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/INS-relType"/>
                <%--        <bc:column field="underInvolveRel" title="底层基础资产是否涉及控股股东或控股股东的关联方" width="100"--%>
                <%--                   formatter="fixedDict:IsStatusDict"/>--%>
                <bc:column field="financialAssstsName" title="金融产品名称" width="100"/>
                <bc:column field="financialProScale" title="产品批准/注册/备案号" width="100"/>
                <bc:column field="financialProRatio" title="投资金融产品份额占比（%）" width="100"/>
                <%--        <bc:column field="isHoldingSubsidiary" title="是否控股子公司交易" width="100"--%>
                <%--                   formatter="fixedDict:IsStatusDict"/>--%>
                <%--        <bc:column field="holdingSubsidiaryName" title="控股子公司全称" width="100"/>--%>
                <%--        <bc:column field="holdingSubsidiaryNumber" title="控股子公司有效证件号码" width="100"/>--%>

                <bc:column field="remark" title="其他事项说明" width="100"/>
            </bc:table>
            <bc:button  type="primary" text="返回" icon="arrow-circle-left">
                <be:event>
                    window.location = '${base}/sub/insAllbustraninfo/list';
                </be:event>
            </bc:button>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
</body>
</html>
