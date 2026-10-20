
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <style type="text/css">
        #relPsnTable_tb_add, #relPsnTable_tb_edit,
        #relPsnTable_tb_remove, #relPsnTable_tb_incrementalImport,
        #relPsnTable_tb_sendCommand, #relPsnTable_tb_exportHandler,
        #relPsnTable_tb_send, #relPsnTable_tb_out, #relPsnTable_tb_addOrRemoveBlacklist,
        #relPsnTable_tb_affirm,#relPsnTable_tb_batchImport {
            display: none;

        }
    </style>
    <title>关联关系自然人</title>
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
        #queryId{
            display: none;
        }
        #advancedQuery_id{
            height:30px; line-height:30px; width:100px; overflow:hidden;cursor:pointer;
        }
    </style>
</head>
<body class="gray-bg" style="background: #ffffff;">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="" subTitle="" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">
                    <bf:formGroup col="4">
                        <bc:label text="姓名" col="5" formControl="true"/>
                        <bf:text name="__LIKES_name" col="7" placeholder=""/>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="证件类型" col="5" formControl="true"/>
                        <bf:select id="identifyType" name="__EQS_identifyType" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-IdentifyType">
                            <bf:option text="请选择" value=""></bf:option>
                            <be:event type="select">
                                var input = $("#__EQS_identifyType");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="证件号码" col="5" formControl="true"/>
                        <bf:text name="__LIKES_identifyNumber" col="7" placeholder="证件号码"/>
                    </bf:formGroup>
                    <center>
                        <div id="advancedQueryId">
                            <span id="advancedQuery_id" style="color: #02afff">高级查询▼</span>
                        </div>
                    </center>
                    <div id="queryId" class="queryClass">
                        <bf:formGroup col="4">
                            <bc:label text="是否存在有效关系" col="5" formControl="true"/>
                            <bf:select name="__EQS_effectRelation" col="7" dataSource="custom"
                                       url="sys/dictItem/getDictItems/BUS-EffectRelation">
                                <bf:option text="请选择" value=""></bf:option>
                            </bf:select>
                        </bf:formGroup>
                        <bf:formGroup col="4" type="success">
                            <bc:label text="更新时间" col="5" formControl="true"></bc:label>
                            <bf:datebox name="__LIKES_modifiedTime" id="modifiedTime" col="7" format="date"/>
                        </bf:formGroup>
                    </div>
                </bc:row>
                <bc:row cls="m-b">
                    <div class="col-sm-12 col-sm-offset-5">
                        <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                        <bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
                    </div>
                </bc:row>
            </bf:form>
            <bc:table id="relPsnTable" idField="pid" url="${base}/bus/relPsn" selectFirst="true"  multiple="true">
                <bc:toolbar id="add" text="新增" type="button"/>
                <bc:toolbar id="edit" text="编辑" type="button" handler="editRelPsn"/>
                <bc:toolbar id="remove" text="删除" type="button" handler="removeRelPsn"/>
                <bc:toolbar id="incrementalImport" text="增量导入" type="button" handler="incrementalImportHandler"/>
                <bc:toolbar id="batchImport" text="全量导入" type="button" handler="batchImportHandler"/>
                <bc:toolbar id="out" text="导出" type="button" handler="outHandler"/>
                <bc:toolbar id="exportHandler" text="模板下载" type="button" handler="exportHandler"/>
                <bc:column field="pid" title="自增长主键" width="100" visible="false"/>
