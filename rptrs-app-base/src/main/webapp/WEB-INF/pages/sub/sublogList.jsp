<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>Sublog</title>
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
        <bc:ibox title="" subTitle="金融产品信息报送" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">
                    <bf:formGroup col="3">
                        <bc:label text="文件名称" col="5" formControl="true"/>
                        <bf:text name="__LIKES_fileName" col="7" placeholder="文件名称"/>
                    </bf:formGroup>
                    <bf:formGroup col="3">
                        <bc:label text="报送状态" col="5" formControl="true"/>
                        <bf:select name="__LIKES_downStatus" col="7" dataSource="custom" url="sys/dictItem/getDictItems/SUBMIT-status">
                            <bf:option text="请选择" value="%"></bf:option>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="3">
                        <bc:label text="开始时间" col="5" formControl="true"/>
                        <bf:datebox id="startDate" name="__GES_maxPercentInNetAssets" col="7" format="date"/>
                    </bf:formGroup>
                    <bf:formGroup col="3">
                        <bc:label text="截止时间" col="5" formControl="true"/>
                        <bf:datebox id="endDate" name="__LTS_maxPercentInNetAssets" col="7" format="date"/>
                    </bf:formGroup>
                    <bc:row cls="m-b">
                        <div class="col-sm-12 col-sm-offset-5">
                            <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                            <bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
                        </div>
                    </bc:row>
                </bc:row>
            </bf:form>
            <bc:table id="sublogTable" idField="pid" url="${base}/sub/sublog" queryUrl="query/pro">
                <bc:toolbar id="submitFile" text="新增" type="button" handler="addSubPro"/>
                <bc:toolbar id="submitFile" text="编辑" type="button" handler="addSubPro"/>
<%--            <bc:toolbar id="submitFile" text="生成报送文件" type="button" handler="frmSubmitFile"/>--%>
               <%-- <bc:toolbar id="send" text="提交审批" handler="send" type="button"/>--%>
<%--                <bc:toolbar id="submitFile" text="新增报送文件" type="button" handler="addSubPro"/>--%>
                <bc:toolbar id="remove" text="删除" type="button" handler="removeHandler"/>
                <bc:toolbar id="downFile" text="下载文件" type="button" handler="downFileOnclick"/>
                <bc:toolbar id="send" text="获取审批记录" handler="send" type="button"/>
                <bc:column field="pid" title="自增长主键" width="100" visible="false"/>
                <bc:column field="batchDecimal" title="batchDecimal" width="100" visible="false"/>
                <bc:column field="fileName" title="文件名称" width="100"/>
                <bc:column field="subOrg" title="报送机构" width="100" formatter="custom:bus/limit/getTranOrgItems"/>
                <bc:column field="maxPercentInNetAssets" title="生成报送文件时间" width="100"/>
                <bc:column field="operatorName" title="操作员" width="100"/>
                <bc:column field="subType" title="状态" width="100" visible="false" formatter="custom:sys/dictItem/getDictItems/SUBMIT-status" />
                <bc:column field="downStatus" title="是否已下载" width="100" formatter="fixedDict:IsStatusDict"/>
                <bc:column field="operatorName" title="操作员" width="100"/>
                <bc:column field="approvalId" title="审批id" width="100" visible="false"/>
                <bc:operator id="view" icon="search" title="操作"/>
            </bc:table>
            <h3>审批意见</h3>
            <bc:table id="OAApprovalLog" idField="approvalId" url="${base}/pub/oaApprovalLog"
                      parentType="table" parentId="sublogTable" parentField="approvalId">
                <bc:column field="pid" title="自增长主键" width="100" visible="false"/>
                <bc:column field="opinionSerialno" title="序号" width="100"/>
                <bc:column field="opinionInfo" title="审批详细信息" width="100"/>
                <bc:column field="opinionUser" title="意见填写人" width="100"/>
                <bc:column field="opinionTime" title="意见填写日期" width="100"/>
            </bc:table>
        </bc:ibox>
    </div>
</div>
<%--<div  id="exampleModalCenter1" role="dialog"
      style="display: none">
    <div class="modal-body">
        <div style="text-align: center" id="removing"><p style="color: dodgerblue">正在删除报送文件，请稍后操作...</p></div>
    </div>
