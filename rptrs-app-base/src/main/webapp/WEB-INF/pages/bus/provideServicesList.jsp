<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <%@ include file="/WEB-INF/pages/include/common.jsp" %>
    <link rel="stylesheet" href="../../static/css/dialog.css">
    <br:css pack="list"/>
    <title>提供服务类</title>
    <script>

        /* 任务下发 */
        function sendCommand() {
            var tables = $('#provideServicesTable').jTable('getCheckData');
            if (tables.length == 1) {
                $.messager.modal({
                    winId: 'provideServicesSend',
                    width: 700,
                    height: 420,
                    title: '选择机构',
                    url: '${base}/bus/provideServices/provideServicesSend',
                    args: {orgId: tables[0].pid},
                    onClose: function () {
                        $('#provideServicesTable').jTable('clearCheckData');
                        $('#provideServicesTable').jTable('refresh');
                    }
                });
            } else {
                $.messager.toastr('提示', '请选择一条数据');
            }
        }


        //重置
        function frmReset() {
            window.location = '${base}/bus/provideServices/list';
        }
    </script>
</head>
<body class="gray-bg">
<div id="wrapper" class="animated fadeInRight">
    <div class="row">
        <bc:ibox title="" subTitle="" col="12" collapsible="true">
            <bf:form id="queryForm" validation="true">
                <bc:row cls="m-b">
                    <bf:formGroup col="4">
                        <bc:label text="关联方名称" col="5" formControl="true"/>
                        <bf:text name="__LIKES_relatedName" col="7" placeholder="关联方名称"/>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="证件类型" col="5" formControl="true"/>
                        <bf:select name="__LIKES_identlfyType" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-IdentifyType">
                            <bf:option text="ALL" value="%"></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_identlfyType");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="证件号码" col="5" formControl="true"/>
                        <bf:text name="__LIKES_identNumber" col="7" placeholder="证件号码"/>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="状态" col="5" formControl="true"/>
                        <bf:select name="__LIKES_status" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-Applystatus">
                            <bf:option text="ALL" value="%"></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_status");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="业务类型" col="5" formControl="true"/>
                        <bf:select name="__LIKES_businessType" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-ProvideServices">
                            <bf:option text="ALL" value="%"></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_businessType");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="统计口径" col="5" formControl="true"/>
                        <bf:select name="__LIKES_regulatorOth1" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-Caliber">
                            <bf:option text="ALL" value="%"></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_regulatorOth1");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                        <%--                        <select id="sel"--%>
                        <%--                                style="width:180px;height:28px;margin-left: 13px;border:1px solid  #e7eaec;background-color: #ffffff;color: inherit;padding: 5px 5px 5px 5px;">--%>
                        <%--                            <option value="1" name="regulatorYbj">银保监口径</option>--%>
                        <%--                            　　--%>
                        <%--                            <option value="2" name="regulatorZjh">证监会口径</option>--%>
                        <%--                            　　--%>
                        <%--                            <option value="3" name="regulatorCzb">财政部口径</option>--%>
                        <%--                        </select>--%>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="是否重大关联交易" col="5" formControl="true"/>
                        <bf:select name="__LIKES_importantTrade" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-isNo">
                            <bf:option text="ALL" value="%"></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_importantTrade");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="交易机构" col="5" formControl="true"/>
                        <bf:select name="__LIKES_houseManageOrg" col="7" dataSource="custom"
                                   url="sys/dictItem/getDictItems/BUS-GHOrg">
                            <bf:option text="ALL" value="%"></bf:option>
                            <be:event type="select">
                                var input = $("#__LIKES_houseManageOrg");
                                input.focus();
                                input.blur();
                            </be:event>
                        </bf:select>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="业务起始时间" col="5" formControl="true"/>
                        <bf:datebox id="businessStartTime" name="__GES_businessStartTime" col="7"/>
                    </bf:formGroup>
                    <bf:formGroup col="4">
                        <bc:label text="业务结束时间" col="5" formControl="true"/>
                        <bf:datebox id="businessEndTime" name="__LES_businessEndTime" col="7"/>
                    </bf:formGroup>
                </bc:row>
                <%--<bc:row cls="m-b">
                    <bc:col col="3" colOffset="1">
                        <bc:col col="3" colOffset="1">
                            <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                        </bc:col>
                        <bc:col col="3">
                            <bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
                        </bc:col>
                    </bc:col>
                </bc:row>--%>
            </bf:form>
            <bc:row cls="m-b">
                <div class="col-sm-12 col-sm-offset-5">
                    <bc:button id="btnQuery" type="danger" text="查询" icon="search"/>
                    <bc:button id="btnReset" type="primary" text="重置" icon="refresh" click="frmReset()"/>
                </div>

            </bc:row>
            <bc:table id="provideServicesTable" idField="pid" url="${base}/bus/provideServices">
                <bc:toolbar id="add" text="新增" type="button"/>
                <bc:toolbar id="download" text="编辑" type="button" handler="editHandler"/>
                <bc:toolbar id="remove" text="删除" type="button" handler="removeHandler"/>
                <bc:toolbar id="import" text="导入" type="button"/>
                <bc:toolbar id="importExp" text="任务下发" type="button" handler="sendCommand"/>
                <bc:toolbar id="" text="模板下载" type="button"/>
                <bc:toolbar id="sub" text="提交审批" handler="sub" type="button"/>
                <bc:toolbar id="" text="导出" type="button"/>
                <bc:column field="pid" title="序号" width="100" visible='false'/>
                <bc:column field="relatedName" title="关联方名称" width="100"/>
                <bc:column field="identlfyType" title="证件类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-IdentifyType"/>
                <bc:column field="identNumber" title="证件号码" width="100"/>
                <bc:column field="houseManageOrg" title="交易机构" width="100"/>
                <bc:column field="tranType" title="交易类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-BusinessType"/>
                <bc:column field="businessType" title="业务类型" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-ProvideServices"/>
                <bc:column field="transationAmount" title="交易金额/万元/人民币" width="100"/>
                <bc:column field="importantTrade" title="是否重大关联交易" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-isNo"/>
                <bc:column field="regulatorOth1" title="统计口径" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-Caliber"/>
                <bc:column field="businessEndTime" title="业务完成时间" width="100"/>
                <bc:column field="relatedTranType" title="关联交易类别" width="100" visible="false"/>
                <bc:column field="status" title="状态" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-Applystatus"/>
                <bc:column field="statusComment" title="状态说明" width="100"/>
                <bc:column field="dataResource" title="数据来源" width="100"
                           formatter="custom:sys/dictItem/getDictItems/BUS-DataSources"/>
                <bc:operator id="view" icon="search" title="查看"/>

            </bc:table>
        </bc:ibox>
    </div>
