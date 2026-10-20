<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>审批进度-我发起的流程</title>
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
                <bf:hidden name="__INS_status" value="40,60,50,30"/>
                <bf:hidden name="__EQS_starterCode" value="${userCode}"/>
                <bc:row cls="m-b">
                    <bf:formGroup col="4">
                        <bc:label text="审批事项类型" col="5" formControl="true"/>
                        <bf:select name="__LIKES_moduleName" col="7" dataSource="custom"
                        >
                            <bf:option text="全部" value="%"></bf:option>
                            <bf:option text="关联关系" value="关联关系"></bf:option>
                            <bf:option text="关联交易" value="关联交易"></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_releaseType");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="流程状态" col="5" formControl="true"/>
                        <bf:select name="__INS_status" col="7" dataSource="custom"
                        >
                            <bf:option text="全部" value="40,60,50,30,20"></bf:option>
                            <bf:option text="审批中" value="40,60"></bf:option>
                            <bf:option text="已完成" value="50,30"></bf:option>
                            <bf:option text="已退回" value="20"></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_releaseType");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                </bc:row>
                <bc:row cls="m-b">
                    <bf:formGroup col="4">
                        <bc:label text="发起时间" col="5" formControl="true"/>
                        <bf:datebox id="startTime" name="__GES_startTime" col="7" format="date">
                            <bv:date format="YYYY" message="只能输入合理的年份，类型为YYYY类型"/>
                        </bf:datebox>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="结束时间" col="5" formControl="true"/>
                        <bf:datebox id="endTime" name="__LES_startTime" col="7" format="date">
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
            <bc:table id="procInstTable" idField="id" url="${base}/workflow/process" queryUrl="queryHistory"
                      multipleVisible="false">
                <bc:toolbar id="downLoad" text="导出" type="button" handler="download"/>
                <bc:column field="id" title="主键" width="100" visible="false"/>
                <bc:column field="moduleName" title="审批事项类型" width="100"/>
                <bc:column field="starterDeptName" title="发起部门" width="100"/>
                <bc:column field="startTimeString" title="发起时间" width="100"
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
        $("#queryForm")[0].reset();
    }
    function download() {
        // var data = "00";
        // var data=getIdSelections();
        // alert(data);
        var moduleName = $("[name='__LIKES_moduleName']").val() == "%" ? "%25" : $("[name='__LIKES_moduleName']").val();
        var status = $("[name='__INS_status']").val() == "40,60,50,30" ? "%25" : $("[name='__INS_status']").val();
        var startTime = $("[name='__GES_startTime']").val() == "" ? "%25" : $("[name='__GES_startTime']").val();
        var endTime = $("[name='__LES_startTime']").val() == "" ? "%25" : $("[name='__LES_startTime']").val();
        var data = moduleName + "," + status + "," + startTime + "," + endTime;
        var url = '${base}/sys/xlsie/StartProcessExportCallbackImpl/'+data;
        var elemIF = document.createElement("iframe");
        elemIF.src = url;
        elemIF.style.display = "none";
        document.body.appendChild(elemIF);
    }
</script>
</body>
</html>