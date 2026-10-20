<%@ taglib prefix="bf" uri="http://www.yzj.com/ui/bootstrap/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>Limit</title>
    <style>
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
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="" subTitle="" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">
                    <bf:formGroup hrp="bottom">
                     <bc:col col="4">
                        <bc:label text="交易主体" col="5" formControl="true"/>
                        <bf:select id="tranOrg" name="__LIKES_tranOrg" col="7" dataSource="custom"
                                   url="bus/limit/getTranOrgItems" value="${tranOrg}">
                            <bf:option text="ALL" value="%"></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_tranOrg");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                     </bc:col>
                     <bc:col col="4">
                        <bc:label text="交易类型" col="5" formControl="true"/>
                        <bf:select id="tradeType" name="__LIKES_tradeType" col="7" dataSource="custom"
                                   url="bus/limit/getTradeTypeItems">
                            <bf:option text="ALL" value="%"></bf:option>
                            <be:event type="select">
                                setBusinessType(e.target.value);
                            </be:event>
                        </bf:select>
                     </bc:col>
                    <bc:col col="4">
                        <bc:label text="业务类型" col="5" formControl="true"/>
                        <bf:select id="businessType" name="__LIKES_businessType" col="7" dataSource="custom"
                                   url="bus/limit/getBuinessTypeItems" >
                            <bf:option text="ALL" value="%"></bf:option>
                        </bf:select>
                    </bc:col>
                    </bf:formGroup>
                    <bf:formGroup hrp="bottom">
                     <bc:col col="4">
                        <bc:label text="产品名称" col="5" formControl="true"/><bf:text id="proPath" name="__LIKES_proPath" col="7" placeholder="产品名称"/>
                         <%--<bf:lookup id="proPath" name="__LIKES_proPath" col="7" title="业务级别名称"
                                    url="${base}/bus/limit/proNameListTree" winId="productViewId"
                                    propText="proName" propValue="level"
                                    callback="result" onSelect="onSelect" placeholder="请在此选择选择层级">
                         </bf:lookup>--%>
                     </bc:col>
                     <bc:col col="4">
                       <bc:label text="审批状态" col="5" formControl="true"/>
                        <bf:select id="approveStatus" name="__LIKES_approveStatus" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-Applystatus">
                            <bf:option text="ALL" value="%"></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_approveStatus");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                     </bc:col>
                    <bc:col col="4">
                    <bc:label text="预警级别" col="5" formControl="true"/>
                        <bf:select id="warnLeve" name="__LIKES_warnLeve" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-Warninglevel">
                            <bf:option text="ALL" value="%"></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_warnLeve");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bc:col>
                    </bf:formGroup>
                </bc:row>
                <bc:row cls="m-b">
                    <div class="col-sm-12 col-sm-offset-5">
                        <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                        <bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
                    </div>
                </bc:row>
            </bf:form>
            <bc:table id="limitTable" idField="pid" url="${base}/bus/limit">
                <bc:toolbar id="add" text="新增" type="button"/>
                <bc:toolbar id="edit" text="编辑" type="button" handler="editLimit"/>
                <bc:toolbar id="remove" text="删除" type="button" handler="remove"/>
                <bc:toolbar id="send" text="提交审批" handler="sendLimit" type="button"/>
                <bc:column field="pid" title="自增长主键" width="100" visible="false"/>
                <%-- <bc:toolbar id="importSimple" text="导入" type="button" handler="importHandler"/>
                 <bc:toolbar id="download" text="模板下载" type="button" handler="downloadFile"/>
                 <bc:toolbar id="send" text="额度申报" type="button" handler="send"/>--%>
                <bc:toolbar id="out" text="数据导出" type="button" handler="out"/>
                <bc:column field="pid" title="自增长主键" width="100" visible="false"/>
                <bc:column field="tranOrg" title="交易主体" width="150" formatter="custom:bus/limit/getTranOrgItems" />
                <bc:column field="tradeType" title="交易类型" width="150"
                           formatter="custom:bus/limit/getTradeTypeItems"/>
                <bc:column field="businessType" title="业务类型" width="150"
                           formatter="custom:bus/limit/getBuinessTypeItems"/>
                <bc:column field="proPath" title="产品名称" width="100"/>
                <bc:column field="preLimit" title="预计额度（元）" width="150"/>
                <bc:column field="limitRate" title="额度使用率（%）" width="150"/>
                <bc:column field="warnLeve" title="预警级别" width="150"
                           formatter="custom:sys/dictItem/getDictItems/BUS-Warninglevel" />
                <bc:column field="approveStatus" title="审批状态" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-Applystatus"/>
                <%--                <bc:column field="createByName" title="申请人" width="150"/>--%>

                <%--<bc:column field="pasLimit" title="已用额度（元）" width="150"/>--%>
                <%--                <bc:column field="remLimit" title="剩余额度（元）" width="150"/>--%>
                <%--                <bc:column field="activeTime" title="生效时间" width="150"/>--%>
                <%--                <bc:column field="expiryTime" title="失效时间" width="150"/>--%>
                <%-- <bc:column field="isActive" title="是否失效" width="150"/>--%>
                <%--                <bc:column field="approveStatus" title="审批状态" width="150"--%>
                <%--                           formatter="custom:sys/dictItem/getDictItems/BUS-Applystatus"/>--%>
                <%--                <bc:column field="modifiedByName" title="审批人" width="150"/>--%>
                <%--                <bc:column field="modifiedTime" title="更新时间" width="150"/>--%>
                <bc:operator id="view" icon="search" title="查看"/>
            </bc:table>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">
    function onSelect() {
        var proPath = $("input[name=__LIKES_proPath]").val();
        // alert(proName);
        $.ajax({
            type: "post",
            url: "${base}/bus/limit/selectbuinessLS",
            data: {"unitCodes": proPath},
            success: function (data) {
                var tradeType = data.tradesType;
                var businessType = data.businessType;
                alert(tradeType +'...'+ businessType)
                var proName = data.proName;
                $('#tradeType').val(tradeType);
                $('#tradeType').select2({value: tradeType});
                $('#businessType').val(businessType);
                $('#businessType').select2({value: businessType});
                $('#proPath').val(proName);
            }
        });
    }




    var tradeType = $('#tradeType').val();
    var businessType = $('#businessType').val();
    debugger;
    //初始化内容脚本
    $(document).ready(function(){
        if(tradeType == null || tradeType == '' || tradeType == undefined ){
            setBusinessType(tradeType);
        }
    });
    //通过交易类型查询相对应的业务类型
    function setBusinessType(value){
        $.ajax({
            type: 'GET',
            url: '${base}/bus/limit/queryBusinessType/' + value,
            dataType: 'json',
            delay: 200,
            success: function(data){
                debugger;
                var arr = new Array();
                $.each(data, function(i, val){
                    var obj = {
                        id: val.value,
                        text: val.name
                    };
                    arr[i] = obj;
                });
                $("#businessType").empty();
                $("#businessType").select2({data: arr, width: "100%"});
            }
        });
    }

    /*
    * 编辑
    * */
    function editLimit() {
        var pid = getIdSelections();
        if (pid == '' || pid == null || pid.length != 1) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }
        if (pid != "") {
            var approveStatus = getApproveStatusSelections();
            if (approveStatus == '4') {
                $.messager.alert('错误', "审批中数据不可编辑", 'error');
                return false;
            }
            window.location = '${base}/bus/limit/edit/' + pid + '?';
        } else {
            $.messager.alert('提示', '请选择。', 'info');
            return "";
        }
    }

    /*
    * 移除
    * */
    function remove() {
        var pid = getIdSelections();
        if (pid == '' || pid == null || pid.length != 1) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }
        if (pid != "") {
            var approveStatus = getApproveStatusSelections();
            if (approveStatus == '4') {
                $.messager.alert('错误', "审批中，无法删除", 'error');
                return false;
            }
            if (confirm("确认删除吗")) {
                $.ajax({
                    url: "${base}/bus/limit/remove/" + pid,
                    method: "GET",
                    dataType: "JSON",
                    traditional: true,
                    success: function (data) {
                        if (data.success) {
                            window.location = '${base}/bus/limit/list/';
                            alert('删除成功！');
                        } else {
                            $.messager.alert('错误', data.msg, 'error');
                        }
                    }
                })
            }
        } else {
            $.messager.alert('提示', '请选择。', 'info');
            return "";
        }

    }

    /*
    * 审批
    * */
    function sendLimit() {
        debugger
        // 提交审批  的必须是待提交状态
        var status = getApproveStatusSelections();
        var pid = getIdSelections();

        if (pid == '' || pid == null || pid.length != 1) {
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }
        if (status == '3') {
            //查询出基本信息 修改状态 放到data中
            $.ajax({
                url: "${base}/bus/limit/send/" + pid,
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
                            procDefCode: 'Estimated_Quota',
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

    /*
     * 数据导出
     */
    function out() {
        var tranOrg = $("[name='__LIKES_tranOrg']").val() == "%" ? "%25" : $("[name='__LIKES_tranOrg']").val();
        var tradeType = $("[name='__LIKES_tradeType']").val() == "%" ? "%25" : $("[name='__LIKES_tradeType']").val();
        var businessType = $("[name='__LIKES_businessType']").val() == "%" ? "%25" : $("[name='__LIKES_businessType']").val();
        var proPath = $("[name='__LIKES_proPath']").val()==""?"%25":$("[name='__LIKES_proPath']").val();
        var approveStatus = $("[name='__LIKES_approveStatus']").val() == "%" ? "%25" : $("[name='__LIKES_approveStatus']").val();
        var warnLeve = $("[name='__LIKES_warnLeve']").val() == "%" ? "%25" : $("[name='__LIKES_warnLeve']").val();
        var data = tranOrg + "," + tradeType + "," + businessType +","+ proPath + "," + approveStatus+ "," + warnLeve;
        debugger;
        var url = '${base}/sys/xlsie/BusLimitExportCallbackImpl/' + data;
        var elemIF = document.createElement("iframe");
        elemIF.src = url;
        elemIF.style.display = "none";
        document.body.appendChild(elemIF);
    }


    function getIdSelections() {
        return $.map($('#limitTable').bootstrapTable('getSelections'), function (row) {
            return row.pid;//返回数据行中的id值
        });
    }

    function getApproveStatusSelections() {
        return $.map($('#limitTable').bootstrapTable('getSelections'), function (row) {
            return row.approveStatus;//返回数据行中的id值
        });
    }
    function getdataResourceSelections() {
        return $.map($('#limitTable').bootstrapTable('getSelections'), function (row) {
            return row.dataResource;//返回数据行中的id值
        });
    }


    //刷新表格
    function loadCallback() {
        $('#productTable').jTable('refresh');
    }

    //重置
    function frmReset() {
        window.location = '${base}/bus/limit/list';
    }


</script>
</body>
</html>