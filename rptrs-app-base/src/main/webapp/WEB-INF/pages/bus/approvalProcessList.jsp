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
            <bc:table id="dlyTable" idField="pid" url="${base}/bus/approvalProcess" queryUrl="queryAppProc"
                      multipleVisible="false">
                <bc:toolbar id="downLoad" text="导出" type="button" handler="download"/>
                <bc:column field="pid" title="主键" width="100" visible="false"/>
                <bc:column field="moduleName" title="审批事项类型" width="100"/>
                <bc:column field="starterDeptName" title="发起部门" width="100"/>
                <bc:column field="starterName" title="发起人" width="100"/>
                <bc:column field="startTimeString" title="发起时间" width="100"
                />
                <bc:column field="handleStatus" title="处理状态" width="100"/>
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
        var starterDeptName = $("[name='__EQS_starterDeptName']").val() == "" ? "%25" : $("[name='__EQS_starterDeptName']").val();
        var starterName=$("[name='__LIKES_starterName']").val() == "" ? "%25" : $("[name='__LIKES_starterName']").val();
        var status = $("[name='__INS_status']").val() == "30,40,50,60" ? "%25" : $("[name='__INS_status']").val();
        var startTime = $("[name='__GES_startTime']").val() == "" ? "%25" : $("[name='__GES_startTime']").val();
        var endTime = $("[name='__LES_startTime']").val() == "" ? "%25" : $("[name='__LES_startTime']").val();
        var data = moduleName + "-" +starterDeptName +"-"+starterName+"-"+ status + "-" + startTime + "-" + endTime;
        var url = '${base}/sys/xlsie/ApprovalProcessExportCallbackImpl/'+data;
        var elemIF = document.createElement("iframe");
        elemIF.src = url;
        elemIF.style.display = "none";
        document.body.appendChild(elemIF);
    }
</script>
</body>
</html>