</div>--%>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">


    //获取审批记录
    function send() {
        // 获取审批记录
        $.ajax({
            url:"${base}/sub/sublog/getApprovalLog",
            method:"GET",
            dataType:"JSON",
            traditional:true,
            success:function(data){
                if(data.success){
                    $.messager.alert('提示', data.msg, 'info');
                }else
                {
                    $.messager.alert('错误', data.msg, 'error');
                }
            }
        })
    }
    function addSubPro() {
        window.location = '${base}/sub/sublog/addSubPro';
    }
    //生成报送文件
    function frmSubmitFile() {
        var data = "pro";
        var url = '${base}/sub/sublog/template/' + data;
        $.ajax({
            url: url,
            type: 'post',
            data: data,
            dataType: 'json',
            success: function (data) {
                if (data.success) {
                    $.messager.alert('成功',data.msg,'info',function(){
                        window.location = '${base}/sub/sublog/list';
                    });
                } else {
                    if(data.msg == "暂无报送数据生成"){
                        $.messager.alert('提示',data.msg,'info');
                    }else{
                        $.messager.alert('失败',data.msg,'error');
                    }
                }
            }
        });

    }

    //提交审批
    function send() {
        // 提交审批  的必须是待提交状态
        var status=getStatusSelections();
        var pid=getIdSelections();
        if (pid=='' || pid==null||pid.length!=1){
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }
        if (status == "01") {
            //查询出基本信息 修改状态 放到data中
            $.ajax({
                url:"${base}/sub/sublog/send/"+pid,
                method:"GET",
                dataType:"JSON",
                traditional:true,
                success:function(data){
                    if(data.success){
                        //开始走工作流
                        var objData=data.data;
                        var myDate = new Date();
                        objData.id = myDate.getTime();
                        data = JSON.stringify(objData);
                        var formData = $.helper.base64Encode(data);
                        //挂取工作流
                        var obj = {
                            procDefCode: 'YZJ_SUB001',
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
                    }else
                    {
                        $.messager.alert('错误', data.msg, 'error');
                    }
                }
            })
        }else {
            alert("当前状态不符合要求！");
        }
    }

    //文件下载
    function downFileOnclick(){
        var pid = getIdSelections();
        if (pid=='' || pid==null||pid.length!=1){
            $.messager.alert('错误', "请选择一条数据", 'error');
            return;
        }
        var status = getStatusSelections();
        var url = '${base}/sub/credit/subDownFile/' + pid;
        if(true){
            var elemIF = document.createElement("iframe");
            elemIF.src = url;
            elemIF.style.display = "none";
            document.body.appendChild(elemIF);
            }else {
               debugger;
              $.messager.alert('警告', "当前状态不支持下载，请查看！", 'warning');
               //当状态未通过时按钮置灰 不可点击
              document.getElementById("downFile").setAttribute("disabled", true);
        }
    }

    //删除
    function removeHandler() {

        var status = getStatusSelections();
        if (status == "02" || status == "03") {
            $.messager.alert('错误', "审批中，或审批通过 则无法删除", 'error');
            return;
        }
        if (confirm('确认删除？')) {
            var pid = getIdSelections();
            $.ajax({
                url: '${base}/sub/sublog/remove/' + pid,
                type: 'json',
/*                beforeSend: function (XMLHttpRequest) {
                    $("#exampleModalCenter1").attr("style","display:block;");
                },*/
                beforeSend: function (jqXHR, settings) {
                    $.mask.show('正在删除，请稍后。。。');
                },
                success: function (data) {
                    $.mask.remove();
                    if (data.success) {
                        $.messager.alert('提示', data.msg, 'info', function () {
                            window.location = '${base}/sub/sublog/list';
                        })
                    } else {
                        $.messager.alert('错误', data.msg, 'error');
                    }
                }
            });
        }
    }

    //获取表单的审批状态
    function getStatusSelections() {
        return $.map($('#sublogTable').bootstrapTable('getSelections'), function (row) {
            return row.status;
        });
    }
    //获取表单选择的pid
    function getIdSelections() {
        return $.map($('#sublogTable').bootstrapTable('getSelections'), function (row) {
            return row.pid;
        });
    }
    //重置
    function frmReset() {
        window.location = '${base}/sub/sublog/list';
    }
</script>
</body>
</html>