<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
    <title>内部交易信息统计明细表查询</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox col="12" title="" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">
                    <div id="A">
                        <bc:row cls="m-b">
                            <bf:formGroup col="4">
                                <bc:label text="内部人名称" col="5" formControl="true"/>
                                <bf:text id="relatedName" name="relatedName" col="7" value="${traCredit.relatedName}">
                                </bf:text>
                            </bf:formGroup>
                            <bf:formGroup col="4">
                                <bc:label text="证件类型" col="5" formControl="true"/>
                                <bf:select id="identifyType" name="identifyType" dataSource="custom"
                                           url="sys/dictItem/getDictItems/BUS-IdentifyType" col="7"
                                           value="${traCredit.identifyType}">
                                    <bf:option text="ALL" value=""></bf:option>
                                    <be:event type="select">
                                        var input = $("#__LIKES_identifyType");
                                        input.focus();
                                        input.blur();
                                    </be:event>
                                </bf:select>
                            </bf:formGroup>
                            <bf:formGroup col="4">
                                <bc:label text="证件号码" col="5" formControl="true"/>
                                <bf:text id="identNumber" name="identNumber" col="7" value="${traCredit.identNumber}">
                                    <bv:notEmpty/>
                                </bf:text>
                            </bf:formGroup>
                        </bc:row>
                    </div>
                    <div id="B">
                        <bc:row cls="m-b">
                            <bf:formGroup col="4">
                                <bc:label text="业务类型" col="5" formControl="true"/>
                                <bf:select id="businessType" name="businessType" dataSource="custom"
                                           url="sys/dictItem/getDictItems/BUS-CreditCategory" col="7"
                                           value="${traCredit.businessType}">
                                    <bf:option text="ALL" value=""></bf:option>
                                    <be:event type="select">funName(e.target.value);</be:event>
                                    <bv:notEmpty/>
                                </bf:select>
                            </bf:formGroup>
                            <%--<bf:formGroup col="3">--%>
                                <%--<bc:label text="机构名称" col="5" formControl="true"/>--%>
                                <%--<bf:select name="manage_org" id="manage_org" col="7" dataSource="custom"--%>
                                           <%--url="sys/dictItem/getDictItems/BUS-GHOrg">--%>
                                    <%--<bf:option text="ALL" value="%"></bf:option>--%>
                                <%--</bf:select>--%>
                            <%--</bf:formGroup>--%>
                            <bf:formGroup col="4">
                                <bc:label text="交易时间" col="3" formControl="true" ></bc:label><bf:datebox name="startTime" id="startTime" col="4" format="date">
                                </bf:datebox>
                                    <bc:label text="~" col="1" formControl="true"></bc:label>
                                    <bf:datebox id="endTime" name="endTime" col="4" format="date">
                                    </bf:datebox>

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


                    <%--<bc:col col="3" colOffset="1">
                        <bc:button  type="danger" text="查询" icon="search" click = "btnsearch()">
                        </bc:button>
                        <bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="btnReast()" >
                        </bc:button>
                    </bc:col>--%>
                </bc:row>
                <%--<bc:row cls="m-b">--%>
                    <%--<div class="col-sm-12 col-sm-offset-5">--%>
                        <%--<bc:button id="btnQuery" type="danger" text="查询" icon="search" click="btnsearch();"/>--%>
                        <%--<bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="btnReast();"/>--%>
                    <%--</div>--%>
                <%--</bc:row>--%>
            </bf:form>
        </bc:ibox>
        <%--<iframe id="content" name="content" frameborder="0" style="width:100%;height: 500px" scrolling="yes" src="${reportUrl}report_tranInfoDetails.rptdesign"></iframe>--%>
        <iframe id="content" name="content" frameborder="0" style="width:100%;height: 500px" scrolling="yes" src="${reportUrl}report_tranInfoDetails.rptdesign"></iframe>--%>
    </div>
</div>

<br:js pack="list"/>
<be:processEvent/>
<script src="../../static/js/jquery.dialog.js"></script>
<script type="text/javascript">
    //查询
    function btnsearch() {
        // var relatedName = $("#relatedName").val();
        var relatedName = document.getElementById("relatedName").value;
        var identifyType = document.getElementById("identifyType").value;
        var identNumber = document.getElementById("identNumber").value;
        var startTime = "";
        var endTime = "" ;
        var time1 = $('input[name="startTime"]').val();
        var time2 = $('input[name="endTime"]').val();
        var businessType = $("#businessType").val();
        var manage_org = $("#manage_org").val();
        var param = document.getElementById("param1");
        if (relatedName == null || relatedName == "" ){
            $.messager.alert('错误', "请输入内部人名称", 'error',function()
            {
                window.location = "${base}/report/design/tranInfoDetails";
            });
            return;
        }
        if (identifyType == null || identifyType == "" ){
            $.messager.alert('错误', "请输入证件类型", 'error',function()
            {
                window.location = "${base}/report/design/tranInfoDetails";
            });
            return;
        }
        if (identNumber == null || identNumber == "" ){
            $.messager.alert('错误', "请输入证件号码", 'error',function()
            {
                window.location = "${base}/report/design/tranInfoDetails";
            });
            return;
        }
        if(time1 !="" && time2 !=""){

            startTime = time1;
            endTime = time2;
        }else{
            if(time1 =="" || time2 ==""){
                $.messager.alert('错误', "请输入交易时间", 'error',function()
                {
                    window.location = "${base}/report/design/tranInfoDetails";
                });
                return;
            }
        }
        if (businessType == null || businessType == "" ){
            $.messager.alert('错误', "请输入业务类型", 'error',function()
            {
                window.location = "${base}/report/design/tranInfoDetails";
            });
            return;
        }
        <%--if (manage_org == null || manage_org == "" ){--%>
            <%--$.messager.alert('错误', "请输入机构名称", 'error',function()--%>
            <%--{--%>
                <%--window.location = "${base}/report/design/tranInfoDetails";--%>
            <%--});--%>
            <%--return;--%>
        <%--}--%>

        //window.location ="${base}/report/design/tranInfoDetailsView?relatedName="+relatedName+"&identifyType="+identifyType+"&identNumber="+identNumber+"&startTime="+startTime+"&endTime="+endTime+"&businessType="+businessType+"&manage_org="+manage_org;
        <%--window.location ="${base}/report/design/allBusTranInfoView;--%>
        <%--window.location ="${base}/report/design/tranInfoDetailsgxr;--%>
        document.getElementById("content").src ="${reportUrl}report_tranInfoDetails.rptdesign"+"&startTime="+startTime+"&endTime="+endTime+"&manage_org="+manage_org;


        <%--document.getElementById("content").src ="${reportUrl}report_tranInfoDetails.rptdesign"+ "&relatedName="+relatedName +"&identifyType="+identifyType+"&identNumber="+identNumber+ "&startTime="+startTime +"&endTime="+endTime--%>
        <%--+"&businessType="+businessType+"&manage_org="+manage_org;--%>
    }


    //重置
    function btnReast() {

        window.location = '${base}/report/design/tranInfoDetails';
    }
</script>
</body>
</html>
