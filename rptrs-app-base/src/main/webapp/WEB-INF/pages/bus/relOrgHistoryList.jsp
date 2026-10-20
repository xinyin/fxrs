<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <style type="text/css">
        #relPsnTable_tb_add, #relPsnTable_tb_edit,
        #relPsnTable_tb_remove, #relPsnTable_tb_importSimple,
        #relPsnTable_tb_sendCommand, #relPsnTable_tb_exportHandler,
        #relPsnTable_tb_send, #relPsnTable_tb_out, #relPsnTable_tb_addOrRemoveBlacklist,
        #relPsnTable_tb_affirm {
            display: none;

        }
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
        #advancedQuery_id{
            height:30px; line-height:30px; width:100px; overflow:hidden;cursor:pointer;
        }
    </style>
</head>
<body class="gray-bg" style="background: #ffffff;">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="" subTitle="" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">
<%--                    <bf:formGroup col="4">--%>
<%--                        <bc:label text="法人名称" col="5" formControl="true"/>--%>
<%--                        <bf:text name="__LIKES_orgName" col="7" placeholder="姓名"/>--%>
<%--                    </bf:formGroup>--%>

<%--                    <bf:formGroup col="4">--%>
<%--                        <bc:label text="证件号码" col="5" formControl="true"/>--%>
<%--                        <bf:text name="__LIKES_uniscCode" col="7" placeholder="证件号码"/>--%>
<%--                    </bf:formGroup>--%>

<%--                    <bf:formGroup col="4">--%>
<%--                        <bc:label text="境内-境外" col="5" formControl="true"/>--%>
<%--                        <bf:select name="__EQS_domesticOrForeign" col="7" dataSource="custom"--%>
<%--                                   url="sys/dictItem/getDictItems/BUS-DomesticOrForeign">--%>
<%--                            <bf:option text="请选择" value=""></bf:option>--%>
<%--                            <be:event type="select">--%>
<%--                                var input = $("#domesticOrForeign");--%>
<%--                                input.focus();--%>
<%--                                input.blur();--%>
<%--                            </be:event>--%>
<%--                        </bf:select>--%>
<%--                    </bf:formGroup>--%>

<%--                    <bf:formGroup col="4">--%>
<%--                        <bc:label text="备案类型" col="5" formControl="true"/>--%>
<%--                        <bf:select name="__EQS_recordType" col="7" dataSource="custom"--%>
<%--                                   url="sys/dictItem/getDictItems/INS-operationType">--%>
<%--                            <bf:option text="请选择" value=""></bf:option>--%>
<%--                        </bf:select>--%>
<%--                    </bf:formGroup>--%>

<%--                    <bf:formGroup col="4">--%>
<%--                        <bc:label text="证件类型" col="5" formControl="true"/>--%>
<%--                        <bf:select name="__LIKES_identifyType" col="7" dataSource="custom"--%>
<%--                                   url="sys/dictItem/getDictItems/BUS-ORGIdentifyType">--%>
<%--                            <bf:option text="请选择" value=""></bf:option>--%>
<%--                            <be:event type="select">--%>
<%--                                var input = $("#__LIKES_identifyType");--%>
<%--                                input.focus();--%>
<%--                                input.blur();--%>
<%--                            </be:event>--%>
<%--                        </bf:select>--%>
<%--                    </bf:formGroup>--%>

<%--                    <bf:formGroup col="4">--%>
<%--                        <bc:label text="经办部门" col="5" formControl="true"/>--%>
<%--                        <bf:text name="__LIKES_transactionDep" col="7" placeholder="部门"/>--%>
<%--                    </bf:formGroup>--%>

<%--            <center>--%>
<%--                <div id="advancedQueryId">--%>
<%--                    <span id="advancedQuery_id" style="color: #02afff">高级查询▼</span>--%>
<%--                </div>--%>
<%--            </center>--%>
<%--            <div id="queryId" class="queryClass">--%>

<%--                    <bf:formGroup col="4">--%>
<%--                        <bc:label text="经办人" col="5" formControl="true"/>--%>
<%--                        <bf:text name="__LIKES_operator" col="7" placeholder="姓名"/>--%>
<%--                    </bf:formGroup>--%>

