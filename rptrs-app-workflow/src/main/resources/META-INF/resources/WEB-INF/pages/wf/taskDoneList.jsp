<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>流程实例列表</title>
    <x:jsLib/>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="" subTitle="" col="12" collapsible="true">
<%--            <bf:form id="queryForm" validation="true">--%>
<%--                <bc:row cls="m-b">--%>
<%--                    <bf:formGroup col="4">--%>
<%--                        <bc:label text="业务流程名称" col="5" formControl="true"></bc:label>--%>
<%--                        <bf:text name="__LIKES_procName" col="7" placeholder="业务流程名称"></bf:text>--%>
<%--                    </bf:formGroup>--%>
<%--                    <bf:formGroup col="4">--%>
<%--                        <bc:label text="模块名称" col="5" formControl="true"></bc:label>--%>
<%--                        <bf:text name="__LIKES_moduleName" col="7" placeholder="模块名称"></bf:text>--%>
<%--                    </bf:formGroup>--%>
<%--                    &lt;%&ndash;<bc:col col="3" colOffset="1">&ndash;%&gt;--%>
<%--                    &lt;%&ndash;<bc:button id="btnQuery" text="查询" icon="search" type="primary"/>&ndash;%&gt;--%>
<%--                    &lt;%&ndash;<bc:button id="btnReset" text="重置" icon="refresh"/>&ndash;%&gt;--%>
<%--                    &lt;%&ndash;</bc:col>&ndash;%&gt;--%>
<%--                </bc:row>--%>
<%--                <bc:row cls="m-b">--%>
<%--                    <bc:col col="12" colOffset="5">--%>
<%--                        <bc:button id="btnQuery" text="查询" icon="search" type="primary"/>--%>
<%--                        <bc:button id="btnReset" text="重置" icon="refresh"/>--%>
<%--                    </bc:col>--%>
<%--                </bc:row>--%>
<%--            </bf:form>--%>
            <bc:table id="taskInstTable" idField="id" url="${base}/workflow/approve" queryUrl="queryHis">
                <bc:toolbar id="viewBiz" text="查看业务数据" type="button" handler="viewBizHandler111"/>
