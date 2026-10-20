<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>traInternaiTran</title>
    <script>
        /*导入*/
        function importHandler(){
            $.messager.modal({
                winId: 'wImportFromExcel', //winid只能为 wImportFromExcel
                width: 500,
                height: 230,
                args: {
                    callback:loadCallback
                },
                title: '内部交易管理信息导入',
                url: '${base}/sys/xlsie/import?bean=TraCreditImportCallbackImpl',
                onClose: function(){
                    location.reload();
                    loadCallback();
                }
            });
        }
        /*模板下载*/

        function outHandler(){
            var data = "11";
            var url='${base}/sys/xlsie/TraCreditOutExportCallbackImpl/'+ data;
            var elemIF = document.createElement("iframe");
            elemIF.src = url;
            elemIF.style.display = "none";
            document.body.appendChild(elemIF);
        }

        /*导出*/

        function exportHandler() {

            var relatedName = $("#relatedName").val();
            var identifyType = $("#identifyType").val();
            var identNumber = $("#identNumber").val();

            var status = $("#status").val();
            var businessType = $("#businessType").val();
            var tranType = $("#tranType").val();

            var dataResource = $("#dataResource").val();
            var  importantTrade=$("#importantTrade").val();

             var data = relatedName+","+identifyType+","+identNumber+","+status+","+businessType+","+tranType+","+dataResource+","+importantTrade+",";

            var url='${base}/sys/xlsie/TraCreditExportCallbackImpl/' + data;

            var elemIF = document.createElement("iframe");
            elemIF.src = url;
            elemIF.style.display = "none";
            document.body.appendChild(elemIF);

        }

        /* 任务下发 */
        function sendCommand(){
            var tables =  $('#traCreditTable').jTable('getCheckData');
            if(tables.length == 1)
            {
                $.messager.modal({
                    winId: 'traInternaiTranSend',
                    width: 700,
                    height: 420,
                    title: '选择机构',
                    url: '${base}/bus/traInternaiTran/traInternaiTranSend',
                    args: {orgId: tables[0].pid},
                    onClose: function(){
                        $('#traCreditTable').jTable('clearCheckData');
                        $('#traCreditTable').jTable('refresh');
                    }
                });
            }
            else
            {
                $.messager.toastr('提示', '请选择一条数据');
            }
        }

        //重置
        function frmReset(){
            window.location = '${base}/bus/traInternaiTran/list';
        }

    </script>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="" subTitle="" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">
                    <%--<bf:formGroup col="4">
                        <bc:label text="自增主键" col="5" formControl="true"/>
                        <bf:text name="__LIKES_pid" col="7" placeholder="自增主键"/>
                    </bf:formGroup>--%>

                    <bf:formGroup col="4">
                        <bc:label text="内部人名称" col="5" formControl="true"/>
                        <bf:text id="relatedName" name="__LIKES_relatedName" col="7" placeholder="内部人名称"/>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="证件类型" col="5" formControl="true"/>
                        <bf:select id="identifyType" name="__LIKES_identifyType" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-IdentifyType">
                            <bf:option text="ALL" value=""></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_identifyType");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="证件号码" col="5" formControl="true"/>
                        <bf:text  id="identNumber" name="__LIKES_identNumber" col="7" placeholder="证件号码"/>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="状态" col="5" formControl="true"/>
                        <bf:select id="status" name="__LIKES_status" col="7" dataSource="custom"
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
                        <bf:select id="businessType" name="__LIKES_businessType" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-BusinessType">
                            <bf:option text="ALL" value=""></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_businessType");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="交易类型" col="5" formControl="true"/>
                        <bf:select id="tranType" name="__LIKES_tranType" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-TtradeType">
                            <bf:option text="ALL" value=""></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_businessType");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="数据来源" col="5" formControl="true"/>
                        <bf:select name="__LIKES_dataSources" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-DataSources">
                            <bf:option text="ALL" value=""></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_dataSources");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="是否重大内部交易" col="5" formControl="true"/>
                        <bf:select id="importantTrade" name="__LIKES_importantTrade" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-isNo">
                            <bf:option text="ALL" value=""></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_importantTrade");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>

                   <%-- <bc:col col="3" colOffset="1">
                    <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                    <bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
                </bc:col>--%>
                </bc:row>
                <bc:row cls="m-b">
                    <div class="col-sm-12 col-sm-offset-5">
                        <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                        <bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
                    </div>

                </bc:row>
            </bf:form>
            <bc:table id="traCreditTable" idField="pid" url="${base}/bus/traInternaiTran">
                <bc:toolbar id="add" text="新增" type="button"/>
                <bc:toolbar id="download" text="编辑" type="button" handler="editHandler"/>
                <bc:toolbar id="remove" text="删除" type="button" handler="removeHandler"/>
                <bc:toolbar id="import" text="导入" type="button" handler="importHandler"/>
                <bc:toolbar id="send" text="任务下发" type="button" handler="sendCommand"/>

                <bc:toolbar id="out" text="模板下载" type="button" handler="outHandler"/>
                <bc:toolbar id="sub" text="提交审批" handler="sub" type="button"/>
                <bc:toolbar id="export" text="导出" type="button" handler="exportHandler"/>
                <%--<bc:toolbar id="add" text="新增" type="button"/>
                <bc:column field="pid" title="自增主键" width="100"/>
                <bc:column field="pid" title="序号" width="100"/>--%>
                <bc:column field="relatedName" title="内部人名称" width="100"/>
                <bc:column field="manageOrg" title="交易机构" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-GHOrg"/>
                <bc:column field="identifyType" title="证件类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-IdentifyType"/>
                <bc:column field="identNumber" title="证件号码" width="100"/>
                <bc:column field="tranType" title="交易类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-TtradeType"/>
                <bc:column field="businessType" title="业务类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-BusinessType"/>
                <bc:column field="creditAmount" title="交易金额" width="100" formatter=""/>
                <bc:column field="importantTrade" title="是否重大关联交易" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-isNo"/>
                <%--<bc:column field="regulator" title="统计口径" width="100"/>--%>
                <bc:column field="approvalTime" title="审批时间" width="100" visible="false"/>
                <bc:column field="validityContract" title="合同有效期" width="100" visible="false"/>
                <bc:column field="status" title="状态" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-Applystatus"/>
                <bc:column field="statusComment" title="状态说明" width="100"/>
                <bc:column field="validityBalance" title="授信余额" width="100" visible="false"/>
                <bc:column field="creditStartDate" title="授信起始日期" width="100" visible="false"/>
                <bc:column field="creditEndDate" title="授信到期日" width="100" visible="false"/>
                <bc:column field="guaraMode" title="担保方式" width="100" visible="false"/>
                <bc:column field="collateral" title="担保品情况" width="100" visible="false"/>
                <bc:column field="guarantor" title="保证人名称" width="100" visible="false"/>
                <bc:column field="fiveLevel" title="五级分类" width="100" visible="false"/>
                <bc:column field="securitDeposit" title="保证金金额" width="100" visible="false"/>
                <bc:column field="marginRatio" title="保证金比例" width="100" visible="false"/>
                <bc:column field="dataResource" title="数据来源" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-DataSource"/>
               <bc:column field="contractSignDate" title="合同签署日期" width="100" />
                <%--<bc:column field="isDel" title="是否删除" width="100"/>--%>
                <bc:operator id="view" icon="search" title="查看"/>
                <%--<bc:operator id="edit" icon="edit" title="编辑"/>--%>
                <%--<bc:operator id="remove" icon="trash" title="移除"/>--%>
            </bc:table>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>