<%--                    <bf:formGroup col="4">--%>
<%--                        <bc:label text="更新日期" col="5" formControl="true"/>--%>
<%--                        <bf:datebox id="modifiedTime" name="__LIKES_modifiedTime" col="7" format="date"/>--%>
<%--                    </bf:formGroup>--%>
<%--            </div>--%>
                    <bf:formGroup col="4">
                        <bc:label cls="required" text="时点日期" col="5" formControl="true"/>
                        <bf:datebox id="createTime" name="createTime" col="7" format="date"/>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="版本号" col="5" formControl="true"/>
                        <bf:select name="recordNumber" col="7" dataSource="custom"
                                   url="/bus/relOrg/history/getRecordNumber">
                            <be:event type="select">
                                var input = $("#recordNumber");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="类型" col="5" formControl="true"/>
                        <bf:select name="recordType" value="4" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/INS-operationType">
                            <be:event type="select">
                                var input = $("#recordType");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                </bc:row>
                <bc:row cls="m-b">
                    <div class="col-sm-12 col-sm-offset-5">
                        <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                        <bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
                    </div>

                </bc:row>
            </bf:form>
            <bc:table id="psnHistoryTable" idField="pid" url="${base}/bus/relOrg/history" selectFirst="true" queryUrl="queryPage">
                <bc:toolbar id="out" text="导出" type="button" handler="out"/>
                <bc:column field="pid" title="自增长主键" width="100" visible="false"/>
                <bc:column field="orgName" title="机构名称" width="100"/>
                <bc:column field="identifyType" title="证件类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-ORGIdentifyType"/>
                <bc:column field="uniscCode" title="证件号码" width="100"/>
                <bc:column field="domesticOrForeign" title="境内/境外" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-DomesticOrForeign"/>
                <bc:column field="effectRelation" title="是否存在有效关系"
                           formatter="custom:sys/dictItem/getDictItems/BUS-EffectRelation" width="100"/>
                <bc:column field="approvalStatus" title="数据状态" visible="false"
                           formatter="custom:sys/dictItem/getDictItems/BUS-ApplySingleStatus" width="100"/>
                <bc:column field="operator" title="经办人" width="100"/>
                <bc:column field="transactionDep" title="经办部门" width="100"/>
                <bc:column field="recordNumber" title="版本号" width="100"/>
                <bc:column field="recordType" title="备案类型" width="100" formatter="custom:sys/dictItem/getDictItems/INS-operationType"/>
                <bc:column field="createTimeString" title="备案日期" width="100" />
                <bc:operator id="view" icon="search" title="操作/查看"/>
            </bc:table>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">

    //高级查询
    window.onload = function(){
        var flag = true;
        document.getElementById("queryId").style.display = "none";
        document.getElementById("advancedQuery_id").onclick = function() {
            if (flag){
                document.getElementById("queryId").style.display = "block";
                flag = false;
            }else{
                document.getElementById("queryId").style.display = "none";
                flag = true;
            }
        }
    }
    //根据权限显示按钮
    $(function () {
        var code = "${code }";

        //导出
        if (code.search("08,") != -1) {
            $('#psnHistoryTable_tb_out').show();
        }
    })

    $("[name='createTime']").change(function () {
        $("[name='recordNumber']").val('%')
        $("[name='recordNumber']").select2({value: "%",minimumResultsForSearch: -1});
        $("[name='recordType']").val('%')
        $("[name='recordType']").select2({value: "%",minimumResultsForSearch: -1});

    })

    $("[name='recordNumber']").change(function () {
        $("[name='createTime']").val('')
        console.log($("[name='createTime']").val())
    })

    $("[name='recordType']").change(function () {
        $("[name='createTime']").val('')
        console.log($("[name='createTime']").val())
    })

    function getIdSelections() {
        return $.map($('#psnHistoryTable').bootstrapTable('getSelections'), function (row) {
            return row.pid;
        });
    }
    function out() {
        // var orgName = $("[name='__LIKES_orgName']").val() == "" ? "%25" : $("[name='__LIKES_orgName']").val();
        // var identifyType = $("[name='__LIKES_identifyType']").val().trim() == "" ? "%25" : $("[name='__LIKES_identifyType']").val();
        // var uniscCode = $("[name='__LIKES_uniscCode']").val() == "" ? "%25" : $("[name='__LIKES_uniscCode']").val();
        // var recordType = $("[name='__EQS_recordType']").val();
        // var domesticOrForeign = $("[name='__EQS_domesticOrForeign']").val();
        // var operator = $("[name='__LIKES_operator']").val() == "" ? "%25" : $("[name='__LIKES_operator']").val();
        // var transactionDep = $("[name='__LIKES_transactionDep']").val() == "" ? "%25" : $("[name='__LIKES_transactionDep']").val();
        // var modifiedTime = $("[name='__LIKES_modifiedTime']").val() == "" ? "%25" : $("[name='__LIKES_modifiedTime']").val();
        var type = "check";
        // var data = type + "," + orgName + "," + identifyType + "," + uniscCode + "," + recordType + "," + domesticOrForeign +
        //         "," + operator + "," + transactionDep + "," + modifiedTime + ",";
        var createTime = $("[name='createTime']").val();
        var recordNumber = $("[name='recordNumber']").val();
        var recordType = $("[name='recordType']").val();
        // var getselectDataId = getIdSelections();
        // var dataId = getselectDataId.join(",");
        var data = type + ","  + createTime + ","+ recordNumber + ","+ recordType + ",";
        var url = '${base}/sys/xlsie/OrgHistoryExportCallbackImpl/' + data;
        var elemIF = document.createElement("iframe");
        elemIF.src = url;
        elemIF.style.display = "none";
        document.body.appendChild(elemIF);
    }
    //刷新表格
    function loadCallback() {
        $('#psnHistoryTable').jTable('refresh');
    }
    //重置
    function frmReset() {
        window.location = '${base}/bus/relOrg/history/list';
    }
</script>
</body>
</html>
