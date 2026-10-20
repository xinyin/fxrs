<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>流程实例表</title>
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
    <x:jsLib/>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="" subTitle="" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bf:hidden name="__INS_status" value="20,40,50,60"/>
                <bf:hidden name="__EQS_starterCode" value="${userCode}"/>
<%--                <bc:row cls="m-b">--%>
<%--                    <bf:formGroup hrp="bottom">--%>
<%--                        <bf:formGroup col="4">--%>
<%--                            <bc:label text="业务流程名称" col="5" formControl="true"></bc:label>--%>
<%--                            <bf:text name="__LIKES_procName" col="7" placeholder="业务流程名称"></bf:text>--%>
<%--                        </bf:formGroup>--%>
<%--                        <bf:formGroup col="4">--%>
<%--                            <bc:label text="模块名称" col="5" formControl="true"></bc:label>--%>
<%--                            <bf:text name="__LIKES_moduleName" col="7" placeholder="模块名称"></bf:text>--%>
<%--                        </bf:formGroup>--%>
<%--                    </bf:formGroup>--%>
<%--                    <div class="col-sm-12 col-sm-offset-5">--%>
<%--                        <bc:button id="btnQuery" text="查询" icon="search" type="primary"/>--%>
<%--                        <bc:button id="btnReset" text="重置" icon="refresh"/>--%>
<%--                    </div>--%>
<%--                </bc:row>--%>
            </bf:form>
            <bc:table id="procInstTable" idField="id" url="${base}/workflow/process" queryUrl="queryHis">
                <bc:toolbar id="viewBiz" text="查看业务数据" type="button" handler="viewBizHandler"/>
                <bc:toolbar id="down" text="下载文件" type="button" handler="downBizHandler"/>
                <bc:column field="taskId" title="任务ID" width="100" visible="false"/>
                <bc:column field="id" title="流程实例ID" width="100" visible="false"/>
                <bc:column field="procCode" title="业务流程编码" width="100" visible="false"/>
                <bc:column field="procName" title="流程名称" width="100"/>
                <bc:column field="relType" title="关联方类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-OrgOrPsn"/>
                <bc:column field="relName" title="关联方名称" width="100"/>
                <bc:column field="relNumber" title="证件号码" width="100"/>
                <bc:column field="transType" title="交易类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/INS-tranType"/>
                <bc:column field="businessType" title="业务类型" width="100"/>
                <bc:column field="transactionAmount" title="合同金额（万元）" width="100"/>
                <%--				<bc:column field="formName" title="表单名称" width="100"/>--%>
                <bc:column field="starterDeptName" title="发起部门" width="100"/>
                <bc:column field="starterName" title="发起人" width="100"/>
                <bc:column field="startTimeString" title="开始时间" width="100"/>
                <bc:column field="status" title="状态" width="100" formatter="fixedDict:ProcInstStatus"/>
                <%--                <bc:column field="bizNameString" title="相关信息" width="100"/>--%>
                <be:event type="onClickRow">onClickRow</be:event>
                <be:event type="onCheck">onClickRow</be:event>
            </bc:table>
            <bc:tabs id="tabContent">
                <%--                <bc:table id="tabContent" > &lt;%&ndash;idField="id" url="${base}/workflow/process" queryUrl="queryHistory‘&ndash;%&gt;--%>
                <bc:tab id="tab1" title="审批记录" visible="true">
                    <div style="overflow:auto;">
                        <div id="logContent">

                        </div>
                    </div>
                </bc:tab>
                <bc:tab id="tab2" title="流程模板">
                    <div style="overflow:auto;">
                        <img id="img" alt="流程模板"/>
                    </div>

                </bc:tab>
                <%--                </bc:table>--%>
            </bc:tabs>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script type="text/javascript">

    function ajaxSubmit(url1, url2) {
        debugger
        $.ajax({
            url: '${base}' + url1,
            method: "GET",
            dataType: "JSON",
            traditional: true,
            success: function (data) {
                debugger
                // alert(data.success)
                if (data.success) {
                    window.location = '${base}' + url2
                } else {
                    $.messager.toastr('提示', data.msg, 'info');
                }
            }
        })

    }


    function viewBizHandler() {
        var taskid = getTaskIdSelections();
        let procCode = getProcCodeSelections();
        let procInsId = getProcInsIdSelections();
        let status = getStatusSelections();
        if (status != '40') {
            var tables = $('#procInstTable').jTable('getCheckData');
            if (tables.length == 1) {
                window.location = '${base}/workflow/process/view?procInstId=' + tables[0].id + '&option=approve/todoList';
            } else {
                $.messager.toastr('提示', '请先选择需要查看的数据。');
            }
        } else {
            var tables = $('#procInstTable').jTable('getCheckData');
            if (tables.length == 1) {
                window.location = '${base}/sub/process/queryData?procInsId=' + procInsId+'&procCode=' + procCode;
            } else {
                $.messager.toastr('提示', '请先选择需要查看的数据。');
            }

        }

        // alert(taskid + '===' + procCode)
        // var tables = $('#procInstTable').jTable('getCheckData');
        /*debugger
        if (tables.length == 1) {
            if (procCode == 'zjcc_Psn001') {   //关联自然人
                var url1 = '/fil/filRelPsn/viewA/' + taskid;
                var url2 = '/fil/filRelPsn/viewB/' + taskid+'?wt=0';
                ajaxSubmit(url1, url2)
            } else if (procCode == 'zjcc_ORG001') {  //关联机构
                var url1 = '/fil/relOrg/viewA/' + taskid;
                var url2 = '/fil/relOrg/viewB/' + taskid+'?wt=0';
                ajaxSubmit(url1, url2)
            } else if (procCode == 'zjcc_tra001') {  //关联交易保险业务类
                var url1 = '/ins/filBusiness/viewA/' + taskid;
                var url2 = '/ins/filBusiness/viewB/' + taskid+'?wt=0';
                ajaxSubmit(url1, url2)
            } else if (procCode == 'zjcc_tra001') {  //关联交易其他四类
                // alert('关联交易其他四类')
                var sear = new RegExp('o');
                if (sear.test(taskid)) {
                    var str = taskid.toString();
                    let splitElement = str.split('o')[0];
                    if (splitElement == '1') { //投资入股类
                        var url1 = '/ins/filInvestment/viewA/' + taskid;
                        var url2 = '/ins/filInvestment/viewB/' + taskid+'?wt=0';
                        ajaxSubmit(url1, url2)
                    } else if (splitElement == '2') { //资金运用类
                        var url1 = '/ins/filFunds/viewA/' + taskid;
                        var url2 = '/ins/filFunds/viewB/' + taskid+'?wt=0';
                        ajaxSubmit(url1, url2)
                    } else if (splitElement == '4') {   //利益转移类
                        var url1 = '/ins/filInterest/viewA/' + taskid;
                        var url2 = '/ins/filInterest/viewB/' + taskid+'?wt=0';
                        ajaxSubmit(url1, url2)
                    } else if (splitElement == '5') {   //提供服务类
                        var url1 = '/ins/filBusiness/viewA/' + taskid;
                        var url2 = '/ins/filBusiness/viewB/' + taskid+'?wt=0';
                        ajaxSubmit(url1, url2)
                    }
                }
            } else {
                $.messager.toastr('提示', '该节点没有数据备案。', 'info');
            }

        } else {
            $.messager.toastr('提示', '请先选择需要查看的数据。', 'info');
        }*/
    }

    function downBizHandler() {
        var tables = $('#procInstTable').jTable('getCheckData');
        let id = getProcInsIdSelections();
        let status = getStatusSelections();
        let baseProCode ="FXBDX_ZDTY_BranchOffice";
        let procCode = getProcCodeSelections();
        if (tables.length == 1) {
            if(baseProCode.includes(procCode)){
                if(status == '40'){
                    var url = '${base}/ins/traInsenclosure/downFileHisLog/' + id;
                    var elemIF = document.createElement("iframe");
                    elemIF.src = url;
                    elemIF.style.display = "none";
                    document.body.appendChild(elemIF);
                }else {
                    $.messager.toastr('提示', '请先选择已归档的数据。');
                }
            }else {
                $.messager.toastr('提示', '非重大审批不生成审批记录文件。');
            }
        }else {
            $.messager.toastr('提示', '请先选择需要查看的数据。');
        }
    }

    /* 选中模板，展示模板内容 */
    function onClickRow(row) {
        /*图片路径*/
        var imgUrl = '${base}/workflow/diagram/exedFlowImage/' + row['id'];
        /*加载流程图片  */
        document.getElementById("img").src = imgUrl;

        var logUrl = '${base}/workflow/procLog/hisLog/' + row['id'];
        $.mask.post(logUrl, {}, function (data) {
            if (data.success) {

                $('#logContent').html(data.logContent);
                console.log(data);
            }
        });
    }


    function getTaskIdSelections() {
        return $.map($('#procInstTable').bootstrapTable('getSelections'), function (row) {
            return row.taskId;//返回数据行中的taskId 备案编号
        });
    }

    function getProcCodeSelections() {
        return $.map($('#procInstTable').bootstrapTable('getSelections'), function (row) {
            return row.procCode;//返回数据行中的procCode 业务流程编码
        });
    }

    function getProcInsIdSelections() {
        return $.map($('#procInstTable').bootstrapTable('getSelections'), function (row) {
            return row.id;
        });
    }

    function getStatusSelections() {
        return $.map($('#procInstTable').bootstrapTable('getSelections'), function (row) {
            return row.status;
        });
    }


</script>
</body>
</html>
