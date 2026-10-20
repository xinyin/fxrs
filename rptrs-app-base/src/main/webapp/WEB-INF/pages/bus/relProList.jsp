<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <link rel="stylesheet" href="/static/css/dialog.css">
    <script src="/static/js/jquery.dialog.js"></script>
    <br:css pack="list"/>
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
        .black_overlay {
            display: none;
            position: absolute;
            top: 0%;
            left: 0%;
            width: 100%;
            height: 100%;
            background-color: black;
            z-index: 1001;
            -moz-opacity: 0.8;
            opacity: .80;
            filter: alpha(opacity=88);
        }
        .white_content {
            display: none;
            position: absolute;
            top: 25%;
            left: 25%;
            width: 45%;
            height: 25%;
            padding: 20px;
            border: 10px solid orange;
            background-color: white;
            z-index: 1002;
            overflow: auto;
        }
        .BOX {
            display: flex;
            justify-content: space-around;
            width: 100%;
            margin: 1% auto;
        }
        .dialog-confirm {
            width: 100% !important;
        }
        .boxchild {
            width: 33%;
            display: flex;
        }
        .BOX .frm-label {
            width: 7em !important;
        }
        .dialog-section {
            display: block;
            left: 50%;
            position: fixed;
            margin-left: -438px !important;
            margin-top: -144px !important;
            opacity: 1;
            width: 80% !important;
        }
        #relProTable_tb_add, #relProTable_tb_edit,
        #relProTable_tb_remove, #relProTable_tb_importSimple,
        #relProTable_tb_exportHandler, #relProTable_tb_send,
        #relProTable_tb_export, #relProTable_tb_outTemplate {
            display: none;

        }
        #queryId {
            display: none;
        }
        #advancedQuery_id {
            height: 30px;
            line-height: 30px;
            width: 100px;
            overflow: hidden;
            cursor: pointer;
        }
    </style>
    <script type="text/javascript">
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
            var no = year + month + day + hour + minutes + seconds;
            return no + (Math.round(Math.random() * 89 + 100)).toString();
        }

        var nextApprovalPeople;

        //选择下一节点审批人
        function Approved() {
            debugger
            var pid12 = getIdSelections();
            if (pid12 == '' || pid12 == null) {
                $.messager.alert('错误', "请选择一条数据", 'error');
                return;
            }
            if (isCreatePer(pid12) == "false") {
                return;
            }

            var status = getStatusSelections();
            if (status == '3') {

                $.messager.modal({
                    winId: 'Approved',
                    width: 1200,
                    height: 420,
                    title: '选择下一节点审批人',
                    url: '${base}/ins/traInvestment/Approved?mult=true',
                    args: {
                        result: function (obj) {
                            nextApprovalPeople = obj["staffCode"];
                            // alert(nextApprovalPeople)
                            send();
                        }
                    },
                });
            } else {
                $.messager.alert('提示', "当前状态不符合要求！", 'info');
            }

        }


        /*
      * 审批
      * */
        function send() {

            // 提交审批  的必须是待提交状态
            var status = getStatusSelections();
            var pid = getIdSelections();
            if (pid == '' || pid == null ) {
                $.messager.alert('错误', "请选择一条数据", 'error');
                return;
            }

            let statusArr = status.filter(v => { return '3' === v })
            console.log(statusArr)
            if (!statusArr || statusArr.length < pid.length) {
                $.messager.alert('提示', '请选择审批状态为"待提交"的数据。', 'info');
                return;
            }
            /*if (isCreatePer(pid) == "false") {
                return;
            }*/
            if(pid.length ==1){
                //查询出基本信息 修改状态 放到data中
                $.ajax({
                    url: "${base}/bus/relPro/send/" + pid,
                    method: "GET",
                    data: {"": ""},
                    dataType: "JSON",
                    traditional: true,
                    success: function (data) {
                        //ZZ_PRO001
                        if (data.success) {
                            //开始走工作流
                            var objData = data.data;
                            var myDate = new Date();
                            objData.id = myDate.getTime();
                            //获取备案号
                            // var filNum = randomNumber();
                            // objData.filNum = filNum;
                            data = JSON.stringify(objData);

                            var formData = $.helper.base64Encode(data);
                            //挂取工作流
                            var obj = {
                                procDefCode: 'ZZ_Pro001',
                                oldData: formData,
                                newData: formData,
                                uploadSessions: '',
                                procInstId: ''
                            }

                            $.mask.post('${base}/workflow/process/saveAndStart', obj, function (data) {
                                if (data.success) {
                                    $.messager.alert('提示', '提交成功。', 'info', function () {
                                        window.location = '${base}/bus/relPro/list';
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
            }else {
                //批量提交,拼接pid
                var pidStr = "";
                for(i=0;i<pid.length;i++){
                    pidStr += pid[i] + ","
                }
                console.log(pidStr)
                $.ajax({
                    url: "${base}/bus/relPro/batchSend/" + pidStr,
                    method: "GET",
                    data: {"": ""},
                    dataType: "JSON",
                    traditional: true,
                    success: function (data) {
                        //ZZ_PRO001
                        if (data.success) {
                            //开始走工作流
                            var objData = data.data;
                            var myDate = new Date();
                            objData.id = myDate.getTime();
                            //获取备案号
                            // var filNum = randomNumber();
                            // objData.filNum = filNum;
                            data = JSON.stringify(objData);

                            var formData = $.helper.base64Encode(data);
                            //挂取工作流
                            var obj = {
                                procDefCode: 'ZZ_Pro001',
                                oldData: formData,
                                newData: formData,
                                uploadSessions: '',
                                procInstId: ''
                            }

                            $.mask.post('${base}/workflow/process/saveAndStart', obj, function (data) {
                                if (data.success) {
                                    $.messager.alert('提示', '提交成功。', 'info', function () {
                                        window.location = '${base}/bus/relPro/list';
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
            }


        }

        function isCreatePer(pid) {
            var boo = "true";
            debugger
            $.ajax({
                type: 'GET',
                url: '${base}/bus/relPro/createPerisExist?pid=' + pid,
                dataType: 'json',
                delay: 200,
                success: function (data) {
                    if (data.success) {
                        return boo;
                    } else {
                        $.messager.alert('提示', "您没有权限!", 'error');
                        boo = "false";
                        return boo;
                    }
                }
            });
        }

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
                url: '${base}/bus/relPro/createPerisExist?pid=' + pid,
                dataType: 'json',
                delay: 200,
                success: function (data) {
                    if (data.success) {
                        if (pid != "") {
                            var status = getStatusSelections();
                            if (status != 4) {
                                window.location = '${base}/bus/relPro/edit/' + pid + '?op=0';
                            } else {
                                $.messager.alert('提示', '审批中，不可编辑！。', 'error');
                                return;
                                <%--window.location = '${base}/bus/relPro/edit/' + pid+'?op=1';--%>
                            }

                        } else {
                            $.messager.alert('提示', '请选择金融产品。', 'info');
                            return "";
                        }
                    } else {
                        $.messager.alert('提示', "您没有权限!", 'error');

                        return;
                    }
                }
            });
            // if (isCreatePer(pid) == "false"){
            //     return ;
            // }
            // alert(pid)

        }

        function importHandler() {
            $.messager.modal({
                winId: 'wImportFromExcel', //winid只能为 wImportFromExcel
                width: 500,
                height: 230,
                args: {
                    callback: loadCallback
                },
                title: '金融产品信息导入',
                url: '${base}/sys/xlsie/import?bean=ProImportCallbackImpl',
                onClose: function () {
                    loadCallback();
                }
            });

            //刷新表格
            function loadCallback() {
                $('#relProTable').jTable('refresh');
            }
        }

        function getStatusSelections() {
            return $.map($('#relProTable').bootstrapTable('getSelections'), function (row) {
                return row.approvalStatus;
            });
        }

        function getIdSelections() {
            return $.map($('#relProTable').bootstrapTable('getSelections'), function (row) {
                return row.pid;
            });
        }

        function getEffectRelationSelections() {
            return $.map($('#relProTable').bootstrapTable('getSelections'), function (row) {
                return row.effectRelation;
            });
        }

        function removeHandler() {
            var pid = getIdSelections();
            var status = getStatusSelections();
            var effectRelation = getEffectRelationSelections();
            if (pid == '' || pid == null) {
                $.messager.alert('错误', "请选择一条数据", 'error');
                return;
            }
            if (pid.length > 1) {
                $.messager.alert('错误', "不能选择多条数据进行删除操作，请重新勾选", 'error');
                return;
            }
            if (effectRelation == "1" && status == "6") {
                $.messager.alert('错误', "操作状态不符，无法删除", 'error');
                return;
            }
            $.messager.confirm('提示', '确定要删除吗？', function (r) {
                if (r) {
                    $.ajax({
                        url: "${base}/bus/relPro/remove/" + pid,
                        method: "GET",
                        data: {"": ""},
                        dataType: "JSON",
                        traditional: true,
                        success: function (data) {
                            if (data.success) {
                                $.messager.alert('提示', data.msg, 'info', function () {
                                    window.location = "${base}/bus/relPro/list"
                                })
                            } else {
                                $.messager.alert('错误', data.msg, 'error');
                            }
                        }
                    })
                    /*} else {
                        $.messager.alert('错误', data.msg, 'error');
                    }
                }
            })*/
                }
            })
        }

        //模板下载
        function outTemplate() {
            var data = "1"
            // alert(data);
            var url = '${base}/sys/xlsie/ProoutExportCallbackImpl/' + data;
            var elemIF = document.createElement("iframe");
            elemIF.src = url;
            elemIF.style.display = "none";
            document.body.appendChild(elemIF);
        }

        //数据导出
        function exportHandler() {
            var proName = $("[name='__LIKES_proName']").val() == "" ? "%25" : $("[name='__LIKES_proName']").val();
            var proType = $("[name='__LIKES_proType']").val() == "%" ? "%25" : $("[name='__LIKES_proType']").val();
            var proProvider = $("[name='__LIKES_proProvider']").val() == "" ? "%25" : $("[name='__LIKES_proProvider']").val();
            var effectRelation = $("[name='__LIKES_effectRelation']").val() == "%" ? "%25" : $("[name='__LIKES_effectRelation']").val();
            var gesmodifiedTime = $("[name='__LIKES_modifiedTime']").val() == "" ? "%25" : $("[name='__LIKES_modifiedTime']").val();
            var data = proName + "," + proType + ","+effectRelation+","+proProvider+","+gesmodifiedTime;
            // if(getselectDataId.length>0){
            //     data = dataId
            // }else {
            //     data = type + "," + proName + "," + proType + "," + proProvider;
            // }
            var url = '${base}/sys/xlsie/ProInfoExportCallbackImpl/' + data;
            var elemIF = document.createElement("iframe");
            elemIF.src = url;
            elemIF.style.display = "none";
            document.body.appendChild(elemIF);
        }


        function addOrRemoveBlacklist() {
            var pid = getIdSelections();
            var status = getStatusSelections();
            if (status != "6") {
                $.messager.alert('提示', '请选择已归档的关联方', 'info');
                return "";
            }
            if (pid != "") {
                window.location = '${base}/bus/relOrg/addOrRemoveBlack/' + pid + '?';
            } else {
                $.messager.alert('提示', '请选择自然人。', 'info');
                return "";
            }
        }

        function disconnection() {
            var pid = getIdSelections();
            if (pid != "") {
                window.location = '${base}/bus/relOrg/disconnection/' + pid + '?';
            } else {
                $.messager.alert('提示', '请选择机构。', 'info');
                return "";
            }
        }

        function upload() {
            var obj = document.getElementById("file");
            for (var i = 0; i < obj.files.length; i++) {
                var temp = obj.files[i].name;
                var temArr = temp.split('.');
                var fileLast = temArr[temArr.length - 1];
                alert(fileLast);
                if (fileLast != 'xls' && fileLast != 'xlsx') {
                    alert("请上传Excel文件");
                    return;
                } else {
                    var formData = new FormData($('#uploadForm')[0]);
                    //alert(formData);
                    $.ajax({
                        url: '${base}/bus/relPro/impor',
                        type: 'post',
                        data: formData,
                        cache: false,
                        processData: false,  // 不处理数据
                        contentType: false,
                        success: function (data) {
                            if (data.success) {
                                alert("导入成功！")
                                $("#close").click();
                            }
                        }
                    })
                }
            }

        }


        /* 任务下发 */
        function sendCommand() {
            var tables = $('#relProTable').jTable('getCheckData');

            /* var pid = getIdSelections();*/
            /* var orgId=tables[0].pid;*/
            if (tables.length == 1) {
                $.messager.modal({
                    winId: 'relProSend',
                    width: 700,
                    height: 420,
                    title: '选择机构',
                    url: '${base}/bus/relPro/relProSend',
                    args: {proId: tables[0].pid},
                    onClose: function () {
                        $('#relProTable').jTable('clearCheckData');
                        $('#relProTable').jTable('refresh');
                    }
                });
            } else {
                $.messager.toastr('提示', '请选择一条数据');
            }
        }

        //重置
        function frmReset() {
            window.location = '${base}/bus/relPro/list';
        }
    </script>
    <title>关联关系金融产品表</title>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="" subTitle="" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">
                    <bf:formGroup col="4">
                        <bc:label text="产品名称" col="5" formControl="true"/>
                        <bf:text name="__LIKES_proName" col="7" placeholder="金融产品全称"/>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="产品分类" col="5" formControl="true"/>
                        <bf:select name="__LIKES_proType" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-pro">
                            <bf:option text="请选择" value="%"></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_proType");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="是否存在有效关系" col="5" formControl="true"/>
                        <bf:select name="__LIKES_effectRelation" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-EffectRelation">
                            <bf:option text="请选择" value="%"></bf:option>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="产品批准/注册/备案号" col="5" formControl="true"/>
                        <bf:text name="__LIKES_proProvider" col="7" placeholder="产品批准/注册/备案号"/>
                    </bf:formGroup>
<%--                    <center>--%>
<%--                        <div id="advancedQueryId">--%>
<%--                            <span id="advancedQuery_id" style="color: #02afff">高级查询▼</span>--%>
<%--                        </div>--%>
<%--                    </center>--%>

<%--                    <div id="queryId" class="queryClass">--%>
                       <%-- <bf:formGroup col="4">
                            <bc:label text="产品管理人全称" col="5" formControl="true"/>
                            <bf:text name="__LIKES_adName" col="7" placeholder="产品管理人全称"/>
                        </bf:formGroup>
                        <bf:formGroup col="4">
                            <bc:label text="管理人统一社会信用代码" col="5" formControl="true"/>
                            <bf:text name="__LIKES_adCreditCode" col="7" placeholder="管理人统一社会信用代码"/>
                        </bf:formGroup>
                        <bf:formGroup col="4">
                            <bc:label text="操作状态" col="5" formControl="true"/>
                            <bf:select name="__LIKES_approvalStatus" col="7" dataSource="custom"
                                       url="sys/dictItem/getDictItems/BUS-ApplySingleStatus">
                                <bf:option text="ALL" value=""></bf:option>
                            </bf:select>
                        </bf:formGroup>--%>
                        <bf:formGroup col="4">
                            <bc:label text="更新日期" col="5" formControl="true"/>
                                <bf:datebox id="__EQS_modifiedTime" name="__LIKES_modifiedTime" col="7"/>
                        </bf:formGroup>

<%--                    </div>--%>
                </bc:row>
                <bc:row cls="m-b">
                    <div class="col-sm-12 col-sm-offset-5">
                        <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                        <bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
                    </div>
                </bc:row>
            </bf:form>
            <bc:table id="relProTable" idField="pid" url="${base}/bus/relPro"  selectFirst="true">
                <bc:toolbar id="add" text="新增" type="button"/>
                <bc:toolbar id="edit" text="编辑" type="button" handler="editHandler"/>
                <bc:toolbar id="remove" text="删除" type="button" handler="removeHandler"/>
                <bc:toolbar id="importSimple" text="导入" type="button" handler="importHandler"/>
                <bc:toolbar id="outTemplate" text="模板下载" type="button" handler="outTemplate"/>
<%--                <bc:toolbar id="send" text="提交审批" handler="send" type="button"/>--%>
                <bc:toolbar id="export" text="导出" type="button" handler="exportHandler"/>
                <bc:column field="proName" title="产品名称" width="100"/>
                <bc:column field="proProvider" title="产品批准/注册/备案号" width="100"/>
                <bc:column field="proType" title="产品分类" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-pro"/>
                <bc:column field="adName" title="产品管理人全称" width="100"/>
                <bc:column field="adCreditCode" title="管理人统一社会信用代码" width="100"/>
               <%-- <bc:column field="mgerOrg" title="管户机构" width="100" formatter="custom:sys/unit/selectUnit"/>--%>
<%--                <bc:column field="approvalStatus" title="操作状态"--%>
<%--                           formatter="custom:sys/dictItem/getDictItems/BUS-ApplySingleStatus" width="100"/>--%>
                <bc:column field="effectRelation" title="是否存在有效关系"
                           formatter="custom:sys/dictItem/getDictItems/BUS-EffectRelation" width="100"/>
                <bc:column field="modifiedTime" title="更新日期" width="100"/>
                <bc:column field="amendName" title="操作人" width="100"/>
                <%--                <bc:column field="careName" title="托管人" width="100"/>--%>
                <%--                <bc:column field="adName" title="管理人" width="100" visible="false"/>--%>
                <%--                <bc:column field="amendName" title="操作人" width="100"/>--%>
                <%--<bc:column field="statusComment" title="操作状态说明" width="100"/>
                <bc:column field="modifiedTime" title="更新时间" width="100"/>--%>
                <bc:operator id="view" icon="eye" title="查看"/>
            </bc:table>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
    //高级查询
    window.onload = function () {
        var flag = true;
        document.getElementById("advancedQuery_id").onclick = function () {
            if (flag) {
                document.getElementById("queryId").style.display = "block";
                flag = false;
            } else {
                document.getElementById("queryId").style.display = "none";
                flag = true;
            }
        }
    }


    //根据权限显示按钮
    $(function () {
        var code = "${code }";
        //新增
        if (code.search("01,") != -1) {
            $('#relProTable_tb_add').show();
        }
        //编辑
        if (code.search("02,") != -1) {
            $('#relProTable_tb_edit').show();
        }
        //删除
        if (code.search("03,") != -1) {
            $('#relProTable_tb_remove').show();
        }
        //导入
        if (code.search("04,") != -1) {
            $('#relProTable_tb_importSimple').show();
        }
        //模板下载
        if (code.search("06,") != -1) {
            $('#relProTable_tb_outTemplate').show();
        }
        //提交审批
        if (code.search("07,") != -1) {
            $('#relProTable_tb_send').show();
        }
        //导出
        if (code.search("08,") != -1) {
            $('#relProTable_tb_export').show();
        }
    })

</script>
</body>
</html>