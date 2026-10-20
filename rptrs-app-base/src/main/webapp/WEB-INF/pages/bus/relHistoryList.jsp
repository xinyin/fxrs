<%@ taglib prefix="bc" uri="http://www.yzj.com/ui/bootstrap/component" %>
<%@ taglib prefix="bf" uri="http://www.yzj.com/ui/bootstrap/form" %>
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

    <title>关联关系维护</title>
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
                    <bf:formGroup col="4">
                        <bc:label cls="required" text="时点日期" col="5" formControl="true"/>
                        <bf:datebox id="createTime" name="createTime" col="7" format="date"/>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="版本号" col="5" formControl="true"/>
                        <bf:select name="recordNumber" col="7" dataSource="custom"
                                   url="/bus/rel/history/getRecordNumber">
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
        <bc:table id="relHistoryTable" idField="pid" url="${base}/bus/rel/history" multiple="true" queryUrl="queryList" selectFirst="true">
                <bc:toolbar id="out" text="导出" type="button" handler="out"/>
                <bc:column field="pid" title="自增长主键" width="100" visible="false"/>
                <bc:column field="fromMemo" title="本层关联方名称" width="100"/>
                <bc:column field="fromIdentifyNumber" title="本层关联方代码" width="100"/>
                <bc:column field="fromType" title="本层关联方类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-OrgOrPsn"/>
                <bc:column field="relPsnOrg" title="与前一层的关联关系" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-RelZZD"/>
                <bc:column field="toMemo" title="前一层关联方名称" width="100"/>
                <bc:column field="toIdentifyNumber" title="前一层关联方代码" width="100"/>
                <bc:column field="toType" title="前一层关联方类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-OrgOrPsn"/>
                <bc:column field="isValid" title="该关系是否仍然有效" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-valid"/>
                <bc:column field="equityShare" title="与前一层是否有权益份额" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-RelequityShare"/>
                <bc:column field="shareholdingRatio" title="持有比例（%）" width="100"/>
                <bc:column field="pledgeRatio" title="股权质押比例(%)" width="100"/>
                <bc:column field="activeTimeString" title="生效日期" width="100"/>
                <bc:column field="invalidTimeString" title="失效日期" width="100"/>
                <bc:column field="relStatement" title="关联关系说明" width="100"/>
                <bc:column field="transactionDep" title="经办部门" width="100"/>
                <bc:column field="operator" title="经办人" width="100"/>
                <bc:column field="recordNumber" title="版本号" width="100"/>
                <bc:column field="recordType" title="备案类型" width="100" formatter="custom:sys/dictItem/getDictItems/INS-operationType"/>
                <bc:column field="createTimeString" title="备案日期" width="100" />
<%--                <bc:column field="createTimeString" title="更新日期" width="100"/>--%>
<%--                <bc:column field="approvalStatus" title="数据状态" width="100"--%>
<%--                           formatter="custom:sys/dictItem/getDictItems/BUS-RelApplystatus"/>--%>
                <bc:operator id="view" icon="search" title="操作/查看"/>
            </bc:table>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">



    //根据权限显示按钮
    $(function () {
        var code = "${code }";
        //导出
        if (code.search("08,") != -1) {
            $('#relHistoryTable_tb_out').show();
        }
    })
    function getIdSelections() {
        return $.map($('#relHistoryTable').bootstrapTable('getSelections'), function (row) {
            return row.pid;
        });
    }
    //导出按钮
    function out() {
        var getselectDataId = getIdSelections();
        var dataId = getselectDataId.join(",");
        //关联方名称

        var createTime = $("[name='createTime']").val();
        var recordNumber = $("[name='recordNumber']").val();
        var recordType = $("[name='recordType']").val();
        var type = "check";
        var data = '';

        var data = type + ","  + createTime + ","+ recordNumber + ","+ recordType + ",";

        //下载文档
        var url = '${base}/sys/xlsie/RelHistoryExportCallbackImpl/' + data;
        var elemIF = document.createElement("iframe");
        elemIF.src = url;
        elemIF.style.display = "none";
        document.body.appendChild(elemIF);
    }

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


    //刷新表格
    function loadCallback() {
        $('#relPsnTable').jTable('refresh');
    }

    //重置
    function frmReset() {
        window.location = '${base}/bus/rel/history/list';
    }
</script>
</body>
</html>
