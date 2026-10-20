<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <style type="text/css">

        #traBusinessTable_tb_add,#traBusinessTable_tb_edit,
        #traBusinessTable_tb_remove,#traBusinessTable_tb_import,
        #traBusinessTable_tb_sendCommand,#traBusinessTable_tb_downTmp,
        #traBusinessTable_tb_approve,#traBusinessTable_tb_export,
        #traBusinessTable_tb_ActualTra, #traBusinessTable_tb_confirm,
        #traBusinessTable_tb_confirm1,#traBusinessTable_tb_attachmentAdd

        {
            display: none;

        }
    </style>

    <title>关联交易（保险）-保险业务类</title>
    <style>
        #queryId1{
            display: none;
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
                                       url="sys/dictItem/getDictItems/INT-InsBusinessType">
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


                    <div id="queryId1">
                        <bf:formGroup col="4">
                            <bc:label text="是否为统一交易协议" col="5" formControl="true"/>
                            <bf:select name="isAgreement" col="7" dataSource="fixedDict" url="YesOrNoForRelTraDict">
                                <bf:option text="请选择" value=""></bf:option>
                            </bf:select>
                        </bf:formGroup>
                        <bf:formGroup col="4">
                            <bc:label text="统一交易协议执行情况" col="5" formControl="true"/>
                            <bf:select name="isAgreementImpl" col="7" dataSource="custom"
                                       url="sys/dictItem/getDictItems/INS-isAgreementImpl">
                                <bf:option text="请选择" value=""></bf:option>
                            </bf:select>
                        </bf:formGroup>
                    </div>

                    <bc:row cls="m-b">
                        <div class="col-sm-12 col-sm-offset-5">
                            <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                            <bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
                        </div>
                    </bc:row>
                </bc:row>
            </bf:form>
            <bc:table id="traBusinessTable" idField="pid" url="${base}/ins/traBusiness" multiple="false" selectFirst="true" queryUrl="queryPage">
                <bc:toolbar id="add" text="新增" type="button" />
                <bc:toolbar id="edit" text="编辑" type="button" handler="toEdit"/>
                <bc:toolbar id="remove" text="删除" type="button" handler="removeTraBusiness"/>
                <bc:toolbar id="downTmp" text="模板下载" type="button" handler="downloadHandler"/>
                <bc:toolbar id="import" text="导入" type="button" handler="importHandler"/>
                <bc:toolbar id="export" text="导出" type="button" handler="out"/>
                <bc:toolbar id="approve" text="提交审批" type="button" handler="send"/>
                <%--<bc:toolbar id="batchApprove" text="集中审议" type="button" handler="batchSend"/>--%>
                <bc:toolbar id="confirm" text="确认" type="button" handler="toConfirm"/>
                <bc:toolbar id="confirm1" text="签订" type="button" handler="sign"/>
                <bc:toolbar id="attachmentAdd" text="附件补充" type="button" handler="attachmentAdd"/>
                <bc:column field="pid" title="主键" width="100" visible="false"/>
                <bc:column field="relId" title="关联方ID" width="100" visible="false"/>
                <bc:column field="fundsFlowNumber" title="交易流水编号" width="150"/>
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
                <bc:column field="relTradeAmount" title="本位币交易金额（万元）" width="150"/>
