<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <style type="text/css">

        #filInterestTable_tb_add,#filInterestTable_tb_download,
        #filInterestTable_tb_remove,#filInterestTable_tb_import,
        #filInterestTable_tb_send,#filInterestTable_tb_export,
        #filInterestTable_tb_approve,#filInterestTable_tb_out{
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
    </style>
    <title>关联交易表（保险）-利益转移类</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="" subTitle="" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">
                    <bf:formGroup col="4">
                        <bc:label text="关联方名称" col="5" formControl="true"/>
                        <bf:text name="__LIKES_relName" col="7" placeholder="关联方名称"/>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="关联方类型" col="5" formControl="true"/>
                        <bf:select name="__LIKES_relType" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/INS-relType">
                            <bf:option text="ALL" value=""></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_relType");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="证件号码" col="5" formControl="true"/>
                        <bf:text name="__LIKES_relNumber" col="7" placeholder="证件号码"/>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="状态" col="5" formControl="true"/>
                        <bf:select name="__LIKES_status" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-Applystatus">
                            <bf:option text="ALL" value=""></bf:option>

                            <be:event type="select">
                                var input = $("#__LIKES_status");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="业务类型" col="5" formControl="true"/>
                        <bf:select name="__LIKES_busType" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/INT-BusinessType">
                            <bf:option text="ALL" value=""></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_busType");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="口径" col="5" formControl="true"/>
                        <bf:select name="__LIKES_caliber" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-Caliber" value="A">
                            <bf:option text="ALL" value=""></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_caliber");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="是否重大关联交易" col="5" formControl="true"/>
                        <bf:select name="__LIKES_majorTrade" col="7" dataSource="fixedDict"
                                   url="IsStatusDict">
                            <bf:option text="ALL" value=""></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_majorTrade");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="交易机构" col="5" formControl="true"/>
                        <bf:select name="__LIKES_tradeOrg" col="7" dataSource="custom"
                                   url="sys/unit/selectUnit">
                            <bf:option text="ALL" value=""></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_tradeOrg");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="数据来源" col="5" formControl="true"/>
                        <bf:select name="__LIKES_dataSources" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-DataSource">
                            <bf:option text="ALL" value="%"></bf:option>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="交易定价是否为国家规定" col="5" formControl="true"/>
                        <bf:select name="__LIKES_isDealPrice" col="7" dataSource="fixedDict" url="IsStatusDict">
                            <bf:option text="ALL" value="%"></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_isDealPrice");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="交易开始时间" col="5" formControl="true"/>
                        <bf:datebox id="startDate" name="__GES_tranDate" col="7" format="date"/>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="交易截止时间" col="5" formControl="true"/>
                        <bf:datebox id="endDate" name="__LTS_tranDate" col="7" format="date"/>
                    </bf:formGroup>

                    <bc:col col="12" colOffset="5">
                        <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                        <bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
                    </bc:col>
                </bc:row>
            </bf:form>

            <bc:table id="filInterestTable" idField="pid" selectFirst="false" multiple="false" url="${base}/ins/filInterest" queryUrl="queryPidsByConditions/${pids}">
                <bc:toolbar id="" text="查看" type="button" handler="viewFilInterest"/>
                <bc:toolbar id="compared" text="查看变更" handler="compared" type="button"/>
