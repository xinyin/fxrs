<%@ taglib prefix="bf" uri="http://www.yzj.com/ui/bootstrap/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>与单一法人资金运用类关联交易统计表</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">
                    <bf:formGroup col="4">
                        <bc:label text="年度" col="5" formControl="true"/>
                        <bf:datebox id="investmentBalanceTimes" name="investmentBalanceTime" col="7" format="date">
                         <%--   <bv:date format="YYYY" message="只能输入合理的年份，类型为YYYY类型"/>--%>
                        </bf:datebox>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="季度" col="5" formControl="true"/>
                        <bf:select id="quarter" name="quarter" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-Quarter">
                            <bf:option text="本年度" value=""></bf:option>
                            <be:event type="select">
                                var input = $("#quarter");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="关联方名称" col="5" formControl="true"/>
                        <bf:text id="relName" name="__LIKES_relName" col="7" placeholder="关联方名称"/>
                    </bf:formGroup>
                    <bc:col col="1"></bc:col>

                    <bc:col col="3" colOffset="1">
                        <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                        <bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
                    </bc:col>
                </bc:row>
            </bf:form>
            <bc:table id="relFundsStaTable" idField="pid" url="${base}/bus/relFundsSta"
                      queryUrl="query1">
<%--                <bc:toolbar id="add" text="新增" type="button"/>--%>
                <bc:toolbar id="out" text="导出" type="button" handler="out"/>
                <bc:column field="investmentBalanceTimeString" title="投资余额时间" width="100"/>
                <bc:column field="relName" title="法人主体名称" width="100"/>
                <bc:column field="fundsInvestmentBalance" title="投资余额" width="100"/>
                <bc:column field="allInvestProForTotal" title="占总资产比例" width="100"/>
                <bc:column field="relTotalAssets" title="法人主体总资产" width="100"/>
                <bc:column field="allInvestProForOrgTotal" title="占法人主体总资产比例" width="100"/>
<%--                <bc:operator id="view" icon="eye" title="操作"/>--%>
            </bc:table>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
    $(document).ready(function () {
        // $("input[name='__LIKES_years']").val(new Date().getUTCFullYear());
        $("#investmentBalanceTimes").datetimepicker("setDate", new Date());

    });
    $("#investmentBalanceTimes").datetimepicker({
        format: "yyyy",
        autoclose: true,
        startView: 4,
        minView: 4,
        todayHighlight: true,
    });
    $("#investmentBalanceTimes").change(function () {
        if (
            $("#investmentBalanceTimes").datetimepicker("getDate") == "" ||
            $("#investmentBalanceTimes").datetimepicker("getDate") == null ||
            $("#investmentBalanceTimes").datetimepicker("getDate") == undefined
        ) {
            $("#investmentBalanceTimes").datetimepicker("setDate", new Date());
        }
    });
    /*获取pid*/
    function getIdSelections() {
        return $.map($('#fundsInvestmentStaTable').bootstrapTable('getSelections'), function (row) {
            return row.pid;
        });
    }
    //获取状态
    function getStausSelections() {
        return $.map($('#fundsInvestmentStaTable').bootstrapTable('getSelections'), function (row) {
            return row.status;
        });
    }

    function out() {
        var investmentBalanceTime = $("[name='investmentBalanceTime']").val();
        var quarter = $("[name='quarter']").val();
        var relName = $("[name='__LIKES_relName']").val() == "" ? "%25" : $("[name='__LIKES_relName']").val();
        console.log(investmentBalanceTime)
        console.log(quarter)
        var data = investmentBalanceTime + "-" + quarter + "-" + relName;
        var url = '${base}/sys/xlsie/RelFundsStaExportCallBackImpl/'+data;
        var elemIF = document.createElement("iframe");
        elemIF.src = url;
        elemIF.style.display = "none";
        document.body.appendChild(elemIF);
    }

    //重置
    function frmReset() {
        window.location = '${base}/bus/relFundsSta/list';
    }

</script>

</body>
</html>