<%--                <bc:column field="pracTradeAmount" title="实际交易金额（万元）" width="100" visible="false"/>--%>
                <bc:column field="creater" title="创建人" width="130"/>
                <bc:column field="contractDateString" title="合同签署日期" width="100"/>
                <bc:column field="considerType" title="审议类型" width="100" formatter="custom:sys/dictItem/getDictItems/INS-considerType"/>
                <bc:column field="status" title="交易状态" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-Applystatus"/>
                <bc:column field="deptName" title="经办人部门" width="100"/>
                <bc:column field="statusComment" title="操作状态说明" width="100" visible="false"/>
                <bc:operator id="view" icon="eye" title="操作/查看"/>
            </bc:table>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
    //高级查询
    $(document).ready(function(){
        var flag = true;
        $("#advancedQuery_id").bind("click",function(){
            if (flag){
                $("#queryId1").show();
                flag = false;
            }else{
                $("#queryId1").hide();
                flag = true;
            }
        });
    });
       //根据权限显示按钮
        $(function () {
        var code = "${code }";
        if(code.search("01,") != -1){
            $('#traBusinessTable_tb_add').show();
        }
        if(code.search("02,") != -1){
            $('#traBusinessTable_tb_edit').show();
        }
        if(code.search("03,") != -1){
            $('#traBusinessTable_tb_remove').show();
        }
        if(code.search("04,") != -1){
            $('#traBusinessTable_tb_import').show();
        }
        if(code.search("05,") != -1){
            $('#traBusinessTable_tb_sendCommand').show();
        }
        if(code.search("06,") != -1){
            $('#traBusinessTable_tb_downTmp').show();
        }
        if(code.search("07,") != -1){
            $('#traBusinessTable_tb_approve').show();
        }
        if(code.search("08") != -1){
            $('#traBusinessTable_tb_export').show();
        }
        if (code.search("021") != -1) {//上传
            $('#traBusinessTable_tb_attachmentAdd').show();
        }
        if(code.search("034") != -1){
            $('#traBusinessTable_tb_ActualTra').show();
        }
        if(code.search("035") != -1){
            $('#traBusinessTable_tb_confirm').show();
        }
        if(code.search("040") != -1){
            $('#traBusinessTable_tb_confirm1').show();
        }
    })
    function removeTraBusiness() {
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
            url: '${base}/ins/traBusiness/createPerisExist?pid=' + pid,
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
                                url: "${base}/ins/traBusiness/removeTraBusiness/" + pid,
                                method: "GET",
                                data: {"": ""},
                                dataType: "JSON",
                                traditional: true,
                                success: function (data) {
                                    if (data.success) {
                                        $.messager.alert('提示', data.msg, 'info', function () {
                                            window.location = "${base}/ins/traBusiness/list";
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

    function getrelIdSelections() {
        return $.map($('#traFundsTable').bootstrapTable('getSelections'), function (row) {
            return row.relId;
        });
    }


    // 获取关联方类型
    function getTranTypeSelections() {
        return $.map($('#traBusinessTable').bootstrapTable('getSelections'), function (row) {
            return row.tranType;
        });
    }

    // 获取关联方类型
    function getRelTypeSelections() {
        return $.map($('#traBusinessTable').bootstrapTable('getSelections'), function (row) {
            return row.relType;
        });
    }

    /**获取选中的审议类型*/
   function getConsiderTypeSelections() {
       return $.map($('#traBusinessTable').bootstrapTable('getSelections'), function (row) {
           return row.considerType;
       });
   }

   /**获取选中的实际交易金额*/
   function getPracTradeAmountSelections() {
       return $.map($('#traBusinessTable').bootstrapTable('getSelections'), function (row) {
           return row.pracTradeAmount;
       });
   }


    /* 任务下发 */
    function sendCommand() {
        var tables = $('#traBusinessTable').jTable('getCheckData');
        if (tables.length == 1) {
            $.messager.modal({
                winId: 'InsTraBusinessSend',
                width: 700,
                height: 420,
                title: '选择机构',
                url: '${base}/ins/traBusiness/traBusinessSendCommand',
                args: {traBusinessId: tables[0].pid},
                onClose: function () {
                    $('#traBusinessTable').jTable('clearCheckData');
                    $('#traBusinessTable').jTable('refresh');
                }
            });
        } else {
            $.messager.toastr('提示', '请选择一条数据');
        }
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

       function toEdit() {
           debugger
           var pid = getIdSelections();
           if (pid == '' || pid == null) {
               $.messager.alert('错误', "请选择一条数据", 'error');
               return;
           }
           if (pid.length > 1) {
               $.messager.alert('错误', "不能选择多条数据进行编辑操作，请重新勾选", 'error');
               return;
           }
           if (pid != "") {
               $.ajax({
                   type: 'GET',
                   url: '${base}/ins/traBusiness/createPerisExist?pid=' + pid,
                   dataType: 'json',
                   delay: 200,
                   success: function (data) {
                       if (data.success) {
                           var status = getStatusSelections();
                           var considerType = getConsiderTypeSelections();
                           if (status == '4') {
                               $.messager.alert('提示', '审批中数据不可编辑', 'info');
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
                           window.location = '${base}/ins/traBusiness/edit/' + pid+ '?op=1';
                       } else {
                           $.messager.alert('提示', "您没有权限!", 'error');
                           return;
                       }
                   }
               });
           } else {
               $.messager.alert('提示', '请选择自然人。', 'info');
               return "";
           }
       }


    /*
    * 审批
    * */
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
            url: '${base}/ins/traBusiness/createPerisExist?pid=' + pid,
            dataType: 'json',
            delay: 200,
            success: function (data) {
                if (data.success) {
                    if (status == 5) {
                        $.messager.alert('错误', "审批退回数据请编辑后在重新提交！", 'error');
                        return;
                    }
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
                    $.mask.post('${base}/ins/traBusiness/send/' + pid, function (data) {
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
            url: '${base}/ins/traService/jungleRelatedDate?relType=' + relType + "&pid=" + pid+"&tranType=" + tranType,
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
   function batchSend(){
       window.location='${base}/ins/traBusiness/batchSendList'
   }
    function editTraBusiness() {
        var pid = getIdSelections();
        if (pid == '' || pid == null || pid.length != 1) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }
        if (pid != "") {
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
            window.location = '${base}/ins/traBusiness/edit/' + pid+ '?op=1';


        } else {
            $.messager.alert('提示', '请选择自然人。', 'info');
            return "";
        }
    }

    //刷新表格
    function loadCallback() {
        $('#naturalPersonTable').jTable('refresh');
    }

    function addOrRemoveBlacklist() {
        var status = getStatusSelections();
        if (status != "6") {
            $.messager.alert('提示', '请选择已归档的关联方', 'info');
            return "";
        }
        var pid = getIdSelections();
        if (pid != "") {
            window.location = '${base}/bus/relPsn/addOrRemoveBlack/' + pid + '?';
        } else {
            $.messager.alert('提示', '请选择自然人。', 'info');
            return "";
        }
    }

    function disconnection() {
        var pid = getIdSelections();
        if (pid != "") {
            window.location = '${base}/bus/relPsn/disconnection/' + pid + '?';
        } else {
            $.messager.alert('提示', '请选择自然人。', 'info');
            return "";
        }
    }

       //确认按钮
       function toConfirm() {
           var pid = getIdSelections()
           var status = getStatusSelections()
           if (pid == '' || pid == null || pid.length != 1) {
               $.messager.alert('错误', "请选择一条数据", 'error');
           }
           if (status == 2) {
               window.location = '${base}/ins/traBusiness/toConfirm/' + pid;
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
            window.location = '${base}/ins/traBusiness/sign/' + pid;
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
           return $.map($('#traBusinessTable').bootstrapTable('getSelections'), function (row) {
               return row.transNum;
           });
       }

    /*
   * 重置
   * */
    function frmReset(){
        window.location = '${base}/ins/traBusiness/list';
    }

    //模板下载
    function downloadHandler() {
        var data = "1"
        var url = '${base}/sys/xlsie/InsTraBusinessOutCallbackImpl/' + data;
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
            url: '${base}/sys/xlsie/import?bean=InsTradeBusinessImportCallbackImpl',
            onClose: function () {
                loadCallback();
            }
        });
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
           var endTranDate = $("[name='endTranDate']").val() == "" ? "%25" : $("[name='endTranDate']").val();
           var contractNo = $("[name='contractNo']").val() == "" ? "%25" : $("[name='contractNo']").val();
           var type = "check";
           var data = type + "," + relName + "," + busType + "," + relNumber
               + "," + status  + "," + considerType + "," + majorTrade + "," +startTranDate + "," + endTranDate + "," + contractNo;
           var url = '${base}/sys/xlsie/InsTradeBusinessExportCallbackImpl/' + data;
           var elemIF = document.createElement("iframe");
           elemIF.src = url;
           elemIF.style.display = "none";
           document.body.appendChild(elemIF);
       }


    function loadCallback() {
        $('#traBusinessTable').jTable('refresh');
    }

    function submitHandler() {
           var status = getStatusSelections();
           var pid = getIdSelections();
        if(status == 5 ){
            $.messager.alert('错误', "审批退回数据请编辑后在重新提交！", 'error');
            return;
        }
          if (status == 3 || status == 9) {
           window.location = "${base}/ins/traBusiness/submit/" + pid;
        }else {
              $.messager.alert('错误',"当前状态不符合要求！", 'error');
          }
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
            window.location = '${base}/ins/traBusiness/attachmentAdd/' + pid;
        } else if ('9' != status) {
            $.messager.alert('错误', "该笔交易已经被驳回，请勿上传附件！", 'error');
            return;
        }
    }

</script>
</body>
</html>
