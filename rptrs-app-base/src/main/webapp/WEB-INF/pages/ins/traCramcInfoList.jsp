<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <style type="text/css">

    </style>

    <title>关联关系自然人</title>
    <style>
        .form-group select {
            width: 100% !important;
        }

        .form-group .input-group.date,
        .form-group .select2 {
            width: 100% !important;
        }

        .boxse .form-group .col-xs-4 {
            margin: 0.3% 0 !important;
        }

        .boxse .has-success .form-control, .boxse .has-success .form-control:focus {
            border-color: #CCCCCC !important;
        }
    </style>
</head>
<body class="gray-bg" style="background: #ffffff;">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="" subTitle="" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">
                    <bf:formGroup hrp="bottom">
                        <bf:formGroup col="4">
                            <bc:label text="交易主体名称" col="5" formControl="true"/>
                            <bf:text name="__LIKES_vcEntity" col="7" placeholder="交易主体名称"/>
                        </bf:formGroup>
                        <bf:formGroup col="4">
                            <bc:label text="交易对手名称" col="5" formControl="true"/>
                            <bf:text name="__LIKES_vcCounterparty" col="7" placeholder="交易对手名称"/>
                        </bf:formGroup>
                        <bf:formGroup col="4">
                            <bc:label text="交易日期" col="5" formControl="true"/>
                            <bf:datebox  name="__LIKES_dtradeDate" col="7">
                            </bf:datebox>
                        </bf:formGroup>
                    </bf:formGroup>
                    <bc:row cls="m-b">
                        <div class="col-sm-12 col-sm-offset-5">
                            <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                            <bc:button id="btnReset" type="primary" text="重置" icon="refresh"/>
                        </div>
                    </bc:row>
                </bc:row>
            </bf:form>
            <bc:table id="traCramcInfoTable" idField="pid" url="${base}/ins/traCramcInfo">
                <bc:column field="pid" title="pid" width="100" visible="false"/>
                <bc:column field="frefcon" title="交易编号" width="100"/>
                <bc:column field="vcEntity" title="交易主主体名称" width="100"/>
                <bc:column field="vcCounterparty" title="交易对手名称" width="100"/>
                <bc:column field="vcName" title="证券名称" width="100"/>
                <bc:column field="vcTypeLevel1" title="资产分类" width="100"/>
                <bc:column field="famount" title="成交金额" width="100"/>
                <bc:column field="dtradeDate" title="交易日期" width="100"/>
            </bc:table>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">


</script>
</body>
</html>