<%--                <bc:toolbar id="add" text="新增" type="button"/>--%>
<%--                <bc:toolbar id="download" text="编辑" type="button" handler="editHandler"/>--%>
<%--                <bc:toolbar id="remove" text="删除" type="button" handler="removeHandler"/>--%>
<%--                <bc:toolbar id="import" text="导入" type="button" handler="importHandler"/>--%>
<%--                <bc:toolbar id="send" text="任务下发" type="button" handler="sendCommand"/>--%>
<%--                <bc:toolbar id="export" text="模板下载" type="button" handler="exportHandler"/>--%>
<%--                <bc:toolbar id="approve" text="提交审批" type="button" handler="send"/>--%>
<%--                <bc:toolbar id="out" text="导出" type="button" handler="out"/>--%>


                <bc:column field="pid" title="主键" width="100" visible="false"/>
                <bc:column field="relId" title="关联方ID" width="100" visible="false"/>
                <bc:column field="filNum" title="备案编号" width="100"/>
                <bc:column field="relName" title="关联方名称" width="100"/>
                <bc:column field="tradeOrg" title="交易机构" width="100" formatter="custom:sys/unit/selectUnit"/>
                <bc:column field="relType" title="关联方类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/INS-relType"/>
                <bc:column field="relNumber" title="证件号码" width="100"/>
                <bc:column field="tranType" title="交易类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/INS-tranType"/>
                <bc:column field="busType" title="业务类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/INT-BusinessType"/>
                <bc:column field="tradeAmount" title="交易金额（万元）" width="100"/>
                <bc:column field="majorTrade" title="是否重大关联交易" width="100" formatter="fixedDict:IsStatusDict"/>
                <bc:column field="caliber" title="统计口径" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-Caliber"/>
                <bc:column field="dataSources" title="数据来源" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-DataSource"/>
                <bc:column field="isDealPrice" title="交易定价是否为国家规定" width="100" formatter="fixedDict:IsStatusDict"/>
                <bc:column field="status" title="状态" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-Applystatus"/>
                <bc:column field="statusComment" title="状态说明" width="100"/>
                <bc:column field="tranDateString" title="交易日期" width="100" visible="false"/>
                <bc:column field="totolTradeAmount" title="本年度与该关联方发生的关联交易累计金额（万元）" width="100" visible="false"/>
                <bc:column field="contractNo" title="合同编号" width="100" visible="false"/>
                <bc:column field="busStartTimeString" title="业务起始日期" width="100" visible="false"/>
                <bc:column field="busEndTimeString" title="业务终止日期" width="100" visible="false"/>
                <bc:column field="tradeOccur" title="交易发生原因及合同、证明文件等" width="100" visible="false"/>
                <bc:column field="tradePrice" title="交易价格" width="100" visible="false"/>
                <bc:column field="pricingPolicy" title="定价政策" width="100" visible="false"/>
                <bc:column field="tradeSubject" title="交易标的" width="100" visible="false"/>
                <bc:column field="remark" title="其他事项说明" width="100" visible="false"/>
                <bc:column field="transNum" title="交易编号" width="100" visible="false"/>
