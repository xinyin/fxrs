<%@ taglib prefix="bc" uri="http://www.yzj.com/ui/bootstrap/component" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
  <%--  <title>待生成报送文件数据</title>--%>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="待生成季度关联交易数据" subTitle="" col="12" collapsible="false" >
            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">
                    <bf:formGroup col="4">
                        <bc:label text="年度" col="5" formControl="true"/>
                        <bf:datebox id="years" name="year" col="7" format="date">
                            <bv:date format="YYYY" message="只能输入合理的年份，类型为YYYY类型"/>
                        </bf:datebox>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="季度" col="5" formControl="true"/>
                        <bf:select id="quarter" name="quarter" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-Quarter" >
                            <bf:option text="请选择" value=""></bf:option>
                            <be:event type="select">
                                var input = $("#quarter");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <div class="col-sm-12 col-sm-offset-5">
                        <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                        <bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
                    </div>
                </bc:row>
            </bf:form>
            <bc:table id="publogTable" idField="pid" url="${base}/pub/publog" queryUrl="queryQuarter"  multipleVisible="false">
                <bc:toolbar id="export" text="导出" type="button" handler="out"/>
                <bc:column field="tranType" title="交易类型" width="100"/>
                <bc:column field="number" title="件数" width="100"/>
                <bc:column field="amount" title="金额(元)" width="100"/>
                <bc:column field="proprotion" title="占比" width="100"/>
            </bc:table>



        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
    $(document).ready(function () {
        // $("input[name='__LIKES_yearss']").val(new Date().getUTCFullyears());
  //      $("#years").datetimepicker("setDate", new Date());

    });
    $("#years").datetimepicker({
        format: "yyyy",
        autoclose: true,
        startView: 4,
        minView: 4,
        todayHighlight: true,
    });
    $("#years").change(function () {
        if (
            $("#years").datetimepicker("getDate") == "" ||
            $("#years").datetimepicker("getDate") == null ||
            $("#years").datetimepicker("getDate") == undefined
        ) {
            $("#years").datetimepicker("setDate", new Date());
        }
    });

    //导出
    function out() {
        var  yyy = $("[name='quarter']").val();
        var year = $("[name='year']").val() == "" ? "null" : $("[name='year']").val();
        var quarter = $("[name='quarter']").val() == " " ? "null" : $("[name='quarter']").val();

        var type = "check";
        var data = type + "," + year + "," + quarter  ;
        var url = '${base}/sys/xlsie/reportDataExcelImpl/' + data;
        var elemIF = document.createElement("iframe");
        elemIF.src = url;
        elemIF.style.display = "none";
        document.body.appendChild(elemIF);
    }


</script>
</body>
</html>