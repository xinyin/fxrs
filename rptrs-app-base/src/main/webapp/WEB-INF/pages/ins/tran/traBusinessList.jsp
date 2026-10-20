<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <style type="text/css">

        #traBusinessTable_tb_add, #traBusinessTable_tb_edit,
        #traBusinessTable_tb_remove, #traBusinessTable_tb_import,
        #traBusinessTable_tb_sendCommand, #traBusinessTable_tb_downTmp,
        #traBusinessTable_tb_approve, #traBusinessTable_tb_export,
        #traBusinessTable_tb_ActualTra, #traBusinessTable_tb_confirm,
        #traBusinessTable_tb_confirm1 {
            display: none;

        }
    </style>

    <title>关联交易（保险）-保险业务类</title>
    <style>
        #queryId1 {
            display: none;
        }

        #advancedQuery_id {
            height: 30px;
            line-height: 30px;
            width: 100px;
            overflow: hidden;
            cursor: pointer;
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
                    <bf:formGroup col="4">
                        <bc:label text="交易流水号" col="5" formControl="true"/>
                        <bf:text name="transNum" col="7" placeholder="交易流水号"/>
                    </bf:formGroup>

                    <bc:row cls="m-b">
                        <div class="col-sm-12 col-sm-offset-5">
                            <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                            <bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
                        </div>
                    </bc:row>
                </bc:row>
            </bf:form>
            <bc:table id="traBusinessTable" idField="pid" url="${base}/ins/tran" multiple="false"
                      selectFirst="true" queryUrl="queryBusinessPage">
                <bc:toolbar id="updateAmend" text="更新经办人" type="button" outline="true" handler="updateAmend"/>
                <bc:toolbar id="view" text="查看" type="button" handler="viewEventHandler"/>

                <bc:column field="pid" title="主键" width="100" visible="false"/>
                <bc:column field="relId" title="关联方ID" width="100" visible="false"/>
                <bc:column field="transNum" title="交易流水号" width="150"/>
                <bc:column field="tranName" title="交易概述" width="100"/>
                <bc:column field="contractNo" title="合同编号" width="100"/>
                <bc:column field="relName" title="关联方名称" width="120"/>
                <bc:column field="relType" title="关联方类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/INS-relType"/>
                <bc:column field="relNumber" title="证件号码" width="100"/>
                <bc:column field="busType" title="业务类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/INT-InsBusinessType"/>
                <bc:column field="majorTrade" title="是否重大关联交易" width="150" formatter="fixedDict:IsStatusDict"/>
                <bc:column field="isAgreement" title="是否统一交易协议" width="150" formatter="fixedDict:IsStatusDict"/>
                <%--                <bc:column field="isAgreementImpl" title="统一交易协议执行情况" width="150" formatter="custom:sys/dictItem/getDictItems/INS-isAgreementImpl"/>--%>
                <bc:column field="relTradeAmount" title="关联交易金额（万元）" width="150"/>
                <%--                <bc:column field="pracTradeAmount" title="实际交易金额（万元）" width="100" visible="false"/>--%>
                <bc:column field="creater" title="创建人" width="130"/>
                <bc:column field="contractDateString" title="合同签署日期" width="100"/>
                <bc:column field="considerType" title="审议类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/INS-considerType"/>
                <bc:column field="status" title="交易状态" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-Applystatus"/>
                <bc:column field="deptName" title="经办人部门" width="100"/>
                <bc:column field="statusComment" title="操作状态说明" width="100" visible="false"/>
<%--                <bc:operator id="view" icon="eye" title="操作/查看"/>--%>
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
        if (code.search("01,") != -1) {
            $('#traBusinessTable_tb_add').show();
        }
        if (code.search("02,") != -1) {
            $('#traBusinessTable_tb_edit').show();
        }
        if (code.search("03,") != -1) {
            $('#traBusinessTable_tb_remove').show();
        }
        if (code.search("04,") != -1) {
            $('#traBusinessTable_tb_import').show();
        }
        if (code.search("05,") != -1) {
            $('#traBusinessTable_tb_sendCommand').show();
        }
        if (code.search("06,") != -1) {
            $('#traBusinessTable_tb_downTmp').show();
        }
        if (code.search("07,") != -1) {
            $('#traBusinessTable_tb_approve').show();
        }
        if (code.search("08") != -1) {
            $('#traBusinessTable_tb_export').show();
        }
        if (code.search("034") != -1) {
            $('#traBusinessTable_tb_ActualTra').show();
        }
        if (code.search("035") != -1) {
            $('#traBusinessTable_tb_confirm').show();
        }
        if (code.search("040") != -1) {
            $('#traBusinessTable_tb_confirm1').show();
        }
    })

    function getStatusSelections() {
        return $.map($('#traBusinessTable').bootstrapTable('getSelections'), function (row) {
            return row.status;
        });
    }

    function getIdSelections() {
        return $.map($('#traBusinessTable').bootstrapTable('getSelections'), function (row) {
            return row.pid;
        });
    }

    function getDataSourcesSelections() {
        return $.map($('#traBusinessTable').bootstrapTable('getSelections'), function (row) {
            return row.dataSources;
        });
    }


    //刷新表格
    function loadCallback() {
        $('#naturalPersonTable').jTable('refresh');
    }

    /*
   * 重置
   * */
    function frmReset() {
        window.location = '${base}/ins/tran/traBusiness';
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
                $('#traBusinessTable').bootstrapTable('refresh');
            }
        });
    }
    function getDataSelections(){
        return $.map($('#traBusinessTable').bootstrapTable('getSelections'), function (row) {
            return row.pid + ',' + row.amendId + ',' + row.amendName + ',' + row.tranType ;//返回数据行中的id值
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
        return $.map($('#traBusinessTable').bootstrapTable('getSelections'), function (row) {
            return row.tranType ;
        });
    }
</script>
</body>
</html>