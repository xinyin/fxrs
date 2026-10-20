<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <style type="text/css">
        {
            display: none;
        }
    </style>
    <title>RelPar</title>
    <style>
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
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="生产报送文件" subTitle="" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">
                    <bf:formGroup col="3">
                        <bc:label text="文件名称" col="4" formControl="true"/>
                        <bf:text name="__LIKES_fileName" col="7" placeholder=""/>
                    </bf:formGroup>
                    <bf:formGroup col="3">
                        <div title="生成时间">
                            <bc:label cls="required" text="生成时间" col="4" formControl="true"/>
                            <bf:datebox  id="__LIKES_createTime" name="__LIKES_createTime" col="7" value="${entrust.createTime}"
                                         disabled="${opt == 'view'}">
                                <bv:notEmpty/>
                            </bf:datebox>
                        </div>
                    </bf:formGroup>
                    <bf:formGroup col="3">
                        <bc:label text="确认状态" col="4" formControl="true"/>
                        <bf:select name="__EQS_ackStatus" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-FailureStatus">
                            <bf:option text="请选择" value=""/>
                        </bf:select>
                    </bf:formGroup>
                    <bc:row cls="m-b">
                        <div class="col-sm-12 col-sm-offset-5">
                            <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                            <bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
                        </div>
                    </bc:row>
                </bc:row>
            </bf:form>
            <bc:table id="eastBatch" idField="id" url="${base}/east/batch" queryUrl="queryEastBatch">
                <bc:toolbar id="addNewFile" text="新增" type="button" handler="addHandler"/>
                <bc:toolbar id="remove" text="删除" type="button" handler="removeHandler"/>
                <bc:toolbar id="downFile" text="下载文件" type="button" handler="downFileOnclick"/>
                <bc:toolbar id="confirmFile" text="确认" type="button" handler="confirmButton"/>

                <bc:column field="batchNo" title="批次号" width="200"/>
                <bc:column field="fileName" title="文件名称" width="400"/>
                <bc:column field="createTimeString" title="生成报送文件时间" width="100"/>
                <bc:column field="createByName" title="操作员" width="100"/>
                <bc:column field="ackStatus" title="确认状态" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-FailureStatus"/>
            </bc:table>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">

    // 获取数据id
    function getIdSelections() {
        return $.map($('#eastBatch').bootstrapTable('getSelections'), function (row) {
            return row.id;
        });
    }

      // 获取数据批次号
    function getBatchNo() {
        return $.map($('#eastBatch').bootstrapTable('getSelections'), function (row) {
            return row.batchNo;
        });
    }

    // 获取数据fileType
    function getFileTypeSelections() {
        return $.map($('#eastBatch').bootstrapTable('getSelections'), function (row) {
            return row.fileType;
        });
    }

    // 新增
    function addHandler() {
        $.ajax({
            url: '${base}/east/batch/judgeDate',
            type: 'json',
            success: function (data) {
                if (data.success) {
                    $.messager.modal({
                        winId: 'batchAdd',
                        width: 550,
                        height: 550,
                        title: '请选择创建报送文件类型',
                        url: '${base}/east/batch/add',
                        onClose: function () {
                            $('#eastBatch').jTable('refresh');
                        }
                    });
                } else {
                    $.messager.alert('错误', data.msg, 'error');
                }
            }
        });
    }

    //删除文件
    function removeHandler() {
        $.messager.confirm('提示', '确定要删除吗？', function (r) {
            if (r) {
                const pid = getIdSelections();
                if (pid === '' || pid == null || pid.length !== 1) {
                    $.messager.alert('错误', "请选择一条数据", 'error');
                    return;
                }
                $.ajax({
                    url: '${base}/east/batch/remove/' + pid,
                    type: 'json',
                    success: function (data) {
                        if (data.success) {
                            $.messager.alert('提示', data.msg, 'info', function () {
                                window.location = '${base}/east/batch/list';
                            })
                        } else {
                            $.messager.alert('错误', data.msg, 'error');
                        }
                    }
                });
            }
        });
    }

    //下载文件
    function downFileOnclick() {
        const pid = getIdSelections();
        if (pid === '' || pid == null || pid.length !== 1) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }
        const types = getFileTypeSelections();
        for (const value of types) {
           var type = value;
        }
        const batchNos = getBatchNo();
        for (const value of batchNos) {
           var batchNo = value;
        }
        const data = batchNo;
        let url = "";
        if (type === 'RELATED') {
            url = '${base}/sys/xlsie/MidEastRelatedExportCallbackImpl/' + data;
        }else if (type === 'PRODUCT') {
            url = '${base}/sys/xlsie/MidEastProductExportCallbackImpl/' + data;
        }else if (type === 'RELATION') {
            url = '${base}/sys/xlsie/MidEastRelationshipExportCallbackImpl/' + data;
        }else if (type === 'FUNDS') {
            url = '${base}/sys/xlsie/MidEastFundsTraExportCallbackImpl/' + data;
        }else if (type === 'SERVICE') {
            url = '${base}/sys/xlsie/MidEastServiceTraExportCallbackImpl/' + data;
        }else if (type === 'INTEREST') {
            url = '${base}/sys/xlsie/MidEastInterestsTraExportCallbackImpl/' + data;
        }else if (type === 'BUSINESS') {
            url = '${base}/sys/xlsie/MidEastInsuranceTraExportCallbackImpl/' + data;
        }else if (type === 'TRANSACTION') {
            url = '${base}/sys/xlsie/MidEastTraDetailsExportCallbackImpl/' + data;
        }else if (type === 'ALL_FUNDS') {
            url = '${base}/sys/xlsie/MidEastZjyygljytjbExportCallbackImpl/' + data;
        }else if (type === 'REL_FUNDS') {
            url = '${base}/sys/xlsie/MidEastYdyglfzjyygljytjbExportCallbackImpl/' + data;
        }

        const elemIF = document.createElement("iframe");
        elemIF.src = url;
        elemIF.style.display = "none";
        document.body.appendChild(elemIF);
    }

    // 确认
    function confirmButton() {
        const pid = getIdSelections();
        if (pid === '' || pid == null || pid.length !== 1) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }
        $.ajax({
            url: '${base}/east/batch/judgeDate',
            type: 'json',
            success: function (data) {
                if (data.success) {
                    $.mask.show();
                    $.ajax({
                        url: '${base}/east/batch/confirm/' + pid,
                        type: 'json',
                        success: function (data) {
                            $.mask.remove();
                            if (data.success) {
                                $.messager.alert('提示', data.msg, 'info', function () {
                                    window.location = '${base}/east/batch/list';
                                })
                            } else {
                                $.messager.alert('错误', data.msg, 'error');
                            }
                        }
                    })
                } else {
                    $.messager.alert('错误', data.msg, 'error');
                }
            }
        });
    }

    //重置
    function frmReset() {
        window.location = '${base}/east/batch/list';
    }

</script>
</body>
</html>