<script src="../../static/js/jquery.dialog.js"></script>
<script type="text/javascript">
    function send() {
        $.ajax({
            type: "get",
            url: "/test/initData",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                if (data.statusCode == 100) {
                    var options = "<option>--请选择--</option>";
                    for (var i = 0; i < data.data.length; i++) {
                        options += "<option>" + data.data[i].companyName + "</option>";
                    }
                    $(".customerIds").html(options);
                    //活动名称下拉选改变事件：
                    $(".customerIds").change(function () {
                        $(this).removeClass("redFrame");
                        console.log("活动名称下拉选改变事件：" + $(this).children('option:selected').val());
                        console.log("选择的具体是哪一个：" + $(this).get(0).selectedIndex);
                        var selectedIndex = $(this).get(0).selectedIndex;
                        if (selectedIndex == 0) {
                            console.log("没有进行选择");
                            $("#customerName").val("");
                            $("#customerMobile").val("");
                            $("#customerAddress").val("");
                            $("#customerWeb").val("");
                        } else {
                            $("#customerName").val(data.data[selectedIndex - 1].customer.customerName);
                            $("#customerMobile").val(data.data[selectedIndex - 1].customer.customerMobile);
                            $("#customerAddress").val(data.data[selectedIndex - 1].customer.customerAddress);
                            $("#customerWeb").val(data.data[selectedIndex - 1].customer.customerWeb);
                        }
                    });
                } else {
                    console.log("获取数据失败");
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {

            }
        });
        $.sendConfirm({
            title: '提交审批',
            content: '<div id="addIpBox" >'
                + '<div class="frm-item">'
                + '<div class="frm-label" >'
                + '<br>'
                + '选择审批人：'
                + '<br>'
                + '<span class="requireIcon">*</span  > 部门:</div>'
                + ' <select class="customerIds"><option >请选择</option><option >合规部</option><option >审计部</option></select>'
                + '</div>'
                + '<div class="frm-item">'
                + '<div class="frm-label"><span class="requireIcon">*</span> 审批人:</div>'
                + ' <select class="customerIds"><option >请选择</option><option >审批人1</option><option >审批人2</option></select>'
                + '</div>'
                + '</div>',
            button: {
                confirm: '确认',
                cancel: '取消'
            },
            width: 260,
            // onBeforeConfirm: function() {},
            onConfirm: function () {
                console.log('点击确认！');
            },
            onCancel: function () {
                console.log('点击取消！');
            },
            onClose: function () {
                console.log('点击关闭！');
            }
        });
    }

    //刷新表格
    function loadCallback() {
        $('#naturalPersonTable').jTable('refresh');
    }
</script>
<script type="text/javascript">


    function editHandler() {
        var dataResource = getdataResourceSelections();
        if (dataResource != '1'){
            alert('抽取的数据不能修改');
            return false;
        }
        var status = getStatusSelections();
        if (status == '4'){
            alert('审批中不能修改');
            return false;
        }

        var pid = getIdSelections();

        if (pid != "") {
            window.location = '${base}/bus/traCredit/edit/' + pid;
        } else {
            $.messager.alert('提示', '请选择关联方', 'info');
            return "";
        }
    }

    function getIdSelections() {
        return $.map($('#traCreditTable').bootstrapTable('getSelections'), function (row) {
            return row.pid;//返回数据行中的id值
        });
    }

    function getStatusSelections() {
        return $.map($('#traCreditTable').bootstrapTable('getSelections'), function (row) {
            return row.status;//返回数据行中的id值
        });
    }

    function getdataResourceSelections() {
        return $.map($('#traCreditTable').bootstrapTable('getSelections'), function (row) {
            return row.dataResource;//返回数据行中的id值
        });
    }

    function removeHandler() {
        var dataResource = getdataResourceSelections();
        if (dataResource != '1'){
            alert('抽取的数据不能删除');
            return false;
        }
        var status = getStatusSelections();
        if (status != '3'){
            alert('已提交审批的不能删除');
            return false;
        }
        var pid = getIdSelections();
        $.ajax({
            type: 'post',
            url: '${base}/bus/traCredit/remove/' + pid,
            data: 'json',
            success: function (data) {
                if (data.success) {
                    alert(data.msg);
                    window.location = '${base}/bus/traInternaiTran/list';
                }
            }
        });
    }

    function sub(){
        var status = getStatusSelections();
        if (status != '3'){
            alert('不能提交审批');
            return false;
        }
        var pid = getIdSelections();
        window.location = '${base}/bus/traCredit/submit/' + pid;
    }
</script>
</body>
</html>