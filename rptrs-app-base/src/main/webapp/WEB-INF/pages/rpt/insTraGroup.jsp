
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp"%>
    <!-- 加载systemInfo配置文件 -->
    <fmt:setBundle basename="jep" var="sysInfo" />
    <!-- 读取配置值AppName，并赋值给变量appName -->
    <fmt:message key="reportUrl" var="reportUrl" bundle="${sysInfo}" />
    <br:css pack="list"/>
    <style>
        .m-b {
            margin-bottom: -10px;
        }
    </style>
    <title>大家保险集团有限责任公司关联交易明细表</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox  col="12" title=""  collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">
                    <div id="A">
                        <bc:row cls="m-b" >
                            <bf:formGroup col="4">
                                <bc:label text="年度" col="5" formControl="true"/>
                                <bf:select id="year" name="year" col="7">
                                    <bf:option text ="2020年" value ="2020"></bf:option>
                                    <bf:option text ="2021年" value ="2021"></bf:option>
                                    <bf:option text ="2022年" value="2022"></bf:option>
                                    <bf:option text ="2023年" value="2023"></bf:option>
                                    <bf:option text ="2024年" value="2024"></bf:option>
                                    <bf:option text ="2025年" value="2025"></bf:option>
                                    <bf:option text ="2026年" value="2026"></bf:option>
                                </bf:select>
                            </bf:formGroup>
                            <bf:formGroup col="4">
                                <bc:label text="统计主体" col="5" formControl="true"/>
                                <bf:select name="tradeORG" id="tradeORG" col="7" dataSource="custom"
                                           url="sys/unit/selectUnit">
                                    <bf:option text="ALL" value="%"></bf:option>
                                </bf:select>
                            </bf:formGroup>
                            <bf:formGroup col="4">
                                <bc:col col="3">
                                    <table id="ta" style="margin-left: 84px;margin-top: -6px;border-collapse: separate;border-spacing:4px;">
                                        <tr>
                                            <td><bc:button type="danger" text="统计" icon="search" click="btnsearch()">
                                            </bc:button></td>
                                            <td><bc:button id="btnReset" type="primary" text="重置" icon="refresh"
                                                           click="btnReast()">
                                                <be:event>
                                                    <%--alert($("#quarter").val())
                                                    var q =document.getElementById("quarter");
                                                    q.value="";
                                                    var m = document.getElementById("month");
                                                    m.value ="";
                                                    alert($("#quarter").val())--%>
                                                    $("#quarter").val("");
                                                    $("#month").val("");
                                                    <%--$("#quarter").text(""); 下拉选项也重置--%>

                                                </be:event>
                                            </bc:button></td>
                                        </tr>
                                    </table>
                                </bc:col>
                            </bf:formGroup>
                        </bc:row>
                    </div>
                </bc:row>
            </bf:form>
        </bc:ibox>
        <%--<iframe id="content" name="content" frameborder="0" style="width:100%;height: 500px" scrolling="yes" src="${reportUrl}allBusTranInfo.rptdesign"></iframe>&ndash;%&gt;--%>
        <iframe id="content" name="content" frameborder="0" style="width:100%;height: 500px;background-color: #ffffff;" scrolling="yes" src="${reportUrl}report_group.rptdesign"></iframe>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>

<script src="../../static/js/jquery.dialog.js"></script>
<script type="text/javascript">

    //查询
    function btnsearch() {

        //var a = $("#tranType").val()
        var startTime = "";
        var endTime = "" ;
        var year = document.getElementById("year").value;
        var tradeORG = $("#tradeORG").val();
        startTime = year;
        endTime = year;
        //alert("year="+year+";quarter="+quarter+";month="+month+";tradeORG="+tradeORG);
        //alert("startTime="+startTime+";endTime="+endTime+";tradeORG="+tradeORG);
        // window.location ="${base}/report/design/allBusTranInfoView?startTime="+startTime+"&endTime="+endTime+"&manage_org="+manage_org;
        document.getElementById("content").src ="${reportUrl}report_group.rptdesign"+"&startTime="+startTime+"&endTime="+endTime+"&tradeORG="+tradeORG;
    }


    //重置
    function btnReast() {

        window.location = '${base}/report/design/insTraGroup';
    }

</script>
</body>
</html>

