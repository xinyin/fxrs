<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <br:css pack="list"/>
    <title>TraCredit</title>
    <script>

         /* 导出*/

        /* function exportHandler() {

             var relatedName = $("#relatedName").val();
             var relType = $("#relType").val();
             var identNumber = $("#identNumber").val();

             var status = $("#status").val();
             var regulatorOth1 = $("#regulatorOth1").val();
             var importantTrade = $("#importantTrade").val();
             var  manageOrg=$("#importantTrade").val();
             var dataResources = $("#dataResources").val();


             var data = relatedName+","+relType+","+identNumber+","+status+","+regulatorOth1+","+importantTrade+","+manageOrg+","+dataResources;

             var url='/sys/xlsie/TraCreditExportCallbackImpl/' + data;

             var elemIF = document.createElement("iframe");
             elemIF.src = url;
             elemIF.style.display = "none";
             document.body.appendChild(elemIF);

         }*/
        /*导出*/
         function exportHandler() {

             var relatedName = $("#relatedName").val();
             var relType = $("#relType").val();
             var identNumber = $("#identNumber").val();

             var status = $("#status").val();
             var regulatorOth1 = $("#regulatorOth1").val();
             var importantTrade = $("#importantTrade").val();
             var  manageOrg=$("#importantTrade").val();
             var dataResource = $("#dataResource").val();
            var data = relatedName+","+relType+","+identNumber+","+status+","+regulatorOth1+","+importantTrade+","+manageOrg+","+dataResource+",";

             var url='${base}/sys/xlsie/TraCreditDealExportCallbackImpl/' + data;

             var elemIF = document.createElement("iframe");
             elemIF.src = url;
             elemIF.style.display = "none";
             document.body.appendChild(elemIF);
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

        /* 任务下发 */
        function sendCommand(){
            var tables =  $('#traCreditTable').jTable('getCheckData');
            if(tables.length == 1)
            {
                $.messager.modal({
                    winId: 'traCreditSend',
                    width: 700,
                    height: 420,
                    title: '选择机构',
                    url: '${base}/bus/traCredit/traCreditSend',
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
        function frmReset() {
            window.location = '${base}/bus/traCredit/list';
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
                        <bc:label text="关联方名称" col="5" formControl="true"/>
                        <bf:text id="relatedName" name="__LIKES_relatedName" col="7" placeholder="关联方名称"/>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="关联方类型" col="5" formControl="true"/>
                        <bf:select id="relType" name="__LIKES_relType" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-RelatedPartiesType">
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
                        <bf:text id="identNumber" name="__LIKES_identNumber" col="7" placeholder="证件号码"/>
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
                        <bc:label text="口径" col="5" formControl="true"/>
                        <bf:select id="regulatorOth1" name="__LIKES_regulatorOth1" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-Caliber">
                            <bf:option text="ALL" value=""></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_regulatorOth1");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="是否重大关联交易" col="5" formControl="true"/>
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
                    <bf:formGroup col="4">
                        <bc:label text="交易机构" col="5" formControl="true"/>
                        <bf:select id="manageOrg" name="__EQS_manageOrg" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-GHOrg">
                            <bf:option text="ALL" value=""></bf:option>
                            <be:event type="select">
                                var input = $("#__EQS_manageOrg");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="数据来源" col="5" formControl="true"/>
                        <bf:select id="dataResource" name="__LIKES_dataSource" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-DataSource">
                            <bf:option text="ALL" value=""></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_dataSource");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>

                    <%--<bc:col col="3" colOffset="1">--%>
                        <%--<bc:button id="btnQuery" type="danger" text="查询" icon="search"/>--%>
                        <%--<bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>--%>
                    <%--</bc:col>--%>
                </bc:row>
                <bc:row cls="m-b">
                    <div class="col-sm-12 col-sm-offset-5">
                        <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                        <bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
                    </div>

                </bc:row>
            </bf:form>
            <bc:table id="traCreditTable" idField="pid" url="${base}/bus/traCredit">
                <bc:toolbar id="add" text="新增" type="button"/>
                <bc:toolbar id="download" text="编辑" type="button" handler="editHandler"/>
                <bc:toolbar id="remove" text="删除" type="button" handler="removeHandler"/>
                <bc:toolbar id="import" text="导入" type="button" handler="importHandler"/>
                <bc:toolbar id="" text="任务下发" type="button" handler="sendCommand"/>
                <bc:toolbar id="out" text="模板下载" type="button" handler="outHandler"/>
                <bc:toolbar id="sub" text="提交审批" handler="sub" type="button"/>
                <bc:toolbar id="export" text="导出" type="button" handler="exportHandler"/>
                <bc:column field="pid" title="自增主键" width="100" visible="false"/>
                <bc:column field="relatedName" title="关联方名称" width="100"/>
                <bc:column field="manageOrg" title="交易机构" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-GHOrg"/>
                <bc:column field="relType" title="关联方类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-RelatedPartiesType"/>
                <bc:column field="identifyType" title="证件类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-IdentifyType" visible="false"/>
                <bc:column field="identNumber" title="证件号码" width="100"/>
                <bc:column field="tranType" title="交易类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-BusinessType"/>
                <bc:column field="businessType" title="业务类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-TtradeType" visible="false"/>
                <bc:column field="creditAmount" title="授信额度" width="100"/>
                <bc:column field="importantTrade" title="是否重大关联交易" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-isNo"/>
                <bc:column field="regulatorOth1" title="统计口径" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-Caliber"/>
                <bc:column field="dataResource" title="数据来源" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-DataSources"/>
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
                <bc:column field="isDel" title="是否删除" width="100" visible="false"/>
                <bc:operator id="view" icon="search" title="查看"/>
                <%-- <bc:operator id="edit" icon="edit" title="编辑"/>--%>
                <%-- <bc:operator id="remove" icon="trash" title="移除"/>--%>
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
        if (dataResource != '1') {
            alert('抽取的数据不能修改');
            return false;
        }
        var status = getStatusSelections();
        if (status == '4') {
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
        if (dataResource != '1') {
            alert('抽取的数据不能删除');
            return false;
        }
        var status = getStatusSelections();
        if (status == '4') {
            alert('数据正在审批中');
            return false;
        }
        if ('6' == status) {
            alert('数据已审批通过');
            return false;
        }
        if (confirm("确定删除?")) {
            var pid = getIdSelections();
            $.ajax({
                type: 'post',
                url: '${base}/bus/traCredit/remove/' + pid,
                data: 'json',
                success: function (data) {
                    if (data.success) {
                        alert(data.msg);
                        window.location = '${base}/bus/traCredit/list';
                    }
                }
            });
        }
    }

    function sub() {
        var status = getStatusSelections();
        if (status != '3') {
            alert('不能提交审批');
            return false;
        }
        var pid = getIdSelections();
        window.location = '${base}/bus/traCredit/submit/' + pid;
    }
</script>
</body>
</html>