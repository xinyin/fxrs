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
        #relPsnTable_tb_send, #relPsnTable_tb_out, #relPsnTable_tb_addOrRemoveBlacklist {
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
    </style>
</head>
<body class="gray-bg" style="background: #ffffff;">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="选择本层关联方" subTitle="" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <div class="col-sm-12 col-sm-offset-5">
                    <bc:row cls="m-b">
                        <bf:formGroup col="5">
                            <bc:label text="关联方名称" col="5" formControl="true"/>
                            <bf:text name="__LIKES_fromMemo" id="__LIKES_fromMemo" col="7" placeholder="关联方名称"/>
                        </bf:formGroup>
                        <bf:formGroup col="5">
                            <bc:label text="证件号码" col="5" formControl="true"/>
                            <bf:text name="__LIKES_identifyNumber" col="7" placeholder="证件号码"/>
                        </bf:formGroup>
                        <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                    </bc:row>
                </div>
            </bf:form>
            <div class="col-sm-4 col-sm-offset-5">

            </div>
            <bc:button type="danger" text="确定" click="submit1()">
            </bc:button>
            <bc:table id="relPsnOrOrgOrProTable" idField="pid" url="${base}/bus/relation" queryUrl="forNameSelectAll" multiple="false" selectFirst="true">
                <bc:column field="pid" title="ID" width="100"/>
                <bc:column field="fromMemo" title="关联方名称" width="100"/>
                <bc:column field="fromType" title="关联方类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-OrgOrPsn"/>
                <bc:column field="identifyNumber" title="关联方证件号码" width="100"/>
            </bc:table>

        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
    $(document).ready(function () {
        var type = "${type}";
        selectIdentifyType(type);
    });

    //页面默认关联关系是自然人
    function selectIdentifyType(val) {
        $('#relPsnOrOrgOrProTable').bootstrapTable('hideColumn', 'pid');
    }

    $("#__LIKES_fromMemo").blur(function () {
        var val = $("#__LIKES_fromMemo").val();
        if (val == '' || val == undefined) {
            $("#__LIKES_fromMemo").val('')
        }
    })

    /*获取pid*/
    function getIdSelections() {
        return $.map($('#relPsnOrOrgOrProTable').bootstrapTable('getSelections'), function (row) {
            return row.pid;
        });
    }

    /*获取姓名*/
    function getFromMemo() {
        return $.map($('#relPsnOrOrgOrProTable').bootstrapTable('getSelections'), function (row) {
            return row.fromMemo;
        });
    }

    /*获取自然人类型*/
    function getFromType() {
        return $.map($('#relPsnOrOrgOrProTable').bootstrapTable('getSelections'), function (row) {
            return row.fromType;
        });
    }


    /*获取证件号码*/
    function getIdentifyNumber() {
        return $.map($('#relPsnOrOrgOrProTable').bootstrapTable('getSelections'), function (row) {
            return row.identifyNumber;
        });
    }



    //关闭弹窗返回选择对象
    function submit1() {
        var pid = getIdSelections();
        var fromMemo = getFromMemo();
        var fromType = getFromType();
        var identifyNumber = getIdentifyNumber();
        // alert(pid + '->' + name + '->' + psnType + '->' + number + '->' + domesticOrForeign)
        if (pid != null && pid != " " || pid != undefined) {
            $.messager.args('forNameSelect').result({
                'pid': pid,
                'fromMemo': fromMemo,
                'fromType': fromType,
                'identifyNumber': identifyNumber
            });
            $.messager.hide('forNameSelect');
            //关闭窗口
        } else {
            $.messager.alert('提示', '请选择一条数据', 'warning');
            return false;
        }
    }

</script>
</body>
</html>