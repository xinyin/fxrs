<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <style type="text/css">

        #traFundsTable_tb_add, #traFundsTable_tb_edit,
        #traFundsTable_tb_remove, #traFundsTable_tb_import,
        #traFundsTable_tb_task, #traFundsTable_tb_download,
        #traFundsTable_tb_submit, #traFundsTable_tb_export,
        #traFundsTable_tb_ActualTra, #traFundsTable_tb_confirm,
        #traFundsTable_tb_PaymentCollection, #traFundsTable_tb_confirm1,
        #traFundsTable_tb_approve,
        #traFundsTable_tb_downTmp {
            display: none;

        }
    </style>
    <title>关联交易表（保险）-资金运用类</title>
    <style>
        .form-group {
            margin: 0.3% 0 !important;
        }

        .form-group .col-xs-12 {
            margin: 0.3% 0 !important;
        }

        .form-group .col-xs-12 label {
            display: inline-block;
            float: left;
            margin: 0 0 0 3%;
            padding: 0 2% 0 0;
        }

        .form-group .col-xs-12 label {
            display: inline-block;
            float: left;
            margin: 0 0 0 3%;
            padding: 0 2% 0 0;
            width: 10%;
        }

        .has-success .form-control, .boxse .has-success .form-control:focus {
            border-color: #CCCCCC !important;
        }

        .gray-bg {
            background: white !important;
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

        table {
            table-layout: fixed;
        }

        td {
            overflow: hidden;
            white-space: nowrap;
            text-overflow: ellipsis;
        }

    </style>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="" subTitle="" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">
                    <bf:formGroup hrp="bottom">
                        <bf:formGroup col="4">
                            <bc:label text="交易流水号" col="5" formControl="true"/>
                            <bf:text name="transNum" col="7" placeholder="交易流水号"/>
                        </bf:formGroup>
                    </bf:formGroup>

                    <bc:row cls="m-b">
                        <div class="col-sm-12 col-sm-offset-5">
                            <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                            <bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
                        </div>
                    </bc:row>
                </bc:row>
            </bf:form>
            <bc:table id="traFundsTable" idField="pid" url="${base}/ins/tran" multiple="false" selectFirst="true"
                      queryUrl="queryFundsPage">
                <bc:toolbar id="updateAmend" text="更新经办人" type="button" outline="true" handler="updateAmend"/>
                <bc:toolbar id="view" text="查看" type="button" handler="viewEventHandler"/>

                <bc:column field="pid" title="主键" width="100" visible="false"/>
                <bc:column field="relId" title="关联方ID" width="100" visible="false"/>
                <bc:column field="transNum" title="交易流水号" width="150"/>
                <bc:column field="tranName" title="交易概述" width="100"/>
                <bc:column field="contractNo" title="合同编号" width="100"/>
                <bc:column field="relName" title="关联方名称" width="120"/>
                <bc:column field="relType" title="关联方类型" width="120"
                           formatter="custom:sys/dictItem/getDictItems/INS-relType"/>
                <bc:column field="relNumber" title="证件号码" width="100"/>
                <bc:column field="busType" title="业务类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/INS-FundsType"/>
                <%--                <bc:column field="investmentType" title="投资类别" width="100" formatter="custom:sys/dictItem/getDictItems/INS-FundsType"/>--%>
                <bc:column field="majorTrade" title="是否重大关联交易" width="150" formatter="fixedDict:IsStatusDict"/>
                <bc:column field="relTradeAmount" title="关联交易金额（万元）" width="150"/>
                <%--                <bc:column field="pracTradeAmount" title="实际交易金额（万元）" width="100" visible="false"/>--%>
                <bc:column field="creater" title="创建人" width="130"/>
                <bc:column field="tranDate" title="合同签署日期" width="100"/>
                <bc:column field="considerType" title="审议类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/INS-considerType"/>
                <bc:column field="status" title="交易状态" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-Applystatus"/>
                <%--                <bc:column field="statusComment" title="操作状态说明" width="100" visible="false"/>--%>

                <bc:column field="deptName" title="经办人部门" width="100"/>
<%--                <bc:operator id="view" icon="eye" title="操作/查看"/>--%>
            </bc:table>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script>

    //根据权限显示按钮
    $(function () {
        var code = "${code }";
        if (code.search("01,") != -1) {
            $('#traFundsTable_tb_add').show();
        }
        if (code.search("02,") != -1) {
            $('#traFundsTable_tb_edit').show();
        }
        if (code.search("03,") != -1) {
            $('#traFundsTable_tb_remove').show();
        }
        if (code.search("04,") != -1) {
            $('#traFundsTable_tb_import').show();
        }
        if (code.search("05,") != -1) {
            $('#traFundsTable_tb_task').show();
        }
        if (code.search("06,") != -1) {
            $('#traFundsTable_tb_downTmp').show();
        }
        if (code.search("07,") != -1) {
            $('#traFundsTable_tb_approve').show();
        }
        if (code.search("08") != -1) {
            $('#traFundsTable_tb_export').show();
        }
        if (code.search("034") != -1) {
            $('#traFundsTable_tb_ActualTra').show();
        }
        if (code.search("035") != -1) {
            $('#traFundsTable_tb_confirm').show();
        }
        if (code.search("036") != -1) {
            $('#traFundsTable_tb_PaymentCollection').show();
        }
        if (code.search("040") != -1) {
            $('#traFundsTable_tb_confirm1').show();
        }
    })


    // 修改弹出框的title, 显示弹框
    function ShowCreateModal(title) {
        $("#createFileTitle").text(title);
        $('#createFileMModal').modal('show');
    }

    // 关闭弹框， 获取输入值，然后执行逻辑
    $("#createFileSureBut").click(function () {
        $("#createFileMModal").modal("hide");
        var inputFileName = $("#fileName").val();
        console.log("input file name : " + inputFileName);
    });


    function getTransNumSelections() {
        return $.map($('#traFundsTable').bootstrapTable('getSelections'), function (row) {
            return row.transNum;
        });
    }

    function getIdSelections() {
        return $.map($('#traFundsTable').bootstrapTable('getSelections'), function (row) {
            return row.pid;//返回数据行中的id值
        });
    }

    function getStatusSelections() {
        return $.map($('#traFundsTable').bootstrapTable('getSelections'), function (row) {
            return row.status;//返回数据行中的id值
        });
    }

    function getDataSelections() {
        return $.map($('#traFundsTable').bootstrapTable('getSelections'), function (row) {
            return row.pid + ',' + row.amendId + ',' + row.amendName + ',' + row.tranType;//返回数据行中的id值
        });
    }

    // 获取关联方类型
    function getRelTypeSelections() {
        return $.map($('#traFundsTable').bootstrapTable('getSelections'), function (row) {
            return row.relType;
        });
    }

    function getStatusSelections() {
        return $.map($('#traBusinessTable').bootstrapTable('getSelections'), function (row) {
            return row.status;
        });
    }


    /*
  * 重置
  * */
    function frmReset() {
        window.location = '${base}/ins/tran/traFunds';
    }


    function loadCallback() {
        $('#traFundsTable').jTable('refresh');
    }

    //编辑
    function updateAmend(row) {
        var data = getDataSelections();
        var status = getStatusSelections();

        if (status == '4') {
            $.messager.alert('提示', '审批中的数据不支持修改经办人！', 'info');
            return false;
        }

        $.messager.modal({
            autoOpen: false,
            winId: 'winad',
            width: 800,
            height: 400,
            title: '变更经办人',
            url: '${base}/ins/tran/changeAmend/' + data,
            onClose: function () {
                $('#traFundsTable').bootstrapTable('refresh');
            }
        });
    }

    function getDataSelections() {
        return $.map($('#traFundsTable').bootstrapTable('getSelections'), function (row) {
            return row.pid + ',' + row.amendId + ',' + row.amendName + ',' + row.tranType;//返回数据行中的id值
        });
    }

    // 查看
    function viewEventHandler(){
        var pid = getIdSelections();
        var tranType = getTranType();
        window.location = '${base}/ins/tran/view/' + pid + '/' + tranType;
    }

    // 返回交易类型
    function getTranType(){
        return $.map($('#traFundsTable').bootstrapTable('getSelections'), function (row) {
            return row.tranType ;
        });
    }

</script>
</body>
</html>
