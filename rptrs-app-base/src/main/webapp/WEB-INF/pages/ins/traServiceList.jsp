<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <style type="text/css">

        #traServiceTable_tb_add, #traServiceTable_tb_edit,
        #traServiceTable_tb_remove, #traServiceTable_tb_import,
        #traServiceTable_tb_sendCommand, #traServiceTable_tb_downTmp,
        #traServiceTable_tb_approve, #traServiceTable_tb_export,
        #traServiceTable_tb_batchApprove,
        #traServiceTable_tb_ActualTra, #traServiceTable_tb_confirm,
        #traServiceTable_tb_confirm1,#traServiceTable_tb_attachmentAdd
        {
            display: none;

        }
        .form-group .input-group.date,
        .form-group .select2{
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


        table {
            table-layout: fixed;
        }

        td {
            overflow: hidden;
            white-space: nowrap;
            text-overflow: ellipsis;
        }

    </style>
    <title>关联交易表（保险）-提供服务类</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="" subTitle="请输入关联交易表（保险）-提供服务类查询信息" col="12" collapsible="true">
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
                                       url="sys/dictItem/getDictItems/INS-ServiceType">
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
                                           url="sys/dictItem/getDictItems/INS-considerType" >
                                    <bf:option text="请选择" value=""></bf:option>
                                    <%--                                value="${unitCode}" disabled="${opt == 'MH'}"--%>
                                </bf:select>
                            </bf:formGroup>
                            <bf:formGroup col="4">
                                <bc:label text="是否重大关联交易" col="5" formControl="true"/>
                                <bf:select name="majorTrade" col="7" dataSource="fixedDict" url="YesOrNoForRelTraDict">
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

                    <bf:formGroup hrp="bottom" id="queryId">
                        <bf:formGroup col="4">
                            <bc:label text="是否为统一交易协议" col="5" formControl="true"/>
                            <bf:select name="isAgreement" col="7" dataSource="fixedDict" url="YesOrNoForRelTraDict">
                                <bf:option text="请选择" value=""></bf:option>
                            </bf:select>
                        </bf:formGroup>
                        <bf:formGroup col="4">
                            <bc:label text="统一交易协议执行情况" col="5" formControl="true"/>
                            <bf:select name="isAgreementImpl" col="7" dataSource="fixedDict" url="YesOrNoForRelTraDict">
                                <bf:option text="请选择" value=""></bf:option>
                            </bf:select>
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
            <bc:table id="traServiceTable" idField="pid" url="${base}/ins/traService" multiple="false" selectFirst="true" queryUrl="queryPage">
                <bc:toolbar id="add" text="新增" type="button"/>
                <bc:toolbar id="edit" text="编辑" type="button" handler="editHandler"/>
                <bc:toolbar id="remove" text="删除" type="button" handler="removeHandler"/>
                <bc:toolbar id="downTmp" text="模板下载" type="button" handler="downloadHandler"/>
                <bc:toolbar id="import" text="导入" type="button" handler="importHandler"/>
                <bc:toolbar id="export" text="导出" type="button" handler="out"/>
                <bc:toolbar id="approve" text="提交审批" type="button" handler="send"/>
                <bc:toolbar id="confirm" text="确认" type="button" handler="toConfirm"/>
                <bc:toolbar id="confirm1" text="签订" type="button" handler="sign"/>
                <bc:toolbar id="attachmentAdd" text="附件补充" type="button" handler="attachmentAdd"/>
               <%-- <bc:toolbar id="batchApprove" text="集中审议" type="button" handler="batchSend"/>--%>

                <bc:column field="pid" title="主键" width="100" visible="false"/>
                <bc:column field="relId" title="关联方ID" width="100" visible="false"/>
                <bc:column field="fundsFlowNumber" title="交易流水编号" width="100"/>
                <bc:column field="tranName" title="交易概述" width="100"/>
                <bc:column field="contractNo" title="合同编号" width="100"/>
                <bc:column field="relName" title="关联方名称" width="100"/>
                <bc:column field="relType" title="关联方类型" width="100" formatter="custom:sys/dictItem/getDictItems/INS-relType"/>
                <bc:column field="relNumber" title="证件号码" width="120"/>
                <bc:column field="busType" title="业务类型" width="100" formatter="custom:sys/dictItem/getDictItems/INS-ServiceType"/>
                <bc:column field="majorTrade" title="是否重大关联交易" width="150" formatter="fixedDict:IsStatusDict"/>
                <bc:column field="relTradeAmount" title="本位币交易金额（万元）" width="150"/>
                <bc:column field="isAgreement" title="是否统一交易协议" width="150" formatter="fixedDict:IsStatusDict"/>
<%--                <bc:column field="isAgreementImpl" title="统一交易协议执行情况" width="70" formatter="custom:sys/dictItem/getDictItems/INS-isAgreementImpl"/>--%>
<%--                <bc:column field="relTradeAmount" title="关联交易金额（万元）" width="100"/>--%>
<%--                <bc:column field="pracTradeAmount" title="实际交易金额（万元）" width="100" visible="false"/>--%>
                <bc:column field="creater" title="创建人" width="130"/>
                <bc:column field="contractDateString" title="合同签署日期" width="100"/>
                <bc:column field="considerType" title="审议类型" width="100" formatter="custom:sys/dictItem/getDictItems/INS-considerType"/>
                <bc:column field="status" title="交易状态" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-Applystatus"/>
                <bc:column field="deptName" title="经办人部门" width="100"/>
<%--                <bc:column field="statusComment" title="操作状态说明" width="100"/>--%>
                <bc:operator id="view" icon="eye" title="操作/查看"/>
            </bc:table>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>

<script src="../../static/js/jquery.dialog.js"></script>

<script type="text/javascript">
    //高级查询
    window.onload = function(){
        document.getElementById("queryId").style.display = "none";
        var flag = true;
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
    $(function () {
        var code = "${code }";
        if (code.search("01,") != -1) {//新增
            $('#traServiceTable_tb_add').show();
        }
        if (code.search("02,") != -1) {//编辑
            $('#traServiceTable_tb_edit').show();
        }
        if (code.search("03,") != -1) {//删除
            $('#traServiceTable_tb_remove').show();
        }
        if (code.search("04,") != -1) {
            $('#traServiceTable_tb_import').show();
        }
        if (code.search("05,") != -1) {
            $('#traServiceTable_tb_sendCommand').show();
        }
        if (code.search("06,") != -1) {//模板下载
            $('#traServiceTable_tb_downTmp').show();
        }
        if (code.search("07,") != -1) {//提交审批
            $('#traServiceTable_tb_approve').show();
        }
        if (code.search("08") != -1) {//导出
            $('#traServiceTable_tb_export').show();
        }
        if (code.search("021") != -1) {//上传
            $('#traServiceTable_tb_attachmentAdd').show();
        }
        if (code.search("034") != -1) {
            $('#traServiceTable_tb_ActualTra').show();
        }
        if (code.search("035") != -1) {//归档确认
            $('#traServiceTable_tb_confirm').show();
        }
        if (code.search("040") != -1) {//归档确认
            $('#traServiceTable_tb_confirm1').show();
        }
    })

    //模板下载
    function downloadHandler() {
        var data = '0';
        var url = '${base}/sys/xlsie/InsTraServiceOutCallbackImpl/' + data;
        var elemIF = document.createElement("iframe");
        elemIF.src = url;
        elemIF.style.display = "none";
        document.body.appendChild(elemIF);
    }

    //导出
    function out() {
        var relName = $("[name='relName']").val() == "" ? "%25" : $("[name='relName']").val();
        var relNumber = $("[name='relNumber']").val() == "" ? "%25" : $("[name='relNumber']").val();
        var busType = $("[name='busType']").val() == "" ? "%25" : $("[name='busType']").val();
        var status = $("[name='status']").val() == "" ? "%25" : $("[name='status']").val();
        var considerType = $("[name='considerType']").val() == "" ? "%25" : $("[name='considerType']").val();
        var majorTrade = $("[name='majorTrade']").val() == "" ? "%25" : $("[name='majorTrade']").val();
        var startTranDate = $("[name='startTranDate']").val() == "" ? "%25" : $("[name='startTranDate']").val();
        var endTranDate = $("[name='endTranDate']").val() == "" ? "%25" : $("[name='sendTranDate']").val();
        var endTranDate = $("[name='endTranDate']").val() == "" ? "%25" : $("[name='endTranDate']").val();
        var contractNo = $("[name='contractNo']").val() == "" ? "%25" : $("[name='contractNo']").val();
        var type = "check";
        var data = type + "," + relName + "," + busType + "," + relNumber
            + "," + status  + "," + considerType + "," + majorTrade + "," +startTranDate + "," + endTranDate + "," + contractNo ;
        var url = '${base}/sys/xlsie/InsTraServiceExportCallbackImpl/' + data;
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
            title: '提供服务类信息导入',
            url: '${base}/sys/xlsie/import?bean=TraServiceImportCallbackImpl',
            onClose: function () {
                loadCallback();
            }
        });
    }

    //刷新表格
    function loadCallback() {
        $('#traServiceTable').jTable('refresh');
    }

    /*
    根据当前时间和随机数生成备案号
    * */
    function randomNumber() {
        var now = new Date();
        var year = now.getFullYear().toString();
        var month = now.getMonth() + 1;
        var day = now.getDate();
        var hour = now.getHours();
        var minutes = now.getMinutes();
        var seconds = now.getSeconds();
        var no = year+month+day+hour+minutes+seconds;
        return no+(Math.round(Math.random()*89+100)).toString();
    }



    //objData.filNum = randomNumber()+'.'+data.pid

    /*
	* 审批
	* */

    function submitHandler() {
        var status=getStatusSelections();
        var pid = getIdSelections();
        if(status == 5 ){
            $.messager.alert('错误', "审批退回数据请编辑后在重新提交！", 'error');
            return;
        }
        if (status == 3 || status == 9) {
            window.location = "${base}/ins/traService/submit/" + pid;
        }else {
            $.messager.alert('错误', "当前状态不符合要求！", 'error');
        }
    }
    // 获取关联方类型
    function getRelTypeSelections() {
        return $.map($('#traServiceTable').bootstrapTable('getSelections'), function (row) {
            return row.relType;
        });
    }


    function getrelIdSelections() {
        return $.map($('#traServiceTable').bootstrapTable('getSelections'), function (row) {
            return row.relId;
        });
    }
    function send() {
        // 提交审批  的必须是待提交状态
        var status = getStatusSelections();
        var pid = getIdSelections();
        var relType = getRelTypeSelections();
        var relId = getrelIdSelections();
        var tranType = getTranTypeSelections();
        var considerType = getConsiderTypeSelections();
        if (pid == '' || pid == null || pid.length != 1) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }
        $.ajax({
            type: 'GET',
            url: '${base}/ins/traService/createPerisExist?pid=' + pid,
            dataType: 'json',
            delay: 200,
            success: function (data) {
                if (data.success) {
                    if(status != 3){
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
                    $.mask.post('${base}/ins/traService/send/' + pid, function (data) {
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


    function jungleRelatedDate(relType, pid, tranType) {
        /**
         * 校验关联方数据是否填完整
         * @type {*|jQuery}
         */
        let flag = true;
        $.ajax({
            type: 'GET',
            url: '${base}/ins/traService/jungleRelatedDate?relType=' + relType + "&pid=" + pid +"&tranType="+tranType,
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

    /* 任务下发 */
    function sendCommand() {
        var tables = $('#traServiceTable').jTable('getCheckData');
        if (tables.length == 1) {
            $.messager.modal({
                winId: 'traSend',
                width: 700,
                height: 420,
                title: '选择机构',
                url: '${base}/ins/traService/sendCommand',
                args: {traId: tables[0].pid},
                onClose: function () {
                    $('#traServiceTable').jTable('clearCheckData');
                    $('#traServiceTable').jTable('refresh');
                }
            });
        } else {
            $.messager.toastr('提示', '请选择一条数据');
        }
    }


    /*
    * 重置
    * */
    function frmReset() {
        $("#queryForm")[0].reset();
    }

    /*
    * 编辑
    * */
    function editHandler() {
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
            url: '${base}/ins/traService/createPerisExist?pid=' + pid,
            dataType: 'json',
            delay: 200,
            success: function (data) {
                if (data.success) {
                    var status = getStatusSelections();
                    var considerType = getConsiderTypeSelections();
                    if (status == '2') {
                        $.messager.alert('提示', '带确认数据不可编辑', 'info');
                        return false;
                    }
                    if (status == '4') {
                        $.messager.alert('提示', '审批中的数据不可编辑', 'info');
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
                        debugger
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
                    window.location = '${base}/ins/traService/edit/' + pid;
                } else {
                    $.messager.alert('提示', "您没有权限!", 'error');
                    return;
                }
            }
        });
    }

    //确认按钮
    function toConfirm() {
        var pid = getIdSelections()
        var status = getStatusSelections()
        if (pid == '' || pid == null || pid.length != 1) {
            $.messager.alert('错误', "请选择一条数据", 'error');
        }
        if (status == 2) {
            window.location = '${base}/ins/traService/toConfirm/' + pid;
        }else {
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
            window.location = '${base}/ins/traService/sign/' + pid;
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
        return $.map($('#traServiceTable').bootstrapTable('getSelections'), function (row) {
            return row.transNum;
        });
    }

    function getIdSelections() {
        return $.map($('#traServiceTable').bootstrapTable('getSelections'), function (row) {
            return row.pid;//返回数据行中的id值
        });
    }

    function getStatusSelections() {
        return $.map($('#traServiceTable').bootstrapTable('getSelections'), function (row) {
            return row.status;//返回数据行中的id值
        });
    }

    function getdataResourceSelections() {
        return $.map($('#traServiceTable').bootstrapTable('getSelections'), function (row) {
            return row.dataSources;//返回数据行中的id值
        });
    }

    // 获取关联方类型
    function getTranTypeSelections() {
        return $.map($('#traServiceTable').bootstrapTable('getSelections'), function (row) {
            return row.tranType;
        });
    }

    /**获取选中的审议类型*/
    function getConsiderTypeSelections() {
        return $.map($('#traServiceTable').bootstrapTable('getSelections'), function (row) {
            return row.considerType;
        });
    }

    /**获取选中的实际交易金额*/
    function getPracTradeAmountSelections() {
        return $.map($('#traServiceTable').bootstrapTable('getSelections'), function (row) {
            return row.pracTradeAmount;
        });
    }

    function removeHandler() {
        var pid = getIdSelections();
        var status = getStatusSelections();
        var considerType = getConsiderTypeSelections();
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
            url: '${base}/ins/traService/createPerisExist?pid=' + pid,
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
                                url: "${base}/ins/traService/remove/" + pid,
                                method: "GET",
                                data: {"": ""},
                                dataType: "JSON",
                                traditional: true,
                                success: function (data) {
                                    if (data.success) {
                                        $.messager.alert('提示', data.msg, 'info', function () {
                                            window.location = "${base}/ins/traService/list";
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
    // 跳转到集中审议列表
    function batchSend(){
        window.location='${base}/ins/traService/batchSendList'
    }

    //日期插件初始化
    $('#startTranDate').datetimepicker({
        language:  'zh-CN',
        format:'yyyy-mm-dd',
        autoclose: true,
        minView: "month"
    }).on("changeDate",function(ev){  //值改变事件
        //选择的日期不能大于第二个日期控件的日期
        if(ev.date){
            $("#endTranDate").datetimepicker('setStartDate', new Date(ev.date.valueOf()));
        }else{
            $("#endTranDate").datetimepicker('setStartDate',null);
        }
    });
    $('#endTranDate').datetimepicker({
        language:  'zh-CN',
        format:'yyyy-mm-dd',
        minView: "month",
        autoclose: true,

    }).on("changeDate",function(ev){
        //选择的日期不能小于第一个日期控件的日期
        if(ev.date){
            $("#startTranDate").datetimepicker('setEndDate', new Date(ev.date.valueOf()));
        }else{
            $("#startTranDate").datetimepicker('setEndDate',new Date());
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
            window.location = '${base}/ins/traService/attachmentAdd/' + pid;
        } else if ('9' != status) {
            $.messager.alert('错误', "该笔交易已经被驳回，请勿上传附件！", 'error');
            return;
        }
    }

</script>

</body>
</html>
