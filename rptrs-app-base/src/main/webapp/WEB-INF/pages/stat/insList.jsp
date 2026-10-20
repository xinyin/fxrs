<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>审批台账列表展示</title>
</head>
<style>
    .bs-checkbox {
        display: none;
    }
</style>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="" subTitle="" col="12" collapsible="true">
            <%--查询条件--%>
            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">
                    <%--年度--%>
                    <bf:formGroup col="3">
                        <bc:label text="年度" col="5" formControl="true"/>
                        <bf:text name="year" col="7" placeholder="年度"/>
                    </bf:formGroup>
                    <%--季度，调用字典接口--%>
                    <bf:formGroup col="3">
                        <bc:label text="季度" col="5" formControl="true"/>
                        <bf:select name="quarter" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-Quarter">
                            <bf:option text="ALL" value=""></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_fromType");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <%--审批时间--%>
                    <bf:formGroup col="3">
                        <bc:label text="审批时间" col="5" formControl="true"/>
                        <bf:datebox id="startTime" name="startTime" col="7"/>
                    </bf:formGroup>
                    <bf:formGroup col="3">
                        <bc:label text="~" col="1" formControl="true"/>
                        <bf:datebox id="endTime" name="endTime" col="7"/>
                    </bf:formGroup>
                </bc:row>
                <bc:row cls="m-b">
                    <div class="col-sm-12 col-sm-offset-5">
                        <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                        <bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
                    </div>

                </bc:row>
            </bf:form>
            <bc:table id="affiliateTable" idField="pid" url="${base}/stat/ins" queryUrl="statIns" selectFirst="true">
                <bc:toolbar id="out" text="导出" type="button" handler="out"/>
                <bc:column field="appNo" title="审批编号" width="100"/>
                <bc:column field="tranDate" title="交易时间" width="100"/>
                <bc:column field="tranOpponent" title="交易对手名称" width="100"/>
                <bc:column field="relName" title="关联方名称" width="100"/>
                <bc:column field="tranName" title="交易名称" width="100"/>
                <bc:column field="tradeAmount" title="交易金额" width="100"/>
                <bc:column field="tranType" title="交易类型" width="100"/>
                <bc:column field="starterUnitName" title="经办单位" width="100"/>
                <bc:column field="assigneeOrg" title="承办部门" width="100"/>
                <bc:column field="assignee" title="经办人" width="100"/>
                <bc:column field="approveTime" title="审批时间" width="100"/>
                <bc:column field="memo" title="审批意见" width="100"/>
                <bc:column field="endTime" title="审批通过时间" width="100"/>
            </bc:table>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">

    function out() {
        var year = $("[name='year']").val();
        var quarter = $("[name='quarter']").val();
        var startTime = $("[name='startTime']").val();
        var endTime = $("[name='endTime']").val();
        console.log(year)
        console.log(quarter)
        console.log(startTime)
        console.log(endTime)
        var data = year + " ," + quarter+" ,"+startTime+" ,"+endTime+" ,";
        var url = '${base}/sys/xlsie/InsStatisticsExportCallBackImpl/'+data;
        var elemIF = document.createElement("iframe");
        elemIF.src = url;
        elemIF.style.display = "none";
        document.body.appendChild(elemIF);
    }

    //重置
    function frmReset() {
        window.location.reload();
    }
</script>
</body>
</html>