<%--                 <bc:column field="relateNumber" title="关联方编号" width="100" visible="false"/>--%>
                <bc:column field="name" title="姓名" width="100"/>
                <bc:column field="identifyType" title="证件类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-IdentifyType"/>
                <bc:column field="identifyNumber" title="证件号码" width="100"/>
                <bc:column field="sex" title="性別" width="100" visible="false"
                           formatter="custom:sys/dictItem/getDictItems/BUS-Sex"/>
                <bc:column field="domesticOrForeign" title="境内/境外" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-DomesticOrForeign"/>
                <bc:column field="effectRelation" title="是否存在有效关系" formatter="custom:sys/dictItem/getDictItems/BUS-EffectRelation" width="100"/>
                <bc:column field="transactionDep" title="经办部门" width="100" visible="false"/>
                <bc:column field="operator" title="经办人" width="100" visible="false"/>
                <bc:column field="createByName" title="创建人" width="100" />
                <bc:column field="createTimeString" title="创建日期" width="100" />
                <bc:column field="modifiedTime" title="更新日期" width="100"/>
                <bc:column field="status" title="状态" width="100" formatter="custom:sys/dictItem/getDictItems/BUS-RelApplystatus" visible="false"/>
                <bc:operator id="view" icon="eye" title="操作/查看"/>
            </bc:table>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
    //高级查询
    window.onload = function(){
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

    //根据权限显示按钮
    $(function () {
        var code = "${code}";
        //新增
        if (code.search("01,") != -1) {
            $('#relPsnTable_tb_add').show();
        }
        //编辑
        if (code.search("02,") != -1) {
            $('#relPsnTable_tb_edit').show();
        }
        //删除
        if (code.search("03,") != -1) {
            $('#relPsnTable_tb_remove').show();
        }
        //导入
        if (code.search("04,") != -1) {
            $('#relPsnTable_tb_incrementalImport').show();
            $('#relPsnTable_tb_batchImport').show();
        }
        //模板下载
        if (code.search("06,") != -1) {
            $('#relPsnTable_tb_exportHandler').show();
        }
        //导出
        if (code.search("08,") != -1) {
            $('#relPsnTable_tb_out').show();
        }
    })

    function editRelPsn() {
        var pid = getIdSelections();
        if (pid == '' || pid == null) {
            $.messager.alert('错误', "请选择一条数据进行编辑！", 'error');
            return;
        }
        if (pid.length > 1) {
            $.messager.alert('提示', "请选择一条数据进行编辑！", 'info');
            return;
        }
        var status = getStatusSelections();
        if (status != 4) {
            window.location = '${base}/bus/relPsn/edit/' + pid;
        } else {
            $.messager.alert('错误', "审批中，无法编辑", 'error');
            return;
        }
    }

    /*获取状态*/
    function getStatusSelections() {
        return $.map($('#relPsnTable').bootstrapTable('getSelections'), function (row) {
            return row.status;
        });
    }

    //获取是否有效
    function getEffectRelationSelections() {
        return $.map($('#relPsnTable').bootstrapTable('getSelections'), function (row) {
            return row.effectRelation;
        });
    }

    function getMgerOrgSelections() {
        return $.map($('#relPsnTable').bootstrapTable('getSelections'), function (row) {
            return row.mgerOrg;
        });
    }

    /*获取pid*/
    function getIdSelections() {
        return $.map($('#relPsnTable').bootstrapTable('getSelections'), function (row) {
            return row.pid;
        });
    }

    /*删除*/
    function removeRelPsn() {
        var pids = getIdSelections();
        // var status = getStatusSelections();
        var effectRelation = getEffectRelationSelections();
        var merOrgHOme = getMgerOrgSelections();
        var patt1 = new RegExp("Delegation");
        // alert(!patt1.test(merOrgHOme))
        if (pids.length >= 1) {
            for (let i = 0; i < pids.length; i++) {

                if (!patt1.test(merOrgHOme[i])) {
                    if (effectRelation[i] != "0") {
                        $.messager.alert('错误', "已选中的关联方存在有效关系，无法删除！", 'error');
                        return;
                    }
                }
            }

            $.messager.confirm('提示', '确定要删除吗？', function (r) {
                if (r) {
                    $.ajax({
                        url: "${base}/bus/rel/queryRel/" + pids + "/PSN",
                        method: "GET",
                        dataType: 'json',
                        traditional: true,
                        success: function (data) {
                            if (data.success) {
                                $.ajax({
                                    url: "${base}/bus/relPsn/remove/" + pids,
                                    method: "GET",
                                    data: {"": ""},
                                    dataType: "JSON",
                                    traditional: true,
                                    success: function (data) {
                                        if (data.success) {
                                            $.messager.alert('提示', data.msg, 'info', function () {
                                                window.location = "${base}/bus/relPsn/list"
                                            })
                                        } else {
                                            $.messager.alert('错误', data.msg, 'error');
                                        }
                                    }
                                })
                            } else {
                                $.messager.alert('错误', data.msg, 'error');
                                return;
                            }
                        }
                    })
                }
            })

        } else {
            $.messager.alert('错误', "请至少选择一条数据进行数据删除！", 'error');
            return;
        }
    }

    //模块下载
    function exportHandler() {
        var data = "1"
        var url = '${base}/sys/xlsie/PsnoutExportCallbackImpl/' + data;
        var elemIF = document.createElement("iframe");
        elemIF.src = url;
        elemIF.style.display = "none";
        document.body.appendChild(elemIF);
    }

    // 增量导入
    function incrementalImportHandler() {
        $.messager.modal({
            winId: 'wImportFromExcel', //winid只能为 wImportFromExcel
            width: 500,
            height: 230,
            args: {
                callback: loadCallback
            },
            title: '自然人信息导入',
            url: '${base}/sys/xlsie/import?bean=PsnImportCallbackImpl',
            onClose: function () {
                loadCallback();
            }
        });
    }

    // 批量导入  PsnBatchImportCallbackImpl
    function batchImportHandler() {
        $.ajax({
            url: "${base}/bus/relPsn/recordNumber" ,  //待修改
            method: "POST",
            data: {"": ""},
            dataType: "JSON",
            traditional: true,
            success: function (data) {
                if (data.success) {
                    var recordNumber = data.recordNumber;
                    var smallRecordNumber = data.smallRecordNumber;
                    var newRecordNumber = data.newRecordNumber;
                    if(data.version){
                        // $.messager.confirm('提示', '数据未存在版本，是否新增版本号：'+data.recordNumber +"?", function (d) {
                        //     if(d){
                                newVersionImport(recordNumber);
                        //     }
                        // })
                    }else {
                        if(data.newVersion){
                            $.messager.yesOrNo('提示', '系统中已有'+recordNumber +'版本数据，是否延用版本号，生成'+smallRecordNumber+'版本数据?', function (d) {
                                if(d){
                                    newVersionImport(smallRecordNumber);
                                }
                            })
                        }else {
                            $.messager.yesOrNo('提示', '执行全量导入后，是否新增版本号'+ newRecordNumber +"，自然人关联方信息将全部替换，是否操作？", function (d) {
                                if(d){
                                    newVersionImport(newRecordNumber);
                                }else {
                                    $.messager.yesOrNo('提示', '系统中已有'+recordNumber +'版本数据，是否延用版本号，生成'+smallRecordNumber+'版本数据?', function (d) {
                                        if(d){
                                            oldVersionImport(smallRecordNumber);
                                        }
                                    })
                                }
                            })
                        }
                    }
                } else {
                    $.messager.alert('错误', data.msg, 'error');
                }
            }
        })

        // $.messager.confirm('提示', '执行全量导入后，自然人关联方信息将全部替换，是否操作？', function (r) {
        //     if (r) {
        //     }
        // })
    }

    function newVersionImport(recordNumber) {
        $.messager.modal({
            winId: 'wImportFromExcel',
            width: 500,
            height: 230,
            args: {
                callback: loadCallback
            },
            title: '自然人信息导入',
            url: '${base}/sys/xlsie/importBatch?bean=PsnBatchImportCallbackImpl&type='+recordNumber,
            onClose: function () {
                loadCallback();
            }
        });

    }

    function oldVersionImport(recordNumber) {
        $.messager.modal({
            winId: 'wImportFromExcel',
            width: 500,
            height: 230,
            args: {
                callback: loadCallback
            },
            title: '自然人信息导入',
            url: '${base}/sys/xlsie/importBatch?bean=PsnBatchImportCallbackImpl&type='+recordNumber,
            onClose: function () {
                loadCallback();
            }
        });
    }

        //导出
    function outHandler() {
        // var getselectDataId = getIdSelections();
        // var dataId = getselectDataId.join(",");
        var name = $("[name='__LIKES_name']").val();
        var identifyType = $("[name='__EQS_identifyType']").val();
        var identifyNumber = $("[name='__LIKES_identifyNumber']").val();
        var effectRelation = $("[name='__EQS_effectRelation']").val();
        var modifiedTime = $("[name='__LIKES_modifiedTime']").val();
        var data = {
            // "ids": dataId,
            "name": name,
            "identifyType": identifyType,
            "identifyNumber": identifyNumber,
            "effectRelation": effectRelation,
            "modifiedTime": modifiedTime
        };
        var url = '${base}/sys/xlsie/PsnInfoExportCallbackImpl/' + JSON.stringify(data);
        var elemIF = document.createElement("iframe");
        elemIF.src = url;
        elemIF.style.display = "none";
        document.body.appendChild(elemIF);
    }

    //刷新表格
    function loadCallback() {
        $('#relPsnTable').jTable('refresh');
    }

    //重置
    function frmReset() {
        window.location = '${base}/bus/relPsn/list';
    }

</script>
</body>
</html>
