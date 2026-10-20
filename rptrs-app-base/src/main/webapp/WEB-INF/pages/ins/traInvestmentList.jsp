<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>关联交易表（保险）-投资入股类</title>
    <style type="text/css">
        #traInvestmentTable_tb_add, #traInvestmentTable_tb_edit,
        #traInvestmentTable_tb_remove, #traInvestmentTable_tb_import,
        #traInvestmentTable_tb_issued, #traInvestmentTable_tb_downTmp,
        #traInvestmentTable_tb_approve, #traInvestmentTable_tb_export, #traInvestmentTable_tb_ActualTra,
        #traInvestmentTable_tb_confirm,#traInvestmentTable_tb_confirm1
        {
            display: none;

        }
    </style>
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
                            <bf:text name="__LIKES_relNumber" col="7" placeholder="关联方证件号码"/>
                        </bf:formGroup>
                        <bf:formGroup col="4">
                            <bc:label text="关联方名称" col="5" formControl="true"/>
                            <bf:text name="__LIKES_relName" col="7" placeholder="关联方名称"/>
                        </bf:formGroup>
                        <bf:formGroup col="4">
                            <bc:label text="业务类型" col="5" formControl="true"/>
                            <bf:select name="__LIKES_investmentType" col="7" dataSource="custom"
                                       url="sys/dictItem/getDictItems/INS-InvestmentType">
                                <bf:option text="请选择" value=""></bf:option>
                            </bf:select>
                        </bf:formGroup>
                    </bf:formGroup>
                    <bf:formGroup hrp="bottom">

                        <bf:formGroup hrp="bottom">
                            <bf:formGroup col="4">
                                <bc:label text="交易状态" col="5" formControl="true"/>
                                <bf:select name="__EQS_status" col="7" dataSource="custom"
                                           url="sys/dictItem/getDictItems/BUS-Applystatus">
                                    <bf:option text="请选择" value=""></bf:option>
                                </bf:select>
                            </bf:formGroup>
                            <bf:formGroup col="4">
                                <bc:label text="审议类型" col="5" formControl="true"/>
                                <bf:select name="__EQS_considerType" col="7" dataSource="custom"
                                           url="sys/dictItem/getDictItems/INS-considerType" >
                                    <bf:option text="请选择" value=""></bf:option>
                                    <%--                                value="${unitCode}" disabled="${opt == 'MH'}"--%>
                                </bf:select>
                            </bf:formGroup>
                            <bf:formGroup col="4">
                                <bc:label text="是否重大关联交易" col="5" formControl="true"/>
                                <bf:select name="__EQS_majorTrade" col="7" dataSource="fixedDict" url="YesOrNoForRelTraDict">
                                    <bf:option text="请选择" value=""></bf:option>
                                </bf:select>
                            </bf:formGroup>



                            <bf:formGroup col="6">
                                <bc:label text="合同签署日期" col="3" formControl="true"/>
                                <bf:datebox id="startTranDate" name="__GES_startTranDate" col="3">
                                </bf:datebox>
                                <div style="float: left ;margin-top:8px">
                                    ----
                                </div>
                                <bf:datebox id="endTranDate" name="__LES_endTranDate" col="3">
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
            <bc:table id="traInvestmentTable" idField="pid" url="${base}/ins/traInvestment"  selectFirst="true">
                <bc:toolbar id="add" text="新增" type="button"/>
                <bc:toolbar id="edit" text="编辑" type="button" handler="toEdit"/>
                <bc:toolbar id="remove" text="删除" type="button" handler="remove"/>
<%--                <bc:toolbar id="downTmp" text="模板下载" type="button" handler="downloadHandler"/>--%>
<%--                <bc:toolbar id="import" text="导入" type="button" handler="importHandler"/>--%>
                <bc:toolbar id="export" text="导出" type="button" handler="out"/>

<%--                <bc:toolbar id="issued" text="任务下发" type="button" handler="sendCommand"/>--%>

                <bc:toolbar id="approve" text="提交审批" type="button" handler="send"/>

                <bc:toolbar id="confirm" text="确认" type="button" handler="toConfirm"/>
                <bc:toolbar id="confirm1" text="签订" type="button" handler="sign"/>
                <bc:column field="transNum" title="交易流水号" width="100"/>
               <%-- <bc:column field="relType" title="关联方类型" width="100"/>--%>
                <bc:column field="relName" title="关联方名称" width="100"/>
