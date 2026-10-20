<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <style type="text/css">

        #traInterestTable_tb_add, #traInterestTable_tb_edit,
        #traInterestTable_tb_remove, #traInterestTable_tb_import,
        #traInterestTable_tb_task, #traInterestTable_tb_download,
        #traInterestTable_tb_submit, #traInterestTable_tb_out ,
        #traInterestTable_tb_ActualTra, #traInterestTable_tb_toConfirm{
            display: none;

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

        .bs-checkbox{
            display: none;
        }
    </style>
    <title>统一交易协议执行情况统计</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="" subTitle="" col="12" collapsible="true">
            <%--查询条件--%>
            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">
                    <bf:formGroup col="4">
                        <bc:label text="年度" col="5" formControl="true"/>
                        <bf:datebox id="year" name="year" col="7" format="date" />
                    </bf:formGroup>
                   <%-- <bf:formGroup col="4">
                        <bc:label text="年度" col="5" formControl="true"/>
                        <bf:datebox id="year" name="year" col="7" format="date">
                            <bv:date format="YYYY" message="只能输入合理的年份，类型为YYYY类型"/>
                        </bf:datebox>
                    </bf:formGroup>--%>
                    <%--季度，调用字典接口--%>
                    <bf:formGroup col="4">
                        <bc:label text="季度" col="5" formControl="true"/>
                        <bf:select name="quarter" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-Quarter">
                            <bf:option text="ALL" value=""></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_fromType");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <%--合同编号--%>
                    <bf:formGroup col="4">
                        <bc:label text="合同编号" col="5" formControl="true"/>
                        <bf:text name="contractNo" col="7" placeholder="合同编号" value=""/>
                    </bf:formGroup>
                </bc:row>
                <bc:row cls="m-b">
                    <div class="col-sm-12 col-sm-offset-5">
                        <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                        <bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
                    </div>

                </bc:row>
            </bf:form>
            <bc:table id="traInterestTable" idField="pid" url="${base}/bus/protocol" queryUrl="queryByConditions">
                <bc:toolbar id="export" text="导出" type="button" handler="out"/>
                <bc:column field="pid" title="主键" width="100" visible="false"/>
                <bc:column field="contractDateStr" title="合同签订日期" width="100"/>
                <bc:column field="relName" title="关联方名称" width="100"/>
                <bc:column field="relType" title="关联方类别" width="100"
                           formatter="custom:sys/dictItem/getDictItems/INS-relType"/>
                <bc:column field="relNumber" title="关联方代码" width="100"/>
                <bc:column field="tranName" title="交易概述" width="100"/>
                <bc:column field="contractNo" title="合同编号" width="100"/>
                <bc:column field="contractAmount" title="合同金额(万元)" width="100"/>
                <bc:column field="pracTradeAmount" title="实际交易金额(万元)" width="100"/>
                <bc:column field="accounted" title="实际交易金额占比%" width="100"/>
               <%-- <bc:column field="transactionName" title="交易名称" width="100"/>--%>
                <%--<bc:column field="relName" title="关联方名称" width="100"/>
                <bc:column field="relId" title="关联方代码" width="100"/>
                <bc:column field="relType" title="关联方类别" width="100"
                           formatter="custom:sys/dictItem/getDictItems/INS-relType"/>
                <bc:column field="tranType" title="交易类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/INS-tranType"/>
                <bc:column field="busType" title="业务类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/INS-ServiceType"/>--%>
                <%--<bc:column field="isAgreementImpl" title="统一交易协议执行情况" width="100"
                           formatter="custom:sys/dictItem/getDictItems/INS-isAgreementImpl"/>--%>
                <%--<bc:column field="agreementImpl" title="统一交易协议执行情况" width="100"/>--%>
             <%--   <bc:column field="agreementNum" title="框架协议编号" width="100"/>--%>
               <%-- <bc:column field="contractNo" title="合同编号" width="100"/>
                <bc:column field="tranDateStr" title="合同签署日期" width="100"/>
                <bc:column field="pracTradeAmount" title="实际交易金额" width="100"/>
                <bc:column field="pricingPolicy" title="定价政策" width="100"/>
                <bc:column field="tradeSubject" title="交易标的" width="100"/>--%>
               <%-- <bc:column field="isHoldingSubsidiary" title="是否为控股子公司" width="100" formatter="fixedDict:IsStatusDict"/>
                <bc:column field="holdingSubsidiaryName" title="控股子公司全称" width="100"/>
                <bc:column field="holdingSubsidiaryNumber" title="控股子公司证照号码" width="100"/>--%>
                <%--<bc:column field="InterestType" title="证件类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/INS-certType"/>
                <bc:column field="relNumber" title="证照号码" width="100"
                           formatter="custom:sys/dictItem/getDictItems/INS-relNumber"/>

                <bc:column field="majorTrade" title="是否重大关联交易" width="100" formatter="fixedDict:IsStatusDict"/>
                <bc:column field="contractAmount" title="合同金额（万元/人民币）" width="100"/>

                <bc:column field="considerType" title="审议类型" width="100"/>
                <bc:column field="status" title="操作状态" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-Applystatus"/>
                <bc:column field="statusComment" title="操作状态说明" width="100"/>
                <bc:operator id="view" icon="search" title="操作/查看"/>--%>
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
            $('#traInterestTable_tb_add').show();
        }
        if (code.search("02,") != -1) {
            $('#traInterestTable_tb_edit').show();
        }
        if (code.search("03,") != -1) {
            $('#traInterestTable_tb_remove').show();
        }
        if (code.search("04,") != -1) {
            $('#traInterestTable_tb_import').show();
        }
        if (code.search("05,") != -1) {
            $('#traInterestTable_tb_send').show();
        }
        if (code.search("06,") != -1) {
            $('#traInterestTable_tb_export').show();
        }
        if (code.search("07,") != -1) {
            $('#traInterestTable_tb_submit').show();
        }
        if (code.search("08") != -1) {
            $('#traInterestTable_tb_out').show();
        }
        if (code.search("034") != -1) {
            $('#traInterestTable_tb_ActualTra').show();
        }
        if (code.search("035") != -1) {
            $('#traInterestTable_tb_toConfirm').show();
        }
    })
    //导出
    function out() {
        var year = $("[name='year']").val();
        var quarter = $("[name='quarter']").val();
        var protocolCode = $("[name='protocolCode']").val();
        var data={
            "year":year,
            "quarter":quarter,
            "protocolCode":protocolCode
        }
        console.log(JSON.stringify(data))
        var url = '${base}/sys/xlsie/UnifiedProtocolExportCallBackImpl/'+JSON.stringify(data);
        var elemIF = document.createElement("iframe");
        elemIF.src = url;
        elemIF.style.display = "none";
        document.body.appendChild(elemIF);
    }

    //确认按钮
    function toConfirm() {
        var pid = getIdSelections()
        var status = getStatusSelections()
        if (pid == '' || pid == null || pid.length != 1) {
            $.messager.alert('错误', "请选择一条数据", 'error');
        }
        if (status == 9) {
            window.location = '${base}/ins/traInterest/toConfirm/' + pid;
        }else {
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
        return $.map($('#traInterestTable').bootstrapTable('getSelections'), function (row) {
            return row.transNum;
        });
    }
    /*获取当前数据id*/
    function getIdSelections() {
        return $.map($('#traInterestTable').bootstrapTable('getSelections'), function (row) {
            return row.pid;
        });
    }

    /*获取当前数据状态*/
    function getStatusSelections() {
        return $.map($('#traInterestTable').bootstrapTable('getSelections'), function (row) {
            return row.status;
        });
    }

    /*
    * 编辑
    * */
    function editHandler() {
        var pid = getIdSelections();
        if (pid == '' || pid == null || pid.length != 1) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }
        var status = getStatusSelections();
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
        window.location = '${base}/ins/traInterest/edit/' + pid;
    }

    /*
    * 提交审批
    * */

    function submitHandler() {
        var status = getStatusSelections();
        var pid = getIdSelections();
        if(status == 5 ){
            $.messager.alert('错误', "审批退回数据请编辑后在重新提交！", 'error');
            return;
        }
        if (status == 3  || status == 9) {
            window.location = "${base}/ins/traInterest/submit/" + pid;
        } else {
            $.messager.alert('错误', "当前状态不符合要求！", 'error');
        }
    }

    function send() {
        // 提交审批  的必须是待提交状态
        var status = getStatusSelections();
        var pid = getIdSelections();
        if (pid == '' || pid == null || pid.length != 1) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }
        if (status == 3) {
            //查询出基本信息 修改状态 放到data中
            $.ajax({
                url: "${base}/ins/traInterest/send/" + pid,
                method: "GET",
                dataType: "JSON",
                traditional: true,
                success: function (data) {
                    if (data.success) {
                        //开始走工作流
                        var objData = data.data;
                        var myDate = new Date();
                        objData.id = myDate.getTime();
                        objData.filNum = '4o' + randomNumber()
                        data = JSON.stringify(objData);
                        var formData = $.helper.base64Encode(data);
                        //挂取工作流
                        var obj = {
                            procDefCode: 'zjcc_tra001',
                            oldData: '',
                            newData: formData,
                            uploadSessions: '',
                            procInstId: ''
                        }

                        $.mask.post('${base}/workflow/process/saveAndStart', obj, function (data) {
                            if (data.success) {
                                $.messager.alert('提示', '提交成功。', 'info', function () {
                                    window.location = '${base}/workflow/process/runingList';
                                });
                            } else {
                                $.messager.alert('错误', data.msg, 'error');
                            }
                        });
                    } else {
                        $.messager.alert('错误', data.msg, 'error');
                    }
                }
            })
        } else {
            alert("当前状态不符合要求！");
        }
    }

    /* 任务下发 */
    function sendCommand() {
        var tables = $('#traInterestTable').jTable('getCheckData');
        if (tables.length == 1) {
            $.messager.modal({
                winId: 'traInterestSend',
                width: 700,
                height: 420,
                title: '选择机构',
                url: '${base}/ins/traInterest/traInterestSend',
                args: {orgId: tables[0].pid},
                onClose: function () {
                    $('#traInterestTable').jTable('clearCheckData');
                    $('#traInterestTable').jTable('refresh');
                }
            });
        } else {
            $.messager.toastr('提示', '请选择一条数据');
        }
    }

    /*
    * 重置
    *
    *
    * */
    function frmReset() {

        window.location = '${base}/bus/protocol/list';
    }

    //模板下载
    function exportHandler() {
        var data = "1"
        var url = '${base}/sys/xlsie/TraInterestOutCallbackImpl/' + data;
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
            title: '保险业务类信息导入',
            url: '${base}/sys/xlsie/import?bean=InsTradeInterestImportCallbackImpl',
            onClose: function () {
                loadCallback();
            }
        });
    }

    $("#year").datetimepicker({
        format:"yyyy",
        autoclose: true,
        startView:4,
        minView:4,
        todayHighlight:true
    });

    function loadCallback() {
        $('#traInterestTable').jTable('refresh');
    }
</script>
</body>
</html>