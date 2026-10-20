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
        #traFundsTable_tb_downTmp ,#traFundsTable_tb_attachmentAdd{
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
                            <bc:label text="关联方证件号码" col="5" formControl="true"/>
                            <bf:text name="relNumber" col="7" placeholder="关联方证件号码"/>
                        </bf:formGroup>
                        <bf:formGroup col="4">
                            <bc:label text="关联方名称" col="5" formControl="true"/>
                            <bf:text name="relName" col="7" placeholder="关联方名称"/>
                        </bf:formGroup>
                        <bf:formGroup col="4">
                            <bc:label text="业务类型" col="5" formControl="true"/>
                            <bf:select name="busType" col="7" dataSource="custom"
                                       url="sys/dictItem/getDictItems/INS-FundsType">
                                <bf:option text="请选择" value=""></bf:option>
                            </bf:select>
                        </bf:formGroup>
                    </bf:formGroup>
                    <bf:formGroup hrp="bottom">

                        <bf:formGroup hrp="bottom">
                            <bf:formGroup col="4">
                                <bc:label text="交易状态" col="5" formControl="true"/>
                                <bf:select name="status" col="7" dataSource="custom"
                                           url="sys/dictItem/getDictItems/BUS-Applystatus">
                                    <bf:option text="请选择" value=""></bf:option>
                                </bf:select>
                            </bf:formGroup>
                            <bf:formGroup col="4">
                                <bc:label text="审议类型" col="5" formControl="true"/>
                                <bf:select name="considerType" col="7" dataSource="custom"
                                           url="sys/dictItem/getDictItems/INS-considerType">
                                    <bf:option text="请选择" value=""></bf:option>
                                    <%--                                value="${unitCode}" disabled="${opt == 'MH'}"--%>
                                </bf:select>
                            </bf:formGroup>
                            <bf:formGroup col="4">
                                <bc:label text="是否重大关联交易" col="5" formControl="true"/>
                                <bf:select name="majorTrade" col="7" dataSource="fixedDict"
                                           url="YesOrNoForRelTraDict">
                                    <bf:option text="请选择" value=""></bf:option>
                                </bf:select>
                            </bf:formGroup>

                            <bf:formGroup col="4">
                                <bc:label text="合同编号" col="5" formControl="true"/>
                                <bf:text name="contractNo" col="7" placeholder="合同编号"/>
                            </bf:formGroup>

                            <bf:formGroup col="6">
                                <bc:label text="合同签署日期" col="3" formControl="true"/>
                                <bf:datebox id="startTranDate" name="startTranDate" col="3">
                                </bf:datebox>
                                <div style="float: left ;margin-top:8px">
                                    ----
                                </div>
                                <bf:datebox id="endTranDate" name="endTranDate" col="3">
                                </bf:datebox>
                            </bf:formGroup>

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
            <bc:table id="traFundsTable" idField="pid" url="${base}/ins/traFunds" multiple="false" selectFirst="true" queryUrl="queryPage">
                <bc:toolbar id="add" text="新增" type="button"/>
                <bc:toolbar id="edit" text="编辑" type="button" handler="toEdit"/>
                <bc:toolbar id="remove" text="删除" type="button" handler="removeHandler"/>
                <bc:toolbar id="downTmp" text="模板下载" type="button" handler="downloadHandler"/>
                <bc:toolbar id="import" text="导入" type="button" handler="importHandler"/>
                <bc:toolbar id="export" text="导出" type="button" handler="exportHandler"/>
                <%--                <bc:toolbar id="issued" text="任务下发" type="button" handler="sendCommand"/>--%>

                <bc:toolbar id="approve" text="提交审批" type="button" handler="send"/>
                <%--<bc:toolbar id="batchApprove" text="集中审议" type="button" handler="batchSend"/>--%>

                <bc:toolbar id="confirm" text="确认" type="button" handler="toConfirm"/>
                <bc:toolbar id="confirm1" text="签订" type="button" handler="sign"/>
                <bc:toolbar id="attachmentAdd" text="附件补充" type="button" handler="attachmentAdd"/>

                <bc:column field="pid" title="主键" width="100" visible="false"/>
                <bc:column field="relId" title="关联方ID" width="100" visible="false"/>
                <bc:column field="fundsFlowNumber" title="资金流水编号" width="150"/>
                <bc:column field="tranName" title="交易概述" width="100"/>
                <bc:column field="contractNo" title="合同编号" width="100"/>
                <bc:column field="relName" title="关联方名称" width="120"/>
                <bc:column field="relType" title="关联方类型" width="120"
                           formatter="custom:sys/dictItem/getDictItems/INS-relType"/>
                <bc:column field="relNumber" title="证件号码" width="100"/>
                <bc:column field="busType" title="业务类型" width="100" formatter="custom:sys/dictItem/getDictItems/INS-FundsType"/>
                <%--                <bc:column field="investmentType" title="投资类别" width="100" formatter="custom:sys/dictItem/getDictItems/INS-FundsType"/>--%>
                <bc:column field="majorTrade" title="是否重大关联交易" width="150" formatter="fixedDict:IsStatusDict"/>
                <bc:column field="relTradeAmount" title="本位币交易金额（万元）" width="150"/>
                <%--                <bc:column field="pracTradeAmount" title="实际交易金额（万元）" width="100" visible="false"/>--%>
                <bc:column field="creater" title="创建人" width="130"/>
                <bc:column field="tranDate" title="合同签署日期" width="100"/>
                <bc:column field="considerType" title="审议类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/INS-considerType"/>
                <bc:column field="status" title="交易状态" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-Applystatus"/>
                <%--                <bc:column field="statusComment" title="操作状态说明" width="100" visible="false"/>--%>

                <bc:column field="deptName" title="经办人部门" width="100"/>
                <bc:operator id="view" icon="eye" title="操作/查看"/>
            </bc:table>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script>

    //回款
    function PaymentCollection() {
        var contractAmount = getContractAmount();
        var pid = getIdSelections();
        $('#PaymentCollectionAmount').val(0);
        $('#loginModalId').modal('show');
        $.ajax({
            url: "${base}/ins/traFunds/PaymentCollection/" + pid,
            method: "GET",
            dataType: "JSON",
            traditional: true,
            success: function (data) {
                if (data.success) {
                    $('#amountReceived').val(data.amountReceived);
                    $('#contractAmount').val(contractAmount)
                }
            }
        })
    }

    function PaymentCollectionAmount() {
        var pid = getIdSelections();
        var paymentCollectionAmount = $('#PaymentCollectionAmount').val();
        var amountReceived = $('#amountReceived').val();
        var contractAmount = $('#contractAmount').val();
        if ((parseFloat(paymentCollectionAmount) + parseFloat(amountReceived)) < parseFloat(contractAmount)) {
            $.ajax({
                url: "${base}/ins/traFunds/PaymentCollectionAmount/" + pid + "/" + paymentCollectionAmount,
                method: "GET",
                dataType: "JSON",
                traditional: true,
                success: function (data) {
                    if (data.success) {
                        $('#loginModalId').modal('hide');
                        $.messager.alert('提示', data.msg, 'info');
                    }
                }
            })
        } else {
            $.messager.alert('提示', "累计汇款金额不可超过关联交易金额", 'info');
        }
    }

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
        if (code.search("021") != -1) {//上传
            $('#traFundsTable_tb_attachmentAdd').show();
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

    /*
    * 跳转到提交审批页面
    * */
    function send() {
        // 提交审批  的必须是待提交状态
        var status = getStatusSelections();
        var pid = getIdSelections();

        var relType = getRelTypeSelections();
        var tranType = getTranTypeSelections();
        var relId = getrelIdSelections();
        var considerType = getConsiderTypeSelections();
        if (pid == '' || pid == null || pid.length != 1) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }
        $.ajax({
            type: 'GET',
            url: '${base}/ins/traFunds/createPerisExist?pid=' + pid,
            dataType: 'json',
            delay: 200,
            success: function (data) {
                if (data.success) {
                    if (status == 5) {
                        $.messager.alert('错误', "审批退回数据请编辑后在重新提交！", 'error');
                        return;
                    }
                    if (status != 3) {
                        $.messager.alert('错误', "提交数据状态不符，无法提交！", 'error');
                        return;
                    }

                    if ("3" == considerType) {
                        if (relType == "ORG") {
                            var flag = jungleRelatedDate(relType, pid, tranType);
                            if (!flag) {
                                return;
                            }
                        }
                    }
                    //提交
                    $.mask.post('${base}/ins/traFunds/send/' + pid, function (data) {
                        if (data.success) {
                            $.messager.alert('提示', '提交成功。', 'info', function () {
                                window.location = '${base}/workflow/process/runingList';
                            });
                        } else {
                            $.messager.alert('错误', data.msg, 'error');
                        }
                    });
                } else {
                    $.messager.alert('提示', "您没有权限!", 'error');
                    return;
                }
            }
        });
    }


    function jungleRelatedDate(relType, pid,tranType) {
        /**
         * 校验关联方数据是否填完整
         * @type {*|jQuery}
         */
        let flag = true;
        $.ajax({
            type: 'GET',
            url: '${base}/ins/traService/jungleRelatedDate?relType=' + relType + "&pid=" + pid +"&tranType=" + tranType,
            dataType: 'json',
            async: false,
            delay: 200,
            success: function (data) {
                if (data.success) {
                    $.messager.alert('提示', "请联系法律合规部关联交易管理岗位人员补录完整法人关联方信息。", 'error');
                    flag = false;
                } else {
                    flag = true;
                }
            }
        });
        return flag;
    }


    // 跳转到集中审议列表
    function batchSend() {
        window.location = '${base}/ins/traFunds/batchSendList'
    }

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

    //确认按钮
    function toConfirm() {
        var pid = getIdSelections()
        var status = getStatusSelections()
        if (pid == '' || pid == null || pid.length != 1) {
            $.messager.alert('错误', "请选择一条数据", 'error');
        }
        if (status == 2) {
            window.location = '${base}/ins/traFunds/toConfirm/' + pid;
        } else {
            $.messager.alert('错误', "当前状态不符合要求", 'error');
            return;
        }
    }


    //签订按钮
    function sign() {
        var pid = getIdSelections()
        var status = getStatusSelections()
        if (pid == '' || pid == null || pid.length != 1) {
            $.messager.alert('错误', "请选择一条数据", 'error');
        }
        if (status == 8) {
            window.location = '${base}/ins/traFunds/sign/' + pid;
        } else {
            $.messager.alert('错误', "当前状态不符合要求", 'error');
            return;
        }
    }

    //实际交易数据管理
    function ActualTra() {
        var transNum = getTransNumSelections();
        var pid = getIdSelections();
        if (pid == '' || pid == null || pid.length != 1) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }
        window.location = '${base}/ins/traCramcInfo/ActualTra/' + transNum;
    }

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

    // 获取关联方类型
    function getRelTypeSelections() {
        return $.map($('#traFundsTable').bootstrapTable('getSelections'), function (row) {
            return row.relType;
        });
    }

    // 获取关联方类型
    function getTranTypeSelections() {
        return $.map($('#traFundsTable').bootstrapTable('getSelections'), function (row) {
            return row.tranType;
        });
    }



    function getrelIdSelections() {
        return $.map($('#traFundsTable').bootstrapTable('getSelections'), function (row) {
            return row.relId;
        });
    }

    /**获取选中的审议类型*/
    function getConsiderTypeSelections() {
        return $.map($('#traFundsTable').bootstrapTable('getSelections'), function (row) {
            return row.considerType;
        });
    }

    /**获取选中的实际交易金额*/
    function getPracTradeAmountSelections() {
        return $.map($('#traFundsTable').bootstrapTable('getSelections'), function (row) {
            return row.pracTradeAmount;
        });
    }

    function getDataSourcesSelections() {
        return $.map($('#traFundsTable').bootstrapTable('getSelections'), function (row) {
            return row.dataSources;//返回数据行中的id值
        });
    }

    function getContractAmount() {
        return $.map($('#traFundsTable').bootstrapTable('getSelections'), function (row) {
            return row.contractAmount;//返回数据行中的id值
        });
    }

    function editHandler() {
        var status = getStatusSelections();
        if (status == '2') {
            $.messager.alert('提示', '带确认数据不可编辑', 'info');
            return false;
        }
        if (status == '4') {
            $.messager.alert('提示', '审批中数据不可编辑', 'info');
            return false;
        }
        if (status == '6') {
            $.messager.alert('提示', '已归档数据不可编辑', 'info');
            return false;
        }
        if (status == '8') {
            $.messager.alert('提示', '已查阅数据不可编辑', 'info');
            return false;
        }
        if (status == '10') {
            $.messager.alert('提示', '待归档数据不可编辑', 'info');
            return false;
        }
        var pid = getIdSelections();
        if (pid == '') {
            $.messager.alert('提示', '请选择一条数据', 'info');
            return false;
        }
        window.location = "${base}/ins/traFunds/edit/" + pid;
    }

    function removeHandler() {
        var status = getStatusSelections();
        var considerType = getConsiderTypeSelections();
        var pid = getIdSelections();
        if (pid == '' || pid == null) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }
        if (pid.length > 1) {
            $.messager.alert('错误', "不能选择多条数据进行删除操作，请重新勾选", 'error');
            return;
        }
        $.ajax({
            type: 'GET',
            url: '${base}/ins/traFunds/createPerisExist?pid=' + pid,
            dataType: 'json',
            delay: 200,
            success: function (data) {
                if (data.success) {
                    if ((status == '2'||  status == '4' || status == '7' || status == '8' || status == '6') && ("1" != considerType)) {
                        $.messager.alert('提示', '待确认、审批中、履行中、待签订、已归档（一般审议、逐笔审议）的交易数据不允许删除', 'info');
                        return false;
                    }
                    $.messager.confirm('提示', '确定要删除吗？', function (r) {
                        if (r) {
                            $.ajax({
                                url: "${base}/ins/traFunds/remove/" + pid,
                                method: "GET",
                                data: {"": ""},
                                dataType: "JSON",
                                traditional: true,
                                success: function (data) {
                                    if (data.success) {
                                        $.messager.alert('提示', data.msg, 'info', function () {
                                            window.location = "${base}/ins/traFunds/list";
                                        })
                                    } else {
                                        $.messager.alert('错误', data.msg, 'error');
                                    }
                                }
                            })
                        }
                    })
                } else {
                    $.messager.alert('提示', "您没有权限!", 'error');
                    return;
                }
            }
        });
    }

    function taskHandler() {
        var tables = $('#traFundsTable').jTable('getCheckData');
        if (tables.length == 1) {
            $.messager.modal({
                winId: 'traSend',
                width: 700,
                height: 420,
                title: '选择机构',
                url: '${base}/ins/traFunds/sendCommand',
                args: {traId: tables[0].pid},
                onClose: function () {
                    $('#traFundsTable').jTable('clearCheckData');
                    $('#traFundsTable').jTable('refresh');
                }
            });
        } else {
            $.messager.toastr('提示', '请选择一条数据');
        }
    }

    function submitHandler() {
        var status = getStatusSelections();
        var pid = getIdSelections();
        if (status == 5) {
            $.messager.alert('错误', "审批退回数据请编辑后在重新提交！", 'error');
            return;
        }
        if (status == 3 || status == 9) {
            window.location = "${base}/ins/traFunds/submit/" + pid;
        } else {
            $.messager.alert('错误', "当前状态不符合要求！", 'error');
        }
    }

    /*
  * 重置
  * */
    function frmReset() {
        window.location = '${base}/ins/traFunds/list';
    }

    //模板下载
    function downloadHandler() {
        var data = "00"
        var url = '${base}/sys/xlsie/InsTraFundsOutCallbackImpl/' + data;
        var elemIF = document.createElement("iframe");
        elemIF.src = url;
        elemIF.style.display = "none";
        document.body.appendChild(elemIF);
    }

    //导出
    function exportHandler() {
        var relName = $("[name='relName']").val() == "" ? "%25" : $("[name='relName']").val();
        var relNumber = $("[name='relNumber']").val() == "" ? "%25" : $("[name='relNumber']").val();
        var investmentType = $("[name='busType']").val() == "" ? "%25" : $("[name='busType']").val();
        var status = $("[name='status']").val() == "" ? "%25" : $("[name='status']").val();
        var considerType = $("[name='considerType']").val() == "" ? "%25" : $("[name='considerType']").val();
        var majorTrade = $("[name='majorTrade']").val() == "" ? "%25" : $("[name='majorTrade']").val();
        var startTranDate = $("[name='startTranDate']").val() == "" ? "%25" : $("[name='startTranDate']").val();
        var endTranDate = $("[name='endTranDate']").val() == "" ? "%25" : $("[name='endTranDate']").val();

        var contractNo = $("[name='contractNo']").val() == "" ? "%25" : $("[name='contractNo']").val();
        var type = "check";
        var data = type + "," + relName + "," + investmentType + "," + relNumber
            + "," + status + "," + considerType + "," + majorTrade + "," + startTranDate + "," + endTranDate+ "," + contractNo;
        var url = '${base}/sys/xlsie/InsTradeFundsExportCallbackImpl/' + data;
        var elemIF = document.createElement("iframe");
        elemIF.src = url;
        elemIF.style.display = "none";
        document.body.appendChild(elemIF);
    }

    //导入
    function importHandler() {
        $.messager.modal({
            winId: 'wImportFromExcel', //winid只能为 wImportFromExcel
            width: 500,
            height: 230,
            args: {
                callback: loadCallback
            },
            title: '资金运用类信息导入',
            url: '${base}/sys/xlsie/import?bean=InsTradeFundsImportCallbackImpl',
            onClose: function () {
                loadCallback();
            }
        });
    }

    function loadCallback() {
        $('#traFundsTable').jTable('refresh');
    }

    //编辑
    function toEdit() {
        var pid = getIdSelections();
        if (pid == '' || pid == null) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }
        if (pid.length > 1) {
            $.messager.alert('错误', "不能选择多条数据进行编辑操作，请重新勾选", 'error');
            return;
        }
        $.ajax({
            type: 'GET',
            url: '${base}/ins/traFunds/createPerisExist?pid=' + pid,
            dataType: 'json',
            delay: 200,
            success: function (data) {
                if (data.success) {
                    var status = getStatusSelections();
                    var considerType = getConsiderTypeSelections();
                    if (status == '4') {
                        $.messager.alert('提示', '审批中的数据不可编辑', 'info');
                        return false;
                    }
                    if (status == '2') {
                        $.messager.alert('提示', '带确认数据不可编辑', 'info');
                        return false;
                    }
                    // if (status == '9') {
                    //     $.messager.alert('提示', '已驳回的数据不可编辑', 'info');
                    //     return false;
                    // }
                    // if (status == '3') {
                    //     $.messager.alert('提示', '待提交数据不可编辑', 'info');
                    //     return false;
                    // }

                    if ("1" != considerType) {
                        if (status == '8') {
                            $.messager.alert('提示', '待签订的数据不可编辑', 'info');
                            return false;
                        }
                        if (status == '7') {
                            $.messager.alert('提示', '履行中的数据不可编辑', 'info');
                            return false;
                        }
                        if (status == '6') {
                            $.messager.alert('提示', '已归档数据不可编辑', 'info');
                            return false;
                        }

                    }

                    window.location = '${base}/ins/traFunds/edit/' + pid;
                } else {
                    $.messager.alert('提示', "您没有权限!", 'error');
                    return;
                }
            }
        });
    }

    //日期插件初始化
    $('#startTranDate').datetimepicker({
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        autoclose: true,
        minView: "month"
    }).on("changeDate", function (ev) {  //值改变事件
        //选择的日期不能大于第二个日期控件的日期
        if (ev.date) {
            $("#endTranDate").datetimepicker('setStartDate', new Date(ev.date.valueOf()));
        } else {
            $("#endTranDate").datetimepicker('setStartDate', null);
        }
    });
    $('#endTranDate').datetimepicker({
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        minView: "month",
        autoclose: true,

    }).on("changeDate", function (ev) {
        //选择的日期不能小于第一个日期控件的日期
        if (ev.date) {
            $("#startTranDate").datetimepicker('setEndDate', new Date(ev.date.valueOf()));
        } else {
            $("#startTranDate").datetimepicker('setEndDate', new Date());
        }
    });

    /**
     * 上传附件
     */
    function attachmentAdd() {
        var pid = getIdSelections()
        var status = getStatusSelections()
        if (pid == '' || pid == null || pid.length != 1) {
            $.messager.alert('错误', "请选择一条数据", 'error');
        }
        if ('4' != status) {
            window.location = '${base}/ins/traFunds/attachmentAdd/' + pid;
        } else if ('9' != status) {
            $.messager.alert('错误', "该笔交易已经被驳回，请勿上传附件！", 'error');
            return;
        }
    }
</script>
</body>
</html>