<%--                <bc:operator id="view" icon="search" title="查看"/>--%>
                <%--<bc:operator id="edit" icon="edit" title="编辑"/>--%>
                <%--<bc:operator id="remove" icon="trash" title="移除"/>--%>
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
        if(code.search("01") != -1){
            $('#filInterestTable_tb_add').show();
        }
        if(code.search("02") != -1){
            $('#filInterestTable_tb_download').show();
        }
        if (code.search("03") != -1){
            $('#filInterestTable_tb_remove').show();
        }
        if(code.search("04") != -1){
            $('#filInterestTable_tb_import').show();
        }
        if(code.search("05") != -1){
            $('#filInterestTable_tb_send').show();
        }
        if(code.search("06") != -1){
            $('#filInterestTable_tb_export').show();
        }
        if(code.search("07") != -1){
            $('#filInterestTable_tb_approve').show();
        }
        if(code.search("08") != -1){
            $('#filInterestTable_tb_out').show();
        }
    })
    /*获取当前数据id*/
    function getIdSelections() {
        return $.map($('#filInterestTable').bootstrapTable('getSelections'), function (row) {
            return row.pid;
        });
    }
    /*查看当前数据*/
    <%--function viewFilInterest() {--%>
    <%--    var pid = getIdSelections();--%>
    <%--    alert(pid)--%>
    <%--    window.location = '${base}/ins/filInterest/view/'+pid;--%>
    <%--}--%>
    /*查看当前数据并判断选中个数*/
    function viewFilInterest() {
        var pid = getIdSelections();
        if(pid.length==1){
            window.location = '${base}/ins/filInterest/view/'+pid;
        }else{
            $.messager.alert('提示', '请选择一个关联方。', 'info');
            return "";
        }
    }
    function compared() {
        var pid = getIdSelections();
        if(pid.length==1){
            window.location = '${base}/ins/filInterest/compared/' + pid+'?';
        }else {
            $.messager.alert('提示', '请选择一个关联方。', 'info');
            return "";
        }
    }

    /*获取当前数据状态*/
    function getStatusSelections() {
        return $.map($('#filInterestTable').bootstrapTable('getSelections'), function (row) {
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
        window.location = '${base}/ins/traInterest/edit/' + pid;
    }

    /*
    * 删除
    * */
    function removeHandler() {
        var pid = getIdSelections();
        if (pid == '' || pid == null || pid.length != 1) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }
        $.messager.confirm('提示', '确定要删除吗？', function (r) {
            if (r) {
                $.ajax({
                    url: "${base}/ins/traInterest/remove/" + pid,
                    method: "GET",
                    dataType: "JSON",
                    traditional: true,
                    success: function (data) {
                        if (data.success) {
                            window.location = '${base}/ins/traInterest/list/';
                        } else {
                            $.messager.alert('错误', data.msg, 'error');
                        }
                    }
                })
            }
        })
    }

    /*
    * 提交审批
    * */
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
        var tables = $('#filInterestTable').jTable('getCheckData');
        if (tables.length == 1) {
            $.messager.modal({
                winId: 'traInterestSend',
                width: 700,
                height: 420,
                title: '选择机构',
                url: '${base}/ins/traInterest/traInterestSend',
                args: {orgId: tables[0].pid},
                onClose: function () {
                    $('#filInterestTable').jTable('clearCheckData');
                    $('#filInterestTable').jTable('refresh');
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

        window.location = '${base}/ins/filInterest/list';
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

    //导出
    function out() {
        var relName = $("[name='__LIKES_relName']").val() == "" ? "%25" : $("[name='__LIKES_relName']").val();
        var relType = $("[name='__LIKES_relType']").val() == "%" ? "%25" : $("[name='__LIKES_relType']").val();
        var relNumber = $("[name='__LIKES_relNumber']").val() == "" ? "%25" : $("[name='__LIKES_relNumber']").val();
        var status = $("[name='__LIKES_status']").val() == "%" ? "%25" : $("[name='__LIKES_status']").val();
        var busType = $("[name='__LIKES_busType']").val() == "%" ? "%25" : $("[name='__LIKES_busType']").val();
        var caliber = $("[name='__LIKES_caliber']").val() == "%" ? "%25" : $("[name='__LIKES_caliber']").val();
        var majorTrade = $("[name='__LIKES_majorTrade']").val() == "%" ? "%25" : $("[name='__LIKES_majorTrade']").val();
        var tradeOrg = $("[name='__LIKES_tradeOrg']").val() == "%" ? "%25" : $("[name='__LIKES_tradeOrg']").val();
        var dataSources = $("[name='__LIKES_dataSources']").val() == "%" ? "%25" : $("[name='__LIKES_dataSources']").val();
        var isDealPrice = $("[name='__LIKES_isDealPrice']").val() == "%" ? "%25" : $("[name='__LIKES_isDealPrice']").val();
        var startTranDate = $("#startDate").val() == "" ? "%25" : $("#startDate").val();
        var endTranDate = $("#endDate").val() == "" ? "%25" : $("#endDate").val();
        var data = relName + "," + relType + "," + relNumber + "," + status + ","
            + busType + "," + caliber + "," + majorTrade + "," + tradeOrg + "," + dataSources
            + "," + startTranDate + "," + endTranDate+","+isDealPrice;
        var url = '${base}/sys/xlsie/InsTradeInterestExportCallbackImpl/' + data;
        var elemIF = document.createElement("iframe");
        elemIF.src = url;
        elemIF.style.display = "none";
        document.body.appendChild(elemIF);
    }

    function loadCallback() {
        $('#filInterestTable').jTable('refresh');
    }
</script>
</body>
</html>