</div>
<br:js pack="list"/>
<be:processEvent/>
<script src="../../static/js/jquery.dialog.js"></script>
<script type="text/javascript">
    /*  $("#beginDate").datetimepicker({
          format:"yyyy-mm-dd hh:ii:00",
          language:"zh-CN",
          minuteStep:30,
          autoclose:2,
          todayBtn:"linked",
          minView: "hour"
      });

      $("#endDate").datetimepicker({
          format:"yyyy-mm-dd hh:ii:00",
          language:"zh-CN",
          minuteStep:30,
          autoclose:2,
          todayBtn:"linked",
          minView: "hour"
      });
  */

    function getIdSelections() {
        return $.map($('#provideServicesTable').bootstrapTable('getSelections'), function (row) {
            return row.pid;//返回数据行中的id值
        });
    }

    function getStatusSelections() {
        return $.map($('#provideServicesTable').bootstrapTable('getSelections'), function (row) {
            return row.status;//返回数据行中的id值
        });
    }

    function getdataResourceSelections() {
        return $.map($('#provideServicesTable').bootstrapTable('getSelections'), function (row) {
            return row.dataResource;//返回数据行中的id值
        });
    }

    function removeHandler() {
        var dataResource = getdataResourceSelections();
        if (dataResource != '1') {
            $.messager.alert('抽取的数据不能删除');
            return false;
        }
        var status = getStatusSelections();
        if (status != '3') {
            $.messager.alert('已提交审批的不能删除');
            return false;
        }
        if (confirm("确认删除?")) {
            var pid = getIdSelections();
            $.ajax({
                url: '${base}/bus/provideServices/remove/' + pid,
                type: 'post',
                data: 'json',
                success: function (data) {
                    if (data.success) {
                        $.messager.alert(data.msg);
                        window.location = '${base}/bus/provideServices/list';
                    }
                }
            });
        }

        var pid = getIdSelections();
        $.ajax({
            url: '${base}/bus/provideServices/remove/' + pid,
            type: 'post',
            data: 'json',
            success: function (data) {
                if (data.success) {
                    $.messager.alert(data.msg);
                    window.location = '${base}/bus/provideServices/list'
                }
            }
        });
    }

    function editHandler() {

        var dataResource = getdataResourceSelections();
        if (dataResource != '1') {
            $.messager.alert('抽取的数据不能修改');
            return false;
        }
        var status = getStatusSelections();
        if (status == '4') {
            $.messager.alert('审批中不能修改');
            return false;
        }
        var pid = getIdSelections();
        window.location = '${base}/bus/provideServices/edit/' + pid;
    }

    function sub() {
        var status = getStatusSelections();
        if (status != '3') {
            $.messager.alert('不能提交审批');
            return false;
        }
        var pid = getIdSelections();
        window.location = '${base}/bus/provideServices/submit/' + pid;
    }


    //提交审核
    /*function send(){
        $.ajax({
            type: "get",
            url: "/test/initData",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                if(data.statusCode==100) {
                    var options = "<option>--请选择--</option>";
                    for (var i = 0; i < data.data.length; i++) {
                        options+="<option>"+data.data[i].companyName+"</option>";
                    }
                    $(".customerIds").html(options);
                    //活动名称下拉选改变事件：
                    $(".customerIds").change(function () {
                        $(this).removeClass("redFrame");
                        console.log("活动名称下拉选改变事件："+$(this).children('option:selected').val());
                        console.log("选择的具体是哪一个："+$(this).get(0).selectedIndex);
                        var selectedIndex = $(this).get(0).selectedIndex;
                        if(selectedIndex==0){
                            console.log("没有进行选择");
                            $("#customerName").val("");
                            $("#customerMobile").val("");
                            $("#customerAddress").val("");
                            $("#customerWeb").val("");
                        }
                        else{
                            $("#customerName").val(data.data[selectedIndex-1].customer.customerName);
                            $("#customerMobile").val(data.data[selectedIndex-1].customer.customerMobile);
                            $("#customerAddress").val(data.data[selectedIndex-1].customer.customerAddress);
                            $("#customerWeb").val(data.data[selectedIndex-1].customer.customerWeb);
                        }
                    });
                }
                else{
                    console.log("获取数据失败");
                }
            },
            error:function (XMLHttpRequest, textStatus, errorThrown) {
                /!*alert(XMLHttpRequest.status);
                alert(XMLHttpRequest.readyState);
                alert(textStatus);*!/
            }
        });
        $.sendConfirm({
            title: '提交审批',
            content: '<div id="addIpBox" >'
                + '<div class="frm-item">'
                + '<div class="frm-label" >'
                + '<br>'
                +  '选择审批人：'
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
            onConfirm: function() {
                console.log('点击确认！');
            },
            onCancel: function() {
                console.log('点击取消！');
            },
            onClose: function() {
                console.log('点击关闭！');
            }
        });
    }

    //刷新表格
    function loadCallback(){
        $('#naturalPersonTable').jTable('refresh');
    }*/
</script>
</body>
</html>