<%--                <bc:toolbar id="down" text="下载文件" type="button" handler="downBizHandler"/>--%>
                <bc:column field="taskId" title="备案编号" width="100" visible="false"/>
                <bc:column field="id" title="流程实例ID" width="100" visible="false"/>
                <bc:column field="procCode" title="业务流程编码" width="100" visible="false" />
                <bc:column field="procName" title="流程名称" width="100"/>
                <bc:column field="relType" title="关联方类型" width="100"  formatter="custom:sys/dictItem/getDictItems/BUS-OrgOrPsn"/>
                <bc:column field="relName" title="关联方名称" width="100"/>
                <bc:column field="relNumber" title="证件号码" width="100"/>
                <bc:column field="transType" title="交易类型" width="100" formatter="custom:sys/dictItem/getDictItems/INS-tranType"/>
                <bc:column field="businessType" title="业务类型" width="100"/>
                <bc:column field="transactionAmount" title="合同金额（万元）" width="100"/>
                <%--				<bc:column field="formName" title="表单名称" width="100"/>--%>
                <bc:column field="starterDeptName" title="发起部门" width="100"/>
                <bc:column field="starterName" title="发起人" width="100"/>
                <bc:column field="startTimeString" title="开始时间" width="100"/>
                <bc:column field="status" title="状态" width="100" formatter="fixedDict:ProcInstStatus"/>
                <be:event type="onClickRow">onClickRow</be:event>
                <be:event type="onCheck">onClickRow</be:event>
            </bc:table>
            <bc:tabs id="tabContent">
                <bc:tab id="tab1" title="审批记录" visible="true">
                    <div style="overflow:auto;">
                        <div id="logContent"></div>
                    </div>
                </bc:tab>
                <bc:tab id="tab2" title="流程模板">
                    <div style="overflow:auto;">
                        <img id="img" alt="流程模板"/>
                    </div>
                </bc:tab>
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

    /* 查看业务数据 */
    function viewBizHandler(){
        var tables =  $('#taskInstTable').jTable('getCheckData');
        if(tables.length == 1)
        {
            window.location = '${base}/workflow/process/view?procInstId=' + tables[0].id + '&option=approve/todoList';
        }
        else
        {
            $.messager.toastr('提示', '请先选择需要查看的数据。');
        }
    }


    /*
    * 按钮-----查看业务数据
    * */
    function viewBizHandler111() {
        var tables = $('#taskInstTable').jTable('getCheckData');
        let procCode = getProcCodeSelections();
        let taskid = getTaskIdSelections();
        let status = getStatusSelections();
        let procInsId = getProcInsIdSelections();
        if (tables.length == 1) {

            if (status == '40' || status == '50' || status == '60') {
                window.location = '${base}/sub/process/queryData?procInsId=' + procInsId+'&procCode=' + procCode;

                /*$.ajax({
                    url: '${base}/sub/process/hasData?procCode='+procCode +'&procInsId='+procInsId+'&fillNumber='+taskid,
                    method: "GET",
                    dataType: "JSON",
                    traditional: true,
                    success: function (data) {
                        // debugger
                        //alert(data.success)
                        if (data.success) {
                            window.location='${base}/sub/process/queryFormData?procCode='+procCode +'&procInsId='+procInsId+'&fillNumber='+taskid
                        } else {
                            $.messager.toastr('提示', data.msg, 'info');
                        }
                    }
                })*/

                /*if (procCode == 'zjcc_Psn001') {   //关联自然人
                    var url1 = '/fil/filRelPsn/viewA/' + taskid;
                    var url2 = '/fil/filRelPsn/viewB/' + taskid+'?wt=2';
                    ajaxSubmit(url1, url2)
                } else if (procCode == 'zjcc_ORG001') {  //关联机构
                    var url1 = '/fil/relOrg/viewA/' + taskid;
                    var url2 = '/fil/relOrg/viewB/' + taskid+'?wt=2';
                    ajaxSubmit(url1, url2)
                } else if (procCode == 'zjcc_tra001') {  //关联交易保险业务类
                    var url1 = '/ins/filBusiness/viewA/' + taskid;
                    var url2 = '/ins/filBusiness/viewB/' + taskid+'?wt=2';
                    ajaxSubmit(url1, url2)
                } else if (procCode == 'zjcc_tra001') {  //关联交易其他四类
                    debugger
                    var sear = new RegExp('o');
                    if (sear.test(taskid)) {
                        // alert('关联交易其他四类')
                        var str = taskid.toString();
                        let splitElement = str.split('o')[0];
                        if (splitElement == '1') { //投资入股类
                            var url1 = '/ins/filInvestment/viewA/' + taskid;
                            var url2 = '/ins/filInvestment/viewB/' + taskid+'?wt=2';
                            ajaxSubmit(url1, url2)
                        } else if (splitElement == '2') { //资金运用类
                            var url1 = '/ins/filFunds/viewA/' + taskid;
                            var url2 = '/ins/filFunds/viewB/' + taskid+'?wt=2';
                            ajaxSubmit(url1, url2)
                        } else if (splitElement == '4') {   //利益转移类
                            var url1 = '/ins/filInterest/viewA/' + taskid;
                            var url2 = '/ins/filInterest/viewB/' + taskid+'?wt=2';
                            ajaxSubmit(url1, url2)
                        } else if (splitElement == '5') {   //提供服务类
                            var url1 = '/ins/filBusiness/viewA/' + taskid;
                            var url2 = '/ins/filBusiness/viewB/' + taskid+'?wt=2';
                            ajaxSubmit(url1, url2)
                        }
                    } else {
                        $.messager.toastr('提示', '备案号异常,请联系管理员。', 'info');
                    }
                } else {
                    $.messager.toastr('提示', '该节点没有数据备案。', 'info');
                }*/

            } else {
                window.location = '${base}/workflow/process/view?procInstId=' + tables[0].id + '&option=approve/doneList';
            }

        } else {
            $.messager.toastr('提示', '请先选择需要查看的数据。');
        }
    }

    function downBizHandler() {
        var tables = $('#taskInstTable').jTable('getCheckData');
        let id = getProcInsIdSelections();
        let status = getStatusSelections();
        if (tables.length == 1) {
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
            }
        });

        /*if(row['status'] == '40' || row['status'] == '50' || row['status'] == '60' ){
            $('#taskInstTable_tb_viewBiz').attr('disabled', true);
        }
        else
        {
            $('#taskInstTable_tb_viewBiz').attr('disabled', false);
        } */
    }

    function getTaskIdSelections() {
        return $.map($('#taskInstTable').bootstrapTable('getSelections'), function (row) {
            return row.taskId;//返回数据行中的taskId 备案编号
        });
    }

    function getProcCodeSelections() {
        return $.map($('#taskInstTable').bootstrapTable('getSelections'), function (row) {
            return row.procCode;//返回数据行中的procCode 业务流程编码
        });
    }

    function getStatusSelections() {
        return $.map($('#taskInstTable').bootstrapTable('getSelections'), function (row) {
            return row.status;//返回数据行中的procCode 业务流程编码
        });
    }
    function getProcInsIdSelections() {
        return $.map($('#taskInstTable').bootstrapTable('getSelections'), function (row) {
            return row.id;//返回数据行中的procCode 业务流程编码
        });
    }
</script>
</body>
</html>
