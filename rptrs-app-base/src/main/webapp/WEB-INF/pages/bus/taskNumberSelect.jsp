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

    <title>任务委托编码</title>
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
                <div class="col-sm-12 col-sm-offset-5">
                    <bc:row cls="m-b">
                        <div id="org_">
                            <bf:formGroup col="8">
                                <bc:label text="委托人" col="4" formControl="true"/>
                                <bf:text name="__LIKES_staffName" id="__LIKES_staffName" value="" placeholder="请输入名称"
                                         col="7"/>
                            </bf:formGroup>
                        </div>
                        <bf:formGroup col="4">
                            <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                        </bf:formGroup>
                    </bc:row>
                </div>
            </bf:form>
            <div class="col-sm-4 col-sm-offset-5">

            </div>
            <bc:button type="danger" text="选择" click="submit1()">
            </bc:button>
            <bc:table id="taskNumberTable" idField="pid" url="${base}/pat/taskNumber">
                <bc:column field="id" title="任务编号" width="100"/>
                <bc:column field="staffName" title="委托人" width="100"/>
                <bc:column field="staffCode" title="委托人编码" width="100"/>
                <bc:column field="createTime" title="委托日期" width="100"/>
            </bc:table>

        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">


    $(document).ready(function () {
        var type = "${type}";
    });



    $("#__LIKES_name").blur(function () {
        var val = $("#__LIKES_name").val();
        if (val == '' || val == undefined) {
            $("#__LIKES_name").val('')
        }
    })


    /*获取pid*/
    function getIdSelections() {
        return $.map($('#taskNumberTable').bootstrapTable('getSelections'), function (row) {
            return row.id;
        });
    }

    /*获取姓名*/
    function getName() {
        return $.map($('#taskNumberTable').bootstrapTable('getSelections'), function (row) {
            return row.staffName;
        });
    }

    function getCreateTime() {
        return $.map($('#taskNumberTable').bootstrapTable('getSelections'), function (row) {
            return row.createTime;
        });
    }

    function getCode() {
        return $.map($('#taskNumberTable').bootstrapTable('getSelections'), function (row) {
            return row.staffCode;
        });
    }
    function getTransferStaffName() {
        return $.map($('#taskNumberTable').bootstrapTable('getSelections'), function (row) {
            return row.transferStaffName;
        });
    }

    function geteStartTime() {
        return $.map($('#taskNumberTable').bootstrapTable('getSelections'), function (row) {
            return row.startTime;
        });
    }

    function geteEndTime() {
        return $.map($('#taskNumberTable').bootstrapTable('getSelections'), function (row) {
            return row.endTime;
        });
    }

    function geteStaffDep() {
        return $.map($('#taskNumberTable').bootstrapTable('getSelections'), function (row) {
            return row.staffDep;
        });
    }


    function getTransferStaffDep() {
        return $.map($('#taskNumberTable').bootstrapTable('getSelections'), function (row) {
            return row.transferStaffDep;
        });
    }
    //关闭弹窗返回选择对象
    function submit1() {
        var pid = getIdSelections();
        var name = getName();
        var code = getCode();
        var createTime = getCreateTime();
        var startTime = geteStartTime();
        var endTime = geteEndTime();
        var staffDep = geteStaffDep();
        var transferStaffDep = getTransferStaffDep();

        var transferStaffName = getTransferStaffName();

        if (pid != null && pid != " " || pid != undefined) {
            $.messager.args('taskNumberSelect').result({
                'pid': pid,
                'name': name,
                'code': code,
                'createTime': createTime,
                'startTime': startTime,
                'endTime': endTime,
                'staffDep': staffDep,
                'transferStaffDep': transferStaffDep,
                'transferStaffName': transferStaffName
            });
            $.messager.hide('taskNumberSelect');
            //关闭窗口
        } else {
            $.messager.alert('提示', '请选择一条数据', 'warning');
            return false;
        }
    }

</script>
</body>
</html>