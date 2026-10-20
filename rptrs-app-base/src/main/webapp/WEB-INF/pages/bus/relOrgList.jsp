<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <link rel="stylesheet" href="/static/css/dialog.css">
    <script src="/static/js/jquery.dialog.js"></script>
    <br:css pack="list"/>
    <title>RelOrg</title>
    <style>
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

        #relOrgTable_tb_add, #relOrgTable_tb_edit,
        #relOrgTable_tb_remove, #relOrgTable_tb_importSimple,
        #relOrgTable_tb_sendCommand, #relOrgTable_tb_export,
        #relOrgTable_tb_send, #relOrgTable_tb_out, #relOrgTable_tb_addOrRemoveBlacklist,
        #relOrgTable_tb_affirm {
            display: none;

        }
        #relOrgTable_tb_importSimple,#relOrgTable_tb_batchImport{
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
    <script>
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


        /*
        * 审批
        * */
        function getStatusSelections() {
            return $.map($('#relOrgTable').bootstrapTable('getSelections'), function (row) {
                return row.approvalStatus;
            });
        }

        //获取是否有效
        function getEffectRelationSelections() {
            return $.map($('#relOrgTable').bootstrapTable('getSelections'), function (row) {
                return row.effectRelation;
            });
        }

        /*
         * 编辑按钮
         */
        function editHandler() {
            var pid = getIdSelections();
            if (pid == '' || pid == null) {
                $.messager.alert('错误', "请选择一条数据", 'error');
                return;
            }
            if (pid.length > 1) {
                $.messager.alert('提示', "请选择一条数据进行编辑！", 'info');
                return;
            }
            var status = getStatusSelections();
            if (status != 4) {
                window.location = "${base}/bus/relOrg/edit/" + pid;
            } else {
                $.messager.alert('错误', "审批中，无法编辑", 'error');
                return;
            }
        }

        function importHandler() {
            // $("#hiddenBtn").click();
            $.messager.modal({
                winId: 'wImportFromExcel', //winid只能为 wImportFromExcel
                width: 500,
                height: 230,
                args: {
                    callback: loadCallback
                },
                title: '机构信息导入',
                url: '${base}/sys/xlsie/import?bean=RelOrgImportCallbackImpl',
                onClose: function () {
                    loadCallback();
                }
            });
        }


        function getIdSelections() {
            return $.map($('#relOrgTable').bootstrapTable('getSelections'), function (row) {
                return row.pid;
            });
        }

        function getMgerOrgSelections() {
            return $.map($('#relOrgTable').bootstrapTable('getSelections'), function (row) {
                return row.mgerOrg;
            });
        }

        function removeHandler() {
            var pids = getIdSelections();
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
                            url: "${base}/bus/rel/queryRel/" + pids + "/ORG",
                            method: "GET",
                            data: {"": ""},
                            dataType: "JSON",
                            traditional: true,
                            success: function (data) {
                                if (data.success) {
                                    $.ajax({
                                        url: "${base}/bus/relOrg/remove/" + pids,
                                        method: "GET",
                                        data: {"": ""},
                                        dataType: "JSON",
                                        traditional: true,
                                        success: function (data) {
                                            if (data.success) {
                                                $.messager.alert('提示', data.msg, 'info', function () {
                                                    window.location = "${base}/bus/relOrg/list"
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

        //数据导出
        function exportHandler() {
            var orgName = $("[name='__LIKES_orgName']").val();
            var identifyType = $("[name='__LIKES_identifyType']").val();
            var uniscCode = $("[name='__LIKES_uniscCode']").val();
            var effectRelation = $("[name='__LIKES_effectRelation']").val();
            var modifiedTime = $("[name='__LIKES_modifiedTime']").val();
            // var isSubsidiary = $("[name='__LIKES_isSubsidiary']").val();
            var data = {
                "orgName": orgName,
                "identifyType": identifyType == '%' ? '' : identifyType,
                "uniscCode": uniscCode,
                "effectRelation": effectRelation == '%' ? '' : effectRelation,
                "modifiedTime": modifiedTime
                // "isSubsidiary": isSubsidiary == '%' ? '' : isSubsidiary
            };
            console.log(data)
            var url = '${base}/sys/xlsie/OrgInfoExportCallbackImpl/' + JSON.stringify(data);
            var elemIF = document.createElement("iframe");
            elemIF.src = url;
            elemIF.style.display = "none";
            document.body.appendChild(elemIF);
        }

        /* 导入模板下载*/
        function outtHandler() {
            var data = "1";
            var url = '${base}/sys/xlsie/OrgoutExportCallbackImpl/' + data;
            var elemIF = document.createElement("iframe");
            elemIF.src = url;
            elemIF.style.display = "none";
            document.body.appendChild(elemIF);
        }

        //导入操作
        function upload() {
            var obj = document.getElementById("file");
            for (var i = 0; i < obj.files.length; i++) {
                var temp = obj.files[i].name;
                var temArr = temp.split('.');
                var fileLast = temArr[temArr.length - 1];
                // alert(fileLast);
                if (fileLast != 'xls' && fileLast != 'xlsx') {
                    $.messager.alert("请上传Excel文件");
                    return;
                } else {
                    var formData = new FormData($('#uploadForm')[0]);
                    // alert(formData);
                    $.ajax({
                        url: '${base}/bus/relOrg/import?bean=RelOrgImportCallbackImpl',
                        type: 'post',
                        data: formData,
                        cache: false,
                        processData: false,  // 不处理数据
                        contentType: false,
                        success: function (data) {
                            var a = data;
                            console.log(JSON.parse(data));
                            var parse = JSON.parse(data);
                            var msg = parse.msg[0].message;
                            if (parse.success) {
                                alert(msg);
                                $("#close").click();
                                location.reload();
                            } else {
                                alert(msg);
                                $("#close").click();
                                location.reload();
                            }
                        }
                    })
                }
            }
        }


        // 批量导入 RelOrgBatchImportCallbackImpl
        function batchImportHandler() {
            $.ajax({
                url: "${base}/bus/relOrg/recordNumber" ,  //待修改
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
                                $.messager.confirm('提示', '系统中已有'+recordNumber +'版本数据，是否延用版本号，生成'+smallRecordNumber+'版本数据?', function (d) {
                                    if(d){
                                        newVersionImport(smallRecordNumber);
                                    }
                                })
                            }else {
                                $.messager.confirm('提示', '执行全量导入后，是否新增版本号'+ newRecordNumber +"，法人或非法人组织关联方信息将全部替换，是否操作？", function (d) {
                                    if(d){
                                        newVersionImport(newRecordNumber);
                                    }else {
                                        $.messager.confirm('提示', '系统中已有'+recordNumber +'版本数据，是否延用版本号，生成'+smallRecordNumber+'版本数据?', function (d) {
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

            // $.messager.confirm('提示', '执行全量导入后，法人关联方信息将全部替换，是否操作？', function (r) {
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
                title: '法人关联方信息导入',
                url: '${base}/sys/xlsie/importBatch?bean=RelOrgBatchImportCallbackImpl&type='+recordNumber,
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
                title: '法人关联方信息导入',
                url: '${base}/sys/xlsie/importBatch?bean=RelOrgBatchImportCallbackImpl&type='+recordNumber,
                onClose: function () {
                    loadCallback();
                }
            });
        }

        //刷新表格
        function loadCallback() {
            $('#relOrgTable').jTable('refresh');
        }

        //重置
        function frmReset() {
            window.location = '${base}/bus/relOrg/list';
        }

    </script>
</head>
<body class="gray-bg" style="background: #ffffff;">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="" subTitle="" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">
                    <bf:formGroup col="4">
                        <bc:label text="机构名称" col="5" formControl="true"/>
                        <bf:text name="__LIKES_orgName" col="7" placeholder=""/>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="证件类型" col="5" formControl="true"/>
                        <bf:select name="__LIKES_identifyType" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-ORGIdentifyType">
                            <bf:option text="请选择" value="%"></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_identifyType");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="证件号码" col="5" formControl="true"/>
                        <bf:text name="__LIKES_uniscCode" col="7" placeholder=""/>
                    </bf:formGroup>
                    <center>
                        <div id="advancedQueryId">
                            <span id="advancedQuery_id" style="color: #02afff">高级查询▼</span>
                        </div>
                    </center>
                    <div id="queryId" class="queryClass">
                        <bf:formGroup col="4">
                            <bc:label text="是否存在有效关系" col="5" formControl="true"/>
                            <bf:select name="__LIKES_effectRelation" col="7" dataSource="custom"
                                       url="sys/dictItem/getDictItems/BUS-EffectRelation">
                                <bf:option text="请选择" value="%"></bf:option>
                            </bf:select>
                        </bf:formGroup>
                        <%--<bf:formGroup col="4">
                            <bc:label text="是否为控股公司" col="5" formControl="true"/>
                            <bf:select name="__LIKES_isSubsidiary" id="isSubsidiary" col="7" dataSource="custom"
                                       url="sys/dictItem/getDictItems/BUS-isSubsidiary">
                                <bf:option text="请选择" value="%"></bf:option>
                            </bf:select>
                        </bf:formGroup>--%>
                        <bf:formGroup col="4" type="success">
                            <bc:label text="更新时间" col="5" formControl="true"></bc:label>
                            <bf:datebox name="__LIKES_modifiedTime" id="modifiedTime" col="7" format="date"/>
                        </bf:formGroup>
                    </div>
                </bc:row>
                <bc:row cls="m-b">
                    <bc:col col="12" colOffset="5">
                        <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                        <bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
                    </bc:col>
                </bc:row>
            </bf:form>
            <bc:table id="relOrgTable" idField="pid" url="${base}/bus/relOrg" selectFirst="true" multiple="true">
                <bc:toolbar id="add" text="新增" type="button"/>
                <bc:toolbar id="edit" text="编辑" type="button" handler="editHandler"/>
                <bc:toolbar id="remove" text="删除" type="button" handler="removeHandler"/>
                <bc:toolbar id="importSimple" text="增量导入" type="button" handler="importHandler"/>
                <bc:toolbar id="batchImport" text="全量导入" type="button" handler="batchImportHandler"/>
                <bc:toolbar id="export" text="导出" type="button" handler="exportHandler"/>
                <bc:toolbar id="out" text="模板下载" type="button" handler="outtHandler"/>
                <bc:toolbar id="affirm" text="确认" type="button" handler="affirmOrg"/>
                <bc:column field="pid" title="自增长主键" width="100" visible="false"/>
            <%--    <bc:column field="relateNumber" title="关联方编号" width="100" visible="false"/>--%>
                <bc:column field="orgName" title="机构名称" width="100"/>
                <bc:column field="identifyType" title="证件类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-ORGIdentifyType"/>
                <bc:column field="uniscCode" title="证件号码" width="100"/>
                <bc:column field="domesticOrForeign" title="境内/境外" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-DomesticOrForeign"/>
                <bc:column field="effectRelation" title="是否存在有效关系"
                           formatter="custom:sys/dictItem/getDictItems/BUS-EffectRelation" width="100"/>
                <bc:column field="createByName" title="创建人" width="100" />
                <bc:column field="createTimeString" title="创建日期" width="100" />
                <bc:column field="modifiedTimeString" title="更新日期" width="100"/>
                <bc:column field="approvalStatus" title="数据状态" visible="false"
                           formatter="custom:sys/dictItem/getDictItems/BUS-ApplySingleStatus" width="100"/>
<%--                <bc:column field="isSubsidiary" title="是否为控股公司"
                           formatter="custom:sys/dictItem/getDictItems/BUS-EffectRelation" width="100"/>--%>
                <bc:operator id="view" icon="eye" title="查看"/>
            </bc:table>
        </bc:ibox>
    </div>
</div>
<div>
    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModalCenter"
            id="hiddenBtn"
            style="display: none">
        文件上传按钮
    </button>
    <div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog"
         aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalCenterTitle">文件上传</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form method="post" enctype="multipart/form-data" id="uploadForm"
                          style="border: 1px dashed #cccccc; height: 150px;padding: 10px 0;margin: 0 0 19px 0;">
                        <div class="form-group" style="text-align: center;vertical-align: middle;">
                            <label for="file"></label>
                            <img src="../../static/img/sendfile.png"/>
                            <a href="javascript:;" class="file">选择文件
                                <input type="file" class="form-control" id="file" name="file" multiple
                                       style="padding: 15px !important;height: 59px !important; border: none !important; text-align: center !important;margin: 0 auto; width: 38%;">
                            </a>
                        </div>

                    </form>
                    <div class="form-group"
                         style="width: 50%; padding-top:30px;height: 90px !important;margin: 0 auto;text-align: center;">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal" id="close">关闭</button>
                        <button type="submit" class="btn btn-primary" onclick="upload()" id="upload">上传</button>
                    </div>
                </div>
            </div>
        </div>
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
            $('#relOrgTable_tb_add').show();
        }
        //编辑
        if (code.search("02,") != -1) {
            $('#relOrgTable_tb_edit').show();
        }
        //删除
        if (code.search("03,") != -1) {
            $('#relOrgTable_tb_remove').show();
        }
        //导入
        if (code.search("04,") != -1) {
            $('#relOrgTable_tb_importSimple').show();
            $('#relOrgTable_tb_batchImport').show();
        }
        //模板下载
        if (code.search("06,") != -1) {
            $('#relOrgTable_tb_out').show();
        }
        //导出
        if (code.search("08,") != -1) {
            $('#relOrgTable_tb_export').show();
        }
    })
</script>
</body>
</html>