<%--                <bc:column field="investmentType" title="证件类型" width="100" formatter="custom:sys/dictItem/getDictItems/INS-certType"/>--%>
                <bc:column field="relNumber" title="证件号码" width="100" />
                <bc:column field="investmentType" title="业务类型" width="100" formatter="custom:sys/dictItem/getDictItems/INS-InvestmentType"/>
                <bc:column field="majorTrade" title="是否重大关联交易" width="100" formatter="fixedDict:IsStatusDict"/>
                <bc:column field="relTradeAmount" title="关联交易金额（万元）" width="100"/>
                <bc:column field="creater" title="创建人" width="100"/>
                <bc:column field="tranDate" title="合同签署日期" width="100"/>
                <bc:column field="pracTradeAmount" title="实际交易金额（万元）" width="100" visible="false"/>
                <bc:column field="considerType" title="审议类型" width="100" formatter="custom:sys/dictItem/getDictItems/INS-considerType"/>
                <bc:column field="status" title="交易状态" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-Applystatus"/>
<%--                <bc:column field="statusComment" title="操作状态说明" width="100"/>--%>
                <bc:operator id="view" icon="eye" title="操作"/>
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
            $('#traInvestmentTable_tb_add').show();
        }
        if (code.search("02,") != -1) {
            $('#traInvestmentTable_tb_edit').show();
        }
        if (code.search("03,") != -1) {
            $('#traInvestmentTable_tb_remove').show();
        }
        if (code.search("04,") != -1) {
            $('#traInvestmentTable_tb_import').show();
        }
        if (code.search("05,") != -1) {
            $('#traInvestmentTable_tb_issued').show();
        }
        if (code.search("06,") != -1) {
            $('#traInvestmentTable_tb_downTmp').show();
        }
        if (code.search("07,") != -1) {
            $('#traInvestmentTable_tb_approve').show();
        }
        if (code.search("08,") != -1) {
            $('#traInvestmentTable_tb_export').show();
        }
        if (code.search("034,") != -1) {
            $('#traInvestmentTable_tb_ActualTra').show();
        }
        if (code.search("035,") != -1) {
            $('#traInvestmentTable_tb_confirm').show();
        }
        if (code.search("040") != -1) {
            $('#traInvestmentTable_tb_confirm1').show();
        }

    })


     // 跳转到集中审议列表
    function batchSend(){
        window.location='${base}/ins/traInvestment/batchSendList'
    }
    //模块下载
    function downloadHandler() {
        var data = "1"
        var url = '${base}/sys/xlsie/InsTraInvestmentOutCallbackImpl/' + data;
        var elemIF = document.createElement("iframe");
        elemIF.src = url;
        elemIF.style.display = "none";
        document.body.appendChild(elemIF);
    }


    function  sensitiveField(field, before, after ) {
        if (!field) {
            return '';
        }
        field = String(field);

        // 匹配中文、英文、数字
        const regItem = '[\u4e00-\u9fa5a-zA-Z0-9]';
        const regExp = `(${regItem}{${before}})${regItem}*(${regItem}{${after}})`;
        const reg = new RegExp(regExp);

        return field.replace(reg, '$1*****$2');
    }


    //导出
    function out() {
        var relName = $("[name='__LIKES_relName']").val() == "" ? "%25" : $("[name='__LIKES_relName']").val();
        var relNumber = $("[name='__LIKES_relNumber']").val() == "" ? "%25" : $("[name='__LIKES_relNumber']").val();
        var investmentType = $("[name='__LIKES_investmentType']").val() == "" ? "%25" : $("[name='__LIKES_investmentType']").val();
        var status = $("[name='__EQS_status']").val() == "" ? "%25" : $("[name='__EQS_status']").val();
        var considerType = $("[name='__EQS_considerType']").val() == "" ? "%25" : $("[name='__EQS_considerType']").val();
        var majorTrade = $("[name='__EQS_majorTrade']").val() == "" ? "%25" : $("[name='__EQS_majorTrade']").val();
        var startTranDate = $("[name='__GES_startTranDate']").val() == "" ? "%25" : $("[name='__GES_startTranDate']").val();
        var endTranDate = $("[name='__LES_endTranDate']").val() == "" ? "%25" : $("[name='__LES_endTranDate']").val();



        var type = "check";
        var data = type + "," + relName + "," + investmentType + "," + relNumber
              + "," + status  + "," + considerType + "," + majorTrade + "," +startTranDate + "," + endTranDate ;
        var url = '${base}/sys/xlsie/InsTraInvestmentExportCallbackImpl/' + data;
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
            title: '投资入股类信息导入',
            url: '${base}/sys/xlsie/import?bean=InsTraInvestmentImportCallbackImpl',
            onClose: function () {
                loadCallback();
            }
        });
    }

    //刷新表格
    function loadCallback() {
        $('#traInvestmentTable').jTable('refresh');
    }

    //重置
    function frmReset() {
        window.location = '${base}/ins/traInvestment/list';
    }

    //根据当前时间和随机数生成备案号
    function randomNumber() {
        var now = new Date();
        var year = now.getFullYear().toString();
        var month = now.getMonth() + 1;
        var day = now.getDate();
        var hour = now.getHours();
        var minutes = now.getMinutes();
        var seconds = now.getSeconds();
        var no = year + month + day + hour + minutes + seconds;
        return no + (Math.round(Math.random() * 89 + 100)).toString();
    }


    //审批
    function send() {
        // 提交审批  的必须是待提交状态
        var status = getStatusSelections();
        var pid = getIdSelections();
        if (pid == '' || pid == null || pid.length != 1) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }
        if(status == 5 ){
            $.messager.alert('错误', "审批退回数据请编辑后在重新提交！", 'error');
            return;
        }

        if(status != 3){
            $.messager.alert('错误', "提交数据状态不符，无法提交！", 'error');
            return;
        }

        $.ajax({
            type: 'GET',
            url: '${base}/ins/traInvestment/createPerisExist?pid=' + pid,
            dataType: 'json',
            delay: 200,
            success: function (data) {
                if (data.success) {
                    //提交
                    $.mask.post('${base}/ins/traInvestment/send/' + pid, function (data) {
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

    /* 任务下发 */
    function sendCommand() {
        var tables = $('#traInvestmentTable').jTable('getCheckData');
        if (tables.length == 1) {
            $.messager.modal({
                winId: 'traSend',
                width: 700,
                height: 420,
                title: '选择机构',
                url: '${base}/ins/traInvestment/sendCommand',
                args: {traId: tables[0].pid},
                onClose: function () {
                    $('#traInvestmentTable').jTable('clearCheckData');
                    $('#traInvestmentTable').jTable('refresh');
                }
            });
        } else {
            $.messager.toastr('提示', '请选择一条数据');
        }
    }

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
            url: '${base}/ins/traInvestment/createPerisExist?pid=' + pid,
            dataType: 'json',
            delay: 200,
            success: function (data) {
                if (data.success) {
                    var status = getStatusSelections();
                    if (status == '4') {
                        $.messager.alert('提示', '审批中数据不可编辑', 'info');
                        return false;
                    }
                    // if (status == '6') {
                    //     $.messager.alert('提示', '已归档数据不可编辑', 'info');
                    //     return false;
                    // }
                    if (status == '2') {
                        $.messager.alert('提示', '待确认数据不可编辑', 'info');
                        return false;
                    }
                    window.location = '${base}/ins/traInvestment/edit/' + pid;
                } else {
                    $.messager.alert('提示', "您没有权限!", 'error');
                    return;
                }
            }
        });

    }

    function remove() {
        var pid = getIdSelections();
        var status = getStatusSelections();
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
            url: '${base}/ins/traInvestment/createPerisExist?pid=' + pid,
            dataType: 'json',
            delay: 200,
            success: function (data) {
                if (data.success) {
                    var row = getRowSelections();
                    if (status == '4'  || status == '2' || status == '6') {
                        $.messager.alert('提示', '审批中、待确认、已归档的交易数据不允许删除', 'info');
                        return false;
                    }
                    $.messager.confirm('提示', '确定要删除吗？', function (r) {
                        if (r) {
                            $.ajax({
                                url: "${base}/ins/traInvestment/remove/" + pid,
                                method: "GET",
                                dataType: "JSON",
                                traditional: true,
                                success: function (data) {
                                    if (data.success) {
                                        window.location = '${base}/ins/traInvestment/list/';
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

    //确认按钮
    function toConfirm() {
        var pid = getIdSelections()
        var status = getStatusSelections()
        if (!pid.length>0) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }
        if (status == 2) {
            window.location = '${base}/ins/traInvestment/toConfirm/' + pid;
        } else {
            $.messager.alert('错误', "当前状态不符合要求", 'error');
            return;
        }
    }
// 签订
    function sign() {
        var pid = getIdSelections()
        var status = getStatusSelections()
        if (!pid.length>0) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }
        if (status == 8) {
            window.location = '${base}/ins/traInvestment/sign/' + pid;
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
        return $.map($('#traInvestmentTable').bootstrapTable('getSelections'), function (row) {
            return row.transNum;
        });
    }

    function getRowSelections() {
        return $.map($('#traInvestmentTable').bootstrapTable('getSelections'), function (row) {
            return row;
        });
    }

    function getStatusSelections() {
        return $.map($('#traInvestmentTable').bootstrapTable('getSelections'), function (row) {
            return row.status;
        });
    }

    function getIdSelections() {
        return $.map($('#traInvestmentTable').bootstrapTable('getSelections'), function (row) {
            return row.pid;
        });
    }

    /**获取选中的审议类型*/
    function getConsiderTypeSelections() {
        return $.map($('#traInvestmentTable').bootstrapTable('getSelections'), function (row) {
            return row.considerType;
        });
    }

    /**获取选中的实际交易金额*/
    function getPracTradeAmountSelections() {
        return $.map($('#traInvestmentTable').bootstrapTable('getSelections'), function (row) {
            return row.pracTradeAmount;
        });
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

</script>
</body>
</html>