<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>信息发布</title>
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
<%--                <bf:hidden name="__INS_status" value="40,60,50,30"/>--%>
                <bc:row cls="m-b">
                    <bf:formGroup col="4">
                        <bc:label text="审批事项类型" col="5" formControl="true"/>
                        <bf:select name="procType" col="7" dataSource="custom">
                            <bf:option text="全部" value="0"></bf:option>
                            <bf:option text="关联关系" value="1"></bf:option>
                            <bf:option text="关联交易" value="2"></bf:option>
                            <bf:option text="金融产品" value="3"></bf:option>
                            <bf:option text="任务委托" value="4"></bf:option>
                            <bf:option text="权限申请" value="5"></bf:option>
                            <bf:option text="机构基础数据" value="6"></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_releaseType");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="发起部门" col="5" formControl="true"/>
                        <bf:text name="starterDeptName" col="7"/>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="发起人" col="5" formControl="true"/>
                        <bf:text name="starterName" col="7"/>
                    </bf:formGroup>
                </bc:row>
                <bc:row cls="m-b">
                    <bf:formGroup col="4">
                        <bc:label text="流程状态" col="5" formControl="true"/>
                        <bf:select name="status" col="7" dataSource="custom">
                            <bf:option text="全部" value=""></bf:option>
                            <bf:option text="草稿" value="10"></bf:option>
                            <bf:option text="已退回" value="20"></bf:option>
                            <bf:option text="审批中" value="30"></bf:option>
                            <bf:option text="已完成" value="40"></bf:option>
                            <bf:option text="挂起" value="50"></bf:option>
                            <bf:option text="终止" value="60"></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_releaseType");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="发起时间" col="5" formControl="true"/>
                        <bf:datebox id="startTime" name="startTime" col="7" format="date">
                            <bv:date format="YYYY" message="只能输入合理的年份，类型为YYYY类型"/>
                        </bf:datebox>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="结束时间" col="5" formControl="true"/>
                        <bf:datebox id="endTime" name="startTime" col="7" format="date">
                            <bv:date format="YYYY" message="只能输入合理的年份，类型为YYYY类型"/>
                        </bf:datebox>
                    </bf:formGroup>
                </bc:row>
                <bc:row cls="m-b">
                    <div class="col-sm-12 col-sm-offset-5">
                        <bc:button id="btnQuery" type="primary" text="查询" icon="search"/>
                        <bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
                    </div>
                </bc:row>
            </bf:form>
            <bc:table id="procInstTable" idField="id" url="${base}/stat/ins" queryUrl="sumIns"
                      multipleVisible="false">
                <bc:toolbar id="downLoad" text="导出" type="button" handler="download"/>
                <bc:column field="id" title="主键" width="100" visible="false"/>
                <bc:column field="procName" title="审批事项类型" width="100"/>
                <bc:column field="summarize" title="审批概述" width="100"/>
                <bc:column field="starterDeptName" title="发起部门" width="100"/>
                <bc:column field="starterName" title="发起人" width="100"/>
                <bc:column field="startTime" title="发起时间" width="100"
                />
                <bc:column field="status" title="流程状态" width="100"
                           formatter="fixedDict:ProcInstStatus"/>
            </bc:table>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
    function frmReset() {
        window.location.reload()
    }
    function download() {
        // var data = "00";
        // var data=getIdSelections();
        // alert(data);
        var procType = $("[name='procType']").val();
        var starterDeptName = $("[name='starterDeptName']").val();
        var starterName=$("[name='starterName']").val();
        var status = $("[name='status']").val();
        var startTime = $("[name='startTime']").val();
        var endTime = $("[name='startTime']").val();
        // var data = procType + "," +starterDeptName+","+starterName+","+ status + "," + startTime + "," + endTime;
        var data={
            "procType":procType,
            "starterDeptName":starterDeptName,
            "starterName":starterName,
            "status":status,
            "startTime":startTime,
            "endTime":endTime
        }
        console.log(data)
        var url = '${base}/sys/xlsie/ProcessSummaryExportCallbackImpl/'+JSON.stringify(data);
        var elemIF = document.createElement("iframe");
        elemIF.src = url;
        elemIF.style.display = "none";
        document.body.appendChild(elemIF);
    }
</script>
</body>
</html>